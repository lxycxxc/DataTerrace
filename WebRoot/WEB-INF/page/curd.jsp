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
	<link rel="stylesheet" href="css/switch.css"/>
	<script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
	<link rel="stylesheet" href="js/layui/css/layui.css">
	<script type="text/javascript" src="js/layui/layui.all.js"></script>
	<script type="text/javascript"src="js/rem.js"></script>
	<style type="text/css">
	select{
	margin-right: 10px;
    padding: 0 10px;
    width: 120px;
    height: 38px;
    line-height: 38px;
    font-size: 16px;
    color: #999;
    border: 1px solid #f0eeee;
    border-radius: 6px;}
	</style>
	
<style type="text/css">
table.itable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
}
table.itable thead td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.itable tbody td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}

table.itable tbody td {
    overflow:hidden;
    white-space:nowrap;
    text-overflow:ellipsis;
    -o-text-overflow:ellipsis;
    -moz-text-overflow: ellipsis;
    -webkit-text-overflow: ellipsis;
}
</style>
	
	
</head>
<body>
	<div class="dialog hide">
		<div class="dia-outter">
			
		</div>
	</div>
	<div class="wrap">
		<!-- 正文   -->
		<div class="index">
			<div class="iheader"><img src="img/itop-pc.png"/></div>
		</div>
		<div class="isection">
<!-- 			<div>
				<p>1.如果需要加入筛选条件，在原语句后面加上 where 字段名='值' 例如：select * from js_video_user where phone='13195338323' 表示查询手机号为13195338323的所有记录</p>
				<p>2.如果多条件查询 在条件与条件之间加上用 and 隔开，例如 :select * from js_video_user where phone='13195338323' and create_time>to_date('2017-02-01','yyyy-mm-dd')</br>表示查询手机号为 13195338323 创建时间 大于 2017-02-01 的所有记录</p>
				<p>3.为了保障后台性能，每次查询最多显示100行，如果需要显示更多 调整 rownum<100参数大小.查询范围最大显示1千条 导出最大为5万条</p>
				<p>4.参考文档：<a href="http://www.runoob.com/sql/sql-where.html">http://www.runoob.com/sql/sql-where.html</a></p>
			</div> -->
			</br>
			<div class="inputs">
			    选择查询项目：
				<select onchange="findName()" id="findSqlName" class="sphone" >
					<option>选择查询项目</option>
					<c:forEach items="${grouplist}" var="list">
						<option value="${list.id}">${list.classname}</option>
					</c:forEach>
				</select>
				<select onchange='find($("#findsql").val())' id="findsql" class="sphone" >
					<option>选择查询项目</option>
				</select>
				<label class="iSwitch">
				    <input type="checkbox" id="is_texts">
				    <i></i>
				</label> 
				<br><br>
				<div id="texts" style="display: none">
					<p>条件模板：{"id":{"name":"id","explain":"ID","type":"number"}}</p>
					<textarea rows="" cols="" style="height: 200px;width: 50%" id="explain" >${error}</textarea>
					<textarea rows="" cols="" style="height: 200px;width: 30%" id="wheres_content" >${error}</textarea>
				</div>
				<br><br>	
				<form action="busList" method="post">
					<!-- <input class="sphone" type="text" placeholder="请输入sql语句搜索" name="sql" style="width: 90%"/> -->
					<%-- <textarea rows="" cols="" style="height: 50px;width: 95%" name="sql" id="sql">${sql}</textarea> --%>
					
					筛选条件：<span id="proname"></span><br><br>

					<table id="whrers">
					
						<!-- <select onchange="find()" id="findsql" class="sphone" >
							<option>选择查询项目</option>
						</select>
						
						<input type="text" placeholder="手机号码"> -->
				
					</table>


					</br></br>
					
						
				
					<!-- <a class="btn-green">搜 索</a> -->
					<input type="submit" class="btn-green" />
					<a class="btn-yellow excel">下载报表</a>
					<a class="btn-yellow csv" >导出csv</a>
				 <!-- 	<a class="btn-green" onclick="jfbf()">积分补发</a>  -->
				</form>
			</div>
			<table class="itable" >
				<thead>
					<!-- <tr class="ths">
						<th width="20%">订购日期</th>
						<th width="15%">手机号码</th>
						<th width="20%">产品名称</th>
						<th width="15%">金额</th>
						<th width="30%">返回结果</th>
					</tr> -->
					<tr>
				    	<c:forEach items="${columns}" var="c">
				    		<td>${c}</td>
				    	</c:forEach>
				    </tr>
				</thead>
				<tbody class="userlist">
				<!--用户列表-->
				    <c:forEach items="${list}" var="l">
						<tr>
						<c:forEach items="${columns}" var="c">
							<td>${l[c]}</td>
						</c:forEach>
						</tr>	    
				    </c:forEach>
				</tbody>
			</table>
			<!-- <ul class="pages clearfix">
				<li class="cur"><a onclick="getBusPK(0)">首 页</a></li>
				<li><a onclick="getBusPK(-1)">上一页</a></li>
				<li><a onclick="getBusPK(1)">下一页</a></li>
				<li><a onclick="getBusPK(1024)">尾 页</a></li>
			</ul> -->
		</div>
	</div>
