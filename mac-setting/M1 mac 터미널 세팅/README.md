# 세팅 순서
1. iTerm2 설치
2. oh-my-zsh 설치
3. iTerm2 커스터마이징: 테마, 폰트, 색상, 상태바
4. 플러그인 설치 : 자동 완성, 하이라이터, Neofetch

## 0. Homebrew 설치
- [Homebrew 공식 홈페이지](https://brew.sh/index_ko)
```zsh
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

brew inco {설치한 APP 이름} 위치 조회 가능
```
## 1. iTerm2 설치
```zsh
brew install iterm2
```

## 2. oh-my-zsh 설치
```zsh
sh -c "$(curl -fsSL https://raw.github.com/robbyrussell/oh-my-zsh/master/tools/install.sh)"
```

## 3. 커스터 마이징

### 3.1 테마 변경
- [테마 목록](https://github.com/ohmyzsh/ohmyzsh/wiki/Themes)
```zsh
open ~/.zshrc
```
여기서 ZSH_THEME="agnoster"로 변경

### 3.2 폰트 변경
테마 변경 후 iTerm2를 다시 시작하면 폰트가 깨져 있음
- [D2 Coding 폰트 다운로드](https://github.com/naver/d2codingfont)

위 링크에 들어가서 zip파일을 다운받고 .ttf 파일을 눌러 서체를 설치해준다.

iTerm2를 켠 뒤 상태바 좌상단의 iTerm2 > Profiles > Open Profiles > Default Edit Profiles > Text > Font 에서 다운받은 D2Coding 으로 폰트를 변경한다.  

![스크린샷 2024-01-10 오후 8 28 20](https://github.com/jisu3316/til/assets/95600042/2acc7483-467d-4c80-acac-687f39ff3e2f)

원래 터미널도 변경해야 안꺠지므로 기본 터미널도 변경해준다.  

터미널 > 환경설정 > 텍스트 > 서체

![스크린샷 2024-01-10 오후 8 29 28](https://github.com/jisu3316/til/assets/95600042/795b1609-9336-4dea-bd08-7cf71454c91a)

### 3.3 색상 변경
- [iTerm2 color shemes 다운로드 링크](https://iterm2colorschemes.com/)

설정에서 원하는 컬러 프리셋으로 변경할수도 있고, 아래 링크에서 많은 컬러 테마를 다운받아서 사용할 수 있다. 링크에서 zip파일로 다운받으면 schemes 라는 폴더 안에 .itermcolors 파일이 존재하고 이를 컬러 프리셋에 import 한다. 아래는 Atom이라는 컬러 프리셋이다. 참고로 최종 완성된 테마는 Snazzy를 사용하였다.  

Color Presets > Import > Snazzy을 import 한다.


### 3.4 상태바 추가
- 상태바 추가하기: iTerm2 > Preferences > Profiles > Session > Status bar enabled
- 상태바 위치 설정(bottom): Preferences > Appearance > Status bar location > Bottom
상태바를 클릭해서 아래처럼 원하는 메뉴를 놓는다.

![스크린샷 2024-01-10 오후 8 43 26](https://github.com/jisu3316/til/assets/95600042/a756806c-a070-43bb-8d06-d93b0b20f0d3)


## 4. 플러그인 설치
터미널 대신 iTerm2를 사용하는 이유는 플러그인을 사용하기 위해서이다.  
플러그인을 사용하면 터미널에서 자동완성, 하이라이터, 프롬프트 등을 사용할 수 있다.  

### 4.1 자동완성 플러그인 설치
예전에 사용한 명령어를 추천해주어 자동완성해주는 플러그인이다.
- [zsh-autosuggestions](https://github.com/zsh-users/zsh-autosuggestions)
```zsh
git clone https://github.com/zsh-users/zsh-autosuggestions ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-autosuggestions
```

open ~/.zshrc 에 아래 플러그인 경로를 추가한다.
```kotlin
plugins=( 
    # other plugins...
    zsh-autosuggestions
)
```

### 4.2 하이라이터 플러그인 설치
- [zsh-syntax highlighter](https://github.com/zsh-users/zsh-autosuggestions)

```zsh
git clone https://github.com/zsh-users/zsh-syntax-highlighting.git ${ZSH_CUSTOM:-~/.oh-my-zsh/custom}/plugins/zsh-syntax-highlighting
```
open ~/.zshrc 에 아래 플러그인 경로를 추가한다.
```kotlin
plugins=(
    # other plugins...
    zsh-syntax-highlighting
)
```
```kotlin
plugins=(git 
	zsh-autosuggestions
	zsh-syntax-highlighting)
```
![스크린샷 2024-01-10 오후 8 50 02](https://github.com/jisu3316/til/assets/95600042/d00f793f-8b86-4e73-9728-cac848466b21)


### 4.3 Neofetch 설치
- [Neofetch 깃헙 링크](https://github.com/dylanaraps/neofetch)
화룡점정으로 영롱한 🍎사과 모양이 빠질 수 없다. Neofetch는 iTerm2 부팅 시에 사용자의 정보가 뜨도록 하는 플러그인으로 다양하게 커스터마이징이 가능하다. Neofetch 플러그인을 Homebrew를 통해 설치하고 terminal 실행 시 자동으로 실행되도록 open ~/.zshrc 을 입력해서 에디터로 맨 아래에 neofetch라고 한 줄 추가해준다.

```zsh
brew install neofetch
```

![스크린샷 2024-01-10 오후 8 52 57](https://github.com/jisu3316/til/assets/95600042/339bc194-c040-46bb-bb9e-5876bfe61128)


