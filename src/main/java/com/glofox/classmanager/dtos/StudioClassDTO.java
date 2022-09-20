package com.glofox.classmanager.dtos;

import java.time.LocalDate;

public record StudioClassDTO(
    Long id,
    String name,
    LocalDate date,
    Integer capacity
) {
    
}
