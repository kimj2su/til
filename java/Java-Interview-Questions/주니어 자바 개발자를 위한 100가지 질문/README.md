# (1) 기초
## JDK와 JRE의 차이점은 무엇입니가?
JDK(Java Development Kit)는 자바 개발 도구로 자바 프로그램을 개발하기 위한 도구들을 제공한다.  
JRE(Java Runtime Environment)는 자바 실행 환경으로 자바 프로그램을 실행하기 위한 도구들을 제공한다.  
JDK는 JRE를 포함한다.

## ==와 equals()의 차이점은 무엇입니까?
==는 비교하고자 하는 대상의 주소값을 비교한다.  
equals()는 비교하고자 하는 대상의 주소값이 아닌 내용을 비교한다.  
String의 경우 equals()를 오버라이딩하여 내용을 비교하도록 되어있다.

## 두 객체가 동일한 hashCode를 가지면 Equals()가 참이어야 합니다, 그렇죠?
아니다. 두 객체가 동일한 hashCode를 가지면 Equals()가 참일 수도 있고 거짓일 수도 있다.  
동일한 객체는 동일한 해시코드를 가져야하므로 equals() 메서드를 오버라이드 한다면, hashCode() 메서드도 함께 오버라이드 해야한다.

## 자바에서 final의 기능은 무엇입니까?
final은 불변을 의미하고 변수, 메서드, 클래스에 사용할 수 있다.  
변수에 사용할 경우, 변수의 값을 변경할 수 없다.  
메서드에 사용할 경우, 메서드를 오버라이드 할 수 없다.  
인자로 사용할 경우 메서드내에서 인자의 값을 변경할 수 없다.  
클래스에 사용할 경우, 클래스를 상속할 수 없다.

## 자바에서 Math.round(-1.5)는 무엇을 의미합니까?
-1.5를 반올림하여 -1을 의미한다.  
Math.round() 메서드는 소수점 첫째자리에서 반올림하여 반환해준다.

## String은 기본 데이터 타입입니까?
String은 클래스타입이다.  
기본적으로 자바에서 클래스는 대문자로 시작한다.

## 자바에서 문자열을 조작하는 클래스는 무엇이 있습니까? 각 클래스의 차이점은 뭘까요?
String, StringBuffer, StringBuilder가 있다.  
String은 불변이고, StringBuffer와 StringBuilder는 가변이다.  
StringBuffer와 StringBuilder의 차이점은 동기화의 유무이다.  
StringBuffer는 동기화를 지원하고, StringBuilder는 동기화를 지원하지 않는다.

## String str ="i"와 String str = new String("i")가 동일합니까?
아니다.
리터럴로 값을 할당하는 경우 String Pool에 저장되고, new 연산자를 통해 생성하는 경우 Heap에 저장된다.

## 문자열을 반전시키는 가장 좋은 방법은 무엇인가요?
StringBuilder의 reverse() 메서드를 사용하는 것이 가장 좋다.  
문자열은 불변 객체이기 때문에 한번 생성된 문자열은 수정할 수 없다.  
원본 문자열은 변경하지 않으며, 새로운 문자열을 생성하여 반환합니다.

## String 클래스의 일반적인 메서드는 무엇이 있나요?
String 클래스의 일반적인 메서드는 다음과 같다.
- charAt(int index) : 문자열의 index번째 문자를 반환한다.
- concat(String str) : 문자열을 뒤에 붙인다.
- contains(CharSequence s) : 문자열이 포함되어 있는지 확인한다.
- endsWith(String suffix) : 문자열이 suffix로 끝나는지 확인한다.
- equals(Object anObject) : 문자열이 같은지 확인한다.
- indexOf(int ch) : 문자열에서 ch가 처음으로 등장하는 위치를 반환한다.  
- isEmpty() : 문자열이 비어있는지 확인한다.

## 추상 클래스에서 추상 메서드는 필수적인가요?
추상 클래스에서 추상 메서드는 필수적이지 않다.  
추상 클래스에서 추상 메서드가 하나도 없어도 추상 클래스로 선언할 수 있다.

