# coding=utf-8
__author__ = 'chenxi'


# 冒泡排序
def bubble_sort(array):
    for i in range(len(array) - 1, 0, -1):
        # print("----------i", i)
        for j in range(i):
            # print("-----j", j)
            if array[j] > array[j + 1]:
                tmp = array[j + 1]
                array[j + 1] = array[j]
                array[j] = tmp
    return array


# 快速排序
def quick_sort(array, start, end):
    def partition(array, left, right):
        flag = array[left]
        while left < right:
            while left < right and array[right] >= flag:
                right -= 1
            if left < right:
                array[left] = array[right]
                left += 1
            while left < right and array[left] <= flag:
                left += 1
            if left < right:
                array[right] = array[left]
                right -= 1
        array[left] = flag
        return left
    if start < end:
        pivotpos = partition(array, start, end)
        quick_sort(array, start, pivotpos - 1)
        quick_sort(array, pivotpos + 1, end)


# 直接插入排序
def insert_sort(array):
    length = len(array)
    for i in range(1, length):
        # 往有序数组s[:i]中插入元素s[i],当新元素比末尾元素小时，往前插入
        if array[i] < array[i - 1]:
            current = array[i]
            j = i - 1
            while current < array[j] and j >= 0:
                array[j + 1] = array[j]  # 单独移动
                j = j - 1
                # 注意：当最前面的元素比x小时，j=-1
                # 将1查找到比x大的元素之后的所有元素，后移一位
                # s[j+2:i+1] = s[j+1:i]    # 2. 采用统一移动
            array[j + 1] = current


# 二分插入排序，往有序区域中插入元素，但使用折半查找法寻找合适位置
def binary_insert_sort(array):
    length = len(array)
    for i in range(1, length):
        # 往有序数组s[:i]中插入元素s[i],当新元素比末尾元素小时，往前插入
        if array[i] < array[i - 1]:
            current = array[i]
            low = 0
            high = i - 1
            while low <= high:
                mid = (low + high) // 2
                if current < array[mid]:
                    high = mid - 1
                else:
                    low = mid + 1
            for j in range(i - 1, high, -1):
                array[j + 1] = array[j]
            array[high + 1] = current


# 希尔插入排序
def shell_insert_sort(array):
    gap = len(array) // 2
    while gap > 0:
        for i in range(gap, len(array)):  # 对每组数据进行组内排序
            # array[i,i+gap,i+2gap,i+ngap]
            # 对余数的处理，通过将间隔继续后移解决
            cur = array[i]
            j = i - gap
            while cur < array[j] and j >= 0:
                array[j + gap] = array[j]     # 元素后移
                j -= gap
            array[j + gap] = cur
        gap //= 2

buddlearray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
bubble_sort(buddlearray)
print("buddlearray:", buddlearray)

quickarray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
quick_sort(quickarray, 0, len(quickarray) - 1)
print("quickarray:", quickarray)

insertarray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
insert_sort(insertarray)
print("insertarray:", insertarray)

binaryinsertarray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
binary_insert_sort(binaryinsertarray)
print("binaryinsertarray:", binaryinsertarray)

shellinsertarray = [8, 10, 9, 6, 4, 16, 5, 13, 26, 18, 2, 45, 34, 23, 1, 7, 3]
shell_insert_sort(shellinsertarray)
print("shellinsertarray:", shellinsertarray)
