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
<title>用户添加</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		$("body").css({visibility:"visible"});
		$('#save').click(function(){
			var v = $("#roleForm").form("validate");
			if(v){
				$.post('user/add', $("#roleForm").serialize(), function(data){
					if(data.status == 200 ){
						location.href='page/admin_userlist';
					} else {
						$.messager.alert("提示信息", data.msg, "warning");
					}
				});
			}else{
				$.messager.alert("提示信息","请将内容填写完整","warning");
			}
		});
		
		//发送ajax请求，获取所有的角色数据，返回json，在页面中动态构造到checkbox中
		var url = "role/listAjax";
		$.post(url,{},function(data){
			//解析json数据，构造checkbox
			for(var i=0;i<data.length;i++){
				var id = data[i].id;
				var name = data[i].name;
				$("#roleTD").append('<input type="checkbox" value="'+id+'" name="roleIds">'+name);
			}
		});
	});
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
    <div region="center" style="overflow:auto;padding:5px;" border="false">
       <form id="roleForm" method="post" >
           <table class="table-edit"  width="95%" align="center">
           		<tr class="title"><td colspan="4">基本信息</td></tr>
	           	<tr><td>用户名:</td><td><input type="text" name="username" id="username" class="easyui-validatebox" required="true" /></td>
					<td>口令:</td><td><input type="password" name="password" id="password" class="easyui-validatebox" required="true" validType="minLength[5]" /></td></tr>
				<tr class="title"><td colspan="4">其他信息</td></tr>
	           	<tr><td>工资:</td><td><input type="text" name="salary" id="salary" class="easyui-numberbox" /></td>
					<td>生日:</td><td><input type="text" name="birthday" id="birthday" class="easyui-datebox" /></td></tr>
	           	<tr><td>性别:</td><td>
	           		<select name="gender" id="gender" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="男">男</option>
	           			<option value="女">女</option>
	           		</select>
	           	</td>
					<td>单位:</td><td>
					<select name="station" id="station" class="easyui-combobox" style="width: 150px;">
	           			<option value="">请选择</option>
	           			<option value="总公司">总公司</option>
	           			<option value="分公司">分公司</option>
	           			<option value="厅点">厅点</option>
	           			<option value="基地运转中心">基地运转中心</option>
	           			<option value="营业所">营业所</option>
	           		</select>
				</td></tr>
				<tr>
					<td>联系电话</td>
					<td colspan="3">
						<input type="text" name="telephone" id="telephone" class="easyui-validatebox" required="true" />
					</td>
				</tr>
	           	<tr><td>备注:</td><td colspan="3"><textarea style="width:80%"></textarea></td></tr>
	           	<tr><td>选择角色:</td>
		           	<td colspan="3" id="roleTD">
		           	</td>
           </table>
       </form>
	</div>
</body>
</html>