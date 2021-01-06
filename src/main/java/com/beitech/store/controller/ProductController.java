package com.beitech.store.controller;

import com.beitech.store.constants.ResourceMapping;
import com.beitech.store.dto.ProductDto;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ResourceMapping.PRODUCT)
public class ProductController {

    private final IProductService productService;

    @GetMapping(value = ResourceMapping.AVAILABLE + "/{customerId}")
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCustomer(@PathVariable int customerId) {
        return productService.getProductsByCustomer(customerId);
    }

}
