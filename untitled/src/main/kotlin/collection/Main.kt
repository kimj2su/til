

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
}