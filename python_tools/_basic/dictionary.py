# coding=utf-8
__author__ = 'TracyZro'


# Python 字典(Dictionary)

# 访问字典里的值

mydict = {'Name': 'Zara', 'Age': 7, 'Class': 'First'}

print("dict['Name']: ", mydict['Name'])
print("dict['Age']: ", mydict['Age'])

# 修改字典
mydict['Age'] = 8  # update existing entry
mydict['School'] = "DPS School"  # Add new entry
print('mydic:', mydict)

# 删除字典元素
# 字典值可以没有限制地取任何python对象，既可以是标准的对象，也可以是用户定义的，但键不行
del mydict['Name']  # 删除键是'Name'的条目
mydict.clear()  # 清空词典所有条目

# 字典内置函数&方法
# http://www.runoob.com/python/python-dictionary.html
dict1 = {'Name': 'Zara', 'Age': 7}
dict2 = {'Name': 'Mahnaz', 'Age': 27}
print(len(dict1))  # 计算字典元素个数，即键的总数。
print(str(dict2))  # 输出字典可打印的字符串表示
print(type(dict1))  # 返回输入的变量类型，如果变量是字典就返回字典类型。
dict3 = dict1.copy()  # 返回一个字典的浅复制
dict1.clear()  # 删除所有元素
print(dict3.get('Name', 'defalut = None'))  # 返回指定键的值，如果值不在字典中返回default值
print(dict3.keys())
