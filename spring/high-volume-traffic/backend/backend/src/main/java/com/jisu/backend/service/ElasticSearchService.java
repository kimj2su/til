package com.jisu.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

  private final WebClient webClient;

  public Mono<String> articleSearch(String index, String query) {
    return webClient.get()
        .uri("{index}/_search?q={query}", index, query)
        .retrieve()
        .bodyToMono(String.class);
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
}
