package com.drivesoft.testapi.models.requests;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.USE_DEFAULTS)
@ToString
public class FetchDataRequest {

    @NotNull
    private Integer pageNumber;

    @NotNull
    private Long layoutID;

    @NotNull
    private Long institutionID;

    private String salesLocation;

    private String accType;

    private String accStatus;


}
