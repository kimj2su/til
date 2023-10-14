# 파이썬의 List는 ArrayList로 구현되어 있다.
# LinkedList를 구현하고자 할 때는, 직접 구현해야 한다. => Node를 구현해야 한다.

class Node :
    def __init__(self, value, next = None):
        self.value = value
        self.next = next

first = Node(1)
second = Node(2)
third = Node(3)
first.next = second
second.next = third
first.value = 6
# print(first.next.next.value)


class LinkedList(object):
    def __init__(self):
        self.head = None
        self.tail = None
    def append(self, value):
        # 코드를 쓰세요
        new_node = Node(value)
        if self.head is None:
            self.head = new_node
            self.tail = new_node
        else:
            # O(1)의 시간 복잡도를 가진다.
            self.tail.next = new_node
            self.tail = self.tail.next
            # O(n)의 시간 복잡도를 가진다.
            # currunt = self.head
            # while currunt.next is not None:  # (currunt.next)
            #     currunt = currunt.next
            # currunt.next = new_node
    def get(self, idx):
        currunt = self.head
        for i in range(idx):
            currunt = currunt.next
        return currunt

    def insert_at(self, idx, value):
        new_node = Node(value)
        if idx == 0:
            new_node.next = self.head
            self.head = new_node
        else:
            currunt = self.get(idx - 1)
            next_node = currunt.next
            currunt.next = new_node
            new_node.next = next_node

    def remove_at(self, idx):
        if idx == 0:
            if self.head is not None:
                self.head = self.head.next
        else:
            currunt = self.get(idx - 1)
            currunt.next = self.get(idx).next


li = LinkedList()
li.append(1)
li.append(2)
li.append(3)
li.append(4)
li.append(5)
# 조회의 시간 복잡도는 O(n)이다.
li.get(1)
li.get(2)
li.get(3)
# li.insert_at(idx = 2, value = 9)

print(li.get(3).value)
li.remove_at(3)
print(li.get(3).value)