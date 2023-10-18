# Recursion 재귀
재귀함수란 자신을 정의할 때 자기 자신을 재 참조하는 함수를 뜻한다.  

## 구성 요소 2가지

### recurrence relation -> 점화식
- 나 자신을 또 호출하는 관계 식

### base case -> 기저 사례
- 더 이상 recurrence relation을 분해할 수 없는 경우
- 재귀 호출을 끝내는 조건
- base case가 없으면 무한 루프에 빠질 수 있다.

## 시간 복잡도
재귀 함수 전체 시간 복잡도 = 재귀 함수 호출 수 * (재귀 함수 하나당) 시간 복잡도

# Tree
## 트리의 구성 요소
- 노드 : 트리를 구성하고 있는 각각의 요소
- 간선(Edge) : 노드간에 연결된 선
- 루트 노드(Root Node) : 트리는 항상 루트에서 시작합니다.
- 리프 노드(Leaf Node) : 자식 노드가 없는 노드
- 자식 노드(Child Node) : 노드는 0개 이상의 자식 노드를 가질 수 있다.
- 차수(degree) : 각 노드가 갖는 자식의 수, 모든 노드의 차수가 n개 이하면 트리를 n진 트리라고 합니다.
- 조상(ancestor) : 위로 올라가면서 만나는 모든 노드
- 자손(descendant) : 아래로 내려가면서 만나는 모든 노드
- 높이(height) : 루트 노드에서 가장 멀리 있는 리프 노드 까지의 거리, 즉 리프 노드중에 최대 레벨 값
- 서브트리(subtree) : 트리의 어떤 노드를 루트로 하고, 그 자손으로 구성된 트리를 subtree라고 한다.

## 트리 순회
트리 순회(Traversal)란 트리 탐색이라고도 불리우며 트리의 각 노드를 방문하는 과정을 말한다.  
모든 노드를 한 번씩 방문해야 하므로 완전 탐색이라고도 불린다. 순회 방법으로는 너비 우선 탐색의 BFS와 깊이 우선 탐색의 DFS가 있다.

## BFS
큐를 이용해 구현할 수 있다.

### 큐
```python
def bfs(root):
    visited = []
    if root is None:
        return
    queue = deque()
    queue.append(root)
    while queue:
        cur_node = queue.popleft()
        visited.append(cur_node.value)
        
        if cur_node.left:
            queue.append(cur_node.left)
        if cur_node.right:
            queue.append(cur_node.right)
    return visited
bfs(root)
```

## DFS
재귀와 스택을 이용해 구현할 수 있다.

### 재귀
```python
def dfs(current_node):
    if current_node is None:
        return
    dfs(current_node.left)
    dfs(current_node.right)
    
dfs(current_node)
```

### 전위 순회(Preorder Traversal)
루트부터 왼쪽 노드부터 순서대로 방문한다.
```python
def preorder_traversal(current_node):
    if current_node is None:
        return
    print(current_node.value)
    preorder_traversal(current_node.left)
    preorder_traversal(current_node.right)

preorder_traversal(current_node)
```

### 중위 순회(Inorder Traversal)
왼쪽 노드부터 방문한다.
```python
def inorder_traversal(current_node):
    if current_node is None:
        return
    inorder_traversal(current_node.left)
    print(current_node.value)
    inorder_traversal(current_node.right)

inorder_traversal(current_node)
```

### 후위 순회(Postorder Traversal)
왼쪽의 노드부터 자식노드를 다 방문한 후에 루트 노드를 방문한다.
```python
def postorder_traversal(current_node):
    if current_node is None:
        return
    postorder_traversal(current_node.left)
    postorder_traversal(current_node.right)
    print(current_node.value)
    
postorder_traversal(current_node)
```
```
