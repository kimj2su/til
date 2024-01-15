package com.example;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE}) // TYPE: class, interface, enum
@Retention(RetentionPolicy.SOURCE)
public @interface Magic {
}
