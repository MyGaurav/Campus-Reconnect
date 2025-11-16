package com.campusreconnect.lost_and_found_ai.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MatchService {

    private final WebClient webClient = WebClient.create("http://localhost:5001");

    public List<Map<String, Object>> match(String lostText, List<String> foundTexts) {

        Map<String, Object> request = new HashMap<>();
        request.put("source_text", lostText);
        request.put("candidate_texts", foundTexts);

        Map<String, Object> response = webClient.post()
                .uri("/match")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Map.class)
                .block();

        return (List<Map<String, Object>>) response.get("matches");
    }
}

