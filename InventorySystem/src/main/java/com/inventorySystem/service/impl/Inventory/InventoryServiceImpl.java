package com.inventorySystem.service.impl.Inventory;

import com.inventorySystem.Model.Inventory;
import com.inventorySystem.dto.InventoryDto;
import com.inventorySystem.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryServiceImpl(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Override
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @Override
    public Page<Inventory> getInventoryByPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return inventoryRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        inventoryRepository.deleteById(id);
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return inventoryRepository.findById(id);
    }

    @Override
    public void save(InventoryDto inventoryDto) {
        Inventory inventory = new Inventory();
        inventory.setProductCode(inventoryDto.getProductCode());
        inventory.setProductName(inventoryDto.getProductName());
        inventory.setEndingInventory(inventoryDto.getEndingInventory());
        inventory.setDate(String.valueOf(new Date())); // Set current date
        inventoryRepository.save(inventory);
    }

    @Override
    public Optional<Inventory> getInventoryByProductCode(String productCode) {
        // Get the current month and year in "MMM-yyyy" format (e.g., "Feb-2025")
        String currentDate = new SimpleDateFormat("MMM-yyyy").format(new Date());

        List<Inventory> inventories = inventoryRepository.findByProductCodeAndDate(productCode, currentDate);

        if (inventories.isEmpty()) {
            return Optional.empty();
        }

        // If multiple records exist, return the most recent one
        return Optional.of(inventories.get(0));
    }

    @Override
    public Optional<Inventory> getInventoryByProductName(String productName) {
        // Get the current month and year in "MMM-yyyy" format (e.g., "Feb-2025")
        String currentDate = new SimpleDateFormat("MMM-yyyy").format(new Date());

        List<Inventory> inventories = inventoryRepository.findByProductNameAndDate(productName, currentDate);

        if (inventories.isEmpty()) {
            return Optional.empty();
        }

        // If multiple records exist, return the most recent one
        return Optional.of(inventories.get(0));
    }

    @Override
    public void updateInventory(Inventory inventory) {
        inventoryRepository.save(inventory);
    }
}
