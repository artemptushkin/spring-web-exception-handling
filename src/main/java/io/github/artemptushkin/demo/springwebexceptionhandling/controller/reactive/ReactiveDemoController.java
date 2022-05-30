package io.github.artemptushkin.demo.springwebexceptionhandling.controller.reactive;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@Profile("reactive")
@RequestMapping("/demo")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.REACTIVE)
public class ReactiveDemoController {

    @GetMapping("/hello")
    public Mono<String> hello() {
        return Mono.just("hello");
    }

    @GetMapping("/it-throws")
    public Mono<Void> throwSomething() {
        throw new CustomExceptionInController("I'm a demo exception from controller");
    }
}
