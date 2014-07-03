<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="审批"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>

<%--<div class="container">--%>
    <div class="location">
        <a href="/transfer/emp/list">人力资源管理系统</a> &gt; <a href="#">异动记录</a> &gt;
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
    <table border="0" width="100%" cellpadding="0" cellspacing="0" class="tableListNew tableNarrow mt_10">
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

<%--普通审批--%>
<c:if test="${approvalStep == 'exportManagerApprove'
                || approvalStep == 'exportSupervisorApprove'
                || approvalStep == 'exportDirectorApprove'
                || approvalStep == 'importSupervisorApprove'}">
    <!-- 审批任务条 begin -->
    <div id="fixedBar" ng-style="init.taskStyle">
        <div class="movingBox">
            <span class="f14 mt_5 in_block"><b>任务：</b>请仔细核对单据信息，并给予审批</span>
            <a href="javascript:;" class="btn-small btn-blue in_block ml_5" id="btn_pass">审批通过</a>
            <a href="javascript:;" class="btn-small btn-red in_block ml_5" id="btn_reject">打回修改</a>
        </div>
    </div>
    <!-- 审批任务条 end -->

    <!-- 审批通过弹层 begin-->
    <div class="popLayer" id="layer_pass">
        <div class="popTitle clearfix">
            <h1>审批通过</h1>

            <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_pass')); return false"></a>
            </div>
        </div>
        <form id="formPass" action="/transfer/emp/pass/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" method="post">
            <webplus:token/>
            <input type="hidden" name="approvalStep" id="approvalStep" value="${approvalStep}" />
            <div class="popCon">
                <div class="form clearfix">
                    <table>
                        <tr>
                            <td valign="top">审批意见：</td>
                            <td width="20" class="red" valign="top">●</td>
                            <td><textarea class="tA t300" maxLength="500" name="remark" rule="required">同意</textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <br><br>
            <div class="popBtn">
                <a href="javascript:;" class="btn-small btn-silver in_block" onclick="hidePopupDiv($('#layer_pass'))">关闭</a>
                <a href="javascript:;" class="btn-small btn-blue in_block" id="btn_pass_submit">提交</a>
            </div>
        </form>
    </div>
    <!-- 审批通过弹层 end-->

    <!-- 打回修改弹层 begin-->
    <div class="popLayer" id="layer_reject">
        <div class="popTitle clearfix">
            <h1>打回修改</h1>

            <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_reject')); return false"></a>
            </div>
        </div>
        <form id="formReject" action="/transfer/emp/unPass/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" method="post">
            <webplus:token/>
            <input type="hidden" name="approvalStep" id="approvalStep" value="${approvalStep}" />
            <div class="popCon">
                <div class="form">
                    <table>
                        <tr>
                            <td valign="top">打回原因：</td>
                            <td width="20" class="red" valign="top">●</td>
                            <td><textarea class="tA t300" maxLength="500" name="unpassRemark" rule="required"></textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            <br><br>

            <div class="popBtn">
                <a href="javascript:;" class="btn-small btn-silver in_block ml_5"
                   onclick="hidePopupDiv($('#layer_reject'))">关闭</a>
                <a href="javascript:;" class="btn-small btn-blue in_block ml_5" id="btn_reject_submit">提交</a>
            </div>
        </form>
    </div>
    <!-- 打回修改弹层 end-->
</c:if>

<%--房源待交接处理--%>
<c:if test="${approvalStep == 'houseHandover'}">
    <!-- 审批任务条 begin -->
    <div id="fixedBar">
        <div class="movingBox">
            <span class="f14 mt_5 in_block"><b>任务：</b>房源交接处理</span>
            <a href="${appfn:formatUrl(config.fyTransferUrl, user.companyENLower, transfer.userCode)}" class="btn-small btn-green in_block ml_5" id="fky_btn">处理</a>
        </div>
    </div>
    <!-- 审批任务条 end -->
</c:if>

<%--客源待交接处理--%>
<c:if test="${approvalStep == 'customerHandover'}">
    <!-- 审批任务条 begin -->
    <div id="fixedBar">
        <div class="movingBox">
            <span class="f14 mt_5 in_block"><b>任务：</b>客源交接处理</span>
            <a href="${appfn:formatUrl(config.kyTransferUrl, user.companyENLower, transfer.userCode)}" class="btn-small btn-green in_block ml_5" id="fky_btn">处理</a>
        </div>
    </div>
    <!-- 审批任务条 end -->
</c:if>

