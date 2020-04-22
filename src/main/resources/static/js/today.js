
    //上传图片
    function readAsDataURL(){
        var file = document.getElementById("picture").files;
        var fileLength = file.length;
        if(fileLength > 9){
            alert("最多只能选9张噢")
            $("#picture").replaceWith('<input id="picture" name="picture" type="file" multiple="multiple" onchange="readAsDataURL()" accept=".gif,.jpg,.jpeg,.png"><a class="am-icon-heart am-icon-sm"> 图片</a>');
        }
    }

    //选择心情
    $('.moods i').click(function () {
        $(this).css("color","red");
        if($('.moods').hasClass('happy')){
            $('#happy').css("color","#8c8c8c");
            $('.moods').removeClass('happy');
        } else if ($('.moods').hasClass('just')){
            $('#just').css("color","#8c8c8c");
            $('.moods').removeClass('just');
        } else if($('.moods').hasClass('terrible')){
            $('#terrible').css("color","#8c8c8c");
            $('.moods').removeClass('terrible');
        }
        var moodStr = $(this).eq(0).attr('class');
        var thisMood = moodStr.substring(0, moodStr.indexOf(" "));
        $('.moods').addClass(thisMood);
    });

    var last = 0;
    var yearArray = new Array();
    var monthArray = new Array();
    function putInAllTodayInfo(data, pageInfo) {
        var says = $('.says');
        $.each(data, function (index, obj) {
            var date = new Date(obj['publishDate']);
            var year = date.getFullYear();
            var month = date.getMonth() + 1;
            var day = date.getDate();
            var hour = date.getHours() >= 10 ? date.getHours() : '0' + date.getHours();
            var minute = date.getMinutes() >= 10 ? date.getMinutes() : '0' + date.getMinutes();
            if($.inArray(year, yearArray) == -1){
                yearArray.push(year);
                monthArray.splice(0);
                says.append('<div class="time">' +
                    '<h1>' + year + '</h1>' +
                    '<i class="yearHeart am-icon-heart am-icon-md"></i>' +
                    '</div>')
            }
            if($.inArray(month, monthArray) == -1){
                monthArray.push(month);
                says.append('<div class="timeline-wrap">' +
                    '<div class="timeline-row-major">' +
                    '<span class="node"></span>' +
                    '<div class="month">' + month + '月</div>' +
                    '</div>' +
                    '</div>');
            }
            var timelineWrap = $('<div class="timeline-wrap"></div>');
            var timelineRowMajor = $('<div class="timeline-row-major"></div>');
            timelineRowMajor.append('<span class="node"></span>')
            var say = $('<div class="say am-comment-main"></div>');
            var todayTime = $('<div class="todayTime"></div>');
            todayTime.append('<i class="todayTimeNow am-icon-calendar"></i> ' + year+"."+month+"."+day+" "+hour+":"+minute);
            if(obj['mood'] === 'happy'){
                todayTime.append('<i class="todayMood am-icon-smile-o" style="color: #ff8f52;"></i><span class="todayMoodWord"> 开心</span>');
            } else if (obj['mood'] === 'just'){
                todayTime.append('<i class="todayMood am-icon-meh-o" style="color: #666666"></i><span class="todayMoodWord"> 一般</span>');
            } else {
                todayTime.append('<i class="todayMood am-icon-frown-o" style="color: red"></i><span class="todayMoodWord"> 伤心</span>');
            }
            say.append(todayTime);
            say.append('<div class="missYou">' + obj['content'] + '</div>');
            if(typeof(obj['picsUrl']) !== "undefined" && obj['picsUrl'].length > 0){
                var todayPic = $('<div class="todayPic"></div>');
                for(var i=1;i<=obj['picsUrl'].length;i++){
                    todayPic.append('<p class="todayPicP" data-src="' + obj['picsUrl'][i-1] + '"><img src="' + obj['picsUrl'][i-1] + '"></p>');
                }
            }
            say.append(todayPic);
            timelineRowMajor.append(say);
            timelineWrap.append(timelineRowMajor);
            says.append(timelineWrap);
        });
        $('.saysButton').append('<button type="button"  id="moreSaysButton" class="lookMore am-btn am-btn-default am-radius">相不相信还有更多日常</button>');
        if(pageInfo['isLastPage'] == true){
            last++;
        }

        //放大图片
        $(document).ready(function() {
            $(".todayPic").lightGallery({
                thumbnail   : true,
                controls    : true,
                mode   : 'slide',
                loop:false,  //循环播放图片
                auto:false,
                escKey           : true,
                pause:4000,
                hideControlOnEnd : false,
                lang : { allPhotos: 'All photos' },
                counter     : true
            });
        });

    }

    
    //提交每天说的话
    var iSay = $('#iSay');
    var moods = $('.moods');
    $('.sendBtn').click(function (event) {
        //阻止表单submit提交
        event.preventDefault();
        var moodStr = moods.attr('class');
        var iSayContent = iSay.val();
        if(iSayContent.length === 0){
            alert("每天就不想说点什么吗！！！")
        } else {
            if(moods.hasClass('happy') || moods.hasClass('just')  || moods.hasClass('terrible')){
                var formData = new FormData(uploadForm);
                formData.append("mood",moodStr.substring(moodStr.indexOf(" ")+1));
                $.ajax({
                    type: 'post',
                    url: '/commitTodayWords',
                    dataType: 'json',
                    data: formData,
                    async:false,
                    processData: false,
                    contentType: false,
                    success: function (data) {
                        if(data['status'] == 101){
                            $.get("/toLogin",function(data,status,xhr){
                                window.location.replace("/login");
                            });
                        } else if(data['status'] == 103){
                            alert(data['message'] + " 发表失败");
                        } else {
                            alert("发布成功");
                            $("#picture").replaceWith('<input id="picture" name="picture" type="file" multiple="multiple" onchange="readAsDataURL()" accept=".gif,.jpg,.jpeg,.png"><a class="am-icon-heart am-icon-sm"> 图片</a>');
                            window.location.reload()
                        }
                    },
                    error: function () {
                        alert("提交失败");
                    }
                });
            } else {
                alert("选择个你今天的心情吧")
            }
        }

    });

    function getTodayInfo(currentPage) {
        $.ajax({
            type: 'post',
            url: '/getTodayInfo',
            dataType: 'json',
            data: {
                rows:"10",
                pageNum:currentPage
            },
            success: function (data) {
                if (data['status'] == 103){
                    alert(data['message'] + " 获得内容失败");
                    return;
                }
                putInAllTodayInfo(data['data']['result'], data['data']['pageInfo']);

                $('#moreSaysButton').click(function () {
                    $('.saysButton').empty();
                    if(last > 0){
                        $('.saysButton').append('<div class="buttonLine">' +
                            '<hr data-am-widget="divider" class="am-divider am-divider-default" />' +
                            '<span>别点啦，俺老张也是有底线的人～(>_<)～～</span>' +
                            '</div>');
                    } else {
                        getTodayInfo(++currentPage);
                    }
                })
            },
            error: function () {
            }
        });
    }

    getTodayInfo(1);