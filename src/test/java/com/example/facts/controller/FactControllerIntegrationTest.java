package com.example.facts.controller;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.facts.dto.FactDto;
import com.example.facts.service.FactService;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@WireMockTest(httpPort = 1080)
public class FactControllerIntegrationTest {

  @Autowired
  FactService factService;
  @Autowired
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    WireMock.reset();
  }

  @Test
  public void testCreateFact() throws Exception {
    stubFor(WireMock.get(urlEqualTo("/random-fact"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"id\": \"123\", \"text\": \"A random fact\", \"source\": \"source\", \"sourceUrl\": \"http://source.com\", \"language\": \"en\", \"permalink\": \"http://fact.com/123\"}")));

    mockMvc.perform(post("/facts")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.originalFact").value("A random fact"))
        .andExpect(jsonPath("$.shortenedUrl").exists());
  }

  @Test
  public void testCreateFact_RateLimitExceeded() throws Exception {
    // Set up WireMock to return a rate limit exceeded response
    stubFor(WireMock.get(urlEqualTo("/random-fact"))
        .willReturn(aResponse()
            .withStatus(429)
            .withHeader("Content-Type", "text/plain")
            .withBody("Rate limit exceeded")));

    mockMvc.perform(post("/facts")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isTooManyRequests())
        .andExpect(jsonPath("$.message").value("Rate limit exceeded. Please try again later."));
  }

  @Test
  public void testCreateFact_ApiError() throws Exception {
    stubFor(WireMock.get(urlEqualTo("/random-fact"))
        .willReturn(aResponse()
            .withStatus(500)
            .withHeader("Content-Type", "text/plain")
            .withBody("Internal Server Error")));

    mockMvc.perform(post("/facts")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.message").value("500 Server Error: \"Internal Server Error\""));
  }

  @Test
  public void testGetFact() throws Exception {
    stubFor(WireMock.get(urlEqualTo("/random-fact"))
        .willReturn(aResponse()
            .withStatus(200)
            .withHeader("Content-Type", "application/json")
            .withBody(
                "{\"id\": \"123\", \"text\": \"A random fact\", \"source\": \"source\", \"sourceUrl\": \"http://source.com\", \"language\": \"en\", \"permalink\": \"http://fact.com/123\"}")));

    FactDto fact = factService.createFact();
    mockMvc.perform(get("/facts/" + fact.shortenedUrl().split("/")[4])
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is3xxRedirection())
        .andExpect(header().string("Location", "http://fact.com/123"));
  }
}
