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
