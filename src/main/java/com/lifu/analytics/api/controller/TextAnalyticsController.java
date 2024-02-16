package com.lifu.analytics.api.controller;

import com.lifu.analytics.api.dto.response.ApiResponse;
import com.lifu.analytics.api.service.TextAnalyticService;
import com.lifu.analytics.api.service.TextAnalyticServiceAsync;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpHeaders;

@RestController
@RequiredArgsConstructor
public class TextAnalyticsController {
    private final TextAnalyticService textAnalyticService;
    private final TextAnalyticServiceAsync textAnalyticServiceAsync;
    private static final String SENTIMENT_PATH = "/api/v1/sentiment";
    private static final String LANGUAGE_PATH = "/api/v1/language";
    private static final String KEYPHRASES_PATH = "/api/v1/keyphrases";
    private static final String NAMED_ENTITIES_PATH = "/api/v1/namedentities";
    private static final String NAMED_ENTITIES_ASYNC_PATH = NAMED_ENTITIES_PATH + "/async";

    @PostMapping(value = SENTIMENT_PATH)
    public ResponseEntity<?> extractSentiments(@RequestBody final String request) {
        return getResponse(textAnalyticService.extractSentiments(request));
    }

    @PostMapping(value = LANGUAGE_PATH)
    public ResponseEntity<?> languageDetection(@RequestBody final String request) {
        return getResponse(textAnalyticService.languageDetection(request));
    }

    @PostMapping(value = KEYPHRASES_PATH)
    public ResponseEntity<?> keyPhrases(@RequestBody final String request) {
        return getResponse(textAnalyticService.keyPhrasesDetection(request));
    }

    @PostMapping(value = NAMED_ENTITIES_PATH)
    public ResponseEntity<?> namedEntities(@RequestBody final String request) {
        return getResponse(textAnalyticService.namedEntities(request));
    }

    //async call on named entities
    @PostMapping(value = NAMED_ENTITIES_ASYNC_PATH)
    public ResponseEntity<?> namedEntitiesAsync(@RequestBody final String request) {
        textAnalyticServiceAsync.namedEntitiesAsync(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
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
