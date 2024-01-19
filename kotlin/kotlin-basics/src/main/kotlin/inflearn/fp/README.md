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
