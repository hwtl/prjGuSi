<div class="popTitle clearfix">
		<h1>暂停组织</h1>
		<div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_stop_org')); return false"></a></div>
	</div>
	<div class="popCon">
		<div class="form">
			<form id="formPauseOrg">
				<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew">
					<tbody>
						  <tr>
								<td class="tdTitle" width="100">暂停组织：</td>
								<td class="request" width="20"></td>
								<td>
									${org.orgName}
								</td>
							</tr>
					   <#if org.orgType =='分行'>
							<tr>
								<td class="tdTitle" width="100">改名后组织名称：</td>
								<td class="request" width="20">●</td>
								<td>
									<input type="text" class="txtNew w200" name="newOrgName" rule="required" value="${org.orgName}(${org.managerName})"/>
								</td>
							</tr>
						</#if>
						<tr>
							<td class="tdTitle"  width="100">关闭日期：</td>
							<td class="request">●</td>
							<td><input type="text" class="txtDate" name="closeDate" rule="required" value="" />
							<input type="hidden" value="${org.id}" name="orgId"/>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div><br><br>
	<div class="popBtn">
		<a href="javascript:;" class="btn-small btn-silver in_block" onclick="hidePopupDiv($('#layer_stop_org')); return false">关闭</a>
		<a href="javascript:;" class="btn-small btn-blue in_block js_btnPauseSubmit">提交</a>
	</div>
	<script type="text/javascript">
	   $("input.txtDate").date_input();
	</script>