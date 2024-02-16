package com.lifu.analytics.api.http;

import com.lifu.analytics.api.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

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
    public ApiResponse postApiRequest(String jsonRequest, String endpoint) {
        log.info("\n:::Request:::\n{}\n", jsonRequest);
        ApiResponse apiResponse;
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .header(apiKeyName, apiKeyValue)
                    .uri(URI.create(baseUrl+endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            apiResponse = ApiResponse.builder()
                    .responseBody(response.body())
                    .statusCode(response.statusCode())
                    .headers(response.headers())
                    .build();
        } catch (Exception e) {
            var errorMsg = "failed http client request <=> "+ e.getMessage();
            log.error(errorMsg);
            Map<String, List<String>> headersMap = Map.of(
                    "status", List.of("400"),
                    "Error-Message", List.of("Failed Request")
            );
            HttpHeaders headers = HttpHeaders.of(headersMap, (name, value) -> true);
            apiResponse = ApiResponse.builder()
                    .responseBody(getErrorMsg(errorMsg))
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .headers(headers)
                    .build();
        }
        log.info("\n:::Response:::\nStatus Code => {}\n Headers Info => {}\n Response => {}\n",
                apiResponse.statusCode(), apiResponse.headers(), apiResponse.responseBody());
        return apiResponse;
    }

    @Override
    public void postApiRequestAsync(String jsonRequest, String endpoint) {
        log.info("\n:::Request:::\n{}\n", jsonRequest);
        try(HttpClient client = HttpClient.newHttpClient()) {
            HttpRequest request = HttpRequest.newBuilder()
                    .header(apiKeyName, apiKeyValue)
                    .uri(URI.create(baseUrl+endpoint))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
                    .build();
            client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(result -> ApiResponse.builder()
                            .responseBody(result.body())
                            .statusCode(result.statusCode())
                            .headers(result.headers())
                            .build())
                    .thenAccept(apiResult -> {
                        System.out.println("***********************************************");
                        log.info("\n::::::::::::::::API-Response::::::::::::::::::::\nStatus Code => {}\n Headers Info => {}\n Response => {}\n",
                                apiResult.statusCode(), apiResult.headers(), apiResult.responseBody());
                        System.out.println("***********************************************");
                    });


        } catch (Exception e) {
            var errorMsg = "failed http client request <=> "+ e.getMessage();
            log.error(errorMsg);
        }
    }

    private String getErrorMsg(String errorMsg) {
        return String.format("{'status': %s}", errorMsg);
    }
}
