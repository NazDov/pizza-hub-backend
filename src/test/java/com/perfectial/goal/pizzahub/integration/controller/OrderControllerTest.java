package com.perfectial.goal.pizzahub.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.perfectial.goal.pizzahub.dto.OrderCreateResponseDto;
import com.perfectial.goal.pizzahub.dto.OrderDto;
import com.perfectial.goal.pizzahub.dto.OrderItemDto;
import com.perfectial.goal.pizzahub.dto.ProductDto;
import com.perfectial.goal.pizzahub.integration.BaseIntegrationTest;
import com.perfectial.goal.pizzahub.integration.common.dto.ErrorDto;
import com.perfectial.goal.pizzahub.integration.common.dto.ResponseDto;
import com.perfectial.goal.pizzahub.integration.controller.client.OrderApiClient;
import com.perfectial.goal.pizzahub.model.Product;
import com.perfectial.goal.pizzahub.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

public class OrderControllerTest extends BaseIntegrationTest {
    @Autowired
    private OrderApiClient orderApiClient;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    private ProductDto testProduct;

    @Before
    public void before() {
        Product product = new Product();
        product.setId(-1);
        product.setPrice(BigDecimal.valueOf(9.99));
        product.setDescription("Test");
        product.setName("Test");

        product = productRepository.save(product);
        testProduct = modelMapper.map(product, ProductDto.class);
    }


    @Test
    public void testSubmitOrder_shouldReturnOrderCreateResponse() {
        OrderDto orderDto = testValidOrder();

        ResponseDto<OrderCreateResponseDto> responseDto = orderApiClient.submitOrder(orderDto);

        OrderCreateResponseDto orderCreateResponse = responseDto.getData();

        assertThat(orderCreateResponse).isNotNull();
        assertThat(orderCreateResponse.getOrderId()).isNotZero();
        assertThat(orderCreateResponse.getDate()).isNotNull();
    }

    @Test
    public void testSubmitOrderWhenRequiredFieldIsNullOrEmpty_shouldThrow400() {
        OrderDto noNameOrder = OrderDto.builder().firstName(null).lastName(null).build();
        assertOrderSubmitThrowsException(noNameOrder);

        OrderDto noPhoneOrder = OrderDto.builder().phone(null).build();
        assertOrderSubmitThrowsException(noPhoneOrder);

        noPhoneOrder = OrderDto.builder().phone("").build();
        assertOrderSubmitThrowsException(noPhoneOrder);

        OrderDto noAddressOrder = OrderDto.builder().address(null).build();
        assertOrderSubmitThrowsException(noAddressOrder);

        noAddressOrder = OrderDto.builder().address("").build();
        assertOrderSubmitThrowsException(noAddressOrder);

        OrderDto noOrderItems = OrderDto.builder().orderItems(null).build();
        assertOrderSubmitThrowsException(noOrderItems);

        noOrderItems = OrderDto.builder().orderItems(Collections.emptyList()).build();
        assertOrderSubmitThrowsException(noOrderItems);
    }

    private void assertOrderSubmitThrowsException(OrderDto orderDto) {
        ResponseDto<OrderCreateResponseDto> responseDto = orderApiClient.submitOrder(orderDto);

        ErrorDto errorDto = responseDto.getError();

        assertThat(errorDto).isNotNull();
        assertThat(errorDto.getStatus()).isSameAs(HttpStatus.BAD_REQUEST);
    }

    private OrderDto testValidOrder() {
        return OrderDto.builder().firstName("Test").lastName("Test").address("Test").city("Test").email("Test")
                .phone("Test").zip("Test").orderItems(testOrderItems()).build();
    }

    private List<OrderItemDto> testOrderItems() {
        List<OrderItemDto> orderItems = new ArrayList<>();
        orderItems.add(testOrderItem());
        return orderItems;
    }

    private OrderItemDto testOrderItem() {
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setPrice(BigDecimal.valueOf(9.99));
        orderItemDto.setQuantity(1);
        orderItemDto.setProduct(testProduct);
        return orderItemDto;
    }

    @After
    public void after() {
        productRepository.deleteById(testProduct.getId());
    }
}
