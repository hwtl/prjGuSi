//社保管理--社会保险--上海退出列表页控制器
function SiShQuitListCtrl($scope, $http, $filter){

/*************** 初始化 ****************/
	$scope.init = {
		isBtnTrigger : false, //搜索-区分按钮触发或监听事件的状态标识
		confirmStatus : null, //用于存储当前选中批次是否已确认退出的状态
		act : {
			listLoading : false //列表加载等待反馈
		}
	}

	$scope.result = {//页面初始化时不自动搜索，故搜索结果初始化为0
		totalCount : 0
	}

	$scope.query = {
		batchId : null, //退出批次
		keyword : null //关键字
	}

	//获取服务端时间，初始化退出年月为当前月份
	loadData($http, "/oms/api/getServerTime", $scope, 'server', {callback: function(){
		$scope.query.paymentDate = $filter('date')($scope.server.serverTime, 'yyyy-MM-dd');
	}});


/*************** 事件定义***************/
	//切换退出年月事件 - 获取批次下拉框选项
	$scope.getBatchList = function(){
		if($scope.query.paymentDate){
			var data = {
				"paymentDate" : $scope.query.paymentDate
			}
			loadData($http, "/si/shquit/switchdate", $scope, 'batch', {params:data});
		}
		else{
			$scope.init.confirmStatus = null;
			$scope.query.batchId = null;
			$scope.batch = null;
		}
	}

	//切换退出批次 - 获取批次信息
	$scope.getBatchInfo = function(){
		if($scope.query.batchId){
			//导出数据链接
			$scope.exportExcel = "/si/shquit/export?batchId=" + $scope.query.batchId;
			//确认退出按钮状态
			var pickedBatch = $scope.batch.batchInfo.filter(function(item){
			    return item.batchId === $scope.query.batchId;
			});
			$scope.init.confirmStatus = pickedBatch[0].confirmStatus;
		}
		else{
			$scope.init.confirmStatus = null;
		}
	}

	//查询
	$scope.search = function(isBtnTrigger){
		//验证
		if(!$scope.query.paymentDate){
			alert("请选择退出年月");
			return false;
		}
		if(!$scope.query.batchId){
			alert("请选择退出批次");
			return false;
		}

		//数据处理
		if(isBtnTrigger){
			$scope.init.isBtnTrigger = true;
			$scope.query.pageNo = 1;
		}

		//查询请求
		var data = clearObject($scope.query);
		//console.log(data);
		$scope.init.act.listLoading = true; //开始转转转
		loadData($http, "/si/shquit/list", $scope, 'result', {params:data, callback:function(){
			$scope.init.act.listLoading = false;  //结束转转转
			$scope.init.isBtnTrigger = false;
		}});
	}

	//确认退出--出警示弹层
	$scope.confirmQuitWarning = function(){
		//验证
		if(!$scope.query.paymentDate){
			alert("请选择退出年月");
			return false;
		};
		showPopupDiv($("#layer_confirmQuit"));
	}
	//确认退出--警示弹层提交
	$scope.confirmQuit = function(){
		var batchId = $scope.query.batchId;
		var data = 'batchId=' + $scope.query.batchId;
		$http({method:'POST', url:'/si/shquit/confirmquit', data:data, headers : {
        'Content-Type' : 'application/x-www-form-urlencoded'
        }}).success(function(data, status, headers, config){
            if(data.status == "ok"){
				$scope.init.confirmStatus = 1;//已确认退出
				$.each($scope.batch.batchInfo, function(index, node){//更新批次下拉列表项的状态
					if(node.batchId === batchId){
						node.confirmStatus = 1;
					}
				});
			}
			else{
				alert(data.message);
			}
        });
		hidePopupDiv($("#layer_confirmQuit"));
	}


/*************** 数据处理 **************/

/*************** 事件监听 **************/
	//退出年月监听
	$scope.$watch('query.paymentDate', function(newVal, oldVal){
        $scope.getBatchList();
	});	

	//退出批次监听
	$scope.$watch('query.batchId', function(newVal, oldVal){
		$scope.getBatchInfo()
	})

	//翻页对象监听--搜索列表
	$scope.$watch('query.pageNo', function(newVal, oldVal){
        if(newVal!=undefined && !$scope.init.isBtnTrigger){ //当前页号为undefined时不search
        	$scope.search();
        }
	});
	
}