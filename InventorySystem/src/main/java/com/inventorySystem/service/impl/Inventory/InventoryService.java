package com.inventorySystem.service.impl.Inventory;

import com.inventorySystem.Model.Inventory;
import com.inventorySystem.Model.StockIssuance;
import com.inventorySystem.dto.InventoryDto;
import com.inventorySystem.dto.StockIssuanceDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface InventoryService {

    List<Inventory> getAllInventory();

    Page<Inventory> getInventoryByPage(int page, int size);

    /**
     * Deletes a StockIssuance by its ID.
     * @param id the ID of the StockIssuance to delete.
     */
    void deleteById(Long id);

    /**
     * Finds a StockIssuance by its ID.
     * @param id the ID of the StockIssuance.
     * @return an Optional containing the StockIssuance if found, otherwise empty.
     */
    Optional<Inventory> findById(Long id);

    void save(InventoryDto inventory);

    Optional<Inventory> getInventoryByProductCode(String productCode);

    void updateInventory(Inventory inventory);
}
