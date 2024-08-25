package com.jisu.backend.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

  private final WebClient webClient;
  private final ObjectMapper objectMapper;

  public Mono<List<Long>> articleSearch(String keyword) {
    String query = String.format(
        "{\"_source\": false, \"query\": {\"match\": {\"content\": \"%s\"}}, \"fields\": [\"_id\"], \"size\": 10}",
        keyword);
    return webClient.post()
        .uri("/article/_search")
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .bodyValue(query)
        .retrieve()
        .bodyToMono(String.class)
        .flatMap(this::extractIds);
  }

  public Mono<String> indexArticleDocument(String id, String document) {
    return webClient.post()
        .uri("/article/_doc/{id}", id)
        .header("Content-Type", "application/json")
        .header("Accept", "application/json")
        .bodyValue(document)
        .retrieve()
        .bodyToMono(String.class);
  }

  private Mono<List<Long>> extractIds(String responseBody) {
    List<Long> ids = new ArrayList<>();
    try {
      JsonNode hits = objectMapper.readTree(responseBody).path("hits").path("hits");
      hits.forEach(hit -> ids.add(hit.path("_id").asLong()));
    } catch (IOException e) {
      return Mono.error(e);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
    return Mono.just(ids);
  }
}
