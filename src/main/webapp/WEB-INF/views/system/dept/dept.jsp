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
<table id="table-dept" title="<i class='icon-save'/><i:info name='机构管理'/>&nbsp;" style="width:100%;height:400px"></table>
<div id="dialog-dept" title="" class="easyui-dialog" style="width:500px;height:400px;"
     data-options="left:360,top:70,closed:true,resizable:false,modal:true,buttons:button_dialog">
    <form id="form1" method="post">
        <input type="hidden" id="id" name="id" value=""/>
        <input type="hidden" id="life" name="life" value="1"/>
        <input type="hidden" id="parentid" name="parentid" value="1"/>
        <input type="hidden" id="name_old" name="parentid" value=""/>
        <table style="width: 100%;height:70px;">
            <tr>
                <td><i:info name='机构编号'/></td>
                <td><input class="easyui-textbox" type="text" id="code" name="code" data-options="required:true"/></td>
                <td><i:info name='机构名称'/></td>
                <td><input class="easyui-textbox" type="text" id="name" name="name" data-options="required:true"/></td>
            </tr>
            <tr>
                <td><i:info name='备注'/></td>
                <td><input class="easyui-textbox" type="text" id="remark" name="remark" /></td>
                <td><i:info name='是否启用'/></td>
                <td>
                    <a id="btn" href="#" class="easyui-linkbutton"
                       data-options="toggle:true,selected:true" onclick="clickLife()">
                        <i:info name='启用'/></a>
                </td>
            </tr>
        </table>
        <div style="margin:10px 0 10px 0;"/>
    </form>
    <div class="easyui-panel" title="<i:info name='上级目录'/>" style="height:236px;"  data-options="border:false">
        <ul id="dept_tree"></ul>
    </div>
