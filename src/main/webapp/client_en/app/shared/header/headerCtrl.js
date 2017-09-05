cnsphoto_enModule.controller('headerCtrl', function($state, req, md5, $scope, $rootScope, $cookies) {
	var vm = this;
	console.log("headerCtrl");

	function initSetting() {
		//从cookie获取客户端用户名
		$rootScope.client_uName = $cookies.get('client_uname');
		$rootScope.admin_roleInfo = $cookies.get('admin_roleInfo');
	}
	initSetting();

	//判断用户角色，根据用户角色显示不同的 内容
	/*
	 * 摄影师：3
	 * 订户：5
	 */

	var role = angular.fromJson($rootScope.admin_roleInfo, true);
	vm.roleTogg_5 = false;
	vm.roleTogg_3 = false;
	vm.roleTogg = function() {
		if(role) {
			for(var i = 0; i < role.length; i++) {
				if(role[i].id == 5) {
					vm.roleTogg_5 = true;
				} else if(role[i].id == 3) {
					vm.roleTogg_3 = true;
				}
			}
		}
	}
	vm.roleTogg();
	//重新登录前清除所有的cookie
	function removeAllCookies() {
		var cookies = $cookies.getAll();
		angular.forEach(cookies, function(v, k) {
			$cookies.remove(k);
		});
	}
	//用户退出请求
	vm.req_userExit = function() {
		req.post('login/doLogout.do', {}).success(function(resp) {
			if(resp.code == '211') {
				$state.go('root.login');
				//$cookies.remove('client_uname');
				//initSetting();
				$rootScope.client_uName = null;
				console.log($rootScope.client_uName);
				removeAllCookies();
			} else {
				console.log(resp.msg);
			}
		});
	}
	//用户退出
	vm.logout = function() {
		vm.req_userExit();
	};
	/**
	 * 全文检索
	 * */
	vm.searchAll = function() {
		/*getFullText.req_getFullText({
		    page: 1,
		    rows: 10,
		    strWhere: vm.searchAllName
		});*/
		vm.randomNum = new Date().getTime() + Math.random() + Math.random();
		var fullTextUrl = $state.href('root.fullText', {
			searchAllName: vm.searchAllName,
			randomNum: vm.randomNum
		});
		window.open(fullTextUrl, '_blank');
	};

	req.post('enColumn/showEnColumn.do', {
		"pid": 0,
		"state": 1,
		"position": 1,
		"langType": 1
	}).success(function(resp) {
		if(resp.code == '211') {
			$scope.upLanMu = resp.data;
		} else if(resp.msg != '未登录') {
			layer.alert(resp.msg);
		}
	});
});