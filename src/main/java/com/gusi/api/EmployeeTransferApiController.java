package com.gusi.api;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gusi.boms.util.ListUtil;
import com.gusi.empTransfer.dto.VEmployeeTransfer;
import com.gusi.empTransfer.model.EmployeeTransferTaskStatus;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.identity.User;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gusi.boms.common.Constants;
import com.gusi.boms.service.ActivitiService;
import com.gusi.empTransfer.common.EmpTransferConstants;
import com.gusi.empTransfer.service.EmployeeTransferService;
import com.gusi.empTransfer.service.EmployeeTransferTaskStatusService;
import com.dooioo.web.controller.BaseController;
import com.dooioo.web.helper.JsonResult;

/**
 * @Author: fdj
 * @Since: 2014-02-25 15:17
 * @Description: 员工转调接口
 */
@Controller
@RequestMapping("/oms/api/empTransfer/**")
public class EmployeeTransferApiController extends BaseController {

	private static final Log LOG = LogFactory
			.getLog(EmployeeTransferApiController.class);

	@Autowired
	private EmployeeTransferService employeeTransferService;
	@Autowired
	private EmployeeTransferTaskStatusService employeeTransferTaskStatusService;
	@Autowired
	private ActivitiService<String> activitiService;
	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private TaskService taskService;
	@Autowired
	private RuntimeService runtimeService;
	@Autowired
	private IdentityService identityService;
	@Autowired
	private HistoryService historyService;

	/**
	 * 房源交接
	 * 
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/fyTransfer/{userCode}")
	public String updateFyStatus(@PathVariable int userCode, Model model) {
		try {
			VEmployeeTransfer employeeTransfer = (VEmployeeTransfer) employeeTransferService
					.findByUserCode(userCode,
							Constants.TASK_DEF_KEY_HOUSE_HANDOVER);
			if (employeeTransfer != null) {
				return "redirect:/transfer/emp/approval/"
						+ employeeTransfer.getId() + "/"
						+ employeeTransfer.getProcessInsId() + "/"
						+ employeeTransfer.getTaskId();
			}
		} catch (Exception e) {
			LOG.error("更新房源状态失败。userCode:" + userCode, e);
		}
		model.addAttribute("title", "错误提醒！");
		model.addAttribute("msg", "处理交接回调失败！");
		return "error/error";
	}

	@ResponseBody
	@RequestMapping(value = "/findViewById/{id}")
	public JsonResult findViewById(@PathVariable int id) {
		return ok().put("rs", employeeTransferService.findViewById(id));
	}

	/**
	 * 可以交接
	 * 
	 * @param userCode
	 * @return
	 */
	@RequestMapping("/kyTransfer/{userCode}")
	public String updateKyStatus(@PathVariable int userCode, Model model) {
		try {
			VEmployeeTransfer employeeTransfer = (VEmployeeTransfer) employeeTransferService
					.findByUserCode(userCode,
							Constants.TASK_DEF_KEY_CUSTOMER_HANDOVER);
			if (employeeTransfer != null) {
				return "redirect:/transfer/emp/approval/"
						+ employeeTransfer.getId() + "/"
						+ employeeTransfer.getProcessInsId() + "/"
						+ employeeTransfer.getTaskId();
			}
		} catch (Exception e) {
			LOG.error("更新房源状态失败。userCode:" + userCode, e);
		}
		model.addAttribute("title", "错误提醒！");
		model.addAttribute("msg", "处理交接回调失败！");
		return "error/error";
	}

