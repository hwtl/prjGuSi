<%@include file="_includes.jsp" %>
<%@page import="com.gusi.boms.common.Configuration"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${param.title} - 后台管理 - ${user.company}地产</title>
    <link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/main.css?version=<%=Configuration.getInstance().getCurrentVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="http://dui.${user.companyENLower}.com/public/css/header.css?version=<%=Configuration.getInstance().getCurrentVersion()%>"/>
    <link type="text/css" rel="stylesheet" href="/static/css/position/fronterp.css?version=<%=Configuration.getInstance().getCurrentVersion()%>"/>
    <c:if test="${not empty param.css || not empty param.duiCss ||not empty param.ajCss}">
        <c:forEach var="css" items="${fn:split(param.css, ',')}">
            <c:if test="${not empty css}">
                <link type="text/css" rel="stylesheet" href="/static/css/${css}.css?version=<%=Configuration.getInstance().getCurrentVersion()%>">
            </c:if>
        </c:forEach>
        <c:forEach var="css" items="${fn:split(param.duiCss, ',')}">
            <c:if test="${not empty css}">
                <link type="text/css" rel="stylesheet"
                      href="http://dui.${user.companyENLower}.com/public/css/${css}.css?version=<%=Configuration.getInstance().getCurrentVersion()%>">
            </c:if>
        </c:forEach>
        <c:forEach var="css" items="${fn:split(param.ajCss, ',')}">
            <c:if test="${not empty css}">
                <link type="text/css" rel="stylesheet"
                      href="${css}?version=<%=Configuration.getInstance().getCurrentVersion()%>">
            </c:if>
        </c:forEach>
    </c:if>
    
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/jquery-1.4.2.min.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
	<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/fns.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
  
   <c:if test="${ not empty param.js}">
	    <c:forEach var="js" items="${fn:split(param.js, ',')}">
	        <c:if test="${not empty js}">
	            <script type="text/javascript" src="/static/js/${js}.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
	        </c:if>
	    </c:forEach>
   </c:if>
   
    <link rel= "shortcut icon" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">
    <link rel= "bookmark" href="http://dui.${user.companyENLower}.com/public/images/favicon.ico" type="image/x-icon">

    <!-- 全局参数 -->
    <script type="text/javascript" language="javascript">
        var headerParameters={
            empNo:'${user.userCode}',
            empName:'${user.userName}',
            env:'${config.env}',
            appName:'dms'
        };
    </script>

</head>
<body>
<div id="new_header">
    <div class="headerWrap">
        <div class="header clearfix">
            <a href="http://gzz.${user.companyENLower}.com/workspace" class="logo_mini left"></a>
            <div class="mainMenu left ml_10">
                <a href="http://renzi.${user.companyENLower}.com" class="sub" rel="home"
                   style="background-image: none;">人力资源管理系统</a>
            </div>
            <div class="mainMenu topNav ml_20 right">
                <a href="#" class="sub mr_10" style="background-image: none;">${user.userName}</a>
				<a href="${config.logoutUrl }" style="background-image: none;">退出</a>
            </div>
        </div>
    </div>
    <div class="vPopupArrow fixedPop" id="nav_pop_arrow"></div>
    <div class="vPopup fixedPop" id="nav_pop_submenu">
        <div class="vPopupList4Nav t150"></div>
    </div>
    <div class="vPopupBD fixedPop" id="nav_pop_bd">

    </div>
    </div>
