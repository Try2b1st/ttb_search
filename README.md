# 项目介绍

## 项目名称：TTB聚合搜索中台

基于 Vue 3 + Spring Boot + Elastic Stack 的一站式聚合搜索平台，也是简化版的企业级搜索中台。
对用户来说，使用该平台，可以在同一个页面集中搜索出不同来源、不同类型的内容，提升用户的检索效率和搜索体验。
对企业来说，当企业内部有多个项目的数据都存在搜索需求时，无需针对每个项目单独开发搜索功能，可以直接将各项目的数据源接入搜索中台，从而提升开发效率、降低系统维护成本。

## 主要工作：

### 前端：

##### 1.基于 Vue 3 + Ant Design Vue 实现响应式页面开发，使用 Tab 组件 + Vue Router 动态路由实现统一页面布局，通过用户点击 Tab 时更改路由来切换各类数据（文章、图片、用户）的搜索结果，并选用不同的组件进行展示。
##### 2.为解决刷新页面后搜索结果丢失的问题，定义 searchParams 响应式变量来集中保存当前的搜索条件（比如关键词、搜索类别、分页），并通过 Vue Router 的 query 参数将搜索条件同步到 url 的 querystring 中。

### 后端：

##### 1.基于自己二次开发的 Spring Boot 初始化模板 + MyBatis X 插件，快速生成基本数据源的增删改查（比如用户、文章）。
##### 2.数据源获取： ○使用 HttpClient 请求 离线 获取外部网站的文章，并使用 Hutool 的 JSONUtil 解析和预处理文章，最终入库。○使用 jsoup 实时 请求 bing 搜索接口获取图片，并使用 CSS Selector 语法解析和预处理图片信息，最终返回给前端。
##### 3 为实现多类数据源的整体搜索，使用 门面模式 在后端对各类数据源的搜索结果进行聚合，统一返回给前端，减少了前端请求次数（N 次到 1 次）以及前端开发复杂度。并通过 CompletableFuture 并发搜索各数据源进一步提升搜索接口性能。
##### 4.为提高聚合搜索接口的通用性，首先通过定义数据源接口来实现统一的数据源接入标准（比如新数据源必须支持分页）；当新数据源（比如视频）要接入时，只需使用适配器模式对其数据查询接口进行封装、以适配数据源接口，无须修改原有代码，提高了系统的可扩展性。
##### 5.为减少代码的圈复杂度，使用注册器模式代替 if else 来管理多个数据源对象，调用方可根据名称轻松获取对象。
##### 6.为解决文章搜不出的问题，自主搭建 Elasticsearch 来代替 MySQL 的模糊查询，并通过为索引绑定 ik 分词器实现了更灵活的分词搜索。 
##### 7.构建 ES 文章索引时，采用动静分离的策略，只在 ES 中存储要检索的、修改不频繁字段（比如文章）用于检索，而修改频繁的字段（比如点赞数）从数据库中关联查出，从而减少了 ES 数据更新和同步的成本、保证数据一致性。 
##### 8.为了更方便地管理 Elasticsearch 中的数据，自主搭建 Kibana 并配置 index pattern 和看板，实现对文章数据的可视化管理。 
##### 9.开发搜索功能时，使用 Kibana DevTools + DSL 调试 ES 的搜索效果，并使用 Spring Data Elasticsearch 的 QueryBuilder 组合查询条件，实现对 ES 内文章的灵活查询（比如查询同时查询标题和文章中带有指定关键字的内容）。 
##### 10.自主搭建 Logstash 实现每分钟同步 MySQL 的文章数据到 ES，并通过指定 tracking_column 为更新时间字段解决重复更新的问题。
##### 11.使用 Knife4j + Swagger 自动生成后端接口文档，并通过编写 ApiOperation 等注解补充接口注释，避免了人工编写维护文档的麻烦。

## 技术亮点：
### 在原有功能上实现了搜索建议

参考官方文档：[https://www.elastic.co/guide/en/elasticsearch/reference/7.17/search-suggesters.htm](https://www.elastic.co/guide/en/elasticsearch/reference/7.17/search-suggesters.html)
在使用ES实现搜索建议：https://blog.csdn.net/qq_41489540/article/details/121865225

### 在原有功能上实现搜索高亮

参考官方文档：https://www.elastic.co/guide/en/elasticsearch/reference/7.17/highlighting.html

## 待解决：前端防抖节流

问题：用户频繁输入，点击搜索按钮向后端发送请求怎么办？

解决：使用lodash工具库实现防抖和节流

节流：每段时间最多能执行x次(比如服务器限流)

参考文档：https://www.lodashjs.com/docs/lodash.throttle

防抖：等待一段时间内没有其他操作后，才执行操作(比如输入搜索)

参考文档：https://www.lodashjs.com/docs/lodash.debounce

## 使用了Java后端模板进行二次开发

作者：程序员鱼皮
GitHub：https://github.com/liyupi

### 主流框架 & 特性

- Spring Boot 2.7.x（贼新）
- Spring MVC
- MyBatis + MyBatis Plus 数据访问（开启分页）
- Spring Boot 调试工具和项目处理器
- Spring AOP 切面编程
- Spring Scheduler 定时任务
- Spring 事务注解

### 数据存储

- MySQL 数据库
- Elasticsearch 搜索引擎

### 工具类

- Hutool 工具库
- Gson 解析库
- Apache Commons Lang3 工具类
- Lombok 注解

### 业务特性

- 全局请求响应拦截器（记录日志）
- 全局异常处理器
- 自定义错误码
- 封装通用响应类
- Swagger + Knife4j 接口文档
- 自定义权限注解 + 全局校验
- 全局跨域处理
- 长整数丢失精度解决
- 多环境配置


## 业务功能

- 提供示例 SQL（用户、帖子、帖子点赞、帖子收藏表）
- 用户登录、注册、注销、更新、检索、权限管理
- 帖子创建、删除、编辑、更新、数据库检索、ES 灵活检索
- 帖子点赞、取消点赞
- 帖子收藏、取消收藏、检索已收藏帖子
- 帖子全量同步 ES、增量同步 ES 定时任务
- 支持微信开放平台登录
- 支持微信公众号订阅、收发消息、设置菜单
- 支持分业务的文件上传

### 单元测试

- JUnit5 单元测试
- 示例单元测试类

### 架构设计

- 合理分层

### MySQL 数据库

1）修改 `application.yml` 的数据库配置为你自己的：

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456
```

2）执行 `sql/create_table.sql` 中的数据库语句，自动创建库表

3）启动项目，访问 `http://localhost:8101/api/doc.html` 即可打开接口文档，不需要写前端就能在线调试接口了~


### Elasticsearch 搜索引擎

1）修改 `application.yml` 的 Elasticsearch 配置为你自己的：

```yml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: root
    password: 123456
```

2）复制 `sql/post_es_mapping.json` 文件中的内容，通过调用 Elasticsearch 的接口或者 Kibana Dev Tools 来创建索引（相当于数据库建表）

```
PUT post_v1
{
 参数见 sql/post_es_mapping.json 文件
}
```

这步不会操作的话需要补充下 Elasticsearch 的知识，或者自行百度一下~

3）开启同步任务，将数据库的帖子同步到 Elasticsearch

找到 job 目录下的 `FullSyncPostToEs` 和 `IncSyncPostToEs` 文件，取消掉 `@Component` 注解的注释，再次执行程序即可触发同步：

