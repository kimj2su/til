# EC2

## EC2 개요
- AWS 클라우드 컴퓨팅 서비스 = 클라우드 가상 서버(Virtual Machine)
- EC2 클라우드 가상 서버를 인스턴스라고 부른다.
- EC2 인스턴스 생성 방법
  1. 이름 및 태그
  2. 애플리케이션 및 OS 이미지
  3. 인스턴스 유형
  4. 키 페어
  5. 네트워크 설정
  6. 스토리지 구성
  7. 고급 세부 정보

## EC@ 인스턴스 원격 접속 실습

### SSH 연결 (Linux 인스턴스)
- SSH(Secure Shell) : 원격 접속 프로토콜을 이용해 Linux 인스턴스에 원격으로 연결 및 파일 전송 가능
- SSH(Secure Shell Protocol)은 보안을 통해 원격으로 접속하기 위한 방식
- 아이디, 패스워드 방식이 아닌 Public Key와 Private Key를 이용해 접속
- 원격 접속 방법 : MAC PC의 Terminal, Windows Powershell, Windows Putty 프로그램 등을 사용

### RDP 연결 (Windows 인스턴스)
- RDP 프로토콜을 이용해 Windows 인스턴스에 원격으로 연결 및 파일 전송 가능
- RDP(Remote Desktop Protocol)는 Windows OS를 원격으로 접속하기 위한 방식
- 아이디, 패스워드를 이용해 접속
- 원격 접속 방법 : 윈도우의 원격 데스크톱 연결 프로그램 사용

### Instance Connect 연결(Linux 인스턴스)
- AWS 콘솔 웹 브라우저를 이용해 EC2 인스턴스에 연결
- SSH 프로토콜을 사용하여 일회용 SSH 퍼블릭키를 인스턴스 메타데이터에 업로드해서 EC2 연결
- SSH 프로토콜을 사용하기에 22번 포트가 오픈 되어 있어야 연결 가능
- PowerShell이나 Putty를 이용한 SSH 연결처럼 프라이빗 키를 다운로드 받을 필요가 없음
- Linux 인스턴스만 연결 가능