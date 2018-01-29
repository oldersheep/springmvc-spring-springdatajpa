<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
<title>正在运行流程实例列表</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	$(function() {
		$("#grid").datagrid({
			rownumbers : true,
			striped : true,
			singleSelect : true,
			nowrap : false,
			fit : true
		});
	});
	function showPng(id) {
// 		window.showModalDialog("process/instance/showpng?id="+ id);
		window.open("process/instance/showpng?id="+ id);
	}
	
</script>
</head>
<body class="easyui-layout">
	<div region="center">
		<table id="grid" class="easyui-datagrid">
			<thead>
				<tr>
					<th data-options="field:'id'" width="60">实例编号</th>
					<th data-options="field:'name'" width="150">流程定义编号</th>
					<th data-options="field:'activity'" width="150">运行到哪个任务</th>
					<th data-options="field:'viewRuntime'" width="500">业务数据</th>
					<th data-options="field:'viewpng'" width="200">查看流程图</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${instanceList}" var="instance" varStatus="status">
					<tr>
						<td>${instance.id }</td>
						<td>${instance.processDefinitionId }</td>
						<td>${instance.activityId }</td>
						<td><div id="div${instance.id }">
							<script>
								//根据流程实例id查询流程变量
								$.post("process/instance/findData", { "id" : ${instance.id}}, function(data) {
									
									$("#div"+${instance.id}).html(data);
								});
							</script>
							</div>
						</td>
						<td><a onclick="showPng('${instance.id}');">查看流程图</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>
</html>