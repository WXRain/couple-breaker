// weibo.js

$(function() {
    $("#enterBtn").on("click", function() {
        var weiboType = $("#weiboType").val();
        var userType = $("#userType").val();
        var weibo = $("#weibo").val();
        var target = $("#target").val();

        console.info(weiboType, userType, weibo, target);
        if (weibo == "" || target == "") {
            alert("参数不能为空");
            return;
        }

        if (weiboType == "url") {
            weibo = /\d{16}/.exec(weibo);
            if (weibo == null) {
                alert("不是有效的 URL");
                return;
            }
        }

        $("#enterBtn").html("请稍等");
        if (userType == "name") {
            $.ajax({
                url: "weibo/comment/name/" + weibo +"/" + target,
                type: "get",
                dataType: "json",
                success: function(data) {
                    requestSuccess(data);
                },
                error: function(data) {
                    alert("请求错误");
                }
            });
        } else if (userType == "id") {
            $.ajax({
                url: "weibo/comment/id/" + weibo +"/" + target,
                type: "get",
                dataType: "json",
                success: function(data) {
                    requestSuccess(data);
                },
                error: function(data) {
                    alert("请求错误");
                }
            });
        } else {
            alert("参数错误");
        }
        
        $("#enterBtn").html("确 定");
    });
});

function requestSuccess(data) {
    console.info(data.data.length);
    if (data.data.length == 0) {
        alert("没有找到");
        return;
    }

    $("#data").empty();
    data.data.forEach(function(e) {
        var tr = "<tr><td> " + e.user.id +"</td><td>" + e.user.screen_name
            + "</td><td>" + e.text + "</td><td>" + e.created_at + " </td></tr>";
        $("#data").append(tr);
    });

    $("#dataPane").show();
}
