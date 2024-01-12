# 변수

변수에는 val, var가 있다.
val은 자바의 final 키워드와 같고 var는 가변 변수와 같다.
하지만 var s : String = "Hello" 와 같이 타입을 지정해서 선언하면 재 할당시 다른 타입으로는 하지 못한다.

## 탑 레벨 변수
```kotlin
var x = 5

fun main() {
    x += 1
    println(x)
}
```
출력을 하면 6으로 정상 출력 된다. 


# 함수
## 기본적인 함수 선언 스타일
```kotlin
fun sum(a: Int, b: Int): Int {
    return a + b
}
```
## 표현식 스타일
```kotlin
fun sum(a: Int, b: Int) : Int = a + b
```

##  표현식 & 반환타입 생략
```kotlin
fun sum3(a: Int, b: Int) = a + b
```

## 몸통이 있는 함수는 반환 타입을 제거하면 컴파일 오류
```kotlin
fun sum4(a: Int, b: Int) : Int {
    return a + b
}
```

## 반환타입이 없는 함수는 Unit을 반환한다
```kotlin
fun printSum(a: Int, b: Int) : Unit {
    println("$a + $b = ${a + b}")
}
```

##  디폴트 파라미터
```kotlin
fun greeting(message: String = "안녕하세요!!") {
    println(message)
}

fun main( ) {
    greeting()
    greeting("HI!!!")
}
```
디폴트 파라미터로 파라미터에 값이 없으면 안녕하세요!!가 출려되고 값이 있으면 HI!!!가 출력된다.

## 네임드 아규먼트
```kotlin
fun log(level: String = "INFO", message: String) {
    println("[$level]$message")
}

fun main( ) {
    log(message = "인포 로그")
    log(level = "DEBUG", "디버그 로그")
    log("WARN", "워닝 로그")
    log(level = "ERROR", message = "에러 로그")
}
```

# 흐름 제어

## if-else
```kotlin
fun main() {
    val age : Int = 10
    
    val str = if (age >= 20) {
        println("성인입니다.")
    } else if (age >= 17) {
        println("고등학생입니다.")
    } else {
        println("학생입니다.")
    }
}
```

## when

```kotlin
fun main() {
    val day = 2

    val result = when (day) {
        1 -> "월요일"
        2 -> "화요일"
        3 -> "수요일"
        4 -> "목요일"
        5 -> "금요일"
        6 -> "토요일"
        7 -> "일요일"
        else -> "알 수 없는 요일"
    }
    print(result)

    // else를 생략할 수 있다.
    when (getColor()) {
        Color.RED -> println("빨간색")
        Color.BLUE -> println("파란색")
        Color.GREEN -> println("초록색")
        else -> println("알 수 없는 색")
    }
    
    // 여러개의 조건을 콤마로 구분해 한줄에서 정의할 수 있다.
    when (getNumber()) {
        0, 1 -> println("0 또는 1")
        else -> println("그 외의 숫자")
    }
}

enum class Color {
    RED, BLUE, GREEN
}

fun getColor() : Color {
    return Color.RED
}

fun getNumber()  = 2
```


## for loof
```kotlin

    // 범위 연산자 ..를 사용해 for loop 돌리기
    for (i in 1..10) {
        println(i)
    }

    for (i in 0 .. 3) { // 0<= .. <= 3
        println(i)
    }

    // until은 뒤에 온 숫자는 포함하지 않는다.
    for (i in 0 until 3) { // 0<= .. < 3
        println(i)
    }

    // step을 사용해 증가폭을 지정할 수 있다.
    for (i in 0 .. 10 step 2) {
        println(i)
    }

    // downTo를 사용해 감소하는 for loop을 만들 수 있다.
    for (i in 10 downTo 0) {
        println(i)
    }

    // downTo와 step을 함께 사용할 수 있다.
    for (i in 10 downTo 0 step 2) {
        println(i)
    }

    // 전달받은 배열을 반복
    val numbers = arrayOf(1, 2, 3, 4, 5)

    for (i in numbers) {
        println(i)
    }
```

