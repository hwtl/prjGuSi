<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
 <p class="mt_15">
		<b class="f14">基本信息</b>
		 <appel:checkPrivilege url="archives_basic_edit">
			<a href="#" action="/archives/${userCode}/BasicEdit" class="btn-small btn-silver in_block right js_edit">编辑</a>
		 </appel:checkPrivilege>
	</p>
	<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew equalcol mt_15">
				<tbody>
					<tr>
						<td class="tdTitle">姓名：</td>
						<td class="request"></td>
						<td class="bdR bold" >${details.userNameCn }</td>
						<td class="tdTitle">部门：</td>
						<td class="request"></td>
						<td class="bold">${details.orgName}</td>
					</tr>
					<tr>
						<td class="tdTitle">工号：</td>
						<td class="request"></td>
						<td class="bdR bold">${details.userCode }</td>
						<td class="tdTitle">职位：</td>
						<td class="request"></td>
						<td class="bold">${details.positionName }</td>
					</tr>
					<tr>
						<td class="tdTitle">英文名：</td>
						<td class="request">&nbsp;</td>
						<td class="bdR bold">
							${details.userNameEn }
						</td>
						<td class="tdTitle">性别：</td>
						<td class="request"></td>
						<td class="bold">
							${details.sex }
						</td>
					</tr>
					<tr>
						<td class="tdTitle">身份证号：</td>
						<td class="request"></td>
						<td class="bdR bold">${details.credentialsNo }</td>
						<td class="tdTitle">身高：</td>
						<td class="request"></td>
						<td class="bold">${details.userHight}CM</td>
					</tr>
					<tr>
						<td class="tdTitle">出生年月：</td>
						<td class="request"></td>
						<td class="bdR bold">
							<fmt:formatDate value="${details.bornDay}" pattern="yyyy-MM-dd"/>
						</td>
                        <td class="tdTitle">户籍性质：</td>
                        <td class="request"></td>
                        <td class="bold">
                            <c:choose>
                                <c:when test="${details.census==1 }">
                                    外地城镇
                                </c:when>
                                <c:when test="${details.census==0 }">
                                    外地农村
                                </c:when>
                                <c:when test="${details.census==3 }">
                                    上海
                                </c:when>
                                <c:when test="${details.census==4 }">
                                    上海农村
                                </c:when>
                            </c:choose>
                        </td>

					</tr>
					<tr>
						<td class="tdTitle">生日：</td>
						<td class="request"></td>
						<td class="bdR bold">
							<input type="hidden" id="h_birthday_type" value="${details.lunarCalendar}" />
							<input type="hidden" id="h_birthday_solar" 
							value='<fmt:formatDate value="${details.birthday}" pattern="yyyy-MM-dd"/>' />
							<span id="birthday_text"></span>
							<c:choose>
								<c:when test="${details.lunarCalendar==1}">
									<span class="ml_5">(公历)</span>
								</c:when>
								<c:when test="${details.lunarCalendar==0}">
									<span class="ml_5">(农历)</span>
								</c:when>
							</c:choose>
						</td>
                        <td class="tdTitle">血型：</td>
                        <td class="request"></td>
                        <td class="bold">
                            ${details.bloodTypeValue }
                        </td>
					</tr>
					<tr>
						<td class="tdTitle">星座：</td>
						<td class="request"></td>
						<td class="bdR bold">
						${details.constellationValue}
						</td>
                        <td class="tdTitle">健康状况：</td>
                        <td class="request"></td>
                        <td class="bold">
                            <c:choose>
                                <c:when test="${details.health==1 }">
                                    良好
                                </c:when>
                                <c:when test="${details.health==0 }">
                                    一般
                                </c:when>
                            </c:choose>
                        </td>
					</tr>
                    <tr>
                        <td class="tdTitle">是否有从军经历：</td>
                        <td class="request"></td>
                        <td class="bold">
                            <c:choose>
                                <c:when test="${details.enlist==1 }">
                                    是
                                </c:when>
                                <c:when test="${details.enlist==0 }">
                                    否
                                </c:when>
                            </c:choose>
                        </td>
                        <td class="tdTitle">婚姻状况：</td>
                        <td class="request"></td>
                        <td class="bold">
                            ${details.maritalStatusValue}
                        </td>
                    </tr>
					<tr>
						<td class="tdTitle">常用手机：</td>
						<td class="request"></td>
						<td class="bdR bold">
						${details.mobilePhone}
						</td>
						<td class="tdTitle">有无子女：</td>
						<td class="request"></td>
						<td class="bold">
						 <c:choose>
						 	<c:when test="${details.hasChildren==1}">
						 	 有
						 	</c:when>
						 	<c:when test="${details.hasChildren==0}">
						 		无
						 	</c:when>
						 </c:choose>
						 </td>
					</tr>
					<tr>
						<td class="tdTitle">其他手机：</td>
						<td class="request"></td>
						<td class="bdR bold">
							${details.alternatePhone}
						</td>
						<td class="tdTitle">政治面貌：</td>
						<td class="request"></td>
						<td class="bold">
						${details.politicalBackGroundValue}
						</td>
					</tr>
					<tr>
						<td class="tdTitle">办公电话：</td>
						<td class="request"></td>
						<td class="bdR bold">
						${details.officePhone}
						</td>
						<td class="tdTitle">民族：</td>
						<td class="request"></td>
						<td class="bold">
						${details.nationValue}
						</td>
					</tr>
					<tr>
						<td class="tdTitle">家庭电话：</td>
						<td class="request"></td>
						<td class="bdR bold">
							${details.familyTel}
						</td>
						<td class="tdTitle">籍贯：</td>
						<td class="request"></td>
						<td class="bold">
							${details.homeTown}
						</td>
					</tr>
					<tr>
						<td class="tdTitle">常用邮箱：</td>
						<td class="request"></td>
						<td class="bdR bold">
							${details.usedMailBox}
						</td>
						<td class="tdTitle">现住址：</td>
						<td class="request"></td>
						<td class="bold">
							${details.currAddress}
						</td>
					</tr>
					<tr>
						<td class="tdTitle">紧急联系人：</td>
						<td class="request"></td>
						<td class="bdR bold">
							${details.emergencyContacts}
						</td>
						<td class="tdTitle">户口所在地：</td>
						<td class="request"></td>
						<td class="bold">
							${details.accountLocation}
						</td>
					</tr>
					<tr>
						<td class="tdTitle">紧急联系人电话：</td>
						<td class="request"></td>
						<td class="bdR bold">
						${details.emergencyContactsPhone}
						</td>
						<td class="tdTitle">参加工作时间：</td>
						<td class="request"></td>
						<td class="bold">
							<fmt:formatDate value="${details.workTime}" pattern="yyyy-MM-dd"/>
						</td>
					</tr>
					<tr>
						<td class="tdTitle">家庭/父母住址：</td>
						<td class="request"></td>
						<td class="bdR bold">
							${details.familyAddress}
						</td>
						<td class="tdTitle">最高学历：</td>
						<td class="request"></td>
						<td class="bold">
							${details.lastDegreeValue}
                            <c:if test="${not empty details.lastStudyTypeValue}">
                                （${details.lastStudyTypeValue}）
                            </c:if>
						</td>
					</tr>
					<tr>
						<td class="tdTitle">显示手机号码：</td>
						<td class="request"></td>
						<td class="bdR bold">
						   <c:choose>
						   	<c:when test="${details.showPhone==1}">
						   	常用手机
						   	</c:when>
						   	<c:when test="${details.showPhone==2}">
						   	其他手机
						   	</c:when>
						   	<c:when test="${details.showPhone==3}">
						   	办公电话
						   	</c:when>
						   	<c:when test="${details.showPhone==4}">
						   	家庭电话
						   	</c:when>
						   	<c:otherwise>
						   		不显示
						   	</c:otherwise>
						   </c:choose>
						</td>
					</tr>
		</tbody>
</table>
<script type="text/javascript">
	var birthday_text = $("#h_birthday_solar").val();
	if($("#h_birthday_type").val() == 0){//农历
		var lunar = lunarCalendar.solar2lunar(new Date($("#h_birthday_solar").val()));
		birthday_text = lunar.year + '年' + (lunar.isLeap ? '闰' : '') 
			+ lunarCalendar.monthInfo[lunar.month-1] + '月' 
		+ lunarCalendar.dayInfo[lunar.day-1];
	}
	$("#birthday_text").text(birthday_text);
</script>