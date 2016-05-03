from graph_adjacency_list import Graph
from queue import Queue
# coding=utf-8
__author__ = 'chenxi'


# 创建dictionary桶式保存单词数据，构造图
def buildGraph(wordFile):
    dic = {}
    g = Graph()

    def read_file_line(wordFile):
        with open(wordFile, 'r') as wfile:
            while True:
                blockline = wfile.readline()  # 调用readlines()一次读取所有内容并按行返回list
                if blockline:
                    yield blockline.strip()
                else:
                    return
    # create buckets of words that differ by one letter
    for line in read_file_line(wordFile):
        word = line[:-1]
        for i in range(len(word)):
            bucket = word[:i] + '_' + word[i + 1:]
            if bucket in dic:
                dic[bucket].append(word)
            else:
                dic[bucket] = [word]
    # add vertices and edges for words in the same bucket
    for bucket in dic.keys():
        for word1 in dic[bucket]:
            for word2 in dic[bucket]:
                if word1 != word2:
                    g.addEdge(word1, word2)
    return g


# 广度优先搜索，染色成一棵树，然后查找路径（并不一定是最短路径）
def bfs(g, start):
    start.setDistance(0)
    start.setPred(None)
    vertQueue = Queue()
    vertQueue.enqueue(start)
    while (vertQueue.size() > 0):
        currentVert = vertQueue.dequeue()
        for nbr in currentVert.getConnections():
            if (nbr.getColor() == 'white'):
                nbr.setColor('gray')
                nbr.setDistance(currentVert.getDistance() + 1)
                nbr.setPred(currentVert)
                vertQueue.enqueue(nbr)
        currentVert.setColor('black')

    def traverse(y):
        x = y
        while (x.getPred()):
            print(x.getId())
            x = x.getPred()
        print(x.getId())

    traverse(g.getVertex('sage'))
