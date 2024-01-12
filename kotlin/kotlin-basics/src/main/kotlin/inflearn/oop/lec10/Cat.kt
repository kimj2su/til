package inflearn.oop.lec10

class Cat (
    species: String
) : Animal(species, 4) {

    override fun move() {
        println("고양이가 달립니다.")
    }
}