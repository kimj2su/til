# 레디스 기본 개념
레디스는 키 - 값 형태의 데이터 저장소이다.   

# String
```
set hello world
get hello
```
만약 기존키가 있다면 새로운 값으로 대체 된다.  
### NX 옵션을 주면 키가 없을때만 새로운키를 저장한다.
```
set hello world NX
```

### XX 옵션을 주면 키가 있을때만 새로운 값으로 덮어 쓴다.
```
set hello world XX
```

string자료 구조에는 모든 문자열 데이터를 저장할 수 있으므로 숫자 형태의 데이터도 저장 가능하다.

incr 명령어는 string 자료구조에 저장된 숫자를 원자적(atomic)으로 조작할 수 있다.
```redis
set counter 100

incr counter // 101
incrby counter 50 // 151
```
여기서 원자적이라는 것은 같은 키에 접근하는 여러 클라이언트가 경쟁 상태를 발생시킬 일이 없음을 의미한다.

### mset, mget 명령어는 한 번에 여러키를 조작할 수 있다.
네트워크 시간을 줄여 성능을 향상시킬 수 있다.
```redis
mset a 10 b 20 c 30
mget a b c
```

# List
레디스에서 리스트는 순서를 가지는 문자열의 목록이다.  
하나의 리스트에는 최대 42억여 개의 아이템을 저장할 수 있다.  
일반적으로 리스트는 서비스에서 스택과 큐로 사용된다.  

### lpush, rpush 명령어로 리스트에 아이템을 추가할 수 있다.
```redis
lpush list E
rpush list B
lpush list D A C B A
lrange list 0 -1 // A, B, C, A, D ,E, B

lrange list 0 3 // A, B, C, A
```
가장 오른쪽에 있는 아이템의 인덱스는 -1, 그 앞에는 -2이다.  
0 -1 은 전체를 출력.

### lpop, rpop 명령어로 리스트에서 아이템을 제거할 수 있다.
양 쪽 끝의 아이템을 리스트에서 삭제하고 반환한다.
```redis
lpop list // A
rpop list // B
```

### LTRIM 범위 지정 ㅅㄱ제
리스트의 시작과 끝 아잍ㅁ의 인덱스를 인자로 전달받아 지정한 범위에 속하지 않은 아이템은 모두 삭제한다.  
lpop과 같이 삭제되는 아이템을 반환하지는 않는다.
```redis
lrange 0 -1 // A, B, C, A, D ,E, B
ltrim 0 1
lrange 0 -1 // A, B
```

### LPUSH 와 LTRIM을 이용한 큐 구현
```redis
lpush stack A
ltrim 0 999
```
해당 리스트는 왼쪽에서 데이터를 삽입하고 오른쪽에서 데이터를 제거하는 스택으로 사용할 수 있다.  
해당 리스트의 데이터가 1000개가 쌓이면 제일 오래된 데이터를 삭제한다.  
배치 처리로 삭제하는 것보다 끝에  데이터를 삭제하는것이 O(1)이기 때문에 더 효율적이다.  

LPUSH, RPUSH, LPOP, RPOP, LTRIM 명령어는 O(1)의 시간 복잡도를 가진다.  
하지만 인덱스나 데이터를 이용해 리스트 중간 데이터에 접근할 때는 O(N)의 시간 복잡도를 가진다.  

### LINSERT
해당 커맨드는 원하는 데이터 앞이나 뒤에 데이터를 추가할 수 있다.  
데이터 앞에 추가하려면 BEFORE 옵션을, 뒤에 추가하려면 AFTER 옵션을 사용한다.  
만약 지정한 데이터가 없으면 오류를 반환한다.  
```redis
lrange list 0 -1 // A, B
LINSERT list BEFORE B C
lrange list 0 -1 // A, C, B
```

### LSET
인덱스의 데이터를 신규 입력하는 데이터로 덮어 쓴다.  
만약 리스트의 범위를 벗어난 인덱스를 입력하면 에러를 반환한다.
```redis
LSET list 2 D
lrange list 0 -1 // A, C, D
```

### LINDEX
원하는 인덱스의 데이터를 확인할 수 있다.
```redis
LINDEX list 1 // C
```

