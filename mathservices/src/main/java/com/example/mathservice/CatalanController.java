package com.example.mathservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@CrossOrigin(origins = "*")
public class CatalanController {

    @Autowired
    private MathServices mathServices;

    @GetMapping("/catalan")
    public ResponseEntity<MathResponse> catalan(@RequestParam(value = "value") String value) {
        try {
            int n = Integer.parseInt(value);
            if (n < 0) {
                return ResponseEntity.badRequest().build();
            }

            String result = mathServices.calculateCatalan(n);
            MathResponse response = new MathResponse("Secuencia de Catalan", n, result);

            return ResponseEntity.ok(response);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}