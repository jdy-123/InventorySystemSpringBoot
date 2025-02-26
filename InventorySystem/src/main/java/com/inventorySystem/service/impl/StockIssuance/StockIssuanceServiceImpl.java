package com.inventorySystem.service.impl.StockIssuance;

import com.inventorySystem.Model.Inventory;
import com.inventorySystem.Model.Product;
import com.inventorySystem.Model.StockIssuance;
import com.inventorySystem.dto.StockIssuanceDto;
import com.inventorySystem.repository.StockIssuanceRepository;
import com.inventorySystem.service.impl.Inventory.InventoryService;
import com.inventorySystem.service.impl.Product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StockIssuanceServiceImpl implements StockIssuanceService {
    private final ProductService productService;
    private final StockIssuanceRepository stockIssuanceRepository;
    private final InventoryService inventoryService;

    public StockIssuanceServiceImpl(ProductService productService, StockIssuanceRepository stockIssuanceRepository, InventoryService inventoryService) {
        this.productService = productService;
        this.stockIssuanceRepository = stockIssuanceRepository;
        this.inventoryService = inventoryService;
    }

    @Override
    public Page<StockIssuance> findAllItem(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return stockIssuanceRepository.findAll(pageable);
    }

    @Override
    public void deleteById(Long id) {
        stockIssuanceRepository.deleteById(id);  // Delete StockIssuance by ID
    }

    @Override
    public Optional<StockIssuance> findById(Long id) {
        return stockIssuanceRepository.findById(id);  // Return StockIssuance by ID wrapped in Optional
    }

    @Transactional
    public Map<String, Object> save(StockIssuanceDto stockIssuance) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (stockIssuance == null) {
                response.put("status", "error");
                response.put("message", "Stock issuance cannot be null");
                return response;
            }

            List<Product> productsPage = productService.getAllProducts();
            if (productsPage.isEmpty()) {
                response.put("status", "error");
                response.put("message", "No products found in the database");
                return response;
            }

            Optional<Product> matchingProductByCode = productsPage.stream()
                    .filter(product -> product.getProductCode().equals(stockIssuance.getProductCode()))
                    .findFirst();

            Optional<Product> matchingProductByName = productsPage.stream()
                    .filter(product -> product.getProductName().equals(stockIssuance.getProductName()))
                    .findFirst();

            if (matchingProductByCode.isEmpty() && !stockIssuance.getProductCode().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Product not found with product code: " + stockIssuance.getProductCode());
                return response;
            }

            if (matchingProductByName.isEmpty() && !stockIssuance.getProductName().isEmpty()) {
                response.put("status", "error");
                response.put("message", "Product not found with product name: " + stockIssuance.getProductName());
                return response;
            }
            Optional<Inventory> inventoryOpt;
            inventoryOpt = inventoryService.getInventoryByProductCode(stockIssuance.getProductCode());
            if (inventoryOpt.isEmpty() && !stockIssuance.getProductCode().isEmpty()) {
                response.put("status", "error");
                response.put("message", "No inventory found for product code: " + stockIssuance.getProductCode());
                return response;
            }

            inventoryOpt = inventoryService.getInventoryByProductName(stockIssuance.getProductName());
            if (inventoryOpt.isEmpty() && !stockIssuance.getProductName().isEmpty()) {
                response.put("status", "error");
                response.put("message", "No inventory found for product name: " + stockIssuance.getProductCode());
                return response;
            }

            Inventory inventory = inventoryOpt.get();
            int availableStock = Integer.parseInt(inventory.getEndingInventory());
            int issuedQuantity = Integer.parseInt(stockIssuance.getQuantity());

            if (issuedQuantity > availableStock) {
                response.put("status", "warning");
                response.put("message", "Insufficient stock! Available: " + availableStock);
                return response;
            }

            inventory.setEndingInventory(String.valueOf(availableStock - issuedQuantity));
            inventoryService.updateInventory(inventory);

            StockIssuance stock = new StockIssuance();
            Product product = stockIssuance.getProductCode().isEmpty() ? matchingProductByName.get() :  matchingProductByCode.get();
            stock.setProductCode(product.getProductCode());
            stock.setProductName(product.getProductName());
            stock.setDate(stockIssuance.getDate());
            stock.setShop(stockIssuance.getShop());
            stock.setAdjustment(stockIssuance.getAdjustment());
            stock.setQuantity(stockIssuance.getQuantity());
            stock.setGrossAmount(product.getGrossPrice());

            double totalCost = tryParseDouble(stock.getQuantity()) * tryParseDouble(stock.getGrossAmount());
            stock.setTotalCostInGross(String.valueOf(totalCost));

            stockIssuanceRepository.save(stock);

            response.put("status", "success");
            response.put("message", "Stock issued successfully!");
            response.put("data", stock); // Send back the saved data to update UI
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An unexpected error occurred: " + e.getMessage());
        }
        return response;
    }




    private double tryParseDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

}
