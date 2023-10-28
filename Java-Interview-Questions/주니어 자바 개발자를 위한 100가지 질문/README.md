# 기초
## 1. JDK와 JRE의 차이점은 무엇입니가?
JDK(Java Development Kit)는 자바 개발 도구로 자바 프로그램을 개발하기 위한 도구들을 제공한다.  
JRE(Java Runtime Environment)는 자바 실행 환경으로 자바 프로그램을 실행하기 위한 도구들을 제공한다.  
JDK는 JRE를 포함한다.

## 2. ==와 equals()의 차이점은 무엇입니까?
==는 비교하고자 하는 대상의 주소값을 비교한다.
equals()는 비교하고자 하는 대상의 주소값이 아닌 내용을 비교한다.
String의 경우 equals()를 오버라이딩하여 내용을 비교하도록 되어있다.