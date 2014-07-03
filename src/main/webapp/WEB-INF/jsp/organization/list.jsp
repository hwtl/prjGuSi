<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="组织列表-组织管理"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>
<div class="container left p100">
		<div class="boxWrapBlue">
			<div class="boxWrapFilter2">
				<p class="js_searchBox">
					<span class="ml_10">组织名称：</span>
                    <input id="orgName" type="text" class="txt mr_15" value="${param.orgName}"/>
					<span class="ml_10">类型：</span>
					<select id="orgType">
						<option value="" ${param.orgType == '' ? 'selected=true' : ''}>请选择类型</option>
                        <option value="分行" ${param.orgType == '分行' ? 'selected=true' : ''}>分行</option>
                        <option value="门店" ${param.orgType == '门店' ? 'selected=true' : ''}>门店</option>
                        <option value="区域" ${param.orgType == '区域' ? 'selected=true' : ''}>区域</option>
                        <option value="部门" ${param.orgType == '部门' ? 'selected=true' : ''}>部门</option>
                    </select>
					<span class="ml_10">状态：</span>
					<select id="status">
						<option value="-9" ${param.status == -9 ? 'selected=true' : ''} >请选择状态</option>
                        <option value="1" ${param.status == 1 ? 'selected=true' : ''}>启用</option>
                        <option value="0" ${param.status == 0 ? 'selected=true' : ''}>暂停</option>
                        <option value="2" ${param.status == 2 ? 'selected=true' : ''}>临时</option>
                        <option value="-1" ${param.status == -1 ? 'selected=true' : ''}>停用</option>
                    </select>
						<input type="checkbox" id="manager" name="manager" ${param.manager != null && param.manager == 1 ? 'checked=true' : ''} /><label for="manager">暂缺负责人组织</label>
                    <a href="javascript:;" class="btnOpH24 h24Blue in_block js_search">搜索</a>
                    <a href="/organization/list" class="btnOpH24 h24Silver in_block ml_5 js_reset">重置</a>
				</p>
			</div>
		</div>
    <p class="mt_10 clearfix">
        <span class="grey999 left mt_5"><b class="red">${paginate.totalCount}</b>&nbsp;符合要求的组织</span>
        <appel:checkPrivilege url="oms_om_org_add">
            <a href="/organization/add" target="_blank" class="btnOpH24 h24Green in_block js_hrefs right">新增组织</a>
        </appel:checkPrivilege>
    </p>
    <table width="100%" cellspacing="0" class="tableListNew tableNarrow mt_10">
        <tbody>
        <tr class="trHead txtRight">
					<td align="left" width="100">组织名称</td>
					<td align="left" nowrap>负责人</td>
					<td align="center" nowrap>类型</td>
					<td align="center" nowrap>
                        <c:choose>
                            <c:when test="${empty param.orderBy}">
                                <a href="#" id="order_by_employeeCount" orderBy="employeeNo_desc" class="listOrder ">
                                    直属在职人数
                                </a>
                            </c:when>
                            <c:otherwise>
                                <a href="#" id="order_by_employeeCount" orderBy="${param.orderBy == 'employeeNo_desc' ? 'employeeNo' : 'employeeNo_desc'}" class="listOrder ">
                                    直属在职人数
                                </a>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td align="center" nowrap>直属兼职人数</td>
					<td align="center" nowrap>状态</td>
					<td align="left" width="100">上级组织名称</td>
                    <td align="center" nowrap>开组日期</td>
					<td align="right" width="100" nowrap>操作</td>
				</tr>
                <c:forEach var="organization" items="${paginate.pageList}">
                    <tr>
                        <td align="left" ><a href="/employee/list?keyWords=${organization.orgName}">${organization.orgName}</a></td>
                        <td align="left" ><a href="/employee/list?keyWords=${organization.managerName}">${organization.managerName}</a></td>
                        <td align="center" >${organization.orgType}</td>
                        <td align="center" >${organization.employeeNo}</td>
                        <td align="center" >${organization.partTimeNo}</td>
                        <td align="center" name="tr_${organization.id}">${organization.statusStr}</td>
                        <td align="left" ><a href="/employee/list?keyWords=${organization.parentName}">${organization.parentName}</a><c:if test="${organization.orgType == '分行' && not empty organization.areaName }">（<a href="/employee/list?keyWords=${organization.areaName}">${organization.areaName}</a>）</c:if></td>
                        <td align="center" td_openDate="tr_${organization.id}" ><fmt:formatDate value="${organization.openDate}" pattern="yyyy-MM-dd"/></td>
                        <td align="right">
                            <appel:checkPrivilege url="oms_om_org_details">
                                <a href="/organization/${organization.id}/details"  target="_blank" class="btnOpH24 h24Silver in_block mt_5 js_hrefs">详情</a>
                            </appel:checkPrivilege>

                            <span id="operateSpan_${organization.id}">
                                <appel:checkPrivilege url="oms_om_org_add,oms_om_org_phone">
                                    <a href="/organization/${organization.id}/edit" class="btnOpH24 h24Silver in_block mt_5">编辑</a>
                                </appel:checkPrivilege>

                                    <%--分行可以修改编制--%>
                                <%-- <appel:checkPrivilege url="employee_count_edit">
                                    <c:if test="${organization.orgType == '分行'}">
                                        <c:choose>
                                            <c:when test="${not empty organization.maxCount}">
                                                <a href="javascript:;" id="${organization.id}_maxCount_delete" orgId="${organization.id}" orgName="${organization.orgName}" maxCount="${organization.maxCount}" deleteUrl="/organization/deleteEmployeeCount/${organization.id}" class="btnOpH24 h24Red in_block mt_5 js_deleteEmployeeCountStepOne">恢复默认编制</a>
                                            </c:when>
                                            <c:otherwise>
                                                <a href="javascript:;" id="${organization.id}_maxCount" orgId="${organization.id}" orgName="${organization.orgName}" maxCount="${organization.maxCount}" class="btnOpH24 h24Silver in_block mt_5 js_editEmployeeCountStepOne">修改编制</a>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </appel:checkPrivilege>--%>

                                <%--区域浮动人数--%>
                                <appel:checkPrivilege url="employee_count_edit">
                                    <c:if test="${organization.orgType == '区域'}">
                                        <a href="javascript:;" id="${organization.id}_maxCount" orgId="${organization.id}" orgName="${organization.orgName}" maxCount="${organization.maxCount}" class="btnOpH24 h24Silver in_block mt_5 js_editEmployeeCountStepOne">修改编制</a>
                                    </c:if>
                                </appel:checkPrivilege>

                                <c:if test="${organization.status == 1}">
                                    <appel:checkPrivilege url="hrm_employee_add">
                                        <a href="/employee/${organization.id}/add" class="btnOpH24 h24Silver in_block ml_5 mt_5">新增员工</a>
                                    </appel:checkPrivilege>
                                    <appel:checkPrivilege url="oms_om_org_switch">
                                        <a href="javascript:;" orgId="${organization.id}"  class="btnOpH24 h24Silver in_block mt_5 js_do_org_pause" >暂停</a>
                                    </appel:checkPrivilege>
                                </c:if>
                                <%--临时组织不能新增员工--%>
                                <%--<c:if test="${organization.status == 2}">
                                    <appel:checkPrivilege url="hrm_employee_add">
                                        <a href="/employee/${organization.id}/add" class="btnOpH24 h24Silver in_block mt_5">新增员工</a>
                                    </appel:checkPrivilege>
                                </c:if>--%>
                                <appel:checkPrivilege url="oms_om_org_switch">
                                    <c:if test="${organization.status == -1 || organization.status == 0}">
                                        <a href="javascript:;" orgId="${organization.id}" method="open" orgName="${organization.orgName}" class="btnOpH24 h24Green in_block mt_5 js_openOrgStepOne">启用</a>
                                    </c:if>
                                    <c:if test="${organization.status == 1 || organization.status == 0}">
                                        <a href="javascript:;" orgId="${organization.id}" method="close" orgName="${organization.orgName}"  url="/employee/list?keyWords=${organization.orgName}" class="btnOpH24 h24Red in_block mt_5 js_operateOrgStepOne">停用</a>
                                    </c:if>
                                </appel:checkPrivilege>

                            </span>

                            <appel:checkPrivilege url="pms_base">
                                <a tabname="${organization.orgName}_配置角色" target="_blank" href="/privilege/orgSet?key=${organization.id}" class="js_hrefs btnOpH24 h24Green in_block mt_5">配角色</a>
                            </appel:checkPrivilege>
                        </td>

                    </tr>
                </c:forEach>
			</tbody>
		</table>

        <div class="tabConInfoBtm mt_10" style="padding:0px">
            <app:paginate/>
        </div>

    <appel:checkPrivilege url="oms_om_org_switch">
		<!--暂停弹层 begin -->
		<div class="popLayer" id="layer_stop_org">
			
		</div>
		<!--暂停弹层 end -->

		<!--关闭弹层 begin -->
		<div class="popLayer" id="layer_close_org_no">
			<div class="popTitle clearfix">
				<h1>关闭组织</h1>
				<div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_close_org_no')); return false"></a></div>
			</div>
			<div class="popCon">
				<div class="form">
					<div>
					<p>该组织下还有员工，或者该组织的下级组织还未停用或有临时组织，不能停用。</p>
                    <p>若要关闭请转移员工到其他组织，并把该组织的下级组织停用。</p>
                    <p><a type="closePop" href="#">立即去处理</a></p>
					</div>
				</div>
			</div><br><br>
			<div class="popBtn">
				<a href="javascript:;" class="btnOpH24 h24Blue in_block" onclick="hidePopupDiv($('#layer_close_org_no')); return false">确定</a>
			</div>
		</div>
		<!--关闭弹层 end -->

		<!--关闭弹层 begin -->
		<div class="popLayer" id="layer_close_org_ok">
			<div class="popTitle clearfix">
				<h1>关闭组织</h1>
				<div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_close_org_ok')); return false"></a></div>
			</div>
			<div class="popCon">
                <div class="form">
                    <div class="bold">确定关闭【<span id="closeInfo">行政事务</span>】组织？</div>
                    <div class="mv_10">
                        请选择关闭日期：
                        <input type="text" class="txtDate" readonly="readonly" id="close_closedDate" name="closedDate" rule="required">
                        <span class="red" id="close_tips"></span>
                        <input type="hidden" id="closeId">
                    </div>
                </div>
			</div><br><br>
			<div class="popBtn">
				<a href="javascript:;" method="close" class="btnOpH24 h24Blue in_block js_operateOrgStepTwo">确定</a>
			</div>
		</div>
		<!--关闭弹层 end -->

        <!--启用弹层 begin -->
        <div class="popLayer" id="layer_open_org_ok">
            <div class="popTitle clearfix">
                <h1>启用组织</h1>
                <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_open_org_ok')); return false"></a></div>
            </div>
            <div class="popCon">
                <div class="form">
                    <div class="bold">确定启用【<span id="openInfo">行政事务</span>】组织？</div>
                    <div class="mv_10">
                        请选择开组日期:<input type="text" class="ml_10 txtDate" readonly="readonly" id="open_openDate" name="openDate" rule="required">
                        <span class="red" id="open_tips"></span>
                        <input type="hidden" id="openId">
                    </div>
                </div>
            </div><br><br>
            <div class="popBtn">
                <a href="javascript:;" class="btnOpH24 h24Blue in_block js_openOrgStepTwo">确定</a>
            </div>
        </div>
        <!--启用弹层 end -->
    </appel:checkPrivilege>

