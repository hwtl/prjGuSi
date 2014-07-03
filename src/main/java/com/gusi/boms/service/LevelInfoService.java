package com.gusi.boms.service;

import com.gusi.boms.dto.LevelInfo;
import com.gusi.boms.dto.LevelTree;
import com.gusi.boms.helper.CacheHelper;
import com.gusi.boms.helper.LevelHelper;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-11 下午4:33
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
@Service
public class LevelInfoService extends BaseService<LevelInfo> {

    /**
     * 获取职级
     * @since: 2014-06-06 14:36:03
     * @return
     */
    public List<LevelInfo> queryLevelList() {
        return queryForList(sqlId("queryLevelList"), 0);
    }

    /**
     * 获取职等
     * @since: 2014-06-06 14:36:03
     * @return
     */
    public List<LevelInfo> queryTitleList() {
        return queryForList(sqlId("queryTitleList"), 0);
    }

    /**
     * 获取职系
     * @since: 2014-06-06 14:36:03
     * @return
     */
    public List<LevelInfo> querySerialList() {
        return queryForList(sqlId("querySerialList"), 0);
    }

    /**
     * 获取职系职等职级树
     * @since: 2014-06-06 14:36:03
     * @return
     */
    private List<LevelTree> generateTree() {
        return LevelHelper.getLevelTree(this.queryLevelList(), this.queryTitleList(), this.querySerialList());
    }

    /**
     * 获取职等职级树
     * @since: 2014-06-12 09:35:04
     * @return
     */
    public List<LevelTree> getTree() {
        List<LevelTree> levelList = CacheHelper.getLevelTree("levelList");
        if(levelList == null) {
            levelList = this.generateTree();
            CacheHelper.putLevelTree("levelList", levelList);
        }
        return levelList;
    }
}
