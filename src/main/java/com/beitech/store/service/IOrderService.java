package com.beitech.store.service;

import com.beitech.store.dto.OrderDto;
import com.beitech.store.dto.RequestOrdersDto;
import com.beitech.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface IOrderService {

    int saveOrUpdate(OrderDto orderDto);

    ResponseEntity<ApiResponse<OrderDto>>getOrderById(Integer id);

    ResponseEntity<ApiResponse<List<OrderDto>>> getOrdersPerCustomerByDateRange(RequestOrdersDto requestOrdersDto);

}
