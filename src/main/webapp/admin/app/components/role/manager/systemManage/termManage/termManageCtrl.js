/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('termManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove) {
    var vm = this;

    //从cookie获取userToken
    vm.userToken = $cookies.get('userToken');

    //检索词模态框隐藏
    vm.termModalHide = function (modalId) {
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
    vm.moveModal = function (dragDiv, tagDiv) {
        allModalMove.modalMove(dragDiv, tagDiv);
    };

    //检索词管理添加模态框显示
    vm.termModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };

    //检索词管理添加模态框隐藏
    vm.termModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };

    //初始化相关配置
    function initSetting(){
        //存放检索词数组
        vm.termSearchArray = [];
        //检索词总条数
        vm.termList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }

    //初始化
    function init(){
        initSetting();
        getTermTableData("", 1, 1,3);
    }
    init();
    /**
     * 获取检索词表格数据
     */
    function getTermTableData(searchWord, page, type,isSearch) {
    	var searchUrl = "";
        var paramsObj = {
            page: page,
            rows: vm.selPageRows,
            langType:window.localStorage.lang,
            userToken: vm.userToken
        };
        if ((searchWord != null || searchWord != "" || typeof(searchWord) == "undefined" )&& isSearch==0) {
        	paramsObj['paramter'] = searchWord;
        }else if(isSearch==1){
        	paramsObj = {
              page: page,
              rows: vm.selPageRows,
              langType:window.localStorage.lang,
              scWord:vm.scWord,
              scWordId:vm.scWordId,
              count:vm.count,
              beginTime:vm.beginTime,
              endTime:vm.endTime
            };
        }
        req.post("scWordCtro/getScWordByQuery.do", paramsObj).success(function (resp) {
            if (resp.code == '211') {
                vm.termSearchArray = resp.data;
                vm.totalPages = resp.page;
                vm.termList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //页数变化
    vm.pageChanged = function (pageNumber) {
        getTermTableData("", 1, 1,3);
    };

    /**
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.termSearchArray.map(function (item) {
                return item.ID
            });
        } else {
            vm.checkBoxArray = vm.termSearchArray.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('termManage.checkBoxArray', function (newC) {
        if (newC.every(function (item) {
                return item != false;
            })) {
            vm.isHadAllCheck = true;
        } else {
            vm.isHadAllCheck = false;
        }
    });

    /**
     * 点击搜索
     */
    vm.onSearchDataClick = function () {
        getTermTableData(vm.termSearchModel, 1, 1,0);
    };
    /*
     * 高级检索
     */
    vm.termAdvanceSearch = function() {
		//搜索之前把当前页重置为1
		vm.pagination.current = 1;
		getTermTableData('',1, 1, 1);
		$('#term-sele-modal').hide();		
	};
    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.onSearchDataClick();
        }
    };

    /**
     * 点击删除
     */
    vm.deleteDataParamsId = "";
    vm.onShowDeleteModelClick = function (deleteType, mineDeleteId) {
        if (deleteType == -1) {
            vm.deleteDataParamsId = mineDeleteId;
            vm.termModalShow('term-del-modal');
        } else {
            var paramsId = "";
            for (var c = 0; c < vm.checkBoxArray.length; c++) {
                var checkBoxItem = vm.checkBoxArray[c];
                if (checkBoxItem != false) {
                    paramsId += checkBoxItem + ",";
                }
            }
            if (paramsId != "") {
                vm.deleteDataParamsId = paramsId.substr(0, paramsId.length - 1);
                vm.termModalShow('term-del-modal');
            } else {
                layer.alert("请选择要删除的检索词");
            }
        }
    };

    /**
     * 删除网站信息表格数据
     */
    vm.onDeleteTermTableData = function () {
        req.post('scWordCtro/delScWordByIds.do', {
            scWordIds: vm.deleteDataParamsId,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.termModalHide('term-del-modal');
                layer.alert('删除成功');
                getTermTableData("", 1, 1,3);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

});