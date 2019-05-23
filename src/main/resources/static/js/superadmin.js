
    var deleteArticleId="";
    var friendLinkId="";

    $('.superAdminList .superAdminClick').click(function () {
        var flag = $(this).attr('class').substring(16);
        $('#statistics,#articleManagement,#articleThumbsUp,#articleCategories,#friendLink,#userFeedback,#privateWord').css("display","none");
        $("#" + flag).css("display","block");
    });

    //填充悄悄话
    function putInAllPrivateWord(data) {
        var privateWord = $('.superAdminInfo .privateWord');
        privateWord.empty();
        var amPanelGroup = $('<div class="am-panel-group" id="accordion"></div>');
        $.each(data['result'], function (index,obj) {
            var amPanel = $('<div class="am-panel am-panel-default"></div>');
            amPanel.append('<div class="am-panel-hd">' +
                '<h3 style="font-weight: 500" class="am-panel-title" data-am-collapse="{parent: \'#accordion\', target: \'#do-not-say-' + index + '\'}">' +
                obj['publisher'] +
                '</h3>' +
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
            feedbackInfos.append('<div class="noNews">这里空空如也</div>');
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
        var table = $('<table class="table am-table am-table-bd am-table-striped admin-content-table  am-animation-slide-right"></table>');
        table.append($('<thead>' +
            '<tr>' +
            '<th>文章标题</th><th>发布时间</th><th>文章分类</th><th>浏览量</th><th>操作</th>' +
            '</tr>' +
            '</thead>'));
        var tables = $('<tbody class="tables"></tbody>');
        $.each(data['result'], function (index, obj) {
            tables.append($('<tr id="a' + obj['id'] + '"><td><a href="article/' + obj['articleId'] + '">' + obj['articleTitle'] + '</a></td><td>' + obj['publishDate'] + '</td><td>' + obj['articleCategories'] + '</td> <td><span class="am-badge am-badge-success">' + obj['visitorNum'] + '</span></td>' +
                '<td>' +
                '<div class="am-dropdown" data-am-dropdown>' +
                '<button class="articleManagementBtn articleEditor am-btn am-btn-secondary">编辑</button>' +
                '<button class="articleDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
                '</div>' +
                '</td>' +
                '</tr>'));
        });
        table.append(tables);
        articleManagementTable.append(table);
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
    //填充点赞信息
    function putInArticleThumbsUp(data) {
        var msgContent = $('.msgContent');
        msgContent.empty();
        if(data['result'].length == 0){
            msgContent.append($('<div class="noNews">' +
                '这里空空如也' +
                '</div>'));
        } else {
            msgContent.append($('<div class="msgReadTop">' +
                '未读消息：<span class="msgIsReadNum">' + data['msgIsNotReadNum'] + '</span>'+
                '<a class="msgIsRead">全部标记为已读</a>' +
                '</div>'));
            $.each(data['result'], function (index, obj) {
                var msgRead = $('<div class="msgRead" id="p' + obj['id'] + '"></div>');
                if(obj['isRead'] == 1){
                    msgRead.append($('<span class="msgReadSign"></span>'));
                }
                msgRead.append($('<span class="am-badge msgType">点赞</span>'));
                msgRead.append($('<span class="msgHead"><a class="msgPerson">' + obj['praisePeople'] + '</a>点赞了你的博文</span>'));
                msgRead.append($('<div class="msgTxt">' +
                    '<span><a class="articleTitle" href="/article/' + obj['articleId'] + '" target="_blank">' + obj['articleTitle'] + '</a></span>' +
                    '<span class="msgDate">' + obj['likeDate'] + '</span>' +
                    '</div>' +
                    '<hr>'));
                msgContent.append(msgRead);
            });
            msgContent.append($('<div class="my-row" id="thumbsUpPage">' +
                '<div class="thumbsUpPagination">' +
                '</div>' +
                '</div>'))
        }

        //已读一条消息
        $('.articleTitle').click(function () {
            var parent = $(this).parent().parent().parent();
            var isRead = true;
            var num = $('.msgIsReadNum').html();
            if(parent.find($('.msgReadSign')).length != 0){
                isRead = false;
            }
            if(isRead == false){
                var id = parent.attr('id').substring(1);
                $.ajax({
                    type:'get',
                    url:'/readThisThumbsUp',
                    dataType:'json',
                    data:{
                        id:id,
                    },
                    success:function (data) {
                    },
                    error:function () {
                    }
                })
                //去掉未读红点
                parent.find($('.msgReadSign')).removeClass('msgReadSign');
                //未读消息减1
                $('.msgIsReadNum').html(--num);

                // 去掉左侧栏未读消息
                if(num == 0){
                    $('.articleThumbsUpNum').remove();
                } else {
                    $('.articleThumbsUpNum').html(num);
                }
            }
        })

        //全部标记为已读
        $('.msgIsRead').click(function () {
            var num = $('.msgIsReadNum').html();
            if(num != 0){
                $.ajax({
                    type:'get',
                    url:'/readAllThumbsUp',
                    dataType:'json',
                    data:{
                    },
                    success:function (data) {
                        if(data['status'] == 403){
                            $.get("/toLogin",function(data,status,xhr){
                                window.location.replace("/login");
                            });
                        } else {
                            $('.msgIsReadNum').html(0);
                            $('.msgContent').find($('.msgReadSign')).removeClass('msgReadSign');

                            $('.articleThumbsUpNum').remove();
                        }
                    },
                    error:function () {
                    }
                })
            }

        })
    }

    //删除文章
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
    });
    //编辑或增加友链
    $('.sureFriendLinkAddBtn').click(function () {
        var blogger = $.trim($('#blogger').val());
        var url = $.trim($('#url').val());
        if(blogger != "" && url != ""){
            $.ajax({
                type:'post',
                url:'/addFriendLink',
                dataType:'json',
                data:{
                    id:friendLinkId,
                    blogger:blogger,
                    url:url
                },
                success:function (data) {
                    if(data['code'] == 200){
                        successNotice(data['msg']);
                        var tr = $('<tr id="p' + data['data'] + '"><td class="blogger">' + blogger + '</td><td class="url">' + url + '</td>' +
                            '<td>' +
                            '<div class="am-dropdown" data-am-dropdown="">' +
                            '<button class="friendLinkManagementBtn articleEditor am-btn am-btn-secondary">编辑</button>' +
                            '<button class="friendLinkDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
                            '</div>' +
                            '</td>' +
                            '</tr>');
                        $('.friendLinkManagementTable').append(tr);
                        var friendLinkNum = $('.friendLinkNum').html();
                        $('.friendLinkNum').html(++friendLinkNum);

                        //刷新刚填充上的友链的两个按钮，使编辑和删除两个按钮的js生效
                        addAndDeleteBtn();
                    } else if (data['code'] == 201){
                        dangerNotice(data['msg']);
                    } else if(data['code'] == 202){
                        successNotice(data['msg']);
                        $('#p' + friendLinkId).find($('.blogger')).html(blogger);
                        $('#p' + friendLinkId).find($('.url')).html(url);
                    } else {
                        dangerNotice(data['msg']);
                    }
                },
                error:function () {
                   alert("更新友链失败！")
                }
            });
        } else {
            dangerNotice("博主或博客地址不能为空！");
        }
    });
    //删除友链
    $('.sureFriendLinkDeleteBtn').click(function () {
        $.ajax({
            type:'post',
            url:'/deleteFriendLink',
            dataType:'json',
            data:{
                id:friendLinkId
            },
            success:function (data) {
                if(data['code'] == 200){
                    successNotice(data['msg']);
                    $('#p'+ friendLinkId).remove();
                    var friendLinkNum = $('.friendLinkNum').html();
                    $('.friendLinkNum').html(--friendLinkNum);
                } else {
                    dangerNotice(data['msg']);
                }
            },
            error:function () {
                alert("删除友链失败");
            }
        });
    });

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
                if(data['articleThumbsUpNum'] != 0){
                    $('.articleThumbsUp').find('a').append($('<span class="am-badge am-badge-warning am-margin-right am-fr articleThumbsUpNum">' + data['articleThumbsUpNum'] + '</span>'));
                }
            },
            error:function () {
                alert("获取统计信息失败");
            }
        });
    }
    //获得文章管理文章
    function getArticleManagement(currentPage) {
        $.ajax({
            type:'post',
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
    //获得文章点赞信息
    function getArticleThumbsUp(currentPage) {
        $.ajax({
            type:'post',
            url:'/getArticleThumbsUp',
            dataType:'json',
            data:{
                rows:10,
                pageNum:currentPage
            },
            success:function (data) {
                if(data['status'] == 403){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
                }
                putInArticleThumbsUp(data);
                scrollTo(0,0);//回到顶部

                //分页
                $(".thumbsUpPagination").paging({
                    rows:data['pageInfo']['pageSize'],//每页显示条数
                    pageNum:data['pageInfo']['pageNum'],//当前所在页码
                    pages:data['pageInfo']['pages'],//总页数
                    total:data['pageInfo']['total'],//总记录数
                    callback:function(currentPage){
                        getArticleThumbsUp(currentPage);
                    }
                });
            },
            error:function () {
                alert("获取文章点赞信息失败");
            }
        });
    }
    //获得文章分类信息
    function getArticleCategories() {
        $.ajax({
            type:'get',
            url:'/getArticleCategories',
            dataType:'json',
            data:{
            },
            success:function (data) {
                var categoryContent = $('.categoryContent');
                categoryContent.empty();
                categoryContent.append($('<div class="contentTop">' +
                    '目前分类数：<span class="categoryNum">' + data['result'].length + '</span>' +
                    '<div class="updateCategory">' +
                    '<a class="addCategory"><i class="am-icon-plus-square"></i> 添加分类</a> / ' +
                    '<a class="subCategory"><i class="am-icon-minus-square"></i> 删除分类</a>' +
                    '</div>'));
                var categories = $('<div class="categories"></div>');
                $.each(data['result'], function (index, obj) {
                    categories.append($('<span id="p' + obj['id'] + '" class="category">' + obj['categoryName'] + '</span>'));
                })
                categoryContent.append(categories);

                // 添加分类
                $('.addCategory').click(function () {
                    $('#addCategory').modal({
                        relatedTarget: this,
                        onConfirm: function(e) {
                            var categoryName = e.data;
                            categoryName = $.trim(categoryName);
                            if(categoryName == ""){
                                dangerNotice("分类名不能为空！");
                            }else {
                                updateCategory(categoryName, 1);
                            }
                        },
                        onCancel: function(e) {
                        }
                    });
                })

                // 删除分类
                $('.subCategory').click(function () {
                    $('#subCategory').modal({
                        relatedTarget: this,
                        onConfirm: function(e) {
                            var categoryName = e.data;
                            categoryName = $.trim(categoryName);
                            if(categoryName == ""){
                                dangerNotice("分类名不能为空！");
                            }else {
                                updateCategory(categoryName, 2);
                            }
                        },
                        onCancel: function(e) {
                        }
                    });
                })
            },
            error:function () {

            }
        });
    }

    // 添加或者删除分类
    function updateCategory(categoryName, type) {
        $.ajax({
            type:'post',
            url:'/updateCategory',
            dataType:'json',
            data:{
                categoryName:categoryName,
                type:type
            },
            success:function (data) {
                var categoryNum = $('.categoryNum').html();
                if(data['status'] == 200){
                    $('.categories').append($('<span id="p' + data['id'] + '" class="category">' + categoryName + '</span>'));
                    $('.categoryNum').html(++categoryNum);
                } else if (data['status'] == 202){
                    var allCategories = $('.category');
                    $('.categoryNum').html(--categoryNum);
                    for(var i=0;i<allCategories.length;i++){
                        if(allCategories[i].innerHTML == categoryName){
                            allCategories[i].remove();
                        }
                    }
                }
                successNotice(data['msg']);
            },
            error:function () {
                alert("操作失败");
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
    //点击点赞管理
    $('.superAdminList .articleThumbsUp').click(function () {
        getArticleThumbsUp(1);
    });
    //点击分类管理
    $('.superAdminList .articleCategories').click(function () {
        getArticleCategories();
    });
    //点击友链管理
    $('.superAdminList .friendLink').click(function () {
        $.ajax({
            type:'post',
            url:'/getFriendLink',
            dataType:'json',
            data:{
            },
            success:function (data) {
                var friendLinkContent = $('.friendLinkContent');
                friendLinkContent.empty();
                friendLinkContent.append($('<div class="contentTop">' +
                    '目前友链数：' +
                    '<span class="friendLinkNum">' + data.length + '</span>' +
                    '<div class="updateFriendLink">' +
                    '<a class="addFriendLink"><i class="am-icon-plus-square"></i> 添加友链</a>' +
                    '</div>' +
                    '</div>'));
                var table = $('<table class="am-table am-table-bd am-table-striped admin-content-table  am-animation-slide-right" style="margin-top: 15px"></table>');
                table.append($('<thead>' +
                    '<tr>' +
                    '<th>博主</th><th>博客地址</th><th>操作</th>' +
                    '</tr>' +
                    '</thead>'));
                var friendLinkManagementTable = $('<tbody class="friendLinkManagementTable"></tbody>');
                for(var i in data){
                    var friendLink = $('<tr id="p' + data[i]['id'] + '">' +
                        '<td class="blogger">' + data[i]['blogger'] + '</td>' +
                        '<td class="url">' + data[i]['url'] + '</td>' +
                        '<td>' +
                        '<div class="am-dropdown" data-am-dropdown="">' +
                        '<button class="friendLinkManagementBtn articleEditor am-btn am-btn-secondary">编辑</button>' +
                        '<button class="friendLinkDeleteBtn articleDelete am-btn am-btn-danger">删除</button>' +
                        '</div>' +
                        '</td>' +
                        '</tr>');
                    friendLinkManagementTable.append(friendLink);
                }
                table.append(friendLinkManagementTable);
                friendLinkContent.append(table);

                //添加友链
                $('.addFriendLink').click(function () {
                    friendLinkId = "";
                    $('#addFriendLink').modal('open');
                    $('#blogger').val("");
                    $('#url').val("");
                });

                addAndDeleteBtn();
            },
            error:function () {
            }
        });
    });

    //获得仪表盘信息
    getStatisticsInfo();

    function addAndDeleteBtn() {
        //编辑友链
        $('.friendLinkManagementBtn').click(function () {
            $('#addFriendLink').modal('open');
            var $this = $(this).parent().parent().parent();
            var blogger = $this.find('.blogger').html();
            var url = $this.find('.url').html();
            friendLinkId = $this.attr('id').substring(1);
            $('#blogger').val(blogger);
            $('#url').val(url);
        });

        //删除友链
        $('.friendLinkDeleteBtn').click(function () {
            friendLinkId = $(this).parent().parent().parent().attr('id').substring(1);
            $('#deleteFriendLink').modal('open');
        });
    }