package inflearn.fp.lec17

fun main() {
    val fruits = listOf(
        Fruit("Apple", 1000),
        Fruit("Orange", 1500),
        Fruit("Grape", 2000),
        Fruit("Banana", 500),
        Fruit("Watermelon", 3000)
    )

    // 람다
    val isApple = fun (fruit: Fruit): Boolean {
        return fruit.name == "Apple"
    }

    // 타입을 명시해주면 더 간단하게 표현할 수 있다.
//    val isApple: (Fruit) -> Boolean = fun (fruit: Fruit): Boolean {
//        return fruit.name == "Apple"
//    }

    val isApple2 = { fruit: Fruit ->
        fruit.name == "Apple"
    }

    isApple(fruits[0])
    isApple.invoke(fruits[0])

    println( filterFruits(fruits, isApple))
    println( filterFruits(fruits) { fruit -> fruit.name == "Apple" } ) // 중괄호가 마지막 파라미터로 들어간다.
    println( filterFruits(fruits) { it.name == "Apple" } ) // 파라미터가 하나일 경우에는 it으로 대체할 수 있다.

}

class Fruit(
    val name: String,
    val price: Int
) {
    override fun toString(): String {
        return "Fruit(name='$name', price=$price)"
    }

    val isSamePrice: Boolean
        get() = this.price == 1000

}

// filter
private fun filterFruits(fruits: List<Fruit>, filter: (Fruit) -> Boolean): List<Fruit> {
    val filteredFruits = mutableListOf<Fruit>()
    for (fruit in fruits) {
        if (filter(fruit)) {
            filteredFruits.add(fruit)
        }
    }
    return filteredFruits
}