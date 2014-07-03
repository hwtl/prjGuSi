package com.gusi.boms.service;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.helper.EmployeeHelper;
import com.gusi.boms.model.EmployeeBaseInfor;
import com.gusi.boms.model.EmployeeBlackReasons;
import com.gusi.boms.model.EmployeeBlackRecords;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.service.BaseService;

/**
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:25:44 )
 */
@Service
public class EmployeeBlackRecordsService extends BaseService<EmployeeBlackRecords> {
	@Autowired
	EmployeeBlackReasonsService employeeBlackReasonsService;
	@Autowired
	EmployeeBaseInforService employeeBaseInforService;
	@Autowired
	AttachmentService attachmentService;

    /**
     * @param records
     * @return 保存离职记录
     */
	@Transactional
    public boolean saveRecords(EmployeeBlackRecords records,
    		List<EmployeeBlackReasons> blackReasons)
    {
    	if (records== null) {
			return false;
		}
    	//将已有正常的记录全部标记为历史
    	 update(sqlId("updateNormalToHistory"), records);
    	//将未生效的记录作废
    	 update(sqlId("updateDeactiveToInvalid"),records);
    	Integer blackId=insertAndReturnId(records);
    	if (blackId==null) {
			throw new IllegalArgumentException("黑名单记录失败");
		}
    	if (!employeeBlackReasonsService.batchInsert(blackReasons, blackId)) {
    		throw new IllegalArgumentException("黑名单原因保存失败");
		}
    	return true;
    }


	/**
	 * @param blackRecords
	 * @param curEmp
	 * @return 增加黑名单记录
	 */
	@Transactional
	public boolean addBlackRecords(EmployeeBlackRecords blackRecords,
			Employee curEmp) {
		//判断状态
		EmployeeBaseInfor eb=employeeBaseInforService.findEmpForAddBlack(blackRecords.getUserCode(), curEmp.getCompany());
		if (eb==null) {
			throw new IllegalArgumentException("员工不存在，只有离职员工才可以加入黑名单。");
		}
		blackRecords.setCreator(curEmp.getUserCode());
		blackRecords.setSerialNo(EmployeeHelper.createBlackSerialNo(blackRecords.getUserCode()));
		blackRecords.setStatus(1);
		//保存黑名单记录
		if (saveRecords(blackRecords, findBlackReasons(blackRecords))) {
			//更新黑名单员工状态
			EmployeeBaseInfor ebs=new EmployeeBaseInfor();
			ebs.setUserCode(blackRecords.getUserCode());
			ebs.setIsBlack(1);
			if (!employeeBaseInforService.update(ebs)) {
				throw new IllegalArgumentException("员工无法加入黑名单。");
			}
			Integer attachId=blackRecords.getAttachId();
			if (attachId !=null) {
				attachmentService.updateValidById(attachId);
			}
		}
		return true;
	}
	/**
	 * @param blackRecords
	 * @param curEmp
	 * @return 撤销黑名单
	 */
	public boolean revocationBlackRecords(EmployeeBlackRecords blackRecords,
			Employee curEmp) {
		//判断状态
		EmployeeBaseInfor eb=employeeBaseInforService.findEmpForRemoveBlack(blackRecords.getId(), curEmp.getCompany());
		if (eb==null) {
			throw new IllegalArgumentException("员工不存在，只有离职且已是黑名单的员工才可以撤销黑名单。");
		}
		blackRecords.setUpdator(curEmp.getUserCode());
		blackRecords.setStatus(3);
		blackRecords.setOperator(curEmp.getUserCode());
		//保存黑名单记录
		if (update(sqlId("revocationBlackRecords"), blackRecords)) {
			//更新黑名单员工状态
			EmployeeBaseInfor ebs=new EmployeeBaseInfor();
			ebs.setUserCode(blackRecords.getUserCode());
			ebs.setIsBlack(0);
			if (!employeeBaseInforService.update(ebs)) {
				throw new IllegalArgumentException("员工无法撤销黑名单。");
			}
			Integer attachId=blackRecords.getGuaranteeAttachId();
			if (attachId !=null) {
				attachmentService.updateValidById(attachId);
			}
		}
		return true;
	}
	/**
	 * @param reasons
	 * @return 解析黑名单原因
	 */
	private List<EmployeeBlackReasons> findBlackReasons(EmployeeBlackRecords records)
	{
		String reasons=records.getReasons();
		if (reasons==null || reasons.isEmpty()) {
			return null ;
		}
		// 原因。
		JSONArray jsonReasons = JSONArray.fromObject(reasons);
		int size=jsonReasons.size();
		if (size==0) {
			throw new IllegalArgumentException("黑名单原因不能为空");
		}
		//离职原因
		List<EmployeeBlackReasons> bss=new ArrayList<EmployeeBlackReasons>();
		JSONObject js_reason=null;
		for (int i = 0; i < size; i++) {
			js_reason=jsonReasons.getJSONObject(i);
			bss.add(new EmployeeBlackReasons(js_reason.getInt("reasonId"),js_reason.getString("reason"),js_reason.getString("description")));
		}
	   return bss;
	}

	
}
