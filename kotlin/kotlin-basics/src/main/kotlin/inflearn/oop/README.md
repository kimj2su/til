# 코를린에서 클래스를 다루는 방법 oop - lec09
1. 클래스와 프로퍼티
2. 생성자와 int
3. 커스텀 getter와 setter
4. backing field

## 클래스와 프로퍼티
코를린은 필드를 만들면 자동으로 getter와 setter를 만들어준다.
생성자와 바디를 생략할 수 있다.
```kotlin
class Person (
    val name: String,
    var age: Int,
)

// 위의 코드는 아래와 같다.
class Person2 (
    val name: String, var age: Int
) {
    val name: String,
    var age: Int
}
```

## 생성자와 init
```kotlin
class Person (
    val name: String,
    var age: Int,
) {
    init {
        if (age <= 0) throw IllegalArgumentException("나이는 ${age}보다 작을 수 없습니다.")
    }

    constructor(name: String) : this(name, 1)
}
```
init은 생성자가 호출될 때 실행된다.  
보조 생성자를 만들 때는 반드시 주 생성자를 호출해야 한다.  
기본적으로 default parameter를 지원하기 때문에 보조 생성자를 만들지 않아도 된다.  
Converting과 같은 경우 부생성자를 사용할 수 있지만 그보다는 정적 팩토리 메소드를 추천한다.

## 커스텀 getter와 setter
```kotlin
fun  isAdult(): Boolean {
        return age >= 20
    }
    
val isAdult: Boolean
    get() = age >= 20
val isAdult: Boolean
    get() {
        return age >= 20
    } 
```
함수 대신 프로퍼로도 만들 수 있다.

## backing field
```kotlin
class Person (
    name: String,
    var age: Int,
) {
    val name: String = name
        get() = field.uppercase()
}
```
field는 backing field를 의미한다.  
만약 get() = name.uppercase() 한다면 무한루프에 빠진다.  
자기 자신을 가리키는 보이지 않는 필드라고해서 backing field라고 한다.

# 코틀린에서 상속을 다루는 방법 oop - lec10
1. 추상 클래스
2. 인터페이스
3. 클래스를 상속할 때 주의할 점
4. 상속 관련 지시어 정리

## 추상 클래스
- open 키워드를 붙여야 gettter와 setter를 오버라이딩 할 수 있다.
- 상위 클래스에 접근하는 키워드는 super이다.

## 인터페이스
- default method는 fun 으로 만든다.

## 클래스를 상속할 때 주의할 점
