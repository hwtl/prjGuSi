<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="员工奖惩"/>
</jsp:include>
<jsp:include page="/WEB-INF/jsp/common/employee_detail_nav.jsp">
    <jsp:param value="reward" name="nav"/>
</jsp:include>
    <appel:checkPrivilege url="reward_add">
	<div class="clearfix">
    <a href="#" class="btn-small btn-green in_block mt_5 js_newreward right">新增奖惩</a>
	</div>
    </appel:checkPrivilege>
<table width="100%" cellspacing="0" class="tableListNew tableNarrow mt_10 js_jobinfo" module="positionInfo">
    <tbody>
    <tr class="trHead">
        <td width="50" nowrap>奖惩类别</td>
        <td width="70" nowrap>生效日期</td>
        <td width="50" nowrap>奖惩渠道</td>
        <td width="100" nowrap>奖惩结果</td>
        <td width="100" nowrap>奖惩依据</td>
        <td>奖惩备注</td>
        <td width="70" nowrap>失效日期</td>
<appel:checkPrivilege url="reward_add">
        <td width="60" nowrap>操作</td>
</appel:checkPrivilege>
    </tr>
    <c:forEach items="${paginate.pageList}" var="reward">
        <tr>
            <td>${reward.typeName}</td>
            <td><fmt:formatDate value="${reward.effectiveTime}" pattern="yyyy-MM-dd"></fmt:formatDate></td>
            <td>${reward.channelName}</td>
            <td>${reward.resultName}</td>
            <td>${reward.rulesTitle}${reward.rulesName}</td>
             <td>${reward.rulesTitle}${reward.remark}</td>
            <td><fmt:formatDate value="${reward.failTime == reward.effectiveTime ? '' : reward.failTime}"
                                pattern="yyyy-MM-dd"></fmt:formatDate></td>
            <appel:checkPrivilege url="reward_add">
            <td>
                <c:choose>
                   <c:when test="${reward.status ==1}">
                     <span class="green">已生效</span>
                   </c:when>
                   <c:when test="${reward.status==0 }">
                      <appel:checkPrivilege url="reward_add">
		              	  <a href="#" data-id="${reward.id}" class="btnMini miniEdit in_block js_edit"></a>
		              	  <a href="#" data-id="${reward.id}" class="ml_5 btnMini miniDel in_block js_delete"></a>
		               </appel:checkPrivilege>
                   </c:when>
                </c:choose>
            </td>
            </appel:checkPrivilege>
        </tr>
    </c:forEach>
    </tbody>
</table>
<!--分页 -->
<app:paginate/>
<!--分页 -->
<!-- 奖惩弹层 -->
<div class="popLayer w600" id="layer_reward">
  
