<%@ page import="com.SpringOS.util.MessageKey" %>
<%@page language="java" contentType="text/html; charset=utf-8"
        pageEncoding="utf-8"%>

<%--<%@ taglib uri="/MyTag" prefix="i"%>--%>
<%--<%pageContext.setAttribute("AppId",request.getServletPath());%>--%>
<%--<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>--%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <%@ include file="/static/layout/layout_system.jsp"%>
</head>
<style>
    .e-icon{
        top: 6px;
    }
</style>
<%-- 调用方法script --%>
<script>

</script>
<body>
<table id="table-role" title="<i:info name='角色管理'/>" style="width:100%;height:400px"></table>
<div id="dialog-role" title="" class="easyui-dialog" style="width:500px;height:400px;"
     data-options="left:360,top:70,closed:true,iconCls:'icon-save',resizable:false,modal:true,buttons:button_dialog">
    <form id="form1" method="post">
        <input type="hidden" id="id" name="id" value=""/>
        <input type="hidden" id="available" name="available" value="0"/>
        <%--<input type="hidden" id="roleId" name="roleId" value="1"/>--%>
        <table style="width: 100%;height:70px;">
            <tr>
                <td><i:info name='角色'/></td>
                <td><input class="easyui-textbox" type="text" id="role" name="role" data-options="required:true"/></td>
                <td><i:info name='名称'/></td>
                <td><input class="easyui-textbox" id="description" name="description" data-options="required:true"/></td>
            </tr>
            <tr>
                <td><i:info name='是否锁定'/></td>
                <td>
                    <a id="btn" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'e-icon icon-ok',toggle:true,selected:false" onclick="clickLife()">
                        <i:info name='未锁定'/>
                    </a>
                </td>
            </tr>
        </table>
        <div style="margin:10px 0 10px 0;"/>
    </form>
    <%-- 表单选择插件 --%>
    <%--<div class="easyui-panel" title="角色选择" style="height:236px;"  data-options="border:false">--%>
        <%--<ul id="role_tree"></ul>--%>
    <%--</div>--%>
</div>
</body>
<%--初始化Html Script--%>
<script>
    $(function() {
        //初始化treegrid数据
        $('#table-role').datagrid({
            title:'<i:info name='角色编辑' />',
            url: '/role/date_grid.json',
            loadMsg:"<i:info name='数据加载中...'/>",
            fitColumns:true,
//            striped:true,
            rownumbers:true,
            singleSelect:true,
            toolbar:toolbar,
            columns: [[
                {field: 'id', title: 'id', width: 80, hidden:true},
                {field: 'role', title: 'role', width: 80},
                {field: 'description', title: 'description', width: 220},
                {field: 'available', title: 'available', width: 60}
            ]],
            onDblClickRow:function(data){
                actionOver("edit")
            }
        });

        //初始化选择插件

    });

    //定义treegrid工具栏
    var toolbar = [{
        text:"<i:info name='添加'/>",
        <shiro:lacksPermission name="role:save:add">disabled:true,</shiro:lacksPermission>
        iconCls: 'e-icon icon-plus',
        handler: function(){
            actionOver("add");
        }
    },{
        text:"<i:info name='修改'/>",
        <shiro:lacksPermission name="role:save:edit">disabled:true,</shiro:lacksPermission>
        iconCls: 'e-icon icon-pencil',
        handler: function(){
            actionOver("edit");
        }
    },{
        text:"<i:info name='删除'/>",
        <shiro:lacksPermission name="role:delete">disabled:true,</shiro:lacksPermission>
        iconCls: 'e-icon icon-remove',
        handler: function(){
            actionOver("delete");
        }
    },'-'];

//定义dialog对话框按钮
    var button_dialog = [{
        text:'<i:info name='确认'/>',
        iconCls: 'e-icon icon-ok',
        handler: function(){
            submitApply();
            parent._left_TreeReload();
        }
    },{
        text:'<i:info name='取消'/>',
        iconCls: 'e-icon icon-remove',
        handler:function(){
            actionOver("colse");
        }
    }];

//定义页面操作集合
    function actionOver(code){
        switch(code){
            case "colse":
                $('#dialog-role').dialog('close');
                break;
            case "reload":
                $('#table-role').datagrid("reload");
                break;
            case "add":
                <shiro:hasPermission name="role:save:add">
                addChangeroleTree();
                $('#dialog-role').dialog({title: "<i:info name='新增角色'/>"});
                $('#dialog-role').dialog('open');
                break;
                </shiro:hasPermission>
            case "edit":
                <shiro:hasPermission name="role:save:edit">
                if(editChangeroleTree()) {
                    $('#dialog-role').dialog({title: "<i:info name='修改角色'/>"});
                    $('#dialog-role').dialog('open');
                }else{
                    $.messager.alert('Warning',"<i:info name='请选择...'/>");
                }
                break;
                </shiro:hasPermission>
            case "delete":
                <shiro:hasPermission name="role:delete">
                deleteRow();
                break;
                </shiro:hasPermission>
            default:
                return;
        }
    }
</script>
<script>
    //form1确认提交
    function submitApply(){
        $('#form1').form('submit', {
            url:"role/save",
            onSubmit: function(){

            },
            success:function(data){
                var data = JSON.parse(data);
                if(data.success) {
                    actionOver("reload");
                    actionOver("colse");
                }else{
                    $.messager.alert('Warning',data.message);
                }
            }
        });
    }

    //修改treegrid数据
    function editChangeroleTree(){
        return ChangeroleTree(false);
    }

    function addChangeroleTree(){
        ChangeroleTree(true);
    }

    function ChangeroleTree(idFlag){
        var row = $('#table-role').datagrid('getSelected');
        if(row != null) {//有选择资料,将资料值初始化到表单
            if(idFlag){//新增时id制空
                row.id = "";
            }
            setFormRow(row);
            return true;
        }else{//未选择资料,默认初始化值
            setFormRow();
            return false;
        }
    }
    //删除treegrid数据
    function deleteRow() {
        var row = $('#table-role').treegrid('getSelected');
        if (row != null) {
            $.ajax({
                url: "role/delete?id=" + row.id,
                type: "POST",
                success: function (data) {
                    if (data.success) {
                        actionOver("reload");
                        actionOver("colse");
                    } else {
                        $.messager.alert('Warning', data.message);
                    }
                }
            });
        } else {
            $.messager.alert('Warning', "<i:info name='请选择...'/>");
        }
    }

    //开关按钮点击切换
    function clickLife(){
        if($("#btn").linkbutton("options").selected){
            changeLife(0);
        }else{
            changeLife(1);
        }
    }
    //开关按钮赋值
    function changeLife(val){
        if(val){
            $("#btn").linkbutton({
                selected:true,
                text:"<i:info name='已锁定'/>",
                iconCls:'e-icon icon-ban-circle'
            });
            $("#available").val(1);
        }else{
            $("#btn").linkbutton({
                selected:false,
                text:"<i:info name='未锁定'/>",
                iconCls:'e-icon icon-ok'
            });
            $("#available").val(0);
        }
    }
    //表单赋值
    function setFormRow(data){
        if(data == null){
            $("#id").val("");
            $("#role").textbox("setValue","");
            $("#description").textbox("setValue","");
            changeLife(0);
        }else{
            $("#id").val(data.id);
            $("#role").textbox("setValue",data.role);
            $("#description").textbox("setValue",data.description);
            changeLife(data.available);
        }
    }
</script>
</html>