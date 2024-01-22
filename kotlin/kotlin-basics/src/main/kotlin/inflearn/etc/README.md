# Type Alias와 as import
- Type Alias
  - 긴 이름의 클래스 혹은 함수 탙입이 있을 때 축약하거나 더 좋은 이름을 쓰고 싶다.
- as import
  - 어떤 클래스나 함수를 임포트 할 때 이름을 바꾸는 기능

```kotlin
import com.aaa.bbb.ccc as ddd

fun main() {
  ddd()  
}
```

# 구조분해와 componetN 함수
- 구조분해
  - 복합적인 값을 분해하여 여러 변수를 한 번에 초기화하는것
  - data class에서 주로 사용
  - name.component1() 처럼 사용 가능
  - componentN() 함수를 사용하여 분해 가능
```kotlin
data class Person(val name: String, val age: Int)
val person = Person("name", 30)
val (name, age) = person
//val name = person.component1()
//val age = person.component2()

println("$name $age")
```
- componentN() 함수
  - data class가 아닌 일반 클래스에서도 사용 가능
  - operator 키워드를 사용하여 componentN() 함수를 오버로딩 할 수 있다.
```kotlin
class Person(
  val name: String, 
  val age: Int
) {
  operator fun component1() = name
  operator fun component2() = age
}
```

# Jump와 Label

- return: 기본적으로 가장 가까운 enclosing function 또는 익명함수로 값이 반환된다.
- break: 가장 가까운 루프가 제거된다.
```kotlin

run {
    numbers.forEach {
        if (it % 2 == 0) {
            return@run
        }
    }
}
```
- continue: 가장 가까운 루프를 다음 step으로 보낸다.
```kotlin
run {
    numbers.forEach {
        if (it % 2 == 0) {
            return@forEach // continue
        }
    }
}
```

코틀린은 라벨이라는 기능을 제공한다.  
특정 expression에 라벨이름@을 붙여 하나의 라벨로 간주하고 break. continue, return 등을 사용하는 기능  
기본적으로 중간의 루프를 빠져나온다 생각하지만 처음 라벨을 만나는 곳으로 이동한다.

# TakeIf와 TakeUnless
- TakeIf
  - 람다식의 결과가 true이면 결과를 반환하고, false이면 null을 반환한다.
- TakeUnless
  - 람다식의 결과가 false이면 결과를 반환하고, true이면 null을 반환한다.

  