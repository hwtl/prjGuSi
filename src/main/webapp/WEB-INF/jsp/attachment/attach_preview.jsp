<% request.setCharacterEncoding("UTF-8"); %>
<%@page import="com.gusi.boms.common.Configuration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core"	prefix="c" %>
<%@ taglib prefix="webplus" uri="http://www.dooioo.com/webplus/tags" %>
<html>
<head>
    <title>查看附件- 相关材料 - 组织架构 - 德佑地产</title>
    <link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/browsePictures.css?version=<%=Configuration.getInstance().getCurrentVersion()%>">
    <link rel= "shortcut icon" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">
    <link rel= "bookmark" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">
    <script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/fns.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/jquery-1.4.2.min.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
</head>

<body>

<form id="previewForm" target="_blank" method="post" action="/attachment/original">
    <webplus:token />
    <input type="hidden" id="attachPath" name="attach" />
    </form>

        <div id="imageContainer" class="imageContainer">
            <!--保存要显示的图片信息-->
            <input type="hidden" name="imagesInfo" value="${attachs}">

            <!--操作列表-->
            <div class="imageOperationContainer">
                <a href="#" class="imageOperation prevImage js_prevImgOpt">上一页</a>
                <a href="#" class="imageOperation nextImage js_nextImgOpt">下一页</a>
                <a href="#" class="imageOperation rotateLeft js_rotateLeftOpt">逆时针旋转</a>
                <a href="#" class="imageOperation rotateRight js_rotateRightOpt">顺时针旋转</a>
                <a href="#" class="imageOperation printImage js_printImgOpt">打印</a>
                <a href="#" class="imageOperation viewOriginalImage js_viewOriginOpt">查看原图</a>
            </div>

            <!--图片展示区-->
            <div class="previewContainer">
                <div class="imageTitle js_imageTitle"></div>
                <div class="imageWrapper"><img class="js_displayImage" border="0" src="http://dui.dooioo.com/public/images/icon_ajaxload.gif"/></div>
            </div>

            <!--原图加载区-->
            <img src="" class="js_originImage" style="display:none;max-height: 500px;max-width: 500px;" >
        </div>
        <script type="text/javascript">
            $(function(){
                $(".js_globleSuggestWrapper").remove();
                $('#imageContainer').browsePictures({
                    imagesInfo: $('[name=imagesInfo]').val(),
                    loop: false,
                    previewOriginImage: function(src){
                        $("#attachPath").val(src);
                        $("#previewForm").submit();
                    }
                });
            });
        </script>    
    <script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/browsePictures.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
</body>
</html>
