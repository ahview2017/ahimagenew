/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('mDraftDetailCtrl', function ($scope, $cookies, req, md5, $state, $window, $rootScope, $stateParams, modalOperate) {
    var vm = this;

    //获取从路由传过来的稿件id
    vm.groupId = $stateParams.id;
    //从cookie里取得角色id
    vm.adminRoleId = $cookies.get('admin_roleId');

    //页面初始化相关配置
    function initSetting() {
        //点击管理详情div是否展示的标识
        vm.mangeOperateFlag = false;

        //存储签发参数的数组
        vm.signReqParamData = [];
    }

    //页面初始化
    function init() {
        initSetting();
        getManuscriptDetails();
    }

    init();


    //获取稿件详情
    function getManuscriptDetails() {
        req.post('groupPicCtro/getGroupPics.do', {
            groupId: vm.groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.manuscriptDetail = resp.data;
                vm.manuscriptProperties = resp.data.properties;
                vm.manuscriptCates = resp.data.cates;
                vm.groupStatus = resp.data.groupStatus;
                console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //稿件详情模态框隐藏
    vm.manuscriptDetailModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    }
    //确认提交校审提交模态框显示
    vm.commitModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    }
    //删除模态框显示
    vm.delModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    }
    //确认提交校审
    vm.confirmCommit = function (modalId) {
        if (vm.groupStatus == 0) {
            req_commitDraft(modalId);
        }
    }
    //确认提交草稿箱
    function req_commitDraft(modalId){
        req.post('groupPicCtro/submitGroups.do',{
            groupIds: vm.groupId,
            roleId: vm.adminRoleId,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
                getManuscriptDetails();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //点击管理控制下面详情div切换
    vm.toggleMangeOperateCon = function () {
        vm.mangeOperateFlag = !vm.mangeOperateFlag;
    }


    //确认删除稿子
    vm.confirmDelDraftMs = function (modalId) {
        req_delManuscript(modalId);
    }

    //确认删除稿子请求
    function req_delManuscript(modalId) {
        req.post('groupPicCtro/delDraftsGroups.do',{
            groupIds: vm.groupId
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
                getManuscriptDetails();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //开始编辑：编辑前锁定稿件
    vm.editSendManuscript = function () {
        req_beginEditSendManuscript();
    }
    //开始编辑：编辑前锁定稿件请求
    function req_beginEditSendManuscript() {
        req.post('groupPicCtro/editBegin.do', {
            groupId: vm.groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                console.log('success');
                $state.go('role.manager.draftEdit', {
                    id: vm.groupId
                });
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //强制解锁
    vm.unlockManuscript = function () {
        req_unlockManuscript();
    }
    //强制解锁请求
    function req_unlockManuscript() {
        req.post('groupPicCtro/unLockGroup.do', {
            groupId: vm.groupId,
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('操作成功');
                getManuscriptDetails();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }



});