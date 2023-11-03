package inflearn.section1


fun main() {

    var number1: Long = 10L
    val number2: Long = 20L

    println(number1)

    var number3: Long? = 1000L
    number3 = null

    val str: String? = "ABC"
    println(str?.length)

    val str2: String? = null
    println( str2?.length ?: 0)

    val person = Variable.Person("김지수")


    // 타입을 다루는 방법
    val number4: Int? = 3
    val number5 = number4?.toLong() ?: 0L
    println(number5)

    printNameIfPerson2(null)
    val name = "김지수"
    val age = 27
    val log = "내 이름은: $name, 나이: $age"
    println(log)

    val string = """
        안녕
        
        1
        """.trimIndent()
    println(string)

    val str3 = "A"
    val str4 = "B"
    println(str3 > str4)

    val person1 = Person(10)
    val person2 = Person(20)
    println(person1 + person2)
}

fun startWithA1(str: String?): Boolean {
    if (str == null) {
        throw IllegalArgumentException("str must not be null")
    }

    return str.startsWith("A")
}

data class Person(
    val age: Int
) {
    operator fun plus(person: Person): Person {
        return Person(age + person.age)
    }
}
fun startWithA2(str: String?): Boolean {
    if (str == null) {
        return false
    }
    return str.startsWith("A")
}

fun startWithA3(str:String?): Boolean {
    if (str == null) {
        return  false
    }

    return str.startsWith("A")
}

fun startWithA4(str: String?): Boolean {
    return str?.startsWith("A") ?: false
}

// 타입을 다루는 방법
fun printNameIfPerson(obj: Any) {
    if (obj is Variable.Person) {
        val person = obj as Variable.Person
        println(person.name)
    }
}

fun printNameIfPerson2(obj: Any?) {
    val person = obj as? Variable.Person
    println(person?.name)
}