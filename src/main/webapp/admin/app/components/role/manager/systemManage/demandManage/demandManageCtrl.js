/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('demandManageCtrl', function($scope, $cookies, req, md5, $state, $rootScope, allModalMove, modalOperate) {
	var vm = this;
	//从cookie里取得角色id
	vm.adminRoleId = $cookies.get('admin_roleId');
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

	//需求管理模态框显示
	vm.demandModalShow = function(modalId) {
		$('#' + modalId).show();
		maskShow();
	};
	//需求管理模态框隐藏
	vm.demandModalHide = function(modalId) {
		$('#' + modalId).hide();
		maskHide();
	};

	//审核
	vm.reviewNeeds = function() {
		req_review(function(resp) {
			renderList(2, 1);
			vm.demandModalHide("demand-check-modal");
		})
	};
	vm.showReviewModel = function(word) {
		//resetTempNeedsData();
		vm.reviewId = word.id;
		vm.tempNeedsData.status = word.status;
		vm.tempNeedsData.auditdesc = word.auditdesc;
		req_detail();
		vm.demandModalShow("demand-check-modal");
	};
	//审核状态
	vm.reviewStatus = [
	{
		id: 0,
		name: '未审核'
	}, {
		id: 1,
		name: '审核通过'
	}, {
		id: 2,
		name: '审核未通过'
	}, {
		id: 3,
		name: '保存文档'
	}, {
		id: 4,
		name: '审核通过需求关闭'
	}, {
		id: 5,
		name: '审核未通过需求关闭'
	}
	];
	//修改
	vm.editNeeds = function() {
		req_edit(function(resp) {
			renderList(2, 1);
			vm.demandModalHide("demand-check-modal");
		})
	};

	vm.delNeeds = function() {
		req_delete(function(resp) {
			renderList(2, 1);
			vm.demandModalHide("demand-del-modal");
		})
	};
	vm.showDelModel = function(word) {
		vm.reviewId = word.id;
		vm.demandModalShow("demand-del-modal");
	};

	vm.closeNeeds = function() {
		req_close(function(resp) {
			renderList(2, 1);
			vm.demandModalHide("demand-close-modal");
		})
	};
	vm.showCloseModel = function(word) {
		vm.reviewId = word.id;
		vm.demandModalShow("demand-close-modal");
	};

	//需求详情
	function req_detail() {
		req.post('needs/detail.do', {
			id: vm.reviewId
		}).success(function(resp) {
			if(resp.code && resp.code == 211) {
				//callback(resp.data);
				vm.detailData = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	//修改需求
	function req_edit(callback) {
		req.post('needs/edit.do', {
			id: vm.reviewId,
			status: vm.tempNeedsData.status,
			auditdesc: vm.tempNeedsData.auditdesc
		}).success(function(resp) {
			if(resp.code && resp.code == 211) {
				callback(resp.data);
				//vm.detailData = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	//删除需求
	function req_delete(callback) {
		req.post('needs/delete.do', {
			id: vm.reviewId
		}).success(function(resp) {
			if(resp.code && resp.code == 211) {
				callback(resp.data)
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		})
	}

	//关闭需求
	function req_close(callback) {
		req.post('needs/closeneeds.do', {
			id: vm.reviewId
		}).success(function(resp) {
			if(resp.code && resp.code == 211) {
				callback(resp.data)
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		})
	}

	//需求列表
	/*isSearch说明
	 * 0:搜索
	 * 1：高级检索
	 * 2：展示
	 */
    vm.searchType=2;
	function renderList(isSearch, pageNum) {
		var searchUrl = "";
		var searchObj = {
			page: pageNum,
			rows: vm.selPageRows,
			langType: lang,
			roleId: vm.adminRoleId,
			descs: vm.descs,
			userName: vm.userName,
			pictureUse: vm.pictureUse,
			pStartTime: vm.beginTime,
			pEndTime:vm.pEndTime 
		};
		if(isSearch == 0) {
			searchUrl = "needs/search.do";
			vm.searchType=0;
			searchObj['someThing'] =vm.searchDemandModel;
		} else if(isSearch == 1) {
			searchUrl = 'needs/advancedSearch.do';
			vm.searchType==1;
		} else {
			searchUrl = "needs/show.do";
			vm.searchType==2;
		}

		req.post(searchUrl, searchObj).success(function(resp) {
			if(resp.code && resp.code == 211) {
				vm.listData = resp.data;
				vm.totalPages = resp.page;
				vm.demandList_total = resp.other;
				vm.pagination.current=pageNum;				
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	function resetTempNeedsData() {
		vm.tempNeedsData = {
			status: '',
			auditdesc: ''
		}
		//存放需求数组
		vm.listData = [];
		//需求总条数
		vm.demandList_total = 0;
		//默认当前页1
		vm.pagination = {
			current: 1
		};
		//默认每页10条
		vm.selPageRows = '10';
	}

	function init() {
		renderList(2, 1);
		resetTempNeedsData();
	}

	init();

	//页数变化
	vm.pageChanged = function(pageNumber) {
		renderList(vm.searchType,pageNumber);
	};

	/**
	 * 搜索
	 */
	vm.onSearchDemandClick = function() {
		vm.pagination.current = 1;
		renderList(0,1);
	};

	/**
	 * 回车搜索
	 */
	vm.onEnterSearchClick = function(e) {
		var keyCode = window.event ? e.keyCode : e.which;
		if(keyCode == 13) {
			vm.onSearchDemandClick();
		}
	};
	//高级检索 
	//用户管理添加模态框显示
	vm.userModalShow = function(modalId) {
		vm.descs = '';
		vm.userName = '';
		vm.pictureUse = '';
		vm.beginTime = '';
		vm.pEndTime='';
		modalOperate.modalShow(modalId);
	};
	//资料库模态框隐藏
	vm.databaseModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	};
	
	vm.userAdvanceSearch = function() {
		vm.pagination.current = 1;
		renderList(1, 1);
		$('#user-search-modal').hide();		
	};
});