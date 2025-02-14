package com.inventorySystem.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDto {

    private long id;
    private String productCode;
    private String productName;
    private String po;
    private String inventoryBeginning;
    private String endingInventory;
    private String po2;
    private String po3;
    private String po4;
    private String po5;
    private String issued;
    private String category;
    private String remarks;
    private String adjustment;
    private String date;
    private String inbound;
    private String netPrice;
    private String total;

}
