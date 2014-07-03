<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="异动信息"/>
</jsp:include>
<style type="text/css">
    .tableListNew td {
        line-height: 18px;
    }
</style>

<div class="clearfix mt_5">
    <jsp:include page="/WEB-INF/jsp/common/employee_detail_nav.jsp">
        <jsp:param value="changes" name="nav"/>
    </jsp:include>
    <appel:checkPrivilege url="changes_add">
        <div class="right mb_10 mt_5">
            <select id="js_add_change">
                <option value="">添加异动</option>
                <option value="/changes/${userCode}/newRegular">转正</option>
                <option value="/changes/${userCode}/newAppoint">任命</option>
                <option value="/changes/${userCode}/newRelevel">转调</option>
                <option value="/changes/${userCode}/newPromotion">晋升</option>
                <option value="/changes/${userCode}/newDemotion">降职</option>
                <option value="/changes/${userCode}/newRehire">回聘</option>
                <option value="/changes/${userCode}/newLeave">离职</option>
                <option value="/changes/${userCode}/dealData">数据处理</option>
            </select>
        </div>
    </appel:checkPrivilege>

    <table boder="0" cellpadding="0" cellspacing="0" width="100%" class="tableListNew tableNarrow mt_15">
        <tbody>
        <tr class="trHead">
            <td width="70" nowrap>生效日期</td>
            <td width="60" nowrap>异动类型</td>
            <td>原岗位信息</td>
            <td>新岗位信息</td>
            <td width="50" nowrap>状态</td>
            <td width="40" nowrap>操作</td>
        </tr>
        <c:forEach var="change" items="${changes}" varStatus="vs">
            <tr>
                <td>
                    <fmt:formatDate value="${change.activeDate}" pattern="yyyy-MM-dd"/>
                </td>
                <td>${change.changeType}</td>
                <td>
                    <p>
                        部门：${change.oldOrgName}
                        <br>职系：${change.oldSerialName}
                        <br>职等职级：${change.oldTitleLevelFullName}
                        <br>岗位：${change.oldPositionName}
                        <br>任职状态：${change.oldStatus}
                    </p>
                </td>
                <td>
                    部门：${change.newOrgName}
                    <br>职系：${change.newSerialName}
                    <br>职等职级：${change.newTitleLevelFullName}
                    <br>岗位：${change.newPositionName}
                    <br>任职状态：${change.newStatus}
                    <c:choose>
                        <c:when test="${change.changeType=='离职'}">
                            <br>离职分类：${change.leaveType}
                            <br>离职原因：${change.leaveReason}
                        </c:when>
                    </c:choose>
                    <br>创建人：${change.creatorName},创建时间：<fmt:formatDate value="${change.createTime}"
                                                                       pattern="yyyy年M月d日 HH:mm"/>
                    <c:if test="${change.status==-2 || change.status==-1 }">
                        <br>操作人：${change.updatorName},操作时间：<fmt:formatDate value="${change.updateTime}"
                                                                           pattern="yyyy年M月dd日 HH:mm"/>
                    </c:if>
                </td>

                <c:choose>
                <c:when test="${change.status==1 }">
                    <td>
                        <span class="green bold">已生效</span>
                    </td>
                    <td> &nbsp;
                        <c:if test="${change.canRollback && change.creator != 80001}">
                            <appel:checkPrivilege url="changes_rollback">
                                <a href="/changes/${change.userCode}/${change.changeId}/rollback"
                                   onclick="return confirm('您确认要回滚此条异动记录吗？');"
                                   class="btn-small btn-silver in_block">回滚</a>
                            </appel:checkPrivilege>
                        </c:if>
                    </td>
                </c:when>
                <c:when test="${change.status==0 }">
                    <td>
                        <span class="red bold">未生效</span>
                    </td>
                    <td>
                        <appel:checkPrivilege url="changes_delete">
                            <a href="/changes/${change.userCode}/${change.changeId}/doDelete"
                               onclick="return confirm('您确认要删除此条异动记录吗？');" class="btn-small btn-silver in_block">删除</a>
                        </appel:checkPrivilege>
                    </td>
                </c:when>
                <c:when test="${change.status==-2}">
                <td>
                    <span class="red bold">已回滚</span>
                </td>
                <td>&nbsp;
                <td>
                    </c:when>
                    <c:when test="${change.status==-1}">
                <td>
                    已删除
                </td>
                <td>&nbsp;
                <td>
                    </c:when>
                    </c:choose>

            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<p class="clearfix mt_15">
    <span class="f12 grey999 noBold">注：由于新老系统交替导致2013年6月之前所有异动信息遗失，无法查询。</span>
</p>

<div class="popLayer w840" id="personChangePop">
    <div class="popTitle clearfix">
        <h1 id="js_changeType">添加异动</h1>

        <div class="cls">
            <a href="#" class="xx"></a>
        </div>
    </div>
    <div id="formSection">

    </div>
</div>
<script type="text/javascript">
    <!--
    $(function () {
        $("#js_add_change").change(function () {
            var postUrl = $(this).val();
            if (postUrl == "") {
                return;
            }
            ajaxGet({
                url: '/changes/${userCode}/validateCanAddChange',
                async: false,
                ok: function (data) {
                    $.post(postUrl, function (page) {
                        $("#formSection").html(page);
                        $("#formSection").find(".txtDate").date_input();
                        showPopupDiv($('#personChangePop'));
                        return false;
                    });
                },
                fail: function (data) {
                    alert("无法连续添加异动，此人有异动记录未生效。");
                }
            });
        });
        $(".xx").click(function () {
            hidePopupDiv($(parent.document.body).find('#personChangePop'));
            return false;
        });

    });
    //-->
</script>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param value="changes/queryChange,portal,select" name="js"/>
    <jsp:param name="duiJs" value="validation,WdatePicker,autocomplete"/>
</jsp:include>
