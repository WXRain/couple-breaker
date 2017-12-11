<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no" />
<script src="http://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<script src="js/weibo.js"></script>
<link rel="shortcut icon" href="image/icon.png" type="image/x-icon" />
<link href="http://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" />
<link href="css/weibo.css" rel="stylesheet" />
<title>Couple Breaker</title>
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-md-8 col-md-offset-2 col-sm-12">
      <div>
        <header class="header">
          <div class="banner-area" style="padding-top:20px;padding-bottom:20px;">
            <h1>Couple Breaker</h1>
          </div>
        </header>
      </div>
      <div>
        <h3 style="text-align:center">你可以在这里找到指定微博评论下指定用户的评论</h3>
        <p>使用说明：</p>
        <p>搜索条件需要微博 ID 和你要搜索用户的 ID（数字 ID）或者用户名</p>
        <p>
          微博——指定需要搜索的微博评论，支持 ID 和链接 URL。
          微博ID可以分享微博链接再用浏览器打开即可获取，
          比如：https://m.weibo.cn/api/comments/show?id=4157476123456789&page=1，
          4157476123456789 便是这条微博的 ID。也可以直接将链接 URL 填入，自动获取 ID。
        </p>
        <p>
          用户——指定搜索的目标用户，支持 ID 和用户名。
          用户名即昵称，ID 是数字型的，每个用户唯一确定且不能修改的，一般看不到。
        </p>
        <h3 style="text-decoration: line-through; text-align: center; color: green">
          <span>都到这一步了，快分了吧！</span>
        </h3>
      </div>
      <div>
        <div style="padding-top:20px;">
          <form role="form" id="findForm">
            <div class="form-group">
              <label>微博来源</label>
              <div class="row">
                <div class="col-md-2">
                  <select id="weiboType" class="form-control">
                    <option value="url">URL</option>
                    <option value="id">ID</option>
                  </select>
                </div>
                <div class="col-md-10">
                  <input id="weibo" class="form-control" placeholder="填写微博链接或ID">
                </div>
              </div>
            </div>
            <div class="form-group">
              <label>用户来源</label>
              <div class="row">
                <div class="col-md-2">
                  <select id="userType" class="form-control">
                    <option value="name">用户名</option>
                    <option value="id">数字ID</option>
                  </select>
                </div>
                <div class="col-md-10">
                    <input id="target" class="form-control" placeholder="填写用户名或ID" />
                </div>
              </div>
              </div>
          </form>
        </div>
        <div class="form-group">
          <span class="col-md-2 col-md-offset-5">
            <label><button id="enterBtn" class="btn btn-default" style="width: 100%">确 认</button></label>
          </span>
        </div>
      </div>
    </div>
  </div>
  <div class="row">
    <div id="dataPane" style="display: none; padding-top:20px">
      <hr>
      <table class="table table-striped">
        <caption>搜索结果</caption>
        <thead>
          <tr>
            <th>用户ID</th>
            <th>用户名</th>
            <th>内容</th>
            <th>时间</th>
          </tr>
        </thead>
        <tbody id="data">
        </tbody>
      </table>
    </div>
  </div>
  <div class="row">
    <hr>
    <footer class="footer">
      <div style="text-align:center">
        <p><span>您是第<span style="color: red">&nbsp;${visitCount}&nbsp;</span>个访问者</span></p>
        <p><span>&copy; Couple Breaker 2017 权利所有</span></p>
        <p><span>如有问题，请联系 jingqingyun@163.com，虽然我极有可能看不到。</span></p>
      </div>
    </footer>
  </div>
</div>
</body>
</html>
