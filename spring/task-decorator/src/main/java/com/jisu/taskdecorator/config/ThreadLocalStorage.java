package com.jisu.taskdecorator.config;

public class ThreadLocalStorage {

  public static final ThreadLocal<String> PARAMETER = ThreadLocal.withInitial(String::new);


  public static void setParameter(String parameter) {
    PARAMETER.set(parameter);
  }

  public static String getParameter() {
    return PARAMETER.get();
  }
}
