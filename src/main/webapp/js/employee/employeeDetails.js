/**
 * Created with IntelliJ IDEA.
 * User: hw
 * Date: 13-6-3
 * Time: 上午9:18
 * 员工详情页的js
 */
$(function(){
    $.fn.dataForm = function(){
        var rowName = $(this).attr("rowName");
        if(!rowName){
            return;
        }
        $(this).find("tr.js_dataRowContainer").each(function(rowid){
            $(this).find("[name]").each( function(){
                if(!$(this).attr("field")){
                    $(this).attr("field", $(this).attr("name"));
                }
                var thisname = rowName+'['+rowid+'].'+$(this).attr("field");
                $(this).attr("name", thisname);
            });
        });
    };

    // 局部表单编辑、保存
    $("a.js_save").live("click", function(e){
        e.preventDefault();
        var formsection = $(this).parents(".formsection");
        var formEl = formsection.find("form");
        var posturl = formEl.attr("action");
        formEl.dataForm();
        var validator = formsection.validate();
        validator.refresh();    
        if(validator.validateForm()){
            formsection.find(".txtDate").each(function(){
                $(this).val($(this).attr("onlymonthdate"));
            });
          
            $.post(posturl, formEl.serialize(), function(backtable){
                formsection.html(backtable);

            });
        }
    });

    $("a.js_edit").live("click",function(e){
        e.preventDefault();
        var formsection = $(this).parents(".formsection");
        var posturl = $(this).attr("action");
        $.get(posturl,function(page){
            formsection.html(page);
            formsection.find(".txtDate").date_input().change();

        });
    });

    $("a.js_delete_two").live('click',function(){
        if(confirm('确定删除')){
            strArrOp($(this).attr('id'), $('#delId'), 1);
            $(this).parents("tr").remove();
        }
        return false;
    });
    $("a.js_cancel").live("click",function(e){
        e.preventDefault();
        var formsection = $(this).parents(".formsection");
        var posturl = $(this).attr("action");
        $.get(posturl,function(page){
            formsection.html(page);
        });
    });
});
