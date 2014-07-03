<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<div class="container">
    <form id="employeeFrm" name="employeeFrm" action="/oms/employee/query.action" method="post">
        <input type="hidden" name="advanced_Search" value="0" id="_advanced"/>
        <table border="0" cellspacing="0" cellpadding="0" class="tableFormNew">
            <tbody>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" width="115" nowrap="">部门：</td>
                <td class="" width="600">
                    <a href="#" class="btnOpH24 h24Silver in_block" onclick="showPopupDiv($('#layer_selDept')); return false;" id="btnSelDept">选择部门</a>
                    <span id="js_deptShow" class="in_block showSelContent grey999 ml_10"></span>
                </td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">职位：</td>
                <td class=""><a href="#" class="btnOpH24 h24Silver in_block" onclick="showPopupDiv($('#layer_selPos')); return false;">选择职位</a><span id="js_posShow" class="in_block showSelContent grey999 ml_10"></span></td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">任职状态：</td>
                <td class="">
                    <input type="hidden" name="statusList" value="0,1,2,3">
								<span>
								<input type="checkbox" id="zhengshi" name="employeeStatus" value="0">
								正式</span>
                </td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">入职时间：</td>
                <td class="">
                    <input name="joinDate" type="text" class="txtDate" value="" readonly="readonly">
                </td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">离职时间：</td>
                <td class="">
                    <input name="leaveDateStart" type="text" class="txtDate" value="" readonly="readonly">
                </td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">身份证出生日期：</td>
                <td class="">
                    <select name="officialYear">
                        <option value="">不选择</option>

                    </select>&nbsp;年
                    <select name="officialMonth" class="ml_10">
                        <option value="">不选择</option>

                    </select>&nbsp;月
                    <select name="officialDay" class="ml_10">
                        <option value="">不选择</option>

                    </select>&nbsp;日 </td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">生日：</td>
                <td class="">
                    <select name="bornYear">
                        <option value="">不选择</option>

                    </select>&nbsp;年
                    <select name="bornMonth" class="ml_10">
                        <option value="">不选择</option>

                    </select>&nbsp;月
                    <select name="bornDay" class="ml_10">
                        <option value="">不选择</option>

                    </select>&nbsp;日 </td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">星座：</td>
                <td class="">
                    <select name="constellation">
                        <option value="">请选择</option>

                        <option value="6001">水瓶座 (1月20日-2月18日)</option>
                    </select></td>
            </tr>
            <tr class="js_advSearchField hideit">
                <td class="tdTitle" nowrap="">学历：</td>
                <td>
                    <input type="checkbox" name="lastDegree" value="5001" id="lastDegree-1">
                    <label for="lastDegree-1" class="checkboxLabel">小学</label>
                </td>
            </tr>
            <tr>
                <td class="tdTitle">关键字：</td>
                <td>
                    <input type="text" value="" id="_keyWords" class="txt tt450 textH24" placeholder="可输入姓名、工号、手机号码、分行名称、职位、学校、曾工作单位">
                    <a herf="javascript:;" id="searchBtn" class="btn-small btn-blue in_block ml_20">查询</a>
                    <a href="##" class="ml_10 mt_5 in_block js_btnAdvanceSearch" target="_self">高级搜索</a>
                </td>
            </tr>
            </tbody>
        </table>

    </form>

</div>

<!-- 部门选择弹层 -->
<div id="layer_selDept" class="popLayer w320">
    <div class="popTitle clearfix">
        <h1>选择部门</h1>
        <div class="cls"><a onclick="hidePopupDiv($('#layer_selDept')); return false" class="xx" href="#"></a></div>
    </div>
    <div class="popCon clearfix pd_20" style="">
        <div class="bd_ddd left ml_10">
            <div class="bg_eee pd_10">组织架构(点击组织名称选择)
            </div>
            <!--begin 适用的组织:树 -->
            <div style="width:250px; height:250px; overflow:hidden; overflow-y:auto; position:relative" class="mt_10" id="treeWrap">

            </div>
            <!--end 适用的组织:树 -->
        </div>
    </div>
    <div class="popBtn">
        <a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#layer_selDept'))">取消</a>
        <a href="#" class="btnOpH24 h24Blue in_block ml_10" onclick="confirmDept();">确定</a>
    </div>
