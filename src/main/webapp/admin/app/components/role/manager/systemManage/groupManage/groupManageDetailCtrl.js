adminModule.controller('groupManageDetailCtrl', function(req, $scope, $stateParams,$state,modalOperate,$http) {
	var vm = this;
	console.log("groupManageCtrl");
	
	//初始化数据
	vm.groupName = '';
	vm.groupNames = '';
	vm.groupMemo = '';	
	//初始化标题
	vm.reMyRoleName = '群组管理';
	//初始化当前页默认显示10条
	vm.selPageRows = '10';	
	//群组总条数
	vm.userList_total = 0;
	//默认当前页1
	vm.pagination = {
		current: 1
	};
	//导出用户的模态框显示
	vm.userModalShow = function(modalId){
		modalOperate.modalShow(modalId);
	}
	//导出用户的模态框隐藏
	vm.databaseModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	};

	//导出全选按钮
	$(".user_selectall").click(function() {
		var xz = $(this).prop("checked"); //判断全选按钮的选中状态
		$(".user_select").prop("checked", xz); //让class名为qx的选项的选中状态和全选按钮的选中状态一致。
	})
	//导出
	vm.userexport = function() {
		var exportResult = {};
		$('.user_select:checked').each(function() {
			exportResult[this.value]='';
		});
		if(exportResult.length==0){
			layer.alert("请勾选需要导出的用户信息");
			return;
		}
		console.log(exportResult);
		var userInfo = angular.toJson(exportResult);
		var time = new Date().format("yyyyMMddhhmmss");
		var fileName="用户信息_"+time+".xls";//文件名
		var paramsObj = {
			userInfo: userInfo,
			groupId: $stateParams.groupId
		};
		return $http({
			url: "/photo/userCtro/downLoadUserInfo.do",
			method: "POST",
			headers: {
				'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
			},
			data: $.param(paramsObj),
			responseType: 'arraybuffer'
		}).success(function (data) {
			var blob = new Blob([data], {type: "application/vnd.ms-excel"});
			var objectUrl = URL.createObjectURL(blob);
			var a = document.createElement('a');
			document.body.appendChild(a);
			a.setAttribute('style', 'display:none');
			a.setAttribute('href', objectUrl);
			a.setAttribute('download', fileName);
			a.click();
			URL.revokeObjectURL(objectUrl);
			modalOperate.modalHide('user-export-modal');
		});
	}
	//展开获取更多导出字段
	$('.user-export-modal-ll').height(377);
	vm.showmoreexportInfo = false;
	vm.showmoreexport = function() {
		vm.showmoreexportInfo = !vm.showmoreexportInfo;
		if(vm.showmoreexportInfo) {
			$('.user-export-modal-ll').height(837);
		} else {
			$('.user-export-modal-ll').height(377);
		}
	};
	/**
	 * 格式化时间
	 * @param format
	 * @returns {*}
	 */
	Date.prototype.format =function(format)
	{
		var o = {
			"M+" : this.getMonth()+1, //month
			"d+" : this.getDate(), //day
			"h+" : this.getHours(), //hour
			"m+" : this.getMinutes(), //minute
			"s+" : this.getSeconds(), //second
			"q+" : Math.floor((this.getMonth()+3)/3), //quarter
			"S" : this.getMilliseconds() //millisecond
		}
		if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
			(this.getFullYear()+"").substr(4- RegExp.$1.length));
		for(var k in o)if(new RegExp("("+ k +")").test(format))
			format = format.replace(RegExp.$1,
				RegExp.$1.length==1? o[k] :
					("00"+ o[k]).substr((""+ o[k]).length));
		return format;
	}
	//修改群组成员弹框		
	vm.getUserAll = function() {
		//群组所有成员
		req.post("userCtro/getUserAll.do", {
			groupId: $stateParams.groupId,
			userName: vm.selectAllName //查询用户
		}).success(function(resp) {
			if(resp.code == '211') {
				console.log(resp);
				vm.groupUser = resp.data;
				vm.totalPages = resp.page;
				vm.userList_total = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
		//群组组内成员	
		req.post("groupManagementCtrl/getGroupManagementUser.do", {
			groupId: $stateParams.groupId,
			userName: vm.selectGroupName //查询指定用户			
		}).success(function(resp) {
			if(resp.code == '211') {
				console.log(resp.data);
				vm.getGroupManagementUser = resp.data;
				vm.totalPages_l = resp.page;
				vm.userList_total_l = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};
	vm.getUserAll();
	//添加组成员
	vm.addGroupUser = function(userId) {
		req.post("groupManagementCtrl/addGroupManagementUser.do", {
			groupId: $stateParams.groupId,
			userId: userId
		}).success(function(resp) {
			if(resp.code == '211') {
				console.log(resp.data);
				layer.alert('添加到组成功！');
				update();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	//刷新群组数据
	function update() {
		//群组组内成员	
		req.post("groupManagementCtrl/getGroupManagementUser.do", {
			groupId: $stateParams.groupId,
			userName: vm.selectGroupName //查询指定用户
		}).success(function(resp) {
			if(resp.code == '211') {
				console.log(resp.data);
				vm.getGroupManagementUser = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
		//群组所有成员
		req.post("userCtro/getUserAll.do", {
			groupId: $stateParams.groupId,
			rows:vm.selPageRows_l,
			userName: vm.selectAllName //查询用户
		}).success(function(resp) {
			if(resp.code == '211') {
				console.log(resp.data);
				vm.groupUser = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	//移除组成员
	vm.deleteGroupmeber = function(userid) {
		req.post("groupManagementCtrl/deleteGroupManagementUser.do", {
			groupId: $stateParams.groupId,
			userId: userid
		}).success(function(resp) {
			if(resp.code == '211') {
				console.log(resp.data);
				layer.alert('移除成功！');
				update();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	//所有成员分页
	vm.pageChanged = function(pageNumber) {
		vm.pagination.current = pageNumber;
		getallUserList(pageNumber, 1);
	};

	function getallUserList(curPage, type) {
		req.post('userCtro/getUserAll.do', {
			page: curPage,
			rows: vm.selPageRows,
			groupId: $stateParams.groupId,
			userName: vm.selectAllName //查询用户
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.groupUser = resp.data;
				vm.totalPages = resp.page;
				vm.userList_total = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	vm.save=function(){
		layer.alert('保存成功！');
		$state.go('role.manager.groupManage');
	}
	/**
	 * 传入拖拽发生id和被拖拽id，如果相同，则本体就是按钮和拖拽对象
	 */
	vm.modalMove = function(dragDiv, tagDiv) {
		var dragDiv = document.getElementById(dragDiv);
		var tagDiv = document.getElementById(tagDiv);
		var tagContainer = document;
		var e, offsetT, offsetL, downX, downY, moveX, moveY;

		dragDiv.onmouseover = function(e) {
			this.style.cursor = "move";
		};

		dragDiv.onmousedown = function(e) {
			e = e || window.event;
			offsetT = tagDiv.offsetTop;
			offsetL = tagDiv.offsetLeft;
			downX = e.clientX;
			downY = e.clientY;

			dragDiv.onmouseup = function(e) {
				tagContainer.onmousemove = function() {
					return false;
				}
			};

			tagContainer.onmousemove = function(e) {
				e = e || window.event;
				moveX = e.clientX;
				moveY = e.clientY;
				tagDiv.style.top = offsetT + (moveY - downY) + "px";
				tagDiv.style.left = offsetL + (moveX - downX) + "px";
			}
		}
	}
});