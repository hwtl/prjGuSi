<input type="hidden" id="operateType" value="orgBaseDetails">
                <#if (organization?? && organization.parentId == 0)>
                <#else>
                    <tr>
                        <td class="tdTitle">上级组织：</td>
                        <td class="request">●</td>
                        <td width="200" class="bdR">${organization.parentName!""}</td>
                        <input type="hidden" value="${organization.parentName!""}" class="txtNew w200" id="parentOrgName"  rule="required"/>
                        <input type="hidden" value="${organization.parentId!"0"}" id="parentId" name="parentId">
                        <td colspan="3">&nbsp;</td>
                    </tr>
                </#if>
				<tr>
					<td class="tdTitle" width="140">组织名称：</td>
					<td class="request" width="20">●</td>
					<td width="200" class="bdR">${organization.orgName!""}</td>
                    <input type="hidden" value="${organization.orgName!""}" class="txtNew w200" id="orgName" name="orgName" rule="required">
                    <input type="hidden" id="id" name="id" value="${organization.id!"0"}">
					<td class="tdTitle" width="140">组织英文名称：</td>
					<td  width="20">${organization.eOrgName!""}</td>
					<td width="360">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitle">类型：</td>
					<td class="request">●</td>
					<td class="bdR">${organization.orgType!""}</td>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitle">生效日期：</td>
					<td class="request">●</td>
					<td class="bdR"><#if (organization.openDate?exists )>${organization.openDate?string("yyyy-MM-dd")}<#else ></#if></td>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitle">属性：</td>
					<td class="request">●</td>
					<td class="bdR">${organization.orgClass!""}</td>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitle">组织负责人：</td>
					<td class="request" width="20">&nbsp;</td>
					<td width="200">${organization.manager!""}</td>
                    <input type="hidden" value="${organization.managerName!""}" class="txtNew w200" id="managerName" name="managerName" />
                    <input type="hidden" value="${organization.manager!"0"}" id="manager" name="manager">
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitle">组织描述：</td>
					<td class="request" width="20">&nbsp;</td>
					<td colspan="4">${organization.remark!""}</td>
				</tr>
				<tr>
					<td class="tdTitle">办公地点：</td>
					<td class="request" width="20">&nbsp;</td>
					<td colspan="4">${organization.address!""}</td>
				</tr>

