/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('mManuscriptDetailCtrl', function($scope,$sce, $cookies, req, md5, $state, $rootScope, $stateParams, modalOperate, getMyDuty, allModalMove, $window) {
	var vm = this;
	$scope.langType = window.localStorage.lang;

	// 获取用户名
	vm.uName = $cookies.get('admin_uname');

	// 获取从路由传过来的稿件id
	vm.groupId = $stateParams.id;

	// 获取从路由传过来的稿件值别
	vm.dtType = $stateParams.dtType;

	// 页面初始化相关配置
	function initSetting() {
		// 点击管理详情div是否展示的标识
		vm.mangeOperateFlag = false;
		// 存储签发参数的数组
		vm.signReqParamData = [];
		vm.signlanmu = [];
		vm.type = 0;
		vm.delManuscriptReson = '废稿';
	}

	// 页面初始化
	function init() {
		initSetting();
		getMasBaseUrl();
		getManuscriptDetails();
		initSignTree();
		// 从service里取得我的值班级别的数据，实现数据持久化
		getMyDuty.req_getMyDuty(function(type) {
			vm.mydutyType = type;
		})
	}

	init();
	
	
	//全选
    $(".all").click(function(){
        var xz = $(this).prop("checked");
        $(".check").prop("checked", xz);
    });

	//获取Mas视频基础URL add by xiayunan 20170907
	function getMasBaseUrl(){
		req.get('groupPicCtro/getMasBaseUrl.do').success(function(resp) {
			if(resp.code == '211') {
				vm.masBaseUrl = resp.data.masBaseUrl;
			}else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	
	//作者信息弹框
	vm.showAuthorinfor = function() {
		$("#authorinformation-modal").show();
	}
	vm.hideAuthorinfor = function() {
		$("#authorinformation-modal").hide();
	}
	//获取作者信息
	vm.authorinfor = function(authorId) {
		req.post('enGetGroupPicCtro/selCpUserBasicInfo.do', {
			id: authorId
		}).success(
			function(resp) {
				if(resp.code == '211') {
					$("#authorinformation-modal").show();
					vm.author = resp.data;
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
	}
	var IDMark_A = "_a";

	// 设置
	var setting = {
		view: {
			addDiyDom: addDiyDom
		},
		check: {
			enable: true,
			chkboxType: {
				"Y": "ps",
				"N": "ps"
			}
		},
		callback: {
			// onClick: treeClick,
			//onCheck: treeCheckEvent,
			//add by xiayunan@2017-09-24 
        	beforeCheck: zTreeBeforeCheck
		}
	};
	// 设置
	var settings = {
		view: {
			addDiyDom: addDiyDoms
		},
		check: {
			enable: true,
			chkboxType: {
				"Y": "ps",
				"N": "ps"
			}
		},
		callback: {
			// onClick: treeClick,
			//onCheck: treeCheckEvents
			//add by xiayunan@2017-09-24 
        	beforeCheck: zTreeBeforeCheck
		}
		
		
	};
	
	//add by xiayunan@2017-09-24 
    function zTreeBeforeCheck(treeId, treeNode) {
        return !treeNode.isParent;//当是父节点 返回false 不让选取
    }
	
	
	// 树复选框选中回调事件

	function treeCheckEvent(event, treeId, treeNode, clickFlag) {

        if(window.localStorage.lang==0){
        	var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		    var treeObj_node = treeObj.getNodes()[0].children;
		    $('#treeDemo_' + (treeObj_node.length + 1) + '_check').removeClass('checkbox_false_full');
		    $('#treeDemo_' + (treeObj_node.length + 1) + '_check').addClass('checkbox_true_fulls');
		    treeObj_node[treeObj_node.length - 1].checked = true;
        }		
	}

	function treeCheckEvents(event, treeId, treeNode, clickFlag) {
	}

	// 获取签发的参数
	function getSignParams(callback) {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var nodes = treeObj.getCheckedNodes(true);

		var treeObjs = $.fn.zTree.getZTreeObj("tree");
		var nodess = treeObjs.getCheckedNodes(true);
		if(nodes.length == 0 && nodess.length == 0) {
			layer.alert('请至少选中一个签发类型');
			return;
		}
		for(var i = 0; i < nodes.length; i++) {
			var position = $("#diyBtn_" + nodes[i].id).prop(
				'value');
			if(nodes[i].type == '0') {
				vm.signReqParamData.push({
					type: '0',
					signId: nodes[i].id,
					position: position
				});
			}
			if(nodes[i].type == '1') {
				// todo 专题暂时这样
				vm.signReqParamData.push({
					type: '1',
					topicId: 1
				});
			}

		}
		if(callback) callback();
	}

	function getSignParamss(callback) {
		var treeObjs = $.fn.zTree.getZTreeObj("treeDemo");
		var nodess = treeObjs.getCheckedNodes(true);

		var treeObj = $.fn.zTree.getZTreeObj("tree");
		var nodes = treeObj.getCheckedNodes(true);
		if(nodes.length == 0 && nodess.length == 0) {
			layer.alert('请至少选中一个签发类型');
			return;
		}
		for(var i = 0; i < nodes.length; i++) {
			var positions = $("#diyBtn_" + nodes[i].id).prop('value');
			vm.signlanmu.push({
				lanmuId: nodes[i].id,
				position: positions
			});
		}
		if(callback) callback();
	}

	req.post('enColumn/showUpColumnAll.do', {
		"langType": $scope.langType
	}).success(
		function(resp) {
			if(resp.code == '211') {
				$scope.zNodes = resp.data;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	//获取专题和子栏目	
	var data_a = [];
	req.post('enSpec/showTopic.do', {
		"langType": $scope.langType
	}).success(
		function(resp) {
			if(resp.code == '211') {
				$scope.datas = resp.data;
				for(var i = 0; i < resp.data.length; i++) {
					if(resp.data[i].type == 0 && resp.data[i].display == 1) {
						data_a.push(resp.data[i]);
					}
				}
				$scope.data = data_a;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});

	// 查看已签稿件位置
	function lookSignedGroupPos(sginId, limit) {
		if($scope.langType == 0) {
			req.post('groupPicCtro/getSginGroupsBySginId.do', {
				sginId: sginId,
                limit: limit
			}).success(function(resp) {
				if(resp.code == '211') {
					vm.signedGroupList = resp.data;
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		} else {
			req.post('enSign/browsePosition.do', {
				columnId: sginId
			}).success(function(resp) {
				if(resp.code == '211') {
					vm.signedGroupList = resp.data;
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		}

	}

	function lookSignedGroupPoss(sginId) {
		req.post('lanmu/lanMuPicDetail.do', {
			lanmuid: sginId
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.lanmu_group = resp.data.cpLanmuPictures;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	// 用于在节点上固定显示用户自定义控件
	function addDiyDom(treeId, treeNode) {
		if(treeNode.parentNode && treeNode.parentNode.id != 2) return;
		var aObj = $("#" + treeNode.tId + IDMark_A);

		for(var i = 0; i < $scope.zNodes.length; i++) {
			var children = $scope.zNodes[i].children;
			if($scope.zNodes[i].signPosition > 0) {

				if(treeNode.id == $scope.zNodes[i].id) {
					var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
					var editStr = "<span id='diyBtn_space_" +
						$scope.zNodes[i].id +
						"' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
						treeNode.id +
						"'></select>";
					aObj.after(aLinkStr);
					aObj.after(editStr);

					for(var j = 0; j <= treeNode.signPosition; j++) {
						if(j == 0) {
							$("#diyBtn_" + treeNode.id).append("<option value=" + j + ">不显示</option>");
						} else {
							$("#diyBtn_" + treeNode.id).append("<option value=" + j + ">第" + j + "位</option>");
						}
					}
				}
			}
			for(var a = 0; a < children.length; a++) {
				var childrens = children[a].children;
				if(children[a].signPosition > 0) {
					if(treeNode.id == children[a].id) {
						var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
						var editStr = "<span id='diyBtn_space_" +
							children[a].id +
							"' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
							treeNode.id +
							"'></select>";
						aObj.after(aLinkStr);
						aObj.after(editStr);
						for(var s = 0; s <= treeNode.signPosition; s++) {
							if(s == 0) {
								$("#diyBtn_" + treeNode.id).append("<option value=" + s + ">不显示</option>");
							} else {
								$("#diyBtn_" + treeNode.id).append("<option value=" + s + ">第" + s + "位</option>");
							}
						}
					}
				}
				for(var x = 0; x < childrens.length; x++) {
					if(childrens[x].signPosition > 0) {
						if(treeNode.id == childrens[x].id) {
							var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
							var editStr = "<span id='diyBtn_space_" +
								childrens[x].id +
								"' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
								treeNode.id +
								"'></select>";
							aObj.after(aLinkStr);
							aObj.after(editStr);
							for(var n = 0; n <= childrens[x].signPosition; n++) {
								if(n == 0) {
									$("#diyBtn_" + treeNode.id).append("<option value=" + n + ">不显示</option>");
								} else {
									$("#diyBtn_" + treeNode.id).append("<option value=" + n + ">第" + n + "位</option>");
								}
							}
						}
					}

				}
			}

		}
		var btn = $("#diyBtn1_" + treeNode.id);

		if(btn) btn.bind("click", function() {
			modalOperate.modalShow('look-sign-pos-modal');
			lookSignedGroupPos(treeNode.id, treeNode.signPosition);
		});

	}
	// 用于在节点上固定显示用户自定义控件--专题
	function addDiyDoms(treeId, treeNode) {
		if(treeNode.parentNode && treeNode.parentNode.id != 2) return;
		var aObj = $("#" + treeNode.tId + IDMark_A);
		for(var i = 0; i < $scope.data.length; i++) {
			var children = $scope.data[i].children;
			for(var a = 0; a < children.length; a++) {
				var childrens = children[a].children;
				if(children[a].zhanshuCount > 0) {
					if(treeNode.id == children[a].id) {
						var aLinkStr = "<a id='diyBtn1_" + treeNode.id + "' style='margin:0 0 0 5px;'>浏览位置</a>";
						var editStr = "<span id='diyBtn_space_" +
							children[a].id +
							"' >&nbsp;</span><select class='selDemo' id='diyBtn_" +
							treeNode.id +
							"'></select>";
						aObj.after(aLinkStr);
						aObj.after(editStr);
						for(var s = 0; s <= treeNode.zhanshuCount; s++) {
							if(s == 0) {
								$("#diyBtn_" + treeNode.id).append("<option value=" + s + ">不显示</option>");
							} else {
								$("#diyBtn_" + treeNode.id).append("<option value=" + s + ">第" + s + "位</option>");
							}
						}
					}
				}
			}

		}
		var btn = $("#diyBtn1_" + treeNode.id);

		if(btn) btn.bind("click", function() {
			modalOperate.modalShow('look-sign-pos-modals');
			lookSignedGroupPoss(treeNode.id);
		});

	}

	// 初始化签发树
	function initSignTree() {
		$.fn.zTree.init($("#tree"), settings, $scope.data);
		$.fn.zTree.init($("#treeDemo"), setting, $scope.zNodes);
	}
	// 获取稿件详情
	function getManuscriptDetails() {
		req.post('groupPicCtro/getGroupPics.do', {
			groupId: vm.groupId
		}).success(function(resp) {
			if(resp.code == '211') {
				vm.manuscriptDetail = resp.data;
				if(vm.manuscriptDetail.videoId!=null&&vm.manuscriptDetail.videoId!=0){
					vm.masUrl = vm.masBaseUrl+"&method=exPlay&type=vod&id="+vm.manuscriptDetail.videoId;
				}
				vm.masUrl =  $sce.trustAsResourceUrl(vm.masUrl);
				vm.manuscriptProperties = resp.data.properties;
				vm.manuscriptCates = resp.data.cates;
				vm.fristPfdUser = resp.data.fristPfdUser;
				vm.msDelRemark = resp.data.delRemark;
				vm.sensitiveWord = resp.msg;
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	// 稿件详情模态框隐藏
	vm.manuscriptDetailModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	}
	// 移动模态框
	vm.moveModal = function(dragDiv, tagDiv) {
		allModalMove.modalMove(dragDiv, tagDiv);
	};

	// 确认提交校审提交模态框显示
	vm.commitModalShow = function(modalId, groupStatus) {
		vm.groupStatus = groupStatus;
		modalOperate.modalShow(modalId);
	}
	// 删除模态框显示
	vm.delModalShow = function(modalId) {
		modalOperate.modalShow(modalId);
	}

	// 签发模态框显示
	vm.signModalShow = function(modalId) {
		if(vm.manuscriptCates.length == 0) {
			layer.alert('签发必须有稿件类别');
			return;
		} else if(vm.sensitiveWord) {
			layer.msg('此稿件存在敏感词：' + vm.sensitiveWord);
		}
		initSignTree();
		modalOperate.modalShow(modalId);

	}
	
	//	add by liu.jinfeng@20170904
	vm.signModalShow2 = function(modalId) {
		modalOperate.modalShow(modalId);
	}
	
	//签报
	vm.signManuscript2 = function(modalId) {
		//add by xiayunan@20180224  选择指定图片签报
		 var id_array=new Array();
        $('input[name="check"]:checked').each(function(){
            id_array.push($(this).val());//向数组中添加元素
        });
	     var picIds=id_array.join(',');//将数组元素连接起来以构建一个字符串
	     
	     if(id_array.length==0){
	         layer.alert("请选择要签报的图片");
	         return;
	     }
		 var v = $(":radio[name='checkpic']:checked").val();  
		 if(typeof(v)=="undefined"){
			 layer.alert("请选择要签报的类型");
			return;
		 }
		vm.loadUpMs = layer.load(1);
		signManuscript2(modalId,v,picIds);
	}
	function signManuscript2(modalId,v,picIds){
		req.post('groupPicCtro/signPic.do ', {
			groupId: vm.groupId,
			type: v,
			flag:1,
			picIds:picIds
		}).success(function(resp) {
			layer.close(vm.loadUpMs);
			if(resp.code == '211') {
				layer.alert("签报成功");
				modalOperate.modalHide("sign-manuscript-modal2");//add by xiayunan@20171204 签报成功，隐藏弹框 
				return;
			}
			if(resp.code == '213'){
				vm.qbmsg = resp.msg;
				modalOperate.modalHide("sign-manuscript-modal2");
				modalOperate.modalShow("commit-qd-modal");
			}
			//layer.alert(resp.msg);
		});
	}
	
	
	

	
	

	// 确认提交校审
	vm.confirmCommit = function(modalId) {
		if(vm.groupStatus == 1) {
			if(vm.manuscriptCates.length == 0) {
				layer.msg('提交前请先编辑稿件类别');
				modalOperate.modalHide(modalId);
				return;
			}
			//add by xiayunan@20171013
			if(vm.mydutyType.length==1){
				if(vm.mydutyType==1){
					fristSubmitGroupPic(modalId);
				//如果值班级别是二审或三审直接将待一审稿件进行二审提交
				}else if(vm.mydutyType==2||vm.mydutyType==3){
					firstAndSecondSubmitGroupPic(modalId);
				}
			}else if(vm.mydutyType.length>1){
				vm.mydutyType = vm.mydutyType[vm.mydutyType.length-1];
				if(vm.mydutyType==1){
					fristSubmitGroupPic(modalId);
				//如果值班级别是二审或三审直接将待一审稿件进行二审提交
				}else if(vm.mydutyType==2||vm.mydutyType==3){
					firstAndSecondSubmitGroupPic(modalId);
				}
			
			}
		}
		if(vm.groupStatus == 2) {
			secondSubmitGroupPic(modalId);
		}
	}
	// 一审提交
	function fristSubmitGroupPic(modalId) {
		req.post('groupPicCtro/fristSubmitGroupPic.do', {
			groupId: vm.groupId
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.msg('操作成功');
				modalOperate.modalHide(modalId);
				getManuscriptDetails();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	// 二审提交
	function secondSubmitGroupPic(modalId) {
		req.post('groupPicCtro/secondSubmitGroupPic.do', {
			groupId: vm.groupId
		}).success(function(resp) {
			if(resp.code == '211') {
				modalOperate.modalHide(modalId);
				layer.msg('操作成功');
				getManuscriptDetails();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
				modalOperate.modalHide(modalId);
			}
		});
	}
	
	// 一审和二审提交 add by xiayunan@20171010
	function firstAndSecondSubmitGroupPic(modalId) {
		req.post('groupPicCtro/fristSubmitGroupPic.do', {
			groupId: vm.groupId
		}).success(function(resp) {
			if(resp.code == '211') {
				secondSubmitGroupPic(modalId);
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
				modalOperate.modalHide(modalId);
			}
		});
	}
	

	// 点击管理控制下面详情div切换
	vm.toggleMangeOperateCon = function() {
		vm.mangeOperateFlag = !vm.mangeOperateFlag;
	}

	// 退稿模态框显示
	vm.backManuscriptModalShow = function(modalId) {
		modalOperate.modalShow(modalId);
	}
	// 确认退稿
	vm.confirmBackManuscript = function(modalId) {
		req_confirmBackManuscript(modalId);
	}

	// 确认退稿请求
	function req_confirmBackManuscript(modalId) {
		if(!vm.backManuscriptReson) {
			layer.alert('请填写退稿评语!');
			return;
		}
		req.post('groupPicCtro/retreatGroupPic.do', {
			groupId: vm.groupId,
			content: vm.backManuscriptReson
		}).success(function(resp) {
			if(resp.code == '211') {
				modalOperate.modalHide(modalId);
				layer.alert('操作成功');
				$state.go('role.manager.sendManuscript');
				getManuscriptDetails();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	// 确认删除稿子
	vm.confirmDelManuscript = function(modalId) {
		req_delManuscript(modalId);
	}

	// 确认删除稿子请求
	function req_delManuscript(modalId) {
		if(!vm.delManuscriptReson) {
			layer.alert('请填写删稿评语!');
			return;
		}
		req.post('groupPicCtro/delGroupAndMemo.do', {
			groupId: vm.groupId,
			memo: vm.delManuscriptReson
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.msg('操作成功');
				modalOperate.modalHide(modalId);
				getManuscriptDetails();
				$state.go('role.manager.sendManuscript');
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
	
	
	//新增忽略重复签报入口 add by xiayunan@20180222
	// 确认重复签报
	vm.confirmQbManuscript = function(modalId) {
		req_qbManuscript(modalId);
	}
	//确定重复签报请求
	function req_qbManuscript(modalId) {
		var v = $(":radio[name='checkpic']:checked").val(); 
		
		
		var id_array=new Array();
	    $('input[name="check"]:checked').each(function(){
	        id_array.push($(this).val());//向数组中添加元素
	    });
	    var picIds=id_array.join(',');//将数组元素连接起来以构建一个字符串
	     
	    if(id_array.length==0){
	        layer.alert("请选择要签报的图片");
	        return;
	    }
		
		req.post('groupPicCtro/signPic.do ', {
			groupId: vm.groupId,
			type: v,
			flag:0,
			picIds:picIds
		}).success(function(resp) {
			layer.close(vm.loadUpMs);
			if(resp.code == '211') {
				layer.alert("签报成功");
				modalOperate.modalHide("sign-manuscript-modal2");//add by xiayunan@20171204 签报成功，隐藏弹框 
				modalOperate.modalHide("commit-qd-modal");//add by xiayunan@20171204 签报成功，隐藏弹框 
				return;
			}
			
		});
	}
	
	
	
	
	

	// 内部留资弹框
	vm.innerLeaveInfo = function(modalId) {
		modalOperate.modalShow(modalId);
	/*	req.post('groupPicCtro/keepGroupPic.do', {
			groupId: vm.groupId
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.alert('操作成功');
				vm.mangeOperateFlag = !vm.mangeOperateFlag;
				getManuscriptDetails();
				layer.alert(resp.msg);
	 } else if(resp.msg != '未登录') {
			}
		});*/
	}
	//内部留资
	vm.innerLeave = function(modalId){
		req.post('groupPicCtro/keepGroupPic.do', {
			groupId: vm.groupId,
			info:vm.info,
			langType:$scope.langType
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.alert('操作成功');
				vm.mangeOperateFlag = !vm.mangeOperateFlag;
				getManuscriptDetails();
				layer.alert(resp.msg);
			} else if(resp.msg != '未登录') {
			}
		});
		modalOperate.modalHide(modalId);
	}
	// 开始编辑：编辑前锁定稿件
	vm.editSendManuscript = function() {
		req_beginEditSendManuscript();
	}

	// 开始编辑：编辑前锁定稿件请求
	function req_beginEditSendManuscript() {
		req.post('groupPicCtro/editBegin.do', {
			groupId: vm.groupId
		}).success(function(resp) {
			if(resp.code == '211') {
				$state.go('role.manager.sendManuscriptEdit', {
					id: vm.groupId,
					dtType: vm.dtType,
					type: vm.type
				});
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	// 签发
	vm.signManuscript = function(modalId) {
		vm.loadUpMs = layer.load(1);
		getSignParams(function() {
			req_signManuscript(modalId);
		});
		getSignParamss(function() {
			req_signManuscripts(modalId);
		});
	}
	//签发栏目
	function sign() {
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var column = new Array(); //定义一数组存放栏目id
		var nodes = treeObj.getCheckedNodes(true); //获取选中的值
		for(var i = 0; i < nodes.length; i++) {
			column.push(nodes[i].id);
		}
		var signColumn = [] //定义一数组存放栏目id和显示的位置
		for(var i = 0; i < column.length; i++) {
			var options = $("#diyBtn_" + column[i] + " " + "option:selected"); //获取选中的项的value
			var signId = options.val();
			signColumn.push({
				signId: signId,
				columnId: column[i]
			});

		}
		req.post('enSign/signEnColumn.do', {
			"groupId": vm.groupId,
			"signColumn": angular.toJson(signColumn),
			"langType": $scope.langType,
			"user": vm.uName
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.msg('操作成功')
				getManuscriptDetails();
				modalOperate.modalHide(modalId);
				initSetting();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});

	}
	// 签发请求
	function req_signManuscript(modalId) {
		if($scope.langType != 0) {
			var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
			var column = new Array(); //定义一数组存放栏目id
			var nodes = treeObj.getCheckedNodes(true); //获取选中的值
			for(var i = 0; i < nodes.length; i++) {
				column.push(nodes[i].id);
			}
			var signColumn = [] //定义一数组存放栏目id和显示的位置
			for(var i = 0; i < column.length; i++) {
				var options = $("#diyBtn_" + column[i] + " " + "option:selected"); //获取选中的项的value
				var signId = options.val();
				signColumn.push({
					signId: signId,
					columnId: column[i]
				});
			}
		}
		req.post('groupPicCtro/threeSubmitGroupPic.do', {
			groupId: vm.groupId,
			cateData: angular.toJson(vm.signReqParamData),
			"signColumn": angular.toJson(signColumn),
			"langType": $scope.langType,
			"userName": vm.uName
		}).success(function(resp) {
			layer.close(vm.loadUpMs);
			if(resp.code == '211') {
				layer.msg('操作成功');
				getManuscriptDetails();
				modalOperate.modalHide(modalId);
				initSetting();
				$state.go('role.manager.sendManuscript');
			} else if(resp.code == '100'){
            	layer.alert(resp.msg);
            }else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}

	function req_signManuscripts(modalId) {
		if($scope.langType != 0) {
			var treeObj = $.fn.zTree.getZTreeObj("tree");
			var lanmu = new Array(); //定义一数组存放栏目id			
			var nodes = treeObj.getCheckedNodes(true); //获取选中的值
			for(var i = 0; i < nodes.length; i++) {
				lanmu.push(nodes[i].id);
			}
			var signlanmu = [] //定义一数组存放栏目id和显示的位置
			for(var i = 0; i < lanmu.length; i++) {
				var options = $("#diyBtn_" + lanmu[i] + " " + "option:selected"); //获取选中的项的value
				var signId = options.val();
				vm.signlanmu.push({
					position: signId,
					lanmuId: lanmu[i]
				});
			}
		}
		for(var i = 0; i < vm.signlanmu.length; i++) {
			req.post('lanmu/addLanmuPic.do', {
				groupId: vm.groupId,
				potision: vm.signlanmu[i].position,
				lanmuId: vm.signlanmu[i].lanmuId,
				"langType": $scope.langType
			}).success(function(resp) {
				layer.close(vm.loadUpMs);
				if(resp.code == '211') {
					layer.msg('操作成功----');
					getManuscriptDetails();
					modalOperate.modalHide(modalId);
					initSetting();
					$state.go('role.manager.sendManuscript');
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
		}
	}
	// 强制解锁
	vm.unlockManuscript = function() {
		req_unlockManuscript();
	}
	// 强制解锁请求
	function req_unlockManuscript() {
		vm.type = 1;
		req.post('groupPicCtro/unLockGroup.do', {
			groupId: vm.groupId,
		}).success(function(resp) {
			if(resp.code == '211') {
				layer.alert('操作成功');
				getManuscriptDetails();
			} else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
		});
	}
});