## 보통의 클래스와 추상 클래스의 차이는 무엇인가요?
보통의 클래스는 인스턴스화 할 수 있지만, 추상 클래스는 인스턴스화 할 수 없다.  
보통의 클래스는 추상 메서드를 가질 수 없지만, 추상 클래스는 추상 메서드를 가질 수 있다.

## final은 추상 클래스를 수정할 때 사용할 수 있나요?
final은 불변을 뜻하므로 추상 클래스를 수정할 수 없다.


# (2) Container

## 자바 컨테이너란 무엇인가요?
자바 컨테이너는 자바에서 데이터를 저장하는 객체이다.  
java.util 패키지에 정의되어 있으며, 기본적으로 List, Set, Queue, Map 인터페이스를 구현하고 있다.

## Collection과 Collections의 차이는 무엇인가요?
Collection은 자바에서 데이터의 집합, 그룹이라는 뜻을 가지고 있다.  
Collection은 인터페이스를 포함하는 Collection Framework의 최상위 인터페이스이다.  
Collections는 Collection 인터페이스를 구현한 클래스들을 조작하기 위한 정적 메서드를 제공하는 클래스이다.

## List, Set, Map의 차이점을 말해주세요.
List는 순서가 있는 데이터의 집합이다. -> 순서 보장 o , 중복 허용  
Set은 중복을 허용하지 않는 데이터 집합이다. -> 순서 보장 x, 중복 불가  
Map은 키와 값의 쌍으로 이루어진 데이터 집합이다. -> 순서 보장 x, 키 중복 불가, 값 중복 허용  

## HashMap과 Hashtable의 차이는 무엇인가요?
- Thread-safe 여부
  - HashMap은 Thread-safe하지 않다.
  - Hashtable은 Thread-safe하다.
- Null 허용 여부
  - HashMap은 Null을 허용한다.
- Enumeration
  - HashMap은 Enumeration을 지원하지 않는다.
  - Hashtable은 Enumeration을 지원한다.

## 각각 어떤 상황에서 HashMap과 TreeMap을 선택하나요?
HashMap은 순서를 보장하지 않는다.  
TreeMap은 순서를 보장한다.  
순서가 중요하지 않은 경우 HashMap을 사용하고, 순서가 중요한 경우 TreeMap을 사용한다.  

## HashMap 구현 원칙은 무엇인가요?
HashMap은 해시 테이블을 기반으로한 자료구조로, 효율적인 데이터 검색과 삽입을 위해 설계되었다.
1. 해시 함수 사용 : HashMap은 해시 함수를 사용하여 키와 해시코드를 매핑한다. 해시함수는 키를 해시 코드로 변환하는 역할을 한다.
   이 해시코드는 배열의 인덱스로 사용한다.
2. 동등성 비교 : HashMap은 동등성 비교를 통해 키가 같은지 비교한다. 동등성 비교를 위해 equals() 메서드를 사용한다.
3. 충동 해결 : HashMap은 충돌이 발생할 수 있다. 충돌이 발생하면 연결리스트를 사용하여 해결한다.
  개방주소법은 동일한 버킷에 새로운 키를 저장할 다른 위치를 찾는 방법이다.  
  연결리스트는 동일한 버킷에 속하는 모든 키들을 연결 리스트로 관리하여 충돌을 해결한다.
4. 동기화 : HashMap은 동기화를 지원하지 않는다. ConcurrentHashMap을 사용하여 동기화를 지원한다.  

HashMap은 검색, 삽입, 삭제 작업을 평균적으로 O(1)의 시간 복잡도로 처리할 수 있으며, 큰 데이터셋에서 효과적으로 사용될 수 있습니다.


## HashSet 구현 원칙은 무엇인가요?
HashSet은 자바의 Set 인터페이스를 구현한 자료 구조로, 중복을 허용하지 않는 요소들의 집합을 저장하는데 사용됩니다. HashSet의 구현 원칙은 다음과 같습니다.  
1. 해시 함수 사용: HashSet은 해시 함수를 사용하여 요소를 저장합니다. 해시 함수는 요소의 해시 코드를 계산하는 역할을 합니다. HashSet은 각 요소의 해시 코드를 기반으로 내부 배열에서 요소를 저장하고 검색합니다.

