/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('mDraftboxCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove) {
    var vm = this;

    //草稿箱显示
    vm.draftModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    };

    //草稿箱模态框隐藏
    vm.draftModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };

    //移动模态框
    vm.moveModal = function (dragDiv, tagDiv) {
        allModalMove.modalMove(dragDiv, tagDiv);
    };

    //从cookie里取得角色id
    vm.adminRoleId = $cookies.get('admin_roleId');

    //页面初始化相关配置
    function initSetting() {
        vm.selGroupIds = {};
        vm.paramStr = '';
        //存放草稿箱数组
        vm.draftboxList = [];
        //草稿箱总条数
        vm.draftList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页6条
        vm.selPageRows = '6';
    }

    //页面初始化
    function init() {
        initSetting();
        initSearchParams();
        getMyDraftsGroups(1);
    }

    //初始化参数
    function initSearchParams() {
        //搜索对象
        vm.search = {
            id: '',
            title: '',
            author: '',
            place: '',
            editor: '',
            memo: '',
            fileName: '',
            beginTime: '',
            endTime: ''
        };
        vm.paramStr = '';
    }

    init();

    //获取草稿箱列表
    function getMyDraftsGroups(pageNum) {
        req.post('groupPicCtro/getMyDraftsGroups.do', {
            page: pageNum,
            rows: vm.selPageRows,
            id: vm.search.id,
            title: vm.search.title,
            author: vm.search.author,
            place: vm.search.place,
            editor: vm.search.editor,
            memo: vm.search.memo,
            fileName: vm.search.fileName,
            beginTime: vm.search.beginTime,
            endTime: vm.search.endTime,
            paramStr: vm.paramStr,
            langType:window.localStorage.lang
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.draftboxList = resp.data;
                vm.totalPages = resp.page;
                vm.draftList_total = resp.other;
                vm.draftModalHide('draftBoxModalId');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getMyDraftsGroups(pageNumber);
    };

    //回车查询
    vm.enterQuery = function (e) {
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.onSearchDataClick();
        }
    };
    /**
     * 点击搜索
     */
    vm.onSearchDataClick = function () {
        getMyDraftsGroups(1);
    };

    /**
     * 点击高级搜索
     */
    vm.onAdvancedSearchClick = function () {
        getMyDraftsGroups(1);
    };

    //草稿箱删除模态框显示
    vm.draftDelModalShow = function (modalId) {
        vm.selKeyArr = [];
        judgeIfSelData();
        if (vm.selKeyArr.length == 0) {
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if (vm.selKeyArr.length > 0) {
            modalOperate.modalShow(modalId);
        }
    };

    //确认删除草稿箱
    vm.confirmDelDraft = function (modalId) {
        getGroupIds();
        req_confirmDelDraft(modalId);
    };
    //判断是否选择了数据
    function judgeIfSelData() {
        vm.selKeyArr = [];
        for (var key in vm.selGroupIds) {
            if (vm.selGroupIds[key]) {
                vm.selKeyArr.push(key);
            }
        }
    }

    //获取稿件id
    function getGroupIds() {
        vm.finalGroupIds = '';
        for (var key in vm.selGroupIds) {
            if (vm.selGroupIds[key]) {
                vm.finalGroupIds += key + ',';
            }
        }
        if (vm.groupItemId) {
            vm.groupIds = vm.groupItemId;
        } else {
            vm.groupIds = vm.finalGroupIds.slice(0, vm.finalGroupIds.length - 1);
        }
    }

    //确认删除草稿箱请求
    function req_confirmDelDraft(modalId) {
        req.post('groupPicCtro/delDraftsGroups.do', {
            groupIds: vm.groupIds
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
                getMyDraftsGroups(1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //提交模态框显示
    vm.commitDraftModalShow = function (modalId) {
        vm.selKeyArr = [];
        judgeIfSelData();
        if (vm.selKeyArr.length == 0) {
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if (vm.selKeyArr.length > 0) {
            modalOperate.modalShow(modalId);
            //getGroupIds();
            //req_commitDraft();
        }
    };
    //提交草稿箱
    vm.confirmCommitDraft = function (modalId) {
        getGroupIds();
        req_commitDraft(modalId);
    };

    //全选
    vm.checkAllDraft = function (checkAll) {
        angular.forEach(vm.draftboxList, function (item, index) {
            if (checkAll) {
                vm.selGroupIds[item.ID] = true;
            } else {
                vm.selGroupIds[item.ID] = false;
            }
        });
    };

    //直接在列表里对草稿箱进行提交操作
    vm.commitItemDraftModalShow = function (item, modalId) {
        vm.groupItemId = item.ID;
        modalOperate.modalShow(modalId);
    };
    //直接在列表里对草稿箱进行删除操作
    vm.delItemDraftModalShow = function (item, modalId) {
        vm.groupItemId = item.ID;
        modalOperate.modalShow(modalId);
    };
    //确认提交草稿箱
    function req_commitDraft(modalId) {
        req.post('groupPicCtro/submitGroups.do', {
            groupIds: vm.groupIds,
            roleId: vm.adminRoleId,
            langType: window.localStorage.lang
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
                getMyDraftsGroups(1);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});