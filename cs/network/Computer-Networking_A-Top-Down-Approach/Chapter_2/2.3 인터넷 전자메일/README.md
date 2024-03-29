# 2.3 인터넷 전자메일
일반 우편과 같이 전자메일은 비동기적인 통신 매체다.

<br/>

![스크린샷 2023-11-08 오후 12 16 04](https://github.com/jisu3316/til/assets/95600042/18b91888-9eaa-451d-8d4b-76b16b9b85ca)

<br/>

위의 그림은 인터넷 메일 시스템의 상위 레벨 개념을 보여준다.  
이 다이어그램에 사용자 에이전트, 메일 서버, SMTP라는 3개의 주요 요소가 있음을 알 수 있다.

### 사용자 에이전트
사용자 에이전트는 사용자가 메시지를 읽고, 응답하고, 전달하고, 저장하고, 구성하게 해줌.  
- 아웃룩
- 애플 메일
- 구글 지메일  

사용자가 메시지를 메일서버로 보내고, 거기서 메시지는 메일 서버의 출력 메시지 큐에 들어간다.    
다른 사용자가 메시지를 읽고 싶을 때  그의 메일 서버에 있는 메일박스에서 메시지를 가져온다.  

### 메일서버
전자메일 인프라 스트럭쳐의 중심이다.  
수신자는 메일 서버 안에 메일박스를 갖고 있다.  
메일박스는 메시지를 유지하고 관리한다.  

### 메일이 보내지는 과정
1. 송신자의 에이전트에서 전달이 시작됨.
2. 송신자의 메일서버를 거친후 수신자의 메일서버로 전달
3. 수신자의 메일박스에 저장
4. 수신자는 메일서버의 계정과 비밀번호를 이용하여 인증

여기서 송신자는 수신자의 메일서버 고장에 대처해야함.  
수신자의 메일서버가 메시지를 수신할 수 없다면 메시지 큐에 보관후 30분마다 재시도함.  
성공하지 못한다면 송신자에게 전자메일로 통보.

### SMTP
SMTP는 전자메일을 송신자의 메일서버에서 수신자의 메일서버로 전달하는데 사용되는 프로토콜이다.  
SMTP는 TCP를 사용하며, 25번 포트를 사용한다.  
메일을 보낼때는 SMTP 클라이언트로 동작하는 방면, 메일서버가 상대 메일 서버로부터 메일을 받을때는 SMTP서버로 동작한다.

## 2.3.1 SMTP
SMTP는 HTTP보다 오래되었다.  
SMTP는 1982년에 정의되었고, 2008년에는 RFC 5321로 정의되었다.    
오래된 기술인 만큼 낡은 특성을 가졌다.  
모든 메일 메시지의 몸체(헤더뿐 아니라)는 단순한 7비트 ASCII여야 한다는 단점이 있다.
이 제한은 전송용량이 젷나되고 커다란 첨부파일이나 커다란 이미지, 오디오 혹은 비디오파일을 보내지 않은 1980년대에는 나름대로 의미가 있었다.  
그러나 오늘날에서는 문제를 일으킨다.  
SMTP를 통해 이진 멀티미디어 데이터를 보내기 전에 ASCII로 변환할 필요가 생겼다.  
그리고 SMTP 전송 후 에는 ASCII를 다시 원래 메시지로 변환해야만 한다.  
살펴봤듯이 HTTP는 전송 전에 멀티미디어 데이터를 ASCII로 변환하는 것을 요구하지 않는다.  

<br/>

![스크린샷 2023-11-08 오후 12 38 18](https://github.com/jisu3316/til/assets/95600042/53c8900f-0ed0-4104-8130-71ee4349d9c3)

<br/>


1. 앨리스는 전자메일 사용자 에이전트를 수행하고 밥의 전자메일 주소(예: bob@someschool. edu)를 제공하고, 메시지를 작성하고 사용자 에이전트에게 메시지를 보내라고 명령한다.  
2. 앨리스의 사용자 에이전트는 메시지를 그녀의 메일 서버에게 보내고 그곳에서 메시지는 메시지 큐에 놓인다.
3. 앨리스의 메일 서버에서 동작하는 SMTP의 클라이언트 측은 메시지 큐에 있는 메시지를 본다.   
밥의 메일 서버에서 수행되고 있는 SMTP 서버에게 TCP 연결을 설정한다.
4. 초기 SMTP 핸드셰이킹 이후에 SMTP 클라이언트는 앨리스의 메시지를 TCP 연결로 보낸다.
5. 밥의 메일 서버 호스트에서 SMTP의 서버 측은 메시지를 수신한다. 밥의 메일 서버는 그 메시지를 밥의 메일박스에 놓는다.
6. 밥은 편한 시간에 그 메시지를 읽기 위해 사용자 에이전트를 시동한다.  

여기서 두 메일 서버가 멀리 떨어져있어도 직접연결한다는 점을 이해하는게 중요하다.  
SMTP 핸드셰이킹 과정 동안에 SMTP 클라이언트 송신자의 전자메일 주소와 수신자의 전자메일 주소를 제공한다.  

```java
S: 220 hamburger.edu
С: HELO crepes.fг
S: 250 He丄丄о crepes.fr, pleased to meet you
C: MAIL FROM: <己丄丄06@crepes.fr>
S: 250 alice@crepes.fr ... Sender ok
C: RCPT TO: <bob@hamburger.edu>
S : 250 bob@ hamburger. edu ... Recipient ok
C: DATA
S: 354 Enter mail, end with "." on a line by itself
C: Do you like ketchup?
C: How about pickles?
C: .
S: 250 Message accepted for delivery
C: QUIT
S: 221 hamburger.edu eclosing connection
```

## 2.3.2 메일 접속 프로토콜
메일을 전송하면 메시지는 수신자의 메일박스에 놓이게 된다.  
수신자가 메일을 얻는 방법은 웹 기반 전자메일(지메일)을 사용한다면 HTTP를 사용한다.  
이 경우에 밥의 메일 서버는 엘리스 메일 서버와 통신하기 위해 SMTP인터페이슨느 물론이고 HTTP 인터페이스를 가지고 있어야 한다.  
다른 방법으로는 아웃룩과 같은 전형적인 메일 클라이언트를 사용하는데 인터넷 메일 접근 프로토콜(IMAP)을 사용하게 된다.  
HTTP, IMAP 사용 방법 모두 밥의 메일 서버에 의해 유지되는 폴더를 관리하게 된다. 수신자는 자신의 메시지를
자신이 생성한 폴더로 이동, 삭제하거나 중요 메시지로 표기해둘 수 있다.
