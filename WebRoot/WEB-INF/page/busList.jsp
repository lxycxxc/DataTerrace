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
/* table.itable {
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
} */
table.itable tbody td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #ffffff;
}

table.itable  td {
    overflow:hidden;
    white-space:nowrap;
    text-overflow:ellipsis;
    -o-text-overflow:ellipsis;
    -moz-text-overflow: ellipsis;
    -webkit-text-overflow: ellipsis;
}
</style>
	

<script> /*curd操作*/

var laydate = layui.laydate;
var laypage = layui.laypage;
var upload = layui.upload;
	
	$(function(){

		$("#UP").click(function(){//修改数据功能
			var td = $(".itable thead td").html();
			var up = $(".itable thead #exce_up").html();
			//alert(up)
			if(td!=undefined&&up==undefined){
			 $(".itable thead tr").append("<td id='exce_up'>操作</td>");
			 $(".itable tbody tr").append("<td><a onclick='up($(this).parents(\"tr\").attr(\"rid\"))'>修改</a></td>");				
			}
		})
		
		$("#DE").click(function(){//删除数据功能
			var td = $(".itable thead td").html();
			var de = $(".itable thead #exce_de").html();
			//alert(up)
			if(td!=undefined&&de==undefined){
			 $(".itable thead tr").append("<td id='exce_de'>操作</td>");
			 $(".itable tbody tr").append("<td><a onclick='de($(this).parents(\"tr\").attr(\"rid\"))'>删除</a></td>");				
			}
		})
		
		$("#IN").click(function(){//添加数据功能
			inss();
		})
		
		var im_index=0;
		$("#IM").click(function(){//批量导入数据功能
			var sqlid = $("#findsql").val();
			var proname = $("#proname").text();
			var imstr = "<div class='layui-upload-drag' id='filecsv'><i class='layui-icon'></i><p>点击上传，或将文件拖拽到此处</p></div><div><a href='load/uploadTemplate?sqlid="+sqlid+"'>"+proname+"</a></div>";
			im_index = layer.open({
				  type: 1,
				  title: '导入数据',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['50%', '60%'],
				  content: imstr //iframe的url
			}); 
			
			 upload.render({ //允许上传的文件后缀
			    elem: '#filecsv'
			    ,url: 'load/uploadCsv?sqlid='+sqlid
			    ,accept: 'file' //普通文件
			    ,exts: 'csv' //只允许上传压缩文件
			    ,done: function(res){
			      //console.log(res)
			      if(res.res==1){
			    	  layer.msg("数据导入成功",function(){
			    		  layer.close(im_index);
			    	  });
			      }else{
			    	  layer.msg(res.msg);
			      }
			    }
			 });
			 
		})
	})
	
	var upstr = "";
	var up_index;
	function up(id){
		if(id!=null&&id!=undefined&&id!="undefined"){
			//alert("正在更新："+id)
			up_index = layer.open({
				  type: 1,
				  title: '添加数据',
				  shadeClose: true,
				  shade: 0.8,
				  area: ['50%', '60%'],
				  content: upstr //iframe的url
			}); 
			
			$.each($('#form_up').serializeArray(), function(){//根据id和index定位到数据，回填至Up表单
				//data-index
				var $input  = $("#form_up").find("input[name="+this.name+"]");
				var data_index = $input.attr("data-index");
			    var dd = $("tr[rid="+id+"] td").eq(data_index).text();
			    $input.val(dd);
			});
			$("#form_up").find("input[name=rid]").val(id);
			
			
		}else{
			layer.msg("无效的ID")
		}
	}
	
	function de(id){
		if(id!=null&&id!=undefined){
			//alert("正在删除："+id)
			layer.confirm('是否删除这条数据？', {
			  btn: ['删除','取消'] //按钮
			}, function(){
				 var sqlid = $("#findsql").val();
				 $.post("curd/delete",{"sqlid":sqlid,"rid":id},function(data){
					 if(data.res==1){
						$("tr[rid="+id+"]").hide();
						layer.msg("删除成功！");
					 }else{
						 layer.msg(data.msg);
					 }
				 },"JSON")
			});
		}else{
			layer.msg("无效的ID")
		}
	}
	
	var insstr = "";
	var in_index;
	function inss(){
	
		in_index = layer.open({
			  type: 1,
			  title: '添加数据',
			  shadeClose: true,
			  shade: 0.8,
			  area: ['50%', '60%'],
			  content: insstr //iframe的url
		}); 
		
	}

	function hides(){//隐藏所有操作
		$("#EXP").hide();
		$("#UP").hide();
		$("#DE").hide();
		$("#IN").hide();
	}
	
	
	//-----------查询报表数据-----------------------------------------------

	function submit_wheres(){//查询表单
		//alert("tijiao");
		//findList(2, size)
		var data = {};
	    $.each($('#form_wheres').serializeArray(), function(){data[this.name] = this.value;});
		$.post("curd/findCount",data,function(data){ //查询总页数
			laypage.render({
			    elem: 'pages' //注意，这里的 test1 是 ID，不用加 # 号
			    ,count: data.cou //数据总数，从服务端得到
			    ,jump: function(obj, first){
			        //obj包含了当前分页的所有参数，比如：
			        console.log(obj.curr); //得到当前页，以便向服务端请求对应页的数据。
			        console.log(obj.limit); //得到每页显示的条数
			        
			       findList(obj.curr, obj.limit);//查询数据
			        
			    }
			});
			
		},"JSON");
		
		return false;
	}
	
	
	function findList(page,size){
		var data = {"page":page,"size":size};
		var table = $('#form_wheres').serializeArray();
		  
	    $.each(table, function() {
	      data[this.name] = this.value;
	    });
	    //alert(JSON.stringify(data));
	
		 $.post("curd/findList",data,function(data){
			 var head=""
			 var body="";
			 if(data.list!=null){
			     var list = data.list.list;
				 for(var k in list[0]){
					 head+="<td>"+k+"</td>"
				 }
				 for(var i in list){
					 var h = list[i];
					 body+="<tr rid='"+h["ID"]+"'>"
					 for(var k in h){
						 body+="<td>"+h[k]+"</td>"
					 }
					 body+="</tr>"
				 }
				 
				 $(".itable thead tr").html(head);
				 $(".itable tbody").html(body);
			 }else{
				 layer.msg(data.msg)
			 }
			
		},"JSON"); 
	}

	//-----------查询报表数据-----------------------------------------------
	
	
	
	function submit_in(){//插入表单
		var sqlid = $("#findsql").val();
		var data = {"sqlid":sqlid}
		var table = $('#form_in').serializeArray();
		 $.each(table, function() {
		      data[this.name] = this.value;
		 });
		 $.post("curd/insert",data,function(data){
			 if(data.res==1){
				 layer.msg("添加成功！",function(){
					 layer.close(in_index);
				 });
			 }else{
				 layer.msg(data.msg);
			 }
		 },"JSON")
		 
		 
		console.log(JSON.stringify(data));
		return false;
	}
	
	
	function submit_up(){
		var sqlid = $("#findsql").val();
		var data = {"sqlid":sqlid}
		var table = $('#form_up').serializeArray();
		 $.each(table, function() {
		      data[this.name] = this.value;
		 });
		 $.post("curd/update",data,function(data){
			 if(data.res==1){
				 layer.msg("更新成功！",function(){
					 layer.close(up_index);
				 });
			 }else{
				 layer.msg(data.msg);
			 }
		 },"JSON")
		 
		 
		console.log(JSON.stringify(data));
		return false;
	}
	
