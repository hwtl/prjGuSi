<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="档案信息"/>
    <jsp:param name="js" value="archives/lunarCalendar"/>
    <jsp:param name="duiCss" value="treeNew"/>
</jsp:include>
<div class="clearfix mt_5">
    <div>
        <jsp:include page="/WEB-INF/jsp/common/employee_detail_nav.jsp">
            <jsp:param value="archives" name="nav"/>
        </jsp:include>

        <div id="statusDiv" class="${details.status==6000?'boxWrapGreen':'boxWrapYellow' } center f14 mt_10 mb_10">
            <c:choose>
                <c:when test="${details.status==0}">
                    档案待确认
                    <appel:checkPrivilege url="archives_sure">
                        <a href="javascript:;" class="btnOpH24 h24Silver in_block ml_5"
                           onclick="confirmDoc()">确认档案</a>
                        <a href="javascript:;" class="btnOpH24 h24Silver in_block ml_5" onclick="showPopupDiv($('#layer_form'))">打回修改</a>
                        <c:if test="${not empty details.rollbackReason}">
                            <div class="mt_10 f12">打回原因：${details.rollbackReason}</div>
                        </c:if>
                    </appel:checkPrivilege>

                </c:when>
                <c:when test="${details.status==6000 }">
                    档案已确认
                    <appel:checkPrivilege url="archives_sure">
                        <a href="/archives/${details.userCode}/uncheckArchives" class="btnOpH24 h24Silver in_block ml_5"
                           onclick="return confirm('您确定撤销确认此人档案吗？')">撤销确认</a>
                        <a href="javascript:;" class="btnOpH24 h24Silver in_block ml_5" onclick="showPopupDiv($('#layer_form'))">打回修改</a>
                    </appel:checkPrivilege>
                </c:when>
                <c:otherwise>
                    档案未完善
                    <c:if test="${not empty details.rollbackReason}">
                        <div class="mt_10 f12">打回原因：${details.rollbackReason}</div>
                    </c:if>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="infoedit">
            <!-- 基本信息 -->
            <div class="formsection">
                <jsp:include page="basic.jsp"></jsp:include>
            </div>
            <div class="formsection">
                <!-- 工作经历-->
                <jsp:include page="WorkExperienceDetail.jsp"></jsp:include>
            </div>
            <div class="formsection">
                <!-- 教育经历-->
                <jsp:include page="EducationExperienceDetail.jsp"></jsp:include>
            </div>
            <div class="formsection">
                <!-- 培训经历-->
                <jsp:include page="TrainingExperienceDetail.jsp"></jsp:include>
            </div>
            <div class="formsection">
                <!-- 家庭成员 -->
                <jsp:include page="FamilyMemberDetail.jsp"></jsp:include>
            </div>
        </div>
    </div>
</div>
<div class="popLayer" id="layer_form" style="top: 7161.5px; left: 535.5px; display: none;">
	<div class="popTitle clearfix">
		<h1>打回修改</h1>
		<div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_form')); return false"></a></div>
	</div>
	<div class="popCon">
		<div class="form boxWrapBlue pt_10 pb_20 pl_10 pr_10 mt_5">
			<div>打回原因：<span class="red">● 请输入打回原因</span></div>
			<div class="center "><textarea id="rollbackReason" name="rollbackReason" class="tANew mt_10 pd_2 w300" maxlength="50"></textarea></div>
		</div>
	</div><br><br>
	<div class="popBtn">
		<a href="javascript:;" class="btn-small btn-silver in_block" onclick="hidePopupDiv($('#layer_form')); return false">关闭</a>
		<a href="javascript:;" action="/archives/${details.userCode}/rollbackArchives" class="btn-small btn-blue in_block js_rollback">提交</a>
	</div>

</div>

