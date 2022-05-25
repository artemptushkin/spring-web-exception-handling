package io.github.artemptushkin.demo.springwebexceptionhandling.config;

import io.github.artemptushkin.demo.springwebexceptionhandling.filter.ExampleFilterThatThrows;
import io.github.artemptushkin.demo.springwebexceptionhandling.filter.ExceptionHandlingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebConfiguration {
    @Autowired
    ExceptionHandlingFilter exceptionHandlingFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .addFilterBefore(exceptionHandlingFilter, BasicAuthenticationFilter.class)
                .addFilterAfter(new ExampleFilterThatThrows(), ExceptionHandlingFilter.class)
                .build();
    }
}
