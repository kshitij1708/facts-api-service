package com.example.facts.controller;

import com.example.facts.controller.api.AdminApi;
import com.example.facts.dto.StatisticsDto;
import com.example.facts.service.FactStatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController implements AdminApi {
    private final FactStatisticsService factStatisticsService;

    @Override
    @GetMapping("/statistics")
    public ResponseEntity<List<StatisticsDto>> getStatistics() {
        List<StatisticsDto> stats = factStatisticsService.getStatistics();
        return ResponseEntity.ok(stats);
    }
}
