package com.drivesoft.testapi.controllers;


import com.drivesoft.testapi.models.general.ApiResponse;
import com.drivesoft.testapi.models.requests.FetchDataRequest;
import com.drivesoft.testapi.repos.VehicleRepository;
import com.drivesoft.testapi.services.TestApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Slf4j
@Controller
@RequestMapping("/accDetails")
@RequiredArgsConstructor
public class TestApiController {

    private final TestApiService testApiService;

    private final VehicleRepository vehicleRepository;

    /** fetchData - to internally call Account/GetAccountList API
     * @param token,FetchDataRequest
     * @return ResponseEntity<ApiResponse>
     */
    @PostMapping(value = "/fetchAndStoreData")
    public ResponseEntity<ApiResponse> fetchData(@RequestHeader("Authorization")  String token   ,
                                                 @RequestBody FetchDataRequest fetchDataRequest) {
        ApiResponse apiResponse = new ApiResponse();


        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        apiResponse = testApiService.fetchAccountDetails(token,fetchDataRequest);

        if (apiResponse == null) {
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setMessage("Error occurred fetching Data");
            apiResponse.setData(new ArrayList<>());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);

        } else if (apiResponse.getData() == null) {

            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("received empty response fetching Data");
            apiResponse.setData(new ArrayList<>());

            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

        } else {
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Data fetched and stored successfully");
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);

        }

    }

    /**
     * getData - after getting custom JWT token this endpoint can be used to retrieve Stored data
     * @param token
     * @return ResponseEntity<ApiResponse>
     */
    @GetMapping("/getData")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ApiResponse> getSecureData(@RequestHeader("Authorization") String token) {
        ApiResponse apiResponse = new ApiResponse();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        log.info("Welcome "+username+ "you're authorized to get data");

        var response  = vehicleRepository.findAll();

        if (response!=null || response.isEmpty()){
            log.info("vehicle list retrieved {}",response.size());
            apiResponse.setStatus(HttpStatus.OK.value());
            apiResponse.setMessage("Vehicle List Retrieved");
            apiResponse.setData(response);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);


        } else {
            log.info("error retrieving vehicle list {}",response.size());
            apiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            apiResponse.setMessage("Error Retrieved vehicle list");
            apiResponse.setData(new ArrayList<>());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiResponse);

        }
    }

}
