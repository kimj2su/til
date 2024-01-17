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

# 코틀린에서 접근 제어를 다루는 방법
## Java
|접근 제한자|접근 가능 구역|
|---|---|
|public|모든 곳|
|protected|같은 패키지, 상속 관계|
|default|같은 패키지|
|private|같은 클래스|

## Kotlin
|접근 제한자| 접근 가능 구역     |
|---|--------------|
|public| 모든 곳         |
|protected| 같은 모듈, 상속 관계 |
|internal| 같은 모듈        |
|private| 같은 클래스       |

- Kotlin에서 패키지는 namespace 관리용이기 때문에 protected는 의미가 달라졌다.
- default가 사라지고 , 모듈간의 접근을 통제하는 internal이 생겼다.
- 생성자에 접근 지시어를 붙일 때에는 constructor를 명시적으로 써주어야 한다.
- 유틸성 함수를 만들 때는 파일 최상단을 이용하면 편하다.
- 프로퍼티의 custom setter에 접근 지시어를 붙일 수 있다.
- Java에서 코틀린의 internal과 protected는 주의 해야 한다.

# 코틀린에서 object를 다루는 방법 - lec12
## 1. static 함수와 변수
- companion object를 사용한다. -> 클래스와 동행하는 유일한 객체
- const val은 static final과 같다. -> 컴파일 시점에 값이 결정된다.
- 이름을 붙일 수도 있고 인터페이스도 구현할 수 있다.
- 1개의 클래스에 1개의 companion object만 만들 수 있다.
- @JvmStatic을 붙이면 자바에서도 static으로 사용할 수 있다.

## 2. 싱글톤
- object를 사용한다.

## 3. 익명 클래스
```kotlin
moveSomething(object : OnClickListener {
    override fun onClick() {
        println("익명 클래스")
    }
})
```

# 코틀린에서 중첩 클래스를 다루는 방법 - lec13
- 클래스 안에 클래스가 있는 경우는 두가지 이다.
  - (Java 기준) static을 사용하는 클래스
  - (Java 기준) static을 사용하지 않는 클래스
- 권장되는 클래스는 static을 사용하는 클래스이다.
- 코틀린에서 이런한 가이드를 따르기 위해 클래스 안에 기본 클래스를 사용하면 바깥 클래스에 대한 참조가 없다.
- 바깥 클래스를 참조하고 싶으면 inner 키워드를 붙여야 한다. -> this@OuterClass

# 코틀린에서 다양한 클래스를 다루는 방법 - lec14
## 1. data class
```kotlin
data class PersonDto(
    val name: String,
    val age: Int,
) {
}
```
- toString(), equals(), hashCode(), copy()를 자동으로 만들어준다.


## 2. Enum class
```kotlin
fun handlerCountry(contry: Country) {
    when (contry) {
        Country.KOREA -> println("한국")
        Country.JAPAN -> println("일본")
    }
}
enum class Country(
    private val code: String,
) {

    KOREA("KR"),
    JAPAN("JP"),
    ;
}
```

## 3. Sealed class

```kotlin
sealed class HyundaiCar(
    val name: String,
    val price: Long,
) {
    
    class  Avante: HyundaiCar("아반떼", 20000000L)
    class  Sonata: HyundaiCar("소나타", 30000000L)
    class  Grandeur: HyundaiCar("그랜저", 40000000L)
}
```
- 상속이 가능하도록 추상 클래스를 만들까 했는데 외부에서는 이 클래스를 상속 받지 않았으면 좋겠다.는 니즈에서 나옴
- 컴파일 타임 때 하위 클래스의 타입을 모두 기억한다. -> 런타임때 클래스 타입이 추가될 수 없다.
- 하위클래스는 같은 패키지에 있어야한다.
- enum과 다른점
    - enum은 상수를 정의하는 것이 목적
    - sealed class는 상속을 통해 클래스를 확장하는 것이 목적
    - 클래스를 상속받을 수 있다.
    - 하위 클래스는 멀티 인스턴스가 가능하다.
- when과 함께 사용하면 좋다.









