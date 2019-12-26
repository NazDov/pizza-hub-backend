package com.perfectial.goal.pizzahub.service;

import com.perfectial.goal.pizzahub.dto.OrderCreateResponseDto;
import com.perfectial.goal.pizzahub.dto.OrderDto;

public interface OrderService {
    OrderCreateResponseDto submitOrder(OrderDto order);
}
