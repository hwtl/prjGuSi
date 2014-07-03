package com.gusi.app.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.app.model.AppEmployee;
import org.springframework.stereotype.Service;

import com.dooioo.web.service.BaseService;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-10-18 下午5:36
 * @Description: 通讯录员工业务逻辑
 * To change this template use File | Settings | File Templates.
 */
@Service
public class AppEmployeeService extends BaseService<AppEmployee> {

    /**
     * 根据组织id查找该组织树下的所有正式、试用期的员工
     * @param orgId 部门id
     * @return
     */
    public List<AppEmployee> queryForTreeByOrgId(int orgId) {
        return queryForList(sqlId("queryForTreeByOrgId"), orgId);
    }

    /**
      * @since: 3.1.2 
      * @param company
      * @return
      * <p>
      *  根据公司查找在职的员工
      * </p>   
     */
	public List<AppEmployee> findEmployeesByUpdateTime(
			Map<String, Object> variables) {
		return queryForList(sqlId("findEmployeesByUpdateTime"), variables);
	}
    /**
     * @since: 3.1.2 
     * @param company
     * @param userCodes
     * @return
     * <p>
     * 根据公司以及userCodes查询员工信息
     * </p>   
    */
   public List<AppEmployee> findEmployeesByCompany(String company)
   {
   	Map<String,Object> params=new HashMap<>();
   	params.put("company", company);
   	return queryForList(sqlId("findEmployeesWithCompany"), params);
   }
    /**
      * @since: 3.1.2 
      * @param company
      * @param userCodes
      * @return
      * <p>
      * 根据公司以及userCodes查询员工信息
      * </p>   
     */
    public List<AppEmployee> findEmployeesByCompany(String company,String userCodes)
    {
    	Map<String,Object> params=new HashMap<>();
    	params.put("company", company);
    	params.put("userCodes", userCodes);
    	return queryForList(sqlId("findEmployeesWithCompany"), params);
    }
    
}
