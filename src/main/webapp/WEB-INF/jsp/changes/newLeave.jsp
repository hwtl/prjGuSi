<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
	<form action="/changes/processChanges" method="post" >
        <webplus:token />
			<div class="popCon ml_20 mr_20">
				<h2>离职</h2>
				<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
					<tr>
						<td class="tdTitle" width="100">离职日期：</td>
						<td class="request">●</td>
						<td>
							<input type="hidden" name="userCode" value="${userCode}">
							<input type="hidden" name="changeType" value="${changeType}">
							<input type="text" readonly="readonly" name="leaveDate" value="" class="txtDate" rule="required" >
						</td>
					</tr>
					<tr>
						<td class="tdTitle">离职生效日期：</td>
						<td class="request">●</td>
						<td>
							<input type="text" readonly="readonly"  name="activeDate" value="" class="txtDate" rule="required" >
						</td>
					</tr>
					<tr>
						<td class="tdTitle">离职类别：</td>
						<td class="request">●</td>
						<td>
							<select name="leaveType" id="leaveType" rule="required" >
								<option value="">请选择</option>
								<c:forEach items="${leaveTypes}" var="p">
									<option value="${p.optionValue}">${p.optionValue}</option>
								</c:forEach>
							</select>							
						</td>
					</tr>
					<tr>
						<td class="tdTitle">离职原因：</td>
						<td class="request">●</td>
						<td>
							<div id="leaveReasonPanel">
								<textarea id="textReason" name="leaveReason" class="tA grey999" rule="required"></textarea>
							</div>
						</td>
					</tr>
		</table>
	</div>
	<div class="popBtn mt_10">
		<a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#personChangePop')); return false">关闭</a>
		<a href="#" class="btnOpH24 h24Blue in_block js_submitChange">提交</a>
	</div>
 </form>