# 함수형 프로그래밍
자바에서는 메서드의 형태로  함수를 구현한다.
객체는 명사의 형태로 Car, Apple 이런식으로 작성하고 메서드는 동사로 작성하게 됩니다.
## 명령형 프로그래밍 vs 선언형 프로그래밍
|    | 명령형 프로그래밍         | 선언형 프로그래밍              |
|----|-------------------|------------------------|
| 예시 | for, if, switch   | Stream API             |
| 방식 | OOP 객체 지향 프로그래밍   | Functional Programming |
| 특징 | 어떻게(how)를 구현할 것인가 | 무엇(what)을 구현할 것인가      |
| 장점 | 명확한 구현            | 간결한 구현                 |

### 예시 - 유저 리스트가 주어졌을때, 검증되지 않은(unverified) 유저들의 이메일을 리스트로 주세요.
|    | 명령형 프로그래밍                                                                                                      | 선언형 프로그래밍                                                                |
|----|----------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------|
| 예시 | 1. 이메일을 담을 리스트 선언<br/> 2. 루프 <br/> 3. 유저 선언 <br/> 4. 검증되지 않았는지 체크 <br/> 5. 않았다면 변수에 이메일 추출<br/> 6. 이메일 리스트에 넣기 | 1. 유저리스트에서 <br/> 1-1 검증되지 않은 유저만 골라서 <br/> 1-2 이메일을 추출해서 <br/>2. 리스트로 받기 

# 1급 시민으로서의 함수
1급 시민의 조건
- 함수/ 메서드의 매개변수(parameter)로서 전달할 수 있는가
- 함수/ 메서드의 반환값(return value)이 될 수 있는가
- 변수에 담을 수 있는가

# 함수형 프로그래밍을 통해 얻는것들
- 역할에 충실한 코드 -> 가독성 좋은 코드, 유지/보수에 용이, 버그로부터 안정, 확장성에 용이
- 패러다임의 전환 -> Stream, Optional,.. 다양한 활용 가능성

# Function Interface
```java
@FunctionalInterface
public interface Function<T, R> {
    R apply(T t);
}
```
- T 타입을 받아서 R 타입을 리턴하는 함수형 인터페이스
- 예를 들면 Function<Integer, Integer> f = (i) -> i + 10; 이런식으로 사용할 수 있다.
```java
public class Adder implements Function<Integer, Integer> {
    @Override
    public Integer apply(Integer integer) {
        return integer + 10;
    }

    public static void main(String[] args) {
        Function<Integer, Integer> adder = new Adder();
        Integer apply = adder.apply(10);
        System.out.println("apply = " + apply);
    }
}
```

# lambda
```java
Function<Integer, Integer> adder = (Integer x) -> {
            return x + 10;
};
Integer apply = adder.apply(10);
System.out.println("apply = " + apply);
```
람다를 통해 함수형 인터페이스를 구현할 수 있다.

1. 람다식은 매개변수의 타입이 유추 가능할 경우 타입 생략가능
2. 매개변수가 하나일 경우 괄호 생략가능
3. 바로 리턴하는 경우 중괄호 생략가능

# BiFunction - 매개변수가 두 개일때
```java
@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
}
```

# Function의 활용
@FunctionalInterface
- 단 하나의 abstract 메서드만 가지고 있는 인터페이스
- Default 메서드나 static 메서드를 가질 수 있다.
- 그러나 매개변수를 3개이상 받는 함수형 인터페이스는 없기 때문에 구현해줘야한다.
```java

@FunctionalInterface
public interface TriFunction <T, U, V, R> {
    R apply(T t, U u, V v);
}

TriFunction<Integer, Integer, Integer, Integer> triFunction = 
        (x, y, z) -> x + y + z;

Integer triFunctionResult = triFunction.apply(10, 20, 30);
System.out.println("triFunctionResult = " + triFunctionResult);
```

# Supplier<T> - 공급하다 
```java
@FunctionalInterface
public interfate Supplier<T> {
    T get();
}

public static void main(String[] args) {
    Supplier<String> myStringSupplier = () -> "Hello World";;
    System.out.println(myStringSupplier.get());
    
    // 함수가 일급 시민이 되었으므로 파라미터로 넘길 수 있다.
    //        Supplier<Double> myRandomDoubleSupplier = () -> Math.random();
    Supplier<Double> myRandomDoubleSupplier = Math::random;
    printRandomDoubles(myRandomDoubleSupplier, 5);
}

public static void printRandomDoubles(Supplier<Double> randomSupplier, int count) {
    for (int i = 0; i < count; i ++) {
        System.out.println(randomSupplier.get());
    }
}
```
supplier는 인풋없이 get이라는 리턴값만 있는 메소드만 가진다.

