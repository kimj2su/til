# Hash Table
효율겆인 탐색을 위한 자료구조로써 key-vaclue 쌍의 데이터를 입력받는다.  
hash function에 key 값을 넣어 해시값 위치로 지정하여 key-value 데이터 쌍을 저장한다.  
저장, 삭제, 검색의 시간 복잡도는 모두 O(1) 이다.  
해시 테이블은 구현 방법이 2가지가 있다.  
1. Array List
2. Array List + Linked List

파이썬의 딕셔너리는 Array List로 구현 되어 있다.


# Dictionary
코딩테스트에 자주 쓰이는 자료구조이다.  
```java
socre = {
        'math' : 97,
        'english' : 88,
        'science' : 100
}
socre['math']

score['math'] = 100
print(score['math'])
```
일반적인 배열을 조회할때는 O(N)의 시간 복잡도를 가지지만, Dictionary는 O(1)의 시간 복잡도를 가진다.  
```java
if 2022408 in student:
    print("있음")
else:
    print("없음")
```
위의 코드에서 일반적인 인덱스로 조회하려면 하나씩 다 조회하면 O(N) 이지만 in으로 하면 O(1)로 시간 복잡도를 엄청 줄일 수 있다.