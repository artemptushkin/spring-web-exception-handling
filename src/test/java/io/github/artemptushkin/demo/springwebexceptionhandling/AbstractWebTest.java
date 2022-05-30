package io.github.artemptushkin.demo.springwebexceptionhandling;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public abstract class AbstractWebTest {

    @LocalServerPort
    Integer serverPort;

    @BeforeEach
    void setup() {
        RestAssured.port = serverPort;
    }

    @Test
    void itThrowsExceptionInFilterAndReturnsBadRequest() {
        given()
                .header("header-that-breaks", "it-will-throw-exception-in-filter")
                .when()
                .request("GET", "/demo/hello")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("I'm exception from filter that setup after the exception filter"));
    }

    @Test
    void itThrowsExceptionInControllerAndReturnsBadRequest() {
        given()
                .when()
                .request("GET", "/demo/it-throws")
                .then()
                .statusCode(400)
                .body("code", equalTo(400))
                .body("message", equalTo("I'm a demo exception from controller"));
    }
}
