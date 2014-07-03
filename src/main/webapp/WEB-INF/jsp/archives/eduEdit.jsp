<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<p class="mt_15">
	<b class="f14">教育经历</b><span class="ml_5 red f14">（必填，请完善最高学历及以下所有教育经历）</span>
	<span class="right">
        <a href="#" class="btn-small btn-blue in_block right js_save mr_5">保存</a>
        <a href="#" action="/archives/${userCode}/EducationExperienceDetail" class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
	</span>
</p>
<form:form action="/archives/${userCode}/updateEducationExperience" method="post" id="educationForm">

<webplus:token />
	<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15" >
		<tbody>
			<tr class="trHead grey999">
				<td  class="bdR">阶段</td>
				<td  class="bdR">时间段</td>
				<td  class="bdR">学校名称</td>
				<td  class="bdR">专业</td>
				<td  class="bdR">毕业/肄业</td>
				<td >学位 </td>
			</tr>

            <tr>
                <td class="bdR">高中及以下<input type="hidden" name="educations[0].degree" value="高中及以下"/> </td>
                <td class="bdR">
                <input type="text" rule="fillrow" name="educations[0].startDate" id="startxtDate0" compareDateTo="endDate0" class="txtDate onlyMonth educationExper_startDate"
                     value='<fmt:formatDate value="${educationMap['高中及以下'].startDate}" pattern="yyyy-MM"/>'
                     onlymonthdate='<fmt:formatDate value="${educationMap['高中及以下'].startDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" />
                <input type="text" name="educations[0].endDate" id="endDate0" compareDateFrom="startxtDate0" class="txtDate onlyMonth ml_10 educationExper_endDate"
                     value='<fmt:formatDate value="${educationMap['高中及以下'].endDate}" pattern="yyyy-MM" />'
                     onlymonthdate='<fmt:formatDate value="${educationMap['高中及以下'].endDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" /></td>
                <td class="bdR">
                    <input type="text" name="educations[0].schoolName" class="txtNew tt160" maxlength="100" value="${educationMap['高中及以下'].schoolName}" placeholder="请填写学校名称"/>
                </td>
                <td class="bdR">&nbsp;</td>
                <td class="bdR">
                      <select name="educations[0].graduation">
                            <option value="">请选择</option>
                            <option value="2" ${educationMap['高中及以下'].graduation==2 ? 'selected=selected' : ''}>在读</option>
                            <option value="1" ${educationMap['高中及以下'].graduation==1 ? 'selected=selected' : ''}>毕业</option>
                            <option value="0" ${educationMap['高中及以下'].graduation==0 ? 'selected=selected' : ''}>肄业</option>
                      </select>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>


            <tr>
                <td class="bdR">大专<input type="hidden" name="educations[1].degree" value="大专"/> </td>
                <td class="bdR">
                <input type="text" rule="fillrow" name="educations[1].startDate" id="startxtDate1" compareDateTo="endDate1" class="txtDate onlyMonth educationExper_startDate"
                     value='<fmt:formatDate value="${educationMap['大专'].startDate}" pattern="yyyy-MM"/>'
                     onlymonthdate='<fmt:formatDate value="${educationMap['大专'].startDate}" pattern="yyyy-MM-dd"/>' readonly="readonly"/>
                <input type="text" name="educations[1].endDate" id="endDate1" compareDateFrom="startxtDate1" class="txtDate onlyMonth ml_10 educationExper_endDate"
                     value='<fmt:formatDate value="${educationMap['大专'].endDate}" pattern="yyyy-MM" />'
                     onlymonthdate='<fmt:formatDate value="${educationMap['大专'].endDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" /></td>
                <td class="bdR">

                    <input type="text" class="txtNew tt160 in_block" id="autoC1" popdiv="autoComp_keyword1" name="educations[1].schoolName" maxlength="100" value="${educationMap['大专'].universityName}" placeholder="请填写并选择学校" />
                    <input type="hidden" name="educations[1].universityId" class="js_universityId" value="${educationMap['大专'].universityId}">
                    <p><a href="http://bangzhu.${user.companyENLower}.com/recourse/add" class="help in_block" target="blank">没找到我的学校</a></p>
                    <div id="autoComp_keyword1" class="autoComplate"></div>

                </td>
                <td class="bdR"><input type="text" name="educations[1].department" class="txtNew " maxlength="20"  value="${educationMap['大专'].department}" /></td>
                <td class="bdR">
                      <select name="educations[1].graduation">
                          <option value="">请选择</option>
                          <option value="2" ${educationMap['大专'].graduation==2 ? 'selected=selected' : ''}>在读</option>
                          <option value="1" ${educationMap['大专'].graduation==1 ? 'selected=selected' : ''}>毕业</option>
                          <option value="0" ${educationMap['大专'].graduation==0 ? 'selected=selected' : ''}>肄业</option>
                      </select>
                </td>
                <td>
                    &nbsp;
                </td>
            </tr>

            <tr>
                <td class="bdR">本科<input type="hidden" name="educations[2].degree" value="本科"/> </td>
                <td class="bdR">
                <input type="text" rule="fillrow" name="educations[2].startDate" id="startxtDate2" compareDateTo="endDate2" class="txtDate onlyMonth educationExper_startDate"
                     value='<fmt:formatDate value="${educationMap['本科'].startDate}" pattern="yyyy-MM"/>'
                     onlymonthdate='<fmt:formatDate value="${educationMap['本科'].startDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" />
                <input type="text" name="educations[2].endDate" id="endDate2" compareDateFrom="startxtDate2" class="txtDate onlyMonth ml_10 educationExper_endDate"
                     value='<fmt:formatDate value="${educationMap['本科'].endDate}" pattern="yyyy-MM" />'
                     onlymonthdate='<fmt:formatDate value="${educationMap['本科'].endDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" /></td>
                <td class="bdR">

                    <input type="text" class="txtNew tt160 in_block" id="autoC2" popdiv="autoComp_keyword2" name="educations[2].schoolName" maxlength="100" value="${educationMap['本科'].universityName}" placeholder="请填写并选择学校" />
                    <input type="hidden" name="educations[2].universityId" class="js_universityId" value="${educationMap['本科'].universityId}">
                    <p><a href="http://bangzhu.${user.companyENLower}.com/recourse/add" class="help in_block" target="blank">没找到我的学校</a></p>
                    <div id="autoComp_keyword2" class="autoComplate"></div>

                </td>
                <td class="bdR"><input type="text" name="educations[2].department" class="txtNew " maxlength="20"  value="${educationMap['本科'].department}" /></td>
                <td class="bdR">
                      <select name="educations[2].graduation">
                          <option value="">请选择</option>
                          <option value="2" ${educationMap['本科'].graduation==2 ? 'selected=selected' : ''}>在读</option>
                          <option value="1" ${educationMap['本科'].graduation==1 ? 'selected=selected' : ''}>毕业</option>
                          <option value="0" ${educationMap['本科'].graduation==0 ? 'selected=selected' : ''}>肄业</option>
                      </select>
                </td>
                <td>
                     <select name="educations[2].eduLevel">
                         <option value="">请选择</option>
                         <option value="无" ${educationMap['本科'].eduLevel=='无'?'selected':''}>无</option>
                         <option value="学士" ${educationMap['本科'].eduLevel=='学士' ? 'selected=selected' : ''}>学士</option>
                     </select>
                </td>
            </tr>

            <tr>
                <td class="bdR">硕士<input type="hidden" name="educations[3].degree" value="硕士"/> </td>
                <td class="bdR">
                <input type="text" rule="fillrow" name="educations[3].startDate" id="startxtDate3" compareDateTo="endDate3" class="txtDate onlyMonth educationExper_startDate"
                     value='<fmt:formatDate value="${educationMap['硕士'].startDate}" pattern="yyyy-MM"/>'
                     onlymonthdate='<fmt:formatDate value="${educationMap['硕士'].startDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" />
                <input type="text" name="educations[3].endDate" id="endDate3" compareDateFrom="startxtDate3" class="txtDate onlyMonth ml_10 educationExper_endDate"
                     value='<fmt:formatDate value="${educationMap['硕士'].endDate}" pattern="yyyy-MM" />'
                     onlymonthdate='<fmt:formatDate value="${educationMap['硕士'].endDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" /></td>
                <td class="bdR">

                    <input type="text" class="txtNew tt160 in_block" id="autoC3" popdiv="autoComp_keyword3" name="educations[3].schoolName" maxlength="100" value="${educationMap['硕士'].universityName}" placeholder="请填写并选择学校" />
                    <input type="hidden" name="educations[3].universityId" class="js_universityId" value="${educationMap['硕士'].universityId}">
                    <p><a href="http://bangzhu.${user.companyENLower}.com/recourse/add" class="help in_block" target="blank">没找到我的学校</a></p>
                    <div id="autoComp_keyword3" class="autoComplate"></div>

                </td>
                <td class="bdR"><input type="text" name="educations[3].department" class="txtNew " maxlength="20"  value="${educationMap['硕士'].department}" /></td>
                <td class="bdR">
                      <select name="educations[3].graduation">
                          <option value="">请选择</option>
                          <option value="2" ${educationMap['硕士'].graduation==2 ? 'selected=selected' : ''}>在读</option>
                          <option value="1" ${educationMap['硕士'].graduation==1 ? 'selected=selected' : ''}>毕业</option>
                          <option value="0" ${educationMap['硕士'].graduation==0 ? 'selected=selected' : ''}>肄业</option>
                      </select>
                </td>
                <td>
                    <select name="educations[3].eduLevel">
                        <option value="">请选择</option>
                        <option value="无" ${educationMap['硕士'].eduLevel=='无'?'selected':''}>无</option>
                        <option value="硕士" ${educationMap['硕士'].eduLevel=='硕士'?'selected':''}>硕士</option>
                    </select>
                </td>
            </tr>

            <tr>
                <td class="bdR">博士<input type="hidden" name="educations[4].degree" value="博士"/> </td>
                <td class="bdR">
                <input type="text" rule="fillrow" name="educations[4].startDate" id="startxtDate4" compareDateTo="endDate4" class="txtDate onlyMonth educationExper_startDate"
                     value='<fmt:formatDate value="${educationMap['博士'].startDate}" pattern="yyyy-MM"/>'
                     onlymonthdate='<fmt:formatDate value="${educationMap['博士'].startDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" />
                <input type="text" name="educations[4].endDate" id="endDate4" compareDateFrom="startxtDate4" class="txtDate onlyMonth ml_10 educationExper_endDate"
                     value='<fmt:formatDate value="${educationMap['博士'].endDate}" pattern="yyyy-MM" />'
                     onlymonthdate='<fmt:formatDate value="${educationMap['博士'].endDate}" pattern="yyyy-MM-dd"/>' readonly="readonly" /></td>
                <td class="bdR">

                    <input type="text" class="txtNew tt160 in_block" id="autoC4" popdiv="autoComp_keyword4" name="educations[4].schoolName" maxlength="100" value="${educationMap['博士'].universityName}" placeholder="请填写并选择学校" />
                    <input type="hidden" name="educations[4].universityId" class="js_universityId" value="${educationMap['博士'].universityId}">
                    <p><a href="http://bangzhu.${user.companyENLower}.com/recourse/add" class="help in_block" target="blank">没找到我的学校</a></p>
                    <div id="autoComp_keyword4" class="autoComplate"></div>

                </td>
                <td class="bdR"><input type="text" name="educations[4].department" class="txtNew " maxlength="20"  value="${educationMap['博士'].department}" /></td>
                <td class="bdR">
                      <select name="educations[4].graduation">
                            <option value="">请选择</option>
                          <option value="2" ${educationMap['博士'].graduation==2 ? 'selected=selected' : ''}>在读</option>
                          <option value="1" ${educationMap['博士'].graduation==1 ? 'selected=selected' : ''}>毕业</option>
                          <option value="0" ${educationMap['博士'].graduation==0 ? 'selected=selected' : ''}>肄业</option>
                      </select>
                </td>
                <td>
                    <select name="educations[4].eduLevel">
                        <option value="">请选择</option>
                        <option value="无" ${educationMap['博士'].eduLevel=='无'?'selected':''}>无</option>
                        <option value="博士" ${educationMap['博士'].eduLevel=='博士'?'selected':''}>博士</option>
                    </select>
                </td>
            </tr>

		</tbody>
	</table>
</form:form>
<script type="text/javascript">
$(function(){

    var education_keyword_current = "";
    var config = {
        urlOrData : '/oms/api/getUniversity',
        itemTextKey : 'name',
        dataKey : 'schoolList',
        selectCallback : function(selectedItem, context){		//选中选项， 执行回调
            context.data("verified", true);
            context.parents("td").find("input:hidden.js_universityId").eq(0).val(selectedItem.id);
        },
        setRequestParams : function(requestParams){
            requestParams.keyword = education_keyword_current; //输入参数(用户输入)
        },
        crossDomain: false
    };


    var isComplete = true;
	$('[popdiv]').keyup(function(event){
		if(isComplete === false){
			event.stopImmediatePropagation();
		}
	}).click(function(event){
		if(isComplete === false){
			event.stopImmediatePropagation();
		}
	});

	$('[popdiv]').autocomplete(config).bind("blur", function(){
		if($(this).data("verified") === false){
			$(this).val("");
		}
	}).bind("keyup", function(event){
		if(event.keyCode != 13){ //除了回车
            education_keyword_current = $(this).val();
            $(this).data("verified", false);
		}
	});

});
</script>