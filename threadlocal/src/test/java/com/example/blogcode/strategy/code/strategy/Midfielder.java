package com.example.blogcode.strategy.code.strategy;

import com.example.blogcode.strategy.code.Strategy;

public class Midfielder implements Strategy {
    @Override
    public void choosePosition() {
        System.out.println("미드필더로 선발되었습니다.");
    }
}
