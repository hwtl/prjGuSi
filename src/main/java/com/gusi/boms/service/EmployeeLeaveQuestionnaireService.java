package com.gusi.boms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.EmployeeLeaveQuestionnaire;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;

@Service
public class EmployeeLeaveQuestionnaireService extends BaseService<EmployeeLeaveQuestionnaire> {

	/**
	 * @param interviewQuestionaires
	 * @param interviewId
	 * @return 保存问卷记录
	 */
	@Transactional
	public boolean saveBatch(final List<EmployeeLeaveQuestionnaire> questionaires,final int interviewId) {
		if (questionaires==null ||questionaires.isEmpty()) {
			return false;			
		}
		return getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
			@Override
			public Boolean doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (EmployeeLeaveQuestionnaire r : questionaires) {
					r.setLeaveRecordId(interviewId);
					executor.insert(sqlId("insert"),r);
				}
				return executor.executeBatch()==questionaires.size();
			}
		});
	}
}
