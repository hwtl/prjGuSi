<%--
  Created by IntelliJ IDEA.
  Title:
  User: Jerry.hu
  Create: Jerry.hu (2013-06-03 13:02)
  Description: To change this template use File | Settings | File Templates.
--%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<div class="mt_15 clearfix">
    <span class="in_block mt_5 left f14 bold">岗位信息</span>
    <span class="right">
        <a href="javascript:;" class="btn-small btn-blue in_block right js_save">保存</a>
        <a href="#" action="/employeePosition/${userCode}/details" class="btn-small btn-silver in_block right js_cancel mr_5">取消</a>
    </span>
</div>
<form:form action="/employeePosition/${userCode}/update" rowName="positions" method="post" id="PositionForm">
    <webplus:token />
   <input type="hidden" id = "delId" name="delId"/>
    <table width="100%" cellspacing="0" class="tableListNew mt_10 js_jobinfo" module="positionInfo">
        <tbody>
        <tr class="trHead">
            <td width="50" nowrap="">&nbsp;</td>
            <td  nowrap="">部门</td>
            <td  nowrap="">岗位</td>
            <td width="50" nowrap="">操作</td>
        </tr>
        <tr>
            <td>主要岗位</td>
            <td>${employee.orgName}</td>
            <td>${employee.positionName}</td>
            <td>&nbsp;</td>
        </tr>
        <c:forEach var="partTime" items="${partTimePositions}">
            <tr class="js_dataRowContainer">
                <td>兼职岗位</td>
                <td>${partTime.orgName}</td>
                <td>${partTime.positionName}</td>
                <td><a href="javascript:;" class="btnMini miniDel in_block js_delete_two" id="${partTime.id}"></a></td>
            </tr>
        </c:forEach>
        <tr class="js_insertBefore">
            <td colspan="4"><a href="javascript:;" rel="workExperiences" class="btnOpH24 h24Silver in_block js_add">添加</a></td>
        </tr>
        </tbody>
    </table>
</form:form>

<!-- 职位信息 -->
<script type="text/template" id="jobinfo">
    <tr class="js_dataRowContainer">
        <td>
            兼职岗位 <input type="hidden" vlaue="兼职">
        </td>
        <td>
            <input type="hidden" name="orgId" class="orgId"/>
            <span class="red">●</span> <input autocomplete="off"  type="text" name="department" popdiv="autoComp_keyword" rule="required"  class="deptSelector txtNew t250 in_block" placeholder="请填写正确部门,否则被清空">
            <div id="autoComp_keyword" class="autoComplate"></div>
        </td>
        <td>
            <span>职系：</span>
            <span class="red">●</span>
            <select name="serialId" rule="required" class="js_serialId"  groupby="titleId" >
                <option value="">请选择</option>
                <c:forEach var="titleSerial" items="${titleSerialList}">
                    <option value="${titleSerial.id }" >${titleSerial.serialName }</option>
                </c:forEach>
            </select><br />
            <span>职等：</span>
            <span class="red">●</span>
            <select name="titleId" rule="required" groupby="titleId" class="js_titleId">
                <option value="">请选择</option>
            </select><br />
            <span>岗位：</span>
            <span class="red">●</span>
            <select name="positionId" class="js_positionId" rule="required"  groupby="titleId"  >
                <option value="">请选择</option>
            </select>
        </td>
        <td>
            <a href="javascript:;" class="btnMini miniDel in_block js_delete"></a>
        </td>
    </tr>
</script>
<script type="text/javascript">
    /**
     * 新增岗位记录
     */
    $('.js_jobinfo').addRemoveRow({
        buildDataRowContent: function(self){
            return $('#jobinfo').html();
        },
        addCallback: function(newRow){
            newRow.find('.txtDate').date_input();

        },
        renameCtrl: function(container, currentRow){
            container.data('maxid', Number(container.data('maxid'))+1);
            currentRow.find('[popdiv]').each(function(){
                $(this).attr('popdiv', $(this).attr('popdiv')+container.data('maxid'));
            });
            currentRow.find('.autoComplate').each(function(){
                $(this).attr('id', $(this).attr('id')+container.data('maxid'));
            });
            autocomplete(currentRow.find(".deptSelector"), 'hrm_employee_add', 'ALL', currentRow.find(".orgId"));
        },
        deleteCallback: function(deletedRow){
            return confirm('确定删除');
        }

    });

    /**
     *职系变更事件
     */
    $(".js_serialId").live('change',function(){
        var titleId = $(this).parents("tr").find(".js_titleId");
        if($(this).val() === ''){
            initTitleLevel($(this).next("select").next("select"));
            return false;
        }else{
            autoCompletion('/titleLevel/queryTitle/' + $(this).val(), titleId);
        }
        initTitleLevel($(this).next("select").next("select"));
    });
    /**
     *职等变更事件
     */
    $(".js_titleId").live('change',function(){
        var positionId = $(this).parents("tr").find(".js_positionId");
        autoCompletion('/position/queryPosition/' + $(this).val(), positionId);
    });
    function initTitleLevel(obj){
        obj.empty();
        obj.append("<option value=''>请选择</option>");
    }
</script>