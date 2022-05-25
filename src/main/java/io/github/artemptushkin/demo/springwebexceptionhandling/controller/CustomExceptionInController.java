package io.github.artemptushkin.demo.springwebexceptionhandling.controller;

public class CustomExceptionInController extends RuntimeException {

    public CustomExceptionInController(String message) {
        super(message);
    }
}
