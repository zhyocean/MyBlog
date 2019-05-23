
    function getFriendLink() {
        $.ajax({
            type:'post',
            url:'/getFriendLinkInfo',
            dataType:'json',
            data:{
            },
            success:function (data) {
                var friendLinks = $('#friendLinks');
                friendLinks.empty();
                for(var i in data['data']){
                    friendLinks.append($('<div class="friendLink">' +
                        '<i class="am-icon-street-view "></i>' +
                        '<a href="' + data['data'][i]['url'] + '" target="_blank">' + data['data'][i]['blogger'] + '</a>' +
                        '</div>'));
                }
            },
            error:function () {
            }
        });
    }

    getFriendLink();