$(function(){
	$("[name=role]").click(function(){
		$("[name=roleName]").val('');
		$("[name=roleDesc]").val('');
		showPopupDiv($('#rolePopupLayer') );
		$("[name=appId]").val($("div.appChanel a.current").attr("chanelID"));
		$("[name=roleSubmit]").die().live("click",function(){
			if($("[name=roleName]").val()==''){
				return alert("必须填写角色名称");
			}
			ajaxPost({
				url:"/privilege/addRoles",
				data:{
					"appId":$("[name=appId]").val(),
					"roleName": $("[name=roleName]").val(),
					"roleDesc": $("[name=roleDesc]").val()
				},
				ok:function(data){
					loadRole();
					hidePopupDiv($('#rolePopupLayer'));
					showSystemMsg("ok","添加角色成功！");
				},
				fail:function(data){
					showSystemMsg("fail",data.message);
				}
			});
		});
	});
	$("[name=privilege]").click(function(){
		$("[name=privilegeName]").val('');
		$("[name=privilegeUrl]").val('');
		showPopupDiv($('#privilegePopupLayer') );
		$("[name=appId2]").val($("div.appChanel a.current").attr("chanelID"));
		$("[name=privilegeSubmit]").die().live("click",function(){
			if($("[name=privilegeName]").val()==''||$("[name=privilegeUrl]").val()==''){
				return alert("必须填写权限名称与权限Url");
			}
			ajaxPost({
				url:"/privilege/addPrivileges",
				data:{
					"appId":$("[name=appId2]").val(),
					"privilegeName": $("[name=privilegeName]").val(),
					"privilegeUrl": $("[name=privilegeUrl]").val(),
					"privilegeClass": $("[name=privilegeClass]").val()
				},
				ok:function(data){
					if($("div.roleChanel a.current").length == 1)
						loadApplicationPrivilegeByRoleId();
					else
						loadApplicationPrivilegeByAppId();
					hidePopupDiv($('#privilegePopupLayer'));
					showSystemMsg("ok","添加权限成功！");
				},
				fail:function(data){
					showSystemMsg("fail",data.message);
				}
			});
		});
	});

	//切换频道
	$("div.appChanel a").live("click", function(){
		$("div.appChanel a.current").removeClass("current");
		$(this).addClass("current");
		loadRole();
		loadApplicationPrivilegeByAppId();
		$("#btnSave").hide();
		return false;
	});

	//切换角色
	$("div.roleChanel a").live("click", function(){
		$("div.roleChanel a.current").removeClass("current");
		$(this).addClass("current");
		loadApplicationPrivilegeByRoleId();
		$("#btnSave").show();
		return false;
	});

	$("#btnSave").live("click",function(){
		var roleId = $("div.roleChanel a.current").attr("roleid");
		var rps = new Array();
		$("div.privilegeChanel input:checkbox:checked").each(function(){
			rps.push("{\"roleId\":"+roleId+",\"privilegeId\":"+$(this).val()+",\"dataPrivilege\":"+$("[name=p_"+$(this).val()+"]:checked").val()+"}");
		});
		ajaxPost({
			url:"/privilege/"+roleId+"/addRolePrivileges",
			data:{"rpList":rps.join(";")},
			ok:function(data){
				loadApplicationPrivilegeByRoleId();
				showSystemMsg("ok","权限设置成功！");
			},
			fail:function(data){
				showSystemMsg("fail",data.message);
			}
		});
	});
});

function bindCheckBoxFunction(){
	$("div.privilegeChanel input:checkbox").die().live("click", function(){//功能：选项选中|勾掉事件
		if($(this).attr("checked") == true){
			$(this).parent("label").parent("li").addClass("chk");
			if($("[name=p_"+$(this).val()+"]:checked").length==0)
				$("[name=p_"+$(this).val()+"]").first().attr("checked","checked");
		}
		else{
			$(this).parent("label").parent("li").removeClass("chk");
		}
	});
}

