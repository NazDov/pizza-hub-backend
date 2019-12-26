package com.perfectial.goal.pizzahub.integration.controller.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.perfectial.goal.pizzahub.dto.PageData;
import com.perfectial.goal.pizzahub.dto.ProductDto;
import com.perfectial.goal.pizzahub.integration.common.dto.ResponseDto;
import java.net.http.HttpRequest;
import org.springframework.stereotype.Component;

@Component
public class ProductApiClient extends AbstractApiClient {

    public ResponseDto<PageData<ProductDto>> getAllProducts(int page) {
        HttpRequest.Builder httpRequestBuilder =
                HttpRequest.newBuilder(toAbsoluteUri(String.format("/api/pizza-hub-test/products?page=%s", page))).GET();
        return doRequest(httpRequestBuilder, new TypeReference<>() {});
    }
}
