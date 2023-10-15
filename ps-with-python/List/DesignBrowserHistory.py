class Node:
    def __init__(self, value, prev=None, next=None):
        self.value = value
        self.prev = prev
        self.next = next

class BrowserHistory(object):
    def __init__(self, url):
        self.head = self.current = Node(url)

    def visit(self, url):
        new_node = Node(url)
        self.current.next = new_node
        new_node.prev = self.current
        self.current = new_node

    def back(self, steps):
        while steps > 0 and self.current.prev != None:
            steps -= 1
            self.current = self.current.prev
        return self.current.value

    def forward(self, steps):
        while steps > 0 and self.current.next != None:
            steps -= 1
            self.current = self.current.next
        return self.current.value

browserhistory = BrowserHistory("leetcode.com")
print(browserhistory.visit("google.com"))
print(browserhistory.visit("facebook.com"))
print(browserhistory.visit("youtube.com"))
print(browserhistory.back(1))
print(browserhistory.back(1))
print(browserhistory.forward(1))
print(browserhistory.visit("linkedin.com"))
print(browserhistory.forward(2))
print(browserhistory.back(2))
print(browserhistory.back(7))
