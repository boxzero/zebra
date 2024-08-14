package com.houseclay.zebra.exceptionHandling;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder(toBuilder = true)
public class ErrorMessage {
    private UUID id;
    private String description;
}