2. 동등성 비교: HashSet은 동일한 해시 코드를 가진 요소를 처리하기 위해 동등성 비교(equality comparison)를 수행합니다. 동등성 비교는 equals() 메소드를 사용하여 요소 간에 동등성을 확인합니다. 동등한 요소는 HashSet 내에서 중복을 허용하지 않고 저장되지 않습니다.

3. 해시 충돌 해결: 해시 함수는 일반적으로 요소의 공간이 무한하지 않기 때문에 서로 다른 요소가 동일한 해시 코드를 가질 수 있습니다. 이러한 상황을 해시 충돌(hash collision)이라고 합니다. HashSet은 충돌을 해결하기 위해 개방 주소법(open addressing) 또는 연결리스트(chaining)를 사용합니다. 개방 주소법은 동일한 버킷에 새로운 요소를 저장할 다른 위치를 찾는 방법입니다. 연결리스트는 동일한 버킷에 속하는 모든 요소들을 연결 리스트로 관리하여 충돌을 해결합니다.

4. 순서 보장: HashSet은 요소들의 순서를 보장하지 않습니다. 요소들이 저장된 순서와는 관계없이 내부적으로 해시 함수와 해시 충돌 해결 방법에 의해 요소들이 저장되기 때문에, HashSet의 순서는 예측할 수 없습니다. 만약 순서를 보장해야 하는 경우에는 LinkedHashSet을 사용할 수 있습니다.

5. 동기화: HashSet은 기본적으로 스레드 간의 안전성을 보장하지 않습니다. 동시에 여러 스레드에서 HashSet에 접근하는 경우에는 외부에서 동기화를 보장해주어야 합니다. 동기화가 필요한 경우에는 ConcurrentHashMap을 고려해볼 수 있습니다.

HashSet은 요소의 삽입, 삭제, 검색 작업을 평균적으로 O(1)의 시간 복잡도로 처리할 수 있습니다. 중복을 허용하지 않는 요소의 집합을 저장하고자 할 때 HashSet은 매우 유용한 자료 구조입니다.

## ArrayList와 LinkedList의 차이점은 무엇인가요?
ArrayList
- 내부적으로 배열을 사용하여 저장
- 인덱스를 기반으로 요소를 검색
- 요소의 삽입, 삭제가 비효율적이다. -> 배열의 재할당과 복사가 필요하기 때문에

LinkedList
- 내부적으로 연결 리스트를 사용하여 저장
- 요소들이 서로 연결되어 있기 때문에 인덱스를 기반으로 요소를 검색하는 것이 비효율적이다.  
- 요소의 삽입, 삭제가 효율적이다. -> 요소의 삽입, 삭제 작업은 요소들이 서로 연결되어 있기 때문에 배열의 재할당과 복사가 필요하지 않다.  
- 요소의 검색이 비효율적이다. -> 요소들이 서로 연결되어 있기 때문에 인덱스를 기반으로 요소를 검색하는 것이 비효율적이다.

## Array에서 List로 전환하려면 어떻게 해야하나요?
1. Arrays.asList() 메서드를 사용하여 Array를 List로 전환할 수 있다.
2. ArrayList의 생성자를 사용하여 Array를 List로 전환할 수 있다.
3. Collections.addAll() 메서드를 사용하여 Array를 List로 전환할 수 있다.
4. Stream의 of() 메서드를 사용하여 Array를 List로 전환할 수 있다.

## ArrayList와 Vector의 차이점을 말해주세요.
ArrayList는 동기화를 지원하지 않는다.
Vector는 동기화를 지원한다.
Vector는 멀티 스레드환경에서 thread-safe 하지만 단일 스레드 환경에서는 성능이 느려질 수 있다.

