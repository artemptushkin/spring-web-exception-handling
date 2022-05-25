package io.github.artemptushkin.demo.springwebexceptionhandling;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.given;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringWebExceptionHandlingBestPracticesApplicationTests {

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
                .statusCode(400);
    }

    @Test
    void itThrowsExceptionInControllerAndReturnsBadRequest() {
        given()
                .when()
                .request("GET", "/demo/it-throws")
                .then()
                .statusCode(400);
    }

}
