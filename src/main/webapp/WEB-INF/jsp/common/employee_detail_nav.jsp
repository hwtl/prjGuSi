<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="f14">
	<b>姓名：</b>${emp.userName}   <b class="ml_15">工号：</b>${emp.userCode}   <b class="ml_15">部门：</b>${orgLongName}   <b class="ml_15">岗位：</b>${emp.positionName}
</p>
<div class="navTab3th mt_10 clearfix">
	<a href="/employee/${emp.userCode}/details" class="js_hrefs ${param.nav=='details'?'current':'' }" >在职信息</a>
	 <appel:checkPrivilege url="archives_detail">
		<a href="/archives/${emp.userCode}" class="js_hrefs ${param.nav=='archives'?'current':'' }" >档案信息</a>
	</appel:checkPrivilege>
	<appel:checkPrivilege url="changes_detail">
		<a href="/changes/${emp.userCode}" class="js_hrefs ${param.nav=='changes'?'current':'' }" >异动记录</a>
	</appel:checkPrivilege>
	<a href="/reward/${emp.userCode}" class="js_hrefs ${param.nav=='reward'?'current':'' }" >奖惩记录</a>
    <appel:checkPrivilege url="reward_detail">
    <a href="#">培训记录</a>
    </appel:checkPrivilege>
	<a href="#">客户满意度</a>
	<a href="/employee/material/${emp.userCode}" class="js_hrefs ${param.nav=='material'?'current':'' }">相关材料</a>
</div>