package com.perfectial.goal.pizzahub.service.impl;

import com.perfectial.goal.pizzahub.dto.PageData;
import com.perfectial.goal.pizzahub.dto.ProductDto;
import com.perfectial.goal.pizzahub.exception.BadRequestException;
import com.perfectial.goal.pizzahub.model.Product;
import com.perfectial.goal.pizzahub.repository.ProductRepository;
import com.perfectial.goal.pizzahub.service.ProductService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ModelMapper mapper;
    private final int pageSize;

    public ProductServiceImpl(ProductRepository repository,
                              ModelMapper mapper,
                              @Value("${product.default-page-size}") int pageSize) {
        this.repository = repository;
        this.mapper = mapper;
        this.pageSize = pageSize;
    }

    @Override
    public PageData<ProductDto> getAllProducts(int page) {
        int currentPage = page - 1;

        if (currentPage < 0) {
            throw new BadRequestException(String.format("Page number should be positive: %s", page));
        }

        PageRequest pageRequest = PageRequest.of(currentPage, pageSize);
        Page<Product> productPage = repository.findAll(pageRequest);

        List<ProductDto> productList = productPage
                .get()
                .map(this::toProductDto)
                .collect(Collectors.toList());

        PageData<ProductDto> pageData = new PageData<>();
        pageData.setCurrentPage(page);
        pageData.setData(productList);
        pageData.setTotalNumberOfPages(productPage.getTotalPages());
        return pageData;
    }

    private ProductDto toProductDto(Product product) {
        return mapper.map(product, ProductDto.class);
    }
}
