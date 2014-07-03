<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="相关材料"/>
</jsp:include>
<div class="js_refresh mt_10" refresh="true">
    <jsp:include page="/WEB-INF/jsp/common/employee_detail_nav.jsp">
        <jsp:param name="nav" value="material"/>
    </jsp:include>
    <br/>
    <form id="newArchiveForm" method="post" >
        <webplus:token />
    	<input type="hidden"  id="addAttachIds" name="addAttachIds"/>
		<input type="hidden"  id="delAttachIds" name="delAttachIds"/>
	    <table width="100%" cellpadding="0" cellspacing="0" class="tableFormNew ">
		        <tr>
		            <td class="tdTitle" width="150" nowrap>身份证扫描件：</td>
		            <td width="810">
		                <jsp:include  page="/WEB-INF/jsp/attachment/attach_upload.jsp">
		                    <jsp:param name="attachType" value="2"/>
		                </jsp:include>
		            </td>
		        </tr>
		         <tr>
		            <td class="tdTitle" nowrap>毕业证扫描件：</td>
		            <td>
                        <jsp:include  page="/WEB-INF/jsp/attachment/attach_upload.jsp">
                            <jsp:param name="attachType" value="3"/>
                        </jsp:include>
		            </td>
		        </tr>
		         <tr>
		            <td class="tdTitle" nowrap>居住证扫描件：</td>
		            <td>
		            	<label>
                            <input type="radio" name="js_resident" class="js_resident" value="4" ${residentType=='4'?'checked':""}/>临时居住证
                        </label>
                        <label>
		            	    <input type="radio" name="js_resident" class="js_resident ml_10" value="5"  ${residentType=='5'?'checked':""}/>长期居住证
                        </label>
		            	<div id="js_temp_resident" class="${residentType=='4'?'':'hideit'} mt_10">
                            <jsp:include  page="/WEB-INF/jsp/attachment/attach_upload.jsp">
                                <jsp:param name="attachType" value="4"/>
                            </jsp:include>
		            	</div>
		              	<div id="js_long_term_resident" class="${residentType=='5'?'':'hideit'} mt_10">
                            <jsp:include  page="/WEB-INF/jsp/attachment/attach_upload.jsp">
                                <jsp:param name="attachType" value="5"/>
                            </jsp:include>
		              	</div>
		            </td>
		        </tr>
	    </table>
        <p class="mt_20 pt_20 txtRight">
            <a class="btnOpH34 h34Silver opH34 in_block js_resetForm js_hrefs" href="/employee/material/${emp.userCode}" >取消</a>
            <a href="javascript:;" class="btnOpH34 h34Blue opH34 in_block " id="js_submitForm">提交</a>
        </p>
	</form>
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="js" value="ajaxupload,upload,dy-ajax"/>
</jsp:include>
<script type="text/javascript">
$(function (){
	$('a.js_btnCheckForm').click(function() {
		removeResidentIds($('[name=js_resident]:checked').length == 0 ? '' : $('[name=js_resident]:checked').val());
		$('#newArchiveForm').submit();
	});
	function removeResidentIds(val)
	{
		if(val == "4")
		{
			//将所有5选的删除
			$('#js_long_term_resident').find('.true_del').each(function(){
				strArrOp($(this).attr('pic_id'), $('#addAttachIds'), 0);
				strArrOp($(this).attr('pic_id'), $('#deleteAttachIds'), 1);
			})

		}else if(val=="5")
		{
			//将4删除
			//将所有5选的删除
			$('#js_temp_resident').find('.true_del').each(function(){
				strArrOp($(this).attr('pic_id'), $('#addAttachIds'), 0);
				strArrOp($(this).attr('pic_id'), $('#deleteAttachIds'), 1);
			})
		}
	}
	$('input.js_resident').click(function() {
		var val=$(this).val();
		if(val == "4")
		{
			$("#js_temp_resident").removeClass("hideit");
			$("#js_long_term_resident").addClass("hideit");
		}else if(val=="5")
		{
			$("#js_temp_resident").addClass("hideit");
			$("#js_long_term_resident").removeClass("hideit");
		}else
		{
			$("#js_temp_resident").addClass("hideit");
			$("#js_long_term_resident").addClass("hideit");
		}

	});

    var commonUploadConfig = {
        generateThumbnailCallback: function(uploadCtrl, responseJson, extName){
        	 var attach = responseJson.attachment;
             var viewUrl=getFileType(attach.attachFilename)=='image'?(attach.attachPreviewUrl+attach.smallAttachPath):attach.smallAttachPath;
        	 return  '<div class="attThumbWrap">'+
                    '<div class="attThumbWrapInner">'+
                    '<a class="attach_thumbnail" target="_blank" href="/attachment/'+attach.id+'/preview" style="background-image: url('+viewUrl+')" title="'+attach.title+'附件"></a>'+
                    '<div class="op">'+
                    '<a class="red js_delete" showtitle="true" href="#" attachbean="CUSTOMER">删除</a>'+
                    '</div>'+
                    '</div>'+
                    '<div class="detail">'+
                    '<a target="_blank" href="/attachment/'+attach.id+'/preview">'+attach.attachFilename+'</a>'+
                    '</div>'+
                    '</div>';
        },
        setRequestParams: function(uploadCtrl, requestParams){
            requestParams.attachType = uploadCtrl.attr("type");
        },
        completeCallback: function(uploadCtrl, responseJson) {
            var attach = responseJson.attachment;
            strArrOp(attach.id, $('#addAttachIds'), 1);

        },deleteCallback: function(deleteObj, responseJson) {
            var attach = responseJson.attachment;
            if(confirm("确认删除吗？")){
                strArrOp(attach.id, $('#addAttachIds'), 0);
                strArrOp(attach.id, $('#delAttachIds'), 1);

                return true;
            }
            return false;
        }
    };
    $("a.true_del").click(function(){
        if(confirm("确认删除吗？")){
            strArrOp($(this).attr("pic_id"), $('#addAttachIds'), 0);
            strArrOp($(this).attr("pic_id"), $('#delAttachIds'), 1);
            $(this).parents('div.attThumbWrap').eq(0).remove();

            return true;
        }
        return false;
    });
    $('a.upload_attach').uploadFile($.extend(true, {url: '/attachment/upload/${emp.userCode}'},commonUploadConfig));

    $("#js_submitForm").click(function(){
       if($("#addAttachIds").val() === "" && $("#delAttachIds").val() === "" ){
           alert("没有变更过,无需变更");
           return false;
       }
        ajaxPost({
            url:'/attachment/save',
            data:{'addAttachIds':$("#addAttachIds").val(),'delAttachIds':$("#delAttachIds").val()},
            async:false,
            ok:function(data){
                alert("提交成功");

            },
            fail:function(data){
                alert("提交失败");
                return false;
            }

        });
        return true;
    });
});
</script>