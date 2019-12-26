package com.perfectial.goal.pizzahub.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.perfectial.goal.pizzahub.dto.PageData;
import com.perfectial.goal.pizzahub.dto.ProductDto;
import com.perfectial.goal.pizzahub.integration.BaseIntegrationTest;
import com.perfectial.goal.pizzahub.integration.common.dto.ErrorDto;
import com.perfectial.goal.pizzahub.integration.common.dto.ResponseDto;
import com.perfectial.goal.pizzahub.integration.controller.client.ProductApiClient;
import com.perfectial.goal.pizzahub.model.Product;
import com.perfectial.goal.pizzahub.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;

public class ProductControllerTest extends BaseIntegrationTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductApiClient productApiClient;
    @Value("${product.default-page-size}")
    private Integer defaultPageSize;

    @Test
    public void testGetAllProductsByPage_shouldReturnActualPageData() {
        verifyGetAllProductsByPage(1);
        verifyGetAllProductsByPage(2);
        verifyGetAllProductsByPage(3);
        verifyGetAllProductsByPage(100500);
    }

    @Test
    public void testGetAllProductsByPageWhenPageNumberIsInvalid_shouldThrow400() {
        int invalidRequestPage = -1;
        ResponseDto<PageData<ProductDto>> responseDto = productApiClient.getAllProducts(invalidRequestPage);

        ErrorDto errorDto = responseDto.getError();
        assertThat(errorDto).isNotNull();
        assertThat(errorDto.getStatus()).isSameAs(HttpStatus.BAD_REQUEST);
    }

    private void verifyGetAllProductsByPage(int requestPage) {
        ResponseDto<PageData<ProductDto>> responseDto = productApiClient.getAllProducts(requestPage);

        List<Product> products = productRepository.findAll();
        int productsSize = products.size();
        PageData<ProductDto> pageData = responseDto.getData();

        int expectedNumOfPages = (int) Math.ceil((double) (productsSize / defaultPageSize));

        int maxTotalPerPage = requestPage * defaultPageSize;
        int expectedPageSize = maxTotalPerPage <= productsSize ?
                defaultPageSize : productsSize - (maxTotalPerPage - productsSize);

        assertThat(pageData).isNotNull();
        assertThat(pageData.getCurrentPage()).isEqualTo(requestPage);
        assertThat(pageData.getTotalNumberOfPages()).isEqualTo(expectedNumOfPages);

        if (pageData.getCurrentPage() > pageData.getTotalNumberOfPages()) {
            assertThat(pageData.getData()).isEmpty();
        } else {
            assertThat(pageData.getData().size()).isEqualTo(expectedPageSize);
        }

        assertThat(pageData.getData()).isEqualTo(expectedPageData(requestPage));
    }

    private List<ProductDto> expectedPageData(int requestPage) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(requestPage - 1, defaultPageSize));
        return productPage.get().map(this::toProductDto).collect(Collectors.toList());
    }

    private ProductDto toProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        productDto.setDescription(product.getDescription());
        productDto.setPrice(product.getPrice());
        return productDto;
    }

}
