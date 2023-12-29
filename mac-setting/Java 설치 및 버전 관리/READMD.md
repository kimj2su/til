# Homebrew로 java 설치
```kotlin
brew search openjdk
brew install openjdk@11
brew install openjdk@17
```

# java 설치된 곳 확인하기
```zsh
/usr/libexec/java_home -V
```

# 자바 버전 바꾸기
```zsh
open ~/.zshrc


# Java Paths
export JAVA_HOME_11=$(/usr/libexec/java_home -v11)
export JAVA_HOME_17=$(/usr/libexec/java_home -v17)

# Java 11
# export JAVA_HOME=$JAVA_HOME_11

# Java 17
# 17버전을 사용하고자 하는 경우 아래 주석(#)을 해제하고 위에 11버전을 주석처리 하면된다.
export JAVA_HOME=$JAVA_HOME_17

alias java11='export JAVA_HOME=$JAVA_HOME_11'
alias java17='export JAVA_HOME=$JAVA_HOME_17'

source ~/.zshrc
```
