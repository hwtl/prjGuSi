//组装原因字段
function resignReasonList(){ 
	var reasonlist = [];
	$(".js_reason_classify").each(function(){
		if($(this).find("input:checked").length !== 0){
			var id = $(this).find(".js_main_classify").attr("id"),
				text = $(this).find(".js_main_classify").text(),
				description = '',
				subs = $(this).find("input:checked").map(function(){
					var subId = $(this).val(),
						subText = $(this).parent().text(),
						subDescription = '',
						subobj = {
							"id" : subId,
							"text" : subText,
							"description" : subDescription
						}
					return subobj;
				}).get(),
				obj  = {
					"id":id,
					"text":text,
					"description":description,
					"subs":subs
				}
			reasonlist.push(obj);	
		}
	});
	return reasonlist;
}

function buildResignReasonData(str){
	var arr = JSON.parse(str),
		resultArr = arr.map(function(n){
			return n.subs.map(function(subN){
				return n.text + '：' + subN.text
			});
		});
	return resultArr;
}

function buildLayOffReasonData(str){
	var arr = JSON.parse(str),
		resultArr = arr.map(function(n){
			if(n.description !== ''){
				return n.text + '：' + n.description
			}else{
				return n.text;
			}
		});
	return resultArr;
}

//面谈页面提交
function faceToFace(){
	$(".js_reasonlist").val('');
	$(".js_submitReasons").html('');
	$(".js_reasonlist").val(JSON.stringify(resignReasonList()));
	if($(".js_reasonlist").val() !== '[]'){
		$(".ErrMsgNew").remove();
		//信息传递
		$(".js_pop_infos").html($(".js_employee_infos").html());
		var arr = resignReasonList(),
			liHtml = $.map(arr,function(n){
				return $.map(n.subs,function(sub){
					return '<li class="listOK">'+n.text+"："+sub.text+'</li>';
				});
				
			}).join(" ");
		$(".js_submitReasons").append(liHtml);
		showPopupDiv($('#layer_form'));
	}else{
		if($(".ErrMsgNew").length === 0){
			$(".js_reasonlist").parent().append('<p class="ErrMsgNew">原因至少选一项</p>');
		}
		return false;
	}
}	