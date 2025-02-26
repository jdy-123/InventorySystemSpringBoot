package com.inventorySystem.repository;

import com.inventorySystem.Model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query("SELECT i FROM Inventory i WHERE i.productCode = :productCode " +
            "AND i.date = :formattedDate")
    List<Inventory> findByProductCodeAndDate(@Param("productCode") String productCode,
                                             @Param("formattedDate") String formattedDate);

    @Query("SELECT i FROM Inventory i WHERE i.productName = :productName " +
            "AND i.date = :formattedDate")
    List<Inventory> findByProductNameAndDate(@Param("productName") String productName,
                                             @Param("formattedDate") String formattedDate);



}
