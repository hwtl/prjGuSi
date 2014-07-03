/**
 * Created with IntelliJ IDEA.
 * User: hw
 * Date: 13-5-28
 * Time: 下午8:18
 * 新增员工页面的js
 */

$(function() {

    var validator = $('#employeeFm').validate();
     validator.addRule('18Years', {
        validate: function($element){
            var birthday_Y = Number($element.val().substring(6, 10));//生日年份
            var birthday_M = Number($element.val().substring(10, 12))-1;//生日月份
            var birthday_D = Number($element.val().substring(12, 14));//生日日期
            var today = new Date();//当天系统日期
            var age = today.getFullYear() - birthday_Y - 1;//年龄
            if(today.getMonth() > birthday_M || today.getMonth() == birthday_M && today.getDate() >= birthday_D){
                age += 1;
            }
            return age >= 18;
        },
        message: '身份证显示年龄小于18周岁'
    });
    validator.addRule('confirm', {
        validate: function($element){
            var hookname = $element.attr()
            return $element.val() === $('.confirm').val();
        },
        message: '两次输入的身份证号码不一致'
    })

    var validatePhone = false;
    validator.addRule('hasphone', {
        validate: function($element){
            if($("#js_hid_userCode").val() > 0){
                if($("#js_hid_mobilePhone").val() === $("#js_mobilePhone").val()){
                   return true;
                }
            }
            ajaxGet({
                url:"/archives/"+$element.val()+"/validate",
                async:false,
                ok: function(data) {
                    validatePhone = true;
                },
                fail: function(data){
                    validatePhone = false;
                }
            });
            return validatePhone;
        },
        message: '号码已存在'
    })

    $("#js_comeFrom").change(function(){//渠道选择处理
        if($(this).val() == "1403310"){
            $("#js_remark").addClass("hideit").val("");
            $("#js_usercode").removeClass("hideit").val("").attr("placeholder","请输入推荐员工工号").attr("rule","integer checkRecommendUser");
        }else if($(this).val() == "1403311"){
            $("#js_usercode").addClass("hideit").val("");
            $("#js_remark").removeClass("hideit").val("").attr("placeholder","请备注具体渠道").attr("rule","required");
        }else{
            $("#js_usercode").addClass("hideit").val("").removeAttr("placeholder");
            $("#js_remark").addClass("hideit").val("").removeAttr("placeholder");
        }
    });

    var validateUser = false;
    validator.addRule('checkRecommendUser', {
        validate: function($element) {
            var recommendUser = $('#js_usercode').val();
            if ($.trim(recommendUser) !== '') {
                ajaxGet({
                    url:"/employee/" + recommendUser,
                    async:false,
                    ok: function(data) {
                        validateUser = true;
                    },
                    fail: function(data){
                        validateUser = false;
                    }
                });

            } else {
                validateUser = true;
            }
            return validateUser;
        },
        message: '该员工不存在'
    })


    $('a.js_submitAddEmp').click(function() {
        validator.refresh();
        if($("#js_hid_userCode").val() > 0){
            if($("#js_hid_idc").val() === $("#js_credential").val()){
                if(validator.validateForm() && checkOrganization()){
                    $("#employeeFm").submit(); //表示没有身份证号重复
                }
            }else{
                if (validator.validateForm() && checkIDC() && checkOrganization()) {
                    $("#employeeFm").submit(); //表示没有身份证号重复
                }
            }
        }else{
            if (validator.validateForm() && checkIDC() && checkOrganization()) {
                $("#employeeFm").submit(); //表示没有身份证号重复
            }
        }
    });
    
     //入职日期变更
    $("input[name=joinDate]").change(function(){
    	 $("input[name=registerDate]").val($(this).val());
    	 $("input[name=registerDate]").trigger("change");
    });
    
    // 身份证号码输入确认
    $("#js_credentialConfirm").focus(function(){
        var foreEl = document.getElementById("js_credential");
        //$("#js_idcDetails").addClass("hideit");
        foreEl.type = 'password';
    });
    $("#js_credential").focus(function(){
        var foreEl = document.getElementById("js_credentialConfirm");
        $("#js_idcDetails").addClass("hideit");
        foreEl.type = 'password';
    });
    $("#js_credentialConfirm").blur(function(){
        var foreEl = document.getElementById("js_credential");
        //$("#js_idcDetails").addClass("hideit");
        foreEl.type = 'text';
    });
    $("#js_credential").blur(function(){
        var foreEl = document.getElementById("js_credentialConfirm");
       //$("#js_idcDetails").addClass("hideit");
        foreEl.type = 'text';
        if( validator.validateElement($(this)) ){
         checkIDC();
        }
    });

         
    $("#js_serialId").change(function(){
        autoCompletion('/titleLevel/queryTitle/' + $(this).val(),$("#js_titleId"));
        initTitleLevel();
    });
    $("#js_titleId").change(function(){
        autoCompletion('/titleLevel/queryLevel/' + $(this).val(),$("#js_levelId"));
        autoCompletion('/position/queryPosition/' + $(this).val(),$("#js_positionId"));
        initTitleLevel();
    });
    $("#ipt_orgId").blur(function(){
        $("#js_hasOrg").addClass("hideit")
    });
    $("#ipt_orgId").blur(function(){
        $("#js_hasOrg").addClass("hideit");
    });

    $("#js_levelId").change(function(){
        if($(this).val() !== ""){
            $("#js_showTitleInfo").text($("#js_levelId option:selected").attr("expand"));
            $("input[name = userTitle]").val($("#js_levelId option:selected").attr("expand"));
            $("#js_showTitleInfo").removeClass("hideit");
        }else{
            $("#js_showTitleInfo").addClass("hideit");
            $("input[name = userTitle]").val("");
        }
    });
   // autocomplete($("#ipt_orgId"),'hrm_employee_add','',$("#orgId"));

    //自动联想，验证
    $("#ipt_orgId").autocomplete({
        urlOrData: '/organization/getOrgNameByPy',
        itemTextKey: 'orgName',
        dataKey: 'OrgNameList',
        setRequestParams: function(requestParams){
            requestParams.orgName = $("#ipt_orgId").val();
            requestParams.privilegeValue = 'hrm_employee_add';
            requestParams.dataType = '';
        },
        selectCallback:function(selectedItem){
            $("#orgId").val(selectedItem.id);
            if($('#orgId').val() != ""){
                $.get("/organization/getEmployeeCount/" + $('#orgId').val(), "", function(data){
                    if(data.status != "ok") {
                        alert(data.message);
                        $("#orgId").val("");
                        $("#ipt_orgId").val("");
                    }
                });
            }
        }
    });

    //根据身份证号带出性别
    $("#js_credential").change(function(){
        var id17 = $.trim($(this).val()).substring(16,17);//取出身份证号第17位
        if(id17 == "") return false;
        if (parseInt(id17) % 2 != 0) {//奇男
            $("#sexMale").attr("checked", "checked");
        }
        else {//偶女
            $("#sexFemale").attr("checked", "checked");
        }

    });

//    /**
//     * 判断组织编制是否可以新增员工
//     */
//    $("#ipt_orgId").live("change", function(){
//        $.get("/organization/getEmployeeCount/" + $("#orgId").val(), "", function(data){
//            if(data.status != "ok") {
//                alert(data.message);
//            }
//        })
//    })
});

function initTitleLevel(){
    $("#js_levelId").empty();
    $("#js_levelId").append("<option value=''>请选择</option>");
    $("#js_showTitleInfo").addClass("hideit");
    $("#js_positionId").empty();
    $("#js_positionId").append("<option value=''>请选择</option>");
}

/**
 * 检查身份证信息
 * @returns {boolean}
 */
function checkIDC(){
    var flag = false;
    if($.trim($("#js_credential").val()) == "") {
        return flag;
    }
    ajaxGet({
        url:"/employee/checkIdc/"+$("#js_credential").val(),
        async:false,
        ok: function(data) {
            flag = true;
            $("#js_idcDetails").addClass("hideit");
        },
        fail: function(data){
            if(data.isBlack == '1') {
                showPopupDiv($('#blackList_layer_message'));
                $("#js_idcDetails").addClass("hideit");
            } else {
                $("#js_idcDetails").removeClass("hideit");
                $("#js_showDetails").attr("href","/employee/"+data.userCode+"/details");
            }
        }
    });
    return flag;
}
