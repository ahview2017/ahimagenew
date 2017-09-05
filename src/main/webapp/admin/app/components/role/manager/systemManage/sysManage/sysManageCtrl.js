/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('sysManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove) {
    var vm = this;

    //从cookie获取userToken
    vm.userToken = $cookies.get('userToken');

    //系统配置模态框隐藏
    vm.sysModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };
    //移动模态框
    vm.moveModal = function (dragDiv, tagDiv) {
        allModalMove.modalMove(dragDiv, tagDiv);
    };
    //系统配置添加模态框显示
    vm.sysAddModalShow = function (modalId) {
        vm.add.configName = "";
        vm.add.configCode = "";
        vm.add.configValue = "";
        vm.add.configMemo = "";
        modalOperate.modalShow(modalId);
    };
    //系统配置修改模态框显示
    vm.sysUpdateModalShow = function (modalId) {
        vm.selKeyArr = [];
        judgeIfSelData();
        console.log(typeof vm.selKeyArr);
        if (vm.selKeyArr.length != 1) {
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if (vm.selKeyArr.length == 1) {
            angular.forEach(vm.sysConfigList, function (item, index) {
                if (item.id == vm.selKeyArr[0]) {
                    vm.willUpdateConfig = item;
                    console.log(vm.willUpdateConfig);
                }
            });
            vm.modify.configName = vm.willUpdateConfig.configName;
            vm.modify.configCode = vm.willUpdateConfig.configCode;
            vm.modify.configValue = vm.willUpdateConfig.configValue;
            vm.modify.configMemo = vm.willUpdateConfig.configMemo;
            modalOperate.modalShow(modalId);
        }
    };
    //系统配置删除模态框显示
    vm.sysDelModalShow = function (modalId) {
        judgeIfSelData();
        if (vm.selKeyArr.length == 0) {
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if (vm.selKeyArr.length > 0) {
            modalOperate.modalShow(modalId);
        }
    };
    //系统配置在列表里修改单条记录
    vm.sysUpdateItemModalShow = function (modalId, item) {
        vm.configItemId = item.id;
        vm.modify.configName = item.configName;
        vm.modify.configCode = item.configCode;
        vm.modify.configValue = item.configValue;
        vm.modify.configMemo = item.configMemo;
        modalOperate.modalShow(modalId);
    };
    //系统配置在列表里删除单条记录
    vm.sysDelItemModalShow = function (modalId, item) {
        vm.configItemId = item.id;
        modalOperate.modalShow(modalId);
    };
    //初始化设置
    function initSetting() {
        vm.add = {
            configName: '',
            configCode: '',
            configValue: '',
            configMemo: ''
        };
        vm.modify = {
            configName: '',
            configCode: '',
            configValue: '',
            configMemo: ''
        }
        //存放系统配置数组
        vm.sysConfigList = [];
        //系统配置总条数
        vm.sysConfigList_total = 0;
        //默认当前页1
        vm.pagination = {
        current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }

    //初始化
    function init() {
        initSetting();
        getSysConfigList("", 1);
    }

    init();

    //获取系统配置列表
    function getSysConfigList(searchName, pageNum) {
        req.post('sysConfigCtro/getConfig.do', {
            strWhere: searchName,
            page: pageNum,
            rows: vm.selPageRows,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.sysConfigList = resp.data;
                vm.totalPages = resp.page;
                vm.sysConfigList_total = resp.other;
                vm.pagination.current = pageNum;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getSysConfigList('',pageNumber);
    };


    //添加系统配置
    vm.addSysConfig = function (modalId) {
        if (!vm.add.configName) {
            layer.alert('请填写配置名称');
            return;
        }
        if (!vm.add.configCode) {
            layer.alert('请填写配置名');
            return;
        }
        if (!vm.add.configValue) {
            layer.alert('请填写配置值');
            return;
        }
        req_addSysConfig(modalId);
    };
    //添加系统配置请求
    function req_addSysConfig(modalId) {
        req.post('sysConfigCtro/addConfig.do', {
            configName: vm.add.configName,
            configCode: vm.add.configCode,
            configValue: vm.add.configValue,
            configMemo: vm.add.configMemo,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                getSysConfigList("", 1);
                modalOperate.modalHide(modalId);
                layer.msg('操作成功');
            } else if (resp.code == '514') {
                layer.alert(resp.msg);
            }
            else {
                console.log(resp.msg);
            }
        });
    }

    /**
     * 点击搜索
     */
    vm.onSearchSysManageClick = function () {
        getSysConfigList(vm.searchSysManageModel, 1);
    };

    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.onSearchSysManageClick();
        }
    };

    /**
     * 处理全选
     */
    vm.checkBoxArray = [];
    vm.isHadAllCheck = false;
    vm.onCheckAllClick = function () {
        if (vm.isHadAllCheck) {
            vm.checkBoxArray = vm.sysConfigList.map(function (item) {
                return item.id
            });
        } else {
            vm.checkBoxArray = vm.sysConfigList.map(function (item) {
                return false
            });
        }
    };

    /**
     * 监听每一个选项的改变
     */
    $scope.$watchCollection('sysManage.checkBoxArray', function (newC) {
        if (newC.every(function (item) {
                return item != false;
            })) {
            vm.isHadAllCheck = true;
        } else {
            vm.isHadAllCheck = false;
        }
    });


    //判断是否选择了数据
    function judgeIfSelData() {
        vm.selKeyArr = [];
        for (var j = 0; j < vm.checkBoxArray.length; j++) {
            var checkBoxItem = vm.checkBoxArray[j];
            if (checkBoxItem != false) {
                vm.selKeyArr.push(checkBoxItem);
            }
        }
    }

    //获取选中配置ID
    function getConfigId() {
        vm.finalConfigIds = '';
        for (var c = 0; c < vm.checkBoxArray.length; c++) {
            var checkBoxItem = vm.checkBoxArray[c];
            if (checkBoxItem != false) {
                vm.finalConfigIds += checkBoxItem + ",";
            }
        }
        if (vm.configItemId) {
            vm.ConfigIds = vm.configItemId;
        } else {
            vm.ConfigIds = vm.finalConfigIds.slice(0, vm.finalConfigIds.length - 1);
        }
    }

    //修改系统配置请求
    vm.updateSysConfig = function (modalId) {
        req_updateSysConfig(modalId);
    };
    //修改系统配置请求
    function req_updateSysConfig(modalId) {
        if (!vm.modify.configName) {
            layer.alert('请填写配置名称');
            return;
        }
        if (!vm.modify.configCode) {
            layer.alert('请填写配置名');
            return;
        }
        if (!vm.modify.configValue) {
            layer.alert('请填写配置值');
            return;
        }
        getConfigId();
        req.post('sysConfigCtro/editConfig.do', {
            configName: vm.modify.configName,
            configCode: vm.modify.configCode,
            configValue: vm.modify.configValue,
            configMemo: vm.modify.configMemo,
            id: vm.ConfigIds,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.msg('操作成功');
                getSysConfigList("", 1);
                modalOperate.modalHide(modalId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //删除系统配置
    vm.delSysConfig = function (modalId) {
        req_delSysConfig(modalId);
    };
    //删除系统配置请求
    function req_delSysConfig(modalId) {
        getConfigId();
        req.post('sysConfigCtro/delConfigByIds.do', {
            configIds: vm.ConfigIds,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                getSysConfigList("", 1);
                modalOperate.modalHide(modalId);
                layer.msg('操作成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
});