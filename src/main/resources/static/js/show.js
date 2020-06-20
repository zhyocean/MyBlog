
    var articleId = "";

    //填充文章
    function putInArticle(data) {
        $('.zhy-article-top').html('');
        $('.zhy-article-footer').html('');
        var articleTop = $('<article-top><div class="article-title">' +
            '<h1>' + data.articleTitle + '</h1>' +
            '</div>' +
            '<div class="article-info row">' +
            '<div class="article-info article-info-type am-badge am-badge-success">' +
            data.articleType +
            '</div>' +
            '<div class="article-info article-info-publishDate">' +
            '<i class="am-icon-calendar"><a class="articleCategoryColor" href="/archives?archive=' + data.publishDate + '"> ' + data.publishDate + '</a></i>' +
            '</div>' +
            '<div class="article-info article-info-originalAuthor">' +
            '<i class="am-icon-user"> ' + data.originalAuthor + '</i>' +
            '</div>' +
            '<div class="article-info article-info-categories">' +
            '<i class="am-icon-folder"> <a class="articleCategoryColor" href="/categories?category=' + data.articleCategories + '">' + data.articleCategories + '</a></i>' +
            '</div>' +
            '</div></article-top><div class="article-i-say">' +
            '多年以后，愿你的城市，有清风，有烈酒，也有人是你的归途。<span class="article-i-say-me">--- 张海洋</span>' +
            '</div>');
        $('.zhy-article-top').append(articleTop);
        $("#mdText").text(data.articleContent);
        var wordsView;
        wordsView = editormd.markdownToHTML("wordsView", {
            htmlDecode: "true", // you can filter tags decode
            emoji: true,
            taskList: true,
            tex: true,
            flowChart: true,
            sequenceDiagram: true
        });
        var articleFooter = $('<div class="end-logo">' +
            '<i class="am-icon-btn am-success am-icon-lg">完</i>' +
            '</div>' +
            '<div class="show-weixin">' +
            '<p><i class="weiXinQuoteLeft am-icon-quote-left "></i></p><br>' +
            '<p class="show-weixin-pic">' +
            '<img src="https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/static/img/weixin.jpg">' +
            '</p>' +
            '<p class="show-weixin-pic">欢迎关注我的微信公众号：zhyocean1314</p>' +
            '<p><i class="weiXinQuoteRight am-icon-quote-right "></i></p>' +
            '</div>' +
            '<div>' +
            '<ul class="post-copyright">' +
            '<li><strong>本文作者：</strong><span id="authorFooter">' + data.originalAuthor + '</span></li>' +
            '<li><strong>原文链接：</strong><span id="urlFooter"><a href="' + data.articleUrl + '">' + data.articleUrl + '</a></span></li>' +
            '<li><strong>版权声明：</strong> 本博客所有文章除特别声明外，均采用<span id="copyRightFooter"><a href="https://creativecommons.org/licenses/by/3.0/cn/" target="_blank"> CC BY 3.0 CN协议</a></span>进行许可。转载请署名作者且注明文章出处。</li>' +
            '</ul>' +
            '</div>' +
            '<div class="article-tags">' +

            '</div>' +
            '<hr>' +
            '<div class="two-article">' +
            '<span class="article-last">' +

            '</span>' +
            '<span class="article-next">' +
            '</span>' +
            '</div>');
        $('.zhy-article-footer').append(articleFooter);
        var tags = $('<div class="tags"></div>');
        for(var i=0;i<data.articleTags.length;i++){
            var tag = $('<i class="am-icon-tag"></i><a class="articleTagColor" href="/tags?tag=' + data.articleTags[i] + '"> ' + data.articleTags[i] + '</a>');
            tags.append(tag);
        }
        $('.article-tags').append(tags);
        if(data.lastStatus == "200"){
            var articleLast200 = $('<i class="am-icon-angle-left am-icon-sm"></i>&nbsp;&nbsp;<a class="lastAndNext" href="' + data.lastArticleUrl +'">' + data.lastArticleTitle + '</a>');
            $('.article-last').append(articleLast200);
        } else {
            var articleLast500 = $('<i class="am-icon-angle-left am-icon-sm"></i>&nbsp;&nbsp;<a  class="lastAndNext">' + data.lastInfo + '</a>');
            $('.article-last').append(articleLast500);
        }
        if(data.nextStatus == "200"){
            var articleNext200 = $('<a class="lastAndNext" href="' + data.nextArticleUrl +'">' + data.nextArticleTitle + '</a>' + '&nbsp;&nbsp;<i class="am-icon-angle-right am-icon-sm"></i>');
            $('.article-next').append(articleNext200);
        } else {
            var articleNext500 = $('<a  class="lastAndNext">' + data.nextInfo + '</a>' + '&nbsp;&nbsp;<i class="am-icon-angle-right am-icon-sm"></i>');
            $('.article-next').append(articleNext500);
        }
        var likeBtn = $('<div class="likeBtn am-btn am-btn-danger">' +
            '<div class="likeHeart">' +
            '<i class="am-icon-heart-o">&nbsp;&nbsp;喜欢</i>' +
            '</div>' +
            '<div class="likesNum">' +
            '<span> ' + data.likes + '</span>' +
            '</div>' +
            '</div>');
        $('.other').append(likeBtn);
        if(data.isLiked == 1){
            $('.likeBtn').css({
                "background-color": "#EA6F5A",
                "color":"white"
            });
            $('.likesNum').css({
                "border-left": "1px solid white"
            });
            $('.likeHeart').find('i').removeClass("am-icon-heart-o");
            $('.likeHeart').find('i').addClass("am-icon-heart");
        }
        $('.other').append($('<div class="social-share" data-initialized="true" data-url="https://www.zhyocean.cn/article/' + data.articleId  + '"  data-title="' + data.articleTitle + '">' +
            '<a href="#" class="social-share-icon icon-qq" data-am-popover="{content: \'分享至QQ好友\', trigger: \'hover focus\'}"></a>' +
            '<a href="#" class="social-share-icon icon-qzone" data-am-popover="{content: \'分享至QQ空间\', trigger: \'hover focus\'}"></a>' +
            '<a href="#" class="social-share-icon icon-wechat"></a>' +
            '<a href="#" class="social-share-icon icon-weibo" data-am-popover="{content: \'分享至微博\', trigger: \'hover focus\'}"></a>' +
            '</div>'))

        //选中所有需放大的图片加上data-src属性
        $("#wordsView img").each(function(index){
            if(!$(this).hasClass("emoji")){
                var a=$(this).attr('src');
                $(this).attr("data-src",a);

                $(this).addClass("enlargePicture");
            }
        });
        //放大图片框架
        lightGallery(document.getElementById('wordsView'));
    }

    //填充文章评论和回复
    function putInComment(data) {
        $('#comment').val('');
        var comment = $('.comment');
        var commentBottom = $('.comment-bottom');
        commentBottom.html('');
        if(data.length == 0){
            var comments = $('<div class="comments">' +
                '<span class="noComment" style="color: black">还没有评论，快来抢沙发吧！</span>' +
                '</div>');
            commentBottom.append(comments);
            comment.append(commentBottom);
        } else {
            var articleComment = $('<div class="article-comment"></div>');
            var articleCommentTop = $('<div class="article-comment-top">' +
                '<span class="article-comment-word">评论</span>' +
                '<div class="article-comment-line"></div>' +
                '</div>');
            var newComment = $('<div class="new-comment">' +
                '<i class="all-comment am-icon-ellipsis-v"></i>全部评论（<span class="commentNum">' + '' + '</span>）' +
                '</div>');
            articleComment.append(articleCommentTop);
            articleComment.append(newComment);
            var allComments = $('<div class="all-comments"></div>');
            $.each(data,function (index,obj) {
                var visitorReplies = $('<div class="visitorReplies"></div>');
                $.each(obj['replies'],function (index1,obj1) {
                    var visitorReply = $('<div id="p' + obj1['id'] + '" class="visitorReply"></div>');
                    var visitorReplyWords = $('<div class="visitorReplyWords">' +
                        '<a class="answerer">' + obj1['answerer'] + '</a>： <a class="respondent">@' + obj1['respondent'] + ' </a>' + obj1['commentContent'] +
                        '</div>');
                    var visitorReplyTime = $('<div class="visitorReplyTime">' +
                        '<span class="visitorReplyTimeTime">' + obj1['commentDate'] + '</span>' +
                        '<a>' +
                        '<i class="replyReply am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                        '</a>' +
                        '</div>');
                    visitorReply.append(visitorReplyWords);
                    visitorReply.append(visitorReplyTime);
                    visitorReply.append($('<hr data-am-widget="divider" style="" class="am-divider am-divider-dashed"/>'));
                    visitorReplies.append(visitorReply);
                });
                var subCommentList = $('<div class="sub-comment-list"></div>');
                var moreComment = $('<div class="more-comment">' +
                    '<a>' +
                    '<i class="moreComment am-icon-edit"> 添加新评论</i>' +
                    '</a>' +
                    '</div>');
                subCommentList.append(visitorReplies);
                subCommentList.append(moreComment);
                var subComment = $('<div class="sub-comment"></div>');
                if(obj['replies'].length != 0){
                    subComment.append(subCommentList);
                }
                subComment.append($('<div class="reply-sub-comment-list am-animation-slide-bottom">' +
                    '<div class="replyWord">' +
                    '<div class="replyWordBtn">' +
                    '<textarea class="replyWordTextarea" placeholder="写下你的评论..."></textarea>' +
                    '<button type="button" class="sendReplyWordBtn am-btn am-btn-success">发送</button>' +
                    '<button type="button" class="quitReplyWordBtn am-btn">取消</button>' +
                    '</div>' +
                    '</div>' +
                    '</div>'));

                var amG = $('<div class="am-g"></div>');
                amG.append($('<div class="visitorCommentImg am-u-sm-2 am-u-lg-1">' +
                    '<img src="' + obj['avatarImgUrl'] + '">' +
                    '</div>'));
                var amUSm10 = $('<div class="am-u-sm-10 am-u-lg-11"></div>');
                var visitorInfo = $('<div class="visitorInfo">' +
                    '<span class="visitorFloor">#' + (data.length-index) + '楼</span>' +
                    '<span class="visitorName">' +
                    obj['answerer'] +
                    '</span>' +
                    '<span class="visitorPublishDate">' +
                    obj['commentDate'] +
                    '</span>' +
                    '</div>');
                var visitorSay = $('<div class="visitorSay">' +
                    obj['commentContent'] +
                    '</div>');
                var toolGroup1 = $('<div class="tool-group">' +
                    '<a>' +
                    '<i class="like am-icon-thumbs-o-up">&nbsp;&nbsp;<span>' + obj['likes'] + '</span>人赞</i>' +
                    '</a>' +
                    '<a>' +
                    '<i class="reply am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                    '</a>' +
                    '</div>');
                var toolGroup2 = $('<div class="tool-group">' +
                    '<a>' +
                    '<i class="like am-icon-thumbs-up text-success">&nbsp;&nbsp;<span>' + obj['likes'] + '</span>人赞</i>' +
                    '</a>' +
                    '<a>' +
                    '<i class="reply am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                    '</a>' +
                    '</div>');
                amUSm10.append(visitorInfo);
                amUSm10.append(visitorSay);
                if(obj['isLiked'] == 1){
                    amUSm10.append(toolGroup2);
                } else {
                    amUSm10.append(toolGroup1);
                }
                amUSm10.append(subComment);
                amG.append(amUSm10);
                var visitorComment = $('<div class="visitorComment" id="p' + obj['id'] +  '"></div>');
                visitorComment.append(amG);
                visitorComment.append($('<hr>'));
                allComments.append(visitorComment);
            });
            articleComment.append(allComments);
            commentBottom.append(articleComment);
            comment.append(commentBottom);
            //添加评论数
            $('.commentNum').html(data.length);
        }

        var reply = $('.reply');
        var quitReplyWordBtn = $('.quitReplyWordBtn');
        var moreComment = $('.moreComment');
        var replyReply = $('.replyReply');

        var respondent;
        //点击回复显示评论框
        reply.click(function () {
            var $this = $(this);
            $.ajax({
                type:'get',
                url:'/isLogin',
                dataType:'json',
                async:false,
                data:{
                },
                success:function (data) {
                    if(data['status'] == 101){
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    } else {
                        $this.parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).val('');
                        $this.parent().parent().parent().find($('.reply-sub-comment-list')).css("display","block");
                        $this.parent().parent().parent().find($('.reply-sub-comment-list')).find($('.replyWordTextarea')).focus();

                        respondent = $this.parent().parent().prev().prev().find('.visitorName').html();
                    }
                },
                error:function () {
                }
            });

        });

        //添加新评论显示评论框
        moreComment.click(function () {
            var $this = $(this);
            $.ajax({
                type:'get',
                url:'/isLogin',
                dataType:'json',
                async:false,
                data:{
                },
                success:function (data) {
                    if(data['status'] == 101){
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    }else {
                        $this.parent().parent().parent().next().find($('.replyWordTextarea')).val('');
                        $this.parent().parent().parent().next().css("display","block");
                        $this.parent().parent().parent().next().find($('.replyWordTextarea')).focus();

                        respondent = $this.parent().parent().parent().parent().parent().find('.visitorInfo').find('.visitorName').html();
                    }
                },
                error:function () {
                }
            });

        });

        //评论中的回复的回复按钮显示评论框
        replyReply.click(function () {
            var $this = $(this);
            $.ajax({
                type:'get',
                url:'/isLogin',
                dataType:'json',
                async:false,
                data:{
                },
                success:function (data) {
                    if (data['status'] == 101) {
                        $.get("/toLogin", function (data, status, xhr) {
                            window.location.replace("/login");
                        });
                    } else {
                        respondent = $this.parent().parent().prev().find($('.answerer')).html();
                        $this.parent().parent().parent().parent().parent().next().css("display","block");
                        $this.parent().parent().parent().parent().parent().next().find($('.replyWordTextarea')).val('@' + respondent + ' ');
                        $this.parent().parent().parent().parent().parent().next().find($('.replyWordTextarea')).focus();
                    }
                },
                error:function () {
                }
            });

        });

        //点击取消隐藏评论框
        quitReplyWordBtn.click(function () {
            $(this).parent().parent().find($('.replyWordTextarea')).val('');
            $(this).parent().parent().parent().css("display","none");
        });

        //发送评论中的回复
        $('.sendReplyWordBtn').click(function () {
            var $this = $(this);
            var replyContent = $this.parent().parent().find($('.replyWordTextarea')).val();
            var pId = $this.parent().parent().parent().parent().parent().parent().parent().attr("id");
            if(replyContent == ""){
                alert("我没看清你要回复啥吖！");
            } else {
                $.ajax({
                    type: 'POST',
                    url: '/publishReply',
                    dataType: 'json',
                    data: {
                        commentContent:replyContent,
                        articleId:articleId,
                        parentId:pId,
                        respondent:respondent
                    },
                    success: function (data) {
                        if(data['status'] == 101){
                            $.get("/toLogin",function(data,status,xhr){
                                window.location.replace("/login");
                            });
                        } else if (data['status'] == 103){
                            dangerNotice(data['message'] + " 发表评论失败")
                        } else if (data['status'] == 801){
                            alert("内容不能为空！");
                        } else {
                            var sub_comment = $this.parent().parent().parent().parent();
                            var visitorReply = $('<div id=p' + data['data']['id'] + ' class="visitorReply"></div>');
                            var visitorReplyWords = $('<div class="visitorReplyWords">' +
                                '<a class="answerer">' + data['data']['answerer'] + '</a>： <a class="respondent">@' + data['data']['respondent'] + ' </a>' + data['data']['commentContent'] +  '' +
                                '</div>');
                            var visitorReplyTime = $('<div class="visitorReplyTime">' +
                                '<span class="visitorReplyTimeTime">' + data['data']['commentDate'] + '</span>' +
                                '<a>' +
                                '<i class="replyReply am-icon-comment-o">&nbsp;&nbsp;回复</i>' +
                                '</a>' +
                                '</div>');
                            visitorReply.append(visitorReplyWords);
                            visitorReply.append(visitorReplyTime);
                            visitorReply.append('<hr data-am-widget="divider" style="" class="am-divider am-divider-dashed" />');

                            if(sub_comment.find('.sub-comment-list').length > 0){
                                sub_comment.find('.visitorReplies').append(visitorReply);
                            }else {
                                var visitorReplies = $('<div class="visitorReplies"></div>');
                                var sub_comment_list = $('<div class="sub-comment-list"></div>');
                                visitorReplies.append(visitorReply);
                                sub_comment_list.append(visitorReplies);
                                sub_comment_list.append($('<div class="more-comment">' +
                                    ' <a>' +
                                    '<i class="moreComment am-icon-edit"> 添加新评论</i>' +
                                    '</a>' +
                                    '</div>'));
                                sub_comment.prepend(sub_comment_list);
                            }

                            //给新加入的评论中的回复和下面的添加新评论添加点击事件
                            $this.parent().parent().parent().parent().find('.visitorReplies>div:last-child').find('.replyReply ').click(function () {
                                respondent = $(this).parent().parent().prev().find($('.answerer')).html();
                                $(this).parent().parent().parent().parent().parent().next().css("display","block");
                                $(this).parent().parent().parent().parent().parent().next().find($('.replyWordTextarea')).val('@' + respondent + ' ');
                                $(this).parent().parent().parent().parent().parent().next().find($('.replyWordTextarea')).focus();
                            });
                            $this.parent().parent().parent().parent().find('.sub-comment-list').find('.more-comment').find('.moreComment').click(function () {
                                $(this).parent().parent().parent().next().find($('.replyWordTextarea')).val('');
                                $(this).parent().parent().parent().next().css("display","block");

                                respondent = $(this).parent().parent().parent().parent().parent().find('.visitorInfo').find('.visitorName').html();
                            });
                            $this.parent().find($('.replyWordTextarea')).val('');
                            $this.parent().parent().parent().css("display","none");
                        }
                    },
                    error: function () {
                        alert("回复失败！");
                    }
                });
            }

        });

        //点击评论中的点赞
        $('.like').click(function () {
            var $this = $(this);
            var respondentId = $this.parent().parent().parent().parent().parent().attr("id");
            $.ajax({
                type:'get',
                url:'/addCommentLike',
                dataType:'json',
                data:{
                    articleId : articleId,
                    respondentId : respondentId
                },
                success:function (data) {
                    if(data['status'] == 101){
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    } else if (data['status'] == 103){
                        dangerNotice(data['message'] + " 点赞失败")
                    } else if(data['status'] == 802){
                        //已经点过赞了，啥都不干
                    } else {
                        $this.find('span').html(data['data']);
                        $.tipsBox({
                            obj: $this,
                            str: "+1",
                            callback: function () {
                            }
                        });
                        niceIn($this);
                        $this.removeClass("fa-thumbs-o-up");
                        $this.addClass("fa-thumbs-up");
                        $this.addClass("text-danger");
                    }
                },
                error:function () {
                    alert("点赞失败！")
                }
            });
        });
    }

    $.ajax({
        type: 'HEAD', // 获取头信息，type=HEAD即可
        url : window.location.href,
        async:false,
        success:function (data, status, xhr) {
            articleId = xhr.getResponseHeader("articleId");
        }
    });

    //通过文章id请求文章信息
    $.ajax({
        type:'post',
        url:'/getArticleByArticleId',
        dataType:'json',
        async:false,
        data:{
            articleId : articleId,
        },
        success:function (data) {
            if(data['status'] == 0){
                putInArticle(data['data']);

                //通过文章id和原作者请求评论信息
                $.ajax({
                    type:'post',
                    url:'/getAllComment',
                    dataType:'json',
                    data:{
                        articleId : articleId,
                    },
                    success:function (data) {
                        if(data['status'] == 103){
                            dangerNotice(data['message'] + " 获得评论信息失败");
                        } else {
                            putInComment(data['data']);
                        }
                    },
                    error:function () {
                    }
                });
            } else if (data['status'] == 103){
                dangerNotice(data['message'] + " 获得文章失败");
            } else {
                $('.content').html('');
                var error = $('<div class="article"><div class="zhy-article-top"><div class="error">' +
                    '<img src="https://zhy-myblog.oss-cn-shenzhen.aliyuncs.com/static/img/register_success.jpg">' +
                    '<p>没有找到这篇文章哦</p>' +
                    '<p>可能不小心被博主手残删掉了吧</p>' +
                    '<div class="row">' +
                    '<a href="/">返回首页</a>' +
                    '</div>' +
                    '</div></div></div>');
                $('.content').append(error);
            }
        },
        error:function () {

        }
    });

    // 文章点赞
    (function ($) {
        $.extend({
            tipsBox: function (options) {
                options = $.extend({
                    obj: null,  //jq对象，要在那个html标签上显示
                    str: "+1",  //字符串，要显示的内容;也可以传一段html，如: "<b style='font-family:Microsoft YaHei;'>+1</b>"
                    startSize: "12px",  //动画开始的文字大小
                    endSize: "30px",    //动画结束的文字大小
                    interval: 600,  //动画时间间隔
                    color: "red",    //文字颜色
                    callback: function () {
                    }    //回调函数
                }, options);
                $("body").append("<span class='num'>" + options.str + "</span>");
                var box = $(".num");
                var left = options.obj.offset().left + options.obj.width() / 2;
                var top = options.obj.offset().top - options.obj.height();
                box.css({
                    "position": "absolute",
                    "left": left + "px",
                    "top": top + "px",
                    "z-index": 9999,
                    "font-size": options.startSize,
                    "line-height": options.endSize,
                    "color": options.color
                });
                box.animate({
                    "font-size": options.endSize,
                    "opacity": "0",
                    "top": top - parseInt(options.endSize) + "px"
                }, options.interval, function () {
                    box.remove();
                    options.callback();
                });
            }
        });
    })(jQuery);

    //点赞喜欢效果
    function niceIn(prop) {
        prop.find('i').addClass('niceIn');
        setTimeout(function () {
            prop.find('i').removeClass('niceIn');
        }, 1000);
    }


    //喜欢按钮
    $(".likeBtn").click(function () {
        var $this = $(this);
        $.ajax({
            type:'get',
            url:'/addArticleLike',
            dataType:'json',
            data:{
                articleId : articleId
            },
            success:function (data) {
                if(data['status'] == 101){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
                } else if(data['status'] == 103){
                    dangerNotice(data['message'] + " 点赞失败");
                } else if(data['status'] == 202){
                    //文章已经点过赞了，啥都不干
                } else {
                    $('.likesNum').find('span').html(data['data']);
                    $('.likeBtn').css({
                        "background-color": "#EA6F5A",
                        "color":"white"
                    });
                    $('.likesNum').css({
                        "border-left": "1px solid white"
                    });
                    $('.likeHeart').find('i').removeClass("am-icon-heart-o");
                    $('.likeHeart').find('i').addClass("am-icon-heart");
                    $.tipsBox({
                        obj: $this,
                        str: "+1个喜欢"
                    });
                    niceIn($this);
                }
            },
            error:function () {
                alert("点赞失败！")
            }
        });
    });
