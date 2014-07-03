<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
	<b class="f14">家庭主要成员</b>
	 <appel:checkPrivilege url="archives_family_edit">
		<a href="#" action="/archives/${userCode}/FamilyMemberEdit" class="btn-small btn-silver in_block right js_edit">编辑</a>
	</appel:checkPrivilege>
</p>
<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
	<tbody>
		<tr class="trHead grey999">
			<td  class="bdR">称谓</td>
			<td  class="bdR">姓名</td>
			<td  class="bdR">出生日期</td>
			<td  class="bdR">职业</td>
			<td  class="bdR">工作单位</td>
			<td  class="bdR">联系方式</td>
		</tr>
		 <c:forEach items="${familyMembers}" var="family">
			<tr>
				<td class="bdR">
					${family.alias }
				</td>
				<td class="bdR">
					${family.name }
				</td>
				<td class="bdR">
					<fmt:formatDate value="${family.bornDate}" pattern="yyyy-MM-dd"/>
				</td>
				<td class="bdR">
					${family.job }
				</td>
				<td class="bdR">
					${family.workplace}
				</td>
				<td class="bdR">
					${family.phone }
				</td>
		</tr>
		</c:forEach>
	</tbody>
</table>
