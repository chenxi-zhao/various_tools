# coding=UTF-8
__author__ = 'chenxi'


def consumer():
    r = ''
    print("-------------------wai(r)>>(%s)" % (r))
    while True:
        print("-----------------------nei(r)>>(%s)" % (r))
        n = yield r
        print("-----------------------first(r, n)>>(%s, %s)" % (r, n))
        if not n:
            return
        print('[CONSUMER] Consuming %s...' % n)
        print("-----------------------second(r, n)>>(%s, %s)" % (r, n))
        r = '200 OK'


def produce(c):
    b = c.send(None)
    print("--------------------first-r,", b)
    n = 0
    while n < 5:
        n = n + 1
        print('[PRODUCER] Producing %s...' % n)
        r = c.send(n)
        print("-------------------------r", r)
        print('[PRODUCER] Consumer return: %s' % r)
    c.close()

c = consumer()
produce(c)