# Consumer (먹보)
```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
}

Consumer<String> myStringConsumer = (s) -> System.out.println(s);
myStringConsumer.accept("Hello World");

// imuatableList
List<Integer> inputs = Arrays.asList(4, 2, 3);
Consumer<Integer> myIntegerProcessor = x -> System.out.println("Processing integer " + x);
//        process(inputs, myIntegerProcessor);

Consumer<Integer> myDifferentIntegerProcessor = x -> System.out.println("Processing integer " + x + " differently");
process(inputs, myDifferentIntegerProcessor);
process(inputs, myIntegerProcessor.andThen(myDifferentIntegerProcessor));

Consumer<Double> myDoubleProcessor = x -> System.out.println("Processing double " + x);
List<Double> doubleInputs = Arrays.asList(4.0, 2.0, 3.0);
process(doubleInputs, myDoubleProcessor);

public static <T> void process(List<T> inputs, Consumer<T> processor) {
    for (T input : inputs) {
        processor.accept(input);
    }
}
```
Consumer와 supplier는 반대이다.  
무언가를 받기만하고 리턴하지 않는 함수형 인터페이스입니다.

# Predicate

```java
@FunctionalInterface
public interface Predicate<T> {

    boolean test(T t);
}
```

이 test 메서드는 T라는 인풋을 받아서 true, false를 리턴하는 함수를 의미한다.

# Comparator - 비교를 위한 인터페이스
```java
@FunctionalInterface
public interface Comparator<T> {
    int compare(T o1, T o2);
}

List<User> users = new ArrayList<>();
users.add(new User(3, "Alice"));
        users.add(new User(1, "Charlie"));
        users.add(new User(2, "Bob"));

        Comparator<User> idComparator = (User u1, User u2) -> {
return u1.getId() - u2.getId();
};

users.sort(idComparator);
System.out.println("users = " + users);

// String이 알파벳순으로 정렬되는 것을 이용한 Comparator
Collections.sort(users, (u1, u2) -> u1.getName().compareTo(u2.getName()));
System.out.println("users = " + users);
```
java.util 패키지에 있는 인터페이스이다.
- 음수면 o1 < o2
- 0이면 o1 == o2
- 양수면 o1 > o2

# Stream
- 데이터의 흐름
- 컬렉션 형태로 구성된 데이터를 람다를 이용해 간결하고 직관적으로 프로세스하게 해줌
- For, While 등을 이용하던 loop를 대체
- 손쉽게 병렬 처리를 할 수 있게 해줌

# Filter
- 만족하는 데이터만 걸러내는데 사용
- Predicate를 인자로 받고 Predicate의 test 메서드를 실행해서 true인 경우만 남긴다.

```java
Stream<T> filter(Predicate<? super T> predicate);
```

# Map
- 데이터를 변환할 때 사용
- 데이터에 해당 함수가 적용된 결과물을 제공하는 stream을 리턴
```java
<R> Stream<R> map(Function<? super T, ? extends R> mapper);
```

# 데이터의 정렬
- 데이터가 순서대로 정렬된 stream을 리턴
- 데이터의 종류에 따라 Comparator가 필요할 수 있음
```java
Stream<T> sorted();
Stream<T> sorted(Comparator<? super T> comparator);

List<Order> collect2 = orders.stream()
    .sorted(Comparator.comparing(Order::getCreatedAt))
    .toList();
```

# Distinct - 중복제거
```java
Stream<T> distinct();
List<Order> orders = Arrays.asList(order1, order2, order3);
List<Long> list1 = orders.stream()
        .map(Order::getCreatedByUserId)
        .distinct()
        .toList();
```
# FlatMap 스트림의 스트림을 납작하게
- Map + Flattern
- 데이터에 함수를 적용한 후 중첨된 stream을 연결하여 하나의 stream으로 리턴
```java
<R> Stream<R> flatMap(
        Function<? super T, ? extends Stream<? extends R>> mapper);

List<OrderLine> list = orders.stream() // Stream<Order>
        .map(Order::getOrderLines) // Stream<List<OrderLine>>
        .flatMap(List::stream) // Stream<OrderLine>
        .toList();
```
# Optional
## NPE - NullPointerException
- Null 상태인 오브젝트를 레퍼런스 할 때 발생
- Runtime error 이기 때문에 실행전 까지는 발생 여부를 알기 쉽지 않음

