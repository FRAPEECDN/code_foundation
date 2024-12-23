package com.frapee.basic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Simple Dto data containing only id and name fields
 */
public record SimpleDto(
    
    int id, 
    
    @NotBlank(message = "Name cannot be null or blank")
    @Size(min = 3, max = 50, message = "Name can only be between 3 and 50 characters long")
    String name

) {}
