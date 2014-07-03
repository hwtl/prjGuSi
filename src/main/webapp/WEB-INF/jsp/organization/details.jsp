<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="查看组织详情-组织管理"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>
	<div>
        <div class="right mb_5">

             <span id="operateSpan_${organization.id}">
                <appel:checkPrivilege url="oms_om_org_add,oms_om_org_phone">
                    <a href="/organization/${organization.id}/edit" class="btnOpH24 h24Silver in_block ml_5">编辑</a>
                </appel:checkPrivilege>
                <c:if test="${organization.status == 1}">
                    <appel:checkPrivilege url="hrm_employee_add">
                        <a href="/employee/${organization.id}/add" class="btnOpH24 h24Silver in_block ml_5">新增员工</a>
                    </appel:checkPrivilege>
                    <appel:checkPrivilege url="oms_om_org_switch">
                        <a href="javascript:;" orgId="${organization.id}" method="stop" orgName="${organization.orgName}" class="btnOpH24 h24Silver in_block ml_5 js_do_org_pause" >暂停</a>
                    </appel:checkPrivilege>
                </c:if>
                <appel:checkPrivilege url="oms_om_org_switch">
                    <c:if test="${organization.status == -1 || organization.status == 0}">
                        <a href="javascript:;" orgId="${organization.id}" method="open" orgName="${organization.orgName}" class="btnOpH24 h24Green in_block ml_5 js_openOrgStepOne">启用</a>
                    </c:if>
                    <c:if test="${organization.status == 1 || organization.status == 0}">
                        <a href="javascript:;" orgId="${organization.id}" method="close" orgName="${organization.orgName}"  url="/employee/list?keyWords=${organization.orgName}" class="btnOpH24 h24Red in_block ml_5 js_operateOrgStepOne">停用</a>
                    </c:if>
                </appel:checkPrivilege>
            </span>

            <appel:checkPrivilege url="oms_add_accountingDept">
                <a href="/accounting/${organization.id}/addAccountingDept" class="btnOpH24 h24Silver in_block ml_5 ">添加核算部门</a>
            </appel:checkPrivilege>

        </div>
		<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
			<tbody>
                <c:if test="${ not empty organization && organization.parentId != 0}">
                    <tr>
                        <td class="tdTitle" width="100">上级组织：</td>
                        <td width="260" class="bdR">${organization.parentName}</td>
                        <td colspan="2">&nbsp;</td>
                    </tr>
                </c:if>
				<tr>
					<td class="tdTitle" width="100">组织名称：</td>
					<td class="bdR">${organization.orgName}</td>
					<td class="tdTitle" width="100">组织英文名称：</td>
					<td width="260">${organization.eOrgName}</td>
				</tr>
				<tr>
					<td class="tdTitle">类型：</td>
					<td class="bdR">${organization.orgType}</td>
                    <c:choose>
                        <c:when test="${organization.orgType == '门店'}">
                            <td class="tdTitle" width="140">组数：</td>
                            <td width="260">
                        <span id="orgMaxCount_txt">
                            <span id="orgMaxCount_show">${orgMaxCount}</span>
                            <appel:checkPrivilege url="oms_om_org_count">
                                <a href="#" class="ml_10" id="editOrgMaxCount">编辑</a>
                            </appel:checkPrivilege>
                        </span>
                        <span id="orgMaxCount_ipt" class="hideit">
                            <input class="txtNew" id="orgMaxCount" name="orgMaxCount" maxlength="3"/>
                            <a href="#" class="ml_5" id="saveOrgMaxCount">保存</a>
                        </span>
                            </td>
                        </c:when>
                        <c:otherwise>
                            <td colspan="2">&nbsp;</td>
                        </c:otherwise>
                    </c:choose>


				</tr>
				<tr>
					<td class="tdTitle">开组日期：</td>
					<td class="bdR" td_openDate="td_openDate"><fmt:formatDate value="${organization.openDate}" pattern="yyyy-MM-dd"/></td>
                    <td class="tdTitle" width="100">关组时间：</td>
                    <td width="260" td_closedDate="td_closedDate"><fmt:formatDate value="${organization.closedDate}" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr>
					<td class="tdTitle">属性：</td>
					<td class="bdR">${organization.orgClass}</td>
                    <td colspan="2">&nbsp;</td>
                    <%--<c:if test="${organization.orgType == '区域' && not empty organization.maxCount}">
                        <td class="tdTitle" width="100">浮动人数：</td>
                        <td>
                            ${organization.maxCount}人
                        </td>
                    </c:if>--%>
				</tr>
                <%--<c:if test="${organization.company == '德佑' && organization.orgType == '区域'
                    && (fn:startsWith(organization.orgLongCode, '12020001/120720495/12020003') || fn:startsWith(organization.orgLongCode, '12020001/120720495/140207133236/140207133359'))" >
                    --%>
                <c:if test="${organization.company == '德佑' && organization.orgType == '区域' && (organization.zhongJieBu || organization.xinFang)}">
                    <tr>
                        <td class="tdTitle">编制人数：</td>
                        <td colspan="3"> 分行组数（${orgBZ.branchNo}）*${orgBZ.branchRequireEmpNo} + 区总（${orgBZ.qzNo}）+ 浮动人数（${orgBZ.fdNo}）+ <a href="javascript:;"  id="bianZhiInfo">关组剩余人数</a>（${orgBZ.gzfdNo}）= ${orgBZ.bianZhiNo} 人</td>
                    </tr>
                    <tr>
                        <td class="tdTitle">可入职人数：</td>
                        <td colspan="3">编制人数（${orgBZ.bianZhiNo}） - 在职人数（${orgBZ.allFormalNo}）- 待报到人数（${orgBZ.allRegisterNo}）= ${orgBZ.keRuZhiNo} 人
                        </td>
                    </tr>
                </c:if>
				<tr>
					<td class="tdTitle">组织负责人：</td>
					<td>${organization.managerName}${organization.manager}</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="tdTitle">组织描述：</td>
					<td colspan="3">${organization.remark}</td>
				</tr>
				<tr>
					<td class="tdTitle">办公地点：</td>
					<td colspan="3">${organization.address}</td>
				</tr>
				<tr>
					<td class="tdTitle">传真：</td>
					<td width="260">${organization.orgFax}</td>
					<td colspan="2">&nbsp;</td>
				</tr>
                <c:if test="${empty orgPhoneList}">
                    <tr>
                        <td class="tdTitle">联系电话：</td>
                        <td width="260"></td>
                        <td colspan="2"></td>
                    </tr>
                </c:if>
                <c:forEach items="${orgPhoneList}" var="orgPhone" varStatus="index">
                    <tr>
                        <td class="tdTitle"><c:if test="${index.first}">联系电话：</c:if></td>
                        <td>${orgPhone.phone}</td>
                        <td colspan="2">${orgPhone.line}</td>
                    </tr>
                </c:forEach>
			</tbody>
		</table>

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
    </div>

