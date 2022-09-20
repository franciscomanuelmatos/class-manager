package com.glofox.classmanager.dtos;

import java.time.LocalDate;

public record BookingDTO(
    Long id,
    String name,
    LocalDate date
) {
    
}
