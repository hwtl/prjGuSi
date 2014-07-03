/**
 * 构造 ajaxGet, ajaxPost, ajaxDelete, ajaxPut方法
 * 在jquery ajax 基础的再次封装，返回值只接收json格式的
 * demo:
 * ajaxDelete({
 *      url:url,
 *      ok: function(data) {
 *          alert("ok");
 *      },
 *
 *      fail: function(data){
 *          alert(data.message);
 *      }
 *  });
 *
 * @type {Array}
 */
var methods = ["ajaxGet", "ajaxPost", "ajaxDelete", "ajaxPut"];
for(var i = 0; i < methods.length; i ++) {
    (function(_i){
        var method = methods[_i];
        window[methods[_i]] = function(options) {
            $.ajax({
                type:  methods[_i].replace("ajax", ""),
                url:   options.url,
                cache: options.cache || false,
                data:  options.data  || undefined,
                async: options.async == undefined ? true : options.async,
                dataType:'json',

                success:function (data) {
                    var status = data.status;
                    if(status == 'ok') {
                        options.ok(data);
                    } else if (status == 'fail') {
                        options.fail(data);
                    } else {
                        alert(status + " - 你的操作出现异常状态，请重试或报修！");
                    }
                },
                error:function (jqXHR) {
                    errorStatus = jqXHR.status;
                    if(errorStatus == 404) {
                        alert(jqXHR.status + ", 你请求的操作不存在！");
                    } else if (errorStatus == 403) { //未登录
                        //showPopupDiv($('#loginLayer'));
                        return false;
                    } else if (errorStatus == 500) {
                        alert(jqXHR.status + ", 你请求的出现错误了，请重试或报修！");
                    } else {
                        alert(jqXHR.status + ", 你请求的出现错误了，请重试或报修！");
                    }
                },
                complete:function (jqXHR) {
                    if (jqXHR == null)
                        return;
                    if (jqXHR.status == 403) { //未登录
                        showPopupDiv($('#loginLayer'));
                    }
                }
            });
        }
    })(i);
}
