package inflearn.fp.lec15

fun main() {
    val array = arrayOf(1, 2, 3, 4, 5)

    // 인덱스를 활용
    for (i in array.indices) {
        println(i)
    }

    // 인덱스와 값을 함께 활용
    for ((idx, value) in array.withIndex()) {
        println("$idx : $value")
    }

    // 배열에 값을 추가
    array.plus(6)

    // 컬렉션 만들기
    val numbers = listOf(1, 2, 3, 4, 5) // 불변 리스트
    val emptyList = emptyList<Int>() // 빈 리스트를 선언할 때는 타입을 명시해야 한다.
    printNumbers(emptyList) // 타입을 추론할 수 있으면 타입을 생략할 수 있다.

    // 컬렉션 값 가져오지
    println(numbers[0]) // 인덱스로 접근
    for ((idx, value) in numbers.withIndex()) {
        println("$idx : $value")
    }

    // 가변 리스트
    val mutableList = mutableListOf<Int>()
    mutableList.add(1)

    // set
    val set = setOf(1, 2, 3, 4, 5) // 불변 set
    val mutableSet = mutableSetOf<Int>() // 가변 set

    // for each
    set.forEach { println(it) }
    for ((index, value) in mutableSet.withIndex()) {
        println("$index : $value")
    }

    // map
    val oldMap = mutableMapOf<Int, String>()
    oldMap[1] = "hello"
    oldMap[2] = "world"

    mapOf(1 to "hello", 2 to "world") // 불변 map, 중위 호출

    for (key in oldMap.keys) {
        println("$key : ${oldMap[key]}")
    }

    for ((key, value) in oldMap) { // oldMap.entries
        println("$key : $value")
    }
}

private fun printNumbers(numbers: List<Int>) {
    for (number in numbers) {
        println(number)
    }
}