</script>
	
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
				<form id="form_wheres" onsubmit="return submit_wheres();">
					<!-- <input class="sphone" type="text" placeholder="请输入sql语句搜索" name="sql" style="width: 90%"/> -->
					<%-- <textarea rows="" cols="" style="height: 50px;width: 95%" name="sql" id="sql">${sql}</textarea> --%>
					
					筛选条件：<span id="proname"></span><br><br>

					<table id="whrers">
				
					</table>

					
					<div>
						</br></br>
						<input type="submit" class="btn-yellow"  value="查询">
						<!-- <a id="SE" class="btn-yellow"  onclick="submit()">查询</a> -->
						
						<span id="EXP" style="display: none">
							<a class="btn-yellow excel">下载报表</a>
							<a class="btn-yellow csv" >导出csv</a>
						</span>
					</div>
					<div>
						</br></br>
						<a id="IM" class="btn-green"   style="display: none">导入</a>
						<a id="IN" class="btn-green"   style="display: none">添加</a>
						<a id="UP" class="btn-green"   style="display: none">修改</a>
						<a id="DE" class="btn-green"   style="display: none">删除</a>
					</div>
					
					
				 <!-- 	<a class="btn-green" onclick="jfbf()">积分补发</a>  -->
				</form>
			</div>
			
			
			<table class="layui-table itable">
			  
			  <thead>
			    <tr>
			     
			    </tr> 
			  </thead>
			  <tbody>
			    
			  </tbody>
			</table>
			
			
			
			
			<%-- <table class="itable" >
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
			</table> --%>
			<div id="pages"></div>
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
				var limits = sql.LIMITS;
				var inserts = sql.INSERTS;
				var updates = sql.UPDATES;
				var comments = data.comments;
				var explain = '';
				var html = "<input type='hidden' name='sqlid' value='"+id+"'/>";
				cou = data.cou;
				for(var i in comments ){
					explain+=comments[i].COLUMN_NAME+' : '+comments[i].COMMENTS+'\n'
				} 
				
				hides();
				
				if(limits!=null){//加载权限
					var lss = limits.split("|")
					for(var i in lss){
						$("#"+lss[i]).show();
					}
	
				
					if(limits.indexOf("SE")>=0){//具备查询权限
						for(var w in wheres){
							//-----------非必要参数---------------------
							var pattern = wheres[w].pattern;
							pattern = pattern==null||pattern==undefined?"":" pattern='"+pattern.substring(1,pattern.length-1)+"' ";
							
							var required = wheres[w].required;
							required = required==null||required==undefined?"":" required ";
							
							var title = wheres[w].title;
							title = title==null||title==undefined?"":" title='"+title+"' ";
						//------------------------------------------
		
							/* if(wheres[w].type=='date'||wheres[w].type=='datestr'){
								
								html+="<input type='text' class='date'  name='"+wheres[w].name+"' placeholder='"+wheres[w].explain+"' "+pattern+required+title+"/>";
							}else  */
							if(wheres[w].type=="select"){
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
								html+="<input type='"+wheres[w].type+"' name='"+wheres[w].name+"' placeholder='"+wheres[w].explain+"' "+pattern+required+title+" />";
							}
						}
						
					//	$("#sql").val(sql.SQLTEXT);
						$("#wheres_content").val(JSON.stringify(wheres,null,4));
						$("#wheres_content").attr("onchange","updateWheres("+id+")")

						
					}
					
					
					if(limits.indexOf("IN")>=0){ //加载添加的数据
						
						if(inserts!=null&&inserts!=undefined){
							insstr = "<form id='form_in' onsubmit='return submit_in();'><table>";
							insstr = toForm(insstr,inserts);
							insstr+="<tr><td><input class='btn-yellow' type='submit' value='提交'/></td><td><input class='btn-green' type='reset' value='重置'/></td></tr></table></form>"
						
							//upstr = "<form id='form_up' onsubmit='return submit_up();'><table>";
							//upstr+="<tr><td><input class='btn-yellow' type='submit' value='提交'/></td><td><input class='btn-green' type='reset' value='重置'/></td></tr></table></form>"
						}
						//alert(insstr);
					}
					
					if(limits.indexOf("UP")>=0){ //加载添加的数据
						
						if(updates!=null&&updates!=undefined){						
							upstr = "<form id='form_up' onsubmit='return submit_up();'><table><tr><td><input type='hidden' name='rid'></td></tr>";
							upstr = toForm(upstr,updates);
							upstr+="<tr><td><input class='btn-yellow' type='submit' value='提交'/></td><td><input class='btn-green' type='reset' value='重置'/></td></tr></table></form>";
						}
						//alert(insstr);
					}
					
					
					$("#whrers").html(html);
					$("#explain").html(explain);
					$("#proname").text(sql.NAME);
					
					
					//同时绑定多个
					lay('input[type=datetime],input[type=datetimestr]').each(function(){laydate.render({elem: this,trigger: 'click',type: 'datetime'});});
					lay('input[type=dates],input[type=datestr]').each(function(){laydate.render({elem: this,trigger: 'click'});});

					
					//laydate.render({elem: 'input[type=datetime],input[type=datetimestr]',type: 'datetime'})
					//laydate.render({elem: 'input[type=dates],input[type=datestr]'});//加载事件控件
				}			
				
			},"JSON")
		}
	}
	
	
	function toForm(insstr,inserts){
		for(var i in inserts){
			//-----------非必要参数---------------------
			var pattern = inserts[i].pattern;
			pattern = pattern==null||pattern==undefined?"":" pattern='"+pattern.substring(1,pattern.length-1)+"' ";
			
			var required = inserts[i].required;
			required = required==null||required==undefined?"":" required ";
			
			var title = inserts[i].title;
			title = title==null||title==undefined?"":" title='"+title+"' ";
			
			var index = inserts[i].index;
			index = index==null||index==undefined?"":" data-index='"+index+"' ";
		//------------------------------------------
			
			var rest = pattern+required+title+index;
			
			insstr += "<tr><td>"+inserts[i].explain+":</td><td>";
		/* 	if(inserts[i].type=='date'||inserts[i].type=='datestr'){
				
				//insstr+="<input type='text' class='date'  name='"+inserts[i].name+"' placeholder='"+inserts[i].explain+"' "+rest+"/>";
			}else  */
				
			if(inserts[i].type=="select"){
				insstr+="<select id='"+inserts[i].name+"' name='"+inserts[i].name+"' > <option value=''>"+inserts[i].explain+"</option>"
				var select = inserts[i].select;
				for(var s in select){
					for(var key in select[s]){
						var value = select[s][key];
						insstr+="<option value='"+value+"'>"+key+"</option>"
						//alert("key:"+key+",,value:"+value)
					}
				}
				insstr+="</select>"
			}else{
				insstr+="<input type='"+inserts[i].type+"' name='"+inserts[i].name+"' placeholder='"+inserts[i].explain+"' "+rest+" />";
			} 
			
			insstr += "</td></tr>";
		}
		
		return insstr;
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
	

	var size=20;

	$(function(){

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
		    location.href='curd/expBus?'+p
			
		});
		
		$('.csv').click(function(){
			   var t = $('form').serializeArray();
			    var p = '';
			    for(var i in t){
			    	p+=t[i].name+"="+t[i].value+"&";
			    }
			    p = p.substring(0, p.length-1)
			    location.href='curd/expBusToCsv?'+p
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
