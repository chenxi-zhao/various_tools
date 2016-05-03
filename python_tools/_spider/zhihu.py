# -*- coding:utf-8 -*-

from spider import SpiderHTML
import sys, urllib, http, os, random, re, time

__author__ = 'waiting'
'''
使用了第三方的类库 BeautifulSoup4，请自行安装
需要目录下的spider.py文件
运行环境：python3.4,windows7
'''

# 收藏夹的地址
zhihu_url = 'https://www.zhihu.com/collection/69135664'  # page参数改为代码添加

# 本地存放的路径,不存在会自动创建
store_path = 'E:\\python_spider\\zhihu\收藏夹\\攻不可破的大美妞阵线联盟'


class ZhihuCollectionSpider(SpiderHTML):
    def __init__(self, page_start, page_end, url):
        self._url = url
        self._pageStart = int(page_start)
        self._pageEnd = int(page_end) + 1
        self.downLimit = 0  # 低于此赞同的答案不收录

    def start(self):
        for per_page in range(self._pageStart, self._pageEnd):  # 收藏夹的页数
            print('------------------------------------')
            print('---------PAGE' + str(per_page) + '----------')
            print('------------------------------------')
            per_url = self._url + '?page=' + str(per_page)
            content = self.get_url(per_url)
            question_list = content.find_all('div', class_='zm-item')
            for question in question_list:  # 收藏夹的每个问题
                qt_title = question.find('h2', class_='zm-item-title')
                if qt_title is None:  # 被和谐了
                    continue

                # question_str = qt_title.a.string
                qt_url = 'https://www.zhihu.com' + qt_title.a['href']  # 问题题目
                qt_title = re.sub(r'[\\/:*?"<>]', '#', qt_title.a.string)  # windows文件/目录名不支持的特殊符号
                print('-----正在获取问题:' + qt_title + '-----')  # 获取到问题的链接和标题，进入抓取
                qt_content = self.get_url(qt_url)
                answer_list = qt_content.find_all('div', class_='zm-item-answer  zm-item-expanded')
                self._process_answer(answer_list, qt_title)  # 处理问题的答案
                time.sleep(5)

    def _process_answer(self, answer_list, qt_title):
        j = 0
        for answer in answer_list:
            j += 1

            upvoted = int(answer.find('span', class_='count').string.replace('K', '000'))  # 获得此答案赞同数
            if upvoted < 40:
                continue
            author_info = answer.find('div', class_='zm-item-answer-author-info')  # 获取作者信息
            author = {'introduction': '', 'link': ''}
            try:
                author['name'] = author_info.find('a', class_='author-link').string  # 获得作者的名字
                author['introduction'] = str(author_info.find('span', class_='bio')['title'])  # 获得作者的简介
            except AttributeError:
                author['name'] = '匿名用户' + str(j)
            except TypeError:  # 简介为空的情况
                pass

            try:
                author['link'] = author_info.find('a', class_='author-link')['href']
            except TypeError:  # 匿名用户没有链接
                pass

            file_name = os.path.join(store_path, qt_title, 'info', author['name'] + '_info.txt')
            if os.path.exists(file_name):  # 已经抓取过
                continue

            self.save_text(file_name, '{introduction}\r\n{link}'.format(**author))  # 保存作者的信息
            print('正在获取用户`{name}`的答案'.format(**author))
            answer_content = answer.find('div', class_='zm-editable-content clearfix')
            if answer_content is None:  # 被举报的用户没有答案内容
                continue

            imgs = answer_content.find_all('img')
            if len(imgs) == 0:  # 答案没有上图
                pass
            else:
                self._getimgfromanswer(imgs, qt_title, **author)

    # 收录图片
    def _getimgfromanswer(self, imgs, qt_title, **author):
        i = 0
        for img in imgs:
            if 'inline-image' in img['class']:  # 不抓取知乎的小图
                continue
            i += 1
            imgurl = img['src']
            extension = os.path.splitext(imgurl)[1]
            path_name = os.path.join(store_path, qt_title, author['name'] + '_' + str(i) + extension)
            try:
                self.save_img(imgurl, path_name)  # 捕获各种图片异常，流程不中断
            except ValueError:
                pass
            except urllib.error.HTTPError:
                pass
            except KeyError:
                pass
            except http.client.IncompleteRead:
                pass

    # 收录文字
    def _gettextfromanswer(self):
        pass


# 例：zhihu.py 1 5   获取1到5页的数据
if __name__ == '__main__':
    page, limit, paramsNum = 1, 0, len(sys.argv)
    if paramsNum >= 3:
        page, pageEnd = sys.argv[1], sys.argv[2]
    elif paramsNum == 2:
        page = sys.argv[1]
        pageEnd = page
    else:
        page, pageEnd = 6, 7

    spider = ZhihuCollectionSpider(page, pageEnd, zhihu_url)
    print('-------------------------get Starting------------------------')
    spider.start()
    print('-------------------------get Ending------------------------')

