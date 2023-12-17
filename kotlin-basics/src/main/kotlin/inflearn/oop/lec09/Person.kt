package inflearn.oop.lec09

fun main() {
    val person = Person("John", 1)
    println(person.name)
    println(person.age)

    val person2 = Person("John")

    val person1 = Person("John", 1)
    println(person1.name)
}

class Person (
    val name: String,
    var age: Int,
) {

    init {
        if (age <= 0) throw IllegalArgumentException("나이는 ${age}보다 작을 수 없습니다.")
    }

    constructor(name: String) : this(name, 1)


    fun  isAdult(): Boolean {
        return age >= 20
    }

    val isAdult2: Boolean
        get() = age >= 20
}