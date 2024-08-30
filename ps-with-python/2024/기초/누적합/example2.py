# 수열 가장 크게 만들기 (#1912)
# 수열의 길이 N과 수열이 주어진다.
# 연속된 몇 개의 수를 선택하여 구할 수 있는 합 중에서, 가장 큰 합을 구하는 프로그램을 작성하시오

"""
10
10 -4 3 1 5 6 -35 12 21 -1

33
"""

N = int(input())
arr = list(map(int, input().split()))

prefix = [0 for _ in range(N + 1)]

for i in range(N):
  prefix[i + 1] = max(prefix[i] + arr[i], arr[i]) # 이전까지의 합과 현재값을 비교해서 큰값을 저장

print(max(prefix)) # 최대값 출력