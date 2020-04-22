
$('.admire-pic img').click(function () {
    var src = $(this).attr("src");
    $('#reward-show-modal').find('img').attr("src", src);
    $('#reward-show-modal').modal('open');
})

function getRewardInfo() {
    $.ajax({
        type:'post',
        url:'/getRewardInfo',
        dataType:'json',
        data:{
        },
        success:function (data) {
            if (data['status'] == 103){
                dangerNotice(data['message'] + " 获得募捐记录失败");
                return;
            }
            var rewardTable = $('#rewardTable');
            rewardTable.empty();
            if (data['data'].length > 0 && data['status'] == 0){
                $.each(data['data'], function (index,obj) {
                    var fundRaiser = obj['fundRaiser'];
                    var tr = $('<tr id=' + obj['rewardUrl'] +  '></tr>');
                    if(fundRaiser == "张张张张先森"){
                        tr.append($('<th>'+ fundRaiser +'<span class="is-me am-badge am-badge-danger am-radius am-round">?</span></th>'));
                    } else {
                        tr.append($('<th>'+ fundRaiser +'</th>'));
                    }
                    tr.append($('<th>' + obj['fundRaisingSources'] + '</th>' +
                        '<th  class="am-hide-sm-down">'+ obj['fundraisingPlace'] + '</th>' +
                        '<th><i class="am-icon-cny">'+ obj['rewardMoney'] + '</i></th>' +
                        '<th  class="am-hide-sm-down">'+ obj['remarks'] + '</th>' +
                        '<th>'+ timestampToYMDTime(obj['rewardDate']) + '</th>' +
                        '<th>' +
                        '<button type="button" class="rewardShowBtn am-btn am-btn-warning am-btn-xs">查看</button>' +
                        '</th>'));
                    rewardTable.append(tr);
                })
            }

            $('.rewardShowBtn').click(function () {
                var rewardUrl = $(this).parent().parent().attr("id");
                $('#reward-show-modal').find('img').attr("src", rewardUrl);
                $('#reward-show-modal').modal('open');
            })

            $(function() {
                $('.is-me').popover({
                    content: '来自张先森的工资捐赠',
                    trigger:'hover'
                })
            });
        },
        error:function () {
        }
    });
}

//时间转换为2019年7月13日
function timestampToYMDTime(timestamp) {
    var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
    Y = date.getFullYear() + '年';
    M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '月';
    D = date.getDate() + '日';
    h = date.getHours() + ':';
    m = date.getMinutes() + ':';
    s = date.getSeconds();
    return Y+M+D;
}

//获得募捐记录
getRewardInfo();