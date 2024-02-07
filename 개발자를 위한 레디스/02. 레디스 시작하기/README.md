# 레디스 시작하기
## 레디스 설치하기
### 리눅스
```mongodb-json
# 버전 지정
wget http://download.redis.io/releases/redis-7.0.8.tar.gz

# 최신 버전 다운로드
wget http://download.redis.io/redis-stable.tar.gz
```

레디스를 빌드하기 위해서는 gcc 버전 4.6 이상이 필요하므로 gcc를 미리 설치하는것이 좋다.
```bash
sudo apt-get install gcc
yum intall -y gcc

tar -zxvf redis-7.0.8.tar.gz
mv redis-7.0.8 redis
cd redis
make
```

디렉터리 내의 bin 디렉터리에 실행 파일을 복사하기 위해 make install 커맨드로 프리픽스 지정과 함께 수행
```bash
make PREFIX=/home/centos/redis install

# 또는
make install PREFIX=/usr/local/redis install
```

### redis 실행
```bash
cd /usr/local/redis/bin
./redis-server redis.conf
```
conf파일은 레디스의 설정 파일을 의미한다.

### mac os
```bash
brew install redis
brew services start redis
```

## 레디스 환경 구성
일부 서버 설정 파일과 구성 파일을 변경하여 사용하는 것이 좋다.  

### 서버 환경 설정 변경
Open files 확인  
레디스의 기본 maxclients 설정값은 10000이다. 이는 레디스 프로세스에서 받아들일 수 있는 최대 클라이언트 개수를 의미한다.  
이 값은 레디스를 실행하는 서버의 파일 디스크립터 수에 영향을 받는다.
레디스 프로세스 내부적으로 사용하기 위해 예약한 파일 디스크립터 수는 32개로 maxclients 값에 32를 더한 값보다 서버의 최대 파일디스크립터 수가 작으면 레디스는
실행될때 자동으로 그 수에 맞게 조정된다.
```bash
ulimit -a
ulimit -n
```

만약 10032 보다 작으면 /etc/security/limits.conf 파일에 다음과 같이 추가한다.
```bash
* hard nofile 10032
* soft nofile 10032
```

### THP 비활성화
리눅스는 메모리를 페이지 단위로 관리하며 기본 페이지는 4096바이트(4KB)로 고정돼 있다.  
메모리 크기가 커질수록 페이지를 관리하는 테이블인 TLB의 크기도 커져 페이지를 크게 만든 뒤 자동으로 관리하는 
THP(Transparent Huge Pages)라는 기능이 있다.  

하지만 레디스는 메모리를 많이 사용하는데 이런 경우 THP를 사용하면 성능이 떨어질 수 있다.  
그래서 THP를 비활성화하는 것이 좋다.  
```bash
echo never > /sys/kernel/mm/transparent_hugepage/enabled
```

위의 명령어는 일시적ㅇ로 hugepage를 비활성화한다. 영구적으로 기능을 비활성화 하려면 다음과 같이 한다.

```bash
vi /etc/rc.local

# 파일의 맨 아래에 다음과 같이 추가
if test -f /sys/kernel/mm/transparent_hugepage/enabled; then
    echo never > /sys/kernel/mm/transparent_hugepage/enabled
fi
```

다음 커맨드를 ㅅ행하면 부팅중 rc.local 파일이 실행되도록 설정된다.
```bash
chmod +x /etc/rc.d/rc.local
```

### vm.overcommit_memory = 1로 변경
레디스는 디스크에 파일을 저장할 때 fork()를 이용해 백그라운드 프로세스를 만드는데, 이때 COW(Copy On Write)를 사용한다.  
이 메커니즘에서는 부모 프로세스와 자식 프로세스가 동일한 메모리 페이지를 공유하다가 레디스의 데이터가 변경될 때마다 메모리 페이지를 복사하기 때문에 메모리를 많이 사용한다.

이때 vm.overcommit_memory 설정을 1로 변경하면 레디스가 메모리를 많이 사용할 때 fork()를 사용해 백그라운드 프로세스를 만들 때 메모리를 할당할 수 있도록 한다.
```bash
vi /etc/sysctl.conf

vm.overcommit_memory = 1

sysctl vm.overcommit_memory=1
```

### somaxconn 과 syn backlog 설정
레디스의 설정 파일의 Tcp-backlog 파라미터는 레디스 인스턴스가 클라이언트와 통신할때 사용하는 tcp backlog 큐의 크기를 지정한다.
이때 tcp-backlog의 값은 서버의 somaxconn과 syn backlog 값보다 클 수 없다.
tcp-backlog의 기본 값은 511이므로 서버 설정이 최소 이 값보다 크도록 설정해야 한다.

```bash
sysctl -a | grep somaxconn

sysctl -a | grep syn_backlog

vi /etc/sysctl.conf

net.core.somaxconn = 1024
net.ipv4.tcp_max_syn_backlog = 1024

# 재부팅 없이 적용하는 방법
sysctl net.core.somaxconn=1024
sysctl net.ipv4.tcp_max_syn_backlog=1024
```

## 레디스 설정 파일 변경
keyword argument1 argument2 와 같은 형태로 구성됐다.

### port
기본 값: 6379  

### bind
기본 값 : 127.0.0.1 -::1    
레디스가 설치된 서버 외부에서 레디스 인스턴스로 바로 접근하는 것을 허용하기 위해서는 해당 설정값을 변경해줘야함.  

```
bind 192.168.1.100 127.0.0.1
```
위의 설정은 두 개의 ip 주소로 들어오는 연결을 허용함을 의미한다.

### protected-mode
기본 값 : yes  
이 설정이 yes일 경ㅇ 패스워드를 설정해야만 레디스에 접근할 수 있다.  
패스워드가 없다면 오직 로컬에서만 접근 가능하다.

### requirepass/ masterauth
기본값: 없음  

requirepass 파라미터는 서버에 접속하기 위한 패스워드 값을 의미함.  
masterauth 파라미터는 슬레이브가 마스터에 접속하기 위한 패스워드 값을 의미함.

### daemonize
기본값: no  
레디스 프로세스를 데몬으로 실행시키려면 yes로 설정한다.

### dir
기본값: ./  
레디스의 워킹 디렉터리를 의미한다. 로그 파일이나 백업 파일 등 인스턴스를 실행하면서 만들어지는 파일은 기본적으로 해당 파라미터에서 지정한 디렉터리에 저장되므로 특정 값을 지정해주는 것이 좋다.  

## redis-cli
레디스는 명령어를 입력하는 콘솔을 제공한다.  
```bash
export PATH=$PATH:/usr/local/redis/bin
export PATH=$PATH:/home/centos/redis/bin

redis-cli -h <ip주소> -p <포트번호> -a <패스워드>
```
ip주소를 생략할 경우 localhost로 접속한다.  
포트번호를 생략할 경우 6379로 접속한다.  
패스워드를 설정해준 경우 -a 옵션을 이용해 패스워드를 함꼐 입력해준다.



















