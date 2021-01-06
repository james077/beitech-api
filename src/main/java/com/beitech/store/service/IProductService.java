package com.beitech.store.service;

import com.beitech.store.dto.ProductDto;
import com.beitech.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IProductService {

    ProductDto getProductById(Integer id);

    ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCustomer(int customerId);

}
