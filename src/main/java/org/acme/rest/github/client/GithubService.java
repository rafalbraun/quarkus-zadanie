package org.acme.rest.github.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.acme.rest.github.model.Branch;
import org.acme.rest.github.model.Repository;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.PathParam;

import java.util.List;

@RegisterRestClient(baseUri = "https://api.github.com/")
public interface GithubService {

    @GET
    @Path("/users/{user}/repos")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<Repository>> getUserRepositories(@PathParam("user") String user);

    @GET
    @Path("/repos/{owner}/{repo}/branches")
    @Produces(MediaType.APPLICATION_JSON)
    Uni<List<Branch>> getRepositoryBranches(@PathParam("owner") String owner, @PathParam("repo") String repo);

}
