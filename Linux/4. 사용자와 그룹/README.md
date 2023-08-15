# 사용자와 그룹

리눅스는 여러명의 사용자(멀티 유저)를 지원하는 시스템입니다.  
리눅스 머신하나의 여러명의 사용자가 사용이 가능합니다.  
A 사용자의  데이터를 B 사용자로부터 보호할 수 있는 메커니즘이 구현 되어 있는데 이게 사용자와 그룹으로 나뉘어져 있습니다.  

<br/><br/>

# 파일의 소유권과 권한

- ## 파일의 소유권
## ls -l
total 1
| drwxr-xr-x | 2    | user2  | users | 1024 | Feb 9 | 14:22 | directory1 |
|------------|------|--------|-------|------|-------|-------|------------|
|Permissions | Links| Owners | Group | Size | Date  | Time  |  File or Directory name | 

하나의 리눅스에 A조, B조, C조 각조끼리 공유해야할 파일이 있습니다.  
이럴때 그룹과 오너로 매칭해서 파일에 대한 권한을 주어 관리 할 수 있습니다.

# 파일 권한

- ## 파일 접근 권한
ls -l 을 통해 제일 첫번째로 나오는 결과물이고 10글자가 나온다. 

### -rwxrwxrwx
- -or d : File Type
- rwx : Read, Write, Execute - Owner
- rwx : Group
- rwx : Other Users

첫번째 글자가 - 면 일반 파일이고, d면 디렉터리라는 뜻이다. 심볼릭 링크같은경우는 l로 표현된다.  
나머지 9글자는 정해져있고 3글자씩 3묶음입니다.  
첫번째 3글자 묶음은 파일의 실질적인 유저(오너)입니다.  
두번쨰 3글자 묶음은 이 파일의 그룹에 맞는입니다.  
마지막은 나머지그룹입니다. 
내 계정이 오너 계정이면 읽고, 쓰고, 실행할 수 있는 권한이 있습니다.  

<br/><br/>

# 파일 권한 표기법
기본적으로 8진법으로 표현할 수 있다.
## chmod 777
- 위에서 말한 각 묶음에  다 rwx권한을 준다.

## chmod 644
- -rw-r--r--


# /etc/passwd 파일 분석
## $cat /etc/passwd 
조회하면 시스템에 등록 되어 있는 유저들의 목록들과 설정 텍스트 파일이나옵니다.
기본적으로 계정의 이름은 무엇이고, 계정의 GID, UID가 무엇인지, 홈 디렉토리는 무엇인지, 쉘은 무엇인지에 대해 설정하고 저장해놓은 파일이 /etc/passwd 입니다.  

```
jisu:x:1000:1000:jisu,,,:/home/jisu:/bin/bash
```
앞의 1000이 UID, 뒤에 1000이 GID 입니다.
UID는 유저 아이디이고, GID는 그룹 아이디입니다. 
jisu,,,은 설명이고, 홈 디렉토리인 /home/jisu 가 나오고 디폴트 쉘 정보가 나옵니다.  


# 사용자 추가 및 삭제

```
sudo adduser jisu2
'jisu2' 사용자를 추가 중...
새 그룹 'jisu2' (1001) 추가 ...
새 사용자 'jisu2' (1001) 을(를) 그룹 'jisu2' (으)로 추가 ...
'/home/jisu2' 홈 디렉터리를 생성하는 중...
'/etc/skel'에서 파일들을 복사하는 중...
새  암호: 
새  암호 재입력: 
passwd: 암호를 성공적으로 업데이트했습니다
jisu2의 사용자의 정보를 바꿉니다
새로운 값을 넣거나, 기본값을 원하시면 엔터를 치세요
	이름 []: 
	방 번호 []: 
	직장 전화번호 []: 
	집 전화번호 []: 
	기타 []: 
정보가 올바릅니까? [Y/n] y


su - jisu2
```

# 사용자 변경 및 로그아웃 
```
jisu@jisu-Apple-Virtualization-Generic-Platform:~$ su - jisu2
암호: 
jisu2@jisu-Apple-Virtualization-Generic-Platform:~$ 
jisu2@jisu-Apple-Virtualization-Generic-Platform:~$ exit
로그아웃
jisu@jisu-Apple-Virtualization-Generic-Platform:~$ 
```

su는 스위치 유저라는 뜻이로 유저를 바꾼다.
그래서 유저의 이름이 jisu 에서 jisu2인 것을 확인했고 exit를 통해 로그아웃 후 다시 유저의 이름이 jisu로 바뀌는 것을 확인하였다. 


# 사용자 삭제

```
deluser --help
deluser 사용자
시스템에서 일반적인 사용자 제거
용법: deluser mike

--remove-home 메일스풀과 사용자의 처음디렉토리를 제거
--remove-all-files 사용자의 모든 파일을 제거
--backup 삭제하기 전에 백업
--backup-to <DIR> 백업할 대상 디렉토리
기본값 현재 디렉토리.
--system 시스템사용자만 제거

delgroup 그룹
deluser --group 그룹
시스템에서 그룹 삭제.
용법: deluser --group students

--system 시스템그룹만 삭제
--only-if-empty 일원이 아닌것만 제거

deluser 사용자 그룹
그룹의 사용자 제거
용법: deluser mike students

일반적인 선택:
--quiet | -q 표준출력으로 정보를 넘기지 않음
--help | -h 이 출력 화면
--version | -v 제작자와 판번
--conf | -c FILE 설정파일로 FILE 사용

sudo deluser jisu2 --remove-home
jisu@jisu-Apple-Virtualization-Generic-Platform:~$ sudo deluser jisu2 --remove-home
[sudo] jisu 암호: 
백업/제거할 파일들을 찾는 중...
파일 제거중 ...
'jisu2' 사용자 제거 중...
경고: 'jisu2'그룹이 회원목록에 더이상 없음.
완료.
jisu@jisu-Apple-Virtualization-Generic-Platform:~$ 

```

# 실습
테스트 사용자 및 그룹생성

```
sudo addgroup animals
sudo addgroup fruits

sudo adduser pig --ingroup animals
sudo adduser dog --ingroup animals

sudo adduser apple --ingroup fruits
sudo adduser banana --ingroup fruits

su - pig
touch testfile
nano testfile

su - dog
cd /home/pig
cat testfile
nano testfile -> 저장 안됨

chmod 664 testfile
```
## 스크립트 실행
스크립트는 첫번째는 샤뱅으로 시작하는 규칙이 있다.  
여기서 샤뱅이란 첫번째 # 이 먼저 나오고 !나오고   #!/bin/bash 이렇게 시작한다.  

```
nano firstscript

#!/bin/bash

ls
pwd
whoaml

```
