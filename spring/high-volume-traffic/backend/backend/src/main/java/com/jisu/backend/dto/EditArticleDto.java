package com.jisu.backend.dto;

import java.util.Optional;

public record EditArticleDto(
    Optional<String> title,
    Optional<String> content
) {

}
