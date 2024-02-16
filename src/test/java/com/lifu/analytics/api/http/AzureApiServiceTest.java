package com.lifu.analytics.api.http;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.http.HttpClient;

@SpringBootTest
class AzureApiServiceTest {

    @Autowired
    private HttpService httpService;

    @Mock
    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void postApiRequest_Success() throws Exception {
        // Mocking response
        String jsonResponse = "{\"status\": \"success\"}";
        //HttpResponse<String> response = HttpResponse.BodyHandlers.ofString().apply(HttpResponse.BodySubscribers.ofString(jsonResponse));

        // Mocking client.send() method
        //when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(response);

        // Instantiate the class under test
        //AzureApiService yourClass = new AzureApiService(httpClient); // Assuming HttpClient is injected in the constructor

        // Test input data
        String jsonRequest = "{\n" +
                "    \"documents\": [\n" +
                "        {\n" +
                "            \"countryHint\": \"US\",\n" +
                "            \"id\": \"1\",\n" +
                "            \"text\": \"Hello world\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"2\",\n" +
                "            \"text\": \"Bonjour tout le monde\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": \"3\",\n" +
                "            \"text\": \"La carretera estaba atascada. Había mucho tráfico el día de ayer.\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"id\": 4,\n" +
                "            \"text\": \"I love Jesus\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";
        String endpoint = "/text/analytics/v3.0/sentiment";

        // Call the method
        var result = httpService.postApiRequest(jsonRequest, endpoint);
        System.out.println(result);

        // Verify behavior
        //verify(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        //assertEquals(jsonResponse, result);
    }

    @Test
    void postApiRequest_Exception() throws Exception {
        // Mocking exception
        String errorMsg = "Connection refused";
        //when(httpClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(new RuntimeException(errorMsg));

        // Instantiate the class under test
        //AzureApiService yourClass = new AzureApiService(httpClient); // Assuming HttpClient is injected in the constructor

        // Test input data
        String jsonRequest = "{\"data\": \"test\"}";
        String endpoint = "/your-endpoint";

        // Call the method
        var result = httpService.postApiRequest(jsonRequest, endpoint);

        // Verify behavior
        //verify(httpClient).send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class));
        // Verify log.error is called with proper error message
        // Verify getErrorMsg is called properly
        // Add additional assertions based on your implementation
    }
}