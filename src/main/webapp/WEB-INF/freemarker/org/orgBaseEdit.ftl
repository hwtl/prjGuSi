<input type="hidden" id="operateType" value="orgBaseEdit">
    <#if (organization.parentId > 0)>
        <tr>
            <td class="tdTitle">上级组织：</td>
            <#if (isAdd || canEdit)>
	            <td class="request">●</td>
	            <td width="200" class="bdR">
	                <input autocomplete="off" type="text" value="${organization.parentName!""}" class="txtNew w200" id="parentOrgName" popdiv="parentOrgName_autoComp" name="parentOrgName" rule="required"/>
	                <input type="hidden" value="${organization.parentId!"0"}" id="parentId" name="parentId">
	                <div id="parentOrgName_autoComp" class="autoComplate"></div>
	            </td>
            <#else>
              <td class="request">&nbsp;</td>
	            <td width="200" class="bdR">
	            	${organization.parentName!""}
	                <input type="hidden" value="${organization.parentName!""}" id="parentOrgName" name="parentOrgName"/>
	                <input type="hidden" value="${organization.parentId!"0"}" id="parentId" name="parentId">
	            </td>
            </#if>
            <td colspan="3">&nbsp;</td>
        </tr>
    <#else>
        <input type="hidden" value="${organization.parentId!"0"}" id="parentId" name="parentId">
    </#if>
    <tr>
        <td class="tdTitle" width="140">组织名称：</td>
        <#if (isAdd || canEdit)>
	        <td class="request" width="20">●</td>
	        <td width="200" class="bdR">
	            <input type="text" value="${organization.orgName!""}" class="txtNew w200" id="orgName" name="orgName" rule="required">
	            <input type="hidden" id="id" name="id" value="${organization.id!"0"}">
	        </td>
          <#else>
          	 <td class="request" width="20">&nbsp;</td>
	         <td width="200" class="bdR">
	           	${organization.orgName!""}
	            <input type="hidden" value="${organization.orgName!""}" id="orgName" name="orgName">
	            <input type="hidden" id="id" name="id" value="${organization.id!"0"}">
	        </td>
          </#if>
        <td class="tdTitle" width="140">组织英文名称：</td>
        <td class="request" width="20">&nbsp;</td>
        <td width="360"><input type="text" value="${organization.eOrgName!""}" class="txtNew mr_20" name="eOrgName" id="eOrgName">
            <!--<label><input type="checkbox" />虚拟组织</label> -->
        </td>
    </tr>
    <tr>
        <td class="tdTitle">类型：</td>
        <#if (isAdd || canEdit)>
	        <td class="request">●</td>
	        <td class="bdR">
            <select id="orgType" class="txt w200" name="orgType" rule="required">
                <option value="">请选择</option>
                <option value="分行" <#if ((organization.orgType!"") == '分行')>selected="true"<#else></#if>">分行</option>
                <option value="门店" <#if ((organization.orgType!"") == '门店')>selected="true"<#else></#if>">门店</option>
                <option value="区域" <#if ((organization.orgType!"") == '区域')>selected="true"<#else></#if>">区域</option>
                <option value="部门" <#if ((organization.orgType!"") == '部门')>selected="true"<#else></#if>">部门</option>
                <option value="公司" <#if ((organization.orgType!"") == '公司')>selected="true"<#else></#if>">公司</option>
            </select>
           <#else>
	            <td class="request">&nbsp;</td>
		        <td class="bdR">
		       	 ${organization.orgType}
		       	  <input type="hidden" id="orgType" name="orgType" value="${organization.orgType}"/>
		        </td>
           </#if>
        </td>

    <#if ((organization.orgType!"") == '门店')>
        <td class="tdTitle js_orgMaxCount" width="140">组数：</td>
        <td class="request js_orgMaxCount" width="20">&nbsp;</td>
        <td class="js_orgMaxCount" width="360"><input <#if !isAdd && !canEditOrgCount>disabled="true" </#if> type="text" value="${orgMaxCount!""}" class="txtNew mr_20" name="orgMaxCount" id="orgMaxCount" rule="integer" maxlength="3">
        </td>
    <#else>
        <td class="tdTitle js_orgMaxCount hideit" width="140">组数：</td>
        <td class="request js_orgMaxCount hideit" width="20">&nbsp;</td>
        <td class="js_orgMaxCount hideit" width="360"><input <#if !isAdd && !canEditOrgCount>disabled="true"</#if> type="text" value="${orgMaxCount!""}" class="txtNew mr_20" name="orgMaxCount" id="orgMaxCount" rule="integer" maxlength="3">
        </td>
    </#if>


    </tr>
    <tr>
        <td class="tdTitle">开组日期：</td>
        <td class="request">●</td>
        <td class="bdR">
            <input type="text" class="txtDate" value="<#if (organization.openDate?exists )>${organization.openDate?string("yyyy-MM-dd")}<#else ></#if>" readonly="readonly" id="openDate" name="openDate" rule="required">
        </td>
        <td colspan="3">&nbsp;</td>
    </tr>
    <tr>
        <td class="tdTitle">属性：</td>
        <td class="request">●</td>
        <td class="bdR">
            <select class="txt w200" id="orgClass" name="orgClass" rule="required">
                <option value="">请选择</option>
                <option value="前台" <#if ((organization.orgClass!"") == '前台')>selected="true"<#else></#if>">前台</option>
                <option value="后台" <#if ((organization.orgClass!"") == '后台')>selected="true"<#else></#if>">后台</option>
                <option value="集团" <#if ((organization.orgClass!"") == '集团')>selected="true"<#else></#if>">集团</option>
            </select>
        </td>
        <td colspan="3">&nbsp;</td>
    </tr>
    <tr>
        <td class="tdTitle">组织负责人：</td>
        <td class="request" width="20">&nbsp;</td>
        <td width="200">
            <input autocomplete="off" type="text" value="${organization.managerName!""}" class="txtNew w200" id="managerName" name="managerName" popdiv="manager_autoComp" placeholder="请输入员工姓名" onchange="removeManager()" />
            <input type="hidden" value="${organization.manager!"0"}" id="manager" name="manager">
            <div id="manager_autoComp" class="autoComplate"></div>
        </td>
        <td colspan="3">&nbsp;</td>
    </tr>
    <tr>
        <td class="tdTitle">组织描述：</td>
        <td class="request" width="20">&nbsp;</td>
        <td colspan="4"><textarea id="remark" name="remark" class="tt450">${organization.remark!""}</textarea></td>
    </tr>
    <tr>
        <td class="tdTitle">办公地点：</td>
        <td class="request" width="20">&nbsp;</td>
        <td colspan="4"><input value="${organization.address!""}" type="text" id="address" name="address" class="txtNew tt450"></td>
    </tr>

