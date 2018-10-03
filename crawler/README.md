# 爬虫
### For Search

目前是个简单实现，**需要重构**：
1. 从 MQ 取出 Mission，判断是否存在 MongoDB 中 **（每个Mission需要访问数据库，性能较差）**
2. HTTPClient 爬取页面
3. MongoDB 存储 HTML，ElasticSearch 索引去除 HTML 标签的纯文本
4. 第3步中的HTML解析出所有 a 标签的 href 属性，创建 Mission 入列



