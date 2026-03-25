package com.example.proxyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class ProxyController {

    @Autowired
    private LoadBalancerService loadBalancerService;

    @GetMapping("/catalan")
    public ResponseEntity<Map<String, Object>> catalan(@RequestParam(value = "value") String value) {
        return loadBalancerService.delegateRequest("/catalan?value=" + value);
    }
}
