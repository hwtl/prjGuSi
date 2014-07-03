<%--
  Created by IntelliJ IDEA.
  Title:
  User: Jerry.hu
  Create: Jerry.hu (2013-04-09 16:20)
  Description: To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="员工详情-人事管理"/>
</jsp:include>
<div class="js_refresh" refresh="true">
    <jsp:include page="/WEB-INF/jsp/common/employee_detail_nav.jsp">
        <jsp:param name="nav" value="details"/>
    </jsp:include>
        <div class="clearfix mt_20">
            <div class="mt_15 clearfix">
                <span class="in_block mt_5 left f14 bold">基本信息</span>
               <appel:checkPrivilege url="hrm_employee_base_edit">
                    <span class="right">
                        <a class="btnOpH24 btn-silver in_block hideit" href="/employee/${employee.userCode}/details" id="js_baseInfo_rest" userCode ="${employee.userCode}">取消</a>
                        <a class="btnOpH24 btn-silver in_block" action="/employee/edit/${employee.userCode}" id="js_baseInfo_edit" userCode ="${employee.userCode}">编辑</a>
                    </span>
               </appel:checkPrivilege>
            </div>
            <div id="div_baseInfo_view">
                <div class="boxWrapBlue pd_10 mt_15">
                    <input type="hidden" id="_userCode_" name="_userCode_" value="80002">
                    <table border="0" cellpadding="5" cellspacing="0" module="baseInfo">
                        <tbody>
                        <tr>
                            <td align="right" width="120" class="grey999">姓名：</td>
                            <td width="200" class="bold" ref="empName">${employee.userNameCn}</td>
                            <td align="right" width="120" class="grey999">任职状态：</td>
                            <td width="200" class="bold">${employee.status}</td>
                        </tr>
                        <tr>
                            <td align="right" class="grey999">身份证号：</td>
                            <td class="bold" ref="idcard">${employee.credentialsNo}</td>
                            <td align="right" class="grey999">职等职级：</td>
                            <td><b>${employee.titleDegree}-${employee.levelDegree}   ${employee.levelFull}</b></td>
                        </tr>
                        <tr>
                            <td align="right" class="grey999">头衔名称：</td>
                            <td class="bold" ref="postName">${employee.userTitle}</td>
                            <td align="right" class="grey999">头衔英文名称：</td>
                            <td class="bold" ref="postName">${employee.eUserTitle}</td>
                        </tr>
                        <tr>
                            <td align="right" class="grey999">行业经验：</td>
                            <td class="bold" ref="experience">${employee.experience}</td>
                            <td align="right" class="grey999">首次入职时间：</td>
                            <td class="bold" ><fmt:formatDate value="${employee.joinDate}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                        <tr>
                            <td align="right" class="grey999">转正时间：</td>
                            <td class="bold" ref="postName"><fmt:formatDate value="${employee.formalDate}" pattern="yyyy-MM-dd"/></td>
                            <td align="right" class="grey999">创建时间：</td>
                            <td class="bold" ><fmt:formatDate value="${employee.createTime}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                        <c:if test="${employee.joinDate ne employee.newJoinDate}">
                        <tr>
                            <td align="right" class="grey999">回聘时间：</td>
                            <td class="bold"  colspan="3" ><fmt:formatDate value="${employee.newJoinDate}" pattern="yyyy-MM-dd"/></td>
                        </tr>
                        </c:if>
                        <c:if test="${employee.status == '离职'}">
                            <tr>
                                <td align="right" class="grey999">离职类型：</td>
                                <td class="bold" ref="postName">${employee.leaveType}</td>
                                <td align="right" class="grey999">离职日期：</td>
                                <td class="bold" ref="experience"><fmt:formatDate value="${employee.leaveDate}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                            <tr>
                                <td align="right" class="grey999">离职原因：</td>
                                <td class="bold" colspan="3" >${employee.leaveReason}</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                </div>
            </div>
            <div id="div_baseInfo_eidt" class="hideit">
                <form action="/employee/edit/${employee.userCode}" method="post" id="employeePositionForm">
                    <webplus:token />
                    <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
                        <tbody>
                        <tr>
                            <td class="tdTitle">姓名：</td>
                            <td class="request" width="20">●</td>
                            <td class="bdR"><input type="text" class="txtNew" name="userNameCn" id="js_userNameCn" value="${employee.userNameCn}" rule="required"></td>
                            <td class="tdTitle">任职状态：</td>
                            <td class="request" width="20">●</td>
                            <td>${employee.status}</td>
                        </tr>
                        <tr>
                            <td class="tdTitle">身份证号：</td>
                            <td class="request">●</td>
                            <td class="bdR"><input type="text" name="credentialsNo" id="js_credential" rule="required idcard 18Years" class="txtNew w200 confirm" value="${employee.credentialsNo}"  oncopy="return false;" oncut="return false;"></td>
                            <td class="tdTitle">再次输入身份证号：</td>
                            <td class="request" width="20">●</td>
                            <td>
                                <input type="text" name="credentialsNoConfirm" id="js_credentialConfirm" class="required txtNew w200" value="${employee.credentialsNo}" rule="required idcard 18Years confirm" oncopy="return false;" oncut="return false;" />
                                <span id="js_idcDetails"  class="red f14 bold hideit">此身份证号已存在，<a href="#" id="js_showDetails">查看该员工详情>>></a></span>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">职等职级：</td>
                            <td class="request">●</td>
                            <td><b>${employee.titleDegree}-${employee.levelDegree}   ${employee.levelFull}</b></td>
                            <td class="tdTitle">行业经验：</td>
                            <td class="request">●</td>
                            <td class="bdR">
                                <c:if test="${employee.experience == '有'}">
                                   <lable>
                                       <input type="radio" name="experience"  value="有" rule="required" checked="checked">有
                                   </lable>
                                   <lable>
                                       <input type="radio" name="experience"  value="无" rule="required" >无
                                   </lable>
                                </c:if>
                                <c:if test="${employee.experience == '无' or empty employee.experience}">
                                   <lable>
                                       <input type="radio" name="experience"  value="有" rule="required">有
                                   </lable>
                                   <lable>
                                       <input type="radio" name="experience"  value="无" rule="required" checked="checked">无
                                   </lable>
                                </c:if>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">头衔名称：</td>
                            <td class="request">●</td>
                            <td><input type="text" name="userTitle" rule="required" id="js_userTitle" class="txtNew w150" value="${employee.userTitle}"></td>
                            <td class="tdTitle">头衔英文名称：</td>
                            <td class="request">&nbsp;</td>
                            <td><input type="text" name="eUserTitle" id="js_eUserTitle" class="txtNew w150" value="${employee.eUserTitle}"></td>
                        </tr>
                        <tr>
                            <td class="tdTitle">首次入职时间：</td>
                            <td class="request">●</td>
                            <td class="bdR"><input type="text" class="txtDate" name="joinDate" id="js_joinDate" rule="required" value="<fmt:formatDate value="${employee.joinDate}" pattern="yyyy-MM-dd"/>"></td>
                            <td class="tdTitle">转正时间：</td>
                            <td class="request"></td>
                            <td><fmt:formatDate value="${employee.formalDate}" pattern="yyyy-MM-dd"/></td>
                        </tr>

                        </tbody>
                    </table>
                </form>
            </div>
            <div class="formsection">
                <jsp:include page="/WEB-INF/jsp/empTag/empTagDetails.jsp"/>
            </div>
            <div class="formsection">
                <jsp:include page="/WEB-INF/jsp/partTimePosition/positionDetails.jsp"/>
            </div>
            <div class="formsection">
                <jsp:include page="/WEB-INF/jsp/contract/contractDetails.jsp"/>
            </div>
            <!-- 操作记录信息 -->
        </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="autocomplete,validation,WdatePicker,dui_addRemove"/>
    <jsp:param name="js" value="select,employee/employeeDetails,dy-ajax"/>