## Array와 ArrayList의 차이점을 말해주세요.
Array는 크기가 고정되어 있고, 요소의 타입이 동일하다.
ArrayList는 크기가 가변적이고, 제네릭을 통해 요소의 타입이 동일하지 않아도 된다.

## Queue에서, poll()과 remove()의 차이는 무엇인가요?
poll()은 큐에서 요소를 제거하고 반환한다. -> 큐가 비어있으면 null을 반환한다.
remove()는 큐에서 요소를 제거하고 제거된 요소를 반환한다. -> 큐가 비어있으면 NoSuchElementException을 발생시킨다.

## thread-safe한 컬렉션 클래스들은 무엇이 있을까요?
Vector, Hashtable, Stack, Properties, ConcurrentHashMap, CopyOnWriteArrayList, CopyOnWriteArraySet 등이 있다.

## iterator란 무엇인가요?
iterator는 컬렉션의 요소들을 순회하는데 사용하는 인터페이스이다.


## iterator의 사용 목적은 무엇인가요? 어떤 특징이 있죠?
- iterator는 컬렉션의 요소들을 순회하는데 사용한다.
- ArrayList, LinkedList, HashSet 등 어떤 컬렉션 타입이든 일관된 방식으로 요서에 접근할 수 있다.
- 안전한 요소 수정 및 삭제 : 내부적으로 컬렉션의 상태를 추적하고 컬렉션의 구조적 변경을 허용하지 않으면서 요소를 삭제할 수 있다.
- 단방향 순회 : 컬렉션의 요소들을 순회할 때는 단방향으로만 순회할 수 있다.
- 
## iterator와 listIterator의 차이는 무엇인가요?
1. 방향 : iterator는 단방향으로만 순회할 수 있다. listIterator는 양방향으로 순회할 수 있다.
2. 요소 수정 : iterator는 요소를 수정할 수 없다. listIterator는 요소를 수정할 수 있다.
3. 인덱스 : iterator는 인덱스를 사용할 수 없다. listIterator는 인덱스를 사용할 수 있다.
4. 컬렉션 타입 : iterator는 모든 컬렉션 타입에 사용할 수 있다. listIterator는 List 컬렉션 타입에만 사용할 수 있다.

# (3) Multi-Threading
## 병렬과 동시성의 차이점을 말해주세요.
병렬은 하나의 작업을 여러개의 작업으로 나누어 동시에 처리하는 것을 의미한다.
동시성은 여러개의 작업을 동시에 처리하는 것을 의미한다.

## 스레드와 프로세스의 차이를 말해주세요..
프로세스는 운영체제로부터 자원을 할당받는 작업의 단위이다.
스레드는 프레세스가 할당받은 자원을 이용하는 실행의 단위이다.

## 데몬 스레드는 무엇인가요?
데몬 스레드는 주 스레드의 작업을 돕는 보조적인 역할을 수행하는 스레드이다.
주 스레드가 종료되면 데몬 스레드는 강제적으로 종료된다.

## 스레드를 만드는 방법을 나열해주세요.
1. Thread 클래스를 상속받아 run() 메서드를 오버라이딩한다.
2. Runnable 인터페이스를 구현한다.
3. Callable 인터페이스를 구현한다.
4. ExecutorService를 사용한다.

## runnable과 callable의 차이는 무엇인가요?
- runnable은 스레드가 실행할 작업을 정의하는 함수형 인터페이스이다.
- run() 메서드는 반환값이 없고, checked exception을 던질 수 없다.
- call() 메서드는 반환값이 있고, checked exception을 던질 수 있다.

## 스레드의 여러가지 상태에 대해 말해주세요.
### 객체 생성
- NEW : 스레드가 생성되었지만, start() 메서드가 호출되지 않은 상태

### 실행 대기
- RUNNABLE : 실행 중 또는 실행 가능한 상태(실행 대기)

### 일시 정지
- WAITING : 다른 스레드가 통지할 때까지 기다리는 상태
- TIMED_WAITING : 주어진 시간 동안 기다리는 상태
- BLOCKED : 사용하고자 하는 객체의 락이 풀릴 때까지 기다리는 상태

