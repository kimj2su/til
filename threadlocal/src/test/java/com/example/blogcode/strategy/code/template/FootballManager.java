package com.example.blogcode.strategy.code.template;

import com.example.blogcode.strategy.code.Strategy;

public class FootballManager {
    public void execute() {
        FootballPlayer footballPlayer = new FootballPlayer();

//        footballPlayer.beSelected(new Strategy() {
//            @Override
//            public void choosePosition() {
//                System.out.println("수비수로 선발되었습니다.");
//            }
//        });
//
//        footballPlayer.beSelected(new Strategy() {
//            @Override
//            public void choosePosition() {
//                System.out.println("미드필더로 선발되었습니다.");
//            }
//        });
//
//        footballPlayer.beSelected(new Strategy() {
//            @Override
//            public void choosePosition() {
//                System.out.println("공격수로 선발되었습니다.");
//            }
//        });

        footballPlayer.beSelected("수비수");
        footballPlayer.beSelected("미드필더");
        footballPlayer.beSelected("공격수");
    }
}
