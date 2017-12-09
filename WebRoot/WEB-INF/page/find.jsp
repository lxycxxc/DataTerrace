<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
  	<form action="client/find" method="post">
	  	SQL： <textarea rows="" cols="" name="sql"></textarea>
	  	<input type="submit" value="提交"/>  	
  	</form>
  
    <table border="1">
	    <tr>
	    	<c:forEach items="${columns}" var="c">
	    		<td>${c}</td>
	    	</c:forEach>
	    </tr>
	    <c:forEach items="${list}" var="l">
			<tr>
			<c:forEach items="${columns}" var="c">
				<td>${l[c]}</td>
			</c:forEach>
			</tr>	    
	    </c:forEach>

    </table>
  </body>
</html>
