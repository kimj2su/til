package org.example;

import java.time.LocalTime;

public class LocalTimeMain {
    public static void main(String[] args) {
        LocalTime nowTime = LocalTime.now();
        LocalTime ofTime = LocalTime.of(13, 45, 20);

        System.out.println("현재 시간=" + nowTime);
        System.out.println("지정 시간=" + ofTime);

        ofTime.plusHours(10); // 불변 객체이므로 새로운 객체를 생성해야 함
        System.out.println("지정 시간=" + ofTime);
        ofTime = ofTime.plusHours(10);
        System.out.println("지정 시간=" + ofTime);
    }
}
