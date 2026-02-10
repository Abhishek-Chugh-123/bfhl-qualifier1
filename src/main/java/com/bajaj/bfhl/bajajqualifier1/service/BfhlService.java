package com.bajaj.bfhl.bajajqualifier1.service;

import com.bajaj.bfhl.bajajqualifier1.dto.BfhlRequest;
import com.bajaj.bfhl.bajajqualifier1.util.MathUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class BfhlService {

    @Value("${gemini.api.key}")
    private String apiKey;

    public Object handle(BfhlRequest bfhlRequest) {

        int count = 0;
        if (bfhlRequest.getFibonacci() != null) count++;
        if (bfhlRequest.getPrime() != null) count++;
        if (bfhlRequest.getLcm() != null) count++;
        if (bfhlRequest.getHcf() != null) count++;
        if (bfhlRequest.getAI() != null) count++;

        if (count != 1)
            throw new IllegalArgumentException("ExactlyOneKeyIsRequired");

        if (bfhlRequest.getFibonacci() != null) {
            int n = bfhlRequest.getFibonacci();
            if (n < 0)
                throw new IllegalArgumentException("Invalid input");
            return MathUtil.fibonacci(n);
        }

        if (bfhlRequest.getPrime() != null) {
            return bfhlRequest.getPrime()
                    .stream()
                    .filter(MathUtil::isPrime)
                    .collect(Collectors.toList());
        }

        if (bfhlRequest.getHcf() != null) {
            if (bfhlRequest.getHcf().isEmpty())
                throw new IllegalArgumentException("Invalid input");
            return MathUtil.hcf(bfhlRequest.getHcf());
        }

        if (bfhlRequest.getLcm() != null) {
            if (bfhlRequest.getLcm().isEmpty())
                throw new IllegalArgumentException("Invalid input");
            return MathUtil.lcm(bfhlRequest.getLcm());
        }

        if (bfhlRequest.getAI() == null || bfhlRequest.getAI().trim().isEmpty())
            throw new IllegalArgumentException("Invalid input");

        return askAI(bfhlRequest.getAI());
    }

    private String askAI(String question) {

        RestTemplate rt = new RestTemplate();

        String url =
                "https://generativelanguage.googleapis.com/v1/models/gemini-2.5-flash:generateContent?key=" + apiKey;

        Map<String, Object> body = Map.of(
                "contents", List.of(
                        Map.of("parts", List.of(
                                Map.of("text", "Give a single word answer for: " + question)
                        ))
                )
        );

        try {
            Map<?, ?> response = rt.postForObject(url, body, Map.class);

            if (response != null && response.containsKey("candidates")) {
                List<?> candidates = (List<?>) response.get("candidates");
                if (!candidates.isEmpty()) {
                    Map<?, ?> firstCandidate = (Map<?, ?>) candidates.get(0);
                    Map<?, ?> content = (Map<?, ?>) firstCandidate.get("content");
                    List<?> parts = (List<?>) content.get("parts");
                    Map<?, ?> firstPart = (Map<?, ?>) parts.get(0);

                    String fullAnswer = firstPart.get("text")
                            .toString()
                            .trim()
                            .replaceAll("[^a-zA-Z0-9]", " ")
                            .split("\\s+")[0];

                    return normalizeNumber(fullAnswer);
                }
            }
            throw new RuntimeException("No response from Gemini");

        } catch (Exception e) {
            throw new RuntimeException("AI_SERVICE_ERROR: " + e.getMessage());
        }
    }
    private String normalizeNumber(String value) {
        return switch (value.toLowerCase()) {
            case "zero" -> "0";
            case "one" -> "1";
            case "two" -> "2";
            case "three" -> "3";
            case "four" -> "4";
            case "five" -> "5";
            case "six" -> "6";
            case "seven" -> "7";
            case "eight" -> "8";
            case "nine" -> "9";
            case "ten" -> "10";
            default -> value;
        };
    }



}
