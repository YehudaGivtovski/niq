package com.niq.personalizeddataapi.datasource;

import com.niq.personalizeddataapi.entity.ProductEntity;
import com.niq.personalizeddataapi.entity.ShopperEntity;
import com.niq.personalizeddataapi.exception.ApplicationException;

import java.util.List;

public interface DataSourceStrategy {

    List<ProductEntity> fetchProducts() throws ApplicationException;

    List<ShopperEntity> fetchShopperShelfItems() throws ApplicationException;
}