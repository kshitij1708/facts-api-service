package com.example.facts.controller;

import com.example.facts.controller.api.FactApi;
import com.example.facts.dto.FactDto;
import com.example.facts.service.FactService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/facts")
@Validated
public class FactController implements FactApi {

  private final FactService factService;

  @Override
  @PostMapping
  public ResponseEntity<FactDto> createFact() {
    FactDto factDTO = factService.createFact();
    return ResponseEntity.ok(factDTO);
  }

  @Override
  @GetMapping("/{shortenedUrl}")
  public ResponseEntity<Void> redirect(@PathVariable @NotBlank(message = "Shortened URL cannot be blank") String shortenedUrl) {
    String originalFact = factService.resolveOriginalUrl(shortenedUrl);
    HttpHeaders headers = new HttpHeaders();
    headers.add("Location", originalFact);
    return new ResponseEntity<>(headers, HttpStatus.FOUND);
  }

}
