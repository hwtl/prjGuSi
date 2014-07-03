<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="两组对调"/>
</jsp:include>
  <div class="contentRight">
      <div class="container left p100 txtLeft">
          <h1 class="f18">两组对调</h1>
          <form id="transForm" action="/transfer/handleBranchSwap" method="post">
           	 <webplus:token/>
              <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_5">
                  <tbody>
                      <tr>
                          <td class="tdTitle" width="120">对调分行：</td>
                          <td class="request" width="20">●</td>
                          <td width="580">
                              <input type="hidden" name="orgAId" id="orgAId" value="" />
                              <input type="text" name="orgAName" id="orgAName" class="txtNew tt160" popdiv="autoComp_orgA" rule="required" groupby="orgName" autocomplete="off" placeholder="请输入分行名称或首字母" />&nbsp;&nbsp;
                              与&nbsp;&nbsp;
                              <input type="hidden" name="orgBId" id="orgBId" value="" />
                              <input type="text" name="orgBName" id="orgBName" class="txtNew tt160" popdiv="autoComp_orgB" rule="required" groupby="orgName" autocomplete="off" placeholder="请输入分行名称或首字母" />
                              <div id="autoComp_orgA" class="autoComplate"></div>
                              <div id="autoComp_orgB" class="autoComplate"></div>
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
                  <a href="javascript:void(0)" class="btn-normal btn-blue in_block ml_15" id="js_submitForm">对调</a>
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
    //对调分行A自动完成初始化
    initAutoComplete({
        urlOrData: '/oms/api/getOrgNameByPy?orgType=分行',
        itemTextKey: 'orgName', //branchName
        dataKey: 'OrgNameList', //autoBranch
        inputObj: $('#orgAName'),
        keepVessel: $("#orgAId"),
        keepKey: 'id'  //branchId
    }); 

    //对调后分行B自动完成初始化
    initAutoComplete({
        urlOrData: '/oms/api/getOrgNameByPy?orgType=分行',
        itemTextKey: 'orgName', //branchName
        dataKey: 'OrgNameList', //autoBranch
        inputObj: $('#orgBName'),
        keepVessel: $("#orgBId"),
        keepKey: 'id'  //branchId
    });
});
</script>