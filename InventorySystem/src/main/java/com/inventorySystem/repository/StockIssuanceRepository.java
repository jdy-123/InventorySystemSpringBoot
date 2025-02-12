package com.inventorySystem.repository;

import com.inventorySystem.Model.StockIssuance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockIssuanceRepository extends JpaRepository<StockIssuance, Long> {

    StockIssuance findByProductCode(String productCode);

}
