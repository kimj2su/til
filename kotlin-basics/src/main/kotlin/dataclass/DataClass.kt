package dataclass

/**
 * java 의 dto 목적을 사용할때 코틀린에서는 data class 를 사용한다.
 * equals, toString, hashCode, copy 등의 메소드를 자동으로 생성해준다.
 */
data class Person(val name: String, val age: Int) {
}
fun dataClass() {

    // equals 동등성 비교
    val person1 = Person("홍길동", 20)
    val person2 = Person("홍길동", 20)

    // toString
    println(person1.toString())

    // equals
    println(person1 == person2)

    // hashCode
    val set = hashSetOf(person1)
    println(set.contains(person2))

    // name을 var로 했을 경우 해시코드가 달라진다.
//    person1.name = "김길동"
//    println(set.contains(person2))

    // copy
    val person3 = person2.copy(name = "김길동")
    println(person3)


    println("이름=${person1.component1()}, 나이=${person1.component2()}")

    val (name, age) = person1
    println("이름=${name}, 나이=${age}")
}