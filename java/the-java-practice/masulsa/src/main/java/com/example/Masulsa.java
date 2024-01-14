package com.example;


public class Masulsa {
    public static void main(String[] args){
        // new ByteBuddy().redefine(Moja.class)
        //         .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
        //         .make().saveIn(new File("/Users/kimjisu/Intellij/til/java/더 자바, 코드를 조작하는 다양한 방법/thejava/build/classes/java/main/"));

        // ClassLoader classLoader = Main.class.getClassLoader();
        // TypePool typePool = TypePool.Default.of(classLoader);
        // new ByteBuddy().redefine(
        //                 typePool.describe("com.example.Moja").resolve(),
        //                 ClassFileLocator.ForClassLoader.of(classLoader)
        //         )
        //         .method(named("pullOut")).intercept(FixedValue.value("Rabbit!"))
        //         .make().saveIn(new File("/Users/kimjisu/Intellij/til/java/더 자바, 코드를 조작하는 다양한 방법/thejava/build/classes/java/main/"));

        System.out.println(new Moja().pullOut());
    }
}