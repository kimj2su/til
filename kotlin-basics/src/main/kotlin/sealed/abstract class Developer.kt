package sealed

/**
 * sealed class 는 enum class 와 비슷하다.
 * 하나의 상위 클래스 또는 인터페이스에서 하위클래스의 정의를 제안할 수 있는 방법이다.
 * sealed class 는 같은 패키지 내에서만 상속이 가능하다.
 */
sealed class Developer {
    abstract val name: String
    abstract fun code(language: String)
}

data class BackendDeveloper(override val name: String) : Developer() {

    override fun code(language: String) {
        println("저는 백엔드 개발자입니다 ${language}를 사용합니다.")
    }
}

data class FrontendDeveloper(override val name: String) : Developer() {

    override fun code(language: String) {
        println("저는 프론트엔드 개발자입니다 ${language}를 사용합니다.")
    }
}

object OtherDeveloper : Developer() {

    override val name: String = "익명"

    override fun code(language: String) {
//        println("저는 개발자가 아닙니다.")
    }
}


data class AndroidDeveloper(override val name: String) : Developer() {

    override fun code(language: String) {
        println("저는 안드로이드 개발자입니다 ${language}를 사용합니다.")
    }
}

object DeveloperPool {
    val pool = mutableMapOf<String, Developer>()

    // else를 뺴면 컴파일 에러가 난다. -> Developer는 두개만 구현한지 모르기 때문에
    // 그래서 abstract class Developer 를 sealed class Developer 로 바꿔주면 컴파일 에러가 안난다.
    fun add(developer: Developer) = when(developer) {
        is BackendDeveloper -> pool[developer.name] = developer
        is FrontendDeveloper -> pool[developer.name] = developer
        is AndroidDeveloper -> pool[developer.name] = developer
        is OtherDeveloper -> println("지원하지 않는 개발자종류입니다.")
//        else -> println("지원하지 않는 개발자종류입니다.")
//        else -> {println("지원하지 않는 개발자종류입니다.")
//            println("개발자가 아닙니다.")
//        }
    }

    fun get(name: String) = pool[name]
}

