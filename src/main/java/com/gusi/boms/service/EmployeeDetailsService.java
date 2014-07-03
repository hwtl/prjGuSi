package com.gusi.boms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.activemq.helper.EmployeeSenderHelper;
import com.gusi.boms.helper.AdHelper;
import com.gusi.boms.model.EmployeeBaseInfor;
import com.gusi.boms.model.Parameter;
import com.gusi.boms.util.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.EmployeeDetails;
import com.gusi.boms.model.VEmployeeBaseInfor;
import com.dooioo.dymq.annotation.ActiveMQTransactional;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.service.BaseService;

/**
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午09:49:39 )
 */
@Service
@Transactional
public class EmployeeDetailsService extends BaseService<EmployeeDetails> {

	@Autowired
	private ParameterService parameterService;
	@Autowired
	private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private EmployeeSenderHelper employeeSenderHelper;
    @Autowired
    private AdHelper adHelper;
    @Autowired
    private EmployeeTagService employeeTagService;

    /**
     * @param phone
     * @return 验证mobilePhone是否已存在
     */
    public boolean validatePhone(String phone,Employee employee)
    {
        if(StringUtils.isEmpty(phone))
        {
            return false;
        }
        Map<String,Object> m = new HashMap<>();
        m.put("phone",phone);
        m.put("company", employee.getCompany());
        return count(sqlId("validatePhone"),m)==0;
    }

    /**
     * @param userCode
     * @return 根据工号查询员工档案
     */
    public EmployeeDetails findForDetail(int userCode)
    {
    	return findById(sqlId("findForDetail"), userCode);
    }
    /**
     * @param userCode
     * @return 根据工号查询员工档案
     */
    public EmployeeDetails findForEdit(int userCode)
    {
    	return findById(sqlId("findForEdit"), userCode);
    }

    /**
     * @param userCode
     * @return 更新确认档案
     */
    public boolean updateChecked(int userCode,int updator)
    {
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("userCode", userCode);
    	params.put("updator", updator);
    	return update(sqlId("updateChecked"), params);
    }

    /**
     * 确认档案并且处理百强高校标签
     * @since: 2014-05-21 11:30:30
     * @param userCode
     * @param updator
     * @param isBq
     * @return
     */
    public boolean updateChecked(int userCode,int updator, boolean isBq) {
        if(isBq) {
            employeeTagService.addBqTag(userCode, updator);
        } else {
            employeeTagService.removeBqTag(userCode);
        }
    	return updateChecked(userCode, updator);
    }

    /**
     * @param userCode
     * @return 撤销档案
     */
    public boolean updateUnchecked(int userCode,int updator)
    {
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("userCode", userCode);
    	params.put("updator", updator);
    	return update(sqlId("updateUnchecked"), params);
    }
    /**
     * 打回修改
     * @update: 2014-05-16 10:46:05
     * @param userCode
     * @return
     */
    public boolean rollbackArchives(int userCode,String rollbackReason, int updator)
    {
    	Map<String,Object> params=new HashMap<String, Object>();
    	params.put("userCode", userCode);
    	params.put("rollbackReason", rollbackReason);
    	params.put("updator", updator);
    	return update(sqlId("rollbackArchives"), params);
    }
    /**
     * @param emp
     * @return 创建档案信息
     *  字段： 手机，身份证号，工号，creator
     */
    @Transactional
    public boolean createArchives(VEmployeeBaseInfor emp)
    {
    	if (emp==null) {
			return false;
		}
    	return createArchives(emp.getUserCode(), emp.getCredentialsNo(), emp.getMobilePhone(), emp.getCreator(), emp.getLastDegree());
    }

    /**
     * @param credentialsNo 身份证
     * @param phone 手机
     * @param creator 创建人
     * @return  创建档案
     */
    public boolean createArchives(int userCode,String credentialsNo,String phone,Integer creator)
    {
    	Date bornDate=getBirthdayFromCredentialsNo(credentialsNo);
    	EmployeeDetails details=new EmployeeDetails(userCode, bornDate, 1,
    			phone, userCode+"@dooioo.com",creator,-1, bornDate, 1);
    	return insert(details);
    }

