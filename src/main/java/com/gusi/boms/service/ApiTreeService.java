package com.gusi.boms.service;

import com.gusi.boms.model.ApiTree;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-9-26 上午11:38
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class ApiTreeService extends BaseService<ApiTree> {

    public List<ApiTree> search(String sql) {
        return queryForList(sqlId("search"), sql);
    }

}
