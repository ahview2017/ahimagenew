/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('userManageCtrl', function($scope, $cookies, req, md5, $state, $rootScope,
	$stateParams, modalOperate, $filter, cityList, $http) {
	var vm = this;

	// 验证手机号码正则
	var regMobile = /^1[3,4,5,7,8]\d{9}$/;

	// 邮箱验证正则
	var regEmail = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;

	//15位和18位身份证号码的正则表达式
	var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;

	// qq号码验证
	var regQQNum = /[1-9][0-9]{4,10}/;

	//验证密码正则
	var pwdExp = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$/;
	//邮政编码正则
	var regZipCode = /^[1-9]\d{5}$/;

	//从cookie获取userToken
	vm.userToken = $cookies.get('userToken');

	//订阅级别
	vm.editSubscriptionType = '';
	//添加的时候清除上次填写的用户数据
	function clearPrevUserData(){
			vm.addUserName = '';
		    vm.addUserLevel = '0';
		    vm.addPassword = '';
		    vm.addSubscriptionType = '';
			vm.addUserStatus = '';
			vm.addSubscriberType = '0';
		    vm.addConfirmPwd = '';
			vm.addlangType = '0';
		    vm.addTrueName = '';
		    vm.addIdCardNum = '';
			vm.addUserStatus = '';
		    vm.addZipCode = '';
		    vm.addUserEmail = '';
		    vm.addWorkName = '';
		    vm.addUnitPath  = '';
		    vm.addInformPath = '';
			vm.addInformPayType = '';
		    vm.addUserPhone = '';
			vm.addDetailPath = '';
			vm.addUnitPhone = '';
			vm.addInformName = '';
		    vm.addMailNum = '';
			vm.addUserProv = '';
		    vm.addUserCity = '';
		    vm.addUnitName = '';
			vm.addUnitFax = '';
			vm.addInformIdCard = '';
			vm.addPwdTipQue = '';
			vm.addPwdTipAns = '';
			vm.addAccountNum = '';
			vm.addAccountName = '';
			vm.addIsPubInform = '';
			vm.addAccountIdCard = '';
			vm.addUserRemark = '';
			vm.addUserQQNum = '';
			vm.addUserWxNum = '';
	}
	//用户管理添加模态框显示
	vm.userAddModalShow = function(modalId) {
		clearPrevUserData();
		modalOperate.modalShow(modalId);
		//摄影师管理时默认用户类型为摄影师，且用户类型不可编辑
		if(vm.reMyRoleId == 2 && vm.addRoleArray) {
			for(var i = 0; i < vm.addRoleArray.length; i++) {
				//默认选中摄影师
				if(vm.addRoleArray[i].ID == 3) {
					var addSelRoleId = {
						id: vm.addRoleArray[i].ID
					};
					vm.addUserTypeArray[i] = addSelRoleId;
				}

				//用户类型不可编辑
				vm.addRoleArray[i].disabled = true;
			}
		} else if(vm.reMyRoleId == 4 && vm.addRoleArray) {
			for(var i = 0; i < vm.addRoleArray.length; i++) {
				//默认选中摄影师
				if(vm.addRoleArray[i].ID == 5) {
					var addSelRoleId = {
						id: vm.addRoleArray[i].ID
					};
					vm.addUserTypeArray[i] = addSelRoleId;
				}

				//用户类型不可编辑
				vm.addRoleArray[i].disabled = true;
			}
		}
	}
	//用户管理模态框显示
	vm.userModalShow = function(modalId) {
		modalOperate.modalShow(modalId);
	};
	//用户管理添加模态框隐藏
	vm.userModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	};
	//用户管理添加模态框隐藏
	vm.userModalH = function(modalId) {
		modalOperate.modalHide(modalId);
	};

	//页面初始化相关配置
	function initSetting() {
		//默认激活的导航项为全部稿件
		vm.acitiveSlideTit = 1;
		//是否允许下载数组
		vm.editDownIsAgreeArr = [{
				name: '允许',
				value: 1
			},
			{
				name: '不允许',
				value: 0
			}
		];
		//下载限制类型
		vm.downLimitTypeData = [{
				name: '每天',
				value: 0
			},
			{
				name: '每月',
				value: 1
			},
			{
				name: '每年',
				value: 2
			},
			{
				name: '总共',
				value: 3
			}
		]

		//省市县联动数据
		vm.msCityList = cityList.citylist;

		//默认订户类型为个人
		vm.addSubscriberType = '0';

		//默认语种为中文
		vm.addlangType = '0';

		//修改保存按钮是否可用标识： false能用，true不可用
		vm.updateBtnIfUseFlag = false;

		//从cookie获取角色id
		vm.reMyRoleId = $cookies.get('admin_roleId');

		//从路由里取得权限ID，区分不同权限：用户管理:206,摄影师：276，订户管理：277
		vm.userRightId = $stateParams.rightId;

		//根据角色id判断显示不同的文字
		if(vm.reMyRoleId == 1) {
			vm.isReMyRoleIdFlag = false;
			vm.reMyRoleName = "用户管理";
		}
		if(vm.reMyRoleId == 2) {
			vm.isReMyRoleIdFlag = true;
			vm.reMyRoleName = "摄影师管理";

		}
		if(vm.reMyRoleId == 4) {
			vm.isReMyRoleIdFlag = true;
			vm.reMyRoleName = "订户管理";
		}
		//检索标示
		vm.searchType = 0;
		//存放用户信息数组
		vm.userManageArray = [];
		//用户信息总条数
		vm.userList_total = 0;
		//默认当前页1
		vm.pagination = {
			current: 1
		};
		//存放role信息
		vm.userRoleList = [];
		//默认每页10条
		vm.selPageRows = '10';
		//检索属性
		vm.searchList = [];
		//检索属性
		vm.search = [];
		//下载限制类型初始值
		vm.editDownLimitType0 = '';
		vm.editDownLimitType1 = '';
		vm.editDownLimitType2 = '';		
		vm.editDownPackBuyNum = 0 ;
		vm.editDownPackBuyNum = 0 ;
	}

	function init() {
		initSetting();
		getDownLevelList();
		getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, vm.pagination.current, "");
		getRoleList();
		getRoleOption();
	}

	init();

	function getRoleOption() {
		req.post("roleCtro/getRoleByQuery.do", {
			userToken: vm.userToken
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.userRoleList = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	/**
	 * 获取下载级别信息表格数据
	 */
	function getDownLevelList() {
		req.post("downloadLevelCtro/getDownLevelList.do", {
			page: '',
			rows: '',
			levelName: '',
			userToken: vm.userToken
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.downloadLevelList = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	//回车确认搜索用户
	vm.enterSearchUser = function(ev) {

		var e = ev || window.event;
		var code = e.keyCode ? e.keyCode : e.which;
		if(code == 13) {
			vm.onSearchDataClick();
		}
	};

	//回车查询登录日志或者操作日志
	vm.enterQueryLog = function(ev) {
		var e = ev || window.event;
		var code = e.keyCode ? e.keyCode : e.which;
		if(code == 13) {
			if(vm.acitiveSlideTit === 5) {
				getLoginLogData(vm.editUserName, vm.loginLogSiteId, vm.loginLogDaysModel, 1, 1);
			}
			if(vm.acitiveSlideTit === 6) {
				getWorkLogData(vm.updateEditId, vm.workLogDaysModel, 1, 1);
			}
		}
	};

	//选择激活的导航项
	vm.chooseUserInfoItem = function(acitiveSlideTit) {
		vm.acitiveSlideTit = acitiveSlideTit;
		if(acitiveSlideTit === 5) {
			vm.loginLogDaysModel = 7;
			getLoginLogData(vm.editUserName, vm.loginLogSiteId, 7, 1, 1);
		}
		if(acitiveSlideTit === 6) {
			vm.workLogDaysModel = 7;
			getWorkLogData(vm.updateEditId, 7, 1, 1);
		}
	};
	/**
	 * 处理全选
	 */
	vm.checkBoxArray = [];
	vm.isHadAllCheck = false;
	vm.onCheckAllClick = function() {
		if(vm.isHadAllCheck) {
			vm.checkBoxArray = vm.userManageArray.map(function(item) {
				return item.ID
			});
		} else {
			vm.checkBoxArray = vm.userManageArray.map(function(item) {
				return false
			});
		}
	};

	/**
	 * 监听每一个选项的改变
	 */
	$scope.$watchCollection('userManage.checkBoxArray', function(newC) {
		if(newC.every(function(item) {
				return item != false;
			})) {
			vm.isHadAllCheck = true;
		} else {
			vm.isHadAllCheck = false;
		}
	});

	/**
	 * 点击删除
	 */
	vm.deleteDataParamsId = "";
	vm.onShowDeleteModelClick = function(deleteType, mineDeleteId) {
		if(deleteType == -1) {
			vm.deleteDataParamsId = mineDeleteId;
			vm.userModalShow('userDelModalId');
		} else {
			var paramsId = "";
			for(var c = 0; c < vm.checkBoxArray.length; c++) {
				var checkBoxItem = vm.checkBoxArray[c];
				if(checkBoxItem != false) {
					paramsId += checkBoxItem + ",";
				}
			}
			if(paramsId != "") {
				vm.deleteDataParamsId = paramsId.substr(0, paramsId.length - 1);
				vm.userModalShow('userDelModalId');
			} else {
				layer.alert("请选择要删除的用户");
			}
		}
	};

	/**
	 * 删除用户
	 */
	vm.onDeleteUserDataClick = function() {
		req.post('userCtro/removeUser.do', {
			userIds: vm.deleteDataParamsId,
			userToken: vm.userToken
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.msg('删除成功');
				vm.userModalHide('userDelModalId');
				getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, 1, "");
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 点击搜索
	 */
	vm.onSearchDataClick = function() {
		//搜索之前把当前页重置为1
		vm.pagination.current = 1;
		getUserManageData(vm.isReMyRoleIdFlag, 2, false, vm.pagination.current, "");
	};
	//确定高级检索
	vm.userAdvanceSearch = function() {
		getUserManageData(vm.isReMyRoleIdFlag, 1, false, 1, "");
	};
	//改变省的时候
	vm.changeProv = function(prov) {
		for(var i = 0; i < vm.msCityList.length; i++) {
			if(prov == vm.msCityList[i].p) {
				vm.cities = vm.msCityList[i].c;
			}
		}
	};
	//资料库模态框隐藏
	vm.databaseModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	};
	//页数变化
	vm.pageChanged = function(pageNumber) {
		getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, pageNumber, "");

	};
	//联系方式切换
	vm.gettelContact = function() {
		if(vm.search.telContact == 0) {
			$('.emailBind').show();
			$('.telBind').hide();
			$('.standBy1').hide();
			$('.standBy2').hide();
		} else if(vm.search.telContact == 1) {
			$('.telBind').show();
			$('.emailBind').hide()
			$('.standBy1').hide();
			$('.standBy2').hide();
		} else if(vm.search.telContact == 2) {
			$('.standBy1').show();
			$('.emailBind').hide();
			$('.telBind').hide();
			$('.standBy2').hide();
		} else if(vm.search.telContact == 3) {
			$('.standBy2').show();
			$('.emailBind').hide();
			$('.telBind').hide();
			$('.standBy1').hide();
		} else {
			$('.emailBind').hide();
			$('.telBind').hide();
			$('.standBy1').hide();
			$('.standBy2').hide();
		}

	}
	//导出全选按钮     
	$(".user_selectall").click(function() {
		var xz = $(this).prop("checked"); //判断全选按钮的选中状态
		$(".user_select").prop("checked", xz); //让class名为qx的选项的选中状态和全选按钮的选中状态一致。
	})
	vm.userexport = function() {
		var exportResult = {};
		$('.user_select:checked').each(function() {
			exportResult[this.value] = '';
		});
		if(exportResult.length == 0) {
			layer.alert("请勾选需要导出的用户信息");
			return;
		}
		var userInfo = angular.toJson(exportResult);
		getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, true, 1, userInfo);
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
	//展开获取更多高级检索详细信息
	$('.user-search-modal-ll').height(370);
	vm.showBasicInfo = false;
	vm.showDetailBasicInfo = function() {
		vm.showBasicInfo = !vm.showBasicInfo;
		if(vm.showBasicInfo) {
			$('.user-search-modal-ll').height(760);
		} else {
			$('.user-search-modal-ll').height(370);
		}
	};

	/**
	 * 检索
	 * @param roleShowFlag 角色信息
	 * @param isSearch 检索类型
	 * @param isDownload 是否下载
	 * @param pageNumber 页数
	 * @param downloadStr 下载字符串
	 */
	function getUserManageData(roleShowFlag, isSearch, isDownload, pageNumber, downloadStr) {
		var searchUrl = "userCtro/getUserByQuery.do";
		var paramsObj = {
			page: pageNumber,
			rows: vm.selPageRows,
			userInfo: downloadStr,
			userToken: vm.userToken
		};
		if(isSearch == 1) {
			paramsObj['userName'] = vm.search.userName;
			paramsObj['userId'] = vm.search.userId;
			paramsObj['roleId'] = vm.search.roleId;
			paramsObj['userStatus'] = vm.search.userStatus;
			paramsObj['telContact'] = vm.search.telContact;
			paramsObj['direction'] = vm.search.direction;
			paramsObj['zone'] = vm.search.zone;
			paramsObj['orderTime'] = vm.search.orderTime;
			paramsObj['province'] = vm.search.selProv;
			paramsObj['city'] = vm.search.selCity;

			paramsObj['trueName'] = vm.search.trueName;
			paramsObj['roleId'] = vm.search.roleId;
			paramsObj['telContact'] = vm.search.telContact;
			paramsObj['emailBind'] = vm.search.emailBind;
			paramsObj['telBind'] = vm.search.telBind;
			paramsObj['standBy1'] = vm.search.standBy1;
			paramsObj['standBy2'] = vm.search.standBy2;
			paramsObj['unitFlax'] = vm.search.unitFlax;
			paramsObj['unitTel'] = vm.search.unitTel;
			paramsObj['authorName'] = vm.search.authorName;
			paramsObj['address'] = vm.search.address;
			paramsObj['unitName'] = vm.search.unitName;
			paramsObj['unitAddress'] = vm.search.unitAddress;
			paramsObj['mailAddress'] = vm.search.mailAddress;
			paramsObj['mailUserName'] = vm.search.mailUserName;
			paramsObj['mailIdCard'] = vm.search.mailIdCard;
			paramsObj['feeType'] = vm.search.feeType;
			paramsObj['mailZipCode'] = vm.search.mailZipCode;
			paramsObj['bankAccount'] = vm.search.bankAccount;
			paramsObj['bankUserName'] = vm.search.bankUserName;
			paramsObj['bankIdCard'] = vm.search.bankIdCard;
			paramsObj['bankName'] = vm.search.bankName;
			paramsObj['memo'] = vm.search.memo;

			vm.searchType = 1;
		} else if(isSearch == 2) {
			paramsObj['parameter'] = vm.userSearchModel;
			vm.searchType = 2;
		}
		if(roleShowFlag) {
			//userRightId为用户权限id,用户管理:206,摄影师：276，订户管理：277
			if(vm.userRightId == 276) {
				//当摄影师管理下搜索的是摄影师
				//paramsObj['roleId'] = 3;
				paramsObj['roleId'] = 98;//add by xiayunan@20171110  摄影师管理下搜索的是签约摄影家
				paramsObj['userStatus'] = 0;//摄影师管理下搜索的为用户状态为已开通的摄影名家和艺术家
				paramsObj['orderTime'] = 2;
			}
			if(vm.userRightId == 277) {
				//当订户管理时搜索的订户
				paramsObj['roleId'] = 5;
			}
		}
		if(isDownload) {
			var time = new Date().format("yyyyMMddhhmmss");
			var fileName = "用户信息_" + time + ".xls"; //文件名
			// searchUrl = "userCtro/downLoadUserInfo.do";
			return $http({
				url: "/photo/userCtro/downLoadUserInfo.do",
				method: "POST",
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8'
				},
				data: $.param(paramsObj),
				responseType: 'arraybuffer'
			}).success(function(data) {
				var blob = new Blob([data], {
					type: "application/vnd.ms-excel"
				});
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
		} else {
			req.post(searchUrl, paramsObj).success(function(resp) {
				if(resp.code == '211') {
					vm.userManageArray = resp.data;
					$rootScope.user_namecolor = vm.userManageArray; //设置摄影师被禁用时样式
					vm.userList_total = resp.other;
					vm.pagination.current = pageNumber;
					vm.totalPages = resp.page;
					if(isSearch == 1) {
						vm.searchType = 1;
						modalOperate.modalHide('user-search-modal');
					} else if(isSearch == 2) {
						vm.searchType = 2;
					} else {
						vm.searchType = 0;
					}
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		}
	}

	/**
	 * 格式化时间
	 * @param format
	 * @returns {*}
	 */
	Date.prototype.format = function(format) {
		var o = {
			"M+": this.getMonth() + 1, //month
			"d+": this.getDate(), //day
			"h+": this.getHours(), //hour
			"m+": this.getMinutes(), //minute
			"s+": this.getSeconds(), //second
			"q+": Math.floor((this.getMonth() + 3) / 3), //quarter
			"S": this.getMilliseconds() //millisecond
		}
		if(/(y+)/.test(format)) format = format.replace(RegExp.$1,
			(this.getFullYear() + "").substr(4 - RegExp.$1.length));
		for(var k in o)
			if(new RegExp("(" + k + ")").test(format))
				format = format.replace(RegExp.$1,
					RegExp.$1.length == 1 ? o[k] :
					("00" + o[k]).substr(("" + o[k]).length));
		return format;
	}
	/**
	 * 获取用户管理表格数据
	 * @param  roleShowFlag
	 * @param  isSearch  是否检索
	 * @param  searchType 检索类型
	 * @param  searchTitle
	 * @param  pageNumber  跳转页
	 * @param  type
	 */
	// function getUserManageData(roleShowFlag, isSearch, searchType, searchTitle, pageNumber, type) {
	//     var paramsObj = {
	//         page: pageNumber,
	//         rows: vm.selPageRows
	//     };
	//     if(isSearch){
	//         getSignGroups(pageNumber,roleShowFlag,searchType);
	//     }else{
	//         if(searchType){
	//             paramsObj['userName'] = vm.searchList.userName;
	//             paramsObj['userId'] = vm.searchList.userId;
	//             paramsObj['roleId'] = vm.searchList.roleId;
	//             paramsObj['userStatus'] = vm.searchList.userStatus;
	//             paramsObj['telContact'] = vm.searchList.telContact;
	//             paramsObj['direction'] = vm.searchList.direction;
	//             paramsObj['zone'] = vm.searchList.zone;
	//             paramsObj['orderTime'] = vm.searchList.orderTime;
	//             paramsObj['province'] = vm.searchList.selProv;
	//             paramsObj['city'] = vm.searchList.selCity;
	//         }else {
	//             if (vm.userSearchModel) {
	//                 paramsObj['parameter'] = vm.userSearchModel;
	//             }
	//         }
	//         if (roleShowFlag) {
	//             //userRightId为用户权限id,用户管理:206,摄影师：276，订户管理：277
	//             if(vm.userRightId == 276){
	//                 //当摄影师管理下搜索的是摄影师
	//                 paramsObj['roleId'] = 3;
	//             }
	//             if(vm.userRightId == 277){
	//                 //当订户管理时搜索的订户
	//                 paramsObj['roleId'] = 5;
	//             }
	//             //paramsObj['roleId'] = vm.reMyRoleId;
	//         }
	//         if (vm.userSearchModel) {
	//             paramsObj['parameter'] = vm.userSearchModel;
	//         }
	//         req.post("userCtro/getUserByQuery.do", paramsObj).success(function (resp) {
	//             if (resp.code == '211') {
	//                 vm.userManageArray = resp.data;
	//                 vm.userList_total = resp.other;
	//                 vm.pagination.current = pageNumber;
	//                 vm.totalPages = resp.page;
	//             }else if(resp.msg != '未登录'){
	//                 layer.alert(resp.msg);
	//             }
	//         });
	//     }
	//
	// }
	// /**
	//  * 检索
	//  * param: 是否为高级检索 true 高级检索 false 检索
	//  */
	// function getSignGroups(pageNumber,roleShowFlag,searchType) {
	//     var params = {
	//         page: pageNumber,
	//         rows: vm.selPageRows
	//     };
	//     if(searchType){
	//             params['userName']=vm.search.userName;
	//             params['userId']=vm.search.userId;
	//             params['roleId']=vm.search.roleId;
	//             params['userStatus']=vm.search.userStatus;
	//             params['telContact']=vm.search.telContact;
	//             params['direction']=vm.search.direction;
	//             params['zone']=vm.search.zone;
	//             params['orderTime']=vm.search.orderTime;
	//             params['province']=vm.search.selProv;
	//             params['city']=vm.search.selCity;
	//             vm.searchType = true;
	//     }else {
	//         if (vm.userSearchModel) {
	//             params['parameter'] = vm.userSearchModel;
	//             vm.searchType = false;
	//         }
	//     }
	//     if (roleShowFlag) {
	//         //userRightId为用户权限id,用户管理:206,摄影师：276，订户管理：277
	//         if(vm.userRightId == 276){
	//             //当摄影师管理下搜索的是摄影师
	//             params['roleId'] = 3;
	//         }
	//         if(vm.userRightId == 277){
	//             //当订户管理时搜索的订户
	//             params['roleId'] = 5;
	//         }
	//         //paramsObj['roleId'] = vm.reMyRoleId;
	//     }
	//
	//     reqUrl = 'userCtro/getUserByQuery.do';
	//     req.post(reqUrl, params).success(function (resp) {
	//         if (resp.code == '211' && resp.data != [] && resp.data.length > 0) {
	//             vm.userManageArray = resp.data;
	//             vm.totalPages = resp.page;
	//             vm.userList_total = resp.other;
	//             vm.pagination.current = pageNumber;
	//             modalOperate.modalHide('user-search-modal');
	//
	//             vm.searchList.userName=vm.search.userName;
	//             vm.searchList.userId=vm.search.userId;
	//             vm.searchList.roleId=vm.search.roleId;
	//             vm.searchList.userStatus=vm.search.userStatus;
	//             vm.searchList.telContact=vm.search.telContact;
	//             vm.searchList.direction=vm.search.direction;
	//             vm.searchList.zone=vm.search.zone;
	//             vm.searchList.orderTime=vm.search.orderTime;
	//             vm.searchList.province=vm.search.selProv;
	//             vm.searchList.city=vm.search.selCity;
	//         } else {
	//             vm.userManageArray = [];
	//             vm.totalPages = resp.page;
	//             vm.userList_total = resp.other;
	//             vm.pagination.current = pageNumber;
	//             modalOperate.modalHide('user-search-modal');
	//         }
	//     });
	// }

	/**
	 * 添加用户
	 */
	//处理多选 - angularjs-dropdown-multiselect.js
	vm.addUserTypeArray = [];
	vm.optionAddSettings = {
		displayProp: 'ROLE_NAME',
		idProp: 'ID',
		showCheckAll: false,
		showUncheckAll: false,
		scrollable: true,
		smartButtonMaxItems: 2
	};
	vm.optionAddDefaultText = {
		buttonDefaultText: '请选择用户类型'
	};
	vm.onAddSelectOptionEvents = {
		'onItemSelect': function(finalObj) {
			if(finalObj.id === 5) {
				vm.addSubLevelFlag = true;
			}
			if(finalObj.id === 3) {
				vm.addphotodirection = true;
			}
		},
		'onItemDeselect': function(finalObj) {
			if(finalObj.id === 5) {
				vm.addSubLevelFlag = false;
			}
			if(finalObj.id === 3) {
				vm.addphotodirection = false;
			}
		}
	};
	vm.adddirectionArray = [];
	vm.optiondirectionSettings = {
		displayProp: 'direction',
		idProp: 'id',
		showCheckAll: false,
		showUncheckAll: false,
		scrollable: true,
		smartButtonMaxItems: 2
	};
	vm.optionDefaultText = {
		buttonDefaultText: '请选择摄影方向'
	};

	vm.optionEditSettings = {
		displayProp: 'ROLE_NAME',
		idProp: 'ID',
		showCheckAll: false,
		showUncheckAll: false,
		scrollable: true,
		smartButtonMaxItems: 2
	};
	vm.optionEditDefaultText = {
		buttonDefaultText: '请选择用户类型'
	};
	
	vm.optioneditdirectionSettings = {
		displayProp: 'direction',
		idProp: 'id',
		showCheckAll: false,
		showUncheckAll: false,
		scrollable: true,
		smartButtonMaxItems: 2
	};
	vm.optioneditDefaultText = {
		buttonDefaultText: '请选择摄影方向'
	};
	
	vm.onEditSelectOptionEvents = {
		'onItemSelect': function(finalObj) {
			if(finalObj.id === 5) {
				vm.editSubLevelFlag = true;
			}
			if(finalObj.id === 3){
				vm.editphotodirection=true;
			}
		},
		'onItemDeselect': function(finalObj) {
			if(finalObj.id === 5) {
				vm.editSubLevelFlag = false;
			}
			if(finalObj.id === 3){
				vm.editphotodirection=false;
			}
		}
	};
	//添加用户
	vm.onAddUserManageClick = function() {
		vm.adddirectionstr = '';
		if(vm.addphotodirection) {			
			vm.selectedresult = '';
			if(vm.adddirectionArray.length > 0) {
				for(var j = 0; j < vm.adddirectionArray.length; j++) {
					vm.selectedresult += vm.adddirectionArray[j].id;
				}
				for(var i = 1; i < 9; i++) {
					if(vm.selectedresult.toString().indexOf(i) >= 0) {
						vm.adddirectionstr += 1;
					} else {
						vm.adddirectionstr += 0;
					};
				}
			}else{
				vm.adddirectionstr='00000000';
			}
		}
		
		if(!vm.addUserName) {
			layer.alert("请输入用户名");
			return;
		}
		vm.addUserTypeStr = "";
		if(vm.addUserTypeArray.length > 0) {
			for(var add = 0; add < vm.addUserTypeArray.length; add++) {

				//vm.addUserTypeStr += (vm.addUserTypeArray[add].id + ",");
			    for(var attr in vm.addUserTypeArray[add]){
						if(attr || attr == 0){
							vm.addUserTypeStr += (vm.addUserTypeArray[add].id + ",");
						}
				}
			}
			vm.addUserTypeStr = vm.addUserTypeStr.substr(0, vm.addUserTypeStr.length - 1);
		} else {
			layer.alert("请选择用户类型");
			return;
		}

		if(!vm.addTrueName) {
			layer.alert("请输入真实姓名");
			return;
		}
		if(!vm.addPassword) {
			layer.alert("请输入密码");
			return;
		}
		if(vm.addPassword && !(pwdExp.test(vm.addPassword))) {
			layer.alert('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
			return;
		}
		if(!vm.addConfirmPwd) {
			layer.alert("请输入确认密码");
			return;
		}
		if(vm.addConfirmPwd != vm.addPassword) {
			layer.alert("两次输入的密码不一致，请重输");
			return;
		}
		//如果订户类型是企业可以不填身份证号
		if(!vm.addIdCardNum && vm.addSubscriberType != 1) {
			layer.alert("请输入用户身份证号");
			return;
		}
		if(vm.addIdCardNum && !(regIdCard.test(vm.addIdCardNum))) {
			layer.alert("用户身份证号输入有误");
			return;
		}
		if(!vm.addUserStatus) {
			layer.alert("请选择用户状态");
			return;
		}
		if(!vm.addZipCode) {
			layer.alert("请输入邮政编码");
			return;
		} else if(!(regZipCode.test(vm.addZipCode))) {
			layer.alert("邮政编码输入有误");
			return;
		}
		if(!vm.addUserEmail) {
			layer.alert("请输入绑定邮箱号");
			return;
		} else if(!(regEmail.test(vm.addUserEmail))) {
			layer.alert("绑定邮箱号输入有误");
			return;
		}
		if(!vm.addUserPhone) {
			layer.alert("请输入绑定手机号");
			return;
		} else if(!(regMobile.test(vm.addUserPhone))) {
			layer.alert("绑定手机号输入有误");
			return;
		}

		if(!vm.addWorkName) {
			layer.alert("请输入作者名");
			return;
		}
		if(vm.addInformIdCard && (!(regIdCard.test(vm.addInformIdCard)))) {
			layer.alert("收稿费人身份证号输入有误");
			return;
		}
		if(vm.addAccountIdCard && (!(regIdCard.test(vm.addAccountIdCard)))) {
			layer.alert("上述卡户名的身份证号输入有误");
			return;
		}
		if(vm.addIsPubInform == "" || !vm.addIsPubInform) {
			layer.alert("请选择是否公开个人信息！");
			return;
		}
		if(vm.addUserQQNum && (!(regQQNum.test(vm.addUserQQNum)))) {
			layer.alert("QQ号码输入有误");
			return;
		}

		req.post('userCtro/addUser.do', {
			userName: vm.addUserName,
			photographyDirection: vm.adddirectionstr,
			roleIds: vm.addUserTypeStr,
			tureName: vm.addTrueName,
			password: md5.createHash(vm.addPassword),
			question: vm.addPwdTipQue,
			answer: vm.addPwdTipAns,
			province: vm.addUserProv,
			city: vm.addUserCity,
			authorName: vm.addWorkName,
			telBind: vm.addUserPhone,
			emailBind: vm.addUserEmail,
			zipcode: vm.addZipCode,
			subscriptionType: vm.addSubscriptionType,
			address: vm.addDetailPath,
			feeType: vm.addInformPayType,
			mailAddress: vm.addInformPath,
			mailUsername: vm.addInformName,
			mailIdCard: vm.addInformIdCard,
			mailZipCode: vm.addMailNum,
			bankAccount: vm.addAccountNum,
			bankUsername: vm.addAccountName,
			bankIdCard: vm.addAccountIdCard,
			bankName: '中国邮政储蓄银行',
			//			bankName: vm.addAccountBank,
			idCard: vm.addIdCardNum,
			userType: vm.addUserLevel,
			subscriberType: vm.addSubscriberType,
			memo: vm.addUserRemark,
			isPublish: vm.addIsPubInform,
			userStatus: vm.addUserStatus,
			unitName: vm.addUnitName,
			unitAddress: vm.addUnitPath,
			unitTel: vm.addUnitPhone,
			unitFax: vm.addUnitFax,
			standby1: vm.addUserQQNum,
			standby2: vm.addUserWxNum,
			langType: vm.addlangType,
			userToken: vm.userToken
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.addUserTypeArray = [];
				vm.adddirectionArray=[];
				//modalOperate.modalHide('user-add-modal');
				layer.alert('添加用户成功');
				getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, 1, "");
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	vm.onCancelAddModalClick = function() {
		vm.addUserTypeArray = [];
		modalOperate.modalHide('user-add-modal');
	};

	/**
	 * 设置用户信息
	 */
	vm.editUserTypeArray = [];
	vm.editdirectionArray=[];
	function setListUserData(editItemData) {
		vm.editUserName = editItemData['USER_NAME'];
		vm.payUserName = vm.editUserName;
		
		var editRoleArray = editItemData['roles'];	
		vm.editUserTypeArray = [];
		
		if(editRoleArray != null && editRoleArray.length > 0) {
			for(var i = 0; i < editRoleArray.length; i++) {
				var roleNeedId = editRoleArray[i]['id'];
				if(parseInt(roleNeedId) === 5) {
					vm.editSubLevelFlag = true;
				}
				if(parseInt(roleNeedId) === 3) {
					vm.editphotodirection = true;
				}
				var editObj = {
					id: roleNeedId
				};
				vm.editUserTypeArray[i] = editObj;
			}
			if(vm.reMyRoleId == 2 || vm.reMyRoleId == 4) {
				for(var i = 0; i < vm.editRoleArray.length; i++) {
					vm.editRoleArray[i].disabled = true;
				}
			}
		} 
		
		var directionArray = editItemData['PHOTOGRAPHY_DIRECTION'];	
		
		var j=0;
		vm.editdirectionArray=[];
        if(directionArray != null && directionArray.length > 0) {
        	vm.editdirectionArray=[];
			for(var i =0; i < directionArray.length; i++) {
				if(parseInt(directionArray[i])==1){
					var roleNeedId =i+1;
					var editObj = {
					  id: roleNeedId
				    };
				    vm.editdirectionArray[j]=editObj;
				    j++;
				}
			}
			
		} 
		vm.payMoney = editItemData['ACCOUNT'];
		vm.editTrueName = editItemData['TURE_NAME'];
		vm.editlangType = '' + editItemData['LANG_TYPE'] + '';
		vm.payLoginName = vm.editTrueName;
		if(editItemData['SUBSCRIBER_TYPE'] || editItemData['SUBSCRIBER_TYPE'] === 0) {
			vm.editSubscriberType = editItemData['SUBSCRIBER_TYPE'] + "";
		} else {
			vm.editSubscriberType = '';
		}
		vm.editPassword = editItemData['PASSWORD'];
		vm.editConfirmPwd = editItemData['PASSWORD'];
		vm.editPwdTipQue = editItemData['QUESTION'];
		vm.editPwdTipAns = editItemData['ANSWER'];
		vm.editUserProv = editItemData['PROVINCE'];
		vm.changeProv(vm.editUserProv);
		vm.editUserCity = editItemData['CITY'];
		vm.editWorkName = editItemData['AUTHOR_NAME'];
		vm.editUserPhone = editItemData['TEL_BIND'];
		vm.editUserEmail = editItemData['EMAIL_BIND'];
		vm.editZipCode = editItemData['ZIPCODE'];
		vm.editDetailPath = editItemData['ADDRESS'];
		vm.editInformPayType = editItemData['FEE_TYPE'] + "";
		vm.editInformPath = editItemData['MAIL_ADDRESS'];
		vm.editInformName = editItemData['MAIL_USERNAME'];
		vm.editInformIdCard = editItemData['MAIL_ID_CARD'];
		vm.editMailNum = editItemData['MAIL_ZIP_CODE'];
		vm.editAccountNum = editItemData['BANK_ACCOUNT'];
		vm.editAccountName = editItemData['BANK_USERNAME'];
		vm.editAccountIdCard = editItemData['BANK_ID_CARD'];
		vm.editAccountBank = editItemData['BANK_NAME'];
		vm.editIdCardNum = editItemData['ID_CARD'];
		vm.editSubscriptionType = (editItemData['SUBSCRIPTION_TYPE']) + '';
		vm.editUserLevel = editItemData['USER_TYPE'] + "";
		vm.editUserRemark = editItemData['MEMO'];
		vm.editIsPubInform = editItemData['IS_PUBLISH'] + "";
		vm.editUserStatus = editItemData['USER_STATUS'] + "";
		vm.editUnitName = editItemData['UNIT_NAME'];
		vm.editUnitPath = editItemData['UNIT_ADDRESS'];
		vm.editUnitPhone = editItemData['UNIT_TEL'];
		vm.editUnitFax = editItemData['UNIT_FAX'];
		vm.editUserQQNum = editItemData['STANDBY1'];
		vm.editUserWxNum = editItemData['STANDBY2'];
		
		//add by xiayunan@20171110 编辑框显示用户描述、栏目ID，排序ID
		vm.editUserDetail = editItemData['USER_DETAIL'];
		vm.editUserColumnId = editItemData['HOMEPAGE_COLUMNID'];
		vm.editUserOrderId = editItemData['USER_ORDER'];
		vm.editUserClass = editItemData['USER_CLASS']+'';

		// 下载管理
		vm.editDownLevelId = editItemData['DOWNLOAD_LEVEL'];
		vm.onSelectDownWayClick(editItemData['DOWNLOAD_TYPE']);
		if((editItemData['IS_DOWNLOAD']) || (editItemData['IS_DOWNLOAD'] == 0)) {
			vm.editDownIsAgree = editItemData['IS_DOWNLOAD'] * 1;
		} else {
			vm.editDownIsAgree = 1;
		}
		// 下载限制类型
		if(vm.editDownWay == 0) {
			vm.editDownLimitType0 = vm.mineEditItemData['BALANCE_LIMIT_TYPE'];
		} else if(vm.editDownWay == 1) {
			vm.editDownLimitType1 = vm.mineEditItemData['CONTRACT_LIMIT_TYPE'];
		} else if(vm.editDownWay == 2) {
			vm.editDownLimitType2 = vm.mineEditItemData['THREE_LIMIT_TYPE'];
		}
		// 该限制内已下载数量
		if(vm.editDownWay == 0) {
			// 充值
			vm.editDownNumShow0 = editItemData['BALANCE_LIMIT_DL_NUM'] || 0;
		} else if(vm.editDownWay == 1) {
			// 张数
			vm.editDownNumShow1 = editItemData['CONTRACT_LIMIT_DL_NUM'] || 0;
		} else if(vm.editDownWay == 2) {
			// 充值与张数
			vm.editDownNumShow2 = editItemData['THREE_LIMIT_DL_NUM'] || 0;
		}
		// 限制数量
		if(vm.editDownWay == 0) {
			// 充值
			vm.editDownLimitNum0 = editItemData['BALANCE_LIMIT_NUM'] || '';
		} else if(vm.editDownWay == 1) {
			// 张数
			vm.editDownLimitNum1 = editItemData['CONTRACT_LIMIT_NUM'] || '';
		} else if(vm.editDownWay == 2) {
			// 充值与张数
			vm.editDownLimitNum2 = editItemData['THREE_LIMIT_NUM'] || '';
		}
		// 充值
		if(vm.editDownWay == 0 || vm.editDownWay == 2) {
			vm.editDownPayTalkPrice = editItemData['BALANCE_PERPRICE'];
		}

		vm.editDownPaySellRate = editItemData['BALANCE_SALE'];
		vm.editDownPayMoneyRate = editItemData['BALANCE_REVENUE'];
		vm.editDownPayBasicPrice = editItemData['BALANCE_BASE_PERPRICE'];
		vm.editDownPayHadDownNum = editItemData['BALANCE_LIMIT_DL_NUM'];
		vm.balanceHadDownNum = editItemData['BALANCE_ALL_NUM'];

		vm.editDownPayMoneyShow = editItemData['ACCOUNT'];
		// 张数
		if(vm.editDownWay == 1 || vm.editDownWay == 2) {
			vm.editDownPackTalkPrice = editItemData['CONTRACT_PERPRICE'];
		}

		vm.editDownPackBasicPrice = editItemData['CONTRACT_BASE_PERPRICE'];
		vm.editDownPackHadNum = editItemData['CONTRACT_NUM'];
		vm.editDownPackBuyNum = editItemData['CONTRACT_BUY_NUM'];
		vm.downPackStartTime = $filter('date')(editItemData['CONTRACT_START_TIME'], 'yyyy-MM-dd');
		vm.downPackEndTime = $filter('date')(editItemData['CONTRACT_END_TIME'], 'yyyy-MM-dd');
		vm.contactHadDownNum = editItemData['CONTRACT_ALL_NUM'];
	}
	//取消
	vm.onCancelEditModalClick = function() {
		modalOperate.modalHide('user-info-modal');		
		vm.editUserTypeArray = [];
		vm.editdirectionArray=[];
		vm.editdirectionstr='';
		vm.updateBtnIfUseFlag = false;
	};

	/**
	 * 展示编辑框
	 */
	vm.updateEditId = "";
	vm.onShowEditModelClick = function(mineEditId, minePosition) {
		vm.updateBtnIfUseFlag = false;
		vm.updateEditId = mineEditId;
		vm.payUserId = mineEditId;
		vm.mineEditItemData = vm.userManageArray[minePosition];
		setListUserData(vm.mineEditItemData);
		vm.loginLogDaysModel = 7;
		vm.workLogDaysModel = 7;
		vm.loginLogSiteId = vm.mineEditItemData['SITE_ID'];
		if(vm.acitiveSlideTit === 5) {
			getLoginLogData(vm.editUserName, vm.loginLogSiteId, 7, 1, 1);
		}
		if(vm.acitiveSlideTit === 6) {
			getWorkLogData(vm.updateEditId, 7, 1, 1);
		}

		//getLoginLogData(mineEditItemData['USER_NAME'], mineEditItemData['SITE_ID'], 7, 1, 1);
		//getWorkLogData(mineEditId, 7, 1, 1);
		modalOperate.modalShow('user-info-modal');
	};

	/**
	 * 展示修改密码框
	 */
	vm.onShowUpdatePwdClick = function(mineEditId, minePosition) {
		vm.updatePwdUserId = mineEditId;
		var minePwdItemData = vm.userManageArray[minePosition];
		vm.updatePwdUserName = minePwdItemData['USER_NAME'];
		vm.updatePwdLoginName = minePwdItemData['TURE_NAME'];
		modalOperate.modalShow('user-pwd-reset-modal');
	};

	/**
	 * 展示解锁框
	 */
	vm.onShowLockClick = function(mineId, mineType) {
		vm.onWorkLockUserId = mineId;
		if(mineType != null || 'undefined' != typeof mineType) {
			//现在需求改为都是解禁了
			if(parseInt(mineType) === 0) {
				vm.onWorkLockType = 0;
				vm.workLockTypeStr = "是否确认解禁？";
			} else {
				vm.onWorkLockType = 1;
				vm.workLockTypeStr = "是否确认解禁？";
			}
		} else {
			vm.onWorkLockType = 0;
			vm.workLockTypeStr = "是否确认解禁？";
		}
		modalOperate.modalShow('user-unlock-modal');
	};

	/**
	 * 解锁
	 */
	vm.onWorkLockClick = function() {
		req.post('userCtro/lockOrOpenUser.do', {
			userId: vm.onWorkLockUserId,
			type: 1,
			userToken: vm.userToken
			//type: vm.onWorkLockType
		}).success(function(resp) {
			if(resp.code == '211') {
				modalOperate.modalHide('user-unlock-modal');
				layer.alert(resp.msg);
				getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, vm.pagination.current, "");
			} else {
				modalOperate.modalHide('user-unlock-modal');
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 修改密码
	 */
	vm.isOnUpPwdClickFlag = false;
	vm.onUpdatePwdClick = function() {
		if(!vm.updateNewPwd) {
			layer.alert("请输入新密码");
			return;
		}
		if(!(pwdExp.test(vm.updateNewPwd))) {
			layer.alert('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
			return;
		}
		if(!vm.updateConNewPwd) {
			layer.alert("请输入确认密码");
			return;
		}
		if(vm.updateNewPwd != vm.updateConNewPwd) {
			layer.alert("两次密码不一致，请重输");
			return;
		}

		vm.isOnUpPwdClickFlag = true;
		req.post('userCtro/updatePassword.do', {
			userId: vm.updatePwdUserId,
			password: md5.createHash(vm.updateNewPwd),
			userToken: vm.userToken
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.isOnUpPwdClickFlag = false;
				modalOperate.modalHide('user-pwd-reset-modal');
				layer.alert('修改密码成功');
			} else {
				vm.isOnUpPwdClickFlag = false;
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 编辑用户
	 */
	
	vm.onEditUserManageClick = function() {
		
		vm.editdirectionstr = '';	
		if(vm.editphotodirection){
			var selectedresults = '';
			if(vm.editdirectionArray.length > 0) {
				for(var j = 0; j < vm.editdirectionArray.length; j++) {
					selectedresults += vm.editdirectionArray[j].id;
				}
				for(var i = 1; i < 9; i++) {
					if(selectedresults.indexOf(i) < 0) {
						vm.editdirectionstr += 0;
					} else {
						vm.editdirectionstr += 1;
					};
				}
			}else{
				vm.editdirectionstr='00000000';
			}
		}
		
		vm.editUserTypeStr = "";
		if(vm.editUserTypeArray.length > 0) {
			for(var edit = 0; edit < vm.editUserTypeArray.length; edit++) {
				vm.editUserTypeStr += (vm.editUserTypeArray[edit].id + ",");
			}
			vm.editUserTypeStr = vm.editUserTypeStr.substr(0, vm.editUserTypeStr.length - 1);
		} else {
			layer.alert("请选择用户类型");
			return;
		}
		if(!vm.editTrueName) {
			layer.alert("请输入真实姓名");
			return;
		}
		//如果订户类型是企业可以不填身份证号
		if(!vm.editIdCardNum && vm.editSubscriberType != 1) {
			layer.alert("请输入用户身份证号");
			return;
		}
		if(vm.editIdCardNum && !(regIdCard.test(vm.editIdCardNum))) {
			layer.alert("用户身份证号输入有误");
			return;
		}
		if(vm.editConfirmPwd != vm.editPassword) {
			layer.alert("两次输入的密码不一致，请重输");
			return;
		}
		if(!vm.editUserStatus) {
			layer.alert("请选择用户状态");
			return;
		}
		if(!vm.editZipCode) {
			layer.alert("请输入邮政编码");
			return;
		} else if(!(regZipCode.test(vm.editZipCode))) {
			layer.alert("邮政编码输入有误");
			return;
		}
		if(!vm.editUserPhone) {
			layer.alert("请输入绑定手机号");
			return;
		} else if(!(regMobile.test(vm.editUserPhone))) {
			layer.alert("绑定手机号输入有误");
			return;
		}
		if(!vm.editUserEmail) {
			layer.alert("请输入绑定邮箱号");
			return;
		} else if(!(regEmail.test(vm.editUserEmail))) {
			layer.alert("绑定邮箱号输入有误");
			return;
		}
		if(vm.editInformIdCard && (!(regIdCard.test(vm.editInformIdCard)))) {
			layer.alert("收稿费人身份证号输入有误");
			return;
		}
		if(vm.editAccountIdCard && (!(regIdCard.test(vm.editAccountIdCard)))) {
			layer.alert("上述卡户名的身份证号输入有误");
			return;
		}
		if(vm.editIsPubInform == "" || !vm.editIsPubInform) {
			layer.alert("请选择是否公开个人信息！");
			return;
		}
		if(vm.editUserQQNum && (!(regQQNum.test(vm.editUserQQNum)))) {
			layer.alert("QQ号码输入有误");
			return;
		}
		if(!vm.editInformPayType || vm.editInformPayType == "undefined") {
			vm.editInformPayType = "";
		}
		if(!vm.editIsPubInform || vm.editIsPubInform == "undefined") {
			vm.editIsPubInform = "";
		}

		$('#update-uinfo-save-btn').prop('disabled', true);
		vm.updateBtnIfUseFlag = true;
		req.post('userCtro/updateUser.do', {
			id: vm.updateEditId,
			photographyDirection:vm.editdirectionstr,
			userName: vm.editUserName,
			roleIds: vm.editUserTypeStr,
			tureName: vm.editTrueName,
			question: vm.editPwdTipQue,
			answer: vm.editPwdTipAns,
			province: vm.editUserProv,
			city: vm.editUserCity,
			authorName: vm.editWorkName,
			telBind: vm.editUserPhone,
			emailBind: vm.editUserEmail,
			zipcode: vm.editZipCode,
			address: vm.editDetailPath,
			feeType: vm.editInformPayType,
			mailAddress: vm.editInformPath,
			mailUsername: vm.editInformName,
			mailIdCard: vm.editInformIdCard,
			mailZipCode: vm.editMailNum,
			bankAccount: vm.editAccountNum,
			bankUsername: vm.editAccountName,
			bankIdCard: vm.editAccountIdCard,
			bankName: vm.editAccountBank,
			idCard: vm.editIdCardNum,
			userType: vm.editUserLevel,
			subscriberType: vm.editSubscriberType,
			subscriptionType: vm.editSubscriptionType == 'undefined' ? "" : vm.editSubscriptionType,
			memo: vm.editUserRemark,
			isPublish: vm.editIsPubInform,
			userStatus: vm.editUserStatus,
			unitName: vm.editUnitName,
			unitAddress: vm.editUnitPath,
			unitTel: vm.editUnitPhone,
			unitFax: vm.editUnitFax,
			standby1: vm.editUserQQNum,
			standby2: vm.editUserWxNum,
			langType: vm.editlangType,
			userToken: vm.userToken,
			
			userDetail: vm.editUserDetail,
			homepageColumnId: vm.editUserColumnId,
			userOrder: vm.editUserOrderId,
			userClass: vm.editUserClass
			
		}).success(function(resp) {
			if(resp.code == '211') {
				$('#update-uinfo-save-btn').prop('disabled', false);
				layer.alert('编辑用户成功');	
				modalOperate.modalHide('user-info-modal');
				getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, vm.pagination.current, "");
			    vm.editUserTypeArray = [];
			    vm.editdirectionstr='';
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 充值
	 */
	vm.isOnTipUpPayClickFlag = false;
	vm.onTipUpPayClick = function() {
		if(vm.payUpMoney && !(/^[1-9]\d*$/.test(vm.payUpMoney))) {
			layer.alert('充值额为整数');
			return;
		}
		if(parseInt(vm.payMoney) + parseInt(vm.payUpMoney) > 99999999.99) {
			layer.alert('充值额超出限制');
			return;
		}
		vm.isOnTipUpPayClickFlag = true;
		req.post('userCtro/rechargeUser.do', {
			userId: vm.payUserId,
			cash: vm.payUpMoney,
			userToken: vm.userToken
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.payUpMoney = "";
				vm.isOnTipUpPayClickFlag = false;
				modalOperate.modalHide('user-info-modal');
				layer.alert('充值成功');
				getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, vm.pagination.current, "");
			} else {
				vm.isOnTipUpPayClickFlag = false;
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 获取登录日志表格数据
	 */
	function getLoginLogData(userName, siteId, days, page, type) {
		req.post("logCtro/getUserLoginLog.do", {
			userName: userName,
			siteId: siteId,
			days: days,
			page: page,
			rows: 6
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.loginLogArray = resp.data;
				var loginLogPageNum = resp.page;
				if(type == 1 && loginLogPageNum) {
					// 处理分页
					$.jqPaginator('#paginationLoginId', {
						totalPages: loginLogPageNum,
						visiblePages: 4,
						currentPage: 1,
						onPageChange: function(mineNum, pageType) {
							if(pageType === 'change') {
								getLoginLogData(userName, siteId, days, mineNum, 0);
							}
						}
					});
				}
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 获取操作日志表格数据
	 */
	function getWorkLogData(userId, days, page, type) {
		req.post("logCtro/getUserOpeLog.do", {
			userId: userId,
			days: days,
			page: page,
			rows: 6
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.workLogArray = resp.data;
				var workLogPageNum = resp.page;
				if(type == 1 && workLogPageNum) {
					// 处理分页
					$.jqPaginator('#paginationWorkId', {
						totalPages: workLogPageNum,
						visiblePages: 4,
						currentPage: 1,
						onPageChange: function(mineNum, pageType) {
							if(pageType === 'change') {
								getWorkLogData(userId, days, mineNum, 0);
							}
						}
					});
				}
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 点击切换
	 */
	vm.editDownWay = "0";
	vm.currentDownPayWay = "金额结算";
	vm.onSelectDownWayClick = function(type) {
		if(!type) {
			type = 0;
		}
		vm.editDownWay = type + "";
		switch(type) {
			case 0:
				vm.currentDownPayWay = "金额结算";
				break;
			case 1:
				vm.currentDownPayWay = "张数结算";
				break;
			case 2:
				vm.currentDownPayWay = "金额结算与张数结算";
				break;
		}

		// 该限制内已下载数量
		if(vm.editDownWay == 0) {
			// 充值
			vm.editDownNumShow0 = vm.mineEditItemData['BALANCE_LIMIT_DL_NUM'] || 0;
		} else if(vm.editDownWay == 1) {
			// 张数
			vm.editDownNumShow1 = vm.mineEditItemData['CONTRACT_LIMIT_DL_NUM'] || 0;
		} else if(vm.editDownWay == 2) {
			// 充值与张数
			vm.editDownNumShow2 = vm.mineEditItemData['THREE_LIMIT_DL_NUM'] || 0;
		}
		// 限制数量
		if(vm.editDownWay == 0) {
			// 充值
			vm.editDownLimitNum0 = vm.mineEditItemData['BALANCE_LIMIT_NUM'] || '';
		} else if(vm.editDownWay == 1) {
			// 张数
			vm.editDownLimitNum1 = vm.mineEditItemData['CONTRACT_LIMIT_NUM'] || '';
		} else if(vm.editDownWay == 2) {
			// 充值与张数
			vm.editDownLimitNum2 = vm.mineEditItemData['THREE_LIMIT_NUM'] || '';
		}

		// 下载限制类型
		if(vm.editDownWay == 0) {
			vm.editDownLimitType0 = vm.mineEditItemData['BALANCE_LIMIT_TYPE'];
		} else if(vm.editDownWay == 1) {
			vm.editDownLimitType1 = vm.mineEditItemData['CONTRACT_LIMIT_TYPE'];
		} else if(vm.editDownWay == 2) {
			vm.editDownLimitType2 = vm.mineEditItemData['THREE_LIMIT_TYPE'];
		}
		//是否允许下载
		if((vm.mineEditItemData['IS_DOWNLOAD']) || (vm.mineEditItemData['IS_DOWNLOAD'] == 0)) {
			vm.editDownIsAgree = vm.mineEditItemData['IS_DOWNLOAD'] * 1;
		} else {
			vm.editDownIsAgree = 1;
		}
	};

	/**
	 * 点击编辑下载管理
	 */
	vm.onEditDownManageClick = function() {
		if((!vm.editDownLevelId) || (vm.editDownLevelId == 'undefined')) {
			layer.alert("请选择下载级别");
			return;
		}
		if((!vm.editDownWay) || (vm.editDownWay == 'undefined')) {
			layer.alert("请选择下载方式");
			return;
		}
		/*if((!vm.editDownIsAgree) || (vm.editDownIsAgree == 'undefined')) {
			layer.alert("请选择是否允许下载");
			return;
		}*/

		//充值方式时对参数的验证
		if(vm.editDownWay == '0') {
			if(!vm.editDownLimitType0 && vm.editDownLimitType0 != 0) {
				layer.alert('请填写下载限制类型');
				return;
			}
			if(!vm.editDownLimitNum0) {
				layer.alert('请填写限制数量');
				return;
			}
			/*if(!vm.editDownLimitNum) {
			 layer.alert('请填写余额下载限制数量');
			 return;
			 }
			 if(!vm.editDownPayTalkPrice) {
			 layer.alert('请填写用户协议价格');
			 return;
			 }*/
			if(!vm.editDownPaySellRate) {
				layer.alert('请填写特殊定价图打折系数');
				return;
			}
			if(!vm.editDownPayMoneyRate) {
				layer.alert('请填写税收系数');
				return;
			}
			if(!vm.editDownPayBasicPrice) {
				layer.alert('请填写分成基价');
				return;
			}
		}
		//张数方式时对参数的验证
		if(vm.editDownWay == '1') {
			/*	if(!vm.editDownPackTalkPrice) {
			 layer.alert('请填写用户协议价格');
			 return;
			 }*/
			if(!vm.editDownLimitType1 && vm.editDownLimitType1 != 0) {
				layer.alert('请填写下载限制类型');
				return;
			}
			if(!vm.editDownLimitNum1) {
				layer.alert('请填写限制数量');
				return;
			}
			/*if(!vm.editDownLimitNum) {
			 layer.alert('请填写余额下载限制数量');
			 return;
			 }*/
			if(!vm.editDownPackBasicPrice) {
				layer.alert('请填写分成基价');
				return;
			}
			/*if(!vm.editDownPackBuyNum) {
			 layer.alert('请填写购买数量');
			 return;
			 }
			 */
			 if(!vm.downPackStartTime) {
			 layer.alert('请填写合同期限开始时间');
			 return;
			 }
			 if(!vm.downPackEndTime) {
			 layer.alert('请填写合同期限结束时间');
			 return;
			 }
			 if(vm.downPackStartTime > vm.downPackEndTime) {
			 layer.alert('合同期限开始时间不能大于结束时间');
			 return;
			 }
		}
		//充值与张数方式时对参数的验证
		if(vm.editDownWay == '2') {
			/*if(!vm.editDownPackTalkPrice) {
			 layer.alert('请填写张数协议价格');
			 return;
			 }*/
			if(!vm.editDownLimitType2 && vm.editDownLimitType2 != 0) {
				layer.alert('请填写下载限制类型');
				return;
			}
			if(!vm.editDownLimitNum2) {
				layer.alert('请填写限制数量');
				return;
			}
			/*if(!vm.editDownPayTalkPrice) {
			 layer.alert('请填写充值协议价格');
			 return;
			 }*/

			if(!vm.editDownPaySellRate) {
				layer.alert('请填写特殊定价图打折系数');
				return;
			}
			if(!vm.editDownPayMoneyRate) {
				layer.alert('请填写税收系数');
				return;
			}
			if(!vm.editDownPayBasicPrice) {
				layer.alert('请填写充值分成基价');
				return;
			}
			/*	if(!vm.editDownLimitNum) {
			 layer.alert('请填写余额下载限制数量');
			 return;
			 }*/
			if(!vm.editDownPackBasicPrice) {
				layer.alert('请填写张数分成基价');
				return;
			}
			/*if(!vm.editDownPackBuyNum) {
			 layer.alert('请填写购买数量');
			 return;
			 }
			 if(!vm.downPackStartTime) {
			 layer.alert('请填写合同期限开始时间');
			 return;
			 }
			 if(!vm.downPackEndTime) {
			 layer.alert('请填写合同期限结束时间');
			 return;
			 }*/
		}
		var reqParams = {
			id: vm.updateEditId,
			isDownload: vm.editDownIsAgree,
			downloadType: vm.editDownWay
		};
		switch(parseInt(vm.editDownWay)) {
			case 0:
				reqParams['downloadLevel'] = vm.editDownLevelId;
				reqParams['balancePerprice'] = vm.editDownPayTalkPrice;
				reqParams['balanceLimitType'] = vm.editDownLimitType0;
				reqParams['balanceLimitNum'] = vm.editDownLimitNum0;
				reqParams['balanceBasePerprice'] = vm.editDownPayBasicPrice;
				reqParams['balanceSale'] = vm.editDownPaySellRate;
				reqParams['balanceRevenue'] = vm.editDownPayMoneyRate;
				break;
			case 1:
				reqParams['downloadLevel'] = vm.editDownLevelId;
				reqParams['contractPerprice'] = vm.editDownPackTalkPrice;
				reqParams['contractLimitType'] = vm.editDownLimitType1;
				reqParams['contractLimitNum'] = vm.editDownLimitNum1;
				reqParams['contractBasePerprice'] = vm.editDownPackBasicPrice;
				reqParams['contractBuyNum'] = vm.editDownPackBuyNum;
				reqParams['startTime'] = vm.downPackStartTime;
				reqParams['endTime'] = vm.downPackEndTime;
				break;
			case 2:
				reqParams['downloadLevel'] = vm.editDownLevelId;
				reqParams['threeLimitType'] = vm.editDownLimitType2;
				reqParams['threeLimitNum'] = vm.editDownLimitNum2;
				reqParams['balancePerprice'] = vm.editDownPayTalkPrice;
				reqParams['balanceBasePerprice'] = vm.editDownPayBasicPrice;
				reqParams['balanceSale'] = vm.editDownPaySellRate;
				reqParams['balanceRevenue'] = vm.editDownPayMoneyRate;
				reqParams['contractPerprice'] = vm.editDownPackTalkPrice;
				reqParams['contractBasePerprice'] = vm.editDownPackBasicPrice;
				reqParams['contractBuyNum'] = vm.editDownPackBuyNum;
				reqParams['startTime'] = vm.downPackStartTime;
				reqParams['endTime'] = vm.downPackEndTime;
				break;
		}
		req.post('userCtro/updateUserDownload.do', reqParams).success(function(resp) {
			if(resp.code == '211') {
				vm.editUserTypeArray = [];
				modalOperate.modalHide('user-info-modal');
				layer.alert(resp.msg);
				getUserManageData(vm.isReMyRoleIdFlag, vm.searchType, false, vm.pagination.current, "");
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

	/**
	 * 获取角色列表
	 */
	vm.addRoleArray = [];
	vm.editRoleArray = [];

	function getRoleList() {
		req.post('roleCtro/getRoleByQuery.do', {
			page: 1,
			rows: 100,
			userToken: vm.userToken
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.addRoleArray = resp.data;
				vm.editRoleArray = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	/*设置摄影方向列表*/
	vm.addphotodirectionArray = [{
			'id': 1,
			'direction': '时政'
		},
		{
			'id': 2,
			'direction': '财经'
		},
		{
			'id': 3,
			'direction': '娱乐'
		},
		{
			'id': 4,
			'direction': '体育'
		},
		{
			'id': 5,
			'direction': '社会'
		},
		{
			'id': 6,
			'direction': '风光'
		},
		{
			'id': 7,
			'direction': '图表漫画'
		},
		{
			'id': 8,
			'direction': '其他'
		}
	];
	vm.editphotodirectionArray=[{
			'id': 1,
			'direction': '时政'
		},
		{
			'id': 2,
			'direction': '财经'
		},
		{
			'id': 3,
			'direction': '娱乐'
		},
		{
			'id': 4,
			'direction': '体育'
		},
		{
			'id': 5,
			'direction': '社会'
		},
		{
			'id': 6,
			'direction': '风光'
		},
		{
			'id': 7,
			'direction': '图表漫画'
		},
		{
			'id': 8,
			'direction': '其他'
		}
	];
	

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