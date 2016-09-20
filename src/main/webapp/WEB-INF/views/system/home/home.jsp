<%@ page language="java" pageEncoding="UTF-8" %>
<%@ page import="com.SpringOS.util.MessageKey" %>
<%
    /****
     * 服务器session待探究,写在jsp中不合理
     */

//    String menuTag = request.getAttribute("userTabJson").toString();
//    if(menuTag != null){
//        Cookie menu_list = new Cookie("MENU_LIST", menuTag.split("MENU_LIST=")[1]);
//        response.addCookie(menu_list);
//    }

%>
<!DOCTYPE html>
<html lang="ch">
<head>
    <title>SpringOS</title>
    <%@ include file="/static/layout/layout_system.jsp"%>
</head>
<style>
    .right-float {
        float:right;
    }

    .left-float {
        float:left;
    }
    .hid-overflow {
        /*overflow:scroll;*/
        overflow:hidden;
    }

    .e-icon{
        top: 6px;
        margin-left: 2px;
    }

</style>
<%@ include file="/static/layout/loading_system.jsp"%>
<script>

</script>
<body id="cc" class="easyui-layout">
<div data-options="region:'north',border:false" style="height:50px;padding:0px">
    <div id="numm" class="easyui-meun left-float" style="padding:5px;">
        <img class="easyui-linkbutton" src="/static/system/image/fly_icon.png" style="height: 40px">
        <a href="#" class="easyui-linkbutton" data-options="plain:true" onclick="addTab('<i:info name='首页'/>','/index')"><i:info name='首页'/></a>
        <a id="menuList" href="#" class="easyui-menubutton" data-options="menu:'#mm1'"><i:info name='菜单'/></a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm2'"><i:info name='收藏夹'/></a>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm3'"><i:info name='关于'/></a>
    </div>

    <div class="easyui-meun right-float" style="padding:5px;">
        Hello, <span style="color: #00bbee"><shiro:principal/>&nbsp;</span>
        <%--<a href="#" class="easyui-menubutton" data-options="menu:'#mm5'"><i:info name='系统'/></a>--%>
        <a href="#" class="easyui-menubutton" data-options="menu:'#mm4'"><i:info name='设置'/></a>
        <img class="easyui-linkbutton" src="/static/system/image/fly_icon.png" style="height: 40px">
    </div>

    <div id="mm1" style="width:150px;">

    </div>

    <div id="mm2" style="width:100px;">

    </div>

    <div id="mm3" class="menu-content" style="background:#f0f0f0;padding:10px;text-align:left">
        <%--<img src="http://www.jeasyui.com/images/logo1.png" style="width:150px;height:50px">--%>
        <p style="font-size:14px;color:#444;">Try jQuery EasyUI to build your modern, interactive, javascript applications.</p>
    </div>

    <div id="mm4" style="width:100px;">
        <div onclick="window.location.href = '/logout' "><i:info name='退出'/></div>
    </div>

    <%--<div id="mm5" style="width:100px;">--%>
        <%--<div onclick="addTab('组织机构管理','/system/dept/dept')"><i:info name='收藏'/></div>--%>
    <%--</div>--%>
</div>
<div class="hid-overflow" data-options="region:'west',split:true,collapsed:true,title:'<i:info name='菜单目录'/>'" style="width:200px;">
    <%@ include file="left.jsp"%>
</div>
<div data-options="region:'center',border:false">
    <div id="body" class="easyui-tabs" style="height:100%">
        <%-- tab --%>
        <div class="hid-overflow" title="<i:info name='首页'/>" closable="true">
            <iframe scrolling="auto" frameborder="0"  src="/index" style="width:100%;height:100%;"></iframe>
        </div>

    </div>
