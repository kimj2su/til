package com.example.blogcode.strategy.code.strategy;

import com.example.blogcode.strategy.code.Strategy;

public class FootballPlayer {
    public void beSelected(Strategy strategy) {
        System.out.println("감독이 포지션을 선택합니다.");
        strategy.choosePosition();
        System.out.println("경기를 뜁니다.");
    }
}
