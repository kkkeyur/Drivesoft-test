package com.drivesoft.testapi.entities;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "vehicles")
@RequiredArgsConstructor
public class Vehicles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Ensure this matches the primary key in your databas
    private Long id;

    @Column(name = "acct_id")  // Ensure this matches the primary key in your databas
    private Long acctId;

    @Column(name = "contract_sales_price")
    private String contractSalesPrice;

    @Column(name = "acct_type", length = 10)
    private String acctType;

    @Column(name = "sales_group_person1_id", length = 50)
    private String salesGroupPerson1Id;

    @Column(name = "contract_date")
    private String contractDate;

    @Column(name = "collateral_stock_number")
    private String collateralStockNumber;

    @Column(name = "collateral_year_model", length = 4)
    private String collateralYearModel;

    @Column(name = "collateral_make", length = 20)
    private String collateralMake;

    @Column(name = "collateral_model", length = 20)
    private String collateralModel;

    @Column(name = "borrower1_first_name", length = 50)
    private String borrower1FirstName;

    @Column(name = "borrower1_last_name", length = 50)
    private String borrower1LastName;

}

