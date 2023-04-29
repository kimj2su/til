package com.example.blogcode.strategy.code.strategy;

public class FootballManager {
    public void execute() {
        FootballPlayer footballPlayer = new FootballPlayer();

        Defender defender = new Defender();
        Midfielder midfielder = new Midfielder();
        Forward forward = new Forward();

        footballPlayer.beSelected(defender);
        footballPlayer.beSelected(midfielder);
        footballPlayer.beSelected(forward);
    }
}
