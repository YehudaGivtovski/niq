package com.niq.personalizeddataapi.service;

import com.niq.personalizeddataapi.datasource.DataSourceStrategy;
import com.niq.personalizeddataapi.entity.ProductEntity;
import com.niq.personalizeddataapi.entity.ShopperEntity;
import com.niq.personalizeddataapi.exception.ApplicationException;
import com.niq.personalizeddataapi.repository.ProductRepository;
import com.niq.personalizeddataapi.repository.ShopperShelfRepository;
import com.niq.personalizeddataapi.validation.OnShopperShelfItemsUpdate;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Log4j2
@Service
public class PersonalizedProductService {

    private final ProductRepository productRepository;
    private final DataSourceStrategy dataSourceStrategy;
    private final ShopperShelfRepository shopperShelfRepository;

    public PersonalizedProductService(@Qualifier("fileDataSourceStrategy") DataSourceStrategy dataSourceStrategy, ProductRepository productRepository, ShopperShelfRepository shopperShelfRepository) {
        this.dataSourceStrategy = dataSourceStrategy;
        this.productRepository = productRepository;
        this.shopperShelfRepository = shopperShelfRepository;
    }

    // split the updateProducts and updateShopperShelfItems methods to be independent
    public void updateProducts() throws ApplicationException {
        // get new products data
        List<ProductEntity> products = dataSourceStrategy.fetchProducts();
        final List<ProductEntity> validProducts = validateProducts(products);
        productRepository.saveAll(validProducts);
        log.debug("Products updated successfully");

    }

    public void updateShopperShelfItems() throws ApplicationException {
        // get new shopper shelf data
        List<ShopperEntity> shopperShelfEntities = dataSourceStrategy.fetchShopperShelfItems();
        final List<ShopperEntity> validShelves = validateShelves(shopperShelfEntities);
        shopperShelfRepository.saveAll(validShelves);
        log.debug("Shopper shelf items updated successfully");

    }

    private List<ProductEntity> validateProducts(final List<ProductEntity> products) throws ApplicationException {

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            for (ProductEntity product : products) {
                Set<ConstraintViolation<ProductEntity>> violations = validator.validate(product);
                if (!violations.isEmpty()) {
                    throw new ApplicationException("Validation failed for product: " + violations, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        // For simplicity, assuming all products data content is valid
        return products;
    }

    private List<ShopperEntity> validateShelves(final List<ShopperEntity> shopperShelfEntities) throws ApplicationException {

        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            for (ShopperEntity shopperEntity : shopperShelfEntities) {
                Set<ConstraintViolation<ShopperEntity>> violations = validator.validate(shopperEntity, OnShopperShelfItemsUpdate.class);
                if (!violations.isEmpty()) {
                    throw new ApplicationException("Validation failed for product: " + violations, HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
        }

        // For simplicity, assuming all shelves data content is valid
        return shopperShelfEntities;
    }
}