### 종료
- TERMINATED : 실행을 마친 상태

## sleep()과 wait()의 차이는 무엇인가요?
- sleep()은 Thread 클래스의 정적 메서드이다.
- wait()은 Object 클래스의 메서드이다.
- sleep()은 주어진 시간 동안 스레드를 일시 정지시킨다.
- wait()은 다른 스레드가 notify() 또는 notifyAll() 메서드를 호출할 때까지 스레드를 일시 정지시킨다.

## notify()와 notifyAll()의 차이는 무엇인가요?
- notify()는 wait() 메서드에 의해 일시 정지된 스레드 중 하나를 재시작한다.
- notifyAll()은 wait() 메서드에 의해 일시 정지된 모든 스레드를 재시작한다.

## thread run()과 tnread start()의 차이는 무엇인가요?
- run() 메서드는 스레드가 실행할 작업을 정의한다.
- start() 메서드는 스레드를 실행시킨다.

## 스레드 풀을 생성할 수 있는 여러가지 방법을 말해주세요.
)
### Executors.newFixedThreadPool(int nThread)
파라미터로 제공되는 n개 만큼 스레드 풀을 생성한다.  
보통 일정량의 업무가 발생할 때 사용한다.

### Executors.newCachedThreadPool()
초기 스레드 개수가 0개로 서렂ㅇ되며 스레드 개수보다 많은 양의 작업이 요청되면 새로운 스레드를 생성하여 작업을 처리한다.  
작업이 끝난 스레드가 60초 이상 새로운 작업 요청이 없으면 스레드를 종료하고 스레드 풀에저 제거된다.

### Executors.newSingleThreadExecutor(int corePoolSize)
스레드를 일정시간이 흐르고 난 뒤 실행시키도록하는 스케줄링 스레드 기능이다.

## 스레드 풀의 상태에 대해 말해주세요.
### 생성
- 스레드 풀이 생성되었지만, execute() 메서드가 호출되지 않은 상태

### 작업
- 스레드 풀이 작업을 수행하는 상태  
- 작업이 들어올 때마다 새로운 작업을 큐에 추가하고, 작업을 큐에서 하나씩 꺼내어서 적절한 쓰레드로 할당한다.  
- 작업이 끝나면 이를 콜백형태로 작업을 요청한 주체에게 결과를 알려준다.  

### 종료
shutdown 또는 shutdownNow, awaitTermination 메서드가 호출되어 스레드 풀이 종료되는 상태

## 스레드 풀에서 submit()과 execute()의 차이는 무엇인가요?
### execute()
- excute()는 Runnable만을 처리하며, 작업의 처리 결과를 반환 받을 수 없다.
- execute()는 스레드에서 작업처리 도중 예외가 발생하면 스레드 풀에서 해당 예외를 제거하고 새로운 스레드를 생성한다.

### submit()
- submit()은 작업을 제출하고, Future 객체를 반환한다.  
- submit()은 작업 처리 도중 예외가 발생해도 스레드를 제거하지 않고 다음에 재사용한다.  
- 오버헤드가 더 적으므로 submit()을 사용하는 것이 권장된다.


## 자바 프로그램에서 멀티 스레드 작업의 안전성을 어떻게 보장할 수 있을까요?
변경할 수 있는 상태에 접근하는 과정을 동기화를 통해 조율한다.
- synchronized 키워드를 사용한다.
- volatile 키워드를 사용한다.
- Atomic 클래스를 사용한다.
- Lock 인터페이스를 사용한다.


# (4) Reflection
## 리플렉션은 무엇인가요?
리플렉션은 자바 프로그램에서 클래스의 정보를 얻어내는 기능을 의미한다.  
구체적인 클래스의 타입을 알지 못하더라도 그 클래스의 메서드, 타입, 변수들에 접근할 수 있도록 해주는 자바 API이다.    
컴파일 시간이 아닌 런타임 환경에서 동적으로 클래스의 정보를 추출할수 있다.  
그러나 런타임중에 클래스의 정보를 추출하므로 성능상의 이슈가 발생할 수 있다.

