package com.example.facts.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for shortened URL response.
 */
@Schema(description = "DTO for shortened URL response")
public record FactDto(

    @NotBlank(message = "Original fact cannot be blank")
    @Schema(description = "Original URL", example = "https://example.com")
    String originalFact,

    @NotBlank(message = "Shortened URL cannot be blank")
    @Schema(description = "Shortened URL", example = "abc1234")
    String shortenedUrl
) {

}
