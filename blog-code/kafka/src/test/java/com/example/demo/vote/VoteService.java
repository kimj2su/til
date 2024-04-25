package com.example.demo.vote;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class VoteService {

    public String vote(LocalDateTime voteTime) {
        if (voteTime.getDayOfWeek() == DayOfWeek.THURSDAY) {
            return "You can vote now!";
        }
        return "You can`t vote now!";
    }
}
