package org.acme.rest.github.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.acme.rest.github.dto.RepositoryDto;
import org.acme.rest.github.exceptions.UserNotFoundException;
import org.acme.rest.github.model.Branch;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import jakarta.enterprise.context.ApplicationScoped;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
@Produces("application/json")
@Path("/github")
public class GithubResource {

    private static final Logger LOGGER = Logger.getLogger(GithubResource.class.getName());

    @RestClient
    GithubService githubService;

    @GET
    @Path("/repos/{username}")
    @Blocking
    public Uni<List<RepositoryDto>> getRepositories(String username) {
        return githubService.getUserRepositories(username)
                .onFailure().transform(throwable -> {
                    if (throwable instanceof WebApplicationException webEx) {
                        if (webEx.getResponse().getStatus() == Response.Status.NOT_FOUND.getStatusCode()) {
                            throw new UserNotFoundException(username);
                        }
                    }
                    return throwable;
                })
                .onItem().transformToMulti(repos -> Multi.createFrom().iterable(repos))
                .filter(repo -> !repo.fork)
                .onItem().transformToUniAndMerge(repo -> getRepositoryBranches(repo.owner.login, repo.name)
                        .onItem().transform(branches -> new RepositoryDto(repo.name, repo.owner.login, repo.fork, branches)))
                .collect().asList();
    }

    public Uni<List<Branch>> getRepositoryBranches(String owner, String repoName) {
        return githubService.getRepositoryBranches(owner, repoName);
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("message", exception.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }

}
