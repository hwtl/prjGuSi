<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<div class="container">
 	<div class="boxWrapYellow center f14">
      <span>${msg }</span>
    </div>
</div>
<%@include file="/WEB-INF/jsp/common/_foot.jsp" %>