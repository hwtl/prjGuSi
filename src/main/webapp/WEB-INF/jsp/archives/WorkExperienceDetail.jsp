<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
	<b class="f14">工作经历</b>
	 <appel:checkPrivilege url="archives_work_edit">
		<a href="#" action="/archives/${userCode}/WorkExperienceEdit" class="btn-small btn-silver in_block right js_edit">编辑</a>
	</appel:checkPrivilege>
</p>
<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
	<tbody>
		<tr class="trHead grey999">
			<td class="bdR">任职时间</td>
			<td  class="bdR">单位名称</td>
			<td  class="bdR">担任职务</td>
			<td class="bdR">离职原因</td>
			<td  class="bdR">证明人</td>
            <td class="bdR">证明人电话</td>
		</tr>
		<c:forEach items="${works}" var="work">
			<tr>
				<td class="bdR">
				  <fmt:formatDate value="${work.entryDate}" pattern="yyyy-MM"/> 至<fmt:formatDate value="${work.departureDate}" pattern="yyyy-MM"/>
				</td>
				<td class="bdR">${work.companyName}</td>
				<td class="bdR">${work.positionName}</td>
				<td class="bdR">${work.leaveReason}</td>
				<td class="bdR">${work.prover}</td>
                <td>${work.proverTel}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
