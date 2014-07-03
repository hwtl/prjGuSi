package com.gusi.boms.service;

import com.gusi.boms.model.School;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-05-19 16:01
 * @Description: SchoolService
 */
@Service
public class SchoolService extends BaseService<School> {

    /**
     * 查询学校
     * @since: 2014-05-22 14:20:10
     * @param keyword
     * @param countryId
     * @return
     */
    public List<School> queryByName(String keyword, String countryId) {
        Map<String,Object> param =  new HashMap<>();
        param.put("keyword", keyword);
        param.put("countryId", countryId);
        return queryForList(sqlId("queryByName"), param);
    }

}
