package com.beitech.store.service.impl;

import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.repository.IOrderDetailRepository;
import com.beitech.store.domain.OrderDetail;
import com.beitech.store.dto.OrderDetailDto;
import com.beitech.store.mapper.IOrderDetailMapper;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.IOrderDetailservice;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailservice {

    private final IOrderDetailRepository orderDetailRepository;
    private final IOrderDetailMapper orderDetailMapper;

    @Override
    public ResponseEntity<ApiResponse<Integer>> saveOrUpdate(OrderDetailDto OrderDetailDto) {
        OrderDetail orderDetail = orderDetailRepository
                .save(orderDetailMapper.dtoToEntity(OrderDetailDto));
        if (orderDetail.getId() == null)
            return ResponseEntity.noContent().build();

        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(new ApiResponse<>(orderDetail.getId(), notification));
    }

    @Override
    public ResponseEntity<ApiResponse<OrderDetailDto>> getOrderDetailById(Integer id) {
        Optional<OrderDetail> orderDetail = orderDetailRepository.findById(id);
        if (!orderDetail.isPresent())
            return ResponseEntity.noContent().build();

        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(new ApiResponse<>(orderDetailMapper.entityToDto(orderDetail.get()),notification));
    }

    @Override
    public ResponseEntity<ApiResponse<List<OrderDetailDto>>> getOrderDetails() {
        List<OrderDetail> orderDetails = orderDetailRepository.findAll();

        if(orderDetails == null)
            return ResponseEntity.noContent().build();

        List<OrderDetailDto> orderDetailsDto = orderDetails.stream()
                .map(orderDetail-> orderDetailMapper.entityToDto(orderDetail))
                .collect(Collectors.toList());
        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(new ApiResponse<>(orderDetailsDto,notification));
    }

}
