//社保管理--社会保险--上海新进列表页控制器
function SiShNewListCtrl($scope, $http, $filter){

/*************** 初始化 ****************/
	$scope.init = {
		isBtnTrigger : false, //搜索-区分按钮触发或监听事件的状态标识
		confirmStatus : null, //搜索结果是否可编辑（保存“查询”当时批次的是否确认状态，编辑与其保持一致）
		isPageInit : true, //记录页面初始化的状态
		editIndex : null, //当前编辑的记录index
		act : {
			listLoading : true, //列表加载等待反馈
			editLoading : false, //编辑提交等待反馈
			editDisabled : false //编辑提交按钮disabled状态
		}
	}

	$scope.result = {//页面初始化时如果当前月没有批次则不自动搜索，故搜索结果初始化为0
		totalCount : 0
	}

	$scope.query = {
		batchId : null, //退缴纳批次
		keyword : null //关键字
	}

	//获取服务端时间，初始化缴纳年月为当前月份
	loadData($http, "/oms/api/getServerTime", $scope, 'server', {callback: function(){
		$scope.query.paymentDate = $filter('date')($scope.server.serverTime, 'yyyy-MM-dd');
	}});


/*************** 事件定义***************/
	//切换缴纳年月事件 - 获取批次信息
	$scope.getBatchInfo = function(){
		if($scope.query.paymentDate){
			var data = {
				"paymentDate" : $scope.query.paymentDate
			}
			loadData($http, "/si/shnew/switchdate", $scope, 'batch', {params: data, callback: function(){//
				if($scope.batch.batchInfo){//有批次
					//缴纳批次
					$scope.query.batchId = $scope.batch.batchInfo.batchId;
					//导出数据链接
					if($scope.query.batchId){
						$scope.exportExcel = "/si/shnew/export?batchId=" + $scope.query.batchId;
					}
					//如果是页面初始化，则查询
					if($scope.init.isPageInit){
						$scope.init.isPageInit = false;
						$scope.search();
					}
				}
				else{//无批次
					$scope.query.batchId = null;
					//不查询，去掉转转转
					if($scope.init.isPageInit){
						$scope.init.act.listLoading = false;
					}
				}
			}});
		}
		else{
			$scope.query.batchId = null;
			if($scope.batch && $scope.batch.batchInfo){
				$scope.batch.batchInfo.confirmStatus= null;
			}
		}
	}

	//查询
	$scope.search = function(isBtnTrigger){
		//验证
		if(!$scope.query.paymentDate){
			alert("请选择缴纳年月");
			return false;
		}

		//获取查询时的批次确认状态，查询结果中记录的“编辑”状态与确认状态关联，未确认则可编辑
		if(!$scope.batch.batchInfo){
			$scope.init.confirmStatus = null;
			$scope.result.totalCount = 0;
			$scope.result.resultList = null;
			return false;
		}
		
		$scope.init.confirmStatus = $scope.batch.batchInfo.confirmStatus
		//数据处理
		if(isBtnTrigger){
			$scope.init.isBtnTrigger = true;
			$scope.query.pageNo = 1;
		}

		//查询请求
		var data = clearObject($scope.query);
		//console.log(data);
		$scope.init.act.listLoading = true; //开始转转转
		loadData($http, "/si/shnew/list", $scope, 'result', {params:data, callback:function(){
			$scope.init.act.listLoading = false;  //结束转转转
			$scope.init.isBtnTrigger = false;
		}});
	}

	//确认缴纳--出警示弹层
	$scope.confirmNewWarning = function(){
		//验证
		if(!$scope.query.paymentDate){
			alert("请选择缴纳年月");
			return false;
		};
		showPopupDiv($("#layer_confirmNew"));
	}
	//确认缴纳--警示弹层提交
	$scope.confirmNew = function(){
		var data = 'batchId=' + $scope.query.batchId;
		$http({method:'POST', url:'/si/shnew/confirmpayment', data:data, headers : {
        'Content-Type' : 'application/x-www-form-urlencoded'
        }}).success(function(data, status, headers, config){
            if(data.status == "ok"){
				$scope.batch.batchInfo.confirmStatus = 1;//已确认缴纳
				$scope.init.confirmStatus = 1; //列表中所有“编辑”按钮隐藏
			}
			else{
				alert(data.message);
			}
        });
		hidePopupDiv($("#layer_confirmNew"));
	}

	//编辑
	$scope.edit = function(item, index){
		$scope.params = angular.copy(item);
		if($scope.params.applyStatus != 0){
			$scope.params.applyStatus = 1; //申请状态（是否失败）默认为成功
		}
		$scope.init.editIndex = index;

        //请求是否失败（申请状态）下拉框选项
        if(!$scope.applyStatus){//只请求一次，如果有了就不用再请求了
        	loadData($http, '/si/common/getbaseinfo', $scope, 'categories', {params: {categoryType: '7'}});
        }
        
        //打开弹层
		showPopupDiv($('#layer_edit'));
	}

	//编辑 - 提交
	var validator = $('#editForm').validate(); 
	if(validator.rules.ruleConfig['fillTogether'] === undefined){
		validator.addRule('fillTogether',{
		    validate: function(element){
		    	var A = $scope.params.extraBeginDate;
		    	var B = $scope.params.extraEndDate;
		        return (A && B || !A && !B);
		    },
		    message: '开始、结束月份必须同时填写'
		});
	};
	if(validator.rules.ruleConfig['BnotEarlyThanA'] === undefined){
		validator.addRule('BnotEarlyThanA',{
		    validate: function(element){
		    	if($scope.params.extraBeginDate && $scope.params.extraEndDate){
		    		var A = new Date($scope.params.extraBeginDate).getTime();
		    		var B = new Date($scope.params.extraEndDate).getTime();
			    	return (B >= A);
		    	}
		    	else{
		    		return true;
		    	}
		    },
		    message: '结束月份不能早于开始月份'
		});
	};
	if(validator.rules.ruleConfig['laterThanJoinDate'] === undefined){
		validator.addRule('laterThanJoinDate',{
		    validate: function(element){
		    	if($scope.params.extraBeginDate && $scope.params.extraEndDate){
		    		var A_temp = new Date($scope.params.newJoinDate);
		    		var A = new Date(A_temp.getFullYear(), A_temp.getMonth(), 1).getTime();
		    		var B = new Date($scope.params.extraBeginDate).getTime();
			        return A <= B;
			    }
			    else{
			    	return true;
			    }
		    },
		    message: '不能早于入职月份'
		});
	};
	$scope.editSave = function(){
		validator.refresh();
		if(validator.validateForm()){
			$scope.init.act.editLoading = true;    //开始转转转
			$scope.init.act.editDisabled = true;   //按钮disabled

			//数据加工
			var data = dataProcess_edit();
			//console.log(data)

			//发送请求
			$http({method:'POST', url:'/si/shnew/submit' , data:data, headers : {
            'Content-Type' : 'application/x-www-form-urlencoded'
            }}).success(function(data, status, headers, config){
            	$scope.init.act.editLoading = false;    //结束转转转
				$scope.init.act.editDisabled = false;   //按钮激活

                if(data.status == "ok"){
                	$scope.result.resultList[$scope.init.editIndex] = angular.copy($scope.params);
					hidePopupDiv($("#layer_edit"));
				}
				else{
					alert(data.message);
				}
            });
		}
	}


/*************** 数据处理 **************/
	//编辑弹层数据加工
	function dataProcess_edit(){
		var data = "";

		data += "id=" + $scope.params.id + //记录id
			    "&applyStatus=" + $scope.params.applyStatus + //是否失败
			    ($scope.params.failureReason ? ("&failureReason=" + $scope.params.failureReason) : "") + //失败原因（失败时才有）
			    ($scope.params.comment ? ("&comment=" + $scope.params.comment) : "") //备注（非必填）

		if($scope.params.paymentTypeId != 1){//自缴时不能编辑以下字段，非自缴可以编辑，故提交时根据是否自缴传不同的参数
			 data += "&paymentBase=" + $scope.params.paymentBase; //社保基数
			 if($scope.params.extraBeginDate && $scope.params.extraEndDate){
			 	data += "&extraBeginDate=" + $filter('date')($scope.params.extraBeginDate, 'yyyy-MM-dd') + //缴纳周期-起
			         	"&extraEndDate=" + $filter('date')($scope.params.extraEndDate, 'yyyy-MM-dd') //缴纳周期-止
			 }     
		}
		return data;
	}

/*************** 事件监听 **************/
	//缴纳年月监听
	$scope.$watch('query.paymentDate', function(newVal, oldVal){
        $scope.getBatchInfo();
	});	

	//翻页对象监听--搜索列表
	$scope.$watch('query.pageNo', function(newVal, oldVal){
        if(newVal!=undefined && !$scope.init.isBtnTrigger){ //当前页号为undefined时不search
        	$scope.search();
        }
	});

	//是否失败监听
	$scope.$watch('params.applyStatus', function(newVal, oldVal){
		if(newVal && newVal != 0){
			$scope.params.failureReason = "";//清空失败原因
		}
	});
}