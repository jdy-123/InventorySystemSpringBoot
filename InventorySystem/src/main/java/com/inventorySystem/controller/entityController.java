package com.inventorySystem.controller;

import com.inventorySystem.Model.StockIssuance;
import com.inventorySystem.Model.Product;
import com.inventorySystem.service.impl.Product.ProductService;
import com.inventorySystem.service.impl.StockIssuance.StockIssuanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class entityController {

    private final ProductService productService;
    private final StockIssuanceService stockIssuanceService;

    @Autowired
    public entityController(ProductService productService, StockIssuanceService stockIssuanceService) {
        this.productService = productService;
        this.stockIssuanceService = stockIssuanceService;
    }

    @GetMapping("/stockIssuance")
    public String findAll(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "100") int size) {

        // Validate page and size to prevent invalid inputs
        if (page < 0) page = 0;
        if (size <= 0) size = 100;

        // Fetch paginated products and stock issuance
        List<Product> productsPage = productService.getAllProducts();  // Pagination applied
        Page<StockIssuance> stockIssuancePage = stockIssuanceService.findAllItem(page, size);  // Pagination applied

        // Add pages to the model
        model.addAttribute("products", productsPage);
        model.addAttribute("stockIssuance", stockIssuancePage);

        // Add pagination metadata to the model
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", stockIssuancePage.getTotalPages());  // Ensure correct totalPages based on stockIssuance

        return "stockIssuance";
    }
}
