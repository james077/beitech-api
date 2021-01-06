package com.beitech.store.mapper;

import com.beitech.store.dto.OrderDto;
import com.beitech.store.domain.Order;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author James Martinez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IOrderMapper extends IMapperGeneric<Order, OrderDto> {

}
