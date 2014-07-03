company=''${employee.company}'' and status = 1

<#if empTransferSearch.ydType?? && empTransferSearch.ydType !="">
    and ydType = ''${empTransferSearch.ydType}''
</#if>
<#if empTransferSearch.newOrgId?? && empTransferSearch.newOrgId !="">
    and newOrgLongCode like (select o.orgLongCode from T_OMS_ORGANIZATION o WITH(NOLOCK) where id =  ${empTransferSearch.newOrgId})  + ''%''
</#if>
<#if empTransferSearch.createStartTime?? && empTransferSearch.createStartTime?string("yyyy-MM-dd") !="">
    and createTime >= ''${empTransferSearch.createStartTime?string("yyyy-MM-dd")}''
</#if>
<#if empTransferSearch.createEndTime?? && empTransferSearch.createEndTime?string("yyyy-MM-dd") !="">
    and createTime <=  dateadd(day,1, ''${empTransferSearch.createEndTime?string("yyyy-MM-dd")}'')
</#if>
<#if empTransferSearch.approvalStatus?? && empTransferSearch.approvalStatus !="">
    <#if empTransferSearch.approvalStatus == 1>
        and approvalStatus in (1,5)
    <#else>
        and approvalStatus = ''${empTransferSearch.approvalStatus}''
    </#if>
</#if>
<#if empTransferSearch.keyword?? && empTransferSearch.keyword !="">
    and (
            creator like ''${empTransferSearch.keyword}%''
            or creatorName like ''%${empTransferSearch.keyword}%''
            or userCode like ''%${empTransferSearch.keyword}%''
            or userName like ''%${empTransferSearch.keyword}%''
            or transferNo like ''%${empTransferSearch.keyword}%''
        )
</#if>
<#if scope == 1 || scope == 10 || scope == 50>
    and (
            creator = ${employee.userCode}
            or handlerName like ''%${employee.userCode}%''
            or exists (
                SELECT po.id from dbo.T_OMS_ORGANIZATION po WITH(NOLOCK)
                inner join
                (SELECT o.orgLongCode FROM dbo.T_OMS_EMPLOYEE_POSITION e WITH(NOLOCK)
                left JOIN dbo.T_OMS_ORGANIZATION o  WITH(NOLOCK) ON e.orgId = o.id
                WHERE e.userCode = ${employee.userCode}) as epo on  po.orgLongCode LIKE epo.orgLongCode+''%''
                WHERE
                    po.id = newOrgId or po.id = oldOrgId
                )
        )
</#if>