    /**
     * @param credentialsNo 身份证
     * @param phone 手机
     * @param creator 创建人
     * @return  创建档案
     */
    public boolean createArchives(int userCode,String credentialsNo,String phone,Integer creator, String lastDegree)
    {
    	Date bornDate=getBirthdayFromCredentialsNo(credentialsNo);
    	EmployeeDetails details=new EmployeeDetails(userCode, bornDate, 1,
    			phone, userCode+"@dooioo.com",creator,-1, bornDate, 1);
        details.setLastDegree(lastDegree);
    	return insert(details);
    }
    /**
     * @param credentialsNo
     * @return 获取生日，默认阳历
     */
    private Date getBirthdayFromCredentialsNo(String credentialsNo){
        if(credentialsNo==null || credentialsNo.isEmpty()){
        	return null;
        }
    	StringBuffer tempStr=null;
        if(credentialsNo!=null&&credentialsNo.trim().length()>0){
                if(credentialsNo.trim().length()==15){
                        tempStr=new StringBuffer(credentialsNo.substring(6, 12));
                        tempStr.insert(4, '-');
                        tempStr.insert(2, '-');
                        tempStr.insert(0, "19");
                }else if(credentialsNo.trim().length()==18){
                        tempStr=new StringBuffer(credentialsNo.substring(6, 14));
                        tempStr.insert(6, '-');
                        tempStr.insert(4, '-');
                }
        }
        if (tempStr==null) {
			return null;
		}
        return DateFormatUtil.getDate(tempStr.toString(), "yyyy-MM-dd");
    }

	/**
     * 1、更新档案
     * 2、同步AD
     * 3、发送员工编辑mq消息
	 * @param details
	 * @param userCode
	 * @return 更新档案
	 */
    @ActiveMQTransactional
    @Transactional(rollbackFor = {Exception.class})
	public boolean updateArchives(EmployeeDetails details,int userCode,int updator) {
        EmployeeBaseInfor oldEmp = employeeBaseInforService.findByBean(userCode);
		if(details == null || oldEmp == null){
			return false;
		}
		details.setUpdator(updator);
		details.setUserCode(userCode);
		setAccountLocationAddress(details);
		  EmployeeBaseInfor eb=new EmployeeBaseInfor();
		 eb.setUserNameEn(details.getUserNameEn());
		 eb.setUserCode(userCode);
		 eb.setSex(details.getSex());
		employeeBaseInforService.update(eb);
        if(update(details)) {
            //同步AD用户
            if(!adHelper.syncAD(userCode)) {
                throw new RuntimeException("fail to sync the AD ......");
            }
            //发送员工编辑mq消息
            employeeSenderHelper.sendEmployeeBianJi(userCode, oldEmp.getOrgId());
            return true;
        }
		return false;
	}
	/**
	 * @param details 设置户口本地址
	 */
	private void setAccountLocationAddress(EmployeeDetails details)
	{
		String provinceCode=details.getFamilyAddressProvince();
		String cityCode=details.getFamilyAddressCity();
		String cityDetail=details.getFamilyAddressDetail();
		StringBuilder location=new StringBuilder();
		if (provinceCode!=null && !provinceCode.isEmpty()) {
				location.append(parameterService.findProvinces().get(provinceCode));
		}
		if (cityCode!=null && !cityCode.isEmpty()) {
			Parameter c= parameterService.findByOptionCode(cityCode);
			if (c!=null) {
				location.append(c.getOptionValue());
			}
		}
		if (cityDetail!=null) {
			location.append(cityDetail);
		}
		details.setAccountLocation(location.toString());
	}

    /**
     * 更新员工移动电话
     * @param userCode    员工工号
     * @param mobilePhone 电话号码
     * @return
     */
    public boolean updateMobilePhone(int userCode, String mobilePhone) {
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        param.put("mobilePhone", mobilePhone);
        return update(sqlId("updateMobilePhone"), param);
    }
    
    /**
      * @since: 2.7.1
      * @return
      * <p>
      *  查询农历过生日的人，转换成公历
      * </p>   
     */
    public List<EmployeeDetails> findNongliBirthEmployee(){
    	return queryForListBySqlId(sqlId("findNongliBirthEmployee"));
    }
    /**
      * @since: 2.7.1 
      * @param userCode
      * @param birthday
      * @return
      * <p>
      *  农历生日更新成公历生日。
      * </p>   
     */
    public boolean updateNongliBirthdayToGongli(int userCode,Date birthday){
    	Map<String,Object> params=new HashMap<>();
    	params.put("userCode", userCode);
    	params.put("birthday", birthday);
    	return update(sqlId("updateNongliBirthdayToGongli"), params);
    }

    /**
     * 根据网站发送标识查询员工信息
     * @param flag
     * @return
     */
    public List<EmployeeDetails> queryByWebFlag(int flag) {
        return this.queryForList(sqlId("queryByWebFlag"), flag);
    }

    /**
     * 查询需要发送网站消息的员工
     */
    public static final int FLAG_TO_SEND = 1;
    public List<EmployeeDetails> queryByWebFlag() {
        return this.queryByWebFlag(FLAG_TO_SEND);
    }

    /**
     * 查询档案没有确认的数量
     * @since: 2014-05-16 10:25:40
     * @param company
     * @return
     */
    public int countUnCheckNum(String company) {
        return this.count(sqlId("countUnCheckNum"), company);
    }

}
