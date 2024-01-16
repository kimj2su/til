# 테스트 코드는 왜 필요한가?
1. 개발 과정에서 문제를 미리 발견할 수 있다.
2. 기능 추가와 리팩토링을 안심하고 할 수 있다.
3. 빠른 시간 내 코드의 동작 방식과 결과를 확인할 수 있다.
4. 좋은 테스트 코드를 작성하려 하다보면, 자연스럽게 좋은 코드가 만들어 진다.
5. 잘 작성한 테스트는 문서 역할을 한다.(코드리뷰를 돕는다.)

# Kotlin과 JPA를 함께 사용할 때 이야기거리 3가지

## 1. Setter
```kotlin
@Entity
class User(
    var name: String,
    val age: Int,
) {
    fun updateName(name: String) {
        this.name = name
    }
}
```
- 생성자 안의 var 프로퍼티 -> setter 대신 좋은 이름의 함ㅅ를 사용하는 것이 훨씬 clean하다.
- 하지만 name에 대한 setter는 public이기 때문에 유저 이름 업데이트 기능에서 setter를 사용할 수도 있다. -> 코드 상 setter를 사용할 수도 있다는것을 방지한다.

### 방법 1. backing field를 사용한다.
```kotlin
class User(
    private var _name: String,
) {

    val name: String
        get() = _name
}
```

### 방법 2. custom setter를 사용한다.
생성자에서는 파라미터만 받고, 그 파라미터를 setter를 통해 할당한다.
```kotlin
class User(
    name: String
) {
    var name: String = name
        private set
}
```

## 2. 생성자 안의 프로퍼티. 클래스 body 안의 프로퍼티
- 모든 프로퍼티를 생성자에 넣는다.
- 생성자에 넣지 않은 프로퍼티는 클래스 body 안에서 초기화한다.
- 프로퍼티를 생성자 혹은 클래스 body 안에 구분해서 넣을 때 명확한 기준이 있어야한다.

## 3. JPA와 data class
data class는 equals, hashCode, toString, copy를 자동으로 생성해준다.  
Entity는 data class를 피하는 것이 좋다.

## Entity가 생성되는 로직을 찾고 싶다면 constructor 지시어를 명시적으로 작성하고 추적한다.
```kotlin
class User constroctor()
```
위와 같이 작성하면 User 클래스의 인스턴스를 생성할 때, 생성자를 호출하는 코드를 찾을 수 있다.


# Java -> Kotlin 코드
## 1. Class 'UserService' could be implicitly subclassed and must not be final 
- 기본적으로 코틀린은 final로 만들기 때문에 클래스를 상속할 수 없다.
- 그래서 open 키워드를 붙여야한다.
- 매번 open 키워드를 붙이는 것은 번거롭기 때문에, 플러그인으로 자동으로 붙이도록 설정할 수 있다.

```build.gradle
id 'org.jetbrains.kotlin.plugin.spring' version '1.8.22' // 스프링 빈 클래스를 자동으로 열어준다.
```

## 2. CrudRepositoryExtensions
```kotlin
val user = userRepository.findById(request.id).orElseThrow()
```
기존의 코드는 위와 같이 CrudRepository를 사용하기 때문에 Optional을 반환하여 orElseThrow()를 사용했다.

```kotlin
val user = userRepository.findByIdOrNull(request.id) ?: fail()
```
코틀린에서는 확장 함수를 사용하여 findByIdOrNull()를 사용할 수 있다.

확장함수를 통해 더 간결하게 다음가 같이 작성할 수 있다.
```kotlin
fun <T, ID> CrudRepository<T, ID>.findByIdOrThrow(id: ID): T {
    return this.findByIdOrNull(id) ?: fail()
}
```
CrudRepository를 확장함수로 만들어서 사용하면 더 간결하게 사용할 수 있다.


## 3. json 파싱 에러
```kotlin
implementation 'com.fasterxml.jackson.module:jackson-module-kotlin'
```
java code를 kotlin code로 바꿀 때, jackson을 사용하여 json 파싱을 하기 때문에 jackson-module-kotlin을 추가해줘야한다.