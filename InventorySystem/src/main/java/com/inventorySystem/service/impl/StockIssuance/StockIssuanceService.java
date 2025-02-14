package com.inventorySystem.service.impl.StockIssuance;

import com.inventorySystem.Model.StockIssuance;
import com.inventorySystem.dto.StockIssuanceDto;
import org.springframework.data.domain.Page;

import java.util.Map;
import java.util.Optional;

public interface StockIssuanceService {

    /**
     * Fetches all StockIssuance items with pagination support.
     * @param page the page number (zero-based).
     * @param size the page size (number of items per page).
     * @return a Page containing StockIssuance items.
     */
    Page<StockIssuance> findAllItem(int page, int size);

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
    Optional<StockIssuance> findById(Long id);

    Map<String, Object> save(StockIssuanceDto stockIssuance);
}
