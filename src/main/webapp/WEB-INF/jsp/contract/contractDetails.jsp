<%--
  Created by IntelliJ IDEA.
  Title:
  User: Jerry.hu
  Create: Jerry.hu (2013-06-03 11:57)
  Description: To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<div class="mt_15 clearfix">
    <span class="in_block mt_5 left f14 bold">合同记录</span>
  <appel:checkPrivilege url="hrm_employee_contract_edit">
    <span class="right">
        <a href="javascript:;" action="/contract/${userCode}/edit" class="btn-small btn-silver in_block right js_edit" >编辑</a>
    </span>
   </appel:checkPrivilege>
</div>
<table width="100%" cellspacing="0" class="tableListNew mt_10 " module="contractRecord">
    <tbody>
    <tr class="trHead">
        <td  nowrap="">序号</td>
        <td  nowrap="">合同类型</td>
        <td  nowrap="">合同期限</td>
        <td  nowrap="">合同开始时间</td>
        <td  nowrap="">合同结束时间</td>
    </tr>
    <c:forEach var="contract" items="${contracts}">
        <tr>
            <td  nowrap="">${contract.id}</td>
            <td  nowrap="">${contract.contractType}</td>
            <td  nowrap="">${contract.yaerCountStr}</td>
            <td  nowrap=""><fmt:formatDate value="${contract.startTime}" pattern="yyyy-MM-dd"/> </td>
            <td  nowrap=""><fmt:formatDate value="${contract.endTime}" pattern="yyyy-MM-dd"/></td>
        </tr>
    </c:forEach>
    <c:if test="${empty contracts}">
        <tr>
            <td  nowrap="" colspan="5" class="center"><span class="grey999">暂无合同记录</span></td>
        </tr>
    </c:if>

    </tbody>
</table>