# hash
레디스에서 hash는 필드-값 쌍을 가진 아이템의 집합이다.  
레디스에서 데이터가 키 밸류 쌍으로 저장되는것 처럼 하나의 hash 자료 구조 내에서 아이템은 필드 - 값 쌍으로 저장된다.  
필드는 하나의 hash 내에서 유일하며 필드와 값 모두 문자열 데잍터로 저장된다.

## hset, hget
- hset : hash에 필드와 값을 저장한다.
- hget : hash에서 필드에 해당하는 값을 반환한다.
- hmget : 여러 필드에 해당하는 값을 반환한다.
- hgetall : hash의 모든 필드와 값을 반환한다.
```redis
hset Product:1234 Name "Kim Jisu"
hset Product:1234 TypeID 29
hset Product:1234 Version 2002
hset Product:234 Name "Kim Jisu2" TypeID 32

hget Product:1234 Name // Kim Jisu
hget Product:1234 TypeID // 29
hmget Product:234 Name TypeID // Kim Jisu2, 32
hgetall Product:1234 // Name, Kim Jisu, TypeID, 29, Version, 2002
```

# Set
레디스에서 set은 정렬되지 않은 문자열의 모음이다.  
하나의 set 자료 구조 내에서 아이템은 중복해서 저장되지 않으며 교집합, 합집합, 차집합 등의 집합 연산과 관련한 커맨드를 제공하기 때문에 교집합, 합집합, 차집합 등의 집한 연산과 
간련한 커맨드를 제공하기 때문에 객체 간의 관계를 계산하거나 유일한 원소를 구해야할 경우에 사용될 수 있다.

## sadd, smembers
- sadd : set에 아이템을 추가한다. -> 저장된 아이템의 수를 반환한다.
- smembers : set의 모든 아이템을 반환한다. -> 순서와 상관없이 반환
- srem : set에서 아이템을 제거한다. -> 제거된 아이템의 수를 반환한다.
- spop : set에서 무작위로 아이템을 제거하고 반환한다.

```redis
sadd set a
sadd set a a a b
smembers set // a, b
srem set a // 1
spop set // b
```

## SUNION, SINTER, SDIFF
- SUNION : 여러 set의 합집합을 반환한다.
- SINTER : 여러 set의 교집합을 반환한다.
- SDIFF : 여러 set의 차집합을 반환한다.
```redis
SINTER set1 set2 // 교집합
SUNION set1 set2 // 합집합
SDIFF set1 set2 // 차집합
```

# sorted set
레디스에서 sorted set은 스코어 값에 따라 정렬되는 고유한 문자열의 집합이다.  
저장될 때 부터 스코어 값으로 정렬돼 저장된다.  
데이터는 중복 없이 유일하게 저장되므로 set과 유사하다고 볼 수 있으며  
각 아이템은 스코어라는 데이터에 연결돼 있어 이 점에서 hash와 유사하다고 생각할 수 있다.  
또한 모든 아이템은 스코어 순으로 정렬돼 있어 list처럼 인덱스를 이용해 각 아이템에 접근할 수 있다.  
인덱스로 접근할 때 list는 O(N)의 시간 복잡도를 가지지만 sorted set은 O(logN)의 시간 복잡도를 가진다.  
-> 인덱스로 접근을 할 일이 많다면 sorted set을 사용해라  

## zadd, zrange
- zadd : sorted set에 아이템을 추가한다.
```redis
zadd score:220817 100 user:B
zadd score:220817 150 user:A 150 user:C 200 user:F 300 user:E
```
만약 이미 데이터가 있다면 스코어만 업데이트 되며, 업데이트된 스코어에 의해 아이템이 재정렬 된다.  
존재 하지 않으면 생성하고 이미 존재하지만 sorted set이 아니라면 오류를 반환한다.

### zadd의 옵션
- XX : 이미 존재하는 아이템에 대해서만 스코어를 업데이트한다.
- NX : 존재하지 않는 아이템에 대해서만 추가한다. 업데이트 X
- LT : 새로운 데티어가 기존 데이터보가 작을 때 에만 업데이트하고 없으면 삽입한다.
- GT : 새로운 데이터가 기존 데이터보다 클 때에만 업데이트하고 없으면 삽입한다.

