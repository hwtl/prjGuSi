<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>

<!--begin:编辑-->
<div class="mt_15 clearfix">
    <span class="in_block mt_5 left f14 bold">个人标签</span>
    <span class="right">
        <a href="javascript:;" class="btn-small btn-blue in_block right js_save">保存</a>
        <a href="#" action="/employee/tag/${userCode}/details" class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
    </span>
</div>
<form:form action="/employee/tag/${userCode}/edit" method="post" id="empTagForm">
    <webplus:token />
    <div class="boxWrapBlue pd_10 clearfix mt_10">
        <c:forEach items="${tagList}" var="tag">
            <span class="pLabel hand ${not empty employeeTags[tag.optionCode] ? 'pLabelOn' : ''}">
                <input  ${not empty employeeTags[tag.optionCode] ? 'checked="checked"' : ''} type="checkbox" name="employeeTagCodes" value="${tag.optionCode}" class="js_chk_employeeLabel hideit">
                ${tag.optionValue}
            </span>
        </c:forEach>
    </div>
</form:form>
<script type="text/javascript">
    $("span.pLabel").click(function(){
        var checkbox = $(this).find("input:checkbox.js_chk_employeeLabel");
        if(checkbox.length > 0){
            if(checkbox.eq(0).attr("checked") == true){
                checkbox.eq(0).removeAttr("checked");
                $(this).removeClass("pLabelOn");
            }
            else{
                checkbox.eq(0).attr("checked", "checked");
                $(this).addClass("pLabelOn");
            }
        };
    });
</script>
<!--end:编辑-->