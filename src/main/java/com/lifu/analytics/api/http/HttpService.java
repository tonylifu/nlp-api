package com.lifu.analytics.api.http;

import com.lifu.analytics.api.dto.response.ApiResponse;

public interface HttpService {
    ApiResponse postApiRequest(String jsonRequest, String endpoint);
    void postApiRequestAsync(String jsonRequest, String endpoint);
}
