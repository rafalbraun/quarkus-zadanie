package org.acme.rest.client;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class GitHubResourceTest {

    @Test
    public void testGetUserRepositories() {
        String username = "octocat";

        RestAssured.given()
                .when()
                .get("/github/repos/" + username)
                .then()
                .statusCode(200)
                .body("$.size()", Matchers.greaterThan(0))
                .body("[0].name", Matchers.notNullValue())
                .body("[0].owner", Matchers.equalTo(username))
                .body("[0].branches.size()", Matchers.greaterThan(0))
                .body("[0].branches[0].name", Matchers.notNullValue())
                .body("[0].branches[0].lastCommitSha", Matchers.notNullValue())
                .body("fork", Matchers.everyItem(Matchers.equalTo(false)));
    }
}
