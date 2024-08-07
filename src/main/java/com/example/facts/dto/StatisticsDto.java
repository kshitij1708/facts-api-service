package com.example.facts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object for statistics response.
 */
@Schema(description = "DTO for statistics response")
public record StatisticsDto(
    @NotBlank(message = "Shortened URL cannot be blank")
    @Schema(description = "Shortened URL", example = "abc1234")
    String shortenedUrl,

    @NotNull(message = "Access count cannot be null")
    @Schema(description = "Access count", example = "42")
    Long accessCount
) {}
