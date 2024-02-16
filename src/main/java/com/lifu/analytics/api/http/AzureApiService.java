package com.lifu.analytics.api.http;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
@Slf4j
public class AzureApiService implements HttpService {
    @Value("${azure.api.baseurl}")
    private String baseUrl;
    @Value("${azure.api.key.name}")
    private String apiKeyName;
    @Value("${azure.api.key.value}")
    private String apiKeyValue;
    @Override
    public String postApiRequest(String jsonRequest, String endpoint) {
        String result = "";
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .header(apiKeyName, apiKeyValue)
                    .uri(URI.create(baseUrl+endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (Exception e) {
            var errorMsg = "failed http client request <=> "+ e.getMessage();
            log.error(errorMsg);
            result = getErrorMsg(errorMsg);
        }
        return result;
    }

    private String getErrorMsg(String errorMsg) {
        return String.format("{'status': %s}", errorMsg);
    }
}
