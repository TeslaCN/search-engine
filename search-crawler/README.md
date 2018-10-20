# 爬虫
### For Search

目前是个简单实现，**需要重构**：
1. 从 RocketMQ 取出 Mission  
2. ~~判断是否存在 MongoDB 中 **（每个Mission需要访问数据库，性能较差）**~~  
   判断uri的MD5是否存在Redis Set中
3. HTTPClient 爬取页面
4. MongoDB 存储 HTML，ElasticSearch 索引去除 HTML 标签的纯文本，URI的MD5存入Redis Set
5. 第3步中的HTML解析出所有 a 标签的 href 属性，创建 Mission 入列


记踩过的各种坑：
1. 页面编码可能在Header的Content-Type中没有，要去meta标签看看
2. URI的Path问题，resolve的时候，如果访问根且Path没有/，会直接把后面的path拼接起来，即
http://wcce.scau.edu.cn会变成http://wcce.scau.edu.cncncontent.asp

