var fieldConfig = [
    {name: "keyWords", type: "text"},
    {name: "employeeStatus", type: "checkbox"},
    {name: "lastDegree", type: "checkbox"},
    {name: "black", type: "checkbox"},
    {name: "joinDateStart", type: "text"},
    {name: "joinDateEnd", type: "text"},
    {name: "leaveDateStart", type: "text"},
    {name: "leaveDateEnd", type: "text"},
    {name: "officialYear", type: "select"},
    {name: "officialMonth", type: "select"},
    {name: "officialDay", type: "select"},
    {name: "birthDateFrom", type: "text"},
    {name: "birthDateTo", type: "text"},
    {name: "comeFrom", type: "select"},
    {name: "constellation", type: "select"},
    {name: "positionIds", type: "text"},
    {name: "advancedSearch", type: "text"},
    {name: "orgIds", type: "text"},
    {name: "archiveStatus", type: "hidden"},
    {name: "tags", type: "checkbox"}
];
var fieldExportConfig = [
    {name: "excelHeads", type: "text"},
    {name: "exportKey", type: "text"}
];
var tree = null;


$(function () {

    //初始化组织架构树--选项树
    tree = new TreePanel({
        renderTo: 'treeWrap',   //树的父容器ID
        isChk: true,            //选项树
        //nodeUrl: '#',
        canDept: true,          //部门可选
        canStaff: false,        //员工不可选
        root: All,         //json数据源
        status: '1,-1,0,2'
    });
    tree.render();


    /*b 选择部门添加暂停、停用、临时可选按钮*/
    //隐藏暂停和停用组织 ======begin
    function showOrHideNode() {
        var pauseDepts = $('ul.TreePanel_List').find('[status=0]'),
            stopDepts = $('ul.TreePanel_List').find('[status=-1]'),
            tempDepts = $('ul.TreePanel_List').find('[status=2]');

        //添加样式
        pauseDepts.find('.TreeNode_new').css('color', '#ffa29a');
        stopDepts.find('.TreeNode_new').addClass('red');
        tempDepts.find('.TreeNode_new').addClass('red');
        //隐藏或显示
        $('#showPauseDept').is(':checked') ? pauseDepts.removeClass('hideit') : pauseDepts.addClass('hideit');
        $('#showStopDept').is(':checked') ? stopDepts.removeClass('hideit') : stopDepts.addClass('hideit');
        $('#showTempDept').is(':checked') ? tempDepts.removeClass('hideit') : tempDepts.addClass('hideit');
    }

    //点击暂停或停用复选框
    $('#showPauseDept,#showStopDept,#showTempDept').click(showOrHideNode);
    //节点点击事件
    $("ul.TreePanel_List .switch").live("click", showOrHideNode);


    (function hideAbnormal() {
        $('ul.TreePanel_List').find('[status=0]').addClass('hideit').find('.TreeNode_new').addClass('red');
        $('ul.TreePanel_List').find('[status=-1]').addClass('hideit').find('.TreeNode_new').css('color', '#ffa29a');
        $('ul.TreePanel_List').find('[status=2]').addClass('hideit').find('.TreeNode_new').addClass('red');
    }());

    /*e 选择部门添加暂停、停用、临时可选按钮*/

    (function initPosition() {
        var positionIds = $('[name=positionIds]').val().split(',');
        $.each(positionIds, function (i, val) {
            if ($('[name=position][value=' + val + ']').length > 0) {
                $('[name=position][value=' + val + ']').attr('checked', true);
            }
        });
        confirmPosition();
    }());

    /* (function initDept(){
     setCheckedToTree(tree, $('[name=orgIds]').val());
     // confirmDept();
     }());*/

    //屏蔽、启用账号
    $("a.js_toggleUser").click(function () {
        var self = $(this);
        var shielded = self.attr("shielded");
        var url = shielded === 'true' ? '/employee/enable/' + self.attr("id") : '/employee/shielded/' + self.attr("id");
        if (window.confirm(shielded === 'false' ? '确认将此用户屏蔽？' : '确认将此用户启用？')) {
            ajaxGet({
                url: url,
                ok: function (data) {
                    shielded === 'false' ? self.text("启用账户").attr("shielded", "true").removeClass("h34Red").addClass("h34Green") : self.text("屏蔽账户").attr("shielded", "false").removeClass("h34Green").addClass("h34Red");
                },
                fail: function (data) {
                    alert(data.message);
                }
            });
        }
    });


    //点击高级搜索
    $('a.js_btnAdvanceSearch').click(function () {
        $(this).addClass('hideit');
        $('a.js_btnHiddenSearch').parents("tr").removeClass("hideit");
        $("#searchReset").removeClass('hideit');
        $('tr.js_advSearchField').removeClass('hideit');
        $('#_advanced').val(1);
//    $("[name = 'employeeStatus']").attr("checked","checked");
//    initPanel();
    });

    //点击收起
    $('a.js_btnHiddenSearch').click(function () {
        $(this).parents("tr").addClass('hideit');
        $('a.js_btnAdvanceSearch').removeClass("hideit");
        $("#searchReset").addClass('hideit');
        $('tr.js_advSearchField').addClass('hideit');
        $('#_advanced').val(1);
    });

    //点击搜索
    $("#searchBtn").live("click", function () {
//    var requestUrl = window.location.href.split('?')[0];
//    submitForm($(this),requestUrl);
        submitForm(1);
    });

    //点击切换档案状态tab
    $("#archiveStatusSearch").delegate("a", "click", function () {
        $("#archiveStatus").val($(this).attr("status"));
        submitForm(1);
    });

    //点击重置
    $("#searchReset").live("click", function () {
        resetForm();
        $("#js_deptShow").html("");
        $('#_advanced').val(1);
    });

    //按Enter键快速查询
    $("#_keyWords").keyup(function (evt) {
        var v = $.trim($(this).val());
        var k = window.event ? evt.keyCode : evt.which;
        if (v != '' && k == 13) {
            var requestUrl = window.location.href.split('?')[0];
            submitForm(1);
        }
    });

    $("#excelOutput input.chkExcel").click(function () {
        var len = $("#excelOutput input.chkExcel:checked").length;
        var totalLen = $("#excelOutput input.chkExcel").length;
        if (totalLen == len) {
            $("#js_employee_export_checkALl").attr("checked", "checked");
        } else {
            $("#js_employee_export_checkALl").removeAttr("checked");
        }
    });

    $("#js_employee_export_checkALl").click(function () {
        var checked = $(this).attr("checked");
        $("#excelOutput input.chkExcel").each(function () {
            $(this).attr("checked", checked);
        });
    });

});

