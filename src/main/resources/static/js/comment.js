
    $("#toLogin").click(function(){
        $.get("/toLogin",function(data,status,xhr){
            window.location.replace("/login");
        });
    });
    //客官，来说两句把
    $('#commentBtn').click(function () {
        var commentContent = $('#comment').val();
        commentContent = $.trim(commentContent);
        if(commentContent == ""){
            alert("客官，你还没说两句呢！");
        } else {
            $.ajax({
                type: 'POST',
                url: '/publishComment',
                dataType: 'json',
                data: {
                    commentContent:commentContent,
                    articleId:articleId
                },
                success: function (data) {
                    if(data[data.length-1]['status'] == 403){
                        $.get("/toLogin",function(data,status,xhr){
                            window.location.replace("/login");
                        });
                    }else{
                        putInComment(data);
                    }
                },
                error: function () {
                    alert("发表评论失败！");
                }
            });
        }

    });
