package com.drivesoft.testapi.configs;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Component
@Data
@ConfigurationProperties("drivesoft-api-test")
public class ExampleProperties {

    @NotNull
    private Integer connectionTimeout;

    @NotNull
    private Integer readTimeout;

    @NotBlank
    private String baseURL;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotNull
    private Long layoutID;

    @NotBlank
    private String salesLocation;

    @NotBlank
    private String accountStatus;

    @NotNull
    private Long institutionID;

    @NotNull
    private Integer pageNumber;

    @NotNull
    private String secretKey;

    @NotNull
    private String fetchDataEndpoint;
}
