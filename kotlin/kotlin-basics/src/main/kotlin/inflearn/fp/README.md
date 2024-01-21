# 코틀린에서 배열과 컬렉션을 다루는 방법 - lec15

## 배열
- 배열은 잘 사용하지 않는다.
- 이펙티브 자바에서도 배열보다는 컬렉션을 사용하라고 권장한다.
## 코틀린에서 Collection - List, Set, Map
- List
  - 불변인지, 가변인지를 설정해주어야 한다.
  - 가변(mutable) 컬렉션 : 컬렉션에 element를 추가, 삭제할 수 있다.
  - 불변 (immutable) 컬렉션 : 컬렉션에 element를 추가, 삭제할 수 없다.
  - Collection.unmodifiableXXX() : 불변 컬렉션을 만들어주는 메소드
  - 우선 불변 리스트를 만들고, 꼭 필요한 경우 가변 리스트로 바꾸자
- Set
  - 순서가 없다.
  - 중복을 허용하지 않는다.
- Map
  - key, value로 이루어진다.
## 컬렉션의 null 가능성
- List<Int?> : 리스트에는 null이 들어갈 수 있지만, 리스트 자체는 null이 될 수 없다.
- List<Int>? : 리스트에는 null이 들어갈 수 없고, 리스트 자체가 null이 될 수 있다.
- List<Int?>? : 리스트에는 null이 들어갈 수 있고, 리스트 자체도 null이 될 수 있다.

# 코를린에서 다양한 함수를 다루는 방법

## 확장함수
### 확장 함수가 나온 배경
- 코틀린은 자바와 100% 호황하는 것을 목표로 한다.
- 기존 자바 코드 위에 자연스럽게 코틀린 코드를 추가할 수 없을까 라는 고민이 생김
- 어떤 클래스안에 있는 메소드처럼 호출할 수 있지만 함수는 밖에 만들 수 있게 하자 라는 개념이 나온다.

```kotlin
fun String.lastChar(): Char {
    return this[this.length - 1]
}
fun 확장하려는클래스.함수이름(파라미터): 리턴타입 {
    //this를 이용해 실제 클래스 안의 값에 접근
}
```
- fun : 함수라는 의미.
- String : 확장할 클래스 -> String 클래스를 확장한다. -> 수신객체타입
- this: 함수 안헤서는 this를 통해 인스턴스에 접근 가능하다. -> 수신객체 

- 확장함수는 멤버 변수와 이름이 같을시 멤버 함수가 호출된다.
- 오버라이드할때 -> 현재타입, 정적인 타입에 의해 어떤 확장함수가 호출될지 결정된다.


## infix 함수(중위 함수)
- 함수를 호출하는 새로운 방법
```kotlin
fun Int.add(other: Int): Int {
    return this + other
}

infix fun Int.add2(other: Int): Int {
    return this + other
}

3.add(4)
3.add2(4)
3 add2 4
```

## inline 함수
- 함수가 호출되는 대신 함수를 호출한 지점에 함수 본문을 그대로 복붙하고 싶은 경우 사용한다.

```kotlin
fun main() {
    3.add(4)
}
inline fun Int.add(other: Int): Int {
    return this + other
}
```
- 바이트코드 상에서 함수를 호출하는 것이 아니라, 함수를 호출한 지점에 함수 본문을 그대로 복붙한다.
- 함수를 호출하는 것보다 빠르다.

## 지역함수
- 함수 안에 함수를 선언할 수 있다.

# 람다

## Java에서 람다를 다루기 위한 노력
- jdk 1.8부터 람다를 지원한다.
- Predicate, Consumer, Function, Supplier, Operator 등의 함수형 인터페이스를 제공한다.
- 스트림이 등장함(병렬처리에 강점이 생김)
- 메소드 자체를 직접 넘겨주는것처럼 사용할 수 있다.
## 코틀린에서의 람다
- 코틀린에서는 함수가 그 자체로 값이 될 수 있다.
- 변수에 할당할수도, 파라미터로 넘길 수 도 있다.
- 코틀린은 함수가 1급시민이다.(자바에서는 2급 시민)
- 마지막 파라미터가 함수인 경우, 소괄호 밖에 람다 사용 가능
- 리턴을 명시해주지 않아도 마지막 줄의 결과가 람다의 반환 값이다.

## Closure
- 자바에서는 람다를 쓸 때 사ㅛㅇ할 수 있는 변수에 제약이 있다. -> final이거나 final처럼 쓰이는 변수만 사용할 수 있다.
- 코틀린에서는 람다가 시작하는 지점에 참조하고 있는 변수들을 모두 포획하여 그 정보를 가지고 있다.
- 이렇게 해야만 람다를 일급 시민으로 간주 할 수 있다. 이 데이터 구조를 클로저라고 한다.

