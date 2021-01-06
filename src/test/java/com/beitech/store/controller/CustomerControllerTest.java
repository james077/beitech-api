package com.beitech.store.controller;

import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.constants.ResourceMapping;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.impl.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration(classes ={CustomerController.class})
@WebMvcTest
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @MockBean
    private CustomerService customerService;
    
    @Autowired
    private MockMvc mvc;

    @Test
    public void getCustomers_OK() throws Exception {
        //Arrange
        List<CustomerDto> customers = new ArrayList();
        CustomerDto customerDto = CustomerDto.builder().name("Maria").build();
        customers.add(customerDto);
        ResponseEntity<ApiResponse<List<CustomerDto>>> respService = ResponseEntity
                .ok(new ApiResponse<>(customers,
                        Notification.builder(GeneralConstants.SUCCESS, String.valueOf( HttpStatus.OK.value())).build())
                );
        when(customerService.getCustomers()).thenReturn(respService);

        //Act and assert
        mvc.perform(get(ResourceMapping.CUSTOMER)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
}
