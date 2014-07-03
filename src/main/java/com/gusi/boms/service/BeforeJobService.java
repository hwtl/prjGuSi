package com.gusi.boms.service;

import java.util.Date;

import com.gusi.boms.model.BeforeJobInfo;
import org.springframework.stereotype.Service;

import com.dooioo.web.service.BaseService;

@Service
public class BeforeJobService extends BaseService<BeforeJobInfo> {

	/**
	  * @since: 2.7.4 
	  * @param userCode
	  * @param predictRegisterDate
	  * <p>
	  * 新增信息
	  * </p>   
	 */
	public void addInfo(int userCode,Date predictRegisterDate){
        BeforeJobInfo info=new BeforeJobInfo(userCode, predictRegisterDate);		
		this.insert(info);
	}
}
