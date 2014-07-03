
SELECT top 1 o.id, o.orgLongCode,o.orgName
,ISNULL((SELECT SUM(maxCount) FROM dbo.T_OMS_ORGNIZATION_EMPLOYEE_COUNT ec WITH(NOLOCK)
    WHERE ec.STATUS = 1 AND ec.orgId = o.id
    GROUP BY ec.orgId),0) AS maxCount
,CASE
    <#--中介部-->
    WHEN (o.orgLongCode LIKE (SELECT orgLongCode FROM dbo.T_OMS_ORGANIZATION WITH(NOLOCK) WHERE id = 22) + '%') THEN 10
    <#--新房销售部-->
    WHEN (o.orgLongCode LIKE (SELECT orgLongCode FROM dbo.T_OMS_ORGANIZATION WITH(NOLOCK) WHERE id = 21278) + '%') THEN 11
    ELSE 0
    END AS branchRequireEmpNo
FROM dbo.T_OMS_ORGANIZATION o WITH(NOLOCK)
WHERE o.orgType = '区域' AND o.status = 1 AND o.company = '德佑'
AND (SELECT orgLongCode FROM dbo.T_OMS_ORGANIZATION WITH(NOLOCK) WHERE id = ${id}) LIKE o.orgLongCode + '%'
