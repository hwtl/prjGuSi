<%--
  Created by IntelliJ IDEA.
  Title:
  User: Jerry.hu
  Create: Jerry.hu (2013-06-03 11:57)
  Description:合同表单页面
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<div class="mt_15 clearfix">
    <span class="in_block mt_5 left f14 bold">合同记录</span>
    <span class="right">
        <a href="javascript:;" class="btn-small btn-blue in_block right js_save">保存</a>
        <a href="#" action="/contract/${userCode}/details"
           class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
    </span>
</div>
<form:form action="/contract/${userCode}/update" rowName="contracts" method="post" id="ContractForm">
    <webplus:token />
    <input type="hidden" id = "delId" name="delId"/>
    <table width="100%" cellspacing="0" class="tableListNew mt_10 js_contractinfo" module="contractRecord">
        <tbody>
        <tr class="trHead">
            <td  nowrap="">合同类型</td>
            <td  nowrap="">合同期限</td>
            <td  nowrap="">合同开始时间</td>
            <td  nowrap="">合同结束时间</td>
            <td  nowrap="">操作</td>
        </tr>
        <c:forEach var="contract" items="${contracts}">
            <tr class="js_dataRowContainer">
                <td nowrap="">
                    <input type="hidden" name="id" value="${contract.id}"/>
                    <span class="red">●</span>
                    <select name="contractType" rule="required" >
                    <option value="">请选择</option>
                    <option value="固定期限合同" ${contract.contractType eq '固定期限合同' ? 'selected=selected' : ''}>固定期限合同</option>
                    <option value="无固定期限合同" ${contract.contractType eq '无固定期限合同' ? 'selected=selected' : ''}>无固定期限合同</option>
                    <option value="前程无忧外包协议" ${contract.contractType eq '前程无忧外包协议' ? 'selected=selected' : ''}>前程无忧外包协议</option>
                    <option value="实习协议" ${contract.contractType eq '实习协议' ? 'selected=selected' : ''}>实习协议</option>
                </select>
                </td>
                <td nowrap="">
                    ${contract.yaerCountStr}&nbsp;
                </td>
                <td nowrap="">
                    <span class="red">●</span>
                    <input type="text" name="startTime" rule="required" class="txtNew txtDate" value="<fmt:formatDate value="${contract.startTime}" pattern="yyyy-MM-dd"/>" readonly="readonly">
                </td>
                <td nowrap="">
                    <c:choose>
                        <c:when test="${contract.contractType eq '无固定期限合同'}">
                         	<span class="red">&nbsp;</span>
		                  <input type="text" name="endTime" class="txtNew txtDate" value=" <fmt:formatDate value="${contract.endTime}" pattern="yyyy-MM-dd"/>" readonly="readonly">
                        </c:when>
                        <c:otherwise>
		                    <span class="red">●</span>
		                    <input type="text" name="endTime" rule="required" class="txtNew txtDate" value=" <fmt:formatDate value="${contract.endTime}" pattern="yyyy-MM-dd"/>" readonly="readonly">
                        </c:otherwise>
                    </c:choose>
                </td>
                <td nowrap=""><a href="javascript:;" id="${contract.id}" class="btnMini miniDel in_block js_delete_two"></a> </td>
            </tr>
        </c:forEach>
        <tr class="js_insertBefore">
            <td colspan="6"><a href="javascript:;" rel="workExperiences" class="btnOpH24 h24Silver in_block js_add">添加</a></td>
        </tr>
        </tbody>
    </table>
</form:form>

<!-- 合同记录 -->
<script type="text/template" id="contractinfo">
    <tr class="js_dataRowContainer">
        <td ref="contractType" id="type_1">
            <span class="red">●</span>
            <select name="contractType" rule="required" >
                <option value="">请选择</option>
                <option value="固定期限合同">固定期限合同</option>
                <option value="无固定期限合同">无固定期限合同</option>
                <option value="前程无忧外包协议">前程无忧外包协议</option>
                <option value="实习协议">实习协议</option>
            </select>
        </td>
        <td ref="contractPeriod">&nbsp;</td>
        <td ref="startTime">
            <span class="red">●</span>
            <input type="text" name="startTime" rule="required" class="txtNew txtDate" readonly="readonly">
        </td>
        <td ref="endTime">
            <span class="red">●</span>
            <input type="text" name="endTime" rule="required" class="txtNew txtDate" readonly="readonly">
        </td>
        <td>
            <a href="javascript:;" class="btnMini miniDel in_block js_delete"></a>
        </td>
    </tr>
</script>
<script type="text/javascript">

		$(".js_contractinfo").find("[name=contractType]").live("change", function(){
			if($(this).val() === "无固定期限合同"){
				$(this).parents("tr").find("[ref=endTime] span").text("");
				$(this).parents("tr").find("[name=endTime]").removeAttr("rule");
			} else if($(this).val() === "固定期限合同"){
				$(this).parents("tr").find("[ref=endTime] span").text("●");
				$(this).parents("tr").find("[name=endTime]").attr({"rule":"required"});
			}
		})

	    /**
	     * 新增合同记录
	     */
	    $('.js_contractinfo').addRemoveRow({
	        buildDataRowContent: function(self){
	            return $('#contractinfo').html();
	        },
	        addCallback: function(newRow){
	            newRow.find('.txtDate').date_input();

	        },
	        renameCtrl: function(container, currentRow){
	            container.data('maxid', Number(container.data('maxid'))+1);
	            currentRow.find('[id]').each(function(){
	                $(this).attr('name', $(this).attr('id')+container.data('maxid'));
	            });
	            currentRow.find('[comparedateto]').each(function(){
	                $(this).attr('comparedateto', $(this).attr('comparedateto')+container.data('maxid'));
	            });
	        },
	        deleteCallback: function(deletedRow){
	            return confirm('确定删除');
	        }
	    });

</script>