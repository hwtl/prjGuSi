<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="员工列表-人事管理-人力资源管理系统"/>
    <jsp:param value="http://dui.${user.companyENLower}.com/public/css/treeNew.css" name="ajCss"/>
</jsp:include>
<style type="text/css">
    .excelOutput {margin:0 auto}
    .excelOutput li{float:left; width:110px; padding:5px 0}
    .infoTable td:nth-child(2n+1){padding:10px 0;text-align:right;}
</style>
<form id="employeeFrm" name="employeeFrm" method="post">
    <input type="hidden" name="advancedSearch" value="${employeeSearch.advancedSearch}" id="_advanced"/>
    <input type=hidden name="excelHeads" id="h_exportExcel" />
    <input type=hidden name="exportKey" id="h_exportExcelKey" />
    <table border="0" cellspacing="0" cellpadding="0" class="tableFormNew">
        <tbody>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" width="115" nowrap="">部门：</td>
            <td class="" width="600">
                <a href="javascript:;" class="btnOpH24 h24Silver in_block" onclick="showPopupDiv($('#layer_selDept')); return false;" id="btnSelDept">选择部门</a>
                <span id="js_deptShow" class="in_block showSelContent grey999 ml_10"></span>
                <input type="hidden" name="orgIds" value="${employeeSearch.orgIds}"/>
            </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">职位：</td>
            <td class="">
                <a href="javascript:;" class="btnOpH24 h24Silver in_block" onclick="showPopupDiv($('#layer_selPos')); return false;">选择职位</a>
                <span id="js_posShow" class="in_block showSelContent grey999 ml_10"></span>
                <input type="hidden" name="positionIds" value="${employeeSearch.positionIds}"/>
            </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">任职状态：</td>
            <td class="">
				<apptl:checkbox checkName="employeeStatus" newValue="0" checkValue="${employeeSearch.employeeStatus}" checkText="试用期"/>
				<apptl:checkbox checkName="employeeStatus" newValue="1" checkValue="${employeeSearch.employeeStatus}" checkText="正式"/>
				<apptl:checkbox checkName="employeeStatus" newValue="2" checkValue="${employeeSearch.employeeStatus}" checkText="离职"/>
				<apptl:checkbox checkName="employeeStatus" newValue="4" checkValue="${employeeSearch.employeeStatus}" checkText="申请离职"/>
                <apptl:checkbox checkName="black" newValue="1" checkValue="${employeeSearch.black}" checkText="黑名单"/>
            </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">报到状态：</td>
            <td class="">
				<apptl:checkbox checkName="employeeStatus" newValue="7" checkValue="${employeeSearch.employeeStatus}" checkText="总部待报到"/>
				<apptl:checkbox checkName="employeeStatus" newValue="8" checkValue="${employeeSearch.employeeStatus}" checkText="门店待报到"/>
				<apptl:checkbox checkName="employeeStatus" newValue="9" checkValue="${employeeSearch.employeeStatus}" checkText="总部未报到"/>
				<apptl:checkbox checkName="employeeStatus" newValue="10" checkValue="${employeeSearch.employeeStatus}" checkText="门店未报到"/>
            </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">入职时间：</td>
            <td class="">
                <input name="joinDateStart" type="text" class="txtDate" value="${employeeSearch.joinDateStart}" readonly="readonly">&nbsp;至&nbsp;
                <input name="joinDateEnd" type="text" class="txtDate" value="${employeeSearch.joinDateEnd}" readonly="readonly">
            </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">离职时间：</td>
            <td class="">
                <input name="leaveDateStart" type="text" class="txtDate" value="${employeeSearch.leaveDateStart}" readonly="readonly">&nbsp;至&nbsp;
                <input name="leaveDateEnd" type="text" class="txtDate" value="${employeeSearch.leaveDateEnd}" readonly="readonly">
            </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">身份证出生日期：</td>
            <td class="">
                <select name="officialYear">
                    <option value="">不选择</option>
                    <appel:numberOption fromIndex="1950" toIndex="2020" selected="${employeeSearch.officialYear}"/>
                </select>&nbsp;年
                <select name="officialMonth" class="ml_10">
                    <option value="">不选择</option>
                    <appel:numberOption fromIndex="1" toIndex="12" selected="${employeeSearch.officialMonth}"/>
                </select>&nbsp;月
                <select name="officialDay" class="ml_10">
                    <option value="">不选择</option>
                    <appel:numberOption fromIndex="1" toIndex="31" selected="${employeeSearch.officialDay}"/>
                </select>&nbsp;日 </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">生日：</td>
            <td class="">
                <input type="text" name="birthDateFrom" id="birthDateFrom"
                yearFrom="${nowYear}" yearTo="${nowYear}" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${employeeSearch.birthDateFrom}"/>"
                comparedateto="birthDateTo"
                 class="txtDate" readonly="readonly">&nbsp;&nbsp;至&nbsp;&nbsp;
               <input type="text" name="birthDateTo" yearFrom="${nowYear}" value="<fmt:formatDate pattern="yyyy-MM-dd" value="${employeeSearch.birthDateTo}"/>"
                yearTo="${nowYear}" comparedatefrom="birthDateFrom"  id="birthDateTo"  class="txtDate" readonly="readonly">
               </td>
        </tr>

        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">招聘渠道：</td>
            <td class="">
                <select name="comeFrom">
                    <option value="">请选择</option>
                    <c:forEach var="channel" items="${channels}">
                        <option value="${channel.optionCode}" ${channel.optionCode == employeeSearch.comeFrom ? 'selected=true' : ''}>${channel.optionValue}</option>
                    </c:forEach>
                </select>
            </td>
        </tr>

        <%--<tr class="js_advSearchField hideit">--%>
        <tr class="hideit">
            <td class="tdTitle" nowrap="">星座：</td>
            <td class="">
                <select name="constellation">
                    <option value="">请选择</option>
                    <c:forEach var="constellation" items="${constellations}">
                        <c:choose>
                            <c:when test="${employeeSearch.constellation == constellation.optionCode }">
                            <option value="${constellation.optionCode}" selected="selected">${constellation.optionValue}</option>
                        </c:when>
                            <c:otherwise>
                                <option value="${constellation.optionCode}">${constellation.optionValue}</option>
                            </c:otherwise>
                        </c:choose>
                    </c:forEach>
                </select></td>
        </tr>

        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">学历：</td>
            <td>
                <c:forEach var="degree" items="${degrees}">
                    <apptl:checkbox checkName="lastDegree" newValue="${degree.optionCode}" checkValue="${employeeSearch.lastDegree}" checkText=" ${degree.optionValue}"/>
                </c:forEach>
            </td>
        </tr>

        <tr class="js_advSearchField hideit">
            <td class="tdTitle" nowrap="">人员标签：</td>
            <td>
                <c:forEach var="tag" items="${tagList}">
                    <apptl:checkbox checkName="tags" newValue="${tag.optionCode}" checkValue="${employeeSearch.tags}" checkText=" ${tag.optionValue}"/>
                </c:forEach>
            </td>
        </tr>

        <tr>
            <td class="tdTitle">关键字：</td>
            <td>
                <input type="text" value="${employeeSearch.keyWords}" name="keyWords" id="_keyWords" class="txt tt450 textH24" placeholder="可输入姓名、工号、手机号码、分行名称、职位、学校、曾工作单位">
                <a herf="javascript:;" id="searchBtn"  class="btn-small btn-blue in_block ml_20 js_hrefs">查询</a>
                <appel:checkPrivilege url="employee_advanced_search">
                    <a href="javascript:;" id="searchReset" class="btn-small in_block btn-silver adv_reset mr_10 hideit">重置</a>
                    <a href="javascript:;" id="js_btnAdvanceSearch" class="ml_10 mt_5 in_block js_btnAdvanceSearch" target="_self">高级搜索</a>
                </appel:checkPrivilege>
            </td>
        </tr>
        <tr class="js_advSearchField hideit">
            <td colspan="2" class="txtRight"><a href="javascript:;" class="js_btnHiddenSearch">收起</a></td>
        </tr>

        <input type="hidden" id="archiveStatus" name="archiveStatus" value="${employeeSearch.archiveStatus}">

        </tbody>
    </table>
