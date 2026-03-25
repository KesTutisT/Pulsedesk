package com.pulsedesk.pulsedesk.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.nio.charset.StandardCharsets;

import java.util.List;

@Service
public class HuggingFaceService {
    @Value("${huggingface.api.token}")
    private String apiToken;

    @Value("${huggingface.api.url}")
    private String apiUrl;

    public List<Map<String, Object>> classify(String text, List<String> labels) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiToken);
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        headers.add(HttpHeaders.ACCEPT_CHARSET, StandardCharsets.UTF_8.name());

        Map<String, Object> parameters = Map.of("candidate_labels", labels);
        Map<String, Object> body = Map.of("inputs", text, "parameters", parameters);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<List> response = restTemplate.postForEntity(apiUrl, request, List.class);
        System.out.println("Hugging Face response: " + response.getBody());
        return response.getBody();
    }

}
