package com.glofox.classmanager.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateStudioClassRequest(
    @NotBlank(message = "name is mandatory.")
    String name,

    @NotNull(message = "startDate is mandatory.")
    LocalDate startDate,

    @NotNull(message = "endDate is mandatory.")
    LocalDate endDate,

    @Positive(message = "capacity must be a positive integer.")
    @NotNull(message = "capacity is mandatory.")
    Integer capacity
) {
    
}
