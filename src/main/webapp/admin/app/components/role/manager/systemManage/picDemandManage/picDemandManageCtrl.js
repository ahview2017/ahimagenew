/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('picDemandManageCtrl',function($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove){
    var vm = this;


    //从cookie里取得角色id
    vm.adminRoleId = $cookies.get('admin_roleId');

    //需求管理模态框隐藏`
    vm.picDemandModalHide = function(modalId){
        modalOperate.modalHide(modalId);
    }

    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }

    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            renderList();
        }
    };

    //图片需求简单检索
    vm.picSimpleSearch = function(){
        renderList();
    }
    //图片需求高级检索
    vm.picHighSearch = function(modalId){
        modalOperate.modalShow(modalId);
    }

    //确定高级搜索
    vm.saveHighSearch = function(modalId){
        req_saveHighSearch(modalId);
    }


    //高级搜索请求
    function req_saveHighSearch(modalId) {
        req.post('needs/searchForDutyEditor.do',{
            desc: vm.search.demandCon,
            username: vm.search.userName,
            Timefrom: vm.search.beginTime,
            Timeto: vm.search.endTime,
            langType: window.localStorage.lang
        }).success(function(resp){
            if(resp.code && resp.code == 211){
                vm.listData = resp.data;
                var sendPage = resp.page;
                modalOperate.modalHide(modalId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //审核
    vm.reviewNeeds = function(){
        req_review(function(resp){
            renderList();
            modalOperate.modalHide('demand-check-modal');
        })
    }
    vm.showReviewModel = function(word){
        //resetTempNeedsData();
        vm.reviewId = word.id;
        vm.tempNeedsData.status = word.status;
        vm.tempNeedsData.auditdesc = word.auditdesc;
        req_detail();
        modalOperate.modalShow("pic-demand-check-modal");
    }
    //审核状态
    vm.reviewStatus = [
        {
            id: 0,
            name: '未审核'
        },{
            id: 1,
            name: '审核通过'
        },{
            id: 2,
            name: '审核未通过'
        },{
            id: 3,
            name: '保存文档'
        },{
            id: 4,
            name: '审核通过需求关闭'
        }
    ];
    //修改
    vm.editNeeds = function(){
        req_edit(function(resp){
            renderList();
            modalOperate.modalHide('pic-demand-check-modal');
        })
    }

    vm.delNeeds = function () {
        req_delete(function(resp){
            renderList();
            modalOperate.modalHide('demand-del-modal');
        })
    }
    vm.showDelModel = function(word){
        vm.reviewId = word.id;
        modalOperate.modalShow('demand-del-modal');
    }

    vm.closeNeeds = function () {
        req_close(function(resp){
            renderList();
            modalOperate.modalHide('demand-close-modal');
        })
    }
    vm.showCloseModel = function(word){
        vm.reviewId = word.id;
        modalOperate.modalShow('demand-close-modal');
    }

    //需求详情
    function req_detail() {
        req.post('needs/detail.do',{
            id: vm.reviewId
        }).success(function(resp){
            if(resp.code && resp.code == 211){
                //callback(resp.data);
                vm.detailData = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //修改需求
    function req_edit(callback) {
        req.post('needs/edit.do',{
            id: vm.reviewId,
            status: vm.tempNeedsData.status,
            auditdesc: vm.tempNeedsData.auditdesc
        }).success(function(resp){
            if(resp.code && resp.code == 211){
                callback(resp.data);
                //vm.detailData = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //删除需求
    function req_delete(callback) {
        req.post('needs/delete.do', {
            id: vm.reviewId
        }).success(function(resp) {
            if(resp.code && resp.code == 211){
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    //关闭需求
    function req_close(callback) {
        req.post('needs/closeneeds.do', {
            id: vm.reviewId
        }).success(function(resp) {
            if(resp.code && resp.code == 211){
                callback(resp.data)
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        })
    }

    //需求列表
    function renderList(pageNum) {
        var searchUrl = "";
        var searchObj = {
            page: pageNum,
            rows: vm.selPageRows,
            roleId:vm.adminRoleId,
            langType: window.localStorage.lang
        };
        if (vm.picSearchStr) {
            searchUrl = "needs/search.do";
            searchObj['someThing'] = vm.picSearchStr;
        } else {
            searchUrl = "needs/show.do";
        }
        req.post(searchUrl,searchObj).success(function(resp){
            if (resp.code && resp.code == 211) {
                vm.listData = resp.data;
                vm.totalPages = resp.page;
                vm.demandList_total = resp.other;

            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        renderList(pageNumber);
    };

    function resetTempNeedsData(){
        vm.tempNeedsData = {
            status: '',
            auditdesc: ''
        }
        vm.search = {
            demandCon: '',
            userName:'',
            useTime:'',
            beginTime:'',
            endTime:''
        }
        vm.picSearchStr = '';
        //存放需求数组
        vm.listData = [];
        //需求总条数
        vm.demandList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }

    function init() {
        renderList(1);
        resetTempNeedsData();
    }

    init();



});