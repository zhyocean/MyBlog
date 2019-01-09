
$('.userList .clickLi').click(function () {
    var flag = $(this).attr('class').substring(8);
    $('#personalDate,#basicSetting,#leaveMessage,#privateWord').css("display","none");
    $("#" + flag).css("display","block");
});

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
                if(data['status'] == 403){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
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

$('.basicSetting').click(function () {
    $('#phone').val("");
    $('#authCode').val("");
    $('#password').val("");
    $('#surePassword').val("");
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

//更改头像
function imgChange(e) {
    var dom =$("input[id^='imgTest']")[0];
    var reader = new FileReader();
    reader.onload = (function (file) {
        return function (e) {
            $.ajax({
                type:'POST',
                url:'/uploadHead',
                dataType:'json',
                data:{
                    img:this.result
                },
                success:function (data) {
                    if(data['status'] == 403){
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    } else {
                        if(data['status'] == 200){
                            $('#headPortrait').attr("src",data['avatarImgUrl']);
                            successNotice("更改头像成功");
                        } else {
                            dangerNotice("更改头像失败")
                        }
                    }

                },
                error:function () {
                }
            });
        };
    })(e.target.files[0]);
    reader.readAsDataURL(e.target.files[0]);
}

//获得个人信息
function getUserPersonalInfo() {
    $.ajax({
        type:'get',
        url:'/getUserPersonalInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/toLogin",function(data,status,xhr){
                    window.location.replace("/login");
                });
            } else {
                $('#username').attr("value",data['result']['username']);
                var personalPhone = data['result']['phone'];
                $('#personalPhone').html(personalPhone.substring(0,3) + "****" + personalPhone.substring(7));
                $('#trueName').attr("value",data['result']['trueName']);
                $('#birthday').attr("value",data['result']['birthday']);
                var gender = data['result']['gender'];
                if(gender == "male"){
                    $('.genderTable input').eq(0).attr("checked","checked");
                } else {
                    $('.genderTable input').eq(1).attr("checked","checked");
                }
                $('#email').attr("value",data['result']['email']);
                $('#personalBrief').val(data['result']['personalBrief']);
            }
        },
        error:function () {
        }
    });
}

//获得头像url
function showHeadPortrait() {
    $.ajax({
        type:'get',
        url:'/getHeadPortraitUrl',
        dataType:'json',
        data:{
        },
        success:function (data) {
            $('#headPortrait').attr("src",data['avatarImgUrl']);
        },
        error:function () {
        }
    });
}

//保存个人资料
var savePersonalDateBtn = $('#savePersonalDateBtn');
var username = $('#username');
var trueName = $('#trueName');
var birthday = $('#birthday');
var gender = $('.genderTable input');
var email = $('#email');
var personalBrief = $('#personalBrief');
savePersonalDateBtn.click(function () {
    var usernameValue = username.val();
    var genderValue = "male";
    if(usernameValue.length === 0){
        dangerNotice("昵称不能为空");
    } else if(!gender[0].checked && !gender[1].checked){
        dangerNotice("性别不能为空");
    } else {
        if(gender[0].checked){
            genderValue = "male";
        } else {
            genderValue = "female";
        }
        $.ajax({
            type:'post',
            url:'/savePersonalDate',
            dataType:'json',
            data:{
                username:username.val(),
                trueName:trueName.val(),
                birthday:birthday.val(),
                gender:genderValue,
                email:email.val(),
                personalBrief:personalBrief.val()
            },
            success:function (data) {
                if(data['status'] == 403){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
                } else {
                    if(data['status'] == 200){
                        alert("更改个人信息成功,重新登录后生效");
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    } else if (data['status'] == 500){
                        dangerNotice("该昵称已被占用");
                    } else if (data['status'] == 201){
                        successNotice("更改个人信息成功");
                    } else {
                        dangerNotice("更改个人信息失败");
                    }
                }
            },
            error:function () {
            }
        });
    }
});

var phone = $('#phone');
var authCode = $('#authCode');
var password = $('#password');
var surePassword = $('#surePassword');

phone.blur(function () {
    var pattren = /^1[345789]\d{9}$/;
    var phoneValue = phone.val();
    if(pattren.test(phoneValue)){
        phone.removeClass("wrong");
        phone.addClass("right");
    } else {
        phone.removeClass("right");
        phone.addClass("wrong");
    }
});
phone.focus(function () {
    $('.notice').css("display","none");
});

