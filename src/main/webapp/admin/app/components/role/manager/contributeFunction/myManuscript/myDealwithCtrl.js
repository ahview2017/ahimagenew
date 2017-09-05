/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('myDealwithCtrl',function($scope,$cookies,req,md5,$state,$rootScope,modalOperate,allModalMove){
    var vm = this;


    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }

    function initSetting(){
        //默认激活的导航项为全部稿件
        vm.acitiveSlideTit = 1;
        //默认
        vm.gType = 0;

        //搜索对象
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

        //存放我的稿件数组
        vm.mymanuscriptList = [];
        //我的稿件总条数
        vm.myMsList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页6条
        vm.selPageRows = '6';
    }
    function initSearchSetting(){
        //搜索对象
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
    function init(){
        initSetting();
        getMymanuscript(0,1);
    }
    init();
    //选择激活的导航项
    vm.chooseManuscriptType = function(gType,acitiveSlideTit){
        vm.acitiveSlideTit = acitiveSlideTit;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        vm.gType = gType;
        initSearchSetting();
        getMymanuscript(gType,1);
        vm.search.paramStr = ''
    }

    //回车查询
    vm.enterQuery = function (e) {
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.onSearchDataClick();
        }
    };

    //获取我的稿件：全部、已签、待签、已删、被退
    function getMymanuscript(gType,pageNum){
        req.post('groupPicCtro/getGroupPicsInDeal.do',{
            page: pageNum,
            rows: vm.selPageRows,
            gType: gType,
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
            langType:window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                vm.mymanuscriptList = resp.data;
                //console.log(resp.data);
                modalOperate.modalHide('wait-manuscript-search-modal');
                vm.totalPages = resp.page;
                vm.myMsList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //页数变化
    vm.pageChanged = function (pageNumber) {
        getMymanuscript(vm.gType,pageNumber);
    };

    //点击搜索
    vm.onSearchDataClick = function(){
        vm.pagination.current = 1;
        getMymanuscript(vm.gType,1);
    }

    //高级检索模态框显示
    vm.advanceSearchModalShow = function(modalId){
        modalOperate.modalShow(modalId);
    }
    //保存高级检索
    vm.saveAdvanceSearch = function(){
        vm.pagination.current  = 1;
        getMymanuscript(vm.gType,1);
    }


    //我的稿件模态框隐藏
    vm.myManuscriptModalHide = function(modalId){
        modalOperate.modalHide(modalId);
    }

    //删除被退稿件中的稿件模态框显示
    vm.delBackManuscriptModalShow = function(modalId,groupId){
        vm.groupId = groupId;
        modalOperate.modalShow(modalId,groupId);
    }
    //删除被退稿件中的稿件
    vm.delBackManuscript = function(modalId){
        req_delBackManuscript(modalId);
    }
    //删除被退稿件中的稿件请求
    function req_delBackManuscript(modalId){
        req.post('groupPicCtro/delDraftsGroups.do',{
            groupIds: vm.groupId
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                modalOperate.modalHide(modalId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //开始编辑：编辑前锁定稿件
    vm.editSendManuscript = function (groupId) {
        req_beginEditSendManuscript(groupId);
    }
    //开始编辑：编辑前锁定稿件请求
    function req_beginEditSendManuscript(groupId) {
        req.post('groupPicCtro/editBegin.do', {
            groupId: groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                console.log('success');
                $state.go('role.manager.myManuscriptEdit', {
                    id: groupId
                });
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});