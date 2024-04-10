package org.example.test;

import java.time.LocalDateTime;

public class TestPlus {
    public static void main(String[] args) {
        LocalDateTime dt = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime futureDateTime = dt.plusYears(1)
                .plusMonths(2)
                .plusDays(3)
                .plusHours(4);
        System.out.println("futureDateTime = " + futureDateTime);

    }
}
