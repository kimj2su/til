# Live Template
settings -> Live Templates  
단축키 커맨드 + ,
1. 폴더 생성 -> + 버튼 클릭
2. asj, ast, gwt -> Statement
3. tdd -> Declaration
### asj
```java
@org.assertj.core.api.Assertions

org.assertj.core.api.Assertions.assertThat($EXPR$)
```
### ast
```java
@org.assertj.core.api.Assertions

org.assertj.core.api.Assertions.assertThatThrownBy($EXPR$)
```
### gwt
```java
// given : 선행조건 기술

// when : 기능 수행

// then : 결과 확인
```
### tdd
```java
@org.junit.jupiter.api.DisplayName("")
@org.junit.jupiter.api.Test
void $NAME$() {
    // given : 선행조건 기술
    $END$
    // when : 기능 수행

    // then : 결과 확인
}
```

# Plugin
## Nyan Progress Bar
귀여운 고양이가 돌아 다닌다,,
## One Dark theme
글씨 알록달록하게 보인다.
## Presentation Assistant for 2023.2
누른 단축키를 보여준다.
## String Manipulation
문자열을 다양하게 변경해준다. ex) increment, decrement, switch(대소문자 변경,camelCase, snakeCase) 등등
## Translation 
번역을 해준다. control + command + U
## Key Promoter X
단축키를 보여준다. -> Presentation Assistant for 2023.2와 중복되므로 둘 중 하나만 사용
## Git ToolBox
커밋을 누가 했는지 확인 가능
## GitHub Copilot
깃 허브가 코드를 추천해준다.
## Atom Matertial Icons
디렉터리 및 파일 아이콘을 변경해준다.
## AsciiDoc
asciidoc 문법을 지원해준다.
## Redis Helper
redis를 쉽게 사용할 수 있게 해준다.

