package com.drivesoft.testapi.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;


@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@ToString
public class AccDataResponse {
    @JsonProperty("Status")
    private String status; // mapped from "Status"

    @JsonProperty("Message")
    private String message; // mapped from "Message"

    @JsonProperty("TotalRecords")
    private String totalRecords; // mapped from "TotalRecords"

    @JsonProperty("TotalPages")
    private String totalPages; // mapped from "TotalPages"

    @JsonProperty("PageNumber")
    private String pageNumber; // mapped from "PageNumber"

    @JsonProperty("BeginningPage")
    private String beginningPage; // mapped from "BeginningPage"

    @JsonProperty("EndingPage")
    private String endingPage; // mapped from "EndingPage"

    @JsonProperty("Data")
    private List<DataRow> data; // mapped from "Data"
}








