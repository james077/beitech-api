package com.beitech.store.service;

import com.beitech.store.domain.Customer;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.exception.InvalidOrderException;
import com.beitech.store.exception.NoContentException;
import com.beitech.store.mapper.ICustomerMapper;
import com.beitech.store.repository.ICustomerRepository;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.impl.CustomerService;
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
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@InjectMocks
	private CustomerService customerService;

	@Mock
	private ICustomerRepository customerRepository;

	@Mock
	private ICustomerMapper customerMapper;

	@Test
	public void getCustomerById_whenExistCustomer() {
		//Arrange
		when(customerRepository.findById(anyInt())).thenReturn(Optional.of(new Customer()));
		when(customerMapper.entityToDto(any(Customer.class))).thenReturn(new CustomerDto());
		//Act
		CustomerDto response = customerService.getCustomerById(1);
		//Assert
		Assert.assertNotNull(response);
	}

	@Test
	public void getCustomer_whenNotExistCustomer() {
		//Arrange
		int id = 1;
		when(customerRepository.findById(anyInt())).thenReturn(Optional.empty());

		//Act and Assert
		NoContentException thrown = assertThrows(
				NoContentException.class,
				() ->customerService.getCustomerById(id)
		);
		assertEquals(id, thrown.getId());
	}

	@Test
	public void getCustomers_whenExistCustomers() {
		//Arrange
		List<Customer> customers  = new ArrayList<>();
		customers.add(new Customer());
		when(customerRepository.findAll()).thenReturn(customers);
		when(customerMapper.entityToDto(any(Customer.class))).thenReturn(new CustomerDto());
		//Act
		ResponseEntity<ApiResponse<List<CustomerDto>>> response = customerService.getCustomers();
		//Assert
		Assert.assertNotNull(response.getBody().getData());
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

	@Test
	public void getCustomers_whenNotExistCustomers() {
		//Arrange
		when(customerRepository.findAll()).thenReturn(null);
		//Act
		ResponseEntity<ApiResponse<List<CustomerDto>>> response = customerService.getCustomers();
		//Assert
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
	}
}
