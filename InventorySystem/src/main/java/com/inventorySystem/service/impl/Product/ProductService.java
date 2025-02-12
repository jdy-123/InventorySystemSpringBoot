package com.inventorySystem.service.impl.Product;

import com.inventorySystem.Model.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    /**
     * Fetches all products.
     * @return a list of all products.
     */
    List<Product> getAllProducts();  // Fetch all products without pagination.

    /**
     * Fetches all products with pagination.
     * @param page the page number (zero-based).
     * @param size the page size (number of items per page).
     * @return a paginated list of products.
     */
    Page<Product> getAllProducts(int page, int size);  // Fetch products with pagination.
}
