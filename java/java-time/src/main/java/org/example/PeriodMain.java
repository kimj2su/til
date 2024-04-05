package org.example;

import java.time.LocalDate;
import java.time.Period;

public class PeriodMain {

    public static void main(String[] args) {
        Period period = Period.ofDays(10);
        System.out.println("period = " + period);

        LocalDate currentDate = LocalDate.of(2030, 1, 1);
        System.out.println("currentDate = " + currentDate);
        LocalDate plusDate = currentDate.plus(period);
        System.out.println("plusDate = " + plusDate);

        LocalDate startDate = LocalDate.of(2023, 1, 1);
        LocalDate endDate = LocalDate.of(2023, 4, 2);
        Period between = Period.between(startDate, endDate);
        System.out.println("between = " + between);
        System.out.println("기간: " + between.getYears() + "년 " + between.getMonths() + "개월 " + between.getDays() + "일");
    }
}
