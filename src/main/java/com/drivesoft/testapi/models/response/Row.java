package com.drivesoft.testapi.models.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class Row {

    @JsonProperty("ContractSalesPrice")
    private String contractSalesPrice; // Highlighted field

    @JsonProperty("AcctType")
    private String acctType; // Highlighted field

    @JsonProperty("SalesGroupPerson1ID")
    private String salesGroupPerson1ID; // Not highlighted

    @JsonProperty("ContractDate")
    private String contractDate; // Highlighted field

    @JsonProperty("CollateralStockNumber")
    private String collateralStockNumber; // Highlighted field

    @JsonProperty("AcctCurTotalBalance")
    private String acctCurTotalBalance; // Not highlighted

    @JsonProperty("CollateralYearModel")
    private String collateralYearModel; // Highlighted field

    @JsonProperty("CollateralMake")
    private String collateralMake; // Highlighted field

    @JsonProperty("CollateralModel")
    private String collateralModel; // Highlighted field

    @JsonProperty("Borrower1FirstName")
    private String borrower1FirstName; // Highlighted field

    @JsonProperty("Borrower1LastName")
    private String borrower1LastName; // Highlighted field

    @JsonProperty("AcctID")
    private String acctID;

}
