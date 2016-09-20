<%@ page import="com.SpringOS.util.MessageKey" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String loaclPath = "views.system.user.user.";

    String form1_username = MessageKey.getMsg(loaclPath+"form1.username");
    String form1_password = MessageKey.getMsg(loaclPath+"form1.password");
    String dialog_button_apply = MessageKey.getMsg(loaclPath+"dialog.button.apply");
    String dialog_button_cancel = MessageKey.getMsg(loaclPath+"dialog.button.cancel");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <style>.error{color:red;}</style>
    <%@ include file="/static/layout/layout_system.jsp"%>
    <style>
        .e-icon{
            /*top: 6px;*/
            margin-right: 1px;
        }
        .textbox-addon-right{
            /*right: 0px;*/
            margin-top: 4px;
            margin-right: 3px;
        }
    </style>
</head>
<%--<%@ include file="/static/layout/loading_system.jsp"%>--%>
<body>
<div id="dialog-login" title="登陆" class="easyui-dialog" style="width:330px;height:220px;padding:5px;"
     data-options="closed:true,resizable:false,buttons:button_dialog,draggable:false,closable:false">
    <div id="error" class="error" style="height:15px;">${error}</div>
    <form id="form1" action="/login" method="post">
        <table style="width: 100%">
            <tr>
                <td rowspan="3">
                    <img class="easyui-linkbutton" src="/static/system/image/fly_icon.png" style="height: 80px">
                </td>
                <td><%= form1_username%></td>
                <td><input class="easyui-textbox" type="text" id="username" name="username"
                           data-options="prompt:'用户名',iconCls:'icon-user',required:false" value="admin"/></td>
            </tr>
            <tr>
                <td><%= form1_password%></td>
                <td><input class="easyui-textbox" type="password" id="password" name="password"
                           data-options="prompt:'',iconCls:'e-icon icon-lock',required:false" value="admin"/></td>
            </tr>
            <tr>
                <td>自动登录：</td>
                <td><input type="checkbox" id="rememberMe" name="rememberMe" value="true"></td>
            </tr>
            <tr>
                <td colspan="2"><span>用户名:admin; 密码:admin</span></td>
            </tr>
        </table>
    </form>
</div>

</body>
<script>

    //页面渲染完成
    $.parser.onComplete = function(){
        $('#username').textbox('textbox').focus();
        $("#dialog-login").dialog('open');
    }

    $(window).keyup(function(event){
        switch(event.keyCode) {
            case 13://enter
                if($("#username").val() == ""){
                    $("#error").html("用户名不能为空");
                    return false;
                }
                if($("#password").val() == ""){
                    $("#error").html("密码不能为空");
                    return false;
                }
                $("#form1").submit();
                break;
        }
    });

    //定义dialog对话框按钮
    var button_dialog = [{
        text:'<i class="icon-ok icon-large"/>&nbsp;<%= dialog_button_apply%>',
//        iconCls: 'e-icon icon-ok icon-large',
        handler: function(){
            $("#form1").submit();
        }
    }];
</script>
</html>