/**
 * 1. 컬렉션 타입
 *  코틀린 표준 라이브러리는 기본 컬렉션 타입인 List, Set, Map 을 제공한다.
 *  컬렉션은 두가지 종류로 나뉜다.
 *  - 불변 컬렉션(immutalbe collection) : 읽기 전용 컬렉션
 *  - 가변 컬렉션(mutable collection) : 읽기 쓰기 모두 가능한 컬렉션
 */

fun main() {

    // immutable collection
    val currencyList = listOf("USD", "EUR", "JPY", "KRW")

    // mutable collection
    val mutableList = mutableListOf<String>()
    mutableList.add("USD")
    mutableList.add("EUR")
    mutableList.add("JPY")
    mutableList.add("KRW")


    val currencyMap = mutableMapOf("USD" to "United States Dollar", "EUR" to "Euro", "JPY" to "Japanese Yen", "KRW" to "Korean Won")
    println(currencyMap)
}