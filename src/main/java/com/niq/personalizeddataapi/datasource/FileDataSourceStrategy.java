package com.niq.personalizeddataapi.datasource;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.niq.personalizeddataapi.entity.ProductEntity;
import com.niq.personalizeddataapi.entity.ShopperEntity;
import com.niq.personalizeddataapi.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileDataSourceStrategy implements DataSourceStrategy {

    private final ObjectMapper objectMapper;
    private static final String PRODUCT_DATA_FILE = "teamdata/products.json";
    private static final String SHELVES_DATA_FILE = "teamdata/shelves.json";

    @Override
    public List<ProductEntity> fetchProducts() throws ApplicationException {
        try {
            InputStream inputStream = new ClassPathResource(PRODUCT_DATA_FILE).getInputStream();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new ApplicationException("Error occurred while fetching products", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<ShopperEntity> fetchShopperShelfItems() throws ApplicationException {
        try {
            InputStream inputStream = new ClassPathResource(SHELVES_DATA_FILE).getInputStream();
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new ApplicationException("Error occurred while fetching shopper shelf items", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}