$(function(){
	function checkOneRow($el){
				var namelist = $el.parents("tr").find('[name]').not(":hidden");
				if ($el.val() == "") {
					for(var i=0, len = namelist.length; i<len; i++){
						if( namelist.eq(i).val() !== "" ){
							return false;
						}
					}
				}else {
					for(var i=0, len = namelist.length; i<len; i++)
						{
							if( namelist.eq(i).val() == "" )
							{
									return false;
							}
						}
				};
				return true;
			};
	$.fn.validate.validator.prototype.addRule('fillrow', {validate: function($el){return checkOneRow($el);},message: "必须填完一行"});

    //家庭成员关系--其他输入框必填验证
    $.fn.validate.validator.prototype.addRule('alias_other', {validate:
        function($el){
            return $el.css("display") != "none" && $.trim($el.val()) != "";
        },
        message: "请填写称谓"
    });

    //家庭主要成员--称谓（选其他时显示输入框）
    $(".js_selAlias").live("change", function(){
        var self = $(this);
        var alias_other = $(this).parent("td").find(".js_aliasOther");
        if($(this).val() == "其他"){
            self.attr("name", "");
            alias_other.attr("name", "alias");
            alias_other.show();
        }
        else{
            self.attr("name", "alias");
            alias_other.attr("name", "");
            alias_other.val("").hide();
        }
    });
	
	$.fn.dataForm = function(){
		var rowName = $(this).attr("rowName");
		  if(!rowName){
			  return;
		  }
		  $(this).find("tr.js_dataRowContainer").each(function(rowid){
		    $(this).find("[name]").each( function(){
		      if(!$(this).attr("field")){
		    	  $(this).attr("field", $(this).attr("name"));
		      }
		      var thisname = rowName+'['+rowid+'].'+$(this).attr("field");
		      $(this).attr("name", thisname);
		    });
		  });
		};

	// 局部表单编辑、保存
	$("a.js_save").live("click", function(e){
		e.preventDefault();
		var formsection = $(this).parents(".formsection");
		var formEl = formsection.find("form");
		var posturl = formEl.attr("action");
		 //首先重命名
		formEl.dataForm();
		var validator = formsection.validate();
		var isTrue=validator.validateForm();
		formsection.find(".txtDate").blur();
		if(isTrue){
			formsection.find(".txtDate").each(function(){
				$(this).val($(this).attr("onlymonthdate"));
			});
			$.post(posturl, formEl.serialize(), function(data){
//				formsection.html(backtable);
                if(data.status == 'ok') {
                    window.parent.location.reload();
                } else {
                    alert(data.message);
                }
			});
		}

	});

	$("a.js_cancel").live("click",function(e){
		e.preventDefault();
		var formsection = $(this).parents(".formsection");
		var posturl = $(this).attr("action");
		$.get(posturl,function(page){
			formsection.html(page);		
		});
	});
	$("a.js_edit").live("click",function(e){
		e.preventDefault();
		var formsection = $(this).parents(".formsection");
		var posturl = $(this).attr("action");
		$.get(posturl,function(page){
			formsection.html(page);	
			formsection.find(".txtDate").date_input();
			bindAddRemoveFn();
		});
	});
	
	//点击打回弹窗提交按钮
	$("a.js_rollback").live("click",function(e){
		e.preventDefault();
		var posturl = $(this).attr("action"),
            rollbackReason = $("#rollbackReason").val();
        if($.trim(rollbackReason) == "") {
            alert("请填写打回原因！");
            return;
        }
		$.post(posturl, {rollbackReason:$("#rollbackReason").val()},function(data){
            if(data.status == "ok") {
                hidePopupDiv($('#layer_form'));
                $("#statusDiv").attr("class", "boxWrapYellow center f14 mt_10 mb_10");
                $("#statusDiv").html("档案未完善<div class='mt_10 f12'>打回原因：" + rollbackReason + "</div>");
            } else {
                alert(data.message);
            }
		});
	});

/*  
 * 表单内部交互  
 */
	// 星座
 	function getXinzuo(birthGL){
 		var birthGLObj = birthGL.match(/^(\d{4,4})-(\d{2,2})-(\d{2,2})$/);
		var year = birthGLObj[1],
		    month = birthGLObj[2],
		    day = birthGLObj[3];
		if (month == 1 && day >=20 || month == 2 && day <=18) return {id:6001,text:"水瓶座",date:"1月20日-2月18日"};
		if (month == 2 && day >=19 || month == 3 && day <=20) return {id:6002,text:"双鱼座",date:"2月19日-3月20日"};
		if (month == 3 && day >=21 || month == 4 && day <=19) return {id:6003,text:"白羊座",date:"3月21日-4月19日"};
		if (month == 4 && day >=20 || month == 5 && day <=20) return {id:6004,text:"金牛座",date:"4月20日-5月20日"};
		if (month == 5 && day >=21 || month == 6 && day <=21) return {id:6005,text:"双子座",date:"5月21日-6月21日"};
		if (month == 6 && day >=22 || month == 7 && day <=22) return {id:6006,text:"巨蟹座",date:"6月22日-7月22日"};
		if (month == 7 && day >=23 || month == 8 && day <=22) return {id:6007,text:"狮子座",date:"7月23日-8月22日"};
		if (month == 8 && day >=23 || month == 9 && day <=22) return {id:6008,text:"处女座",date:"8月23日-9月22日"};
		if (month == 9 && day >=23 || month == 10 && day <=22) return {id:6009,text:"天秤座",date:"9月23日-10月23日"};
		if (month == 10 && day >=23 || month == 11 && day <=21) return {id:6010,text:"天蝎座",date:"10月24日-11月21日"};
		if (month == 11 && day >=22 || month == 12 && day <=21) return {id:6011,text:"射手座",date:"11月22日-12月21日"};
		if (month == 12 && day >=22 || month == 1 && day <=19) return {id:6012,text:"摩羯座",date:"12月22日-1月19日"};
 	};
	$("#h_solar_birthday").live("change", function(){
		var xzObj = getXinzuo($('#b_gongli').val());
		$(".js_constellation").text(xzObj.text);
		$("input[name=constellation]").val(xzObj.id);
	});
	// 户口所在地级联
	$("select[name=familyAddressProvince]").live("change",function(){
		var provinceCode=$(this).val();
		var newOption="<option value=''>请选择城市</option>";
		$("select[name=familyAddressCity]").html(newOption);
		if (provinceCode !=''){
			var url="/archives/"+provinceCode+"/getCityByProvinceCode";
			$.post(url,function(data){
				if(data && data.cityList){
					var cs=data.cityList;
					var len=cs.length;
					for ( var i = 0; i < len; i++) {
						$("select[name=familyAddressCity]").append("<option value='"+cs[i].optionCode+"'>"+cs[i].optionValue+"</option>");
					}
				}
			});
		}
	});

function bindAddRemoveFn(){
	
	// 添加行操作
	$('.js_workExperience').addRemoveRow({
		buildDataRowContent: function(self){
			return $('#workExperiences').html();
		},
		addCallback: function(newRow){
			newRow.find('.txtDate').date_input();
		},
		renameCtrl: function(container, currentRow){
			renameToCompare(container, currentRow);
		}
	});
	$('.js_trainingExperiences').addRemoveRow({
		buildDataRowContent: function(self){
			return $('#trainingExperiences').html();
		},
		addCallback: function(newRow){
			newRow.find('.txtDate').date_input();
		},
		renameCtrl: function(container, currentRow){
			renameToCompare(container, currentRow);
		}
	});
	$('.js_familyMembers').addRemoveRow({
		buildDataRowContent: function(self){
			return $('#familyMembers').html();
		},
		addCallback: function(newRow){
			newRow.find('.txtDate').date_input();
		},
		renameCtrl: function(){
			return ;			
		}
		});
}


//如果新添加行有日期时间对比需求在renameCtrl中调用
function renameToCompare(container, currentRow){
	container.data('maxid', Number(container.data('maxid'))+1);
	currentRow.find('[comparedateto]').each(function(){
		$(this).attr('id', $(this).attr('id')+container.data('maxid'));
		$(this).attr('comparedateto', $(this).attr('comparedateto')+container.data('maxid'));
	});
	currentRow.find('[comparedatefrom]').each(function(){
		$(this).attr('id', $(this).attr('id')+container.data('maxid'));
		$(this).attr('comparedatefrom', $(this).attr('comparedatefrom')+container.data('maxid'));
	});		
	currentRow.find('[groupby]').each(function(){
		$(this).attr('groupby', $(this).attr('groupby')+container.data('maxid'));
	});
}
});