package io.github.artemptushkin.demo.springwebexceptionhandling.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.artemptushkin.demo.springwebexceptionhandling.controller.DefaultExceptionHandler;
import io.github.artemptushkin.demo.springwebexceptionhandling.controller.domain.ErrorResponse;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ExceptionHandlingFilter extends OncePerRequestFilter {
    private final DefaultExceptionHandler exceptionHandler;
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
