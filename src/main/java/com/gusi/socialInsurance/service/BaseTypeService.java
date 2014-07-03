package com.gusi.socialInsurance.service;

import com.gusi.socialInsurance.model.SIBaseType;
import com.dooioo.web.service.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: fdj
 * @Since: 2014-06-11 18:09
 * @Description: BaseTypeService
 */
@Service
public class BaseTypeService extends BaseService<SIBaseType> {

    /**
     * 查询缴纳地址
     * @since: 2014-06-11 18:14:24
     * @return
     */
    public List<SIBaseType> queryPaymentLocationList() {
        return queryForList(sqlId("queryPaymentLocationList"),0);
    }

    /**
     * 查询户籍性质
     * @since: 2014-06-12 09:47:49
     * @return
     */
    public List<SIBaseType> queryCensusList() {
        return queryForList(sqlId("queryCensusList"),0);
    }

    /**
     * 查询缴费基数
     * @since: 2014-06-12 09:48:03
     * @return
     */
    public List<SIBaseType> queryPaymentBaseList() {
        return queryForList(sqlId("queryPaymentBaseList"),0);
    }

}
