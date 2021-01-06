package com.beitech.store.delegate;

import com.beitech.store.dto.OrderDto;
import com.beitech.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface IOrderDelegate {

    ResponseEntity<ApiResponse<Integer>> generateOrder(OrderDto OrderDto);
}
