<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<form id="formBqMark" action="/archives/${details.userCode}/checkArchives" method="post" style="padding:0; margin:0">
<p class="mt_15 clearfix">
    <b class="f14 left">教育经历</b>
     <appel:checkPrivilege url="archives_education_edit">
        <%--<a href="#" action="/archives/${userCode}/EducationExperienceEdit" class="btn-small btn-silver in_block right js_edit">编辑</a>--%>
        <a href="#" action="/archives/${userCode}/eduEdit" class="btn-small btn-silver in_block right js_edit">编辑</a>
    </appel:checkPrivilege>

    <c:if test="${details.status=='0' }">
         <span id="checkBq" class="right mr_20 mt_5 hideit">
            是否添加百强高校标签：
            <label><input type="radio" name="isBq" value="1" class="js_isBq">是</label>
            <label><input type="radio" name="isBq" value="0" class="js_isBq">否</label>
        </span>
    </c:if>

</p>
</form>
<table id="eduTable" border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
	<tbody>
		<tr class="trHead grey999">
			<td  class="bdR">阶段</td>
			<td  class="bdR">时间段</td>
			<td  class="bdR">学校名称</td>
			<td  class="bdR">专业</td>
			<td class="bdR">毕业/肄业</td>
			<td >学位 </td>
		</tr>
		<c:forEach items="${educations}" var="edu">
			<tr>
				<td width="80" class="bdR">${edu.degree}</td>
				<td width="240" class="bdR">
				  <fmt:formatDate value="${edu.startDate}" pattern="yyyy-MM"/> 至
				  <fmt:formatDate value="${edu.endDate}" pattern="yyyy-MM"/>
				</td>
				<td width="177" class="bdR feature">
					<span class="black  in_block">
					    ${empty edu.universityId ? edu.schoolName : edu.universityName}
                    </span>
                    <c:if test="${edu.great == '1'}">
                        <c:if test="${edu.degree == '本科' || edu.degree == '硕士' || edu.degree == '博士'}">
                            <span class="${details.status == '6000' ? 'f_bg_green' : 'f_bg_org'} feature in_block right js_bqClass">百</span>
                        </c:if>
                    </c:if>
				</td>
				<td width="177" class="bdR">${edu.department}</td>
				<td width="168" class="bdR">
					<c:choose>
						<c:when test="${edu.graduation==2}">
							 在读
						</c:when>
						<c:when test="${edu.graduation==1}">
							 毕业
						</c:when>
						<c:when test="${edu.graduation==0}">
							肄业
						</c:when>
					</c:choose>
				</td>
				<td width="116">${edu.eduLevel}</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
    $(function() {
        if($(".js_bqClass").length > 0) {
            $("#checkBq").removeClass("hideit");
        }
    })
</script>