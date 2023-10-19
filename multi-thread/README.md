# 쓰레드의 생성
## Runnable 인터페이스를 구현하는 방법
```java
public class MyThread implements Runnable {
    public void run() {
        // 쓰레드가 실행할 코드
    }
}

    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {
            System.out.println("Hello World!");
        }
    });

    thread.start();
```

## Thread 클래스를 상속하는 방법
```java
public class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello from " + Thread.currentThread().getName());
    }
}
    Thread thread = new MyThread();
    thread.start();
```

# 쓰레드 멈추는법
## Thread.interrupt
```java
public class MyThread extends Thread {
    @Override
    public void run() {
        // while (!Thread.currentThread().isInterrupted()) {
        //     System.out.println("Hello from " + Thread.currentThread().getName());
        // }
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
Thread thread = new MyThread();
thread.start();
Thread.sleep(1000);
thread.interrupt();
```
## Thread.setDeamon
데몬 쓰레드는 쓰레드가 실행되는 동안만 실행되는 쓰레드이다.
그래서 메인 쓰레드의 종료를 막지 않는다.
```java
public class MyThread extends Thread {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Hello from " + Thread.currentThread().getName());
        }
    }
}
Thread thread = new MyThread();
thread.setDaemon(true);
thread.start();
Thread.sleep(1000);
thread.interrupt();
```

# 쓰레드 연결
## Thread.join
```java
 List<FactorialThread> threads = new ArrayList<>();

for (long inputNumber : inputNumbers) {
    threads.add(new FactorialThread(inputNumber));
}

for (Thread t : threads) {
    t.start();
}

```
위의 코드는 메인 메서드를 기다리지 않고 각자 수행한다.
```java
 List<FactorialThread> threads = new ArrayList<>();

for (long inputNumber : inputNumbers) {
    threads.add(new FactorialThread(inputNumber));
}

for (Thread t : threads) {
    t.start();
}

for (Thread t : threads) {
    t.join();
}
```
thread.join() 을 통해 메서드를 연결하고 메인 메서드가 기다리게 한다.

# 스레드간 데이터 공유
스택은 메서드가 실행되는 메모리 영역이다.  
함수에 인수를 입력할 때마다 스택에 입력되고 모든 로컬 변수 또한 스택 영역에 저장된다.  
힙은 공유되고 스레드가 접근할 수 있는 메모리 영역이다.
스택은 스레드마다 독립적으로 생성된다.  
객체는 항상 힙영역에 할당된다.

# 병행성 문제와 솔루션

## 임계 영역과 동기화
### Synchronized
```java
public synchronized void incrementCount() {
    count = count + 1;
}
public synchronized void decrementCount() {
        count = count - 1;
}

```
위 방법은 메서드 전체를 임계 영역으로 만든다.
Thread A가 incrementCount()를 실행하면 Thread B는 decrementCount()를 실행할 수 없다.  

### synchronized 블록
```java
public void incrementCount() {
    synchronized (this) {
        count = count + 1;
    }
}

public void decrementCount() {
    synchronized (this) {
        count = count - 1;
    }
}
```
위 방법은 메서드 전체가 아닌 특정 블록을 임계 영역으로 만든다.
Thread A가 incrementCount()를 실행하면 Thread B는 decrementCount()를 실행할 수 있다.  

# 경쟁 상태 및 데이터 경쟁
종종 컴파일러와 CPU가 성능 최적화와 하드웨어 활용을 위해 비순차적으로 명령을 처리하는 경우가 있다.  
이 비순차적 명령어 처리는 유용한 기능이다.  
컴파일러와 CPU가 이렇게 처리하지 않으면 프로그램 처리 속도는 매우 느려질 것이다.  

```java
public void incrementCount() {
    x++;
    y++;
}

public void incrementCount() {
        y++;
        x++;
}

```
위와 같은 의존성이 없는 코드를 가진 이러한 함수에서는 CPU나 컴파일러 관점에서
해당 메서드들의 논리가 동일하므로 같은 메서드로 판단한다.  
싱글 쓰레드에서는 문제가 없으나 멀티쓰레드에서는 다른 코어에서 실행된느 쓰레드를 인지하지 못하고  
동일 변수를 읽고 특정 처리 순서에 의존한다.

## 경쟁을 피하는 방법
### synchronized
읽기, 쓰기, 혹은 공유 변수로부터 보호할 수 있다.  

### volatile
volatile 키워드는 변수를 읽고 쓰는 동작을 최적화하지 않는다.
잠금 오버헤드를 줄이고 처리 순서를 보장한다.  
공유 변수에 volatile를 선언하면 변수 접급전 코드가 접근 명령을 수행하기전에  
실행되도록하고 변수 접근후의 코드가 실행되도록한다.