//导出excel弹层验证
function chkForm_excel() {
    if ($("#excelOutput input.chkExcel:checked").length == 0) {
        alert("请选择要导出的项目");
        $("#excelOutput input.chkExcel").eq(0).focus();
        return false;
    } else {
        var exportExcelValue = "";
        var exportExcelKey = "";
        $("#excelOutput input.chkExcel:checked").each(function (i) {
            exportExcelValue += $(this).val() + ",";
            exportExcelKey += $(this).attr('name') + ",";
        });
        exportExcelValue = exportExcelValue.substring(0, exportExcelValue.length - 1);
        exportExcelKey = exportExcelKey.substring(0, exportExcelKey.length - 1);
        $('#h_exportExcel').val(exportExcelValue);
        $('#h_exportExcelKey').val(exportExcelKey);
//        var requestUrl = "/employee/export";
        $("#employeeFrm").attr("action", "/employee/export").submit();
//        submitExportForm($(this), requestUrl);
    }
    return true;
}

//确认选择部门
function confirmDept() {//orgName
    setHiddenVal({'js_deptShow': tree.getCheckedAllTxt().join(',')});
    $("[name = 'orgIds']").val(tree.getCheckedAll().join(','));
    $("[name = 'orgNameList']").val($("#js_deptShow").text());
    hidePopupDiv($('#layer_selDept'));
}

//确认选择职位
function confirmPosition() {
    var posIdArr = [], posNameArr = [];
    $('[name=position]:checked').each(function () {
        posIdArr.push($(this).val());
        posNameArr.push($(this).attr('title'));
    });
    setHiddenVal({'h_pos_id': posIdArr.join(','), 'h_pos_txt': posNameArr.join(','), 'js_posShow': posNameArr.join(',')});
    $("[name = 'positionIds']").val(posIdArr.join(','));
    $("[name = 'positionNameList']").val($("#js_posShow").text());
    hidePopupDiv($('#layer_selPos'));
}

