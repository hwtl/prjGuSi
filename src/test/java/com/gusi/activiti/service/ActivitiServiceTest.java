/**
 * 
 */
package com.gusi.activiti.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gusi.BaseTest;
import com.gusi.boms.util.DateFormatUtil;
import com.gusi.boms.util.ListUtil;
import com.gusi.empTransfer.dto.ApprovalParameter;
import com.gusi.empTransfer.model.EmployeeTransferTaskStatus;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gusi.boms.service.ActivitiService;
import com.gusi.empTransfer.service.EmployeeTransferTaskStatusService;

/**
 * @author Alex Yu
 * @Created 2014年2月25日上午11:21:09
 */
public class ActivitiServiceTest extends BaseTest {

	Logger log = Logger.getLogger(getClass());

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	ActivitiService activitiService;
	@Autowired
	IdentityService identityService;
	@Autowired
	HistoryService historyService;
	@Autowired
	EmployeeTransferTaskStatusService employeeTransferTaskStatusService;

	Deployment deployment;

	ProcessInstance processIns;

	String processInsId = "96d8fb78-9f71-11e3-83c8-f80f418794b8";

	private Map<String, Object> privilegeMap = new HashMap<String, Object>();

	{
		// 转出: 陆家嘴C组
		privilegeMap.put("exportManagerApproveURL", "20487_exportManagerApprove");
		privilegeMap.put("exportSupervisorApproveURL", "");
		privilegeMap.put("exportDirectorApproveURL", "");
		privilegeMap.put("importSupervisorApproveURL", "");
		privilegeMap.put("hrApproveURL", "hrApprove");
		privilegeMap.put("houseHandoverURL", "20487_houseHandover");
		privilegeMap.put("customerHandoverURL", "20487_customerHandover");
	}

	private ApprovalParameter excludeGatewayParam = new ApprovalParameter();

	{
		// 不是后台
		excludeGatewayParam.setHtStatus(false);
		// 不是后台或者代理项目部
		excludeGatewayParam.setHtOrDlxmStatus(false);
		// 主动
		excludeGatewayParam.setTransferState(1);
		// 交接
		excludeGatewayParam.setFangyuanStatus(1);
		// 不交接
		excludeGatewayParam.setKeyuanStatus(0);
	}

	@Test
	public void testSet() {
		Set<String> sets = new HashSet<String>();
		sets.add("55");
		for (int i = 0; i < 10; i++) {
			sets.add("77");
		}
		System.out.println(sets.toString());
	}
	
