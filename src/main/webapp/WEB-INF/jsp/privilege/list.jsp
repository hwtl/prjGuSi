<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="权限配置-权限管理"/>
    <jsp:param name="css" value="privilege/frame"/>
</jsp:include>
<div class="xAuto">
<div class="container left w1230 privilege">
	<input type="hidden" name="key" value="32"/>
	<input type="hidden" name="type" value="${type }"/>
		<div class="boxWrapGray tt160 left">
			<div class="boxTitle"><b>系统</b>
				<c:if test="${user.userCode==config.superAdminUserCode }">
	               <a href="#" class="btnOpH24 h24Green in_block right mt_2" name="app">添加系统</a>
	            </c:if>
            </div>
			<div class="boxCon appChanel">
				<ul>
					<c:forEach items="${appList }" var="app">
					<li>
						<a href="#" class="in_block" chanelID="${app.id }"><span class="ml_10">${app.applicationName }</span></a>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>

		<div class="boxWrapGray tt160 left ml_5">
			<div class="boxTitle"><b>角色</b>
                 <c:if test="${user.company == '德佑'}">
                     <a href="#" class="btnOpH24 h24Green in_block right mt_2" name="role">添加角色</a>
                 </c:if>
            </div>
			<div class="boxCon roleChanel">
			</div>
		</div>

		<div class="boxWrapGray w550 left ml_5">
			<div class="boxTitle"><b>权限</b>
                <c:if test="${user.company == '德佑'}">
				   <a href="#" class="btnOpH24 h24Green in_block right mt_2" name="privilege">添加权限</a>
                </c:if>
            </div>
			<div class="boxCon privilegeChanel">
			</div>
			<div class="mt_10">
                <c:if test="${user.company == '德佑'}">
                    <a href="#" id="btnSave" class="btnOpH24 h24Blue opH34 right hideit">保存更改</a>
                </c:if>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
</div>
<div class="clearfix"></div>
<c:if test="${user.userCode==config.superAdminUserCode }">
<!-- 添加系统弹层 开始-->
 <div class="popLayer w340 hideit" id="appPopupLayer">
 	<div class="popTitle clearfix">
		<h1>添加系统</h1>
		<div class="cls"><a href="#" class="xx" onclick="hidePopupDiv($('#appPopupLayer')); return false"></a></div>
	</div>
	<div class="popCon">
		<div class="form">
			<div>系统名称：<input type="text" name="applicationName" maxlength="12" value=""  class="txtNew w200 ml_5"/><span class="red ml_5">●</span></div>
			<div class="mt_10">系统代号：<input type="text" name="applicationCode" maxlength="25" value=""  class="txtNew w200 ml_5"/><span class="red ml_5">●</span></div>
		</div>
	</div><br /><br />
	<div class="popBtn">
		<a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#appPopupLayer')); return false">关闭</a>
		<a href="#" class="btnOpH24 h24Blue in_block" name="appSubmit">提交</a>
	</div>
</div>
<!-- 添加系统弹层 结束-->
</c:if>

<div class="popLayer w340 hideit" id="rolePopupLayer">
 	<div class="popTitle clearfix">
		<h1>添加角色</h1>
		<div class="cls"><a href="#" class="xx" onclick="hidePopupDiv($('#rolePopupLayer')); return false"></a></div>
	</div>
	<div class="popCon">
		<div class="form">
			<div>选择系统：<select name="appId" class="ml_5">
				<c:forEach items="${appList }" var="app">
					<option value="${app.id }">${app.applicationName }</option>
				</c:forEach>
			</select></div>
			<div class="mt_5">角色名称：<input type="text" name="roleName" maxlength="20" value=""  class="txtNew w200 ml_5"/><span class="red ml_5">●</span></div>
			<div class="mt_5">角色说明：<textarea type="text" name="roleDesc" class="ml_5" cols="45" rows="5"/></textarea></div>
		</div>
	</div><br /><br />
	<div class="popBtn">
		<a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#rolePopupLayer')); return false">关闭</a>
		<a href="#" class="btnOpH24 h24Blue in_block" name="roleSubmit">提交</a>
	</div>
</div>

<div class="popLayer w340 hideit" id="privilegePopupLayer">
 	<div class="popTitle clearfix">
		<h1>添加权限</h1>
		<div class="cls"><a href="#" class="xx" onclick="hidePopupDiv($('#privilegePopupLayer')); return false"></a></div>
	</div>
	<div class="popCon">
		<div class="form">
			<div>选择系统：<select name="appId2" class="ml_5">
				<c:forEach items="${appList }" var="app">
					<option value="${app.id }">${app.applicationName }</option>
				</c:forEach>
			</select></div>
			<div class="mt_5">权限名称：<input type="text" name="privilegeName" maxlength="50" value="" class="txtNew w200 ml_5"/><span class="red ml_5">●</span></div>
			<div class="mt_5">权限 Url ：<input type="text" name="privilegeUrl" maxlength="50" value=""  class="txtNew w200 ml_5"/><span class="red ml_5">●</span></div>
            <div class="mt_5">选择类型：
                <select name="privilegeClass" class="ml_5">
                    <option value="功能权限">功能权限</option>
                    <option value="职能权限">职能权限</option>
                    <option value="部门权限">部门权限</option>
                </select>
                <span class="red ml_5">●</span>
            </div>
            <div class="mt_20">
                <p class="mt_5 bold red">功能权限：一般权限，非审批权限</p>
                <p class="mt_5 bold red">职能权限：特殊审批权限，如人事审批，财务审批等</p>
                <p class="mt_5 bold red">部门权限：一般审批权限，如部门经理审批</p>
            </div>
        </div>
	</div><br /><br />
	<div class="popBtn">
		<a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#privilegePopupLayer')); return false">关闭</a>
		<a href="#" class="btnOpH24 h24Blue in_block" name="privilegeSubmit">提交</a>
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="js" value="dy-ajax,privilege/list"/>
</jsp:include>
<script type="text/javascript">
$(function(){
	<c:if test="${user.userCode==config.superAdminUserCode }">
	$("[name=app]").die().click(function(){
		$("[name=applicationName]").val('');
		$("[name=applicationCode]").val('');
		showPopupDiv( $('#appPopupLayer'));
		$("[name=appSubmit]").die().live("click",function(){
			if($("[name=applicationName]").val()==''||$("[name=applicationCode]").val()==''){
				return alert("必须填写系统名称与系统代号");
			}
			ajaxPost({
				url:"/privilege/addApps",
				data:{
					"applicationName":$("[name=applicationName]").val(),
					"applicationCode":$("[name=applicationCode]").val(),
				},
				ok:function(data){
					var html = '<ul>';
					for(var i = 0 ; i < data.appList.length ; i++){
						html += "<li><a href=\"#\" chanelID=\""+data.appList[i].id+"\"><span class=\"ml_10\">"+data.appList[i].applicationName+"</span></a></li>";
					}
					html += '</ul>';
					$("div.appChanel").html(html);
					hidePopupDiv($('#appPopupLayer'));
					showSystemMsg("ok","添加系统成功！");
				},
				fail:function(data){
					showSystemMsg("fail",data.msg);
				}
			});
		});
	});
	</c:if>
});
</script>