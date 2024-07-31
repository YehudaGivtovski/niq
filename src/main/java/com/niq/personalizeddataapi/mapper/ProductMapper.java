package com.niq.personalizeddataapi.mapper;

import com.niq.personalizeddataapi.dto.ProductDTO;
import com.niq.personalizeddataapi.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

// MapStruct is one of the fastest and most efficient ways to map between objects in Java,
// especially when compared to other methods manual mapping code.
@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO toProductDTO(ProductEntity productEntity);

    ProductEntity toProductEntity(ProductDTO productDTO);
}
