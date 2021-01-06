package com.beitech.store.delegate.impl;

import com.beitech.store.constants.ErrorMessages;
import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.delegate.IOrderDelegate;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.dto.OrderDetailDto;
import com.beitech.store.dto.OrderDto;
import com.beitech.store.dto.ProductDto;
import com.beitech.store.exception.InvalidOrderException;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.ICustomerService;
import com.beitech.store.service.IOrderService;
import com.beitech.store.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class OrderDelegate implements IOrderDelegate {

    private final IOrderService orderService;
    private final ICustomerService customerService;
    private final IProductService productService;

    @Override
    public ResponseEntity<ApiResponse<Integer>> generateOrder(OrderDto orderDto) {
        AtomicInteger quantity = new AtomicInteger();
        CustomerDto customerDto = customerService.getCustomerById(orderDto.getCustomer().getId());
        orderDto.setCustomer(customerDto);
        List<OrderDetailDto> orderDetailsDto = new ArrayList();
        orderDto.getOrderDetails().stream().forEach(orderDetail -> {
            if(GeneralConstants.MINIMUM_QUANTITY > orderDetail.getQuantity()){
                throw InvalidOrderException.builder()
                        .items(quantity.get())
                        .customError("Error:" + ErrorMessages.INSUFFICIENT_PRODUCTS).build();
            }
            ProductDto productDto = productService.getProductById(orderDetail.getProduct().getId());
            orderDetail.setProduct(productDto);
            orderDetail.setPrice(productDto.getPrice());
            orderDetail.setProductDescription(productDto.getProductDescription());
            orderDetailsDto.add(orderDetail);
            quantity.addAndGet(orderDetail.getQuantity());
        });

        if(quantity.get() > GeneralConstants.MAXIMUM_QUANTITY)
            throw InvalidOrderException.builder()
                    .items(quantity.get())
                    .customError("Error:" + ErrorMessages.NOT_ALLOWED_QUANTITY).build();

        orderDto.setTotal(calculateTotal(orderDetailsDto));
        orderDto.setOrderDetails(orderDetailsDto);

        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(
                new ApiResponse<>(orderService.saveOrUpdate(orderDto),notification)
        );
    }

    private double calculateTotal(List<OrderDetailDto> orderDetailsDto){
        return orderDetailsDto.stream()
                .map(item ->  item.getPrice() * item.getQuantity())
                .collect(Collectors.toList())
                .stream().reduce((double) 0, (a, b) -> a + b);
    }
}
