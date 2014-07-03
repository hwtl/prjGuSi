/**
 * 
 */
package com.gusi.activiti.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gusi.BaseTest;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.identity.User;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Alex Yu
 * @Created 2014年3月4日下午5:21:43
 */
public class ActivitiDiagramTest extends BaseTest {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	IdentityService identityService;

	String processDefKey = "testProcess";

	@Test
	public void deploy() {
		repositoryService.createDeployment().addClasspathResource("process/testProcess.bpmn").deploy();
	}

	@Test
	public void startProcess() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("testGroup1", "hrApprove");
		ProcessInstance processIns = runtimeService.startProcessInstanceByKey(processDefKey, variables);
		System.out.println(processIns.getId());
		Task task = taskService.createTaskQuery().processInstanceId(processIns.getId()).singleResult();
		List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
		List<User> users = identityService.createUserQuery().memberOfGroup("hrApprove").list();
		users = identityService.createUserQuery().memberOfGroup("hrApprove,20009_exportSupervisorApprove").list();
		for (User user : users) {
			System.out.println(user.getFirstName());
		}
		if (identityLinks != null && identityLinks.size() > 0) {
			for (IdentityLink identityLink : identityLinks) {
				String groupId = identityLink.getGroupId();
				String userId = identityLink.getUserId();
				System.out.println("GroupId: " + groupId);
				System.out.println("UserId: " + userId);
			}
		}
	}
}
