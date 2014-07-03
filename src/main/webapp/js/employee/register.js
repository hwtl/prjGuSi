var reportApp = angular.module('reportApp',[]);
reportApp.controller('reportModule',function($scope,$http){
	$scope.textShow = false;
	$scope.dealEmpNo = "";
	$scope.listenChange = function(value){
		$scope.changeFlag = value;
	};
	
	$scope.dealReport = function(e){
		$scope.dealEmpNo = angular.element(e.srcElement).attr("id");//当前被编辑行的人的工号
		$http.get("/employee/deal/" + $scope.dealEmpNo).success(function(data, status, headers, config){//请求弹层的url
			if(data.status == "ok"){
				$scope.personInformation = data.employee;
                $scope.textShow = false;
                $(".js_notReportReason").val("");
				showPopupDiv($('#report_layer_form'));
			}else{
				alert(data.message);
			}
		}).error(function(){
			alert("数据请求失败，请重试！");
		});
	};
	
	function postData(url,params){
        $http({method:'POST', url: url, data: $.param(params), headers : {
            'Content-Type' : 'application/x-www-form-urlencoded'
        }}).success(function(data, status, headers, config){
                if(data.status == "ok"){
                    hidePopupDiv($('#report_layer_form'));
                    if (data.empStatus != null && data.empStatus == "门店待报到") {
                        var statusText = params.registerStatus + "(" + data.empStatus + ")";
                        $("#"+params.userCode+"_status").text(statusText);
                    } else {
                        $("#"+params.userCode+"_status").text(params.registerStatus);
                    }
                    $(".js_deal_"+params.userCode).remove();
                }else{
                    alert(data.message);
                }
            }).error(function(){
                alert("提交数据失败，请重试");
            });

	}
	
	$scope.resetErrorMsg = function(){
		if($scope.textShow == 1){
			$('#reportForm').find(".ErrMsgNew").remove();
		}else{
			$('#noReportForm').parents("td").find(".ErrMsgNew").remove();
		}
	};
	
	$scope.qudaosubmit = function() {
		if ($scope.textShow == 1) {
			// 总部未报到
			$scope.changeFlag = "";
			// 修改标志变空
			var validatorNo = $('#noReportForm').validate();
			if (validatorNo.validateForm()) {
				postData("/employee/deal/"+$scope.dealEmpNo,{userCode:$scope.dealEmpNo,register:false,change:false,remark:$(".js_notReportReason").val(),registerStatus:'总部未报到'});
			}
		} else {
			// 总部已报到
			var validator = $('#reportForm').validate();
			if (validator.validateForm()) {
				if ($scope.changeFlag) {
					postData("/employee/deal/"
                        +$scope.dealEmpNo,{
                        userCode:$scope.dealEmpNo,
                        register:true,
                        change:true,
                        userNameCn:$scope.personInformation.userNameCn,
                        userNameEn:$scope.personInformation.userNameEn,
                        sex:$scope.personInformation.sex,
                        credentialsNo:$scope.personInformation.credentialsNo,
                        mobilePhone:$scope.personInformation.mobilePhone,
                        registerStatus:$scope.personInformation.registerStatus,
                        experience:$scope.personInformation.experience});
				} else {
					postData("/employee/deal/"+$scope.dealEmpNo,{userCode:$scope.dealEmpNo,register:true,change:false,registerStatus:$scope.personInformation.registerStatus});
				}
			}
		}	
	};
});

$(function(){

    /**
     * 恢复总部待报到
     */
    $(".js_dealRecover").click(function() {
        var self = $(this),
            userCode = self.attr("userCode"),
            userStatus = self.attr("userStatus");
        if (userStatus == "总部未报到") {
            if (confirm("你确定要将该员工从总部未报到状态恢复到总部待报到状态吗？")) {
                $.post("/employee/recover/"+userCode+"/"+userStatus,{},function(data) {
                    if (data.status == "ok") {
                        $("#"+userCode+"_status").text("总部待报到");
                        $(".js_recover_"+userCode).remove();
                    } else {
                        alert("恢复失败！" + data.message);
                    }
                });
            }
        } else if (userStatus == "门店未报到") {
            if (confirm("你确定要将该员工从门店未报到状态恢复到门店待报到状态吗？")) {
                $.post("/employee/recover/"+userCode+"/"+userStatus,{},function(data) {
                    if (data.status == "ok") {
                        $("#"+userCode+"_status").text("门店待报到");
                        $(".js_recover_"+userCode).remove();
                    } else {
                        alert("恢复失败！" + data.message);
                    }
                });
            }
        }
    })

})
