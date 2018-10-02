# MyBlog

#### 项目链接：[www.zhyocean.cn](https://www.zhyocean.cn)

#### 关于本地开发
可直接导入该项目于本地，修改配置文件中的数据库连接信息，导入附带数据库结构的SQL文件可直接生成所有表，项目中使用到的关于阿里云功能还需开发者自行前往阿里云进行相应功能开通。

**当你克隆项目到本地后可使用手机号：19940790216，密码：123456进行登录，也可自行注册并将其修改为最高管理权限。**

#### 项目介绍  
- 关于项目，对于学习Springboot是个挺不错的练手项目，可以让你在烦恼的业务逻辑中保持一颗纯洁的心
- 如何从零开始，使用Springboot开发项目
- 开发前的一些准备工作，以及思考项目整体结构与思路
- 记录开发过程中遇到的一些难题以及bug
- 总结目前博客网站的一些优缺点
- 思考整个项目有哪些可以优化的地方，以及有哪些可增加的功能

## 页面展示

##### 首页展示
![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802120610.png)
<br>
##### 文章编辑
![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802120611.png)
<br>
##### 后台管理
![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802121241.png)
<br>
##### 用户个人中心
![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802121749.png)

## 项目需求
#### 项目背景
对于初学Springboot的朋友来说，最好的一个学习方式就是那一个功能俱全的项目来练练手，通过自己重构项目来发现其中的潜在难题，并且也能很好的在编码过程中总结和发现问题、解决问题。使用Springboot开发的博客系统，简单并且实用，适合做练手项目。

#### 功能需求
###### 主页
- 博客汇总，以列表形式展示文章，并附上文章作者、发布日期、分类情况以及文章简要

- 能够以分类形式查看文章

- 能够以时间列表方式归档文章

- 可实现通过标签查找所有相关文章

- 个人介绍、联系方式

- 博客网站更新记录

- 友链链接

###### 后台管理
- 网站仪表盘，记录网站访客量情况

- 文章管理
1.分页展示文章信息
2.可对文章进行再编辑以及删除文章

- 发布文章
1.使用markdown编辑器，支持插入代码，插入图片等功能
2.文章可选择分类和标签，以及转载文章支持链接原作者文章

- 分类管理，支持增加、删除、修改分类

- 友情链接
1.支持增加友情链接
2.支持删除友情链接

- 反馈信息管理，可查看用户反馈信息

#### 安装部署需求
- 可以使用docker方式部署，也可支持-jar方式
- 使用springboot自带方式打包

#### 非功能需求
##### 性能需求
- 首页响应时间不超过2秒钟
- 文章页响应时间不超过3秒钟

## 项目设计

#### 总体设计
- **本项目用到的技术和框架**<br>
1.项目构建：Maven<br>
2.web框架：Springboot<br>
3.数据库ORM：Mybatis<br>
4.数据库连接池： Druid<br>
5.分页插件：PageHelper<br>
6.数据库：MySql<br>
7.缓存：Redis<br>
8.前端模板：Thymeleaf<br>
9.文章展示：Editor.md<br>

- **本项目中的关键点**<br>
1.采用Springboot开发，数据库使用连接池加orm框架的模式，对于系统的关键业务使用Redis缓存，加快相应速度。<br>
2.整体系统采用门户网站+后台管理+用户个人中心的方式搭建，门户网站展示博客内容以及博主介绍，后台管理用于编辑文章，查看反馈，管理评论留言。<br>
3.使用阿里云OSS进行静态资源存储，以及CDN全站加速。<br>

- **环境**

|  工具 | 名称 
| ------------ | ------------
| 开发工具  | IDEA 
|  语言 | JDK1.8、HTML、css、js 
| 数据库  | Mysql5.6 
| 项目框架  | SSM 
| ORM  | Mybatis 
| 安全框架  | SpringSecurity 
| 缓存  | Redis 
| 项目构建  | Maven 
| 运行环境  | 阿里云Centos7 

#### 结构设计

![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802140136.png)
对于熟悉Spring开发的朋友来说，相信对此结构也不会陌生。平时的开发过程中，结构设计是重要的环节，特别是协作开发的时候，明细的分包，模块化，可减少代码提交时的冲突。并且明确的结构有助于我们快速的寻找所对应的类。

## 业务设计
#### 发布文章流程

![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802141221.png)

#### 登录流程

![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/201808021412271.png)

#### 用户个人资料修改流程

![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802143230.png)

## 打包、部署和运行
- 本项目采用Springboot的maven插件进行打包，打包结果：****.jar
- 部署方式：使用 nohup java -jar ******.jar >******.log 2>&1 &的方式，后台启动项目，并在该路径下生成运行日志

## 数据设计

###### 用户表：user
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 
| ------------ | ------------ | ------------ | ------------ | ------------ | ------------
| id  | int  |  11 |  true |  true | 主键，自增 
| phone  | varchar  | 255  | false  | true  | 手机号 
| username  | varchar  | 255  |  false | true  |  用户名
| password  |  varchar |  255 |  false | true  | 密码 
| gender  | char  | 50  | false  |  true | 性别 
| trueName  | varchar  | 255  |  false | false  | 姓名 
| birthday  |  char | 100  |  false | false  | 生日 
| email  | varchar  | 255  | false  | false  | 邮箱 
| personalBrief  |  varchar | 255  | false  | false  |  个人简介
| avatarImgUrl  |  varchar |  255 | false  |  true | 头像url
| recentlyLanded  | varchar  |  255 |  false | false  |  最近登录时间

