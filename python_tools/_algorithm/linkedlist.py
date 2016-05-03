# coding=utf-8
__author__ = 'chenxi'


class LinkedList:

    """docstring for LinkedList"""

    class __Node:

        def __init__(self, item, next=None):
            self.__item = item
            self.__next = next

        def getItem(self):
            return self.__item

        def setItem(self, item):
            self.__item = item

        def getNext(self):
            return self.__next

        def setNext(self, item):
            self.__next = item

    def __init__(self, contents=[]):
        self.first = LinkedList.__Node(None, None)
        self.last = self.first
        self.numItems = 0
        for i in contents:
            self.append(i)

    def __getitem__(self, index):
        if index >= 0 and index < self.numItems:
            cursor = self.first.getNext()
            for i in range(index):
                cursor = cursor.getNext()
            return cursor.getItem()
        raise IndexError("LinkedList index out of range")

    def __setitem__(self, index, val):
        if index >= 0 and index < self.numItems:
            cursor = self.first.getNext()
            for i in range(index):
                cursor = cursor.getNext()
            cursor.setItem(val)
            return
        raise IndexError("LinkedList assignment index out of range")

    def __add__(self, other):
        if type(self) != type(other):
            raise TypeError("object undefined for " + str(type(self)) + " + " + str(type(other)))
        result = LinkedList()

        cursor = self.first.getNext()
        while cursor is not None:
            result.append(cursor.getItem())
            cursor = cursor.getNext()

        cursor = other.first.getNext()
        while cursor is not None:
            result.append(cursor.getItem())
            cursor = cursor.getNext()
        return result

    def append(self, item):
        node = LinkedList.__Node(item)
        self.last.setNext(node)
        self.last = node
        self.numItems += 1

    # front insert
    def insert(self, index, item):
        cursor = self.first
        if index < self.numItems:
            for i in range(index):
                cursor = cursor.getNext()
            node = LinkedList.__Node(item, cursor.getNext())
            cursor.setNext(node)
            self.numItems += 1
        else:
            self.append(item)

    def __delitem__(self, index):
        cursor = self.first
        if index < self.numItems:
            for i in range(index):
                cursor = cursor.getNext()
            node = cursor.getNext()
            cursor.setNext(node.getNext())
            del node
            self.numItems -= 1
        else:
            raise IndexError("LinkedList delete index out of range")

    def __iter__(self):
        cursor = self.first
        for x in range(self.numItems):
            cursor = cursor.getNext()
            yield cursor.getItem

    # and so on




































