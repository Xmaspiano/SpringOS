(function($){
    function getCacheContainer(t){
        var view = $(t).closest('div.datagrid-view');
        var c = view.children('div.datagrid-editor-cache');
        if (!c.length){
            c = $('<div class="datagrid-editor-cache" style="position:absolute;display:none"></div>').appendTo(view);
        }
        return c;
    }
    function getCacheEditor(t, field){
        var c = getCacheContainer(t);
        return c.children('div.datagrid-editor-cache-' + field);
    }
    function setCacheEditor(t, field, editor){
        var c = getCacheContainer(t);
        c.children('div.datagrid-editor-cache-' + field).remove();
        var e = $('<div class="datagrid-editor-cache-' + field + '"></div>').appendTo(c);
        e.append(editor);
    }

    var editors = $.fn.datagrid.defaults.editors;
    for(var editor in editors){
        var opts = editors[editor];
        (function(){
            var init = opts.init;
            opts.init = function(container, options){
                var field = $(container).closest('td[field]').attr('field');
                var ed = getCacheEditor(container, field);
                if (ed.length){
                    ed.appendTo(container);
                    return ed.find('.datagrid-editable-input');
                } else {
                    return init(container, options);
                }
            }
        })();
        (function(){
            var destroy = opts.destroy;
            opts.destroy = function(target){
                if ($(target).hasClass('datagrid-editable-input')){
                    var field = $(target).closest('td[field]').attr('field');
                    setCacheEditor(target, field, $(target).parent().children());
                } else if (destroy){
                    destroy(target);
                }
            }
        })();
    }
})(jQuery);