package inflearn.oop.lec12

class Person private constructor(
    val name: String,
    var age: Int,
) {
    companion object {
        private const val MIN_AGE = 1
        fun newBaby(name: String) : Person {
            return Person(name, MIN_AGE)
        }
    }

//    companion object Factory: Log {
//        private const val MIN_AGE = 1
//        fun newBaby(name: String) : Person {
//            return Person(name, MIN_AGE)
//        }
//
//        override fun log() {
//            println("Log")
//        }
//    }
}

interface Log {
    fun log()
}