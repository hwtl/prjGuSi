package com.gusi.boms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.EmployeeBlackReasons;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:25:44 ) 
 *  黑名单原因表
 */
@Service
public class EmployeeBlackReasonsService extends BaseService<EmployeeBlackReasons> {
	
	/**
	 * @param reasons
	 * @param blackId
	 * @return 批量保存黑名单原因
	 */
	@Transactional
	public boolean batchInsert(final List<EmployeeBlackReasons> reasons,
			final int blackId){
		if (reasons==null || reasons.isEmpty()) {
			return false;			
		}
		return getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
			@Override
			public Boolean doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				for (EmployeeBlackReasons r : reasons) {
					r.setBlackId(blackId);
					executor.insert(sqlId("insert"),r);
				}
				return executor.executeBatch()==reasons.size();
			}
		});
	}

	/**
	 * @param blackId
	 * @return 根据黑名单ID查找黑名单原因
	 */
	public List<EmployeeBlackReasons> findByBlackId(Integer blackId)
	{
		return queryForList(sqlId("findByBlackId"), blackId);
	}
}
