/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : myblog

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2020-06-20 10:31:40
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for archives
-- ----------------------------
DROP TABLE IF EXISTS `archives`;
CREATE TABLE `archives` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `archiveName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of archives
-- ----------------------------
INSERT INTO `archives` VALUES ('1', '2018年07月');
INSERT INTO `archives` VALUES ('2', '2018年08月');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `author` varchar(255) NOT NULL,
  `originalAuthor` varchar(255) NOT NULL,
  `articleTitle` varchar(255) NOT NULL,
  `articleContent` longtext NOT NULL,
  `articleTags` varchar(255) NOT NULL,
  `articleType` varchar(255) NOT NULL,
  `articleCategories` varchar(255) NOT NULL,
  `publishDate` varchar(255) NOT NULL,
  `updateDate` varchar(255) NOT NULL,
  `articleUrl` varchar(255) NOT NULL,
  `articleTabloid` text NOT NULL,
  `likes` int(11) NOT NULL,
  `lastArticleId` bigint(20) DEFAULT NULL,
  `nextArticleId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('1', '1532884460', '张海洋', '张海洋', '关于此博客，我有话哔哔', '##自我介绍\n我叫张海洋，紧张的张，目前是一名在校大学生，该博客的维护人，技术方向是Web后端开发，由于我大学专业是物联网工程专业，偏软硬件结合，但是众所周知，大学所培养的人才专业学习一般都得等到大二下学期乃至大三，这对于许多人来说也是个魔咒吧，好不容易经历了xx年义务教育，又历经了高考的洗礼，到了大学发现生活是如此的悠哉，没有专业课的紧张学习，难免都会如温水煮青蛙一般，陷入“舒适”的生活无法自拔。\n\n我很幸运，在大学刚开始加入了计科院翼灵物联工作室，也很荣幸成为这个大家庭中的一员。大一下学期结束的那个暑假，自己在家花了20天的时间自学了Java，从此以后，陷入后端无法自拔。\n\n##关于这个博客网站吖\n在高中三年里，我养成了每天写日记的习惯，现在我的家里还有一本记载着我高中记忆的本本，这个习惯陪伴了我高中，然后毕业之后也“成功”的戒掉了这个习惯。若非入了程序猿这行，我想恐怕这辈子也没机会拾起自己的破烂笔杆子吧...\n\n我也没想到自己会花费两个月时间去建一个自己的博客，毕竟对于自己来说，两个月前的我也还算的上是一名前端小白吧，但随着自己一步一步去设计页面的每一个元素，到最后的完成前后端交互，真的理解了许多前端知识。\n\n原本是定于7月5号发布博客的第一版本，但由于本学期欠的一些账弄得期末考试复习花费了大量时间（没错，这应该就是我拖延上线时间的借口吧~~），博客也一直放下，没有太多时间去搭建。放假回家也是完全被假期的愉快感消磨掉了激情，所幸的是，我还并没有放弃，总算是完成了当初给自己定下的目标。\n\n> 有些事情不是看到希望才去坚持，而是坚持了才会看到希望\n\n对于这个网站的搭建自己付出了太多时间了，虽然这也并不是我认为的最好版本，等自己能力以及水平进一步提升之后，我想我应该还会为此折腾吧。\n\n##想想再说点啥吧\n对于这个博客，我也准备借此记录下我的一些学习日志、生活日常、旅行风景等等。大学生活真的是还没怎么享受就快要结束了，没办法，自己选的路，再怎么也得往下走。记录记录人生，去看看世界，给未来的自己留下点青春的影子\n\n当然最重要的还是要借此多总结学习中的一些问题以及学习中踩得一些坑来提升自己的能力吧，在此我也不给自己立啥flag了，反正那些总会倒的，不如看自己的心情了，哪天心情好了，上来写点学习心得呀或是吐槽吐槽今天又在学校食堂里吃出哪样“高蛋白”吖。本人文采一般，向来也不是能一个人哔哔很多话的，万事都有开头，坚持下去，一切都会好起来的。\n\n总之，这个博客也将是我程序猿生涯的一个新的开始吧，保持生活的激情，坚持走下去，程序猿这条路很枯燥、很漫长，只要坚守本心，一切困难与寂寞都将如同泡沫。加油，向着梦想中的bat前进吧。', '随笔感悟,原创', '原创', '我的故事', '2018-07-30', '2018-07-30', 'https://www.zhyocean.cn/article/1532884460', '自我介绍我叫张海洋，紧张的张，目前是一名在校大学生，该博客的维护人，技术方向是Web后端开发，由于我大学专业是物联网工程专业，偏软硬件结合，但是众所周知，大学所培养的人才专业学习一般都得等到大二下学期乃至大三，这对于许多人来说也是个魔咒吧，好不容易经历了xx年义务教育，又历经了高考的洗礼，到了大学发现生活是如此的悠哉，没有专业课的紧张学习，难免都会如温水煮青蛙一般，陷入“舒适”的生活无法自拔。...', '3', '0', '1533196734');
