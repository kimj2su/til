# oh-my-zsh 프롬프트에 기본으로 표시되는 사용자 이름 삭제하기
- zsh에서 기본으로 표시되는 kimjisu-macbookAir 등등 같은 부분을 삭제하려면 .zshrc 파일에 아래 내용을 추가한다.

```kotlin
prompt_context() {
  if [[ "$USER" != "$DEFAULT_USER" || -n "$SSH_CLIENT" ]]; then
    prompt_segment black default "%(!.%{%F{yellow}%}.)$USER"
  fi
}
```
- 수정 후 source ~/.zshrc 를 통해 재실행 하면 바로 적용된다.
- prompt_context(){} 로 비워두면 프롬프트에 표시되는 유저 이름이 모두 숨김처리된다.


# alias 추가
- alias는 명령어를 단축시켜주는 기능이다.

```zsh
alias study="cd /Users/kimjisu/Intellij"
```
- 터미널에서 study 입력시 cd /Users/kimjisu/Intellij 명령어가 실행된다.
