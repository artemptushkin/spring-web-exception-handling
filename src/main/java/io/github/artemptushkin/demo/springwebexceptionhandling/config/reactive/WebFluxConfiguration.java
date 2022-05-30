package io.github.artemptushkin.demo.springwebexceptionhandling.config.reactive;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInController;
import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInFilter;
import io.github.artemptushkin.demo.springwebexceptionhandling.controller.reactive.ReactiveExceptionHandler;
import io.github.artemptushkin.demo.springwebexceptionhandling.filter.reactive.ExampleFilterThatThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.reactive.config.EnableWebFlux;

import java.util.Map;

@Configuration
@EnableWebFlux
@Profile("reactive")
@EnableWebFluxSecurity
@EnableConfigurationProperties(WebProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class WebFluxConfiguration {

    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) {
        return http
                .addFilterAfter(new ExampleFilterThatThrows(), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }

    @Bean
    @Order(-2)
    @Profile("reactive")
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
    public ReactiveExceptionHandler reactiveExceptionHandler(WebProperties webProperties, ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        ReactiveExceptionHandler exceptionHandler = new ReactiveExceptionHandler(
                new DefaultErrorAttributes(), webProperties.getResources(), applicationContext, exceptionToStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR
        );
        exceptionHandler.setMessageWriters(configurer.getWriters());
        exceptionHandler.setMessageReaders(configurer.getReaders());
        return exceptionHandler;
    }

    @Bean
    public Map<Class<? extends Exception>, HttpStatus> exceptionToStatusCode() {
        return Map.of(
                CustomExceptionInController.class, HttpStatus.BAD_REQUEST,
                CustomExceptionInFilter.class, HttpStatus.BAD_REQUEST
        );
    }

}
