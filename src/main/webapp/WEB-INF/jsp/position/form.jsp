<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="新增岗位-岗位管理"/>
    <jsp:param name="css" value="position/fronterp"/>
</jsp:include>
		<form id="addPositionForm" action="/position/add" method="post">
            <webplus:token />
		<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew ">
			<tbody class="js_chks">
				<tr>
					<td class="tdTitle" width="160">岗位名称：</td>
					<td class="request" width="20">●</td>
					<td width="250" class="bdR"><input type="text" class="txtNew" id="positionName" name="positionName" rule="required" value="${position.positionName}"></td>
					<td class="tdTitle" width="140">岗位英文名称：</td>
					<td class="request" width="20">&nbsp;</td>
					<td width="250"><input type="text" class="txtNew" name="ePositionName" value="${position.ePositionName}"></td>
				</tr>
                <tr>
                    <td class="tdTitle">类型：</td>
                    <td class="request">●</td>
                    <td class="bdR" colspan="4">
                        <select class="txt" name="positionType" rule="required">
                            <option value="">请选择</option>
                            <option value="销售型" ${position.positionType == '销售型' ? 'selected=true' : ''}>销售型</option>
                            <option value="非销售型" ${position.positionType == '非销售型' ? 'selected=true' : ''}>非销售型</option>
                        </select>
                    </td>
                </tr>
                <input type="hidden" id="titleIds" name="titleIds" class="js_hdJson"/>
                <c:forEach var="titleSerial" items="${titleSerialList}">
                <tr>
					<td class="tdTitle">${titleSerial.serialName }：</td>
					<td class="request">&nbsp;</td>
					<td class="bdR" colspan="4">
                        <ul>
                        <c:forEach var="title" items="${titleList}">
                            <li>
                            <c:if test="${title.serialId == titleSerial.id}">
                                <apptl:checkbox checkName="titleName" newValue="${title.id}" checkValue="${positionTitles}" checkText="${title.titleName}"/>
                            </c:if>
                            </li>
                        </c:forEach>
                        </ul>
					</td>
				</tr>
                </c:forEach>

			</tbody>
		</table>
            <input type="hidden" name="id" id="id" value="${position == null ? '0' : position.id}" >
		<p class="txtRight mt_15">
			<a href="/position/list" class="btnOpH34 h34Silver in_block">取消</a>
			<a href="javascript:;" class="btnOpH34 h34Blue opH34 in_block js_submitForm">提交</a>
		</p>
		</form>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation"/>
    <jsp:param name="js" value="portal,position/jobManage_common,select"/>
</jsp:include>
<script type="text/javascript">
    $(function(){
        //提交表单
        var validator = $('#addPositionForm').validate();

        $('a.js_submitForm').bind("click",function(){
            buildJson();
            if(validator.validateForm()){
                if($("#titleIds").val() == "") {
                    alert("请选择职系！");
                    return;
                }
                  ajaxGet({
                      url:"/position/checkName/" + $('#id').val() + "/"+ $('#positionName').val(),
                      ok: function(data) {
                          $('#addPositionForm').submit(); //表示岗位名称可以使用
                      },
                      fail: function(data){
                          alert(data.message);
                      }
                  });
            }
        });

    });

    function buildJson(){
        var result = [];
        $(".js_chks input[type='checkbox']:checked").each(function(){
            result.push($(this).val());
        });
        $('#addPositionForm').find(".js_hdJson").val(result.length > 0 ? result.join(',') : '').change();//保存选中的id，格式为:{titleId:[1,2,3]}
    }

</script>