<%@page import="java.util.Calendar" %>
<%@page import="com.gusi.boms.common.Configuration" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
    <b class="f14">基本信息</b><span class="ml_10 grey999">标注“<span class="red">●</span>”为必填项</span>
			<span class="right">
				<a href="#" class="btn-small btn-blue in_block right js_save mr_5">保存</a>
				<a href="#" action="/archives/${userCode}/basic"
                   class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
			</span>
</p>
<form:form id="basicinfoForm" name="basicinfoForm" modelAttribute="details" method="post"
           action="/archives/${userCode}/updateArchivesDetails">
<webplus:token/>
<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew equalcol mt_15">
<tbody>
<tr>
    <td class="tdTitle">姓名：</td>
    <td class="request"></td>
    <td class="bdR bold">${details.userNameCn }</td>
    <td class="tdTitle">部门：</td>
    <td class="request"></td>
    <td class="bold">${details.orgName }</td>
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
    <td class="request"></td>
    <td class="bdR bold">
        <form:input path="userNameEn" class="txtNew" maxlength="20"/>
    </td>
    <td class="tdTitle">性别：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:radiobutton path="sex" id="newArchiveForm_sexnan" value="男" rule="required"/>
        <label for="newArchiveForm_sexnan">男</label>

        <form:radiobutton path="sex" cssClass="ml_5" id="newArchiveForm_sexnv" value="女" rule="required"/>
        <label for="newArchiveForm_sexnv">女</label>
    </td>
</tr>
<tr>
    <td class="tdTitle">身份证号：</td>
    <td class="request"></td>
    <td class="bdR bold">${details.credentialsNo}</td>
    <td class="tdTitle">身高：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:input path="userHight" id="newArchiveForm_userHight" class="txtNew" rule="required integer"/>
        CM
    </td>
</tr>
<tr>
    <td class="tdTitle">出生年月：</td>
    <td class="request"></td>
    <td class="bdR bold">
        <fmt:formatDate value="${details.bornDay}" pattern="yyyy-MM-dd"/>
    </td>
    <td class="tdTitle">户籍性质：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:radiobutton path="census" id="newArchiveForm_type1" value="1" rule="required"/>
        <label for="newArchiveForm_type1">外地城镇</label>
        <form:radiobutton path="census" id="newArchiveForm_type2" value="0" rule="required"/>
        <label for="newArchiveForm_type2">外地农村</label>
        <form:radiobutton path="census" id="newArchiveForm_type3" value="3" rule="required"/>
        <label for="newArchiveForm_type3">上海</label>
        <form:radiobutton path="census" id="newArchiveForm_type4" value="4" rule="required"/>
        <label for="newArchiveForm_type4">上海农村</label>
    </td>
</tr>
<tr>
    <td class="tdTitle">生日：</td>
    <td class="request">●</td>
    <td class="bdR">
        <input type="hidden" id="h_solar_birthday"/>
        <input type="hidden" name="bornYear" id="h_birthdayY"/>
        <input type="hidden" name="bornMonth" id="h_birthdayM"/>
        <input type="hidden" name="bornDayTime" id="h_birthdayD"/>
        <form:select path="lunarCalendar" id="birthday_type">
            <form:option value="1">公历</form:option>
            <form:option value="0">农历</form:option>
        </form:select>
        <!--公历生日 begin-->
        <input type="text" value='<fmt:formatDate value="${details.birthday}" pattern="yyyy-MM-dd" />'
               class="txtDate" rule="required" groupby="lunarBirth" name="birthday" id="b_gongli"
               readonly="readonly" yearFrom="1936"
               yearTo="<%=Calendar.getInstance().get(Calendar.YEAR) %>"/>
        <!--公历生日 end-->
        <!--农历生日 begin-->
        <select id="b_selYear" rule="required" name="b_selYear" groupby="lunarBirth" class="hideit">
            <option value="">年</option>
        </select>
        <select id="b_selMonth" rule="required" name="b_selMonth" groupby="lunarBirth" class="hideit">
            <option value="">月</option>
        </select>
        <select id="b_selDay" rule="required" name="b_selDay" groupby="lunarBirth" class="hideit">
            <option value="">日</option>
        </select>
        <br>
        <span class="grey999  gongliNotice ">该日期仅用于生日提醒</span>
    </td>
    <td class="tdTitle">血型：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:select path="bloodType" id="newArchiveForm_bloodType" rule="required">
            <option value="">请选择血型</option>
            <form:options items="${bloodMap}"/>
        </form:select>
    </td>