<div class="container_back">
    <div class="clearfix">
        <div class="sidebarLeft" id="orgFrame">
            <div class="boxWrapGray">
                <appel:checkPrivilege url="oms_base">
                    <div class="boxTitle"><b>组织管理系统</b></div>
                    <div class="boxCon">
                        <ul class="grey666">
                            <appel:checkPrivilege url="oms_om_base">
                                <li class="mt_2">
                                    <div class="clearfix">
                                        <span class="left in_block node_switch js_switch" title="展开" open="true">-</span>
                                        <a href="/organization/list" class="left in_block js_nodes pnode" nodeid="1-1" staticnode="false">组织管理</a>
                                    </div>
                                    <ul class="cnode grey666 js_sections">
                                        <appel:checkPrivilege url="oms_om_org_add">
                                            <li class="mt_5"><a href="/organization/add" class="js_nodes" nodeid="1-1-1" staticnode="false">新增组织</a></li>
                                        </appel:checkPrivilege>
                                        <appel:checkPrivilege url="oms_om_org_index">
                                            <li class="mt_5"><a href="/organization/sort" class="js_nodes" nodeid="1-1-2" staticnode="false">组织排序</a></li>
                                        </appel:checkPrivilege>
                                        <appel:checkPrivilege url="organization_transfer">
                                            <li class="mt_5"><a href="/transfer/org/list" class="js_nodes" nodeid="2-1-4" staticnode="false">组织转调</a></li>
                                        </appel:checkPrivilege>
                                        <appel:checkPrivilege url="oms_view_accountingDept">
                                            <li class="mt_5"><a href="/accounting" class="js_nodes" nodeid="1-1-2" staticnode="false">核算部门对应关系</a></li>
                                        </appel:checkPrivilege>
                                    </ul>
                                </li>
                            </appel:checkPrivilege>

                            <appel:checkPrivilege url="oms_om_position_base">
                                <li class="mt_2">
                                    <div class="clearfix">
                                        <span class="left in_block node_switch js_switch" title="展开" open="true">-</span>
                                        <a href="/position/list" class="left in_block js_nodes pnode" nodeid="1-3" staticnode="false">岗位管理</a>
                                    </div>
                                    <ul class="cnode grey666 js_sections">
                                        <appel:checkPrivilege url="oms_om_position_add">
                                            <li class="mt_5"><a href="/position/add"  class="js_nodes" nodeid="1-3-1" staticnode="false">新增岗位</a></li>
                                        </appel:checkPrivilege>
                                    </ul>
                                </li>
                            </appel:checkPrivilege>
                            <appel:checkPrivilege url="pms_base">
                                <li class="mt_2">
                                    <div class="clearfix">
                                        <span class="left in_block node_noswitch" open="not"></span>
                                        <a href="/privilege/list" class="left in_block js_nodes pnode" nodeid="1-2" staticnode="false">权限管理</a>
                                    </div>
                                </li>
                            </appel:checkPrivilege>
                        </ul>
                    </div>
                </appel:checkPrivilege>
                <appel:checkPrivilege url="hrm_base">
                    <div class="boxWrapGray mt_10">
                        <div class="boxTitle"><b>人力资源管理系统</b></div>
                        <div class="boxCon">
                            <ul class="grey666">
                                <appel:checkPrivilege url="hrm_employee_base">
                                    <li class="mt_2">
                                        <div class="clearfix">
                                            <span class="left in_block node_switch js_switch" title="展开" open="true">-</span>
                                            <a href="/employee/list?employeeStatus=0,1" class="left in_block js_nodes pnode" nodeid="2-1" staticnode="false">人事管理
                                                <span id="detailUnCheckNum" unCheckNumApiUrl="/archives/getUnCheckNum">
                                                </span>
                                            </a>
                                        </div>
                                        <ul class="cnode grey666 js_sections">
                                            <appel:checkPrivilege url="hrm_employee_add">
                                                <li class="mt_5"><a href="/employee/add" class="js_nodes" nodeid="2-1-1" staticnode="false">新增员工</a></li>
                                            </appel:checkPrivilege>
                                            <appel:checkPrivilege url="interview_leave_emp">
                                                <li class="mt_5"><a href="/interview" class="js_nodes" nodeid="2-1-2"
                                                                    staticnode="false">离职面谈</a></li>
                                            </appel:checkPrivilege>
                                <%--            <appel:checkPrivilege url="employee_transfer_know">
                                                <li class="mt_5"><a href="/transfer/emp/list" class="js_nodes"
                                                                    nodeid="2-1-3" staticnode="false">转调记录</a></li>
                                            </appel:checkPrivilege>--%>
                                            <c:if test="${user.company == '德佑'}">
                                                <appel:checkPrivilege url="employee_transfer_list">
                                                    <li class="mt_5">
                                                        <a href="/transfer/emp/list" class="js_nodes" nodeid="2-1-3" staticnode="false">员工异动
                                                            <span id="empTransferNum" empTransferApiUrl="/oms/api/empTransfer/queryForUserTaskCount/${user.userCode}">
                                                            </span>
                                                        </a>
                                                    </li>
                                                </appel:checkPrivilege>
                                           <%--      <appel:checkPrivilege url="hrm_employee_photo_print">
	    											<li class="mt_5"><a href="/employee/photoPrint" target="_blank"
	    												 class="js_nodes" nodeid="2-1-4" staticnode="false">社保打印</a></li>
												 </appel:checkPrivilege>
                                                 <appel:checkPrivilege url="hrm_si_view">
	    											<li class="mt_5"><a href="/employee/photoPrint" target="_blank"
	    												 class="js_nodes" nodeid="2-1-5" staticnode="false">社保管理</a></li>
												 </appel:checkPrivilege>--%>
                                            </c:if>
                                        </ul>

                                    </li>
                                </appel:checkPrivilege>
                            </ul>

                            <c:if test="${user.company == '德佑'}">
                                <ul class="grey666">
                                    <appel:checkPrivilege url="hrm_si_view">

                                        <li class="mt_2">
                                            <div class="clearfix">
                                                <span class="left in_block node_switch js_switch" title="展开" open="true">-</span>
                                                <a href="/si/common/index" class="left in_block js_nodes pnode" target="_blank" nodeid="2-1" staticnode="false">社保管理</a>
                                            </div>
                                            <ul class="cnode grey666 js_sections">
                                                <appel:checkPrivilege url="hrm_employee_photo_print">
                                                    <li class="mt_5">
                                                        <a href="/employee/photoPrint" target="_blank"
                                                           class="js_nodes" nodeid="2-1-1"
                                                           staticnode="false">社保打印</a></li>
                                                </appel:checkPrivilege>
                                            </ul>
                                        </li>

                                    </appel:checkPrivilege>
                                </ul>
                            </c:if>

                        </div>

                    </div>
                </appel:checkPrivilege>
            </div>
        </div>
        <div class="contentRight" ng-app="reportApp" ng-controller="reportModule">
            <script type="text/javascript">
                $(function () {
                    //获取员工转调任务数
                    var empTransferEle = $("#empTransferNum");
                    $.getJSON(empTransferEle.attr("empTransferApiUrl"), function (data) {
                        if (data.status == "ok" && data.taskCount > 0) {
                            empTransferEle.html("<span class='num'>" + data.taskCount + "</span>");
                        }
                    });

                    <appel:checkPrivilege url="archives_sure">
                    //获取档案待确认数
                    var detailUnCheckNumEle = $("#detailUnCheckNum");
                    $.getJSON(detailUnCheckNumEle.attr("unCheckNumApiUrl"), function (data) {
                        if (data.status == "ok" && data.unCheckNum > 0) {
                            detailUnCheckNumEle.html("<span class='num'>" + data.unCheckNum + "</span>");
                            //员工列表页的数字显示
                            $("#emplist_detailUnCheckNum").html($("#detailUnCheckNum").html());
                        }
                    });
                    </appel:checkPrivilege>
                });
            </script>