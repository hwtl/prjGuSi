<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<!--begin:显示-->
<div class="mt_15 clearfix">
    <span class="in_block mt_5 left f14 bold">个人标签</span>
    <appel:checkPrivilege url="employee_tag">
        <span class="right">
            <a href="javascript:;" action="/employee/tag/${userCode}/edit" class="btn-small btn-silver in_block right js_edit">编辑</a>
        </span>
    </appel:checkPrivilege>
</div>

<div class="boxWrapBlue pd_10 clearfix mt_10">
    <c:if test="${not empty employeeTags}">
        <c:forEach items="${employeeTags}" var="tag">
            <span class="pLabel pLabelOn">${tag.tagName}</span>
        </c:forEach>
    </c:if>
    <c:if test="${empty employeeTags}">
        <span class="grey999">暂无标签</span>
    </c:if>
</div>
<!--end:显示-->