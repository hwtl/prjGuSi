 dbo.v2_employee_final v
 INNER JOIN (
   SELECT id,userCode FROM dbo.T_OMS_EMPLOYEE_INTERVIEW_RECORDS lr WHERE interviewerChecked=0 AND status=1
 ) AS t ON v.userCode=t.userCode  
 <#if company?? && company !="">
 AND company=''${company}''
 </#if>
  LEFT JOIN T_OMS_EMPLOYEE_KEYWORDS k ON k.userCode=v.userCode