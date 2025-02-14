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
    private String productCode;
    private String productName;
    private String date;
    private String quantity;
    private String grossAmount;
    private String totalCostInGross;
    private String shop;
    private String adjustment;
}
