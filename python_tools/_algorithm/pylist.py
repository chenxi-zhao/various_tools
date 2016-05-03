# coding=utf-8
__author__ = 'chenxi'


class PyList:

    """docstring for PyList"""

    def __init__(self, contents=[], size=10):
        self.items = [None] * size
        self.numItems = 0
        self.size = size
        for e in contents:
            self.append(e)

    def __getitem__(self, index):
        if index >= 0 and index <= self.numItems:
            return self.items[index]
        raise IndexError("PyList index out of range")

    def __setitem__(self, index, value):
        if index >= 0 and index <= self.numItems:
            self.items[index] = value
        raise IndexError("PyList assignment index out of range")

    def __add__(self, other):
        result = PyList(size=self.numItems + other.numItems)
        for i in range(self.numItems):
            result.append(self.items[i])
        for j in range(other.numItems):
            result.append(other.items[j])
        return result

    def __makeroom(self):
        # increase list size by 1/4 to make more room.
        # add one in case for some reason self.size is 0.
        newlength = (self.size // 4) + self.size + 1
        newlist = [None] * newlength
        for i in range(self.numItems):
            newlist[i] = self.items[i]

        self.items = newlist
        self.size = newlength

    def append(self, item):
        if self.numItems == self.size:
            self.__makeroom()
        self.items[self.numItems] = item
        self.numItems += 1

    def insert(self, index, item):
        if self.numItems == self.size:
            self.__makeroom()
        if index < self.numItems:
            for j in range(self.numItems - 1, index - 1, -1):
                self.items[j + 1] = self.items[j]
            self.items[index] = item
            self.numItems += 1
        else:
            self.append(item)

    def __delitem__(self, index):
        for i in range(index, self.numItems - 1):
            self.items[i] = self.items[i + 1]
        self.numItems -= 1
        self.items[self.numItems] = None

    def __eq__(self, other):
        if type(other) != type(self):
            return False
        if self.numItems != other.numItems:
            return False
        for i in range(self.numItems):
            if self.items[i] != other.items[i]:
                return False
        return True

    def __iter__(self):
        for i in range(self.numItems):
            yield self.items[i]

    def __len__(self):
        return self.numItems

    def __contains__(self, item):
        for i in range(self.numItems):
            if self.items[i] == item:
                return True
        return False

    def __str__(self):
        selfStr = "["
        for i in range(self.numItems):
            selfStr = selfStr + repr(self.items[i])
            if i < self.numItems - 1:
                selfStr = selfStr + ","
        selfStr = selfStr + "]"
        return selfStr

    def __repr__(self):
        selfStr = "PyList(["
        for i in range(self.numItems):
            selfStr = selfStr + repr(self.items[i])
        if i < self.numItems - 1:
            selfStr = selfStr + ", "
        selfStr = selfStr + "])"
        return selfStr
















