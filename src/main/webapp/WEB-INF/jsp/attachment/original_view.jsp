<% request.setCharacterEncoding("UTF-8"); %>
<%@page import="com.gusi.boms.common.Configuration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core"	prefix="c" %>
<html>
<head>
    <title>查看附件- 相关材料 -组织架构- 德佑地产</title>
    <link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/browsePictures.css?version=<%=Configuration.getInstance().getCurrentVersion()%>">
    <script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/jquery-1.4.2.min.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/fns.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
    <link rel= "shortcut icon" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">
    <link rel= "bookmark" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">

</head>
<body>
<div id="imageContainer" class="imageContainer">
    <!--操作列表-->
    <div class="imageOperationContainer">
        <a href="#" class="imageOperation rotateLeft js_rotateLeftOpt">逆时针旋转</a>
        <a href="#" class="imageOperation rotateRight js_rotateRightOpt">顺时针旋转</a>
    </div>

    <!--原图加载区-->
    <img src="${attach}" class="js_originImage">
</div>
<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/browsePictures.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
<script type="text/javascript">
    $(function(){
        $(".js_globleSuggestWrapper").remove();
        $('#imageContainer').browsePictures({
            browseOrigin: true
        });

        $('img.js_originImage').load(function(){
            var max = Math.max($(this).width(), $(this).height());
            $('#imageContainer').width(max).height(max);
        });
    });

</script>

</body>

</html>