</tr>
<tr>
    <td class="tdTitle">星座：</td>
    <td class="request">●</td>
    <td class="bdR bold">
        <span class="js_constellation"></span>
        <form:hidden path="constellation"/>
    </td>
    <td class="tdTitle">健康状况：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:select path="health" id="newArchiveForm_health" rule="required">
            <option value="">请选择健康状况</option>
            <form:option value="1">良好</form:option>
            <form:option value="0">一般</form:option>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdTitle">是否有从军经历：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:radiobutton path="enlist" id="newArchiveForm_isEnlist1" value="1" rule="required"/>
        <label for="newArchiveForm_isEnlist1">是</label>
        <form:radiobutton path="enlist" id="newArchiveForm_isEnlist0" value="0" rule="required"/>
        <label for="newArchiveForm_isEnlist0">否</label>
    </td>
    <td class="tdTitle">婚姻状况：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:select path="maritalStatus" id="newArchiveForm_maritalStatus" rule="required">
            <option value="">请选择婚姻状况</option>
            <form:options items="${maritalMap}"/>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdTitle">常用手机：</td>
    <td class="request">●</td>
    <td class="bdR bold">
        <form:input path="mobilePhone" id="newArchiveForm_mobilePhone" class="txtNew" rule="required moblie"/>
    </td>
    <td class="tdTitle">有无子女：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:radiobutton path="hasChildren" id="newArchiveForm_isHavaChildren1" value="1" rule="required"/>
        <label for="newArchiveForm_isHavaChildren1">有</label>
        <form:radiobutton path="hasChildren" id="newArchiveForm_isHavaChildren0" value="0" rule="required"/>
        <label for="newArchiveForm_isHavaChildren0">无</label>
    </td>
</tr>
<tr>
    <td class="tdTitle">其他手机：</td>
    <td class="request"></td>
    <td class="bdR bold">
        <form:input path="alternatePhone" id="newArchiveForm_alternatePhone" rule="moblie" class="txtNew"/>
    </td>
    <td class="tdTitle">政治面貌：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:select path="politicalBackGround" id="newArchiveForm_politicalBackground" rule="required">
            <option value="">请选择政治面貌</option>
            <form:options items="${politicalMap}"/>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdTitle">办公电话：</td>
    <td class="request"></td>
    <td class="bdR bold">
        <form:input path="officePhone" id="newArchiveForm_officePhone" rule="telephone" class="txtNew"/>
    </td>
    <td class="tdTitle">民族：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:select path="nation" id="newArchiveForm_nation" rule="required">
            <option value="">请选择民族</option>
            <form:options items="${nationList}" itemLabel="optionValue" itemValue="optionCode"/>
        </form:select>
    </td>
</tr>
<tr>
    <td class="tdTitle">家庭电话：</td>
    <td class="request"></td>
    <td class="bdR bold">
        <form:input path="familyTel" id="newArchiveForm_familyTel" rule="telephone" class="txtNew"/>
    </td>
    <td class="tdTitle">籍贯：</td>
    <td class="request">●</td>
    <td class="">
        <form:input path="homeTown" id="newArchiveForm_homeTown" class="txtNew w200" rule="required"/>
        <p class="grey999">
            例如：XX省XX市
        </p>
    </td>
</tr>
<tr>
    <td class="tdTitle">常用邮箱：</td>
    <td class="request">●</td>
    <td class="bdR bold">
        <form:input path="usedMailBox" id="newArchiveForm_usedMailbox" class="txtNew" rule="required"/>
    </td>
    <td class="tdTitle">现住址：</td>
    <td class="request">●</td>
    <td class="bold">
        <form:input path="currAddress" id="newArchiveForm_currAddress" class="txtNew w200" rule="required"/>
    </td>
