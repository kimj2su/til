package inflearn.oop.lec14

fun handlerCountry(contry: Country) {
    when (contry) {
        Country.KOREA -> println("한국")
        Country.JAPAN -> println("일본")
    }
}
enum class Country(
    private val code: String,
) {

    KOREA("KR"),
    JAPAN("JP"),
    ;
}