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
<title>区域设置</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	function doAdd(){
		$('#regionInfoWindow').window("open");
		$('#regionForm').form("clear");
	}
	
	function doView(){
		alert("修改...");
	}
	
	function doDelete(){
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			$.messager.alert("提示信息","请选择要删除的行","warning");
		} else {
			$.messager.defaults = { ok: "是", cancel: "否" };
			$.messager.confirm('确认信息', '确定要删除这些信息?', function(r){
				if (r){
					var array = new Array();
					for(var i =0; i< rows.length;i++){
						array.push(rows[i].id);
					}
					var ids = array.join(",");
					$.post('region/delete', {"ids":ids}, function(data){
						if(data.status == 200 ){
							$.messager.alert("提示信息", "删除成功！！" ,"info");
							$("#grid").datagrid('reload');
							$('#grid').datagrid('clearSelections');
						} else {
							$.messager.alert("提示信息", data.msg ,"warning");
						}
					});
				} else {
					$('#grid').datagrid('clearSelections');
				}
			});
		}
	}
	
	//工具栏
	var toolbar = [ {
		id : 'button-edit',	
		text : '修改',
		iconCls : 'icon-edit',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '删除',
		iconCls : 'icon-cancel',
		handler : doDelete
	}, {
		id : 'button-import',
		text : '导入',
		iconCls : 'icon-redo'
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'province',
		title : '省',
		width : 120,
		align : 'center'
	}, {
		field : 'city',
		title : '市',
		width : 120,
		align : 'center'
	}, {
		field : 'district',
		title : '区',
		width : 120,
		align : 'center'
	}, {
		field : 'postcode',
		title : '邮编',
		width : 120,
		align : 'center'
	}, {
		field : 'shortcode',
		title : '简码',
		width : 120,
		align : 'center'
	}, {
		field : 'citycode',
		title : '城市编码',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 收派标准数据表格
		$('#grid').datagrid({
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [15,30,50],
			pagination : true,
			toolbar : toolbar,
			url : "region/list",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加区域窗口
		$('#regionInfoWindow').window({
	        title: '添加修改区域',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		$("#save").click(function(){
			var flag = $("#regionForm").form("validate");
			if(flag){
				$.post('region/save', $("#regionForm").serialize(), function(data){
					if(data.status == 200 ){
						$("#regionInfoWindow").window("close");
						$.messager.alert("提示信息", "保存成功！！" ,"info");
						$("#grid").datagrid('reload');
						$('#grid').datagrid('clearSelections');
					} else {
						$.messager.alert("提示信息", "保存失败！！" ,"warning");
					}
				});
			}else{
				$.messager.alert("提示信息","请按规则填写数据！","warning");
			}
		});
		
		//Excel导入
		$("#button-import").upload({
			action: 'region/importXls',
			name: 'myFile',
			enctype: 'multipart/form-data',
			onComplete: function(data) {
	        	if(data == '1'){
	        		//上传成功
	        		$.messager.alert("提示信息","区域数据导入成功！","info");
	        	}else{
	        		//失败
	        		$.messager.alert("提示信息","区域数据导入失败！","warning");
	        	}
	        	$("#grid").datagrid('reload');
	        }
		});
		
	});

	function doDblClickRow(rowIndex, rowData){
		$('#regionInfoWindow').window("open");
		// 数据回显
		$("#regionInfoWindow").form("load",rowData);
	}
</script>
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="区域添加/修改" id="regionInfoWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="regionForm" method="post">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">区域信息<input type="hidden" name="id"/></td>
					</tr>
					<tr>
						<td>省</td>
						<td><input type="text" name="province" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>市</td>
						<td><input type="text" name="city" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>区</td>
						<td><input type="text" name="district" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>邮编</td>
						<td><input type="text" name="postcode" class="easyui-validatebox" required="true"/></td>
					</tr>
				<!--	
					<tr>
						<td>简码</td>
						<td><input type="text" name="shortcode" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>城市编码</td>
						<td><input type="text" name="citycode" class="easyui-validatebox" required="true"/></td>
					</tr>
				 -->
				</table>
			</form>
		</div>
	</div>
</body>
</html>