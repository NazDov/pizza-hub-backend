package com.perfectial.goal.pizzahub.controller;

import com.perfectial.goal.pizzahub.dto.PageData;
import com.perfectial.goal.pizzahub.dto.ProductDto;
import com.perfectial.goal.pizzahub.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public PageData<ProductDto> getAllProductsPage(@RequestParam("page") int page) {
        return productService.getAllProducts(page);
    }
}
