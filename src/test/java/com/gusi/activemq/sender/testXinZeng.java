package com.gusi.activemq.sender;

import com.gusi.BaseTest;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-10-22 上午10:27
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class testXinZeng extends BaseTest {

    @Autowired
    private EmployeeXinZeng employeeXinZeng;

    @Test
    public void testSender() {
    	JSONObject json=new JSONObject();
    	json.put("102603", String.valueOf(1/9.0));
    	json.put("102604", String.valueOf(1/3.0));
    	json.put("102605", String.valueOf(1));
    	json.put("102606", String.valueOf(1));
    	json.put("102607", String.valueOf(1));
    	json.put("102610", String.valueOf(1/9.0));
    	json.put("102611", String.valueOf(1/3.0));
    	json.put("102612", String.valueOf(1));
    	System.out.println(json.toString());
//        try {
//            employeeXinZeng.sendMessage("测试员工新增消息");
//        } catch (Exception e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }
//        System.out.println(employeeXinZeng.getConnectionFactory());
//        System.out.println(employeeXinZeng.getDestinationName());
//        System.out.println("ok");
    }

}
