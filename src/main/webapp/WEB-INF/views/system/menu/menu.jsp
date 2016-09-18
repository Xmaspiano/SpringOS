<%@page language="java" contentType="text/html; charset=utf-8"
        pageEncoding="utf-8"%>

<%--<%@ taglib uri="/MyTag" prefix="i"%>--%>
<%--<%pageContext.setAttribute("AppId",request.getServletPath());%>--%>

<!DOCTYPE html>
<html lang="en">
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
<table id="table-menu" title="<i:info name='菜单管理'/>" style="width:100%;height:400px"></table>
<div id="dialog-menu" title="" class="easyui-dialog" style="width:500px;height:400px;"
     data-options="left:360,top:70,closed:true,iconCls:'icon-save',resizable:false,modal:true,buttons:button_dialog">
    <form id="form1" method="post">
        <input type="hidden" id="id" name="id" value=""/>
        <input type="hidden" id="life" name="life" value="1"/>
        <input type="hidden" id="parentid" name="parentid" value="1"/>
        <table style="width: 100%;height:70px;">
            <tr>
                <td><i:info name='菜单编号'/></td>
                <td><input class="easyui-textbox" type="text" id="appid" name="appid" data-options="required:true"/></td>
                <td><i:info name='菜单名称'/></td>
                <td><input class="easyui-textbox" type="text" id="name" name="name" data-options="required:true"/></td>
            </tr>
            <tr>
                <td><i:info name='备注'/></td>
                <td><input class="easyui-textbox" type="text" id="remark" name="remark" /></td>
                <td><i:info name='是否启用'/></td>
                <td>
                    <a id="btn" href="#" class="easyui-linkbutton"
                       data-options="iconCls:'e-icon icon-ok',toggle:true,selected:true" onclick="clickLife()">
                        <i:info name='启用'/></a>
                </td>
            </tr>
        </table>
        <div style="margin:10px 0 10px 0;"/>
    </form>
    <div class="easyui-panel" title="<i:info name='上级目录'/>" style="height:236px;"  data-options="border:false">
        <ul id="menu_tree"></ul>
    </div>
</div>
</body>
<%--初始化Html Script--%>
<script>
    $(function() {
        //初始化treegrid数据
        $('#table-menu').treegrid({
            title:"<i:info name='菜单管理'/>",
            url: '/menu/date_treegrid.json',
            loadMsg:"<i:info name='数据加载中...'/>",
            idField: 'id',
            treeField: 'name',
            fitColumns:true,
//            striped:true,
            rownumbers:true,
            toolbar:toolbar,
            columns: [[
                {field: 'id', title: "<i:info name='主键'/>", width: 180, hidden:true},
                {field: 'name', title: "<i:info name='菜单名称'/>", width: 180},
                {field: 'appid', title: "<i:info name='菜单编号'/>", width: 60, align: 'right'},
                {field: 'parentid', title: "<i:info name='父菜单ID'/>", width: 80},
                {field: 'remark', title: "<i:info name='备注'/>", width: 80},
                {field: 'life', title: "<i:info name='是否启用'/>", width: 60, align: 'right'},
                {field: 'dateremark', title: "<i:info name='操作时间'/>", width: 80}
            ]],
            onDblClickRow:function(data){
                actionOver("edit")
            }
        });

        //初始化上级目录tree数据
        $('#menu_tree').tree({
            url:"/menu/tag/menu_tree.json?parentid=-1",
            type:"POST",
            lines:true,
            loadFilter: function(data){
                if (data.rows){
                    return data.rows;
                } else {
                    return data;
                }
            },
            onClick:function(data){//tree菜单点击事件
                var row = $('#table-menu').treegrid('find',data.id);
                if(row) {
                    $("#appid").textbox("setValue", row.appid);
                }

                $('#parentid').val(data.id);
            }
        });
    });

    //定义treegrid工具栏
    var toolbar = [{
        text:"<i:info name='新增'/>",
        <shiro:lacksPermission name="menu:save:add">disabled:true,</shiro:lacksPermission>
        iconCls: 'e-icon icon-plus',
        handler: function(){
            actionOver("add");
        }
    },{
        text:"<i:info name='修改'/>",
        <shiro:lacksPermission name="menu:save:add">disabled:true,</shiro:lacksPermission>
        iconCls: 'e-icon icon-pencil',
        handler: function(){
            actionOver("edit");
        }
    },{
        text:"<i:info name='删除'/>",
        <shiro:lacksPermission name="menu:save:add">disabled:true,</shiro:lacksPermission>
        iconCls: 'e-icon icon-remove',
        handler: function(){
            actionOver("delete");
        }
    },'-'];

