# -*- coding:utf-8 -*-
from spider import SpiderHTML
import re
import os
import sys
import time
import random


class QiubaiSpider(SpiderHTML):
    def __init__(self, content_type, page_start=1, page_end=1):
        # super.__init__(self)
        self._contentType = content_type
        self._pageStart = int(page_start)
        self._pageEnd = int(page_end) + 1
        self.__url = {'new': 'http://www.qiushibaike.com/textnew/page/', 'hot': 'http://www.qiushibaike.com/text/page/'}

    def get_jokes(self):
        requrl = ''

        if contentType in self.__url:
            requrl = self.__url[self._contentType]
        else:
            requrl = self.__url['new']
        for i in range(self._pageStart, self._pageEnd):
            pageurl = requrl + str(i) + '/'
            jokes = self.get_url(pageurl)
            jokes = jokes.find_all('div', id=re.compile('qiushi_tag_\d+'))
            filepath = os.path.join('E:\\python_spider\\', 'qiubai', 'page_' + self._contentType + str(i))
            info = '正在保存第{page}页的糗事到文件 {file}.txt'
            print(info.format(page=i, file=filepath))
            for joke in jokes:
                joke_content = str(joke.find('div', attrs={'class': 'content'}))
                joke_content = re.sub('<div class="content">', '', joke_content)
                joke_content = re.sub('</div>', '', joke_content)
                joke_content = re.sub('<!--\d+-->', '', joke_content)
                joke_content = re.sub('<br>', '\n', joke_content)
                joke_content = re.sub('<br/>', '\n', joke_content)
                try:
                    author = joke.find(attrs={'class': 'author clearfix'}).find('h2').string
                    upvote = joke.find(attrs={'class': 'stats'}).span.i.string
                except AttributeError:
                    pass

                joke = '-----------------------------\r\n作者：{author}\r\n{joke}\r\n\r\n{upvote}人觉得很赞\r\n'.format(
                    joke=joke_content.strip(), author=author, upvote=upvote)

                self.save_text(filepath + '.txt', joke, 'a')
            if i % 2 == 0:  # 防止被封，间隔时间长一点
                time.sleep(random.random() * 3)


if __name__ == '__main__':
    contentType = 'new'
    page = 0
    paramsNum = len(sys.argv)

    # 输入想获取最新的糗百还是最热的糗百
    # 参数2,3为想要获取的页数
    if paramsNum >= 4:
        contentType = sys.argv[1]
        page = sys.argv[2]
        pageEnd = sys.argv[3]
    elif paramsNum >= 3:
        contentType = sys.argv[1]
        page = sys.argv[2]
        pageEnd = page
    elif paramsNum == 2:
        contentType = sys.argv[1]
        page, pageEnd = 1, 1
    else:
        contentType = 'new'
        page, pageEnd = 1, 1

    qiubai = QiubaiSpider(contentType, page, pageEnd)
    qiubai.get_jokes()
