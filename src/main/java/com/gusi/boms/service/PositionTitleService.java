package com.gusi.boms.service;

import com.gusi.boms.helper.PositionHelper;
import com.gusi.boms.model.PositionTitle;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-06-08 15:21)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PositionTitleService extends BaseService<PositionTitle> {

    /**
     * 插入岗位职等表
     * @param titleIds
     * @param positionId
     */
    public void insertList(String titleIds, int positionId){
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("titleIds", PositionHelper.getStrArray(titleIds));
        paramMap.put("positionId", positionId);
        insert(sqlId("insertList"),paramMap);
    }

    /**
     * 根据岗位id查找
     * @param positionId
     * @return
     */
    public List<PositionTitle> queryForListByPositionId(int positionId) {
        return this.queryForList(sqlId("queryForListByPositionId"), positionId);
    }

    /**
     * 根据职等id删除
     * @param positionId
     */
    public void deleteByPositionId(int positionId) {
        delete(sqlId("deleteByPositionId"), positionId);
    }

}
