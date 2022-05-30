package io.github.artemptushkin.demo.springwebexceptionhandling.filter.reactive;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInFilter;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class ExampleFilterThatThrows implements WebFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String headerThatBreaks = exchange.getRequest().getHeaders().getFirst("header-that-breaks");
        if (headerThatBreaks != null) {
            throw new CustomExceptionInFilter("I'm exception from filter that setup after the exception filter");
        }
        return chain.filter(exchange);
    }
}