<!-- 模板块 -->
<!-- 工作经历 -->
<script type="text/template" id="workExperiences">
    <tr class="js_dataRowContainer">
        <td class="bdR">
            <input type="text" class="txtDate onlyMonth workExper_entryDate" rule="required" groupby="dategroup"
                   value="" field="entryDate" name="entryDate" compareDateTo="departureDate0" id="entryDate0"
                   readonly="readonly"/>
            <input type="text" class="txtDate onlyMonth ml_10 workExper_departureDate" rule="required"
                   groupby="dategroup" field="departureDate" compareDateFrom="entryDate0" id="departureDate0"
                   name="departureDate" value="" readonly="readonly"/>
        </td>
        <td class="bdR">
            <input type="text" name="companyName" class="workExper_companyName txtNew" rule="required"/>
        </td>
        <td class="bdR">
            <input type="text" name="positionName" class="workExper_heldPosition txtNew t75"/>
        </td>
        <td class="bdR">
            <input type="text" name="leaveReason" class="workExper_leaveReason txtNew"/>
        </td>
        <td class="bdR">
            <input type="text" name="prover" class="workExper_provePeople txtNew t50"/>
        </td>
        <td class="bdR">
            <input type="text" name="proverTel" class="workExper_provePeopleTel txtNew" rule="telephone"/>
        </td>
        <td><a href="javascript:;" class="btnMini miniDel in_block js_delete"></a></td>
    </tr>
</script>

<script type="text/template" id="trainingExperiences">
    <tr class="js_dataRowContainer">
        <td class="bdR">
            <input type="text" rule="required" groupby="dategroup" class="txtDate onlyMonth train_startDate"
                   id="startTrainDate0" compareDateTo="endTrainDate0" value="" name="startDate" field="startDate"
                   readonly="readonly"/>
            <input type="text" rule="required" groupby="dategroup" class="txtDate onlyMonth ml_10 train_endDate"
                   value="" name="endDate" id="endTrainDate0" compareDateFrom="startTrainDate0" field="endDate"
                   readonly="readonly"/>
        </td>
        <td class="bdR">
            <input type="text" name="trainName" class="txtNew train_trainName" rule="required"/>
        </td>
        <td class="bdR">
            <input type="text" name="certificate" class="txtNew train_certificate"/>
        </td>
        <td><a href="javascript:;" class="btnMini miniDel in_block js_delete"></a></td>
    </tr>
</script>

<script type="text/template" id="familyMembers">
    <tr class="js_dataRowContainer">
        <%--<td class="bdR">--%>
        <%--<input type="text" name="alias" class="txtNew family_alias" rule="required"/>--%>
        <%--</td>--%>

        <td class="bdR">
            <select name="alias" groupby="alias" rule="required" class="js_selAlias">
                <option value="" selected="selected">请选择</option>
                <option value="父亲">父亲</option>
                <option value="母亲">母亲</option>
                <option value="配偶">配偶</option>
                <option value="子女">子女</option>
                <option value="其他">其他</option>
            </select>
            <input type="text" rule="alias_other" groupby="alias" value="" name="alias_other"
                   class="txtNew t50 js_aliasOther hideit" maxlength="20">
        </td>

        <td class="bdR">
            <input type="text" name="name" class="txtNew family_name" rule="required"/>
        </td>
        <td class="bdR">
            <input type="text" name="bornDate" class="txtDate family_bornDate" rule="required" value=""
                   readonly="readonly"/>
        </td>
        <td class="bdR">
            <input type="text" name="job" class="txtNew family_job"/>
        </td>
        <td class="bdR">
            <input type="text" name="workplace" class="txtNew family_wordUnit"/>
        </td>
        <td class="bdR">
            <input type="text" name="phone" class="txtNew w100 family_phone"/>
        </td>
        <td><a href="javascript:;" class="btnMini miniDel in_block js_delete"></a></td>
    </tr>
</script>
<script type="text/javascript">
    function confirmDoc(){
        if( !$("#checkBq").hasClass("hideit") && $("input:radio.js_isBq:checked").length==0){
            alert("请确认是否添加百强高校标签");
            $("input:radio.js_isBq").eq(0).focus();
            return false;
        }
        else{
            if(confirm('您确定此人档案已完善？')){
                $("#formBqMark").submit();
            }
        }
    }

</script>

<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param value="archives/basic" name="js"/>
    <jsp:param value="WdatePicker,validation,dui_addRemove,plugs/jquery-tree-1.0,autocomplete" name="duiJs"/>
</jsp:include>

