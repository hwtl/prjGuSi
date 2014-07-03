<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="单组转调"/>
</jsp:include>
<div class="contentRight">
    <div class="container left p100 txtLeft">
        <h1 class="f18">单组转调</h1>
        <form id="transForm" action="/transfer/handleSingleOrg" method="post">
         	<webplus:token/>
            <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
                <tbody>
                    <tr>
                        <td class="tdTitle" width="120">转调分行：</td>
                        <td class="request" width="20">●</td>
                        <td width="580">
                            <input type="hidden" name="orgAId" id="orgAId" value="" />
                            <input type="text" class="txtNew tt160" name="orgAName" id="orgAName" popdiv="autoComp_orgA" rule="required" autocomplete="off" placeholder="请输入分行名称或首字母" />
                            <div id="autoComp_orgA" class="autoComplate"></div>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">转调后上级组织：</td>
                        <td class="request">●</td>
                        <td>
                            <input type="hidden" name="orgBParentId" id="orgBParentId" value="" />
                            <input type="text" class="txtNew tt160" name="orgBParentName" id="orgBParentName" popdiv="autoComp_orgBParent" rule="required" autocomplete="off" placeholder="请输入门店名称或首字母" />
                            <div id="autoComp_orgBParent" class="autoComplate"></div>
                            <a href="/organization/add" target="_blank" class="ml_10" id="linkCreadteDept">如未找到所属部门，请新增组织。</a>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle" width="120">转调后分行名称：</td>
                        <td class="request" width="20">●</td>
                        <td width="580">
                            <input type="text" class="txtNew tt160" name="orgBName" rule="required" placeholder="请填写分行名称" id="orgNameB" />
                        </td>
                    </tr>
                    <tr id="trIdcard">
                        <td class="tdTitle">生效日期：</td>
                        <td class="request">●</td>
                        <td>
                            <input type="text" name="activeDate" class="txtDate" rule="required" readonly="readonly" />
                        </td>
                    </tr>
                </tbody>
            </table>
            <p class="txtRight mt_15">
                <input type="button" value="转调" class="btn-normal btn-blue in_block ml_15" id="js_submitForm" />
            </p>
        </form>
    </div>
</div>
<div class="clearfix"></div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,autocomplete,WdatePicker"/>
    <jsp:param name="js" value="boms_common,orgtransfer/initAutoComplete"/>
</jsp:include>
<script type="text/javascript">
$(function(){
    $("#orgNameB").blur(function(){//转调后上级组织--验证是否重名
        if($.trim($(this).val()) != ""){
            $("#js_submitForm").addClass("opacity50").attr("disabled", "disabled");
            $.ajax({
                type: "GET",
                url: "/transfer/checkOrgName?orgName="+$.trim($("#orgNameB").val()),
                dataType: "json",
                success: function (data) {
                    if(data.status == "fail"){
                        alert(data.message);
                        $("#orgNameB").val("").focus();
                    }
                    $("#js_submitForm").removeClass("opacity50").removeAttr("disabled");
               },
                error: function (msg) {
                    alert(msg);
                    $("#js_submitForm").removeClass("opacity50").removeAttr("disabled");
                }
            });
        }
    });
    //提交表单并验证
    var validator = $('#transForm').validate();
    $('#js_submitForm').click(function(){
        if(validator.validateForm()){        //对表单进行验证
            $('#transForm').submit();
        }
    });

    //转调分行自动完成初始化
    initAutoComplete({
        urlOrData: '/oms/api/getOrgNameByPy?orgType=分行',
        itemTextKey: 'orgName', //branchName
        dataKey: 'OrgNameList', //autoBranch
        inputObj: $('#orgAName'),
        keepVessel: $("#orgAId"),
        keepKey: 'id'
    });

    //转调后上级组织自动完成初始化（门店+代理项目部）
    initAutoComplete({
        urlOrData: '/oms/api/getOrgNameByPy?orgType=门店',
        itemTextKey: 'orgName', //storeName
        dataKey: 'OrgNameList',  //autoStore
        inputObj: $('#orgBParentName'),
        keepVessel: $("#orgBParentId"),
        keepKey: 'id' //storeId
    });
});
</script>