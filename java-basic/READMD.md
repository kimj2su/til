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


# 다형성 활용
좋은 프로그램은 제약이 있는 프로그램이다.  
```java
public class Animal {
    public void sound() {
        System.out.println("Animal sound");
    }
}
public class Dog extends Animal {
    @Override
    public void sound() {
        System.out.println("멍멍");
    }
}
public class Cat extends Animal {
    @Override
    public void sound() {
        System.out.println("야옹");
    }
}
```

다음과 같은 Animal을 상속받은 Dog, Cat 클래스가 있다.  
새로운 클래스 추가를 할때 메서드를 오버라이딩 하지 않을 가능성이 있다.  
그렇기 때문에 정상적인 기능이 동작하지 않을 수 있다.  


## 추상 클래스
동물과 같이 부모 클래스는 제공하지만, 실제 생성되면 안되는 클래스를 추상클래스라 한다.  
추상 클래스는 이름 그대로 추상적인 개념을 제공하는 클래스이다. 따라서 실체인 인스턴스가 존재하지 않는다.  
대신에 상속을 목적으로 사용되고, 부모 클래스 역할을 담당한다.  

## 추상 메서드
부모클래스를 상속 받는 자식 클래스가 반드시 구현해야 하는 메서드를 추상 메서드라 한다.

## 순수 추상클래스
추상 메서드만 가진 클래스를 순수 추상 클래스라 한다.  
- 추상 클래스는 인스턴스를 생성할 수 없다.
- 추상 클래스는 상속을 통해서 자식 클래스가 구현하도록 한다.
- 주로 다형성을 위해 사용한다.

## 인터페이스
인터페이스는 앞서 설명한 순수 추상클래스와 같다. 여기에 약간의 편의 기능이 추가된다.  
- 인터페이스의 메서드는 모두 public, abstract이다.
- 메서드에 public, abstract를 생략할 수 있다. 참고로 생략이 권장된다.
- 인터페이스는 다중 구현(다중 상속)을 지원한다.
- 인터페이스에서 멤버 변수는 public, static, final이 모두 포함되어 있다고 간주한다.
- 클래스에서 상속 관계는 UML에서 실선을 사용하지만, 인터페이스는 구현(상속)관계는 점선을 사용한다.

## 인터페이스만 다중 구현을 지원하는 이유
추상클래스는 다중 상속을 하게 되면 어떤걸 호출해야할지를 모르지만  
인터페이스는 어짜피 구현을 해야하기 때문에 구현하는 클래스에서 구현을 해주면 된다.

# 다형성과 설계
## 좋은 객체지향 프로그래밍이란?
### 객체 지향 프로그래밍
객체 지향 프로그래밍은 컴퓨터 프로그램을 명령어의 목록으로 보는 시각에서 벗어나 여러개의 독립된 단위,  
즉 "객체"들의 모임으로 파악하고자 하는것이다. 각각의 객체는 메시지를 주고받고, 데이터를 처리할 수 있다(협력)  
객체 지향 프로그래밍은 프로그램을 유연하고 변경이 용이하게 만들기 때문에 대규모 소프트웨어 개발에 많이 사용된다.

### 유연하고, 변경이 용이하다
- 레고 블럭 조립하듯이
- 키보드, 마우스 갈아 끼우듯이
- 컴퓨터 부품 갈아 끼우듯이
- 컴포넌트를 쉽고 유연하게 변경하면서 개발할 수 있는 방법

객체지향프로그램은 다형성 덕분에 유연하고 변경이 용이하다.

### 다형셩의 실세계 비유
- 실세계와 객체 지향을 1:1로 매칭하기는 어렵다.
- 그래도 실세계의 비유로 이해하기에는 좋다.
- 역할과 구현으로 세상을 구분

#### 운전자 - 자동차
- 운전자는 자동차의 구현과 상관없이 운전할 수 있다.
- 자동차가 바뀌어도 운전자는 변하지 않는다.
- 운전자는 자동차의 인터페이스(바퀴, 핸들, 브레이크)에 의존한다.
- 자동차가 인터페이스를 지킨다면, 어떤 자동차라도 운전할 수 있다.

#### 공연 무대 로미오와 줄리엣 공연
- 배우는 대체 가능하다.
- 로미오와 줄리엣은 역할로 구분한다.