## Optional - 있을 수도 있고 없을 수도 있다.
- Null 상태인 오브젝트를 레퍼런스 할 때 발생하는 NPE를 방지하기 위해 사용
```java
public static <T> Optional<T> of (T value)
public static <T> Optional<T> ofNullable (T value)
public static <T> Optional<T> ofNullable (T value)
Optional<String> empty = Optional.empty();
```
-of - null이 아닌 값을 가지고 Optional 객체를 생성
-ofNullable - null이 될 수 있는 값을 가지고 Optional 객체를 생성
-empty - 비어있는 Optional 객체를 생성

## Optional의 메서드
```java
public boolean isPresent();
public T get();
public T orElse(T other);
public T orElseGet(Supplier<? extends T> other);
public <X extends Throwable> T orElseThrow(
        Supplier<? extends X> exceptionSupplier) throws X;
```
- isPresent - Optional 객체가 값이 있는지 여부를 반환
- get - Optional 객체의 값을 반환, 값이 없으면 NoSuchElementException 발생
- orElse - Optional 객체의 값을 반환, 값이 없으면 인자로 넘긴 값 반환
- orElseGet - Optional 객체의 값을 반환, 값이 없으면 인자로 넘긴 Supplier의 get 메서드를 실행한 결과 반환
- orElseThrow - Optional 객체의 값을 반환, 값이 없으면 인자로 넘긴 Supplier의 get 메서드를 실행한 결과 반환

# Optional 응용
```java
public void ifPresent(Consumer<? super T> consumer);
public <U> Optional<U> map(Function<? super T, ? extends U> mapper);
public <U> Optional<U> flatMap(
        Function<? super T, ? extends Optional<? extends U>> mapper);
```
- ifPresent - Optional 객체가 값이 있는지 여부를 확인하고 값이 있으면 인자로 넘긴 Consumer의 accept 메서드를 실행
- map - Optional 객체가 값이 있는지 여부를 확인하고 값이 있으면 인자로 넘긴 Function의 apply 메서드를 실행한 결과를 가지고 Optional 객체를 생성
- flatMap - Optional 객체가 값이 있는지 여부를 확인하고 값이 있으면 인자로 넘긴 Function의 apply 메서드를 실행한 결과를 가지고 Optional 객체를 생성


# Max / Min / Count
```java
Optional<T> max(Comparator<? super T> comparator);
Optional<T> min(Comparator<? super T> comparator);
long count();
```

- max - Stream 안의 데이터 중 최대값을 반환, Stream이 비어있다면 빈 Optional을 반환
- min - Stream 안의 데이터 중 최소값을 반환, Stream이 비어있다면 빈 Optional을 반환
- count - Stream 안의 데이터 개수를 반환

# All Match / Any Match
```java
boolean allMatch(Predicate<? super T> predicate);
boolean anyMatch(Predicate<? super T> predicate);
```
- allMatch - Stream 안의 모든 데이터가 predicate를 만족하는지 여부를 반환
- anyMatch - Stream 안의 데이터 중 하나라도 predicate를 만족하는지 여부를 반환

# Find First / Find Any
```java
Optional<T> findFirst();
Optional<T> findAny();
```

- findFirst - Stream 안의 첫번째 데이터를 반환, Stream이 비어있다면 빈 Optional을 반환
- findAny - Stream 안의 아무 데이터나 리턴, 순서가 중요하지 않고 Parallel Stream을 사용할 때 최적화를 할 수 있다.  
마찬가지로 Stream이 비어있다면 빈 Optional을 반환

# Reduce
- 주어진 함수를 반복 적용해 Stream 안의 데이터를 하나의 값으로 합치는 작업
- Max, Min, Count, Sum 등은 reduce를 사용해서 구현할 수 있다.

```java
Optional<T> reduce(BinaryOperator<T> accumulator);
T reduce(T identity, BinaryOperator<T> accumulator);
<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator, BinaryOperator<U> combiner);
```

- reduce1 - 주어진 accumulator 를 이용해 데이터를 합침. Stream이 비어있을 경우 빈 Optional을 반환
- reduce2 - 주어진 초기 값과 accumulaotr를 이용. 초기값이 있기 때문에 항상 반환값이 존재
- reduce3 - 합치는 과정에서 타입이 바뀔 경우 사용. Map + reduce로 대체 가능

# Collectors
- Stream의 데이터를 수집하는 역할
```java
<R, A> R collect(Collector<? super T, A, R> collector);

List<Integer> numberList = Stream.of(1, 2, 3, 4, 5)
        .collect(Collectors.toList());

// 절대값으로 변환
List<Integer> numberList2 = Stream.of(1, 2, -3, 4, 5)
        .collect(Collectors.mapping(x -> Math.abs(x), Collectors.toList()));
Set<Integer> numberSet = Stream.of(1, 2, 3, 4, 5)
        .collect(Collectors.toSet());

// 스트림의 합
int sum = Stream.of(1, 2, 3, 4, 5)
        .collect(Collectors.reducing(0, (x, y) -> x+y));

```

