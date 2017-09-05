/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('mDatabaseCtrl', function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, $stateParams, $http, modalOperate, allModalMove,lanmuShareDataService) {
	var vm = this;

	// 从路由获取专题id
	vm.topicId = $stateParams.topicId;

	//移动模态框
	vm.moveModal = function(dragDiv, tagDiv) {
		allModalMove.modalMove(dragDiv, tagDiv);
	}
	//初始化相关配置
	function initSetting() {
		//中英文标记
		vm.langTypeFlag = window.localStorage.lang;
		//默认激活的导航项为全部稿件
		vm.acitiveOneSlideTit = 1;
		vm.acitiveTwoSlideTit1 = 1;
		vm.acitiveTwoSlideTit2 = 1;
		vm.search = {
			priceType: '0'
		};

		/**
		 * 栏目编辑选择稿件
		 * topicId 专题id
		 * lanmuId 栏目id //返回需要
		 * columnId 子栏目/栏目id
		 * columnTemplate 模板id
		 * num 稿件位置
		 * oldId 原稿件id ，在更多稿件替换的时候需要
		 * */
		vm.columnFlag = $cookies.get('columnFlag'); //单选稿件开关
		vm.columnMoreFlag = $cookies.get('columnMoreFlag'); //多选稿件开关
		//从cookie里取得角色id
		vm.adminRoleId = $cookies.get('admin_roleId');
		vm.topicId = $stateParams.topicId;
		vm.lanmuId = $stateParams.lanmuId;
		vm.columnId = $stateParams.columnId;
		vm.columnTemplate = $stateParams.columnTemplate;
		vm.oldId = $stateParams.oldId;
		vm.num = $stateParams.num;
		//存在推送至英文栏目数组
		vm.selPushGroup = [];
		//以撤稿件的复选框的双向绑定
		vm.selCheckback = []
		//存放资料库数组
		vm.hadPubRowDataArray = [];
		//资料库总条数
		vm.databaseList_total = 0;
		//默认当前页1
		vm.pagination = {
			current: 1
		};
		vm.properties = 0;
		//默认每页10条
		vm.selPageRows = '6';
		vm.children = [];
		//稿件的类别
		vm.cateId = null;
		vm.ifAdvanceSearchFlag = false;
		vm.search.priceType = "";
		vm.show = false;
		vm.isHiden = false;
	}
	//下载模态显示
	vm.ModalShow = function(modalId) {
		modalOperate.modalShow(modalId);
	};
	//下载模态框隐藏
	vm.ModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	};
	//初始化
	function init() {
		initSetting();
		getSelCpCategories(function(category) {
			angular.forEach(category, function(item, index) {
				if(item.categoryName == '新闻图片') {
					vm.categories = item.categories;
				}
			});
		});
		lanmu();
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, null, 1, 0, false);
			getSignGroups(1, null, 1, 1, false);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, null, 1, 0, false);
			getSignGroups(2, null, 1, 1, false);
		}
	}

	init();

	function lanmu() {
		req.post('enColumn/showUpColumnAll.do', {
			"langType": vm.langTypeFlag
		}).success(function(resp) {
			if(resp.code == '211') {
				console.log(resp.data);
				vm.sortDetailsArray = resp.data;
				for(var i = 0; i < resp.data.length; i++) {
					for(var s = 0; s < resp.data[i].children.length; s++) {
						vm.children.push(resp.data[i].children[s])
					}
				}

				console.log(vm.children);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	//全选
	$(".all").click(function() {
		var xz = $(this).prop("checked"); //判断全选按钮的选中状态
		$(".check").prop("checked", xz); //发布稿件
		$(".checkBack").prop("checked", xz); //以撤稿件

	});
	//资料库模态框隐藏
	vm.databaseModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	};
	//推送资料图稿件到英文栏目
	vm.pushGroupPicsToEn = function() {
		var groupIds = [];
		if(vm.selPushGroup == '') {
			layer.alert('您还没有选择稿件！');
		} else {
			for(var key in vm.selPushGroup) {
				groupIds.push(key);
			}
			var pushGroupUrl = 'groupPicCtro/pushGroupPicForEn.do';
			req.post(pushGroupUrl, {
				groupIds: groupIds,
				roleId: vm.adminRoleId
			}).success(function(resp) {
				if(resp && resp.code == '211') {
					layer.alert('推送数据成功！');
					console.log('success');
					vm.selPushGroup = [];
				} else if(resp && resp.msg != '未登录') {
					vm.selPushGroup = [];
					layer.alert(resp.msg);
				}

			});
		}
	}
	//初始化搜索对象
	function initSearchSetting() {
		vm.searchDataModel = '';
		if(vm.search && vm.search.id) {
			vm.search.id = '';
		}
		if(vm.search && vm.search.title) {
			vm.search.title = '';
		}
		if(vm.search && vm.search.author) {
			vm.search.author = '';
		}
		if(vm.search && vm.search.place) {
			vm.search.place = '';
		}
		if(vm.search && vm.search.editor) {
			vm.search.editor = '';
		}
		if(vm.search && vm.search.memo) {
			vm.search.memo = '';
		}
		if(vm.search && vm.search.fileName) {
			vm.search.fileName = '';
		}
		if(vm.search && vm.search.beginTime) {
			vm.search.beginTime = '';
		}
		if(vm.search && vm.search.endTime) {
			vm.search.endTime = '';
		}
		if(vm.search && vm.search.paramStr) {
			vm.search.paramStr = '';
		}
	}

	//选择第一类的导航项
	vm.chooseOneClickType = function(acitiveSlideTit) {
		vm.acitiveOneSlideTit = acitiveSlideTit;
		vm.pagination.current = 1;
		$(".all").prop("checked", false)
		initSearchSetting();
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, vm.cateId, 1, 0, false);
			getSignGroups(1, vm.cateId, 1, 1, false);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, vm.cateId, 1, 0, false);
			getSignGroups(2, vm.cateId, 1, 1, false);
		}
	};

	//选择第二类的导航项
	vm.chooseTwoClickType1 = function(acitiveSlideTit) {
		vm.acitiveTwoSlideTit1 = acitiveSlideTit;
		vm.pagination.current = 1;
		initSearchSetting();
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, vm.cateId, 1, 0, false);
			getSignGroups(1, vm.cateId, 1, 1, false);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, vm.cateId, 1, 0, false);
			getSignGroups(2, vm.cateId, 1, 1, false);
		}
	};

	vm.chooseTwoClickType2 = function(acitiveSlideTit) {
		vm.acitiveTwoSlideTit2 = acitiveSlideTit;
		vm.pagination.current = 1;
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, vm.cateId, 1, 0, false);
			getSignGroups(1, vm.cateId, 1, 1, false);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, vm.cateId, 1, 0, false);
			getSignGroups(2, vm.cateId, 1, 1, false);
		}
	};
	/**
	 *
	 * @param type 0：至图片，1：图片及说明
	 */
	vm.downloadGroupPic = function(type) {
		var id_array = new Array(); //存放稿件的id
		//发布稿件
		if(vm.acitiveOneSlideTit == 1 && vm.acitiveTwoSlideTit1 == 1) {
			$('input[name="check"]:checked').each(function() {
				id_array.push($(this).val()); //向数组中添加元素
			});
		} else if(vm.acitiveOneSlideTit == 1 && vm.acitiveTwoSlideTit1 == 2) {
			$('input[name="checked"]:checked').each(function() {
				id_array.push($(this).val()); //向数组中添加元素
			});
		}
		//以撤稿件
		if(vm.acitiveOneSlideTit == 2 && vm.acitiveTwoSlideTit1 == 1) {
			$('input[name="checkBack"]:checked').each(function() {
				id_array.push($(this).val()); //向数组中添加元素
			});
		} else if(vm.acitiveOneSlideTit == 2 && vm.acitiveTwoSlideTit1 == 2) {
			$('input[name="checkBack"]:checked').each(function() {
				id_array.push($(this).val()); //向数组中添加元素
			});
		}

		var groupIds = id_array.join(','); //将数组元素连接起来以构建一个字符串
		if(id_array.length == 0) {
			layer.alert("请选择图片");
			modalOperate.modalHide("database-type-modal");
			return;
		}
		document.location = "/cnsphoto/enGroupPicDown/downGrouplePic.do?groupIds=" + groupIds + "&type=" + type+"&langType="+vm.langTypeFlag;
		modalOperate.modalHide("database-type-modal");

	}

	/**
	 * 获取资料库列表
	 * @param gType     1已发布，2已撤稿件
	 * @param cateId    分类id:区分分类Id，如果为-100则表示搜索全部
	 * @param page      第几页
	 * @param showType  展示类型：0列展示，1表格展示
	 * @param isSearchFlag true:高级搜素 false:普通搜索
	 */
	function getSignGroups(gType, cateId, page, showType, isSearchFlag) {
		vm.totalPages = "0";
		vm.databaseList_total = "0";
		vm.cateId = cateId;
		var params;
		var reqUrl = '';
		if(!isSearchFlag) {
			params = {
				page: page,
				rows: vm.selPageRows,
				gType: gType,
				paramStr: vm.searchDataModel,
				properties: vm.properties,
				cateId: cateId,
				langType: window.localStorage.lang
			};
			reqUrl = 'groupPicCtro/getSginGroup.do';
		} else {
			params = {
				page: page,
				rows: vm.selPageRows,
				gType: gType,
				groupId: vm.search.groupId,
				properties: vm.search.properties,
				beginSginTime: vm.search.beginSginTime,
				endSginTime: vm.search.endSginTime,
				author: vm.search.author,
				title: vm.search.title,
				people: vm.search.people,
				place: vm.search.place,
				pAuthor: vm.search.pAuthor,
				kewords: vm.search.kewords,
				editor: vm.search.editor,
				cateId: vm.search.cateId,
				sginId: vm.search.sginId,
				goodsType: vm.search.goodsType,
				priceType: vm.search.priceType,
				samllPrice: vm.search.samllPrice,
				bigPrice: vm.search.bigPrice,
				fileName: vm.search.fileName,
				isPics: vm.search.isPics,
				memo: vm.search.memo,
				remark:vm.search.remark,
				langType: window.localStorage.lang
			};
			vm.ifAdvanceSearchFlag = true;
			reqUrl = 'groupPicCtro/getGroupComplex.do';
		}
		/* if (cateId != -100) {
		     	params['properties'] = vm.acitiveOneSlideTit;
		     	params['cateId'] = cateId;
		 }*/
		req.post(reqUrl, params).success(function(resp) {
			if(resp.code == '211' && resp.data != [] && resp.data.length > 0) {
				if(gType === 1) {
					if(showType === 0) {
						vm.hadPubRowDataArray = resp.data;
						vm.totalPages = resp.page;
						vm.databaseList_total = resp.other;

					} else {
						vm.hadPubTableDataArray = resp.data;
						vm.totalPages = resp.page;
						vm.databaseList_total = resp.other;
					}
				}
				if(gType === 2) {
					if(showType === 0) {
						vm.hadBackRowDataArray = resp.data;
						vm.totalPages = resp.page;
						vm.databaseList_total = resp.other;
					} else {
						vm.hadBackTableDataArray = resp.data;
						vm.totalPages = resp.page;
						vm.databaseList_total = resp.other;
					}
				}
				modalOperate.modalHide('database-manuscript-search-modal');
			} else {
				if(gType === 1) {
					if(showType === 0) {
						vm.hadPubRowDataArray = [];
						//$('#pageHadPubRowId').jqPaginator('destroy');
					} else {
						vm.hadPubTableDataArray = [];
						//$('#pageHadPubTableId').jqPaginator('destroy');
					}
				} else {
					if(showType === 0) {
						vm.hadBackRowDataArray = [];
						//$('#pageHadBackRowId').jqPaginator('destroy');
					} else {
						vm.hadBackTableDataArray = [];
						//$('#pageHadBackTableId').jqPaginator('destroy');
					}
				}
				modalOperate.modalHide('database-manuscript-search-modal');
			}

		});
	}

	//页数变化
	vm.pageChanged = function(pageNumber) {
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, vm.cateId, pageNumber, 0, vm.ifAdvanceSearchFlag);
			getSignGroups(1, vm.cateId, pageNumber, 1, vm.ifAdvanceSearchFlag);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, vm.cateId, pageNumber, 0, vm.ifAdvanceSearchFlag);
			getSignGroups(2, vm.cateId, pageNumber, 1, vm.ifAdvanceSearchFlag);
		}
	};

	//普通搜索
	vm.onSearchModalShow = function() {
		vm.pagination.current = 1;
		vm.ifAdvanceSearchFlag = false;
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, vm.cateId, 1, 0, vm.ifAdvanceSearchFlag);
			getSignGroups(1, vm.cateId, 1, 1, vm.ifAdvanceSearchFlag);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, vm.cateId, 1, 0, vm.ifAdvanceSearchFlag);
			getSignGroups(2, vm.cateId, 1, 1, vm.ifAdvanceSearchFlag);
		}
	};

	//高级检索
	vm.advanceSearchModalShow = function(modalId) {
		modalOperate.modalShow(modalId);
		vm.show = false;
		vm.isHiden = false;
		vm.ifAdvanceSearchFlag = true;
	};

	//确定高级检索
	vm.saveAdvanceSearch = function(modalId) {
		vm.ifAdvanceSearchFlag = true;
		vm.pagination.current = 1;
		vm.sureHighSearchFlag = true;
		//getSignGroups();
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, vm.cateId, 1, 0, true);
			getSignGroups(1, vm.cateId, 1, 1, true);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, vm.cateId, 1, 0, true);
			getSignGroups(2, vm.cateId, 1, 1, true);
		}
	};
	vm.isShow = function(show){
		if(show == 1) {
			vm.isHiden = true;
			vm.show = false;
		}
		if(show == 2) {
			vm.show = true;
			vm.isHiden = false;
		}
	}
	//高级检索请求
	function req_advanceSearch(pageNum) {
		var params = {
			page: pageNum,
			rows: '',
			groupId: vm.search.groupId,
			properties: vm.search.properties,
			beginSginTime: vm.search.beginSginTime,
			endSginTime: vm.search.endSginTime,
			author: vm.search.author,
			title: vm.search.title,
			people: vm.search.people,
			place: vm.search.place,
			pAuthor: vm.search.pAuthor,
			kewords: vm.search.kewords,
			editor: vm.search.editor,
			cateId: vm.search.cateId,
			sginId: vm.search.sginId,
			goodsType: vm.search.goodsType,
			priceType: vm.search.priceType,
			samllPrice: vm.search.samllPrice,
			bigPrice: vm.search.bigPrice,
			fileName: vm.search.fileName,
			isPics: vm.search.isPics,
			langType: window.localStorage.lang
		};
		req.post('groupPicCtro/getGroupComplex.do', params).success(function(resp) {
			if(resp.code == '211') {
				layer.alert('操作成功');
				vm.hadPubRowDataArray = resp.data;
				var sendPage = resp.page;
				if(sendPage) {
					$.jqPaginator('#sendpaginationId', {
						totalPages: sendPage,
						visiblePages: 6,
						currentPage: pageNum,
						onPageChange: function(pageNum, pageType) {
							if(pageType === 'change') {
								getSysConfigList(searchName, pageNum);
							}
						}
					});
				}
				modalOperate.modalHide('database-manuscript-search-modal');
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 点击展开或收缩菜单
	 */
	var count = 0;
	vm.navFunc = function(name) {
		if(count % 2 == 0) {
			vm.navActiveMenu = name;
		} else {
			vm.navActiveMenu = '';
		}
		if(name == '新闻图片') {
			vm.properties = 0;
		} else if(name == '专题图片') {
			vm.properties = 1;
		}
		if(vm.acitiveOneSlideTit == 1) {
			getSignGroups(1, null, 1, 0, false);
			getSignGroups(1, null, 1, 1, false);
			getSignGroups(2, null, 1, 0, false);
		} else if(vm.acitiveOneSlideTit == 2) {
			getSignGroups(2, null, 1, 1, false);
		}
		count++;
	};
	/**
	 * 获取稿件分类信息
	 */
	function getSelCpCategories(callback) {
		vm.selCpCategories;
		req.post('classification/selCpCategories.do', {
			langType: window.localStorage.lang
		}).success(function(resp) {
			if(resp.code == '211') {
				//vm.selCpCategories = resp.data;
				var classDataArray = resp.data;
				var catedata = [];
				var categories = [];
				if(classDataArray.length > 0) {
					for(var item = 0; item < classDataArray.length; item++) {
						var itemObject = classDataArray[item];
						if(itemObject.categoryName == "新闻图片" || itemObject.categoryName == "专题图片") {
							catedata.push(itemObject);
						} else if(itemObject.categoryName == '新闻类别') {
							categories = itemObject.categories;
						}
					}
					for(var item = 0; item < catedata.length; item++) {
						var itemObject = catedata[item];
						if(itemObject.categoryName == "新闻图片" || itemObject.categoryName == "专题图片") {
							catedata[item].categories = categories;
						}

					}
					vm.selCpCategories = catedata;
				}
				if(callback) callback(catedata);
				//var subId = vm.selCpCategories[0]['categories'][0]['id'];
				//vm.navFunc(vm.selCpCategories[0]['categoryName']);
				//vm.onShowHadPubDetailClick(subId, 0);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	/**
	 * 点击展示已发布分类详情
	 */
	vm.hadPubPosition = -1;
	vm.onShowHadPubDetailClick = function(itemId, position) {
		vm.hadPubPosition = position;
		vm.category = itemId;
		getSignGroups(1, itemId, 1, 0, false);
		getSignGroups(1, itemId, 1, 1, false);
	};

	/**
	 * 点击展示已撤销分类详情
	 */
	vm.hadBackPosition = -1;
	vm.onShowHadBackDetailClick = function(itemId, position) {
		vm.hadBackPosition = position;
		vm.category = itemId;
		getSignGroups(2, itemId, 1, 0, false);
		getSignGroups(2, itemId, 1, 1, false);
	};

	// 选择稿件-新
	vm.selectContribute1 = function(dbItem){
		// 从服务中获取专题栏目数据，找出lanmuPics中falg为true的为其添加图片url，供专题栏目详情页展示
		var lanmuData = lanmuShareDataService.lanmuData;
		console.log(lanmuData);
		for(var i = 0,lanmuLen = lanmuData.length; i < lanmuLen; i++){
				var lanmuPics = lanmuData[i].cpLanmuPictures;
				for(var j = 0,picsLen = lanmuPics.length; j < picsLen; j++){
					if(lanmuPics[j].flag){
							lanmuPics[j].url = dbItem.samllPath;
							lanmuPics[j].groupId = dbItem.ID;
							lanmuPics[j].title = dbItem.TITLE;
							// 最后初始化flag
							lanmuPics[j].flag = false;
					}
				}
		}
		// 跳转回专题栏目详情页
        $state.go('role.manager.projectManageNewDetail',{id: vm.topicId});
        $cookies.remove('columnFlag');
        $cookies.remove('columnMoreFlag');
	}
	/**
	 * 选择稿件
	 * id 所选稿件id
	 * columnId 栏目id
	 * num 稿件所在位置
	 * columnTemplate 栏目模板id
	 * oldId 原稿件id，在更多稿件替换的时候需要
	 * */
	vm.selectContribute = function(id) {
		if(vm.oldId) {
			//更多替换稿件
			req.post('lanmu/addMoreLanmuPic.do', {
				lanmuId: vm.columnId,
				groupId: id,
				id: vm.oldId
			}).success(function(resp) {
				if(resp.code == '211') {
					var _url = 'role.manager.projectManageEdit' + vm.columnTemplate;
					layer.msg(resp.msg ? resp.msg : '添加稿件成功', {
						time: 2000
					});
					$cookies.remove('columnFlag');
					$state.go(_url, {
						id: resp.data.lanmuId,
						specialId: resp.data.topicId
					});
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		} else {
			//模板替换稿件
			req.post('lanmu/addLanmuPic.do', {
				lanmuId: vm.columnId,
				potision: vm.num,
				groupId: id
			}).success(function(resp) {
				if(resp.code == '211') {
					var _url = 'role.manager.projectManageEdit' + vm.columnTemplate;
					layer.msg(resp.msg ? resp.msg : '添加稿件成功', {
						time: 2000
					});
					$cookies.remove('columnFlag');
					$state.go(_url, {
						id: resp.data.lanmuId,
						specialId: vm.topicId
					});
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		}
	};

	/**
	 * 选择更多稿件 多选
	 * id 所选稿件id
	 * columnId 栏目id
	 * columnTemplate 栏目模板id
	 * */
	//判断是否选择了数据
	function judgeIfSelData() {
		vm.finalConfigIds = '';
		var arr = [];
		for(var key in vm.selConfigIds) {
			if(vm.selConfigIds[key]) {
				arr.push(key);
			}
		}
		vm.finalConfigIds = arr.join(',');
	}

	// 添加更多稿件
	vm.selectMoreContribute = function() {
		judgeIfSelData();
		req.post('lanmu/addMoreLanmuPic.do', {
			lanmuId: vm.columnId,
			groupId: vm.finalConfigIds
		}).success(function(resp) {
			if(resp.code == '211') {
				var _url = 'role.manager.projectManageEdit' + vm.columnTemplate;
				layer.msg(resp.msg ? resp.msg : '添加稿件成功', {
					time: 2000
				});
				$cookies.remove('columnMoreFlag');
				$state.go(_url, {
					id: resp.data.lanmuId,
					specialId: vm.topicId
				});
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	};

    // 是否选择了更多稿件
	vm.ifSeledMoreMs = function(dbItem){
		// 过渡数组
		var middleArr = [];
		var keyArr = [];
        // 从服务中获取专题栏目数据，找出lanmuPics中falg为true的为其添加图片url，供专题栏目详情页展示
        var lanmuData = lanmuShareDataService.lanmuData;
        for(var i = 0,lanmuLen = lanmuData.length; i < lanmuLen; i++) {
            // var lanmuPics = lanmuData[i].moreLanmuPictures;
            for (var key in vm.selConfigIds) {
                keyArr.push(key);
                if (vm.selConfigIds[key] && keyArr.indexOf(key) == -1) {
                    /*lanmuData[i].moreLanmuPictures.push({
                        id:'',
                        potision: '',
                        groupId: dbItem.ID,
                        url: dbItem.samllPath,
                        flag: false,
                        title: dbItem.TITLE
					});*/
                    middleArr.push({
                        id:'',
                        potision: '',
                        groupId: dbItem.ID,
                        url: dbItem.samllPath,
                        flag: false,
                        title: dbItem.TITLE
                    });
                }
            }
            lanmuData[i].moreLanmuPictures = middleArr;
            console.log(lanmuData[i].moreLanmuPictures);
        }
	}
    // 添加更多稿件-新
	vm.selectMoreContribute1 = function(dbItem){
		console.log(vm.selConfigIds);
		console.log(lanmuShareDataService.lanmuData);
        // 跳转回专题栏目详情页
        // $state.go('role.manager.projectManageNewDetail',{id: vm.topicId});
        $cookies.remove('columnFlag');
        $cookies.remove('columnMoreFlag');
      /*  // 从服务中获取专题栏目数据，找出lanmuPics中falg为true的为其添加图片url，供专题栏目详情页展示
        var lanmuData = lanmuShareDataService.lanmuData;
        console.log(lanmuData);
        for(var i = 0,lanmuLen = lanmuData.length; i < lanmuLen; i++){
                var lanmuPics = lanmuData[i].lanmuPics;
                if(lanmuPics[i].flag){
                    for(var key in vm.selConfigIds) {
                        if(vm.selConfigIds[key]) {
                            lanmuPics.push({
                                position: '',
                                groupId: dbItem.ID,
                                url: dbItem.samllPath,
                                flag:false,
                                title: dbItem.TITLE
                            });
                        }
                    }
                }
        }
        console.log(lanmuData);
        // 跳转回专题栏目详情页
        $state.go('role.manager.projectManageNewDetail');*/
	}

	/**返回栏目编辑
	 *
	 * */
	vm.backColumn = function() {
		var _url = 'role.manager.projectManageEdit' + vm.columnTemplate;
		$cookies.remove('columnFlag');
		$cookies.remove('columnMoreFlag');
		$state.go(_url, {
			id: vm.lanmuId,
			specialId: vm.topicId
		});
	}

});