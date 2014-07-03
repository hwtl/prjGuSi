package com.gusi.boms.helper;

import com.gusi.boms.model.EmployeeSearch;
import com.gusi.boms.model.VEmployeeBaseInfor;
import com.dooioo.webplus.convenient.LunarSolarUtil;

import org.apache.commons.lang.StringUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.helper
 * Author: Jerry.hu
 * Create: Jerry.hu(2013-04-08 16:48)
 * Description: 员工对应的辅助类
 */
public class EmployeeHelper {
    public static final String DEFAULT_PASSWORD = "123456";
    /**
     * @param userCode
     * @return 生成黑名单记录序列号
     */
    public static String createBlackSerialNo(Integer userCode)
    {
    	 SimpleDateFormat sm=new SimpleDateFormat("yyMMdd");
    	 DecimalFormat nf=new DecimalFormat("0000"); 
    	 Random random=new Random();
    	return "HRM-"+userCode+"-"+sm.format(new Date())+"-"+nf.format((random.nextInt()>>>1)%1000+1);
    }
    /**
     * 生成密码
     * 截取身份证后6位
     * @return  String
     */
    public static String generatePassword(String idCardNo){
        if(StringUtils.isNotBlank(idCardNo) && idCardNo.length() >= 6)
            return idCardNo.substring(idCardNo.length() - 6).toLowerCase();
        return DEFAULT_PASSWORD;
    }