# To Map
```java
public static <T, K, U> Collector<T, ?, Map<K, U>> toMap(
        Function<? super T, ? extends K> keyMapper,
        Function<? super T, ? extends U> valueMapper)
```
- Stream 안의 데이터를 map의 혀앹로 반환해주는 collector
- keyMapper - key를 추출하는 함수
- valueMapper - value를 추출하는 함수

# Grouping By
```java
public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(
        Function<? super T, ? extends K> classifier)
```
- Stream 안의 데이터에 classifier를 적용했을때 결과 값이 같은 값끼리 List로 모아서 Map으로 만들어주는 collector
- 이때 key는 classifier의 결과 값이고 value는 classifier를 적용했을 때 결과 값이 같은 데이터들의 List
- 예를들어 stream에 {1,2,3,4,5,6,7}이 있을때 classifier가 x -> x%3 이라면 반환 되는 map은
- {0=[3, 6], 1=[1, 4, 7], 2=[2, 5]} 이다.

# Partitioning By
```java
public static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(
        Predicate<? super T> predicate)

public static <T, D, A> Collector<T, ?, Map<Boolean, D>> partitioningBy(
        Predicate<? super T> predicate,
        Collector<? super T, A, D> downstream)
```
- Grouping By와 비슷하지만 결과 값이 true, false 두개로 나뉜다.
- 예를들어 stream에 {1,2,3,4,5,6,7}이 있을때 predicate가 x -> x%3 == 0 이라면 반환 되는 map은
- {false=[1, 2, 4, 5, 7], true=[3, 6]} 이다.

# For Each
```java
public void forEach(Consumer<? super T> action);
```
- 제공된 actions 을 Stream의 각 데이터에 적용해주는 종결 처리 메서드

# Parallel Stream - Stream을 병렬로
```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
Stream<Integer> parallelStream = numbers.parallelStream();
Stream<Integer> parallelStream2 = numbers.parallel();
```
- Sequential vs Parallel
- 여러개의 스레드를 이용하여 stream의 처리 과정을 병렬화
- 중간 과정은 병렬 처리 되지만 순서가 있는 Stream의 경우 종결 처리 했을 때의 결과물이 기존의 순차적 처리화  
- 일치하도록 종결 처리과정에서 조정된다. 즉 List로 collect한다면 순서가 항상 올바르게 나온다는것.

## 장점
- 굉장히 간단하게 병렬 처리를 사용할 수 있게 해준다.
- 속도가 비약적으로 빨라질 수 있다.
## 단점
- 항상 속도가 빨라지는것은 아니다.
- 공통으로 사용하는 리소스가 있을 경우 잘못된 결과가 나오거나 아예 오류가 날수도 있다(deadlock)
- 이를 막기위해 mutex, semaphore등 병렬 처리 기술을 이용하면 순차처리보다 느려질 수도 있다.

# Scope Closure Curry
- Scope - 변수가 참조 가능한 범위
- 함수안에 함수가 있을때 내부 함수에서 외부 함수에 있는 변수에 접근이 가능하다(lexical scope) 그 반대는 불가능하다.
```java
public static Supplier<String> getStringSupplier() {
    String hello = "Hello";
    Supplier<String> supplier = () -> {
        String world = "World";
        return hello + " " + world;
    };
    return supplier;
}
```
- Closure - 내부 함수가 존재하는 한 내부 함수가 사용한 외부 함수의 변수들 역시 계속 존재한다.
- 이렇게 lexical scope를 포함하는 함수를 closure라 한다.
- 이 때 내부 함수가 사용한 외부 함수의 변수들은 내부 함수 선언 당시로부터 변할 수 없기 때문에 final로 선언되지 않더라고
- 암묵적으로 final 취급된다.
## Curry
- 여러개의 매개변수를 받는 함수를 중첩된 여러개의 함수로 쪼개어 매개 변수를 한번에 받지 않고 여러단계에 걸쳐 나눠 받을 수 있게 하는기술
- Cloure의 응용
```java
BiFunction<Integer, Integer, Integer> add = (x, y) -> x + y;
// curry를 이용해 중첩된 함수로 쪼갤수 있다. x를 받아 y를 받아 최종적으로 합을 리턴한다.
Function<Integer, Function<Integer, Integer>> add = x -> y -> x + y;
```

# Lazy Evaluation
- Lambda의 계산은 그 결과값이 필요할 때가 되어서야 계산된다.
- 이를 이용하여 불필욯나 계산을 줄이거나 해당 코드의 실행 순서를 의도적으로 미룰 수 있다.