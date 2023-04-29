package com.example.blogcode.strategy;

import com.example.blogcode.strategy.code.strategy.FootballManager;
import org.junit.jupiter.api.Test;

public class StrategyTest {

    @Test
    void strategy() {
        FootballManager footballManager = new FootballManager();
        footballManager.execute();
    }

    @Test
    void templateCallback() {
        FootballManager footballManager = new FootballManager();
        footballManager.execute();
    }
}
