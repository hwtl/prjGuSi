<tr>
    <td class="tdTitle">联系电话：</td>
    <td class="request" width="20">&nbsp;</td>
    <td width="200"  colspan="4">
        <div class="js_addRemoveRow">
            <#list orgPhoneList as orgPhone>
                <p class="js_dataRowContainer">
                    <input type="text" value="${orgPhone.phone}" name="phone${orgPhone_index+1}" class="txtNew w200 mt_5 js_phone" placeholder="电话号码">
                    <input type="text" value="${orgPhone.line!""}" name="line${orgPhone_index+1}" class="txtNew w200 ml_20 js_line" placeholder="线路">
                    <a href="#" class="js_delete ml_10">删除</a>
                </p>
            </#list>
            <p class="js_insertBefore"><a href="javascript:;" class="btnOpH24 h24Silver in_block mt_5 js_add">添加电话</a></p>
        </div>
    </td>
</tr>