</form>

<appel:checkPrivilege url="archives_sure">
    <div class="navTab2th mt_15 clearfix" id="archiveStatusSearch">
        <a href="javascript:;" class="${empty employeeSearch.archiveStatus? 'current' : ''}" status="">全部</a>
        <a href="javascript:;" class="${employeeSearch.archiveStatus == '0' ? 'current' : ''}" status="0">待审核<span id="emplist_detailUnCheckNum"></span></a>
        <a href="javascript:;" class="${employeeSearch.archiveStatus == '-1' ? 'current' : ''}" status="-1">待完善</a>
    </div>
</appel:checkPrivilege>


<p class="clearfix mt_10">
    <span class="grey999 left mt_5"><b class="red">${paginate.totalCount}</b>&nbsp;符合要求的员工</span>
    <appel:checkPrivilege url="hrm_employee_info_export">
        <a href="javascript:;" class="btn-small btn-silver in_block right" id="js_export">导出excel</a>
    </appel:checkPrivilege>
    <appel:checkPrivilege url="hrm_employee_add">
        <a href="/employee/add" target="_blank" class="btn-small btn-green in_block mr_5 right js_hrefs">新增员工</a>
    </appel:checkPrivilege>
</p>

<table width="100%" cellspacing="0" class="tableListNew tableNarrow mt_10">
    <tbody>
    <tr class="trHead txtRight">
        <td align="left" width="60">工号</td>
        <td align="left" width="80">姓名</td>
        <td align="left" width="100">组织名称</td>
        <td align="left" width="100">岗位名称</td>
        <td align="rigth" width="50">职等职级</td>
        <td align="center" width="70">入职日期</td>
        <td align="center" width="80">状态</td>
        <td align="right" width="100" nowrap>操作</td>
    </tr>
    <c:forEach var="employee" items="${paginate.pageList}">
    <tr userCode="${employee.userCode}" employeeName="${employee.userNameCn}">
        <td align="left">${employee.userCode} </td>
        <td align="left">${employee.userNameCn} </td>
        <td align="left">${employee.orgName}</td>
        <td align="left">${employee.positionName}</td>
        <td align="right">
            <c:if test="${not empty employee.titleDegree}">
                ${employee.titleDegree}-${employee.levelDegree}
            </c:if>
        </td>
        <td align="center"><fmt:formatDate value="${employee.joinDate}" pattern="yyyy-MM-dd"/> </td>
        <td align="center">
            <span id="${employee.userCode}_status" class="${employee.status == '离职' || employee.status == '申请离职' ? 'red' : ''}">
                <c:choose>
                    <c:when test="${employee.status == '总部待报到' || employee.status == '门店待报到'}">
                        ${employee.registerStatus}(${employee.status})
                    </c:when>
                    <c:otherwise>
                        ${employee.status}
                    </c:otherwise>
                </c:choose>
            </span>
            <c:if test="${not empty employee.isBlack && employee.isBlack ==1}">
                <p class="red">黑名单</p>
            </c:if>
        </td>
        <td align="right">
            <c:if test="${employee.status == '总部未报到'}">
                <appel:checkPrivilege url="employee_register">
                    <a class="btn-small btn-red in_block mt_5 js_dealRecover js_recover_${employee.userCode}" userStatus="${employee.status}" userCode="${employee.userCode}" target="_blank" href="javascript:;">恢复总部待报到</a>
                </appel:checkPrivilege>
            </c:if>
            <c:if test="${employee.status == '门店未报到'}">
                <appel:checkPrivilege url="employee_register">
                    <a class="btn-small btn-red in_block mt_5 js_dealRecover js_recover_${employee.userCode}" userStatus="${employee.status}" userCode="${employee.userCode}" target="_blank" href="javascript:;">恢复门店待报到</a>
                </appel:checkPrivilege>
            </c:if>
            <c:if test="${employee.status == '总部待报到'}">
                <appel:checkPrivilege url="employee_register">
                    <a class="js_hrefs btn-small btn-green in_block mt_5 js_dealReport js_deal_${employee.userCode}" id="${employee.userCode}" target="_blank" href="javascript:;" ng-click="dealReport($event)">处理总部待报到</a>
                </appel:checkPrivilege>
            </c:if>

            <c:choose>
                <c:when test="${employeeSearch.archiveStatus == '0'}">
                    <appel:checkPrivilege url="archives_sure">
                        <a  class="btn-small btn-silver in_block mt_5" target="_blank" href="/archives/${employee.userCode}">审核</a>
                    </appel:checkPrivilege>
                </c:when>
                <c:otherwise>
                    <%--b详情按钮--%>
                    <appel:checkPrivilege url="hrm_employee_view">
                        <a  class="js_hrefs btn-small btn-silver in_block mt_5" id="${employee.userCode}" target="_blank" tabname="${employee.userNameCn}_详情"  href="/employee/${employee.userCode}/details">详情</a>
                    </appel:checkPrivilege>
                    <%--e详情按钮--%>
                </c:otherwise>
            </c:choose>


             <c:if test="${not empty employee.currentBlackId}">
              <appel:checkPrivilege url="remove_employee_from_black">
                	<a  class="js_hrefs btn-small btn-silver in_block mt_5" id="${employee.userCode}" target="_blank" tabname="移除黑名单"  href="/employee/${employee.currentBlackId}/rollback">移除黑名单</a>
          	  </appel:checkPrivilege>
            </c:if>
            <c:if test="${(empty employee.isBlack || employee.isBlack ==0) && employee.status=='离职'}">
              <appel:checkPrivilege url="add_employee_to_black">
                	<a  class="js_hrefs btn-small btn-silver in_block mt_5" id="${employee.userCode}" target="_blank" tabname="加入黑名单"  href="/employee/${employee.userCode}/addBlack">加入黑名单</a>
          	  </appel:checkPrivilege>
            </c:if>
            <c:if test="${employee.status != '离职' && employee.status != '总部待报到' && employee.status != '总部未报到' && employee.status != '门店待报到' && employee.status != '门店未报到'}">
                <appel:checkPrivilege url="usercode_enabled">
                    <c:if test="${employee.shielded}">
                        <a href="javascript:;" class="btn-small h34Green btn-silver in_block mt_5 js_toggleUser" shielded="true" id="${employee.userCode}">启用账户</a>
                    </c:if>
                </appel:checkPrivilege>
                <appel:checkPrivilege url="usercode_disabled">
                    <c:if test="${employee.shielded == false}">
                        <a href="javascript:;" class="btn-small h34Red btn-silver in_block mt_5 js_toggleUser" shielded="false" id="${employee.userCode}">屏蔽账户</a>
                    </c:if>
                </appel:checkPrivilege>
                <appel:checkPrivilege url="pms_base">
                    <a tabname="${employee.userNameCn}_配置角色" target="_blank" href="/privilege/userSet?key=${employee.userCode}" class="js_hrefs btnOpH24 h24Green in_block mt_5">配角色</a>
                </appel:checkPrivilege>
                <c:if test="${user.userCode==config.superAdminUserCode}">
                    <a tabname="${employee.userNameCn}_配置系统权限" target="_blank" href="/privilege/systemSet?userCode=${employee.userCode}" class="js_hrefs btnOpH24 h24Green in_block mt_5">配系统权限</a>
                </c:if>
            </c:if>
        </td>
    </tr>
    </c:forEach>
    </tbody>
