# -*- coding: utf-8 -*-
import os
# import re
import codecs
import urllib
from urllib import request
from bs4 import BeautifulSoup


class SpiderHTML(object):
    # 打开页面
    @staticmethod
    def get_url(url, coding='utf-8'):
        try:
            req = request.Request(url)
            req.add_header('User-Agent',
                           'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko)')
            with request.urlopen(req) as response:
                return BeautifulSoup(response.read().decode(coding), 'html.parser')
        except urllib.error.HTTPError:
            return BeautifulSoup()

    # 保存文本内容到本地
    def save_text(self, filename, content, mode='w'):
        self._check_path(filename)
        with codecs.open(filename, encoding='utf-8', mode=mode) as f:
            f.write(content)

    # 保存图片
    def save_img(self, imgurl, imgname):
        data = request.urlopen(imgurl).read()
        self._check_path(imgname)
        with open(imgname, 'wb') as f:
            f.write(data)

    # 创建目录
    @staticmethod
    def _check_path(path):
        dirname = os.path.dirname(path.strip())
        if not os.path.exists(dirname):
            os.makedirs(dirname)
