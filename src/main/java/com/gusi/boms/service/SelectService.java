package com.gusi.boms.service;

import com.gusi.boms.model.Select;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-28 19:41)
 * Description: To change this template use File | Settings | File Templates.
 */
@Service
public class SelectService extends BaseService<Select> {

    public List<Select> queryTitlesBySerialId(int serialId){
        return this.queryForList(sqlId("queryTitlesBySerialId"),serialId);
    }

    public List<Select> queryTitleLevelByTitleId(int titleId) {
        return this.queryForList(sqlId("queryTitleLevelByTitleId"),titleId);
    }


    public List<Select> queryPositionByTitleId(int titleId) {
        return this.queryForList(sqlId("queryPositionByTitleId"),titleId);
    }

}