## while
```kotlin
// 자바의 while 문과 동일
// 조건을 확인하고 참이면 코드 블록을 실행한 후 다시 조건을 확인
fun main() {
    var x = 0
    while (x < 10) {
        println(x)
        x++
    }
}
```

# 널 안정성
## 1. 널 참조의 위험성
- 자바를 포함한 많은 프로그래밍 언어에서 가장 많이 발생하는 예외 유형이 바로 NullPointerException이다.
- null을 발명한 토니호어는 1965 null을 발면한 후 수십년간 수십억 달러의 시스템 오류와 피해가 발생했기 때문에 1조원 짜리 실수였다고 고백한다.
- 자바에선 NPE를 줄이기 위해 JDK8에서 Optional을 지원하기 시작했다.
- 자바의 옵셔널은 값을 래핑하기 때문에 객체 생성에 따른 오버헤드가 발생하고, 컴파일 단계에서 Null 가능성을 검사하지 않는다.
- 코틀린을 비롯한 최신언어에선 널 가능성을 컴파일러가 미리 감지해서 NPE 가능성을 줄일 수 있다.

```kotlin
fun main() {
//    val a: String = null // 컴파일 오류

//    var b: String? = "abc"
//    b = null // 컴파일 오류
    
    // 널이 될 수 있는 타입을 컴파일러가 추론할 수 있으면 타입을 명시하지 않아도 된다.
    var a : String? = null // String? 타입으로 추론
    println(a?.length) // 안전연산자를 통해 접근하면 NPE 없이 null이 출력된다.
    
    val c = a?.length ?: 0 // 엘비스 연산자를 통해 null일 경우 0을 반환한다.
    println(c)
    
}

fun getNullStr(): String? = null
fun getLengthIfNotNull(str: String?) = str?.length ?: 0

fun main() {
    
    val nullableStr = getNullStr()
    
    val nullableStrLength = nullableStr.length // 컴파일 오류
    
    val nullableStrLength = nullableStr?.length // ok
    val nullableStrLength = nullableStr?.length ?: "null 인경우 반환".length // ok
    
    val length = getLengthIfNotNull(null) // ok -> 0 반환
    
    val c: String? = null
    val d = c!!.length // NPE 발생 단언 연산자는 개발자에게 널 검증을 맡기기 때문에 컴파일 오류 발생 x
    
}
```

# 예외 처리
                Throwable
                    |
        Error                       Exception
          |                       |             |
    Uncheckederrors         RuntimeException
                                |
                            UncheckedExceptions     CheckedExceptions
                                
- Error : 시스템에 비정상적인 상황이 발생, 예측이 어렵고 기본적으로 복구가 불가능함.
  - OutOfMemoryError, StackOverflowError 등 
- Exception : 개발자가 구현한 로직에서 발생하는 예외, 예측이 가능하고 복구가 가능함. 예외처리 강제
  - IoException, SQLException 등
  - @Transactional 어노테이션을 사용하면 RuntimeException이 발생하면 롤백을 하고, CheckedException이 발생하면 롤백을 하지 않는다.
- RuntimeException : 개발자가 구현한 로직에서 발생하는 예외, 예측이 가능하고 복구가 가능함. 예외처리 강제 x
  - NullPointerException, IllegalArgumentException 등 

```kotlin
val a = try {
    "1234".toInt()
} catch (e: Exception) {
    println()
}

print(a)
```

코틀린에서 try catch 또한 표현식이므로 값을 반환할 수 있다.


