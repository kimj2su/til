# 문제 2 (#14568
# 친구 A,B,C에게 사탕을 나누어 주려고한다
# 조건은 아래와 같다.
# 1. 남는 사탕이 없어야
# 2. A는 B보다 2개 이상 많은 사탕을 가져야
# 3. 셋 중 사탕을 하나도 못 받는 친구는 없어야 한다.
# 4. C가 받는 사탕의 수는 짝수이다.
# 분배 가능한 경우의 수를 출력하는 프로그램을 작성해주세요.
# 6
# 1

# 입력
candy = int(input()) # 6

# 출력
result = 0
for A in range(0, candy+1): # 0 ~ candy
    for B in range(0, candy + 1): # 0 ~ candy
        for C in range(0, candy + 1): # 0 ~ candy
            if A + B + C == candy and A >= B + 2 and C % 2 == 0 and A != 0 and B != 0 and C != 0:
                result += 1
                print(result)