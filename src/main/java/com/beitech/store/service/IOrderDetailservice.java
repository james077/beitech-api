package com.beitech.store.service;

import com.beitech.store.dto.OrderDetailDto;
import com.beitech.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IOrderDetailservice {

    ResponseEntity<ApiResponse<Integer>> saveOrUpdate(OrderDetailDto orderDetailDto);

    ResponseEntity<ApiResponse<OrderDetailDto>>getOrderDetailById(Integer id);

    ResponseEntity<ApiResponse<List<OrderDetailDto>>> getOrderDetails();

}
