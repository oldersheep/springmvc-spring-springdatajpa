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
<title>角色添加</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function(){
		// 授权树初始化
		var setting = {
			data : {
				key : {
					title : "t"
				},
				simpleData : {
					enable : true
				}
			},
			check : {
				enable : true,
			}
		};
		
		$.ajax({
			url : 'function/listAjax',
			type : 'POST',
			dataType : 'text',
			success : function(data) {
				var zNodes = eval("(" + data + ")");
				$.fn.zTree.init($("#functionTree"), setting, zNodes);
			},
			error : function(msg) {
				alert('树加载异常!');
			}
		});
		
		// 点击保存
		$('#save').click(function(){
			var v = $("#roleForm").form("validate");
			if(v){
				var treeObj = $.fn.zTree.getZTreeObj("functionTree");//获得页面中的ztree对象
				var nodes = treeObj.getCheckedNodes(true);//在提交表单之前将选中的checkbox收集
				var array = new Array();
				for(var i=0;i<nodes.length;i++){
					var id = nodes[i].id;//权限id
					array.push(id);
				}
				var ids = array.join(",");//1,2,3,4,5
				$("input[name=ids]").val(ids);
				
				var v = $("#roleForm").form("validate");
				if(v){
					$.post('role/add', $("#roleForm").serialize(), function(data){
						if(data.status == 200 ){
							location.href='page/admin_role';
						} else {
							$.messager.alert("提示信息", data.msg, "warning");
						}
					});
				}else{
					$.messager.alert("提示信息","请将内容填写完整","warning");
				}
			}
		});
	});
</script>	
</head>
<body class="easyui-layout">
	<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
		<div class="datagrid-toolbar">
			<a id="save" icon="icon-save" class="easyui-linkbutton" plain="true" >保存</a>
		</div>
	</div>
	<div region="center" style="overflow:auto;padding:5px;" border="false">
		<form id="roleForm" method="post">
			<table class="table-edit" width="80%" align="center">
				<tr class="title">
					<td colspan="2">角色信息<input type="hidden" name="ids"></td>
				</tr>
				<tr>
					<td width="200">编码</td>
					<td>
						<input type="text" name="code" class="easyui-validatebox" data-options="required:true" />						
					</td>
				</tr>
				<tr>
					<td>名称</td>
					<td><input type="text" name="name" class="easyui-validatebox" data-options="required:true" /></td>
				</tr>
				<tr>
					<td>描述</td>
					<td>
						<textarea name="description" rows="4" cols="60"></textarea>
					</td>
				</tr>
				<tr>
					<td>授权</td>
					<td>
						<ul id="functionTree" class="ztree"></ul>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>