#### 역할과 구현을 분리
- 역할과 구현으로 구분하면 세상이 단순해지고, 유연해지며 변경도 편리해진다.
- 클라이언트는 대상의 역할(인터페이스)만 알면 된다.
- 클라이언트는 구현 대상의 내부 구조를 몰라도 된다.
- 클라이언트는 구현 대상의 내부 구조가 변경되어도 영향을 받지 않는다.
- 클라이언트는 구현 대상 자체를 변경해도 영향을 받지 않는다.

자바 언어의 다형성을 활용
- 역할 = 인터페이스
- 구현 = 인터페이스를 구현한 클래스, 구현 객체
- 객체를 설계할 때 역할과 구현을 명확히 분리
- 객체 설계시 역할(인터페이스)을 먼저 부여하고, 그 역할을 수행하는 구현 객체 만들기

### 다형성의 본질
- 인터페이스를 구현한 구현한 객체 인스턴스를 실행 시점에 유연하게 변경할 수 있다.
- 다형성의 본질을 이해하려면 협력이라는 객체사이의 관계에서 시작해야함
- 클라이언트를 변경하지 않고, 서버의 구현 기능을 유연하게 변경할 수 있다.

#### 좋은 객체 지향 프로그래밍 정리
- 다형성이 가장 중요하다.
- 디자인 패턴 대부분은 다형성을 활용하는 것이다.
- 스프링의 핵심인 제어의 역전(Ioc), 의존관계 주입(DI)은 다형성을 활용해서 구현한다.

## 다향성 - 역할과 구현 
poly.car1
- Driver 운전자는 자동차의 역할에만 의존한다. 구현인 K3, Model3 자동차에 의존하지 않는다.
- Driver 클래스는 Car car 멤버 변수를 가진다. 따라서 Car 인터페이스를 참조한다.
- 인터페이스를 구현한 K3Car, Model3에 의존하지 않고 Car 인터페이스만 의존한다.
- 여기서 설명하는 의존은 클래스 의존 관계를 뜻한다. 클래스 상에서 어떤 클래스를 알고 있는가를 뜻한다. 
- Driver 클래스코드를 보면 Car 인터페이스만 사용하는 것을 확인할 수 있다.
- Car : 자동차의 역할이고 인터페이스이다. K3Car, Model3Car 클래스가 인터페이스를 구혆나다.

## OCP(Open-Closed Principle) - 개방 폐쇄 원칙
좋은 객체 지향 설계 원칙 중 하나로 OCP 원칙이라는 것이 있다.
- Open for extension : 새로운 기능의 추가나 변경 사항이 생겼을때 기존 코드는 확장할 수 있어야 한다.
- Closed for modification : 기존 코드를 변경하지 않고 사용할 수 있어야 한다.

확장에는 열려있고, 변경에는 닫혀 있다는 뜻인데, 쉽게 이야기해서 기존의 코드 수정 없이 새로운 기능을 추가할 수 있다는 의미다.  

### 새로운 차량의 추가
여기서 새로운 차량을 추가해도 Driver의 코드는 전혀 변겨하지 않는다. 운전할 수 있는 차량의 종류가 계속 늘어나도 Car를 사용하는 Driver의 코드는 전혀 변경하지 않는다.  
기능을 확장해도 main() 일부를 제외한 프로그램의 핵심 부분의 코드는 전혀 수정하지 않아도 된다.

### 확장에 열려있다는 의미
Car 인터페이스를 사용해서 새로운 차량을 자유롭게 추가할 수 있다.  
Car 인터페이스를 구현해서 새로운 차량을 만들면 된다.
Car 인터페이스를 사용하는 클라이언트 코드인 Driver도 Car 인터페이스를 통해 새롭게 추가된 차량을 자유롭게 호출할 수 있다.  
이것이 확장에 열려있다는 의미이다.


### 코드 수정은 닫혀있다는 의미
새로운 차를 추가하게 되면 기능이 추가되기 때문에 기존 코드의 수정은 불가피하다. 당연히 어딘가의 코드는 수정해야 한다.

### 변하지 않는 부분
새로운 자동차를 추가할 때 가장 영향을 받는 중요한 클라이언트는 바로 Car의 기능을 사용하는 Driver이다.  
핵심은 Car인터페이스를 사용하는 클라이언트인 Driver의 코드를 수정하지 않아도 된다는 뜻이다.

### 변하는 부분
main()과 같이 새로운 차를 생성하고 Driver에게 필요한 차를 전달해주는 역할은 당연히 코드 수정이 발생한다.  
main()은 전체 프로그램을 설정하고 조율하는 역할을 한다. 이런 부분은 OCP를 지켜도 변경이 필요하다.