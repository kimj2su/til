# 문제 1 (#1816)
# 확인하고자 하는 수가 주어집니다.
# 확인하고자 하는 수가 적절한 비밀번호 인지 아닌지를 구하는 프로그램을 작성해주세요.
# 적절하다면 YES, 적절하지 않다면 NO를 출력해주세요.
# 적절한 비밀번호  : 모든 소인수가 1,000,000보다 크다면 적잘한 비밀번호이다.
# 소인수란 - 1을 제외한 약수를 말한다.

TC = int(input())
for _ in range(TC):
    result = "YES"
    number = int(input())

    for i in range(2, 1000001):
        if number % i == 0:
            result = "NO"
            break
    print(result)
