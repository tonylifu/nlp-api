package com.lifu.analytics.api.dto.response;

import lombok.Builder;

import java.net.http.HttpHeaders;

@Builder
public record ApiResponse(String responseBody, int statusCode, HttpHeaders headers) { }
