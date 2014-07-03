
function load_html(url,obj){
    $.ajax({
        url: url,
        type:'GET',
        success:function(data){
            obj.html(data);
        }

    });
}

function showSystemMsg(type,message){
	var obj = null;
	if(window.parent.$('div.contentRight').length>0){
		obj = window.parent.$('div.contentRight');
	}
	else
		obj = $("div.container");
	showMsg(obj,type,message);
}

function showMsg(obj,type,message,duration){
	var div = "<div id=\"sMsg\" style=\"position:absolute;z-index:1000000; padding-left:40px;padding-right: 40px;\" class=\""+(type=='ok'?"boxWrapGreen":"boxWrapYellow")+" t500 mt_5 f14 center bold hideit\">"+message+"</div>";
	var dura = 1000;
	if(duration)
		dura = duration;
	$(obj).prepend(div);
	$("#sMsg").css({"left":($(window).width() - $("#sMsg").outerWidth())/2}).show().delay(dura).slideUp(function(){$("#sMsg").remove();});
}

//function autocomplete(autooObj,privileData,keyObj){
//    autooObj.autocomplete({
//        urlOrData: '/organization/getOrgNameByPy',
//        itemTextKey: 'orgName',
//        dataKey: 'OrgNameList',
//        setRequestParams: function(requestParams){
//            requestParams.orgName = autooObj.val();
//            requestParams.privilegeValue = privileData;
//        },
//        selectCallback:function(selectedItem){
//            keyObj.val(selectedItem.id);
//        }
//    });
//}

function autocomplete(autooObj,privileData,dataType,keyObj){
    autooObj.autocomplete({
        urlOrData: '/organization/getOrgNameByPy',
        itemTextKey: 'orgName',
        dataKey: 'OrgNameList',
        setRequestParams: function(requestParams){
            requestParams.orgName = autooObj.val();
            requestParams.privilegeValue = privileData;
            requestParams.dataType = dataType;
        },
        selectCallback:function(selectedItem){
            keyObj.val(selectedItem.id);
        }
    });
}

/**
 * 检查组织信息的合法性
 * @returns {boolean}
 */
function checkOrganization(){
    var result = false;
    if(/^[0-9]{1,}$/.test($("#orgId").val())){
        ajaxGet({
            url:"/organization/"+$("#orgId").val()+"/check",
            async:false,
            ok: function(data) {
                $("#js_hasOrg").addClass("hideit");
                result = true;
            },
            fail: function(data){
                $("#js_hasOrg").removeClass("hideit");
               result = false;
            }
        });

        return result;
    }else{
        $("#js_hasOrg").removeClass("hideit");
        return false;
    }
}
