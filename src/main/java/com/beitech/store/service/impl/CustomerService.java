package com.beitech.store.service.impl;

import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.exception.NoContentException;
import com.beitech.store.repository.ICustomerRepository;
import com.beitech.store.domain.Customer;
import com.beitech.store.mapper.ICustomerMapper;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.ICustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerService implements ICustomerService {

    private final ICustomerRepository customerRepository;
    private final ICustomerMapper customerMapper;

    @Override
    public CustomerDto getCustomerById(Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent())
            return customerMapper.entityToDto(customer.get());

        throw NoContentException.builder().id(id).build();
    }

    @Override
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        if(customers == null)
            return ResponseEntity.noContent().build();

        List<CustomerDto> customersDto = customers.stream()
                .map(customer-> customerMapper.entityToDto(customer))
                .collect(Collectors.toList());
        Notification notification = Notification
                .builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value()))
                .build();
        return ResponseEntity.ok(new ApiResponse<>(customersDto,notification));
    }

}
