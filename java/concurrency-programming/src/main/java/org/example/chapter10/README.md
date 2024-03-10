# Executor
제출된 Runnable 작업을 실행하는 객체이다.  
Executor 인터페이스는 각 작업의 실행(실행방법, 스레드 사용, 스케줄링 등의 세부 사항)과 작업의 제출을 분리하는 방법을 제공한다.  

하지만 다음과 같은 기능등은 제공하지 않는다.
- 작업 관리의 부족 : 작업의 기본적인 제출과 실행만 다룸. -> 결과 반환 등 추가적인 기능은 제공하지 않음.
- 작업 결과 반환의 어려움 : 작업이 완료 되었을때 결과를 반환받는 기능이 없음.
- 스레드 종료의 어려움 : 스레드를 명시적으로 종료하거나 정리하는 기능이 없음.
- 작업 간 상호 작용의 어려움 : 작업을 비동기적으로 실행할 경우 작업간의 상호작용이 어려움.

# ExecutorService
위와 같은 문제점을 해결하기 위해 ExecutorService 인터페이스가 제공된다.  
ExecutorService 인터페이스는 Executor 인터페이스를 상속받아 Executor 인터페이스의 기능을 포함하고 있으며, 추가적으로 작업 결과를 반환하거나 작업의 상태를 관리하는 기능을 제공한다.

# Runnable, Callable
- Runnable과 Callable은 작업을 정의하는 인터페이스이다.  
- Runnable은 작업을 정의하는 인터페이스로, 작업을 실행하는 메서드는 반환값이 없다.  (run() 메서드)  
- Callable은 작업을 정의하는 인터페이스로,  예외 처리해야하는 작업을 실행하는 메서드는 반환값이 있다. (call() 메서드, Future 인터페이스를 통해 반환값을 받을 수 있다.)  

# Future, Callback
- 자바에서 비동기 프로그래밍 패턴이다.
- Future는 인터페이스, Callback은 패턴이다.

## Future
- 비동기 작업의 결과를 나타내는 객체
- 비동기 작업이 완료 될때까지 블로킹
- 작업이 완료되면 결과를 얻을 수 있다.
- 자바 8부터 CompletableFuture 클래스를 통해 비동기 작업을 더 쉽게 처리할 수 있다.

-> 비동기 작업에서 스레드 간 결과를 받을 방법이 필요할때 사용

### FutureTask
- Future 인터페이스를 구현한 클래스
- 자바에서 내부적으로 사용하므로 직접 활용할 일은 거의 없음.
- 7개의 상태 값이 있다.
  - NEW : 작업이 아직 시작되지 않은 상태 -> NEW로 시작함.
  - COMPLETING : 작업이 완료되고 결과를 설정하는 중
  - NORMAL : 작업이 정상적으로 완료된 상태, 결과까지 FUTURE에 설정됨.
  - EXCEPTIONAL : 작업이 예외를 던지며 완료된 상태, 예외까지 FUTURE에 설정됨.
  - CANCELLED : 작업이 취소된 상태
  - INTERRUPTING : 작업이 취소되고 있는 상태
  - INTERRUPTED : 작업이 취소되고 완료된 상태

## Callback
- 비동기 작업이 완료되었을때 수행할 동작을 정의한 인터페이스 또는 클래스
- 블로킹 되지 않고 비동기 작업이 완료되면 호출
- 콜백 메서드를 통한 작업 결과를 처리
- 비동기 작업의 완료 후 동작을 정의할 때 주로 사용됨

-> 비동기 작업은 스레드 간 실행의 흐름이 독립적이기 떄문에 비동기 작업의 완료 시점에 결과를 얻을 수 있어야함으로 사용

# ExecutorService

## 스레드 풀 실행 및 관리

### execute() vs submit()  
| 기능       | execute()               | submit() |
|----------|-------------------------|---|
| 작업 유형    | Runnable 작업을 스레드 풀에서 실행 | Runnable 또는 Callable 작업을 스레드 풀에서 실행 |
| 작업 완료 대기 | 작업을 수행하고 완료되기를 기다리지 않음  | 결과를 기다릴 수 있음|
| 결과 반환    | 결과를 반환하지 않음             | 결과를 Future로 반환하여 추후에 결과를 가져올 수 있음|
| 반환 값     | void                    | Future<T>|

## 스레드 풀 중단 및 종료
- shutdown() 
  - 현재 진행중인 작업을 완료한 뒤 스레드 풀을 종료
  - 실행중인 스레드를 강제로 인터럽트 하지 않기 때문에 예외 처리를 할 필요가 없다.
- shutdownNow()
  - 이전에 제출된 작업도 취소하고 현재 실행중인 작업도 중단하려고 시도한다.
  - 작업 대기 중이던 목록을 반환한다.
  - 작업을 종료하기 이해서는 isInterrupted() 나 sleep() 같은 인터럽트 관련 API를 사용해야한다.

