<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
<title>流程定义列表</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	
	$(function(){
		$("#grid").datagrid({
			iconCls : 'icon-forward',
			border : false,
			rownumbers : true,
			striped : true,
			idField : 'id',
			singleSelect : true,
			fitColumns : true,
			toolbar : [
				{
					id : 'deploy',
					text : '发布新流程',
					iconCls : 'icon-add',
					handler : function(){
						location.href = "page/workflow_processdefinitiondeploy";
					}
				}
			]
		});
	});
	
	//定义删除方法
	function del(id){
		$.messager.confirm("确认信息","你确定删除当前流程定义吗？",function(r){
			if(r){
				//发送请求，删除数据
				window.location.href = 'process/definition/delete?id=' + id;
			}
		});
	}
</script>

</head>
<body class="easyui-layout">
	<div region="center" >
		<table id="grid" class="easyui-datagrid">
			<thead>
	  			<tr>
	  				<th data-options="field:'id'" width="120">流程编号</th>
	  				<th data-options="field:'name'" width="200">流程名称</th>
	  				<th data-options="field:'key'" width="100">流程key</th>
	  				<th data-options="field:'version'" width="80">版本号</th>
	  				<th data-options="field:'viewpng'" width="200">查看流程图</th>
	  			</tr>
	  		</thead>
	  		<tbody>
  				<c:forEach items="${definitionList}" var="definition" varStatus="status">  
					<tr>
	  					<td>${definition.id }</td>
	  					<td>${definition.name }</td>
	  					<td>${definition.key }</td>
	  					<td>${definition.version }</td>
	  					<td>
	  						<a onclick="window.showModalDialog('process/definition/showpng?id=${definition.id }')"
	  							 class="easyui-linkbutton" data-options="iconCls:'icon-search'">
	  							 	查看流程图
	  						</a>
	  						<a onclick="del('${definition.id}')"
	  							 class="easyui-linkbutton" data-options="iconCls:'icon-remove'">
	  							 	删除
	  						</a>
	  					</td>
	  				</tr>
				</c:forEach>
  			</tbody>
		</table>
	</div>
</body>
<script type="text/javascript">
	var deltag = '${deltag}';
	if(deltag == '1'){
		//存在关联数据，不能删除
		$.messager.alert("提示信息","当前流程定义正在使用，不能删除！","warning");
	}
</script>
</html>