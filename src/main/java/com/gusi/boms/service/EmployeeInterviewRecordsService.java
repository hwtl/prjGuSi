package com.gusi.boms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.boms.model.EmployeeInterviewRecords;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.EmployeeLeaveQuestionnaire;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.service.BaseService;

/**
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:25:44 )
 */
@Service
public class EmployeeInterviewRecordsService extends BaseService<EmployeeInterviewRecords> {
   @Autowired
   EmployeeLeaveQuestionnaireService employeeLeaveQuestionnaireService;
	/**
	 * @param id
	 * @return 根据id查找离职记录
	 */
	public EmployeeInterviewRecords findForInterview(Integer id,String company) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("id", id);
		params.put("company", company);
		return findByBean(sqlId("findForInterview"), params);
	}

	/**
	 * @param sesionUser
	 * @param records
	 * @return 离职面谈
	 */
	@Transactional
	public boolean interviewerHandle(Employee sesionUser,
			EmployeeInterviewRecords records) {
		if (records==null || sesionUser==null) {
			return false;
		}
		records.setInterviewer(sesionUser.getUserCode());
		//解析并保存面谈报告。
		if (!employeeLeaveQuestionnaireService.saveBatch(getInterviewQuestionaires(records.getReasons()),records.getId())){
			throw new IllegalArgumentException("离职报告保存失败，请及时反馈");
		}
		if (!updateForInterview(records)) {
			throw new IllegalArgumentException("离职面谈失败，请及时反馈");
		}		
		return true;
	}
	private boolean updateForInterview(EmployeeInterviewRecords records)
	{
		return update(sqlId("interviewHandle"),records);
	}
	/**
	 * @param reasons
	 * @return 获取问卷记录
	 */
	private List<EmployeeLeaveQuestionnaire> getInterviewQuestionaires(String reasons)
	{
		if (reasons==null || reasons.isEmpty()) {
			return null ;
		}
		// 原因。
		JSONArray jsonReasons = JSONArray.fromObject(reasons);
		int size=jsonReasons.size();
		if (size==0) {
			throw new IllegalArgumentException("离职报告原因至少有一项");
		}
		//离职原因
		EmployeeLeaveQuestionnaire lr=null;
		List<EmployeeLeaveQuestionnaire> leaveReasons=new ArrayList<EmployeeLeaveQuestionnaire>();
		int id;
		JSONObject js_reason=null;
		for (int i = 0; i < size; i++) {
			js_reason=jsonReasons.getJSONObject(i);
			id=js_reason.getInt("id");
			//如果是主动离职
			//子原因
			JSONArray subs=JSONArray.fromObject(js_reason.get("subs"));
			if (subs.size()<1) {
				continue;
			}
			for (int j = 0,sl=subs.size(); j < sl; j++) {
				lr=new EmployeeLeaveQuestionnaire();
				//设置字段
				lr.setQuestionId(id);
				String rz=subs.getJSONObject(j).getString("text");
				lr.setAnswerId(subs.getJSONObject(j).getInt("id"));
				lr.setAnswerValue(rz);
				String desc=subs.getJSONObject(j).getString("description");
				lr.setDescription(desc);
				leaveReasons.add(lr);
		}
	 }
	return leaveReasons;
 }
}
