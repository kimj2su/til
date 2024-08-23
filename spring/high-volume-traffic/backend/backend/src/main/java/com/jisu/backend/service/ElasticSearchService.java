package com.jisu.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ElasticSearchService {

  private final WebClient webClient;

  public Mono<String> search(String index, String query) {
    return webClient.get()
        .uri("{index}/_search?q={query}", index, query)
        .retrieve()
        .bodyToMono(String.class);
  }

  public Mono<String> index(String index, String document) {
    return webClient.post()
        .uri(index)
        .bodyValue(document)
        .retrieve()
        .bodyToMono(String.class);
  }
}