</body>
<!-- <script type="text/javascript" src="js/laydate/laydate.js"></script> -->
<script>
	var laydate = layui.laydate;

	find("${sqlid}");
	/*根据分组查询SQL名称*/
	function findName() {
		var classid = $("#findSqlName").val();
		if(classid>0){
			$.post("back/findName",{"classid":classid},function(data){
				var list = data.namelist
				var html = "<option value='0'>选择查询项目</option>";
				
				for(var i=0;i<list.length;i++ ){
					html+='<option value="'+list[i].ID+'">'+list[i].NAME+'</option>'
				}
				//alert(explain)
				$("#findsql").html(html);
			},"JSON")
		}
	}


	function find(id){
		if(id>0){
			$.get("back/findSql",{"id":id},function(data){
				var sql = data.sql
				var wheres = sql.WHERES;
				var comments = data.comments;
				var explain = '';
				var html = "<input type='hidden' name='sqlid' value='"+id+"'/>";
				
				for(var i in comments ){
					explain+=comments[i].COLUMN_NAME+' : '+comments[i].COMMENTS+'\n'
				} 
				//alert(explain)
				
				for(var w in wheres){
					//alert(w)
					if(wheres[w].type=='date'||wheres[w].type=='datestr'){
						
						html+="<input type='text' class='date'  name='"+wheres[w].name+"' placeholder='"+wheres[w].explain+"'>";
					}else if(wheres[w].type=="select"){
						html+="<select id='"+wheres[w].name+"' name='"+wheres[w].name+"' > <option value=''>"+wheres[w].explain+"</option>"
						var select = wheres[w].select;
						for(var s in select){
							for(var key in select[s]){
								var value = select[s][key];
								html+="<option value='"+value+"'>"+key+"</option>"
								//alert("key:"+key+",,value:"+value)
							}
						}
						html+="</select>"
					}else{
						html+="<input type='"+wheres[w].type+"' name='"+wheres[w].name+"' placeholder='"+wheres[w].explain+"' />";
					}
				}
				
			//	$("#sql").val(sql.SQLTEXT);
				$("#wheres_content").val(JSON.stringify(wheres,null,4));
				$("#wheres_content").attr("onchange","updateWheres("+id+")")
				
				$("#explain").html(explain);
				$("#whrers").html(html);
				$("#proname").text(sql.NAME);
				
				laydate.render({elem: '.date'});//加载事件控件
			},"JSON")
		}
	}
	
	/*修改筛选条件*/
	function updateWheres(id){
		var text = $("#wheres_content").val();
		$.post("back/updateWheres",{"sqlid":id,"text":text},function(data){
		   if(data.res==1){
				find(id);
			}else{
				layer.msg(data.msg);
			} 
		},"JSON");
	}
	
	/* function jfbf(){
		layer.open({
		  type: 2,
		  title: 'layer mobile页',
		  shadeClose: true,
		  shade: 0.8,
		  area: ['50%', '60%'],
		  content: 'jfcz' //iframe的url
		}); 
	} */


	$(function(){
		
		
		/* $(".btn-yellow").click(function(){
			sql = $("input[name=sql]").val();
			alert(sql)
			location.href='back/expBus?sql='+sql
		}) */
		
		/* //点击搜索
		$('.inputs .btn-green').click(function (){
			sphone = $('.sphone').val();
			sdate = $('.sdate').val();
			//alert("sphone:"+sphone+"	sdate:"+sdate);
			getBusPK(0);
		});; */
		
		$("#is_texts").click(function(){
			var id = $("#is_texts:checked").attr("id")
			if(id=='is_texts'){
				$("#texts").show()
			}else{
				$("#texts").hide()
			}
		})

		//点击查看统计
		$('.excel').click(function(){ 
		   var t = $('form').serializeArray();
		    var p = '';
		    for(var i in t){
		    	p+=t[i].name+"="+t[i].value+"&";
		    }
		    p = p.substring(0, p.length-1)
		    location.href='back/expBus?'+p
			
		});
		
		$('.csv').click(function(){
			   var t = $('form').serializeArray();
			    var p = '';
			    for(var i in t){
			    	p+=t[i].name+"="+t[i].value+"&";
			    }
			    p = p.substring(0, p.length-1)
			    location.href='back/expBusToCsv?'+p
		});

		$('.pages li').click(function(){
			$(this).addClass('cur').siblings().removeClass('cur');
		});
		//弹框关闭按钮
		$('.btn-close').click(function(){
			$('.dialog').addClass('hide');
		});
		
	})
</script>
</html>
