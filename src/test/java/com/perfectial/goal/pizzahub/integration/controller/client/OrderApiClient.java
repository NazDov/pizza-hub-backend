package com.perfectial.goal.pizzahub.integration.controller.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.perfectial.goal.pizzahub.dto.OrderCreateResponseDto;
import com.perfectial.goal.pizzahub.dto.OrderDto;
import com.perfectial.goal.pizzahub.integration.common.dto.ResponseDto;
import java.net.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

@Component
public class OrderApiClient extends AbstractApiClient {

    public ResponseDto<OrderCreateResponseDto> submitOrder(OrderDto orderDto) {
        HttpRequest.Builder httpRequest = HttpRequest.newBuilder(
                toAbsoluteUri("/api/pizza-hub-test/orders/submit")
        ).header("Content-Type", MediaType.APPLICATION_JSON.toString()).POST(toBody(orderDto));
        return doRequest(httpRequest, new TypeReference<>() {
        });
    }
}
