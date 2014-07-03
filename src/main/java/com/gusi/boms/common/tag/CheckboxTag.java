package com.gusi.boms.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.common.tag
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-31 14:49)
 * Description: To change this template use File | Settings | File Templates.
 */
public class CheckboxTag extends SimpleTagSupport {
    private String checkName;       //checkbox 的name名称
    private String newValue;        //checkbox 的值
    private String checkValue="";      //checkbox 选择的值
    private String checkText;       //checkbox 显示值
    private boolean disabled;       //checkbox 是否可编辑
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out=getJspContext().getOut();
        for(String str : checkValue.split(",")){
            if(str.trim().equals(newValue.trim())){
               out.write("<label class=\"checkboxLabel chked\">\n" +
                       "<input"+ (disabled ? " disabled=\"true\" " :"")+ " type=\"checkbox\" checked=\"checked\"  name="+checkName+" value="+newValue+">"+checkText+"</label>");
                return;
            }
        }
        out.write("<label class=\"checkboxLabel\">\n" +
                "<input"+(disabled ? " disabled=\"true\" " :"")+" type=\"checkbox\"   name="+checkName+" value="+newValue+">"+checkText+"</label>");
    }

    public String getCheckName() {
        return checkName;
    }

    public void setCheckName(String checkName) {
        this.checkName = checkName;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public String getCheckText() {
        return checkText;
    }

    public void setCheckText(String checkText) {
        this.checkText = checkText;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
