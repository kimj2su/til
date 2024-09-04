# 이모스의 장애물 경주 ( #3020, #17611 )
#
# 이모스법 : https://imoz.jp/algorithms/imos_method.html
#
# 5명의 사람들이 장애물 경주를 하려고 합니다.
#
# 각각의 레인에는 장애물이 설치되는데, 이 장애물은 길이가 제각각입니다.
#
# 어떤 장애물은 1번 레인에서 3번 레인을 가로막고
#
# 어떤 장애물은 4번 레인에서 5번 레인을 가로막습니다.
#
# 장애물은 순서대로 1번 레인에서 설치되거나, 5번 레인에서 설치됩니다.
#
# 이때, 가장 장애물이 적은 레인의 장애물은 몇 개인지 계산하시오.
"""
6 7
1
5
3
3
5
1

2 3
"""

n, h = map(int,input().split())

line = [0 for _ in range(h)]

for t in range(n):
  height = int(input())
  if t%2 == 0:
    line[0] += 1
    line[height] -= 1

  if t%2 == 1:
    line[h-height] += 1

prefix = [0 for _ in range(h+1)]

for i in range(h):
  prefix[i+1] = prefix[i] + line[i]

prefix = prefix[1:]

print(min(prefix), prefix.count(min(prefix)))