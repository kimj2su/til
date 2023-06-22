package generic

class MyGenetics<out T>(val t: T) {
    fun getItem(): T = t
}

class Bag<T> {
    fun saveAll(
        to: MutableList<in T>,
        from: MutableList<T>,
    ) {
        to.addAll(from)
    }
}