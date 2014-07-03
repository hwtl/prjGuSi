package com.gusi.boms.helper;


import com.gusi.boms.dto.LevelTree;
import com.gusi.boms.dto.LevelTreeCache;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-11-22 上午9:42
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class CacheHelper {

    /**
     * 时间
     */
    public static final int LEVELTREE_CACHE_TIME = 3;

    /**
     * 存放临时数据
     */
    public static Map<String, LevelTreeCache> LEVELTREE_CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 获取缓存的值
     * @param key
     * @return
     */
    public static List<LevelTree> getLevelTree(String key) {
        if(CacheHelper.LEVELTREE_CACHE_MAP.containsKey(key)) {
            LevelTreeCache cache = CacheHelper.LEVELTREE_CACHE_MAP.get(key);
            Date createTime = cache.getCreateTime();
            if(CacheHelper.compareWithNow(createTime, LEVELTREE_CACHE_TIME)) {
                return cache.getLevelTreeList();
            }
        }
        return null;
    }

    /**
     * 存入职等职级树
     * @param key
     * @param list
     */
    public static void putLevelTree(String key, List<LevelTree> list) {
        LevelTreeCache cache = new LevelTreeCache();
        cache.setLevelTreeList(list);
        cache.setCreateTime(new Date());
        LEVELTREE_CACHE_MAP.put(key, cache);
    }

    /**
     * 清除存放排行榜的数据Map
     */
    public static void clearLevelTreeCache() {
        LEVELTREE_CACHE_MAP.clear();
    }

    /**
     * 与当前时间进行比较
     * @param date
     * @param hour 更新时间
     * @return
     */
    public static boolean compareWithNow(Date date, int hour) {
        long diff = new Date().getTime() - date.getTime();
        long hours = diff / (1000 * 60 * 60);
        if(hours < hour) {
            return true;
        }
        return false;
    }

}
