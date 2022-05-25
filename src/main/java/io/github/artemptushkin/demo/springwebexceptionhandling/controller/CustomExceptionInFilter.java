package io.github.artemptushkin.demo.springwebexceptionhandling.controller;

public class CustomExceptionInFilter extends RuntimeException {

    public CustomExceptionInFilter(String message) {
        super(message);
    }
}