</table>
    <div class="tabConInfoBtm mt_10" style="padding:0px">
        <appel:paginate/>
    </div>

<!--报到处理 弹层 begin -->
<div class="popLayer w840" id="report_layer_form">
    <div class="popTitle clearfix">
        <h1>员工报到处理</h1>
        <div class="cls"><a href="#" class="xx" onclick="hidePopupDiv($('#report_layer_form')); return false"></a></div>
    </div>
    <div class="popCon">
        <div class="form">
            <div>
                <table cellspacing="0" cellpadding="0" border="0" width="100%" class="mb_15">
                    <tr>
                        <td width="95"><label><input type="radio" name="reportState" ng-model="textShow" value="0" ng-click="resetErrorMsg()" />总部已报到</label></td>
                        <td width="95"><label><input type="radio" name="reportState" ng-model="textShow" value="1" ng-click="resetErrorMsg()"  />总部未报到</label></td>
                        <td width="570">
                            <form id="noReportForm" action="">
                                <input type="text" class="w320 js_notReportReason" rule="required" name="notReportReason" placeholder="未报到原因，最多输入20个字符" maxlength="20" ng-show="textShow" />
                            </form>
                        </td>
                    </tr>
                </table>
                <div class="boxWrapBlue pd_10 mt_5">
                    <form action="" id="reportForm">
                        <input type="hidden" value="" id="js_changeflag" name="changeFlag" ng-model="changeFlag" />
                        <table cellspacing="0" cellpadding="0" border="0" class="infoTable js_infoTable">
                            <tr>
                                <td width="80">姓名：</td>
                                <td><input type="text" ng-disabled="1==textShow" rule="required" name="name" ng-model="personInformation.userNameCn" ng-change="listenChange(personInformation.userNameCn)" /></td>
                                <td width="100">英文名：</td>
                                <td><input type="text" ng-disabled="1==textShow" name="enName" ng-model="personInformation.userNameEn" ng-change="listenChange(personInformation.userNameEn)" /></td>
                                <td>性别：</td>
                                <td>
                                    <label><input type="radio" name="gender" rule="required" ng-disabled="1==textShow" ng-model="personInformation.sex" value="男" ng-change="listenChange(personInformation.sex)" />男</label>
                                    <label><input type="radio" name="gender" ng-disabled="1==textShow" ng-model="personInformation.sex" value="女" ng-change="listenChange(personInformation.sex)" />女</label>
                                </td>
                            </tr>
                            <tr>
                                <td>身份证号码：</td>
                                <td><input type="text" ng-disabled="1==textShow" rule="required idcard" name="credential" ng-model="personInformation.credentialsNo" ng-change="listenChange(personInformation.credentialsNo)" /></td>
                                <td>手机号码：</td>
                                <td><input type="text" ng-disabled="1==textShow" rule="required moblie" name="phone" ng-model="personInformation.mobilePhone" ng-change="listenChange(personInformation.mobilePhone)" /></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>行业经验：</td>
                                <td>
                                    <label><input type="radio" name="experience" rule="required" ng-disabled="1==textShow" ng-model="personInformation.experience" value="有" ng-change="listenChange(personInformation.experience)" />有</label>
                                    <label><input type="radio" name="experience" ng-disabled="1==textShow" ng-model="personInformation.experience" value="无" ng-change="listenChange(personInformation.experience)" />无</label>
                                </td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>组织：</td>
                                <td ng-bind="personInformation.orgName"></td>
                                <td>职系：</td>
                                <td ng-bind="personInformation.serialName"></td>
                                <td>职等职级：</td>
                                <td>{{personInformation.titleDegree}} - {{personInformation.levelDegree}} {{personInformation.levelFull}}</td>
                            </tr>
                            <tr>
                                <td>岗位：</td>
                                <td ng-bind="personInformation.positionName"></td>
                                <td>头衔：</td>
                                <td ng-bind="personInformation.userTitle"></td>
                                <td>头衔英文名：</td>
                                <td ng-bind="personInformation.eUserTitle"></td>
                            </tr>
                            <tr>
                                <td>入职时间：</td>
                                <td>{{personInformation.joinDate | date:'yyyy-MM-dd'}}</td>
                                <td>任职状态：</td>
                                <td ng-bind="personInformation.status"></td>
                                <td></td>
                                <td></td>
                            </tr>
                            <tr>
                                <td>招聘渠道：</td>
                                <td ng-bind="personInformation.comeFromName"></td>
                                <td></td>
                                <td></td>
                                <td></td>
                                <td></td>
                            </tr>
                        </table>
                    </form>
                </div>

            </div>
        </div>
    </div><br><br>
    <div class="popBtn">
        <a href="javascript:;" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#report_layer_form')); return false">关闭</a>
        <a href="javascript:;" class="btnOpH24 h24Blue in_block js_qudaoSubmit" ng-click="qudaosubmit()">提交</a>
    </div>
