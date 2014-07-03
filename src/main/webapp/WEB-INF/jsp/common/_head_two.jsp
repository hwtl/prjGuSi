<%@include file="_includes.jsp" %>
<%@page import="com.gusi.boms.common.Configuration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${param.title} - 人力资源管理系统-德佑地产</title>
    <link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/main.css?version=<%=Configuration.getInstance().getCurrentVersion()%>" />
    <c:if test="${not empty param.css}">
        <c:forEach var="css" items="${fn:split(param.css, ',')}">
            <link type="text/css" rel="stylesheet" href="/static/css/${css}.css?version=<%=Configuration.getInstance().getCurrentVersion()%>">
        </c:forEach>
    </c:if>
    <script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/jquery-1.4.2.min.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
    <script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/fns.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
</head>
<body>