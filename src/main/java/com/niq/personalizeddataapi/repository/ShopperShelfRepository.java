package com.niq.personalizeddataapi.repository;

import com.niq.personalizeddataapi.entity.ShopperEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopperShelfRepository extends JpaRepository<ShopperEntity, String> {
}
