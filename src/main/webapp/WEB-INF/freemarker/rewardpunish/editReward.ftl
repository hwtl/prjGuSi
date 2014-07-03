<div class="popTitle clearfix">
        <h1>编辑奖惩</h1>
        <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_reward'))"></a></div>
    </div>
    <div class="popCon">
        <div class="form">
            <form id="rewardForm" action="/reward/add" method="post">
            	<input name="${token.name}" value="${token.value}" type="hidden"/>
                <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew">
                    <tbody>
                    <tr>
                        <td class="tdTitle" width="120">奖惩员工：</td>
                        <td class="request" width="20">&nbsp;</td>
                        <td width="440">${reward.userName}（${reward.userCode}）
                            <input type="hidden" name="id" value="${reward.id}" />
                            <input type="hidden" name="userCode" value="${reward.userCode}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">奖惩类别：</td>
                        <td class="request">●</td>
                        <td>
                           <#list rewardTypeList as rt>
                            <label class="mr_10"><input type="radio"  rule="required" name="rewardtype" 
                             <#if rt.optionCode==reward.rewardtype>
                              checked="checked"
                             </#if>
                             value="${rt.optionCode}">${rt.optionValue}</label>
                           </#list>
                        </td>
                    </tr>
                    <#if (channelList?size > 0)>
                    <tr id="trChannel" class="">
                    <#else>
                    <tr id="trChannel" class="hideit">
                    </#if>
                        <td class="tdTitle">奖惩渠道：</td>
                        <td class="request">●</td>
                        <td>
                            <select name="channel" rule="required" id="selChannel">
                              <option value="">请选择</option>
                               <#list channelList as cl>
                               	 <option value="${cl.optionCode}"
                               	   <#if cl.optionCode==reward.channel>
                               	    selected="selected"
                               	   </#if>
                               	 >${cl.optionValue}</option>
                               </#list>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">奖惩结果：</td>
                        <td class="request">●</td>
                        <td>
                            <select name="result" rule="required" id="selResult">
	                           <option value=""  data-money=""
	                               	  data-month="0">请选择</option>
	                               <#list resultList as rl>
	                               	 <option value="${rl.optionCode}"
	                               	   <#if rl.optionCode==reward.result>
	                               	    selected="selected"
	                               	   </#if>
	                               	 data-money="${rl.optionResult}" data-month="${rl.optionTitle}">${rl.optionValue}</option>
	                               </#list>
                            </select>
                            <span id="salaryResult" class="ml_10 red">${reward.resultValue}</span>
                        </td>
                    </tr>
                    <tr id="trEffectTime" class="hideit">
                        <td class="tdTitle"><span>有效时长：</span></td>
                        <td class="request">●</td>                        
                        <td>
                          <input type="hidden" name="effectiveLength" id="h_effectMonth" value="${reward.effectiveLength}" />
                           <span id="effectTime"></span>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">奖惩依据：</td>
                        <td class="request">●</td>
                        <td>
                            <select name="rules" rule="required">
                                <option value="">请选择</option>
	                           <#list ruleList as rl>
	                               	 <option value="${rl.optionCode}"
	                               	   <#if rl.optionCode==reward.rules>
	                               	    selected="selected"
	                               	   </#if>
	                               	 >${rl.optionValue}</option>
	                               </#list>
                            </select>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">奖惩备注：</td>
                        <td class="request">&nbsp;</td>
                        <td colspan="4">
                            <textarea name="remark" class="tA tt280" maxlength="500">${reward.remark}</textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">生效日期：</td>
                        <td class="request">●</td>
                        <td>
                            <input type="text" value="${reward.effectiveTime?string("yyyy-MM-dd")}" name="effectiveTime" class="txtDate ml_10" rule="required">
                            <#if failTime??>
                            <span class="ml_10" id="uneffectDate">失效日期：<b>${failTime?string("yyyy-MM-dd")}</b></span>
                            <#else>
                          	 <span class="ml_10" id="uneffectDate"></span>
                            </#if>                           
                        </td>
                    </tr>
                    </tbody>
                </table>
                 </form>
        </div>
    </div>
    <br><br>
    
    <div class="popBtn">
        <a href="javascript:;" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#layer_reward'))">关闭</a>
        <a href="javascript:;" class="btnOpH24 h24Blue in_block js_rewardSubmitForm">提交</a>
    </div>