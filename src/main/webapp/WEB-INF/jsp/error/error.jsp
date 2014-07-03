<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="${title}"/>
</jsp:include>
<div class="container">
 	<div class="boxWrapYellow center f14">
      <span>${msg }</span>
    </div>
    <div class="mt_10">
      <ul>
      	<li> <a href="/">返回首页</a>	</li>
      	<li class="mt_5"> <a href="javascript:;" onclick="javascript:history.back(1);">返回上一级</a>	</li>
      </ul>
    </div>
</div>
<%@include file="/WEB-INF/jsp/common/_foot.jsp" %>