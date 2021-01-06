package com.beitech.store.service;

import com.beitech.store.constants.ErrorMessages;
import com.beitech.store.domain.Product;
import com.beitech.store.dto.ProductDto;
import com.beitech.store.exception.InvalidOrderException;
import com.beitech.store.exception.NoContentException;
import com.beitech.store.mapper.IProductMapper;
import com.beitech.store.repository.ICustomerProductRepository;
import com.beitech.store.repository.IProductRepository;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.impl.ProductService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

	@InjectMocks
	private ProductService productService;

	@Mock
	private IProductRepository productRepository;

	@Mock
	private ICustomerProductRepository customerProductRepository;

	@Mock
	private IProductMapper productMapper;


	@Test
	public void getProductById_whenExistProduct() {
		//Arrange
		int id = 1;
		when(productRepository.findById(anyInt())).thenReturn(Optional.of(new Product()));
		when(productMapper.entityToDto(any(Product.class))).thenReturn(
				ProductDto.builder().id(id).build()
		);
		//Act
		ProductDto response = productService.getProductById(id);
		//Assert
		Assert.assertEquals(id, response.getId().intValue());
	}

	@Test
	public void getProduct_whenNotExistProduct() {
		//Arrange
		int id = 1;
		when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
		//Act and Assert
		NoContentException thrown = assertThrows(
				NoContentException.class,
				() ->productService.getProductById(id)
		);
		Assert.assertEquals(thrown.getId(),id);

	}

	@Test
	public void getProductsByCustomer_whenExistProducts() {
		//Arrange
		int customerId =1;
		List<Product> products  = new ArrayList<>();
		products.add(new Product());
		when(customerProductRepository.findProductsByCustomer(customerId)).thenReturn(products);
		when(productMapper.entityToDto(any(Product.class))).thenReturn(new ProductDto());
		//Act
		ResponseEntity<ApiResponse<List<ProductDto>>> response = productService.getProductsByCustomer(customerId);
		//Assert
		Assert.assertNotNull(response.getBody().getData());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getProductsByCustomer_whenNotExistProducts() {
		//Arrange
		int customerId =1;
		when(customerProductRepository.findProductsByCustomer(customerId)).thenReturn(null);
		//Act
		ResponseEntity<ApiResponse<List<ProductDto>>> response = productService.getProductsByCustomer(customerId);
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
