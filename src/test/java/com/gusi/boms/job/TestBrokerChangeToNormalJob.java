package com.gusi.boms.job;

import com.gusi.BaseTest;
import com.gusi.boms.model.EmployeeBaseInfor;
import com.gusi.boms.model.EmployeeChangeRecords;
import com.gusi.boms.service.EmployeeBaseInforService;
import com.gusi.boms.service.EmployeeChangeRecordsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: fdj
 * @Create: 13-11-6 上午10:34
 * @Description: IntelliJ IDEA
 * To change this template use File | Settings | File Templates.
 */
public class TestBrokerChangeToNormalJob extends BaseTest {

    @Autowired
    private EmployeeChangeRecordsService employeeChangeRecordsService;
    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;

    private static final Log log = LogFactory.getLog(TestBrokerChangeToNormalJob.class);

    @Test
    public void testDoJob() {
        log.info("凌晨3点执行员工转正：start");
        List<EmployeeBaseInfor> ebs = employeeBaseInforService.findEmployeeToNormal();
        //生成一条异动记录
        Calendar activeDate = Calendar.getInstance();
        activeDate.set(Calendar.MILLISECOND, 0);
        activeDate.set(Calendar.HOUR_OF_DAY, 0);
        activeDate.set(Calendar.MINUTE, 0);
        activeDate.set(Calendar.SECOND, 0);
        EmployeeChangeRecords ecr = null;
        for (EmployeeBaseInfor eb : ebs) {
            try {
                ecr = new EmployeeChangeRecords();
                ecr.setUserCode(eb.getUserCode());
                ecr.setNewLevelId(eb.getLevelId());
                ecr.setNewPositionId(eb.getPositionId());
                ecr.setNewOrgId(eb.getOrgId());
                ecr.setActiveDate(activeDate.getTime());
                ecr.setLeaveDate(activeDate.getTime());
                ecr.setChangeType(EmployeeChangeRecords.CHANGE_TYPE_REGULAR);
                ecr.setCreator(80001);
                employeeChangeRecordsService.processNewRegular(ecr);
            } catch (Throwable e) {
                log.error("凌晨3点执行员工转正失败:" + eb, e);
            }
        }
        log.info("凌晨3点执行员工转正：end");
    }

}
