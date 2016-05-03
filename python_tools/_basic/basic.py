# coding=utf-8
_author_ = "chenxi"

# Python中的字符串
mystr = 'Hello World!'

print(mystr)  # 输出完整字符串
print(mystr[0])  # 输出字符串中的第一个字符
print(mystr[2:5])  # 输出字符串中第三个至第五个之间的字符串
print(mystr[2:])  # 输出从第三个字符开始的字符串
print(mystr * 2)  # 输出字符串两次
print(mystr + "TEST")  # 输出连接的字符串

# Python中的list
mylist = ['abcd', 786, 2.23, 'john', 70.2]
tinylist = [123, 'john']

print(mylist)  # 输出完整列表
print(mylist[0])  # 输出列表的第一个元素
print(mylist[1:3])  # 输出第二个至第三个的元素
print(mylist[2:])  # 输出从第三个开始至列表末尾的所有元素
print(tinylist * 2)  # 输出列表两次
print(mylist + tinylist)  # 打印组合的列表

# Python中的Dictionary
testdict = {}
pass
testdict['one'] = "This is one"
testdict[2] = "This is two"

tinydict = {'name': 'john', 'code': 6734, 'dept': 'sales'}

print(testdict['one'])  # 输出键为'one' 的值
print(testdict[2])  # 输出键为 2 的值
print(tinydict)  # 输出完整的字典
print(tinydict.keys())  # 输出所有键
print(tinydict.values())  # 输出所有值

# Python数据类型转换
x = 15.20
y = int(x)
print(y)

# Python中的运算符

a = 10
b = 20
c = 0

if a and b:
    print("Line 1 - a and b are true")
else:
    print("Line 1 - Either a is not true or b is not true")

if a or b:
    print("Line 2 - Either a is true or b is true or both are true")
else:
    print("Line 2 - Neither a is true nor b is true")
