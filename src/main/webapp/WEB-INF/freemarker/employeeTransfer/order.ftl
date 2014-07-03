CASE WHEN (operateUrl IN(${privileges})) THEN 0 ELSE 1 END
, case approvalStatus
<#--交接-->
when 2 then 10
<#--人事-->
when 5 then 20
<#--审批-->
when 1 then 30
<#--打回-->
when 4 then 40
<#--完成-->
when 3 then 50
<#--作废-->
when -1 then 60
else 100 end
, createTime desc