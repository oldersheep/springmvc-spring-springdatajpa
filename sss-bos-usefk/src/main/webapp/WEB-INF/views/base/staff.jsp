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
<title>取派员页面</title>
<jsp:include page="/commons/common.jsp"></jsp:include>
<script type="text/javascript">
	function doAdd(){
		$('#staffInfoWindow').window("open");
		$('#staffForm').form("clear");
	}
	
	function doView(){
		$('#staffQueryWindow').window("open");
		$('#queryForm').form("clear");
	}
	
	function doDelete(){
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			$.messager.alert("提示信息","请选择要作废的行","warning");
		} else {
			$.messager.defaults = { ok: "是", cancel: "否" };
			$.messager.confirm('确认信息', '确定要作废这些信息?', function(r){
				if (r){
					var array = new Array();
					for(var i =0; i< rows.length;i++){
						array.push(rows[i].id);
					}
					var ids = array.join(",");
					$.post('staff/delete', {"ids":ids}, function(data){
						if(data.status == 200 ){
							$.messager.alert("提示信息", "作废成功！！" ,"info");
							$("#grid").datagrid('reload');
							$('#grid').datagrid('clearSelections');
						} else {
							$.messager.alert("提示信息", "作废失败！！" ,"warning");
						}
					});
				} else {
					$('#grid').datagrid('clearSelections');
				}
			});
		}
	}
	
	// 还原
	function doRestore(){
		var rows = $("#grid").datagrid("getSelections");
		if(rows.length == 0){
			$.messager.alert("提示信息","请选择要还原的行","warning");
		} else {
			var array = new Array();
			for(var i =0; i< rows.length;i++){
				array.push(rows[i].id);
			}
			var ids = array.join(",");
			$.post('staff/restore', {"ids":ids}, function(data){
				if(data.status == 200 ){
					$.messager.alert("提示信息", "恢复成功！！" ,"info");
					$("#grid").datagrid('reload');
					$('#grid').datagrid('clearSelections');
				} else {
					$.messager.alert("提示信息", "恢复失败！！" ,"warning");
				}
			});
		}
	}
	//工具栏
	var toolbar = [ {
		id : 'button-view',	
		text : '查询',
		iconCls : 'icon-search',
		handler : doView
	}, {
		id : 'button-add',
		text : '增加',
		iconCls : 'icon-add',
		handler : doAdd
	}, {
		id : 'button-delete',
		text : '作废',
		iconCls : 'icon-cancel',
		handler : doDelete
	},{
		id : 'button-save',
		text : '还原',
		iconCls : 'icon-undo',
		handler : doRestore
	}];
	// 定义列
	var columns = [ [ {
		field : 'id',
		checkbox : true,
	},{
		field : 'name',
		title : '姓名',
		width : 120,
		align : 'center'
	}, {
		field : 'telephone',
		title : '手机号',
		width : 120,
		align : 'center'
	}, {
		field : 'haspda',
		title : '是否有PDA',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="1"){
				return "有";
			}else{
				return "无";
			}
		}
	}, {
		field : 'deltag',
		title : '是否作废',
		width : 120,
		align : 'center',
		formatter : function(data,row, index){
			if(data=="0"){
				return "正常使用"
			}else{
				return "已作废";
			}
		}
	}, {
		field : 'standard',
		title : '取派标准',
		width : 120,
		align : 'center'
	}, {
		field : 'station',
		title : '所属单位',
		width : 200,
		align : 'center'
	} ] ];
	
	$(function(){
		// 先将body隐藏，再显示，不会出现页面刷新效果
		$("body").css({visibility:"visible"});
		
		// 取派员信息表格
		$('#grid').datagrid( {
			iconCls : 'icon-forward',
			fit : true,
			border : false,
			rownumbers : true,
			striped : true,
			pageList: [15,30,50],
			pagination : true,
			toolbar : toolbar,
			url : "staff/list",
			idField : 'id',
			columns : columns,
			onDblClickRow : doDblClickRow
		});
		
		// 添加取派员窗口
		$('#staffInfoWindow').window({
	        title: '添加/修改取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 400,
	        resizable:false
	    });
		
		$("#save").click(function(){
			var flag = $("#staffForm").form("validate");
			if(flag){
				$.post('staff/save', $("#staffForm").serialize(), function(data){
					if(data.status == 200 ){
						$("#staffInfoWindow").window("close");
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
		
		// 查询取派员窗口
		$('#staffQueryWindow').window({
	        title: '查询取派员',
	        width: 400,
	        modal: true,
	        shadow: true,
	        closed: true,
	        height: 250,
	        resizable:false
	    });
		
		// 查询
		$("#query").click(function(){
			var p = $("#queryForm").serializeJson();//{id:xx,name:yy,age:zz}
			//重新发起ajax请求，提交参数
			$("#grid").datagrid("load",p);
			//关闭查询窗口
			$("#staffQueryWindow").window("close");
			
		});
		
		// 定义手机号的校验规则
		var phoneReg = /^1[3|4|5|7|8|9][0-9]{9}$/;
		$.extend($.fn.validatebox.defaults.rules, {
			phonenumber: {
				validator: function(value, param){
					return phoneReg.test(value);
				},
				message: '手机号输入有误！'
			}
		});
	});

	// 双击打开编辑窗口
	function doDblClickRow(rowIndex, rowData){
		$('#staffInfoWindow').window("open");
		// 数据回显
		$("#staffInfoWindow").form("load",rowData);
	}
</script>	
</head>
<body class="easyui-layout" style="visibility:hidden;">
	<div region="center" border="false">
    	<table id="grid"></table>
	</div>
	<div class="easyui-window" title="" id="staffInfoWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="save" icon="icon-save" class="easyui-linkbutton" plain="true" >保存</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="staffForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">收派员信息<input type=hidden name="id"/></td>
					</tr>
					<!-- TODO 这里完善收派员添加 table -->
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox" required="true" data-options="validType:'phonenumber'"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox" required="true"/></td>
					</tr>
					<tr>
						<td colspan="2">
						<input type="checkbox" name="haspda" value="1" />
						是否有PDA</td>
					</tr>
					<tr>
						<td>取派标准</td>
						<td>
							<input type="text" name="standard" class="easyui-validatebox" required="true"/>  
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	
	<!-- 查询方法 -->
	<div class="easyui-window" title="查询窗口" id="staffQueryWindow" collapsible="false" minimizable="false" maximizable="false" style="top:20px;left:200px">
		<div region="north" style="height:31px;overflow:hidden;" split="false" border="false" >
			<div class="datagrid-toolbar">
				<a id="query" icon="icon-search" class="easyui-linkbutton" plain="true">查询</a>
			</div>
		</div>
		<div region="center" style="overflow:auto;padding:5px;" border="false">
			<form id="queryForm">
				<table class="table-edit" width="80%" align="center">
					<tr class="title">
						<td colspan="2">查询菜单</td>
					</tr>
					<tr>
						<td>姓名</td>
						<td><input type="text" name="name" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>手机</td>
						<td><input type="text" name="telephone" class="easyui-validatebox"/></td>
					</tr>
					<tr>
						<td>单位</td>
						<td><input type="text" name="station" class="easyui-validatebox"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>	