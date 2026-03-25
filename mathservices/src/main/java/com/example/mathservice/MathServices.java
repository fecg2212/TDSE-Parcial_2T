package com.example.mathservice;

import org.springframework.stereotype.Service;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class MathServices {

    public String calculateCatalan(int n) {
        if (n < 0) {
            return "";
        }

        List<BigInteger> catalan = new ArrayList<>();
        catalan.add(BigInteger.ONE);

        for (int i = 1; i <= n; i++) {
            BigInteger ci = BigInteger.ZERO;
            for (int j = 0; j < i; j++) {
                BigInteger cj = catalan.get(j);
                BigInteger ci_1_j = catalan.get(i - 1 - j);
                ci = ci.add(cj.multiply(ci_1_j));
            }
            catalan.add(ci);
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i <= n; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(catalan.get(i).toString());
        }

        return result.toString();
    }
}