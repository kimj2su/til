package com.jisu.testcodewitharchitecture.post.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jisu.testcodewitharchitecture.post.domain.PostUpdate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void 사용자는_게시물을_단건_조회_할_수_있다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/posts/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.content").value("helloworld"))
                .andExpect(jsonPath("$.writer.id").isNumber())
                .andExpect(jsonPath("$.writer.email").value("kimjisu3268@gmail.com"))
                .andExpect(jsonPath("$.writer.nickname").value("jisu3268"));
    }

    @Test
    void 사용자가_존재하지_않는_게시물을_조회할_경우_에러가_난다() throws Exception {
        // given
        // when
        // then
        mockMvc.perform(get("/api/posts/123456789"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Posts에서 ID 123456789를 찾을 수 없습니다."));
    }

    @Test
    void 사용자는_게시물을_수정할_수_있다() throws Exception {
        // given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("foobar")
                .build();

        // when
        // then
        mockMvc.perform(
                        put("/api/posts/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(postUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.content").value("foobar"))
                .andExpect(jsonPath("$.writer.id").isNumber())
                .andExpect(jsonPath("$.writer.email").value("kimjisu3268@gmail.com"))
                .andExpect(jsonPath("$.writer.nickname").value("jisu3268"));
    }
}
