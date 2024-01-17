package inflearn.oop.lec13

class House (
    val address: String,
    val livingRoom: LivingRoom,
) {
    class LivingRoom(
        private val area: Double,
    )
}