</div>
</body>
<%--初始化Html Script--%>
<script>
    $(function() {
        //初始化treegrid数据
        $('#table-dept').treegrid({
            title:"<i class='icon-save'/>&nbsp;<i:info name='机构管理'/>",
            url: '/dept/date_treegrid.json',
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
                {field: 'code', title: "<i:info name='菜单编号'/>", width: 60, align: 'right'},
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
        $('#dept_tree').tree({
            url:"/dept/tag/dept_tree.json?parentid=-1",
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
                var row = $('#table-dept').treegrid('find',data.id);
                if(row) {
                    $("#code").textbox("setValue", row.code);
                }

                $('#parentid').val(data.id);
            }
        });
    });

    //定义treegrid工具栏
    var toolbar = [{
        text:"<i class='icon-plus'/>&nbsp;<i:info name='新增'/>",
        <shiro:lacksPermission name="dept:save:add">disabled:true,</shiro:lacksPermission>
//        iconCls: 'e-icon icon-plus',
        handler: function(){
            actionOver("add");
        }
    },{
        text:"<i class='icon-pencil'/>&nbsp;<i:info name='修改'/>",
        <shiro:lacksPermission name="dept:save:add">disabled:true,</shiro:lacksPermission>
//        iconCls: 'e-icon icon-pencil',
        handler: function(){
            actionOver("edit");
        }
    },{
        text:"<i class='icon-remove'/>&nbsp;<i:info name='删除'/>",
        <shiro:lacksPermission name="dept:save:add">disabled:true,</shiro:lacksPermission>
//        iconCls: 'e-icon icon-remove',
        handler: function(){
            actionOver("delete");
        }
    },'-'];

//定义dialog对话框按钮
    var button_dialog = [{
        text:"<i class='icon-ok'/>&nbsp;<i:info name='确认'/>",
//        iconCls: 'e-icon icon-ok',
        handler: function(){
            submitApply();
        }
    },{
        text:"<i class='icon-remove'/>&nbsp;<i:info name='取消'/>",
//        iconCls: 'e-icon icon-remove',
        handler:function(){
            actionOver("colse");
        }
    }];

//定义页面操作集合
    function actionOver(code){
        switch(code){
            case "colse":
                $('#dialog-dept').dialog('close');
                break;
            case "reload":
                $('#table-dept').treegrid("reload");
                $('#dept_tree').tree("reload");
                break;
            case "deptReload":
                refush_dept();
                break;
            case "add":
                <shiro:hasPermission name="dept:save:add">
                addChangedeptTree();
                $('#dialog-dept').dialog({title: "<i:info name='新增菜单'/>"});
                $('#dialog-dept').dialog('open');
                break;
                </shiro:hasPermission>
            case "edit":
                <shiro:hasPermission name="dept:save:edit">
                if(editChangedeptTree()) {
                    $('#dialog-dept').dialog({title: "<i:info name='资源编辑'/>"});
                    $('#dialog-dept').dialog('open');
                }else{
                    $.messager.alert('Warning',"<i:info name='请选择...'/>");
                }
                break;
                </shiro:hasPermission>
            case "delete":
                <shiro:hasPermission name="dept:save:delete">
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
            url:"dept/save",
            onSubmit: function(){

            },
            success:function(data){
                var data = JSON.parse(data);
                if(data.success) {
                    actionOver("reload");
                    actionOver("colse");

                    var name = $("#name_old").val()==""?$("#name").val():$("#name_old").val();
                    //左侧菜单刷新
                    parent._left_TreeReload();
                    //收藏夹刷新
                    parent.refush_favarite();

                    if (parent.$('#body').tabs('exists', name)){
                        dept_list = JSON.parse(parent.getCookie("dept_LIST"));
                        var jsonObj = parent.getJsondept(dept_list, name);
                        parent.ajax_getTreeTagById(jsonObj.id);
                        parent.$('#body').tabs('close', name);
                    }
                }else{
                    $.messager.alert('Warning',data.message);
                }
            }
        });
    }
    //添加treegrid数据
    function addChangedeptTree(){
        var row = $('#table-dept').treegrid('getSelected');
        if(row != null) {//有选择资料,将部分资料值初始化到表单
            row.parentid = row.id;
            row.id = "";
            row.life = true;
            row.remark = "";
            row.name = "";

            deptTreeSelect(row.parentid);
            setFormRow(row);
        }else{//未选择资料,默认初始化值
            deptTreeSelect();
            setFormRow();
        }
    }
    //修改treegrid数据
    function editChangedeptTree(){
        var row = $('#table-dept').treegrid('getSelected');
        if(row != null) {//有选择资料,将资料值初始化到表单
            deptTreeSelect(row.parentid);
            setFormRow(row);
            $('#dialog-dept').dialog({title: "<i:info name='请选择...'/>"});
            $('#dialog-dept').dialog('open');
            return true;
        }else{//未选择资料,默认初始化值
            return false;
        }
    }
    //删除treegrid数据
    function deleteRow() {
        var row = $('#table-dept').treegrid('getSelected');
        if (row != null) {
            $.ajax({
                url: "dept/delete?id=" + row.id,
                type: "POST",
                success: function (data) {
                    if (data.success) {
                        actionOver("reload");
                        actionOver("colse");
                        //左侧菜单刷新
                        parent._left_TreeReload();
                        //收藏夹刷新
                        parent.change_favarite(row.name, false);
                        if (parent.$('#body').tabs('exists', row.name)) {
                            parent.$('#body').tabs('close', row.name);
                        }
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
    function deptTreeSelect(id){
        var tag = id && $('#dept_tree').tree('find', id) != null?$('#dept_tree').tree('find', id):$('#dept_tree').tree('getRoot');//无id默认到根目录
        if(tag) {
            $('#dept_tree').tree('select', tag.target);
            $('#dept_tree').tree('expandTo', tag.id);

            $('#parentid').val(tag.id);
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
                text:"<i class='icon-ok'/>&nbsp;<i:info name='启用'/>",
//                iconCls:'e-icon icon-ok'
            });
            $("#life").val(1);
        }else{
            $("#btn").linkbutton({
                selected:false,
                text:"<i class='icon-ban-circle'/>&nbsp;<i:info name='禁用'/>",
//                iconCls:'e-icon icon-ban-circle'
            });
            $("#life").val(0);
        }
    }
    //表单赋值
    function setFormRow(data){
        if(data == null){
            $("#id").val("");
            $("#code").textbox("setValue","");
            $("#name").textbox("setValue","");
            $("#name_old").val("");
            $("#remark").textbox("setValue","");
            changeLife(1);
        }else{
            $("#id").val(data.id);
            $("#code").textbox("setValue",data.code);
            $("#name").textbox("setValue",data.name);
            $("#name_old").val(data.name);
            $("#remark").textbox("setValue",data.remark);
            changeLife(data.life);
        }
    }
</script>
</html>