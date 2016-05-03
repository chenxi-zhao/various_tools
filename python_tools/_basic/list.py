# coding=utf-8
__author__ = 'TracyZro'

# List测试

list1 = ['physics', 'chemistry', 1997, 2000]
list2 = [1, 2, 3, 4, 5]
list3 = ["a", "b", "c", "d"]

print('list1[0]:', list1[0])
print('list2[-2]:', list2[-2])
print('list3[2:3]:', list3[2:3])

# 更新list元素
list1[1] = 'math'
print('list1[1]:', list1[1])

# 删除list元素
del list3[len(list3) - 1]
print('list3:', list3)

# Python列表脚本操作符
print('--------Python列表脚本操作符--------')
print([1, 2, 3] + [4, 5, 6])  # list合并
print(len([1, 2, 3]))  # List长度
print(['Hi!'] * 4)  # 重复输出
print(3 in [1, 2, 3])  # 元素是否存在List中
for x in [1, 2, 3]:  # 迭代
    print(x)

L = ['spam', 'Spam', 'SPAM!']

print(L[2])  # 'SPAM!'	读取列表中第三个元素
print(L[-2])  # 'Spam'	读取列表中倒数第二个元素
print(L[1:])  # ['Spam', 'SPAM!']	从第二个元素开始截取列表

# Python列表函数&方法
test1 = [1, 2, 3, 4, 5, 6]
aTuple = (123, 'xyz', 'zara', 'abc')

print(len(test1))  # 长度
print(max(test1))  # 最大值 最小值min
print(list(aTuple))  # tuple转list

test1.append(7)  # 在列表末尾添加新的对象
test1.count(1)  # 统计出现次数
test1.extend(list(aTuple))  # 在列表末尾一次性追加另一个序列中的多个值（用新列表扩展原来的列表）
test1.index(4)  # 从列表中找出某个值第一个匹配项的索引位置
test1.insert(4, 4)  # 将对象插入列表
test1.pop()  # 移除列表中的一个元素（默认最后一个元素），并且返回该元素的值
test1.remove(1)  # 移除列表中某个值的第一个匹配项

print(test1)
test1.reverse()

# test1.sort()对原列表进行排序

# ---------------------------------------------------
