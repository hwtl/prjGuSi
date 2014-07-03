$(function(){	
	initYear();//农历生日--初始化年份下拉框
	
	$("#birthday_type").change(function(){//生日下拉框切换公历农历
		if($(this).val() == 1){ //公历
			$("#b_selYear, #b_selMonth, #b_selDay").hide();
			$("#b_gongli").show();
		}
		else{ //==0 ，农历
			$("#b_gongli").hide();
			$("#b_selYear, #b_selMonth, #b_selDay").show();
		}
		getBirthdayData();
	});

	//农历生日-切换年份，生成农历月份，清空农历日期
	$("#b_selYear").change(function(){
		var htmlMonth = '<option value="">月</option>';
		if($(this).val() == ""){
			$("#b_selMonth").html(htmlMonth);
		}
		else{
			var selYear = $.trim($(this).val());
			var leapM = lunarCalendar.leapMonth(selYear);

			for(var i=0; i<12; i++){
				//有闰月按12个月处理(如九月和闰九月均处理为yyyy-09-dd)
				htmlMonth += '<option value="'+i+'">'+lunarCalendar.monthInfo[i]+'月</option>'; 
				if((i+1) == leapM) {//闰月
					htmlMonth += '<option value="'+i+'" isleap="true">闰'+lunarCalendar.monthInfo[i]+'月</option>';
				}
			}
			$("#b_selMonth").html(htmlMonth);
		}
		var htmlDay = '<option value="">日</option>';
		$("#b_selDay").html(htmlDay);
	});

	//农历生日--切换月份，生成农历日期
	$("#b_selMonth").change(function(){
		var htmlDay = '<option value="">日</option>';
		if($(this).val() == ""){
			$("#b_selDay").html(htmlDay);
		}
		else{
			var selYear = $.trim($("#b_selYear").val());
			var selMonth = Number($.trim($(this).val()))+1;
			var days = lunarCalendar.monthDays(selYear, selMonth);
			var leapDs = lunarCalendar.leapDays(selYear);
			if($("#b_selMonth option:selected").attr("isleap")=="true"){
				days = leapDs;
			}
			for(var i=1; i<=days; i++){
				htmlDay += '<option value="'+i+'">'+lunarCalendar.dayInfo[i-1]+'</option>';
			}
			$("#b_selDay").html(htmlDay);
		}
	});

	//农历生日--初始化年份下拉框（从1900至今）
	function initYear(){
		var htmlYear = '<option value="">年</option>';
		var currentYear = ($("#b_gongli").attr("yearTo") == undefined || $("#b_gongli").attr("yearTo") == "") ? new Date().getFullYear() : Number($("#b_gongli").attr("yearTo"));
		for(var i=currentYear; i>=1936; i--){
			htmlYear += '<option value="'+i+'">'+i+'</option>'
		}
		$("#b_selYear").html(htmlYear);
	}

	
	//公历日历日期切换
	var isConvertingFlag = false;
	$("#b_gongli").change(function(){
		if(isConvertingFlag){
			isConvertingFlag = false; 
			return false;
		}

		var solarBirth = new Date($(this).val());
		//农历日期下拉框赋值
		var lunarBirth = lunarCalendar.solar2lunar(solarBirth);
		$("#b_selYear").val(lunarBirth.year).trigger("change");//年
		window.setTimeout(function(){//月
			var leapMonth = lunarCalendar.leapMonth(lunarBirth.year);
			if(leapMonth == 0 || leapMonth!=0 && lunarBirth.isLeap){//没闰月，或有闰月且是闰月
				$("#b_selMonth").val(lunarBirth.month-1).trigger("change");
			}
			else{ //有闰月且非闰月
				if(lunarBirth.month <= leapMonth){//闰月及之前的
					$("#b_selMonth option").eq(lunarBirth.month).attr("selected", "selected").trigger("change");
				}
				else{//闰月之后的月份
					$("#b_selMonth option").eq(lunarBirth.month+1).attr("selected", "selected").trigger("change");
				}
			}
			window.setTimeout(function(){//日
				$("#b_selDay").val(lunarBirth.day);
				getBirthdayData();//生日数据向隐含域赋值
			},10);
		},10);
	});

	//农历日历日期切换
	$("#b_selYear, #b_selMonth, #b_selDay").change(function(){
		if($("#b_selYear").val() != "" && $("#b_selMonth").val() != "" && $("#b_selDay").val() != ""){
			var isLeapMonth = $("#b_selMonth option:selected").attr("isleap") == undefined ? false : true;
			var solarBirth = lunarCalendar.lunar2solar(new Date($("#b_selYear").val(), $("#b_selMonth").val(), $("#b_selDay").val()), isLeapMonth);
			isConvertingFlag = true;
			$("#b_gongli").val(solarBirth.year + "-" + formatOX(solarBirth.month) + "-" + formatOX(solarBirth.day)).trigger("change");
		}
		getBirthdayData();//生日数据向隐含域赋值
	});

	//初始化，如果公历日期不为空，则触发其change事件（为了触发计算农历生日的值，以及提交给后台用的值）
	$(".txtDate").date_input();
	if($("#b_gongli").val()!=""){
		$("#b_gongli").trigger("change");
	}
	$("#birthday_type").trigger("change");
	$("#b_gongli").next(".date_selector").find(".js_clearDate").remove();
});

function getBirthdayData(){//生日数据处理（提交给后台用的）
	//向隐含域赋值
	$("#h_solar_birthday").val($("#b_gongli").val()).trigger("change");//公历完整日期（用于记录），如1988-01-17
	
	//分段日期(根据生日类型，存放公历或农历日期--用于搜索)
	if($("#birthday_type option:selected").val() == 1){//公历
		if($("#b_gongli").val()!=""){
			var solarBirth = new Date($("#b_gongli").val());
			$("#h_birthdayY").val(solarBirth.getFullYear());//如，1988
			$("#h_birthdayM").val(solarBirth.getMonth()+1);//如，1
			$("#h_birthdayD").val(solarBirth.getDate());//如，17
		}
		else{
			$("#h_birthdayY, #h_birthdayM, #h_birthdayD").val("");
		}
	}
	else{//0--农历
		$("#h_birthdayY").val($("#b_selYear").val());
		$("#h_birthdayM").val($("#b_selMonth").val()!="" ? Number($("#b_selMonth").val())+1 : "");
		$("#h_birthdayD").val($("#b_selDay").val());
	}
}

//数据格式化，用于处理日期中将月份、日期由m,d改为mm,dd
function formatOX(n){
	return n<10 ? "0"+n : n;
}