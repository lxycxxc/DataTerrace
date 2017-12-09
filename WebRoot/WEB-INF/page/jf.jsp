<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
Object user = request.getSession().getAttribute("user");
if(user==null){
	response.sendRedirect(basePath+"login");
}
%>


<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
	<meta charset="utf-8"/>
	<title>首页</title>
	<link rel="stylesheet" href="css/index-pc.css"/>
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<script type="text/javascript" src="js/rem.js"></script>
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/layer/layer.js"></script>
	<style type="text/css">
		.inputs input{width:3rem}
	</style>
</head>
<body>
	<div class="wrap">
		<div class="isection">
			<div class="inputs">

					<li>用户手机：<input type="phone" class="" placeholder="请输入用户手机号" name="phone"> + <input type="number" min="1" max="9999" class="" style="width:50px" name="jf">积分</li>
					<li>验证码<input type="text" value="" placeholder="请输入7373号码的验证码" name="code"> <a class="btn-yellow" onclick="time(this)">获取验证</a></li>
					<li>说明</li>
					<textarea rows="" cols="" style="height: 50px;width: 95%" name="explain" id="explain"></textarea>
					<li><input type="button" class="btn-green" value="提交" onclick="submit()"/></li>
			</div>
		</div>
	</div>
</body>
<script>
			var wait=60; 
			function time(obj) { 
		        if (wait == 0) { 
		            obj.removeAttribute("disabled");   
		            obj.value="获取验证码"; 
		            wait = 60; 
		        } else { 
		        
		        	if(wait==60){//发送验证码
		        		var phone=$("input[name=phone]").val();
						var phoneReg=/^1[0-9]{10}$/;
						if(!phoneReg.test(phone)){
							layer.msg("请输入正确的11位手机号码!");
							return false;
						}
						 obj.setAttribute("disabled", true); 
						$.post("back/sendMsg",{"phone":phone},function(rv){
							if(rv.result==1){
								wait--;
								 setTimeout(function() { 
					                time(obj) 
					            },1000)
							}else{
								obj.removeAttribute("disabled"); 
							}
							
							layer.msg(rv.msg);
						});	
						return;
		        	}
		        
		        
		            obj.setAttribute("disabled", true); 
		            obj.value=wait+"s后重新获取"; 
		            wait--; 
		            setTimeout(function() { 
		                time(obj) 
		            }, 
		            1000) 
		        } 
		    }
		    
		    function submit(){
		    	var phone=$("input[name=phone]").val();
		    	var code=$("input[name=code]").val();
		    	var explain=$("input[name=explain]").val();
		    	var jf = $("input[name=jf]").val();
		    	
		    	$.post("back/submit",{"phone":phone,"code":code,"jf":jf,"text":explain},function(){
		    	
		    	},"JSON")
		    }
		    

	$(function(){

	})
</script>
</html>