<appel:checkPrivilege url="employee_count_edit">
        <!--修改编制 begin -->
        <div class="popLayer" id="editEmployeeCount">
            <form id="editEmployeeCount_form" action="/organization/editEmployeeCount" method="post">
                <webplus:token />
                <div class="popTitle clearfix">
                    <h1>修改编制（<span id="editEmployeeCount_orgName"></span>）</h1>
                    <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#editEmployeeCount')); return false"></a></div>
                </div>
                <div class="popCon">
                    <div class="form">
                        <input type="hidden" name="orgId" id="editEmployeeCount_orgId">
                        <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew">
                            <tr>
                                <td class="tdTitle" width="60">浮动人数：</td>
                                <td class="request" width="20">●</td>
                                <td width="250"><input type="text" rule="required integer" class="txt mr_15" name="maxCount" id="maxCount" /></td>
                            </tr>
                       <%--     <tr>
                                <td class="tdTitle">结束时间：：</td>
                                <td class="request">●</td>
                                <td><input type="text" class="txtDate" readonly="readonly" id="editEmployeeCount_endTime" name="endTime" /></td>
                            </tr>--%>
                        </table>
                    </div>
                    <br/>
                    <div class="red bold" style="padding-left:42px;">
                        用于营运人员编制；
                        <br/>区域人员编制 = 分行数量 * 10 + 1（区域总监）+浮动人数+关组剩余人数
                    </div>
                </div><br><br>
                <div class="popBtn">
                    <a href="javascript:;" class="btnOpH24 h24Blue in_block js_editEmployeeCountStepTwo">确定</a>
                </div>
            </form>
        </div>
        <!--修改编制 end -->
