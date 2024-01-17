package inflearn.oop.lec14

sealed class HyundaiCar(
    val name: String,
    val price: Long,
) {

    class  Avante: HyundaiCar("아반떼", 20000000L)
    class  Sonata: HyundaiCar("소나타", 30000000L)
    class  Grandeur: HyundaiCar("그랜저", 40000000L)
}