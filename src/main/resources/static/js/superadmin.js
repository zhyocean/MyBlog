
    var deleteArticleId="";

    $('.superAdminList .superAdminClick').click(function () {
        var flag = $(this).attr('class').substring(16);
        $('#statistics,#articleManagement,#articleComment,#articleCategories,#friendLink,#userFeedback,#privateWord').css("display","none");
        $("#" + flag).css("display","block");
    });

    // 失败消息盒
    function dangerNotice(notice) {
        $('.dangerNotice').html(notice);
        $('.dangerNoticeAlert').css("display","block");
        var closeNoticeBox = setTimeout(function () {
            $('.dangerNoticeAlert').css("display","none");
        },3000);
    }
    // 成功消息盒
    function successNotice(notice) {
        $('.successNotice').html(notice);
        $('.successNoticeAlert').css("display","block");
        var closeNoticeBox = setTimeout(function () {
            $('.successNoticeAlert').css("display","none");
        },3000);
    }

    //填充悄悄话
    function putInAllPrivateWord(data) {
        var privateWord = $('.superAdminInfo .privateWord');
        privateWord.empty();
        var amPanelGroup = $('<div class="am-panel-group" id="accordion"></div>');
        $.each(data['result'], function (index,obj) {
            var amPanel = $('<div class="am-panel am-panel-default"></div>');
            amPanel.append('<div class="am-panel-hd">' +
                '<h4 style="font-weight: 500" class="am-panel-title" data-am-collapse="{parent: \'#accordion\', target: \'#do-not-say-' + index + '\'}">' +
                obj['publisher'] +
                '</h4>' +
                '</div>');
            var doNotSay = $('<div id="do-not-say-' + index + '" class="am-panel-collapse am-collapse"></div>');
            var userPrivateWord = $('<div class="userPrivateWord am-panel-bd"></div>');
            var userPrivateWordUl = $('<ul class="am-list am-list-border"></ul>');
            $.each(obj['content'], function (index1, obj1) {
                if(obj1['replyContent'] !== ""){
                    userPrivateWordUl.append('<li>' +
                        '<div class="userPrivateWordTime">' +
                        obj1['publisherDate'] +
                        '</div><br>' +
                        '<a id="p' + obj1['id'] + '">' + obj1['privateWord']+
                        '<br>' +
                        '<div class="myReply">' +
                        '回复：<span class="myReplyContent">' + obj1['replyContent'] + '</span>' +
                        '</div></a>' +
                        '</li>');
                } else {
                    userPrivateWordUl.append('<li>' +
                        '<div class="userPrivateWordTime">' +
                        obj1['publisherDate'] +
                        '</div><br>' +
                        '<a id="p' + obj1['id'] + '">' + obj1['privateWord']+
                        '<br>' +
                        '<div class="myNoReply">' +
                        '回复：<span class="myReplyContent">还没有回复人家哦</span>' +
                        '</div></a><div class="userPrivateWordReply am-animation-slide-top">' +
                        '<textarea class="replyTextarea" placeholder="填写悄悄话回复"></textarea>' +
                        '<button type="button" class="userPrivateWordReplyBtn am-btn am-btn-success am-round">回复</button>' +
                        '<button type="button" class="userPrivateWordReplyCloseBtn am-btn am-round">取消</button>' +
                        '</div>' +
                        '</li>');
                }
            });
            userPrivateWord.append(userPrivateWordUl);
            doNotSay.append(userPrivateWord);
            amPanel.append(doNotSay);
            amPanelGroup.append(amPanel);
        });
        privateWord.append(amPanelGroup);

        $('.userPrivateWord a').click(function () {
            var $this = $(this);
            var userPrivateWordReply = $this.next();
            userPrivateWordReply.toggle();
        });
        $('.userPrivateWordReplyCloseBtn').click(function () {
            $('.userPrivateWordReplyCloseBtn').parent().css("display","none");
        });

        $('.userPrivateWordReplyBtn').click(function () {
           var $this = $(this);
           var replyId = $this.parent().prev().attr("id").substring(1);
           var textarea = $this.prev().val();
           if(textarea.length == 0){
               dangerNotice("你还没有填写回复内容！")
           } else {
               $.ajax({
                   type:'post',
                   url:'/replyPrivateWord',
                   dataType:'json',
                   data:{
                       replyId:replyId,
                       replyContent:textarea
                   },
                   success:function (data) {
                       if(data['status'] == 403){
                           $.get("/toLogin",function(data,status,xhr){
                               window.location.replace("/login");
                           });
                       } else {
                           successNotice("回复成功！");
                           $this.prev().val("");
                           $('#p' + data['result']['replyId']).find('.myReplyContent').html(data['result']['replyContent']);
                           $this.parent().css("display","none");
                           $this.parent().prev().find('.myNoReply').css("color","#b5b5b5");
                           $this.parent().prev().attr('disabled', 'true');
                       }
                   },
                   error:function () {
                       alert("获取悄悄话失败");
                   }
               });
           }
        });
    }
    //填充反馈信息
    function putInAllFeedback(data) {
        var feedbackInfos = $('.feedbackInfos');
        feedbackInfos.empty();
        if(data['result'].length == 0){
            feedbackInfos.append('<div class="noFeedback">无反馈信息</div>');
        } else {
            $.each(data['result'], function (index, obj) {
                var feedbackInfo = $('<div class="feedbackInfo"></div>');
                feedbackInfo.append('<div class="feedbackInfoTitle">' +
                    '<span class="feedbackName">' + obj['person'] + '</span>' +
                    '<span class="feedbackTime">' + obj['feedbackDate'] + '</span>' +
                    '</div>');
                feedbackInfo.append('<div class="feedbackInfoContent">' +
                    '<span class="feedbackInfoContentWord">反馈内容：</span>' +
                    obj['feedbackContent'] +
                    '</div>');
                var feedbackInfoContact = $('<div class="feedbackInfoContact"></div>');
                if(obj['contactInfo'] !== ""){
                    feedbackInfoContact.append('<span class="contactInfo">联系方式：</span>' +
                        obj['contactInfo']);
                } else {
                    feedbackInfoContact.append('<span class="contactInfo">联系方式：</span>' + '无'
                    );
                }
                feedbackInfo.append(feedbackInfoContact);
                feedbackInfos.append(feedbackInfo);
            });
            feedbackInfos.append($('<div class="my-row" id="page-father">' +
                '<div id="feedbackPagination">' +
                '<ul class="am-pagination  am-pagination-centered">' +
                '<li class="am-disabled"><a href="">&laquo; 上一页</a></li>' +
                '<li class="am-active"><a href="">1</a></li>' +
                '<li><a href="">2</a></li>' +
                '<li><a href="">3</a></li>' +
                '<li><a href="">4</a></li>' +
                '<li><a href="">5</a></li>' +
                '<li><a href="">下一页 &raquo;</a></li>' +
                '</ul>' +
                '</div>' +
                '</div>'));
        }

    }
    //填充文章管理
    function putInArticleManagement(data) {
        var articleManagementTable = $('.articleManagementTable');
        articleManagementTable.empty();
        $.each(data['result'], function (index, obj) {
            articleManagementTable.append($('<tr id="a' + obj['id'] + '"><td><a href="findArticle?articleId=' + obj['articleId'] + '&originalAuthor=' + obj['originalAuthor'] + '">' + obj['articleTitle'] + '</a></td><td>' + obj['publishDate'] + '</td><td>' + obj['articleCategories'] + '</td> <td><span class="am-badge am-badge-success">' + obj['visitorNum'] + '</span></td>' +
                '<td>' +
                '<div class="am-dropdown" data-am-dropdown>' +
                '<button class="articleManagementBtn articleEditor am-btn am-btn-secondary">编辑</button>' +
                '<button class="articleDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
                '</div>' +
                '</td>' +
                '</tr>'));
        });
        articleManagementTable.append($('<div class="my-row" id="page-father">' +
            '<div id="articleManagementPagination">' +
            '<ul class="am-pagination  am-pagination-centered">' +
            '</ul>' +
            '</div>' +
            '</div>'));

        $('.articleManagementBtn').click(function () {
           var $this = $(this);
           var id = $this.parent().parent().parent().attr("id").substring(1);
           window.location.replace("/editor?id=" + id);
        });
        $('.articleDeleteBtn').click(function () {
            var $this = $(this);
            deleteArticleId = $this.parent().parent().parent().attr("id").substring(1);
            $('#deleteAlter').modal('open');
        })
    }

    $('.sureArticleDeleteBtn').click(function () {
        $.ajax({
            type:'get',
            url:'/deleteArticle',
            dataType:'json',
            data:{
                id:deleteArticleId
            },
            success:function (data) {
                if(data == 0){
                    dangerNotice("删除文章失败")
                } else {
                    successNotice("删除文章成功");
                    getArticleManagement(1);
                }
            },
            error:function () {
                alert("删除失败");
            }
        });
    })

    //获得反馈信息
    function getAllFeedback(currentPage) {
        $.ajax({
            type:'get',
            url:'/getAllFeedback',
            dataType:'json',
            data:{
                rows:10,
                pageNum:currentPage
            },
            success:function (data) {
                putInAllFeedback(data);
                scrollTo(0,0);//回到顶部

                //分页
                $("#feedbackPagination").paging({
                    rows:data['pageInfo']['pageSize'],//每页显示条数
                    pageNum:data['pageInfo']['pageNum'],//当前所在页码
                    pages:data['pageInfo']['pages'],//总页数
                    total:data['pageInfo']['total'],//总记录数
                    callback:function(currentPage){
                        getAllFeedback(currentPage);
                    }
                });
            },
            error:function () {
                alert("获取反馈信息失败");
            }
        });
    }
    //获取统计信息
    function getStatisticsInfo() {
        $.ajax({
            type:'get',
            url:'/getStatisticsInfo',
            dataType:'json',
            data:{
            },
            success:function (data) {
                $('.allVisitor').html(data['allVisitor']);
                $('.yesterdayVisitor').html(data['yesterdayVisitor']);
                $('.allUser').html(data['allUser']);
                $('.articleNum').html(data['articleNum']);
            },
            error:function () {
                alert("获取统计信息失败");
            }
        });
    }
    //获得文章管理文章
    function getArticleManagement(currentPage) {
        $.ajax({
            type:'get',
            url:'/getArticleManagement',
            dataType:'json',
            data:{
                rows:10,
                pageNum:currentPage
            },
            success:function (data) {
                putInArticleManagement(data);
                scrollTo(0,0);//回到顶部

                //分页
                $("#articleManagementPagination").paging({
                    rows:data['pageInfo']['pageSize'],//每页显示条数
                    pageNum:data['pageInfo']['pageNum'],//当前所在页码
                    pages:data['pageInfo']['pages'],//总页数
                    total:data['pageInfo']['total'],//总记录数
                    callback:function(currentPage){
                        getArticleManagement(currentPage);
                    }
                });
            },
            error:function () {
                alert("获取文章信息失败");
            }
        });
    }

    //点击悄悄话
    $('.superAdminList .privateWord').click(function () {
        $.ajax({
            type:'post',
            url:'/getAllPrivateWord',
            dataType:'json',
            data:{
            },
            success:function (data) {
                if(data['result'].length == 0){
                    $('.privateWord').append($('<div>无悄悄话</div>'));
                } else {
                    putInAllPrivateWord(data);
                }
            },
            error:function () {
                alert("获取悄悄话失败");
            }
        });
    });
    //点击反馈
    $('.superAdminList .userFeedback').click(function () {
        getAllFeedback(1);
    });
    //点击文章管理
    $('.superAdminList .articleManagement').click(function () {
        getArticleManagement(1);
    });

    getStatisticsInfo();

    //图片懒加载 注意: 需要引入jQuery和underscore
    $(function() {
        // 获取window的引用:
        var $window = $(window);
        // 获取包含data-src属性的img，并以jQuery对象存入数组:
        var lazyImgs = _.map($('img[data-src]').get(), function (i) {
            return $(i);
        });
        // 定义事件函数:
        var onScroll = function () {
            // 获取页面滚动的高度:
            var wtop = $window.scrollTop();
            // 判断是否还有未加载的img:
            if (lazyImgs.length > 0) {
                // 获取可视区域高度:
                var wheight = $window.height();
                // 存放待删除的索引:
                var loadedIndex = [];
                // 循环处理数组的每个img元素:
                _.each(lazyImgs, function ($i, index) {
                    // 判断是否在可视范围内:
                    if ($i.offset().top - wtop < wheight) {
                        // 设置src属性:
                        $i.attr('src', $i.attr('data-src'));
                        // 添加到待删除数组:
                        loadedIndex.unshift(index);
                    }
                });
                // 删除已处理的对象:
                _.each(loadedIndex, function (index) {
                    lazyImgs.splice(index, 1);
                });
            }
        };
        // 绑定事件:
        $window.scroll(onScroll);
        // 手动触发一次:
        onScroll();
    });