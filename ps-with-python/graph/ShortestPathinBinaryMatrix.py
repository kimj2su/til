# https://leetcode.com/problems/shortest-path-in-binary-matrix/description/
from collections import deque

def shortestPath(grid):
    shortest_path = -1
    row = len(grid)
    col = len(grid[0])
    visited = [[False] * col for _ in range(row)]

    dx = [-1, 1, 0, 0, -1, -1, 1, 1]
    dy = [0, 0, -1, 1, -1, 1, -1, 1]
    queue = deque()
    if grid[0][0] == 1:
        return shortest_path
    queue.append((0, 0, 1))
    visited[0][0] = True

    while queue:
        current_x, current_y, current_len = queue.popleft()
        if current_y == row -1 and current_x == col - 1:
            shortest_path = current_len
            break
        # 연결되어이는 vertex 확인하기
        for i in range(8):
            next_x = current_x + dx[i]
            next_y = current_y + dy[i]
            if 0 <= next_x < row and 0 <= next_y < col:
                if grid[next_x][next_y] == 0 and not visited[next_x][next_y]:
                    visited[next_x][next_y] = True
                    queue.append((next_x, next_y, current_len + 1))

    return shortest_path

print(shortestPath(grid=[
    [0, 0, 0],
    [1, 1, 0],
    [1, 1, 0]
]))