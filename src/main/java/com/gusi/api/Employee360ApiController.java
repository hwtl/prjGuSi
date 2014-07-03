package com.gusi.api;

import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.ApiHelper;
import com.dooioo.web.controller.BaseController;
import com.gusi.boms.model.*;
import com.gusi.boms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * @Author: fdj
 * @update: 2014-1-22
 * 为员工360的组织架构的api服务
 */
@Controller
@RequestMapping(value = "/oms/api/employee/*")
public class Employee360ApiController extends BaseController{
    @Autowired
    private EmployeeRewardPunishmentService rewardService;
    @Autowired
    private EmployeeDetailsService employeeDetailsService;
    @Autowired
    private EmployeeFamilyService employeeFamilyService;
    @Autowired
    private EmployeeEducationExperService employeeEducationExperService;
    @Autowired
    private EmployeeTrainService employeeTrainService;
    @Autowired
    private EmployeeWorkExperService employeeWorkExperService;
    @Autowired
    private EmployeeChangeRecordsService employeeChangeRecordsService;
    @Autowired
    private ParameterService parameterService;


    /**
     * 获取奖惩信息
     * 员工360
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/rewards/{userCode}", method = RequestMethod.GET)
    public @ResponseBody String rewardList(@PathVariable int userCode) {
        String json = null;
        if(Constants.EMPLOYEEREWARDS.containsKey(userCode) && ApiHelper.compareWithNow(Constants.EMPLOYEEREWARDS.get(userCode).getCreateTime(), 6)) {
            json = Constants.EMPLOYEEREWARDS.get(userCode).getCacheData();
        } else {
            List<EmployeeRewardPunishment> rewardList = rewardService.queryForListByUserCode(userCode);
            json = ok().put("rewards", rewardList).toString();
            Constants.EMPLOYEEREWARDS.put(userCode, new ApiCache(json, new Date()));
        }
        return json;
    }

    /**
     * 获取档案信息
     * 员工360
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/archives/{userCode}", method = RequestMethod.GET)
    public @ResponseBody String employeeDetails(@PathVariable int userCode) {
        String json = null;
        if(Constants.EMPLOYEEDETAILS.containsKey(userCode) && ApiHelper.compareWithNow(Constants.EMPLOYEEDETAILS.get(userCode).getCreateTime(), 6)) {
            json = Constants.EMPLOYEEDETAILS.get(userCode).getCacheData();
        } else {
            EmployeeDetails details=employeeDetailsService.findForDetail(userCode);
            initDetails(details);
            List<EmployeeEducationExper> educations = employeeEducationExperService.findByUserCode(userCode);
            List<EmployeeWorkExper> works = employeeWorkExperService.findByUserCode(userCode);
            List<EmployeeFamily> familyMembers = employeeFamilyService.findFamiliesByUserCode(userCode);
            List<EmployeeTrain> trains = employeeTrainService.findTrainsByUserCode(userCode);
            json = ok().put("details",details).put("works",works).put("educations",educations).put("familyMembers", familyMembers).put("trains",trains).toString();
            Constants.EMPLOYEEDETAILS.put(userCode, new ApiCache(json, new Date()));
        }
        return json;
    }

    /**
     * 员工异动信息
     * 员工360
     * @param userCode
     * @return
     */
    @RequestMapping(value = "/changes/{userCode}", method = RequestMethod.GET)
    public @ResponseBody String changeRecord(@PathVariable int userCode) {
        String json = null;
        if(Constants.EMPLOYEECHANGES.containsKey(userCode) && ApiHelper.compareWithNow(Constants.EMPLOYEECHANGES.get(userCode).getCreateTime(), 1)) {
            json = Constants.EMPLOYEECHANGES.get(userCode).getCacheData();
        } else {
            List<EmployeeChangeRecords> changes = employeeChangeRecordsService.findNormalRecordsByUserCode(userCode);
            json = ok().put("changes", changes).toString();
            Constants.EMPLOYEECHANGES.put(userCode, new ApiCache(json, new Date()));
        }
        return json;
    }

    /**
     * 清除api缓存
     */
    @RequestMapping(value = "/removeAllCache", method = RequestMethod.GET)
    public void removeAllCache() {
        ApiHelper.releaseAll();
    }

    /**
     * @param details 初始化档案详情
     */
    private void initDetails(EmployeeDetails details) {
        //血型
        details.setBloodTypeValue(parameterService.findBloodTypes().get(details.getBloodType()));
        //婚姻状态
        details.setMaritalStatusValue(parameterService.findMarriages().get(details.getMaritalStatus()));
        //星座
        details.setConstellationValue(parameterService.findConstellations().get(details.getConstellation()));
        //民族
        details.setNationValue(parameterService.findNations().get(details.getNation()));
        //最近学位
        details.setLastDegreeValue(parameterService.findEducationDegrees().get(details.getLastDegree()));
        //政治背景
        details.setPoliticalBackGroundValue(parameterService.findPoliticalBackgrounds().get(details.getPoliticalBackGround()));
    }

}
