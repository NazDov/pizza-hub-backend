package com.perfectial.goal.pizzahub.service.impl;

import com.perfectial.goal.pizzahub.dto.OrderCreateResponseDto;
import com.perfectial.goal.pizzahub.dto.OrderDto;
import com.perfectial.goal.pizzahub.dto.OrderItemDto;
import com.perfectial.goal.pizzahub.model.Order;
import com.perfectial.goal.pizzahub.model.OrderItem;
import com.perfectial.goal.pizzahub.model.Product;
import com.perfectial.goal.pizzahub.model.Shipping;
import com.perfectial.goal.pizzahub.repository.OrderRepository;
import com.perfectial.goal.pizzahub.service.OrderService;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public OrderCreateResponseDto submitOrder(OrderDto orderDto) {
        final Order order = new Order();
        order.setDate(Instant.now());
        List<OrderItem> orderItems = toOrderItems(orderDto.getOrderItems());
        orderItems.forEach(orderItem -> orderItem.setOrder(order));
        order.getOrderItems().addAll(orderItems);
        Shipping shipping = toShipping(orderDto);
        order.setShipping(shipping);

        Order submittedOrder = orderRepository.save(order);
        return toOrderCreateResponse(submittedOrder);
    }

    private OrderCreateResponseDto toOrderCreateResponse(Order order) {
        OrderCreateResponseDto orderCreateResponse = new OrderCreateResponseDto();
        orderCreateResponse.setOrderId(order.getId());
        orderCreateResponse.setDate(order.getDate());
        return orderCreateResponse;
    }

    private List<OrderItem> toOrderItems(List<OrderItemDto> orderItemDtoList) {
        return orderItemDtoList.stream().map(this::toOrderItem).collect(Collectors.toList());
    }

    private OrderItem toOrderItem(OrderItemDto dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(dto.getQuantity());
        orderItem.setProduct(new Product());
        orderItem.getProduct().setId(dto.getProduct().getId());
        return orderItem;
    }

    private Shipping toShipping(OrderDto orderDto) {
        return modelMapper.map(orderDto, Shipping.class);
    }
}
