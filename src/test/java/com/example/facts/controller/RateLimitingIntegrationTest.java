package com.example.facts.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 1080)
@ActiveProfiles("test")
public class RateLimitingIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        WireMock.reset();
    }


    @Test
    public void testRateLimiting() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/random-fact"))
            .willReturn(aResponse()
                .withStatus(200)
                .withHeader("Content-Type", "application/json")
                .withBody(
                    "{\"id\": \"123\", \"text\": \"A random fact\", \"source\": \"source\", \"sourceUrl\": \"http://source.com\", \"language\": \"en\", \"permalink\": \"http://fact.com/123\"}")));

        // Send 10 allowed requests
        for (int i = 0; i < 5; i++) {
            mockMvc.perform(post("/facts")
                    .contentType("application/json"))
                .andExpect(status().isOk());
        }

        // Send 11th request which should be rate limited
        mockMvc.perform(post("/facts")
                .contentType("application/json"))
            .andExpect(status().isTooManyRequests());
    }
}
