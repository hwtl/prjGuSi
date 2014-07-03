<%@include file="_includes.jsp" %>
<%@page import="com.gusi.boms.common.Configuration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${param.title} - 后台管理 - ${user.company}地产</title>
    <link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/main.css?version=<%=Configuration.getInstance().getCurrentVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/header.css?version=<%=Configuration.getInstance().getCurrentVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="/static/css/position/fronterp.css?version=<%=Configuration.getInstance().getCurrentVersion()%>"/>
    <c:if test="${not empty param.css || not empty param.duiCss}">
        <c:forEach var="css" items="${fn:split(param.css, ',')}">
            <c:if test="${not empty css}">
                <link type="text/css" rel="stylesheet" href="/static/css/${css}.css?version=<%=Configuration.getInstance().getCurrentVersion()%>">
            </c:if>
        </c:forEach>
        <c:forEach var="css" items="${fn:split(param.duiCss, ',')}">
            <c:if test="${not empty css}">
                <link type="text/css" rel="stylesheet"
                      href="http://dui.${user.companyENLower}.com/public/css/${css}.css?version=<%=Configuration.getInstance().getCurrentVersion()%>">
            </c:if>
        </c:forEach>
    </c:if>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/jquery-1.4.2.min.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/fns.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
    <link rel= "shortcut icon" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">
    <link rel= "bookmark" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">
</head>
<body>
<div id="new_header">
    <div class="headerWrap">
        <div class="header clearfix">
            <a href="http://gzz.${user.companyENLower}.com/workspace" class="logo_mini left"></a>
            <div class="mainMenu left ml_10">
                <a href="http://renzi.${user.companyENLower}.com" class="sub" rel="home"
                   style="background-image: none;">人力资源管理系统</a>
            </div>
            <div class="mainMenu topNav ml_20 right">
                <a href="#" class="sub mr_10" style="background-image: none;">${user.userName}</a>
				<a href="${config.logoutUrl }" style="background-image: none;">退出</a>
            </div>
        </div>
    </div>
    <div class="vPopupArrow fixedPop" id="nav_pop_arrow"></div>
    <div class="vPopup fixedPop" id="nav_pop_submenu">
        <div class="vPopupList4Nav t150"></div>
    </div>
    <div class="vPopupBD fixedPop" id="nav_pop_bd">

    </div>
</div>