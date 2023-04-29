package com.example.blogcode.strategy.code.strategy;

import com.example.blogcode.strategy.code.Strategy;

public class Forward implements Strategy {
    @Override
    public void choosePosition() {
        System.out.println("공격수로 선발되었습니다.");
    }
}
