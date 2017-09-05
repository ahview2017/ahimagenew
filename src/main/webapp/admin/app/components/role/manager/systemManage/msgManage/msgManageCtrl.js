/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('msgManageCtrl', function($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove, getsendMsg, $stateParams) {
	var vm = this;

	//初始化相关配置
	function initSetting() {
		//从cookie里取得角色id
		vm.adminRoleId = $cookies.get('admin_roleId');
		if($stateParams.msgType) {
			//从路由里取得消息类型
			vm.acitiveSlideTit = $stateParams.msgType;
		} else {
			//默认激活的导航项为站内信
			vm.acitiveSlideTit = 1;
		}
		//存放站内信数组
		vm.msgMallArray = [];
		//存放电子邮件数组
		vm.msgEmailArray = [];
		//存放网站留言数组
		vm.msgNetInArray = [];
		//存放网站公告数组
		vm.msgNetAdArray = [];
		//存放手机短信数组
		vm.msgPhoneArray = [];
		//站内信总条数
		vm.msgList_total = 0;
		//电子邮件总条数
		vm.msgEailList_total = 0;
		//网站留言总条数
		vm.msgNetList_total = 0;
		//网站公告总条数
		vm.siteNoticeList_total = 0;
		//手机短信总条数
		vm.SMSList_total = 0;
		//默认当前页1
		vm.pagination = {
			current: 1
		};
		//存放群组的数组
		vm.manuscriptCategoryId = [];
		//默认每页10条
		vm.selPageRows = '10';
		//分页默认调用show.do
		vm.searchType = 2;
		vm.searchType_t = 2;
		vm.searchType_th= 2;
		vm.searchType_f = 2;
		vm.searchType_s = 2;

		//站内信搜索输入框默认值
		vm.msgMallSearchModel = '';
		//电子邮件搜索输入框默认值
		vm.msgEmailSearchModel = '';
		//网站留言搜索输入框默认值
		vm.msgNetInSearchModel = '';
		//网站公告搜索输入框默认值
		vm.isMsgNetAdHadAllCheck = '';
		//手机短信搜索输入框默认值
		vm.msgPhoneSearchModel = '';

	}


	//页数变化
	/*vm.pageChanged = function (pageNumber) {
	 switch(parseInt(vm.acitiveSlideTit)){
	 //站内信
	 case 1:
	 getMsgMallTableData(pageNumber, 1);
	 break;
	 //电子邮件
	 case 2:
	 getMsgEmailTableData(pageNumber, 1);
	 break;
	 //网站留言
	 case 3:
	 getMsgNetInTableData(pageNumber, 1);
	 break;
	 //网站公告
	 case 4:
	 getMsgNetAdTableData(pageNumber, 1);
	 break;
	 //手机短信
	 case 5:
	 getMsgPhoneTableData(pageNumber, 1);
	 break;
	 }
	 };*/

	//页数变化
	vm.pageChanged = function(pageNumber) {
		switch(parseInt(vm.acitiveSlideTit)) {
			//站内信
			case 1:
				getMsgMallTableData(pageNumber, 1, vm.searchType);
				break;
			//电子邮件
			case 2:
				getMsgEmailTableData(pageNumber, 1, vm.searchType_t);
				break;
			//网站留言
			case 3:
				getMsgNetInTableData(pageNumber, 1, vm.searchType_th);
				break;
			//网站公告
			case 4:
				getMsgNetAdTableData(pageNumber, 1, vm.searchType_f);
				break;
			//手机短信
			case 5:
				getMsgPhoneTableData(pageNumber, 1, vm.searchType_s);
				break;
		}
	};

	// 实例化编辑器
	function initEdit() {
		var u1 = new UE.ui.Editor({
			//focus时自动清空初始化时的内容
			autoClearinitialContent: true,
			//关闭字数统计
			wordCount: false,
			//关闭elementPath
			elementPathEnabled: false,
			//默认的编辑区域高度
			initialFrameHeight: 300,
			autoHeightEnabled: false,
			autoFloatEnabled: false
		});
		var u2 = new UE.ui.Editor({
			//focus时自动清空初始化时的内容
			autoClearinitialContent: true,
			//关闭字数统计
			wordCount: false,
			//关闭elementPath
			elementPathEnabled: false,
			//默认的编辑区域高度
			initialFrameHeight: 300,
			autoHeightEnabled: false,
			autoFloatEnabled: false
		});
		var u3 = new UE.ui.Editor({
			//focus时自动清空初始化时的内容
			autoClearinitialContent: true,
			//关闭字数统计
			wordCount: false,
			//关闭elementPath
			elementPathEnabled: false,
			//默认的编辑区域高度
			initialFrameHeight: 300,
			autoHeightEnabled: false,
			autoFloatEnabled: false
		});
		var u4 = new UE.ui.Editor({
			//focus时自动清空初始化时的内容
			autoClearinitialContent: true,
			//关闭字数统计
			wordCount: false,
			//关闭elementPath
			elementPathEnabled: false,
			//默认的编辑区域高度
			initialFrameHeight: 300,
			autoHeightEnabled: false,
			autoFloatEnabled: false
		});
		u1.render('msgMallConEditId');
		u2.render('msgEmailConEditId');
		u3.render('msgNetAdConEditId');
		u4.render('msgEditNetAdConEditId');
	}



	//初始化电子邮件分类对象相关配置
	function initAcceptObj(){
		//默认选中的数据
		vm.selReceptObjArr = [];
		//默认显示文本
		vm.defaultReceptObj = {
			buttonDefaultText: '请选择'
		};
		//默认设置
		vm.receptObjSettings = {
			displayProp: 'text',
			idProp: 'id',
			showCheckAll: false,
			showUncheckAll: false,
			scrollable: true,
			smartButtonMaxItems: 2
		};
	}
	//获取电子邮件分类对象数据
	function getAcceptObjData(callback) {
		req.post('groupManagementCtrl/getGroupManagementAll.do', {}).success(function(resp) {
			if(resp.code == '211') {
				vm.selCpCategories = resp.data;
				console.log(resp.data);
				if(callback) callback(resp.data);
				console.log('success');
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	function init() {
		initSetting();
		initEdit();
		initAcceptObj();
		switch(parseInt(vm.acitiveSlideTit)) {
			//站内信
			case 1:
				getMsgMallTableData(1, 1, 2);
				break;
			//电子邮件
			case 2:
				getMsgEmailTableData(1, 1, 0);
				break;
			//网站留言
			case 3:
				getMsgNetInTableData(1, 1, 0);
				break;
			//网站公告
			case 4:
				getMsgNetAdTableData(1, 1, 0);
				break;
			//手机短信
			case 5:
				getMsgPhoneTableData(1, 1, 0);
				break;
		}
		/*getMsgMallTableData(1, 1, 2);
		getMsgEmailTableData(1, 1, 0);
		getMsgNetInTableData(1, 1, 0);
		getMsgNetAdTableData(1, 1, 0);
		getMsgPhoneTableData(1, 1, 0);*/

		getAcceptObjData(function(category) {
			angular.forEach(category, function(item, index) {
				item.text = item.groupName + "(" + item.count + "人" + ")";
			});
		});

	}

	init();

	//消息模态框隐藏
	vm.adminMsgModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	};
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

	//消息模态框显示
	vm.msgModalShow = function(modalId) {
		$('#' + modalId).show();
		maskShow();
	};

	//新建电子邮件模态框显示
	vm.newMailModalShow = function(modalId){
		$('#' + modalId).show();
		maskShow();
		//新建清空群组数组
		vm.manuscriptCategoryId = [];

		vm.isOnSendEmailClickFlag = false;
	}

	//消息模态框隐藏
	vm.msgModalHide = function(modalId) {
		$('#' + modalId).hide();
		maskHide();
	};
	//选择激活的导航项
	vm.chooseManuscriptType = function(acitiveSlideTit) {
		vm.acitiveSlideTit = acitiveSlideTit;
		//默认当前页1
		vm.pagination.current = 1;
		//站内信总条数
		vm.msgList_total = 0;
		//电子邮件总条数
		vm.msgEailList_total = 0;
		//网站留言总条数
		vm.msgNetList_total = 0;
		//网站公告总条数
		vm.siteNoticeList_total = 0;
		//手机短信总条数
		vm.SMSList_total = 0;

		switch(vm.acitiveSlideTit){
			case 1:
				getMsgMallTableData(1, 1);
				break;
			case 2:
				getMsgEmailTableData(1, 1);
				break;
			case 3:
				getMsgNetInTableData(1, 1);
				break;
			case 4:
				getMsgNetAdTableData(1, 1);
				break;
			case 5:
				getMsgPhoneTableData(1, 1);
				break;
		}
	};
	//电子邮件
	vm.MsgEmailTableData = function(acitiveSlideTit) {
		getMsgEmailTableData(1, 1, 0);
		vm.acitiveSlideTit = acitiveSlideTit;
		//默认当前页1
		vm.pagination.current = 1;
	}
	//站内信
	vm.MsgMallTableData = function (acitiveSlideTit){
		getMsgMallTableData(1, 1, 2);
		vm.acitiveSlideTit = acitiveSlideTit;
		//默认当前页1
		vm.pagination.current = 1;
	}
	//网站留言
	vm.MsgNetInTableData = function(acitiveSlideTit) {
		getMsgNetInTableData(1, 1, 0);
		vm.acitiveSlideTit = acitiveSlideTit;
		//默认当前页1
		vm.pagination.current = 1;

	}
	//网站公告
	vm.MsgNetAdTableData = function(acitiveSlideTit) {
		getMsgNetAdTableData(1, 1, 0);
		vm.acitiveSlideTit = acitiveSlideTit;
		//默认当前页1
		vm.pagination.current = 1;

	}
	//手机短信
	vm.MsgPhoneTableData = function(acitiveSlideTit) {
		getMsgPhoneTableData(1, 1, 0);
		vm.acitiveSlideTit = acitiveSlideTit;
		//默认当前页1
		vm.pagination.current = 1;
	}
	/**
	 * 关闭模态框
	 * @param type 1:站内信，2:电子邮件，3:网站留言，4:网站公告，5:手机短信
	 */
	vm.onCloseCurrentModalClick = function(type) {
		switch(type) {
			case 1:
				vm.msgMallReModel = "";
				vm.msgMallTitleModel = "";
				UE.getEditor('msgMallConEditId').setContent("", false);
				vm.msgModalHide('msg-letter-add-modal');
				vm.msgModalHide('msg-letter-sele-modal');
				break;
			case 2:
				vm.msgEmailTitleModel = "";
				//vm.msgEmailReModel = "";
				vm.selReceptObjArr = [];
				UE.getEditor('msgEmailConEditId').setContent("", false);
				vm.msgModalHide('msg-mail-add-modal');
				vm.msgModalHide('msg-mail-sele-modal');
				break;
			case 3:
				vm.msgEditNetAdTitleModel = "";
				vm.msgEditNetAdPbModel = "";
				vm.msgEditNetAdTipModel="";
				UE.getEditor('msgEditNetAdConEditId').setContent("", false);
				vm.msgModalHide('site-notice-edit-modal');
				vm.msgModalHide('msg-leaveword-sele-modal');
				break;
			case 4:
				vm.msgNetAdTitleModel = "";
				vm.msgNetAdPbModel = "";
				vm.msgNetAdTipModel = "";
				UE.getEditor('msgNetAdConEditId').setContent("", false);
				vm.msgModalHide('site-notice-add-modal');
				vm.msgModalHide('msg-notice-sele-modal');
				break;
			case 5:
				vm.msgPhoneReModel = "";
				vm.msgPhoneConModel = "";
				vm.msgModalHide('short-msg-add-modal');
				vm.msgModalHide('msg-msg-sele-modal');
				break;
		}
	};

	/* ------------------站内信开始------------------------- */
	/**
	 * 消息管理-站内信表格数据
	 */
	function getMsgMallTableData(page, type, isSearch) {
		var searchUrl = "";
		var paramsObj = {
			page: page,
			rows: vm.selPageRows,
			msgTitle: vm.msgTitle,
			sendName: vm.sendName,
			msgStatus: vm.msgStatus,
			startTime: vm.startTime,
			endTime: vm.endTime
		};
		if(vm.msgMallSearchModel && isSearch == 0) {
			searchUrl = "station/serachStationMSG.do";
			paramsObj['sendName'] = vm.msgMallSearchModel;
			vm.searchType=0;
		} else if(isSearch == 1) {
			searchUrl = "station/advancedSearch.do";
			vm.searchType=1;
		} else {
			searchUrl = "station/show.do";
			vm.searchType=2;
		}
		req.post(searchUrl, paramsObj).success(function(resp) {
			if(resp.code == '211') {
				vm.msgMallArray = resp.data;
				vm.msgTotalPages = resp.page;
				vm.msgList_total = resp.other;
			}else if(resp.msg != '未登录'){
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 处理全选
	 */
	vm.checkBoxMsgMallArray = [];
	vm.isMsgMallHadAllCheck = false;
	vm.onMsgMallCheckAllClick = function() {
		if(vm.isMsgMallHadAllCheck) {
			vm.checkBoxMsgMallArray = vm.msgMallArray.map(function(item) {
				return item.id
			});
		} else {
			vm.checkBoxMsgMallArray = vm.msgMallArray.map(function(item) {
				return false
			});
		}
	};

	/**
	 * 监听每一个选项的改变
	 */
	$scope.$watchCollection('msgManage.checkBoxMsgMallArray', function(newC) {
		if(newC.every(function(item) {
				return item != false;
			})) {
			vm.isMsgMallHadAllCheck = true;
		} else {
			vm.isMsgMallHadAllCheck = false;
		}
	});

	/**
	 * 点击搜索
	 */
	vm.onSearchMsgMallDataClick = function() {
		//搜索之前把当前页重置为1
		vm.pagination.current = 1;
		getMsgMallTableData(1, 1, 0);
	};
	/*
	 * 站内信高级检索
	 */
	vm.userAdvanceSearch = function() {
		//搜索之前把当前页重置为1
		vm.pagination.current = 1;
		getMsgMallTableData(1, 1, 1);
		$('#msg-letter-sele-modal').hide();
		vm.msgTitle = '';
		vm.sendName = '';
		vm.msgStatus = '';
		vm.startTime = '';
		vm.endTime = '';
	};
	/**
	 * 回车搜索
	 */
	vm.onEnterSearchMsgMallClick = function(e) {
		var keyCode = window.event ? e.keyCode : e.which;
		if(keyCode == 13) {
			vm.onSearchMsgMallDataClick();
		}
	};

	/**
	 * 点击删除
	 */
	vm.deleteMsgMallParamsId = "";
	vm.onShowDeleteMsgMallModelClick = function(deleteType, mineDeleteId) {
		if(deleteType == -1) {
			vm.deleteMsgMallParamsId = mineDeleteId;
			vm.msgModalShow('msg-letter-del-modal');
		} else {
			var paramsId = "";
			for(var c = 0; c < vm.checkBoxMsgMallArray.length; c++) {
				var checkBoxItem = vm.checkBoxMsgMallArray[c];
				if(checkBoxItem != false) {
					paramsId += checkBoxItem + ",";
				}
			}
			if(paramsId != "") {
				vm.deleteMsgMallParamsId = paramsId.substr(0, paramsId.length - 1);
				vm.msgModalShow('msg-letter-del-modal');
			} else {
				layer.alert("请选择要删除的站内信");
			}
		}
	};

	/**
	 * 删除消息管理-站内信表格数据
	 */
	vm.onDeleteMsgMallData = function() {
		req.post('station/delete.do', {
			id: vm.deleteMsgMallParamsId
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.msgModalHide('msg-letter-del-modal');
				layer.alert('删除成功');
				getMsgMallTableData(1, 1,2);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 添加消息管理-站内信表格数据
	 */
	vm.onAddMsgMallClick = function() {
		var msgMallContent = UE.getEditor('msgMallConEditId').getContent();
		if(vm.msgMallTitleModel && vm.msgMallTitleModel.length > 100){
			layer.alert('标题须少于100字');
			return;
		}
		if(msgMallContent && msgMallContent.length > 20000){
			layer.alert('内容须小于20000字');
			return;
		}
		if(!vm.msgMallReModel) {
			layer.msg("请选择接收对象");
			return;
		}
		var msgMallContent = UE.getEditor('msgMallConEditId').getContent();
		req.post('station/send.do', {
			team_id: vm.msgMallReModel,
			msgTitle: vm.msgMallTitleModel,
			msgContent: msgMallContent
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.onCloseCurrentModalClick(1);
				layer.alert('添加站内信成功');
				getMsgMallTableData(1, 1,2);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 设置已读
	 */
	vm.onSetMsgMallClick = function(setType, mineSetId, isReadFlag) {
		if(setType == -1) {
			if(isReadFlag == 0) {
				return;
			}
			reqSetMsgMallFun(mineSetId);
		} else {
			var setId = "";
			for(var c = 0; c < vm.checkBoxMsgMallArray.length; c++) {
				var checkBoxItem = vm.checkBoxMsgMallArray[c];
				if(checkBoxItem != false) {
					setId += checkBoxItem + ",";
				}
			}
			if(setId != "") {
				var setIdSelectStr = setId.substr(0, setId.length - 1);
				reqSetMsgMallFun(setIdSelectStr);
			} else {
				layer.alert("请选择要设置已读的站内信");
			}
		}
	};
	//获取站内信消息未读数量
	function req_getSendMsg() {
		getsendMsg.req_getSendMsg(function(resp) {
			console.log(resp[0].unRead);
			$rootScope.unreadMsgNum = resp[0].unRead;
		});
	}

	/**
	 * 请求设置已读
	 */
	function reqSetMsgMallFun(setId, ifNeedHintFlag) {
		req.post('station/changeStatu.do', {
			id: setId
		}).success(function(resp) {
			if(resp.code == '211') {
				if(!ifNeedHintFlag) {
					layer.msg(resp.msg);
				}
				getMsgMallTableData(vm.pagination.current,  1, vm.searchType);
				req_getSendMsg();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 关闭详情展示页
	 * notNeedHintFlag: 判断是否用弹出操作成功的提示的标识，当查看具体的消息内容时，关闭消息弹框不提示设为已读成功
	 */
	vm.onCloseMsgDetailClick = function(type, notNeedHintFlag) {
		vm.msgModalHide('msgDetailContentModalId');
		if(type == 0 && vm.msgDetailIsReadFlag == 1) {
			reqSetMsgMallFun(vm.msDetailMallId, notNeedHintFlag);
		}
	};

	/* ------------------站内信结束------------------------- */

	/* ------------------电子邮件开始------------------------- */
	/**
	 * 消息管理-电子邮件表格数据
	 */
	function getMsgEmailTableData(page, type, isSearch) {
		var searchUrl = "";
		var paramsObj = {
			page: page,
			rows: vm.selPageRows
		};
		if(vm.msgEmailSearchModel && isSearch == 0) {
			console.log('电子邮件的搜索');
			searchUrl = "mail/searchEmail.do";
			paramsObj['title'] = vm.msgEmailSearchModel;
			vm.searchType_t=0;
		} else if(isSearch == 1) {
			console.log('电子邮件的高级搜索');
			searchUrl = "mail/searchEmail.do";
			vm.searchType_t=1;
			paramsObj['title'] = vm.title;
			paramsObj['timeFrom'] = vm.timeFrom;
			paramsObj['timeTo'] = vm.timeTo;
			paramsObj['reciver'] = vm.reciver;
		} else {
			searchUrl = "mail/showEmail.do";
			vm.searchType_t=2;
		}
		req.post(searchUrl, paramsObj).success(function(resp) {
			if(resp.code == '211') {
				vm.msgEmailArray = resp.data;
				vm.emailTotalPages = resp.page;
				vm.msgEailList_total = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 处理全选
	 */
	vm.checkBoxMsgEmailArray = [];
	vm.isMsgEmailHadAllCheck = false;
	vm.onMsgEmailCheckAllClick = function() {
		if(vm.isMsgEmailHadAllCheck) {
			vm.checkBoxMsgEmailArray = vm.msgEmailArray.map(function(item) {
				return item.id
			});
		} else {
			vm.checkBoxMsgEmailArray = vm.msgEmailArray.map(function(item) {
				return false
			});
		}
	};

	/**
	 * 监听每一个选项的改变
	 */
	$scope.$watchCollection('msgManage.checkBoxMsgEmailArray', function(newC) {
		if(newC.every(function(item) {
				return item != false;
			})) {
			vm.isMsgEmailHadAllCheck = true;
		} else {
			vm.isMsgEmailHadAllCheck = false;
		}
	});

	/**
	 * 点击搜索
	 */
	vm.onSearchMsgEmailDataClick = function() {
		//getMsgEmailTableData(true, vm.msgEmailSearchModel, 1, 1,0);
		getMsgEmailTableData(1, 1, 0);
	};
	/*
	 * 电子邮件的高级检索
	 */
	vm.emailAdvanceSearch = function() {
		if(!vm.timeFrom && !vm.timeTo) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgEmailTableData(1, 1, 1);
			$('#msg-mail-sele-modal').hide();
			vm.title = '';
			vm.reciver = '';
			vm.timeFrom = '';
			vm.timeTo = '';
		} else if(vm.timeFrom && vm.timeTo) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgEmailTableData(1, 1, 1);
			$('#msg-mail-sele-modal').hide();
			vm.title = '';
			vm.reciver = '';
			vm.timeFrom = '';
			vm.timeTo = '';
		} else {
			layer.alert("请输入完整的时间区间作为检索条件");
		}
	}
	/**
	 * 回车搜索
	 */
	vm.onEnterSearchMsgEmailClick = function(e) {
		var keyCode = window.event ? e.keyCode : e.which;
		if(keyCode == 13) {
			vm.onSearchMsgEmailDataClick();
		}
	};

	/**
	 * 点击删除
	 */
	vm.deleteMsgEmailParamsId = "";
	vm.onShowDeleteMsgEmailModelClick = function(deleteType, mineDeleteId) {
		if(deleteType == -1) {
			vm.deleteMsgEmailParamsId = mineDeleteId;
			vm.msgModalShow('msg-mail-del-modal');
		} else {
			var paramsId = "";
			for(var c = 0; c < vm.checkBoxMsgEmailArray.length; c++) {
				var checkBoxItem = vm.checkBoxMsgEmailArray[c];
				if(checkBoxItem != false) {
					paramsId += checkBoxItem + ",";
				}
			}
			if(paramsId != "") {
				vm.deleteMsgEmailParamsId = paramsId.substr(0, paramsId.length - 1);
				vm.msgModalShow('msg-mail-del-modal');
			} else {
				layer.alert("请选择要删除的电子邮件");
			}
		}
	};

	/**
	 * 删除消息管理-电子邮件表格数据
	 */
	vm.onDeleteMsgEmailData = function() {
		req.post('mail/deleteEmail.do', {
			id: vm.deleteMsgEmailParamsId
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.msgModalHide('msg-mail-del-modal');
				layer.alert('删除成功');
				//getMsgEmailTableData(false, "", 1, 1,0);
				getMsgEmailTableData(1, 1, 0);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};
   //获取接受对象参数
	function getAcceptObjParams(){
		var ParamArr = [];
		for(var i = 0,len = vm.selReceptObjArr.length; i < len; i++){
			for(var attr in vm.selReceptObjArr[i]){
				ParamArr.push(vm.selReceptObjArr[i][attr]);
			}
		}
		return ParamArr.toString();
	}
	/**
	 * 添加消息管理-电子邮件表格数据
	 */
	vm.isOnSendEmailClickFlag = false;
	vm.onAddMsgEmailClick = function(status) {
		console.log(getAcceptObjParams());
		if (!vm.msgEmailTitleModel) {
			layer.msg("请选择邮件标题");
			return;
		}
		if (!getAcceptObjParams()) {
			layer.msg("请选择接收对象");
			return;
		}
		var msgEmailContent = UE.getEditor('msgEmailConEditId').getContent();
		if(!msgEmailContent){
			layer.msg('电子邮件内容不可为空');
			return;
		}
		if(msgEmailContent && msgEmailContent.length > 2000){
			layer.msg('电子邮件字数超出限制');
			return;
		}
		vm.isOnSendEmailClickFlag = true;
		req.post('mail/sendEmail.do', {
			title: vm.msgEmailTitleModel,
			teamIds: getAcceptObjParams(),
			content: msgEmailContent,
			status: status
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.onCloseCurrentModalClick(2);
				layer.alert('操作电子邮件成功');
				//getMsgEmailTableData(false, "", 1, 1,0);
				getMsgEmailTableData(1, 1, 0);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/* ------------------电子邮件结束------------------------- */

	/* ------------------网站留言开始------------------------- */
	/**
	 * 消息管理-网站留言表格数据
	 * searchType:是否是要搜索 true:是，false:否
	 * page:当前的页数
	 * type：
	 * issearch:0：搜索，1：高级检索
	 */
	function getMsgNetInTableData(page, type, issearch) {
		var searchUrl = "";
		var paramsObj = {
			page: page,
			rows: vm.selPageRows
		};
		if(vm.msgNetInSearchModel && issearch == 0) {
			console.log('网站留言的搜索');
			searchUrl = "leavingmsg/serchLeavingmsg.do";
			paramsObj['content'] = vm.msgNetInSearchModel;
			vm.searchType_th=0;
		} else if(issearch == 1) {
			console.log('网站留言的高级检索');
			searchUrl = "leavingmsg/serchLeavingmsg.do";
			paramsObj['content'] = vm.content;
			paramsObj['smLink'] = vm.smLink;
			paramsObj['timefrom'] = vm.timefrom;
			paramsObj['timeto'] = vm.timeto;
			vm.searchType_th=1;
		} else {
			searchUrl = "leavingmsg/show.do";
			vm.searchType_th=2;
		}
		req.post(searchUrl, paramsObj).success(function(resp) {
			if(resp.code == '211') {
				vm.msgNetInArray = resp.data;
				vm.msgNetTotalPages = resp.page;
				vm.msgNetList_total = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 处理全选
	 */
	vm.checkBoxMsgNetInArray = [];
	vm.isMsgNetInHadAllCheck = false;
	vm.onMsgNetInCheckAllClick = function() {
		if(vm.isMsgNetInHadAllCheck) {
			vm.checkBoxMsgNetInArray = vm.msgNetInArray.map(function(item) {
				return item.id
			});
		} else {
			vm.checkBoxMsgNetInArray = vm.msgNetInArray.map(function(item) {
				return false
			});
		}
	};

	/**
	 * 监听每一个选项的改变
	 */
	$scope.$watchCollection('msgManage.checkBoxMsgNetInArray', function(newC) {
		if(newC.every(function(item) {
				return item != false;
			})) {
			vm.isMsgNetInHadAllCheck = true;
		} else {
			vm.isMsgNetInHadAllCheck = false;
		}
	});

	/**
	 * 点击搜索
	 */
	vm.onSearchMsgNetInDataClick = function() {
		getMsgNetInTableData(1, 1, 0);
	};
	/*
	 * 网站留言高级检索
	 */
	vm.leavewordAdvanceSearch = function() {
		if(!vm.timefrom && !vm.timeto) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgNetInTableData(1, 1, 1);
			$('#msg-leaveword-sele-modal').hide();
			vm.content = '';
			vm.smLink = '';
			vm.timefrom = '';
			vm.timeto = '';
		} else if(vm.timefrom && vm.timeto) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgNetInTableData(1, 1, 1);
			$('#msg-leaveword-sele-modal').hide();
			vm.content = '';
			vm.smLink = '';
			vm.timefrom = '';
			vm.timeto = '';
		} else {
			layer.alert("请输入完整的时间区间作为检索条件");
		}
	};
	/**
	 * 回车搜索
	 */
	vm.onEnterSearchMsgNetInClick = function(e) {
		var keyCode = window.event ? e.keyCode : e.which;
		if(keyCode == 13) {
			vm.onSearchMsgNetInDataClick();
		}
	};

	/**
	 * 点击删除
	 */
	vm.deleteMsgNetInParamsId = "";
	vm.onShowDeleteMsgNetInModelClick = function(deleteType, mineDeleteId) {
		if(deleteType == -1) {
			vm.deleteMsgNetInParamsId = mineDeleteId;
			vm.msgModalShow('msg-leaveword-del-modal');
		} else {
			var paramsId = "";
			for(var c = 0; c < vm.checkBoxMsgNetInArray.length; c++) {
				var checkBoxItem = vm.checkBoxMsgNetInArray[c];
				if(checkBoxItem != false) {
					paramsId += checkBoxItem + ",";
				}
			}
			if(paramsId != "") {
				vm.deleteMsgNetInParamsId = paramsId.substr(0, paramsId.length - 1);
				vm.msgModalShow('msg-leaveword-del-modal');
			} else {
				layer.alert("请选择要删除的网站留言");
			}
		}
	};

	/**
	 * 删除消息管理-网站留言表格数据
	 */
	vm.onDeleteMsgNetInData = function() {
		req.post('leavingmsg/delete.do', {
			id: vm.deleteMsgNetInParamsId
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.msgModalHide('msg-leaveword-del-modal');
				layer.alert('删除成功');
				//getMsgNetInTableData(false, 1, 1,0);
				getMsgNetInTableData(1, 1, 0);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 添加消息管理-网站留言表格数据
	 */
	//vm.onAddMsgNetInClick = function () {
	//    req.post('leavingmsg/add.do', {
	//        title: vm.msgNetInReModel,
	//        rec: vm.msgNetInConModel,
	//        container: vm.msgNetInConModel
	//    }).success(function (resp) {
	//        if (resp.code == '211') {
	//            vm.msgNetInReModel = "";
	//            vm.msgNetInConModel = "";
	//            vm.msgNetInConModel = "";
	//            vm.msgModalHide('msg-leaveword-add-modal');
	//            layer.alert('添加网站留言成功');
	//            getMsgNetInTableData(false,1, 1);
	//        } else {
	//           layer.alert(resp.msg);
	//        }
	//    });
	//};

	/* ------------------网站留言结束------------------------- */

	/* ------------------网站公告开始------------------------- */
	/**
	 * 消息管理-网站公告表格数据
	 */
	function getMsgNetAdTableData(page, type, isSearch) {
		var searchUrl = "";
		var paramsObj = {
			page: page,
			rows: vm.selPageRows,
			langType: window.localStorage.lang
		};
		if(vm.msgNetAdSearchModel && isSearch == 0) {
			console.log('网站公告的搜索');
			searchUrl = "notice/search.do";
			vm.searchType_f=0;
			paramsObj = {
				searchName: vm.msgNetAdSearchModel,
				langType: window.localStorage.lang
			}
		} else if(isSearch == 1) {
			console.log('网站公告的高级检索');
			searchUrl = "notice/search.do";
			vm.searchType_f=1;
			paramsObj = {
				page: page,
				rows: vm.selPageRows,
				langType: window.localStorage.lang,
				searchName: vm.searchName,
				creater: vm.creater,
				status: vm.status,
				timeFrom: vm.timeFrom,
				timeTo: vm.timeTo
			}
		} else {
			searchUrl = "notice/show.do";
			vm.searchType_f=2;
		}
		req.post(searchUrl, paramsObj).success(function(resp) {
			if(resp.code == '211') {
				vm.msgNetAdArray = resp.data;
				vm.siteNoticeTotalPages = resp.page;
				vm.siteNoticeList_total = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 处理全选
	 */
	vm.checkBoxMsgNetAdArray = [];
	vm.isMsgNetAdHadAllCheck = false;
	vm.onMsgNetAdCheckAllClick = function() {
		if(vm.isMsgNetAdHadAllCheck) {
			vm.checkBoxMsgNetAdArray = vm.msgNetAdArray.map(function(item) {
				return item.id
			});
		} else {
			vm.checkBoxMsgNetAdArray = vm.msgNetAdArray.map(function(item) {
				return false
			});
		}
	};

	/**
	 * 监听每一个选项的改变
	 */
	$scope.$watchCollection('msgManage.checkBoxMsgNetAdArray', function(newC) {
		if(newC.every(function(item) {
				return item != false;
			})) {
			vm.isMsgNetAdHadAllCheck = true;
		} else {
			vm.isMsgNetAdHadAllCheck = false;
		}
	});

	/**
	 * 点击搜索
	 */
	vm.onSearchMsgNetAdDataClick = function() {
		//getMsgNetAdTableData(true, vm.msgNetAdSearchModel, 1, 1);
		getMsgNetAdTableData(1, 1, 0);
	};
	/*
	 * 网站公告高级检索
	 */
	vm.noticeAdvanceSearch = function() {
		if(!vm.timeFrom && !vm.timeTo) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgNetAdTableData(1, 1, 1);
			$('#msg-notice-sele-modal').hide();
			vm.searchName = '';
			vm.creater = '';
			vm.status = '';
			vm.timeFrom = '';
			vm.timeTo = '';
		} else if(vm.timeFrom && vm.timeTo) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgNetAdTableData(1, 1, 1);
			$('#msg-notice-sele-modal').hide();
			vm.searchName = '';
			vm.creater = '';
			vm.status = '';
			vm.timeFrom = '';
			vm.timeTo = '';
		} else {
			layer.alert("请输入完整的时间区间作为检索条件");
		}
	};
	/**
	 * 回车搜索
	 */
	vm.onEnterSearchMsgNetAdClick = function(e) {
		var keyCode = window.event ? e.keyCode : e.which;
		if(keyCode == 13) {
			vm.onSearchMsgNetAdDataClick();
		}
	};

	/**
	 * 点击删除
	 */
	vm.deleteMsgNetAdParamsId = "";
	vm.onShowDeleteMsgNetAdModelClick = function(deleteType, mineDeleteId) {
		if(deleteType == -1) {
			vm.deleteMsgNetAdParamsId = mineDeleteId;
			vm.msgModalShow('site-notice-del-modal');
		} else {
			var paramsId = "";
			for(var c = 0; c < vm.checkBoxMsgNetAdArray.length; c++) {
				var checkBoxItem = vm.checkBoxMsgNetAdArray[c];
				if(checkBoxItem != false) {
					paramsId += checkBoxItem + ",";
				}
			}
			if(paramsId != "") {
				vm.deleteMsgNetAdParamsId = paramsId.substr(0, paramsId.length - 1);
				vm.msgModalShow('site-notice-del-modal');
			} else {
				layer.alert("请选择要删除的网站公告");
			}
		}
	};

	/**
	 * 删除消息管理-网站公告表格数据
	 */
	vm.onDeleteMsgNetAdData = function() {
		req.post('notice/delete.do', {
			id: vm.deleteMsgNetAdParamsId
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.msgModalHide('site-notice-del-modal');
				layer.alert('删除成功');
				//getMsgNetAdTableData(false, "", 1, 1);
				getMsgNetAdTableData(1, 1, 3);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 添加消息管理-网站公告表格数据
	 */
	vm.onAddMsgNetAdClick = function() {
		var msgNetContent = UE.getEditor('msgNetAdConEditId').getContent();
		req.post('notice/add.do', {
			notice_title: vm.msgNetAdTitleModel,
			notice_content: msgNetContent,
			status: vm.msgNetAdPbModel,
			tipId:vm.msgNetAdTipModel,
			langType: window.localStorage.lang
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.onCloseCurrentModalClick(4);
				layer.alert('添加网站公告成功');
				// getMsgNetAdTableData(false, "", 1, 1);
				getMsgNetAdTableData(1, 1, 3);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 展示编辑框
	 */
	vm.updateMsgAdEditId = "";
	vm.onShowEditMsgNetAdModelClick = function(editType, mineEditId, minePosition) {
		if(editType == -1) {
			vm.updateMsgAdEditId = mineEditId;
			var mineEditItemData = vm.msgNetAdArray[minePosition];
			vm.msgEditNetAdTitleModel = mineEditItemData["noticeTitle"];
			vm.msgEditNetAdPbModel = mineEditItemData["status"];
			vm.msgEditNetAdTipModel = mineEditItemData["tipId"]
			var mineEditDetail = mineEditItemData["noticeContent"];
			if(mineEditDetail != null) {
				UE.getEditor('msgEditNetAdConEditId').setContent(mineEditDetail, false);
			}
			vm.msgModalShow('site-notice-edit-modal');
		} else {
			var editId = "";
			var positionStr = "";
			for(var c = 0; c < vm.checkBoxMsgNetAdArray.length; c++) {
				var checkBoxItem = vm.checkBoxMsgNetAdArray[c];
				if(checkBoxItem != false) {
					editId += checkBoxItem + ",";
					positionStr += c + ",";
				}
			}
			if(editId != "") {
				var editIdArray = editId.split(',');
				if(editIdArray.length > 2) {
					layer.alert("只能选择一个网站公告进行编辑");
				} else {
					vm.updateMsgAdEditId = editIdArray[0];
					var position = parseInt(positionStr.split(",")[0]);
					var editItemData = vm.msgNetAdArray[position];
					vm.msgEditNetAdTitleModel = editItemData["noticeTitle"];
					vm.msgEditNetAdPbModel = editItemData["status"];
					vm.msgEditNetAdTipModel = mineEditItemData["tipId"]
					var itemEditContent = editItemData["noticeContent"];
					if(itemEditContent != null) {
						UE.getEditor('msgEditNetAdConEditId').setContent(itemEditContent, false);
					}
					vm.msgModalShow('site-notice-edit-modal');
				}
			} else {
				layer.alert("请选择要编辑的网站公告");
			}
		}
	};

	/**
	 * 编辑网站公告
	 */
	vm.onEditMsgNetAdData = function() {
		var mineEditContent = UE.getEditor('msgEditNetAdConEditId').getContent();
		req.post('notice/edit.do', {
			id: vm.updateMsgAdEditId,
			notice_title: vm.msgEditNetAdTitleModel,
			status: vm.msgEditNetAdPbModel,
			tipId:vm.msgEditNetAdTipModel,
			notice_content: mineEditContent
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.msgModalHide('site-notice-edit-modal');
				layer.msg('编辑网站公告成功');
				vm.msgEditNetAdTitleModel = "";
				vm.msgEditNetAdPbModel = "";
				vm.msgEditNetAdTipModel = "";
				UE.getEditor('msgEditNetAdConEditId').setContent("", false);
				//getMsgNetAdTableData(true, vm.msgNetAdSearchModel, 1, 1);
				getMsgNetAdTableData(1, 1, 3);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/* ------------------网站公告结束------------------------- */

	/* ------------------手机短信开始------------------------- */
	/**
	 * 消息管理-手机短信表格数据
	 */
	function getMsgPhoneTableData(page, type, isSearch) {
		var searchUrl = "";
		var paramsObj = {
			page: page,
			rows: vm.selPageRows
		};
		if(vm.msgPhoneSearchModel && isSearch == 0) {
			searchUrl = "phonemsg/searchPhoneMSG.do";
			paramsObj['search'] = vm.msgPhoneSearchModel;
			vm.searchType_s=0;
		} else if(isSearch == 1) {
			searchUrl = "phonemsg/searchPhoneMSG.do";
			vm.searchType_s=1;
			paramsObj = {
				page: page,
				rows: vm.selPageRows,
				search: vm.search,
				sender: vm.sender,
				status: vm.status,
				timeFrom: vm.timeFrom,
				timeTo: vm.timeTo
			};
		} else {
			searchUrl = "phonemsg/showPhoneMSG.do";
			vm.searchType_s=2;
		}
		req.post(searchUrl, paramsObj).success(function(resp) {
			if(resp.code == '211') {
				vm.msgPhoneArray = resp.data;
				vm.SMStotalPages = resp.page;
				vm.SMSList_total = resp.other;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 处理全选
	 */
	vm.checkBoxMsgPhoneArray = [];
	vm.isMsgPhoneHadAllCheck = false;
	vm.onMsgPhoneCheckAllClick = function() {
		if(vm.isMsgPhoneHadAllCheck) {
			vm.checkBoxMsgPhoneArray = vm.msgPhoneArray.map(function(item) {
				return item.id
			});
		} else {
			vm.checkBoxMsgPhoneArray = vm.msgPhoneArray.map(function(item) {
				return false
			});
		}
	};

	/**
	 * 监听每一个选项的改变
	 */
	$scope.$watchCollection('msgManage.checkBoxMsgPhoneArray', function(newC) {
		if(newC.every(function(item) {
				return item != false;
			})) {
			vm.isMsgPhoneHadAllCheck = true;
		} else {
			vm.isMsgPhoneHadAllCheck = false;
		}
	});

	/**
	 * 点击搜索
	 */
	vm.onSearchMsgPhoneDataClick = function() {
		//getMsgPhoneTableData(true, vm.msgPhoneSearchModel, 1, 1);
		getMsgPhoneTableData(1, 1, 0);
	};
	/*
	 * 手机短信高级检索
	 */
	vm.msgAdvanceSearch = function() {
		if(!vm.timeFrom && !vm.timeTo) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgPhoneTableData(1, 1, 1);
			$('#msg-msg-sele-modal').hide();
			vm.search = '';
			vm.sender = '';
			vm.status = '';
			vm.timeFrom = '';
			vm.timeTo = '';
		} else if(vm.timeFrom && vm.timeTo) {
			//搜索之前把当前页重置为1
			vm.pagination.current = 1;
			getMsgPhoneTableData(1, 1, 1);
			$('#msg-msg-sele-modal').hide();
			vm.search = '';
			vm.sender = '';
			vm.status = '';
			vm.timeFrom = '';
			vm.timeTo = '';
		} else {
			layer.alert("请输入完整的时间区间作为检索条件");
		}
	};
	/**
	 * 回车搜索
	 */
	vm.onEnterSearchMsgPhoneClick = function(e) {
		var keyCode = window.event ? e.keyCode : e.which;
		if(keyCode == 13) {
			vm.onSearchMsgPhoneDataClick();
		}
	};

	/**
	 * 点击删除
	 */
	vm.deleteMsgPhoneParamsId = "";
	vm.onShowDeleteMsgPhoneModelClick = function(deleteType, mineDeleteId) {
		if(deleteType == -1) {
			vm.deleteMsgPhoneParamsId = mineDeleteId;
			vm.msgModalShow('short-msg-del-modal');
		} else {
			var paramsId = "";
			for(var c = 0; c < vm.checkBoxMsgPhoneArray.length; c++) {
				var checkBoxItem = vm.checkBoxMsgPhoneArray[c];
				if(checkBoxItem != false) {
					paramsId += checkBoxItem + ",";
				}
			}
			if(paramsId != "") {
				vm.deleteMsgPhoneParamsId = paramsId.substr(0, paramsId.length - 1);
				vm.msgModalShow('short-msg-del-modal');
			} else {
				layer.alert("请选择要删除的手机短信");
			}
		}
	};

	/**
	 * 删除消息管理-手机短信表格数据
	 */
	vm.onDeleteMsgPhoneData = function() {
		req.post('phonemsg/deletePhoneMSG.do', {
			id: vm.deleteMsgPhoneParamsId
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.msgModalHide('short-msg-del-modal');
				layer.alert('删除成功');
				//getMsgPhoneTableData(false, "", 1, 1);
				getMsgPhoneTableData(1, 1, 3);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 添加消息管理-手机短信表格数据
	 */
	vm.isOnSendPhoneClickFlag = false;
	vm.onAddMsgPhoneClick = function(status) {
		if(!vm.msgPhoneReModel) {
			layer.msg("请选择接收对象");
			return;
		}
		if(!vm.msgPhoneConModel){
			layer.msg('手机短信内容不可为空');
			return;
		}
		if(vm.msgPhoneConModel && vm.msgPhoneConModel.length > 240){
			layer.msg('手机短信字数超出限制');
			return;
		}
		vm.isOnSendPhoneClickFlag = true;
		req.post('phonemsg/sendPhoneMSG.do', {
			teamIds: vm.msgPhoneReModel,
			content: '【中新社】' +vm.msgPhoneConModel,
			status: status
		}).success(function(resp) {
			vm.isOnSendPhoneClickFlag = false;
			if(resp.code == '211') {
				vm.onCloseCurrentModalClick(5);
				layer.alert('操作手机短信成功');
				//getMsgPhoneTableData(false, "", 1, 1);
				getMsgPhoneTableData(1, 1, 3);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 点击显示消息内容弹框
	 */
	vm.msgDetailShowTitle = "";
	vm.msgDetailContent = "";
	vm.msgDetailShowType = 0;
	vm.msgDetailIsReadFlag = 1;
	vm.msDetailMallId = null;
	vm.onShowMsgDetailModalClick = function(detailTitle, detailContent, msgMallId, type, isReadFlag) {
		vm.msgDetailShowTitle = detailTitle;
		vm.msgDetailContent = detailContent;
		vm.msgDetailShowType = type;
		vm.msDetailMallId = msgMallId;
		vm.msgDetailIsReadFlag = isReadFlag;
		vm.msgModalShow('msgDetailContentModalId');
	};

	/* ------------------手机短信结束------------------------- */
});