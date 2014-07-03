$(function(){
	$("input:checkbox").live("click", function(){//功能：选项选中|勾掉事件
		if($(this).attr("checked") == true){
			$(this).parent("label").parent("li").addClass("chk");
		}
		else{
			$(this).parent("label").parent("li").removeClass("chk");
		}
	});

	//切换频道
	$("div.appChanel a").live("click", function(){
		$("div.appChanel a.current").removeClass("current");
		$(this).addClass("current");
		var chanelID = $(this).attr("chanelID");//频道id
		$("div.roleConfig").html('<p class="pd_20 center"><img src="http://dui.dooioo.com/public/images/icon_ajaxload.gif"  /></p>');

		ajaxPost({
			url:"/privilege/getRoles",
			data:{"appId": chanelID,"key":$("[name=key]").val(),"type":$("[name=type]").val()},
			ok:function(data){
				var html = "<ul>";
				for(var i = 0 ; i < data.roleList.length;i++)
				{
					html+= "<li "+(data.roleList[i].checked==true?"class=\"chk\"":"")+"><label><input type=\"checkbox\" name=\"chkFunc\" value=\""+data.roleList[i].id+"\" "+(data.roleList[i].checked==true?"checked=\"checked\"":"")+" "+(data.roleList[i].canEdit==false?"disabled=\"disabled\"":"")+" >"+data.roleList[i].roleName+"</label></li>";
				}
				html+="</ul>";
				$("div.roleConfig").html(html);
			}
		});
		return false;
	});

	$("#btnSave").live("click", function(){//保存当前更改
		var roleIds = "";
		$("div.roleConfig input:checked").each(function(){
			roleIds += "," + $(this).val();
		});
		if(roleIds.length>0)
			roleIds = roleIds.substring(1, roleIds.length);
		ajaxPost({
			url:"/privilege/setRoles",
			data:{"key":$("[name=key]").val(),"appId":$("div.appChanel a.current").attr("chanelID"),"type":$("[name=type]").val(),"roleIds":roleIds},
			ok:function(data){
				if($(this).attr("ref"))
				{
					load_html($(this).attr("ref"),$("div.container"));
				}
				else 
					showSystemMsg("ok","保存成功");
			},
			fail:function(data){
				showSystemMsg("fail",data.message);
			}
		});
	});
});