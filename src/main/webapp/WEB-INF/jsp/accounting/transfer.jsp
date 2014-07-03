<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>

<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="添加核算部门-组织管理"/>
</jsp:include>
<!--end: header-->
<div class="container">
   <form:form modelAttribute="accountingDepartment" action="/accounting/saveAccountingDept" method="post" id="accountingDepartmentForm">
   		<form:hidden path="orgId"/>
       <webplus:token />
   		<div class="clearfix mt_20">
			<h1 class="f18 grey333">添加核算部门</h1>
			<table width="100%" cellpadding="0" cellspacing="0" class="tableFormNew 	 mt_20">
	                 <tr>
	                    <td class="tdTitle">核算部门：</td>
	                    <td class="request">●</td>
	                    <td>
	                       <form:input path="orgName" rule="required" cssClass="txt tt280" maxlength="50"/>
	                    </td>
	                </tr>
	                <tr>
		                <td class="tdTitle" align="right" nowrap>起始日期：</td>
						 <td class="request" >●</td>
						<td>
						    <form:input path="startTime" rule="required" cssClass="txtDate" readonly ="readonly"/>
						</td>
					</tr>
					<tr>
		                <td class="tdTitle" align="right" nowrap>截止日期：</td>
						 <td class="request" >&nbsp;</td>
						<td>
							 <form:input path="endTime" cssClass="txtDate" readonly ="readonly"/>
						</td>
					</tr>
					<tr>
		                <td class="tdTitle" align="right" nowrap>核算部门负责人：</td>
						 <td class="request" >●</td>
						<td>
						   <form:select rule="required"  path="manager">
						   		<option value="">请选择</option>
						   		<form:options items="${managers}" itemLabel="managerName" itemValue="manager"/>
						   </form:select>
						</td>
					</tr>
				<tr height="60">
					<td class="tdTitle"></td>
					 <td class="request" >&nbsp;</td>
					<td>
							<a href="/organization/list" class="btnOpH24 h24Silver in_block">取消</a>
       					 <a href="#" class="btnOpH24 h24Blue in_block js_submitForm">提交</a>
					</td>
				</tr>
			</table>
	   	</div>
   	</form:form>
	<div class="navTab2th mt_20 pt_20 clearfix">
		<a href="javascript:void(0)" class="current">核算部门记录</a>
	</div>
	<table width="100%" cellspacing="0" class="tableListNew mt_10">
			<tr class="trHead">
				<td width="30" nowrap>&nbsp;</td>
				<td>核算部门</td>
				<td>负责人</td>
				<td>起始时间</td>
				<td>截止时间</td>
				<td>创建人</td>
				<td>创建时间</td>
			</tr>
			<c:forEach items="${histories}" var="h" varStatus="vs">
					<tr class="${vs.count%2==0?'tr2':'tr1'}">
					<td>${vs.count}</td>
					<td>${h.orgName}</td>
					<td>${h.managerName}(${h.manager})</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd" value="${h.startTime}"/></td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${h.endTime}"/></td>
					<td>${h.creatorName }</td>
					<td><fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${h.createTime}"/></td>
			</c:forEach>
		</table>
</div>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,WdatePicker"/>
    <jsp:param value="portal" name="js"/>
</jsp:include>
<script type="text/javascript">
$("a.js_submitForm").click(function(){
			 if(checkDate()&&checkAccountingName()&&checkDateRange()){
		     	sumbitForm();
		      }else{
		     	 return false;
		     }
		});
function checkDate(){
	var beginDate = $("input[name=startTime]").val();
    var endDate = $("input[name=endTime]").val();
    if(beginDate !="" && endDate !="")
    {
	     var dt1 = new Date(beginDate.replace(/-/g, "/")); //转换成日期类型
	     var dt2 = new Date(endDate.replace(/-/g, "/")); //转换成日期类型
		 if(dt2 < dt1){
			 alert('截止时间应大于等于起始时间');
		    return false;
		 }
    }
    return true;
}
function checkAccountingName(){
    var hasExists= false;
    ajaxGet({
        url : '/accounting/checkAccountingDeptName',
        async:false,
        data:{
            'orgName':$.trim($("input[name=orgName]").val()),
            'orgId':$('#orgId').val()
        },
        ok : function(data) {
           hasExists=true;
        },
        fail : function(data) {
            alert("该核算部门已经存在");
        }
    });
    return hasExists;
}
function checkDateRange(){
    var valid= false;
    var beginDate = $("input[name=startTime]").val();
    var endDate = $("input[name=endTime]").val();
    ajaxGet({
        url : '/accounting/validateAccountingDate',
        async:false,
        data:{
        	"startTime":beginDate,
 			"endTime":endDate,
			"orgId":$('#orgId').val()
        },
        ok : function(data) {
           valid=true;
        },
        fail : function(data) {
            alert(data.message);
        }
    });
    return valid;
}
function sumbitForm(){
	 var validator = $('#accountingDepartmentForm').validate();
	 if (validator.validateForm()) {
		 $('#accountingDepartmentForm').submit();
	 }
}

$("input[name=endTime],input[name=startTime]").change(function() {
	onDateChanged();
});

function fnCallBack_clear(input) {
	onDateChanged();
}
function onDateChanged()
{
	 var beginDate = $("input[name=startTime]").val();
     var endDate = $("input[name=endTime]").val();
     $("select[name=manager]").html("<option value=''>请选择</option>");
     ajaxGet({
         url : '/accounting/queryManagersByDate',
         data:{
         	"startTime":beginDate,
  			"endTime":endDate,
 			"orgId":$('#orgId').val()
         },
         ok : function(data) {
        	 var managers = data.managers;
             $.each(managers, function(i, item){
            	  $("select[name=manager]").append("<option value="+ item.manager+">" + item.managerName+ "</option>");
             });
         },
         fail:function(data)
         {
        	 alert(data.message);
         }
     });
}
</script>
