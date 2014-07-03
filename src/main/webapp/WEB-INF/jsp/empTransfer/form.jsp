<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="申请转调"/>
</jsp:include>

    <div class="location">
        <c:choose>
            <c:when test="${optionType eq 'add'}">
                <a href="http://zzjg.${user.companyENLower}.com">组织架构</a> &gt;
            </c:when>
            <c:when test="${optionType eq 'edit'}">
                <a href="/">人力资源管理系统</a> &gt;
            </c:when>
            <c:otherwise>
                <a href="http://zzjg.${user.companyENLower}.com">组织架构</a> &gt;
            </c:otherwise>
        </c:choose>
    </div>
    <h1 class="f18 mt_10">申请转调<span class="ml_15 f12 noBold grey999">（标注“<span class="red">●</span>”为必填项）</span></h1>

    <c:choose>
        <c:when test="${optionType eq 'add'}">
            <form id="formTransferEmpAdd" action="/transfer/emp/add" method="post">
        </c:when>
        <c:when test="${optionType eq 'edit'}">
            <form id="formTransferEmpAdd" action="/transfer/emp/edit/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" method="post">
                <input name="id" value="${transfer.id}" hidden />
        </c:when>
        <c:otherwise>
            <form id="formTransferEmpAdd" action="/transfer/emp/add" method="post">
        </c:otherwise>
    </c:choose>

    <input type="hidden" name="h_pageType" id="h_pageType" value="${optionType}" />

    <c:if test="${optionType eq 'edit'}">
        <div class="boxWrapYellow mt_5 f14 center bold">
            打回原因：<span class="black noBold">${remark}</span>
        </div>
    </c:if>

    <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
        <tbody>
            <tr>
                <td class="tdTitle" width="120">转调员工：</td>
                <td class="request" width="20">●</td>
                <td>
                    <webplus:token/>
                    <input type="hidden" name="userCode" id="userCode" value="${transfer.userCode}" />
                    <input type="hidden" name="h_userName" id="h_userName" value="" />
                    <input type="hidden" name="oldOrgId" id="oldOrgId" value="${transfer.oldOrgId}" />
                    <input type="hidden" name="oldLevelId" id="oldLevelId" value="${transfer.oldLevelId}" />
                    <input type="hidden" name="oldPositionId" id="oldPositionId" value="${transfer.oldPositionId}" />
                    <input type="hidden" name="oldTitle" id="oldTitle" value="${transfer.oldTitle}" />
                    <input autocomplete="off" type="text" class="txtNew in_block tt160" name="userName" id="userName" popdiv="autoComp_user" placeholder="请输入姓名或工号" rule="required" autocomplete="off" value="${transfer.userName}" />

                    <c:if test="${optionType eq 'add'}">
                        <span class="ml_20 hideit" id="span_level_original">原职等职级：<b id="level_original"></b></span>
                    </c:if>
                    <c:if test="${optionType eq 'edit'}">
                        <span class="ml_20" id="span_level_original">原职等职级：<b id="level_original">${transfer.oldTitleDegree}-${transfer.oldLevelDegree} ${transfer.oldLevelName}</b></span>
                    </c:if>

                    <div id="autoComp_user" class="autoComplate"></div>
                </td>
            </tr>
            <tr>
                <td class="tdTitle">转入组织：</td>
                <td class="request">●</td>
                <td>
                    <select name="newOrgId" id="newOrgId" rule="required">
                       <option value="">请选择</option>
                        <c:if test="${optionType eq 'add'}">
                           <c:forEach items="${acceptOrgs}" var="org" varStatus="varStatus">
                               <option value="${org.id}" ${varStatus.first && varStatus.last ? 'selected=selected' : ''} transferType="${org.transferType}" transferTypeStr="${org.transferTypeStr}" >${org.orgName}</option>
                           </c:forEach>
                        </c:if>
                        <c:if test="${optionType eq 'edit'}">
                           <c:forEach items="${acceptOrgs}" var="org" varStatus="varStatus">
                               <option value="${org.id}" ${org.id == transfer.newOrgId ? 'selected=selected' : ''} transferType="${org.transferType}" transferTypeStr="${org.transferTypeStr}" >${org.orgName}</option>
                           </c:forEach>
                        </c:if>
                    </select>
                </td>
            </tr>
            <tr>
                <td class="tdTitle">转调类型：</td>
                <td class="request">●</td>
                <td>
                    <input type="hidden" name="transferType" id="transferType" />
                    <b id="span_transfer_type"></b>
                </td>
            </tr>
            <tr id="trTransferMethod" class="hideit">
                <td class="tdTitle">转调情况：</td>
                <td class="request">●</td>
                <td>
                    <label>
                        <input type="radio" name="transferState" ${transfer.transferState == 2 ? 'checked' : ''} value="2" rule="required" />被动发生转调</label>
                    <label class="ml_20">
                        <input type="radio" name="transferState" ${transfer.transferState == 1 ? 'checked' : ''} value="1" rule="required" />由经纪人主动提出</label>
                    <div class="bg_yellow red pd_6 pl_15 mt_10 lineH180 hideit" id="transfer_method_prompt">
                    </div>
                    <div class="mt_20">
                        <p class="f12 grey999 noBold">被动发生转调：分行发生变动（如经理离职、降级、晋升及调岗等），以及经纪人被安排到新分行等情况。</p>
                        <p class="f12 grey999 noBold mt_5">经纪人主动提出：所在分行未发生变动，经纪人主动提出的调动。</p>
                    </div>
                </td>
            </tr>

            <c:if test="${optionType eq 'add'}">
                <tr id="trLevel" class="hideit">
                    <td class="tdTitle">转调后职等职级：</td>
                    <td class="request">●</td>
                    <td>
                        <select name="newSerialId" id="js_serialId" rule="required" groupby="level">
                            <option value="">请选择职系</option>
                        </select>
                        <select name="newTitleId" id="js_titleId" rule="required" groupby="level">
                            <option value="">请选择职等</option>
                        </select>
                        <select name="newLevelId" id="js_levelId" rule="required" groupby="level">
                            <option value="">请选择职级</option>
                        </select>
                    </td>
                </tr>
            </c:if>

            <c:if test="${optionType eq 'edit'}">
                <tr id="trLevel" class="hideit">
                    <td class="tdTitle">转调后职等职级：</td>
                    <td class="request">●</td>
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
            </c:if>

            <tr id="trPosition" class="hideit">
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
            <tr id="trUserTitle" class="hideit">
                <td class="tdTitle">转调后头衔：</td>
                <td class="request">●</td>
                <td>
                    <input type="text" name="newTitle" id="js_userTitle" class="txtNew" rule="required" value="${transfer.newTitle}" />
                </td>
            </tr>
    	</tbody>
    </table>
    <p class="txtRight mt_15 clearfix">
        <a href="javascript:;" class="btn-normal btn-blue right ml_15 js_submit">提交</a>
        <c:choose>
            <c:when test="${optionType eq 'add'}">
                <a href="javascript:window.close();" class="btn-normal btn-silver right">取消</a>
            </c:when>
            <c:when test="${optionType eq 'edit'}">
                <a href="javascript:history.back();" class="btn-normal btn-silver right">取消</a>
            </c:when>
            <c:otherwise>
                <a href="javascript:window.close();" class="btn-normal btn-silver right">取消</a>
            </c:otherwise>
        </c:choose>

    </p>
    </form>

<div class="warningPop" id="layer_warning">
    <div class="warningT clearfix">
        <p class="t">危险操作</p>
    </div>
    <div class="warningC">
        <p>员工转调后，<b>数据不可恢复</b>！</p>
        <p>若操作有误，<b>全部责任由操作人承担</b>！</p>
        <p>请再一次确认员工姓名</p>
        <p class="mt_10"><input type="text" class="txtNew tt280" id="confirmName" placeholder="输入员工名字" style="width:330px"></p>
        <p class="mt_10"><a href="javascript:;" class="btnOpH34 h34Red opH34 in_block" id="btnConfirmName" style="width:286px; text-align:center">确定是此员工</a></p>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,autocomplete"/>
    <jsp:param name="js" value="empTransfer/transferEmpForm,empTransfer/transfer_levelChange"/>
</jsp:include>