## zrange
- zrange : sorted set의 아이템을 스코어 순으로 반환한다.
```redis
zrange key start stop // start ~ stop 까지의 아이템을 반환한다.
zrange score:220817 0 -1 // user:B, user:A, user:C, user:F, user:E
zrange score:220817 0 -1 WITHSCORES // user:B, 100, user:A, 150, user:C, 150, user:F, 200, user:E, 300
zrange score:220817 0 -1 WITHSCORES REV // user:E, 300, user:F, 200, user:C, 150, user:A, 150, user:B, 100
```

### zrange - 옵션
- BYSCORE : 스코어의 범위를 지정해 아이템을 반환한다.
- WITHSCORES : 스코어와 함께 반환한다.
- REV : 역순으로 반환한다.
```redis
zrange score:220817 100 150 BYSCORE WITHSCORES // user:B, 100, user:A, 150, user:C, 150
```
위와 같이 start stop에 점수를 전달할 수 있다. 100이상 150이하를 의미

```redis
zrange score:220817 (100 150 BYSCORE WITHSCORES // user:A, 150, user:C, 150
zrange score:220817 100 (150 BYSCORE WITHSCORES // user:B, 100

```
( 문자를 추가하면 해당 스코어를 포함하지 않는 값만 조회한다.

## 최소값과 최대값을 표한하는 방법 -inf, +inf (infinity)
```redis
zrange score:220817 -inf +inf // user:B, user:A, user:C, user:F, user:E
zrange score:220817 200 +inf BYSCORE WITHSCORES // 200 보다 큰 모든 값
zrange score:220817 200 +inf BYSCORE WITHSCORES REB// 200 보다 큰 모든 값 역순
```

## 사전순으로 데이터 조회
sorted set은 스코어가 같으면 데이터는 사전 순으로 정렬 된다.  
스코어가 같을 때 BYLEX 옵션을 사용하면 사전식 순서를 이용해 아이템을 조죄할 수 있다.
```redis
zadd mySortedset 0 apple 0 banana 0 candy 0 dream 0 egg 0 frog
zrange mySortedset 0 -1 // apple, banana, candy, dream, egg, frog 
zrange mySortedset (b (f BYLEX // banana, candy, dream, egg
```
사전순으로 비교하기 위해선 start, stop에 문자열을 전달해야하며 (나 [ 문자를 합께 입력해야 한다.  
(는 포함 [는 포함하지 않을 때 사용한다.  
문자열의 시작은 - 끝은 +로 나타낸다.
```redis
zrange mySortedset - + BYLEX // apple, banana, candy, dream, egg, frog
```


# 비트맵
비트맵은 독자적인 자료구조는 아니며 string 자료구조를 이용해 비트를 저장하는 방식이다.  
string 자료 구조가 binary safe하고 최대 512MB까지 저장할 수 있기 때문에 비트맵을 저장하는데 적합하다.  
비트맵을 사용할때 가장 큰 장점은 저장 공간을 획기적으로 줄일 수 있다는 것이다.  
예를 들어 각각의 유저가 정수 형태의 ID로 구분되고 전체 유저가 40억이 넘믄다고 해도 각 유저에 대한 y/n 대이터는 512MB안에 충분히 저장할 수 있다.  

## SETBIT, GETBIT
- SETBIT : 비트맵에 비트를 저장한다.
- GETBIT : 비트맵에서 비트를 반환한다.
```redis
SETBIT mybitmap 2 1
GETBIT mybitmap 2 // 1
BITFIELD mybitmap SET u1 6 1 SET u1 10 1 SET u1 14 1

// 1로 설정된 비트의 수를 반환한다.
BITCOUNT mybitmap // 4
```

# Hyperloglog
Hyperloglog는 집합의 원소 개수인 카디널리티를 추정할 수 있는 자료 구조다.  
대량의 데이터에서 중복되지 않는 고유한 값을 집계할 때 유용하게 사용할 수 있는 데이터 구조다.
set은 중복을 처리하기 위해 모든 데이터를 기억하고 있지만  
Hyperloglog는 입력되는 데이터 그 자체를 저장하지 않고 자체적인 방법으로 데이터를 변경해 처리한다.  
Hyperloglog 자료 구조는 저장되는 데이터 개수에 구애 받지 않고 계속 일정한 메모리를 유지할 수 있으며 중복되지 않는 유일한 원소의 개수를 계산할 수 있다.  
하나의 Hyperloglog 자료 구조는 12KB의 크기를 가지며 레디스에서 카디널리티 추정의 오차는 0.81 %로 비교적 정확하게 추정한다.  
하나의 Hyperloglog에는 2의 64승개의 아이템을 저장할 수 있다.

## PFADD, PFCOUNT
- PFADD : Hyperloglog에 아이템을 추가한다.
- PFCOUNT : Hyperloglog의 카디널리티를 반환한다.
```redis
PFADD members 123
PFADD members 500
PFADD members 12
PFCOUNT members // 3
```

# Geospatial
Geospatial 자료 구조는 경도, 위도 데이터 쌍의 집합으로 간편하게 지리 데이터를 저장할 수 있는 방법이다.  
내부적으로 데이터는 sorted set으로 저장되며 하나의 ㅈ료 구조 안에 키는 중복돼 저장되지 않는다.

## GEOADD, GEOPOS, GEODIST
- GEOADD : geospatial 자료 구조에 아이템을 추가한다.
- GEOPOS : geospatial 자료 구조에서 아이템의 위치를 반환한다.
- GEODIST : geospatial 자료 구조에서 두 아이템 사이의 거리를 반환한다.
- GEOSEARCH : geospatial 자료 구조에서 아이템을 검색한다.
- BYRADIUS : 지정한 중심점에서 반경 내에 있는 아이템을 반환한다.

```redis
GEOADD travel 14.3996 50.0992 prague
GEOADD travel 13.4050 52.5200 seoul -122.4345 37.785 SanFrancisco

geopos travel prague // 14.3996, 50.0992
geodist travel seoul SanFrancisco // 0.0
```

# stream
stream은 레디스를 메시지 브로커로서 사용할 수 있게 하는 자료 구조다.  
전체적인 구조는 카프카에서 양향을 받아 만들어졌으며 카프카에서처럼 소비자 그룹 개념을 도입해 데이터를 분산 처리할 수 있는 시스템이다.  

stream 자료 구조는 데이터를 계속해서 추가하는 방식(append-only)으로 데이터를 저장한다.

# 레디스에서 키를 관리하는법

## 키의 자동 생성과 삭제
stream이나 set, sorted set, hash 와 같이 하나의 키가 여러개의 아이템을 가지고 있는 자료 구조에서는 명시적으로 키를 생성하거나 삭제하지 않아도 키는 알아서 생성되고 삭제 된다.  
키의 생성과 삭제는 세가지 공통적인 규칙을 따른다.  

1. 키가 존재하지 않을 때 아이템을 넣으면 아이템을 삽입하기 전에 빈 자료 구조를 생성한다.
```redis
del list
lpush list 1 2 3
```
키가 존재하지 않을 때 lpush 커맨드를 사용해 데이터를 입력하면 명시적으로 키를 생성하는 작업을 하지 않아도 list라는 이름의 자료구조가 생성된다.  
저장하고자 하는 키에 다른 자료구조가 이미 생성 되어 있으면 에러를 반환한다.
```redis
set hello world

lpush hello 1 2 3 // error

type hello // string
```

2. 모든 아이템을 삭제하면 키도 자동으로 삭제 된다.(stream은 예외)
```redis
lpush list 1 2 3
exists list // 1
lpop list // 3
lpop list // 2
lpop list // 1
exists list // 0
```

3. 키가 없는 상태에서 키 삭제, 아이템 삭제, 자료구조 크기 조회 같은 읽기 전용 커맨드를 수행하면 에러를 반환하는 대신 키가 있으나 아이템이 없는 것처럼 동작한다.  
```redis
del list

llen list

lpop list
```
del로 키를 지웠지만 llen으로 리스트의 길이를 확인하고자하면 키가 없음에도 불구하고 에러가 아닌 0을 반환한다.  
lpop으로 리스트의 아이템을 삭제하고자 하면 키가 없음에도 불구하고 에러가 아닌 nil을 반환한다.  

## 키와 관련된 커맨드

### 키의 조회

- exists : 키가 존재하는지 확인한다. 존재하면 1, 존재하지 않으면 0을 반환한다.
```redis
exists key [key...]
```

- keys : 키를 조회한다.
```redis
keys pattern
```
keys 커맨드는 레디스에 저장된 모든 키를 조회하는 커맨드다. 매칭이 되는 패턴에 해당하는 모든 키의 list를 반환한다.  
- h?llo : hello, hallo, hxllo
- h*llo : hllo, heeeello
- h[ae]llo : hello, hallo가 매칭될 수 있지만 hillo는 매칭되지 않는다.
- h[^e]llo : hallo, hxllo가 매칭될 수 있지만 hello는 매칭되지 않는다.
- h[a-b]llo : hallo, hbllo만 매칭될 수 있다.


# **KEYS**
keys는 위험한 커맨드이다.  
만약 100만개의 키가 저장돼 있다면 모든 키의 정보를 반환한다.  
레디스는 싱글 스레드이기 때문에 실행시간이 오래 걸리는 커맨드를 수행하는 동안 다른 클라이언트에서 들어오는 다른 모든 커맨드는 차단된다.  
이러한 위험성을 소개할 때 대표적으로 소개되는 것이 keys이다.  
레디스가 keys명령어를 통해 모든 키를 읽어오는 동안 다른 클라이언트가 set, get 커맨드는 수행 되지 않고 대기한다.  
모니터링 도구가 마스터 노드로 보낸 health check에 응답할 수 없어 의도하지 않은 페일오버가 발생할 수도 있다.  

## SCAN
keys 커맨드의 위험성을 해결하기 위해 SCAN 커맨드가 등장했다.  
커서 기반으로 특정 범위의 키만 조회할 수 있기 때문에 비교적 안전하게 사용할 수 있다.

```redis
scan cursor [MATCH pattern] [COUNT count]
scan 0
scan 10
scan 0 1
```
처음으로 sacn 0을 실행하면 나오는 숫자가 커서 값이다.  
이 값을 다시 scan 커맨드에 넣어주면 다음 범위의 키를 조회할 수 있다.  
두번째 커서의 값이 0이라면 이는 레디스에 저장된 모든 키를 반환해서 디 이상 검색할 키값이 없다는 뜻이다.  
기본적으로 10개 정도 반환하지만 COUNT 옵션을 사용하면 이 개수를 조절할 수 있다.  
하지만 레디스는 정확하게 지정한 개수대로 출력되지 않는다. 데이터가 지정된 형태에 따라 몇 개의 키를 더 읽는 것이 효육적이라 판단되면 
더 많은 키를 읽을 수 있다.  
scan 의 count 또한 너무 많이 주면 서비스에 영향을 줄 수 있다.

### match 옵션
match 옵션을 사용하면 특정 패턴에 매칭되는 키만 조회할 수 있다.  
```redis
keys *a*
scan 0 match *a*
1) "10" 
2) 1) "travel"
```
여기서 sacn은 한번에 패턴에 매칭된 여러 개의 키값이 반환되지 않는다.  
적은 수의 결과가 반환되거나 빈 값이 반환 될 수 있다.  
이 방식은 우선 필털링 없이 스캔을 한 뒤에 패턴에 매칭되는 키를 찾아서 반환하기 때문이다.   

### TYPE 옵션
type 옵션을 사용하면 특정 타입의 키만 조회할 수 있다.  
사용자에게 반환 되기 전에 필터링하는 방법이라 오래걸릴 수 있다.
```redis
SACN 0 TYPE zset

SCAN 048 TYPE zset

SCAN 0 TYPE zset count 1000
```

### sscan, hsacn, zscan
sscan, hscan, zscan은 set, hash, sorted set에 대해 scan을 수행하는 커맨드이다.  


# SORT
```redis
SORT key [BY pattern] [LIMIT offset count] [GET pattern [GET pattern ...]] [ASC | DESC] [ALPHA] [STORE destination]
```
list, set ,sorted set에서만 사용할 수 있는 커맨드이다.  
키 내부의 아이템을 정렬해 반환한다.  
LIMIT 옵션을 사용하면 일부 데이터만 조회할 수 있으며, ASC / DESC 옵션을 사용하면 정렬 순서를 변경할 수 있다.  
정렬할 대상이 문자열일 경우 ALPHA 옵션을 사용하면 데이터를 사전 순으로 정렬해 조회한다.  
```redis
lpush list a
lpush list b
lpush list c
lpush hello
sort list // (error) ERR One or more scores can't be converted into double
sort list alpha // a, b, c
```

# rename / renamenx
```redis
rename key newkey
renamenx key newkey
```
- rename : 기존 키를 새로운 키로 변경한다.
- renamenx : 기존 키를 새로운 키로 변경한다. 단, 새로운 키가 존재하지 않을 때만 변경한다.

# copy
```redis
copy source destination [DB destination-db] [REPLACE]
```
source에 지정된 키를 destination 키에 복사를 한다.  
destination에 지정한 키가 이미 있는 경우  replace 옵션을 사용하면 destination 키를 삭제 한 뒤 값을 복사하기 때문에 에러 발생하지 않음.  
```redis
set b banana
copy b bb
get b // banana
get bb // banana
```

# TYPE
- type : 키의 자료구조 타입을 반환한다.
```redis
type key
```

# OBJECT
```redis
object subcommand [arguments [arguments ...]]
```
키에 대한 상세 정보를 반환한다. 사용할 수 있는 서브커맨드는 encoding, idletime 등이 있다.  
해당 키가 내부적으로 어떻게 저장됐는지 혹은 키가 호출되지 않은 시간이 얼마나 됐는지 등을 확인할 수 있다.

# 키의 삭제

## flushall
```redis
flushall [ASYNC | SYNC]
```
레디스에 저장된 모든 키를 삭제한다. 기본적으로 sync 방식이다.  
그렇기 때문에 커맨드가 실행되는 와중에 다른 요청에 응답할 수 없다.  
async 옵션을 사용하면 백그라운드에서 삭제를 수행한다. -> 삭제 도중 생성된 키는 삭제 안된다.  

lazyfree-lazy-user-flush 옵션이 yes인 경우 async 옵션 없이 백그라운드로 키 삭제 작업이 동작한다.  
7버전 기준 기본값은 no이다.

## DEL
```redis
del key [key ...]
```
키와 키에 저장된 모든 아이템을 삭제하는 커맨드이다. 동기적으로 작동한다.

## UNLINK
```redis
unlink key [key ...]
```
DEL과 비슷하게 키와 데이터를 삭제하는 커맨드이다.  하지만 이 커맨드는 백그라운드에서 다른 스레드에의해 처리하며, 우선 키와 연결된 데이터의 연결을 끊는다.  
set, sorted set과 같이 하나의 키에 여러 개의 아이템이 저장된 자료 구조의 경우 1개의 키를 삭제하는ㄴ DEL 커맨드를 수행하는것은 
레디스 인스턴스에 영향을 끼칠 가능성이 존재한다.  
100만개의 키가 있는 sorted set을 del로 삭제하면 동기 방식으로 flush all을 수행하는것과 같고
수행되는 시간동안 다른 응답을 할 수 없다.  
따라서 키에 저장된 아이템이 많은 경우 del -> unlink를 사용하여 백그라운드에서 삭제하는 것이 좋다.  

# 키의 만료 시간
## expire
```redis
expire key seconds [NX | XX | GT | LT]
```
키가 만료될 시간을 초 단위로 정의할 수 있으며 다음과 같은 옵션을 함께 사용할 수 있다,  
- NX : 해당 키에 만료 시간이 정의돼 있지 않을 경우에만 커맨드 수행
- XX : 해당 키에 만료 시간이 정의돼 있을 때에만 커맨드 수행
- GT : 현재 키가 가지고 있는 만료 시간보다 새로 입력한 초가 더 클 때에만 수행
- LT : 현재 키가 가지고 있는 만료 시간보다 새로 입력한 초가 더 작을 때에만 수행

## expireat
```redis
expireat key unix-time-seconds [NX | XX | GT | LT]
```
키가 특정 유닉스 타임스탬프에 만료될 수 있도록 키의 만료 시간을 직접 지정한다.

## expiretime
```redis
expiretime key
```
키가 삭제되는 유닉스 타임스탬프를 초 단위로 반환한다. 키가 존재하지만 만료 시간이 설정돼 있지 않은 경우에는 -1, 없을때에는 -2를 반환한다.

## TTL
```redis
ttl key
```
키가 삭제되는 시간을 초 단위로 반환한다. 키가 존재하지만 만료 시간이 설정돼 있지 않은 경우에는 -1, 없을때에는 -2를 반환한다.  
PEXPIRE, PEXPIREAT, PEXPIRETIME, PTTL은 밀리초 단위로 계산된다는 점만 다르며
EXPIRE, EXPIREAT, EXPIRETIME과 동일하게 동작한다.
