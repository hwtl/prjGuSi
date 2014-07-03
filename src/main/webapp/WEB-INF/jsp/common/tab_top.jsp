<%--
  Created by IntelliJ IDEA.
  Title:  WEB-INF.jsp.common
  User: Jerry.hu
  Create: Jerry.hu (2013-05-10 08:34)
  Description: To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<h1 class="f18 bold mt_10">组织架构</h1>
<table width="100%" cellpadding="0" cellspacing="0" class="navTab1th">
<tbody>
<tr align="center">
    <td width="25%" class="${param.index == 1 ? 'current' : ''}"><a href="/oms/employee/list" target="_self">员工列表</a></td>
    <td width="25%" class="${param.index == 2 ? 'current' : ''}"><a href="/oms/organization/view" target="_self">组织管理</a></td>
</tr>
</tbody>
</table>