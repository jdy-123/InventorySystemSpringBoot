package com.inventorySystem.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockIssuanceDto
{

    private Long id;
    @NotEmpty(message = "Product Code should not be empty")
    private String productCode;
    @NotEmpty(message = "Product Name should not be empty")
    private String productName;
    private String date;
    private String quantity;
    private String grossAmount;
    private String totalCostInGross;
    @NotEmpty(message = "Shop should not be empty")
    private String shop;
    private String adjustment;
}
