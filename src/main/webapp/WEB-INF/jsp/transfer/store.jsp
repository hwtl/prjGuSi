<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="门店转区"/>
</jsp:include>
     <div class="contentRight">
         <div class="container left p100 txtLeft">
             <h1 class="f18">门店转区</h1>
             <form id="transForm" action="/transfer/handleStoreTransfer" method="post">
                  <webplus:token/>
                 <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
                     <tbody>
                         <tr>
                             <td class="tdTitle" width="120">转调门店：</td>
                             <td class="request" width="20">●</td>
                             <td width="580">
                                 <input type="hidden" name="orgAIds" id="orgAIds" value="" rule="required" alwayscheck="true" />
                                 <input type="text" class="txtNew tt160" name="orgAName" id="orgAName" popdiv="autoComp_orgA" autocomplete="off" placeholder="请输入门店名称或首字母" />
                                  <span class="grey999 ml_5">可添加多个门店</span>
                                 <div class="clearfix" id="orgANameList">
                                 </div>
                                 <div id="autoComp_orgA" class="autoComplate"></div>
                             </td>
                         </tr>
                         <tr>
                             <td class="tdTitle" width="120">转调后区域：</td>
                             <td class="request" width="20">●</td>
                             <td width="580">
                                 <input type="hidden" name="orgBParentId" id="orgBParentId" value="" />
                                 <input type="text" class="txtNew tt160" name="orgBParentName" id="orgBParentName" rule="required" popdiv="autoComp_orgBParent" autocomplete="off" placeholder="请输入区域名称或首字母" />
                                 <div id="autoComp_orgBParent" class="autoComplate"></div>
                                 <a href="/organization/add" target="_blank" class="ml_10" id="linkCreadteDept">如未找到所属部门，请新增组织。</a>
                             </td>
                         </tr>
                         <tr>
                             <td class="tdTitle">生效日期：</td>
                             <td class="request">●</td>
                             <td>
                                 <input type="text" name="activeDate" class="txtDate" rule="required" readonly="readonly" />
                             </td>
                         </tr>
                     </tbody>
                 </table>

                 <p class="txtRight mt_15">
                     <a href="javascript:void(0)" class="btn-normal btn-blue right ml_15" id="js_submitForm">转调</a>
                 </p>
             </form>
         </div>
     </div>
<div class="clearfix"></div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation,autocomplete,WdatePicker"/>
    <jsp:param name="js" value="boms_common,orgtransfer/initAutoComplete"/>
</jsp:include>
<script type="text/javascript">
$(function(){
    //提交表单并验证
    var validator = $('#transForm').validate();
    $('#js_submitForm').click(function(){
        if(validator.validateForm()){        //对表单进行验证
            $('#transForm').submit();
        }
    });

    //转调门店自动完成初始化
    initAutoComplete({
        urlOrData: '/oms/api/getOrgNameByPy?orgType=门店',
        itemTextKey: 'orgName', //storeName
        dataKey: 'OrgNameList', //autoStore
        inputObj: $('#orgAName'),
        keepVessel: $("#orgAId"),
        keepKey: 'id',  //storeId
        ignoreDefaultCallback: true,
        callback: function(selectedItem, params){
        	 //可添加多个
            var arrIds = $("#orgAIds").val()=="" ? [] : $("#orgAIds").val().split(",");//ID串（提交后台用）
            var isExist = false;//去重
            $.each(arrIds, function(i,node){
                if(selectedItem[params.keepKey] == node){
                    isExist = true;
                }
            });
            if(!isExist){
                arrIds.push(selectedItem[params.keepKey]);
                $("#orgAIds").val(arrIds.toString());

                //门店名称列表更新（页面展示用）
                var listItem = '<div class="miniListBox" >'+selectedItem[params.itemTextKey]+'<a href="javascript:;" class="delx ml_5 in_block" onclick="delStore($(this).parent(\'.miniListBox\'))"></a></div>'
                $("#orgANameList").append(listItem);
            }
            //复位
            $('#orgAName').val("");
            $("#orgAIds").trigger("change");
        }
    });

    //转调后区域自动完成初始化
    initAutoComplete({
        urlOrData: '/oms/api/getOrgNameByPy?orgType=区域',
        itemTextKey: 'orgName', //areaName
        dataKey: 'OrgNameList',  //autoArea
        inputObj: $('#orgBParentName'),
        keepVessel: $("#orgBParentId"),
        keepKey: 'id' //areaId
    });
});

//删除选中的转调门店
function delStore(item){
    var index = $("#orgANameList .miniListBox").index(item);
    var arrIds = $("#orgAIds").val()=="" ? [] : $("#orgAIds").val().split(",");
    arrIds.splice(index, 1);
    $("#orgAIds").val(arrIds.toString());
    item.remove();
    $("#orgAIds").trigger("change");
}
</script>