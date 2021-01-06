package com.beitech.store.service;

import com.beitech.store.dto.CustomerDto;
import com.beitech.store.response.ApiResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ICustomerService {

    CustomerDto getCustomerById(Integer id);

    ResponseEntity<ApiResponse<List<CustomerDto>>> getCustomers();

}
