package com.inventorySystem.service.impl.StockIssuance;

import com.inventorySystem.Model.Product;
import com.inventorySystem.Model.StockIssuance;
import com.inventorySystem.Model.User;
import com.inventorySystem.dto.StockIssuanceDto;
import com.inventorySystem.dto.UserDto;
import com.inventorySystem.repository.StockIssuanceRepository;
import com.inventorySystem.service.impl.Product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

@Service
public class StockIssuanceServiceImpl implements StockIssuanceService {
    private final ProductService productService;
    private final StockIssuanceRepository stockIssuanceRepository;

    public StockIssuanceServiceImpl(ProductService productService, StockIssuanceRepository stockIssuanceRepository) {
        this.productService = productService;
        this.stockIssuanceRepository = stockIssuanceRepository;
    }

    @Override
    public Page<StockIssuance> findAllItem(int page, int size) {
        return stockIssuanceRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public void deleteById(Long id) {
        stockIssuanceRepository.deleteById(id);  // Delete StockIssuance by ID
    }

    @Override
    public Optional<StockIssuance> findById(Long id) {
        return stockIssuanceRepository.findById(id);  // Return StockIssuance by ID wrapped in Optional
    }

    @Override
    public void save(StockIssuanceDto stockIssuance) {
        // Ensure stockIssuance is not null
        if (stockIssuance == null) {
            throw new IllegalArgumentException("stockIssuance cannot be null");
        }

// Fetch all products
        List<Product> productsPage = productService.getAllProducts();  // Pagination applied

// Check if the product list is empty
        if (productsPage.isEmpty()) {
            // Log or handle this case if needed
            throw new IllegalStateException("No products found in the database");
        }

// Find the product with the matching productCode
        Optional<Product> matchingProductByCode = productsPage.stream()
                .filter(product -> product.getProductCode().equals(stockIssuance.getProductCode()))
                .findFirst();

// Find the product with the matching productName
        Optional<Product> matchingProductByName = productsPage.stream()
                .filter(product -> product.getProductName().equals(stockIssuance.getProductName()))
                .findFirst();

        StockIssuance stock = new StockIssuance();

// Assign productCode and productName based on matches
        if (stockIssuance.getProductCode() == null || stockIssuance.getProductCode().isEmpty()) {
            // If no match found by productName, use null or a default
            stock.setProductCode(matchingProductByName.map(Product::getProductCode)
                    .orElse(null));
        } else {
            stock.setProductCode(stockIssuance.getProductCode());
        }

        if (stockIssuance.getProductName() == null || stockIssuance.getProductName().isEmpty()) {
            // If no match found by productCode, use null or a default
            stock.setProductName(matchingProductByCode.map(Product::getProductName)
                    .orElse(null));
        } else {
            stock.setProductName(stockIssuance.getProductName());
        }

// Further properties and save logic
        stock.setDate(stockIssuance.getDate());
        stock.setShop(stockIssuance.getShop());
        stock.setAdjustment(stockIssuance.getAdjustment());
        stock.setQuantity(stockIssuance.getQuantity());
        stock.setGrossAmount(stockIssuance.getGrossAmount());
        stock.setTotalCostInGross(stockIssuance.getTotalCostInGross());

        stockIssuanceRepository.save(stock);

    }


}
