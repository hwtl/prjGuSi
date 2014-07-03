ot.status in
    <#if status?? && status!="" && status != "999">
        (${status})
    <#else>
        (0,1)
    </#if>
and transferMode in
    <#if transferMode?? && transferMode!="" && transferMode!="999">
        (${transferMode})
    <#else>
        (1,2,3)
    </#if>
and a.company =
    <#if company?? && company !="">
        '${company}'
    <#else>
        '德佑'
    </#if>