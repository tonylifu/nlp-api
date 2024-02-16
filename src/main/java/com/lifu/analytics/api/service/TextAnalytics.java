package com.lifu.analytics.api.service;

import com.lifu.analytics.api.dto.response.ApiResponse;
import com.lifu.analytics.api.http.HttpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TextAnalytics implements TextAnalyticService {
    private final HttpService httpService;
    @Value("${azure.api.endpoint.sentiment}")
    private String sentimentEndPoint;
    @Value("${azure.api.endpoint.language}")
    private String languageEndPoint;
    @Value("${azure.api.endpoint.keyphrases}")
    private String keyPhrasesEndPoint;
    @Override
    public ApiResponse extractSentiments(String request) {
        return httpService.postApiRequest(request, sentimentEndPoint);
    }

    @Override
    public ApiResponse languageDetection(String request) {
        return httpService.postApiRequest(request, languageEndPoint);
    }

    @Override
    public ApiResponse keyPhrasesDetection(String request) {
        return httpService.postApiRequest(request, keyPhrasesEndPoint);
    }
}
