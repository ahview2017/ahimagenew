/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('friendsLinkManageCtrl', function($scope, $cookies, req, md5, $state, $rootScope, allModalMove) {
	var vm = this;

	//模态框遮罩层显示
	function maskShow() {
		$rootScope.layerIfShow = true;
	}

	//模态框遮罩层隐藏
	function maskHide() {
		$rootScope.layerIfShow = false;
	}
	//移动模态框
	vm.moveModal = function(dragDiv, tagDiv) {
		allModalMove.modalMove(dragDiv, tagDiv);
	}

	//网站信息模态框显示
	vm.friendsLinkModalShow = function(modalId) {
		$('#' + modalId).show();
		maskShow();
	};
	//网站信息模态框隐藏
	vm.friendsLinkModalHide = function(modalId) {
		$('#' + modalId).hide();
		maskHide();
	};
	//是否启用默认为启用
	vm.addstate = '1';
	//初始化
	function init() {
		selectFriendsLlink();
	}
	init();
	/**
	 * 处理全选
	 */
	//全选按钮     
	$(".selectall").click(function() {
		var xz = $(this).prop("checked"); //判断全选按钮的选中状态
		$(".select").prop("checked", xz); //让class名为qx的选项的选中状态和全选按钮的选中状态一致。
	})
	//批量删除	
	vm.deleteAll = function() {
		var deleteResult = '';
		$('.select:checked').each(function() {
			deleteResult += this.value + ',';
		});
		vm.deleteFriendLink(deleteResult);
	};

	//友情链接列表数据展示
	function selectFriendsLlink() {
		req.post('friendshipLink/friendshipLinkShow.do', {
			langType: window.localStorage.lang
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.friendsLink = resp.data;
				for(var i = 0; i < resp.data.length; i++) {
					if(resp.data[i].state == 1) {
						resp.data[i].state = '启用';
					} else {
						resp.data[i].state = '不启用';
					}
				}
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	//友情链接添加
	vm.cleanData=function(){
		vm.friendsLinkModalShow('friendsLink-add-modal');
		vm.addtitle='';
		vm.addorderId='';
		vm.addfriendshipLink='';
		vm.addremark='';
	}
	vm.addFriendsLink = function() {		
		if(!vm.addorderId) {
			layer.alert('排序不能为空');
		} else if(!vm.addtitle) {
			layer.alert('标题不能为空');
		} else if(!vm.addfriendshipLink) {
			layer.alert('链接不能为空');
		} else if(!vm.addstate) {
			layer.alert('状态值不能为空');
		} else if(isNaN(vm.addorderId)){
			layer.alert('排序值必须为数字');
		}else {
			req.post('friendshipLink/addFriendshipLink.do', {
				langType: window.localStorage.lang,
				title: vm.addtitle,
				orderId: vm.addorderId,
				friendshipLink: vm.addfriendshipLink,
				remark: vm.addremark,
				state: vm.addstate
			}).success(function(resp) {
				if(resp.code == '211') {
					layer.alert('添加成功');
					selectFriendsLlink();
					vm.friendsLinkModalHide('friendsLink-add-modal');
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		}
	}
	//友情链接修改    
	vm.editFriendsLink = function(id) {
		req.post('friendshipLink/friendshipLinkShow.do', {
			langType: window.localStorage.lang
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.friendsLinkModalShow('friendsLink-modify-modal');
				for(var i = 0; i < resp.data.length; i++) {
					if(resp.data[i].id == id) {
						vm.editid = id;
						vm.editorderId = resp.data[i].orderId;
						vm.edittitle = resp.data[i].title;
						vm.editfriendshipLink = resp.data[i].friendshipLink;
						vm.editstate = '' + resp.data[i].state + '';
						vm.editremark = resp.data[i].remark;
					}
				}
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};
	vm.editfriendshipLinks = function() {
		req.post('friendshipLink/editFriendshipLink.do', {
			id: vm.editid,
			langType: window.localStorage.lang,
			title: vm.edittitle,
			orderId: vm.editorderId,
			friendshipLink: vm.editfriendshipLink,
			remark: vm.editremark,
			state: vm.editstate
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.alert('修改成功');
				vm.friendsLinkModalHide('friendsLink-modify-modal');
				selectFriendsLlink();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};
	//友情链接删除
	vm.deleFriendLink = function(id) {
		vm.friendsLinkModalShow('friendsLink-del-modal');
		vm.deleFriendLinkId = id;
	}
	vm.deleteFriendLink = function(id) {
		req.post('friendshipLink/deleteFriendshipLink.do', {
			id: id,
			langType: window.localStorage.lang,
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.alert('删除成功');
				vm.friendsLinkModalHide('friendsLink-del-modal');
				selectFriendsLlink();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

});