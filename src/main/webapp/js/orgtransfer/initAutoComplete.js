//自动完成组件配置
var init = {
    isSelect : false
}
function initAutoComplete(params){
    /*  params:
        inputObj    //输入框对象
        keepVessel  //保存选中项额外信息的input对象(通常是隐含域，必须是可写value的对象)
        keepKey     //要保存选中项额外信息的key(如id)
        ignoreDefaultCallback //是否忽略默认回调方法，缺省为false
        callback    //自定义回调函数
        其他        //与autocomplete的参数一一对应
    */
    var ignoreDefaultCallback = params.ignoreDefaultCallback || false;//是否忽略默认回调方法
    //自动完成初始化
    params.inputObj.autocomplete({
        urlOrData: params.urlOrData,
        itemTextKey: params.itemTextKey,
        dataKey: params.dataKey,
        setRequestParams: function(requstParams){
            requstParams.keyword = $.trim(params.inputObj.val());
        },
        selectCallback: function(selectedItem){     //选中选项， 执行回调
            params.inputObj.blur();
            if(!ignoreDefaultCallback){//执行默认回调
                params.keepVessel.val(selectedItem[params.keepKey]); //额外存放的
            }
            if(params.callback){//执行指定回调
                params.callback(selectedItem, params);
            }
            init.isSelect = true;
        }
    });

    //自动完成输入框离开后清空相关值
    params.inputObj.blur(function(){
        init.isSelect = false;
        window.setTimeout(function(){
            if(init.isSelect === false){
                params.inputObj.val("");
                params.keepVessel.val(""); //清空隐藏域中的值
            }
        }, 200);
    });
}