	@Test
	public void testT() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("status", -1);
		List<EmployeeTransferTaskStatus> employeeTransfers = employeeTransferTaskStatusService.findByMap(variables);
		Set<String> processInsIds = new HashSet<String>();
		if (ListUtil.isNotEmpty(employeeTransfers)) {
			for (EmployeeTransferTaskStatus employeeTransfer : employeeTransfers) {
				processInsIds.add(employeeTransfer.getProcessInsId());
			}
		}
		List<ProcessInstance> processInsList = runtimeService.createProcessInstanceQuery().processInstanceIds(processInsIds).list();
		if (ListUtil.isNotEmpty(processInsList)) {
			for (ProcessInstance processIns : processInsList) {
				runtimeService.deleteProcessInstance(processIns.getProcessInstanceId(), "unknowSituationsBadData");
				System.out.println(processIns.getProcessInstanceId());
			}
		}
		System.out.println();
	}

	@Test
	public void testKKK() {
		String processInstanceId = "fbacff00-bb16-11e3-836c-000c293a96f1";
		ProcessInstance processIns = null;
		Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		runtimeService.deleteProcessInstance(processInstanceId, "testDeleteProcessIns");
//		taskService.deleteTask(task.getId());
		processIns = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		HistoricProcessInstance his = historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
	}

	@Test
	public void testK() {
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser("80845").list();
		if (ListUtil.isNotEmpty(tasks)) {
			for (Task task : tasks) {
				String processInsId = task.getProcessInstanceId();
				ProcessInstance processIns = runtimeService.createProcessInstanceQuery().processInstanceId(processInsId).singleResult();
				String businessKey = processIns.getBusinessKey();
				String taskDefKey = task.getTaskDefinitionKey();
				System.out.println("ProcessInsId: " + processInsId + ", BusinessKey: " + businessKey + ", TaskDefKey: " + taskDefKey);
			}
		}
	}

	@Test
	public void testPeriod() {
		String times = "172330731,52707610,2584736,88348536,98110398,120183608,242605691,151774976,202683734,59450945,13140672,4187220,562801,533175,129294481,46607987,164706555,14720998,52797436,5007968,117454320,264136558,5692995,64708,59420045,492429,8767934,338364015,14661592,273962,1064171,1230828,456482,4073852,7610129,14598522,4916100,445744,211941,13578965,544643022,11130247,59297444,111941303,1213853,86364526,982005,255266015,4860000,58898161,77536112,545488045,80546279,3403955,398829,407096680,4794527,80560086,900497,6902748,3356753,20752062,19496733,7712576,233905332,6748805,93144143,279110717,831448,84265242,545356644,445595251,92573417,62920061,233824917,80460121,234329968,7346928,88514771,733012,59452752,35029613,565369,284704172";
		String[] timesStr = times.split(",");
		List<String> timeList = new ArrayList<String>();
		for (String str : timesStr) {
			Long timesLong = Long.parseLong(str);
			String value = DateFormatUtil.getDurationString(timesLong);
			System.out.println(value);
			timeList.add(value);
		}
	}

	@Test
	public void deployProcess() {
		deployment = repositoryService.createDeployment().addClasspathResource("process/positionTransfer.bpmn").deploy();
		log.info(deployment.getId());
		log.info(deployment.getName());
		log.info(deployment.getDeploymentTime());
	}

	@Test
	public void processDefTest() {
		List<ProcessDefinition> processDef = repositoryService.createProcessDefinitionQuery().processDefinitionKey("positionTransfer").list();
		if (ListUtil.isNotEmpty(processDef)) {
			System.out.println(processDef.size());
		}
	}

	@Test
	public void startProcess() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("isToBackStage", true);
		variables.put("isExportDirectorApprove", true);
		processIns = runtimeService.startProcessInstanceByKey("positionTransfer", variables);
		log.info("ProcessDefKey: " + processIns.getProcessDefinitionId());
		log.info("ProcessInstance ID: " + processIns.getProcessInstanceId());
		log.info("ProcessInstance: " + processIns.getId());
	}

	@Test
	public void getTask() {
		ProcessInstance processIns = getProcessIns(processInsId);
		Task task = taskService.createTaskQuery().processInstanceId(processIns.getProcessInstanceId()).singleResult();
		log.info(task.getId());
		log.info(task.getTaskDefinitionKey());
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("isExportDirectorApprove", false);
		taskService.complete(task.getId(), variables);
		task = taskService.createTaskQuery().processInstanceId(processIns.getProcessInstanceId()).singleResult();
		log.info(task.getId());
		log.info(task.getTaskDefinitionKey());
	}

	@Test
	public void testProcess() {
		Integer userCode = 1;
		String businessKey = "transfer_test_businessKey_0001";
		ApprovalParameter excludeGatewayParam = new ApprovalParameter();

//		try {
//			activitiService.startProcess(userCode, businessKey, privilegeMap, excludeGatewayParam);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	@Test
	public void testProcessIns() {
		List<ProcessInstance> processInsList = runtimeService.createProcessInstanceQuery().processDefinitionKey("positionTransfer").list();
		System.out.println(processInsList.size());
	}

	@Test
	public void testTaskHis() {
		String processInsId = "56317523-a8dc-11e3-8e2a-f80f418794b8";
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInsId).singleResult();
		List<Map<String, Object>> taskHisList = activitiService.getTaskHisList(processInsId);
		System.out.println();
	}

	@Test
	public void testTaskComment() {
		String comment = activitiService.getTaskComment("115b6bc4-a4fa-11e3-86ec-f80f416664a5");
		System.out.println(comment);
	}

	@Test
	public void testProcessInfo() {
		ProcessInstance processIns = runtimeService.createProcessInstanceQuery().processInstanceId("b0c381a3-a5d0-11e3-bfb8-f80f416664a5").singleResult();
		Task task = taskService.createTaskQuery().processInstanceId(processIns.getId()).singleResult();
		List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
		for (IdentityLink identityLink : identityLinks) {
			String groupId = identityLink.getGroupId();
			System.out.println(groupId);
			List<User> users = identityService.createUserQuery().memberOfGroup(groupId).list();
			for (User user : users) {
				String userName = user.getFirstName();
				String userId = user.getId();
				System.out.println(userId + ": " + userName);
			}
		}
	}

	@Test
	public void testHouseApprove() {
//		activitiService.approvePassed("cbd056fd-a597-11e3-b6da-f80f416664a5", "fa19187f-a597-11e3-b6da-f80f416664a5", "1234开心", 99991);
	}

	@Test
	public void testTaskDuration() {
		activitiService.getTaskHisList("2057a9a1-a5bb-11e3-8d2d-000c29398072");
	}

	@Test
	public void testListSizeAndNull() {
		List<String> target = null;
		for (String str : target) {
			
		}
		System.out.println("For judge null and size");
	}

	private ProcessInstance getProcessIns(String processInstanceId) {
		ProcessInstance processInstance = null;
		try {
			processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
			if (processInstance != null) {
				log.info(getId(processInstance.getId()));
				log.info(getInstanceId(processInstance.getProcessInstanceId()));
				log.info(getDefId(processInstance.getProcessDefinitionId()));
			}
		} catch (Exception ex) {
			log.error(ex.getMessage());
		}
		return processInstance;
	}

	private String getId(String id) {
		String value = "ID: " + id;
		return value;
	}

	private String getInstanceId(String id) {
		String value = "InsID: " + id;
		return value;
	}

	private String getDefId(String id) {
		String value = "DefID: " + id;
		return value;
	}
}