INSERT INTO `article` VALUES ('2', '1533196734', '张海洋', '张海洋', 'SpringBoot之从零搭建博客网站', '![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/IMG_5015.JPG)\n> 文字不够，图片来凑。\n\n## 前言\n#### 为什么想要搭建这个博客？\n我还记得，在大二寒假的某天，同往常一样的在家解决这某个bug，不停地问度娘，很巧的碰到了一个同行在他的博客中完美的记录了我的bug的解决方案，随后我又看了看他写的其他博客文章，觉得都非常的不错，并且同时也被他博客网站的简约清新吸引，也就在那刻，心中埋下了准备自己搭建myblog的种子...\n\n于是在寒假的时候我就开始了Bootstrap了的学习，然后好像也并没有什么卵用，因为还是不会用~~~\n\n就这样一直拖了大概有3个月，某天我才意识到我好像有件很重要的事没完成，就是这么的突然，毫无准备的就开始了博客的搭建。\n\n自己在本子上设计了网站的所有页面的大致样式(也借鉴了许多大佬的博客样式，哈哈，我承认我审美不是很好)，列出了应该有的功能，当时看来并不算多，也给自己定了一个目标期限，在7月5日之前上线，不错有了目标也就有了动力，就这么开始干了。\n\n一件事情在开头总是想的很美好，然而事实总会跟你对着干。在博客搭建的过程中遇到了无数多的前端页面设计bug，我在此之前也可以算的上是个前端小小白，很是无奈，不过还是要在这里感谢翼灵工作室里帮我解决了许多bug的波波、田小宇和杨小卿，没有你们估计我的博客还得推迟半年才能上线吧，O(∩_∩)O哈哈~\n\n6、7月也恰好是考试月，堆积在一起的无数们考试如暴风雨一样一夜袭来，一学期欠的帐总该还了，不得不放下手中的网站专心去备考，于是乎，完美的错过了本该上线的时间。\n\n> 编程是个脑力活，如果把它做成了体力活，这就代表是时候改变一下了\n\n#### 文章概述\n- 关于项目，对于学习Springboot是个挺不错的练手项目，可以让你在烦恼的业务逻辑中保持一颗纯洁的心\n- 如何从零开始，使用Springboot开发项目\n- 开发前的一些准备工作，以及思考项目整体结构与思路\n- 记录开发过程中遇到的一些难题以及bug\n- 总结目前博客网站的一些优缺点\n- 思考整个项目有哪些可以优化的地方，以及有哪些可增加的功能\n\n## 页面展示\n\n##### 首页展示\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802120610.png)\n<br>\n##### 文章编辑\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802120611.png)\n<br>\n##### 后台管理\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802121241.png)\n<br>\n##### 用户个人中心\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802121749.png)\n\n## 项目需求\n#### 项目背景\n对于初学Springboot的朋友来说，最好的一个学习方式就是那一个功能俱全的项目来练练手，通过自己重构项目来发现其中的潜在难题，并且也能很好的在编码过程中总结和发现问题、解决问题。使用Springboot开发的博客系统，简单并且实用，适合做练手项目。\n\n#### 功能需求\n###### 主页\n- 博客汇总，以列表形式展示文章，并附上文章作者、发布日期、分类情况以及文章简要\n\n- 能够以分类形式查看文章\n\n- 能够以时间列表方式归档文章\n\n- 可实现通过标签查找所有相关文章\n\n- 个人介绍、联系方式\n\n- 博客网站更新记录\n\n- 友链链接\n\n###### 后台管理\n- 网站仪表盘，记录网站访客量情况\n\n- 文章管理\n1.分页展示文章信息\n2.可对文章进行再编辑以及删除文章\n\n- 发布文章\n1.使用markdown编辑器，支持插入代码，插入图片等功能\n2.文章可选择分类和标签，以及转载文章支持链接原作者文章\n\n- 分类管理，支持增加、删除、修改分类\n\n- 友情链接\n1.支持增加友情链接\n2.支持删除友情链接\n\n- 反馈信息管理，可查看用户反馈信息\n\n#### 安装部署需求\n- 可以使用docker方式部署，也可支持-jar方式\n- 使用springboot自带方式打包\n\n#### 非功能需求\n##### 性能需求\n- 首页响应时间不超过2秒钟\n- 文章页响应时间不超过3秒钟\n\n## 项目设计\n\n#### 总体设计\n- **本项目用到的技术和框架**\n1.项目构建：Maven\n2.web框架：Springboot\n3.数据库ORM：Mybatis\n4.数据库连接池： HikariCP \n5.分页插件：PageHelper\n6.数据库：MySql\n7.缓存：Redis\n8.前端模板：Thymeleaf\n9.文章展示：Editor.md\n\n- **本项目中的关键点**\n1.采用Springboot开发，数据库使用连接池加orm框架的模式，对于系统的关键业务使用Redis缓存，加快相应速度。\n2.整体系统采用门户网站+后台管理+用户个人中心的方式搭建，门户网站展示博客内容以及博主介绍，后台管理用于编辑文章，查看反馈，管理评论留言。\n3.使用阿里云OSS进行静态资源存储，以及CDN全站加速。\n\n- **环境**\n\n|  工具 | 名称  |\n| ------------ | ------------ |\n| 开发工具  | IDEA  |\n|  语言 | JDK1.8、HTML、css、js  |\n| 数据库  | Mysql5.6  |\n| 项目框架  | SSM  |\n| ORM  | Mybatis  |\n| 安全框架  | SpringSecurity  |\n| 缓存  | Redis  |\n| 项目构建  | Maven  |\n| 运行环境  | 阿里云Centos7  |\n\n#### 结构设计\n\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802140136.png)\n对于熟悉Spring开发的朋友来说，相信对此结构也不会陌生。平时的开发过程中，结构设计是重要的环节，特别是协作开发的时候，明细的分包，模块化，可减少代码提交时的冲突。并且明确的结构有助于我们快速的寻找所对应的类。\n\n## 业务设计\n#### 发布文章流程\n\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802141221.png)\n\n#### 登录流程\n\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/201808021412271.png)\n\n#### 用户个人资料修改流程\n\n![](https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/%E5%8D%9A%E5%AE%A2%E6%97%A5%E5%BF%97/SpringBoot%E4%B9%8B%E4%BB%8E%E9%9B%B6%E6%90%AD%E5%BB%BA%E5%8D%9A%E5%AE%A2%E7%BD%91%E7%AB%99/20180802143230.png)\n\n## 打包、部署和运行\n- 本项目采用Springboot的maven插件进行打包，打包结果：****.jar\n- 部署方式：使用 nohup java -jar ******.jar >******.log 2>&1 &的方式，后台启动项目，并在该路径下生成运行日志\n\n## 数据设计\n\n###### 用户表：user\n| 名称  | 类型  |  长度 |  主键 | 非空  | 描述  |\n| ------------ | ------------ | ------------ | ------------ | ------------ | ------------ |\n| id  | int  |  11 |  true |  true | 主键，自增  |\n| phone  | varchar  | 255  | false  | true  | 手机号  |\n| username  | varchar  | 255  |  false | true  |  用户名 |\n| password  |  varchar |  255 |  false | true  | 密码  |\n| gender  | char  | 50  | false  |  true | 性别  |\n| trueName  | varchar  | 255  |  false | false  | 姓名  |\n| birthday  |  char | 100  |  false | false  | 生日  |\n| email  | varchar  | 255  | false  | false  | 邮箱  |\n| personalBrief  |  varchar | 255  | false  | false  |  个人简介 |\n| avatarImgUrl  |  varchar |  255 | false  |  true | 头像url |\n| recentlyLanded  | varchar  |  255 |  false | false  |  最近登录时间 |\n\n###### 文章表：article\n| 名称  | 类型  |  长度 |  主键 | 非空  | 描述  |\n| ------------ | ------------ | ------------ | ------------ | ------------ | ------------ |\n| id  | int  |  11 |  true |  true | 主键，自增  |\n| articleId  | bigint  | 20  | false  | true  | 文章id  |\n| author  | varchar  | 255  |  false | true  |  作者 |\n| originalAuthor  |  varchar |  255 |  false | true  | 文章原作者  |\n| articleTitle  | varchar  | 255  | false  |  true | 文章标题  |\n| articleContent  | longtext  | 0  |  false | true  | 文章内容  |\n| articleTags  |  varchar | 255  |  false | true  | 文章标签  |\n| articleType  | varchar  | 255  | false  | true  | 文章类型  |\n| articleCategories  |  varchar | 255  | false  | true  |  文章分类 |\n| publishDate  |  varchar |  255 | false  |  true | 发布文章日期 |\n| updateDate  | varchar  |  255 |  false | true  |  更新文章日期 |\n| articleUrl  | varchar  |  255 |  false | true  |  文章url |\n| articleTabloid  | 0  |  255 |  false | true  |  文章摘要 |\n| likes  | int  |  11 |  false | true  |  文章喜欢数 |\n| lastArticleId  | bigint  |  20 |  false | false  |  上一篇文章id |\n| nextArticleId  | bigint  |  20 |  false | false  |  下一篇文章id |\n\n###### 评论记录表：comment_record\n| 名称  | 类型  |  长度 |  主键 | 非空  | 描述  |\n| ------------ | ------------ | ------------ | ------------ | ------------ | ------------ |\n| id  | bigint  |  20 |  true |  true | 主键，自增  |\n| pId  | bigint  | 20  | false  | true  | 父id  |\n| articleId  | bigint  | 20  |  false | true  |  文章id |\n| originalAuthor  |  varchar |  255 |  false | true  | 文章原作者  |\n| answererId  | int  | 11  | false  |  true | 评论者id  |\n| respondentId  | int  | 11  |  false | true  | 被评论者id  |\n| commentDate  |  varchar | 100  |  false | true  | 评论日期  |\n| likes  | int  | 11  | false  | true  | 评论点赞数  |\n| commentContent  |  text | 0  | false  | true  |  评论内容 |\n\n## 开发流程\n###### 数据库CRUD\n- controller层中编写前端接口，接收前端参数\n- service层中编写所需业务接口，供controller层调用\n- 实现service层中的接口，并注入mapper层中的sql接口\n- 采用Mybatis的JavaConfig方式编写Sql语句。由于并没有使用Mybatis的逆向功能，需要自己手写所有sql语句\n- 关于事务的实现，在启动类中开启事务，并在service层需要实现事务的业务接口上使用`@Transactional`注解，还是十分方便的\n- 本项目开发并不是很难，只是在业务的实现上比较复杂\n\n###### 页面与展示\n- 作为一名后端开发，对于css的功力有所欠缺，这里我使用了[妹子UI主题](http://amazeui.org/ \"妹子UI主题\")，极大的减少了页面的开发难度，特此感谢\n- 前端页面与后端的交互主要是在controller包中，并使用Thymeleaf渲染页面。\n- 自定义异常处理页面，通过重写`WebMvcConfigurerAdapter`实现自动跳转到404、403页面\n\n###### 其他功能\n- 使用lazyload插件实现页面图片懒加载\n- 后台实时记录当天访客量，便于了解博客日常访问量\n- 分析访问量最多的数据，主要在于文章访问部分，将文章放入redis缓存。每次编辑完文章后，更新缓存\n- 使用阿里云互联网中间件的业务实时监控服务，对于网站性能的了解以及优化有很大的帮助\n\n###### 网站建设\n- 服务器选用的是阿里云centos7\n- 域名是阿里云上购买的.cn的域名\n- 网站备案以及公安机关备案，后者备案时间较短但是那个备案网站经常挂掉，所以公安机关备案还得看运气。而网站备案时间就比较长了，按照阿里云的流程走大概1个月左右时间，需要上传个人身份信息以及邮寄个人资料过去。\n- 网站配置了安全证书，可实现https访问以及自动从http跳转到https\n\n## 总结\n#### 开发中遇到的难点\n- 要实现在一个页面进行权限验证，如果验证不成功会跳转到登录界面，并且登录成功后还要返回到之前界面，这里由于对SpringSecurity内部原理的不了解，所以我这里采用的方法是利用请求头和响应头存储url，并在登录成功后的页面出跳转到响应头中存储的url处\n- 上传头像处使用上传头像至阿里云的OSS对象存储中，由于上传问题并没有返回上传成功后的图片url地址，于是只好设置OSS的Bucket为公共读权限，然后当上传成功后手动拼接图片url并存入数据库\n- 项目中最大的难点还是莫过于页面css的设计，但是使用了[妹子UI](http://amazeui.org/ \"妹子UI\")后极大的解决了这个问题，只需修改少量css就能实现自己所需要的样式\n\n#### 博客网站优缺点\n- 首先最大的一个缺点就是在前端页面设计过程中混用了一些Bootstrap，导致依赖过于复杂，不便于后期修改，已经网站上有一些隐藏的bug\n- 对于页面用户体验以及反馈功能的设计便于用户对于浏览过程中出现的问题进行反馈\n- 后端部分明确的分工有利于项目的理解与维护\n\n#### 项目整体优化\n- 目前项目首页以及文章页响应时间过长，后期最好优化到1s响应时间\n- 定时定期进行数据库的备份，防止出现网站被攻击后数据丢失的风险\n- 写文章部分目前仅支持插入网络图片，无法从本地上传图片\n- 手机端浏览文章页面会出现代码自动换行问题，不便于浏览过程\n\n#### 未来需增加的功能\n- 增加文章分享至QQ、微信、微博中功能\n- 用户可在线写文章功能\n- 用户收藏文章功能\n\n#### *以上就是我在博客网站搭建过程后的所有总结记录，可能会有遗缺部分，等以后想起来了再来修改吧。*\n\n> 本人秉持开源原则，待后期网站功能完善之后会同步源码至Github、码云中。需要搭建个人博客的朋友欢迎使用本博客，只要给我个stars就好啦，哈哈。如果搭建过程中有各种问题欢迎来骚。\n', 'SpringBoot,个人博客,原创', '原创', 'SpringBoot', '2018-08-02', '2018-08-02', 'https://www.zhyocean.cn/article/1533196734', '文字不够，图片来凑。前言为什么想要搭建这个博客？我还记得，在大二寒假的某天，同往常一样的在家解决这某个bug，不停地问度娘，很巧的碰到了一个同行在他的博客中完美的记录了我的bug的解决方案，随后我又看了看他写的其他博客文章，觉得都非常的不错，并且同时也被他博客网站的简约清新吸引，也就在那刻，心中埋下了准备自己搭建myblog的种子…于是在寒假的时候我就开始了Bootstrap了的学习，然后好像也并没有什么卵用，因为还是不会用~~~...', '2', '1532884460', '0');

