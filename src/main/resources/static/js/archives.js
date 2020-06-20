
    var archive="";
    //填充归档文章
    function putInArchivesArticleInfo(data){
        var categoryTimeline = $('.categoryTimeline');
        categoryTimeline.empty();
        var timeline = $('<div class="timeline timeline-wrap"></div>');
        timeline.append('<div class="timeline-row">' +
            '<span class="node" style="-webkit-box-sizing: content-box;-moz-box-sizing: content-box;box-sizing: content-box;">' +
            '<i class="am-icon-calendar"></i>' +
            '</span>' +
            '<h1 class="title  am-animation-slide-top"># 很好!&nbsp;目前总计<span class="archivesNum">' + data['articleNum'] + '</span>篇日志，继续努力。</h1>' +
            '</div>');
        var strArray = new Array();
        $.each(data['result'], function (index, obj) {
            var year = obj['publishDate'].substring(0, 4);
            var month = obj['publishDate'].substring(5, 7);
            if(data['showMonth'] == "hide"){
                if($.inArray(year, strArray) == -1){
                    strArray.push(year);
                    timeline.append($('<div class="timeline-row-major">' +
                        '<span class="node am-animation-slide-top am-animation-delay-1"></span>' +
                        '<div class="nodeYear am-animation-slide-top am-animation-delay-1">' + year + '年</div>' +
                        '</div>'));
                }
            } else {
                if($.inArray(year, strArray) == -1){
                    strArray.push(year);
                    timeline.append($('<div class="timeline-row-major">' +
                        '<span class="node am-animation-slide-top am-animation-delay-1"></span>' +
                        '<div class="nodeYear am-animation-slide-top am-animation-delay-1">' + year + '年&nbsp;' + month + '月</div>' +
                        '</div>'));
                }
            }
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
            var amCommentBdTags = $('<i class="am-comment-bd-tags am-icon-tag"></i>');
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
            timeline.append(timelineRowMajor);
        });
        categoryTimeline.append(timeline);
        categoryTimeline.append($('<div class="my-row" id="page-father">' +
            '<div id="pagination">' +
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

    $.ajax({
        type: 'HEAD', // 获取头信息，type=HEAD即可
        url : window.location.href,
        async:false,
        success:function (data, status, xhr) {
            archive = xhr.getResponseHeader("archive");
        }
    });

    function ajaxFirst(currentPage,archive1) {
        $.ajax({
            type:'GET',
            url:'/getArchiveArticle',
            dataType:'json',
            data:{
                archive:archive1,
                rows:"10",
                pageNum:currentPage
            },
            success:function (data) {
                if(data['status'] == 103){
                    dangerNotice(data['message'] + " 获得归档文章失败")
                } else {
                    putInArchivesArticleInfo(data['data']);
                    scrollTo(0,0);//回到顶部

                    //分页
                    $("#pagination").paging({
                        rows:data['data']['pageInfo']['pageSize'],//每页显示条数
                        pageNum:data['data']['pageInfo']['pageNum'],//当前所在页码
                        pages:data['data']['pageInfo']['pages'],//总页数
                        total:data['data']['pageInfo']['total'],//总记录数
                        callback:function(currentPage){
                            ajaxFirst(currentPage, archive1);
                        }
                    });
                }
            },
            error:function () {
                alert("获取归档文章失败");
            }
        });
    }
    ajaxFirst(1,archive);

    //获得归档日期以及该归档日期下的文章数量
    $.ajax({
        type:'GET',
        url:'/findArchiveNameAndArticleNum',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 103){
                dangerNotice(data['message'] + " 获得归档信息失败")
            } else {
                var categories = $('.categories');
                categories.empty();
                categories.append($('<div class="categories-title">' +
                    '<h3 style="font-size: 20px">Archives</h3>' +
                    '</div>'));
                var categoriesComment = $('<div class="categories-comment am-animation-slide-top"></div>');
                $.each(data['data']['result'], function (index, obj) {
                    categoriesComment.append($('<div class="category">' +
                        '<span>' +
                        '<a class="categoryName">' + obj['archiveName'] + '</a>' +
                        '<span class="categoryNum">(' + obj['archiveArticleNum'] + ')</span>' +
                        '</span>' +
                        '</div>'));
                });
                categories.append(categoriesComment);
                $('.categoryName').click(function () {
                    var $this = $(this);
                    var archiveName = $this.html();
                    ajaxFirst(1, archiveName)
                })
            }

        },
        error:function () {
            alert("获取归档信息失败");
        }
    });