package com.gusi.boms.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.boms.model.RewardParameter;
import org.springframework.stereotype.Service;

import com.dooioo.web.service.BaseService;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-23 10:34)
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RewardParameterService extends BaseService<RewardParameter>  {
    /**
     * @param code
     * @return 根据optionCode查找参数
     */
    public RewardParameter findByOptionCode(String optionCode) {
        return findById(sqlId("findByOptionCode"), optionCode);
    }

    /**
     * @param typeKey
     * @return 根据参数typeKey查找
     */
    public List<RewardParameter> findByTypeKey(String typeKey) {
        return queryForList(sqlId("findByTypeKey"), typeKey);
    }
    
    /**
      * @since: 2.7.1
      * @param typeKey
      * @param parentCode
      * @return
      * <p>
      *  根据typeKey和parentCode查找参数
      * </p>   
     */
    public List<RewardParameter> findByTypeKeyAndParentCode(String typeKey,String parentCode){
    	Map<String,Object> params=new HashMap<>();
    	params.put("typeKey", typeKey);
    	params.put("parentCode", parentCode);
    	return queryForList(sqlId("findByTypeKeyAndParentCode"), params);
    }
}
