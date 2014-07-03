package com.gusi.empTransfer.tag;

import com.gusi.boms.common.Constants;
import com.gusi.empTransfer.helper.EmployeeTransferHelper;
import com.gusi.empTransfer.service.EmpTransferOrgService;
import com.dooioo.plus.oms.dyHelper.PrivilegeHelper;
import com.dooioo.plus.oms.dyUser.model.Employee;
import com.dooioo.plus.oms.dyUser.model.Privilege;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;
import java.util.HashMap;

/**
 * @Author: fdj
 * @Since: 2014-03-06 10:40
 * @Description:
 */
public class EmployeeTransferTag extends BodyTagSupport {
    private static final long serialVersionUID = -1393098525987080337L;

    /**
     * 验证模式 or
     */
    private static final String MODE_OR = "or";
    /**
     * 验证模式，and
     */
    private static final String MODE_AND = "and";
    private String url;
    private String display;
    /**
     * url验证模式，如果是and,则所有url权限都必须满足，or模式，则只需含有其中一个权限，默认or模式
     */
    private String mode = MODE_OR;

    @Override
    public int doEndTag() throws JspException {
        boolean result = false;
        Employee employee = (Employee) pageContext.getSession().getAttribute(Constants.SESSION_USER);

        ServletContext application = pageContext.getSession().getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
        EmpTransferOrgService empTransferOrgService = (EmpTransferOrgService)context.getBean("empTransferOrgService");

        //判断是否人事审批权限
        if(!url.equals(Constants.TASK_DEF_KEY_HR_APPROVE)) {
            //获取拼接组织Id后的权限（即审批权限）
            HashMap<String, Privilege> tempPrivilegeMap = EmployeeTransferHelper.getPrivilegeMapWithOrgId(employee, empTransferOrgService.queryPartOrgs(employee.getUserCode()));
            result = mode.equalsIgnoreCase(MODE_OR) ? PrivilegeHelper.checkPrivileges(tempPrivilegeMap, url) : PrivilegeHelper.checkPrivilegesMustAll(tempPrivilegeMap, url);
        } else {
            HashMap<String, Privilege> privilegeMap = employee.getPrivilegeMap();
            result = mode.equalsIgnoreCase(MODE_OR) ? PrivilegeHelper.checkPrivileges(privilegeMap, url) : PrivilegeHelper.checkPrivilegesMustAll(privilegeMap, url);
        }

        if (result) {
            if (getBodyContent() != null)
                writeToPage(getBodyContent().getString());
        } else
            writeToPage(display);
        return EVAL_PAGE;
    }

    /**
     * @param content 写入到页面的内容
     */
    private void writeToPage(String content) {
        JspWriter out = pageContext.getOut();
        if (content != null && !content.isEmpty()) {
            try {
                out.write(content);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return the display
     */
    public String getDisplay() {
        return display;
    }

    /**
     * @param display the display to set
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     * @return the mode
     */
    public String getMode() {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode) {
        this.mode = mode;
    }

}
