	<div class="popTitle clearfix">
				<h1>暂停组织</h1>
				<div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_stop_org')); return false"></a></div>
			</div>
			<div class="popCon">
				<div class="form">
					<div>
					    <p>该组织的下级组织还未暂停或未停用或有临时组织，不能暂停。</p>
                        <p>若要关闭请转移员工到其他组织，并把该组织的下级组织停用或暂停。</p>
					    <p><a href="/employee/list?keyWords=${org.orgName}">立即去处理</a></p>
					</div>
				</div>
			</div><br><br>
			<div class="popBtn">
				<a href="javascript:;" class="btnOpH24 h24Blue in_block" onclick="hidePopupDiv($('#layer_stop_org')); return false">确定</a>
			</div>
		</div>