</jsp:include>
<script type="text/javascript">
    // 身份证号码输入确认
    $("#js_credentialConfirm").focus(function(){
        var foreEl = document.getElementById("js_credential");
        $("#js_idcDetails").addClass("hideit");
        foreEl.type = 'password';
    });
    $("#js_credential").focus(function(){
        var foreEl = document.getElementById("js_credentialConfirm");
        $("#js_idcDetails").addClass("hideit");
        foreEl.type = 'password';
    });
    $("#js_credentialConfirm").blur(function(){
        var foreEl = document.getElementById("js_credential");
        $("#js_idcDetails").addClass("hideit");
        foreEl.type = 'text';
    });
    $("#js_credential").blur(function(){
        var foreEl = document.getElementById("js_credentialConfirm");
        $("#js_idcDetails").addClass("hideit");
        foreEl.type = 'text';
    });
        /**
        * 员工基础信息编辑按钮事件
        */


        var EmpInfoValidator = $('#employeePositionForm').validate();
        EmpInfoValidator.addRule('18Years', {
            validate: function($element){
                var birthday_Y = Number($element.val().substring(6, 10));//生日年份
                var birthday_M = Number($element.val().substring(10, 12))-1;//生日月份
                var birthday_D = Number($element.val().substring(12, 14));//生日日期
                var today = new Date();//当天系统日期
                var age = today.getFullYear() - birthday_Y - 1;//年龄
                if(today.getMonth() > birthday_M || today.getMonth() == birthday_M && today.getDate() >= birthday_D){
                    age += 1;
                }
                return age >= 18;
            },
            message: '身份证显示年龄小于18周岁'
        });
        EmpInfoValidator.addRule('confirm', {
            validate: function($element){
                return $element.val() === $('.confirm').val();
            },
            message: '两次输入的身份证号码不一致'
        })
        $("#js_baseInfo_edit").click(function(){
          var self = $(this);
         if(self.hasClass("js_baseInfo_save")){
             if(EmpInfoValidator.validateForm()) {
                 $("#employeePositionForm").submit();
                 $("#js_baseInfo_rest").addClass("hideit");
             }
         }else{
              self.text("保存").removeClass("btn-silver").addClass("btn-blue js_baseInfo_save");
              $("#js_baseInfo_rest").removeClass("hideit");
              $("#div_baseInfo_view").addClass("hideit");
              $("#div_baseInfo_eidt").removeClass("hideit");
         }
        });
</script>
