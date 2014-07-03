<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="首页"/>
</jsp:include>
    <h2 class="js_welcome f18 bold center mt_20">欢迎来到后台管理系统</h2>
    <div class="js_rightcontent hideit">
        <div class="navTab2th clearfix js_nav">
            <div class="js_tabwrap tabwrap left">
            </div>
        </div>
        <div class="mt_15" id="mainContainer"></div>
        <div class="clearfix"></div>
    </div>
<%@include file="/WEB-INF/jsp/common/_foot.jsp" %>