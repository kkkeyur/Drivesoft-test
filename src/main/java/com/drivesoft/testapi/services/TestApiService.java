package com.drivesoft.testapi.services;


import com.drivesoft.testapi.configs.ExampleProperties;
import com.drivesoft.testapi.entities.Vehicles;
import com.drivesoft.testapi.models.general.ApiResponse;
import com.drivesoft.testapi.models.requests.FetchDataRequest;
import com.drivesoft.testapi.models.response.AccDataResponse;
import com.drivesoft.testapi.models.response.DataRow;
import com.drivesoft.testapi.models.response.Row;
import com.drivesoft.testapi.repos.VehicleRepository;
import com.drivesoft.testapi.utils.Commons;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TestApiService {

    private final Commons commons;

    private final ExampleProperties exampleProperties;

    private final VehicleRepository vehicleRepository;

    public ApiResponse fetchAccountDetails(String token, FetchDataRequest fetchDataRequest) {
        ApiResponse response = new ApiResponse();

        URIBuilder uriBuilder;
        URI uri = null;
        try {
            uriBuilder = new URIBuilder(exampleProperties.getBaseURL() + "/" + exampleProperties.getFetchDataEndpoint());


            uriBuilder.setParameter("Token", token)
                    .setParameter("PageNumber", fetchDataRequest.getPageNumber() != null ? String.valueOf(fetchDataRequest.getPageNumber()) : "1")
                    .setParameter("LayoutID", fetchDataRequest.getLayoutID() != null ? String.valueOf(fetchDataRequest.getLayoutID()) : "")
                    .setParameter("InstitutionID", fetchDataRequest.getInstitutionID() != null ? String.valueOf(fetchDataRequest.getInstitutionID()) : "")
                    .setParameter("SalesLocation", fetchDataRequest.getSalesLocation() != null && !fetchDataRequest.getSalesLocation().isEmpty() ? fetchDataRequest.getSalesLocation() : exampleProperties.getSalesLocation())
                    .setParameter("AccountType", fetchDataRequest.getAccType() != null && !fetchDataRequest.getAccType().isEmpty() ? fetchDataRequest.getAccType() : "")
                    .setParameter("AccountStatus", fetchDataRequest.getAccStatus() != null && !fetchDataRequest.getAccStatus().isEmpty() ? fetchDataRequest.getAccStatus() : exampleProperties.getAccountStatus());

            uri = uriBuilder.build();

        } catch (URISyntaxException e) {

            response.setStatus(HttpStatus.SC_INTERNAL_SERVER_ERROR);
            response.setMessage("error occurred forming 'GetAccountList' returning Empty Response");
            response.setData(new ArrayList<>());
            return response;

        }

        response = commons.sendRequest("fetchData", uri);

        if (response.getData() != null) {

            log.info("Data is retrieved successFully, proceeding further");

            AccDataResponse accDataResponse = commons.deserializeFetchDataJson(String.valueOf(response.getData()));

            if (accDataResponse != null) {
                log.info("overriding response");
                response.setData(accDataResponse);
            }

            log.info("Saving the same data to the Vehicles Table");

            List<Vehicles> savedVehicles = saveOrUpdateVehicles(accDataResponse.getData());

            if (savedVehicles != null || !savedVehicles.isEmpty()) {
                log.info("vehicles data saved for {} entries", savedVehicles.size());
            }
        }
        return response;
    }

    @Transactional
    private List<Vehicles> saveOrUpdateVehicles(List<DataRow> dataRows) {
        List<Vehicles> responseList = new ArrayList<>();
        for (DataRow dataRow : dataRows) {
            Row row = dataRow.getRow(); // Get the Row object from DataRow

            if (row == null) {
                // Handle the case where Row is null (optional)
                log.info("Row is null, skipping this DataRow.");
                continue; // Skip to the next iteration
            }

            Long incomingAcctId = Long.valueOf(row.getAcctID());
            String collateralStockNumber = row.getCollateralStockNumber();

            // Check if the vehicle already exists by AcctID and CollateralStockNumber
            if (vehicleRepository.existsByAcctIdAndCollateralStockNumber(incomingAcctId, collateralStockNumber)) {
                // Log that it already exists and skip
                log.info("Vehicle with acctId " + incomingAcctId + " and collateral stock number " + collateralStockNumber + " already exists. Skipping update.");
            } else {
                // Create a new vehicle if it doesn't exist
                Vehicles newVehicle = createVehicleFromRow(row);

                if (newVehicle != null) {
                    var savedObj = vehicleRepository.save(newVehicle);
                    responseList.add(savedObj);
                    log.info("new entry saved");
                }
            }
        }
        log.info("saved entry {}", responseList.size());
        return responseList;
    }


    private Vehicles createVehicleFromRow(Row row) {
        Vehicles vehicle = new Vehicles();

        try {
            vehicle.setAcctId(Long.valueOf(row.getAcctID()));

            vehicle.setCollateralStockNumber(row.getCollateralStockNumber().toString());
            if (row.getContractSalesPrice() == null) {
                vehicle.setContractSalesPrice("0.0000");
            } else {
                String salesPriceString = row.getContractSalesPrice().toString();

                if (salesPriceString.isEmpty() || salesPriceString.equals("0.0000")) {
                    vehicle.setContractSalesPrice("0.0000");
                } else {
                    vehicle.setContractSalesPrice(row.getContractSalesPrice());
                }
            }

            vehicle.setAcctType(row.getAcctType().toString());
            if (row.getContractDate() != null && !row.getContractDate().isEmpty()) {
                vehicle.setContractDate(row.getContractDate());
            }
            vehicle.setCollateralYearModel(row.getCollateralYearModel());
            vehicle.setCollateralMake(row.getCollateralMake());
            vehicle.setCollateralModel(row.getCollateralModel());
            vehicle.setBorrower1FirstName(row.getBorrower1FirstName());
            vehicle.setBorrower1LastName(row.getBorrower1LastName());

            return vehicle;

        } catch (Exception e) {

            log.error("Error mapping Row-> Vehicle");
            return null;
        }
    }


}
