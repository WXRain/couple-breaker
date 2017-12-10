// weibo.js

$(function() {
    $("#enterBtn").on("click", function() {
        var weiboType = $("#weiboType").val();
        var userType = $("#userType").val();
        var weibo = $("#weibo").val();
        var target = $("#target").val();
        $("#enterBtn").html("请等待");
        
        console.info(weiboType, userType, weibo, target);
        if (weibo == "" || target == "") {
            alert("参数不能为空");
            return;
        }
        
        if (weiboType == "url" && userType == "name") {
            $.ajax({
                url: "weibo/commit/find/name_url",
                type: "post",
                dataType: "json",
                data: {"url": weibo, "userName": target},
                success: function(data) {
                    requestSuccess(data);
                },
                error: function(data) {
                    alert("请求错误");
                }
            });
        } else if (weiboType == "url" && userType == "id") {
            $.ajax({
                url: "weibo/commit/find/id_url",
                type: "post",
                dataType: "json",
                data: {"url": weibo, "userId": target},
                success: function(data) {
                    requestSuccess(data);
                },
                error: function(data) {
                    alert("请求错误");
                }
            });
        } else if (weiboType == "id" && userType == "name") {
            $.ajax({
                url: "weibo/commit/name/" + weibo +"/" + target,
                type: "get",
                dataType: "json",
                success: function(data) {
                    requestSuccess(data);
                },
                error: function(data) {
                    alert("请求错误");
                }
            });
        } else if (weiboType == "id" && userType == "id") {
            $.ajax({
                url: "weibo/commit/id/" + weibo +"/" + target,
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
            alert("请求错误");
        }
    });
});


function requestSuccess(data) {
    console.info(data.data.length);
    $("#enterBtn").html("确 定");
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