# https://leetcode.com/problems/number-of-islands/
from collections import deque


def numIslands(grid):
    number_of_islands = 0
    m = len(grid)
    n = len(grid[0])
    visited = [[False] * n for _ in range(m)]

    def bfs(x, y):
        dx = [-1, 1, 0, 0]
        dy = [0, 0, -1, 1]
        visited[x][y] = True
        queue = deque()
        queue.append((x, y))
        while queue:
            current_x, current_y = queue.popleft()
            for k in range(4):
                next_x = current_x + dx[k]
                next_y = current_y + dy[k]
                if 0 <= next_x < m and 0 <= next_y < n and grid[next_x][next_y] == "1" and not visited[next_x][next_y]:
                    visited[next_x][next_y] = True
                    queue.append((next_x, next_y))


    for i in range(m):
        for j in range(n):
            if grid[i][j] == "1" and not visited[i][j]:
                number_of_islands += 1
                bfs(i, j)
    return number_of_islands


print(numIslands(grid=[
    ["1", "1", "1", "1", "0"],
    ["1", "1", "0", "0", "0"],
    ["0", "0", "1", "0", "0"],
    ["0", "0", "1", "0", "0"],
    ["0", "0", "0", "1", "1"]
]))
