<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
	<b class="f14">家庭主要成员</b><span class="ml_5 red f14">（必填）</span>
	 <span class="right">
				<a href="#" class="btn-small btn-blue in_block right js_save mr_5">保存</a>
				<a href="#" action="/archives/${userCode}/FamilyMemberDetail" class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
	</span>
</p>
<form:form action="/archives/${userCode}/updateFamilyMember" rowName="families" method="post" id="familyMemberForm">
    <webplus:token />
	<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew js_familyMembers mt_15">
		<tbody>
			<tr class="trHead grey999">
				<td  class="bdR">称谓</td>
				<td  class="bdR">姓名</td>
				<td  class="bdR">出生日期</td>
				<td  class="bdR">职业</td>
				<td  class="bdR">工作单位</td>
				<td  class="bdR">联系方式</td>
				<td >操作</td>
			</tr>
			<c:choose>
				<c:when test="${not empty familyMembers}">
					 <c:forEach items="${familyMembers}" var="family" varStatus="vs">
							<tr class="js_dataRowContainer">
								<%--<td class="bdR">--%>
									<%--<input type="text" rule="required" value="${family.alias}" rule="required" name="alias" class="txtNew" maxlength="10"/>--%>
								<%--</td>--%>

                                <td class="bdR">
                                    <select name="${family.alias != '父亲' &&
                                                family.alias != '母亲' &&
                                                family.alias != '配偶' &&
                                                family.alias != '子女'  ? '' : 'alias'}" groupby="alias" rule="required" class="js_selAlias">
                                        <option value="">请选择</option>
                                        <option value="父亲" ${family.alias == '父亲' ? 'selected=selected' : ''}>父亲</option>
                                        <option value="母亲" ${family.alias == '母亲' ? 'selected=selected' : ''}>母亲</option>
                                        <option value="配偶" ${family.alias == '配偶' ? 'selected=selected' : ''}>配偶</option>
                                        <option value="子女" ${family.alias == '子女' ? 'selected=selected' : ''}>子女</option>
                                        <option value="其他" ${family.alias != '父亲' &&
                                                family.alias != '母亲' &&
                                                family.alias != '配偶' &&
                                                family.alias != '子女'  ? 'selected=selected' : ''}>其他</option>
                                    </select>
                                    <input type="text" rule="alias_other" groupby="alias" value="${family.alias}" name="${family.alias != '父亲' &&
                                                family.alias != '母亲' &&
                                                family.alias != '配偶' &&
                                                family.alias != '子女'  ? 'alias' : ''}" class="txtNew t50 js_aliasOther ${family.alias != '父亲' &&
                                                family.alias != '母亲' &&
                                                family.alias != '配偶' &&
                                                family.alias != '子女'  ? '' : 'hideit'}" maxlength="20">
                                </td>

								<td class="bdR">
									<input type="text" rule="required" value="${family.name}" name="name" rule="required" class="txtNew" maxlength="10"/>
								</td>
								<td class="bdR">
									<input type="text" rule="required" value='<fmt:formatDate pattern="yyyy-MM-dd" value="${family.bornDate}"/>' name="bornDate" class="txtDate family_bornDate"  readonly="readonly"/>
								</td>
								<td class="bdR">
									<input type="text" value="${family.job}" name="job" class="txtNew " maxlength="50"/>
								</td>
								<td class="bdR">
										<input type="text" value="${family.workplace}" name="workplace" class="txtNew " maxlength="50"/>
								</td>
								<td class="bdR">
										<input type="text" value="${family.phone }" name="phone" class="txtNew" maxlength="13"/>
								</td>
								<td>
										<a href="javascript:;" class="btnMini miniDel in_block js_delete"></a>
								</td>
							</tr>
					 </c:forEach>
				</c:when>
			</c:choose>
			<tr class="js_insertBefore">
				<td colspan="6"><a href="#" rel="familyMembers" class="btnOpH24 h24Silver in_block js_add">添加</a></td>
			</tr>
		</tbody>
	</table>
</form:form>
