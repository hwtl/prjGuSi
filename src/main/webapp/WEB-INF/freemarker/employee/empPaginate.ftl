<#--list paginate.pageList as list>
${list }
</#list-->
<div class="pageNew txtRight mt_10">
<#assign fPage = 9 />
<#if paginate.firstPage>
    <a href="javascript:void(0);" class="in_block btn prev"></a>
<#else>
    <#--<a href="${url ? replace('<1>', paginate.prePage) }" class="in_block btn prev"></a>-->
    <a href="javascript:void(0);" pageNo="${paginate.prePage}" class="in_block btn prev js_fenye"></a>
</#if>

<#list 4..1 as page>
    <#if paginate.pageNo - page &gt; 0>
        <#assign fPage = fPage - 1 />
        <#--<a href="${url ? replace('<1>', paginate.pageNo - page) }" class="in_block">${paginate.pageNo - page}</a>-->
        <a href="javascript:void(0);" pageNo="${paginate.pageNo - page}" class="in_block js_fenye">${paginate.pageNo - page}</a>
    </#if>
</#list>
<#if paginate.totalPage &gt; 0>
    <#if paginate.pageNo == 1 >
        <b class="in_block">1</b>
    <#else>
        <b class="in_block">${paginate.pageNo}</b>
    </#if>
</#if>

<#list 1..fPage as page>
    <#if paginate.pageNo + page lte paginate.totalPage>
        <#--<a href="${url ? replace('<1>', paginate.pageNo + page) }" class="in_block">${paginate.pageNo + page}</a>-->
        <a href="javascript:void(0);" pageNo="${paginate.pageNo + page}" class="in_block js_fenye">${paginate.pageNo + page}</a>
    </#if>
</#list>
<#if paginate.lastPage>
    <a href="javascript:void(0);" class="in_block btn next"></a>
<#else>
    <#--<a href="${url ? replace('<1>', paginate.nextPage) }" class="in_block btn next"></a>-->
    <a href="javascript:void(0);" pageNo="${paginate.nextPage}" class="in_block btn next js_fenye"></a>
</#if>

共${paginate.totalPage}页&nbsp; 到第<input size='3' id="pageNo" value="${paginate.pageNo }"/>页
<input type="button" value="确定" onclick="changePage();"/>

    <script>

        function changePage(pageNo){
            var totalPage = ${paginate.totalPage};
            pageNo = pageNo == null ? $("#pageNo").val() : pageNo;

            if(!pageNo.match(/^\d+$/)){
                pageNo = 1;
            }else{
                if(pageNo>totalPage){
                    pageNo = totalPage;
                }
                if(pageNo<1 ){
                    pageNo = 1;
                }
            }
            submitForm(pageNo);
        }

        /**
         * 点击分页的tal
         */
        $(".js_fenye").live("click", function(){
            submitForm($(this).attr("pageNo"));
        })
    </script>

</div>