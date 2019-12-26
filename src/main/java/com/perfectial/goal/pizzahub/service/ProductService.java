package com.perfectial.goal.pizzahub.service;

import com.perfectial.goal.pizzahub.dto.PageData;
import com.perfectial.goal.pizzahub.dto.ProductDto;

public interface ProductService {
    PageData<ProductDto> getAllProducts(int page);
}
