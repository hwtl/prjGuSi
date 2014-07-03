$(function(){
    //切换职系，获取职等列表
    $("#js_serialId").change(function(){
        var html = '<option value="">请选择职等</option>';
        if($(this).val() == ""){
            $("#js_titleId").html(html).val("").trigger("change");
        }
        else{
            var serialId = $.trim($(this).val());

            //新增编辑
            var transferType = $("#newOrgId").find("option:selected").attr("transferType");
            //人事审批
            if(transferType == undefined) {
                transferType = $("#transferType").val();
            }

            $.ajax({
                url:"/titleLevel/queryTitle/"+serialId,//传参传职系id
                dataType:'json',
                success:function(data){
             /*       var data = [ //模拟数据，请用返回的真实数据代替
                        { id:1, text:"1 助理/专员" },
                        { id:2, text:"2 主管" },
                        { id:3, text:"3 经理" }
                    ];*/
                    $.each(data.selectList, function(index, node){
                        //转后台：出行政技术职系、行政管理职系的3职等（含3职等及以下）
                        if(transferType == "201402205" && node.value <= 3) {
                            html += '<option value="'+node.key+'">'+node.value+ ' ' + node.expand + '</option>';
                        //转代理项目出：营销职系（代理部）下的1职等
                        } else if (transferType == "201402204" && node.value <= 1) {
                            html += '<option value="'+node.key+'">'+node.value+ ' ' + node.expand + '</option>';
                        }

                    });
                    $("#js_titleId").html(html).val("").trigger("change");
                }
            });
        }
    });
    //切换职等，获取职级列表、岗位列表
    $("#js_titleId").change(function(){
        var html_level = '<option value="">请选择职级</option>';
        var html_position = '<option value="">请选择</option>';
        if($(this).val() == ""){
            $("#js_levelId").html(html_level).val("").trigger("change");
            $("#js_positionId").html(html_position).val("").trigger("change");
        }
        else{
            var titleId = $.trim($(this).val());
            $.ajax({
                url:"/titleLevel/queryLevel/"+titleId,//传参传职等id
                dataType:'json',
                success:function(data){
                    //生成职级列表
                    $.each(data.selectList, function(index, node){
                        html_level += '<option value="'+node.key+'" userTitle="'+node.expand+'">'+node.value + ' ' + node.expand +'</option>';
                    });
                    $("#js_levelId").html(html_level).val("").trigger("change");
                }
            });
            $.ajax({
                url:"/position/queryPosition/"+titleId,//传参传职等id
                dataType:'json',
                success:function(data){
                    //生成岗位列表
                    $.each(data.selectList, function(index, node){
                        html_position += '<option value="'+node.key+'">'+node.value+'</option>';
                    });
                    $("#js_positionId").html(html_position).val("").trigger("change");
                }
            });
        }
    });
    //切换职级，获取头衔
    $("#js_levelId").change(function(){
        if($(this).val() == ""){
            $("#js_userTitle").val("");
        }
        else{
            var userTitle = $(this).find("option:selected").attr("userTitle");
            $("#js_userTitle").val(userTitle);
        }
    });
});