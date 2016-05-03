# coding=utf-8
__author__ = 'chenxi'


def exceptiont_test(div):
    try:
        print("try...")
        r = 10 / div
        print("result: ", r)
    except ZeroDivisionError as e:
        print("exception: ", e)
    finally:
        print('finally...')
    print("end...")

exceptiont_test(0)


def foo(s):
    n = int(s)
    assert n != 0, 'n is zero!'
    return 10 / n

foo('0')