</tr>
<tr>
    <td class="tdTitle">紧急联系人：</td>
    <td class="request">●</td>
    <td class="bdR bold">
        <form:input path="emergencyContacts" id="newArchiveForm_emergencyContacts" class="txtNew" rule="required"/>
    </td>
    <td class="tdTitle">户口所在地：</td>
    <td class="request">●</td>
    <td class="">
        <form:select path="familyAddressProvince" groupby="addressOption" id="newArchiveForm_familyAddressProvince"
                     rule="required">
            <option value="">请选择省份</option>
            <form:options items="${provinceMap}"/>
        </form:select>
        <br>
        <form:select path="familyAddressCity" groupby="addressOption" id="newArchiveForm_familyAddressCity"
                     rule="required">
            <option value="">请选择城市</option>
            <form:options items="${cityMap}"/>
        </form:select>
        <br/>
        <form:input path="familyAddressDetail" groupby="addressOption" id="newArchiveForm_familyAddress"
                    class="txtNew w200" rule="required"/>
        <p class="grey999">
            请填写身份证详细的户口所在地，无需重复填写省、市
        </p>

        <p class="red mt_5 bold">
            请务必填写正确、完整，保证真实有效
        </p></td>
</tr>
<tr>
    <td class="tdTitle">紧急联系人电话：</td>
    <td class="request">●</td>
    <td class="bdR bold">
        <form:input path="emergencyContactsPhone" id="newArchiveForm_emergencyContactsPhone" class="txtNew"
                    rule="required moblie"/>
    </td>
    <td class="tdTitle">参加工作时间：</td>
    <td class="request">●</td>
    <td>
        <form:input path="workTime" id="newArchiveForm_workTime" class="txtDate bg_none" rule="required"/>
    </td>
</tr>
<tr>
    <td class="tdTitle">家庭/父母住址：</td>
    <td class="request">●</td>
    <td class="bdR bold">
        <form:input path="familyAddress" id="newArchiveForm_familyAddress" class="txtNew w200" rule="required"/>
    </td>
    <td class="tdTitle">最高学历：</td>
    <td class="request">●</td>
    <td class="bold">
        <%--<form:select path="lastDegree" id="newArchiveForm_latestDegree" rule="required">
            <option value="">请选择学历</option>
            <form:options items="${degreeMap}"/>
        </form:select>
        --%>

        <select rule="required" id="lastDegree" name="lastDegree">
            <option value="">请选择学历</option>
            <c:forEach items="${degreeList}" var="degree">
                <option ${degree.optionCode == details.lastDegree ? 'selected' : ''} value="${degree.optionCode}">${degree.optionValue}</option>
            </c:forEach>
        </select>

        <select rule="required" id="lastStudyType" name="lastStudyType" class="${details.lastDegree == '5008' or details.lastDegree == '5011' or details.lastDegree == '5007' ? '' : 'hideit'}">
            <option value="">请选择</option>
            <c:forEach items="${studyTypeList}" var="studyType">
                <option ${studyType.optionCode == details.lastStudyType ? 'selected' : ''} value="${studyType.optionCode}">${studyType.optionValue}</option>
            </c:forEach>
        </select>
    </td>
</tr>

<tr>
    <td class="tdTitle">显示手机号码：</td>
    <td class="request">●</td>
    <td class="bdR" colspan="4">
       <span>
            <form:radiobutton path="showPhone" id="newArchiveForm_showPhone1"
                              value="1" rule="required"/>
            <label for="newArchiveForm_showPhone1">常用手机</label>
            <form:radiobutton path="showPhone" id="newArchiveForm_showPhone2"
                              value="2" rule="required"/>
            <label for="newArchiveForm_showPhone2">其他手机</label>
            <form:radiobutton path="showPhone" id="newArchiveForm_showPhone0"
                              value="0" rule="required"/>
            <label for="newArchiveForm_showPhone0">不选</label>
       </span>
    </td>
</tr>
</tbody>
</table>
</form:form>
<script type="text/javascript" src="/static/js/archives/birthday.js?version=<%=Configuration.getInstance().getCurrentVersion()%>"></script>
<script type="text/javascript">
    $(function(){

        $("#lastDegree").change(function(){
            var self = $(this);
            var degree = self.val();
            $("#lastStudyType").val("");
            if(degree != "" && degree != '5007' && degree != '5008' && degree != '5011') {
                $("#lastStudyType").addClass("hideit");
            } else {
                $("#lastStudyType").removeClass("hideit");
            }
        })

    })
</script>
