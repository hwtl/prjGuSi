package com.gusi.boms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.EmployeeFamily;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:26:42 ) 
 */
@Service
public class EmployeeFamilyService extends BaseService<EmployeeFamily>{
	/**
	 * @param families
	 * @param userCode
	 * @return 保存家庭成员
	 * 如果families为empty,则返回true;
	 * 否则先删除原有记录，再insert
	 */
	@Transactional
	public boolean batchInsert(final EmployeeFamily[] families,final int userCode,
			final int creator)
	{
		//可能没有记录
		deleteByUserCode(userCode);
		if (families==null || families.length==0) {
			return true;			
		}
		boolean flag=getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
			@Override
			public Boolean doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int count=families.length;
				for (EmployeeFamily family : families) {
					if (family.getAlias()==null || family.getName()==null || family.getBornDate()==null) {
						count--;
						continue;
					}
					family.setUserCode(userCode);
					family.setCreator(creator);
					executor.insert(sqlId("insert"),family);
				}
				return executor.executeBatch()==count;
			}
		});
		if (!flag) {
			throw new IllegalStateException("批量新增家庭成员失败。");
		}
		return true;
	}
	    /**
	     * @param userCode
	     * @return 根据员工工号删除家庭记录
	     */
	    private void deleteByUserCode(int userCode)
	    {
	       delete(sqlId("deleteFamilyByUserCode"),userCode);
	    }
	    /**
	     * @param userCode
	     * @return 根据工号查询家庭
	     */
	    public List<EmployeeFamily> findFamiliesByUserCode(int userCode)
	    {
	        return queryForList(sqlId("findFamiliesByUserCode"),userCode);
	    }
}
