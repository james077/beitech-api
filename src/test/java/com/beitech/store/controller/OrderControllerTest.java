package com.beitech.store.controller;

import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.constants.ResourceMapping;
import com.beitech.store.delegate.impl.OrderDelegate;
import com.beitech.store.dto.CustomerDto;
import com.beitech.store.dto.OrderDto;
import com.beitech.store.dto.RequestOrdersDto;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.impl.OrderService;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters=false)
@ContextConfiguration(classes ={OrderController.class})
@WebMvcTest
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @MockBean
    private OrderService orderService;

    @MockBean
    private OrderDelegate orderDelegate;
    
    @Autowired
    private MockMvc mvc;

    private OrderDto orderDto;
    private ObjectWriter ow;

    @BeforeEach
    public void init(){
        CustomerDto customerDto = CustomerDto.builder()
                .name("Maria").build();
        orderDto = OrderDto.builder()
                .customer(customerDto).total(1000000d).build();

        org.codehaus.jackson.map.ObjectMapper mapper = new org.codehaus.jackson.map.ObjectMapper();
        mapper.configure(DeserializationConfig.Feature.AUTO_DETECT_SETTERS, false);
        ow = mapper.writer().withDefaultPrettyPrinter();
    }


    @Test
    public void saveOrder_OK() throws Exception {
        //Arrange
        ResponseEntity<ApiResponse<Integer>> respService = ResponseEntity.ok(new ApiResponse<>(1,
                Notification.builder(GeneralConstants.SUCCESS, String.valueOf(HttpStatus.OK.value())).build())
        );
        when(orderDelegate.generateOrder(orderDto)).thenReturn(respService);

        String requestJson = ow.writeValueAsString(orderDto);

        //Act and Assert
        mvc.perform(post(ResourceMapping.ORDER + ResourceMapping.SAVE)
                .content(requestJson)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getOrderById_OK() throws Exception {
        //Arrange
        orderDto.setId(1);
        ResponseEntity<ApiResponse<OrderDto>> respService = ResponseEntity
                .ok(new ApiResponse<>(orderDto,
                        Notification.builder(GeneralConstants.SUCCESS, String.valueOf( HttpStatus.OK.value())).build())
                );
        when(orderService.getOrderById(orderDto.getId())).thenReturn(respService);

        //Act and Assert
        mvc.perform(get(ResourceMapping.ORDER + "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void getOrdersPerCustomerByDateRange_OK() throws Exception {
        //Arrange
        List<OrderDto> orders = new ArrayList<>();
        orders.add(orderDto);
        RequestOrdersDto requestOrdersDto = RequestOrdersDto.builder()
                .customerId(1).initialDate(LocalDate.now()).finalDate(LocalDate.now())
                .build();
        ResponseEntity<ApiResponse<List<OrderDto>>> respService = ResponseEntity
                .ok(new ApiResponse<>(orders,
                        Notification.builder(GeneralConstants.SUCCESS, String.valueOf( HttpStatus.OK.value())).build())
                );
        when(orderService.getOrdersPerCustomerByDateRange(requestOrdersDto)).thenReturn(respService);
        String requestJson = ow.writeValueAsString(requestOrdersDto);

        //Act and Assert
        mvc.perform(post(ResourceMapping.ORDER)
                .content(requestJson)
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
