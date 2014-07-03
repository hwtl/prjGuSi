<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="详情"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>

<%--<div class="container">--%>
    <div class="location">
        <a href="/">人力资源管理系统</a> &gt; <a href="/transfer/emp/list">异动记录</a> &gt;
    </div>

    <div class="clearfix">
        <h1 class="f18 mt_10 left">转调单（${transfer.transferNo}）
            <c:choose>
                <c:when test="${transfer.approvalStatus == -1}"><span class="grey999">已作废</span></c:when>
                <c:when test="${transfer.approvalStatus == 1}"><span class="orange">审批中</span></c:when>
                <c:when test="${transfer.approvalStatus == 2}"><span class="orange">待交接处理</span></c:when>
                <c:when test="${transfer.approvalStatus == 5}"><span class="orange">人事审批</span></c:when>
                <c:when test="${transfer.approvalStatus == 4}"><span class="red">打回修改</span></c:when>
                <c:when test="${transfer.approvalStatus == 3}"><span class="green">已完成</span></c:when>
                <c:otherwise></c:otherwise>
            </c:choose>
        </h1>
        <span class="right mt_5">
            <appel:checkEmpTransferPrivilege url="${transfer.operateUrl}">
                <c:if test="${transfer.creator == user.userCode}">
                    <a href="/transfer/emp/approval/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" class="btn-small btn-silver in_block">编辑</a>
                    <a href="#" transferId="${transfer.id}" taskId="${transfer.taskId}" class="btn-small btn-red in_block js_cancel">作废</a>
                </c:if>
            </appel:checkEmpTransferPrivilege>
        </span>
    </div>

    <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
        <tbody>
            <tr>
                <td class="tdTitle" width="120">申请人：</td>
                <td colspan="3"><b>${transfer.creatorName}</b></td>
            </tr>
            <tr>
                <td class="tdTitle">申请日期：</td>
                <td colspan="3"><b><fmt:formatDate value="${transfer.createTime}" pattern="yyyy-MM-dd"/></b></td>
            </tr>
            <tr>
                <td class="tdTitle">转调员工：</td>
                <td colspan="3"><b>${transfer.userName}（${transfer.userCode}）</b></td>
            </tr>
            <tr>
                <td class="tdTitle">转调类型：</td>
                <td colspan="3"><b>${transfer.transferTypeStr}</b></td>
            </tr>
            <c:if test="${transfer.transferState != 0}">
                <tr>
                    <td class="tdTitle">转调情况：</td>
                    <td colspan="3">
                        <input type="hidden" name="h_transferMethod" id="h_transferMethod" value="${transfer.transferState}" />
                        <b>${transfer.transferStateStr}</b>
                        <div class="bg_yellow red pd_6 pl_15 mt_10 lineH180 hideit" id="transfer_method_prompt"></div>
                    </td>
                </tr>
            </c:if>
            <tr>
                <td class="tdTitle" width="120">转出组织：</td>
                <td class="bdR" width="350"><b>${transfer.oldOrgName}</b></td>
                <td class="tdTitle" width="120">转入组织：</td>
                <td width="350"><b>${transfer.newOrgName}</b></td>
            </tr>
            <tr>
                <td class="tdTitle">原职等职级：</td>
                <td class="bdR"><b>${transfer.oldTitleDegree}-${transfer.oldLevelDegree} ${transfer.oldLevelName}</b></td>
                <td class="tdTitle">转调后职等职级：</td>
                <td><b>${transfer.newTitleDegree}-${transfer.newLevelDegree} ${transfer.newLevelName}</b></td>
            </tr>
            <tr>
                <td class="tdTitle">原岗位：</td>
                <td class="bdR"><b>${transfer.oldPositionName}</b></td>
                <td class="tdTitle">转调后岗位：</td>
                <td><b>${transfer.newPositionName}</b></td>
            </tr>
            <tr>
                <td class="tdTitle">原头衔：</td>
                <td class="bdR"><b>${transfer.oldTitle}</b></td>
                <td class="tdTitle">转调后头衔：</td>
                <td><b>${transfer.newTitle}</b></td>
            </tr>
    	</tbody>
    </table>

    <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
        <tbody>
            <tr>
                <td class="tdTitle" width="120">交接事项：</td>
                <td>&nbsp;</td>
            </tr>

            <tr>
                <td class="tdTitle"><b class="black">房源：</b></td>
                <td>
                    <c:choose>
                        <c:when test="${transfer.fangyuanStatus == 0}">
                            <b class="grey999">不需要交接</b>
                        </c:when>
                        <c:when test="${transfer.fangyuanStatus == 1}">
                            <b class="red">未交接</b>
                        </c:when>
                        <c:when test="${transfer.fangyuanStatus == 2}">
                            <b class="green">已交接</b>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
            </tr>

            <tr>
                <td class="tdTitle"><b class="black">客源：</b></td>
                <td>
                    <c:choose>
                        <c:when test="${transfer.keyuanStatus == 0}">
                            <b class="grey999">不需要交接</b>
                        </c:when>
                        <c:when test="${transfer.keyuanStatus == 1}">
                            <b class="red">未交接</b>
                        </c:when>
                        <c:when test="${transfer.keyuanStatus == 2}">
                            <b class="green">已交接</b>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
            </tr>

        </tbody>
    </table>

    <div class="navTab2th navLog mt_20 pt_20 clearfix">
        <a class="current" href="#">审批记录</a>
    </div>
    <table border="0" width="100%" cellpadding="0" cellspacing="0" class="tableListNew tableLog mt_10">
        <tbody>
            <tr class="trHead">
                <td width="100" nowrap="">处理环节</td>
                <td width="100" nowrap="">处理人</td>
                <td width="60" nowrap="">处理角色</td>
                <td width="70">处理结果</td>
                <td width="150">处理意见</td>
                <td width="80" nowrap="">处理时间</td>
                <td width="100" nowrap="">耗时</td>
            </tr>
            <c:forEach items="${taskList}" var="task">
                <tr>
                    <td>${task.taskName}</td>
                    <td>${task.assigneeName}</td>
                    <td>${task.role}</td>
                    <td>${task.result}</td>
                    <td>${task.comment}</td>
                    <td>${task.endTime}</td>
                    <td>${task.elapse}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
<%--</div>--%>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp"/>

<script type="text/javascript">
$(function(){
    //初始化转调情况提示信息
    var html = "";
    switch($.trim($("#h_transferMethod").val())){
        case "1": html = "房源：需交接给原分行。<br />客源：仍归属该转调员工。"; break;
        case "2": html = "房源：仍归属该转调员工。（签赔自动留在原分行，如需转移请见<a href='http://bangzhu.dooioo.com/document/details/12527S110147' target='_blank'>帮助</a>）<br />客源：仍归属该转调员工。"; break;
        default:;
    }
    $("#transfer_method_prompt").html(html).show();

    //作废按钮事件
    $("a.js_cancel").live("click", function () {
        if (window.confirm("确认删除此转调单")) {
            var id = $(this).attr("transferId");//记录id
            var taskId = $(this).attr("taskId");
            //执行删除操作，成功后刷新
            $.post("/transfer/emp/destroy/" + id + "/" + taskId, function(data){
                if(data.status == "ok") {
                    $("#td_option_" + id).html("");
                } else {
                    alert(data.message);
                }
            })
        }
    });

});
</script>
