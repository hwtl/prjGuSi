<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="css" value="privilege/frame"/>
</jsp:include>
<div class="container left p100 privilege">
	<h1 class="f12 mt_10">配置系统权限管理员 (${employee.userName }  ${employee.orgName })</h1>
	<div class="pt_15 pb_15">
		<div class="boxWrapGray t250 systemList left">
			<div class="boxTitle"><b>系统列表</b></div>
			<div class="boxCon privilege">
				<ul>
					<c:forEach items="${appList }" var="app">
						<li ${app.checked==true?'class=\"chk\"':'' }><label><input type="checkbox" name="chkFunc" value="${app.id }" ${app.checked==true?'checked=\"checked\"':'' }>${app.applicationName }</label></li>
					</c:forEach>
				</ul>
			</div>
		</div>
		<a href="#" id="btnSave" class="btnOpH34 h34Green opH34 ml_15 left">保存更改</a>
	</div>
	<div class="clearfix"></div>
</div>
<input type="hidden" value="${employee.userCode }" name="userCode">
<div class="clearfix"></div>
<script type="text/javascript">
$(function(){
	$("#btnSave").live("click", function(){//保存当前更改
		var appIds = "";
		$("div.boxCon input:checked").each(function(){
			appIds += ","+ $(this).val();
		});
		if(appIds.length>0)
			appIds = appIds.substring(1, appIds.length);
		ajaxPost({
			url:"/privilege/setApplicationEmployeeAccess",
			data:{"userCode":$("[name=userCode]").val(),"appIds":appIds},
			ok:function(data){
				showSystemMsg("ok","设置管理员成功！");
			},
			fail:function(data){
				showSystemMsg("fail",data.msg);
			}
		});
	});
});
</script>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="js" value="dy-ajax"/>
</jsp:include>