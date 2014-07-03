<%--
  Created by IntelliJ IDEA.
  Title:
  User: Jerry.hu
  Create: Jerry.hu (2013-06-03 13:02)
  Description: To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<div class="mt_15 clearfix">
    <span class="in_block mt_5 left f14 bold">岗位信息</span>
    <appel:checkPrivilege url="hrm_employee_parttime_position_edit">
    <span class="right">
        <a href="javascript:;" action="/employeePosition/${userCode}/edit" class="btn-small btn-silver in_block right js_edit">编辑</a>
    </span>
    </appel:checkPrivilege>
</div>
<table width="100%" cellspacing="0" class="tableListNew mt_10 js_jobinfo" module="positionInfo">
    <tbody>
    <tr class="trHead">
        <td  width="50" nowrap="">&nbsp;</td>
        <td  nowrap="">部门</td>
        <td  nowrap="">岗位</td>
    </tr>
    <tr>
        <td>主要岗位</td>
        <td>${employee.orgName}</td>
        <td>${employee.positionName}</td>
    </tr>
    <c:forEach var="partTime" items="${partTimePositions}">
        <tr>
            <td>兼职岗位</td>
            <td>${partTime.orgName}</td>
            <td>${partTime.positionName}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>