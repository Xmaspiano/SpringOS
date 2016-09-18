<%@ page language="java" pageEncoding="UTF-8" %>
<div id="menu-accordion" class="easyui-accordion" data-options="multiple:false,border:false,selected:false" style="width:100%;height:auto;">
</div>
<script>
    $(function(){
        _left_getMenuDate();
    });

    function _left_getMenuDate(){
        $.ajax({
            url: "/menu/tag/menu_tree.json",
            type:"POST",
            success: function(data){
                $.each(data.rows, function(i, item) {
                    $('#menu-accordion').accordion('add', {
                        title: item.text,
                        content: "<ul id=\""+item.id+"\">",
                        selected: false
                    });
                    if(item.children != null) {
                        parent._left_setTreeDate(item.id, JSON.stringify(item.children));
                    }
                });
            }
        });
    }

    function _left_setTreeDate(id, valTree) {
        var datajson = JSON.parse(valTree);
        $("#"+id).tree({
            animate:true,
            data:datajson,
            onClick: function(node){
                ajax_getTreeTagById(node.id);
            }
        });
    }

    function ajax_getTreeTagById(id){
        $.ajax({
            url: "/menu/tag/menu_tree_url.json?id="+id,
            type:"POST",
            success: function(data){
                addTabByClick(data.title, data.id, data.url, data.parentId, data.parentName);
            }
        });
    }

    function _left_TreeReload(){
//        _left_getMenuDate();
    }
</script>