import math
# coding=utf-8
__author__ = 'TracyZro'


def myabs(x):
    if not isinstance(x, (int, float)):  # 只允许整数和浮点数的求绝对值
        raise TypeError('bad operand type')
    if x >= 0:
        return x
    else:
        return -x


absx = myabs(-10)
print(absx)


def nop():
    pass


def move(x, y, step, angle=0):
    nx = x + step * math.cos(angle)
    ny = y - step * math.sin(angle)
    return nx, ny


ma, mb = move(100, 100, 20, math.pi / 6)
mr = move(100, 100, 20, math.pi / 6)
mg = move(100, 100, 20)
print(ma, mb)
print(mr)
print(mg)


def add_end(mylist=None):  # 默认参数必须指向不变对象
    if mylist is None:
        mylist = []
    mylist.append('END')
    return mylist


print(add_end())
print(add_end())


def calc(*numbers):  # 可变参数
    mysum = 0
    for n in numbers:
        mysum = mysum + n * n
    return mysum


print(calc(1, 2, 3))
print(calc(1, 2, 3, 4, 5))
nums = [1, 2, 3]
print(calc(*nums))

# 组合参数
# 在Python中定义函数，可以用必选参数、默认参数、可变参数、关键字参数和命名关键字参数，
# 这5种参数都可以组合使用，除了可变参数无法和命名关键字参数混合。
# 但是请注意，参数定义的顺序必须是：必选参数、默认参数、可变参数/命名关键字参数和关键字参数。


def f1(a, b, c=0, *args, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'args =', args, 'kw =', kw)


def f2(a, b, c=0, *, d, **kw):
    print('a =', a, 'b =', b, 'c =', c, 'd =', d, 'kw =', kw)


f1(1, 2)
f1(1, 2, c=3)
f1(1, 2, 3, 'a', 'b')
f1(1, 2, 3, 'a', 'b', x=99)
f2(1, 2, d=99, ext=None)

argss = (1, 2, 3, 4)
args2 = (1, 2, 3)
kws = {'d': 99, 'x': '#'}
f1(*argss, **kws)
f2(*args2, **kws)


# Python的函数具有非常灵活的参数形态，既可以实现简单的调用，又可以传入非常复杂的参数
# 默认参数一定要用不可变对象，如果是可变对象，程序运行时会有逻辑错误！
# 要注意定义可变参数和关键字参数的语法：
# *args是可变参数，args接收的是一个tuple；
# **kw是关键字参数，kw接收的是一个dict。
# 以及调用函数时如何传入可变参数和关键字参数的语法：
# 可变参数既可以直接传入：func(1, 2, 3)，又可以先组装list或tuple，再通过*args传入：func(*(1, 2, 3))；
# 关键字参数既可以直接传入：func(a=1, b=2)，又可以先组装dict，再通过**kw传入：func(**{'a': 1, 'b': 2})。
# 使用*args和**kw是Python的习惯写法，当然也可以用其他参数名，但最好使用习惯用法。
# 命名的关键字参数是为了限制调用者可以传入的参数名，同时可以提供默认值。
# 定义命名的关键字参数不要忘了写分隔符*，否则定义的将是位置参数。
