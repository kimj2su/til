# 출력 리디렉션
리디렉션은 화면을 바꾼다는 뜻입니다.  
그래서 화면에 출력될 무엇인가를 파일로 저장을 하거나 등을 할 수 있습니다.  

# 출력 리디렉션:>
화면에 출력되는것을 다른곳으로 보내는것입니다.  
- ## [n] > [|] word
[] 은 옵셔널로 안넣어도 됩니다.
- 스트림을 대상 파일에 저장
    - 표준 출력 스트림 번호 : 1
    - 표준 에러 스트림 번호 : 2
    - 생략 시 표준 출력
- 대상 파일이 존재하는 경우 덮어쓰기
- noclobber 옵션 설정 시 덮어쓰기 시도 시 에러 발생
- \> | 사용시 noclobber 옵션 설정 여부와 관계없이 덮어쓰기 성공

```
프로세스 목록 조회 ps 출력을 result 파일로 만듭니다.
ps > result

ls /dasdasd 2> result
cat result
ls: '/dasdasd'에 접근할 수 없습니다: 그런 파일이나 디렉터리가 없습니다
```
ls /dasdasd 은 없는 디렉터리 이기 떄문에 에러가 난다. 그렇기 때문에 에러 스트림 번호인 2번을 주어야 한다.  


# 추가 모드 출력 리디렉션 >>

- ## [n] >> word
- 스트림을 대상 파일 끝에 저장
    - 표준 출력 스트림 번호 : 1
    - 표준 에러 스트림 번호 : 2
    - 생략 시 표준 출력

기존에는 > result 하면 기존 파일에 덮어쓰기가 되었다.  
추가 모드 출력 리디렉션은 append 모드로 파일 끝에 이어서 쓰기가 되는 모드입니다.  


# 파일 디스크립터로 리디렉션:>&
- ## [n] >& [FD]
- \>과 동일하지만 '대상 파일' 대신 '대상 파일 디스크립터' 지정
- 표춘 출력과 표준 에러를 한꺼번에 출력하고 싶을 때 자주 사용
```
ls /desdss > result 2>&1
만약 스트림 번호가 2번이면 1번으로 저장 시키라는 의미
```

# 표준 출력 및 표준 에러 동시 리디렉션 : &>
- &>word
- 파일 디스크립터 리디렉션 문법이 귀찮고 어렵다
- 좀 더 편하게 사용 가능
```
ls /desdss &> result
위의 문법을 간편하게 사용할 수 있다.
```

# 입력 리디렉션 : <
- ## [n] < word
- 파일의 내용이 지정된 스트림(n)으로 리디렉션
- n이 생략되면 표준 입력(fd 0)을 의미

```
wc --help
사용법: wc [옵션]... [파일]...
  또는:  wc [옵션]... --files0-from=F
Print newline, word, and byte counts for each FILE, and a total line if
more than one FILE is specified.  A word is a non-zero-length sequence of
characters delimited by white space.

차례대로 라인수, 단어수, 글자수를 출력해줍니다. 

wc
a b c d e
12345
      2       6      16
```

```
cat firstscript
#!/bin/bash

ls
pwd
whoaml
 wc < firstscript
 5  4 27
```

# Here Documents : <<
## [command] << [-]DELIM
## ...
## DELIM

- ## 프로그램의 표준 입력으로 multi-line string 전달
- ## 코드 블록의 내용이 임시 파일로 저장됐다가 프로그램의 표준 입력으로 리디렉션
- ## DELIM은 다른 단어로 변경가능(의미상 EOF, END등)
- ## <<- 사용되면 라인 앞쪽의 tab 문자가 제거됨 

```
cat > hellotext << EOF
> hello world
> hi there
> EOF
cat hellotext 
hello world
hi there
```

# Here strings: <<<
- ## [command] <<< word
- ## Here document의 한줄 버전