shutdown 후 작업을 제출하려고 하면 RejectedExecutionException 예외가 발생한다.  
shutdown 호출한 스레드는 실행 중인 작업이 종료될 때까지 기다리지 않고 바로 다음 라인을 실행한다.  
만약 스레드가 메서드 호출 후 블록킹 되기 위해서는 awaitTermination() 메서드를 사용해야한다.  

### 작업 종료 대기 및 확인
- awaitTermination(long timeout, TimeUnit unit)
  - 스레드 풀이 종료되기를 기다린다.
  - timeout 시간이 지나면 false를 반환한다.
  - 스레드 풀이 종료되면 true를 반환한다.
- isShutdown()
  - ExecutorService의 shutdown(), shutdownNow() 메서드가 호출 후 종료 절차가 시작되었는지를 나타낸다. 
- isTerminated()
  - ExecutorService의 모든 작업이 완료되었는지를 나타낸다.
  - shutdown() 또는 shutdownNow() 메서드가 호출된 후 모든 작업이 완료되었으면 true를 반환한다.

## ExecutorService 다중 작업 처리

### invokeAll
작업들 중 가장 오래 걸리는 작업 만큼 시간이 소요된다.

```java
List<Future> invokeAll<Collection tasks> throws InterruptedException
```
- 여러개의 Callable 작업을 동시에 실행하고 모든 작업이 완료될때까지 블록되며 각 작업의 결과를 나타내는 Future 객체의 리스트를 반환한다.
- 작업이 완료 되면 Future.isDone() 이 ture가 되며 작업은 정상적으로 종료되거나 예외를 던져 종료 될 수 있다.
- 대기 중에 입터럽트가 발생한경우 아직 완료 되지 않은 작업들은 취소된다.

```java
import java.util.concurrent.TimeUnit;

List<Future> invokeAll(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException
```
- 기본 메서드와 동일한 기능을 하고 시간 경과와 관련된 부분만 차이가 난다.
- 타임아웃이 발생하거나 모든 작업이 완료될 때 까지 블록되며 각 작업의 결과를 나타내는 Futrue 객체의 리스트를 반환한다.
- 타임아웃이 발생한 경우 이 작업 중 일부는 완료되지 않을 수 있으며 완료 되지 않은 작업은 취소 된다.

### InvokeAny
작업들 중 가장 짧게 걸리는 작업 만큼 시간이 소요된다.
```java
T invokeAny(Collection tasks) throws InterruptedException, ExecutionException
```
- 여러개의 Callable 작업을 동시에 실행하고 가장 먼저 완료되는 작업의 결과를 반환한다.(예외를 던지지 않은)
- 어떤 작업이라도 성공적으로 완료하면 블록을 해제하고 해당 작업의 결과를 반환한다.
- 정상적인 반환 또는 예외 발생 시 완료 되지 않은 작업들은 모두 취소된다.

```java
T invokeAny(Collection tasks, long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException
```
- 주어진 시간이 경과하기전에 예외 없이 성공적으로 완료된 작업의 결과를 반환한다.

# ScheduledExecutorService
주어진 시간 후에 명령을 실행하거나 주기적으로 실행할 수 있는 ExecutorService를 상속한 인터페이스이다.  
작업의 예약 및 실행을 위한 강력한 기능을 제공하여 시간 기반 작업을 조절하거나 주기적인 작업을 수행하는데 유용하다.

```java
ScheduledFuture<?> schedule(Runnable command, long delay, TimeUnit unit)
```
- 주어진 지연 시간 이후에 Runnable 작업을 예약하고 ScheduledFuture를 반환한다.
- command: 실행할 작업, delay: 실행을 지연할 시간, unit: 지연 시간의 단위

```java
ScheduledFuture<V> schedule(Callable<V> callable, long delay, TimeUnit unit)
```
- 주어진 지연 시간 이후에 Callable 작업을 예약하고 ScheduledFuture를 반환한다.

```java
ScheduledFuture<?> scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)
```
- 초기 지연 시간 이후에 Runnable 작업을 주기적으로 실행하도록 예약하고 ScheduleFuture를 반환한다. 이후에 주어진 주기로 실행된다.
- command: 실행할 작업, initialDelay: 첫 번째 실행을 지연할 시간, period: 작업 간의 주기, unit: 시간 단위

```java
ScheduledFuture<?> scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
```
- 초기 지연 시간 이후에 Runnable 작업을 주기적으로 실행하도록 예약하고 ScheduleFuture를 반환한다. 작업이 완료되고 나서 지연 시간 후 실행된다.
- command: 실행할 작업, initialDelay: 첫 번째 실행을 지연할 시간, delay: 작업 간의 지연 시간, unit: 시간 단위

## ScheduledFutrue
- ScheduledExecutorService의 작업 예약 결과를 나타내는 인터페이스
- 주요 모겆ㄱ은 지연이나 주기적인 작업 실행을 위한 것이며 결과를 처리하는 것은 아니다.
- getDelay() : 작업이 예약된 시간까지 남은 시간을 반환한다.