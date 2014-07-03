var si = angular.module('si', ['dui.directive']).config(function($routeProvider, $locationProvider){
	$locationProvider.html5Mode(true);
	$routeProvider.
		when('/siList', {templateUrl:'/static/front/si/views/siList.html', controller: SiListCtrl}). //社会保险-缴纳列表
		when('/siShNewList', {templateUrl:'/static/front/si/views/siShNewList.html', controller: SiShNewListCtrl}). //社会保险-上海新进列表
		when('/siShQuitList', {templateUrl:'/static/front/si/views/siShQuitList.html', controller: SiShQuitListCtrl}). //社会保险-上海退出列表
		when('/siShSelfList', {templateUrl:'/static/front/si/views/siShSelfList.html', controller: SiShSelfListCtrl}). //社会保险-上海自缴列表
		when('/siNbNewList', {templateUrl:'/static/front/si/views/siNbNewList.html', controller: SiNbNewListCtrl}).  //社会保险-宁波新进列表
		when('/siNbQuitList', {templateUrl:'/static/front/si/views/siNbQuitList.html', controller: SiNbQuitListCtrl}).  //社会保险-宁波退出列表
		when('/siSzQuitList', {templateUrl:'/static/front/si/views/siSzQuitList.html', controller: SiSzQuitListCtrl}). //社会保险-深圳退出列表
		when('/noPrivilege', {templateUrl:'/static/front/si/views/noPrivilege.html'}).  //无权限重定向
		otherwise({redirectTo:'/siList'});
});

si.run(function($rootScope, $http, $location){
    $rootScope.privileges = privileges;//权限
    $rootScope.version = version;//版本
    $rootScope.containerWidth = {};

	//页面加载时判断权限
	$rootScope.$on('$routeChangeStart', function(event, next, current){
		var p = $rootScope.privileges;
		var obj = [
			{ controller: SiListCtrl, privilege: p.hrm_si_view }, //社会保险--在缴信息列表页
			{ controller: SiShNewListCtrl, privilege: p.hrm_si_sh_view }, //社会保险--上海新进列表页
			{ controller: SiShQuitListCtrl, privilege: p.hrm_si_sh_view }, //社会保险--上海退出列表页
			{ controller: SiShSelfListCtrl, privilege: p.hrm_si_sh_view }, //社会保险--上海自缴列表页
			{ controller: SiNbNewListCtrl, privilege: p.hrm_si_nb_view }, //社会保险--宁波新进列表页
			{ controller: SiNbQuitListCtrl, privilege: p.hrm_si_nb_view }, //社会保险--宁波退出列表页
			{ controller: SiSzQuitListCtrl, privilege: p.hrm_si_sz_view } //社会保险--深圳退出列表页
		];
		$rootScope.privilegeTreat(obj, next);
	});
	$rootScope.privilegeTreat = function(obj, next){
		for(var i=0; i<obj.length; i++){
			if(next.controller == obj[i].controller && !obj[i].privilege){
				$location.path('/noPrivilege');
			}
		}
	}
});


/********get请求的封装
**@param http 传参时传入$http
**@param url 字符串
**@param scope 作用域，传参时传入$scope
**@param key 对应scope下的对象的键名
**@param opts 其他参数 
**@param opts.params - get请求的参数
**@param opts.callback - get请求的回调函数 
**/
function loadData(http, url, scope, key, opts){
	var opts = opts || {};
	var resultCallback = function(response){
		if(response.status == "ok"){
			scope[key] = response.data;
			if(opts.callback){
				opts.callback(response);
			}
		}
		else{
			alert(response.message);
		}
	};
	var params = {params: opts.params};
	var promise = http.get(url, params);
	promise.success(resultCallback);

	return promise;
}

/*删除某个对象中的一些属性
**@param obj 要删除属性的对象
**@param arr 要删除属性组成的数组
**/
function deletePro(obj, arr){
	for (var i = 0; i < arr.length; i++) {
		delete obj[arr[i]];
	};
}

/*从一个对象中复制一些属性
**@param target 目标对象
**@param resource 源对象
**@param keyArray 要复制的属性名称数组，如['key1', 'key2']
**/
function copyObjectElement(target, resource, keyArray){
	for(var i=0; i<keyArray.length; i++){
		target[keyArray[i]] = resource[keyArray[i]];
	}
}

function clearObject(resource){
	var obj = angular.copy(resource);
	for(key in obj){
		if(obj[key] === null || obj[key] === undefined || obj[key] === ""){
			delete obj[key];
		}
	}
	return obj;
}

//社会保险 - 上海自缴 - 新增（根据工号或姓名，查找员工）
angular.module('si').directive('searchEmp', function($timeout){
	return {
		restrict: 'E',
		require: '?ngModel',
		link: function(scope, el, attrs, ngModel){
			el.find('.js_search').autocomplete({
				urlOrData: '/si/self/searchEmpByCodeAndName',//请求的url
				itemTextKey: 'longName',//返回数据源中要在自动完成列表中显示的数据的key
				dataKey: 'empList',//数据源的key
				crossDomain: false,
				autocompleteOnfocus: false,
				lazyTime: 50,
				setRequestParams: function(requstParams){
					requstParams.keyword = $.trim(el.find('.js_search').val());
				},
				selectCallback: function(selectedItem){		//选中选项， 执行回调
					el.find('.js_search').blur();
					scope.$apply(function(){
						ngModel.$setViewValue(selectedItem);//selectedItem可以是简单数据，也可能是个对象
						//scope.isSelect = true;
					});
				}
			});

			ngModel.$render = function(){
				var modelValue = ngModel.$viewValue || {};
				el.find('.js_search').val(modelValue.userName || '');
			};
		}
	}
});