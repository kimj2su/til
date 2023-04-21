package com.example.blogcode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Locker {
    private Integer lockerNumberSequence = 0;
    private ThreadLocal<Integer> lockerNumber = new ThreadLocal<>();
    private ThreadLocal<Student> lockerOwner = new ThreadLocal<>();
//    private Student lockerOwner;

    public void assignLocker() {
//        ++lockerNumberSequence;
//        log.info("저장 lockerNumber={} -> lockerOwner={}", lockerNumberSequence, lockerOwner.getStudentName());
//        sleep(1000);
//        log.info("조회 lockerNumber={} -> lockerOwner={}", lockerNumberSequence, lockerOwner.getStudentName());
        ++lockerNumberSequence;
        lockerNumber.set(lockerNumberSequence);
        log.info("저장 lockerNumber={} -> lockerOwner={}", lockerNumber.get(), lockerOwner.get().getStudentName());
        sleep(1000);
        log.info("조회 lockerNumber={} -> lockerOwner={}", lockerNumber.get(), lockerOwner.get().getStudentName());
    }

    public void addStudent(String name) {
        lockerOwner.set(new Student(name));
    }
//    public void addStudent(String name) {
//        lockerOwner = new Student(name);
//    }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
