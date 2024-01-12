package lazyInitialization

/**
 * 지연 초기화란
 * 지연초기화는 대상에 대한 초기화를 미뤘다가 실제 사용시점에 초기화하는 기법을 말한다.
 * 초기화 과정에서 지원을 많이 쓰거나 오버헤드가 발생할 경우 지연초기화를 사용하는게 유리할 수 있다.
 * 지연초기화는 많은 상황에서 쓰리고 있다.
 * - 웹페이지에서 특정 스크롤에 도달했을때 컨텐츠를 보여준느 무한 스크롤
 * - 싱글톤 패턴의 지연초기화
 * public class Singleton {
 * private Singleton() {}
 * public Singleton() {
 *    return SingletonHolder.instance;
 * }
 * private static class LazyHolder {
 *   private static final Singleton instance = new Singleton();
 *   }
 * }
 * jpa의 지연로딩
 * @OneToMany(fetch = FetchType.LAZY)
 */


class HelloBot {

    // multithread 에서도 안전하게 동작한다.
    val greeting: String by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        println("초기화 로직 수행")
        getHello()
    }

    fun sayHello() = println(greeting)
}

fun getHello() = "Hello"

class LateInit {
    //lateinit 은 var 변수에만 사용할 수 있다. 항상 not null
    lateinit var name: String
    fun init() {
        name = "안녕하세요"
        if (this::name.isInitialized) {
            println("초기화가 되었습니다.")
        } else {
            println("초기화가 되지 않았습니다.")
        }
        println(name)
    }

    val textInitialized : Boolean
        get() = this::name.isInitialized
}