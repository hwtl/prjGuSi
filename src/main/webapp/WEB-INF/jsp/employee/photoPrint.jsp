<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head_photo.jsp">
    <jsp:param name="title" value="社保打印"/>
</jsp:include>
    <style type="text/css">
    ul.photoList {width:895px;border-top:1px dashed #666; border-left:1px dashed #666;margin:0 auto;}
    ul.photoList li{width:130px; height:240px; padding:10px 9px; border-right:1px dashed #666; border-bottom:1px dashed #666; float:left; text-align:center;overflow:hidden;}
    ul.photoList li .imgWrap {width:130px; height:190px; overflow:hidden;}
    ul.photoList li img.photo {width:142px; height:190px; margin-left:-6px;}
    </style>
  <style type="text/css" media="print">
    #new_header, .location, .footer, .js_title {display:none;}
    body {margin-top:0;}
   </style>
<div class="container">
    <div class="mt_15 mb_10 clearfix js_title">
        <h1 class="f18 left">社保打印</h1>
        <span class="right">
            <a href="javascript:;" class="btn-small btn-green in_block" id="uploadFile">导入名单</a>
            <input type="file" accept=".xls,.xlsx" id="trueFile" class="hideit" /> 
            <a href="javascript:;" class="btn-small btn-silver in_block ml_5" onclick="window.print()">打印名单</a>
        </span>
    </div>

    
    <ul class="photoList clearfix hideit">
    </ul>
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
  <jsp:param value="http://dui.dooioo.com/public/js/plugs/jquery-upload-1.0.js" name="ajs"/>
</jsp:include>
<script type="text/javascript">
$(function(){
    //导入名单
    $('#uploadFile').click(function(){
        $('#trueFile').trigger('click');
    });
    $('#trueFile').change(function(){
        var fileUploader = new $.fn.dui.FilesUploader($(this).get(0).files, '/employee/photoPrint/upload', {
            successCallback: function(response){
                if (response.status == "ok"){//成功
                    if(response.list.length > 0){//有名单，生成照片列表
                    	 var htmlStr = "";
                         $.each(response.list, function(index, node){
                             htmlStr += '<li><p class="imgWrap"><img class="photo" src="http://100.dooioo.com:7070/photos_nologo/'+node.userCode+'_142x190.jpg" /></p><p class="mt_15">'+node.userNameCn+'<br />'+node.userCode+'</p></li>';
                         });
                         $("ul.photoList").html(htmlStr).show();
                    }
                    else{//无名单
                        alert(response.message);
                    }
                } else {//失败
                    alert(response.message);
                }
            }
        });
        fileUploader.upload();
        $(this).val('');
    });
});
</script>