package io.github.artemptushkin.demo.springwebexceptionhandling.controller.reactive;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
public class ReactiveExceptionHandler extends AbstractErrorWebExceptionHandler {
    private final Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode;
    private final HttpStatus defaultStatus;

    public ReactiveExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                                    ApplicationContext applicationContext, Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode,
                                    HttpStatus defaultStatus) {
        super(errorAttributes, resources, applicationContext);
        this.exceptionToStatusCode = exceptionToStatusCode;
        this.defaultStatus = defaultStatus;
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {

        Throwable error = getError(request);
        log.error("An error has been occurred", error);
        HttpStatus httpStatus;
        if (error instanceof Exception exception) {
            httpStatus = exceptionToStatusCode.getOrDefault(exception.getClass(), defaultStatus);
        } else {
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return ServerResponse
                .status(httpStatus)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(ErrorResponse
                        .builder()
                        .code(httpStatus.value())
                        .message(error.getMessage())
                        .build())
                );
    }
}
