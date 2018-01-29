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
<title>功能权限列表</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		$("#grid").datagrid({
			iconCls : 'icon-forward',
			pageList: [10,20,30],
			pagination : true,
			fit : true,
			toolbar : [{
				id : 'add',
				text : '添加权限',
				iconCls : 'icon-add',
				handler : function(){
					location.href='page/admin_functionadd';
				}
			}],
			url : 'function/list',
			columns : [[
			  {
				  field : 'id',
				  title : '编号',
				  width : 150
			  },
			  {
				  field : 'name',
				  title : '名称',
				  width : 200
			  },  
			  {
				  field : 'description',
				  title : '描述',
				  width : 200
			  },  
			  {
				  field : 'generatemenu',
				  title : '是否生成菜单',
				  width : 100,
				  formatter : function(data){
					  if(data=='1'){
						  return '是';
					  }else{
						  return '否';
					  }
				  }
			  },  
			  {
				  field : 'zindex',
				  title : '优先级',
				  width : 200
			  },  
			  {
				  field : 'page',
				  title : '路径',
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