-- ----------------------------
-- Table structure for article_likes_record
-- ----------------------------
DROP TABLE IF EXISTS `article_likes_record`;
CREATE TABLE `article_likes_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `likerId` int(11) NOT NULL,
  `likeDate` varchar(255) NOT NULL,
  `isRead` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_likes_record
-- ----------------------------
INSERT INTO `article_likes_record` VALUES ('1', '1532884460', '1', '2018-07-31 20:00', '0');
INSERT INTO `article_likes_record` VALUES ('2', '1533196734', '1', '2018-08-02 21:24', '0');

-- ----------------------------
-- Table structure for categories
-- ----------------------------
DROP TABLE IF EXISTS `categories`;
CREATE TABLE `categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `categoryName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of categories
-- ----------------------------
INSERT INTO `categories` VALUES ('1', '我的故事');
INSERT INTO `categories` VALUES ('2', 'SpringBoot');

-- ----------------------------
-- Table structure for comment_likes_record
-- ----------------------------
DROP TABLE IF EXISTS `comment_likes_record`;
CREATE TABLE `comment_likes_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `articleId` bigint(20) NOT NULL,
  `pId` int(11) NOT NULL,
  `likerId` int(11) NOT NULL,
  `likeDate` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment_likes_record
-- ----------------------------