## 자바 직렬화란 무엇인가요? 어떤 상황에서 필요한가요?
자바 직렬화는 자바 객체를 바이트 형태로 변환하는 것을 의미한다.(역직렬화도 포함)  
시스템적으로 JVM의 Runtime Data Area에 상주하고 있는 객체 데이터를 바이트 형태로 변환하는 기술과 직력화된 바이트 형태의 데이터를 
객체로 변환해서 JVM으로 상주시키는 형태를 말하기도 한다.  
기본적으로 자바의 객체는 JVM메모리에서만 상주를 하기 때문에 JVM이 사라지면 객체의 정보도 사라진다.  
그렇기 때문에 영속화 → JVM 외부에서도 영원히 데이터가 존재할 수 있도록 데이터를 외부(DB 등)에 보내거나 저장할 때 직렬화 를활용한다.

## 동적 프록시란 무엇인가요?
동적 프록시는 프록시 클래스를 런타임에 동적으로 생성하는 기술이다.  
동적 프록시는 프록시 클래스를 런타임에 동적으로 생성하기 때문에 프록시 클래스를 직접 작성하지 않아도 된다.  
객체를 동적으로 생성해주는 newProxyInstance() 메서드를 사용하여 프록시 객체를 생성한다.
하지만 리플렉션을 활용하므로 속도가 느리다는 단점 또한 존재한다.  

## 동적 프록시는 어떻게 사용하나요?
```java
Object proxy = Proxy.newProxyInstance(ClassLoader       // 클래스로더
                                    , Class<?>[]        // 타겟의 인터페이스
                                    , InvocationHandler // 타겟의 정보가 포함된 Handler
                                                        );
```
위 코드와 같이 단순히 리플렉션 Proxy.newProxyInstane() 메소드를 사용하면 된다.  
그리고 전달 받은 파라미터를 가지고 프록시 객체를 생성해준다.

```java
@Slf4j
public class JdkDynamicProxyTest {

    static interface ProxyInterface {
        void call(String name);
    }

    static class Kimjisu implements ProxyInterface {
        @Override
        public void call(String name) {
            log.info(name);
        }
    }

    static class MyInvocationHandler implements InvocationHandler {
        private final ProxyInterface target;

        public MyInvocationHandler(ProxyInterface target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            log.info("proxy 전");
            Object result = method.invoke(target, args);
            log.info("proxy 후");
            return result;
        }
    }

    @Test
    void jdkDynamicTest() {
        Kimjisu kimjisu = new Kimjisu();
        MyInvocationHandler handler = new MyInvocationHandler(kimjisu);
        ProxyInterface proxy = (ProxyInterface) Proxy.newProxyInstance(
                ProxyInterface.class.getClassLoader(),
                new Class[]{ProxyInterface.class},
                handler);

        proxy.call("JDK Dynamic Proxy");
        kimjisu.call("call");

    }
}
```
ProxyInterface 인터페이스의 구현체 Kimjisu를 만들고 위에서 설명한 핸들러를 작성한다.

핸들러는 원본 객체의 메소드를 호출 전, 후 로그를 찍고 리플렉션의 Proxy.newProxyInstance() 메소드를 사용 하여 ProxyInterface의 프록시 객체를 만들 수 있다.  
프록시 객체는 프록시 전, 후 로그가 찍히고 일반 객체는 로그 없이 call 로그만 찍히게 된다.  

# (5) bject Copy

## 복사가 사용되는 이유는 무엇인가요?
복사는 객체를 생성하는 방법 중 하나이다.  
원본 객체를 복사하지 않고 대입하여 사용하게 되면 원본 객체가 변경되면 안되는 작업의 경우 원본 객체의 안정성을 보장하기 어렵다.  

## 객체 복사는 어떻게 할 수 있나요?
Object에 존재하는 clone()메서드를 이용하거나 Cloneable 인터페이스를 구현하여 사용한다.  
clone() 메서드는 객체를 복사하여 반환한다.
구현하는 경우 clone() 메서드를 오버라이딩하여 사용한다.
구현하지 않으면 CloneNotSupportedException을 발생시킨다. 

