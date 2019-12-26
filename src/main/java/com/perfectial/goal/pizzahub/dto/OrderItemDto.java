package com.perfectial.goal.pizzahub.dto;

import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemDto {
    @NotNull
    private BigDecimal price;
    @NotNull
    private ProductDto product;
    @NotNull
    private int quantity;
}
