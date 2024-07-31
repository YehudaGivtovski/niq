package com.niq.personalizeddataapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
// Indexes speed up the retrieval of records from the database.
// Without an index, the database engine must perform a full table scan to find the records that match the query conditions
@Table(name = "product", indexes = {
        @Index(name = "idx_product_id", columnList = "product_id"),
        @Index(name = "idx_brand", columnList = "brand"),
        @Index(name = "idx_category", columnList = "category")
})
public class ProductEntity {

    @Id
    @NotBlank
    private String productId;
    @NotBlank
    private String category;
    @NotBlank
    private String brand;

}
