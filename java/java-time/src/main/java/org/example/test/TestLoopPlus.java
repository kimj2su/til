package org.example.test;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

public class TestLoopPlus {
    public static void main(String[] args) {
        LocalDate date = LocalDate.of(2024, 01, 01);
        for (int i = 0; i < 5; i++) {
            // LocalDate nextDate = date.plus(2 * i, ChronoUnit.WEEKS);
            LocalDate nextDate = date.plusWeeks(2 * i);
            System.out.println("날짜 " + (i + 1) + ":" + nextDate);
        }
    }
}
