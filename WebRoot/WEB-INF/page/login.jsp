<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object user = request.getSession().getAttribute("user");
if(user!=null){
	response.sendRedirect(basePath+"busList");
}
%>

<!doctype html>
<html lang="zh">
<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>后台登录</title>

<link rel="stylesheet" type="text/css" href="css/login.css">
<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="js/layer/layer.js"></script>
</head>
<body>


<div class="wrapper">
	<div class="container">
		<h1>Welcome</h1>
		<form class="form" method="post" onsubmit="return false">
			<input type="text" placeholder="用户名" name='user'>
			<input type="password" placeholder="密码" name='pass'>
			<button  id="login-button">登录</button>
		</form>
	</div>
</div>


<script type="text/javascript">

$('#login-button').click(function(){
	var user = $('input[name="user"]').val();
	var pass = $('input[name="pass"]').val();
	if(user==""||pass==""){
		layer.msg("用户名或密码不能为空！");
	}else{
		$.ajax({
			url:'${pageContext.request.contextPath }/back/sendLogin',
			dataType:'json',
			async:false,
			data:{"user":user,"pass":pass},
			type:'POST',
			success:function(data){
				if(data.res==1){
					window.location.href='${pageContext.request.contextPath }/busList';
				}else{			
					layer.msg(data.msg);
				}
			}
		
		});
	}
});
</script>

</body>
</html>