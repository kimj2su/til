package com.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

// @MyAnnotation("jisu2")
public class Main {
    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchFieldException {
        // Arrays.stream(Main.class.getAnnotations())
        //     .forEach(System.out::println);
        //
        // Arrays.stream(Main.class.getAnnotations())
        //         .forEach(annotation -> {
        //             if (annotation instanceof MyAnnotation) {
        //                 MyAnnotation myAnnotation = (MyAnnotation) annotation;
        //                 System.out.println(myAnnotation.name());
        //                 System.out.println(myAnnotation.value());
        //                 System.out.println(myAnnotation.number());
        //             }
        //         });
        // Arrays.stream(Main.class.getDeclaredAnnotations())
        //         .forEach(f -> {
        //             Arrays.stream(f.annotationType().getAnnotations()).forEach(System.out::println);
        //         });
        // Arrays.stream(Main.class.getDeclaredFields())
        //         .forEach(f -> {
        //             Arrays.stream(f.getAnnotations()).forEach(System.out::println);
        //         });

        Class<?> bookClass = Class.forName("com.example.Book");
        Constructor<?> constructor = bookClass.getConstructor();
        Book book = (Book) constructor.newInstance();
        System.out.println(book);

        // static field access
        Field a = Book.class.getDeclaredField("A");
        System.out.println(a.get(null));
        a.set(null, "AAAAA");
        System.out.println(a.get(null));

        // private field access
        Field b = Book.class.getDeclaredField("B");
        b.setAccessible(true);
        System.out.println(b.get(book));

        // method access
        Method c = Book.class.getDeclaredMethod("c");
        c.setAccessible(true);
        c.invoke(book);

        // public method access
        Method sum = Book.class.getDeclaredMethod("sum", int.class, int.class);
        int invoke = (int) sum.invoke(book, 1, 2);
        System.out.println(invoke);
    }
}