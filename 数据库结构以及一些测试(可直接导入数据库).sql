/*
Navicat MySQL Data Transfer

Source Server         : lcoalhost
Source Server Version : 50719
Source Host           : localhost:3306
Source Database       : myblog

Target Server Type    : MYSQL
Target Server Version : 50719
File Encoding         : 65001

Date: 2019-05-13 13:05:32
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of article_likes_record
-- ----------------------------
INSERT INTO `article_likes_record` VALUES ('1', '1532884460', '1', '2018-07-31 20:00');
INSERT INTO `article_likes_record` VALUES ('2', '1533196734', '1', '2018-08-02 21:24');

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
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text NOT NULL,
  `mood` varchar(20) NOT NULL,
  `picsUrl` text,
  `publishDate` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of daily_speech
-- ----------------------------
INSERT INTO `daily_speech` VALUES ('1', '整了快一个月的页面，终于上线了<br>\r\n果不其然，前端页面是最恼火的<br>\r\n<br>\r\n把那些不想在空间或是朋友圈说的话<br>\r\n在自己的这片空间里<br>\r\n记录每日的心情<br>\r\n写写身边发生的小事<br>', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079972079.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973042.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973159.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973261.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973360.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973453.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973688.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079973939.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544079974188.jpeg', '2018-12-06 15:06:14');
INSERT INTO `daily_speech` VALUES ('2', '每晚回寝室的路上<br/>\n总是能看见小情侣的卿卿我我<br/>\n<br/>\n其实我也好想能有大学到结婚的爱情<br/>\n可是爱情不是配不配得上<br/>\n更多的时候<br/>\n是连追求的资格都没有', 'terrible', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110157631.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110158407.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110158509.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110158611.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110158705.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110158790.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110158891.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110159127.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-06/1544110159229.jpeg', '2018-12-06 23:29:19');
INSERT INTO `daily_speech` VALUES ('3', '这个冬天，冻的是肉体，寒的是心里<br/>\n只有工作室里的空调<br/>\n加上一瓶娃哈哈<br/>\n才有不一般的感觉，哈哈<br/>\n<br/>\n从朋友那才听说到，西安下雪了<br/>\n真的真的很美<br/>\n雪落西安，便是长安<br/>\n总有一天，我也想带着喜欢的人一起去看雪', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-07/1544155891051.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-07/1544155905452.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-07/1544155926976.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-07/1544155927224.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-07/1544155927486.jpeg', '2018-12-07 12:12:08');
INSERT INTO `daily_speech` VALUES ('4', '尴尬，前两周一个朋友帮我把简历投给成都的一家公司，刚才突然接到电话预约面试<br/>\n前提是要求我周一到周五推掉学校的课去实习<br/>\n<br/>\n没办法，只有果断拒绝了，下学期的课还是蛮多的也没时间去吧<br/>\n不过，那个小姐姐还多好的，哈哈哈<br/>\n挺有缘的一家公司，明年春招有机会到还可以去试试', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-07/1544166768415.jpeg', '2018-12-07 15:12:49');
INSERT INTO `daily_speech` VALUES ('5', '男人的嘴，骗人的鬼<br/>\n现在每次想起我朋友对我说的这句话就想笑<br/>\n今天又恰好在一篇文章中看到，又忍不住笑了<br/>\n<br/>\n天气真冷，冷的我都不敢装疯了', 'just', null, '2018-12-07 19:09:48');
INSERT INTO `daily_speech` VALUES ('6', '想起好久都没给家里打电话了<br/>\n也有要半年没有回过家了<br/>\n<br/>\n我也不知道自己在忙啥<br/>\n明明每天都有在制定计划的学习<br/>\n可是还是总那么漫无目的的进行着', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-08/1544244861281.jpeg', '2018-12-08 12:54:21');
INSERT INTO `daily_speech` VALUES ('7', '这周团队打卡时间严重不够<br/>\n就算如此，还跑去看了海王<br/>\n<br/>\n湄拉真的太太太漂亮了<br/>\n呜呜呜~~<br/>\n瞬间小心脏怦怦乱跳了<br/>\n<br/>\n看完吃了米粉跟煎饼<br/>\n一个满足的夜晚<br/>\n<br/>\n朋友们都开始关心起我要不帮我找女朋友<br/>\n总是嫌我看电影在她们之前一个人去<br/>\n哈哈哈，我有啥子办法', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-08/1544274538113.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-08/1544274538898.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-08/1544274556234.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-08/1544274574171.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-08/1544274578820.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-08/1544274578821.jpeg', '2018-12-08 21:09:39');
INSERT INTO `daily_speech` VALUES ('8', '每天困得不行<br/>\n还得硬撑着复习<br/>\n真的是难受的一匹<br/>\n<br/>\n何时是个头喃', 'terrible', null, '2018-12-09 14:55:26');
INSERT INTO `daily_speech` VALUES ('9', '芬兰的极光酒店<br/>\n简直美得过分！<br/>\n<br/>\n已经想好了<br/>\n藏心阁的往昔栏以及我的故事中的个人相册<br/>\n就统一做成相册形式吧<br/>\n往昔栏留着当以后的爱情相册<br/>\n我的故事就是一个简单的个人生活相册<br/>\nemmmm，等页面设计好了闲下来了就开干', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345499285.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345500043.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345500167.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345500630.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345501337.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345502029.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345502503.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345503220.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-09/1544345503915.jpeg', '2018-12-09 16:51:45');
INSERT INTO `daily_speech` VALUES ('10', '做一个稳妥的人<br/>\n不要高估两年后的自己<br/>\n也不要低估十年后的自己', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-10/1544408133749.jpeg', '2018-12-10 10:15:34');
INSERT INTO `daily_speech` VALUES ('13', '突然想起来有一周没看《我是大哥大》了<br/>\n今天赶忙去看了看<br/>\n果然还没有更新<br/>\n<br/>\n傻屌剧，一周的快乐源泉吖<br/>\n还有美美的理子', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-10/1544417650107.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-10/1544417650598.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-10/1544417651549.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-10/1544417652677.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-10/1544417653346.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-10/1544417661243.jpeg', '2018-12-10 12:54:45');
INSERT INTO `daily_speech` VALUES ('14', '昨晚上为了看最新的一集大哥大熬到12点半<br/>\n结果早上马原课起的多晚<br/>\n没吃早饭<br/>\n有点饿啊', 'terrible', null, '2018-12-11 10:14:40');
INSERT INTO `daily_speech` VALUES ('15', '今晚数据库原理上机<br/>\n本来以为可以一节课就写完的<br/>\n可是没想到<br/>\n不仅有点难，题还多<br/>\n其实写完这个实验才发现<br/>\n我的SQL语句写的真的有点差<br/>\n还需要多练呀<br/>\n平时项目中用到的SQL语句也都太简单<br/>\n仅仅涉及到的是单表查询<br/>\n<br/>\n今天的课多的一匹<br/>\n从早上到晚的<br/>\n奔溃啊！！<br/>\n', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-11/1544532394297.jpeg', '2018-12-11 20:46:35');
INSERT INTO `daily_speech` VALUES ('16', '其实，我们只是想找一个谈得来、合脾性，在一起舒坦、分开久了有点想念，安静久了想闹腾一下、吵架了又立马会后悔认输的人。爱情如此，友情同理。&nbsp;​​​', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580376566.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580376665.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580376684.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580376763.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580376999.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580377254.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580377494.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580377735.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544580377982.jpeg', '2018-12-12 10:06:18');
INSERT INTO `daily_speech` VALUES ('17', '这周到目前为止<br/>\n团队打卡时间不超过10个小时<br/>\n课又多<br/>\n还要抽时间去健身<br/>\n真滴累吖<br/>\n<br/>\n晚上跟伟哥他们健完身<br/>\n又是一顿豪餐(也挺便宜的，但是好吃啊)<br/>\n鸡腿堡+鸡肉卷+中可<br/>\n哇，健身需要的营养太多了<br/>\n<br/>\n', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544628931913.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-12/1544628932074.jpeg', '2018-12-12 23:35:47');
INSERT INTO `daily_speech` VALUES ('18', '下午对团队大一大二的进行了一番思想教育<br/>\n晚上还跟着去上了一会网<br/>\n英雄联盟打的又自闭了<br/>\n一晚上就只赢了两把<br/>\n有把还是对面挂机<br/>\n<br/>\n距离六级还有两天<br/>\nemmmm，我好像还没有开始<br/>\n明天刷几道阅读理解吧', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544707864765.jpeg', '2018-12-13 21:31:05');
INSERT INTO `daily_speech` VALUES ('19', '8102<br/>\n又是单身的一年<br/>\n羡慕那种<br/>\n让人痴迷的爱情<br/>\n', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715110241.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715110383.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715110481.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715111160.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715111655.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715112332.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715112799.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715113492.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-13/1544715114181.jpeg', '2018-12-13 23:31:55');
INSERT INTO `daily_speech` VALUES ('20', '昨晚做了个梦<br/>\n<br/>\n我妈给我生了一个弟弟跟妹妹<br/>\n弟弟很皮<br/>\n妹妹很乖<br/>\n<br/>\n我给我妈说能不能不要弟弟<br/>\n我只想要妹妹<br/>\n<br/>\n因为在梦里我弟打我<br/>\nemmmmm', 'just', null, '2018-12-14 12:40:42');
INSERT INTO `daily_speech` VALUES ('21', '下午健身练背+肱二头肌<br/>\n感觉还行，但是训练强度还不够<br/>\n<br/>\n晚上健完身又是吃华莱士+一份炒面<br/>\n奢侈的晚餐，花了20块大洋<br/>\n<br/>\n说好的一周只去上一次网的<br/>\n结果又没忍住朋友们的诱惑<br/>\n<br/>\n明天还要考六级什么都没准备<br/>\n算我活该吧', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-15/1544803662546.jpeg', '2018-12-15 00:07:43');
INSERT INTO `daily_speech` VALUES ('22', '下午考了六级<br/>\n很安逸，考的很懵逼<br/>\n下次不考了，浪费钱<br/>\n<br/>\n还有就是<br/>\n这周时间要打不够了<br/>\n不想罚钱啊', 'terrible', null, '2018-12-15 20:31:41');
INSERT INTO `daily_speech` VALUES ('23', '今早8点钟起床<br/>\n就为了早早的到团队打卡<br/>\n<br/>\n中午的时候感受到了地震的晃感<br/>\n然后在明理楼十楼根本没有跑下去的欲望<br/>\n<br/>\n中午没有吃饭就为了把这周的48个小时打够<br/>\n然而下午饿的不得了<br/>\n偷偷的签着到跑到后门吃了久香冒菜', 'just', null, '2018-12-16 20:35:13');
INSERT INTO `daily_speech` VALUES ('24', '如今<br/>\n连一个可以道晚安的人都没有<br/>\n<br/>\n那就<br/>\n晚安啦，世界', 'terrible', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-17/1544976393817.jpeg', '2018-12-17 00:06:34');
INSERT INTO `daily_speech` VALUES ('25', '又双叒叕没吃早饭<br/>\n<br/>\n上马原课<br/>\n逛微博<br/>\n聊微信<br/>\n肚子叫乎乎', 'just', null, '2018-12-18 10:48:45');
INSERT INTO `daily_speech` VALUES ('26', '生活不如意<br/>\n你有什么办法<br/>\n还不是得自己一人扛<br/>\n<br/>\n我也想<br/>\n多读书<br/>\n多旅行<br/>\n多去认识一些可爱的人', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135803567.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135803719.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135803857.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135803957.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135804052.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135804142.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135804377.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135804859.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-18/1545135805333.jpeg', '2018-12-18 20:23:26');
INSERT INTO `daily_speech` VALUES ('27', '喜欢慢一点的关系<br/>\n<br/>\n可以用很长时间去喜欢一个人<br/>\n但是只希望不要对喜欢草草决定<br/>\n<br/>\n你可以慢点来<br/>\n我们可以慢慢认识', 'just', null, '2018-12-19 20:41:52');
INSERT INTO `daily_speech` VALUES ('28', '其实互相喜欢并不难<br/>\n难的是既相爱又合适', 'terrible', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231291407.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231291611.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231291722.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231291825.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231291903.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231291990.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231292094.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231292177.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-19/1545231292270.jpeg', '2018-12-19 22:54:52');
INSERT INTO `daily_speech` VALUES ('29', '今天圣诞节<br/>\n会不会有什么不一样吖<br/>\n<br/>\n生活：不会<br/>\n我：嗷', 'terrible', null, '2018-12-20 23:11:51');
INSERT INTO `daily_speech` VALUES ('30', '今天真是蠢到家了<br/>\n<br/>\n前两天看完了少帅<br/>\n今天突然想看灵魂摆渡<br/>\n于是充了爱奇艺<br/>\n挺开心的哈哈哈<br/>\n<br/>\n但是我发现充错手机号了<br/>\n···········(假装保持微笑)', 'just', null, '2018-12-21 12:49:09');
INSERT INTO `daily_speech` VALUES ('31', '这学期坚持下来了两件事<br/>\n一是写博客<br/>\n二是健身<br/>\n也想要把这两件事一直坚持下去<br/>\n<br/>\n这两天开始看起《灵魂摆渡》电视剧<br/>\n炒鸡不错<br/>\n就是不要再像昨晚一样<br/>\n为了看剧熬到1点多睡了', 'terrible', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-22/1545460085149.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-22/1545460093590.jpeg', '2018-12-22 14:28:17');
INSERT INTO `daily_speech` VALUES ('32', '最近作息时间不规律<br/>\n痘痘又冒出来了<br/>\n还长了个溃疡<br/>\n贼难受<br/>\n<br/>\n这两天还碰到个傻逼网友<br/>\n妈的，有病<br/>\n三言两句就骂人的<br/>\n要不是看是个女的而且还是个程序媛<br/>\n早把她删了<br/>\n哇，我从小到大真没见过这种人', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-23/1545540737756.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-23/1545540737621.jpeg', '2018-12-23 12:52:18');
INSERT INTO `daily_speech` VALUES ('33', '今日健身后晚餐<br/>\n依然是<br/>\n汉堡、鸡肉卷、中可and一份饺子<br/>\n二十块大洋<br/>\n吃不起吃不起了<br/>\n<br/>\n2018年倒数第二个星期天<br/>\n过的还是那么一般<br/>\n圣诞节要到了<br/>\n今年没有苹果吧', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-23/1545566448140.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-23/1545566448316.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-23/1545566456944.jpeg', '2018-12-23 20:01:05');
INSERT INTO `daily_speech` VALUES ('34', '最近<br/>\n又长了好多痘痘<br/>\n真的好烦', 'terrible', null, '2018-12-25 13:13:15');
INSERT INTO `daily_speech` VALUES ('35', '最近迷上了《灵魂摆渡》<br/>\n每天干的事就是追剧<br/>\n团队、教室、寝室<br/>\n整的这几天完全不在状态<br/>\n学习没有学多少<br/>\n觉也睡的少，成天困兮兮的<br/>\n也逃了不少课<br/>\n果然追剧害死人喃<br/>\n还好，今天追完了<br/>\n可以回归正常作息', 'just', null, '2018-12-26 17:09:58');
INSERT INTO `daily_speech` VALUES ('36', '喜欢到极致<br/>\n是种什么样的感觉<br/>\n我不曾尝过<br/>\n<br/>\n今日是最后一次熬夜追剧<br/>\n哦不，是电影<br/>\n《灵魂摆渡黄泉》<br/>\n三七，你这个憨货<br/>\n怎能这生美丽', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545842834835.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545842841884.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545842851856.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545842862641.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545842871946.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545842881904.jpeg', '2018-12-27 00:48:08');
INSERT INTO `daily_speech` VALUES ('37', '早起的人为了钱<br/>\n晚睡的人为了情<br/>\n我不仅要晚睡还要早起<br/>\n因为我喜欢的东西都很贵<br/>\n我爱的人不爱我', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545896980246.jpeg', '2018-12-27 15:49:40');
INSERT INTO `daily_speech` VALUES ('38', '今天一个同行让我长了一般见识<br/>\n事情是这样的<br/>\n晚上访问我的博客网站首页<br/>\n发现加载完首页后会立刻跳转到百度页面<br/>\n由于这中间的速度真的太快没法调试是怎么跳过去的<br/>\n一开始以为是网站被DNS攻击了<br/>\n并且阿里云服务器攻击日志里今天也恰好有几条恶意攻击日志<br/>\n有恶意脚本也有人试图上传恶意漏洞<br/>\n不过都被防火墙防御住了<br/>\n也是这误导了我以为是DNS解析的问题<br/>\n花了点时间买了个VIP升级版的DNS云解析后发现问题仍在<br/>\n跟前端朋友交流后猛然发现是不是页面加载完后执行了什么js跳转<br/>\n于是去数据库里查看网站评论信息<br/>\n果然………mlgb，有人在文章评论中故意输入了重定向到百度的js代码<br/>\n而我恰好没有处理输入是js的情况<br/>\n<br/>\n惭愧惭愧，一件小小的恶意攻击事反应出了编码过程的不严谨<br/>\n网站安全还有待提高', 'just', null, '2018-12-27 23:47:13');
INSERT INTO `daily_speech` VALUES ('39', '希望我的2019<br/>\n吃得好&nbsp;睡得香<br/>\n痘痘全没&nbsp;身体备棒<br/>\n有朋友&nbsp;玩到老<br/>\n有人陪&nbsp;一起走<br/>\n<br/>\n我不善言语&nbsp;凡事冷漠&nbsp;<br/>\n不刻意迎合也不取悦谁&nbsp;<br/>\n性子很傲&nbsp;慢热但是重感情&nbsp;<br/>\n我没有很难过&nbsp;也没有很快乐<br/>\n<br/>\n以上，晚安。', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2018-12-27/1545926191217.jpeg', '2018-12-27 23:56:32');
INSERT INTO `daily_speech` VALUES ('40', '明天就是2018年的最后一天了<br/>\n朋友买了《地球最后的夜晚》的电影请我看<br/>\n看完打算一起去吃个烧烤<br/>\n以此度过18年地球最后一个夜晚吧<br/>\n<br/>\n抽时间写一篇文章<br/>\n简单的记录下我的2018吧', 'just', null, '2018-12-30 18:57:36');
INSERT INTO `daily_speech` VALUES ('41', '2018走的很快<br/>\n元旦三天选择了待在学校<br/>\n重复着团队、寝室、网吧的生活<br/>\n写了一篇年度总结<br/>\n但是我知道想写的其实远远不止那些<br/>\n<br/>\n总之<br/>\n2018，慢走<br/>\n2019，请善待我', 'happy', null, '2019-01-02 16:51:56');
INSERT INTO `daily_speech` VALUES ('42', '我的网易云年度总结<br/>\n想着去年好像关键字也是“姑娘”<br/>\n可能就好这口吧，哈哈<br/>\n<br/>\n为啥子我听民谣<br/>\n还是那么上瘾呢<br/>\n就不腻吗？', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-01-04/1546581780570.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-01-04/1546581783884.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-01-04/1546581786191.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-01-04/1546581788261.jpeg', '2019-01-04 14:03:12');
INSERT INTO `daily_speech` VALUES ('43', '1月9号有两门考试<br/>\n马原跟物联网传输技术<br/>\n还没有开始复习<br/>\n<br/>\n距离考试还有4天', 'terrible', null, '2019-01-05 13:32:19');
INSERT INTO `daily_speech` VALUES ('44', '以后博客只发技术文章了<br/>\n情感生活类还是转移到公众号上吧<br/>\n<br/>\n当然了<br/>\n自认为有写的好的还是例外', 'just', null, '2019-01-07 20:55:14');
INSERT INTO `daily_speech` VALUES ('45', '马原马原<br/>\n你要我命吗<br/>\n<br/>\n哲学不愧是哲学<br/>\n读起来都脑壳痛', 'terrible', null, '2019-01-08 13:50:39');
INSERT INTO `daily_speech` VALUES ('46', '经历了一场伟大哲学的斗争后发现<br/>\n其实马原考试也没那么难嘛<br/>\n自己挺会编的嘛<br/>\n<br/>\n上传解决了网站评论恶意js代码问题后<br/>\n不小心自己又写了bug<br/>\n怪不得留言内容都不见了<br/>\nemmmmm<br/>\n果然<br/>\nbug无处不在', 'just', null, '2019-01-09 14:58:33');
INSERT INTO `daily_speech` VALUES ('47', '明天还有最后两门专业课考试<br/>\n考完了就可以安心准备面试过程了<br/>\n<br/>\n很久没有给家里打电话了<br/>\n每次都是在生活费要完时才会主动给家里打电话<br/>\n<br/>\n最近眼睛干涩的很<br/>\n很疲劳，右下眼皮也经常跳个不停<br/>\n总是担心最近有什么不好的事情发生<br/>\n', 'terrible', null, '2019-01-13 20:50:14');
INSERT INTO `daily_speech` VALUES ('48', '正式开始最后一个寒假生活<br/>\n昨天上了一下午的网<br/>\n满足了心中的网瘾了<br/>\n<br/>\n剩下的日子就好好在学校学习吧<br/>\n认真做好每天的学习任务<br/>\n少玩点手机吧', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-01-16/1547630077822.jpeg', '2019-01-16 17:14:38');
INSERT INTO `daily_speech` VALUES ('49', '昨天跟王萍讨论了一下我的简历<br/>\n真的是见识大涨<br/>\n听到了很多以前听过但不了解的牛逼技术<br/>\n也才发现自己真的有点自以为是了<br/>\n对于目前会的知识就以为自己会很多了<br/>\n殊不知<br/>\n那只是冰山一角啊', 'just', null, '2019-01-19 12:05:15');
INSERT INTO `daily_speech` VALUES ('50', '目标：乐山<br/>\n回家的第一天<br/>\n仔细算算也有半年没有回家了<br/>\n<br/>\n内心还算平静吧<br/>\n以后回家的时间也更少了<br/>\n也是跟妈妈吵架后的第一次回家<br/>\n<br/>\n希望一切都是好的<br/>\n希望有个好年吧<br/>\n回家好好复习<br/>\n准备年后的面试', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-01-24/1548315043459.jpeg', '2019-01-24 15:30:53');
INSERT INTO `daily_speech` VALUES ('51', '放假回家这么久了<br/>\n已经完全忘记了初衷<br/>\n<br/>\n今天是一月的最后一天<br/>\n是该结束这样浑浑浊浊的日子了', 'terrible', null, '2019-01-31 14:52:33');
INSERT INTO `daily_speech` VALUES ('52', '时间：2018年农历大年三十<br/>\n<br/>\n今年一个人在老家默默的看着春晚，父母都去打牌了，苦苦的一个人哎，哎<br/>\n我的小女朋友你在哪里，我好想你<br/>\n<br/>\n今年的集福集卡也就将就不到30块钱<br/>\n年年虽如此<br/>\n没有什么好运<br/>\n但是重在参与嘛<br/>\n嘻嘻', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-02-04/1549291288578.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-02-04/1549291289484.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-02-04/1549291291102.jpeg', '2019-02-04 22:41:40');
INSERT INTO `daily_speech` VALUES ('53', '拿着压岁钱<br/>\n一口气还完花呗的感觉真好<br/>\n<br/>\n最后一年拿压岁钱了<br/>\n以后就要自己挣钱喽', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-02-06/1549439974619.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-02-06/1549439974664.jpeg', '2019-02-06 15:59:37');
INSERT INTO `daily_speech` VALUES ('54', '好久没来写了<br/>\n都要忘了自己还有个藏心阁了<br/>\n<br/>\n提前了快一周就来到学校复习<br/>\n完善了自己的简历<br/>\n投了不少的公司的春招提前批<br/>\n<br/>\n结果是大多数公司回复要么不合适要么待处理(然后进度就不变了)<br/>\n也是有点郁闷<br/>\n不过好在<br/>\n有个厦门吉比特的游戏公司简历过了<br/>\n一面也过了<br/>\n等着下周一的二面<br/>\n<br/>\n加油，不知这会不会是我人生的第一个offer', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-02-22/1550842370721.jpeg', '2019-02-22 21:32:51');
INSERT INTO `daily_speech` VALUES ('55', '英语六级350<br/>\n还是出乎了我的意料<br/>\n裸考都能这么高<br/>\n那我准备一下岂不是就...<br/>\n嘿嘿<br/>\n不过也没有机会考了<br/>\n<br/>\n吉比特经过三轮面试基本稳了<br/>\n就等后两天的HR面了<br/>\n还蛮期待的', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-02-27/1551253478253.jpeg', '2019-02-27 15:44:38');
INSERT INTO `daily_speech` VALUES ('56', '春招开始的第一个offer<br/>\n工作薪资还挺心动的<br/>\n问题在于<br/>\n深圳的房价啊是真的高。。。<br/>\n<br/>\n接下来春招还得继续<br/>\n不能因为有了一个offer就放弃后面更多的机会<br/>\n毕竟<br/>\n梦想可是大厂啊<br/>\n', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-03-01/1551404682346.jpeg', '2019-03-01 09:45:02');
INSERT INTO `daily_speech` VALUES ('57', '做梦都没想到的事<br/>\n一天就面完了一二面<br/>\n期待下周一的公司面试', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-03-01/1551436735693.jpeg', '2019-03-01 18:38:56');
INSERT INTO `daily_speech` VALUES ('58', '腾讯的三面多半是凉了<br/>\n不过好在<br/>\n吉比特的offer已经发下来了<br/>\n可问题也在这<br/>\n有了一个offer后就很懈怠了<br/>\n而且最近也没有面试<br/>\n是有点闲闲的', 'just', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-03-07/1551961248948.jpeg', '2019-03-07 20:20:50');
INSERT INTO `daily_speech` VALUES ('59', '呜呜呜<br/>\n今天收到腾讯春招提前批凉了<br/>\n哎<br/>\n阿里那边简历又一直处于筛选阶段', 'terrible', null, '2019-03-10 13:18:09');
INSERT INTO `daily_speech` VALUES ('60', '可怜，今天有小猫咪抓出血了<br/>\n第一次觉得离死亡进了一步<br/>\n吓得我感觉跑去打了狂犬疫苗<br/>\n<br/>\n明天字节跳动面试<br/>\n嘤嘤嘤<br/>\n加油吧<br/>\n反正希望不大', 'terrible', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-03-22/1553261811823.jpeg', '2019-03-22 21:37:11');
INSERT INTO `daily_speech` VALUES ('61', '我恋爱了，爱上了一个小傻瓜，好开心<br/>\n一定一定要好好爱她<br/>\n刘云芸，我爱你', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-04-22/1555919411914.jpeg', '2019-04-19 10:50:13');
INSERT INTO `daily_speech` VALUES ('62', '刚刚改了博客的一些内容<br/>\n和她在一起的感觉真好<br/>\n虽然她有时真的挺傻的<br/>\n我也很笨<br/>\n总是喜欢黏着她<br/>\n哎<br/>\n说好的是她黏着我的嘛<br/>\n怎么。。。<br/>\n不过每次她叫我小宝贝时心里就甜蜜蜜的<br/>\n哈哈哈，喜欢上她的感觉真好', 'happy', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-04-22/1555920059475.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-04-22/1555920059945.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-04-22/1555920060416.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-04-22/1555920060888.jpeg,https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/dailySpeech/2019-04-22/1555920063524.jpeg', '2019-04-22 16:01:06');

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
INSERT INTO `user` VALUES ('1', '19940790216', '张海洋', 'a3caed36f0fe5a01e5f144db8927235e', 'male', '张海洋', '1997-07-05', '1125694337@qq.com', '', 'https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/public/user/avatar/张海洋/1536759681.jpeg', '2018-09-19 13:52:50');

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
