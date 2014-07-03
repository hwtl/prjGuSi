<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="撤销黑名单"/>
</jsp:include>
        <div class="contentRight">
            <div class="clearfix mt_5">
                <span class="in_block f14 bold">撤销黑名单</span>
            </div>
            <div class="boxWrapBlue pd_10 mt_10">
             		  姓名（工号）：${blackEmployee.userNameCn}（${blackEmployee.userCode}） <span class="ml_10">组织名称：${blackEmployee.orgName}</span>  
             		             <span class="ml_10">岗位名称：${blackEmployee.positionName}</span>    
             		                       <span class="ml_10">  职等职级：${blackEmployee.levelFull}</span> 
            </div>
            <form id="removeBlackListForm" method="post" action="/employee/removeBlack">
                <webplus:token />
             	<input type="hidden" value="${blackEmployee.userCode}" name="userCode" id="blackUserCode"/>
             	<input type="hidden" value="${blackId}" name="id">
             	<input type="hidden" value="${user.userCode}" id="attachCreator">
                <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_10">
                    <tbody>
                        <tr>
                            <td class="tdTitle">撤销原因备注：</td>
                            <td class="request">●</td>
                            <td class="bdR">
                                <textarea name="revocation" id="reason" rule="required lessThan200" rows="10" cols="80" placeholder="200字以内"></textarea>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdTitle">上传担保书：</td>
                            <td class="request">●</td>
                            <td class="bdR">
                           		 <input type="hidden" alwaysCheck="true" rule="required" name="guaranteeAttachId"  class="js_h_attach">
                            	<div class="js_uploadContainer">
                            	</div>
                            </td>
                            
                        </tr>
                    </tbody>
                </table>
                <p class="txtRight mt_15 mb_20">
                    <a href="/" class="btnOpH34 h34Silver in_block">取消</a>
                    <a href="javascript:;" class="btnOpH34 h34Blue opH34 in_block js_submitRemoveBlackList">提交</a>
                </p>
            </form>
        </div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation"/>
    <jsp:param name="js" value="black,jquery.upload"/>
</jsp:include>


