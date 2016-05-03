from functools import wraps
# coding=utf-8
__author__ = 'chenxi'


def my_shiny_new_decorator(a_function_to_decorate):
    def the_wrapper_around_the_original_function():
        print("Before the function runs")
        # Call the function here (using parentheses)
        a_function_to_decorate()
        print("After the function runs")
    return the_wrapper_around_the_original_function


# Now imagine you create a function you don't want to ever touch again.
def a_stand_alone_function():
    print("I am a stand alone function, don't you dare modify me")


# a_stand_alone_function()
# outputs: I am a stand alone function, don't you dare modify me
# a_stand_alone_function_decorated = my_shiny_new_decorator(a_stand_alone_function)
# a_stand_alone_function_decorated()
# 调用时another_stand_alone_function =
# my_shiny_new_decorator(another_stand_alone_function)
@my_shiny_new_decorator
def another_stand_alone_function():
    print("Leave me alone")

another_stand_alone_function()


def bread(func):
    def wrapper():
        print("</''''''\>")
        func()
        print("<\______/>")

    return wrapper


def ingredients(func):
    def wrapper():
        print("#tomatoes#")
        func()
        print("~salad~")

    return wrapper


def sandwich(food="--ham--"):
    print(food)


# sandwich()
# outputs: --ham--
# sandwich = bread(ingredients(sandwich))
# sandwich()
@bread
@ingredients
def sandwich_2(food="--ham_2--"):
    print(food)

sandwich_2()


# Fibonacci数列的一系列方法演示
# 装饰器法decorator
def cache(func):
    caches = {}

    @wraps(func)
    def wrap(*args):
        # print("number:" + str(args) + "  caches:" + str(caches))
        if args not in caches:
            caches[args] = func(*args)

        return caches[args]
    return wrap


@cache
def fib_cache(n):
    if n == 0:
        return 0
    if n == 1:
        return 1
    else:
        return fib_cache(n - 1) + fib_cache(n - 2)

print("Fib_decorator------", fib_cache(10))


# memorized设置备忘,与上述的装饰器法类似，空间换时间，设置缓存
memo = {}


def fib_memorized(n):
    if n in memo:
        return memo[n]
    if n == 0:
        memo[0] = 0
        return 0
    if n == 1:
        memo[1] = 1
        return 1
    val = fib_memorized(n - 1) + fib_memorized(n - 2)
    memo[n] = val

    return val

print("fib_memorized------", fib_memorized(100))


# 直接递归
def fib_direct(n):
    assert n > 0, 'invalid n'
    if n < 3:
        return 1
    else:
        return fib_direct(n - 1) + fib_direct(n - 2)
# print(fib_direct(100))


# for循环 迭代
def fib_loop(n):
    a, b = 0, 1
    for i in range(1, n + 1):
        a, b = b, a + b
    return a
print("Fib_loop-----------", fib_loop(100))


# fib数组
def fib_array(n):
    if n == 1:
        return [1]
    if n == 2:
        return [1, 1]
    fibs = [1, 1]
    for i in range(3, n + 1):
        fibs.append(fibs[-1] + fibs[-2])  # 去最后两个数相加然后加载最后
    return fibs
print("Fib_Array--------", fib_array(10))






























