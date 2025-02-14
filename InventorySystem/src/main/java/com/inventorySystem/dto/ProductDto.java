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
    private String productName;
    private String grossPrice;
    private String netPrice;
    private String criticalQuantity;
    private String category;
    private String unitOfMeasurement;
}
