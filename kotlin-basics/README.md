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

