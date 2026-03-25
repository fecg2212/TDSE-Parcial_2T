package com.example.proxyservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.RestClientException;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoadBalancerService {

    @Value("${service.instance1.url}")
    private String instance1Url;

    @Value("${service.instance2.url}")
    private String instance2Url;

    private final RestTemplate restTemplate = new RestTemplate();
    private boolean instance1Active = true;

    public ResponseEntity<Map<String, Object>> delegateRequest(String path) {
        if (instance1Active) {
            try {
                String url = instance1Url + path;
                ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
                return (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>) response;
            } catch (RestClientException e) {
                instance1Active = false;
                return delegateToInstance2(path);
            }
        } else {
            return delegateToInstance2(path);
        }
    }

    private ResponseEntity<Map<String, Object>> delegateToInstance2(String path) {
        try {
            String url = instance2Url + path;
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            instance1Active = false;
            return (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>) response;
        } catch (RestClientException e) {
            instance1Active = true;
            try {
                String url = instance1Url + path;
                ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
                return (ResponseEntity<Map<String, Object>>) (ResponseEntity<?>) response;
            } catch (RestClientException e2) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("error", "Todos los servicios están caídos");
                return ResponseEntity.status(503).body(errorResponse);
            }
        }
    }
}