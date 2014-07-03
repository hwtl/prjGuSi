package com.gusi.init;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @Description: spring绑定时处理函数
 * @author Jail    E -Mail:86455@ dooioo.com 
 */
public class DefaultBindingInitializer implements org.springframework.web.bind.support.WebBindingInitializer {
	@Override
    public void initBinder(WebDataBinder webDataBinder, WebRequest webRequest) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
		webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(false));
    }
}
  
    