<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="组织转调列表"/>
</jsp:include>
<div class="container left p100">
    <div class="boxWrapBlue">
        <div class="boxWrapFilter2">
            <p class="js_searchBox">
                <form action="/transfer/org/list" method="get" id="orgTransferSearch">
                    <span class="ml_15">转调类型：</span>
                    <select id="transferMode" name="transferMode">
                        <option value="999" ${empty param.transferMode ? "selected=true" : ""}>全选</option>
                        <option value="1" ${param.transferMode == 1 ? "selected=true" : ""}>单组转调</option>
                        <option value="2" ${param.transferMode == 2 ? "selected=true" : ""}>两组对调</option>
                        <option value="3" ${param.transferMode == 3 ? "selected=true" : ""}>门店转区</option>
                    </select>
                    <span class="ml_15">是否生效：</span>
                    <select id="status" name="status">
                        <option value="999" ${empty param.status ? "selected=true" : ""}>全选</option>
                        <option value="1" ${param.status == 1 ? "selected=true" : ""}>已生效</option>
                        <option value="0" ${param.status == 0 ? "selected=true" : ""}>未生效</option>
                    </select>
                    <a href="javascript:;" class="btnOpH24 h24Blue in_block ml_20 js_search" onclick="$('#orgTransferSearch').submit();">搜索</a>
                    <a href="/transfer/org/list" class="btnOpH24 h24Silver in_block ml_5 js_reset">重置</a>
                </form>
            </p>
        </div>
    </div>
    <p class="clearfix">
        <span class="grey999 left mt_15"><b class="red">${paginate.totalCount}</b>&nbsp;符合要求</span>
        <appel:checkPrivilege url="organization_transfer">
            <a href="/transfer/single" target="_blank" class="mt_10 btn-small btn-green in_block mr_5 js_hrefs right">单组转调</a>
            <a href="/transfer/branchSwap" target="_blank" class="mt_10 btn-small btn-green in_block mr_5 js_hrefs right">两组对调</a>
            <a href="/transfer/store" target="_blank" class="mt_10 btn-small btn-green in_block mr_5 js_hrefs right">门店转区</a>
        </appel:checkPrivilege>
    </p>
			<table width="100%" cellspacing="0" class="tableListNew tableNarrow mt_10">
				<tbody>
					<tr class="trHead">
						<td>提交日期</td>
						<td>转调类型</td>
						<td>转调前组织名</td>
						<td>转调前所在部门</td>
						<td>转调后组织名</td>
						<td>转调后所在部门</td>
						<td>状态</td>
						<td width="70" nowrap>生效日期</td>
						<td width="70" nowrap>操作</td>
					</tr>
					<c:forEach items="${paginate.pageList}" var="transfer">
						<tr>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${transfer.createTime}"></fmt:formatDate></td>
							<td>
								 <c:choose>
								    <c:when test="${transfer.transferMode == 1}">
								       单组转调
								    </c:when>
								    <c:when test="${transfer.transferMode == 2}">
								     两组对调
								    </c:when>
								    <c:when test="${transfer.transferMode == 3}">
								     门店转区
								    </c:when>
								 </c:choose>
							</td>
							<td>${transfer.orgAName}</td>
							<td>${transfer.orgAParentName}</td>
							<td>${transfer.orgBName}</td>
							<td>${transfer.orgBParentName}</td>
							<td>${transfer.status == 0 ? '<span class="red bold">未生效</span>' : '<span class="green bold">已生效</span>'}</td>
							<td><fmt:formatDate pattern="yyyy-MM-dd" value="${transfer.activeDate}"></fmt:formatDate></td>
							<td>
							  <c:if test="${transfer.status==0 }">
								  <appel:checkPrivilege url="organization_transfer">
								 	 <a href="javascript:;" transferId="${transfer.id}" class="js_transferDel btn-small btn-red in_block ml_5">取消</a>
								  </appel:checkPrivilege>
							  </c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<div class="clearfix"></div>
    <!--分页 -->
            <div class="tabConInfoBtm mt_10" style="padding:0px">
                <app:paginate/>
            </div>
    <!--分页 -->
</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp"></jsp:include>
<script type="text/javascript">
$(function(){
	 $("a.js_transferDel").click(function(){
		 if (confirm("您确定要取消本次组织转调？")) {
		    var transferId=$(this).attr("transferId");
		   $.post("/transfer/"+transferId+"/del",function(data){
			   alert(data.message);  
			   window.location.reload();
		    });
		   }
	 });
 });
</script>