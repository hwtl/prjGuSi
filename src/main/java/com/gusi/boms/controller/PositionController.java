package com.gusi.boms.controller;

import com.gusi.base.controller.BomsBaseController;
import com.gusi.boms.common.Constants;
import com.gusi.boms.helper.PositionHelper;
import com.gusi.boms.model.Position;
import com.gusi.boms.model.Select;
import com.gusi.boms.model.TitleSerial;
import com.gusi.boms.model.VPosition;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.helper.JsonResult;
import com.gusi.boms.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 职位控制器
 * @ClassName: PositionController 
 * @Description: 处理职位相关的Url拦截
 * @author fdj
 * @date 2013-5-23 下午4:20:33
 */
@Controller
@RequestMapping(value="/position")
public class PositionController extends BomsBaseController {

	@Autowired
	private Position2Service position2Service;
	@Autowired
	private TitleSerialService titleSerialService;
    @Autowired
    private EmployeePositionService employeePositionService;
    @Autowired
    private SelectService selectService;
    @Autowired
    private TitleService titleService;
    @Autowired
    private PositionTitleService positionTitleService;


    /**
     * 列表页跳转拦截
     * @param position
     * @param pageNo
     * @param model
     * @return
     */
	@RequestMapping(value="/list",method= RequestMethod.GET)
	public String list(VPosition position, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo, Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_POSITION_BASE)) {
            return errorNoPrivilege(model);
        }
		Paginate paginate = position2Service.queryForPaginate(position, pageNo, employee);
		model.addAttribute("paginate", paginate);
        List<TitleSerial> titleSerialList = titleSerialService.queryTitleSerials();
        model.addAttribute("titleSerialList", titleSerialList);
        model.addAttribute("position", position);
        return "/position/list";
	}

    /**
     * 添加页跳转拦截
     * @param model
     * @return
     */
	@RequestMapping(value="/add",method= RequestMethod.GET)
	public String add(Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_POSITION_ADD)) {
            return errorNoPrivilege(model);
        }
		List<TitleSerial> titleSerialList = titleSerialService.queryTitleSerials();
		model.addAttribute("titleSerialList", titleSerialList);
        model.addAttribute("titleList", titleService.queryForList());
		return "/position/form";
	}

    /**
     * 添加、编辑岗位保存拦截
     * @param position
     * @param request
     * @return
     */
	@RequestMapping(value="/add",method= RequestMethod.POST)
	public String save(VPosition position, HttpServletRequest request, Model model) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_POSITION_ADD)) {
            return errorNoPrivilege(model);
        }
        if(position.getId() == 0) {
           position = position2Service.addPostion(position, employee);
        } else {
            position2Service.updatePostion(position, employee.getUserCode());
        }
		return "redirect:/position/"+position.getId()+"/details";
	}

    /**
     * 详情页跳转拦截
     * @param id
     * @param model
     * @return
     */
	@RequestMapping(value="/{id}/details",method= RequestMethod.GET)
	public String details(@PathVariable int id, Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_POSITION_ADD)) {
            return errorNoPrivilege(model);
        }
        model.addAttribute("position",  position2Service.findById(id));
        model.addAttribute("titleSerialList", titleSerialService.queryTitleSerials());
        model.addAttribute("titleList", titleService.queryForList());
        model.addAttribute("positionTitles", PositionHelper.format(positionTitleService.queryForListByPositionId(id)));
        return "/position/details";
	}

    /**
     * 编辑页面跳转拦截
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/{id}/edit",method= RequestMethod.GET)
    public String edit(@PathVariable int id, Model model, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_POSITION_ADD)) {
            return errorNoPrivilege(model);
        }
        model.addAttribute("position",  position2Service.findById(id));
        model.addAttribute("titleSerialList", titleSerialService.queryTitleSerials());
        model.addAttribute("titleList", titleService.queryForList());
        model.addAttribute("positionTitles", PositionHelper.format(positionTitleService.queryForListByPositionId(id)));
        return "/position/form";
    }

    /**
     * 关闭岗位
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/close",method= RequestMethod.GET)
    public @ResponseBody JsonResult close(@PathVariable int id, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_POSITION_SWITCH)) {
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        Position position = position2Service.findById(id);
        position.setUpdator(employee.getUserCode());
        if(position2Service.close(position)) {
            position.setStatus(Position.STATUS_STOP);
            return ok().put("operateStr", PositionHelper.getOperateStr(position, employee));
        }
        return fail("对不起，停用失败！");
    }

    /**
     * 开启岗位
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/open",method= RequestMethod.GET)
    public @ResponseBody JsonResult open(@PathVariable int id, HttpServletRequest request) {
        Employee employee = this.getSesionUser(request);
        if(!this.isSuperAdmin(employee) && !employee.checkPrivilege(Constants.OMS_OM_POSITION_SWITCH)) {
            return fail("对不起，你没有此操作权限，请联系管理员。");
        }
        Position position = position2Service.findById(id);
        position.setUpdator(employee.getUserCode());
        if(position2Service.open(position)) {
            position.setStatus(Position.STATUS_NORMAL);
            return ok().put("operateStr", PositionHelper.getOperateStr(position, employee));
        }
        return fail("对不起，启用失败！");
    }

    /**
     * 通过职系id获得职等拦截
     * @param titleServialId
     * @return
     */
	@RequestMapping(value="/searchTitle/{titleServialId}",method= RequestMethod.GET)
	public @ResponseBody JsonResult searchTitle(@PathVariable int titleServialId) {
		List<Select> titleList = selectService.queryTitlesBySerialId(titleServialId);
		return ok().put("selectList", titleList);
	}

    /**
     * 验证职位名称
     * @param id
     * @param positionName
     * @return
     */
    @RequestMapping(value="/checkName/{id}/{positionName}",method= RequestMethod.GET)
    public @ResponseBody JsonResult checkName(@PathVariable int id, @PathVariable String positionName) {
        if(position2Service.checkName(id, positionName)) {
            return ok();
        }
        return fail("对不起，该岗位名称已经存在！");
    }

    /**
     * 判断该岗位下是否存在员工
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/countEmployee",method= RequestMethod.GET)
    public @ResponseBody JsonResult countEmployee(@PathVariable int id) {
        if(employeePositionService.countEmployeeByPositionId(id) == 0) {
            return ok();
        }
        return  fail();
    }

    @RequestMapping(value = "/queryPosition/{titleId}")
    public @ResponseBody JsonResult queryPositionByTitleId(@PathVariable int titleId){
        List<Select> positions = selectService.queryPositionByTitleId(titleId);
        return ok().put("selectList",positions);
    }


}
