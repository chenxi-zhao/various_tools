from functools import reduce
# coding=utf-8
__author__ = 'chenxi'


# 高阶函数(Higher-order function)
# 将函数赋值给变量
print("--------------------------函数式编程")

fabs = abs
print(fabs(-10))  # 输出10

# 那么函数名是什么呢？函数名其实就是指向函数的变量！
# 对于abs()这个函数，完全可以把函数名abs看成变量，它指向一个可以计算绝对值的函数！


def absadd(x, y, f):
    return f(x) + f(y)

x = -5
y = 6
f = abs
print(absadd(x, y, f))
# 把函数作为参数传入，这样的函数称为高阶函数，函数式编程就是指这种高度抽象的编程范式


def xx(x):  # map/reduce
    return x * x
print("--------------------------map/reduce测试")

mylist = [1, 2, 3, 4, 5, 6, 7, 8, 9]
r = list(map(xx, mylist))  # 计算序列中每个数的平方
print(r)


def add(x, y):
    return x + y
# reduce(f, [x1, x2, x3, x4]) = f(f(f(x1, x2), x3), x4)
print(reduce(add, [1, 3, 5, 7, 9]))  # 计算序列和


def fn(x, y):
    return x * 10 + y
# [1, 3, 5, 7, 9]转换为13578
print("1,3,5,7,9转成整数13579：", reduce(fn, [1, 3, 5, 7, 9]))


def char2num(s):  # 字符转数字
    return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s]

print("字符串13579转成整数13579：", reduce(fn, map(char2num, '13579')))


def str2int(s):  # 字符串转整数函数
    def fn(x, y):
        return x * 10 + y

    def char2num(s):
        return {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9}[s]
    return reduce(fn, map(char2num, s))

print("字符串13579转成整数13579：", str2int("13579"))


def str2int(s):  # lambda x, y: x * 10 + y 计算效果等于fn
    return reduce(lambda x, y: x * 10 + y, map(char2num, s))  # 使用lambda表达式


print("--------------------------filter测试")


def is_odd(n):  # 删掉偶数，只保留奇数
    return n % 2 == 1
print("删掉偶数，只保留奇数", list(filter(is_odd, [1, 2, 4, 5, 6, 9, 10, 15])))
# 结果: [1, 5, 9, 15]


def not_empty(s):
    return s and s.strip()
print("删除序列中的空字符串", list(filter(not_empty, ['A', '', 'B', None, 'C', '  '])))
# 结果: ['A', 'B', 'C']


def primes_compute():  # 计算素数 埃氏筛法
    def _odd_iter():  # 生成一个从三开始的奇数序列
        n = 1
        while True:
            n = n + 2
            yield n

    def _not_divisible(n):  # 定义一个筛选函数  特殊、、
        return lambda x: x % n > 0

    yield 2
    it = _odd_iter()
    while True:
        n = next(it)
        yield n
        it = filter(_not_divisible(n), it)  # 构造新序列
# 打印20以内的素数:
print("----------prime")
for n in primes_compute():
    if n < 20:
        print("素数", n)
    else:
        break
print("----------prime")


print("--------------------------sorted函数测试")  # sorted函数测试
print(sorted([36, 5, -12, 9, -21], key=abs))
print(sorted(['bob', 'about', 'Zoo', 'Credit'], key=str.lower))
print(sorted(['bob', 'about', 'Zoo', 'Credit'], key=str.lower, reverse=True))


def lazy_sum(*args):
    def sum():
        ax = 0
        for n in args:
            ax = ax + n
        return ax
    return sum

print("---------------------函数作为返回值")
func_lazy = lazy_sum(1, 2, 3, 4, 5)
print(func_lazy)  # lazy_sum(1, 2, 3, 4, 5)返回值是一个函数引用当调用此函数的时候有返回值
print(func_lazy())
func_lazy2 = lazy_sum(1, 2, 3, 4, 5)
print(func_lazy == func_lazy2)  # 结果为false 两个函数调用结果不影响


def count1():
    fs = []
    for i in range(1, 4):
        def f():
            return i * i
        fs.append(f)
    return fs


def count2():  # 闭包（Closure）
    def f(j):
        def g():
            return j * j
        return g
    fs = []
    for i in range(1, 4):
        fs.append(f(i))  # f(i)立刻被执行，因此i的当前值被传入f()
    return fs


# 函数装饰器（Decorator）
def test_decorator():
    print("-------------------")
print("-------------------------函数装饰器（Decorator）")
print(test_decorator.__name__)
