package extension

fun String.first() : Char {
    return this[0]
}

class MyExample {
    fun printMessage() = println("확장 출력")
}

// 널 가능성있는 확장 함수
fun MyExample?.printNullOrNotNull() {
    if (this == null) {
        println("null")
    } else {
        println("널이 아님")
    }
}
fun String.addFirst(char: Char) :String {
    return char + this.substring(0)
}