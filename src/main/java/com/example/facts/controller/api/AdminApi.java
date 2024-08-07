package com.example.facts.controller.api;

import com.example.facts.dto.StatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Tag(name = "Admin API", description = "API for managing admin operations")
public interface AdminApi {

    @Operation(summary = "Get access statistics for all shortened URLs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Statistics retrieved",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = StatisticsDto.class))})})
    ResponseEntity<List<StatisticsDto>> getStatistics();
}
