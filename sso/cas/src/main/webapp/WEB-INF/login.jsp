<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>用户登录</title>
</head>
<body>

<form action="login" method="post">
    <p>登录名：<input type="text" name="id"></p>
    <p>密码：<input type="password" name="pwd"></p>
    <p>来自域：<input type="text" id="redir_url" name="LOCAL_SERVICE" readonly></p>
    <p><input type="submit" value="登录"></p>
</form>

<script type="text/javascript">
    function getQueryVariable(variable)
    {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i=0;i<vars.length;i++) {
            var pair = vars[i].split("=");
            if(pair[0] == variable){return pair[1];}
        }
        return null;
    }
    document.getElementById("redir_url").value = getQueryVariable("LOCAL_SERVICE");
</script>
</body>
</html>
