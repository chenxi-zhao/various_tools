from enum import Enum, unique
from types import MethodType
# coding=utf8
__author__ = 'chenxi'


# 创建一个 员工类
class Employee:

    """所有员工的基类"""  # 类文档 object.__doc__
    empCount = 0

    def nopass(self):
        pass

    def __init__(self, name, salary):
        self.name = name
        self.salary = salary
        Employee.empCount += 1

    def displayCount(self):
        print("Total Employee %d" % Employee.empCount)

    def displayEmployee(self):
        print("Name : ", self.name, ", Salary: ", self.salary)


print("---------------------------Python属性访问")
# "创建 Employee 类的第一个对象"
emp1 = Employee("Zara", 2000)

# "创建 Employee 类的第二个对象"
emp2 = Employee("Manni", 5000)
emp1.displayEmployee()
emp2.displayEmployee()
print("Total Employee %d" % Employee.empCount)

# 通过函数获取设置添加新属性
emp1.age = 1
hasattr(emp1, 'age')    # 如果存在 'age' 属性返回 True。
getattr(emp1, 'age')    # 返回 'age' 属性的值
setattr(emp1, 'age', 8)    # 添加属性 'age' 值为 8
delattr(emp1, 'age')    # 删除属性 'age'

print("---------------------------Python内置属性")
print("Employee.__doc__:", Employee.__doc__)
print("Employee.__name__:", Employee.__name__)
print("Employee.__module__:", Employee.__module__)
print("Employee.__bases__:", Employee.__bases__)
print("Employee.__dict__:", Employee.__dict__)


# Animal类
class Animal(object):

    """Animal is all"""

    def __init__(self, name):  # 该对象及其子对象声明时调用
        super(Animal, self).__init__()
        self.name = name
        print("Animal is parent")

    def __del__(self):  # 对象销毁时调用方法
        class_name = self.__class__.__name__
        print(class_name, "destroyed")

    def eat(self):
        print(self.name, " eat")

    def run(self):
        print("Animal is running")


# Dog 类 继承Animal
class Dog(Animal):

    """dog is animal"""

    def __init__(self, name):  # 先调用子类初始化，然后调用父类初始化
        print("dog is child")
        super(Dog, self).__init__(name)

    def run(self):
        print("Dog is running")

    def eat(self):
        print("eat for dog")


class Timer(object):
    def run(self):
        print('Timer is running...')


def runtwice(object):
    object.run()


print("--------------------------Python继承")
animal1 = Animal("aa")
print(animal1.name)

dog = Dog("mike")
dog.eat()
dog.run()
print(dog.name)
del animal1
del dog
print("--------------------------Python多态")
runtwice(Animal("ceshi"))
runtwice(Timer())
runtwice(Dog("ceshi"))
print("--------------------------Python继承  end")


# 运算符重载
class Vector:
    __secretCount = 0  # 私有变量前面加两个下划线

    def __init__(self, a, b):
        self.__secretCount += 1
        self.a = a
        self.b = b

    def __str__(self):  # 类似于java中的toString方法重写
        return 'Vector (%d, %d)' % (self.a, self.b)

    def __add__(self, other):  # 加运算符重载
        return Vector(self.a + other.a, self.b + other.b)

print("-----------------------------Python运算符重载")
v1 = Vector(2, 10)
v2 = Vector(5, -2)
print(v1 + v2)


# 使用slots
class Student(object):
    """docstring for Student"""
    __slots__ = ('name', 'age')  # 用tuple定义允许绑定的属性名称

    def __init__(self):
        super(Student, self).__init__()
s = Student()
s.name = 'Michael'
s.age = 20
# s.score = 99 报错信息AttributeError，slots中没有绑定，但是对继承子类没有没有作用
# 子类实例允许定义的属性就是自身的__slots__加上父类的__slots__。

# def set_age(self, age):  # 定义一个函数作为实例方法
#     self.age = age

# s.set_age = MethodType(set_age, s)  # 给实例绑定一个方法
# s.set_age(25)  # 调用实例方法
# s.age  # 测试结果


class GraduateStudent(Student):
    """docstring for GraduateStudent"""
    def __init__(self):
        super(GraduateStudent, self).__init__()
g = GraduateStudent()
g.score = 99


# 使用@property (getter)，@property装饰器就是负责把一个方法变成属性调用
class Student2(object):
    """Student2"""

    def __init__(self):
        super(Student2, self).__init__()

    @property  # stu.score
    def score(self):
        return self.__score
    # 当新建一个实例对象调用xx.score时返回self.__score，如果不加__，
    # 调用self.score的时候返回self.score 无限循环

    @score.setter  # stu.score == 9999
    # 装饰器@score.setter，负责把一个setter方法变成属性赋值
    def score(self, value):
        if not isinstance(value, int):
            raise ValueError("score must be a integer")
        if value < 0 or value > 100:
            raise ValueError("score must between 0-100")
        self.__score = value

    @property
    def birth(self):
        return self.__birth

    @birth.setter
    def birth(self, value):
        self.__birth = value

    @property  # 只定义getter方法，不定义setter方法就是一个只读属性
    def age(self):
        return 2015 - self.__birth

# __len__()、__str__()、__repr__()、__next__(self)、__getitem__()、__call__()
# __getattr__(self, attr)


# 枚举
Month = Enum('Month', ('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'))
Weekday2 = Enum('Weekday2', ('Sun', 'Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat'))

for name, member in Month.__members__.items():
    print(name, '=>', member, ',', member.value)

for name, member in Weekday2.__members__.items():
    print(name, '=>', member, ',', member.value)


@unique  # 元素唯一,检查保证没有重复值
class Weekday(Enum):
    """docstring for Weekday"""
    Sun = 0  # Sun的value被设定为0
    Mon = 1
    Tue = 2
    Wed = 3
    Thu = 4
    Fri = 5
    Sat = 6
for name, member in Weekday.__members__.items():
    print(name, '=>', member, ',', member.value)


# type()函数既可以返回一个对象的类型，又可以创建出新的类型
def fnfn(self, name='world'):
    print('Hello, %s.' % name)
# 创建对象三个参数，Class名称、继承对象的tuple集合、class的方法名称与函数绑定
Hello = type('Hello', (object,), dict(hello=fnfn))  # 创建Hello class
h = Hello()
h.hello()  # 输出Hello world.
# 要获得一个对象的所有属性和方法，可以使用dir()函数，它返回一个包含字符串的list

# dir('ABC')



























