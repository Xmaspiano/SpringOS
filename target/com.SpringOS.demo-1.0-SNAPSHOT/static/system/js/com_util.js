var SYSTEM_CALEN = {
    current:new Date(),
    fit:true,
    weeks:['周日','周一','周二','周三','周四','周五','周六'],
    months:['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月']
};

var SYSTEM_DATEBOX = {
    panelWidth: 200,
    currentText: '今天',
    closeText: '关闭',
    okText: '确认',
    formatter: function(date){
        var y = date.getFullYear();
        var m = date.getMonth()+1;
        var d = date.getDate();
        return y+'/'+m+'/'+d;
    },
    parser:function(s){
        var t = Date.parse(s);
        if (!isNaN(t)){
            return new Date(t);
        } else {
            return new Date();
        }
    }

};