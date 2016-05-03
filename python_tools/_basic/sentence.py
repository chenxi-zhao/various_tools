# coding=utf8
__author__ = 'TracyZro'


# if 基本用法

flag = False
name = 'luren'
if name == 'python':  # 判断变量否为'python'
    flag = True  # 条件成立时设置标志为真
    print('welcome boss')  # 并输出欢迎信息
else:
    print(name)  # 条件不成立时输出变量名称

num = 5
if num == 3:  # 判断num的值
    print('boss')
elif num == 2:
    print('user')
elif num == 1:
    print('worker')
elif num < 0:  # 值小于零时输出
    print('error')
else:
    print('roadman')  # 条件均不成立时输出

myString = '''
fdf
fdsfa
'''

# while循环
i = 1
while i < 10:
    i += 1
    if i % 2 > 0:  # 非双数时跳过输出
        continue
    print(i)  # 输出双数2、4、6、8、10

var = 1
while var == 1:  # 该条件永远为true，循环将无限执行下去
    num = input("Enter a number  :")
    print("You entered: ", num)

# for循环
for letter in 'Python':  # First Example
    print('Current Letter :', letter)

fruits = ['banana', 'apple', 'mango']
for fruit in fruits:  # Second Example
    print('Current fruit :', fruit)

fruits = ['banana', 'apple', 'mango']
for index in range(len(fruits)):  # len是求长函数
    print('Current fruit :', fruits[index])


# break continue pass  同java
