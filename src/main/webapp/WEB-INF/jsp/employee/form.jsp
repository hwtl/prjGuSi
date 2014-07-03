<%--
  Created by IntelliJ IDEA.
  Title:
  User: Jerry.hu
  Create: Jerry.hu (2013-04-09 15:31)
  Description: 新增员工表单
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="新增员工"/>
</jsp:include>
<div class="contentRight">
<div class="container left p100">
        <%--<div class="boxWrapYellow center f14">注意：分行人数（含分行经理、待报到员工）不能大于分行编制人数，否则会导致添加失败！</div>--%>
        <form id="employeeFm" name="employeeFm" method="post" action="/employee/add">
            <webplus:token />
            <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
                <tbody>

                <tr>
                    <td class="tdTitle" width="170">组织：</td>
                    <td class="request" width="20">●</td>
                    <td width="760">
                        <p class="red hideit" id="js_hasOrg">组织信息错误，请重新选择</p>
                        <input type="hidden" name="orgId" id="orgId" class="txtNew" value="${org.id}">
                        <input autocomplete="off" type="text" name="ipt_orgId" id="ipt_orgId" class="txtNew" value="${org.orgName}" rule="required">
                        <div id="autoComp_keyword" class="autoComplate"></div>
                    </td>
                </tr>

                <tr>
                    <td class="tdTitle">姓名：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <input type="text" name="userNameCn" class="txtNew" rule="required">
                        <label class="ml_20">英文名：</label>
                        <input type="text" name="userNameEn" class="txtNew">
                    </td>
                </tr>

                <tr id="trIdcard">
                    <td class="tdTitle">身份证号码：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <input type="text" name="credentialsNo" id="js_credential" class="txtNew tt280 confirm" rule="required idcard 18Years" oncopy="return false;" oncut="return false;" />
                        <span id="js_idcDetails"  class="red hideit">此身份证号已存在，<a href="#" id="js_showDetails">查看该员工详情>>></a></span>
                    </td>
                </tr>

                <tr id="trIdcardConfirm">
                    <td class="tdTitle">再次输入身份证号：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <input type="text" name="credentialsNoConfirm" id="js_credentialConfirm" class="required txtNew tt280" rule="required idcard 18Years confirm" oncopy="return false;" oncut="return false;" />
                    </td>
                </tr>

                <tr>
                    <td class="tdTitle">性别：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <input type="radio" name="sex" id="sexMale" value="男" checked>
                        <label for="sexMale">男</label>
                        <input type="radio" name="sex" id="sexFemale" value="女">
                        <label for="sexFemale">女</label></td>
                </tr>

                <tr>
                    <td class="tdTitle">最高学历：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <select id="lastDegree" name="lastDegree" rule="required">
                            <option value="">请选择</option>
                            <c:forEach var="degree" items="${degreeList}">
                                <option value="${degree.optionCode}">${degree.optionValue}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td class="tdTitle">手机号码：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <input type="text" name="mobilePhone" rule="required moblie hasphone">
                    </td>
                </tr>

                <tr>
                    <td class="tdTitle">职系：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <select name="serialId" rule="required" id="js_serialId">
                            <option value="">请选择</option>
                            <c:forEach var="titleSerial" items="${titleSerialList}">
                                <option value="${titleSerial.id }" >${titleSerial.serialName }</option>
                            </c:forEach>
                        </select>
                        <label class="ml_20">行业经验：</label>
                        <input name="experience" id="expTrue" value="有" type="radio" >
                        <label for="expTrue">是</label>
                        <input name="experience" id="expFalse" value="无" type="radio" checked>
                        <label for="expFalse">否</label>
                    </td>
                </tr>

                <tr>
                    <td class="tdTitle">职等职级：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <select name="titleId" rule="required" id="js_titleId">
                            <option value="">请选择</option>
                        </select>
                        <select name="levelId" rule="required" id="js_levelId">
                            <option value="">请选择</option>
                        </select>
                        <span id="js_showTitleInfo" class="hideit"></span>
                    </td>
                </tr>

                <tr>
                    <td class="tdTitle">岗位：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <select name="positionId"  id="js_positionId" rule="required" >
                            <option value="">请选择</option>
                        </select>
                        <label class="ml_20">头衔：</label>
                        <input type="text" name="userTitle" class="txtNew">
                        <label class="ml_20">头衔英文名：</label>
                        <input type="text" name="eUserTitle" class="txtNew w150">
                    </td>
                </tr>

                <tr>
                    <td class="tdTitle">入职日期：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <input type="text" name="joinDate" class="txtDate" value="" rule="required" readonly="readonly" maxlength="10">
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle">任职状态：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <input name="status" id="statusProbation" type="radio" value="试用期" checked>
                        <label for="statusProbation">试用期</label>
                        <input name="status" id="statusOfficial" type="radio" value="正式">
                        <label for="statusOfficial">正式</label>
                    </td>
                </tr>
                <tr>
                    <td class="tdTitle">招聘渠道：</td>
                    <td class="request" width="20">●</td>
                    <td>
                        <select name="comeFrom" id="js_comeFrom" rule="required" groupby="comeFrom">
                            <option value="">请选择</option>
                            <c:forEach var="channel" items="${channels}">
                                <option value="${channel.optionCode}">${channel.optionValue}</option>
                            </c:forEach>
                        </select>
                        <input type="text" groupby="comeFrom" name="recommendUser" id="js_usercode" class="checkRecommendUser hideit" placeholder="请输入推荐员工工号">
                        <input type="text" groupby="comeFrom" name="remark" id="js_remark" class="hideit" placeholder="请备注具体渠道">
                    </td>
                </tr>
                 <tr>
                    <td class="tdTitle">预计报到日期：</td>
                    <td class="request" width="20">●</td>
                    <td style="vertical-align: middle;">
                        <input type="text" name="registerDate" class="txtDate" value="" rule="required" readonly="readonly" maxlength="10">
                         <select name="registerDateHour">
	                          <option value="08">08</option>
	                          <option value="09" selected="selected">09</option>
	                          <option value="10">10</option>
	                          <option value="11">11</option>
	                          <option value="12">12</option>
	                          <option value="13">13</option>
	                          <option value="14">14</option>
	                          <option value="15">15</option>
	                          <option value="16">16</option>
	                          <option value="17">17</option>
	                          <option value="18">18</option>
	                          <option value="19">19</option>
	                          <option value="20">20</option>
	                          <option value="21">21</option>
                         </select>
                      		   时
                         <select name="registerDateMinute">
	                          <option value="00" selected="selected">00</option>
	                          <option value="30">30</option>
                         </select>
                       		  分
                    </td>
                </tr>
            </table>
            <p class="txtRight mt_15">
                <a href="javascript:;" class="btn-normal btn-blue right ml_15 js_submitAddEmp">提交</a>
                <a href="/employee/list" class="btn-normal btn-silver right">取消</a>
            </p>
        </form>
</div>
</div>
<!--黑名单员工弹层 begin-->
<div class="popLayer" id="blackList_layer_message">
    <div class="popTitle clearfix">
        <h1>黑名单员工提示</h1>
        <div class="cls"><a href="#" class="xx" onclick="hidePopupDiv($('#blackList_layer_message')); return false"></a></div>
    </div>
    <div class="popCon">
        <div class="message">
            <div class="f14 bold org">
                <p>此员工为黑名单员工，如需录用请至黑名单管理列表操作</p>
                <p>跳转至<a href="/employee/list?black=1">黑名单管理列表页</a></p>
            </div>
        </div>
    </div><br><br>
    <div class="popBtn">
        <a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#blackList_layer_message')); return false">关闭</a>
    </div>
</div>
<!--黑名单员工弹层 end-->
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,WdatePicker,autocomplete"/>
    <jsp:param name="js" value="portal,select,employee/employeeForm"/>
</jsp:include>
