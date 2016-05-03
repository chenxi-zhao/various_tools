import math
# coding=utf-8
__author__ = 'chenxi'


# 桶排序
def buck_sort(A):
    bucks = dict()      # 桶
    for i in A:
        bucks.setdefault(i, [])  # 每个桶默认为空列表
        bucks[i].append(i)      # 往对应的桶中添加元素
    A_sort = []
    for i in range(min(A), max(A) + 1):
        if i in bucks:                  # 检查是否存在对应数字的桶
            A_sort.extend(bucks[i])     # 合并桶中数据
    return A_sort

buckarray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
print("buckarray:", buck_sort(buckarray))


# 基数排序
def radix_sort(array, radix=10):
    """array为整数列表， radix为基数"""
    K = int(math.ceil(math.log(max(array), radix)))  # 用K位数可表示任意整数
    bucket = [[] for i in range(radix)]  # 不能用 [[]]*radix
    for i in range(1, K + 1):  # K次循环
        for val in array:
            # 析取整数第K位数字 （从低到高）
            k = val % (radix ** i) // (radix ** (i - 1))
            bucket[k].append(val)
            # print("bucket[%d]: " % k, bucket[k])
        del array[:]
        # print("%d---------bucket:" % i, bucket)
        for each in bucket:
            array.extend(each)  # 桶合并
        bucket = [[] for i in range(radix)]

radixtarray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
radix_sort(radixtarray)
print("radixtarray:", radixtarray)


# 选择排序法
def select_sort(array):
    length = len(array)
    for i in range(length - 1):
        min = i
        for j in range(i + 1, length):
            if array[j] < array[min]:
                min = j
        array[min], array[i] = array[i], array[min]

selectarray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
select_sort(selectarray)
print("selectarray:", selectarray)












