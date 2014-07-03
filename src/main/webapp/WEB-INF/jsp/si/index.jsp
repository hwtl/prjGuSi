<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="plus" uri="http://www.dooioo.com/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE HTML>
<html ng-app="si">
<head>
	<meta charset="UTF-8">
	<title>社保管理 - 德佑地产</title>
	<base href="/si/common/index/">
	<link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/main.css?version=${config.currentVersion}">
	<link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/header.css?version=${config.currentVersion}"/>
	<link rel="stylesheet" type="text/css" href="http://dui.${user.companyENLower}.com/public/css/treeNew.css?version=${config.currentVersion}">
	<link rel="stylesheet" type="text/css" href="/static/front/si/css/si.css?version=${config.currentVersion}">
</head>
<body>
	<!--header begin-->
	<%--<div id="new_header"></div>--%>

    <%--没有使用公共头部导航--%>
    <div class="headerWrap">
        <div class="header clearfix">
            <a href="http://gzz.${user.companyENLower}.com/workspace" class="logo_mini left"></a>
            <div class="mainMenu left ml_10">
                <a href="http://renzi.${user.companyENLower}.com" class="sub" rel="home"
                   style="background-image: none;">人力资源管理系统</a>
            </div>
            <div class="mainMenu topNav ml_20 right">
                <a href="#" class="mr_10" style="background-image: none;">${user.userName}</a>
                <a href="${config.logoutUrl }" style="background-image: none;">退出</a>
            </div>
        </div>
    </div>

    <!--header end-->
	<div class="container">
		<div ng-view></div>
	</div>

	<!-- footer begin -->
	<%--<div class="footer clearFix" ng-style="containerWidth" ng-cloak>©2014 德佑地产 <span ng-show="version">版本：{{version}}</span></div>--%>
    <plus:foot/>
    <!-- footer end -->

	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/lib/jquery-1.6.2.min.js?version=${config.currentVersion}"></script>
	<script type="text/javascript">
	var headerParameters = {
        empNo: ${user.userCode},
        empName: '${user.userName}',
        env : '${config.env}'
    };
    var privileges = {
    };

    <c:forEach items="${user.privileges}" var="pri" varStatus="i">
        privileges['${pri.privilege_url}'] = true;
    </c:forEach>

    var version = '${config.version}';
	</script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/header.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/fns.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/lib/angular.min.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/angular/lib/angular-mocks.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/WdatePicker.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/paginate.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/angular/directive/directive.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/validation.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/autocomplete.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/plugs/jquery-tree-1.0.js?version=${config.currentVersion}"></script>
    <script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/dui_treePanel.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/json/${user.companyENLower}/${user.companyENLower}All.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/plugs/jquery-upload-1.0.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/app.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/controller/siListCtrl.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/controller/siShNewListCtrl.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/controller/siShQuitListCtrl.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/controller/siShSelfListCtrl.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/controller/siNbNewListCtrl.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/controller/siNbQuitListCtrl.js?version=${config.currentVersion}"></script>
	<script type="text/javascript" src="/static/front/si/js/controller/siSzQuitListCtrl.js?version=${config.currentVersion}"></script>
</body>
</html>