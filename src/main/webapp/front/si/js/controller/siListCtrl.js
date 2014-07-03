//社保管理--社会保险--缴纳信息列表页控制器
function SiListCtrl($scope, $http, $rootScope, $timeout, $route){
/*************** 初始化 ****************/
	$scope.init = {
		isBtnTrigger : false, //搜索-区分按钮触发或监听事件的状态标识
		orgInfo : null, //搜索条件-部门完整对象
		levelNames : "选择职等职级", //搜索条件-职等职级选中项文案
		leave : false, //搜索条件-是否离职人员
		paymentStatus : true, //搜索条件-是否当前在缴
		act : {
			listLoading : true //列表加载等待反馈
		}
	}

	$scope.query = {//查询
		"paymentLocationId" : null, //缴纳地ID
		"orgId" : null, //部门ID
		"beginDateStart" : null, //参保起始月份-起
		"beginDateEnd" : null, //参保起始月份-止
		"endDateStart" : null, //参保结束月份-起
		"endDateEnd" : null, //参保结束月份-止
		"possessSocialCard" : null, //社保卡 0-无,1-有
		"applyFormDateStart" : null, //异地申请表发放时间-起
		"applyFormDateEnd" : null, //异地申请表发放时间-止
		"applyForm" : null, //异地申请表 0-无,1-有
		"extraBeginDate" : null, //补缴周期-起
		"extraEndDate" : null, //补缴周期-止
		"levelIds" : null, //职等职级(多选)
		"paymentBase" : null, //缴费基数
		"censusId" : null, //户籍性质ID
		"newJoinDateStart" : null, //入职日期-起
		"newJoinDateEnd" : null, //入职日期-止
		"keyword" : null, //关键字
		"paymentStatus" : null, //当前在缴 0-否,1-是
		"leave" : null, //离职人员 0-在职,1-离职
		"pageNo" : 1 //当前页号
	}

	//搜索条件选项请求（公共接口：获取缴纳地、社保卡、异地申请表、职等职级、户籍性质）
    loadData($http, '/si/common/getbaseinfo', $scope, 'categories', {params: {categoryType: '1,2,3,4,5'}, callback: function(){
    	initTree();//初始化职等职级选项树
    	$scope.search();
    }});

    //往职等职级树上置值
	function setLevel(){
		if($scope.params.levelIds != undefined && $scope.params.levelIds != null && $scope.params.levelIds != ""){
			var strChecked = $scope.params.levelIds.toString();
			setCheckedToTree(tree, strChecked);
		}
	}
	
	//初始化组织架构树--选项树
	function initTree(){
		var titleLevelDegreeList = {
			"id" : 0,
			"text" : "职等职级",
			"children" : $scope.categories.titleLevelDegreeList
		};
		tree = new TreePanel({
			renderTo:'treeWrap',   //树的父容器ID
			isChk:true,            //选项树
			canDept:true,          //部门可选
			canStaff:false,        //员工不可选
			root:titleLevelDegreeList  //json数据源
		});
		tree.render();
	}

	//职等职级弹层打开
	$scope.openLevelPop = function(){
		fnShowLayer($("#selectDept"), $('#popup_tree'), 'right');
		showMask();
	}
	//职等职级弹层--确定按钮
	$scope.getLevel = function(){
		$("#mask").remove(); //if mask div exist, remove it
		$("#ifrm").remove(); 
		hideTreePopup();//关闭弹层
		$scope.query.levelIds = tree.getChecked().toString(); //id串
		if($scope.query.levelIds){
			$scope.init.levelNames = tree.getCheckedTxt().toString(); //文本串
		}
		else{
			$scope.init.levelNames = "选择职等职级";
		}
		
	}


	
/*************** 事件定义***************/
	$scope.exportExcelData = null; //存放搜索条件，用于在导出时使用
	//查询(参数太长，故只好使用post提交)
	$scope.search = function(isBtnTrigger){
		//数据处理
		if(isBtnTrigger){
			$scope.init.isBtnTrigger = true;
			$scope.query.pageNo = 1;
		}
		//部门
		if($scope.init.orgInfo){
			$scope.query.orgId = $scope.init.orgInfo.id; 
		}
		//显示离职人员
		if($scope.init.leave === true){
			$scope.query.leave = 1;
		}
		if($scope.init.leave === false){
			$scope.query.leave = 0;
		}
		//显示当前在缴
		if($scope.init.paymentStatus === true){
			$scope.query.paymentStatus = 1;
		}
		if($scope.init.paymentStatus === false){
			$scope.query.paymentStatus = 0;
		}
		var data = dataProcess_search();
		//$scope.exportExcelData = data;
		//console.log(data);


		//查询请求
		$scope.init.act.listLoading = true; //开始转转转
		$http({method:'POST', url:"/si/base/list", data:data, headers : {
	        'Content-Type' : 'application/x-www-form-urlencoded'
	        }}).success(function(data, status, headers, config){
	        	$scope.init.act.listLoading = false;  //结束转转转
				$scope.init.isBtnTrigger = false;

	            if(data.status == "ok"){
	            	$scope.result = data.data;
	            	//生成附件、异地申请表附件大图、小图url
	            	$.each($scope.result.resultList, function(index, node){
	            		if(node.attachUrl){
	            			var filename = dataProcess_filename(node.attachUrl);
							node.attachUrlBig = filename.big;//大图url
						    node.attachUrlSmall = filename.small; //缩略图url
	            		}

	            		if(node.applyFormAttachUrl){
	            			var filename = dataProcess_filename(node.applyFormAttachUrl);
							node.applyFormAttachUrlBig = filename.big;//大图url
						    node.applyFormAttachUrlSmall = filename.small; //缩略图url
	            		}
	            	});

	            	//生成导出数据url
					$scope.excelUrl = "/si/base/export?" + dataProcess_search();
	            }
	        });
	}

	//导出数据
	/*$scope.exportExcel = function(){
        loadData($http, '/si/base/export', $scope, 'categories', {params: {categoryType: '1,2,3,4,5'}, callback: function(data){
            console.log($scope.)
        }});
		$http({method:'POST', url:"/si/base/export", async:false, data:$scope.exportExcelData, headers : {
	        'Content-Type' : 'application/x-www-form-urlencoded'
	        }}).success(function(data, status, headers, config){
	            if(data.status == "ok"){
	            	
	            }
	        });
	}*/

	//编辑 - 打开弹层
	var editIndex = null;
	$scope.edit = function(item, index){
		$scope.params = angular.copy(item);
		editIndex = index;
		showPopupDiv($("#layer_edit"));
	}

	//编辑 - 提交
	$scope.editSubmit = function(){
		var validator = $('#editForm').validate(); 
		$scope.init.act.editLoading = true;    //开始转转转
		$scope.init.act.editDisabled = true;   //按钮disabled

		//数据加工
		var data = dataProcess_edit();
		//console.log(data);

		$http({method:'POST', url:"/si/base/submit", data:data, headers : {
        'Content-Type' : 'application/x-www-form-urlencoded'
        }}).success(function(data, status, headers, config){
        	$scope.init.act.editLoading = false;    //结束转转转
			$scope.init.act.editDisabled = false;   //按钮激活

            if(data.status == "ok"){
        		var current = $scope.result.resultList[editIndex];
        		current.comment = $scope.params.comment;//备注
                //附件
        		current.attachUrl = $scope.params.attachUrl; //原始url
                current.attachUrlBig = $scope.params.attachUrlBig; //大图url
                current.attachUrlSmall = $scope.params.attachUrlSmall; //缩略图url
                //异地申请表
                current.applyFormAttachUrl = $scope.params.applyFormAttachUrl; //原始url
                current.applyFormAttachUrlBig = $scope.params.applyFormAttachUrlBig; //大图url
                current.applyFormAttachUrlSmall = $scope.params.applyFormAttachUrlSmall; //缩略图url
        		if(current.applyFormAttachUrl){
        			current.applyForm = 1;
        		}
                else{
                    current.applyForm = 0;
                }

        		hidePopupDiv($("#layer_edit"));
			}
			else{
				alert(data.message);
			}
        });
	}

	//编辑弹层 - 上传附件、异地申请表
	$scope.attachType = null;
    $('#uploadFile, #uploadFile2').click(function(){
    	switch($(this).attr("id")){
    		case 'uploadFile': $scope.attachType = 1; break; //异地申请表
    		case 'uploadFile2': $scope.attachType = 2; break; //附件
    		default: break;
    	}
        $('#trueFile').trigger('click');
    });
    $('#trueFile').change(function(){
        var fileUploader = new $.fn.dui.FilesUploader($(this).get(0).files, '/si/base/attach', {
        	/*urlParams: true,//以url参数方式传参
        	params: {//参数
        		attachType: $scope.attachType
        	},*/
            successCallback: function(response){
                if (response.status == "ok"){//成功
                	$scope.$apply(function(){
                		//上传到后台获得一个地址 fileName.png，fileName.jpeg（任意图片类型）
						//前端生成缩略图url规则：fileName.png_small.png，fileName.jpeg_small.jpeg
						//前端生成大图的url规则：fileName.png_big.png，fileName.jpeg_big.jpeg
						var filename = dataProcess_filename(response.data.attachUrl);
                		switch($scope.attachType){
				    		case 1: //异地申请表
                                    $scope.params.applyFormAttachUrl = response.data.attachUrl;//原始url
                                    $scope.params.applyFormAttachUrlBig = filename.big;//大图url
                                    $scope.params.applyFormAttachUrlSmall = filename.small; //缩略图url
								    break; 
				    		case 2: //附件
                                    $scope.params.attachUrl = response.data.attachUrl;//原始url
                                    $scope.params.attachUrlBig = filename.big;//大图url
                                    $scope.params.attachUrlSmall = filename.small; //缩略图url
								    break; 
				    		default: break;
				    	}
                		
                	});
                } else {//失败
                    alert(response.message);
                }
            }
        });
        
        fileUploader.upload();
        $(this).val('');
    });

	//编辑弹层 - 删除附件()
	$scope.delAttach = function(id, attachType){
		switch(attachType){
    		case 1: //异地申请表
                    $scope.params.applyFormAttachUrl = null;//原始url
                    $scope.params.applyFormAttachUrlBig = null;//大图url
                    $scope.params.applyFormAttachUrlSmall = null; //缩略图url
				    break; 
    		case 2: //附件
                    $scope.params.attachUrl = null;//原始url
                    $scope.params.attachUrlBig = null;//大图url
                    $scope.params.attachUrlSmall = null; //缩略图url
				    break; 
    		default: break;
    	}
		//数据加工
		/*var data = {
			id : id,
			attatchType : attachType //1-异地申请表, 2-附件
		}

		$http({method:'POST', url:"/si/base/attachDel", data:data, headers : {
        'Content-Type' : 'application/x-www-form-urlencoded'
        }}).success(function(data, status, headers, config){
            if(data.status == "ok"){
        		switch(attachType){
		    		case 1: //异地申请表
                            $scope.params.applyFormAttachUrl = null;//原始url
                            $scope.params.applyFormAttachUrlBig = null;//大图url
                            $scope.params.applyFormAttachUrlSmall = null; //缩略图url
						    break; 
		    		case 2: //附件
                            $scope.params.attachUrl = null;//原始url
                            $scope.params.attachUrlBig = null;//大图url
                            $scope.params.attachUrlSmall = null; //缩略图url
						    break; 
		    		default: break;
		    	}
			}
			else{
				alert(data.message);
			}
        });*/
	}

	//导入数据
    $('#uploadFile_excel').click(function(){
        $('#trueFile2').trigger('click');
    });
    $('#trueFile2').change(function(){
        var fileUploader = new $.fn.dui.FilesUploader($(this).get(0).files, '/si/base/import', {
        	/*urlParams: true,//以url参数方式传参
        	params: {//参数
        		attachType: $scope.attachType
        	},*/
            successCallback: function(response){
                if (response.status == "ok"){//成功
                	$route.reload();
                } else {//失败
                    alert(response.message);
                }
            }
        });
        
        fileUploader.upload();
        $(this).val('');
    });



/*************** 数据处理 **************/
	//查询-数据处理
	function dataProcess_search(){
		var data = clearObject($scope.query);
		var search = "";
		for(key in data){
			search += "&" + key + "=" + data[key];
		}
		if(search === ""){
			return "";
		}
		else{
			return (search.substring(1, search.length));
		}
	}

	//编辑-数据处理
	function dataProcess_edit(){
		var dataTemp = {};
		copyObjectElement(dataTemp, $scope.params, ["id","applyFormAttachUrl","attachUrl","comment"]);
		var data = clearObject(dataTemp);
		var search = "";
		for(key in data){
			search += "&" + key + "=" + data[key];
		}
		if(search === ""){
			return "";
		}
		else{
			return (search.substring(1, search.length));
		}
	}

	//加工附件文件名，通过原始文件url，生成大图url，缩略图url
	function dataProcess_filename(filename){
		var filename = filename;//原始url
		var start = filename.lastIndexOf(".");
		var filename_prefix = filename.substring(0, start);//文件主名
		var filename_suffix = filename.substring(start+1, filename.length);//文件后缀
		var small = filename_prefix + "." + filename_suffix + "_small." + filename_suffix;//缩略图url
		var big = filename_prefix + "." + filename_suffix + "_big." + filename_suffix;//大图url
		return {
			small : small,
			big : big
		}
	}


/*************** 事件监听 **************/
	//翻页对象监听--搜索列表
	$scope.$watch('query.pageNo', function(newVal, oldVal){
        if(newVal!=undefined && !$scope.init.isBtnTrigger){ //当前页号为undefined时不search
        	$scope.search();
        }
	});
}