<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>app2</title>
    <script type="text/javascript">
        var Ajax={
            get: function(url, fn) {
                // XMLHttpRequest对象用于在后台与服务器交换数据
                var xhr = new XMLHttpRequest();
                xhr.open('GET', url, true);
                xhr.onreadystatechange = function() {
                    // readyState == 4说明请求已完成
                    if (xhr.readyState == 4 && xhr.status == 200 || xhr.status == 304) {
                        // 从服务器获得数据
                        fn.call(this, xhr.responseText);
                    }
                };
                xhr.send();
            },
            // datat应为'a=a1&b=b1'这种字符串格式，在jq里如果data为对象会自动将对象转成这种字符串格式
            post: function (url, data, fn) {
                var xhr = new XMLHttpRequest();
                xhr.open("POST", url, true);
                // 添加http头，发送信息至服务器时内容编码类型
                xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
                xhr.onreadystatechange = function() {
                    if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 304)) {
                        fn.call(this, xhr.responseText);
                    }
                };
                xhr.send(data);
            }
        }
        function logoutAll(){
            var urls = ["http://localhost:8080/app1/Logout",
                "http://localhost:8080/app2/Logout",
                "http://localhost:8080/cas/Logout"];
            for(let i=0;i<urls.length;i++){ // 这里不用let可能会由于js函数提升导致后面console出错
                Ajax.get(urls[i],function (x) {
                    console.log(urls[i]);
                });
            }
            window.location = "/cas/";
        }
    </script>
</head>
<body>
<h2>------WELCOME TO APP2------</h2><br>
<p>
    <a href="http://localhost:8080/app1/app">click to app1</a>
</p>
<button onclick="logoutAll()">LOGOUT</button>
</body>
</html>
