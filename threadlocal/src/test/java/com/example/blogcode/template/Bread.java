package com.example.blogcode.template;

public abstract class Bread {
    public void makeBread() {
        putDough();
        putExtra();
        putDough();
        waitMinutes();
    }

    public void putDough() {
        System.out.println("반죽을 넣는다.");
    }

    public abstract void putExtra();

    public abstract void waitMinutes();
}
