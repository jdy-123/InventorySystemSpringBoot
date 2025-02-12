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
@Table(name="tblreport")
public class StockIssuance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(nullable=false)
    private String productCode;

    @Column(nullable=false)
    private String productName;

    @Column(nullable=false)
    private String date;

    @Column(nullable=false)
    private String quantity;

    @Column(nullable=false)
    private String grossAmount;

    @Column(nullable=false)
    private String totalCostInGross;

    @Column(nullable=false)
    private String shop;

    @Column(nullable=false)
    private String adjustment;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


}