//定义dialog对话框按钮
    var button_dialog = [{
        text:"<i:info name='确认'/>",
        iconCls: 'e-icon icon-ok',
        handler: function(){
            submitApply();
            parent._left_TreeReload();
        }
    },{
        text:"<i:info name='取消'/>",
        iconCls: 'e-icon icon-remove',
        handler:function(){
            actionOver("colse");
        }
    }];

//定义页面操作集合
    function actionOver(code){
        switch(code){
            case "colse":
                $('#dialog-menu').dialog('close');
                break;
            case "reload":
                $('#table-menu').treegrid("reload");
                $('#menu_tree').tree("reload");
                break;
            case "add":
                <shiro:hasPermission name="menu:save:add">
                addChangeMenuTree();
                $('#dialog-menu').dialog({title: "<i:info name='新增菜单'/>"});
                $('#dialog-menu').dialog('open');
                break;
                </shiro:hasPermission>
            case "edit":
                <shiro:hasPermission name="menu:save:edit">
                if(editChangeMenuTree()) {
                    $('#dialog-menu').dialog({title: "<i:info name='资源编辑'/>"});
                    $('#dialog-menu').dialog('open');
                }else{
                    $.messager.alert('Warning',"<i:info name='请选择...'/>");
                }
                break;
                </shiro:hasPermission>
            case "delete":
                <shiro:hasPermission name="menu:save:delete">
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
            url:"menu/save",
            onSubmit: function(){

            },
            success:function(data){
                var data = JSON.parse(data);
                if(data.success) {
                    actionOver("reload");
                    actionOver("colse");
//                    _left_TreeReload();
                }else{
                    $.messager.alert('Warning',data.message);
                }
            }
        });
    }
    //添加treegrid数据
    function addChangeMenuTree(){
        var row = $('#table-menu').treegrid('getSelected');
        if(row != null) {//有选择资料,将部分资料值初始化到表单
            row.parentid = row.id;
            row.id = "";
            row.life = true;
            row.remark = "";
            row.name = "";

            menuTreeSelect(row.parentid);
            setFormRow(row);
        }else{//未选择资料,默认初始化值
            menuTreeSelect();
            setFormRow();
        }
    }
    //修改treegrid数据
    function editChangeMenuTree(){
        var row = $('#table-menu').treegrid('getSelected');
        if(row != null) {//有选择资料,将资料值初始化到表单
            menuTreeSelect(row.parentid);
            setFormRow(row);
            $('#dialog-menu').dialog({title: "<i:info name='请选择...'/>"});
            $('#dialog-menu').dialog('open');
            return true;
        }else{//未选择资料,默认初始化值
            return false;
        }
    }
    //删除treegrid数据
    function deleteRow() {
        var row = $('#table-menu').treegrid('getSelected');
        if (row != null) {
            $.ajax({
                url: "menu/delete?id=" + row.id,
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
    //选择对话框目录tree并赋值
    function menuTreeSelect(id){
        var tag = id?$('#menu_tree').tree('find', id):$('#menu_tree').tree('getRoot');//无id默认到根目录
        $('#menu_tree').tree('select', tag.target);
        $('#menu_tree').tree('expandTo',tag.id);

        $('#parentid').val(tag.id);
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
                text:"<i:info name='启用'/>",
                iconCls:'e-icon icon-ok'
            });
            $("#life").val(1);
        }else{
            $("#btn").linkbutton({
                selected:false,
                text:"<i:info name='禁用'/>",
                iconCls:'e-icon icon-ban-circle'
            });
            $("#life").val(0);
        }
    }
    //表单赋值
    function setFormRow(data){
        if(data == null){
            $("#id").val("");
            $("#appid").textbox("setValue","");
            $("#name").textbox("setValue","");
            $("#remark").textbox("setValue","");
            changeLife(1);
        }else{
            $("#id").val(data.id);
            $("#appid").textbox("setValue",data.appid);
            $("#name").textbox("setValue",data.name);
            $("#remark").textbox("setValue",data.remark);
            changeLife(data.life);
        }
    }
</script>
</html>