# 클래스와 프로퍼티
```kotlin
//constructor는 생략할 수 있다.
// 기본적으로 클래스를 생성하면 게터, 세터를 생성해주지만 val 키워드를 사용하면 게터만 생성해준다.
class Coffee constructor(
  var name: String = "",
  var price: Int = 0,
) { 
          
    // 커스텀 게터
    val brand : String
//      get() = "스타벅스" 
    get() {
        return "스타벅스"
    }
  
  var quantity = Int = 0
    set(value) {
        if (value > 0) {  // field는 프로퍼티의 값을 저장하는 데 사용되는 특수한 식별자이다.
            field = value
          // quantity = value 이런식으로 값을 할당하면 무한루프에 빠진다. 왜냐하면 quantity에 값을 할당하면 setter가 호출되기 때문이다.
        }
    }
}

class EmptyClass

fun main() {
    val coffee = Coffee()
  coffee.name = "아이스 아메리카노"
  coffee.price = 2000
  
  println("${coffee.name} 가격은 ${coffee.price}")
}
```


# 상속
코틀린의 최상위 클래스는 Any 라는 클래스이다. 
이 Any라는 클래스에는 equals, hashCode, toString이 있어 모든 클래스에는 이 3가지 메서드가 구현되어있다.
그리고 코틀린의 클래스는 기본적으로 final 클래스이므로 상속이 불가능하다. 그렇기 때문에 상속을 사용하러면 open 키워드를 사용해야한다.

```kotlin
open class Dog {
    open var age: Int = 0
  
  open fun bark() {
      println("멍멍")
  }
}

class Bulldog: Dog() {
    override var age : Int = 0 // override 키워드를 통해 재정의해서 사용한다.
  
  override fun bark() {
      println("불독이 짖는다.")
  }
}

open class Bulldog(override var age: Int = 0): Dog() {

  override fun bark() { // open 클래스의 메서드는 기본적으로 open 메서드 이므로 자식 클래스에서 재정의를 막으러면 final 키워드를 붙인다.
    println("컹컹")
  }
}

class ChildBulldog: Bulldog() { // 기본적으로 open 클래스의 메서드들은 open 메서드이므로 자식 클래스에서 재정의 가능하다.

  override var age: Int = 0
  
  override fun bark() {
   super.bark() // 부모의 메서드를 호출할 때 super 키워드를 사용한다.
  }
}

// 추상 클래스
abstract class Devleoper {
    abstract var age: Int
    abstract fun coded(language: String)
}

class BackendDeveloper(override var age: Int = 0) : Devleoper() {
    
    
  override fun code(language: String) {
      println("$language 언어로 백엔드 개발을 한다.")
  }
}

fun main() { 
    val dog = Bulldog(age = 2)
    println(dog.age) //  2 출력 
    dog.bark()  //  컹컹 출력
  
  val backendDeveloper = BackendDeveloper(age = 28)
  println(backendDeveloper.age) // 28 출력
  backendDeveloper.code("Kotlin") // Kotlin 언어로 백엔드 개발을 한다.
}
```

# 인터페이스

```kotlin
class Product(val name: String, bal price: Int) 

interface Wheel {
    fun roll()
}
interface Cart : Wheel {
    
    var coin: Int
    
    val weight: String 
      get() = "20KG"
    fun add(product: Product) 
    
    fun rent () {
        if (coin > 0) {
            println("카트를 대여합니다.")
        }
    }
  
  override fun roll() {
      println("카트가 굴러갑니다.")
  }

  fun printId() = printId("1234")
}

interface Order {
    fun add(product: Product) {
        println("${product.name}을(를) 주문합니다.")
    }
  
  fun printId() = printId("5678")
}

class MyCart(override var coin: Int) : Cart, Order {
    
    override fun add (product: Product) {
      if (coin <= 0) pringln("코인을 넣엏주세요")
      else println("${product.name}이(가) 카트에 추가했습니다.")
      
      super<Order.add(product)>
    }
  
    override fun printId() {
      super<Cart>.printId()
      super<Order>.printId()
    }
}

fun main() { 
  val cart = MyCart(coin = 1000)
  cart.rent()
  cart.roll()
  cart.add(Product(name = "장난감", price = 1000))
  cart.printId()
}
```