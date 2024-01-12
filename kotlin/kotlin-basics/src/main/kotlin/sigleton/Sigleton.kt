package sigleton

import java.time.LocalDateTime

/**
 * 싱글톤 패턴은 클래스의 인스턴스를 하나의 단일 인스턴스로 제한하는 디자인 패턴이다.
 * 싱글톤 패턴을 구현할때는 몇가지 제약사항을 통해 구현한다.
 * 직접 인스턴스화 하지 못하도록 생성자를 private으로 숨긴다.
 * getInstane() 라는 클래스의 단일 인스턴스를 반환하는 static 메서드를 제공한다.
 * 멀티-스레드 환경에서도 안전하게 유일한 인스턴스를 반환해야한다.
 *
 * 다양한 구현 방법들
 * 1. DCL(Double-checked locking)
 *      - JVM 환경에선 거의 사용안함.
 * 2. Enum 싱글톤
 *      - 이펙티브 자바에서 소개 -> 실무에선 잘 사용안함.
 * 3. 이른 초기화
 * 4. 지연 초기화
 *
 * 자바에서 많이 쓰이는 구현 방식
 * 1. Eager initialization
 * public class Singleton {
 *   private static final Singleton instance = new Singleton();
 *   private Singleton() {}
 *   public static Singleton getInstance() {
 *      return instance;
 *   }
 * }
 *
 * 2. 지연 초기화
 * public class Singleton {
 *  private Singleton() {}
 *  public Singleton getInstance() {
 *      return SingletonHolder.instance;
 *  }
 *  private static class LAzyHolder {
 *    private static final Singleton instance = new Singleton();
 *  }
 * }
 */

object Singleton {

    val a = 1234

    fun printA() = println(a)
}

object DatetimeUtils {

    val now : LocalDateTime
        get() = LocalDateTime.now()

    const val DATE_FORMAT = "yyyy-MM-dd"   // 자바의 상수

    fun same(a: LocalDateTime, b: LocalDateTime) : Boolean {
        return a == b
    }
}

class Myclass {

    private constructor()

    companion object MyCompainion {
        val a = 1234

        fun newInstance() = Myclass()
    }
}
fun singleton() {

}