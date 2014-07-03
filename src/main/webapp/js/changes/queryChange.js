$(function(){
    $(parent.document.body).find("a.js_submitChange").live("click", function(e){
		e.preventDefault();
		var validator = $('#personChangePop').validate();
		if (validator.validateForm()){
			 var formEl=$(this).parents("form");
			 var posturl=formEl.attr("action");
			ajaxPost({
				url:posturl,
				data:formEl.serialize(),
				ok:function(data){
					hidePopupDiv($(parent.document.body).find('#personChangePop'));
					window.location.reload();
				},
				fail:function(data){
                    hidePopupDiv($(parent.document.body).find('#personChangePop'));
					alert(data.message);
				}
			});
		}
		return false;
	});

    $(parent.document.body).find("#newSerialId").live("change", function () {
        $(parent.document.body).find("#newTitleId,#newLevelId,#newPositionId").html("<option value='' >请选择</option>");
        $(parent.document.body).find("#titleLevlDetail").empty();
        if ($(this).val() != "") {
            autoCompletion('/titleLevel/queryTitle/' + $(this).val(), $(parent.document.body).find("#newTitleId"));
        }
    });

    $(parent.document.body).find("#newTitleId").live("change", function () {
        $(parent.document.body).find("#newLevelId,#newPositionId").html("<option value='' >请选择</option>");
        $(parent.document.body).find("#titleLevlDetail").empty();
        if ($(this).val() != "") {
            autoCompletion('/titleLevel/queryLevel/' + $(this).val(), $(parent.document.body).find("#newLevelId"));
            autoCompletion('/position/queryPosition/' + $(this).val(), $(parent.document.body).find("#newPositionId"));
        }
    });

    $(parent.document.body).find("#newLevelId").live("change",function(){
	        if($(this).val() !== ""){
	        	  $(parent.document.body).find("#titleLevlDetail").text($(parent.document.body).find("#newLevelId option:selected").attr("expand"));
	        }else{
	        	$(parent.document.body).find("#titleLevlDetail").empty();
	        }
	    });

    $(parent.document.body).find("#isBlack").live("click",function(){
		$(parent.document.body).find("#blackReason option").eq(0).attr("selected",true);
		if($(this).is(':checked')){
			$(parent.document.body).find("#textReason").addClass("hideit").removeAttr("name");
			$(parent.document.body).find("#blackReason").removeClass("hideit").attr("name", "leaveReason");
		}else{
			$(parent.document.body).find("#textReason").removeClass("hideit").attr("name", "leaveReason");;
			$(parent.document.body).find("#blackReason").addClass("hideit").removeAttr("name");
		}
	});
    $(parent.document.body).find("#blackReason").live("change", function(){
		if($(this).val() === "其他"){
			$(parent.document.body).find("#textReason").removeClass("hideit");
			$(parent.document.body).find("#blackReason").removeAttr("name");
			$(parent.document.body).find("#textReason").attr("name", "leaveReason");
		} else {
			$(parent.document.body).find("#textReason").addClass("hideit").removeAttr("name");
			$(parent.document.body).find("blackReason").attr("name", "leaveReason");
		}
	});
});


