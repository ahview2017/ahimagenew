adminModule.controller('groupManageCtrl', function(req, $scope) {
	var vm = this;
	//初始化数据
	vm.groupName = '';
	vm.groupNames = '';
	vm.groupMemo = '';
	vm.editName = '';
	vm.editMemo = '';
	vm.Group = [];
	//全选按钮     
	$(".group_selectall").click(function() {
		var xz = $(this).prop("checked"); //判断全选按钮的选中状态
		var ck = $(".group_select").prop("checked", xz); //让class名为qx的选项的选中状态和全选按钮的选中状态一致。
	})
	//初始化标题
	vm.reMyRoleName = '群组管理';
	//初始化当前页默认显示10条
	vm.selPageRows = '10';
	vm.selPageRows_g = '10';
	//群组总条数
	vm.userList_total = 0;
	//默认当前页1
	vm.pagination = {
		current: 1
	};

	//群组列表数据初始化
	//getGroup();	
	//搜索群组名字
	vm.selectGroup = function() {
		//getGroup();
		getGroupList(1, 1);
	};

	function getGroup() {
		req.post("groupManagementCtrl/getGroupManagement.do", {
				groupName: vm.groupName
			})
			.success(function(resp) {
				if(resp.code == '211') {
					vm.groupList = resp.data;
					vm.totalPages = resp.page;
					vm.userList_total = resp.other;
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
	}
	//删除群组
	vm.deleteGroup = function(groupid) {
		req.post("groupManagementCtrl/deleteGroupManagement.do", {
				groupId: groupid,
				rows: vm.selPageRows
			})
			.success(function(resp) {
				if(resp.code == '211') {
					layer.alert('删除此群组成功！');
					//getGroup();
					getGroupList(vm.pagination.current, 1);
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
	}
	//批量删除	
	vm.deleteAll = function() {
		var deleteResult = '';
		$('.group_select:checked').each(function() {
			deleteResult += this.value + ',';
		});
		vm.deleteGroup(deleteResult);

	};
	//群组分页
	getGroupList(1, 1); //默认显示第一页	
	//群组信息页数变化
	vm.pageChanged = function(pageNumber) {
		vm.pagination.current = pageNumber;
		getGroupList(pageNumber, 1);
	};
	//群组信息分页
	function getGroupList(curPage, type) {
		req.post('groupManagementCtrl/getGroupManagement.do', {
			page: curPage,
			rows: vm.selPageRows,
			groupName: vm.groupName
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.groupList = resp.data;
				vm.totalPages = resp.page;
				vm.userList_total = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	function updateGroup(curPage, type) {
		req.post('groupManagementCtrl/getGroupManagement.do', {
			page: curPage,
			rows: vm.selPageRows
		}).success(function(resp) {

			if(resp.code == '211') {} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	//点击添加按钮数据清空
	vm.cleanData = function() {
		vm.groupNames = '';
		vm.groupMemo = '';
		$('.group_cont').show();
	}
	//添加新群组
	vm.addGroup = function() {
		if(!vm.groupNames) {
			layer.alert("请输入群组名字！");
			return;
		} else if(!vm.groupMemo) {
			layer.alert("请输入群组简介！");
			return;
		} else {
			req.post("groupManagementCtrl/addGroupManagement.do", {
				name: vm.groupNames,
				memo: vm.groupMemo,
				rows: vm.selPageRows
			}).success(function(resp) {
				if(resp.code == '211') {
					layer.alert("添加成功！");
					$('.group_cont').hide();
					//getGroup();
					getGroupList(vm.pagination.current, 1);
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		}
	};
	//修改群組
	//获取要修改的群组信息
	vm.editGroup = function(groupid) {
		req.post("groupManagementCtrl/getGroupManagement.do", {
				page: vm.pagination.current,
				rows: vm.selPageRows
			})
			.success(function(resp) {
				if(resp.code == '211') {
					$('.goup_update_na').show();
					for(var i = 0; i < resp.data.length; i++) {
						if(resp.data[i].id == groupid) {
							vm.editName = resp.data

							[i].groupName;
							vm.editMemo = resp.data

							[i].memo;
							vm.editId = groupid;
						}
					}
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
	};
	vm.updateGroup = function() {
		req.post("groupManagementCtrl/editGroupManagement.do", {
			groupId: vm.editId,
			name: vm.editName,
			memo: vm.editMemo,
			rows: vm.selPageRows
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.alert("修改成功！");
				$('.goup_update_na').hide();
				//getGroup(); //刷新数据
				getGroupList(vm.pagination.current, 1);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};
});