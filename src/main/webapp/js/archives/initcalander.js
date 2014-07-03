$(function(){
	//初始化农历生日日历
	createLunerYear();  //年
	createLunarMonth(); //月
	createLunarDate();  //日

	//初始化农历生日日期
	if($.trim($("#h_nongli").val()) != ""){
		var arrDate = $.trim($("#h_nongli").val()).split(",");
		setNongliVal(arrDate[0], arrDate[1], arrDate[2]);

		var year = $.trim($("#nl_year").val());
		var month = $.trim($("#nl_month").val());
		var date = $.trim($("#nl_date").val());
		var arrDateGongli = convertLunarToSolar(year, month, date);

		year = arrDateGongli[0];
		month = arrDateGongli[1] < 10 ? "0"+arrDateGongli[1] : arrDateGongli[1];
		date = arrDateGongli[2] < 10 ? "0"+arrDateGongli[2] : arrDateGongli[2];
		var dateGongli = year + "-" + month + "-" + date;
		$("#b_gongli").val(dateGongli);
	}

	//生日公历农历切换
	$("#birthday_type").change(function(){
		if($.trim($(this).val()) == "1"){ //公历
			$("#b_gongli").removeClass('hideit');
			$("#b_nongli").addClass('hideit');
		}
		else{ //农历
			$("#b_nongli").removeClass('hideit');
			$("#b_gongli").addClass('hideit');
		}
		
		$('#b_gongli').change();
	});
	
	//公历生日同步农历生日
	$("#b_gongli").change(function(){
		// if( !regDate.test($.trim($(this).val())) ) return false;
		
		if($.trim($(this).val()) == "") return false;

		//转换为农历
		var d = new Date($.trim($(this).val()).replace(/-/g, "/"));
		var year = d.getFullYear();
		var month = d.getMonth()+1;
		var date = d.getDate();
		var arrDateNongli = convertSolarToLunar(year, month, date);
		
		//写入农历下拉框
		year = arrDateNongli[0];
		month = arrDateNongli[4];
		date = arrDateNongli[5];
		setNongliVal(year, month, date);
	});

	//农历生日同步公历生日
	$("#nl_year, #nl_month, #nl_date").change(function(){
		var year = $.trim($("#nl_year").val());
		var month = $.trim($("#nl_month").val());
		var date = $.trim($("#nl_date").val());
		var arrDateGongli = convertLunarToSolar(year, month, date);

		year = arrDateGongli[0];
		month = arrDateGongli[1] < 10 ? "0"+arrDateGongli[1] : arrDateGongli[1];
		date = arrDateGongli[2] < 10 ? "0"+arrDateGongli[2] : arrDateGongli[2];
		var dateGongli = year + "-" + month + "-" + date;
		$("#b_gongli").val(dateGongli);
	});
});


function convertToGongli(){
	var year = $.trim($("#nl_year").val());
	var month = $.trim($("#nl_month").val());
	var date = $.trim($("#nl_date").val());
	var arrDateGongli = convertLunarToSolar(year, month, date);

	year = arrDateGongli[0];
	month = arrDateGongli[1] < 10 ? "0"+arrDateGongli[1] : arrDateGongli[1];
	date = arrDateGongli[2] < 10 ? "0"+arrDateGongli[2] : arrDateGongli[2];
	var dateGongli = year + "-" + month + "-" + date;
	
	return dateGongli;
}

//写入农历生日
function setNongliVal(year,month,date){
	//年
	$("#nl_year").val(year);
	//setSelectCtrl("nl_year",year);
	//月
	createLunarMonth();
	$("#nl_month").val(month);
	//setSelectCtrl("nl_month",month);
	//日
	createLunarDate();
	$("#nl_date").val(date);
	//setSelectCtrl("nl_date",date);
}


function setSelectCtrl(selId, val){
	setTimeout(function() {
		$("#"+selId+" option").attr("selected",true);
		$("#"+selId+" option[value='"+val+"']").attr("selected",true);
	}, 1);
}