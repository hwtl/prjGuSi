<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/jsp/common/_includes.jsp" %>
<jsp:include page="/WEB-INF/jsp/common/_head.jsp">
    <jsp:param name="title" value="组织排序-组织管理"/>
    <jsp:param name="css" value="position/fronterp"/>
    <jsp:param value="http://dui.${user.companyENLower}.com/public/css/treeNew.css" name="ajCss"/>
</jsp:include>
	<div>
		<form id="sortOrgForm" method="post" action="/organization/saveOrgsSort" name="">
            <webplus:token />
		<table border="0" cellpadding="0" cellspacing="0" class="tableFormNew mt_15">
			<tbody>
				<tr>
					<td width="40%" class="pl_30 grey999 bold">组织架构</td>
					<td width="30%" class="grey999 bold">组织名称</td>
					<td width="30%" class="grey999 bold">序号</td>
				</tr>
				<tr>
					<td>
						<!--begin:组织架构树-->
						<input type="hidden" id="currentDept" deptid="" pid="">
						<div id="treeWrap" class="frameTreeWrap mt_10">

						</div>
						<!--end:组织架构树-->
					</td>
					<td colspan="2">
						<!--orgSortInnerTable 这个table是放点击了树的某个组织，展现它的子节点的 开始-->
                        <table border="0" cellpadding="0" cellspacing="0" class="orgSortInnerTable js_orgSortJson">
							<tbody id="_organizations">
								<!--每一个tr就是一个子节点的信息 第一个td是子节点名，第二个td是对应的序号 -->
							</tbody>
						</table>
                        <input type="hidden" id="orgList" name="orgList" value="" class="js_hidOrgSort" />
                        <!--orgSortInnerTable 这个table是放点击了树的某个组织，展现它的子节点的 开始-->

					</td>

				</tr>
			</tbody>
		</table>
		<p class="txtRight mt_15">
			<a href="/" class="btnOpH34 h34Silver in_block">取消</a>
			<a href="javascript:;" class="btnOpH34 h34Blue opH34 in_block js_submitForm">保存</a>
		</p>
		</form>
		<input type="hidden" name="phoneJson" class="js_phoneJson" value="" />
	</div>
<jsp:include page="/WEB-INF/jsp/common/_foot.jsp">
    <jsp:param name="duiJs" value="validation"/>
    <jsp:param name="css" value="position/fronterp"/>
    <jsp:param name="js" value="dui_treePanel,portal"/>
    <jsp:param value="http://dui.${user.companyENLower}.com/public/json/${user.companyENLower}/${user.companyENLower}All.js" name="ajs"/>
</jsp:include>
<script type="text/javascript">
    $(function(){
        //初始化组织架构树--选项树
        tree = new TreePanel({
            renderTo:'treeWrap',   //树的父容器ID
            //isChk:true,            //选项树
            nodeUrl: '#',
            canDept:true,          //部门可选
            canStaff:false,        //员工不可选
            root:All,         //json数据源
            status: '1'
        });
        tree.render();
        $("#1").addClass("bold");//根节点加粗

        //当前节点展开并定位
        setCurrentNode($("#currentDept"));
        //展开
        $('#treeWrap').find('span.switch').live('click',function(){

        })
        //节点点击事件
        $("ul.TreePanel_List a").live("click", function(){
            var id = $(this).attr("id");
            var text = $(this).text();
            var type = $(this).attr("common");
            ajaxGet({
                url:'/organization/querySonOrgs/'+id,
                ok:function(data){
                    var orgList = data.orgList;
                    $('#_organizations').empty();
                    $.each(orgList, function(i,item){
                        if(item.locIndex == null) {
                            item.locIndex = 0;
                        }
                        $('#_organizations').append(
                            "<tr><td>" + item.orgName + "</td>" +
                            "<td class='js_dataCell'><input type='hidden' value='" + item.id +"' class='js_id' name='id' />" +
                            "<input type='text' value='" + item.locIndex + "' class='txtNew js_locIndex' rule='required integer' name='locIndex'/></td></tr>"
                        );
                    });

                },
                fail:function(data){
                    $('#_organizations').empty();
                }
            });
            return false;
        });
    });

    //提交表单
    var formReg = /^[0-9]+$/;
    $('a.js_submitForm').bind("click",function(){
        var flag = true;
        $(".js_orgSortJson .js_locIndex").each(function(){
            if($.trim($(this).val()) === ''){
                alert('请填入序号！');
                $(this).focus();
                flag = false;
                return false;
            }else if(formReg.test($.trim($(this).val())) === false){
                alert('序号只能是整数数字！');
                $(this).focus();
                flag = false;
                return false;
            }else{
                flag = true;
                var result = [];
                $(".js_orgSortJson .js_dataCell").each(function(){
                    result.push(formToJson($(this), {
                        keys: ['id', 'locIndex']
                    }));
                })
                $(".js_hidOrgSort").val('['+result.join(',')+']');
            }
            return flag;
        });
        if(flag === true){
            ajaxPost({
                url: "/organization/saveOrgsSort",
                data: {'orgList': $('#orgList').val()},
                ok:function(data){
                    showSystemMsg("ok","保存成功！");
                },
                fail:function(data){
                    showSystemMsg("fail",data.msg);
                }
            });
        }
    });
    /*
        var result = [];
        $(".js_orgSortJson .js_dataCell").each(function(){
            result.push(formToJson($(this), {
                keys: ['id', 'locIndex']
            }));
        })
        $(".js_hidOrgSort").val('['+result.join(',')+']');
        if(validator.validateForm()){
            //$('#sortOrgForm').submit();
            ajaxPost({
                url: "/organization/saveOrgsSort",
                data: {'orgList': $('#orgList').val()},
                ok:function(data){
                    showSystemMsg("ok","保存成功！");
                },
                fail:function(data){
                    showSystemMsg("fail",data.msg);
                }
            });
        }
    });
*/
</script>