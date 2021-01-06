package com.beitech.store.delegate;


import com.beitech.store.constants.ErrorMessages;
import com.beitech.store.delegate.impl.OrderDelegate;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.dto.OrderDetailDto;
import com.beitech.store.dto.OrderDto;
import com.beitech.store.dto.ProductDto;
import com.beitech.store.exception.InvalidOrderException;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.ICustomerService;
import com.beitech.store.service.IOrderService;
import com.beitech.store.service.IProductService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderDelegateTest {

    @InjectMocks
    private OrderDelegate orderDelegate;

    @Mock
    private IOrderService orderService;

    @Mock
    private ICustomerService customerService;

    @Mock
    private IProductService productService;

    private OrderDto orderDto;
    private CustomerDto customerDto;
    private ProductDto productDto;

    @BeforeEach
    public void init(){
        customerDto = CustomerDto.builder()
                .id(1).name("Maria")
                .build();
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        orderDetails.add(OrderDetailDto.builder()
                .product(ProductDto.builder().id(1).name("product").price(100000d).build())
                .quantity(1).build()
        );
        orderDto = OrderDto.builder()
                .customer(customerDto).total(1000000d).orderDetails(orderDetails)
                .build();
        productDto = ProductDto.builder()
                .name("product1").price(100000d)
                .build();
    }

    @Test
    public void generateOrder_whenDataIsCorrect() {
        //Arrange
        when(customerService.getCustomerById(anyInt())).thenReturn(customerDto);
        when(orderService.saveOrUpdate(orderDto)).thenReturn(1);
        when(productService.getProductById(anyInt())).thenReturn(productDto);
        //Act
        ResponseEntity<ApiResponse<Integer>> response = orderDelegate.generateOrder(orderDto);
        //Assert
        Assert.assertNotNull(response.getBody().getData());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void generateOrder_whenNotProducts() {
        //Arrange
        List<OrderDetailDto> orderDetails = new ArrayList<>();
        orderDetails.add(OrderDetailDto.builder()
                .product(ProductDto.builder().id(1).name("product").price(100000d).build())
                .quantity(6).build()
        );
        orderDto.setOrderDetails(orderDetails);
        when(productService.getProductById(anyInt())).thenReturn(productDto);

        //Act and Assert
        InvalidOrderException thrown = assertThrows(
                InvalidOrderException.class,
                () ->orderDelegate.generateOrder(orderDto)
        );
        Assert.assertEquals(thrown.getCustomError(),"Error:" + ErrorMessages.NOT_ALLOWED_QUANTITY);
    }

}
