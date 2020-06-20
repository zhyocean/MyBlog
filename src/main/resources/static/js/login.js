
    //隐藏错误提示
    $('.icons').hide();
    $('.modal_icons').hide();

    //登录表单
    var phone = $("#phone");
    var phone_null = $("#phone_null");
    var phone_error = $("#phone_error");
    //输入框失去焦点
    phone.blur(function () {
        var phone_len = phone.val().length;
        if(phone_len === 0){
            phone_null.show();
        }
        if(phone_len !== 0){
            var pattren = /^1[345789]\d{9}$/;
            var phone_value = phone.val();
            if(!pattren.test(phone_value)){
                phone_error.show();
            }
        }
    });
    //输入框获得焦点
    var login_error = $('.login_error');
    phone.focus(function () {
        phone_null.hide();
        phone_error.hide();
        login_error.css("visibility","hidden");
    });
    //登录表单提交
    var loginFormSubmit = $("#loginFormSubmit");
    var password = $("#password");
    var password_null = $("#password_null");
    loginFormSubmit.click(function () {
        var password_len = password.val().length;
        var phone_len = phone.val().length;
        var phone_value = phone.val();
        var pattren1 = /^1[345789]\d{9}$/;

        var isPhone = checkPhone(phone_value);
        if(!isPhone){
            alert("手机号输入有误");
            return false;
        }
        console.log(isPhone);
        if(password_len !== 0 && phone_len !== 0 && pattren1.test(phone_value)){
            return true;
        } else {
            if(phone_len === 0){
                phone_null.show();
            }
            if(!pattren1.test(phone_value) && phone_len !== 0){
                phone_error.show();
            }
            if(password_len === 0){
                password_null.show();
            }
            return false;
        }
    });
    password.focus(function () {
        password_null.hide();
        login_error.css("visibility","hidden");
    });

    //模态框
    var msg_btn=$("#msg_btn");
    var modal_phone = $("#modal_phone");
    var modal_phone_null = $("#modal_phone_null");
    var modal_phone_error = $("#modal_phone_error");
    modal_phone.addClass("phone_error");
    //模态框中输入手机号框失去焦点
    modal_phone.blur(function () {
        var modal_phone_len = modal_phone.val().length;
        var modal_phone_value = modal_phone.val();
        if(modal_phone_len === 0){
            modal_phone_null.show();
            modal_phone.removeClass("phone_success");
            modal_phone.addClass("phone_error");
        }
        if(modal_phone_len !== 0){
            var pattren1 = /^1[345789]\d{9}$/;
            if(!pattren1.test(modal_phone_value)){
                modal_phone_error.show();
                modal_phone.removeClass("phone_success");
                modal_phone.addClass("phone_error");
            }
            if(pattren1.test(modal_phone_value)){
                modal_phone.removeClass("phone_error");
                modal_phone.addClass("phone_success");
            }
        }
    });
    //模态框中输入手机号框获得焦点
    modal_phone.focus(function () {
        modal_phone_error.hide();
        modal_phone_null.hide();
        $('.phoneNotExitSpan').hide();
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
                msg_btn.html(timeLeft + "秒重新发送");
                timeCount();
            } else {
                msg_btn.html("重新发送");
                timeLeft=60;
                $("#msg_btn").attr('disabled',false);
            }
        }, 1000);
    };
    msg_btn.click(function () {
        msg_btn.attr('disabled',true);
        if(modal_phone.hasClass("phone_success")){
            $.ajax({
                type:'post',
                url:'/getCode',
                dataType:'json',
                data:{
                    phone:modal_phone.val(),
                    sign:"changePassword"
                },
                success:function (data) {
                    if(parseInt(data['status']) == 0) {
                        alert("短信验证码发送成功");
                        timeCount();
                    } else {
                        alert("短信验证码发送异常");
                    }
                },
                error:function () {

                }
            })
        }
        else {
            alert("请正确填写手机号");
            msg_btn.attr('disabled',false);
        }
    });

    //判断确认密码与新密码的值是否相等
    var new_password = $("#new_password");
    var check_password = $("#check_password");
    var modal_check_password_error = $("#modal_check_password_error");
    var change_password_btn = $("#change_password_btn");
    change_password_btn.addClass("no_submit");
    change_password_btn.addClass("password_error");
    check_password.blur(function () {
        var new_password_value = new_password.val();
        var check_password_value = check_password.val();
        if(new_password_value !== check_password_value){
            modal_check_password_error.show();
            change_password_btn.removeClass("password_right");
            change_password_btn.addClass("password_error");
        }
        if(new_password_value === check_password_value){
            modal_check_password_error.hide();
            change_password_btn.removeClass("password_error");
            change_password_btn.addClass("password_right");
        }
    });

    //模态框表单提交
    var auth_code = $("#auth_code");
    var auth_code_null = $("#auth_code_null");
    var new_password_null = $("#new_password_null");
    var check_password_null = $("#check_password_null");
    var auth_code_error = $('#auth_code_error');
    change_password_btn.click(function () {
        var modal_phone_len = modal_phone.val().length;
        var auth_code_len = auth_code.val().length;
        var new_password_len = new_password.val().length;
        var check_password_len = check_password.val().length;
        //点击确认修改时再次检测两次密码是否一致
        var new_password_value = new_password.val();
        var check_password_value = check_password.val();
        if(new_password_value !== check_password_value){
            modal_check_password_error.show();
            change_password_btn.removeClass("password_right");
            change_password_btn.addClass("password_error");
        }
        if(new_password_value === check_password_value){
            modal_check_password_error.hide();
            change_password_btn.removeClass("password_error");
            change_password_btn.addClass("password_right");
        }

        if(modal_phone_len !== 0 && auth_code_len !== 0 && new_password_len !== 0 && check_password_len !== 0){
            change_password_btn.removeClass("no_submit");
            change_password_btn.addClass("yes_submit");
        }
        else {
            if(modal_phone_len === 0){
                modal_phone_null.show();
                change_password_btn.removeClass("yes_submit");
                change_password_btn.addClass("no_submit");
            }
            if(auth_code_len === 0){
                auth_code_null.show();
                change_password_btn.removeClass("yes_submit");
                change_password_btn.addClass("no_submit");
            }
            if(new_password_len === 0){
                new_password_null.show();
                change_password_btn.removeClass("yes_submit");
                change_password_btn.addClass("no_submit");
            }
            if(check_password_len === 0){
                check_password_null.show();
                change_password_btn.removeClass("yes_submit");
                change_password_btn.addClass("no_submit");
            }
        }
    });
    auth_code.focus(function () {
        auth_code_null.hide();
        auth_code_error.hide();
    });
    new_password.focus(function () {
        new_password_null.hide();
    });
    check_password.focus(function () {
        check_password_null.hide();
        modal_check_password_error.hide();
    });

    change_password_btn.click(function () {
        var modal_phone_value = modal_phone.val();
        var modal_auth_code_value = auth_code.val();
        var modal_new_password_value = new_password.val();
        if(change_password_btn.hasClass("yes_submit") && change_password_btn.hasClass("password_right") && modal_phone.hasClass("phone_success")){
            $.ajax({
                type:'post',
                url:'/changePassword',
                dataType:'json',
                data:{
                    phone:modal_phone_value,
                    authCode:modal_auth_code_value,
                    newPassword:modal_new_password_value
                },
                success:function (data) {
                    if(data['status'] == 902){
                        auth_code_error.show();
                    }else if (data['status'] == 506){
                        $('.phoneNotExitSpan').show();
                    }else if (data['status'] == 103){
                        alert(data['message'] + " 密码修改失败")
                    }else if(data['status'] == 901){
                        alert("手机号不正确，请重新输入");
                    }else {
                        alert("密码修改成功，快去登录吧！");
                        window.location.reload();
                    }
                },
                error:function () {
                    alert("修改密码失败");
                }
            })
        }
    });

    function checkPhone(phone){
        if(!(/^1[3456789]\d{9}$/.test(phone))){
            return false;
        }
        return true;
    }

    //关闭模态框时清空其中的值
    var change_password_cancel_btn = $("#change_password_cancel_btn");
    change_password_cancel_btn.click(function () {
        var modal_value = $(".modal_value");
        var modal_icons = $(".modal_icons");
        modal_value.val("");
        modal_icons.hide();
    });




