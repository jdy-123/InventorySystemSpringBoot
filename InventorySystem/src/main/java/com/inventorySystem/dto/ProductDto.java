package com.inventorySystem.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto
{

    private String productCode;
    @NotEmpty(message = "Product Name should not be empty")
    private String productName;
    private String grossPrice;
    private String netPrice;
    private String criticalQuantity;
    @NotEmpty(message = "Category should not be empty")
    private String category;
    private String unitOfMeasurement;
}
