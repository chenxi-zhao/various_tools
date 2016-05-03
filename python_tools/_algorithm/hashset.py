# coding=utf-8
__author__ = 'chenxi'


class HashSet:

    def __init__(self, contents=[], size=10):
        self.items = [None] * size
        self.numItems = 0
        for item in contents:
            self.add(item)

    def __getitem__(self, item):
        idx = hash(item) % len(self.items)
        while self.items[idx] != None:
            if self.items[idx] == item:
                return self.items[idx]
            idx = (idx + 1) % len(self.items)
        return None

    def __add(item, items):
        idx = hash(item) % len(items)
        loc = -1
        while items[idx] != None:  # 线性探测 linear probing， Hash碰撞 Collision
            if items[idx] == item:
                return False  # item already in set
            if loc < 0 and type(items[idx]) == HashSet.__Placeholder:
                loc = idx
            idx = (idx + 1) % len(items)
        if loc < 0:
            loc = idx
        items[loc] = item
        return True

    def __rehash(oldList, newList):  # 重新计算hash值，存储数据
        for x in oldList:
            if x is not None and type(x) != HashSet.__Placeholder:
                HashSet.__add(x, newList)
        return newList

    def add(self, item):
        if HashSet.__add(item, self.items):
            self.numItems += 1
            load = self.numItems / len(self.items)
            if load >= 0.75:  # 超过hash负载因子load factor 扩大集合的大小
                newsize = [None] * 2 * len(self.items)
                self.items = HashSet.__rehash(self.items, newsize)

    class __Placeholder:

        def __init__(self):
            pass

        def __eq__(self, other):
            return False

    def __remove(item, items):
        idx = hash(item) % len(items)

        while items[idx] != None:
            if items[idx] == item:
                nextIdx = (idx + 1) % len(items)
                if items[nextIdx] == None:
                    items[idx] = None
                else:
                    items[idx] = HashSet.__Placeholder()
                return True
            idx = (idx + 1) % len(items)
        return False

    def remove(self, item):
        if HashSet.__remove(item, self.items):
            self.numItems -= 1
            load = max(self.numItems, 10) / len(self.items)
            if load <= 0.25:
                newsize = [None] * int(len(self.items) / 2)
                self.items = HashSet.__rehash(self.items, newsize)
        else:
            raise KeyError("Item not in HashSet")

    def discard(self, item):
        if HashSet.__remove(item, self.items):
            self.numItems -= 1
            load = max(self.numItems, 10) / len(self.items)
            if load <= 0.25:
                newsize = [None] * int(len(self.items) / 2)
                self.items = HashSet.__rehash(self.items, newsize)

    def __contains__(self, item):
        idx = hash(item) % len(self.items)
        while self.items[idx] != None:
            if self.items[idx] == item:
                return True
            idx = (idx + 1) % len(self.items)
        return False

    def __iter__(self):
        for i in range(len(self.items)):
            if self.items[i] != None and type(self.items[i]) != HashSet.__Placeholder:
                yield self.items[i]

    def difference_update(self, other):
        for item in other:
            self.discard(item)

    def difference(self, other):
        result = HashSet(self)
        result.difference_update(other)
        return result










