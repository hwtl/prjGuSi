package com.gusi.boms.service;

import com.gusi.boms.common.Constants;
import com.gusi.boms.model.EmployeeTag;
import com.dooioo.web.service.BaseService;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-05-21 12:02
 * @Description: EmployeeTagService
 */
@Service
@Transactional
public class EmployeeTagService extends BaseService<EmployeeTag> {

    /**
     * 添加百强高校标签（如果有就不添加）
     * @since: 2014-05-21 11:29:40
     * @param userCode
     * @param creator
     */
    public void addBqTag(int userCode, int creator) {
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("userCode", userCode);
        params.put("creator", creator);
        insert(sqlId("addBqTag"), params);
    }

    /**
     * 移除百强高校标签（如果有就不添加）
     * @since: 2014-05-21 11:29:40
     * @param userCode
     */
    public void removeBqTag(int userCode) {
        delete(sqlId("removeBqTag"), userCode);
    }

    /**
     * 查询员工标签
     * @since: 2014-05-21 12:11:57
     * @param userCode
     * @return
     */
    public List<EmployeeTag> queryByUserCode(int userCode) {
        return queryForList(sqlId("queryByUserCode"), userCode);
    }

    /**
     * 更新标签
     * 先删除后插入
     * @since: 2014-05-21 15:10:17
     * @update: 2014-05-28 10:41:16 保存操作记录
     * @param empTags
     * @param userCode
     * @param creator
     * @return
     */
    public void update(String[] empTags,int userCode,int creator) {
        //保存被删除的标签的操作记录
        saveBeginOperateHistory(userCode, creator);
        //删除标签
        delete(userCode);
        //插入标签
        batchInsert(empTags, userCode, creator);
        //保存新插入的标签的操作记录
        saveEndOperateHistory(userCode, creator);
    }

    /**
     * 保存删除操作记录
     * @since: 2014-05-28 10:41:09
     * @param userCode
     * @param creator
     */
    public void saveBeginOperateHistory(int userCode,int creator) {
        saveEmployeeOperateHistory(userCode, creator, Constants.PEOPLE_TAG_DEL);
    }

    /**
     * 保存新增操作记录
     * @since: 2014-05-28 10:41:06
     * @param userCode
     * @param creator
     */
    public void saveEndOperateHistory(int userCode,int creator) {
        saveEmployeeOperateHistory(userCode, creator, Constants.PEOPLE_TAG_ADD);
    }

    /**
     * 将现有的员工标签存一份操作记录
     * @since: 2014-05-28 10:41:52
     * @param userCode
     * @param creator
     * @param field
     */
    public void saveEmployeeOperateHistory(int userCode,int creator, String field) {
        Map<String, Object> param = new HashMap<>();
        param.put("userCode", userCode);
        param.put("creator", creator);
        param.put("field", field);
        param.put("title", Constants.PEOPLE_TAG);
        this.insert(sqlId("saveEmployeeOperateHistory"), param);
    }

    /**
     * 批量插入
     * @since: 2014-05-22 14:19:49
     * @param empTags
     * @param userCode
     * @param creator
     * @return
     */
    public boolean batchInsert(final String[] empTags,final int userCode,final int creator) {
        if (empTags==null || empTags.length==0) {
            return true;
        }
        boolean flag=getSqlMapClientTemplate().execute(new SqlMapClientCallback<Boolean>() {
            @Override
            public Boolean doInSqlMapClient(SqlMapExecutor executor)
                    throws SQLException {
                executor.startBatch();
                int count = empTags.length;
                Map<String, Object> param = new HashMap<>();
                param.put("userCode", userCode);
                param.put("creator", creator);
                for (String tagCode : empTags) {
                    param.put("tagCode", tagCode);
                    executor.insert(sqlId("insert"),param);
                }
                return executor.executeBatch()==count;
            }
        });
        if (!flag) {
            throw new IllegalArgumentException("批量添加标签出错");
        }
        return true;
    }

}
