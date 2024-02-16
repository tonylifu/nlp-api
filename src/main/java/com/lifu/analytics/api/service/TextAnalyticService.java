package com.lifu.analytics.api.service;

import com.lifu.analytics.api.dto.response.ApiResponse;

public interface TextAnalyticService {
    ApiResponse extractSentiments(String request);
    ApiResponse languageDetection(String request);
    ApiResponse keyPhrasesDetection(String request);
    ApiResponse namedEntities(String request);
}

