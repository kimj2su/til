# 문제 5. 모이기(#1090)
# N명의 학생들이 모각코를 하기 위해서 한 곳에서 모이려고
#
# 학생들은 어디에 모여도 괜찮으나 모든 사람들의 이동 거리를 합쳤을 때 갖아 적은 이동 거리였으면 좋겠다고
# N명의 학생의 집의 위치가 Y,X,2차원으로 주어졌을때
# 1, 2, 3..N명의 학생들이 모일 수 있는 최소 거리를 계산하는 프로그램을
#
# 조건
# - N - 50 이하의 자연수
# - X, Y의 좌표는 1_000_000 이하의 자연수
#
"""
4
15 14
15 16
14 15
16 15
0 2 3 4
"""

# 모든 위치에서
# 모든 친구들의 거리를 계산해서
# 가장 적은 값을 알려주면 된다.
# 1번 아이디어
# X, Y를 구분해서 계산해준 뒤에 합쳐서 최솟값을 알려주면 된다.

# 최소 거리를 계산하고 싶다
# 그리고 2명이 모여야한다.
# 그 점에서 가까운 두명의 거리만 더해주면 되지 않을까?

# A 번집, B 번집, C 번집

# [1,2,3]  [3.4.5] [2, 2, 5]
# 두 사람이 모일 수 있는 최소거리는 -> 31

# for 반복문
# 입력과 출력
# 배열 List
# 정렬 sort

n = int(input())
arr = []
arr_y = []
arr_x = []
answer = [-1]*n

# 입력 받기
for _ in range(n):
    a, b = map(int, input().split())
    arr.append([a, b])
    arr_y.append(b)
    arr_x.append(a)

print("arr:", *arr)
print("arr_y:", *arr_y)
print("arr_x:", *arr_x)
# 만날 장소 정하기
for y in arr_y:
    for x in arr_x:
        dist = []
        print("y, x:", y, x)

        # 만날 장소로 각각의 점들이 오는 비용 계산하기
        for ex, ey in arr:
            d = abs(ex-x) + abs(ey-y)
            dist.append(d)
            print("dist:", *dist)


        # 가까운 순서대로 정렬하기
        dist.sort()
        print("sort dist:", *dist)
        print("===========================================")

        tmp = 0
        for i in range(len(dist)):
            d = dist[i]
            tmp += d
            if answer[i] == -1:
                answer[i] = tmp
            else:
                answer[i] = min(tmp, answer[i])
        print("answer:", *answer)
        print("one roof===========================================")
print(*answer)