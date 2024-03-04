# ThreadPoolExecutor
- ThreadPoolExecutor는 ExecutorService 인터페이스를 구현한 클래스로서 스레드 풀을 생성하고 관리하는 클래스이다.

```java
public ThreadPoolExecutor(
            int corePoolSize, 
            int maximumPoolSize, 
            long keepAliveTime, 
            TimeUnit unit, 
            BlockingQueue<Runnable> workQueue, 
            ThreadFactory threadFactory, 
            RejectedExecutionHandler handler
) {
    // ...
}
```
- corePoolSize : 스레드 풀의 기본 크기
- maximumPoolSize : 스레드 풀의 최대 크기
- keepAliveTime : 스레드 풀의 최대 크기를 초과하는 스레드가 유휴 상태로 대기할 시간, 제거 되는 시간
- unit : keepAliveTime의 단위
- BlockingQueue<Runnable> : 작업 큐
- ThreadFactory : 스레드를 생성하는 팩토리
- RejectedExecutionHandler : 작업을 처리할 수 없을 때 호출되는 핸들러

## corePoolSize & maximumPoolSize
- ThreadPoolExecutor 는 corePoolSize 및 maximumPoolSize 로 설정된 개수에 따라 풀 크기를 자동으로 조정한다
- ThreadPoolExecutor 는 새 작업이 제출 될 때 corePoolSize 미만의 스레드가 실행 중이면 corePoolSize 가 될 때까지 새 스레드를 생성한다
- corePoolSize 를 초과할 경우 큐 사이즈가 남아 있으면  큐에 작업을 추가하고 큐가 가득 차 있는 경우는 maximumPoolSize 가 될 때까지 새 스레드가 생성된다
- setCorePoolSize 및 setMaximumPoolSize 메서드를 사용하여 동적으로 값을 변경할 수 있다
- 기본적으로 스레드 풀은 스레드를 미리 생성하지 않고 새 작업이 도착할 때만 생성하지만 prestartCoreThread 또는 prestartAllCoreThreads 메서드를 사용하여 동적으로 재 정의할 수 있다

```java
int corePoolSize = 2; // 기본 스레드 풀 크기
int maximumPoolSize = 4; // 최대 스레드 풀 크기
long keepAliveTime = 10; // 초 단위의 스레드 유지 시간
// BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>();
BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(4);
int taskNum = 7; // 작업의 개수

executor.prestartCoreThread(); // 기본 스레드 1개를 미리 생성
executor.prestartAllCoreThreads(); // 모든 기본 스레드를 미리 생성
```
corePoolSize + queueSize < taskNum 이면 최대 maximumPoolSize 만큼 스레드가 생성된다.  
위의 예제에서는 2 + 4 < 7 이므로 1개의 스레드가 생성된다.

# KeepAliveTime
- corePoolSize 보다 더많은 스레드가 존재하는 경우 keepAliveTime 이후에 스레드가 제거된다.
- allowCoreThreadTimeOut(true)로 설정하면 core스레드에도 적용할 수 있음.
- Executors.newCachedThreadPool() 은 keepAliveTime 이 60초로 설정되어 있다.
- Executors.newFixedThreadPool() 은 keepAliveTime 이 0초로 설정되어 있다.(제한 없음)

# BlockingQueue
- 기본적으로 스레드 풀은 작업이 제출되면 corePoolSize의 새 스레드를 추가해서 작업을 할당하고 큐에 작업을 바로 추가히지 않는다.
- corePoolSize를 초과해서 스레드가 실행 중이면 새 스레드를 추가해서 작업을 할당하는 대신 큐에 작업을 추가한다.(큐가 가득찰 때까지)
- 큐에 공간이 가득차게 되고 스레드가 maxPoolSize 이상 실행 중이면 더 이상 작업은 추가되지 않고 거부 된다.

## SynchronousQueue
- newCachedThreadPool() 에서 사용한다.
- 크기가 0인 큐로서 작업을 대기열에 넣으려고 할 때 실행할 스레드가 없으면 즉시 새로운 스레드가 생성된다.

