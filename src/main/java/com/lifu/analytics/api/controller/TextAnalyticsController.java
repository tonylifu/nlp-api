package com.lifu.analytics.api.controller;

import com.lifu.analytics.api.dto.response.ApiResponse;
import com.lifu.analytics.api.service.TextAnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;

@RestController
@RequiredArgsConstructor
public class TextAnalyticsController {
    private final TextAnalyticService textAnalyticService;
    private static final String SENTIMENT_PATH = "/api/v1/sentiment";
    private static final String LANGUAGE_PATH = "/api/v1/language";
    private static final String KEYPHRASES_PATH = "/api/v1/keyphrases";
    private static final String NAMED_ENTITIES_PATH = "/api/v1/namedentities";

    @PostMapping(value = SENTIMENT_PATH)
    public ResponseEntity<?> extractSentiments(@RequestBody final String request) {
        var response = textAnalyticService.extractSentiments(request);
        return getResponse(response);
    }

    @PostMapping(value = LANGUAGE_PATH)
    public ResponseEntity<?> languageDetection(@RequestBody final String request) {
        var response = textAnalyticService.languageDetection(request);
        return getResponse(response);
    }

    @PostMapping(value = KEYPHRASES_PATH)
    public ResponseEntity<?> keyPhrases(@RequestBody final String request) {
        var response = textAnalyticService.keyPhrasesDetection(request);
        return getResponse(response);
    }

    @PostMapping(value = NAMED_ENTITIES_PATH)
    public ResponseEntity<?> namedEntities(@RequestBody final String request) {
        var response = textAnalyticService.namedEntities(request);
        return getResponse(response);
    }

    private ResponseEntity<?> getResponse(ApiResponse response) {
        return ResponseEntity
                .status(response.statusCode())
                .header(getHeaders(response.headers()))
                .body(response.responseBody());
    }

    private String getHeaders(HttpHeaders headers) {
        var map = headers.map();
        String headerKey = map.keySet().stream().findFirst().orElse("header");
        StringBuilder headerBuilder = new StringBuilder(headerKey);
        map.values().forEach(headerBuilder::append);
        return headerBuilder.toString();
    }
}
