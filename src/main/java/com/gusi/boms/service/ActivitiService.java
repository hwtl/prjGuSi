/**
 * 
 */
package com.gusi.boms.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.CommentEntity;
import org.activiti.engine.runtime.ProcessInstance;
//import org.activiti.engine.impl.persistence.entity.IdentityLinkEntity;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gusi.boms.common.Constants;
import com.gusi.empTransfer.callback.CallBackInterface;
import com.dooioo.plus.oms.dyEnums.EmployeeStatus;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.service.EmployeeService;

/**
 * 工作流操作帮助类
 * 
 * @author Alex Yu
 * @Created 2014年2月19日下午6:04:21
 */
@Service
@Transactional(value = "activitiTransactionManager")
public class ActivitiService<T> {

	@Autowired
	RepositoryService repositoryService;
	@Autowired
	IdentityService identityService;
	@Autowired
	RuntimeService runtimeService;
	@Autowired
	TaskService taskService;
	@Autowired
	HistoryService historyService;
	@Autowired
	EmployeeService employeeService;

	/**
	 * 启动流程
	 * 
	 * @param userCode
	 *            直接申请人工号
	 * @param businessKey
	 *            业务表Id
	 * @param privilegeMap
	 *            候选人权限Map
	 * @param excludeGatewayParam
	 *            流程变量
	 * @param callBack
	 *            回调函数
	 * @return T
	 *            返回值
	 */
	@Transactional
	public T startProcess(Integer userCode, String businessKey,
			Map<String, Object> privilegeMap, ApprovalParameter excludeGatewayParam, CallBackInterface<T> callBack) {

		// 设定直接申请人(而不是被代理申请的人)
		identityService.setAuthenticatedUserId(String.valueOf(userCode));
		// 启动流程
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.putAll(privilegeMap);
		variables.putAll(excludeGatewayParam.getParameterMap());
		ProcessInstance processIns = runtimeService.startProcessInstanceByKey(
				Constants.PROCESS_DEFINITION_KEY, businessKey, variables);
		EmployeeTransferTaskStatus empTransStatus = getTaskStatus(processIns);
		T returnValue = callBack.callBack(empTransStatus, null, null);
		return returnValue;
	}

	/**
	 * 申请被打回
	 * 
	 * @param processInsId
	 *            流程Id
	 * @param taskId
	 *            打回流程的任务Id
	 * @param Comment
	 *            打回流程的备注
	 * @param userCode
	 *            打回流程者工号
	 * @param callBack
	 *            回调函数
	 * @return T
	 *            返回值
	 */
	@Transactional
	public T approveDenied(String processInsId, String taskId,
			String Comment, Integer userCode, CallBackInterface<T> callBack) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