-- ----------------------------
-- Table structure for comment_record
-- ----------------------------
DROP TABLE IF EXISTS `comment_record`;
CREATE TABLE `comment_record` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `pId` bigint(20) NOT NULL,
  `articleId` bigint(20) NOT NULL,
  `answererId` int(11) NOT NULL,
  `respondentId` int(11) NOT NULL,
  `commentDate` varchar(255) NOT NULL,
  `likes` int(255) NOT NULL,
  `commentContent` text NOT NULL,
  `isRead` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of comment_record
-- ----------------------------
INSERT INTO `comment_record` VALUES ('2', '0', '1533196734', '1', '1', '2018-08-03 00:13', '1', '测试评论功能，嘻嘻嘻', '0');
INSERT INTO `comment_record` VALUES ('3', '2', '1533196734', '1', '1', '2018-08-03 00:15', '0', '一切正常，哈哈哈', '0');

-- ----------------------------
-- Table structure for daily_speech
-- ----------------------------
DROP TABLE IF EXISTS `daily_speech`;
CREATE TABLE `daily_speech` (
  `id` int(11) NOT NULL,
  `content` text NOT NULL,
  `mood` varchar(20) NOT NULL,
  `picsUrl` text,
  `publishDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of daily_speech
-- ----------------------------
INSERT INTO `daily_speech` VALUES ('1', '整了快一个月的页面，终于上线了<br>\r\n果不其然，前端页面是最恼火的<br>\r\n<br>\r\n把那些不想在空间或是朋友圈说的话<br>\r\n在自己的这片空间里<br>\r\n记录每日的心情<br>\r\n写写身边发生的小事<br>', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079972079.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973042.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973159.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973261.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973360.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973453.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973688.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973939.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079974188.jpeg', '2018-12-06 15:06:14');

-- ----------------------------
-- Table structure for feedback
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `feedbackContent` text NOT NULL,
  `contactInfo` varchar(255) DEFAULT NULL,
  `personId` int(11) NOT NULL,
  `feedbackDate` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feedback
-- ----------------------------

-- ----------------------------
-- Table structure for friendlink
-- ----------------------------
DROP TABLE IF EXISTS `friendlink`;
CREATE TABLE `friendlink` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `blogger` varchar(40) NOT NULL,
  `url` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of friendlink
-- ----------------------------
INSERT INTO `friendlink` VALUES ('1', 'naget的小屋', 'https://naget.github.io');
INSERT INTO `friendlink` VALUES ('2', 'Li Pan\'s 博客', 'http://www.lipan.xyz');
INSERT INTO `friendlink` VALUES ('3', '陈晓雷个人博客', 'http://www.csxll.top');
INSERT INTO `friendlink` VALUES ('4', 'SAn Blog', 'https://sanii.cn');
INSERT INTO `friendlink` VALUES ('5', '会打篮球的程序猿', 'http://www.liuzhaopo.top');
INSERT INTO `friendlink` VALUES ('6', 'Mr_曾中杰', 'https://www.zengzhongjie.com');
INSERT INTO `friendlink` VALUES ('7', '去当CTO', 'https://www.qdcto.com');
INSERT INTO `friendlink` VALUES ('8', 'Face2Object', 'https://www.bossding.com.cn');
INSERT INTO `friendlink` VALUES ('9', '小海博客', 'https://www.celess.cn');
INSERT INTO `friendlink` VALUES ('10', '枫之羽', 'http://fzhiy.com');

-- ----------------------------
-- Table structure for leave_message_likes_record
-- ----------------------------
DROP TABLE IF EXISTS `leave_message_likes_record`;
CREATE TABLE `leave_message_likes_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pageName` varchar(255) NOT NULL,
  `pId` int(11) NOT NULL,
  `likerId` int(11) NOT NULL,
  `likeDate` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leave_message_likes_record
-- ----------------------------

-- ----------------------------
-- Table structure for leave_message_record
-- ----------------------------
DROP TABLE IF EXISTS `leave_message_record`;
CREATE TABLE `leave_message_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pageName` varchar(255) NOT NULL,
  `pId` int(255) NOT NULL,
  `answererId` int(11) NOT NULL,
  `respondentId` int(11) NOT NULL,
  `leaveMessageDate` varchar(255) NOT NULL,
  `likes` int(11) NOT NULL,
  `leaveMessageContent` text NOT NULL,
  `isRead` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of leave_message_record
-- ----------------------------
INSERT INTO `leave_message_record` VALUES ('14', 'categories', '0', '1', '1', '2018-09-19 13:53', '0', '分类留言测试', '0');
INSERT INTO `leave_message_record` VALUES ('15', 'archives', '0', '1', '1', '2018-09-19 13:53', '0', '归档留言', '0');
INSERT INTO `leave_message_record` VALUES ('16', 'tags', '0', '1', '1', '2018-09-19 13:53', '0', '标签留言', '0');
INSERT INTO `leave_message_record` VALUES ('17', 'update', '0', '1', '1', '2018-09-19 13:53', '0', '更新留言', '0');
INSERT INTO `leave_message_record` VALUES ('18', 'friendlylink', '0', '1', '1', '2018-09-19 13:54', '0', '需要添加友链的朋友可在www.zhyocean.cn/friendlylink下方留言（网站名称+网址），随后验证后会在本人博客中添加友链链接', '0');

-- ----------------------------
-- Table structure for privateword
-- ----------------------------
DROP TABLE IF EXISTS `privateword`;
CREATE TABLE `privateword` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `privateWord` varchar(255) NOT NULL,
  `publisherId` varchar(255) NOT NULL,
  `replierId` varchar(255) DEFAULT NULL,
  `replyContent` varchar(255) DEFAULT NULL,
  `publisherDate` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of privateword
-- ----------------------------
INSERT INTO `privateword` VALUES ('8', '悄悄话测试', '1', '0', null, '2018-09-19 14:13:32');

-- ----------------------------
-- Table structure for reward
-- ----------------------------
DROP TABLE IF EXISTS `reward`;
CREATE TABLE `reward` (
  `id` int(11) NOT NULL,
  `fundRaiser` varchar(30) NOT NULL,
  `fundRaisingSources` varchar(50) NOT NULL,
  `fundraisingPlace` varchar(50) NOT NULL,
  `rewardMoney` float NOT NULL,
  `remarks` varchar(100) DEFAULT NULL,
  `rewardDate` datetime NOT NULL,
  `rewardUrl` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of reward
-- ----------------------------
INSERT INTO `reward` VALUES ('1', '海**依', '公众号赞赏', '《搜狐焦点·拯救先心儿》', '20', '无', '2019-07-04 00:00:00', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/rewardRecord/2019-07-15/1563121018.jpeg');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES ('1', 'ROLE_USER');
INSERT INTO `role` VALUES ('2', 'ROLE_ADMIN');
INSERT INTO `role` VALUES ('3', 'ROLE_SUPERADMIN');

-- ----------------------------
-- Table structure for tags
-- ----------------------------
DROP TABLE IF EXISTS `tags`;
CREATE TABLE `tags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `tagName` varchar(255) NOT NULL,
  `tagSize` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tags
-- ----------------------------
INSERT INTO `tags` VALUES ('1', '随笔感悟', '15');
INSERT INTO `tags` VALUES ('4', 'SpringBoot', '17');
INSERT INTO `tags` VALUES ('5', '个人博客', '18');
INSERT INTO `tags` VALUES ('18', '原创', '20');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `phone` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `gender` char(255) NOT NULL,
  `trueName` varchar(255) DEFAULT NULL,
  `birthday` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `personalBrief` varchar(255) DEFAULT NULL,
  `avatarImgUrl` text NOT NULL,
  `recentlyLanded` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', '19940790216', '张海洋', 'a3caed36f0fe5a01e5f144db8927235e', 'male', '张海洋', '1997-07-05', '1125694337@qq.com', '', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/张海洋/1575283189.png', '2019-12-02 18:31:15');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role` (
  `User_id` int(11) NOT NULL,
  `Role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES ('1', '1');
INSERT INTO `user_role` VALUES ('1', '2');
INSERT INTO `user_role` VALUES ('1', '3');

-- ----------------------------
-- Table structure for visitor
-- ----------------------------
DROP TABLE IF EXISTS `visitor`;
CREATE TABLE `visitor` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `visitorNum` bigint(20) NOT NULL,
  `pageName` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of visitor
-- ----------------------------
INSERT INTO `visitor` VALUES ('1', '3228', 'totalVisitor');
INSERT INTO `visitor` VALUES ('2', '1032', 'visitorVolume');
INSERT INTO `visitor` VALUES ('3', '42', 'article/1532884460');
INSERT INTO `visitor` VALUES ('5', '57', 'article/1533196734');
