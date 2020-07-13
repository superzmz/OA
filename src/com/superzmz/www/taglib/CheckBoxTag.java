package com.superzmz.www.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class CheckBoxTag extends SimpleTagSupport{
//    模拟 Struts2 标签 <s:property value="%{id in privilegeIds ? 'checked' : ''}"/>

    //标签的属性均为字符串，不支持其他类型
    private Long var;
    private Long [] list;


    @Override
    public void doTag() throws JspException, IOException {
        if (var == null || list == null) {
            return;
        }

        JspWriter jspWriter = getJspContext().getOut();

        for (Long i : list) {
            if ( var == i) {
                jspWriter.write("checked");
            }
        }
    }

    public Long getVar() {
        return var;
    }

    public void setVar(Long var) {
        this.var = var;
    }

    public Long[] getList() {
        return list;
    }

    public void setList(Long[] list) {
        this.list = list;
    }
}
