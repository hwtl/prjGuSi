<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
	<b class="f14">培训经历</b><span class="ml_5 red f14">（非必填，完善培训经历将有助于提高您内部招聘的竞争力）</span>
	<span class="right">
				<a href="#" class="btn-small btn-blue in_block right js_save mr_5">保存</a>
				<a href="#" action="/archives/${userCode}/TrainingExperienceDetail" class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
	</span>
</p>
<form:form action="/archives/${userCode}/updateTrainingExperience" rowName="trains" method="post" id="trainingForm">
    <webplus:token />
	<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew js_trainingExperiences mt_15" ref="trainingExperiences">
		<tbody>
			<tr class="trHead grey999">
				<td  nowrap class="bdR">培训时间</td>
				<td  nowrap class="bdR">培训名称</td>
				<td  nowrap class="bdR">获得证书</td>
				<td  nowrap>操作</td>
			</tr>
		 <c:choose>
		 	<c:when test="${not empty trains }">
		 		<c:forEach  var="train" items="${trains}" varStatus="vs">
		 		<tr class="js_dataRowContainer">
					<td class="bdR">
						<input type="text" class="txtDate onlyMonth train_startDate"
						     compareDateTo="endDate${vs.index}" id="startDate${vs.index}" rule="required" groupby="dategroup${vs.index}"
							 value='<fmt:formatDate value="${train.startDate}" pattern="yyyy-MM"/>' onlymonthdate='<fmt:formatDate value="${train.startDate}" pattern="yyyy-MM-dd"/>'
							  name="startDate" readonly="readonly">
						<input type="text" class="txtDate onlyMonth ml_10 train_endDate"  rule="required" groupby="dategroup${vs.index}"
							value='<fmt:formatDate value="${train.endDate}" pattern="yyyy-MM"/>'
							onlymonthdate='<fmt:formatDate value="${train.endDate}" pattern="yyyy-MM-dd"/>' name="endDate" readonly="readonly"
							 compareDateFrom="startDate${vs.index}" id="endDate${vs.index}"
							>
					</td>
					<td class="bdR"><input type="text" value="${train.trainName }" rule="required" name="trainName" class="txtNew  train_trainName" maxlength="100"></td>
					<td class="bdR"><input type="text" value="${train.certificate}" name="certificate" class="txtNew train_certificate" maxlength="100"></td>
						<td>
						 	<a href="javascript:;" class="btnMini miniDel in_block js_delete"></a>
					</td>
				</tr>
		 		</c:forEach>
		 	</c:when>
		 </c:choose>
		 <tr class="js_insertBefore">
				<td colspan="4"><a href="#" rel="trainingExperiences" class="btnOpH24 h24Silver in_block js_add">添加</a></td>
		 </tr>
		</tbody>
	</table>
</form:form>