## try-with-resource
- 자바에서는 try-with-resource를 사용하면 자동으로 close()를 호출해준다.
- 코틀린에서는 use()를 사용하면 자동으로 close()를 호출해준다.

# 컬렉션을 함수형으로 다루는 방법

## 필터와 맵

```kotlin
val apples = fruits.filter { it == "사과" }
val apples = fruits.filterIndexed { index, s -> 
    println(index), 
    s.name == "사과" 
}

// 사과의 가격만 가져오기
val applePrices = fruits
    .filter {fruit -> fruit.name == "사과"}
    .map { it.price }

val applePrices = fruits
    .filter {fruit -> fruit.name == "사과"}
    .mapIndexed { index, fruit -> 
        println(index)
        fruit.price
    }

// 매핑의 결과가 null이 아닌 것만 가져오기
val applePrices = fruits
    .filter {fruit -> fruit.name == "사과"}
    .mapNotNull { it.price }
//    .mapNotNull { fruit -> fruit.nullOrValue() }
```

## 다양한 컬렉션 처리 기능
- 모든 조건을 만족할 때 : all
```kotlin
val isAllPriceHigherThan1000 = fruits.all { it.price > 1000 }
```
- 조건을 모두 불만족하면 true 그렇지 않으면 false : none
```kotlin
val isAllPriceHigherThan1000 = fruits.none { it.price > 1000 }
```
- 하나라도 조건을 만족하면 true 그렇지 않으면 false : any
```kotlin
val isAllPriceHigherThan1000 = fruits.any { it.price > 1000 }
```
- count
```kotlin
val isAllPriceHigherThan1000 = fruits.count { it.price > 1000 }
```
- sortedBy: (오름차순) 정렬을 한다.
```kotlin
val isAllPriceHigherThan1000 = fruits.sortedBy { it.price }
```
- sortedByDescending: (내림차순) 정렬을 한다.
```kotlin
val isAllPriceHigherThan1000 = fruits.sortedByDescending { it.price }
```
- distinctBy: 변형된 값을 기준으로 중복을 제거한다.
```kotlin
val isAllPriceHigherThan1000 = fruits.distinctBy { it.price }
```
- 조건을 만족하는 첫번째 element를 반환(무조건 null이 아니여야한다.) : first
- 조건을 만족하는 첫번째 element를 반환(조건을 만족하는 element가 없으면 null) : firstOrNull
```kotlin
val isAllPriceHigherThan1000 = fruits.first { it.price > 1000 }
val isAllPriceHigherThan1000 = fruits.firstOrNull { it.price > 1000 }
```
- 조건을 만족하는 마지막 element를 반환(무조건 null이 아니여야한다.) : last
- 조건을 만족하는 마지막 element를 반환(조건을 만족하는 element가 없으면 null) : lastOrNull
```kotlin
val isAllPriceHigherThan1000 = fruits.last { it.price > 1000 }
val isAllPriceHigherThan1000 = fruits.lastOrNull { it.price > 1000 }
```


## List를 Map으로
- groupBy : 조건을 기준으로 그룹핑이 된다.
```kotlin
val map: Map<String, List<Fruit>> = fruits.groupBy {fruit -> fruit.name}
val map: Map<String, List<Long>> = fruits.groupBy({ fruit -> fruit.name}, {fruit -> fruit.price})
```
- associateBy : 조건을 기준으로 그룹핑이 된다. -> value가 하나만 나온다.(중복이 없을때 사용)
```kotlin
val map: Map<String, Fruit> = fruits.associateBy {fruit -> fruit.name}
val map: Map<String, Long> = fruits.associateBy({ fruit -> fruit.name}, {fruit -> fruit.price})
```

- filter
- 사과 -> List<사과>만 잇게 된다.
```kotlin
val map: Map<String, List<Fruit>> = fruits.groupBy {fruit -> fruit.name}
  .filter{(key, value) -> key == "사과"}
```
## 중첩된 컬렉션 처리
- flatMap : 중첩된 컬렉션을 한번에 펼쳐준다.
- List<List<Fruit>> -> List<Fruit> : flatten
```kotlin
val fruits = listOf(
    listOf(Fruit("사과", 1000), Fruit("배", 2000)),
    listOf(Fruit("딸기", 3000), Fruit("키위", 4000))
)
val flatFruits = fruits.flatten()
```
