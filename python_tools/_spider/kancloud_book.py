# -*- coding:utf-8 -*-

from spider import SpiderHTML
import os
import pdfkit

S = 'stylesheet'

__author__ = 'waiting'
'''
使用了第三方的类库 BeautifulSoup4，请自行安装
需要目录下的spider.py文件
运行环境：python3.4,windows7
'''

html_head = '''
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
'''

# 本地存放的路径,不存在会自动创建
store_path = r'D:\\GitCode\\kancloud\\Python_3零起点教程\\'

resource_url = 'file:///D:/GitCode/kancloud/resource/'

css_file = ('kancloud.min.css', 'solarized_dark.min.css')
css_head = "<link rel='stylesheet'  href='"
css_tail = "' type='text/css'/>"

light_code_css = '''
    <link rel="stylesheet" href="http://yandex.st/highlightjs/8.0/styles/tomorrow.min.css">
'''
light_code_scripts = '''
    <script src="http://yandex.st/highlightjs/8.0/highlight.min.js"></script>
    <script>hljs.initHighlightingOnLoad();</script>
'''

summary_url = 'http://www.kancloud.cn/thinkphp/python-guide/39196'
base_url = 'http://www.kancloud.cn/'

pdf_options = {  # https://pypi.python.org/pypi/pdfkit
    'margin-top': '0.6in',
    'margin-right': '0.7in',
    'margin-bottom': '0.6in',
    'margin-left': '0.7in',
    'encoding': "UTF-8",
    'no-outline': None
}


class KanyunSpider(SpiderHTML):
    def parse_index(self):
        content = self.get_url(summary_url)
        summary = content.find('div', class_='catalog-list read-book-preview')
        summary_list = summary.find_all('a')
        articles = []
        for link in summary_list:
            title = str(link.string)
            title = title.replace('/', '-').replace(' ', '')
            tmp = {'title': title, 'url': base_url + str(link['href'])}
            articles.append(tmp)

        return articles

    def parse_page(self, summary):
        bs_html = self.get_url(summary['url'])
        body = bs_html.find('body')
        manual_body = body.find('div', class_='manual-body')

        manual_body.find('div', class_='manual-left').decompose()
        manual_body.find('div', class_='article-head').decompose()
        manual_body.find('div', class_='view-foot').decompose()

        real_content = manual_body.find('div', class_='view-body think-editor-content')

        new_title = bs_html.new_tag("h1")
        new_title.string = summary['title']
        new_br = bs_html.new_tag("br")

        real_content.insert(0, new_title)
        real_content.insert(1, new_br)

        return str(manual_body)

    def generate_html(self):
        articles = self.parse_index()
        for art in articles:
            if self.save_html_pdf(art):
                continue
            else:
                break

    def save_html_pdf(self, art):
        file_name = (store_path + str(art['title']) + '.html')
        if os.path.exists(file_name):  # 已经抓取过
            return True
        title_html = '<title>' + art['title'] + '</title>\r\n</head>\r\n<body>'

        raw_html = html_head + generate_css_links() + title_html + light_code_scripts + self.parse_page(
                art) + '</body>\r\n</html>'
        self.save_text(file_name, raw_html)

        try:
            pdf_filename = store_path + 'pdf\\' + str(art['title']) + '.pdf'
            print(str(art['title']) + '.html :  已保存')
            self._check_path(pdf_filename)
            pdfkit.from_file(file_name, pdf_filename, options=pdf_options)
            return True
        except IOError:
            print(str(art['title']) + '.pdf :  已保存')
            return True


def generate_css_links():
    css_str = ""
    for css in css_file:
        css_str_per = css_head + resource_url + css + css_tail + '\r\n'
        css_str += css_str_per
    css_str += light_code_css
    # print(css_str)
    return css_str


if __name__ == '__main__':
    spider = KanyunSpider()
    print('-------------------------get Starting------------------------')
    # print(spider.parse_index())
    # print(generate_css_links())
    # testdata = {'url': 'http://www.kancloud.cn//thinkphp/python-guide/39580', 'title': '期末总结'}
    # print(spider.parse_page(testdata))
    # spider.save_html_pdf(testdata)
    spider.generate_html()
    print('-------------------------get Ending------------------------')
