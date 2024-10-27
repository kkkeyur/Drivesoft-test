package com.drivesoft.testapi.models.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@ToString
public class DataRow {
    @JsonProperty("Row")
    private Row row; // mapped from "Row"
}