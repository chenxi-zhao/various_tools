from graph_adjacency_list import Graph, Vertex
# coding=utf-8
__author__ = 'chenxi'


# 创建骑士的棋盘
def knightGraph(bdSize):
    ktGraph = Graph()
    for row in range(bdSize):
        for col in range(bdSize):
            nodeId = posToNodeId(row, col, bdSize)
            newPositions = genLegalMoves(row, col, bdSize)
            for e in newPositions:
                nid = posToNodeId(e[0], e[1], bdSize)
                ktGraph.addEdge(nodeId, nid)
    return ktGraph


# 计算棋盘坐标id，表示在棋盘的坐标
def posToNodeId(row, column, board_size):
    return (row * board_size) + column


# 生成下一步能走的坐标值
def genLegalMoves(x, y, bdSize):
    newMoves = []
    moveOffsets = [(-1, -2), (-1, 2), (-2, -1), (-2, 1),
                   (1, -2), (1, 2), (2, -1), (2, 1)]
    for i in moveOffsets:
        newX = x + i[0]
        newY = y + i[1]
        if legalCoord(newX, bdSize) and legalCoord(newY, bdSize):
            newMoves.append((newX, newY))
    return newMoves


# 判断坐标横纵值是否合法
def legalCoord(x, bdSize):
    if x >= 0 and x < bdSize:
        return True
    else:
        return False


def knightTour(n, path, u, limit):  # dfs深度优先搜索
    u.setColor('gray')
    path.append(u)
    if n < limit:
        nbrList = list(u.getConnections())
        i = 0
        done = False
        while i < len(nbrList) and not done:
            if nbrList[i].getColor() == 'white':
                done = knightTour(n + 1, path, nbrList[i], limit)
            i = i + 1
        if not done:  # prepare to backtrack
            path.pop()
            u.setColor('white')
    else:
        done = True
    return done


# dfs深度优先搜索 + 贪心算法，优先选择下一步出口最少的作为下一个递归对象
def knightTour2(n, path, u, limit):
    u.setColor('gray')
    path.append(u)
    if n < limit:
        nbrList = orderByAvail(u)
        i = 0
        done = False
        while i < len(nbrList) and not done:
            if nbrList[i].getColor() == 'white':
                done = knightTour2(n + 1, path, nbrList[i], limit)
            i = i + 1
        if not done:  # prepare to backtrack
            path.pop()
            u.setColor('white')
    else:
        done = True
    return done


def orderByAvail(vertex):
    resList = []
    for ver in vertex.getConnections():
        if ver.getColor() == 'white':
            c = 0
            for w in ver.getConnections():
                if w.getColor() == 'white':
                    c = c + 1
            resList.append((c, ver))
    resList.sort(key=lambda x: x[0])
    return [y[1] for y in resList]


ktGraph = knightGraph(8)
path = []
knightTour2(1, path, ktGraph.getVertex(18), 64)
for ver in path:
    print(ver)



