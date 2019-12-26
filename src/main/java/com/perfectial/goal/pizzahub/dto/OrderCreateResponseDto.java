package com.perfectial.goal.pizzahub.dto;

import java.time.Instant;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Data
@ResponseStatus(HttpStatus.CREATED)
public class OrderCreateResponseDto {
    int orderId;
    Instant date;
}