// 定义发送时间间隔(s)
var my_interval;
my_interval = 60;
var timeLeft = my_interval;
//重新发送计时函数
var timeCount = function() {
    window.setTimeout(function() {
        if(timeLeft > 0) {
            timeLeft -= 1;
            $('#authCodeBtn').html(timeLeft + "秒重新发送");
            timeCount();
        } else {
            $('#authCodeBtn').html("重新发送");
            timeLeft=60;
            $("#authCodeBtn").attr('disabled',false);
        }
    }, 1000);
};
//发送短信验证码
$('#authCodeBtn').click(function () {
    $('.notice').css("display","none");
    $('#authCodeBtn').attr('disabled',true);
    var phoneLen = phone.val().length;
    if(phoneLen == 0){
        dangerNotice("手机号不能为空");
        $('#authCodeBtn').attr('disabled',false);
    } else {
        if(phone.hasClass("right")){
            $.ajax({
                type:'post',
                url:'/getCode',
                dataType:'json',
                data:{
                    phone:$('#phone').val(),
                    sign:"changePassword"
                },
                success:function (data) {
                    if(parseInt(data) == 1) {
                        successNotice("短信验证码发送成功");
                        timeCount();
                    }
                },
                error:function () {
                }
            });
        } else {
            dangerNotice("手机号不正确");
            $('#authCodeBtn').attr('disabled',false);
        }
    }

});

//修改密码
$('#changePasswordBtn').click(function () {
    $('.notice').css("display","none");
    if(phone.val().length === 0){
        dangerNotice("手机号不能为空");
    } else if (phone.hasClass("wrong")){
        dangerNotice("手机号不正确");
    } else if (authCode.val().length === 0){
        dangerNotice("验证码不能为空");
    } else if (password.val().length === 0){
        dangerNotice("新密码不能为空");
    } else if (surePassword.val().length === 0){
        dangerNotice("确认密码不能为空");
    } else{
        if (password.val() !== surePassword.val()){
            dangerNotice("确认密码不正确");
        } else {
            $.ajax({
                type:'post',
                url:'/changePassword',
                dataType:'json',
                data:{
                    phone:phone.val(),
                    authCode:authCode.val(),
                    newPassword:password.val()
                },
                success:function (data) {
                    if(data == "0"){
                        dangerNotice("验证码不正确")
                    }else if (data == "2"){
                        dangerNotice("手机号不存在")
                    }else if(data == "1"){
                        successNotice("密码修改成功");
                    }
                },
                error:function () {
                    alert("修改密码失败");
                }
            })
        }
    }
});

