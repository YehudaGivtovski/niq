package com.niq.personalizeddataapi.entity;


import com.niq.personalizeddataapi.validation.OnShopperShelfItemsUpdate;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
@Entity
// Indexes speed up the retrieval of records from the database.
// Without an index, the database engine must perform a full table scan to find the records that match the query conditions
@Table(name = "shopper")
public class ShopperEntity {

    @Id
    @NotBlank
    private String shopperId;

    // orphanRemoval = true -> If a child entity is removed from the relationship, it should also be removed from the database.
    @Valid
    // The NotEmpty validation will only be applied when the OnShopperShelfItemsUpdate group is validated.
    // In case of create or update shopper, the shelf items can be empty.
    @NotEmpty(groups = OnShopperShelfItemsUpdate.class)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "shopper_id")
    private List<ShopperShelfItemEntity> shelf;
}
