import dataclass.dataClass
import extension.MyExample
import extension.addFirst
import extension.first
import extension.printNullOrNotNull
import generic.Bag
import generic.MyGenetics
import lazyInitialization.HelloBot
import lazyInitialization.LateInit
import lazyInitialization.getHello
import sealed.*
import sigleton.DatetimeUtils
import sigleton.Myclass
import sigleton.Singleton
import sigleton.singleton
import java.time.LocalDateTime

fun main(args: Array<String>) {

    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")

    println()
    println("=============collection===============")
    // collection()
    collectionPractice()

    println()
    println("=============dataClass===============")
    // dataClass()
    dataClass()

    println()
    println("=============singleton===============")
    // singleton()
    val singleton = Singleton
    singleton.printA()

    // datetime()
    val datetimeUtils = DatetimeUtils
    val now = LocalDateTime.now()
    println( datetimeUtils.same(now, now))

    println(Myclass.a)
    println(Myclass.newInstance())

    println()
    println("=============sealed class===============")
    // sealed
    val backendDeveloper = BackendDeveloper("홍길동")
    DeveloperPool.add(backendDeveloper)
    val frontendDeveloper = FrontendDeveloper("김길동")
    DeveloperPool.add(frontendDeveloper)
//    val otherDeveloper = OtherDeveloper
//    DeveloperPool.add(otherDeveloper)
    val androidDeveloper = AndroidDeveloper("이길동")
    DeveloperPool.add(androidDeveloper)

    println(DeveloperPool.get("홍길동"))
    println(DeveloperPool.get("김길동"))
//    println(DeveloperPool.get("익명"))
    println(DeveloperPool.get("이길동"))

    println()
    println("=============Extension===============")
    println("ABCD".first())
    println("ABCD".addFirst('E'))
    var myExample: MyExample? = null
    myExample.printNullOrNotNull()
    myExample = MyExample()
    myExample.printNullOrNotNull()

    println()
    println("=============Generic===============")
//    val genetics = MyGenetics<String>("Hello")
    val genetics = MyGenetics("Hello")

    // 변수의 타입에 제네릭을 사용한 경우
    val list1: MutableList<String>  = mutableListOf()
    // 타입아규먼트를 생성자에서 추가
    val list2 = mutableListOf<String>()

    val list3: List<*> = listOf<String>("테스트")
    val list4: List<*> = listOf<Int>(1, 2, 3, 4)

    // 변성은 타입아규먼트가 서로 다른 제네릭 타입이 상하위 관계에 있을 때 어떻게 할지 결정하는 것
    // 변셩 PECS(Producer Extends Consumer Super)
    // 공변성은 자바 제네릭의 extends 코틀린은 out
    // 반공변성은 자바 제네릭의 super 코틀린은 in
    // 스트링을 제네릭을 받고 실수로 스트링이 아닌 타입으로 들어가게 되면 컴파일 에러가 난다.
    // 이럴때는 타입 파라미터에 out을 넣어준다.
    val genetics2 = MyGenetics<String> ("Hello")
    val charGenetics : MyGenetics<CharSequence> = genetics2 // String 이 CharSequence의 하위 타입이므로 가능 out 키워드를 사용


    // 반공변성은 자바 제네릭의 super 코틀린은 in 이므로 in을 넣어준다.
    val bag = Bag<String>()
    bag.saveAll(mutableListOf<CharSequence>("1", "2"), mutableListOf<String>("3", "4"))

    println()
    println("=============LazyInitialization===============")
    // LazyInitialization
    val helloBot = HelloBot()
//    helloBot.greeting = getHello()
    helloBot.sayHello()
    helloBot.sayHello()
    helloBot.sayHello()

    for (i in 1..5) {
        Thread {
            helloBot.sayHello()
        }.start()
    }

    val lateInit = LateInit()
    lateInit.init()

    if (!lateInit.textInitialized) {
        println("초기화가 되었습니다.")
    } else {
        println("초기화가 되지 않았습니다.")
    }
}