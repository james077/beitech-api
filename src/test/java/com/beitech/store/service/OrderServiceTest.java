package com.beitech.store.service;

import com.beitech.store.domain.Customer;
import com.beitech.store.domain.Order;
import com.beitech.store.domain.Product;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.dto.OrderDetailDto;
import com.beitech.store.dto.OrderDto;
import com.beitech.store.dto.ProductDto;
import com.beitech.store.dto.RequestOrdersDto;
import com.beitech.store.mapper.IOrderMapper;
import com.beitech.store.mapper.IProductMapper;
import com.beitech.store.repository.ICustomerRepository;
import com.beitech.store.repository.IOrderRepository;
import com.beitech.store.repository.IProductRepository;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.impl.OrderService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

	@InjectMocks
	private OrderService orderService;

	@Mock
	private IOrderRepository orderRepository;

	@Mock
	private ICustomerRepository customerRepository;

	@Mock
	private IOrderMapper orderMapper;

	@Mock
	private IProductRepository productRepository;

	@Mock
	private IProductMapper productMapper;

	private OrderDto orderDto;

	@BeforeEach
	public void init(){
		CustomerDto customerDto = CustomerDto.builder()
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
	}

	@Test
    public void saveOrUpdate_whenNewOrder_shouldSaveInfo() {
		//Arrange
        Integer id = 1;
        Order order = new Order();
        order.setId(id);
        Product product = new Product();
        when(orderMapper.dtoToEntity(any(OrderDto.class))).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);

		//Act
        int newId = orderService.saveOrUpdate(orderDto);

        //Assert
        Assert.assertEquals(id.intValue(), newId);
    }

	@Test
	public void getOrderById_whenExistOrder() {
		//Arrange
		when(orderRepository.findById(anyInt())).thenReturn(Optional.of(new Order()));
		when(orderMapper.entityToDto(any(Order.class))).thenReturn(new OrderDto());
		//Act
		ResponseEntity<ApiResponse<OrderDto>> response = orderService.getOrderById(1);
		//Assert
		Assert.assertNotNull(response.getBody().getData());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getOrdeByIdr_whenNotExistOrder() {
		//Arrange
		when(orderRepository.findById(anyInt())).thenReturn(Optional.empty());
		//Act
		ResponseEntity<ApiResponse<OrderDto>> response = orderService.getOrderById(1);
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}

	@Test
	public void getOrdersPerCustomerByDateRange_whenExistOrders() {
		//Arrange
		RequestOrdersDto requestOrdersDto = RequestOrdersDto.builder()
				.customerId(1).initialDate(LocalDate.now()).finalDate(LocalDate.now())
				.build();
		List<Order> orders  = new ArrayList<>();
		orders.add(new Order());
		when(orderRepository.findOrdersByDateRange(
				requestOrdersDto.getCustomerId(),requestOrdersDto.getInitialDate(),requestOrdersDto.getFinalDate()
		)).thenReturn(orders);
		when(orderMapper.entityToDto(any(Order.class))).thenReturn(new OrderDto());
		//Act
		ResponseEntity<ApiResponse<List<OrderDto>>> response = orderService.getOrdersPerCustomerByDateRange(requestOrdersDto);
		//Assert
		Assert.assertNotNull(response.getBody().getData());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getOrdersPerCustomerByDateRange_whenNotExistOrders() {
		//Arrange
		RequestOrdersDto requestOrdersDto = RequestOrdersDto.builder()
				.customerId(1).initialDate(LocalDate.now()).finalDate(LocalDate.now())
				.build();
		when(orderRepository.findOrdersByDateRange(
				requestOrdersDto.getCustomerId(),requestOrdersDto.getInitialDate(),requestOrdersDto.getFinalDate()
		)).thenReturn(null);
		//Act
		ResponseEntity<ApiResponse<List<OrderDto>>> response = orderService.getOrdersPerCustomerByDateRange(requestOrdersDto);
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
