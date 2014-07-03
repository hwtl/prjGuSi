<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
	<b class="f14">工作经历</b><span class="ml_5 red f14">（必填，无工作经验者可不填）</span>
	<span class="right">
				<a href="#" class="btn-small btn-blue in_block right js_save mr_5">保存</a>
			<a href="#" action="/archives/${userCode}/WorkExperienceDetail"
						class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
	</span>
</p>
<form:form action="/archives/${userCode}/updateWorkExperience" rowName="works" method="post" id="WorkExperienceForm">
    <webplus:token />
	<table border="0" cellpadding="0" cellspacing="0" class="js_workExperience tableFormNew mt_15">
		<tbody>
			<tr class="trHead grey999">
				<td  class="bdR">任职时间</td>
				<td  class="bdR">单位名称</td>
				<td  class="bdR">担任职务</td>
				<td  class="bdR">离职原因</td>
				<td  class="bdR">证明人</td>
                <td class="bdR">证明人电话</td>
				<td >操作</td>
			</tr>
			<c:choose>
				<c:when test="${not empty works}">
				   <c:forEach items="${works}" var="work" varStatus="vs">
					   <tr class="js_dataRowContainer">
							<td class="bdR">
								<input type="text" class="txtDate onlyMonth  workExper_entryDate" compareDateTo="departureDate${vs.index}"
									id="entryDate${vs.index}" value='<fmt:formatDate value="${work.entryDate}" pattern="yyyy-MM"/>' rule="required"
									groupby="dategroup${vs.index}"
									 onlymonthdate="<fmt:formatDate value="${work.entryDate}" pattern="yyyy-MM-dd"/>"
									name="entryDate" readonly="readonly">

								<input type="text" class="txtDate  onlyMonth ml_10 workExper_departureDate" compareDateFrom="entryDate${vs.index}"
									id="departureDate${vs.index}" name="departureDate"  rule="required" groupby="dategroup${vs.index}"
									onlymonthdate="<fmt:formatDate value="${work.departureDate}" pattern="yyyy-MM-dd"/>"
									value='<fmt:formatDate value="${work.departureDate}" pattern="yyyy-MM"/>' readonly="readonly"></td>
							<td class="bdR"><input type="text" value="${work.companyName }" name="companyName" class="txtNew " maxlength="100"></td>
							<td class="bdR"><input type="text" value="${work.positionName }" name="positionName" class="txtNew t75" maxlength="50"></td>
							<td class="bdR"><input type="text" value="${work.leaveReason }" name="leaveReason" class="txtNew " maxlength="100"></td>
							<td class="bdR"><input type="text" value="${work.prover}" name="prover" class="txtNew t50" maxlength="20"></td>
                           <td class="bdR"><input type="text" value="${work.proverTel}" name="proverTel" class="txtNew" rule="telephone" maxlength="20"></td>
							<td>
									<a href="javascript:;" class="btnMini miniDel in_block js_delete"></a>
							</td>
						</tr>
				   </c:forEach>
				</c:when>
			</c:choose>
			<tr class="js_insertBefore">
				<td colspan="6"><a href="#" class="btnOpH24 h24Silver in_block js_add">添加</a></td>
			</tr>
		</tbody>
	</table>
</form:form>
