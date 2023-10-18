# Definition for a binary tree node.
from collections import deque
from typing import Optional


class TreeNode:
    def __init__(self, val=0, left=None, right=None):
        self.val = val
        self.left = left
        self.right = right


class Solution:
    def maxDepth(self, root: Optional[TreeNode]):
        if root is None:
            return 0

        return max(self.maxDepth(root.left), self.maxDepth(root.right)) + 1

node = TreeNode(3)
node.left = TreeNode(9)
node.right = TreeNode(20)
node.right.left = TreeNode(15)
node.right.right = TreeNode(7)

depth = Solution().maxDepth(node)
print(depth)


class Solution2:
    def maxDepth(self, root: Optional[TreeNode]):
        depth = 0
        if root is None:
            return depth
        queue = deque()
        queue.append((root, 1))
        while queue:
            node, current_depth = queue.popleft()
            if node.left:
                queue.append((node.left, current_depth + 1))
            if node.right:
                queue.append((node.right, current_depth + 1))
            depth = max(depth, current_depth)
        return depth
print(Solution2().maxDepth(node))


class DfsSolution:
    def dfs(self, root: Optional[TreeNode]):
        if root is None:
            return 0
        dfs_left = self.dfs(root.left)
        dfs_right = self.dfs(root.right)
        return max(dfs_left, dfs_right) + 1


node = TreeNode(3)
node.left = TreeNode(5)
node.left.left = TreeNode(6)
node.left.right = TreeNode(2)
node.left.right.left = TreeNode(7)
node.left.right.right = TreeNode(4)
node.right = TreeNode(1)
node.right.left = TreeNode(0)
node.right.right = TreeNode(8)

depth = Solution().maxDepth(node)

print(DfsSolution().dfs(node))
