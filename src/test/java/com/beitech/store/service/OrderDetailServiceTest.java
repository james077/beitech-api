package com.beitech.store.service;

import com.beitech.store.domain.OrderDetail;
import com.beitech.store.dto.OrderDetailDto;
import com.beitech.store.mapper.IOrderDetailMapper;
import com.beitech.store.repository.IOrderDetailRepository;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.impl.OrderDetailService;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderDetailServiceTest {

	@InjectMocks
	private OrderDetailService orderDetailservice;

	@Mock
	private IOrderDetailRepository orderDetailRepository;

	@Mock
	private IOrderDetailMapper orderDetailMapper;


	@Test
    public void saveOrUpdate_whenNewOrderDetail_shouldSaveInfo() {
		//Arrange
        Integer id = 1;
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setId(id);
        when(orderDetailMapper.dtoToEntity(any(OrderDetailDto.class))).thenReturn(orderDetail);
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
		//Act
        ResponseEntity<ApiResponse<Integer>> newId = orderDetailservice.saveOrUpdate(new OrderDetailDto());
        //Assert
        Assert.assertEquals(id, newId.getBody().getData());
        assertThat(newId.getStatusCode()).isEqualTo(HttpStatus.OK);

    }

    @Test
    public void saveOrUpdate_whenNotSaveInfo() {
		//Arrange
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailMapper.dtoToEntity(any(OrderDetailDto.class))).thenReturn(orderDetail);
        when(orderDetailRepository.save(orderDetail)).thenReturn(orderDetail);
		//Act
        ResponseEntity<ApiResponse<Integer>> response = orderDetailservice.saveOrUpdate(new OrderDetailDto());
		//Assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

	@Test
	public void getOrderDetailById_whenExistOrderDetail() {
		//Arrange
		when(orderDetailRepository.findById(anyInt())).thenReturn(Optional.of(new OrderDetail()));
		when(orderDetailMapper.entityToDto(any(OrderDetail.class))).thenReturn(new OrderDetailDto());
		//Act
		ResponseEntity<ApiResponse<OrderDetailDto>> response = orderDetailservice.getOrderDetailById(1);
		//Assert
		Assert.assertNotNull(response.getBody().getData());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getOrderDetail_whenNotExistOrderDetail() {
		//Arrange
		when(orderDetailRepository.findById(anyInt())).thenReturn(Optional.empty());
		//Act
		ResponseEntity<ApiResponse<OrderDetailDto>> response = orderDetailservice.getOrderDetailById(1);
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	public void getOrderDetails_whenExistOrderDetails() {
		//Arrange
		List<OrderDetail> orderDetails  = new ArrayList<>();
		orderDetails.add(new OrderDetail());
		when(orderDetailRepository.findAll()).thenReturn(orderDetails);
		when(orderDetailMapper.entityToDto(any(OrderDetail.class))).thenReturn(new OrderDetailDto());
		//Act
		ResponseEntity<ApiResponse<List<OrderDetailDto>>> response = orderDetailservice.getOrderDetails();
		//Assert
		Assert.assertNotNull(response.getBody().getData());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getOrderDetails_whenNotExistOrderDetails() {
		//Arrange
		when(orderDetailRepository.findAll()).thenReturn(null);
		//Act
		ResponseEntity<ApiResponse<List<OrderDetailDto>>> response = orderDetailservice.getOrderDetails();
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
