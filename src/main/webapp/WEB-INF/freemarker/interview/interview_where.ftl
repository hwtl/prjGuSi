1=1
<#if keywords?? && keywords !="">
	and k.keywords like ''%${keywords}%''
</#if>