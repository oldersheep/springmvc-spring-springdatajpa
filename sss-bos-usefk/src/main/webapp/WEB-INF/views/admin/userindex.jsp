<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户左侧查询</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		$("body").css({visibility:"visible"});
		// 注册按钮事件
		$('#reset').click(function() {
			$('#form').form("clear");
		});
		// 注册所有下拉控件
		$("select").combobox( {
			width : 155,
			listWidth : 180,
			editable : true
		});
		// 注册ajax查询
		$('#ajax').click(function() {
			var elWin = $("#list").get(0).contentWindow;
			elWin.$('#grid').datagrid( {
				pagination : true,
				queryParams:$("#form").serializeJson(),
				url : "user/list"
			});
		});
	});
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
    <div region="east" title="查询条件" icon="icon-forward" style="width:180px;overflow:auto;" split="false" border="true" >
		<div class="datagrid-toolbar">	
			<a id="reset" class="easyui-linkbutton" plain="true" icon="icon-reload">重置</a>
		</div>
		
		<form id="form" method="post" >
			<table class="table-edit" width="100%" >				
				<tr><td>
					<b>用户名</b><span class="operator"><a name="username-opt" opt="all"></a></span>
					<input type="text" id="username" name="username"/>
				</td></tr>
				<tr><td>
					<b>性别</b><span class="operator"><a name="gender-opt" opt="all"></a></span>
					<select id="gender" name="gender" >
					    <option value=""></option>
					    <option value="女">女</option>
					    <option value="男">男</option>
					</select>
				</td></tr>
				<tr><td>
					<b>生日</b><span class="operator"><a name="birthday-opt" opt="date"></a></span>
					<input type="text" id="birthday" name="birthday" value="1977-11-11" class="easyui-datebox" /><br/>
					<input type="text" id="_birthday" name="_birthday" value="2000-11-11" class="easyui-datebox" />
				</td></tr>

			</table>
		</form>
		
		<div class="datagrid-toolbar">	
			<a id="ajax" class="easyui-linkbutton" plain="true" icon="icon-search">查询</a>
		</div>
    </div>
    <div region="center" style="overflow:hidden;" border="false">
		<iframe id="list" src="page/admin_userlist" scrolling="no" style="width:100%;height:100%;border:0;"></iframe>
    </div>
</body>
</html>