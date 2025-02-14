package com.inventorySystem.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tblinventory")
public class Inventory implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long id;
    @Column(nullable = false)
    private String productCode;
    @Column(nullable = false)
    private String productName;
    @Column
    private String po;
    @Column
    private String inventoryBeginning;
    @Column
    private String endingInventory;
    @Column
    private String po2;
    @Column
    private String po3;
    @Column
    private String po4;
    @Column
    private String po5;
    @Column
    private String issued;
    @Column
    private String category;
    @Column
    private String remarks;
    @Column
    private String adjustment;
    @Column(nullable = false)
    private String date;
    @Column
    private String inbound;
    @Column
    private String netPrice;
    @Column
    private String total;
}
