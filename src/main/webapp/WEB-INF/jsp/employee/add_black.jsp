<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="加入黑名单"/>
</jsp:include>
<style type="text/css">
    .reason-list {
        height: 430px;
        max-width: 800px;
        overflow-y: scroll; 
    }
    .reason-list li {
        list-style-position: inside;
        padding: 10px 0 10px 20px;
        text-indent: -20px;
        border-bottom: 1px solid #d5dee7;
    }
    .reason-list li:first-child {
        border-top: 1px solid #d5dee7;
    }
</style>
        <div class="contentRight">
            <div class="clearfix mt_5">
                <span class="in_block left f14 bold">加入黑名单</span>
            </div>
            <div class="boxWrapBlue pd_10 mt_10">
               		 姓名（工号）：${blackEmployee.userNameCn}（${blackEmployee.userCode}） <span class="ml_10">组织名称：${blackEmployee.orgName}</span>  
               		             <span class="ml_10">岗位名称：${blackEmployee.positionName}</span>    
               		                       <span class="ml_10">  职等职级：${blackEmployee.levelFull}</span>    
            </div>
            <form id="addBlackListForm" method="post" action="/employee/addBlack">
                <webplus:token />
                <input type="hidden" value="${blackEmployee.userCode}" name="userCode" id="blackUserCode"/>
                <input type="hidden" value="${user.userCode}" id="attachCreator">
                <input type="hidden" name="reasons" id="blackReasons" value=""/>
                <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_10">
                    <tbody>
                        <tr>
                            <td class="tdTitle" width="100px;">黑名单原因：</td>
                            <td class="request">●</td>
                            <td class="bdR">
                                <ul class="reason-list">
                                  <c:forEach var="p" items="${blackReasons}">
                                    <c:choose>
                                       <c:when test="${p.other==1}">
                                        <li><label>
		                                    <input type="checkbox" class="js_selfCheckbox" description="" name="blackreason" groupby="customizeCheckbox" rule="required" id="otherReason" value="${p.id}">${p.parameterValue}</label>
		                                    <input type="text" class="js_customizeCheckbox hideit" name="blackdescription" groupby="customizeCheckbox" rule="required lessThan100 validJsonChar" checkboxid="otherReason">
		                                </li>
                                       </c:when>
                                       <c:otherwise>
                                        <li>
	                                      	<label>
	                                   			 <input type="checkbox" description="" name="blackreason" rule="required" value="${p.id}"> 
	                                    		     ${p.parameterValue}
	                               			 </label>
	                               		 </li>
                                       </c:otherwise>
                                    </c:choose>
                                  </c:forEach>
                            </ul>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">上传公告截图：</td>
                            <td></td>
                            <td class="bdR js_uploadContainer">
                            </td>
                            <input type="hidden" value="" name="attachId" class="js_h_attach">
                        </tr>
                    </tbody>
                </table>
                <p class="txtRight mt_15 mb_20">
                    <a href="/" class="btnOpH34 h34Silver in_block">取消</a>
                    <a href="javascript:;" class="btnOpH34 h34Blue opH34 in_block js_submitBlackList">提交</a>
                </p>
            </form>
        </div>
        <!-- ,plugs/jquery.upload -->
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation"/>
    <jsp:param name="js" value="black,jquery.upload"/>
</jsp:include>