## LinkedBlockingQueue
- Executors.newFixedThreadPool() 에서 사용한다.
- 무제한 크기의 큐로서 corePoolSize의 스레드가 모두 사용 중인 경우 새로운 작업이 제출 되면 대기열에 등록하고 대기하게 된다.
- 무제한 크기의 큐이기 때문에 corePoolSize의 스레드만 생성하고 maxPoolSize는 무시된다. -> 큐가 다 차야 maximumPoolSize의 스레드가 생성되기 때문에.

## ArrayBlockingQueue
- 내부적으로 고정된 크기의 배열을 사용하여 작업을 추가하고 큐를 생성할때 최대 크기를 지정해야하며 한 번 지정된 큐의 크기는 변경할 수 없다.
- 큰 대기열과 작은풀(큐의 크기가 크지만 스레드의 개수가 작은)을 사용하면 CPU 사용량 및 OS 리소스 및 컨텍스트 전환 오버헤드가 최소화 되지만 낮은 처리량을 유발할 수 있다.
- 작은 대기열과 큰 풀(큐의 크기가 작지만 스레드의 개수가 큰)을 사용하면 CPU 사용량이 높아지지만 대기열이 가득 찰 경우 추가적인 작업을 거부하기 때문에 처리량이 감소할 수 있다.
- 그렇기 때문에 ArrayBlockingQueue을 사용할 때는 정책을 잘 정해야 한다.

|메서드| 반환값                                    | 블록 여부| 예외처리                   |
|---|----------------------------------------|---|------------------------|
|add(e)| 성공시 true, 실패시 예외                       |X| IllegalStateException  |
|offer(e)| 성공시 true, 실패시 false                    |X| InterruptedException   |
|put(e)| X                                      |O| X                      |
|remove(e)| 큐에서 요소를 제거하고 반환, <br/> 큐가 비어 있으면 예외 발생 |X| NoSuchElementException |
|poll(e)| 큐에서 요소를 제거하고 반환, <br/> 큐가 비어 있으면 null 반환 |X| X                      |
|take(e)| 큐에서 요소를 제거하고 반환, <br/> 큐가 비어 있으면 대기 |O| InterruptedException   |

- add(E e) : 사용하기 쉽고 간단하다.
- remove(): 큐가 가득 차 있거나 비어 있을때 요소 추가/삭제 시 예외를 발생시키므로 예외처리가 필요하다.

- offer(E e): 큐가 가득 차 있거나 비어 있을때 요소 추가/삭제시 예외를 발생시키지 않는다.
- put(E e): 요소를 추가하고 제거할 때 실패할 경우 false 또는 null을 반환하므로 예외 처리는 필요 없지만 결과에 대한 로직이 필요하다.

- poll() : 큐가 가득 차 있거나 비어있을 때 요소 추가/삭제시 메서드에서 블록된다.
- take() : put() take() 메서드는 블록되어 대기하기 때문에 작업을 동기적으로 조절하는데 유용하다. 무기한 대기가 될 수 있기 때문에 타임 아웃을 지원하는 다른 메서드를 활용해야 할 수 있다.

# RejectedExecutionHandler
- execute() 메서드가 호출되었을 때 스레드 풀이 작업을 처리할 수 없을 때 호출되는 핸들러이다.
- 기본적으로 ThreadPoolExecutor 클래스는 작업을 처리할 수 없을 때 예외를 발생시키는 AbortPolicy를 사용한다.
- ThreadPoolExecutor 클래스는 작업을 처리할 수 없을 때 다음과 같은 핸들러를 제공한다.
    - AbortPolicy : 기본값으로 작업을 처리할 수 없을 때 예외를 발생시킨다.
    - CallerRunsPolicy : 작업을 처리할 수 없을 때 현재 스레드(호출한 스레드)에서 작업을 실행한다.
    - DiscardPolicy : 작업을 처리할 수 없을 때 작업을 무시한다.
    - DiscardOldestPolicy : 작업을 처리할 수 없을 때 큐의 가장 오래된 작업을 제거하고 새 작업을 추가한다.