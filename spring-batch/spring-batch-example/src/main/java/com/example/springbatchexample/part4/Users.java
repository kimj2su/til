package com.example.springbatchexample.part4;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    @Enumerated
    private Level level = Level.NOMAL;

    private int totalAmount;

    private LocalDate updatedDate;

    @Builder
    public Users(String username, int totalAmount) {
        this.username = username;
        this.totalAmount = totalAmount;
    }

    public boolean availableLevelUp() {
        return Level.availableLevelUp(this.getLevel(), this.getTotalAmount());
    }

    public Level levelUp() {
        Level nextLevel = Level.getNextLevel(this.getTotalAmount());

        this.level = nextLevel;
        this.updatedDate = LocalDate.now();

        return nextLevel;
    }

    public enum Level {
        VIP(500_000, null),
        GOLD(500_000, VIP),
        SILVER(300_000, GOLD),
        NOMAL(200_000, SILVER);

        private final int nextAmount;
        private final Level nextLevel;
        Level(int nextAmount, Level nextLevel) {
            this.nextAmount = nextAmount;
            this.nextLevel = nextLevel;
        }

        private static boolean availableLevelUp(Level level, int totalAmount) {
            if (Objects.isNull(level)) {
                return false;
            }

            if (Objects.isNull(level.nextLevel)) {
                return false;
            }

            return totalAmount >= level.nextAmount;
        }

        private static Level getNextLevel(int totalAmount) {
            if (totalAmount >= Level.VIP.nextAmount) {
                return VIP;
            }
            if (totalAmount >= Level.GOLD.nextAmount) {
                return GOLD.nextLevel;
            }
            if (totalAmount >= Level.SILVER.nextAmount) {
                return SILVER.nextLevel;
            }
            if (totalAmount >= Level.NOMAL.nextAmount) {
                return NOMAL.nextLevel;
            }

            return NOMAL;
        }
    }

}
