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
<title>流程定义文件上传</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
</head>
<body>
<form action="process/definition/deploy" theme="simple" method="post" 
	  enctype="multipart/form-data" id="uploadForm">
	<table class="table-edit" width="100%" >
		<tr class="title"><td colspan="2">发布新流程</td></tr>
		<tr>
			<td width="200">浏览流程定义zip压缩文件</td>
			<td>
				<input type="file" name="zipFile" />
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<a id="btn" href="javascript:$('#uploadForm').submit();"
					 class="easyui-linkbutton" data-options="iconCls:'icon-save'">发布新流程</a>  
			</td>
		</tr>
	</table>
</form>
</body>
</html>