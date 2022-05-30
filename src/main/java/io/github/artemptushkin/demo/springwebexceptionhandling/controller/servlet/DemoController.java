package io.github.artemptushkin.demo.springwebexceptionhandling.controller.servlet;

import io.github.artemptushkin.demo.springwebexceptionhandling.controller.CustomExceptionInController;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("servlet")
@RequestMapping("/demo")
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class DemoController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/it-throws")
    public void throwSomething() {
        throw new CustomExceptionInController("I'm a demo exception from controller");
    }
}
