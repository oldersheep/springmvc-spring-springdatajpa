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
<title>角色列表页</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		// 数据表格属性
		$("#grid").datagrid({
			iconCls : 'icon-forward',
			pageList: [10,20,30],
			pagination : true,
			fit : true,
			toolbar : [{
				id : 'add',
				text : '添加角色',
				iconCls : 'icon-add',
				handler : function(){
					location.href='page/admin_roleadd';
				}
			}],
			url : 'role/list',
			columns : [[
				{
					field : 'id',
					title : '编号',
					width : 260
				},
				{
					field : 'name',
					title : '名称',
					width : 200
				},
				{
					field : 'code',
					title : '编码',
					width : 260
				},
				{
					field : 'description',
					title : '描述',
					width : 200
				} 
			]]
		});
	});
</script>	
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<table id="grid"></table>
	</div>
</body>
</html>