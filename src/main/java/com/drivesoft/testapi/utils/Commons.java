package com.drivesoft.testapi.utils;

import com.drivesoft.testapi.configs.ExampleProperties;
import com.drivesoft.testapi.models.general.ApiResponse;
import com.drivesoft.testapi.models.response.AccDataResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.util.Arrays;
import java.util.stream.Collectors;


@Slf4j
@Component
@RequiredArgsConstructor
public class Commons {


    private final ExampleProperties exampleProperties;

    public ApiResponse sendRequest(String identifier, URI uri) {
        ApiResponse finalResp = new ApiResponse();

        Integer conn = exampleProperties.getConnectionTimeout();
        Integer soc = exampleProperties.getReadTimeout();

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(conn)
                .setSocketTimeout(soc)
                .build();


        try (CloseableHttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build()) {

            HttpGet httpGet = new HttpGet(uri);
            httpGet.setHeader("Content-type", "application/json;charset=utf-8");
            httpGet.setHeader("Accept", "application/json");

            log.info("Params to be sent: Headers " + Arrays.toString(httpGet.getAllHeaders()));
            log.info("Params to be sent: request params " + httpGet);

            try (CloseableHttpResponse apiResponse = httpClient.execute(httpGet);
                 BufferedReader rd = new BufferedReader(
                         new InputStreamReader(apiResponse.getEntity().getContent()))) {
                String httpStatusCode = String.valueOf(apiResponse.getStatusLine().getStatusCode());

                String content = rd.lines().collect(Collectors.joining()).trim();

                if (httpStatusCode.equals(String.valueOf(HttpStatus.SC_OK))) {

                    log.info(" SUCCESS: SENT request to service, ");

                    if (content.isEmpty()) {

                        finalResp.setStatus(HttpStatus.SC_NO_CONTENT);
                        finalResp.setMessage("Received Empty Response");
                        log.error(finalResp.toString());
                        return finalResp;

                    } else {

                        finalResp.setStatus(HttpStatus.SC_OK);
                        finalResp.setMessage("Received Response");
                        finalResp.setData(content);
                        log.info("Received Response");
                        return finalResp;

                    }
                } else {
                    log.error("The HTTP Status returned is different from success, for serviceName " + identifier + " HTTP Status is: "
                            + httpStatusCode + " with response " + content);

                    finalResp.setStatus(Integer.valueOf(httpStatusCode));
                    finalResp.setMessage("The HTTP Status returned differs from success status for serviceName:: returned status " + httpStatusCode);
                    log.error(finalResp.toString());
                }
                return finalResp;

            }

        } catch (ConnectTimeoutException ctEx) {

            log.error(ctEx.getClass().getSimpleName() + " Connection timeout encountered while pushing for serviceName: " + " " + ctEx.getMessage());
            finalResp.setStatus(408);
            finalResp.setMessage("Connection Timeout occured");
            log.error(finalResp.toString());
            return finalResp;

        } catch (SocketTimeoutException stEx) {

            log.error(stEx.getClass().getSimpleName() + " socket timeout encountered while pushing serviceName: " + " " + stEx.getMessage());
            finalResp.setStatus(502);
            finalResp.setMessage("socket timeout encountered");
            log.error(finalResp.toString());
            return finalResp;

        } catch (Exception e) {

            log.error(e.getClass().getSimpleName() + "An error occurred while pushing serviceName: " + identifier + e.getMessage());
            if (finalResp.getMessage() == null) {
                finalResp.setStatus(400);
                finalResp.setMessage("An error occurred while pushing serviceName: " + identifier);
            }
            log.error(finalResp.toString());
            return finalResp;

        }
    }

    public static AccDataResponse deserializeFetchDataJson(String jsonString) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.readValue(jsonString, AccDataResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
            return new AccDataResponse();
        }
    }


}
