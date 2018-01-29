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
<title>权限新增页面</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		// 点击保存
		$('#save').click(function(){
			var v = $("#functionForm").form("validate");
			if(v){
				$.post('function/add', $("#functionForm").serialize(), function(data){
					if(data.status == 200 ){
						location.href='page/admin_function';
					} else {
						$.messager.alert("提示信息", data.msg, "warning");
					}
				});
			}else{
				$.messager.alert("提示信息","请将内容填写完整","warning");
			}
		});
	});
</script>	
</head>
<body class="easyui-layout">
<div data-options="region:'north'">
	<div class="datagrid-toolbar">
		<a id="save" icon="icon-save" class="easyui-linkbutton" plain="true" >保存</a>
	</div>
</div>
<div data-options="region:'center'">
	<form id="functionForm">
		<table class="table-edit" width="80%" align="center">
			<tr class="title">
				<td colspan="2">功能权限信息</td>
			</tr>
			<!--
			<tr>
				<td width="200">编号</td>
				<td>
					<input type="text" name="id" class="easyui-validatebox" data-options="required:true" />						
				</td>
			</tr>
			-->
			<tr>
				<td>名称</td>
				<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
			</tr>
			<tr>
				<td>访问路径</td>
				<td><input type="text" name="page"  /></td>
			</tr>
			<tr>
				<td>是否生成菜单</td>
				<td>
					<select name="generatemenu" class="easyui-combobox">
						<option value="0">不生成</option>
						<option value="1">生成</option>
					</select>
				</td>
			</tr>
			<tr>
				<td>优先级</td>
				<td>
					<input type="text" name="zindex" class="easyui-numberbox" data-options="required:true" />
				</td>
			</tr>
			<tr>
				<td>父功能点</td>
				<td>
					<input name="function.id" class="easyui-combobox" data-options="valueField:'id',textField:'name',url:'function/listAjax'"/>
				</td>
			</tr>
			<tr>
				<td>描述</td>
				<td>
					<textarea name="description" rows="4" cols="60"></textarea>
				</td>
			</tr>
		</table>
	</form>
</div>
</body>
</html>