package io.github.artemptushkin.demo.springwebexceptionhandling.controller.domain;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ErrorResponse {
    String message;
    Integer code;
}