	/**
	 * 部署流程接口
	 * 
	 * @param fileName
	 *            文件名
	 */
	@RequestMapping(value = "/deploy/{fileName}")
	public void deployProcess(@PathVariable String fileName) {
		try {
			if (StringUtils.isEmpty(fileName)) {
				fileName = Constants.PROCESS_DEFINITION_KEY;
			}
			Deployment deployment = repositoryService.createDeployment()
					.addClasspathResource("process" + "/" + fileName + ".bpmn")
					.deploy();
			if (deployment != null) {
				LOG.info("Deployment ID: " + deployment.getId());
			}
		} catch (Exception ex) {
			LOG.error(ex.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "/getProcessDef/{processDefKey}")
	public JsonResult getProcessDef(@PathVariable String processDefKey) {
		List<ProcessDefinition> processDefinitions = repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefKey).list();
		if (ListUtil.isNotEmpty(processDefinitions)) {
			List<Map<String, Object>> deployInfos = new ArrayList<Map<String, Object>>();
			for (ProcessDefinition processDefinition : processDefinitions) {
				ProcessDefinitionEntity processDefEntity = (ProcessDefinitionEntity) processDefinition;
				String deployId = processDefEntity.getDeploymentId();
				List<Deployment> deployments = repositoryService
						.createDeploymentQuery().deploymentId(deployId).list();
				if (ListUtil.isNotEmpty(deployments)) {
					for (Deployment deployment : deployments) {
						String singleDeployId = deployment.getId();
						Date deployDate = deployment.getDeploymentTime();
						SimpleDateFormat sdf = new SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String deployDateStr = sdf.format(deployDate);
						Map<String, Object> deployMap = new HashMap<String, Object>();
						deployMap.put("deployId", singleDeployId);
						deployMap.put("deployDate", deployDateStr);
						deployInfos.add(deployMap);
					}
				}
			}
			return ok().put("deployInfos", deployInfos);
		}
		return fail();
	}

	/**
	 * 根据流程ID查询流程信息
	 * 
	 * @param proccessInsId
	 *            流程ID
	 * @return 流程信息
	 */
	@ResponseBody
	@RequestMapping(value = "/getProcessDetails/{processInsId}")
	public JsonResult getProcessDetails(
			@PathVariable(value = "processInsId") String proccessInsId) {
		try {
			ProcessInstance processIns = runtimeService
					.createProcessInstanceQuery()
					.processInstanceId(proccessInsId).singleResult();
			Task task = taskService.createTaskQuery()
					.processInstanceId(proccessInsId).singleResult();
			Map<String, Object> result = new HashMap<String, Object>();
			if (task != null) {
				String taskId = task.getId();
				String taskDefKey = task.getTaskDefinitionKey();
				List<IdentityLink> identityLinks = taskService
						.getIdentityLinksForTask(taskId);
				if (identityLinks != null) {
					Map<String, Map<String, String>> candidatesInfo = new HashMap<String, Map<String, String>>();
					for (IdentityLink identityLink : identityLinks) {
						String groupId = identityLink.getGroupId();
						Map<String, String> usersInfo = new HashMap<String, String>();
						List<User> users = identityService.createUserQuery()
								.memberOfGroup(groupId).list();
						if (users != null && users.size() > 0) {
							for (User user : users) {
								usersInfo
										.put(user.getId(), user.getFirstName());
							}
							candidatesInfo.put(groupId, usersInfo);
						}
					}
					// 处理人信息
					result.put("candidatesInfo", candidatesInfo);
				}
				// 审批历史记录
				List<Map<String, Object>> taskHisList = activitiService
						.getTaskHisList(proccessInsId);
				if (taskHisList != null && taskHisList.size() > 0) {
					result.put("taskHisList", taskHisList);
				}
				// 流程ID
				result.put("ProcessId", processIns.getId());
				// 任务ID
				result.put("TaskId", taskId);
				// 任务节点定义
				result.put("TaskDefKey", taskDefKey);
			}
			return ok().put("data", result);
		} catch (Exception ex) {
			return fail(ex.getMessage());
		}
	}

	@RequestMapping(value = "/claim/{processInstanceId}")
	public void claim(
			@PathVariable(value = "processInstanceId") String processInstanceId) {
		List<Task> tasks = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).list();
		if (ListUtil.isNotEmpty(tasks) && tasks.size() == 1) {
			Task task = tasks.get(0);
			taskService.claim(task.getId(), "93197");
		}
	}

	/**
	 * 对于已经claim的任务, 重新解绑
	 * 
	 * @param processInstanceId
	 *            流程ID
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/unclaim/{processInstanceId}")
	public Map<String, String> unclaimTask(
			@PathVariable(value = "processInstanceId") String processInstanceId) {
		List<Task> tasks = taskService.createTaskQuery()
				.processInstanceId(processInstanceId).list();
		Map<String, String> maps = new HashMap<String, String>();
		if (ListUtil.isNotEmpty(tasks) && tasks.size() == 1) {
			Task task = tasks.get(0);
			maps.put("processInstanceId", processInstanceId);
			maps.put("taskId", task.getId());
			maps.put("taskDefinitionKey", task.getTaskDefinitionKey());
			taskService.claim(task.getId(), null);
		}
		return maps;
	}

	/**
	 * 根据工号查找任务数
	 * 
	 * @param userCode
	 *            工号
	 * @return 任务数
	 */
	@ResponseBody
	@RequestMapping(value = "/queryForUserTaskCount/{userCode}")
	public JsonResult queryForUserTaskCount(@PathVariable String userCode) {
		Set<String> processIds = new HashSet<String>();
		try {
			// 根据工号来查询所属任务
			List<Task> candidateTaskList = taskService.createTaskQuery()
					.processDefinitionKey(Constants.PROCESS_DEFINITION_KEY)
					.taskCandidateUser(userCode).active().list();
			List<Task> assigneeTaskList = taskService.createTaskQuery()
					.processDefinitionKey(Constants.PROCESS_DEFINITION_KEY)
					.taskAssignee(userCode).active().list();
			List<Task> tasks = new ArrayList<Task>();
			tasks.addAll(candidateTaskList);
			tasks.addAll(assigneeTaskList);
			for (Task task : tasks) {
				processIds.add(task.getProcessInstanceId());
			}
		} catch (Exception ex) {
			return fail(ex.getMessage());
		}
		return ok().put("taskCount", processIds.size());
	}