<!-- begin: 编制 -->
<div class="popLayer bubbleBox" id="bianZhiInfo_form">
    <div class="bubbleBoxTitle clearfix">
        <h1>关组剩余人数</h1>
        <div class="cls"><a href="javascript:;" class="closePopBox xx" onclick="hidePopupDiv($('#bianZhiInfo_form'))"></a></div>
    </div>
    <div class="bubbleBoxCon pd_20">
        <div style="max-height:150px; overflow-y:auto" class="mt_10">
        <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew tableFormList" id="bianZhiInfo_table">
        </table>
        </div>
        <p class="bold red mt_5">关组剩余人数：分行被暂停时仍然在该分行任职的人数总和。</p>
    </div>
    <div class="popBtn">
        <a href="javascript:;" class="btn-small btn-silver in_block" onclick="hidePopupDiv($('#bianZhiInfo_form'))">关闭</a>
    </div>
</div><br />
<!-- begin: 编制 -->

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,WdatePicker"/>
    <jsp:param name="js" value="portal,position/jobManage_common"/>
</jsp:include>

<script type="text/javascript">

    $(function(){
        $("#bianZhiInfo").click(function(){
            $.get("/organization/${organization.id}/getExtraEmpNoList", function(data){
                if(data.status == "ok") {
                    var html = '<tr class="trHead"><td>关闭分行</td><td>关组剩余人数</td><td>失效日期</td></tr>';
                    $.each(data.extraEmpNoList, function(i,n){
                        html += '<tr ' + (n.status == 0 ? 'class="grey999"' : '') + '><td>'+ n.branchName+'</td><td>'+ n.maxCount +'</td><td>'+ n.endTimeStr +'</td></tr>';
                    });
                    $("#bianZhiInfo_table").html(html);
                    showPopupDiv($("#bianZhiInfo_form"));
                } else {
                    alert(data.message);
                }
            })
        })
    })

    <appel:checkPrivilege url="oms_om_org_switch">

    $(function(){
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
                url: '/organization/' + self.attr("orgId") + '/countEmployee/' + self.attr('method'),
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
            if($('#' + self.attr("method") + '_closedDate').val() !== '') {
                $('#' + self.attr("method") + '_tips').text("");
                ajaxGet({
                    url: '/organization/' +  $('#' + self.attr("method") + 'Id').val() + '/' + self.attr("method"),
                    data: {'closedDate': $('#' + self.attr("method") + '_closedDate').val()},
                    ok:function(data){
                        hidePopupDiv($('#layer_' + self.attr("method") + '_org_ok'));
                        $("td[td_closedDate='td_closedDate']").html($('#' + self.attr("method") + '_closedDate').val());
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
                        $("td[td_openDate='td_openDate']").html($('#open_openDate').val());
                        $("td[td_closedDate='td_closedDate']").html("");
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

    });

    </appel:checkPrivilege>

    /**
     * 编辑组数
     */
    $("#editOrgMaxCount").click(function(data){
        $("#orgMaxCount").val($("#orgMaxCount_show").html());
        $("#orgMaxCount_txt").addClass("hideit");
        $("#orgMaxCount_ipt").removeClass("hideit");
    });

    /**
     * 保存组数
     */
    $("#saveOrgMaxCount").click(function(data){
        var count = $("#orgMaxCount").val();
        if(!checkNum(count)) {
            alert("请输入正整数！");
            return;
        }
        $.post("/organization/updateOrgCount/" +${organization.id} + "/"  + count,function(data){
            if(data.status == "ok") {
                $("#orgMaxCount_show").html(count);
                $("#orgMaxCount_ipt").addClass("hideit");
                $("#orgMaxCount_txt").removeClass("hideit");
            } else {
                alert("修改失败！" + data.message);
            }
        });

    });

    /**
     * 验证组数
     */
    function checkNum(value){
        //定义正则表达式部分
        var reg = /^\d+$/;
        if( value.constructor === String ){
            return value.match( reg );
        }
        return false;
    }

</script>