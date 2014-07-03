package com.gusi.boms.service;

import com.gusi.boms.model.TitleSerial;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-15 17:32)
 * Description:职系的业务处理
 */
@Service
public class TitleSerialService extends BaseService<TitleSerial> {
    public static List<TitleSerial>  titleSerials = null;

    public List<TitleSerial> queryTitleSerials(){
        if(titleSerials == null ){
            titleSerials = this.queryForList();
        }
        return titleSerials;
    }

}