</div>
<!--报到处理 弹层 end -->

<!-- 部门选择弹层 -->
<div id="layer_selDept" class="popLayer w600">
    <div class="popTitle clearfix">
        <h1>选择部门</h1>
        <div class="cls"><a onclick="hidePopupDiv($('#layer_selDept')); return false" class="xx" href="javascript:;"></a></div>
    </div>

    <%--b 筛选组织状态--%>
    <p class="center">
        <label><input type="checkbox" name="" value="" id="showPauseDept">看暂停组织</label>
        <label><input type="checkbox" name="" value="" class="ml_20" id="showStopDept">看停用组织</label>
        <label><input type="checkbox" name="" value="" class="ml_20" id="showTempDept">看临时组织</label>
    </p>
    <%--e 筛选组织状态--%>

    <div class="popCon clearfix pd_20" style="">
        <div class="bd_ddd left ml_10">
            <div class="bg_eee pd_10">组织架构(点击组织名称选择)</div>

            <!--begin 适用的组织:树 -->
            <div style="width:540px; height:250px; overflow:hidden; overflow-y:auto; position:relative" class="mt_10" id="treeWrap">
            </div>
            <!--end 适用的组织:树 -->
        </div>
    </div>
    <div class="popBtn">
        <a href="javascript:;" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#layer_selDept'))">取消</a>
        <a href="javascript:;" class="btnOpH24 h24Blue in_block ml_10" onclick="confirmDept();">确定</a>
    </div>
