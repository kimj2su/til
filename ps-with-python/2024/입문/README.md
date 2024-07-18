# 숫자를 입력받을 때는 int(input())
```python
number = int(input())
```
# 문자를 입력 받을 때는 input()
```python
string = input()
```

# 여러 개의 숫자를 입력 받을 때는 split()을 사용
```python
a, b = map(int, input().split())
print(a + b)
```

# 여러 개의 숫자를 입력 받을 때는 split()을 사용
```python
a, b = map(int, input().split())
print(a + b)
```

# 여러개의 숫자들의 조합, 수열
```python
list1 = list(map(int,input().split()))
print(list1)
```

# 여러개의 문자들의 조합, 문자열
```python
list = list(map(str,input().split()))
print(list)
```

# 배열의 값만 출력
```python
list = list(map(int,input().split()))
print(*list)
for i in list:
    print(i)
```