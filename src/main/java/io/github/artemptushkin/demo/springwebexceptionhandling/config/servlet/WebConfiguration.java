package io.github.artemptushkin.demo.springwebexceptionhandling.config.servlet;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInController;
import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInFilter;
import io.github.artemptushkin.demo.springwebexceptionhandling.filter.servlet.ExampleFilterThatThrows;
import io.github.artemptushkin.demo.springwebexceptionhandling.filter.servlet.ExceptionHandlingFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.Map;

@Configuration
@EnableWebSecurity
@Profile("servlet")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class WebConfiguration {

    @Bean
    public SecurityFilterChain filterChain(ExceptionHandlingFilter exceptionHandlingFilter, HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(exceptionHandlingFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(new ExampleFilterThatThrows(), ExceptionHandlingFilter.class)
                .build();
    }

    @Bean
    public HttpStatus defaultStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    @Bean
    public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
        return Map.of(
                CustomExceptionInController.class, HttpStatus.BAD_REQUEST,
                CustomExceptionInFilter.class, HttpStatus.BAD_REQUEST
        );
    }
}
