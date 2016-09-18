<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="/MyTag" prefix="i"%>
<%pageContext.setAttribute("AppId",request.getServletPath());%>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<script type="text/javascript" src="/static/easyui/script/jquery.min.js"></script>
<%--<script type="text/javascript" src="/static/easyUI/script/json2.js"></script>--%>
<script type="text/javascript" src="/static/easyui/script/jquery.easyui.min.js"></script>
<%--<script type="text/javascript" src="http://www.jeasyui.com/easyui/jquery.easyui.min.js"></script>--%>

<link rel="stylesheet" type="text/css" href="/static/easyui/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="/static/easyui/themes/icon.css">
<link rel="stylesheet" type="text/css" href="/static/easyui/themes/color.css">
<link rel="stylesheet" type="text/css" href="/static/FontAwesome/css/font-awesome.min.css">

<script type="text/javascript" src="/static/system/js/com_util.js"></script>
<style>
    a:focus{outline:none;}
</style>