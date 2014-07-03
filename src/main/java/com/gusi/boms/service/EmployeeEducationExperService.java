package com.gusi.boms.service;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.model.EmployeeEducationExper;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/** 
 *	author:liuhui
 *	createTime: liuhui (2013-4-9 上午11:23:33 ) 
 */
@Service
public class EmployeeEducationExperService extends BaseService<EmployeeEducationExper>{
	    
	    /**
	     * @param userCode
	     * @return 根据工号删除
	     */
		private void deleteByUserCode(int userCode)
	    {
	        delete(sqlId("deleteByUserCode"), userCode);
	    }
	    
	    /**
		 * @param trains
		 * @param userCode
		 * @return
		 *  批量insert培训经历
		 *  如果培训经历为空则直接返回true
		 *  否则 先删除已有的记录，批量新增教育记录
		 */
		@Transactional
		public boolean batchInsert(final EmployeeEducationExper[] educations,final int userCode,
				final int creator)
		{
			deleteByUserCode(userCode);
			if (educations==null || educations.length==0) {
				return true;			
			}
			boolean flag=getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
				@Override
				public Boolean doInSqlMapClient(SqlMapExecutor executor)
						throws SQLException {
					executor.startBatch();
					int count=educations.length;
					for (EmployeeEducationExper exper : educations) {
						if (exper.getStartDate()==null || exper.getEndDate()==null || exper.getSchoolName()==null ) {
							count--;
							continue;
						}
						exper.setUserCode(userCode);
						exper.setCreator(creator);
						executor.insert(sqlId("insert"),exper);
					}
					return executor.executeBatch()==count;
				}
			});
			if (!flag) {
				throw new IllegalArgumentException("批量新增教育记录出错");
			}
			return true;
		}

    /**
     * 根据工号查询教育经验
     * @update: 2014-05-20 13:30:39
     * @param userCode
     * @return
     */
    public List<EmployeeEducationExper> findByUserCode(int userCode) {
        return queryForList(sqlId("findByUserCode"), userCode);
    }

}
