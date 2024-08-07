package com.example.facts.controller.api;

import com.example.facts.dto.FactDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Fact API", description = "API for getting random facts")
public interface FactApi {

  @Operation(summary = "Fetch a random fact and shorten URL")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Fact fetched and shortened URL created",
          content = {@Content(mediaType = "application/json",
              schema = @Schema(implementation = FactDto.class))}),
      @ApiResponse(responseCode = "500", description = "Internal server error",
          content = @Content)})
  ResponseEntity<FactDto> createFact();

  @Operation(summary = "Redirect to the original fact")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "302", description = "Redirected to the original fact"),
      @ApiResponse(responseCode = "404", description = "Fact not found",
          content = @Content)})
  ResponseEntity<Void> redirect(@PathVariable @NotBlank(message = "Shortened URL cannot be blank") String shortenedUrl);
}
