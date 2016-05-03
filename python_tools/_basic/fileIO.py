from io import StringIO, BytesIO
import os
import pickle
# coding=utf-8
__author__ = 'chenxi'


# 打印到屏幕
print("Python is really a great language!")


# 读取键盘输入
# str = input("Enter your input: ")
# print("Received input is : ", str)

# 文件读取
try:
    f = open('../test.txt', 'r')
    print(f.read())
except Exception as e:
    raise e
finally:
    if f:
        f.close()


# 读取文件内容
with open('../test.txt', 'r') as f:
    print(f.read())


# 按块大小读取文件内容
def read_file_block(fpath):
    BLOCK_SIZE = 1024
    with open(fpath, 'rb') as f:
        while True:
            block = f.read(BLOCK_SIZE)
            if block:
                yield block.strip()
            else:
                return


# 按文件行读取文件内容
def read_file_line(fpath):
    with open(fpath, 'rb') as f:
        while True:
            blockline = f.readline()
            # 调用readlines()可以一次读取所有内容并按行返回list
            if blockline:
                yield blockline.strip()
            else:
                return
for linestr in read_file_line('../test.txt'):
    print(linestr)


# StringIO BytesIO
strf = StringIO()
strf.write('hello world')
print(strf.getvalue())

f = BytesIO()
f.write('中文'.encode('utf-8'))
print(f.getvalue())


# 操作文件目录等
print(os.name)

# 序列化
d = dict(name='Bob', age=20, score=88)
print(pickle.dumps(d))

with open('../dump.txt', 'wb') as f:
    pickle.dump(d, f)

with open('../dump.txt', 'rb') as f:
    print(pickle.load(f))

# json序列化


































