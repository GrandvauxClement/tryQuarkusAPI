package com.amiltone;

import com.amiltone.rest.AuthorRessource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
//import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestHTTPEndpoint(AuthorRessource.class)
public class AuthorResourceTest {

   /* @Test
    public void getAll() {
        given()
    }*/
}
