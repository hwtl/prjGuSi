package com.gusi.boms.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.model.Position;
import com.gusi.boms.model.VPosition;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.web.common.Paginate;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-04-10 09:36)
 * 职位业务逻辑
 */
@Service
public class Position2Service extends BaseService<Position> {

    @Autowired
    private EmployeeBaseInforService employeeBaseInforService;
    @Autowired
    private PositionTitleService positionTitleService;

	/**
	 * 职位分页
	 * @param vPosition
	 * @param pageNo
	 * @return
	 */
	public Paginate queryForPaginate(VPosition vPosition, int pageNo, Employee employee) {
		VPosition params = new VPosition();
		params.setColumns(" * ");
        params.setTable("[v2_position_titile_serial_list]");
        params.setOrderBy(" id desc ");
        params.setPageNo(pageNo);
        params.setPageSize(Configuration.getInstance().getPageSize());
        params.setWhere("company = '" + employee.getCompany() + "'");
        if(vPosition.getPositionType() != null && !vPosition.getPositionType().equals("")) {
        	params.setWhere(params.getWhere() + " and positionType = '" + vPosition.getPositionType() + "'");
        }
        if(vPosition.getPositionName() != null && !vPosition.getPositionName().equals("")) {
            params.setWhere(params.getWhere() + " and positionName like '%" + vPosition.getPositionName() + "%'");
        }
        if(vPosition.getSerialName() != null && !vPosition.getSerialName().equals("")) {
            params.setWhere(params.getWhere() + " and serialTitleInfo like '%" + vPosition.getSerialName() + "%'");
        }
        return queryForPaginate2(params);
	}
	
	/**
	 * 添加职位
	 * @param position 职位
	 * @param emp 创建人
	 */
    @Transactional
	public VPosition addPostion(VPosition position, Employee emp) {
        position.setCreator(emp.getUserCode());
        position.setId(this.insertAndReturnId(position));
        positionTitleService.deleteByPositionId(position.getId());
        positionTitleService.insertList(position.getTitleIds(), position.getId());
        return position;
	}

    /**
     * 更新职位相关信息
     * @update: 2014-05-27 13:22:16
     * @param position
     */
    @Transactional
    public void updatePostion(VPosition position, int updator) {
        //删除原有的岗位与职等职级的关系
        positionTitleService.deleteByPositionId(position.getId());
        //新增岗位的职等职级关系
        positionTitleService.insertList(position.getTitleIds(), position.getId());
        //更新岗位信息
        this.baseEdit(position, updator);
        employeeBaseInforService.updateEmpOrgPositionByPositionId(position.getId());
    }

    /**
     * 更新岗位保存操作人信息
     * @since: 2014-05-27 13:21:53
     * @param position
     * @param updator
     * @return
     */
    public boolean baseEdit(Position position, int updator) {
        position.setUpdator(updator);
        return update(sqlId("baseEdit"), position);
    }

	/**
	 * 停用岗位
	 * @param position 岗位
	 * @return
	 */
	public boolean close(Position position) {
		if(position != null) {
			return update(sqlId("close"), position);
		}
		return false;
	}

    public boolean open(Position position) {
        if(position != null) {
            return update(sqlId("open"), position);
        }
        return false;
    }

    /**
     * 根据Id查找职位详细信息（包括职系职等信息）
     * @param id
     * @return
     */
    public Position findDetailsById(int id) {
        return findById(sqlId("findDetailsById"), id);
    }

    /**
     * 根据职位名称、职等Id查找职位
     * @param positionName
     * @param titleId
     * @return
     */
    private Position findByNameAndTitleId(String positionName, int titleId) {
        Position p = new Position();
        p.setPositionName(positionName);
        p.setTitleId(titleId);
        return findByBean(sqlId("findByNameAndTitleId"), p);
    }

    /**
     * 判断职位名称是否正确
     * @param id
     * @param positionName
     * @return
     */
    public boolean checkName(int id, String positionName) {
        Position p = this.findByName(positionName);
        if(p == null ? true : id > 0 ? id == p.getId() : false) {
            return true;
        }
        return false;
    }

    /**
     * 根据岗位名称查找
     * @param positionName
     * @return
     */
    public Position findByName(String positionName) {
        return findByBean(sqlId("findByName"), positionName);
    }

}