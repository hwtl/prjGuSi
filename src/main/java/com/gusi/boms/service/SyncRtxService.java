package com.gusi.boms.service;

import com.gusi.boms.model.EmployeeBaseInfor;
import com.dooioo.web.dao.QueryDao;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-06-27 14:56)
 * Description: To change this template use File | Settings | File Templates.
 */
@Service
public class SyncRtxService extends BaseService<EmployeeBaseInfor> {
    //192.168.0.104 同步 dooiooerp
    @Autowired
    private QueryDao dooiooerpDao;
    /**
     * @param newPassword 新密码
     * @param userCode 工号
     * 同步邮箱 同步RTX和邮箱（Exchange） 东风提供
     */
    public void synchDooiooErp(String newPassword,Integer userCode)
    {
        Map<String,Object> params=new HashMap<>();
        params.put("newPassword", newPassword);
        params.put("userCode", userCode);
        dooiooerpDao.update(sqlId("synUpdateErp"),params );
    }
    /**
     * @param password
     * @param userCode
     * 更改192.168.0.104上的dooerp的密码
     */
    public void updateDooerpPassword(String password,Integer userCode)
    {
        Map<String,Object> params=new HashMap<>();
        params.put("password", password);
        params.put("userCode", userCode);
        dooiooerpDao.update(sqlId("updateErpPassword"),params);
    }
}
