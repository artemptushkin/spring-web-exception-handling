package io.github.artemptushkin.demo.springwebexceptionhandling;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * This test should run with a gradle property: `-PwebProfile=reactive`
 */
@ActiveProfiles("reactive")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { ReactiveControllerTest.TestConfiguration.class, SpringWebExceptionHandlingBestPracticesApplication.class})
class ReactiveControllerTest extends AbstractWebTest {

    @Configuration
    @EnableWebFlux
    public static class TestConfiguration {

    }

}