//填充用户评论
function putInCommentInfo(date) {
    var comment = $('.comment');
    comment.empty();
    if(date['result'].length == 0){
        comment.append($('<div class="noComment">' +
            '你还没有任何评论哦' +
            '</div>'));
    } else {
        var amList = $('<ul class="am-list"></ul>');
        $.each(date['result'], function (index, obj) {
            amList.append($('<li class="am-g am-list-item-dated">' +
                '<a target="_blank" href="/findArticle?articleId=' + obj['articleId'] + '&originalAuthor=' + obj['originalAuthor'] + '" style="padding: 5px 0 2px 0" class="leaveMessageTitle am-list-item-hd">' + obj['articleTitle'] + '</a>' +
                '<span class="am-list-date" style="color: #a7baaa">' + obj['commentDate'] + '</span>' +
                '<div class="leaveMessageContent">' +
                obj['answerer'] + '：' +obj['commentContent'] +
                '</div>' +
                '<span class="reply"><span class="replyNum">' + obj['replyNum'] + '</span>个回复</span>' +
                '</li>'))
        });
        var amListNewsBd = $('<div class="am-list-news-bd"></div>');
        amListNewsBd.append(amList);
        amListNewsBd.append($('<div class="my-row" id="page-father">' +
            '<div id="commentPagination">' +
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
        comment.append(amListNewsBd);
    }
}
//填充用户留言
function putInLeaveMessageInfo(data) {
    var userLeaveMessage = $('.userLeaveMessage');
    userLeaveMessage.empty();
    if(data['result'].length == 0){
        userLeaveMessage.append($('<div class="noLeaveMessage">' +
            '你还没有任何留言哦' +
            '</div>'))
    } else {
        var amList = $('<ul class="am-list"></ul>');
        $.each(data['result'], function (index, obj) {
            amList.append($('<li class="am-g am-list-item-dated">' +
                '<a target="_blank" href="/' + obj['pageName'] + '" class="leaveMessageTitle am-list-item-hd ">' + obj['answerer'] + "：" + obj['leaveMessageContent'] + '</a>' +
                '<span class="am-list-date">' + obj['leaveMessageDate'] + '</span>' +
                '</li>'));
        })
        var amListNewsBd = $('<div class="am-list-news-bd"></div>');
        amListNewsBd.append(amList);
        amListNewsBd.append($('<div class="my-row" id="page-father">' +
            '<div id="leaveMessagePagination">' +
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
        userLeaveMessage.append(amListNewsBd);
    }
}
//填充悄悄话内容
function putInPrivateWord(data) {
    var yesterdayContent = $('.yesterdayContent');
    yesterdayContent.empty();
    if(data['result'].length == 0){
        yesterdayContent.append($('<div class="noYesterday">' +
            '你的曾今我好像未曾参与耶' +
            '</div>'));
    } else {
        var says = $('<div class="says"></div>');
        $.each(data['result'], function (index, obj) {
            var say = $('<div class="say"></div>');
            var youSay = $('<div class="youSay"></div>');
            youSay.append($('<div class="youSayTime">' +
                obj['publisherDate'] +
                '</div>'));
            youSay.append($('<div class="you">' +
                '<span>' + obj['publisher'] + '</span>：' + obj['privateWord'] +
                '</div>'));
            if(obj['replyContent'] !== ""){
                youSay.append($('<div class="me">' +
                    obj['replyContent'] + '：' + obj['replier'] +
                    '</div>'));
            } else {
                youSay.append($('<div class="me">' +
                    '<span class="noReply">暂未收到回复</span>' +
                    '</div>'));
            }
            say.append(youSay);
            says.append(say);
        });
        says.append($('<div class="my-row" id="page-father">' +
            '<div id="privateWordPagination">' +
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
        yesterdayContent.append(says);
    }
}

//获得评论
function getUserComment(currentPage) {
    $.ajax({
        type:'post',
        url:'/getUserComment',
        dataType:'json',
        data:{
            rows:"10",
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/toLogin",function(data,status,xhr){
                    window.location.replace("/login");
                });
            }
            putInCommentInfo(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#commentPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getUserComment(currentPage);
                }
            });
        },
        error:function () {
            alert("获取评论留言失败");
        }
    })
}
//获得留言
function getUserLeaveMessage(currentPage) {
    $.ajax({
        type:'post',
        url:'/getUserLeaveMessage',
        dataType:'json',
        data:{
            rows:"10",
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/toLogin",function(data,status,xhr){
                    window.location.replace("/login");
                });
            }
            putInLeaveMessageInfo(data);
            scrollTo(0,0);//回到顶部

            //分页
            $("#leaveMessagePagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getUserLeaveMessage(currentPage);
                }
            });
        },
        error:function () {
            alert("获取评论留言失败");
        }
    })
}
//获得悄悄话内容
function getPrivateWordByPublisher(currentPage) {
    $.ajax({
        type:'post',
        url:'/getPrivateWordByPublisher',
        dataType:'json',
        data:{
            rows:"5",
            pageNum:currentPage
        },
        success:function (data) {
            if(data['status'] == 403){
                $.get("/toLogin",function(data,status,xhr){
                    window.location.replace("/login");
                });
            }
            putInPrivateWord(data);

            //分页
            $("#privateWordPagination").paging({
                rows:data['pageInfo']['pageSize'],//每页显示条数
                pageNum:data['pageInfo']['pageNum'],//当前所在页码
                pages:data['pageInfo']['pages'],//总页数
                total:data['pageInfo']['total'],//总记录数
                callback:function(currentPage){
                    getPrivateWordByPublisher(currentPage);
                }
            });
        },
        error:function () {
            alert("获取悄悄话失败");
        }
    })
}

// 点击评论留言
$('.leaveMessage').click(function () {
    getUserComment(1);
});

//点击评论留言中的留言
$('#userLeaveMessageClick').click(function () {
    getUserLeaveMessage(1);
});

//发布悄悄话
$('.userSayBtn').click(function () {
    var userSay = $('#userSay');
    userSay = $.trim(userSay);
    if(userSay.val().length == 0){
        dangerNotice("你还没说两句呢");
    } else {
        $.ajax({
            type:'post',
            url:'/sendPrivateWord',
            dataType:'json',
            data:{
                privateWord:userSay.val()
            },
            success:function (data) {
                if(data['status'] == 403){
                    $.get("/toLogin",function(data,status,xhr){
                        window.location.replace("/login");
                    });
                } else {
                    if(data['status'] == 200){
                        successNotice("发布悄悄话成功");
                    }
                    userSay.val("");
                    getPrivateWordByPublisher(1);
                }
            },
            error:function () {
                alert("发布悄悄话失败");
            }
        })
    }
});
//点击悄悄话
$('.privateWord').click(function () {
    getPrivateWordByPublisher(1);
});

showHeadPortrait();
getUserPersonalInfo();

