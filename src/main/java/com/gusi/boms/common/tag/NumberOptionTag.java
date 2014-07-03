package com.gusi.boms.common.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.common.tag
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-31 10:50)
 * Description:生成下拉框的标签
 */
public class NumberOptionTag  extends SimpleTagSupport {

    private int fromIndex;
    private int toIndex;
    private int selected;
    @Override
    public void doTag() throws JspException, IOException {
        JspWriter out=getJspContext().getOut();
        for (int i = fromIndex; i <= toIndex; i++) {
            if(i == selected){
                out.write("<option value='"+i+"' selected=selected>"+i+"</option>");
            }else{
                out.write("<option value='"+i+"'>"+i+"</option>");
            }
        }
    }
    public int getFromIndex() {
        return fromIndex;
    }
    public void setFromIndex(int fromIndex) {
        this.fromIndex = fromIndex;
    }
    public int getToIndex() {
        return toIndex;
    }
    public void setToIndex(int toIndex) {
        this.toIndex = toIndex;
    }

    public int getSelected() {
        return selected;
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }
}