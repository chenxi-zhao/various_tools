# coding=utf-8
__author__ = 'chenxi'


# 邻接矩阵
class Graph:

    def __init__(self, maps=[], vertexs=[], nodenum=0, edgenum=0):
        self.map = maps  # 图的矩阵结构
        self.vertex = vertexs
        self.nodenum = len(maps)
        self.edgenum = edgenum
        # self.nodenum = GetNodenum()#节点数
        # self.edgenum = GetEdgenum()#边数

    def isOutRange(self, x):
        try:
            if x >= self.nodenum or x <= 0:
                raise IndexError
        except IndexError:
            print("节点下标出界")

    def GetNodenum(self):
        self.nodenum = len(self.map)
        return self.nodenum

    def GetEdgenum(self):
        self.GetNodenum()
        self.edgenum = 0
        for i in range(self.nodenum):
            for j in range(self.nodenum):
                if self.map[i][j] is 1:
                    self.edgenum = self.edgenum + 1
        return self.edgenum

    def InsertNode(self, item):
        for i in range(self.nodenum):
            self.map[i].append(0)
        self.vertex.append(item)
        self.nodenum = self.nodenum + 1
        ls = [0] * self.nodenum
        self.map.append(ls)

    # 假删除，只是归零而已
    def DeleteNode(self, x):
        for i in range(self.nodenum):
            if self.map[i][x] is 1:
                self.map[i][x] = 0
                self.edgenum = self.edgenum - 1
            if self.map[x][i] is 1:
                self.map[x][i] = 0
                self.edgenum = self.edgenum - 1

    def AddEdge(self, x, y):
        if self.map[x][y] is 0:
            self.map[x][y] = 1
            self.edgenum = self.edgenum + 1

    def RemoveEdge(self, x, y):
        if self.map[x][y] is 1:
            self.map[x][y] = 0
            self.edgenum = self.edgenum - 1

    def BreadthFirstSearch(self):
        def BFS(self, i):
            print(self.vertex[i])
            visited[i] = 1
            for k in range(self.nodenum):
                if self.map[i][k] == 1 and visited[k] == 0:
                    BFS(self, k)

        visited = [0] * self.nodenum
        for i in range(self.nodenum):
            if visited[i] is 0:
                BFS(self, i)

    def DepthFirstSearch(self):
        def DFS(self, i, queue):
            queue.append(i)
            print(self.vertex[i])
            visited[i] = 1
            if len(queue) != 0:
                w = queue.pop()
                for k in range(self.nodenum):
                    if self.map[w][k] is 1 and visited[k] is 0:
                        DFS(self, k, queue)

        visited = [0] * self.nodenum
        queue = []
        for i in range(self.nodenum):
            if visited[i] is 0:
                DFS(self, i, queue)


def DoTest():
    maps = [
        [-1, 1, 0, 0],
        [0, -1, 0, 0],
        [0, 0, -1, 1],
        [1, 0, 0, -1]]
    vertexs = [3, 9, 5, 6]
    G = Graph(maps, vertexs)
    G.InsertNode(7)
    # print(G.map)
    G.AddEdge(1, 4)
    # print(G.map)
    print("广度优先遍历")
    G.BreadthFirstSearch()
    print("深度优先遍历")
    G.DepthFirstSearch()

if __name__ == '__main__':
    DoTest()


# def graphDFS(G, start, goal):
#     # G = (V,E) is the graph with vertices, V, and edges, E.
#     V, E = G
#     stack = Stack()
#     visited = Set()
#     stack.push(start)
#     while not stack.isEmpty():
#         # A vertex is popped from the stack. This is called the current vertex.
#         current = stack.pop
#     # The current vertex is added to the visited set.
#     visited.add(current)
#     # If the current vertex is the goal vertex, then we discontinue the
#     # search reporting that we found the goal.
#     if current == goal:
#         return True  # or return path to goal perhaps
#     # Otherwise, for every adjacent vertex, v, to the current vertex
#     # in the graph, v is pushed on the stack of vertices yet to search
#     # unless v is already in the visited set in which case the edge
#     # leading to v is ignored.
#     for v in adjacent(current, E):
#         if v not in visited:
#             stack.push(v)
#     # If we get this far, then we did not find the goal.
#     return False  # or return an empty p
