package com.gusi.boms.service;

import com.gusi.boms.dto.SimpleOrg;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *
* @ClassName: SimpleOrgService
* @Description: SimpleOrgService
* @author fdj
* @date 2013-5-8 上午10:12:43
 */
@Service
public class SimpleOrgService extends BaseService<SimpleOrg> {

    /**
     * 查询组织
     * @since: 2014-05-08 14:42:14
     * @param company
     * @param status
     * @return
     */
    public List<SimpleOrg> queryOrgsApi(Map<String, Object> variables) {
        return this.queryForList(sqlId("queryOrgsApi"), variables);
    }

}