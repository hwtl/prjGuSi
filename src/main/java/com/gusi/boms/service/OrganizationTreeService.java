package com.gusi.boms.service;

import com.gusi.boms.common.Configuration;
import com.gusi.boms.model.Organization;
import com.gusi.boms.model.OrganizationTree;
import com.gusi.boms.util.TreePathUtils;
import com.dooioo.plus.oms.dyEnums.Company;
import com.dooioo.plus.oms.dyHelper.StringBuilderHelper;
import com.dooioo.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Title:  com.dooioo.boms.service
 * Author: Jerry.hu
 * Create: Jerry.hu (2013-05-14 15:30)
 * Description: To change this template use File | Settings | File Templates.
 */
@Service
public class OrganizationTreeService  extends BaseService<OrganizationTree> {
	
	@Autowired
	private Organization2Service orgService;

    /**
     * 判断一段时间内组织是否有更新
     * @param counter
     * @return
     */
    public boolean isHasUpdateCount(int counter,Company company) {
        Map<String,Object> paramMap = new HashMap<String, Object>();
        paramMap.put("minute",counter * Configuration.getInstance().getIntervalTime());
        paramMap.put("company",company.toString());
        return this.count(sqlId("isHasupdateCount"),paramMap) > 0;
    }

    /**
     * 全公司的树
     * @param company 公司枚举
     * @param orgLevel 组织层级
     * @param fileName 文件名
     * @return String
     */
    public String  generateTree(Company company,int orgLevel,String fileName){
        Organization root = orgService.getRoot(company.toString());
        List<Organization> orgs = orgService.queryOrgTree(company.toString());
        return TreePathUtils.getFilePrefix(fileName) + generateOrgTree(root, orgs, orgLevel);
    }

    /**
     * 销售型的树（营运中心）
     * @param company 公司枚举
     * @param orgLevel 组织层级
     * @param fileName 文件名
     * @return String
     */
    public String generateSalesOrgTree(Company company,int orgLevel,String fileName){
        Organization root = orgService.getSalesRoot(Organization.SalesOrgId);
        List<Organization> orgs = orgService.querySales(company.toString());
        return TreePathUtils.getFilePrefix(fileName) + generateOrgTree(root, orgs, orgLevel);
    }

    /**
     * 销售型树 (营运中心不含门店)
     * @param company 公司枚举
     * @param orgLevel 组织层级
     * @param fileName 文件名
     * @return String
     */
    public String generateSalesWithoutStoreOrgTree(Company company,int orgLevel,String fileName){
        Organization root = orgService.getSalesRoot(Organization.SalesOrgId);
        List<Organization> orgs = orgService.querySalesWithoutStore(company.toString());
        System.out.println(orgs);
        return TreePathUtils.getFilePrefix(fileName) + generateOrgTree(root, orgs, orgLevel);
    }

    /**
     * 销售型树(营运中心不含分行)
     * @param company 公司枚举
     * @param orgLevel 组织层级
     * @param fileName 文件名
     * @return String
     */
    public String generateSalesWithoutBranchOrgTree(Company company,int orgLevel,String fileName){
        Organization root = orgService.getSalesRoot(Organization.SalesOrgId);
        List<Organization> orgs = orgService.querySalesWithoutBranch(company.toString());
        return TreePathUtils.getFilePrefix(fileName) + generateOrgTree(root, orgs, orgLevel);
    }

    /**
     * 后台组织架构树(营运中心除外)
     * @param company 公司枚举
     * @param orgLevel 组织层级
     * @param fileName 文件名
     * @return String
     */
    public String generateSupportOrgTree(Company company,int orgLevel,String fileName){
        Organization root = orgService.getRoot(company.toString());
        List<Organization> orgs = orgService.querySupport(company.toString());
        return TreePathUtils.getFilePrefix(fileName) + generateOrgTree(root, orgs, orgLevel);
    }


    private String generateOrgTree(Organization org, List<Organization> orgs, int  addOrgLevel){
    	StringBuilder json = new StringBuilder();
		json.append("{'text':'").append(org.getOrgName())
		.append("','id':'").append(org.getId())
		.append("','type':'").append(org.getOrgType())
		.append("','status':'").append(org.getStatus())
		.append("','orgClass':'").append(org.getOrgClass())
		.append("'");
		if(org.hasSonTree(orgs, addOrgLevel)){
				json.append(",'children':[").append(generateSonTree(org, orgs, addOrgLevel)).append("]");
		}
		json.append("}");
		return json.toString();
	}
    
    private String generateSonTree(Organization org,List<Organization> orgs,int addOrgLevel){
		StringBuilder json = new StringBuilder();
		for(Organization o : orgs){
			if(o.isSonOf(org,addOrgLevel)){
				json.append(generateOrgTree(o, orgs,addOrgLevel)).append(",");
			}
		}
		return StringBuilderHelper.trimLast(json, ",").toString();
	}
    
}
