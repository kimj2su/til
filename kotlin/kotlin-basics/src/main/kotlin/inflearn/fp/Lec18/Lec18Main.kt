package inflearn.fp.Lec18

import inflearn.fp.lec17.Fruit

fun main() {
    val fruitList: List<List<Fruit>> = listOf(
        listOf(
            Fruit("Apple1", 1000),
            Fruit("Orange", 1500),
            Fruit("Grape", 2000),
            Fruit("Banana", 500),
            Fruit("Watermelon", 3000)
        ),
        listOf(
            Fruit("Apple2", 1000),
            Fruit("Orange", 1500),
            Fruit("Grape", 2000),
            Fruit("Banana", 500),
            Fruit("Watermelon", 3000)
        ),
        listOf(
            Fruit("Apple3", 1000),
            Fruit("Orange", 1500),
            Fruit("Grape", 2000),
            Fruit("Banana", 500),
            Fruit("Watermelon", 3000)
        )
    )

    val samePriceFruits = fruitList.flatMap { list ->
        list.filter { fruit -> fruit.price == 1000 }
    }
    println(samePriceFruits)

    // 확장함수를 통해 위의 코드를 리팩토링
    val samePriceFruits2 = fruitList.flatMap { list ->
        list.samePriceFilter
    }
}
val List<Fruit>.samePriceFilter: List<Fruit>
    get() =
        this.filter(Fruit::isSamePrice)