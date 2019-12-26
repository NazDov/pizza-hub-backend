package com.perfectial.goal.pizzahub.dto;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductDto {
    private int id;
    private String name;
    private String description;
    private BigDecimal price;
}
