package com.perfectial.goal.pizzahub.integration.controller.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfectial.goal.pizzahub.integration.common.dto.ResponseDto;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class AbstractApiClient {

    @Value("${server.port}")
    private Integer serverPort;
    @Autowired
    private ObjectMapper objectMapper;
    private HttpClient httpClient = HttpClient.newHttpClient();


    @SneakyThrows
    protected <T> ResponseDto<T> doRequest(HttpRequest.Builder httpRequestBuilder, Class<T> type) {
        HttpResponse<byte[]> httpResponse = doRequest(httpRequestBuilder);
        return ResponseDto.wrapResponse(httpResponse, type);
    }

    @SneakyThrows
    protected <T> ResponseDto<T> doRequest(HttpRequest.Builder httpRequestBuilder, TypeReference<T> type) {
        HttpResponse<byte[]> httpResponse = doRequest(httpRequestBuilder);
        return ResponseDto.wrapResponse(httpResponse, type);
    }

    @SneakyThrows
    protected URI toAbsoluteUri(String relativeUrl) {
        return new URI(String.format("http://localhost:%s%s", serverPort, relativeUrl));
    }

    protected HttpRequest.BodyPublisher toBody(Object object) {
        return HttpRequest.BodyPublishers.ofByteArray(toJsonByteArray(object));
    }

    @SneakyThrows
    protected byte[] toJsonByteArray(Object code) {
        return objectMapper.writeValueAsBytes(code);
    }

    @SneakyThrows
    private HttpResponse<byte[]> doRequest(HttpRequest.Builder httpRequestBuilder) {
        httpRequestBuilder.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpRequestBuilder.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

        HttpRequest httpRequest = httpRequestBuilder.build();
        return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
    }
}
