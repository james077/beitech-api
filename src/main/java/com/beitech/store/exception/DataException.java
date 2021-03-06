package com.beitech.store.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author James Martinez
 * @since 05/10/2020
 */
@Getter
@AllArgsConstructor
@Builder
public class DataException extends RuntimeException {

    private String customError;

}