## 깊은 복사와 얕은 복사의 차이를 말해주세요.
### 얕은 복사
얕은 복사는 단순히 필드값을 복사하여 객체를 복제하는것을 말한다.  
필드값만 복사하기 때문에 필드가 원시타입일 경우 값 복사가 일어나고 참조타입일 경우에는 객체의 주소가 복사된다.

### 깊은 복사
깊은 복사는 필드가 객체인 경우 객체까지 복사하는 경우이다.  
보통 얕은 복사를 하게되면 원본 값에 대한 안정성을 보장하지 못하는데 깊은 복사는 주소값이 아닌 객체를 복사하기 때문에 원본 객체에 영향을 주지 않는다.  
이 경우는 반드시 Cloneable인터페이스를 구현하여 clone()메서드를 재정의 해야한다.
Cloneable 인터페이스를 구현해야 하는 이유는 명시적으로 설계자가 복사를 허용한다는 표시이기 때문이다.
```java
@Override
Object clone() throws CloneNotSupportedException {
    Member cloned = (Member) super.clone(); //먼저 얕은복사 수행
    cloned.car = new Car(this.car.model); //객체를 새롭게 생성

    return cloned;
}
```

# (6) Java Web
## jsp와 servlet의 차이점은 무엇인가요?

## jsp를 기본 제공하는 객체는 무엇이 있나요?

## 4개의 jsp scope에는 무엇이 있나요?

## 세션과 쿠키의 차이는 무엇인가요?

## 세션 과정을 설명해주세요.

## 쿠키를 사용할 수 없을 때 세션을 대신 사용할 수 있을까요?

## 스프링 MVC와 struts의 차이는 무엇인가요?

## SQL Injection을 피할 수 있는 방법을 설명해주세요.

## XSS 공격이 무엇이고, 어떻게 피할 수 있는지 설명해주세요.

## CSRF 공격이 무엇이고, 어떻게 피할 수 있는지 설명해주세요.


# (7) 예외 클래스
## throw와 throws의 차이는 무엇인가요?

## final, finally, finalise의 차이는 무엇인가요?

## try-catch-finally에서 생략할 수 있는 부분이 무엇인가요?

## catch가 반환되면 finally가 실행되나요?

## exception 클래스의 예시를 말해주세요.

# (8) internet
## 301과 302 상태 코드의 의미와 차이는 무엇인가요?

## forward와 redirect의 차이는 무엇인가요?

##  tcp와 udp의 차이점을 말해주세요.

## 왜 tcp는 3 handshakes를 필요로 하나요? 왜 2개가 아니죠?

## tcp packet은 어떻게 생성되나요?

## OSI 7계층에 대해서 설명해주세요.

## get과 post요청의 차이를 말해주세요.

## 어떻게 도메인 간의 요청이 작동하나요?

## JSONP의 구현 원칙은 무엇인가요?

## 디자인 패턴에 대해 말해주세요.

## 알고 있는 디자인 패턴이 있으신가요?

## 추상 팩토리와 심플 팩토리의 차이가 무엇인가요?

# (9) Spring

## 스프링 사용의 장점은 무엇인가요?

## AOP란 무엇인가요?

## IOC란 무엇인가요?

## 스프링의 메인 모듈은 무엇인가요?

## 가장 많이 사용되는 의존성 주입 방법은 무엇인가요?

## 스프링 빈은 thread-safe 한가요?

## 스프링은 얼마나 많은 bean scope를 유지할 수 있나요?

## 스프링 auto-assembles 빈을 위한 방법들을 말해주세요.

## 스프링 트랜잭션을 구현하기 위한 다양한 방법을 말해주세요.

## 스프링 트랜잭션 고립이란 무엇인가요?

## 스프링 mvc의 런타임 flow는 무엇인가요?

## @RequestMapping은 어떤 역할을 하나요?

## @Autowired의 기능은 무엇인가요?




