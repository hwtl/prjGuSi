$(function(){
	 // 自定义checkbox值
    $(".js_selfCheckbox").live("click", function(){
        if ($(this).attr('checked') == true) {
            $(".js_customizeCheckbox").show();
        } else {
            $(this).attr("description","");    
            $(".js_customizeCheckbox").hide().val('');
        };
    })
    $(".js_customizeCheckbox").live("change", function(){
        var checkboxId = $(this).attr('checkboxid');
        var $checkBox = $('#'+checkboxId);

        $checkBox.attr("description", $(this).val());    
    });

 

    // 加入黑名单表单验证
    var validator = $('#addBlackListForm').validate();
    validator.addRule('lessThan100', {
	    validate: function(element){
	        return $.trim(element.val()).length <= 100;
	    },
	    message: '不能超过100个字'
	});
    // 加入黑名单提交
    $(".js_submitBlackList").click(function(){
    	validator.refresh(); 
        if(validator.validateForm()){
        	   var postData = [];
        	    $('.reason-list input:checked').each(function(){
        	        postData.push({"reasonId":$(this).val(),"reason":$.trim($(this).parent('label').text()),"description":$(this).attr("description")});
        	    });
        	    $("#blackReasons").val(JSON.stringify(postData));
        	    $("#addBlackListForm").submit();
        }      
    });

    // 移出黑名单表单验证
    var validatorRemoveBlackList = $('#removeBlackListForm').validate();

    validatorRemoveBlackList.addRule('lessThan200', {
        validate: function(element){
            return $.trim(element.val()).length <= 200;
        },
        message: '不能超过200个字'
    });

    // 移出黑名单提交
    $(".js_submitRemoveBlackList").click(function(){
        if(validatorRemoveBlackList.validateForm()){
          $("#removeBlackListForm").submit();
        }   
    });

    // 上传组件调用
    $('.js_uploadContainer').uploadFileNew({
        uploadAttachUrl: '/attachment/black/upload/'+$("#attachCreator").val()+"?attachType=10",
        multiple:false,
        getUploadAttachIdAndUrl: function(response){
            strArrOp(response.attachment.id, $('.js_h_attach'), 1);
            return {
                attachUrl: response.attachment.smallAttachPath,
                attachId: response.attachment.id
            };
        },
        deleteCallback: function(response){
            var result = false;
            $.ajax({
                url:'/attachment/'+response.attachment.id+'/delete',
                type:'post',
                async: false,
                success: function(){
                	strArrOp(response.attachment.id, $('.js_h_attach'), 0);
                    result = true;
                }
            });
            return result;
        }, 
        beforeUpload: function(){
            if($(".js_h_attach").val() == '') {
                return true;
            } else {
                alert("只能上传一张图片,请先删除附件。");
                return false;
            }
        }

    });
})