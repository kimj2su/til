import java.util.*

/**
 * 1. 컬렉션 타입
 *  코틀린 표준 라이브러리는 기본 컬렉션 타입인 List, Set, Map 을 제공한다.
 *  컬렉션은 두가지 종류로 나뉜다.
 *  - 불변 컬렉션(immutable collection) : 읽기 전용 컬렉션
 *  - 가변 컬렉션(mutable collection) : 읽기 쓰기 모두 가능한 컬렉션
 */

fun collectionPractice() {

    // immutable collection
    val currencyList = listOf("USD", "EUR", "JPY", "KRW")

    // mutable collection
    val mutableList = mutableListOf<String>()
    mutableList.add("USD")
    mutableList.add("EUR")
    mutableList.add("JPY")
    mutableList.add("KRW")

    val mutableList2 = mutableListOf<String>().apply {
        add("USD")
        add("EUR")
        add("JPY")
        add("KRW")
    }

    // immutable set
    val currencySet = setOf("USD", "EUR", "JPY", "KRW")
    val mutableSet = mutableSetOf<Int>().apply {
        add(1)
        add(2)
        add(3)
    }

    // immutable map
    val currencyMap = mutableMapOf("USD" to "United States Dollar", "EUR" to "Euro", "JPY" to "Japanese Yen", "KRW" to "Korean Won")
    val mutableNumberMap = mutableMapOf<Int, String>().apply {
        put(1, "one")
        put(2, "two")
        put(3, "three")
    }

    val mutableNumberMap2 = mutableMapOf<String, Int>()
    mutableNumberMap2["one"] = 1
    mutableNumberMap2["two"] = 2
    mutableNumberMap2["three"] = 3

    // 컬렉션 빌더 내부에선 mutable 반환은 immutable 컬렉션으로 변환되어 반환된다.
    val numberList : List<Int> = buildList{
        add(1)
        add(2)
        add(3)
    }

    // linked list
    val linkedList = LinkedList<Int>().apply {
        addFirst(3)
        add(2)
        addLast(1)
    }

    val arrayList = arrayListOf<Int>().apply {
        add(1)
        add(2)
        add(3)
    }

    val iterator = currencyList.iterator()
    while(iterator.hasNext()) {
        println(iterator.next())
    }

    println("=================")

    for(currency in currencyList) {
        println(currency)
    }

    println("=================")

    currencyList.forEach {
        println(it)
    }

    println("=================")

    //for loop -> map
    val lowerList = listOf("a", "b", "c")
//    val upperList = mutableListOf<String>()

//    for (lower in lowerList) {
//        upperList.add(lower.uppercase())
//    }

    val upperList = lowerList.map { it.uppercase() }
    println(upperList)

    println("=================")

//    val filteredList = mutableListOf<String>()
//    for (upperCase in upperList) {
//        if(upperCase == "A" || upperCase == "C") {
//            filteredList.add(upperCase)
//        }
//    }

    val filteredList = upperList.filter { it == "A" || it == "C" }
    println(filteredList)

    println("=================")
}