    /**
     * @param date  日期
     * @return 获取合同结束时间
     */
    public static Date getConstractEndTime(Date date)
    {
        Calendar c=Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.YEAR, 3);
        c.add(Calendar.DAY_OF_MONTH, -1);
        return c.getTime();
    }

    public static String getWhere(EmployeeSearch employeeSearch){
        VEmployeeBaseInfor vp  = new VEmployeeBaseInfor();
        vp.setWhere(" company = ''"+employeeSearch.getCompany()+"''");
        vp.setWhere(vp.getWhere() +" and status in (" + employeeSearch.getStatus()+")");
        if(!StringUtils.isBlank(employeeSearch.getKeyWords())){
            vp.setWhere(vp.getWhere() +" and keywords like ''%" + employeeSearch.getKeyWords() + "%''");
        }
        if(employeeSearch.getBlack() == 1 ){
            vp.setWhere(vp.getWhere() +" and isBlack = 1");
        }
        if(!StringUtils.isBlank(employeeSearch.getComeFrom())){
            vp.setWhere(vp.getWhere() +" and comeFrom = "+ EmployeeSearch.getFormatKey(employeeSearch.getComeFrom()));
        }
        if(!StringUtils.isBlank(employeeSearch.getConstellation())){
            vp.setWhere(vp.getWhere() +" and constellation = "+ EmployeeSearch.getFormatKey(employeeSearch.getConstellation()));
        }
        if(!StringUtils.isBlank(employeeSearch.getArchiveStatus())){
            vp.setWhere(vp.getWhere() +" and archiveStatus = "+ employeeSearch.getArchiveStatus());
        }
        if(!StringUtils.isBlank(employeeSearch.getLastDegree())){
            vp.setWhere(vp.getWhere() +" and lastDegree in ("+employeeSearch.getLastDegreeStr()+")");
        }
        if(!StringUtils.isBlank(employeeSearch.getOrgIds())){
            vp.setWhere(vp.getWhere() +" and orgId in ("+employeeSearch.getOrgIds()+")");
        }
        if(!StringUtils.isBlank(employeeSearch.getPositionIds())){
            vp.setWhere(vp.getWhere() +" and positionId in ("+employeeSearch.getPositionIds()+")");
        }
        if(!StringUtils.isBlank(employeeSearch.getJoinDateStart())){
            vp.setWhere(vp.getWhere() +" and joinDate >= " + EmployeeSearch.getFormatKey(employeeSearch.getJoinDateStart()+" 00:00:00"));
        }
        if(!StringUtils.isBlank(employeeSearch.getJoinDateEnd())){
            vp.setWhere(vp.getWhere() +" and joinDate <= " + EmployeeSearch.getFormatKey(employeeSearch.getJoinDateEnd()+" 23:59:59"));
        }
        if(!StringUtils.isBlank(employeeSearch.getLeaveDateStart())){
            vp.setWhere(vp.getWhere() +" and leaveDate >= " + EmployeeSearch.getFormatKey(employeeSearch.getLeaveDateStart()+" 00:00:00"));
        }
        if(!StringUtils.isBlank(employeeSearch.getLeaveDateEnd())){
            vp.setWhere(vp.getWhere() +" and leaveDate <= " + EmployeeSearch.getFormatKey(employeeSearch.getLeaveDateEnd()+" 23:59:59"));
        }
        if(employeeSearch.getOfficialYear() != null && employeeSearch.getOfficialYear()>0){
            vp.setWhere(vp.getWhere() +" and officialYear = "+employeeSearch.getOfficialYear());
        }
        if(employeeSearch.getOfficialMonth() != null && employeeSearch.getOfficialMonth()>0){
            vp.setWhere(vp.getWhere() +" and officialMonth = "+employeeSearch.getOfficialMonth());
        }
        if(employeeSearch.getOfficialDay() != null && employeeSearch.getOfficialDay()>0){
            vp.setWhere(vp.getWhere() +" and officialDay = "+employeeSearch.getOfficialDay());
        }
        
        //查询生日 ，阳历转换阴历,生日只是当前年
        Date birthFrom=employeeSearch.getBirthDateFrom();
        Date birthTo=employeeSearch.getBirthDateTo();
        if (birthFrom !=null && birthTo !=null) {
        	//如果有起止时间，则转换农历
        	Calendar birthFromCal=LunarSolarUtil.date2Calendar(birthFrom);
        	Calendar birthToCal=LunarSolarUtil.date2Calendar(birthTo);
        	// 算法 bornMonth*100+bornDayTime,公历日期区间
        	int gongliNumFrom=((birthFromCal.get(Calendar.MONTH)+1)*100)+
        			birthFromCal.get(Calendar.DAY_OF_MONTH);
        	int gongliNumTo=((birthToCal.get(Calendar.MONTH)+1)*100)+
        			birthToCal.get(Calendar.DAY_OF_MONTH);

        	//计算公历对应农历日期的区间

        	int[] lunarInfoFrom=LunarSolarUtil.solarToLunar(birthFrom);
        	int[] lunarInfoTo=LunarSolarUtil.solarToLunar(birthTo);
        	//不跨年，生日位于from <= month*100+day <= to，跨年农历生日 >=fromMonth*100+day or 生日 <=toMonth*100+day
        	int nongliNumFrom=(lunarInfoFrom[1]*100)+lunarInfoFrom[2];
        	int nongliNumTo=(lunarInfoTo[1]*100)+lunarInfoTo[2];;
        	//判断农历是否跨年
        	boolean isNongliNextYear=(lunarInfoFrom[0] != lunarInfoTo[0]);
        	//生成SQL语句
        	vp.setWhere(vp.getWhere()+" and (((lunarCalendar is null or lunarCalendar=1) and (bornMonth*100+bornDayTime) between " +
        			+gongliNumFrom +" and "+gongliNumTo+") or (lunarCalendar =0 and (" );
        	if (isNongliNextYear) {
        		//如果农历跨年
        		vp.setWhere(vp.getWhere()+" (bornMonth*100+bornDayTime) >= "+nongliNumFrom+" or (bornMonth*100+bornDayTime) <="
        				+nongliNumTo+"))) ");
        	}else{
        		//不跨年
        		vp.setWhere(vp.getWhere()+"(bornMonth*100+bornDayTime) between " +
        				+nongliNumFrom +" and "+nongliNumTo+"))) ");
        	}
        }else if (birthFrom !=null) {
        	Calendar birthFromCal=LunarSolarUtil.date2Calendar(birthFrom);
        	//公历对应的农历日期
        	int[] lunarInfoFrom=LunarSolarUtil.solarToLunar(birthFrom);
        	
        	vp.setWhere(vp.getWhere()+" and (((lunarCalendar is null or lunarCalendar=1) and bornMonth = " +
        			(birthFromCal.get(Calendar.MONTH)+1)+" and bornDayTime= "+birthFromCal.get(Calendar.DAY_OF_MONTH) 
        			+") or (lunarCalendar =0 and bornMonth = "+
        			(lunarInfoFrom[1])+" and bornDayTime= "+lunarInfoFrom[2]+"))" );
        }else if (birthTo !=null) {
        	Calendar birthToCal=LunarSolarUtil.date2Calendar(birthTo);
        	//公历对应的农历日期
        	int[] lunarInfoTo=LunarSolarUtil.solarToLunar(birthTo);
        	
        	vp.setWhere(vp.getWhere()+" and (((lunarCalendar is null or lunarCalendar=1) and bornMonth = " +
        			(birthToCal.get(Calendar.MONTH)+1)+" and bornDayTime= "+birthToCal.get(Calendar.DAY_OF_MONTH) 
        			+") or (lunarCalendar =0 and bornMonth = "+
        			(lunarInfoTo[1])+" and bornDayTime= "+lunarInfoTo[2]+"))" );
		}
        if(employeeSearch.getBornYear() > 0){
        	vp.setWhere(vp.getWhere() +" and bornYear = "+employeeSearch.getBornYear());
        }
        if(employeeSearch.getBornMonth() > 0){
        	vp.setWhere(vp.getWhere() +" and bornMonth = "+employeeSearch.getBornMonth());
        }
        if(employeeSearch.getBornDay() > 0){
        	vp.setWhere(vp.getWhere() +" and bornDayTime = "+employeeSearch.getBornDay());
        }
        if(StringUtils.isNotBlank(employeeSearch.getTags())) {
            vp.setWhere(vp.getWhere() + " and exists ( SELECT 1 FROM t_oms_employee_tag WITH(NOLOCK) WHERE userCode = l.userCode AND tagCode IN (" + employeeSearch.getTags() + ") ) ");
        }
        return vp.getWhere();
    }

    public static String getWhereForExecl(EmployeeSearch employeeSearch){
    	VEmployeeBaseInfor vp  = new VEmployeeBaseInfor();
    	vp.setWhere("eb.status in (" + employeeSearch.getStatus()+")");
    	if(!StringUtils.isBlank(employeeSearch.getKeyWords())){
    		vp.setWhere(vp.getWhere() +" and k.keywords like ''%" + employeeSearch.getKeyWords() + "%''");
        }
        if(employeeSearch.getBlack() == 1 ){
            vp.setWhere(vp.getWhere() +" and eb.isBlack = 1");
        }
        if(!StringUtils.isBlank(employeeSearch.getConstellation())){
            vp.setWhere(vp.getWhere() +" and d.constellation = "+ EmployeeSearch.getFormatKey(employeeSearch.getConstellation()));
        }
        if(!StringUtils.isBlank(employeeSearch.getLastDegree())){
            vp.setWhere(vp.getWhere() +" and d.lastDegree in ("+employeeSearch.getLastDegreeStr()+")");
        }
        if(!StringUtils.isBlank(employeeSearch.getOrgIds())){
            vp.setWhere(vp.getWhere() +" and eb.orgId in ("+employeeSearch.getOrgIds()+")");
        }
        if(!StringUtils.isBlank(employeeSearch.getPositionIds())){
            vp.setWhere(vp.getWhere() +" and eb.positionId in ("+employeeSearch.getPositionIds()+")");
        }
        if(!StringUtils.isBlank(employeeSearch.getJoinDateStart())){
            vp.setWhere(vp.getWhere() +" and eb.joinDate >= " + EmployeeSearch.getFormatKey(employeeSearch.getJoinDateStart()+" 00:00:00"));
        }
        if(!StringUtils.isBlank(employeeSearch.getJoinDateEnd())){
            vp.setWhere(vp.getWhere() +" and eb.joinDate <= " + EmployeeSearch.getFormatKey(employeeSearch.getJoinDateEnd()+" 23:59:59"));
        }
        if(!StringUtils.isBlank(employeeSearch.getLeaveDateStart())){
            vp.setWhere(vp.getWhere() +" and eb.joinDate >= " + EmployeeSearch.getFormatKey(employeeSearch.getLeaveDateStart()+" 00:00:00"));
        }
        if(!StringUtils.isBlank(employeeSearch.getLeaveDateEnd())){
            vp.setWhere(vp.getWhere() +" and eb.joinDate <= " + EmployeeSearch.getFormatKey(employeeSearch.getLeaveDateEnd()+" 23:59:59"));
        }
        if(employeeSearch.getOfficialYear() > 0){
            vp.setWhere(vp.getWhere() +" and d.officialYear = "+employeeSearch.getOfficialYear());
        }
        if(employeeSearch.getOfficialMonth() > 0){
            vp.setWhere(vp.getWhere() +" and d.officialMonth = "+employeeSearch.getOfficialMonth());
        }
        if(employeeSearch.getOfficialDay() > 0){
            vp.setWhere(vp.getWhere() +" and d.officialDay = "+employeeSearch.getOfficialDay());
        }
        if(employeeSearch.getBornYear() > 0){
            vp.setWhere(vp.getWhere() +" and d.bornYear = "+employeeSearch.getBornYear());
        }
        if(employeeSearch.getOfficialYear() > 0){
            vp.setWhere(vp.getWhere() +" and d.bornMonth = "+employeeSearch.getBornMonth());
        }
        if(employeeSearch.getOfficialYear() > 0){
            vp.setWhere(vp.getWhere() +" and d.bornDay = "+employeeSearch.getBornDay());
        }
        return vp.getWhere();
    }


}
