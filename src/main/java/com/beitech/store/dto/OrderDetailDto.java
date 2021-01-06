package com.beitech.store.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    private Integer id;
    private String productDescription;
    private Double price;
    private Integer quantity;
    private Integer orderId;
    private ProductDto product;

}
