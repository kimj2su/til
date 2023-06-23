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

