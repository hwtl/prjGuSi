package com.gusi.boms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.EmployeeWorkExper;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:22:37 ) 
 */
@Service
public class EmployeeWorkExperService  extends BaseService<EmployeeWorkExper>{
    /**
     * @param userCode
     * @return 根据工号查找工作经验
     */
    public List<EmployeeWorkExper> findByUserCode(int userCode)
    {
        return queryForList(sqlId("findByUserCode"),userCode);
    }
    
    /**
     * @param userCode
     * @return 根据工号删除工作经验
     */
    private void deleteByUserCode(int userCode)
    {
      delete(sqlId("deleteByUserCode"), userCode);
    }
    /**
	 * @param trains
	 * @param userCode
	 * @return
	 *  批量insert工作经历
	 *  如果工作经历为空则直接返回true
	 *  否则 先删除已有的记录，再insert新的工作记录
	 */
	@Transactional
	public boolean batchInsert(final EmployeeWorkExper[] works,final int userCode,
			final int creator)
	{
		deleteByUserCode(userCode);
		if (works==null || works.length==0) {
			return true;			
		}
		boolean flag=getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
			@Override
			public Boolean doInSqlMapClient(SqlMapExecutor executor)
					throws SQLException {
				executor.startBatch();
				int count=works.length;
				for (EmployeeWorkExper workExper : works) {
					if (workExper.getEntryDate()==null || workExper.getDepartureDate()== null || workExper.getCompanyName()==null) {
						count--;
						continue;
					}
					workExper.setUserCode(userCode);
					workExper.setCreator(creator);
					executor.insert(sqlId("insert"),workExper);
				}
				return executor.executeBatch()==count;
			}
		});
		if (!flag) {
			throw new IllegalStateException("批量新增工作记录失败。");
		}
		return true;
	}
}