	/**
     * 结束业务中已经作废的流程
     */
	@ResponseBody
	@RequestMapping(value = "/deleteProcessIns")
	public JsonResult deleteProcessInstance() {
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("status", EmpTransferConstants.STATUS_D);
		List<EmployeeTransferTaskStatus> employeeTransfers = employeeTransferTaskStatusService
				.findByMap(variables);
		Set<String> processInsIds = new HashSet<String>();
		if (ListUtil.isNotEmpty(employeeTransfers)) {
			for (EmployeeTransferTaskStatus employeeTransfer : employeeTransfers) {
				processInsIds.add(employeeTransfer.getProcessInsId());
			}
		}
		LOG.info("待处理流程ID: " + processInsIds.toString());
		List<ProcessInstance> processInsList = runtimeService
				.createProcessInstanceQuery().processInstanceIds(processInsIds)
				.list();
		if (ListUtil.isNotEmpty(processInsList)) {
			for (ProcessInstance processIns : processInsList) {
				runtimeService.deleteProcessInstance(
						processIns.getProcessInstanceId(),
						"unknowSituationsBadData");
				LOG.info("已删除流程: " + processIns.getProcessInstanceId());
			}
		}
		return ok().put("ProcessInstanceId: ", processInsIds.toString());
	}

	/**
	 * 员工转调数据采集
	 * @return 区间段数据
	 */
	@ResponseBody
	@RequestMapping(value = "/getTransferHandleDuration")
	public JsonResult getTransferHandleDuration() {
		long less1H = getMilisecondsByHour(1);
		long less2H = getMilisecondsByHour(2);
		long less5H = getMilisecondsByHour(5);
		long less24H = getMilisecondsByHour(24);
		long less48H = getMilisecondsByHour(48);
		long less5D = getMilisecondsByHour(5 * 24);

		int less1HCount = 0;
		int less2HCount = 0;
		int less5HCount = 0;
		int less24HCount = 0;
		int less48HCount = 0;
		int less5DCount = 0;
		int more5DCount = 0;

		List<String> less1HID = new ArrayList<String>();
		List<String> less2HID = new ArrayList<String>();
		List<String> less5HID = new ArrayList<String>();
		List<String> less24HID = new ArrayList<String>();
		List<String> less48HID = new ArrayList<String>();
		List<String> less5DID = new ArrayList<String>();
		List<String> more5DID = new ArrayList<String>();
		
		List<HistoricProcessInstance> historicProcessInsList = historyService.createHistoricProcessInstanceQuery().finished().list();
		if (ListUtil.isNotEmpty(historicProcessInsList)) {
			for (HistoricProcessInstance hisProcessIns : historicProcessInsList) {
				if (hisProcessIns.getDurationInMillis() <= less1H) {
					less1HCount++;
					less1HID.add(hisProcessIns.getId());
					continue;
				} else if (hisProcessIns.getDurationInMillis() <= less2H) {
					less2HCount++;
					less2HID.add(hisProcessIns.getId());
					continue;
				} else if (hisProcessIns.getDurationInMillis() <= less5H) {
					less5HCount++;
					less5HID.add(hisProcessIns.getId());
					continue;
				} else if (hisProcessIns.getDurationInMillis() <= less24H) {
					less24HCount++;
					less24HID.add(hisProcessIns.getId());
					continue;
				} else if (hisProcessIns.getDurationInMillis() <= less48H) {
					less48HCount++;
					less48HID.add(hisProcessIns.getId());
					continue;
				} else if (hisProcessIns.getDurationInMillis() <= less5D) {
					less5DCount++;
					less5DID.add(hisProcessIns.getId());
					continue;
				} else {
					more5DCount++;
					more5DID.add(hisProcessIns.getId());
				}
			}
		}
		Map<String, Integer> count = new HashMap<String, Integer>();
		count.put("1小时之内", less1HCount);
		count.put("2小时之内", less2HCount);
		count.put("5小时之内", less5HCount);
		count.put("24小时之内", less24HCount);
		count.put("48小时之内", less48HCount);
		count.put("5天之内", less5DCount);
		count.put("5天以上", more5DCount);

		Map<String, String> processIds = new HashMap<String, String>();
		processIds.put("1小时之内", less1HID.toString());
		processIds.put("2小时之内", less2HID.toString());
		processIds.put("5小时之内", less5HID.toString());
		processIds.put("24小时之内", less24HID.toString());
		processIds.put("48小时之内", less48HID.toString());
		processIds.put("5天之内", less5DID.toString());
		processIds.put("5天以上", more5DID.toString());
		return ok().put("count", count).put("processIDs", processIds);
	}

	private long getMilisecondsByHour(int hours) {
		long miliseconds = hours * 60 * 60 * 1000;
		return miliseconds;
	}
}
