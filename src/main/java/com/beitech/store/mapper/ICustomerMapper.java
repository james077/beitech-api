package com.beitech.store.mapper;

import com.beitech.store.domain.Customer;
import com.beitech.store.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author James Martinez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ICustomerMapper extends IMapperGeneric<Customer, CustomerDto> {

}
