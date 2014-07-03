/**
 * Created with IntelliJ IDEA.
 * User: hw
 * Date: 13-5-28
 * Time: 下午8:13
 * 下拉框的触发填充事件
 */
function autoCompletion(url,obj){
    ajaxGet({
        url: url,
        ok:function(data){
            var titleList = data.selectList;
            obj.empty();
            obj.append("<option value=''>请选择</option>");
            $.each(titleList, function(i, item){
                obj.append("<option value="+ item.key + " expand="+item.expand+">" + item.value + "</option>");
            });
        },
        fail:function(data){
            alert(data.message);
        }
    });
}