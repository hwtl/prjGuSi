  <div class="popTitle clearfix">
        <h1>新增奖惩</h1>
        <div class="cls"><a href="javascript:;" class="xx" onclick="hidePopupDiv($('#layer_reward'))"></a></div>
    </div>
    <div class="popCon">
        <div class="form">
            <form id="rewardNewForm" action="/reward/add" method="post">
                <input name="${token.name}" value="${token.value}" type="hidden"/>
                <table border="0" cellpadding="0" cellspacing="0" class="tableFormNew">
                    <tbody>
                    <tr>
                        <td class="tdTitle" width="120">奖惩员工：</td>
                        <td class="request" width="20">&nbsp;</td>
                        <td width="440">${emp.userName}（${emp.userCode}）
                            <input type="hidden" name="id" value="0" />
                            <input type="hidden" name="userCode" value="${emp.userCode}" />
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">奖惩类别：</td>
                        <td class="request">●</td>
                        <td>
                          <#list rewardTypeList as rw>
                            <label class="mr_10"><input type="radio" name="rewardtype" value="${rw.optionCode}"
                             rule="required"
                              <#if punishOptionCode==rw.optionCode>
                               checked="checked"
                              </#if>
                             >${rw.optionValue}</label>
                          </#list>
                        </td>
                    </tr>
                    <tr id="trChannel" >
                        <td class="tdTitle">奖惩渠道：</td>
                        <td class="request">●</td>
                        <td>
                            <select name="channel" rule="required" id="selChannel">
                              <option value="">请选择</option>
                               <#list channelList as cl>
                               	 <option value="${cl.optionCode}">${cl.optionValue}</option>
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
	                               	 <option value="${rl.optionCode}" data-money="${rl.optionResult}"
	                               	  data-month="${rl.optionTitle}">${rl.optionValue}</option>
	                          </#list>
                            </select>
                            <span id="salaryResult" class="ml_10 red"></span>
                        </td>
                    </tr>
                    <tr id="trEffectTime" class="hideit">
                        <td class="tdTitle">有效时长：</td>
                        <td class="request">●</td>
                        <td>
                            <input type="hidden" name="effectiveLength" id="h_effectMonth" value="0" />
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
	                               	 <option value="${rl.optionCode}">${rl.optionValue}</option>
	                               </#list>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">奖惩备注：</td>
                        <td class="request">&nbsp;</td>
                        <td colspan="4">
                            <textarea name="remark" class="tA tt280" maxlength="500"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td class="tdTitle">生效日期：</td>
                        <td class="request">●</td>
                        <td>
                            <input type="text" readonly="readonly" name="effectiveTime" class="txtDate ml_10" rule="required">
                            <span class="ml_10" id="uneffectDate"></span>
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