</div>
<!-- 职位选择弹层 -->
<div id="layer_selPos" class="popLayer w320">
    <div class="popTitle clearfix">
        <h1>选择职位</h1>
        <div class="cls"><a onclick="hidePopupDiv($('#layer_selPos')); return false" class="xx" href="javascript:;"></a></div>
    </div>
    <div class="popCon clearfix pd_20 lineH200">
        <div style="height:200px;overFlow-x:hidden;overFlow-y:auto">
            <c:forEach var="position" items="${positions}">
                <p><input type="checkbox" name="position" value="${position.id}" title="${position.positionName}">${position.positionName}</p>
            </c:forEach>
        </div>
    </div>
    <div class="popBtn">
        <a href="javascript:;" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#layer_selPos'))">取消</a>
        <a href="javascript:;" class="btnOpH24 h24Blue in_block ml_10" onclick="confirmPosition();">确定</a>
    </div>
</div>
<div class="popLayer w650" id="layer_excel">
    <div class="popTitle clearfix">
        <h1>导出excel</h1>
        <div class="cls"><a href="#" class="xx" onclick="hidePopupDiv($('#layer_excel')); return false"></a></div>
    </div>
    <div class="popCon">
        <div class="form">
            <span>
                <label><input type="checkbox"  id="js_employee_export_checkALl" />全选</label></span>
            <ul id="excelOutput" class="excelOutput mt_10 clearfix">
                <li><label><input type="checkbox" class="chkExcel" name="userCode" value="工号" />工号</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="userNameCn" value="姓名" />姓名</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="userNameEn" value="英文名" />英文名</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="sex" value="性别" />性别</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="credentialsNo" value="身份证号码" />身份证号码</label></li>

                <li><label><input type="checkbox" class="chkExcel" name="areaName" value="区域" />区域</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="orgName" value="部门" />部门</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="positionName" value="岗位" />岗位</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="titleLevel" value="职等职级" />职等职级</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="userTitle" value="头衔" />头衔</label></li>

                <li><label><input type="checkbox" class="chkExcel" name="status" value="任职状态" />任职状态</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="joinDate" value="入职日期" />入职日期</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="formalDate" value="转正日期" />转正日期</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="leaveDate" value="离职日期" />离职日期</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="experience" value="行业经验" />行业经验</label></li>

                <li><label><input type="checkbox" class="chkExcel" name="bornDay" value="身份证出生日期" />身份证出生日期</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="birthday" value="生日" />生日</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="constellationStr" value="星座" />星座</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="comeFromStr" value="招聘渠道" />招聘渠道</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="lastDegreeStr" value="学历" />学历</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="accountLocation" value="户籍所在址地" />户籍所在址地</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="censusStr" value="户籍性质" />户籍性质</label></li>

                <li><label><input type="checkbox" class="chkExcel" name="orgAddress" value="办公地点" />办公地点</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="orgPhone" value="办公地点电话" />办公地点电话</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="mobilePhone" value="移动电话" />移动电话</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="officePhone" value="办公电话" />办公电话</label></li>
                <li><label><input type="checkbox" class="chkExcel" name="orgFax" value="传真" />传真</label></li>
            </ul>
        </div>
    </div><br /><br />
    <div class="popBtn">
        <input type="button" value="导出" class="btnForm fBlue60" onclick="if(chkForm_excel()) hidePopupDiv($('#layer_excel'));" />&nbsp;&nbsp;&nbsp;&nbsp;
        <input type="button" value="取消" class="btnForm fGray60" onclick="hidePopupDiv($('#layer_excel'))" /></div>
</div>

<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/json/${user.companyENLower}/${user.companyENLower}All.js"></script>
<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/lib/jquery-1.6.2.min.js"></script>
<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/lib/angular.min.js"></script>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="WdatePicker,validation"/>
    <jsp:param name="js" value="dui_treePanel,portal,employee/employee,employee/register"/>
</jsp:include>

<script type="text/javascript">
    <appel:checkPrivilege url="employee_advanced_search">
        <c:if test="${employeeSearch.advancedSearch == 1}">
            $('tr.js_advSearchField').removeClass('hideit');
            $('a.js_btnAdvanceSearch').addClass('hideit');
            $("#searchReset").removeClass('hideit');

        </c:if>
    </appel:checkPrivilege>
    $("#js_export").live('click',function(){
        if(${paginate.totalCount == 0}){
            alert("对不起没有数据可供导出哦");
            return false;
        }
        else{
            showPopupDiv($('#layer_excel'));
            return false
        }
    });

</script>