		Map<String, Object> variables = new HashMap<String, Object>();
		// 任务审批Key
		String taskApproveKey = getTaskApproveKey(task);
		// 审批不通过
		variables.put(taskApproveKey, Constants.IS_FALSE);
		taskService.setVariablesLocal(taskId, variables);
		// 操作人的审批意见
		taskService.addComment(taskId, processInsId, Comment);
		taskService.claim(taskId, String.valueOf(userCode));
		taskService.complete(taskId, variables);
		ProcessInstance processIns = runtimeService.createProcessInstanceQuery().processInstanceId(processInsId).singleResult();
		EmployeeTransferTaskStatus empTransStatus = getTaskStatus(processIns);
		T returnValue = callBack.callBack(empTransStatus, null, null);
		return returnValue;
	}

	/**
	 * 审批通过
	 * 
	 * @param processInsId
	 *            流程Id
	 * @param taskId
	 *            通过审批的任务Id
	 * @param comment
	 *            任务审批通过备注
	 * @param userCode
	 *            通过审批的工号
	 * @param callBack
	 *            回调函数
	 * @return T
	 *            返回值
	 */
	@Transactional
	public T approvePassed(String processInsId, String taskId,
			String comment, Integer userCode, CallBackInterface<T> callBack) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		Map<String, Object> variables = new HashMap<String, Object>();
		// 任务审批Key
		String taskApproveKey = getTaskApproveKey(task);
		// 任务定义Key
		String taskDefKey = task == null ? null : task.getTaskDefinitionKey();
		if (StringUtils.isNotEmpty(taskApproveKey)
				&& StringUtils.isNotEmpty(taskDefKey)) {
			// 审批通过
			variables.put(taskApproveKey, Constants.IS_TRUE);
			taskService.setVariablesLocal(task.getId(), variables);
		}
		taskService.addComment(task.getId(), processInsId, comment);
		taskService.claim(task.getId(), String.valueOf(userCode));
		taskService.complete(task.getId(), variables);
		ProcessInstance processIns = runtimeService.createProcessInstanceQuery().processInstanceId(processInsId).singleResult();
		EmployeeTransferTaskStatus empTransStatus = new EmployeeTransferTaskStatus();
		if (processIns == null) {
			HistoricProcessInstance historicProcessIns =  historyService.createHistoricProcessInstanceQuery().processInstanceId(processInsId).singleResult();
			empTransStatus.setId(Integer.parseInt(historicProcessIns.getBusinessKey()));
			empTransStatus.setProcessInsId(processInsId);
		} else {
			empTransStatus = getTaskStatus(processIns);
		}
		T returnValue = callBack.callBack(empTransStatus, userCode, comment);
		return returnValue;
	}

	/**
	 * 重新申请
	 * 
	 * @param processInsId
	 *            流程Id
	 * @param taskId
	 *            任务Id
	 * @param userCode
	 *            处理人工号
	 * @param privilegeMap
	 *            候选人权限Map
	 * @param excludeGatewayParam
	 *            流程变量
	 * @param callBack
	 *            回调函数
	 * @return T
	 *            返回值
	 */
	@Transactional
	public T modifyApply(String processInsId, String taskId, Integer userCode,
			Map<String, Object> privilegeMap, ApprovalParameter excludeGatewayParam, CallBackInterface<T> callBack) {

		Map<String, Object> variables = new HashMap<String, Object>();
		// 重新申请
		variables.put(Constants.CONDITION_KEY_IS_ABORT, Constants.IS_FALSE);
		if (privilegeMap != null) {
			// 重新设置节点处理人
			variables.putAll(privilegeMap);
		}
		variables.putAll(excludeGatewayParam.getParameterMap());
		taskService.setVariablesLocal(taskId, variables);
		taskService.claim(taskId, String.valueOf(userCode));
		taskService.complete(taskId, variables);
		ProcessInstance processIns = runtimeService.createProcessInstanceQuery().processInstanceId(processInsId).singleResult();
		EmployeeTransferTaskStatus empTransStatus = getTaskStatus(processIns);
		T returnValue = callBack.callBack(empTransStatus, null, null);
		return returnValue;
	}

	/**
	 * 取消申请
	 * 
	 * @param taskId
	 *            任务Id
	 * @param callBack
	 *            回调函数
	 */
	@Transactional
	public void abortApply(String taskId, CallBackInterface<T> callBack) {

		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		String processInsId = task.getProcessInstanceId();
		ProcessInstance processIns = runtimeService.createProcessInstanceQuery().processInstanceId(processInsId).singleResult();

		Map<String, Object> variables = new HashMap<String, Object>();
		// 取消申请
		variables.put(Constants.CONDITION_KEY_IS_ABORT, Constants.IS_TRUE);
		taskService.setVariablesLocal(taskId, variables);
		taskService.complete(taskId, variables);
		EmployeeTransferTaskStatus empTransStatus = new EmployeeTransferTaskStatus();
		empTransStatus.setId(Integer.valueOf(processIns.getBusinessKey()));
        empTransStatus.setProcessInsId(processInsId);
		callBack.callBack(empTransStatus, null, null);
	}

	/**
	 * 根据流程Id取得对应的已完成任务的信息
	 * 
	 * @param processInsId
	 *            流程Id
	 * @return 已完成任务的信息
	 */
	public List<Map<String, Object>> getTaskHisList(String processInsId) {

		// 已经完成的任务查询
		List<HistoricTaskInstance> hisTasks = historyService
				.createHistoricTaskInstanceQuery()
				.processInstanceId(processInsId).finished()
				.orderByHistoricTaskInstanceEndTime().asc().list();
		// 页面上的任务履历
		List<Map<String, Object>> taskDetails = new ArrayList<Map<String, Object>>();
		// 插入第一行固定数据
		getHisTaskStartInfo(taskDetails, processInsId);

		Map<String, Object> element;
		int index = 1;
		for (HistoricTaskInstance hisTask : hisTasks) {
			element = new HashMap<String, Object>();
			// 时间排序的序号
			element.put("index", index);
			index++;
			// 任务Id
			element.put("taskId", hisTask.getId());
			// 任务名称
			element.put("taskName", hisTask.getName());
			// 处理人
			String userCodeStr = hisTask.getAssignee();
			element.put("assignee", userCodeStr);
			if (StringUtils.isEmpty(userCodeStr)) {
				continue;
			}
			Employee employee = employeeService.getEmployee(Integer
					.parseInt(userCodeStr), EmployeeStatus.All);
			// 处理人名
			element.put("assigneeName", employee.getUserName());
			// 处理人角色
			element.put("role", employee.getPositionName());
			List<HistoricVariableInstance> hisVarInstances = historyService
					.createHistoricVariableInstanceQuery()
					.taskId(hisTask.getId()).list();
			for (HistoricVariableInstance hisVarIns : hisVarInstances) {
				if (checkApproveKey(hisVarIns.getVariableName())
						&& Constants.IS_TRUE.equals(hisVarIns.getValue())) {
					// 处理结果_通过
					element.put("result", Constants.APPROVED);
				} else if (checkApproveKey(hisVarIns.getVariableName())
						&& Constants.IS_FALSE.equals(hisVarIns.getValue())) {
					// 处理结果_打回
					element.put("result", Constants.DENIED);
				}
			}
			// 处理意见
			List<Comment> comments = taskService.getTaskComments(hisTask
					.getId());
			StringBuilder sb = new StringBuilder();
			for (Comment comment : comments) {
				sb.append(((CommentEntity)comment).getMessage());
				sb.append(". ");
			}
			element.put("comment", sb.toString());
			// 处理完成时间
			element.put("endTime",
					DateFormatUtil.getString(hisTask.getEndTime()));
			// 耗时
			Long durationInMillis = hisTask.getDurationInMillis();
			String elapse = DateFormatUtil.getDurationString(durationInMillis);
			element.put("elapse", elapse);
			taskDetails.add(element);
		}

		// 插入下一条任务信息
		getNextTaskInfo(taskDetails, processInsId, index);
		return taskDetails;
	}

	/**
	 * 根据任务Id, 返回处理意见
	 * @param taskId 任务Id
	 * @return 处理意见
	 */
	public String getTaskComment(String processInstanceId) {
		List<HistoricTaskInstance> hisTasks = historyService.createHistoricTaskInstanceQuery()
				.processInstanceId(processInstanceId)
				.orderByHistoricTaskInstanceStartTime().finished().desc()
				.list();
		StringBuilder sb = new StringBuilder();
		if (ListUtil.isNotEmpty(hisTasks)) {
			List<Comment> comments = taskService.getTaskComments(hisTasks.get(0).getId());
			for (Comment comment : comments) {
				sb.append(((CommentEntity)comment).getMessage());
				sb.append(". ");
			}
			StringBuilderHelper.trimLast(sb, ". ");
		}
		return sb.toString();
	}

	/**
	 * 检查流程是否结束
	 * 
	 * @param processInsId
	 *            流程Id
	 * @return 是否结束. 未结束:false, 结束:true
	 */
	public boolean checkProcessFinished(String processInsId) {
		List<HistoricProcessInstance> hisProcessInstances = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInsId).finished().list();
		if (ListUtil.isNotEmpty(hisProcessInstances)) {
			return true;
		}
		return false;
	}

	/**
	 * 给定任务Id与用户工号, 判断是否是该用户任务
	 * 
	 * @param taskId
	 *            任务Id
	 * @param userCode
	 *            用户工号
	 * @return true:对应, false:不对应
	 */
	public boolean checkTaskOwner(String taskId, String userCode) {
		// 给定任务Id与用户工号, 取得对应的任务集
		List<Task> candidateTasks = taskService.createTaskQuery()
				.taskId(taskId).taskCandidateUser(userCode).list();
		List<Task> assigneeTasks = taskService.createTaskQuery().taskId(taskId)
				.taskAssignee(userCode).list();
		if (ListUtil.isNotEmpty(candidateTasks)
				&& ListUtil.isNotEmpty(assigneeTasks)) {
			return false;
		} else if (candidateTasks.size() > 0 || assigneeTasks.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获得当前任务所在处理环节
	 * 
	 * @param taskId
	 *            当前任务Id
	 * @return 处理环节
	 */
	public String getProcessStatus(String taskId) {
		Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
		// 应对从已经处理过的任务迁移做的判断处理
		if (task == null) {
			return null;
		}
		return task.getTaskDefinitionKey();
	}

	/**
	 * 获得历史任务的开始信息
	 * 
	 * @param hisTaskDetails
	 *            历史任务信息集
	 * @param processInsId
	 *            流程变量Id
	 */
	private void getHisTaskStartInfo(List<Map<String, Object>> hisTaskDetails,
			String processInsId) {
		Map<String, Object> element = new HashMap<String, Object>();
		// 历史流程实例
		HistoricProcessInstance hisProcessInstance = historyService
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processInsId).singleResult();
		// 时间排序的序号
		element.put("index", 0);
		// 任务名称
		element.put("taskName", "制单");
		// 处理人
		String userCodeStr = hisProcessInstance.getStartUserId();
		element.put("assignee", userCodeStr);
		Employee employee;
		if (StringUtils.isNotEmpty(userCodeStr)) {
			employee = employeeService.getEmployee(Integer
					.parseInt(userCodeStr));
			// 处理人名
			element.put("assigneeName", employee.getUserName());
			// 处理人角色
			element.put("role", employee.getPositionName());
		}
		// 处理结果_制单完成
		element.put("result", "制单完成");
		// 处理完成时间
		element.put("endTime",
				DateFormatUtil.getString(hisProcessInstance.getStartTime()));
		element.put("elapse", "0秒");
		hisTaskDetails.add(element);
	}

	/**
	 * 获得下一条任务的开始信息
	 * 
	 * @param taskDetails
	 *            任务信息集
	 * @param processInsId
	 *            流程变量Id
	 * @param index
	 *            任务序号
	 */
	private void getNextTaskInfo(List<Map<String, Object>> taskDetails,
			String processInsId, int index) {
		Map<String, Object> element = new HashMap<String, Object>();
		// 当前需要处理的任务
		List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInsId).list();
		if (ListUtil.isNotEmpty(tasks)) {
			for (Task task : tasks) {
				// 时间排序的序号
				element.put("index", index);
				index++;
				// 任务名称
				String taskName = getTaskName(task.getTaskDefinitionKey());
				element.put("taskName", taskName);
				// 处理人
				List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
				if (ListUtil.isNotEmpty(identityLinks)) {
					StringBuilder userCodeSb = new StringBuilder();
					for (IdentityLink identity : identityLinks) {
						String groupId = identity.getGroupId();
						List<User> users = identityService.createUserQuery().memberOfGroup(groupId).list();
						if (ListUtil.isNotEmpty(users)) {
							for (User user : users) {
								if (!checkExist(userCodeSb, user.getId())) {
									userCodeSb.append(user.getId());
									userCodeSb.append(",");
								}
							}
						}
					}
					if (userCodeSb.length() > 0) {
						userCodeSb = StringBuilderHelper.trimLast(userCodeSb, ",");
						List<Employee> employees = employeeService.getEmployeesByUserCodes(userCodeSb.toString());
						element.put("assignee", userCodeSb.toString());
						if (ListUtil.isNotEmpty(employees)) {
							StringBuilder userNameSb = new StringBuilder();
							StringBuilder userRoleSb = new StringBuilder();
							for (Employee employee : employees) {
								userNameSb.append(employee.getUserName());
								userNameSb.append(",");
								userRoleSb.append(employee.getPositionName());
								userRoleSb.append(",");
							}
							userNameSb = StringBuilderHelper.trimLast(userNameSb, ",");
							userRoleSb = StringBuilderHelper.trimLast(userRoleSb, ",");
							// 处理人名
							element.put("assigneeName", userNameSb.toString());
							// 处理人角色
							element.put("role", userRoleSb.toString());
						}
					}
				}
				taskDetails.add(element);
			}
		}
	}

	/**
	 * 取得任务的任务审批Key
	 * 
	 * @param taskId
	 *            任务Id
	 * @return 任务审批Key
	 */
	private String getTaskApproveKey(Task task) {
		if (task != null) {
			// 任务定义Key
			String taskDefKey = task.getTaskDefinitionKey();
			switch (taskDefKey) {
			case Constants.TASK_DEF_KEY_EXPORT_MANAGER_APPROVE:
				return Constants.CONDITION_KEY_IS_EXPORT_MANAGER_APPROVE;
			case Constants.TASK_DEF_KEY_EXPORT_SUPERVISOR_APPROVE:
				return Constants.CONDITION_KEY_IS_EXPORT_SUPERVISOR_APPROVE;
			case Constants.TASK_DEF_KEY_EXPORT_DIRECTOR_APPROVE:
				return Constants.CONDITION_KEY_IS_EXPORT_DIRECTOR_APPROVE;
			case Constants.TASK_DEF_KEY_HR_APPROVE:
				return Constants.CONDITION_KEY_IS_HR_APPROVE;
			case Constants.TASK_DEF_KEY_IMPORT_SUPERVISOR_APPROVE:
				return Constants.CONDITION_KEY_IS_IMPORT_SUPERVISOR_APPROVE;
			case Constants.TASK_DEF_KEY_HOUSE_HANDOVER:
				return Constants.CONDITION_KEY_NEED_HOUSE_HANDOVER;
			case Constants.TASK_DEF_KEY_CUSTOMER_HANDOVER:
				return Constants.CONDITION_KEY_NEED_CUSTOMER_HANDOVER;
			}
		}
		return null;
	}

	/**
	 * 确认是否是审批结果的Key
	 * 
	 * @param varName
	 *            任务变量名
	 * @return 是否
	 */
	private boolean checkApproveKey(String varName) {
		switch (varName) {
		case Constants.CONDITION_KEY_IS_EXPORT_MANAGER_APPROVE:
		case Constants.CONDITION_KEY_IS_EXPORT_SUPERVISOR_APPROVE:
		case Constants.CONDITION_KEY_IS_EXPORT_DIRECTOR_APPROVE:
		case Constants.CONDITION_KEY_IS_HR_APPROVE:
		case Constants.CONDITION_KEY_IS_IMPORT_SUPERVISOR_APPROVE:
		case Constants.CONDITION_KEY_NEED_HOUSE_HANDOVER:
		case Constants.CONDITION_KEY_NEED_CUSTOMER_HANDOVER:
			return true;
		}
		return false;
	}

	/**
	 * 根据流程节点定义Key, 取得流程节点名称
	 * @param taskDefKey 流程节点定义Key
	 * @return 流程节点名称
	 */
	private String getTaskName(String taskDefKey) {
		switch (taskDefKey) {
		case Constants.TASK_DEF_KEY_EXPORT_MANAGER_APPROVE:
			return Constants.TASK_DEF_NAME_EXPORT_MANAGER_APPROVE;
		case Constants.TASK_DEF_KEY_EXPORT_SUPERVISOR_APPROVE:
			return Constants.TASK_DEF_NAME_EXPORT_SUPERVISOR_APPROVE;
		case Constants.TASK_DEF_KEY_EXPORT_DIRECTOR_APPROVE:
			return Constants.TASK_DEF_NAME_EXPORT_DIRECTOR_APPROVE;
		case Constants.TASK_DEF_KEY_IMPORT_SUPERVISOR_APPROVE:
			return Constants.TASK_DEF_NAME_IMPORT_SUPERVISOR_APPROVE;
		case Constants.TASK_DEF_KEY_HR_APPROVE:
			return Constants.TASK_DEF_NAME_HR_APPROVE;
		case Constants.TASK_DEF_KEY_HOUSE_HANDOVER:
			return Constants.TASK_DEF_NAME_HOUSE_HANDOVER;
		case Constants.TASK_DEF_KEY_CUSTOMER_HANDOVER:
			return Constants.TASK_DEF_NAME_CUSTOMER_HANDOVER;
		case Constants.TASK_DEF_KEY_MODIFY_APPLY:
			return Constants.TASK_DEF_NAME_MODIFY_APPLY;
		default:
			return null;
		}
	}

	/**
	 * 根据流程实例, 获取流程节点的详情(节点名称和处理人)
	 * @param processIns 流程实例
	 */
	private EmployeeTransferTaskStatus getTaskStatus(ProcessInstance processIns) {
		if (processIns == null) {
			// 如果找不到流程, 流程已经结束
			return null;
		}
		// 取得任务
		Task task = taskService.createTaskQuery().processInstanceId(processIns.getId()).singleResult();
		List<IdentityLink> identityLinks = taskService.getIdentityLinksForTask(task.getId());
		// 处理人工号
		StringBuilder userCodeSb = new StringBuilder();
		// 处理人姓名
		StringBuilder userNameSb = new StringBuilder();
		// 处理所需权限
		StringBuilder groupSb = new StringBuilder();
		for (IdentityLink identityLink : identityLinks) {
			String groupId = identityLink.getGroupId();
			if (!checkExist(groupSb, groupId)) {
				// 如果不存在, 则添加, 过滤重复元素
				groupSb.append(groupId);
				groupSb.append(",");
			}
			List<User> users = identityService.createUserQuery().memberOfGroup(groupId).list();
			for (User user : users) {
				String userId = user.getId();
				if (!checkExist(userCodeSb, userId)) {
					// 用户工号
					userCodeSb.append(userId);
					userCodeSb.append(",");
					// 用户姓名(工号)
					userNameSb.append(user.getFirstName());
					userNameSb.append("(");
					userNameSb.append(user.getId());
					userNameSb.append(")");
					userNameSb.append(",");
				}
			}
		}
		userCodeSb = StringBuilderHelper.trimLast(userCodeSb, ",");
		userNameSb = StringBuilderHelper.trimLast(userNameSb, ",");
		groupSb = StringBuilderHelper.trimLast(groupSb, ",");
		EmployeeTransferTaskStatus empTransStatus = new EmployeeTransferTaskStatus();
		// 业务Id
		empTransStatus.setId(Integer.parseInt(processIns.getBusinessKey()));
		// 流程Id
		empTransStatus.setProcessInsId(processIns.getId());
		// 任务Id
		empTransStatus.setTaskId(task.getId());
		// 处理人工号
		empTransStatus.setHandlerCode(userCodeSb.toString());
		// 处理人
		empTransStatus.setHandlerName(userNameSb.toString());
		// 操作所需权限Url
		empTransStatus.setOperateUrl(groupSb.toString());
		// 节点定义Key
		empTransStatus.setTaskDefKey(task.getTaskDefinitionKey());
		// 节点定义Key
		empTransStatus.setTaskName(task.getTaskDefinitionKey());
		// 节点名称
		empTransStatus.setTaskNameCn(getTaskName(task.getTaskDefinitionKey()));
		return empTransStatus;
	}

	/**
	 * 寻找给定的StringBuilder对象中是否存在指定字符串
	 * @param sb 源StringBuilder
	 * @param findStr 指定的字符串
	 * @return 是否存在, 存在:true, 不存在:false
	 */
	private boolean checkExist(StringBuilder sb, String findStr) {
		boolean result = false;
		if (sb.indexOf(findStr) >= 0) {
			result = true;
		}
		return result;
	}
}
