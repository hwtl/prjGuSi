package com.gusi.activemq.helper;

import com.gusi.activemq.sender.OrganizationBianJi;
import com.gusi.activemq.sender.OrganizationCloseSender;
import com.gusi.activemq.sender.OrganizationPauseSender;
import com.gusi.activemq.sender.OrganizationXinZeng;
import com.gusi.boms.helper.MailHelper;
import com.dooioo.dymq.model.OrganizationMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: fdj
 * @Create: 13-12-19 下午2:11
 * @Description: 组织消息发送辅助类
 * 涉及到组织发送mq消息的地方目前为：
 * 1、页面新增组织，组织的service新增方法里面
 * 2、页面编辑组织，组织的service编辑方法里面
 * 3、页面整组转调，转调的service转调方法里面，底层会调用组织service的编辑方法，所以不需要添加。
 */
@Component
public class OrganizationSenderHelper {

    private static final Log LOG = LogFactory.getLog(OrganizationSenderHelper.class);

    @Autowired
    private OrganizationXinZeng organizationXinZeng;
    @Autowired
    private OrganizationBianJi organizationBianJi;
    @Autowired
    private OrganizationPauseSender organizationPauseSender;
    @Autowired
    private OrganizationCloseSender organizationCloseSender;
    @Autowired
    private MailHelper mailHelper;

    public void sendOrgXinZeng(int id) {
        this.sendOrgXinZeng(id, null, null);
    }
    /**
      *	<p>
      * 发送组织暂停消息
      * </p>
      * @since: 2.0.0
      * @param orgId
      * @param orgName
      * @param parentId    
     */
    public void sendOrgPause(int orgId,String orgName,Integer parentId){
    	organizationPauseSender.sendMessage(orgId, orgName, parentId);
    }
    /**
      *	<p>
      * 发送组织关闭消息
      * </p>
      * @since: 2.0.0
      * @param orgId
      * @param orgName
      * @param parentId    
     */
    public void sendOrgClose(int orgId,String orgName,Integer parentId){
    	organizationCloseSender.sendMessage(orgId, orgName, parentId);
    }
    /**
     * 发送组织新增mq消息
     * @param id
     * @param orgName
     * @param parentId
     */
    public void sendOrgXinZeng(int id, String orgName, Integer parentId) {
        OrganizationMessage organizationMessage = new OrganizationMessage();
        organizationMessage.setOrgId(id);
        organizationMessage.setOrgName(orgName);
        organizationMessage.setParentId(parentId);
        try {
            organizationXinZeng.sendMessage(organizationMessage);
        } catch (Exception e) {
            LOG.error("发送组织新增mq消息失败!"+organizationMessage, e);
            mailHelper.sendMQErrorMessage(organizationMessage.toString());
        }
    }

    /**
     * 发送组织编辑Mq消息
     * @param id
     * @param orgName
     * @param parentId
     */
    public void sendOrgBianJi(int id, String orgName, Integer parentId) {
        OrganizationMessage organizationMessage = new OrganizationMessage();
        organizationMessage.setOrgId(id);
        organizationMessage.setOrgName(orgName);
        organizationMessage.setParentId(parentId);
        try {
            organizationBianJi.sendMessage(organizationMessage);
        } catch (Exception e) {
            LOG.error("发送组织编辑Mq消息失败!"+organizationMessage, e);
            mailHelper.sendMQErrorMessage(organizationMessage.toString());
        }
    }

}
