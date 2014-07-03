<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="列表"/>
    <jsp:param name="duiCss" value="treeNew"/>
</jsp:include>

<div class="contentRight">
    <div class="container left p100">
        <!--搜索条件 begin-->
        <form id="searchForm" action="/transfer/emp/list" method="get">
            <div class="boxWrapBlue pd_10 mt_15 clearfix">
                <div class="left">
                    <table width="620" border="0" cellspacing="0" cellpadding="3" border="0">
                        <tbody>
                        <tr>
                            <td width="60" align="right" nowrap>异动类型：</td>
                            <td width="150">
                                <select name="ydType">
                                    <option value="">请选择</option>
                                    <option value="转调" selected="selected">转调</option>
                                </select>
                            </td>
                            <td width="60" align="right" nowrap>申请组织：</td>
                            <td width="150">
                                <input type="hidden" id="h_deptId" name="newOrgId" value="" />
                                <input type="text" class="selInput w184" id="selectDept" name="newOrgName" value="${empTransferSearch.newOrgName eq null ? '请选择' : empTransferSearch.newOrgName}" deptid="" readonly="readonly" pid="">
                                <div id="popup_tree" class="popup_tree">
                                    <input type="text" class="txtNew" id="autoCByTree" popdiv="autoComp_keyword"><!--自动完成输入框-->
                                    <div id="autoComp_keyword" class="autoComplete_tree" style="display: none;"></div>
                                    <!--自动完成选项-->
                                    <div id="treeWrap" class="treeWrap"></div>
                                    <!--树-->
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">申请日期：</td>
                            <td>
                                <input type="text" name="createStartTime" class="txtDate" readonly="readonly" value="<fmt:formatDate value="${empTransferSearch.createStartTime}" pattern="yyyy-MM-dd"/>"/>
                                    -
                                <input type="text" name="createEndTime" class="txtDate" readonly="readonly" value="<fmt:formatDate value="${empTransferSearch.createEndTime}" pattern="yyyy-MM-dd"/>"/>
                            </td>
                            <td align="right">状态：</td>
                            <td>
                                <select name="approvalStatus">
                                    <option ${empTransferSearch.approvalStatus == null || empTransferSearch.approvalStatus == 0 ? 'selected=selected' : ''} value="">请选择</option>
                                    <option ${empTransferSearch.approvalStatus == 1 || empTransferSearch.approvalStatus == 5 ? 'selected=selected' : ''} value="1">审批中</option>
                                    <option ${empTransferSearch.approvalStatus == 2 ? 'selected=selected' : ''} value="2">待处理交接</option>
                                    <%--<option ${empTransferSearch.approvalStatus == 5 ? 'selected=selected' : ''} value="5">人事审批</option>--%>
                                    <option ${empTransferSearch.approvalStatus == 3 ? 'selected=selected' : ''} value="3">已完成</option>
                                    <option ${empTransferSearch.approvalStatus == 4 ? 'selected=selected' : ''} value="4">打回修改</option>
                                    <option ${empTransferSearch.approvalStatus == -1 ? 'selected=selected' : ''} value="-1">已作废</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td align="right">关键字：</td>
                            <td colspan="3">
                                <input name="keyword" value="${empTransferSearch.keyword}" type="text" class="txtNew tt280" placeholder="申请单号、申请人、异动员工工号、姓名"/>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="right center bdL_ccc" style="width:80px; height:90px;">
                    <a href="javascript:;" id="searchBtn" class="btn-small btn-blue in_block" style="margin-top:30px">查询</a>
                </div>
            </div>
        </form>


        <!--搜索条件 end-->
        <p class="mt_10"><b class="red">${paginate.totalCount}</b>&nbsp;条符合要求的结果</p>
        <table width="100%" cellspacing="0" class="tableListNew tableNarrow mt_10">
            <tbody>
            <tr class="trHead">
                <td width="60">异动类型</td>
                <td width="70">申请日期</td>
                <td width="150">申请单号</td>
                <td width="60">申请人</td>
                <td width="80">申请组织</td>
                <td width="100">异动员工</td>
                <td width="80">状态</td>
                <td width="80" nowrap>操作</td>
            </tr>
            <c:forEach items="${paginate.pageList}" var="transfer">
                <tr>
                    <td>${transfer.ydType}</td>
                    <td><fmt:formatDate value="${transfer.createTime}" pattern="yyyy-MM-dd"/></td>
                    <td><a href="/transfer/emp/detail/${transfer.id}/${transfer.processInsId}" target="_blank">${transfer.transferNo}</a></td>
                    <td>${transfer.creatorName}</td>
                    <td>${transfer.newOrgName}</td>
                    <td><a href="http://gzz.${user.companyENLower}.com/employee360/${transfer.userCode}" target="_blank">${transfer.userName}(${transfer.userCode})</a></td>
                    <%--<td><a href="/employee/${transfer.userCode}/details" target="_blank">${transfer.userName}(${transfer.userCode})</a></td>--%>
                    <td  id="td_approvalStatus_${transfer.id}">
                        <c:choose>
                            <c:when test="${transfer.approvalStatus == 4}">
                                <b class="red">${transfer.approvalStr}</b><br/>${transfer.handlerName}
                            </c:when>
                            <c:when test="${transfer.approvalStatus == -1}">
                                <b class="grey999">${transfer.approvalStr}</b>
                            </c:when>
                            <c:when test="${transfer.approvalStatus == 3}">
                                <b class="green">${transfer.approvalStr}</b>
                            </c:when>
                            <c:otherwise>
                                <b class="orange">${transfer.approvalStr}</b><br/>${transfer.handlerName}
                            </c:otherwise>
                        </c:choose>
                    </td>

                    <td id="td_option_${transfer.id}">
                        <c:if test="${transfer.operateUrl != null && transfer.approvalStatus != 3}">
                            <appel:checkEmpTransferPrivilege url="${transfer.operateUrl}">

                                <c:if test="${transfer.approvalStatus == 4}">
                                    <a href="/transfer/emp/approval/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" class="btn-small btn-silver in_block">编辑</a>
                                    <a href="#" transferId="${transfer.id}" taskId="${transfer.taskId}" class="btn-small btn-red in_block js_cancel">作废</a>
                                </c:if>
                                <c:if test="${transfer.approvalStatus != 4}">
                                    <webplus:token/>
                                    <a href="/transfer/emp/approval/${transfer.id}/${transfer.processInsId}/${transfer.taskId}" class="btn-small btn-silver in_block">处理</a>
                                </c:if>
                            </appel:checkEmpTransferPrivilege>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="tabConInfoBtm mt_10">
            <app:paginate/>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="autocomplete,dui_treePanel,WdatePicker"/>
    <jsp:param name="ajs" value="http://dui.${user.companyENLower}.com/public/json/${user.companyENLower}/${user.companyENLower}All.js"/>
</jsp:include>

<script type="text/javascript">
    $(function () {
        //初始化组织架构选择器(弹层，单选)
        selectDeptInit({
            canDept: true,  //部门可选
            orgStatus: '0,1', //组织
            dataSource: All,
            searchConfig: { //自动完成
                treeType: 'dooiooAll'
            },
            callbacksel: 'fnCallBack'
        });

        //作废按钮事件
        $("a.js_cancel").live("click", function () {
            if (window.confirm("确认删除此转调单")) {
                var id = $(this).attr("transferId");//记录id
                var taskId = $(this).attr("taskId");
                //执行删除操作，成功后刷新
                $.post("/transfer/emp/destroy/" + id + "/" + taskId, function(data){
                    if(data.status == "ok") {
                        $("#td_option_" + id).html("");
                        $("#td_approvalStatus_" + id).html("<b class='grey999'>已作废</b>");
                    } else {
                        alert(data.message);
                    }
                })
            }
        });

        //搜索
        $("#searchBtn").click(function(){
            $("#searchForm").submit();
        });

    });

    function fnCallBack(){//组织架构树的选中回调
        $("#h_deptId").val($("#selectDept").attr("deptId"));
    }

</script>