</div>
<script type="text/javascript">
$(function(){
    //新增奖惩--打开弹层
    $('.js_newreward').click(function(){
         $.ajax({
             method: 'GET',
             url:'/reward/add/${emp.userCode}',
             dataType: 'json',
             success: function (response) {
                 if(response.status == 'ok'){
                     $("#layer_reward").html(response.html);
                     $("input.txtDate").date_input();
                     showPopupDiv($('#layer_reward'));
                 }
                 else{
                     alert(response.message);
                 }
             }
         });
    });

    //编辑奖惩--将编辑项ID传给后台，获取编辑弹层html
    $(".js_edit").click(function(){
        var itemId = $.trim($(this).attr("data-id"));
        $.ajax({
            method: 'GET',
            url:'/reward/edit/'+itemId,
            dataType: 'json',
            success: function (response) {
                if(response.status == 'ok'){
                    $("#layer_reward").html(response.html);
                    if($.trim($("#h_effectMonth").val()) != "0"){
                        $("#effectTime").html(Number($.trim($("#h_effectMonth").val()))/12 + "年");
                        $("#trEffectTime").show();
                    }
                    $("input.txtDate").date_input().change();
                    showPopupDiv($('#layer_reward'));
                }
                else{
                    alert(response.message);
                }
            }
        });
    });

    //切换奖惩类别（请求奖惩渠道、奖惩结果等数据）
    $("input[name=rewardtype]").live("click",function(){
        var rewardTypeId = $(this).val();
        $.ajax({
            method: 'GET',
            url:'/reward/queryChannelsWithResults/'+rewardTypeId,
            dataType: 'json',
            success: function (response) {
                if(response.status == 'ok'){
                    //组装-奖惩渠道
                    if(response.data.channelList.length == 0){
                        $("#selChannel").empty();
                        $("#trChannel").hide();
                    }else{
                        var channelHtml = '<option value="">请选择</option>';
                        $.each(response.data.channelList, function(i,n){
                            channelHtml += '<option value="'+n.optionCode+'">'+n.optionValue+'</option>';
                        });
                        $("#selChannel").html(channelHtml);
                        $("#trChannel").show();
                    }; 

                    //组装-奖惩结果
                    var resultHtml = '<option value="">请选择</option>';
                    $.each(response.data.resultList, function(i,n){
                        resultHtml += '<option value="'+n.optionCode+'" data-money="'+n.optionResult+'" data-month="'+n.optionTitle+'">'+n.optionValue+'</option>';
                    });
                    $("#selResult").html(resultHtml);
                }
                else{
                    alert(response.message);
                }
           }
        });
        //清空其他字段
        $("select[name=rules], textarea[name=remark], input[name=effectiveTime]").val("");//奖惩依据，奖惩备注，生效日期
        $("#salaryResult, #effectTime").text("");
        $("#trEffectTime").hide();
        $("#uneffectDate").html("");
		$("#h_effectMonth").val("0");
    });

    //奖惩结果切换
    $("#selResult").live("change",function(){
        var option = $(this).find("option:selected");
        //生成奖惩薪资额度 
        $("#salaryResult").text(option.attr("data-money"));
        //生成有效时长
        var month = option.attr("data-month");
        $("#h_effectMonth").val(month);
        setUneffectDate();
        if(month == undefined || month == "0"){
            $("#effectTime").text("");
            $("#trEffectTime").hide();
            $("#uneffectDate").html("");
        }
        else{
            $("#effectTime").html(Number(month)/12 + "年");
            $("#trEffectTime").show();
        }
    });

    //生效时间change事件
    $("input[name=effectiveTime]").live("change",function(){
    	setUneffectDate();
    });

    
    
    //新增、编辑奖惩表单验证及提交
    $(".js_rewardSubmitForm").live("click", function(){
        var formName = $(this).parents("div.popLayer").eq(0).find("form").eq(0).attr("id");
        var validator = $('#'+formName).validate();
        // validator.refresh();
        if (validator.validateForm()) {
            $('#'+formName).submit();
        }
    });

    //删除
    $('.js_delete').live('click',function () {
        var id = $(this).attr("data-id");
        if (confirm('确实要删除吗？')) {
            $.post('/reward/delete/' + id, {}, function (data) {
                alert(data.message);
                window.location.reload();
            });
        }
    });
    
    function fnCallBack_clear(){
    	setUneffectDate();
    }

    //生成失效时间
    function setUneffectDate(){
    	 var month = Number($("#selResult option:selected").attr("data-month"));
    	 var effectTime=$("input[name=effectiveTime]").val();
        if(effectTime != "" && month > 0){
            var effectiveDate = new Date(effectTime);
            var year = effectiveDate.getFullYear() + Math.floor(month/12);
            var m = effectiveDate.getMonth() + 1 +  month%12;
			var dm;
            var date = effectiveDate.getDate();
            if(m <= 12){
                dm = m;
            }
            else{
                dm = m-12;
                year += 1;
            }
            var newDate = new Date(new Date(year + "-" + dm + "-" +date).getTime()-24*60*60*1000);
            var yyyy = newDate.getFullYear();
            var mm = (newDate.getMonth()+1) < 10 ? ("0" + (newDate.getMonth()+1) ) : (newDate.getMonth()+1);
            var dd = newDate.getDate() < 10 ? ("0" + newDate.getDate()) : newDate.getDate();
            $("#uneffectDate").html("失效日期：<b>"+yyyy + "-" + mm + "-" + dd+"</b>");
        }else{
        	$("#uneffectDate").html("");
        }
    }
});
</script>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param value="changes/queryChange,portal,select" name="js"/>
    <jsp:param name="duiJs" value="validation,WdatePicker,autocomplete,dui_addRemove,paginate"/>
</jsp:include>




