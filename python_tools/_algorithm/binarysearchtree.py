# coding=utf-8
__author__ = 'chenxi'


class BinarySearchTree:
    '''二叉树'''

    class __Node:

        def __init__(self, val, left=None, right=None):
            self.val = val
            self.left = left
            self.right = right

        def getVal(self):
            return self.val

        def setVal(self, newval):
            self.val = newval

        def getLeft(self):
            return self.left

        def getRight(self):
            return self.right

        def setLeft(self, newleft):
            self.left = newleft

        def setRight(self, newright):
            self.right = newright

        def __iter__(self):
            if self.left is not None:
                for elem in self.left:
                    yield elem

            yield self.val

            if self.right is not None:
                for elem in self.right:
                    yield elem

    # Below are the methods of the BinarySearchTree class.
    def __init__(self):
        self.root = None

    def insert(self, val):
        # The __insert function is recursive and is not a passed a self parameter. It is a
        # static function (not a method of the class) but is hidden inside the insert
        # function so users of the class will not know it exists.
        def __insert(root, val):
            if root is None:
                return BinarySearchTree.__Node(val)
            if val < root.getVal():
                root.setLeft(__insert(root.getLeft(), val))
            else:
                root.setRight(__insert(root.getRight(), val))
            return root
        self.root = __insert(self.root, val)

    def __iter__(self):
        if self.root is not None:
            return self.root.__iter__()
        else:
            return [].__iter__()


def main():
    s = input("Enter a list of numbers: ")
    lst = s.split()

    tree = BinarySearchTree()

    for x in lst:
        tree.insert(float(x))

    for x in tree:
        print(x)

if __name__ == "__main__":
    main()
