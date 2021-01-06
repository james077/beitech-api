package com.beitech.store.controller;

import com.beitech.store.constants.ResourceMapping;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(ResourceMapping.CUSTOMER)
public class CustomerController {

    private final ICustomerService customerService;

    @GetMapping()
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getCustomers() {
        return customerService.getCustomers();
    }

}
