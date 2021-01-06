package com.beitech.store.service.impl;

import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.dto.OrderDto;
import com.beitech.store.dto.RequestOrdersDto;
import com.beitech.store.exception.NoContentException;
import com.beitech.store.repository.ICustomerRepository;
import com.beitech.store.repository.IOrderRepository;
import com.beitech.store.repository.IProductRepository;
import com.beitech.store.domain.Customer;
import com.beitech.store.domain.Order;
import com.beitech.store.domain.Product;
import com.beitech.store.dto.OrderDetailDto;
import com.beitech.store.mapper.IOrderMapper;
import com.beitech.store.mapper.IProductMapper;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService implements IOrderService {

    private final IOrderRepository orderRepository;
    private final ICustomerRepository customerRepository;
    private final IProductRepository productRepository;
    private final IOrderMapper orderMapper;
    private final IProductMapper productMapper;

    @Override
    public int saveOrUpdate(OrderDto orderDto) {
        Order order = orderMapper.dtoToEntity(orderDto);
        order.setAllJoins();
        orderRepository.save(order);
        return order.getId();
    }

    @Override
    public ResponseEntity<ApiResponse<OrderDto>> getOrderById(Integer id) {
        Optional<Order> order = orderRepository.findById(id);
        if (!order.isPresent())
            return ResponseEntity.noContent().build();

        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(new ApiResponse<>(orderMapper.entityToDto(order.get()),notification));
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrderDto>>> getOrdersPerCustomerByDateRange(RequestOrdersDto requestDto) {
        List<Order> orders = orderRepository
                .findOrdersByDateRange(requestDto.getCustomerId(), requestDto.getInitialDate(), requestDto.getFinalDate());

        if(orders == null)
            return ResponseEntity.noContent().build();

        List<OrderDto> ordersDto = orders.stream()
                .map(order-> orderMapper.entityToDto(order))
                .collect(Collectors.toList());
        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(new ApiResponse<>(ordersDto,notification));
    }



}
