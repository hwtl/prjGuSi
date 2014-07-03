<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="核算部门列表"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>
<div class="container left p100">
		<div class="boxWrapBlue">
			<div class="boxWrapFilter2">
			   <form:form modelAttribute="customer" action="/accounting" method="get" id="accountingSearchForm">
					<p class="js_searchBox">
						<span class="ml_10">核算部门名称：</span>
						 <form:input path="orgName" cssClass="txt mr_15"/>
	                    <span class="ml_10">金蝶客户名称：</span>
	                    <form:input path="remark" cssClass="txt mr_15"/>
	                     <form:checkbox path="relation" value="1" id="relation"/><label for="relation">无对应关系</label>
	                    <a href="javascript:;" class="btnOpH24 h24Blue in_block js_search" onclick="$('#accountingSearchForm').submit();">搜索</a>
					</p>
			   </form:form>
			</div>
		</div>
	<p class="mt_10 mb_10">
		<span class="red bold">${paginate.totalCount}</span>条记录
	</p>
    <table width="100%" cellspacing="0" class="tableListNew tableNarrow mt_10">
        <tbody>
       			 <tr class="trHead txtRight">
					<td align="left" width="200">核算部门编码</td>
					<td align="left" width="300">核算部门名称</td>
					<td align="left" width="200">金蝶客户编码</td>
					<td align="left" width="300">金蝶客户名称</td>
					<td align="left" width="200">创建日期</td>
					<td align="left" width="100">操作</td>
				</tr>
                <c:forEach var="customer" items="${paginate.pageList}">
                    <tr>
                        <td align="left" >${customer.customer}</td>
                        <td align="left" >${customer.orgName}</td>
                        <td align="left" >${customer.customerEASCode}</td>
                        <td align="left">${customer.remark}</td>
                        <td align="left" >
                          <fmt:formatDate value="${customer.createAt}" pattern="yyyy-MM-dd"/>
                        </td>
                        <td align="left">
                            <appel:checkPrivilege url="oms_manage_accountingDept">
                                        <a href="#" customer="${customer.customer}"  class="btnOpH24 h24Silver in_block ml_5 js_manage">维护</a>
							</appel:checkPrivilege>
                        </td>
                    </tr>
                </c:forEach>
			</tbody>
		</table>
		<!--分页 -->
        <app:paginate/>
		<!--分页 -->
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="js" value="portal"/>
    <jsp:param name="duiJs" value="autocomplete"/>
</jsp:include>
<div class="popLayer w650" id="accountingManageDiv">
	<div class="popTitle clearfix">
			  <h1>对应关系维护</h1>
				<div class="cls">
					<a href="#" class="xx"></a>
				</div>
	</div>
	<div id="formSection">

	</div>
</div>
<script type="text/javascript">
    $(function(){
    	 $("a.js_manage").click(function(){
    		  var code=$(this).attr("customer");
    		 $.post("/accounting/"+code+"/relationDetail",function(page)
 					{
 						$("#formSection").html(page);
 						showPopupDiv($('#accountingManageDiv'));
 						return false;
 			 });
 	    });
       $(".xx").click(function(){
    	        hidePopupDiv($(parent.document.body).find('#accountingManageDiv')); return false;
       });

     $(parent.document.body).find("a.js_submit_accounting").live("click", function(e){
    			 e.preventDefault();
   				 var formEl=$(this).parents("form");
   				 var posturl=formEl.attr("action");
    			 var cName=formEl.find("input[name=remark]").val();
    			 if (cName==null || cName=="") {
					alert("请选择金蝶客户");
				    return false;
    			  }
    			  var code=formEl.find("input[name=customerEASCode]").val();
    			 if (code==null || code=="") {
					alert("请选择客户。");
					return false;
				}
   				ajaxPost({
   					url:posturl,
   					data:formEl.serialize(),
   					ok:function(data){
   						hidePopupDiv($(parent.document.body).find('#accountingManageDiv'));
   						window.location.reload();
   					},
   					fail:function(data){
   	                    hidePopupDiv($(parent.document.body).find('#accountingManageDiv'));
   						alert(data.message);
   					}
   				});
    			return false;
    		});
    });

</script>