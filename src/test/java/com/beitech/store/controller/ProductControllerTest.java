package com.beitech.store.controller;

import com.beitech.store.constants.GeneralConstants;
import com.beitech.store.constants.ResourceMapping;
import com.beitech.store.dto.ProductDto;
import com.beitech.store.response.ApiResponse;
import com.beitech.store.response.Notification;
import com.beitech.store.service.impl.ProductService;
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
@ContextConfiguration(classes ={ProductController.class})
@WebMvcTest
public class ProductControllerTest {

    @InjectMocks
    private ProductController productController;

    @MockBean
    private ProductService productService;
    
    @Autowired
    private MockMvc mvc;

    @Test
    public void getProductsByCustomer_OK() throws Exception {
        //Arrange
        List<ProductDto> products = new ArrayList();
        ProductDto productDto = ProductDto.builder().name("RAM").price(200000d).build();
        products.add(productDto);
        ResponseEntity<ApiResponse<List<ProductDto>>> respService = ResponseEntity
                .ok(new ApiResponse<>(products,
                        Notification.builder(GeneralConstants.SUCCESS, String.valueOf( HttpStatus.OK.value())).build())
                );
        when(productService.getProductsByCustomer(1)).thenReturn(respService);

        //Act and Assert
        mvc.perform(get(ResourceMapping.PRODUCT+ResourceMapping.AVAILABLE+ "/1")
                .contentType(APPLICATION_JSON))
                .andExpect(status().isOk());

    }

}
