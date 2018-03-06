/**
 * Created by Sun on 2016/12/12.
 */
adminModule.controller('mSendManuscriptCtrl', function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow ,modalOperate, getMyDuty, allModalMove, $stateParams, $window, $document){
    var vm = this;
    // 获取用户名
	vm.uName = $cookies.get('admin_uname');
	$scope.langType = window.localStorage.lang;
    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }
    //初始化设置
    function initSetting(){
        //默认激活的导航项为新闻稿件
        vm.acitiveSlideTit = 0;
        vm.edtAllot = 1;
        vm.selWaitMsIds = {};
        vm.gIds = '';
        vm.search = {
            id:'',
            title:'',
            author:'',
            place:'',
            editor:'',
            memo:'',
            fileName:'',
            beginTime:'',
            endTime:'',
            paramStr:''
        }
        vm.dtTypeifShow = false;
        //存放待发稿件数组
        vm.waitManuscriptList = [];
        //待发稿件总条数
        vm.sendMsList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页6条
        vm.selPageRows = '6';
        //从cookie获取角色id
        vm.reMyRoleId = $cookies.get('admin_roleId');
        
        // 存储签发参数的数组	add by xiayunan@201711215
		vm.signReqParamData = [];
		vm.signlanmu = [];
		vm.type = 0;
		vm.delManuscriptReson = '废稿';
		vm.signId = 0;
    }
    
    //初始化
    function init(){
        initSetting();
        //从service里取得我的值班级别的数据，实现数据持久化
        getMyDuty.req_getMyDuty(function(type){
            //存储角色拥有的值班级别
            if($window.localStorage['lang'] == 0) {
                if(!$window.localStorage['userDutyLevelZh']){
                    $window.localStorage['userDutyLevelZh']=JSON.stringify(type);
                }
                vm.variedtypeArr = JSON.parse($window.localStorage['userDutyLevelZh'] || '[]');
                
                
            } else if($window.localStorage['lang'] == 1){
                if(!$window.localStorage['userDutyLevelEn']){
                    $window.localStorage['userDutyLevelEn']=JSON.stringify(type);
                }
                vm.variedtypeArr = JSON.parse($window.localStorage['userDutyLevelEn'] || '[]');
            }
            req_getWaitManuscript(1);
        })
        
        getSignBaseColum();
    }
    init();
    
    
    function getSignBaseColum(){
    	req.get('groupPicCtro/getSignBaseColumn.do').success(function(resp) {
			if(resp.code == '211') {
				vm.signId = resp.data.signBasecolumnId;
			}else if(resp.msg != '未登录') {
				layer.alert(resp.msg);
			}
    	});
    }
    
    
    //切换值班级别
    vm.toggleProofGrade = function(){
        vm.finalGIds = '';
        vm.dtTypeifShow = !vm.dtTypeifShow;
        $rootScope.dutyTypeClickFlag = true;
    }

    //回车查询
    vm.enterQuery = function (e) {
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.onSearchDataClick();
        }
    };
    //待发稿件模态框隐藏
    vm.sendManuscriptModalHide = function(modalId){
        modalOperate.modalHide(modalId);
        vm.selWaitMsIds = {};
    }
    //稿件合并模态框显示
    vm.mergeManuscriptModalShow = function(modalId){
        vm.selKeyArr = [];
        judgeIfSelData();
        if(vm.selKeyArr.length == 0 ){
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if((vm.selKeyArr.length > 0)){
            modalOperate.modalShow(modalId);
        }
    }

    //初始化搜索对象
    function initSearchSetting(){
        if(vm.search && vm.search.id){
            vm.search.id = '';
        }
        if(vm.search && vm.search.title){
            vm.search.title = '';
        }
        if(vm.search && vm.search.author){
            vm.search.author = '';
        }
        if(vm.search && vm.search.place){
            vm.search.place = '';
        }
        if(vm.search && vm.search.editor){
            vm.search.editor = '';
        }
        if(vm.search && vm.search.memo){
            vm.search.memo = '';
        }
        if(vm.search && vm.search.fileName){
            vm.search.fileName = '';
        }
        if(vm.search && vm.search.beginTime){
            vm.search.beginTime = '';
        }
        if(vm.search && vm.search.endTime){
            vm.search.endTime = '';
        }
        if(vm.search && vm.search.paramStr){
            vm.search.paramStr = '';
        }
    }

    //选择激活的导航项
    vm.chooseManuscriptType = function(gType){
        vm.acitiveSlideTit = gType;
        vm.search.paramStr = '';
        vm.pagination.current = 1;
        vm.selWaitMsIds = {};
        initSearchSetting();
        req_getWaitManuscript(1);
    
    }

    vm.onSearchDataClick = function(){
        vm.pagination.current = 1;
        req_getWaitManuscript(1);
    }
    //下载图片
    vm.downLoadPic = function (){
    	var type=0;//只下载图
        var id_array=new Array();
            $('input:checked').each(function(){
                id_array.push($(this).val());//向数组中添加元素
            });
        var picIds=id_array.join(',');//将数组元素连接起来以构建一个字符串
        if(id_array.length==0){
            layer.alert("请选择图片");
            return;
        }else{
        	document.location = "/photo/enGroupPicDown/downSinglePic.do?picIds=" + picIds+"&type="+type;
        }        
    }
    
    //一键签报  add by xiayunan@20171205
    vm.signGroups = function (modalId){
    	 var v = $(":radio[name='checkpic']:checked").val();  
		 if(typeof(v)=="undefined"){
			 layer.alert("请选择要签报的类型");
			return;
		 }
		 signGroups(modalId,v);
    }
    
    function signGroups(modalId,v){
		req.post('groupPicCtro/signGroups.do ', {
			signIds: vm.signIds,
			type: v,
			flag:1
		}).success(function(resp) {
			if(resp.code == '211') {
				modalOperate.modalHide("sign-manuscript-modal2");//add by xiayunan@20171204 签报成功，隐藏弹框
				req_getWaitManuscript(1);
				vm.waitId = {};
                vm.selWaitMsIds = {};
				layer.alert("签报成功");
				return;
			}else if(resp.code == '213'){
                //layer.alert(resp.msg);
				vm.qbmsg = resp.msg;
				modalOperate.modalHide("sign-manuscript-modal2");
				modalOperate.modalShow("commit-qd-modal");
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
		req.post('groupPicCtro/signGroups.do ', {
			signIds: vm.signIds,
			type: v,
			flag:0
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
    
    
    // 稿件详情模态框隐藏
	vm.manuscriptDetailModalHide = function(modalId) {
		modalOperate.modalHide(modalId);
	}
    
    
    
    //一键签报	add by xia.yunan@20171205
	vm.signModalShow2 = function(modalId) {
		vm.selKeyArr = [];
    	vm.signIds = '';
        for(var key in vm.selWaitMsIds){
            if(vm.selWaitMsIds[key]){
                vm.selKeyArr.push(key);
            }
        }
        if(!vm.selKeyArr.length){
            layer.alert('请至少选中一个稿件');
            return;
        }
        for(var key in vm.selKeyArr){
            if((key != vm.selKeyArr.length-1)){
            	vm.signIds += vm.selKeyArr[key] + ',';
            }else{
            	vm.signIds += vm.selKeyArr[key];
            }
        }
		modalOperate.modalShow(modalId);
	}
	
	
	
	
	
	
	
	//一键签库  add by xiayunan@20171215
	vm.transferDataBase = function(modalId){
		vm.loadUpMs = layer.load(1);
		var signId = 0;
		if(vm.signId!=null&&typeof(vm.signId)!="undefined"){
			signId = vm.signId;
		}
		vm.signReqParamData.push({
			type: '0',
			signId: signId,
			position: null
		});
		for(var key in vm.selKeyArr){
			req.post('groupPicCtro/threeSubmitGroupPic.do', {
				groupId: vm.selKeyArr[key],
				cateData: angular.toJson(vm.signReqParamData),
				"signColumn": null,
				"langType": $scope.langType,
				"userName": vm.uName
			}).success(function(resp) {
				layer.close(vm.loadUpMs);
				if(resp.code == '211') {
					modalOperate.modalHide(modalId);
					req_getWaitManuscript(1);
					layer.msg('操作成功');
				} else if(resp.code == '100'){
	            	layer.alert(resp.msg);
	            }else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
        }
	}
	
	
	//一键签库模态框显示
    vm.transferDatabaseModalShow = function(modalId){
    	vm.selKeyArr = [];
    	vm.signIds = '';
        for(var key in vm.selWaitMsIds){
            if(vm.selWaitMsIds[key]){
                vm.selKeyArr.push(key);
            }
        }
        if(!vm.selKeyArr.length){
            layer.alert('请至少选中一个稿件');
            return;
        }
       
        modalOperate.modalShow(modalId);
    }
	
	
    
    //自动分配 or 显示全部
    vm.chooseEdtAllot = function(edtAllot){
        vm.edtAllot = edtAllot;
    }
    //高级检索模态框显示
    vm.advanceSearchModalShow = function(modalId){
        modalOperate.modalShow(modalId);
    }
    //保存高级检索
    vm.saveAdvanceSearch = function(modalId){
        vm.pagination.current = 1;
        req_getWaitManuscript(1,modalId);
    }
    //作者信息弹框
	vm.showAuthorinfor = function() {
		$("#authorinformation-modal").show();
	}
	vm.hideAuthorinfor = function() {
		$("#authorinformation-modal").hide();
	}
	//编辑展示信息弹框
    vm.showEditorInfor = function() {
        $("#editorInformation-modal").show();
    }
    vm.hideEditorInfor = function() {
        $("#editorInformation-modal").hide();
    }

    //获取所有一审编辑
    vm.getAllEditor = function (groupId,editorName) {
        // alert(editorName);
        req.post('proofreadCtro/getDutyUser.do',{
            dutyType: 1,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                vm.dutyEditorList = resp.data;
                vm.editorNameFlag = editorName;
                vm.checkedGroupId = groupId;
                vm.showEditorInfor();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });

    }
    //修改一审编辑
    vm.saveSelDutyEditor = function(){
        var editorNameFlag = $("input[name='editorName']:checked").val();
        // alert(editorNameFlag+":"+vm.checkedGroupId);
        req.post('proofreadCtro/updateDutyUser.do',{
            groupId: vm.checkedGroupId,
            editorName:editorNameFlag
        }).success(function(resp){
            if(resp.code == '211'){
                vm.hideEditorInfor();
                req_getWaitManuscript(1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

	//获取作者信息
	vm.authorinfor = function(authorId) {
		req.post('enGetGroupPicCtro/selCpUserBasicInfo.do', {
			id:authorId
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
    //选择我的校审级别
    vm.chooseProofGrade = function(index){
        vm.selWaitMsIds = {};
        vm.finalGIds = '';
        $rootScope.dutyTypeClickFlag = true;
        vm.dtTypeifShow = !vm.dtTypeifShow;
        vm.variedtypeArr.unshift(vm.variedtypeArr[index]);
        vm.variedtypeArr.splice(index + 1,1);
        if($window.localStorage['lang'] == 0) {
            $window.localStorage['userDutyLevelZh']=JSON.stringify(vm.variedtypeArr);
        } else if ($window.localStorage['lang'] == 1) {
            $window.localStorage['userDutyLevelEn']=JSON.stringify(vm.variedtypeArr);
        }
        req_getMyDuty(function(){
            req_getWaitManuscript(1);
        });
    }
    //获取我的值班级别
    function req_getMyDuty(callback){
        req.post('groupPicCtro/getMyDuty.do',{langType: window.localStorage.lang}).success(function(resp){
            if(resp.code == '211'){
                vm.dtType = resp.data;
                if (callback) callback();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //绑定页面单击事件，关闭角色切换的div
    $document.bind("click", function(event) {
        if(event.target.className.indexOf('duty-type-toggle-click') != -1){
            $rootScope.dutyTypeClickFlag = true;
        }else{
            $rootScope.dutyTypeClickFlag=false;
        }
        $scope.$apply();
    });
    //获取待编库稿件列表
    function req_getWaitManuscript(pageNum,modalId){
        if(vm.search.id && !(/^\d+$/.test(vm.search.id))){
            layer.alert('编号为正整数');
            return;
        }
        if(vm.search.beginTime > vm.search.endTime){
            layer.alert('开始时间不能大于结束时间！');
            return;
        }
       // vm.loadUpMs = layer.load(1);
        req.post('groupPicCtro/getWaitSignGroups.do',{
            page: pageNum,
            rows: vm.selPageRows,
            gType: vm.acitiveSlideTit,
            dtType:  vm.variedtypeArr[0],
            id: vm.search.id,
            title: vm.search.title,
            author: vm.search.author,
            place: vm.search.place,
            editor: vm.search.editor,
            memo: vm.search.memo,
            fileName: vm.search.fileName,
            beginTime: vm.search.beginTime,
            endTime: vm.search.endTime,
            paramStr: vm.search.paramStr,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
            	//layer.close(vm.loadUpMs);
                vm.waitManuscriptList = resp.data;
                modalOperate.modalHide(modalId);
                vm.totalPages = resp.page;
                vm.sendMsList_total = resp.other;
            }else if(resp.code == '215' ){// add by xiayunan@20180226
				//layer.close(vm.loadUpMs);
				layer.alert("检索库最多只能输入5个关键词！");
			}else if(resp.msg != '未登录'){
				//layer.close(vm.loadUpMs);
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        req_getWaitManuscript(pageNumber);
    };

    //获取选中稿件ID
    function getWaitMsIds(){
        vm.finalWaitMsIds = '';
        for(var key in vm.selWaitMsIds){
            if(vm.selWaitMsIds[key]){
                vm.finalWaitMsIds += key + ',';
            }
        }
        if(vm.waitItemId){
            vm.waitMsIds = vm.waitItemId;
        }else{
            vm.waitMsIds = vm.finalWaitMsIds.slice(0,vm.finalWaitMsIds.length - 1);
        }
    }
    //过滤需要合并的稿件
    vm.filterMergedMenuscript = function(item){
            for(var key in vm.selWaitMsIds){
                if(key == item.ID && vm.selWaitMsIds[key]){
                    return true;
                }
            }
    }
    //批量彻底删除稿件模态框显示
    vm.thoroughDelModalShow = function(modalId){
        vm.selKeyArr = [];
        judgeIfSelData();
        if(vm.selKeyArr.length == 0 ){
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if((vm.selKeyArr.length > 0)){
            modalOperate.modalShow(modalId);
        }
    }
    //批量彻底删除稿件模态框显示-在列表里点删除（一条条删除）
    vm.thoroughDelItemModalShow = function(modalId,waitId){
        vm.waitItemId = waitId;
        modalOperate.modalShow(modalId);
    }
    //判断是否选择了数据
    function judgeIfSelData(){
        vm.selKeyArr = [];
        for(var key in vm.selWaitMsIds){
            if(vm.selWaitMsIds[key]){
                vm.selKeyArr.push(key);
            }
        }
    }
    //全选
    vm.checkAll = function(picSelect,picsType){
        if(picsType == vm.acitiveSlideTit){
            angular.forEach(vm.waitManuscriptList, function(item,index){
                if(picSelect){
                    vm.selWaitMsIds[item.ID] = true;
                }else{
                    vm.selWaitMsIds[item.ID] = false;
                }
            });
        }
    }
    //批量彻底删除稿件
    vm.thoroughDel = function(modalId){
        req_thoroughDel(modalId);
    }
    //彻底删除稿件请求
    function req_thoroughDel(modalId){
        getWaitMsIds();
        var reqData = {
            groupIds: vm.waitMsIds
        };
        req.post('groupPicCtro/delGroups.do',reqData).success(function(resp){
            if(resp.code == '211') {
                req_getWaitManuscript(1);
                modalOperate.modalHide(modalId);
                vm.waitId = {};
                vm.selWaitMsIds = {};
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //恢复图片
    vm.recoverPic = function(){
        req_recoverPic();
    }
    //恢复图片请求
    function req_recoverPic(){
        getWaitMsIds();
        if(!vm.waitMsIds){
            layer.alert('请至少选择一条数据进行操作');
            return;
        }
        var reqData = {
            groupIds: vm.waitMsIds
        };
        req.post('groupPicCtro/reNewGroups.do',reqData).success(function(resp){
            if(resp.code == '211'){
                layer.alert('恢复成功');
                req_getWaitManuscript(1);
                vm.waitId = {};
                vm.selWaitMsIds = {};
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //稿件合并
    vm.comfirmMergeManuscript = function(modalId){
        req_mergeManuscript(modalId);
    }
    //获取稿件合并的稿件ID
    function getNeedMergeMsIds(){
        vm.finalGIds = '';
        vm.gIds = '';
        for(var key in vm.selWaitMsIds){
            if((key != vm.selMergeWaitMsIds)){
                vm.gIds += key + ',';
            }
        }
        vm.finalGIds = vm.gIds.slice(0,vm.finalGIds.length - 1);
    }
    //稿件合并请求
    function req_mergeManuscript(modalId){
        if(!vm.selMergeWaitMsIds){
            layer.alert('请至少选中一个稿件');
            return;
        }
        getNeedMergeMsIds();
        var reqData = {
            groupId: vm.selMergeWaitMsIds,
            gIds:  vm.finalGIds
        };
        req.post('groupPicCtro/mergeGroups.do',reqData).success(function(resp){
            if(resp.code == '211'){
                layer.alert('稿件合并成功');
                vm.selWaitMsIds = {};
                modalOperate.modalHide(modalId);
                req_getWaitManuscript(1);
                vm.selWaitMsIds = {};
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
                vm.selWaitMsIds = {};
            }
        });
    }
    
    
    //删除模态框显示  add by xiayunan@20171218
    vm.delGroupsModalShow = function(modalId){
    	vm.selKeyArr = [];
    	vm.signIds = '';
        for(var key in vm.selWaitMsIds){
            if(vm.selWaitMsIds[key]){
                vm.selKeyArr.push(key);
            }
        }
        if(!vm.selKeyArr.length){
            layer.alert('请至少选中一个稿件');
            return;
        }
        modalOperate.modalShow(modalId);
    }
    
 
    
    // 确认删除稿子
	vm.delGroupsPic = function(modalId) {
		req_delManuscript(modalId);
	}

	// 确认删除稿子请求
	function req_delManuscript(modalId) {
		if(!vm.delManuscriptReson) {
			layer.alert('请填写删稿评语!');
			return;
		}
		for(var key in vm.selKeyArr){
			req.post('groupPicCtro/delGroupAndMemo.do', {
				groupId: vm.selKeyArr[key],
				memo: vm.delManuscriptReson
			}).success(function(resp) {
				if(resp.code == '211') {
					modalOperate.modalHide(modalId);
					req_getWaitManuscript(1);
					layer.msg('操作成功');
				} else if(resp.msg != '未登录') {
					layer.alert(resp.msg);
				}
			});
        }
		
	}


});