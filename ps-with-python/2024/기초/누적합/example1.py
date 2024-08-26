# 1. 수열 ( #2259 누적합)
# 수열의 길이 A와 간격 B가 주어진다.
# 그리고 수열이 하나 주어집니다.
# 주언진 간격만큼의 합을 구해서, 가장 큰수를 출력하는 프로그램을 작성하세요.
# 입력
"""
10 2
3 -2 -4 -9 0 3 7 13 8 -3
"""
"""
  1  -6  -13 -9 3 10 20 21 5
  3 1 -3 -12 -12 -9 -2  11 19 16
"""

A, B = map(int, input().split())
arr = list(map(int, input().split()))

prefix = [0 for _ in range(A + 1)] # 1칸 더 크게 만들기 !

for i in range(A) :
    prefix[i + 1] = prefix[i] + arr[i]

answer = []
for k in range(B, A + 1):
    answer.append(prefix[k] - prefix[k - B])

print(arr)
print(prefix)
print(answer)