<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.gusi.boms.common.Configuration"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
</div>
<div class="clearfix"></div>
</div>
</div>
<plus:foot/>
<!-- 性能监控 -->
<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/jkk/clientmonitor.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
<input type="hidden" id="ip" value="<%=(request.getHeader("x-forwarded-for") == null)?request.getRemoteAddr() :  request.getHeader("x-forwarded-for")%>"/>
</body>
</html>
<script type="text/javascript" src="/static/js/boms_common.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
<c:if test="${ not empty param.js || not empty param.ajs || not empty param.duiJs}">
    <c:forEach var="js" items="${fn:split(param.duiJs, ',')}">
        <c:if test="${not empty js}">
            <script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/${js}.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
        </c:if>
    </c:forEach>
    <c:forEach var="js" items="${fn:split(param.ajs, ',')}">
        <c:if test="${not empty js}">
            <script type="text/javascript"  src="${js}?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
        </c:if>
    </c:forEach>
    <c:forEach var="js" items="${fn:split(param.js, ',')}">
        <c:if test="${not empty js}">
            <script type="text/javascript" src="/static/js/${js}.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
        </c:if>
    </c:forEach>
</c:if>