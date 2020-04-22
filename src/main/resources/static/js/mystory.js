
    $('.userList .clickLi').click(function () {
        var flag = $(this).attr('class').substring(8);
        $('#myStory,#myPhoto').css("display","none");
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

    //填充我的故事
    function putInMyStory(data) {
        var myStoryContent = $('.myStoryContent');
        myStoryContent.empty();
        var timeLine = $('<div class="timeline timeline-wrap"></div>');
        timeLine.append($('<div class="timeline-row">' +
            '<span class="node" style="-webkit-box-sizing: content-box;-moz-box-sizing: content-box;box-sizing: content-box;">' +
            '<i class="am-icon-coffee"></i>' +
            '</span>' +
            '<h1 class="title am-animation-slide-top"># 择一城终老，遇一人白首</h1>' +
            '</div>'));
        $.each(data['result'],function (index,obj) {

            var timelineRowMajor = $('<div class="timeline-row-major"></div>');
            timelineRowMajor.append($('<span class="node am-animation-slide-top am-animation-delay-1"></span>'));
            var content = $('<div class="content am-comment-main am-animation-slide-top am-animation-delay-1"></div>');
            content.append($('<header class="am-comment-hd" style="background: #fff">' +
                '<div class="contentTitle am-comment-meta">' +
                '<a href="/article/' + obj['articleId'] + '">' + obj['articleTitle'] + '</a>' +
                '</div>' +
                '</header>'));
            var amCommentBd = $('<div class="am-comment-bd"></div>');
            amCommentBd.append($('<i class="am-icon-calendar"> <a href="/archives?archive=' + obj['publishDate'] + '">' + obj['publishDate'] + '</a></i>' +
                '<i class="am-icon-folder"> <a href="/categories?category=' + obj['articleCategories'] + '">' + obj['articleCategories'] + '</a></i>'));
            var amCommentBdTags = $('<i class="am-comment-bd-tags am-icon-tag"></i>')
            for(var i=0;i<obj['articleTags'].length;i++){
                var tag = $('<a href="/tags?tag=' + obj['articleTags'][i] + '">' + obj['articleTags'][i] + '</a>');
                amCommentBdTags.append(tag);
                if(i != (obj['articleTags'].length-1)){
                    amCommentBdTags.append(",");
                }
            }
            amCommentBd.append(amCommentBdTags);
            content.append(amCommentBd);
            timelineRowMajor.append(content);
            timeLine.append(timelineRowMajor);
        });

        myStoryContent.append(timeLine);
        myStoryContent.append($('<div class="my-row" id="page-father">' +
            '<div id="myStoryPagination">' +
            '<ul class="am-pagination  am-pagination-centered">' +
            '<li class="am-disabled"><a href="#">&laquo; 上一页</a></li>' +
            '<li class="am-active"><a href="#">1</a></li>' +
            '<li><a href="#">2</a></li>' +
            '<li><a href="#">3</a></li>' +
            '<li><a href="#">4</a></li>' +
            '<li><a href="#">5</a></li>' +
            '<li><a href="#">下一页 &raquo;</a></li>' +
            '</ul>' +
            '</div>' +
            '</div>'));
    }

    //反馈
    $('.feedbackClick').click(function () {
        $('.feedback').css("display","block")
    });
    $('.feedbackClose').click(function () {
        $('.feedback').css("display","none")
    });
    $('.feedbackFormBtn').click(function () {
        var feedbackFormContent = $('#feedbackFormContent');
        var feedbackFormQQ = $('.feedbackFormQQ');
        if(feedbackFormContent.val().length == 0){
            dangerNotice("反馈内容不能为空哦！")
        } else {
            $.ajax({
                type:'POST',
                url:'/submitFeedback',
                dataType:'json',
                data:{
                    feedbackContent:feedbackFormContent.val(),
                    contactInfo:feedbackFormQQ.val()
                },
                success:function (data) {
                    if(data['status'] == 101){
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    } else if (data['status'] == 103){
                        dangerNotice(data['message'] + " 反馈失败")
                    } else {
                        successNotice("反馈成功，我会尽快解决的！嘻嘻");
                        $('.feedback').css("display","none");
                    }
                },
                error:function () {
                }
            });
        }
    });

    //获得我的故事
    function getMyStory(currentPage) {
        $.ajax({
            type:'get',
            url:'/getMyStory',
            dataType:'json',
            data:{
                rows:"10",
                pageNum:currentPage
            },
            success:function (data) {
                if(data['status'] == 103){
                    alert(data['message'] + " 获得我的故事失败")
                } else {
                    putInMyStory(data['data']);
                    scrollTo(0,0);//回到顶部

                    //分页
                    $("#myStoryPagination").paging({
                        rows:data['data']['pageInfo']['pageSize'],//每页显示条数
                        pageNum:data['data']['pageInfo']['pageNum'],//当前所在页码
                        pages:data['data']['pageInfo']['pages'],//总页数
                        total:data['data']['pageInfo']['total'],//总记录数
                        callback:function(currentPage){
                            getMyStory(currentPage);
                        }
                    });
                }
            },
            error:function () {
            }
        });
    }

    getMyStory(1);