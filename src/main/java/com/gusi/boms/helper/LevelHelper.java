package com.gusi.boms.helper;


import com.gusi.boms.dto.LevelInfo;
import com.gusi.boms.dto.LevelTree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-12-11 下午4:38
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class LevelHelper {

    public static final String ZHIXI = "x";
    public static final String ZHIDENG = "d";

    /**
     * 生成职等职级树
     * @since: 2014-06-06 14:36:54
     * @param levels 职级集合
     * @param titles 职等集合
     * @param serials 职系集合
     * @return
     */
    public static List<LevelTree> getLevelTree(List<LevelInfo> levels, List<LevelInfo> titles, List<LevelInfo> serials) {
        if(levels == null || levels.size()==0) {
            return null;
        }
        if(titles == null || titles.size()==0) {
            return null;
        }
        if(serials == null || serials.size()==0) {
            return null;
        }

        List<LevelTree> levelTreeList = new LinkedList<LevelTree>();
        for(LevelInfo serial :serials) {
            LevelTree levelTree = new LevelTree();
            levelTree.setId(ZHIXI + serial.getSerialId());
            levelTree.setText(serial.getSerialName());
            levelTree.setChildren(new ArrayList<LevelTree>());
            levelTreeList.add(levelTree);
        }

        for(LevelInfo title :titles) {
            for(int i=0;  i<levelTreeList.size(); i++) {
                if(levelTreeList.get(i).getId().equals(ZHIXI + title.getSerialId())) {
                    LevelTree levelTree = new LevelTree();
                    levelTree.setId(ZHIDENG + title.getTitleId());
                    levelTree.setText(title.getTitleDegree() + " " + title.getTitleName());
                    levelTree.setChildren(new ArrayList<LevelTree>());
                    levelTreeList.get(i).getChildren().add(levelTree);
                }
            }
        }

        for(LevelInfo level : levels) {
            for(int i=0;  i<levelTreeList.size(); i++) {
                for(int j=0;  j<levelTreeList.get(i).getChildren().size(); j++) {
                    if(levelTreeList.get(i).getChildren().get(j).getId().equals(ZHIDENG + level.getTitleId())) {
                        LevelTree levelTree = new LevelTree();
                        levelTree.setId(""+level.getLevelId());
                        levelTree.setText(level.getTitleDegree() + "-" + level.getLevelDegree() + " " + level.getLevelName());
                        levelTreeList.get(i).getChildren().get(j).getChildren().add(levelTree);
                    }
                }
            }
        }

        return levelTreeList;
    }

}
