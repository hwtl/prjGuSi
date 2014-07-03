<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
	<form:form action="/changes/processChanges" modelAttribute="changes" method="post" >
        <webplus:token />
			<div class="popCon ml_20 mr_20">
				<h2>${changes.changeType}</h2>
				<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
					<tbody>
						<tr>
							<td class="tdTitle" width="100">原部门：</td>
							<td class="request" width="20"></td>
							<td width="150">${changes.newOrgName}</td>
							<td class="tdTitle" width="100">现部门：</td>
							<td class="request" width="20">●</td>
							<td width="300">
								<form:hidden path="changeType"/>
								<form:hidden path="userCode"/>
								<form:hidden path="newOrgId" id="orgId"/>
								<input autocomplete="off" type="text" popdiv="autoComp_keyword"  id="ipt_orgId"
									 class="txt tt160 deptSelector required" value="${changes.newOrgName}" name="orgName" />
								 <div id="autoComp_keyword" class="autoComplate"></div>
							</td>
						</tr>
						<tr>
							<td class="tdTitle">原职系：</td>
							<td class="request"></td>
							<td>${changes.newSerialName}</td>

							<td class="tdTitle">现职系：</td>
							<td class="request" width="20">●</td>
							<td>
							 <form:select path="newSerialId"  rule="required" >
								<option value="">请选择</option>
								<form:options items="${serials}" itemLabel="serialName" itemValue="id"/>
							 </form:select>
						</tr>
						<tr>
							<td class="tdTitle">原职等职级：</td>
							<td class="request"></td>
							<td>${changes.newTitleDegree}-${changes.newLevelRank}</td>

							<td class="tdTitle">现职等职级：</td>
							<td class="request" width="20">●</td>
							<td>
								<form:select path="newTitleId" rule="required" grouby="optiongroup">
									<option value="">请选择</option>
									<form:options items="${titles}" itemLabel="value" itemValue="key"/>
								</form:select>
							<%--	<form:select path="newLevelId"   rule="required" grouby="optiongroup">
								  <option value="">请选择</option>
									<form:options items="${levels}" itemLabel="value" itemValue="key"/>
								</form:select>--%>

                                <select id="newLevelId" name="newLevelId" rule="required" grouby="optiongroup">
                                    <option value="">请选择</option>
                                    <c:forEach items="${levels}" var="level">
                                        <option ${changes.newLevelId == level.key ? 'selected' : ''} value="${level.key}" expand="${level.expand}">${level.value}</option>
                                    </c:forEach>
                                </select>
                                <span id="titleLevlDetail">${changes.newTitleLevelFullName} </span>
							</td>
						</tr>
						<tr>
							<td class="tdTitle">原岗位：</td>
							<td class="request"></td>
							<td>${changes.newPositionName}</td>

							<td class="tdTitle">现岗位：</td>
							<td class="request" width="20">●</td>
							<td>
								<form:select path="newPositionId" rule="required">
									<option value="">请选择</option>
									<form:options items="${positions}" itemLabel="value" itemValue="key"/>
								</form:select>
							</td>
						</tr>
						<tr>
					<%--		<td class="tdTitle">生效日期：</td>
							<td class="request" width="20">●</td>
							<td>
								<form:input path="activeDate" class="txtDate"  rule="required" readonly="readonly" maxlength="10"/>
							</td>--%>
                            <td colspan="3"></td>
							<td class="tdTitle">回聘日期：</td>
							<td class="request" width="20">●</td>
							<td>
                                <form:hidden path="activeDate" />
								<form:input path="leaveDate" class="txtDate"  rule="required" readonly="readonly" maxlength="10"
                                            onchange="javascript:$('#activeDate').val($(this).val());"/>
							</td>
						</tr>
					</tbody>
				</table>
	</div>
	<div class="popBtn mt_10">
		<a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#personChangePop')); return false">关闭</a>
		<a href="#" class="btnOpH24 h24Blue in_block js_submitChange" >提交</a>
	</div>
 </form:form>
 <script type="text/javascript">
	 autocomplete($("#ipt_orgId"),'changes_add','NT',$("#orgId"));
 </script>