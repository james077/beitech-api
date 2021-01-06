package com.beitech.store.mapper;

import com.beitech.store.domain.Product;
import com.beitech.store.dto.ProductDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

/**
 * @author James Martinez
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring", nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface IProductMapper extends IMapperGeneric<Product, ProductDto> {

}