</div>
<%--<div data-options="region:'south',border:false" style="height: 23px;">--%>
<%--<div id="div_json" class="footer" style="text-align-all: center">Copyright © RDIFramework.NET V2.9, All Rights Reserved</div>--%>
<%--</div>--%>
</body>
<script type="text/javascript">
    $(function(){
        $(document).bind('contextmenu',function(e){
            e.preventDefault();
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        });

        var menu_list = getCookie("MENU_LIST");

        if(menu_list != null && menu_list != ""){
            var jsonObj = JSON.parse(menu_list);

            for(var i = 0; i<jsonObj.length; i++){
                if(jsonObj[i] != null) {
                    if(i == 0) {
                        addTabByClick(jsonObj[i].name, jsonObj[i].id, jsonObj[i].url, jsonObj[i].parentId, jsonObj[i].parentName);
                    }else{
                        addTab(jsonObj[i].name, jsonObj[i].url);
                    }
                    change_favarite_icon(jsonObj[i].favarite, jsonObj[i].name);
                }
            }
        }

        $('#body').tabs({
            tools:[{
                text:'<i class="icon-star-empty"/>',
                width:26,
//                iconCls: 'e-icon icon-star-empty icon-large',
                handler:function(){
                    var tab = $('#body').tabs('getSelected');
                    clear_favarite(tab);
                }
            },{
                text:'<i class="icon-cogs"/>',
                width:26,
//                iconCls: 'e-icon icon-cogs icon-large',
                handler:function(){
                    alert("洪荒之力用完啦...");
                }
            }],
            onBeforeClose: function(title){
                var jsonObj = JSON.parse(getCookie("MENU_LIST"));
                removeJsonMenu(jsonObj, title);
                save_menu_list(jsonObj);

                if(jsonObj == ''){
                    $('#menuList').menubutton({text: '<i:info name='菜单'/>'});
                    show_menu_data(1);
                }
            },
            onSelect: function(title, index){
                menu_list = JSON.parse(getCookie("MENU_LIST"));
                var jsonObj = getJsonMenu(menu_list, title);
                ajax_getTreeTagById(jsonObj.id);
            }
        });

        if(jsonObj.length == 0) {
            show_menu_data(1);
        }

        refush_favarite();
    });

    //页面渲染完成
    $.parser.onComplete = function(){
        loading_close(1000);
    }

    function clear_favarite(tab){
        if (tab){
            var title = tab.panel('options').title;
            var tab_title = $("li.tabs-selected").find($(".tabs-title")).text();
            var flag = tab_title == title;//判断表头信息和注册表单是否相等
            change_favarite_icon(flag, title);
            change_favarite(title, flag);
        }
    }

    function refush_favarite(){
        //收藏夹初始化
        $.ajax({
            url: "/favarite/menu_favarite_url.json",
            type:"POST",
            success: function(data){
                $('#mm2').empty();
                $.each(data.rows, function(i, item) {
                    $('#mm2').menu('appendItem', {
                        text: item.text,
                        onclick: function(){
                            if(item.children == null){
                                ajax_getTreeTagById(item.id);
                            }
                        }
                    });
                });
            }
        });

    }

    function change_favarite_icon(flag, title){
        if(flag) {
            $("li.tabs-selected").find($(".tabs-title")).html('<i id="title" class="icon-star"/>&nbsp;'+title);
        }else{
            $("li.tabs-selected").find($(".tabs-title")).html(title);
        }
    }

    function change_favarite(title, flag){
        var menu_list = getCookie("MENU_LIST");
        if(menu_list != null && menu_list != ""){
            var jsonObj = JSON.parse(menu_list);

            for(var i = 0; i<jsonObj.length; i++){
                if(jsonObj[i] != null && jsonObj[i].name == title) {
                    jsonObj[i].favarite = flag;

                    $.ajax({
                        url: "/favarite/check",
                        data:{menuid:jsonObj[i].id, title:jsonObj[i].title, flag:jsonObj[i].favarite},
                        type:"POST",
                        success: function(data){
                            refush_favarite();//刷新收藏夹
                        }
                    });
                }
            }
            save_menu_list(jsonObj);
        }
    }

    function addTabByClick(title, id, url, parentId, parentName){
        if(parentName == '<i:info name='首页'/>'){
            $('#menuList').menubutton({text: '<i:info name='菜单'/>'});
        }else {
            $('#menuList').menubutton({text: parentName});
        }

        var favariteFlag = $('#mm2').menu('findItem', title) != null;

        show_menu_data(parentId);
        set_menu_remenber(title, id, url, parentId, parentName, favariteFlag );
        addTab(title, url);

        if(favariteFlag){
            change_favarite_icon(favariteFlag, title);
        }
    }

    function addTab(title, url){
//        parentName = parentName==null?title:parentName;

        if ($('#body').tabs('exists', title)){
            $('#body').tabs('select', title);
        } else {
            var content = '<iframe scrolling="auto" frameborder="0" src="'+url+'" style="width:100%;height:100%;"></iframe>';
            $('#body').tabs('add',{
                title:title,
                content:content,
                fit:true,
                closable:true,
                iconCls:'',
//                toolPosition:'left',
            });
            $('#body').tabs('getTab', title).css('overflow', 'hidden');
        }
    }

    function set_menu_remenber(menu_name, menu_id, menu_url, menu_parentId, menu_parentName, menu_favarite){
        menu_favarite = menu_favarite==null?false:menu_favarite;
        if(getCookie("MENU_LIST") == null || getCookie("MENU_LIST") == ""){
            var jsonObj = [{name:menu_name, id:menu_id, url:menu_url, parentId:menu_parentId, parentName:menu_parentName, favarite:menu_favarite}];
        }else{
            var jsonObj = JSON.parse(getCookie("MENU_LIST"));
            if(getJsonMenu(jsonObj, menu_name) == null){
                jsonObj[jsonObj.length] = {
                    name:menu_name,
                    id:menu_id,
                    url:menu_url,
                    parentId:menu_parentId,
                    parentName:menu_parentName,
                    favarite:menu_favarite
                };
            }else{
                return;
            }
        }

        save_menu_list(jsonObj);
    }

    function set_menu_favarite(menu_index, is_favarite){
        if(getCookie("MENU_FAVARITE") == null || getCookie("MENU_FAVARITE") == ""){
            var jsonObj = [{index:menu_index, favarite:is_favarite}];
        }else{
            var jsonObj = JSON.parse(getCookie("MENU_FAVARITE"));
            if(getJsonMenu(jsonObj, menu_index) == null){
                jsonObj[jsonObj.length] = {
                    index:menu_index,
                    favarite:is_favarite
                };
            }else{
                return;
            }
        }

        save_menu_list(jsonObj);
    }

    function save_menu_list(jsonObj){
        var jsonTab = "MENU_LIST="+JSON.stringify(jsonObj);
        document.cookie= jsonTab;

        $.ajax({
            url: "/home/savetab?jsonTab="+jsonTab,
            type:"POST",
            success: function(data){
            }
        });
    }

    function getJsonMenu(jsonObj,name){
        for(var i = 0; i<jsonObj.length; i++){
            if(jsonObj[i] != null &&jsonObj[i].name == name){
                return jsonObj[i];
            }
        }
        return null;
    }

    function removeJsonMenu(jsonObj,name){
//        var index = -1;
        for(var i = 0; i<jsonObj.length; i++){
            if(jsonObj[i] != null && jsonObj[i].name == name){

                jsonObj.splice(i,1);
                return;
//                delete jsonObj[i];
            }
        }
//        jsonObj.remove()
    }

    function getCookie(c_name)
    {
        if (document.cookie.length>0)
        {
            c_start=document.cookie.indexOf(c_name + "=")
            if (c_start!=-1)
            {
                c_start=c_start + c_name.length+1
                c_end=document.cookie.indexOf(";",c_start)
                if (c_end==-1) c_end=document.cookie.length
                return unescape(document.cookie.substring(c_start,c_end))
            }
        }
        return "[]"
    }

    function show_menu_data(pid){
        pid = pid == undefined?0:pid;
        $.ajax({
            url: "/menu/tag/menu_tree.json?parentid="+pid,
            type:"POST",
            success: function(data){
                $('#mm1').empty();
                set_menu_data(data.rows);
            }
        });
    }

    //第一层
    function set_menu_data(data, target){
        $.each(data, function(i, item) {

            $('#mm1').menu('appendItem', {
                parent:target,
                text: item.text,
                onclick: function(){
                    if(item.children == null){
                        ajax_getTreeTagById(item.id);
                    }
                }
            });

            if(item.children != null){
                var m_it = $('#mm1').menu('findItem', item.text).target;
                set_menu_data(item.children, m_it);
            }
        });
    }

</script>
</html>