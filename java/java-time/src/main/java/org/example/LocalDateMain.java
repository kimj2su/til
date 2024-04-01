package org.example;

import java.time.LocalDate;

public class LocalDateMain {
    public static void main(String[] args) {
        LocalDate nowDate = LocalDate.now();
        LocalDate ofDate = LocalDate.of(2013, 11, 21);
        System.out.println("오늘 날짜=" + nowDate);
        System.out.println("지정 날짜=" + ofDate);

        ofDate.plusDays(10); // 불변 객체이므로 새로운 객체를 생성해야 함
        System.out.println("지정 날짜=" + ofDate);
        ofDate = ofDate.plusDays(10);
        System.out.println("지정 날짜=" + ofDate);
    }
}
