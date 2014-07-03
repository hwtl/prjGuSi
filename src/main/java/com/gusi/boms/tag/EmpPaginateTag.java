package com.gusi.boms.tag;

import com.dooioo.commons.Strings;
import com.dooioo.plus.util.FreemarkerUtil;
import com.dooioo.web.common.Paginate;
import freemarker.template.TemplateException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fdj
 * @Since: 2014-05-19 10:54
 * @Description: EmpPaginateTag
 */
public class EmpPaginateTag extends BodyTagSupport {
    private FreemarkerUtil freemarkerUtil;

    private String nextLabel;
    private String previousLabel;
    private String template;

    private JspWriter out;
    private String url;

    public void setTemplate(String template) {
        this.template = template;
    }

    @Override
    public int doEndTag() {
        initData();
        Paginate paginate = (Paginate) pageContext.findAttribute("paginate");
        adjustPageNo(paginate);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("paginate", paginate);
        map.put("nextLabel", nextLabel);
        map.put("previousLabel", previousLabel);
        map.put("url", url);

        if(Strings.isEmpty(template)) {
            template = "/employee/empPaginate";
        }
        try {
            String rs = freemarkerUtil.writeTemplate(template + ".ftl", map);
            out.print(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    private void initData(){
        ServletContext application = pageContext.getSession().getServletContext();
        ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(application);
        freemarkerUtil = (FreemarkerUtil)context.getBean("freemarkerUtil");
        nextLabel = "上一页";
        previousLabel = "下一页";
        out = pageContext.getOut();
        url = getURL();
    }

    private String getURL(){
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String baseUrl = (String)request.getAttribute("javax.servlet.forward.request_uri");
        if(baseUrl == null) baseUrl = (String) request.getRequestURI();
        String q = getQueryString(request);
        return baseUrl + q;
    }

    private String getQueryString(HttpServletRequest request){
        StringBuffer q = new StringBuffer();
        Map<String, String[]>params = request.getParameterMap();
        q.append("?");
        for(String key : params.keySet()){
            if(key.equalsIgnoreCase("pageNo")){
                continue;
            }
            String [] values = params.get(key);
            for(int i = 0; i< values.length; i++) {
                q.append(key);
                q.append("=");
                q.append(Strings.encodeUTF(values[i]));
                q.append("&");
            }
        }
        q.append("pageNo=<1>");
        return q.toString();
    }

    /**
     * 设置分页参数
     * @param paginate
     */
    private void adjustPageNo(Paginate paginate){
        if(paginate.getPageNo() > paginate.getTotalPage()){
            paginate.setPageNo(paginate.getTotalPage());
        }
        if(paginate.getPageNo() <= 0){
            paginate.setPageNo(1);
        }
    }
}
