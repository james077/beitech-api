package com.beitech.store.controller;

import com.beitech.store.constants.ResourceMapping;
import com.beitech.store.delegate.IOrderDelegate;
import com.beitech.store.dto.OrderDto;
import com.beitech.store.dto.RequestOrdersDto;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ResourceMapping.ORDER)
public class OrderController {

    private final IOrderService orderService;
    private final IOrderDelegate orderDelegate;

    @PostMapping(value = ResourceMapping.SAVE)
    public ResponseEntity<ApiResponse<Integer>> saveOrder(@RequestBody OrderDto orderDto) {
        return orderDelegate.generateOrder(orderDto);
    }

    @PostMapping()
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrdersPerCustomerByDateRange(@RequestBody RequestOrdersDto requestDto) {
        return orderService.getOrdersPerCustomerByDateRange(requestDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderDto>> getOrder(@PathVariable int id) {
        return orderService.getOrderById(id);
    }

}
