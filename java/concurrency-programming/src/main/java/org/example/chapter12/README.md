# 비동기프로그래밍 - CompletableFuture

#  동기 vs 비동기 & Blocking vs Non-Blocking
## 동기(Synchronous)
- 동기는 작업이 순차적으로 실행되며 한 작업의 시작과 완료가 다음 작업의 시작과 완료와 밀접하게 연결된 방식을 의미한다.  
- 하나의 작업이 실행 중인 동안 다른 작업은 대기해야 하며 작업의 결과를 기다린 후에 다음 작업이 진행된다.
- 작업은 한번에 하나씩 진행해야 하며 작업을 건너 뛰거나 빠뜨릴 수 없다.

## Blocking
- 블록킹은 동기 작업에서 나타나는 현상으로 작업이 완료될 때 까지 실행 흐름을 멈추고 대기하는 상태이다.
- 파일을 읽거나 네트워크에서 데이터를 받아오는 I/O 작업이 블록킹 작업에 해당하는데 해당 작업이 완료 될 때까지 다른 작업은 차단되고 대기 상태에 놓인다.
- 블록킹 작업은 주로 작업이 완료 될 때까지 결과를 기다려야 하는 경우에 사용되며 대부분 동기 처리에서 나타난다.


## 비동기(Asynchronous)
- 작업이 순차적으로 실행되지 않고 각 작업이 다른 작업의 완료를 기다리지 않고 독립적으로 실행되는 방식을 의미한다.
- 한 작업이 실행된 후에도 다음 작업이 실행 될 수 있고 작업의 결과에 관심이 없고 기다리지 않고 다른 작업을 한다.
- I/O 작업과 같이 시간이 오래 걸리는 작업을 다룰 때 유용하며 다수의 작업을 동시헤 처리하거나 빠른 응답 시간을 보장해야 하는 경우에 활용 된다.
- Fire and Forget 방식으로 작업을 시작하고 그 결과에 대해서 관심을 갖지 않는다.

## Non-Blocking
- 비동기 작업에서 나타나는 현상으로 블록킹 되지 않고 실행 흐름이 지속되는 특성을 나타낸다.
- 특정 작업이 진행 중일 때에도 다른 작업이 계속 실행되며 작업이 완료 되지 않았더라도 대기하지 않고 다음 작업을 처리하는 방식을 의미한다.
- 논 블록킹 작업은 다른 작업들과 동시에 진행될 수 있어서 전체 시스템의 응답성을 향상시킬수 있다.

# 함수 관점에서 동기 & 비동기

함수를 호출한자 (Caller)와 호출된 함수를 수행하는 주체(Callee)가 동일한 스레드면 동기, 서로 다른 스레드이면 비동기 관계가 형성된다고 한다.  


# CompletableFuture 개요
- 자바 8에서 추가된 CompletableFuture는 Future를 확장한 인터페이스로 비동기 프로그래밍을 위한 기능을 제공한다.
- CompletableFuture는 값과 상태를 설정하여 명시적으로 완료 시킬 수 있는 Future로서 코드의 가독성을 높이고 비동기 작업의 조합을 간단하게 처리할 수 있다.
- 자바에서 비동기 및 병렬 프로그래밍의 능력을 향상시키는데 중요한 역할을 한다.

| 특징 | CompletableFuture | Future                  |
|----|---|-------------------------|
| 비동기 및 병렬 처리 | 작업을 병렬로 실행하고 여러 작업을 동시에 처리할 수 있도록 해준다. | 지원하지만 병렬처리를 위해 추가 작업 필요 |
| 콜백 지원 | 콜백을 등록하여 작업이 완료되었을 때 결과를 처리할 수 있다. | 지원하지 않는다.               |
| 조합 및 체인 | 여러 CompletableFuture를 조합하거나 체인으로 연결하여 복잡한 비동기 작업을 처리할 수 있다.| 여러 Future를 별도로 추적 및 조합해야 한다.|
| 예외 처리 | 단계적 예외 처리를 지원하며 작업 중 발생한 예외를 적절하게 처리할 수 있다. | 예외 처리가 번거롭고 구조적이지 않다. | 
| 동기 및 비동기 메서드 | 작업을 동기적 / 비동기적으로 실행할 수 있는 다양한 메서드를 제공한다. | 주도 동기 메서드 get() 사용한다.|
| 명시적 완료 | 명시적으로 값을 저장하고 완료할 수 있다. | 미지원|
| 정상 & 오류 상태 구분 | 지원( 정상과 오류를 구분해서 처리) | 미지언 ( 정상이든 오류든 무조건 완료 상태) |
| 재사용 | 생성한 CompletableFuture은 계속 사용이 가능하다 | 한 번 사용 후 재 사용 불가능 |
| 다수 작업 처리 | Completablefuture의 다중 처리 메서드를 활용할 수 있다. |  다중 Future 작업을 위한 처리 로직 필요|