</appel:checkPrivilege>

</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,WdatePicker"/>
    <jsp:param name="js" value="portal,position/jobManage_common"/>
</jsp:include>
<script type="text/javascript">

    $(function(){
        <appel:checkPrivilege url="oms_om_org_switch">
        $("a.js_do_org_pause").live("click",function(){
        	var self=$(this);
        	 ajaxGet({
                 url: '/organization/' + self.attr("orgId") + '/getPauseHtml',
                 ok:function(data){
                     $("#layer_stop_org").html(data.html);
                     showPopupDiv($('#layer_stop_org'));
                 },
                 fail:function(data){
                     alert(data.message);
                 }
             });
        });
        $("a.js_btnPauseSubmit").live("click",function(){
        	var validator = $('#formPauseOrg').validate();
    		if(validator.validateForm()){
    			var orgId=$("#formPauseOrg input[name=orgId]").val();
   			  ajaxPost({
                     url: '/organization/' + orgId+ '/stop',
                     data: {'closedDate': $("#formPauseOrg input[name=closeDate]").val(),
                    	 "newOrgName":$.trim($("#formPauseOrg input[name=newOrgName]").val())},
                     ok:function(data){
                         hidePopupDiv($('#layer_stop_org'));
                         $("td[name='tr_"+orgId+"']").html("暂停");
                         $("#operateSpan_" + orgId).html(data.operateStr);
                     },
                     fail:function(data){
                    	 hidePopupDiv($('#layer_stop_org'));
                         alert(data.message);
                     }
                 });
    		}
        });
        //查找该组织下是否有员工，并显示弹层
        $("a.js_operateOrgStepOne").live('click',function(){
            var self = $(this);
            ajaxGet({
                url: '/organization/' + self.attr("orgId") + '/countEmployee/' + self.attr("method"),
                ok:function(data){
                    $('#' + self.attr("method") +'Info').text(self.attr("orgName"));
                    $('#' + self.attr("method") +'Id').val(self.attr("orgId"));
                    showPopupDiv($('#layer_' + self.attr("method") + '_org_ok'));
                },
                fail:function(data){
                    $("a[type='" + self.attr("method") + "Pop']").attr("href", self.attr("url"));
                    showPopupDiv($('#layer_' + self.attr("method") + '_org_no'));
                }
            });
        });

        //停用和暂停组织
        $("a.js_operateOrgStepTwo").live('click', function(){
            var self = $(this);
            var tempStatus = '';
            if(self.attr("method") == 'close') {
                tempStatus = '停用';
            } else {
                tempStatus = '暂停';
            }
            if($('#' + self.attr("method") + '_closedDate').val() !== '') {
                $('#' + self.attr("method") + '_tips').text("");
                ajaxGet({
                    url: '/organization/' +  $('#' + self.attr("method") + 'Id').val() + '/' + self.attr("method"),
                    data: {'closedDate': $('#' + self.attr("method") + '_closedDate').val()},
                    ok:function(data){
                        hidePopupDiv($('#layer_' + self.attr("method") + '_org_ok'));
                        $("td[name='tr_"+$('#' + self.attr("method") + 'Id').val()+"']").html(tempStatus);
                        $("#operateSpan_" + $('#' + self.attr("method") + 'Id').val()).html(data.operateStr);
                    },
                    fail:function(data){
                        $('#' + self.attr("method") + '_tips').text("（" + data.message + "）");
                    }
                });
            } else {
                $('#' + self.attr("method") + '_tips').text("（必填）");
            }
        });

        //开启组织弹层
        $("a.js_openOrgStepOne").live('click', function(){
            var self = $(this);
            var method = self.attr("method");
            $('#' + method + 'Info').text(self.attr("orgName"));
            $('#' + method + 'Id').val(self.attr("orgId"));
            showPopupDiv($('#layer_' + method + '_org_ok'));
        });

        //开启组织
        $("a.js_openOrgStepTwo").live('click', function(){
            if($('#open_openDate').val() !== '') {
                $('#open_tips').text("");
                ajaxGet({
                    url: '/organization/' +  $('#openId').val() + '/open',
                    data: {'openDate': $('#open_openDate').val()},
                    ok:function(data){
                        hidePopupDiv($('#layer_open_org_ok'));
                        $("td[name='tr_"+$('#openId').val()+"']").html("正常");
                        $("td[td_openDate='tr_"+$('#openId').val()+"']").html($('#open_openDate').val());
                        $("#operateSpan_" + $('#openId').val()).html(data.operateStr);
                    },
                    fail:function(data){
                        alert(data.message);
                    }
                });
            } else {
                $('#open_tips').text("（必填）");
            }
        });
        </appel:checkPrivilege>

        $("#orgName").keyup(function(evt){	//按Enter键快速查询
            var k = window.event ? evt.keyCode : evt.which;
            if(k == 13) {
                $("a.js_search").click();
            }
        });

        //搜索
        $("a.js_search").live('click', function(){
            var hasManager = 0;
            if($('#manager').is(':checked')) {
                hasManager = 1;
            }
            var url = replaceLocalUrlParams({
                orgName : $('#orgName').val(),
                orgType : $('#orgType').val(),
                status: $('#status').val(),
                manager : hasManager,
                pageNo : 1
            });
            if($('#orgName').val() == '') {
                url = removeOneParam(url, "orgName")
            }
            if($('#orgType').val() == '') {
                url = removeOneParam(url, "orgType")
            }
            if($('#status').val() == -9) {
                url = removeOneParam(url, "status")
            }
            if(!$('#manager').is(':checked')) {
                url = removeOneParam(url, "manager")
            }
            window.location.href = url;
        });

        /**
         * 根据在职人数排序
         */
        $("#order_by_employeeCount").click(function(){
            var self = $(this);
            var url = replaceLocalUrlParams({
                orderBy : self.attr("orderBy"),
                pageNo : 1
            });
            window.location.href = url;
        })

        //重置
//        $("a.js_reset").live('click', function(){
//            removeAllParams();
//        });
/*

        */
/**
        * 修改编制弹出弹层
         */

        $("a.js_editEmployeeCountStepOne").live("click", function(){
            var self = $(this),
                orgId = self.attr("orgId");
            $("#maxCount").val(self.attr("maxCount"));
            $("#maxCount").attr("orgId", orgId);
            $("#editEmployeeCount_orgName").text(self.attr("orgName"));
            showPopupDiv($("#editEmployeeCount"));

        })

/**
         修改编制提交弹层
         */

        $("a.js_editEmployeeCountStepTwo").live("click", function(){
            var validator = $('#editEmployeeCount_form').validate();
            if(validator.validateForm()){
                var orgId = $('#maxCount').attr("orgId");
                $("#editEmployeeCount_orgId").val(orgId);
                $("#editEmployeeCount_form").submit();
            }

        })

    });

</script>