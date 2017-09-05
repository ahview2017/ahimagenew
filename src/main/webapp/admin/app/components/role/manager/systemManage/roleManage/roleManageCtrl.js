/**
 * Created by fangfang on 2016/11/25.
 */

adminModule.controller('roleManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, modalOperate, allModalMove) {
    var vm = this;


    //从cookie获取userToken
    vm.userToken = $cookies.get('userToken');
    
    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }

    var singleDelClickFlag = false; // item 的删除按钮是否被点击
    //初始化相关配置
    function initSetting() {
        //角色添加对象
        vm.add = {
            roleName: '',
            roleCategory: '',
            roleOrderNum: '',
            remark: '',
            langType:'0'
        };
        //角色修改对象
        vm.modify = {
            roleName: vm.oneRoleName,
            roleCategory: vm.oneRoleId,
            roleOrderNum: vm.oneRoleOrderNum,
            remark: vm.oneRoleMemo,
            langType: vm.langType
        };
        //权限分类对象
        vm.rightSort = {
            manageFn:{
                sortName: '管理功能',
                sortArr:[]
            },
            manuscriptEdit:{
                sortName: '稿件编辑',
                sortArr:[]
            },
            dataStatistics:{
                sortName: '数据统计',
                sortArr:[]
            }
        }
        //选中的权限ID
        vm.selRightIds = {};
        //选中角色ID
        vm.selRoleIds = {};
        vm.roleItemId = '';
        //默认搜索字段为空
        vm.searchRoleTxt ='';

        //存放角色数组
        vm.roleList = [];
        //在线角色总条数
        vm.roleList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页20条
        vm.selPageRows = '10';
    }

    //初始化
    function init() {
        initSetting();
        getRoleList(vm.pagination.current, 1);
    }

    init();

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getRoleList(pageNumber, 1);
    };

    //角色添加模态框显示
    vm.AddRoleModalShow = function (modalId) {
        vm.selKeyArr = [];
        vm.selRightIds = {};
        judgeIfSelData();
        getNeedRights();
        modalOperate.modalShow(modalId);
    };
    //角色修改模态框显示
    vm.updateRoleModalShow = function (modalId) {
        vm.selKeyArr = [];
        vm.selRightIds = {};
        judgeIfSelData();
        console.log(typeof vm.selKeyArr);
        if (vm.selKeyArr.length != 1) {
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if (vm.selKeyArr.length == 1) {
            angular.forEach(vm.roleList, function (item, index) {
                if (item.ID == vm.selKeyArr[0]) {
                    vm.willUpdateRole = item;
                    console.log(vm.willUpdateRole);
                }
            });
            vm.willUpdateRoleRights = vm.willUpdateRole.rights;
            vm.modify.roleName = vm.willUpdateRole.ROLE_NAME;
            vm.modify.roleCategory = vm.willUpdateRole.ROLE_NAME;
            vm.modify.roleOrderNum = vm.willUpdateRole.NUM_ORDER;
            vm.modify.remark = vm.willUpdateRole.MEMO;
            vm.modify.langType = ''+vm.willUpdateRole.LANG_TYPE+'';
            for (var i = 0, len = vm.willUpdateRoleRights.length; i < len; i++) {
                vm.selRightIds[vm.willUpdateRoleRights[i].ID] = true;
            }
            modalOperate.modalShow(modalId);
        }
        getNeedRights();
    };
    //获取需要修改的权限
    function getNeedRights(){
        //每次初始化要展示的权限对象
        vm.rightSort = {
            manageFn:{
                sortName: '管理功能',
                sortArr:[]
            },
            manuscriptEdit:{
                sortName: '稿件编辑',
                sortArr:[]
            },
            dataStatistics:{
                sortName: '数据统计',
                sortArr:[]
            }
        }
        //获取权限列表
        getRightList(function(){
            angular.forEach(vm.rightList,function(item,index){
                if(item.STANDBY1 == 1){
                    vm.rightSort.manageFn.sortArr.push(item);
                }
                if(item.STANDBY1 == 2){
                    vm.rightSort.manuscriptEdit.sortArr.push(item);
                }
                if(item.STANDBY1 == 3){
                    vm.rightSort.dataStatistics.sortArr.push(item);
                }
            });
        });
    }
    //角色修改模态框显示-只更新一项
    vm.updateItemRoleModalShow = function (modalId, roleItem) {
        getNeedRights();
        vm.roleItemId = roleItem.ID;
        vm.roleItemRights = roleItem.rights;
        vm.modify.roleName = roleItem.ROLE_NAME;
        vm.modify.roleCategory = roleItem.ROLE_NAME;
        vm.modify.roleOrderNum = roleItem.NUM_ORDER;
        vm.modify.remark = roleItem.MEMO;
        vm.modify.langType = ''+roleItem.LANG_TYPE+'';
        vm.selKeyArr = [];
        vm.selRightIds = {};
        judgeIfSelData();
        for (var i = 0, len = vm.roleItemRights.length; i < len; i++) {
            vm.selRightIds[vm.roleItemRights[i].ID] = true;
        }
        modalOperate.modalShow(modalId);
    };
    //删除角色模态框显示
    vm.delRoleModalShow = function (modalId) {
        judgeIfSelData();
        if (!singleDelClickFlag) {
            if (vm.selKeyArr.length == 0) {
                layer.alert('请选择一条数据进行操作');
                return;
            }
        }
        if (vm.selKeyArr.length > 0) {
            modalOperate.modalShow(modalId);
            getRoleId();
        }
    };
    //角色管理添加模态框隐藏
    vm.roleModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
        if (singleDelClickFlag) {
            singleDelClickFlag = false;
            vm.selRoleIds[vm.roleIds] = false;
        }
    };

   /* 回车搜索*/
    vm.enterSearch = function(){
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.onSearchDataClick();
        }
    }

    /**
     * 点击搜索
     */
    vm.onSearchDataClick = function () {
        //搜索之前把当前页重置为1
        vm.pagination.current = 1;
        getRoleList(vm.pagination.current, 1);
    };

    //获取角色列表
    function getRoleList(curPage, type) {
        req.post('roleCtro/getRoleByQuery.do', {
            roleName: vm.searchRoleTxt,
            roleId: '',
            page: curPage,
            rows: vm.selPageRows,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.roleList = resp.data;
                vm.totalPages = resp.page;
                vm.roleList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //获取权限列表
    function getRightList(callback) {
        req.post('rightCtro/getRightByQuery.do', {
            rightName: '',
            rightId: '',
            ifPage: 0,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.rightList = resp.data;
                if(callback) callback();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //获取选中权限ID
    function getSelRightId() {
        vm.finalRightIds = '';
        for (var key in vm.selRightIds) {
            if (vm.selRightIds[key]) {
                vm.finalRightIds += key + ',';
            }
        }
        vm.rightIds = vm.finalRightIds.slice(0, vm.finalRightIds.length - 1);
    }

    //获取选中角色ID
    function getRoleId() {
        vm.finalRoleIds = '';
        for (var key in vm.selRoleIds) {
            if (vm.selRoleIds[key]) {
                vm.finalRoleIds += key + ',';
            }
        }
        if (vm.roleItemId) {
            vm.roleIds = vm.roleItemId;
        } else {
            vm.roleIds = vm.finalRoleIds.slice(0, vm.finalRoleIds.length - 1);
        }
    }

    //判断是否选择了数据
    function judgeIfSelData() {
        vm.selKeyArr = [];
        for (var key in vm.selRoleIds) {
            if (vm.selRoleIds[key]) {
                vm.selKeyArr.push(key);
                console.log(vm.selKeyArr.length);
            }
        }
        console.log(vm.selKeyArr.length);
    }

    //添加角色请求
    function req_addRoleList(modalId) {
        if (!vm.add.roleName) {
            layer.alert('角色名称不可为空！');
            return;
        }
        if (vm.add.roleName && vm.add.roleName.length > 250) {
            layer.alert('角色名称要少于250个字！');
            return;
        }
        if (vm.add.roleOrderNum && !(/\d/.test(vm.add.roleOrderNum))) {
            layer.alert('排序号为数字！');
            return;
        }
        if (!vm.add.remark) {
            layer.alert('备注不可为空！');
            return;
        }
        if (vm.add.remark && vm.add.remark.length > 250) {
            layer.alert('备注字数要少于250个字！');
            return;
        }
        getSelRightId();
        req.post('roleCtro/addRole.do', {
            roleName: vm.add.roleName,
            memo: vm.add.remark,
            rightIds: vm.rightIds,
            langType: vm.add.langType,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                getRoleList(vm.pagination.current, 1);
                modalOperate.modalHide(modalId);
                initSetting();
                layer.msg('操作成功');
            } else if (resp.code == '513') {
                layer.alert(resp.msg);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //保存-添加角色
    vm.saveAddedRole = function (modalId) {
        req_addRoleList(modalId);
    };
    //全选
    vm.checkAll = function () {
        angular.forEach(vm.roleList, function (item, index) {
            if (vm.selectedAll) {
                vm.selRoleIds[item.ID] = true;
            } else {
                vm.selRoleIds[item.ID] = false;
            }
        });
    };
    //修改角色请求
    function req_updateRoleList(modalId) {
        getRoleId();
        getSelRightId();
        if (!vm.modify.roleName) {
            layer.alert('角色名称不可为空！');
            return;
        }
        if (vm.modify.roleName && vm.modify.roleName.length > 250) {
            layer.alert('角色名称要少于250个字！');
            return;
        }
        if (vm.modify.roleOrderNum && !(/\d/.test(vm.modify.roleOrderNum))) {
            layer.alert('排序号为数字！');
            return;
        }
        if (!vm.modify.remark) {
            layer.alert('备注不可为空！');
            return;
        }
        if (vm.modify.remark && vm.modify.remark.length > 250) {
            layer.alert('备注字数要少于250个字！');
            return;
        }
        req.post('roleCtro/updateRole.do', {
            roleName: vm.modify.roleName,
            memo: vm.modify.remark,
            rightIds: vm.rightIds,
            roleId: vm.roleIds,
            numOrder: vm.modify.roleOrderNum,
            langType: vm.modify.langType,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                getRoleList(vm.pagination.current, 1);
                getRightList(function(){
                    angular.forEach(vm.rightList,function(item,index){
                        if(item.STANDBY1 == 1){
                            vm.rightSort.manageFn.sortArr.push(item);
                        }
                        if(item.STANDBY1 == 2){
                            vm.rightSort.manuscriptEdit.sortArr.push(item);
                        }
                        if(item.STANDBY1 == 3){
                            vm.rightSort.dataStatistics.sortArr.push(item);
                        }
                    });
                });
                modalOperate.modalHide(modalId);
                layer.msg('操作成功');
            } else if (resp.code == '513') {
                layer.alert(resp.msg);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //修改角色
    vm.modifyRole = function (modalId) {
        req_updateRoleList(modalId);
    }
    //删除角色请求
    function req_delRoleList(modalId) {
        // getRoleId();
        req.post('roleCtro/delRoleByIds.do', {
            roleIds: vm.roleIds,
            userToken: vm.userToken
        }).success(function (resp) {
            if (resp.code == '211') {
                getRoleList(vm.pagination.current, 1);
                modalOperate.modalHide(modalId);
                initSetting();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //删除角色
    vm.confirmDelRole = function (modalId) {
        console.log(vm.roleIds);
        req_delRoleList(modalId);
    };

    // 点击item后面的删除
    vm.delItemRoleModalShow = function (modalId, delId) {
        vm.selRoleIds[delId] = true;
        singleDelClickFlag = true;
        vm.roleIds = delId;
        vm.delRoleModalShow(modalId);
    };

});