function loadApplicationPrivilegeByRoleId(){
	$("div.privilegeChanel").html('<p class="pd_20 center"><img src="http://dui.dooioo.com/public/images/icon_ajaxload.gif" /></p>');
	ajaxPost({
		url:"/privilege/getPrivilegesByRoleId",
		data:{
			"appId":$("div.appChanel a.current").attr("chanelID"),
			"roleId":$("div.roleChanel a.current").attr("roleid")
		},
		ok:function(data){
			var html = "<ul>";
			for(var i = 0 ; i < data.priList.length;i++)
			{
				html+= "<li "+(data.priList[i].checked==true?"class=\"chk\"":"")+"><label> <input type=\"checkbox\" name=\"priInput\" value=\""+data.priList[i].id+"\""
						+ (data.priList[i].checked==true?"checked=\"checked\"":"")+">"+data.priList[i].privilegeName+"</label><span class=\"right\"><span class=\"mr_20\">"+data.priList[i].privilegeClass+"</span>"
						+"<label class=\"ml_20 js_addTipsV\" tips=\"仅限本人\"><input type=\"radio\" name=\"p_"+data.priList[i].id+"\" value=\"1\" "+(data.priList[i].dataPrivilege==1?"checked=\"checked\"":"")+"/>本人</label>"
						+"<label class=\"ml_5 js_addTipsV\" tips=\"当前所在组织\"><input type=\"radio\" name=\"p_"+data.priList[i].id+"\" value=\"10\" "+(data.priList[i].dataPrivilege==10?"checked=\"checked\"":"")+"/>本组织</label>"
						+"<label class=\"ml_5 js_addTipsV\" tips=\"当前所在组织以及所有子组织\"><input type=\"radio\" name=\"p_"+data.priList[i].id+"\" value=\"50\" "+(data.priList[i].dataPrivilege==50?"checked=\"checked\"":"")+"/>本组织树</label>"
						+"<label class=\"ml_5 js_addTipsV\" tips=\"当前所在公司\"><input type=\"radio\" name=\"p_"+data.priList[i].id+"\" value=\"100\" "+(data.priList[i].dataPrivilege==100?"checked=\"checked\"":"")+"/>本公司</label>"
                        +"</span></li>";
			}
			html+="</ul>";
			$("div.privilegeChanel").html(html);
        }
	});
	bindCheckBoxFunction();
}

function loadApplicationPrivilegeByAppId(){
	$("div.privilegeChanel").html('<p class="pd_20 center"><img src="http://dui.dooioo.com/public/images/icon_ajaxload.gif" /></p>');
	ajaxPost({
		url:"/privilege/getPrivilegesByAppId",
		data:{
			"appId":$("div.appChanel a.current").attr("chanelID")
		},
		ok:function(data){
			var html = "<ul>";
			for(var i = 0 ; i < data.priList.length;i++)
			{
				html+= "<li><span class=\"ml_10\">"+data.priList[i].privilegeName+"</span></li>";
			}
			html+="</ul>";
			$("div.privilegeChanel").html(html);
		}
	});
	bindCheckBoxFunction();
}
function loadRole(){
	var chanelID = $("div.appChanel a.current").attr("chanelID");
	$("div.roleChanel").html('<p class="pd_20 center"><img src="http://dui.dooioo.com/public/images/icon_ajaxload.gif"  /></p>');
	ajaxPost({
		url:"/privilege/getRolesByAppId",
		data:{"appId": chanelID},
		ok:function(data){
			var html = "<ul>";
			for(var i = 0 ; i < data.roleList.length;i++)
			{
				html+= "<li><a href=\"#\" roleId=\""+data.roleList[i].id+"\"><span class=\"ml_10\">"+data.roleList[i].roleName+"</span></a></li>";
			}
			html+="</ul>";
			$("div.roleChanel").html(html);
		}
	});
}