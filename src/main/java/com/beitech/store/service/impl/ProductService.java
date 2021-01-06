package com.beitech.store.service.impl;

import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.domain.Product;
import com.beitech.store.dto.ProductDto;
import com.beitech.store.exception.NoContentException;
import com.beitech.store.repository.ICustomerProductRepository;
import com.beitech.store.repository.IProductRepository;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.IProductService;
import com.beitech.store.mapper.IProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {

    private final IProductRepository productRepository;
    private final ICustomerProductRepository customerProductRepository;
    private final IProductMapper productMapper;


    @Override
    public ProductDto getProductById(Integer id) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent())
            return productMapper.entityToDto(product.get());

        throw NoContentException.builder().id(id).build();
    }

    @Override
    public ResponseEntity<ApiResponse<List<ProductDto>>> getProductsByCustomer(int customerId) {
        List<Product> products = customerProductRepository.findProductsByCustomer(customerId);

        if(products == null)
            return ResponseEntity.noContent().build();

        List<ProductDto> productsDto = products.stream()
                .map(product-> productMapper.entityToDto(product))
                .collect(Collectors.toList());
        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(new ApiResponse<>(productsDto,notification));
    }

}
