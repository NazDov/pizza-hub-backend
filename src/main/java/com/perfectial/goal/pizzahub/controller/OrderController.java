package com.perfectial.goal.pizzahub.controller;

import com.perfectial.goal.pizzahub.dto.OrderCreateResponseDto;
import com.perfectial.goal.pizzahub.dto.OrderDto;
import com.perfectial.goal.pizzahub.service.OrderService;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/submit")
    public OrderCreateResponseDto submitOrder(@Valid @RequestBody OrderDto orderDto) {
        return this.orderService.submitOrder(orderDto);
    }
}
