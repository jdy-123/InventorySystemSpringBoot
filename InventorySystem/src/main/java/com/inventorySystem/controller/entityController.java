package com.inventorySystem.controller;

import com.inventorySystem.Model.Inventory;
import com.inventorySystem.Model.StockIssuance;
import com.inventorySystem.Model.Product;
import com.inventorySystem.Model.User;
import com.inventorySystem.dto.StockIssuanceDto;
import com.inventorySystem.dto.UserDto;
import com.inventorySystem.service.impl.Inventory.InventoryService;
import com.inventorySystem.service.impl.Product.ProductService;
import com.inventorySystem.service.impl.StockIssuance.StockIssuanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class entityController {

    private final ProductService productService;
    private final InventoryService inventoryService;
    private final StockIssuanceService stockIssuanceService;

    @Autowired
    public entityController(ProductService productService, InventoryService inventoryService, StockIssuanceService stockIssuanceService) {
        this.productService = productService;
        this.inventoryService = inventoryService;
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

    @PostMapping("/stockIssuance/save")
    public ResponseEntity<Map<String, Object>> saveStockIssuance(@RequestBody StockIssuanceDto stockIssuance) {
        Map<String, Object> response = stockIssuanceService.save(stockIssuance);

        if ("error".equals(response.get("status"))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        return ResponseEntity.ok(response);
    }




    @PostMapping("stockIssuance/delete")
    public String deleteUser(@RequestParam("id") Long id) {
        stockIssuanceService.deleteById(id);  // Call the service method to delete the user
        return "redirect:/stockIssuance";  // Redirect back to the list of users
    }

    @PostMapping("/updateQuantity")
    public ResponseEntity<Map<String, String>> updateQuantity(@RequestBody StockIssuanceDto stockIssuance) {
        Optional<Inventory> inventoryOpt = inventoryService.getInventoryByProductCode(stockIssuance.getProductCode());

        Map<String, String> response = new HashMap<>();

        if (inventoryOpt.isPresent()) {
            Inventory inventory = inventoryOpt.get();
            int availableStock = Integer.parseInt(inventory.getEndingInventory());
            int issuedQuantity = Integer.parseInt(stockIssuance.getQuantity());

            // Update quantity regardless of available stock
            inventory.setEndingInventory(String.valueOf(availableStock - issuedQuantity));
            inventoryService.updateInventory(inventory);

            response.put("status", "success");
            response.put("message", "Quantity updated successfully!");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Inventory not found!");
            return ResponseEntity.badRequest().body(response);
        }
    }

}
