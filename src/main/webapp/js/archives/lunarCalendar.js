var lunarCalendar = {
	lunarInfo : new Array( 0x04bd8,0x04ae0,0x0a570,0x054d5,0x0d260,0x0d950,0x16554,0x056a0,0x09ad0,0x055d2,0x04ae0,0x0a5b6,0x0a4d0,0x0d250,0x1d255,0x0b540,0x0d6a0,0x0ada2,0x095b0,0x14977,0x04970,0x0a4b0,0x0b4b5,0x06a50,0x06d40,0x1ab54,0x02b60,0x09570,0x052f2,0x04970,0x06566,0x0d4a0,0x0ea50,0x06e95,0x05ad0,0x02b60,0x186e3,0x092e0,0x1c8d7,0x0c950,0x0d4a0,0x1d8a6,0x0b550,0x056a0,0x1a5b4,0x025d0,0x092d0,0x0d2b2,0x0a950,0x0b557,0x06ca0,0x0b550,0x15355,0x04da0,0x0a5d0,0x14573,0x052d0,0x0a9a8,0x0e950,0x06aa0,0x0aea6,0x0ab50,0x04b60,0x0aae4,0x0a570,0x05260,0x0f263,0x0d950,0x05b57,0x056a0,0x096d0,0x04dd5,0x04ad0,0x0a4d0,0x0d4d4,0x0d250,0x0d558,0x0b540,0x0b5a0,0x195a6,0x095b0,0x049b0,0x0a974,0x0a4b0,0x0b27a,0x06a50,0x06d40,0x0af46,0x0ab60,0x09570,0x04af5,0x04970,0x064b0,0x074a3,0x0ea50,0x06b58,0x055c0,0x0ab60,0x096d5,0x092e0,0x0c960,0x0d954,0x0d4a0,0x0da50,0x07552,0x056a0,0x0abb7,0x025d0,0x092d0,0x0cab5,0x0a950,0x0b4a0,0x0baa4,0x0ad50,0x055d9,0x04ba0,0x0a5b0,0x15176,0x052b0,0x0a930,0x07954,0x06aa0,0x0ad50,0x05b52,0x04b60,0x0a6e6,0x0a4e0,0x0d260,0x0ea65,0x0d530,0x05aa0,0x076a3,0x096d0,0x04bd7,0x04ad0,0x0a4d0,0x1d0b6,0x0d250,0x0d520,0x0dd45,0x0b5a0,0x056d0,0x055b2,0x049b0,0x0a577,0x0a4b0,0x0aa50,0x1b255,0x06d20,0x0ada0 ),
	/* 对lunarInfo的说明：
	 * 数组元素为1900年开始的每一年的信息，每一个元素以16进制数展示
	 * 数组元素代表的年份信息以二进制记录，从右向左依次为：
	 * 1-4位 表示闰月月份，如非闰年则为0000；
	 * 16-5位 依次表示1-12月份是大月还是小月，1为大月30天，0为小月29天
	 * 17-20位 表示如果有闰月的话， 闰月是大月还是小月 
	*/

	monthInfo : ['正', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '腊'],
	dayInfo : ['初一', '初二', '初三', '初四', '初五', '初六', '初七', '初八', '初九', '初十', '十一', '十二', '十三', '十四', '十五', '十六', '十七', '十八', '十九', '二十', '廿一', '廿二', '廿三', '廿四', '廿五', '廿六', '廿七', '廿八', '廿九', '三十'],

	//====================================== 传回农历 y年的总天数
	lYearDays: function(y) {
		var i, sum = 348;
		for(i=0x8000; i>0x8; i>>=1){
			sum += (this.lunarInfo[y-1900] & i)? 1: 0;
		} 
		return(sum+this.leapDays(y));
	},
	//====================================== 传回农历 y年闰月的天数
	leapDays: function(y) {
		if(this.leapMonth(y)){
			return((this.lunarInfo[y-1900] & 0x10000)? 30: 29);
		} 
		else{
			return(0);
		} 
	},
	//====================================== 传回农历 y年闰哪个月 1-12 , 没闰传回 0
	leapMonth: function(y) {
		return(this.lunarInfo[y-1900] & 0xf)
	},
	//====================================== 传回农历 y年m月的总天数
	monthDays: function(y,m) {
		return( (this.lunarInfo[y-1900] & (0x10000>>m))? 30: 29 )
	},
	//====================================== 传回阳历 y年是否闰年
	isLeap: function(year) {
	    return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
	},
	//====================================== 公历-->农历
	solar2lunar: function(date) {
		var self = {};
	    var objDate = new Date(date.getFullYear(), date.getMonth(), date.getDate());
	    var i, leap=0, temp=0;
	    var baseDate = new Date(1900,0,31);
	    // Mac和linux平台的firefox在此处会产生浮点数错误
	    var offset = Math.round((objDate - baseDate)/86400000);

	    self.dayCyl = offset + 40;
	    self.monCyl = 14;

	    for(i=1900; i<2050 && offset>0; i++) {
			temp = this.lYearDays(i);
			offset -= temp;
			self.monCyl += 12;
	    }

	    if(offset<0) {
			offset += temp;
			i--;
			self.monCyl -= 12;
	    }

	    self.year = i;
	    self.yearCyl = i-1864;

	    leap = this.leapMonth(i); //闰哪个月
	    self.isLeap = false;

	    for(i=1; i<13 && offset>0; i++) {
			//闰月
			if(leap>0 && i==(leap+1) && self.isLeap==false){ 
				--i; 
				self.isLeap = true; 
				temp = this.leapDays(self.year); 
			}
			else{ 
				temp = this.monthDays(self.year, i); 
			}

			//解除闰月
			if(self.isLeap==true && i==(leap+1)){
				self.isLeap = false;
			} 

			offset -= temp;
			if(self.isLeap == false){
				self.monCyl ++;
			} 
	    }

	    if(offset==0 && leap>0 && i==leap+1){
	    	if(self.isLeap){ 
	    		self.isLeap = false; 
	    	}
		    else{ 
		    	self.isLeap = true; 
		    	--i; 
		    	--self.monCyl;
		    }
	    }

	    if(offset<0){ 
	    	offset += temp; 
	    	--i; 
	    	--self.monCyl; 
	    }

	    self.month = i;
	    self.day = offset + 1;
	    return {
	    	year: self.year,
	    	month: self.month,
	    	day: self.day,
	    	isLeap:self.isLeap
	    }
	},
	//====================================== 农历-->公历
	lunar2solar: function (date, isLeapMonth) {
		var self = {};
	    var lyear = date.getFullYear(),
			lmonth = date.getMonth() + 1,
			lday = date.getDate(), //农历年月日
			offset = 0,//偏移天数（从1900年1月31号开始）
			leap = this.isLeap(lyear);//是否闰年

	    // increment year  求1900年开始至当前年份前一年的天数加总
	    for(var i = 1900; i < lyear; i++) {
			offset += this.lYearDays(i);
	    }

	    // increment month
	    // add days in all months up to the current month
	    for (var i = 1; i < lmonth; i++) {
			// add extra days for leap month
			if (i == this.leapMonth(lyear)) {
				offset += this.leapDays(lyear);
			}
			offset += this.monthDays(lyear, i);
	    }
	    // if current month is leap month, add days in normal month
	    if (isLeapMonth) {//(leap && isLeapMonth)
			offset += this.monthDays(lyear, i);
	    }

	    // increment
	    offset += parseInt(lday)-1;

	    var baseDate = new Date(1900,0,31);
	    var solarDate = new Date(baseDate.valueOf() + offset * 86400000);

	    return {
	    	year : solarDate.getFullYear(),
		    month : solarDate.getMonth()+1,
		    day : solarDate.getDate(),
		    isLeap : leap
	    }
	}
}