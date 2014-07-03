package com.gusi.boms.service;

import com.gusi.boms.model.TitleLevel;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-22 16:00)
 * Description: 职等的业务逻辑处理
 */
@Service
public class TitleLevelService extends BaseService<TitleLevel> {
    public static List<TitleLevel> titleLevelList = null;

    public List<TitleLevel> queryTitles(){
        if(titleLevelList == null ){
            titleLevelList = this.queryForList();
        }
        return titleLevelList;
    }

   
}
