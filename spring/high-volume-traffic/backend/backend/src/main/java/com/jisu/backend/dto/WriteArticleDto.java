package com.jisu.backend.dto;

public record WriteArticleDto(
        Long bordId,
        String title,
        String content
) {
}
