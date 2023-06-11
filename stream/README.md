# Supplier<T>
```java
@FunctionalInterface
public interfate Supplier<T> {
    T get();
}
```
supplier는 인풋없이 get이라는 메소드만 가진다.

# Consymer (먹보)
```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);
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