//设置hidden的值
function setHiddenVal(params) {
    if (params == null) return;
    $.each(params, function (key, value) {
        if ($('#' + key).is(':text')) {
            $('#' + key).val(value);
        } else {
            if (value.length > 30) value = value.substring(0, 30) + '...';
            $('#' + key).text(value);
        }
    });
}

//取消表单值
function resetForm() {
    var fieldInfo = null;
    for (var i = 0; i < fieldConfig.length; i++) {
        fieldInfo = fieldConfig[i];
        if (fieldInfo.type == "checkbox") {
            $('[name=' + fieldInfo.name + ']:checked').each(function () {
                $(this).attr("checked", false);
                $(this).parent("label").removeClass("advanced_label_chked");
            });
        } else if (fieldInfo.type == "text") {
            $('[name=' + fieldInfo.name + ']').val('');
        } else if (fieldInfo.type == "select") {
            $('[name=' + fieldInfo.name + ']').get(0).selectedIndex = 0;
        }
    }
}

//高级搜索
function submitForm(pageNo) {
//function submitForm(self,requestUrl){
    /*  var fieldInfo = null;
     var paramArray = new Array;
     for(var j=0; j<fieldConfig.length; j++){
     fieldInfo = fieldConfig[j];
     if( fieldInfo.type == "checkbox" || fieldInfo.type == "radio"){
     var _tempArray = new Array;
     $('[name='+fieldInfo.name+']:checked').each(function(){
     _tempArray.push($(this).val());
     });
     if( _tempArray.length>0 )
     paramArray.push(fieldInfo.name+"="+_tempArray.join(","));
     }else if( fieldInfo.type == "text" || fieldInfo.type == "hidden" || fieldInfo.type == "select"){
     if( $('[name='+fieldInfo.name+']').val() != '' )
     paramArray.push(fieldInfo.name+"="+$('[name='+fieldInfo.name+']').val());
     }
     }
     window.location.href = (paramArray.length>0) ? requestUrl+"?"+encodeURI(paramArray.join("&")) : requestUrl;
     */
    pageNo = pageNo == null ? 1 : pageNo;
    $("#employeeFrm").attr("action", "/employee/list?pageNo=" + pageNo).submit();

}

//提交表单
function submitExportForm(self, requestUrl) {
    var fieldInfo = null;
    var allFieldConfig = $.merge($.merge([], fieldConfig), fieldExportConfig);
    var tempForm = $('<form action="' + requestUrl + '" method="post"></form>');
    var paramArray = new Array;
    for (var j = 0; j < allFieldConfig.length; j++) {
        fieldInfo = allFieldConfig [ j];
        if (fieldInfo.type == "checkbox" || fieldInfo.type == "radio") {
            var _tempArray = new Array;
            $('[name=' + fieldInfo.name + ']:checked').each(function () {
                _tempArray.push($(this).val());
            });
            if (_tempArray.length > 0) {
                paramArray.push(fieldInfo.name + "=" + _tempArray.join(","));
                tempForm.append(createHidden(fieldInfo.name, _tempArray.join(",")));
            }
        } else if (fieldInfo.type == "text" || fieldInfo.type == "hidden" || fieldInfo.type == "select") {
            if ($('[name=' + fieldInfo.name + ']').val() != '') {
                paramArray.push(fieldInfo.name + "=" + $('[name=' + fieldInfo.name + ']').val());
                tempForm.append(createHidden(fieldInfo.name, $('[name=' + fieldInfo.name + ']').val()));
            }
        }
        var targetUrl = (paramArray.length > 0) ? requestUrl + "?" + encodeURI(paramArray.join("&")) : requestUrl;
        if (targetUrl.length > 4000) {
            tempForm.submit();
        } else {
            window.location.href = targetUrl;
        }
    }
    function createHidden(name, value) {
        return '<input type="hidden" name="' + name + '" value="' + value + '"/> '
    }

    //window.location.href = (paramArray.length>0) ? requestUrl+"?"+encodeURI(paramArray.join("&")) : requestUrl;
}
