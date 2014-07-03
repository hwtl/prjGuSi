<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="离职面谈"/>
    <jsp:param value="interview" name="css"/>
</jsp:include>
<div class="contentRight">
			<form id="faceToFaceForm" name="" action="/interview/complete" method="post">
                <webplus:token />
				<p class="mt_15 js_employee_infos">
					<span class="bold mr_20">工号：${leaveEmp.userCode}</span>
					<span class="bold mr_20">姓名：${leaveEmp.userNameCn}</span>
					<span class="bold mr_20">区域：${leaveEmp.areaName}</span>
					<span class="bold mr_20">分行：${leaveEmp.orgName}</span>
					<span class="bold mr_20">岗位：${leaveEmp.positionName}</span>
					<span class="bold mr_20">入职日期：<fmt:formatDate value="${leaveEmp.newJoinDate}" pattern="yyyy-MM-dd"/></span>
				</p>
				<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
					<tbody>
						<tr>
							<td width="100">申请离职日期：</td>
							<td class="request"></td>
							<td><fmt:formatDate value="${records.leaveDate}" pattern="yyyy-MM-dd"/><span class="orange ml_20">(即：离开公司的日期)</span></td>
						</tr>
						<tr>
							<td>离职类型：</td>
							<td class="request"></td>
							<td>
							  ${records.leaveType}
							</td>
						</tr>
						<tr>
							<td>申请离职原因：</td>
							<td class="request"></td>
							<td>
							  ${records.reasonString}
							</td>
						</tr>
					</tbody>
				</table>
				<p class="mt_15 bold f14">面谈表格</p>
				<p><input type="hidden" name="reasons" value="" class="js_reasonlist" /></p>
				<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew tableFormList mt_15"  >
					<tbody>
						 <c:forEach items="${leaveList}" var="lv">
							<tr class="js_reason_classify">
						  		<!--大原因循环 begin -->
								<td class="js_main_classify t250" id="${lv.id}">${lv.parameterValue}</td>
								<td class="js_sub_classify">
									<!--小原因循环 begin -->
								   <c:forEach items="${lv.subs}" var="s">
										<label class="t250 in_block lineH200"><input type="checkbox" name="" value="${s.id }" />${s.parameterValue}</label>
								   </c:forEach>
									<!--小原因循环 end -->
								</td>
							</tr>
						 </c:forEach>
					</tbody>
				</table>
				<input type="hidden" name="id" value="${records.id}" />
				<p class="txtRight mt_20">
					<a href="javascript:;" class="btnOpH34 h34Silver opH34 in_block ml_5">取消</a>
					<a href="javascript:;" class="btnOpH34 h34Blue opH34 in_block js_checkButtonResearch">提交</a>
				</p>
			</form>
		<div class="clearfix"></div>
</div>
<!--弹层 begin -->
<div class="popLayer w840" id="layer_form">
	<div class="popTitle clearfix">
		<h1>面谈调查原因确认</h1>
		<div class="cls"><a href="#" class="xx" onclick="hidePopupDiv($('#layer_form')); return false"></a></div>
	</div>
	<div class="popCon">
		<div class="form">
			<div>
				<p class="mt_15 js_pop_infos"></p>
				<ul class="mt_15 js_submitReasons">

				</ul>
			</div>
		</div>
	</div><br><br>
	<div class="popBtn">
		<a href="javascript:;" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#layer_form')); return false">取消</a>
		<a href="javascript:;" class="btnOpH24 h24Blue in_block js_submit">确认</a>
	</div>
</div>
<!--弹层 end -->
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
 <jsp:param name="js" value="interview"/>
 <jsp:param name="duiJs" value="validation"/>
</jsp:include>
<script type="text/javascript">
	$(function(){
		//面谈页面的提交
		$(".js_checkButtonResearch").click(function(){
			faceToFace();
		});

		$(".js_submit").click(function(){
			 hidePopupDiv($("#layer_form"));
			$("#faceToFaceForm").submit();
			return false;
		});
	});
</script>