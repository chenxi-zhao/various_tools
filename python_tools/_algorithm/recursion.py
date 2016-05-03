# coding=utf-8
__author__ = 'chenxi'


# revrese list with for loop
def revListLoop(lst):
    accumulator = []
    for x in lst:
        accumulator = [x] + accumulator
    return accumulator
print(revListLoop([1, 2, 3, 4]))


# reverse list with recursion
def revListRecur(lst):
    if lst == []:
        return []
    revLst = revListRecur(lst[1:])
    first = lst[0:1]
    result = revLst + first
    return result
print(revListRecur([1, 2, 3, 4]))


def revListRecur2(lst):
    def revListHelper(index):
        if index == -1:
            return []
        restrev = revListHelper(index - 1)
        last = [lst[index]]
        # Now put the pieces together.
        result = last + restrev
        return result
    # this is the one line of code for the
    # revList2 function.
    return revListHelper(len(lst) - 1)
print(revListRecur2([1, 2, 3, 4]))


# reverse str with recursion
def revString(str):
    print("----str:", str)
    if str == "":
        return ""
    revStr = revString(str[1:])
    first = str[0:1]
    result = revStr + first
    print("----first(%s)+revStr(%s)=result(%s)" % (first, revStr, result))
    return result
print(revString("hello"))


# reverse sequence with recursion
def myreverse(seq):
    SeqType = type(seq)
    emptySeq = SeqType()
    if seq == emptySeq:
        return emptySeq
    revSeq = myreverse(seq[1:])
    first = seq[0:1]
    result = revSeq + first
    return result
print(myreverse("hello"))






