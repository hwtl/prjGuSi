package com.gusi.boms.service;

import java.sql.SQLException;
import java.util.List;

import com.gusi.boms.model.EmployeeTrain;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:25:44 ) 
 */
@Service
public class EmployeeTrainService extends BaseService<EmployeeTrain> {
	
	/**
	 * @param trains
	 * @param userCode
	 * @return
	 *  批量insert培训经历
	 *  如果培训经历为空则直接返回true
	 *  否则 先删除已有的记录，再insert新的培训记录
	 */
	@Transactional
	public boolean batchInsert(final EmployeeTrain[] trains,final int userCode,
			final int creator)
	{
		deleteByUserCode(userCode);
		if (trains==null || trains.length==0) {
			return true;			
		}
		boolean flag=getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
			@Override
			public Boolean doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int count=trains.length;
				for (EmployeeTrain train : trains) {
					if (train.getStartDate() == null || train.getEndDate()==null || train.getTrainName() ==null) {
						count--;
						continue;
					}
					train.setUserCode(userCode);
					train.setCreator(creator);
					executor.insert(sqlId("insert"),train);
				}
				return executor.executeBatch()==count;
			}
		});
		if (!flag) {
			throw new IllegalStateException("批量新增培训记录失败。");
		}
		return true;
	}
	/**
	 * @param userCode
	 * @return 根据工号删除培训记录
	 */
	private boolean deleteByUserCode(int userCode) {
		return delete(sqlId("deleteByUserCode"),userCode);
	}

	/**
	 * @param userCode
	 * @return 根据工号查询培训记录
	 */
	public List<EmployeeTrain> findTrainsByUserCode(int userCode) {
		return queryForList(sqlId("findByUserCode"), userCode);
	}

}
