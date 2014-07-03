<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
	<b class="f14">培训经历</b>
	 <appel:checkPrivilege url="archives_train_edit">
		<a href="#" action="/archives/${userCode}/TrainingExperienceEdit" class="btn-small btn-silver in_block right js_edit">编辑</a>
	</appel:checkPrivilege>
</p>
<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15" >
	<tbody>
		<tr class="trHead grey999">
			<td  class="bdR">培训时间</td>
			<td class="bdR">培训名称</td>
			<td class="bdR">获得证书</td>
		</tr>
		<c:forEach items="${trains}" var="train">
			<tr>
				<td class="bdR">
					<fmt:formatDate value="${train.startDate}" pattern="yyyy-MM"/> 至 <fmt:formatDate value="${train.endDate}" pattern="yyyy-MM"/>
				</td>
				<td class="bdR">${train.trainName }</td>
				<td class="bdR">${train.certificate}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
