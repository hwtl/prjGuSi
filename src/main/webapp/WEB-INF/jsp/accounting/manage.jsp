<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
	<form:form action="/accounting/saveCustomerRelation" modelAttribute="customer" method="post" >
        <webplus:token />
			<div class="popCon ml_20 mr_20">
				<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
					<tbody>
						<tr>
							<td class="tdTitle" width="100">核算部门编号：</td>
							<td width="200">${customer.customer}</td>
							<form:hidden path="customer"/>
							<form:hidden path="id"/>
						</tr>
						<tr>
							<td class="tdTitle" width="100">核算部门名称：</td>
							<td width="300">${customer.orgName}</td>
						</tr>
						<tr>
							<td class="tdTitle" width="100">请选择金蝶客户名称：</td>
							<td width="300">
								<form:input autocomplete="off" path="remark" popdiv="autoComp_keyword"  id="customer_remark"
									 class="txt tt160"/>
									<p class="red bold mt_5" id="warnInfo"></p>
								 <div id="autoComp_keyword" class="autoComplate"></div>
							</td>
						</tr>
						<tr>
							<td class="tdTitle" width="100">金蝶客户编码：</td>
							<td width="300">
							  <input name="customerEASCode" value="${customer.customerEASCode}" id="customerEASCode" class="grey999" readonly="readonly"/>
						</tr>
					</tbody>
				</table>
	</div>
	<div class="popBtn mt_10">
		<a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#accountingManageDiv')); return false;">关闭</a>
		<a href="#" class="btnOpH24 h24Blue in_block js_submit_accounting" >提交</a>
	</div>
 </form:form>

 <script type="text/javascript">
$(function(){
    $('#customer_remark').autocomplete({
		urlOrData: '/accounting/findEASCustomers',
		itemTextKey: 'remark',
		dataKey: 'customers',
		setRequestParams: function(requstParams){
			requstParams.remark = $.trim($('#customer_remark').val());
		},
		selectCallback: function(selectedItem){		//选中选项， 执行回调
			$("#customerEASCode").val(selectedItem.customerEASCode);
			$.getJSON("/accounting/existRelation/"+selectedItem.customerEASCode,
					function(data){
				 if (data.status && data.status=='ok' && data.customers !=undefined 
						 &&data.customers!=null) {
					 
					var resultHtmlArray = $.map(data.customers, function(customer){
						return "<p>警告：客户"+customer.orgName+"&nbsp;("+customer.customer+")&nbsp;已对应金蝶客户 &nbsp;"
						+customer.remark+"("+customer.customerEASCode+"),请确认。</p>";
					});
					
					$("#warnInfo").html(resultHtmlArray.join(''));
				}
			});
		}
	});
});
</script>