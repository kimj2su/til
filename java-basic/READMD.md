김영한님의 실전 자바 - 기본편을 보고 정리한 내용입니다.  
[강의 링크](https://www.inflearn.com/course/%EA%B9%80%EC%98%81%ED%95%9C%EC%9D%98-%EC%8B%A4%EC%A0%84-%EC%9E%90%EB%B0%94-%EA%B8%B0%EB%B3%B8%ED%8E%B8/dashboard)
# 다형성

부모는 자식타입의 객체를 가질 수 있다.  
하지만 자식 객체는 부모타입을 가질 수 없다.  
이를 해결하기 위해 다운 캐스팅을 한다.  

## 캐스팅의 종류
1. 일시적 다운 캐스팅
```java
Parent poly = new Child();
// 일시적 다운 캐스팅 - 해당 메서드를 호출하는 순간만 다운 캐스팅
((Child) poly).childMethod();
```
2. upcasting vs downcasting
```java
Child child = new Child();
Parent parent1 = (Parent) child; // 업캐스팅은 생략가능, 생략 권장
Parent parent2 = child; // 업캐스팅 생략

parent1.parentMethod();
parent2.parentMethod();
```

## 다운캐스팅과 주의점
```java
Parent parent1 = new Child();
Child child1 = (Child) parent1;
child1.childMethod();// 문제 없음

Parent parent2 = new Parent();
Child child2 = (Child) parent2; // ClassCastException 발생
child2.childMethod(); // 실행 불가
```
부모 객체로 생성하면 메모리 상에서 자식 타입은 전혀 존재하지 않는다. 생성결과를 parent2에 담아두기 때문에 문제가 발생하지 않는다.  
하지만 parent2에 담아둔 객체를 child2에 담으려고 하면 메모리상에 Child 자체가 존재하지 않기 때문에 ClassCastException이 발생한다.  
그래서 child2.childMethod()를 실행 자체가 안된다.

### 업캐스팅이 안정하고 다운캐스팅이 위험한 이유
업캐스팅의 경우 객체를 생성하면 해당 타입의 상위 부모 타입은 모두 메모리에 생성된다.  
따라서 위로만 타입을 변경하는 업캐스팅은 메모리에 존재하기 때문에 항상 안전하다.  
따라서 캐스팅을 생략할 수 있다.  
하지만 다운 캐스팅의 경우 자식 객체는 메모리에 존재조차 하지 않기 때문에 문제가 발생한다.

## instanceof
```java
Parent parent1 = new Parent();
call(parent1);

Parent parent2 = new Child();
call(parent2);

private static void call(Parent parent) {
    if (parent instanceof Child) {
        System.out.println("Child 인스턴스 맞음");
        Child child = (Child) parent;
        child.childMethod();
    } else {
        System.out.println("Child 객체가 아닙니다.");
    }
}
```
instanceof는 해당 객체가 해당 클래스의 인스턴스인지 확인하는 연산자이다.

## Pattern matching for instanceof
```java
private static void call(Parent parent) {
    //Child 인스턴스인 경우 childMethod() 실행
    if (parent instanceof Child child) {
        System.out.println("Child 인스턴스 맞음");
        child.childMethod();
    } else {
        System.out.println("Child 객체가 아닙니다.");
    }
}
```
instanceof를 사용할 때 다운캐스팅을 한번에 할 수 있다.

## 다형성과 메서드 오버라이딩
```java
//자식 변수가 자식 인스턴스 참조
Child child = new Child();
System.out.println("child -> child");
System.out.println(child.value);
child.method();

//부모 변수가 부모 인스턴스 참조
Parent parent = new Parent();
System.out.println("parent -> parent");
System.out.println(parent.value);
parent.method();

// 부모 변수가 자식 인스턴스 참조(다형적 참조)
Parent poly = new Child();
System.out.println("parent -> child");
System.out.println(poly.value);
poly.method();
```
poly 변수는 Parent 타입이다. 따라서 poly.value, poly.method()를 호출하면 부모타입에서 기능을 찾아서 실행한다.  
- poly.value: 부모타입에 value값을 읽는다.
- poly.method(): 오버라이딩된 메서드는 항상 우선권을 가져 자식 메서드가 호출된다.