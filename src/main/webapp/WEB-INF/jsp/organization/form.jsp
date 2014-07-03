<%@ taglib prefix="webplus" uri="http://www.dooioo.com/webplus/tags" %>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="${org.id > 0 ? '编辑' : '新增'}组织-组织管理"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>
    <form id="addOrgForm" method="post" action="/organization/add">
        <webplus:token />
        <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew ">
            <tbody>
            ${baseHtml}
            ${faxHtml}
            ${phoneHtml}
            </tbody>
        </table>
        <input type="hidden" id="orgPhoneList" name="orgPhoneList" class="js_phoneJson" value="" />
        <p class="txtRight mt_15 mb_20">
        <a href="/organization/list" class="btnOpH34 h34Silver in_block">取消</a>
        <a href="javascript:;" class="btnOpH34 h34Blue opH34 in_block js_submitForm">提交</a>
    </p>
    </form>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,WdatePicker,autocomplete,dui_addRemove"/>
    <jsp:param name="js" value="portal,position/jobManage_common"/>
</jsp:include>
<script type="javascript/template" id="phonelist">
    <p class="js_dataRowContainer">
        <input type="text" name="phone" class="txtNew w200 mt_5 js_phone" placeholder="电话号码">
        <input type="text" name="line" class="txtNew w200 ml_20 js_line" placeholder="线路">
        <a href="#" class="js_delete ml_10">删除</a>
    </p>
</script>
<script type="text/javascript">
    //上级组织名称自动联想
    $('#parentOrgName').autocomplete({
        urlOrData: '/organization/getOrgNameByPy',
        itemTextKey: 'orgName',
        dataKey: 'OrgNameList',
        setRequestParams: function(requestParams){
            requestParams.orgName = $('#parentOrgName').val();
            requestParams.privilegeValue = 'org_add';
            //Normal & Temporary 正常临时组织
            requestParams.dataType ="NT";
        },
        selectCallback: function(selectedItem){
            $('#parentId').val(selectedItem.id);
        }
    });

    //负责人自动联想
    $('#managerName').autocomplete({
        urlOrData: '/employee/associative',
        itemTextKey: 'userNameCn',
        dataKey: 'employeeList',
        lazyTime: 200,
        autocompleteOnfocus: false,
        setRequestParams: function(requestParams){
            requestParams.key = $('#managerName').val();
        },
        selectCallback: function(selectedItem){
            $('#manager').val(selectedItem.userCode);
        }
    });

    /**
    * 判断是否为顶级部门
     */
    $('.js_submitForm').click(function(){
        if($('#operateType').val() == 'orgBaseEdit' && $('#parentId').val() != 0) {
            checkName();
        } else {
            submitForm();
        }
    });

    //验证组织名称
    function checkName() {
        var validator = $('#addOrgForm').validate();
        if(validator.validateForm()){
            var parentName = $('#parentOrgName').val();
            if(parentName != '') {
                ajaxGet({
                    url : '/organization/checkName',
                    data: {id:$('#id').val(), orgName:$('#orgName').val(), parentName:parentName},
                    ok : function(data) {
                        var parentOrg = data.parentOrg;
                        $('#parentId').val(parentOrg.id);
                        checkManagerName();
                    },
                    fail : function(data) {
                        alert(data.message);
                    }
                });
            }
        }
    }

    /**
     *如果负责人变更则把负责人userCode清空
     */
    function removeManager() {
        $("#manager").val("");
    }

    /**
     * 验证负责人
     */
    function checkManagerName() {
        var managetName = $("#managerName").val();
        var manager = $("#manager").val();
        if(managetName.trim() != "") {
            if(manager != ""){
                ajaxGet({
                    url : '/employee/' + manager,
                    ok : function(data) {
                        submitForm();
                    },
                    fail : function(data) {
                        alert("对不起，该负责人不存在！");
                    }
                });
            }else{
                alert("对不起，请输入负责人名称后选择列表中的员工！");
            }
        } else {
            $("#manager").val("");
            submitForm();
        }
    }

    //提交表单
    function submitForm() {
        var result = [];
        $('div.js_addRemoveRow .js_dataRowContainer').each(function(){
            result.push(formToJson($(this), {
                keys: ['phone', 'line']
            }));
        });
        $(".js_phoneJson").val('['+result.join(',')+']');//拼出来的电话和线路的json字符串，存到了js_phoneJson这个隐藏域里
        $('#addOrgForm').submit();
    };

    //动态生成行
    $('.js_addRemoveRow').addRemoveRow({
        maxrows: 200,
        buildDataRowContent: function(self){

            return $('#phonelist').html();
        },
        deleteCallback: function(deletedRow){
            return confirm('确定删除');
        }
    }).find('.js_add').click();

    $("#orgType").change(function(data){
        var type = $(this).val();
        if(!($("#orgMaxCount").attr("disabled"))) {
            $("#orgMaxCount").val("");
        }
        if(type == "门店") {
            $(".js_orgMaxCount").removeClass("hideit");
        } else {
            $(".js_orgMaxCount").addClass("hideit");
        }
    });

</script>