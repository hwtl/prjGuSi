T_OMS_ORGANIZATION_TRANSFER as ot
left join T_OMS_ORGANIZATION as a on a.id = ot.orgAParentId
left join T_OMS_ORGANIZATION as b on b.id = ot.orgBParentId