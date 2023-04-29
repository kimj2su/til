package com.example.blogcode.strategy.code.template;

import com.example.blogcode.strategy.code.Strategy;

public class FootballPlayer {
    public void beSelected(Strategy strategy) {
        System.out.println("감독이 포지션을 선택합니다.");
        strategy.choosePosition();
        System.out.println("경기를 뜁니다.");
    }

    public void beSelected(String position) {
        System.out.println("감독이 포지션을 선택합니다.");
        strategy(position).choosePosition();
        System.out.println("경기를 뜁니다.");
    }

    private Strategy strategy(String position) {
        return new Strategy() {
            @Override
            public void choosePosition() {
                System.out.println(position + "로 선발되었습니다.");
            }
        };
    }
}
