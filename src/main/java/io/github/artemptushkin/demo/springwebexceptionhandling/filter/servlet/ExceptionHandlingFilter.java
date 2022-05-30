package io.github.artemptushkin.demo.springwebexceptionhandling.filter.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.artemptushkin.demo.springwebexceptionhandling.controller.servlet.ServletExceptionHandler;
import io.github.artemptushkin.demo.springwebexceptionhandling.controller.domain.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Profile("servlet")
@RequiredArgsConstructor
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ExceptionHandlingFilter extends OncePerRequestFilter {
    private final ServletExceptionHandler exceptionHandler;
    /**
     * naming this differently than _objectMapper_ you give a chance your code to pass a specific object mapper by the qualifier
     * the field name will be considered as the name of the bean
     */
    private final ObjectMapper exceptionHandlerObjectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            ResponseEntity<ErrorResponse> responseEntity = exceptionHandler.handleException(e);
            writeResponseEntity(responseEntity, response);
        }
    }

    private void writeResponseEntity(ResponseEntity<ErrorResponse> responseEntity, HttpServletResponse response) throws IOException {
        PrintWriter out = response.getWriter();
        ErrorResponse error = responseEntity.getBody();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(responseEntity.getStatusCodeValue());
        out.print(exceptionHandlerObjectMapper.writeValueAsString(error));
        out.flush();
    }
}
