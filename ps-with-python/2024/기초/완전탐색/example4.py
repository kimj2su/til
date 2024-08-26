# 문제4. 숫자야구(#2503)
# A는 3자리 숫자로 된 정답을 하나 정합니다.
# B는 3자리 숫자를 제시해서 A가 생각하고 있는 정답을 맞히려고 합니다.
# B가 말한 숫자가 정답에 포함되어 있다면 1Ball
# B가 말한 숫자가 정답에 포함되어 있고 자리도 동일하다면 1 Strike 입니다.
# 다른 숫자로 이루어진 세자리수 Strike와 Ball의 결과를 보고 가능한 숫자를 계산하는 프로그램을 작성하세요,
"""
4
123 1 1
356 1 0
327 2 0
489 0 1

2
"""

# A가 정답으로 생각할 수 있는 모든 수를 넣어보기

# 그리고 B가 도전한 내용에 맞는지 확인하기

n = int(input())

numbers = [list(map(str,input().split())) for _ in range(n)]
# string을 미리 넣어준 이유는, 나중에 쪼개기 위해서

answer = 0

# (1) 세 자리 숫자 만들기
for a in range(1,10): # 100의 자리수
    for b in range(1,10): # 10의 자리수
        for c in range(1,10): # 1의 자리수
            counter = 0

            # (2) 다른 세 자리수
            if a == b or b == c or c == a:
                continue

            # continue, 그 숫자를 넘김
            # break, 반복문을 넘김

            # (3) 배열에 넣은 조건을 넣어주기
            for array in numbers:
                check = list(array[0]) # ['1','2','3']
                strike = int(array[1])
                ball = int(array[2])

                strike_count = 0
                ball_count = 0

                #스트라이크 계산기
                if a == int(check[0]):
                    strike_count += 1
                    print("if a == int(check[0]):" + str(strike_count))
                if b == int(check[1]):
                    strike_count += 1
                    print("if b == int(check[1]):" + str(strike_count))
                if c == int(check[2]):
                    strike_count += 1
                    print("if c == int(check[2]):" + str(strike_count))


                #볼 계산기
                if a == int(check[1]) or a == int(check[2]):
                    ball_count += 1
                    print("if a == int(check[1]) or a == int(check[2]):" + str(ball_count))
                if b == int(check[0]) or b == int(check[2]):
                    ball_count += 1
                    print("if b == int(check[0]) or b == int(check[2]):" + str(ball_count))
                if c == int(check[0]) or c == int(check[1]):
                    ball_count += 1
                    print("if c == int(check[0]) or c == int(check[1]):" + str(ball_count))


                #(4) 매칭 여부 확인하기
                if strike != strike_count:
                    break
                if ball != ball_count:
                    break

                counter += 1

            if counter == n:
                answer += 1

print(answer)
