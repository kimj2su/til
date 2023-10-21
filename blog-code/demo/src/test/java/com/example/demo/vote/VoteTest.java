package com.example.demo.vote;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("투표 관련 기능")
public class VoteTest {

    @Test
    @DisplayName("투표 가능한 시간이면 투표한다.")
    void test() {
        VoteService voteService = new VoteService();
        String response = voteService.vote(LocalDateTime.of(2023, 11, 9, 0, 0));
        assertThat(response).isEqualTo("You can vote now!");
    }
}
