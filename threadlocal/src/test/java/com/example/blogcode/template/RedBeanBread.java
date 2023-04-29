package com.example.blogcode.template;

public class RedBeanBread extends Bread {

    @Override
    public void putExtra() {
        System.out.println("팥을 넣는다.");
    }

    @Override
    public void waitMinutes() {
        System.out.println("3분 기다린다.");
    }
}
