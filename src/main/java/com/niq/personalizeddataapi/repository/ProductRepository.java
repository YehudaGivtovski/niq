package com.niq.personalizeddataapi.repository;

import com.niq.personalizeddataapi.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, String> {

    @Query(value = "SELECT p.product_id, p.brand, p.category " + "FROM product p " +
            "JOIN shopper_shelf_item ssi ON p.product_id = ssi.product_id " +
            "WHERE ssi.shopper_id = :shopperId " +
            "AND (:category IS NULL OR p.category = :category) " +
            "AND (:brand IS NULL OR p.brand = :brand) " +
            "ORDER BY ssi.relevancy_score DESC " +
            "LIMIT :limit", nativeQuery = true)
    List<ProductEntity> findProductsByShopperIdAndFilters(@Param("shopperId") String shopperId,
                                                          @Param("category") String category,
                                                          @Param("brand") String brand,
                                                          @Param("limit") Integer limit);

}