</div>
<!-- 职位选择弹层 -->
<div id="layer_selPos" class="popLayer w320">
    <div class="popTitle clearfix">
        <h1>选择职位</h1>
        <div class="cls"><a onclick="hidePopupDiv($('#layer_selPos')); return false" class="xx" href="#"></a></div>
    </div>
    <div class="popCon clearfix pd_20 lineH200">
        <div style="height:200px;overFlow-x:hidden;overFlow-y:auto">

            <p><input type="checkbox" name="position" value="1" title="置业顾问">置业顾问</p>

        </div>
    </div>
    <div class="popBtn">
        <a href="#" class="btnOpH24 h24Silver in_block" onclick="hidePopupDiv($('#layer_selPos'))">取消</a>
        <a href="#" class="btnOpH24 h24Blue in_block ml_10" onclick="confirmPosition();">确定</a>
    </div>
</div>


<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/WdatePicker.js"></script>
<script type="text/javascript" src="http://dui.${user.companyENLower}.com/public/js/dui_treePanel.js"></script>
<script type="text/javascript" src="js/StructureList_Json_data_normal.js"></script>
<script type="text/javascript">
    $(function(){

        //初始化组织架构树--选项树
        deptTree = new TreePanel({
            renderTo:'treeWrap',
            isChk:true,          //选项树
            canDept:true,        //可选部门
            canStaff:false,      //可选员工
            'root':globleTreeConfig['dooiooNormal']		 //数据源
        });
        deptTree.render();

        //点击高级搜索
        $('a.js_btnAdvanceSearch').click(function(){
            $(this).addClass('hideit');
            $('tr.js_advSearchField').removeClass('hideit');
            $('#_advanced').val(1);
            initPanel()
        });

        $("#searchBtn").live("click",function(){
            if(chkForm_search()){
                ajaxSubmitSearch();
            }
        });
    })

    //点击高级搜索时默认全部选中
    function initPanel(){
        $("#zhengshi").attr("checked","checked");
        $("#shiyongqi").attr("checked","checked");
        $("#shengqinglizhi").attr("checked","checked");
        $("#lizhi").attr("checked","checked");
        $("[name = 'statusList']").val("0,1,2,3");
    }
    //确认选择部门
    function confirmDept(){//orgName
        setHiddenVal({'js_deptShow':deptTree.getCheckedAllTxt().join(',')});
        //$('[name = orgName]').val($('#js_deptShow').text());
        $("[name = 'orgIdList']").val(deptTree.getCheckedAll().join(','));
        $("[name = 'orgNameList']").val($("#js_deptShow").text());
        hidePopupDiv($('#layer_selDept'));
    }


    //确认选择职位
    function confirmPosition(){
        var posIdArr = [], posNameArr = [];
        $('[name=position]:checked').each(function(){
            posIdArr.push( $(this).val() );
            posNameArr.push( $(this).attr('title') );
        });
        setHiddenVal({'h_pos_id':posIdArr.join(','), 'h_pos_txt':posNameArr.join(','), 'js_posShow':posNameArr.join(',')});
        //orgId
        $("[name = 'positionList']").val(posIdArr.join(','));
        $("[name = 'positionNameList']").val($("#js_posShow").text());
        hidePopupDiv($('#layer_selPos'));
    }

    //设置hidden的值
    function setHiddenVal(params){
        if(params == null) return;
        $.each(params, function(key, value){
            if( $('#'+key).is(':text')  ){
                $('#'+key).val( value );
            }else{
                if(value.length>30) value = value.substring(0, 30)+'...';
                $('#'+key).text( value );
            }
        });
    }

</script>