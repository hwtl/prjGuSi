<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="权限配置-权限管理"/>
    <jsp:param name="css" value="privilege/frame"/>
</jsp:include>
<div class="container left p100 privilege">
	<input type="hidden" name="key" value="${key }"/>
	<input type="hidden" name="type" value="${type }"/>
	<h1 class="f12 mt_10">配置角色：${label }</h1>
	<div class="pt_15 pb_15">
	<div class="boxWrapGray t250 left">
		<div class="boxTitle"><b>系统</b></div>
		<div class="boxCon appChanel">
			<ul>
			<c:forEach items="${appList }" var="app">
				<li><a href="#"  chanelID="${app.id }"><span class="ml_10">${app.applicationName }</span></a></li>
			</c:forEach>
			</ul>
		</div>
	</div>
	<div class="boxWrapGray t250 left ml_15">
		<div class="boxTitle"><b>角色</b></div>
		<div class="boxCon roleConfig">

		</div>
	</div>
	<c:choose>
		<c:when test="${not empty ref}">
		<a href="#" ref="${ref }" id="btnSave" class="btnOpH34 h34Green opH34 ml_15 left">完成添加</a>
		</c:when>
		<c:otherwise>
		<a href="#" id="btnSave" class="btnOpH34 h34Green opH34 ml_15 left">保存更改</a>
		</c:otherwise>
	</c:choose>
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="js" value="dy-ajax,privilege/set"/>
</jsp:include>