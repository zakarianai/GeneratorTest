package org.javascanner.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javascanner.dto.ClassDetail;
import org.javascanner.util.PackageScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

@Service
public class JavaScannerService {
    @Autowired
    private RestTemplate restTemplate;
    RestClient restClient= RestClient.create();



    public String scanAndSendPackage(String path) throws Exception {
        try {
            // Scan the package
            List<ClassDetail> classDetails = PackageScanner.scanPackage(path);

            // Convert the class details to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String classDetailsJson = objectMapper.writeValueAsString(classDetails);

            // Prepare form-data
            LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("jsondata", classDetailsJson);

            // Send POST request
            String apiUrl = "http://localhost:8081/bot/generate-tests";
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, formData, String.class);

            // Return the API response directly
            if (response.getBody() != null) {
                return response.getBody();
            } else {
                throw new Exception("No response from API");
            }
        } catch (HttpClientErrorException e) {
            throw new Exception("Client error while calling API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (HttpServerErrorException e) {
            throw new Exception("Server error while calling API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new Exception("An unexpected error occurred while processing the package: " + e.getMessage(), e);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    public String scanAndSendPackageRecomendations(String path) throws Exception {
        try {
            // Scan the package
            List<ClassDetail> classDetails = PackageScanner.scanPackage(path);

            // Convert the class details to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            String classDetailsJson = objectMapper.writeValueAsString(classDetails);

            // Prepare form-data
            LinkedMultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
            formData.add("jsonRecomendations", classDetailsJson);

            // Send POST request
            String apiUrl = "http://localhost:8081/bot/generate-recommendations";
            ResponseEntity<String> response = restTemplate.postForEntity(apiUrl, formData, String.class);

            // Return the API response directly
            if (response.getBody() != null) {
                return response.getBody();
            } else {
                throw new Exception("No response from API");
            }
        } catch (HttpClientErrorException e) {
            throw new Exception("Client error while calling API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (HttpServerErrorException e) {
            throw new Exception("Server error while calling API: " + e.getStatusCode() + " - " + e.getResponseBodyAsString(), e);
        } catch (Exception e) {
            throw new Exception("An unexpected error occurred while processing the package: " + e.getMessage(), e);
        }
    }


    //////////////////////////////////////////////////////////////////////////////////////
}
