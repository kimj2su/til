# 2차원 누적합(#11660)
# 직사각형의 좌측 위 좌표와 우측 아래 좌표가 주어졌을 때 그 범위의 합을 구하는 프로그램을 작성하시오.
"""
1 2 3 4
2 3 4 5
3 4 5 6
4 5 6 7
2 2 3 4

27
"""
graph = [list(map(int, input().split())) for _ in range(4)]
print(graph)

x1, y1, x2, y2 = map(int, input().split())

prefix = [[ 0 for _ in range(5) ] for _ in range(5)]

for y in range(4):
  for x in range(4):
    prefix[y + 1][x + 1] = prefix[y][x + 1] + prefix[y + 1][x] - prefix[y][x] + graph[y][x]

print(prefix)

answer = prefix[y2][x2] - prefix[y1 - 1][x2] - prefix[y2][x1 - 1] + prefix[y1 - 1][x1 - 1]

print(answer)