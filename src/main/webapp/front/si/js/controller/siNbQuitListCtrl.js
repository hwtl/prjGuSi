//社保管理--社会保险--宁波退出列表页控制器
function SiNbQuitListCtrl($scope, $http, $rootScope, $filter, $route){
/*************** 初始化 ****************/
	$scope.init = {
		isBtnTrigger : false, //搜索-区分按钮触发或监听事件的状态标识
		isPageInit : true, //记录页面初始化的状态
		isGenerateList : null, //记录当前月份是否已拉名单
		act : {
			listLoading : true //列表加载等待反馈
		}
	}

	$scope.result = {//页面初始化时如果当前月没有批次则不自动搜索，故搜索结果初始化为0
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
	//切换退出年月事件 - 获取批次信息
	$scope.getBatchInfo = function(){
		if($scope.query.paymentDate){
			var data = {
				"paymentDate" : $scope.query.paymentDate
			}
			loadData($http, "/si/nbquit/switchdate", $scope, 'batch', {params: data, callback: function(){//
				var t = new Date();
				var today = new Date(t.getFullYear(), t.getMonth(), 1).getTime();
				var p = new Date($scope.query.paymentDate);
				var paymentDate = new Date(p.getFullYear(), p.getMonth(), 1).getTime();
				if(today== paymentDate){//当前月
					if($scope.batch.batchInfo){//有批次
						//退出批次
						$scope.query.batchId = $scope.batch.batchInfo.batchId;
						//已拉名单
						$scope.init.isGenerateList = 1;
						//导出数据链接
						if($scope.query.batchId){
							$scope.exportExcel = "/si/nbquit/export?batchId=" + $scope.query.batchId;
						}
						//如果是页面初始化，则查询
						if($scope.init.isPageInit){
							$scope.init.isPageInit = false;
							$scope.search();
						}
					}
					else{//无批次
						//未拉名单
						$scope.init.isGenerateList = 0;
	                    $scope.query.batchId = null;
						//不查询，去掉转转转
						if($scope.init.isPageInit){
							$scope.init.act.listLoading = false;
						}
					}
				}
				else{//历史月（不出拉名单状态）
					$scope.init.isGenerateList = null;
				}
			}});
		}
		else{
			$scope.query.batchId = null;
			$scope.init.isGenerateList = null;
			if($scope.batch){
				$scope.batch.batchInfo.confirmStatus= null;
			}
		}
		
	}

	//查询
	$scope.search = function(isBtnTrigger){
		//验证
		if(!$scope.query.paymentDate){
			alert("请选择退出年月");
			return false;
		}


        if(!$scope.batch.batchInfo){
            $scope.result.totalCount = 0;
            $scope.result.resultList = null;
            return false;
        }

		//数据处理
		if(isBtnTrigger){
			$scope.init.isBtnTrigger = true;
			$scope.query.pageNo = 1;
		}

		//查询请求
		var data = clearObject($scope.query)
		//console.log(data);
		$scope.init.act.listLoading = true; //开始转转转
		loadData($http, "/si/nbquit/list", $scope, 'result', {params:data, callback:function(){
			$scope.init.act.listLoading = false;  //结束转转转
			$scope.init.isBtnTrigger = false;
		}});
	}

	//拉名单
	$scope.createBatch = function(){
		loadData($http, "/si/nbquit/gbbegintime", $scope, 'newBatch', {callback:function(){
			var beginTime = $filter('date')($scope.newBatch.batchBeginTime, 'yyyy-MM-dd HH:mm:ss');
			if(window.confirm("确认拉 " + beginTime + "至今的宁波退出名单？")){
				showPopupDiv($("#layer_ajaxing"));
				var data = 'paymentDate=' + $scope.query.paymentDate;
				$http({method:'POST', url:'/si/nbquit/generatebatch', data:data, headers : {
		        'Content-Type' : 'application/x-www-form-urlencoded'
		        }}).success(function(data, status, headers, config){
		            hidePopupDiv($("#layer_ajaxing"));
		            if(data.status == "ok"){
		            	$route.reload();
					}
					else{
						alert(data.message);
					}
		        });
			}
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
		var data = 'batchId=' + $scope.query.batchId;
		$http({method:'POST', url:'/si/nbquit/confirmquit', data:data, headers : {
        'Content-Type' : 'application/x-www-form-urlencoded'
        }}).success(function(data, status, headers, config){
            if(data.status == "ok"){
				$scope.batch.batchInfo.confirmStatus = 1;//已确认退出
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
        $scope.getBatchInfo();
	});	

	//翻页对象监听--搜索列表
	$scope.$watch('query.pageNo', function(newVal, oldVal){
        if(newVal!=undefined && !$scope.init.isBtnTrigger){ //当前页号为undefined时不search
        	$scope.search();
        }
	});
}