###### 文章表：article
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 
| ------------ | ------------ | ------------ | ------------ | ------------ | ------------
| id  | int  |  11 |  true |  true | 主键，自增 
| articleId  | bigint  | 20  | false  | true  | 文章id 
| author  | varchar  | 255  |  false | true  |  作者
| originalAuthor  |  varchar |  255 |  false | true  | 文章原作者 
| articleTitle  | varchar  | 255  | false  |  true | 文章标题 
| articleContent  | longtext  | 0  |  false | true  | 文章内容 
| articleTags  |  varchar | 255  |  false | true  | 文章标签 
| articleType  | varchar  | 255  | false  | true  | 文章类型 
| articleCategories  |  varchar | 255  | false  | true  |  文章分类
| publishDate  |  varchar |  255 | false  |  true | 发布文章日期
| updateDate  | varchar  |  255 |  false | true  |  更新文章日期
| articleUrl  | varchar  |  255 |  false | true  |  文章url
| articleTabloid  | 0  |  255 |  false | true  |  文章摘要
| likes  | int  |  11 |  false | true  |  文章喜欢数
| lastArticleId  | bigint  |  20 |  false | false  |  上一篇文章id
| nextArticleId  | bigint  |  20 |  false | false  |  下一篇文章id

###### 评论记录表：comment_record
| 名称  | 类型  |  长度 |  主键 | 非空  | 描述 
| ------------ | ------------ | ------------ | ------------ | ------------ | ------------
| id  | bigint  |  20 |  true |  true | 主键，自增 
| pId  | bigint  | 20  | false  | true  | 父id 
| articleId  | bigint  | 20  |  false | true  |  文章id
| originalAuthor  |  varchar |  255 |  false | true  | 文章原作者 
| answererId  | int  | 11  | false  |  true | 评论者id 
| respondentId  | int  | 11  |  false | true  | 被评论者id 
| commentDate  |  varchar | 100  |  false | true  | 评论日期 
| likes  | int  | 11  | false  | true  | 评论点赞数 
| commentContent  |  text | 0  | false  | true  |  评论内容

## 开发流程
###### 数据库CRUD
- controller层中编写前端接口，接收前端参数
- service层中编写所需业务接口，供controller层调用
- 实现service层中的接口，并注入mapper层中的sql接口
- 采用Mybatis的JavaConfig方式编写Sql语句。由于并没有使用Mybatis的逆向功能，需要自己手写所有sql语句
- 关于事务的实现，在启动类中开启事务，并在service层需要实现事务的业务接口上使用`@Transactional`注解，还是十分方便的
- 本项目开发并不是很难，只是在业务的实现上比较复杂

###### 页面与展示
- 作为一名后端开发，对于css的功力有所欠缺，这里我使用了[妹子UI主题](http://amazeui.org/ "妹子UI主题")，极大的减少了页面的开发难度，特此感谢
- 前端页面与后端的交互主要是在controller包中，并使用Thymeleaf渲染页面。
- 自定义异常处理页面，通过重写`WebMvcConfigurerAdapter`实现自动跳转到404、403页面

###### 其他功能
- 使用lazyload插件实现页面图片懒加载
- 后台实时记录当天访客量，便于了解博客日常访问量
- 分析访问量最多的数据，主要在于文章访问部分，将文章放入redis缓存。每次编辑完文章后，更新缓存
- 使用阿里云互联网中间件的业务实时监控服务，对于网站性能的了解以及优化有很大的帮助

###### 网站建设
- 服务器选用的是阿里云centos7
- 域名是阿里云上购买的.cn的域名
- 网站备案以及公安机关备案，后者备案时间较短但是那个备案网站经常挂掉，所以公安机关备案还得看运气。而网站备案时间就比较长了，按照阿里云的流程走大概1个月左右时间，需要上传个人身份信息以及邮寄个人资料过去。
- 网站配置了安全证书，可实现https访问以及自动从http跳转到https

## 总结
#### 开发中遇到的难点
- 要实现在一个页面进行权限验证，如果验证不成功会跳转到登录界面，并且登录成功后还要返回到之前界面，这里由于对SpringSecurity内部原理的不了解，所以我这里采用的方法是利用请求头和响应头存储url，并在登录成功后的页面出跳转到响应头中存储的url处
- 上传头像处使用上传头像至阿里云的OSS对象存储中，由于上传问题并没有返回上传成功后的图片url地址，于是只好设置OSS的Bucket为公共读权限，然后当上传成功后手动拼接图片url并存入数据库
- 项目中最大的难点还是莫过于页面css的设计，但是使用了[妹子UI](http://amazeui.org/ "妹子UI")后极大的解决了这个问题，只需修改少量css就能实现自己所需要的样式

#### 博客网站优缺点
- 首先最大的一个缺点就是在前端页面设计过程中混用了一些Bootstrap，导致依赖过于复杂，不便于后期修改，已经网站上有一些隐藏的bug
- 对于页面用户体验以及反馈功能的设计便于用户对于浏览过程中出现的问题进行反馈
- 后端部分明确的分工有利于项目的理解与维护

#### 项目整体优化
- 目前项目首页以及文章页响应时间过长，后期最好优化到1s响应时间
- 定时定期进行数据库的备份，防止出现网站被攻击后数据丢失的风险
- 写文章部分目前仅支持插入网络图片，无法从本地上传图片（已解决）
- 手机端浏览文章页面会出现代码自动换行问题，不便于浏览过程（已解决）

#### 未来需增加的功能
- 增加文章分享至QQ、微信、微博中功能
- 用户收藏文章功能
