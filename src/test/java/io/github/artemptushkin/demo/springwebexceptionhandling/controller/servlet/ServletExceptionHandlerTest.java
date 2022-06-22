package io.github.artemptushkin.demo.springwebexceptionhandling.controller.servlet;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.domain.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class ServletExceptionHandlerTest {

    @Test
    void itHandlesException() {
        Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode = prepareExceptionToStatusCodeMap();
        HttpStatus status = HttpStatus.OK;

        ServletExceptionHandler exceptionHandler = new ServletExceptionHandler(exceptionToStatusCode, status);

        Exception exception = prepareException();

        ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleException(exception);

        assertThat(responseEntity).isNotNull();
    }

    private Exception prepareException() {
        return mock(Exception.class);
    }

    private Map<Class<? extends Exception>, HttpStatus> prepareExceptionToStatusCodeMap() {
        return Map.of(
                NullPointerException.class, HttpStatus.BAD_REQUEST,
                IllegalAccessException.class, HttpStatus.BAD_GATEWAY
        );
    }

}