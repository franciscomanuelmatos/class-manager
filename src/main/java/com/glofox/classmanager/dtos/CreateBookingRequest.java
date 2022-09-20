package com.glofox.classmanager.dtos;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateBookingRequest(
    @NotBlank(message = "name is mandatory.") String name,
    @NotNull(message = "date is mandatory.") LocalDate date
) {
    
}
