package com.gusi.empTransfer.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.gusi.boms.bomsEnum.TransferWilling;
import com.gusi.boms.common.Constants;

/**
 * @Author: fdj
 * @Since: 2014-03-04 09:24
 * @Description: 审批参数类
 */
public class ApprovalParameter implements Serializable {
    private static final long serialVersionUID = -704828251608207940L;

    private int id;                 //转调记录id
    private int creator;            //申请人
    private boolean HtStatus;       //是否转调到后台 true：是
    private boolean HtOrDlxmStatus; //是否转代理项目或者转后台 true：是
    private int transferState;      //转调情况      0 ：无，   1：主动        2：被动
    private int fangyuanStatus;     //房源交接     0 不交接 1，交接
    private int keyuanStatus;       //客源状态     0 不交接 1，交接

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public boolean isHtStatus() {
        return HtStatus;
    }

    public void setHtStatus(boolean htStatus) {
        HtStatus = htStatus;
    }

    public boolean isHtOrDlxmStatus() {
        return HtOrDlxmStatus;
    }

    public void setHtOrDlxmStatus(boolean htOrDlxmStatus) {
        HtOrDlxmStatus = htOrDlxmStatus;
    }

    public int getTransferState() {
        return transferState;
    }

    public void setTransferState(int transferState) {
        this.transferState = transferState;
    }

    public int getFangyuanStatus() {
        return fangyuanStatus;
    }

    public void setFangyuanStatus(int fangyuanStatus) {
        this.fangyuanStatus = fangyuanStatus;
    }

    public int getKeyuanStatus() {
        return keyuanStatus;
    }

    public void setKeyuanStatus(int keyuanStatus) {
        this.keyuanStatus = keyuanStatus;
    }

    /**
     * 条件用Map形式输出
     * @return
     */
    public Map<String, Object> getParameterMap() {
    	Map<String, Object> parameterMap = new HashMap<String, Object>();
    	// 是否转后台
    	parameterMap.put(Constants.CONDITION_KEY_IS_TO_BACKSTAGE, HtStatus);
    	// 是否转代理项目/后台
    	parameterMap.put(Constants.CONDITION_KEY_IS_TO_DELEGATE_OR_BACKSTAGE, HtOrDlxmStatus);
    	// 是否被动转调
    	parameterMap.put(Constants.CONDITION_KEY_IS_PASSIVE_TRANSFER, TransferWilling.isPassiveTransfer(transferState));
    	// 是否需要房源交接
    	parameterMap.put(Constants.CONDITION_KEY_NEED_HOUSE_HANDOVER, fangyuanStatus == 1 ? true : false);
    	// 是否需要客源交接
    	parameterMap.put(Constants.CONDITION_KEY_NEED_CUSTOMER_HANDOVER, keyuanStatus == 1 ? true : false);
    	return parameterMap;
    }

    @Override
    public String toString() {
        return "ApprovalParameter{" +
                "id=" + id +
                ", creator=" + creator +
                ", HtStatus=" + HtStatus +
                ", HtOrDlxmStatus=" + HtOrDlxmStatus +
                ", transferState=" + transferState +
                ", fangyuanStatus=" + fangyuanStatus +
                ", keyuanStatus=" + keyuanStatus +
                '}';
    }
}
