# 리플렉션 API  - 클래스 정보 조회
- 리플렉션의 시작은 Class<T> 타입이다.
- Class<T>에 접근하는 방법
  - 모든 클래스를 로딩 한 다음 Class<T>의 인스턴스가 생긴다. "타입.class"로 접근할 수 있다.
  - 모든 인스턴스는 getClass() 메서드를 가지고 있다. "인스턴스.getClass()"로 접근할 수 있다.
  - 클래스를 문자열로 읽어오는 방법
    - Class.forName("패키지명을 포함한 클래스명")
    - 클래스패스에 해당 클래스가 없다면 ClassNotFoundException 예외가 발생한다.
- Class<T>를 통해 할 수 있는 것
  - 클래스의 모든 정보를 가져올 수 있다.
  - 생성자 정보 가져오기
  - 필드 정보 가져오기
  - 메서드 정보 가져오기
  - 부모 클래스 가져오기
  - 구현 인터페이스 가져오기
  - 애노테이션 가져오기

## 클래스의 필드 정보 가져오기
```java
Class<Book> bookClass = Book.class;
Arrays.stream(bookClass.getDeclaredFields()).forEach(System.out::println);
// getDeclaredFields()는 해당 클래스의 모든 필드를 가져온다.

Book book = new Book();
Arrays.stream(bookClass.getDeclaredFields())..forEach(f -> {
   try {
         f.setAccessible(true); // private 필드에 접근하기 위해
         System.out.printf("%s %s\n", f, f.get(book));
    } catch (IllegalAccessException e) {
         e.printStackTrace();
   } 
});
```

## 메소드 가져오기
```java
Class<Book> bookClass = Book.class;
Arrays.stream(bookClass.getDeclaredMethods()).forEach(System.out::println);
```

## 생성자 가져오기
```java
Class<Book> bookClass = Book.class;
Arrays.stream(bookClass.getDeclaredConstructors()).forEach(System.out::println);
```

## 부모 클래스 가져오기
```java
Class<Book> bookClass = Book.class;
Class<? super Book> superclass = bookClass.getSuperclass();
```

## 인터페이스 가져오기
```java
Class<Book> bookClass = Book.class;
Arrays.stream(bookClass.getInterfaces()).forEach(System.out::println);
```

## 접근제한자 확인
```java
Class<Book> bookClass = Book.class;
Arrays.stream(bookClass.getDeclaredFields()).forEach(f -> {
    int modifiers = f.getModifiers();
    System.out.println(f);
    System.out.println(Modifier.isPrivate(modifiers));
    System.out.println(Modifier.isStatic(modifiers));
});
```


# 어노테이션과 리플랙션
## 어노테이션
- @Retention : 해당 애노테이션을 언제까지 유지할 것인가?
  - SOURCE : 소스 코드까지만 유지. 컴파일하면 사라짐.
  - CLASS : 컴파일한 .class 파일에도 유지. 하지만 리플렉션을 이용해서는 접근할 수 없다. (기본값)
  - RUNTIME : 컴파일한 .class 파일에도 유지. 리플렉션을 이용해서 접근할 수 있다.
- @Inherit : 해당 애노테이션을 하위 클래스까지 전달할 것인가?
- @Target : 어디에 사용할 수 있는가?
  - TYPE : 클래스, 인터페이스, enum에서 사용할 수 있다.
  - FIELD : 필드에서 사용할 수 있다.
  - METHOD : 메소드에서 사용할 수 있다.
  - PARAMETER : 파라미터에서 사용할 수 있다.
  - CONSTRUCTOR : 생성자에서 사용할 수 있다.
  - LOCAL_VARIABLE : 지역변수에서 사용할 수 있다.
  - ANNOTATION_TYPE : 애노테이션에서 사용할 수 있다.
  - PACKAGE : 패키지에서 사용할 수 있다.
  - TYPE_PARAMETER : 타입 파라미터에서 사용할 수 있다.
  - TYPE_USE : 타입 사용시 사용할 수 있다.

애노테이션은 기본적으로 소스에도 남고 클래스파일에도 있지만 바이트 코드를 로딩했을때는 메모리 상에는 남지 않는다.  
@Retention을 RUNTIME으로 설정하면 바이트 코드를 로딩했을때도 메모리 상에 남는다.  
기본값은 CLASS이다.


## 리플렉션
- getAnnotations() : 상속받은 것까지 다 가져온다.
- getDeclaredAnnotations() : 해당 클래스에 붙어있는 애노테이션만 가져온다.


# 리플렉션 API  - 클래스 정보 수정 또는 실행
## Class 인스턴스 만들기
- Class.newInstance()는 deprecated 되었다.
- 생성자를 통해서 만들어야 한다.

## 생성자로 인스턴스 만들기
- Constructor.newInstance(params) : 해당 생성자를 이용해서 인스턴스를 만든다.
```java
// 기본 생성자를 이용해서 인스턴스를 만든다.
Class<?> bookClass = Class.forName("com.example.Book");
Constructor<?> constructor = bookClass.getConstructor();
Book book = (Book) constructor.newInstance();
System.out.println(book);

// 파라미터가 있는 생성자를 이용해서 인스턴스를 만든다.
Class<?> bookClass = Class.forName("com.example.Book");
Constructor<?> constructor = bookClass.getConstructor(String.class);
Book book = (Book) constructor.newInstance("myBook");
System.out.println(book);
```
## 필드 값 접근하기/설정하기
- 특정 인스턴스가 가지고 있는 값을 가져오는 것이기 때문에 인스턴스가 필요하다.
- Field.get(인스턴스)
- Field.set(인스턴스, 값)
- private 필드에 접근할 수 있도록 setAccessible(true)를 해줘야 한다.
- static 필드를 가져올 때는 object가 필요없다.

## 메소드 실행하기
- Object Method.invoke(인스턴스, params)

# DI 프레임워크 만들기
@Inject라는 애노테이션 만들어서 주입 해주는 컨테이너 서비스 만들기

```java
import java.awt.print.Book;

public class BookService {
  @Inject
  BookRepository bookRepository;
}

public static <T> T getObject(T classType) {
  T instance = createInstance(classType);
  Arrays.stream(classType.getDeclaredFields())
          .filter(field -> field.getAnnotation(Inject.class) != null)
          .forEach(field -> {
            Object fieldInstance = createInstance(field.getType());
            field.setAccessible(true);
            try {
              field.set(instance, fieldInstance);
            } catch (IllegalAccessException e) {
              throw new RuntimeException(e);
            }
          });
  return instance;
}
```
- classType에 해당하는 타입의 객체를 만들어 준다.
- 단, 해당 객체의 필드 중에 @Inject가 붙어있는 필드가 있다면 해당 필드도 객체를 만들어서 넣어준다.


# 리플렉션 정리
## 리플렉션 사용시 주의할 것
- 지나친 사용은 성능 이슈를 야기할 수 있다. 반드시 필요한 경우에만 사용할 것
- 컴파일 타임에 확인되지 않고 런타임 시에만 발생하는 문제를 만들 가능성이 있다.
- 접근 지시자를 무시할 수 있다.

## 스프링
- 의존성 주입
- MVC 뷰에서 넘어온 데잍를 객체에 바인딩 할 때

## 하이버네이트
- @Entity 클래스에 Setter가 없다면 리플렉션을 사용한다.