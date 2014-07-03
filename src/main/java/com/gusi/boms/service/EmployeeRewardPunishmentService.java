package com.gusi.boms.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gusi.activemq.sender.EmployeeRewardPunishSender;
import com.gusi.boms.common.Configuration;
import com.gusi.boms.model.EmployeeRewardPunishment;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.dooioo.dymq.model.SalaryMessage;
import com.dooioo.dymq.model.SalaryModifyMessage;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.service.BaseService;
import com.dooioo.webplus.security.DESHelper;
import com.dooioo.webplus.security.HashUtils;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: fdj (2013-07-19 14:55)
 * @Description: 员工奖惩业务逻辑
 * To change this template use File | Settings | File Templates.
 */
@Service
public class EmployeeRewardPunishmentService extends BaseService<EmployeeRewardPunishment> {
    @Autowired
    private EmployeeRewardPunishSender employeeRewardPunishSender;
    @Autowired
    private FreemarkerUtil freemarkerUtil;
    
    private static Logger LOGGER=Logger.getLogger(EmployeeRewardPunishmentService.class);
    
    /**
      * @since: 3.0.4 
      * @param rewardPunishment
      * <p>
      *   发送奖惩MQ消息，没生效的会跳过。
      * </p>   
     */
    @Async
    public void sendSalaryMQMessage(EmployeeRewardPunishment rewardPunishment){ 
    	LOGGER.info("开始发送奖惩MQ消息。");
    	if (rewardPunishment==null) {
    		LOGGER.info("rewardpunishment is null");
			return;
		}
    	if (1!=rewardPunishment.getStatus() || rewardPunishment.getEffectiveTime().after(new Date())) {
			LOGGER.info("RewardPunishment 没有生效，"+rewardPunishment); return ;
		}
    	//加载奖惩结果分类映射
		try {
			String mapping = freemarkerUtil.writeTemplate("rewardpunish/rewardpunishSalaryMapping.ftl");
			if (StringUtils.isEmpty(mapping)) {
				LOGGER.info("找不到奖惩结果映射关系，"+rewardPunishment);
				return;
			}
			JSONObject json=JSONObject.fromObject(mapping);
			String resultKey=rewardPunishment.getResult();
			if (!json.containsKey(resultKey)) {
				LOGGER.info("找不到奖惩结果映射关系，resultOptionCode="+resultKey
						+",mapping:"+mapping+","+rewardPunishment);
				return;
			}
			SalaryMessage message=new SalaryMessage();
			List<SalaryModifyMessage> modifyMessages=new ArrayList<>();
			modifyMessages.add(new SalaryModifyMessage(rewardPunishment.getUserCode(),
					rewardPunishment.getRewardtype(),json.getString(resultKey),rewardPunishment.getEffectiveTime(),
					String.valueOf(rewardPunishment.getId())));
			message.setModifyMessages(modifyMessages);
			
		   //token消息加密
		   message.setToken(DESHelper.encryptStr(String.valueOf(HashUtils.FNVEnhanceHashString(message.messages2Str())),
				   Configuration.getInstance().getBomsDesKey()));
		   //奖惩Id加密
		   message.setRequestId(DESHelper.encryptStr(String.valueOf(rewardPunishment.getId()),
				   Configuration.getInstance().getBomsDesKey()));
		   //时间戳
		   message.setTimestamp(System.currentTimeMillis());
		   employeeRewardPunishSender.sendMessage(message);
		} catch (Exception e) {
			LOGGER.info("加载奖惩结果映射关系ftl出错。",e);
		}
    	
    }
    /**
      * @since: 3.0.4 
      * @param rewardId
      * @return
      * <p>
      *  将未生效的奖惩记录更新为有效。
      * </p>   
     */
    public boolean changeNormal(int rewardId){
    	return update(sqlId("changeNormal"), rewardId);
    }
    /**
      * @since: 3.0.4 
      * @return
      * <p>
      *  奖惩记录生效的记录。
      * </p>   
     */
    public List<EmployeeRewardPunishment> queryForActiveJobs() {
    	return queryForList(sqlId("queryForActiveJobs"),0);
    }
    
    /**
     * 根据工号删除
     * @param id
     * @return
     */
    public boolean deleteById(int id) {
        return update(sqlId("deleteById"), id);
    }

    public List<EmployeeRewardPunishment> queryForListByUserCode(int userCode) {
        return queryForList(sqlId("queryForListByUserCode"), userCode);
    }
    
    public EmployeeRewardPunishment findViewById(Integer id){
    	return findByBean(sqlId("findViewById"), id);
    }

    public EmployeeRewardPunishment findViewForEdit(Integer id){
    	return findByBean(sqlId("findViewForEdit"), id);
    }
    public Paginate queryPaginate(int pageNo, int userCode) {
        EmployeeRewardPunishment param = new EmployeeRewardPunishment();
        param.setPageSize(Configuration.getInstance().getPageSize());
        param.setPageNo(pageNo);
        param.setColumns("*");
        param.setTable("[v2_reward_final]");
        param.setWhere("status in (1,0) and userCode = " + userCode);
        param.setOrderBy("createTime desc");
        return queryForPaginate2(param);
    }

}
