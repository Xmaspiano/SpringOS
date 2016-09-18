<%@ page language="java" pageEncoding="UTF-8" %>
<div id='Loading' style="position: absolute; z-index: 1000; top: 0px; left: 0px;
    width: 100%; height: 100%; background: gray; text-align: center;">
    <h1 style="top: 48%; position: relative;">
        <i class="icon-spinner icon-spin icon-large"></i>
    </h1>
</div>
<script>
    var pc_loading;
    if (pc_loading) clearTimeout(pc_loading);
    pc = setTimeout(loading_close, 300);

    function loading_close(time) {
        if (time==null) time = 1500;
        $("#Loading").fadeOut(time, function () {
                $(this).remove();
        });
    }

</script>