<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="离职面谈列表"/>
</jsp:include>
<div class="container left p100">
			<table border="0" cellspacing="0" cellpadding="0" class="tableFormNew">
				<tbody>
					<tr>
						<td class="tdTitle">关键字：</td>
						 <td class="">
						   <form action="/interview" id="interviewQueryForm">
								<input type="text" name="keywords" value="${param.keywords}" id="_keyWords" class="txt tt450 textH24" placeholder="">
								<a href="javascript:;" id="searchBtn" class="btn-small btn-blue in_block ml_20" onclick="$('#interviewQueryForm').submit();">查询</a>
						   </form>
						</td>
					</tr>
				</tbody>
			</table>

            <p class="clearfix">
                <span class="grey999 left mt_15" style="padding:0px"><b class="red">${paginate.totalCount}</b>&nbsp;符合要求</span>
            </p>
			<table width="100%" cellspacing="0" class="tableListNew mt_10">
				<tbody>
					<tr class="trHead">
						<td>工号</td>
						<td>姓名</td>
						<td>组织名称</td>
						<td>岗位名称</td>
						<td>职等职级</td>
						<td>入职日期</td>
						<td>状态</td>
						<td>操作</td>
					</tr>
					<c:forEach items="${paginate.pageList}" var="vp">
						<tr>
							<td>${vp.userCode}</td>
							<td>${vp.userNameCn}</td>
							<td>${vp.orgName}</td>
							<td>${vp.positionName}</td>
							<td>${vp.levelFull}</td>
							<td>
							  <fmt:formatDate value="${vp.joinDate}" pattern="yyyy-MM-dd"/>
							</td>
							<td>${vp.status}</td>
							<td>
							    <c:if test="${vp.interviewId != null}">
							        <appel:checkPrivilege url="interview_leave_emp">
										<a href="/interview/${vp.userCode}/${vp.interviewId}" target="_blank" class="btnOpH24 h24Silver in_block">面谈</a>
							        </appel:checkPrivilege>
							    </c:if>
								<a class="btnOpH24 h24Silver in_block" id="${vp.userCode}"
									target="_blank" tabname="${vp.userNameCn}_详情"
									href="/employee/${vp.userCode}/details">详情</a><!--跳转到个人详情页 -->
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<div class="clearfix"></div>
    <div class="tabConInfoBtm mt_10" style="padding:0px">
        <app:paginate/>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp"></jsp:include>