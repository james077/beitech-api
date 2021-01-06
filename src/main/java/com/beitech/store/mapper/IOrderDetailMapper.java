package com.beitech.store.mapper;

import com.beitech.store.domain.OrderDetail;
import com.beitech.store.dto.OrderDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author James Martinez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IOrderDetailMapper extends IMapperGeneric<OrderDetail, OrderDetailDto> {

    @Mappings({
            @Mapping(source = "order.id", target = "orderId"),
    })
    OrderDetailDto entityToDto(OrderDetail orderDetail);
}