# CompletableFuture API 구조
- CompletableFuture 는 비동기 작업과 함수형 프로그래밍의 콜백 패턴을 조합한 Future라 할 수 있으며 2가지 유형의 API로 구분할 수 있다.
- CompletableFuture는 Future와 CompletionStage를 구현한 클래스로서 Futrue + CompletionStage라고 정의할 수 있다.

## CompletionStage
- CompletionStage는 비동기 작업을 위한 콜백 함수 API를 제공하며 어떤 작업이 완료된 후에 실행되어야 하는 후속 작업들을 정의하는 데 사용된다.
- CompletionStage는 "이 작업이 끝나면, 그 다음에 이 작업을 해라" 와 같은 연쇄적인 비동기 작업을 표현하기 위한 도구로 사용되며 한 작업의 완료는 자동으로 다음 작업의 시작을 트리거할 수 있어 여러 비동기 작업들을 연결하여 실행할 수 있게 해준다. 


## CompletableFuture API 는 비동기 작업을 시작, 실행, 완료 등의 API 제공
### 비동기 작업 시작
- CompletableFuture<T> CompletableFuture.supplyAsync(Supplier<T> supplier)
- CompletableFuture<Void> CompletableFuture.runAsync(Runnable runnable)

### 비동기 작업 완료 설정
- boolean CompletableFuture.complete()
- boolean CompletableFuture.completeExceptionally()
- CompletableFuture<T> CompletableFuture.completedFuture(T value)

### 다중 비동기 작업 조합
- CompletableFuture<Object> CompletableFuture.allOf(CompletableFuture<?>... cfs)
- CompletableFuture<Object> CompletableFuture.anyOf(CompletableFuture<?>... cfs)

### 비동기 작업 대기
- V join 

### 비동기 작업 유형
| 메서드           | 인수(함수형 인터페이스) | 인수 추상 메서드| 개념                            |
|---------------|---|-------------------------|-------------------------------|
| supplyAsync() | Supplier<T> | T get() | 결과를 반환하는 비동기 작업을 수행한다.        |
| runAsync()    | Runnable | void run() | 결과를 반환하지 않는 비동기 작업을 수행한다.     |
| thenApply()   | Function<T,R> | R apply(T t) | 이전의 작업의 결과를 가공하고 새로운 작업 수행한다. |
| thenAccept()  | Consumer<T> | void accept(T t) | 이전 작업의 결과를 소비하고 새로운 작업을 수행한다. |
| thenRun()     | Runnable | void run() | 이전 작업의 결과를 사용하지 않고 새로운 작업을 수행한다. |
| thenCombine() | BiFunction<T,U,R> | R apply(T t, U u) | 두 작업의 결과를 조합하여 새로운 작업을 수행한다. |
| thenCompose() | Function<T,CompletionStage<U>> | CompletionStage<U> apply(T t) | 이전 작업의 결과를 가지고 새로운 작업을 수행한다. |
| allOf()       | CompletableFuture<?>... |  | 모든 작업이 완료되면 새로운 작업을 수행한다. |
| anyOf()       | CompletableFuture<?>... |  | 하나의 작업이라도 완료되면 새로운 작업을 수행한다. |

# 비동기 작업 시작 
- CompletableFuture는 비동기 작업을 생성하고 실행하는 시작 메서드로 supplyAsync()와 runAsync() 를 제공한다.
- CompletableFuture는 비동기 작업을 실행하기 위해 내부적으로 ForkJoinPool.commonPool()의 스레드 풀을 사용하며 선택적으로 ThreadPoolExecutor를 사용할 수 있다.

## supplyAsync(Supplier s)
- 개념 : 정적 메서드로서 비동기 작업을 시작하고 작업 수행 후 결과를 반환한다.
- 인수 값 : Supplier<T> 함수를 인수로 받아 작업 결과를 반환한다.
- 반환 값 : 새로운 CompletableFuture<T> 객체를 반환하며 CompletableFuture에 비동기 작업의 결과를 저장한다.
- 실행 객체 : AsyncSupply

```java
CompletableFuture<T> future = CompletableFuture.supplyAsync(() -> {
    return T;
});
```
비동기 작업을 시작하고 작업 수행 후 결과 T를 반환 한다.

### supplyAsync() 흐름
1. CompletableFuture의 supplyAsync(Supplier s) 메서드를 호출한다.
2. 내부적으로 AsyncSupply 객체를 생성한다. 이 객체가 비동기 작업을 수행한다.
3. 이 AsyncSupply 객체는 Supplier는 값을 만들어 반환하고 CompletableFuture는 값을 저장되는 객체이다. 

AsyncSupply 에서 수행한 작업 결과는 CompletableFuture.supplyAsync()에서 생성된 CompletableFuture #1에 저장된다.