<%--人事审批--%>
<c:if test="${approvalStep == 'hrApprove'}">
    <!-- 审批任务条 begin -->
    <div id="fixedBar" ng-style="init.taskStyle">
        <div class="movingBox">
            <span class="f14 mt_5 in_block"><b>任务：</b>请仔细核对单据信息，并给予审批</span>
            <a href="javascript:;" class="btn-small btn-blue in_block ml_5" id="btn_pass">审批通过</a>
            <a href="javascript:;" class="btn-small btn-red in_block ml_5" id="btn_reject">打回修改</a>
        </div>
    </div>
    <!-- 审批任务条 end -->

    <!-- 审批通过弹层 begin-->
    <div class="popLayer w600" id="layer_pass">
        <div class="popTitle clearfix">
            <h1>审批通过</h1>
            <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_pass')); return false"></a></div>
        </div>
        <form id="formPass" action="/transfer/emp/pass/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" method="post">
            <webplus:token/>
            <input type="hidden" name="approvalStep" id="approvalStep" value="${approvalStep}" />
            <input type="hidden" name="transferType" id="transferType" value="${transfer.transferType}" />
            <input type="hidden" name="id" id="id" value="${transfer.id}" />
            <div class="popCon">
                <div class="form clearfix">
                    <table cellpadding="0" cellspacing="0" class="tableFormNew">
                        <tr>
                            <td class="tdTitle">转调员工：</td>
                            <td width="20" class="request">&nbsp;</td>
                            <td><b>${transfer.userName}（${transfer.userCode}）</b></td>
                        </tr>
                        <tr>
                            <td class="tdTitle">转调后职等职级：</td>
                            <td width="20" class="request">●</td>
                            <td>
                                <select name="newSerialId" id="js_serialId" rule="required" groupby="level">
                                    <option value="">请选择职系</option>
                                    <c:forEach items="${serialList}" var="serial">
                                        <option ${serial.key == transfer.newSerialId ? 'selected=selected' : ''} value="${serial.key}">${serial.value}</option>
                                    </c:forEach>
                                </select>
                                <select name="newTitleId" id="js_titleId" rule="required" groupby="level">
                                    <option value="">请选择职等</option>
                                    <c:forEach items="${titleList}" var="title">
                                        <%--转后台：出行政技术职系、行政管理职系的3职等（含3职等及以下）--%>
                                        <c:if test="${transfer.transferType eq '201402205' && title.value <= 3}">
                                            <option ${title.key == transfer.newTitleId ? 'selected=selected' : ''} value="${title.key}">${title.value}-${title.expand}</option>
                                        </c:if>
                                        <%--转代理项目出：营销职系（代理部）下的1职等--%>
                                        <c:if test="${transfer.transferType eq '201402204' && title.value <= 1}">
                                            <option ${title.key == transfer.newTitleId ? 'selected=selected' : ''} value="${title.key}">${title.value}-${title.expand}</option>
                                        </c:if>
                                    </c:forEach>
                                </select>
                                <select name="newLevelId" id="js_levelId" rule="required" groupby="level">
                                    <option value="">请选择职级</option>
                                    <c:forEach items="${levelList}" var="level">
                                        <option ${level.key == transfer.newLevelId ? 'selected=selected' : ''} value="${level.key}">${level.value}-${level.expand}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">转调后岗位：</td>
                            <td class="request">●</td>
                            <td>
                                <select name="newPositionId" id="js_positionId" rule="required">
                                    <option value="">请选择</option>
                                    <c:forEach items="${positionList}" var="position">
                                        <option ${position.key == transfer.newPositionId ? 'selected=selected' : ''} value="${position.key}">${position.value}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">转调后头衔：</td>
                            <td class="request">●</td>
                            <td>
                                <input type="text" name="newTitle" id="js_userTitle" class="txtNew" rule="required" value="${transfer.newTitle}" />
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">审批意见：</td>
                            <td width="20" class="request">●</td>
                            <td>
                                <textarea class="tA t300" maxLength="500" name="remark" rule="required">同意</textarea>
                            </td>
                        </tr>
                    </table>
                </div>
            </div><br><br>
            <div class="popBtn">
                <a href="javascript:;" class="btn-small btn-silver in_block" onclick="hidePopupDiv($('#layer_pass'))">关闭</a>
                <a href="javascript:;" class="btn-small btn-blue in_block" id="btn_pass_submit">提交</a>
            </div>
        </form>
    </div>
    <!-- 审批通过弹层 end-->

    <!-- 打回修改弹层 begin-->
    <div class="popLayer" id="layer_reject">
        <div class="popTitle clearfix">
            <h1>打回修改</h1>
            <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_reject')); return false"></a></div>
        </div>
        <form id="formReject" action="/transfer/emp/unPass/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" method="post">
            <webplus:token/>
            <input type="hidden" name="approvalStep" id="approvalStep" value="${approvalStep}" />
            <div class="popCon">
                <div class="form">
                    <table>
                        <tr>
                            <td valign="top">打回原因：</td>
                            <td width="20" class="red" valign="top">●</td>
                            <td><textarea class="tA t300" maxLength="500" name="unpassRemark" rule="required"></textarea></td>
                        </tr>
                    </table>
                </div>
            </div><br><br>
            <div class="popBtn">
                <a href="javascript:;" class="btn-small btn-silver in_block ml_5" onclick="hidePopupDiv($('#layer_reject'))">关闭</a>
                <a href="javascript:;" class="btn-small btn-blue in_block ml_5" id="btn_reject_submit">提交</a>
            </div>
        </form>
    </div>
    <!-- 打回修改弹层 end-->
</c:if>


<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation"/>
    <jsp:param name="js" value="empTransfer/transfer_levelChange"/>
</jsp:include>


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

        <%--b普通审批和人事审批共用的js--%>
<c:if test="${approvalStep == 'exportManagerApprove'
                || approvalStep == 'exportSupervisorApprove'
                || approvalStep == 'exportDirectorApprove'
                || approvalStep == 'importSupervisorApprove'
                || approvalStep == 'hrApprove'}">

        //审批通过按钮
        $("#btn_pass").click(function(){
            showPopupDiv($("#layer_pass"));
        });

        //打回修改按钮
        $("#btn_reject").click(function(){
            showPopupDiv($("#layer_reject"));
        });

        //审批通过弹层-提交按钮
        $("#btn_pass_submit").click(function(){
            var validator_pass = $('#formPass').validate();
            if(validator_pass.validateForm()){
                $('#formPass').submit();
            }
        });

        //打回修改弹层-提交按钮
        $("#btn_reject_submit").click(function(){
            var validator_reject = $('#formReject').validate();
            if(validator_reject.validateForm()){
                $('#formReject').submit();
            }
        });
</c:if>
        <%--e普通审批和人事审批共用的js--%>

    });
    </script>
