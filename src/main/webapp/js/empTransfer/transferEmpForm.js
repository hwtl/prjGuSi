$(function(){
    var init = {
        isSelect : false
    }
    
    //员工姓名自动完成初始化
    //可根据姓名、工号查找，搜索结果只包括经纪人
    $('#userName').autocomplete({
        urlOrData: '/transfer/emp/search',
        itemTextKey: 'longName',//longName
        dataKey: 'employeeList',//autoJJR
        crossDomain:false,
        setRequestParams: function(requstParams){
            requstParams.keyword = $.trim($('#userName').val());
        },
        selectCallback: function(selectedItem){     //选中选项， 执行回调
            //验证工号是否可以申请转调
            $.get("/transfer/emp/check/" + selectedItem.userCode, function(data){
               if(data.status == "ok") {
                   $('#userName').blur();
                   init.isSelect = true;
                   //获取提交给后台的员工工号、姓名
                   $("#userCode").val(selectedItem.userCode);//userCode 工号
                   $("#h_userName").val(selectedItem.userNameCn);//userName 姓名
                   $("#oldOrgId").val(selectedItem.orgId);
                   $("#oldLevelId").val(selectedItem.levelId);
                   $("#oldPositionId").val(selectedItem.positionId);
                   $("#oldTitle").val(selectedItem.userTitle);
                   //显示原职等职级
                   $("#level_original").text(selectedItem.titleLevelName);//levelName
                   $("#span_level_original").show();

                   //员工姓名做二次确认
                   userNameDoubleCheck();
               } else {
                   alert(data.message);
               }
            });
        }
    });
    //转调经纪人姓名二次确认
    function userNameDoubleCheck(){//二次确认弹层打开
        $("#confirmName").val("");
        showPopupDiv($('#layer_warning'));
    }
    $("#btnConfirmName").click(function(){//二次确认提交
        if($.trim($("#confirmName").val()) == ''){
            alert('请输入员工姓名');
            return false;
        }
        if($.trim($("#confirmName").val()) != $.trim($("#h_userName").val())){
            $("#userName").trigger("blur");
        }
        hidePopupDiv($('#layer_warning'));
    })

    //自动完成输入框离开后清空相关值
    $("#userName").blur(function(){
        init.isSelect = false;
        window.setTimeout(function(){
            if(init.isSelect === false){
                $("#userName, #userCode, #h_userName").val(""); //清空员工工号姓名信息
                $("#level_original").text("");//清空职等职级并隐藏
                $("#span_level_original").hide(); 
            }
        }, 500);
    });


    //切换转入组织
    $("#newOrgId").change(function(){
        //获取转调类型
        if($.trim($(this).val()) == ""){
            $("#span_transfer_type").text('');
            $("#transferType").val("")
        }
        else{
            $("#span_transfer_type").text($(this).find("option:selected").attr("transferTypeStr"));
            $("#transferType").val($(this).find("option:selected").attr("transferType"));
        }
        
        //转调情况与转调类型联动（转中介部分行、转租赁分行显示，其余不显示）
        switch($.trim($("#transferType").val())){
            case "201402201": //转中介部分行
            case "201402202": $("#trTransferMethod").show();  break;//转租赁分行
            case "201402203": //转新房销售
            case "201402204": //转代理项目
            case "201402205": //转后台
            default : resetTransferMethod(); $("#trTransferMethod").hide(); break;
        }

        //职系、职等、职级、岗位、头衔与其联动，转调情况与转调类型联动（转代理项目部、转后台显示，其余不显示）
        switch($.trim($("#transferType").val())){
            case "201402201": //转中介部分行
            case "201402202": //转租赁分行
            case "201402203": resetLevels(); $("#trLevel, #trPosition, #trUserTitle").hide(); break; //转新房销售
            case "201402204": //转代理项目
            case "201402205": initSerials(); $("#trLevel, #trPosition, #trUserTitle").show(); break;//转后台
            default : resetLevels(); $("#trLevel, #trPosition, #trUserTitle").hide(); break;
        }
    });
    //清空职系、职等、职级、岗位、头衔的值
    function resetLevels(){
        if($("#h_pageType").val() == "edit" && init.newOrgId){//编辑状态页面初始化排除
            delete init.newOrgId;
            return false;
        }

        $("#js_serialId, #js_titleId, #js_levelId, #js_positionId, #js_userTitle").val("");
    }
    //清空转调情况的值
    function resetTransferMethod(){
        $("input:radio[name=transferState]:checked").attr("checked", ""); //清值
        $("#transfer_method_prompt").html("").hide(); //清提示
    }
    //初始化职等职级-职系
    function initSerials(){
        if($("#h_pageType").val() == "edit" && init.newOrgId){//编辑状态页面初始化排除
            delete init.newOrgId;
            return false;
        }

        var html = '<option value="">请选择职系</option>';
        if($("#transferType").val() == undefined){
            $("#js_serialId").html(html).val("").trigger("change");
        }
        else{
            $.ajax({
                url:"/transfer/stl/serial?transferType="+$.trim($("#transferType").val()),//传参传转入组织类型id
                dataType:'json',
                success:function(data){
                /*    var data = [ //模拟数据，请用返回的真实数据代替
                        { id:1, text:"行政管理职系" },
                        { id:3, text:"行政技术职系" }
                    ];*/
                    $.each(data.selectList, function(index, node){
                        html += '<option value="'+node.key+'">'+node.value+'</option>';
                    });
                    $("#js_serialId").html(html).val("").trigger("change");
                }
            });
        }
    }

    //转调情况切换，显示不同的提示信息
    $("input:radio[name=transferState]").change(function(){
        var html = "";
        switch($(this).val()){
            case "1": html = "房源：需交接给原分行。<br />客源：仍归属该转调员工。"; break;
            case "2": html = "房源：仍归属该转调员工。（签赔自动留在原分行，如需转移请见<a href='http://bangzhu.dooioo.com/document/details/12527S110147' target='_blank'>帮助</a>）<br />客源：仍归属该转调员工。"; break;
            default:;
        }
        $("#transfer_method_prompt").html(html).show();
    });


    //表单提交
    $("a.js_submit").click(function(){
        var validator = $('#formTransferEmpAdd').validate();
        if(validator.validateForm()){
            $('#formTransferEmpAdd').submit();
        }
    });

    /*********初始化**********/
    //转入组织自动选中时的切换事件触发
    if($("#newOrgId").val() != ""){
        init.newOrgId = true;
        $("#newOrgId").trigger("change");
    }
    //转调情况选中时的切换事件触发
    $("input:radio[name=transferState]:checked").trigger("change");
});