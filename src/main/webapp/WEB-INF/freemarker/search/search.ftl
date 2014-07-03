<#if (roleType == 1)>
    SELECT  id AS branchCode,ORG_NAME AS branchName,0 AS pid,ORG_TYPE as orgType  FROM dbo.V_OMS_ORGANIZATION
    WHERE
    COMPANY= '${company}' AND
    ORG_NAME LIKE '${keysWord}%'  <#--##全公司（正常、暂停、停用）dooiooFull-->
    <#if (treeType =='dooiooSales')>  <#--##营运中心（正常、暂停）-->
        AND  ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1'
    <#elseif (treeType =='dooiooSalesNoDept')> <#--##营运中心不含门店（正常、暂停）-->
        AND ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1' AND ORG_TYPE != '门店'
    <#elseif (treeType =='dooiooSalesNoGroup')> <#--##营运中心不含分行（正常、暂停）-->
        AND ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1' AND ORG_TYPE != '分行' OR( ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20457 )+'%'
        OR ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20501 )+'%'
        )
    <#elseif (treeType =='dooiooSupport')> <#--##后台部门（除营运中心之外的所有部门）（正常、暂停）-->
        AND ORG_LONGCODE NOT LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1'
    <#elseif (treeType =="dooiooAll")> <#--##全公司（正常、暂停）-->
        AND STATUS != '-1'
    </#if>
<#elseif (roleType == 2)>
        select eb.userCode as branchCode, eb.userNameCn as branchName
        ,eb.orgId as pid, '' as orgType
        from T_OMS_EMPLOYEE_BASEINFOR as eb
        left join T_OMS_ORGANIZATION as oo on eb.orgId = oo.id
        where
        eb.status != '特殊账号'
        and eb.company = '${company}'
    <#if (keysWord != '')>
        and (
        eb.userCode LIKE '${keysWord}' OR eb.userNameCn LIKE '%${keysWord}%' )
    </#if>
    <#if (userStatus == 1)>
        and eb.status in ('正式','试用期')
    <#elseif (userStatus == 2)>
        and eb.status in ('离职','申请离职')
    </#if>
    <#if treeType =='dooiooSales'>  <#--##营运中心（正常、暂停）-->
        AND  oo.orgLongCode like (
            SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1'
    <#elseif (treeType =='dooiooSalesNoDept')> <#--##营运中心不含门店（正常、暂停）-->
        AND oo.orgLongCode LIKE (
            SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1' AND oo.orgType != '门店'
    <#elseif (treeType =='dooiooSalesNoGroup')> <#--##营运中心不含分行（正常、暂停）-->
        AND oo.orgLongCode LIKE (
            SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1' AND oo.orgType != '分行'
        OR ( oo.orgLongCode LIKE (SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20457 )+'%'
            OR oo.orgLongCode LIKE (SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20501 )+'%'
            )
        AND oo.status != '-1' AND  oo.orgType != '分行'
    <#elseif (treeType =='dooiooSupport')> <#--##后台部门（除营运中心之外的所有部门）（正常、暂停）-->
        AND oo.orgLongCode NOT LIKE (SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1'
    <#elseif (treeType =="dooiooAll")> <#--##全公司（正常、暂停）-->
        AND oo.status != '-1'
    </#if>

<#else>
    SELECT  id AS branchCode,ORG_NAME AS branchName,0 AS pid ,ORG_TYPE as orgType FROM dbo.V_OMS_ORGANIZATION
    WHERE
    COMPANY='${company}' AND
    ORG_NAME LIKE '${keysWord}%'  <#--##全公司（正常、暂停、停用）dooiooFull-->
    <#if (treeType =='dooiooSales')>  <#--##营运中心（正常、暂停）-->
        AND  ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1'
    <#elseif (treeType =='dooiooSalesNoDept')> <#--##营运中心不含门店（正常、暂停）-->
        AND ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1' AND ORG_TYPE != '门店'
    <#elseif (treeType =='dooiooSalesNoGroup')> <#--##营运中心不含分行（正常、暂停）-->
        AND ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1' AND ORG_TYPE != '分行' OR( ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20457 )+'%'
        OR ORG_LONGCODE LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20501 )+'%'
        )
    <#elseif (treeType =='dooiooSupport')> <#--##后台部门（除营运中心之外的所有部门）（正常、暂停）-->
        AND ORG_LONGCODE NOT LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND STATUS != '-1'
    <#elseif (treeType =="dooiooAll")> <#--##全公司（正常、暂停）-->
        AND STATUS != '-1'
    </#if>

    UNION ALL

    select eb.userCode as branchCode, eb.userNameCn as branchName
    ,eb.orgId as pid, '' as orgType
    from T_OMS_EMPLOYEE_BASEINFOR as eb
    left join T_OMS_ORGANIZATION as oo on eb.orgId = oo.id
    where
    eb.status != '特殊账号'
    <#if (keysWord != '')>
        and (
        eb.userCode LIKE '${keysWord}' OR eb.userNameCn LIKE '%${keysWord}%' )
    </#if>
    <#if (userStatus == 1)>
        and eb.status in ('正式','试用期')
    <#elseif (userStatus == 2)>
        and eb.status in ('离职','申请离职')
    </#if>
    <#if treeType =='dooiooSales'>  <#--##营运中心（正常、暂停）-->
        AND  oo.orgLongCode like (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1'
    <#elseif (treeType =='dooiooSalesNoDept')> <#--##营运中心不含门店（正常、暂停）-->
        AND oo.orgLongCode LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1' AND oo.orgType != '门店'
    <#elseif (treeType =='dooiooSalesNoGroup')> <#--##营运中心不含分行（正常、暂停）-->
        AND oo.orgLongCode LIKE (
        SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1' AND oo.orgType != '分行'
        OR ( oo.orgLongCode LIKE (SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20457 )+'%'
        OR oo.orgLongCode LIKE (SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20501 )+'%'
        )
        AND oo.status != '-1' AND  oo.orgType != '分行'
    <#elseif (treeType =='dooiooSupport')> <#--##后台部门（除营运中心之外的所有部门）（正常、暂停）-->
        AND oo.orgLongCode NOT LIKE (SELECT ORG_LONGCODE FROM dbo.V_OMS_ORGANIZATION WHERE ID = 20495 )+'%'
        AND oo.status != '-1'
    <#elseif (treeType =="dooiooAll")> <#--##全公司（正常、暂停）-->
        AND oo.status != '-1'
    </#if>

</#if>

