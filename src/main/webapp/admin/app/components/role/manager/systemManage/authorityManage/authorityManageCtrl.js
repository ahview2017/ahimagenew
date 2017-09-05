/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('authorityManageCtrl',function($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove){
    var vm = this;

    //回车搜索
    vm.enterSearch = function(e){
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.SearchAuthoritylist();
        }
    }
    //搜索权限列表
    vm.SearchAuthoritylist = function(){
        //搜索之前把当前页重置为1
        vm.pagination.current = 1;
        // $scope.searchQuery = angular.copy($scope.search);
        var inputRightName = $(" #user-search-input ").val().replace(/[]/g, "");
        getRightList(inputRightName, vm.pagination.current, 1);
    }
    //模态框显示
    vm.authorityModalShow = function (modalId) {
        initSetting();
        modalOperate.modalShow(modalId);
    }
    //取消模态框
    vm.authorityModalHide = function(modalId){
        modalOperate.modalHide(modalId);
    }
    //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }
    //判断是否选择了数据
    function  judgeIfSelData(){
        vm.selKeyArr = [];
        for(var key in vm.selRightIds){
            if(vm.selRightIds[key]){
                vm.selKeyArr.push(key);
                console.log(vm.selKeyArr.length);
            }
        }
        console.log(vm.selKeyArr.length);
    }
    //获取选中权限ID
    function getSelRightId(){
        vm.finalRightIds = '';
        for(var key in vm.selRightIds){
            if(vm.selRightIds[key]){
                vm.finalRightIds += key + ',';
            }
        }
        if(vm.rightItemId){
            vm.rightIds = vm.rightItemId;
        }else{
            vm.rightIds = vm.finalRightIds.slice(0,vm.finalRightIds.length - 1);
        }
    }
    //权限修改模态框显示
    vm.updateAuthority = function(modalId){
        vm.selKeyArr = [];
        judgeIfSelData();
        if(vm.selKeyArr.length != 1){
            layer.alert('请选择一条数据进行操作');
            return;
        }
        if(vm.selKeyArr.length == 1){
            angular.forEach(vm.rightList,function(item,index){
                if(item.ID == vm.selKeyArr[0]){
                    vm.willUpdateRight = item;
                    console.log(vm.willUpdateRight);
                }
            });
            vm.right.rightName = vm.willUpdateRight.RIGHT_NAME;
            vm.right.memo = vm.willUpdateRight.MEMO;
            modalOperate.modalShow(modalId);
        }
    }
    //权限修改模态框显示--只更新一项
    vm.updateItemAuthority = function(modalId,authorityItem){
        vm.rightItemId = authorityItem.ID;
        vm.right.rightName = authorityItem.RIGHT_NAME;
        vm.right.memo = authorityItem.MEMO;
        modalOperate.modalShow(modalId);
    }
    //确认修改
    vm.sureUpdateAuthority = function(modalId){
        if(!vm.right.rightName){
            layer.alert('权限名称不可为空！');
            return;
        }
        /*if(vm.right.orderNum && !(/\d/.test(vm.right.orderNum))){
            layer.alert('排序号为数字！');
            return;
        }*/
        if(!vm.right.memo){
            layer.alert('备注不可为空！');
            return;
        }
        getSelRightId();
        req_updateRightList(modalId);
    }
    //页面初始化配置
    function initSetting(){
        vm.right = {
            rightName:'',
            memo:'',
            rightId:''
        }
        vm.selRightIds = {},
        vm.rightItemId = ''
        //存放权限列表数组
        vm.rightList = [];
        //在线用户总条数
        vm.authorityList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页20条
        vm.selPageRows = '10';
    }
    //页面初始化
    function init(){
        initSetting();
        getRightList('', vm.pagination.current, 1);
    }
    init();
    //获取权限列表
    function getRightList(inputRightName, curPage, type){
        req.post('rightCtro/getRightByQuery.do',{
            rightName: inputRightName,
            rightId: '',
            page:curPage,
            ifPage: 1,
            rows: vm.selPageRows
        }).success(function(resp){
            if(resp.code == '211'){
                vm.rightList = resp.data;
                vm.totalPages = resp.page;
                vm.authorityList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getRightList('', pageNumber, 1);
    };

    //修改权限请求
    function req_updateRightList(modalId){
        req.post('rightCtro/updateRight.do',{
            rightName: vm.right.rightName,
            memo: vm.right.memo,
            rightId: vm.rightIds
        }).success(function(resp){
            if(resp.code == '211'){
                getRightList('',vm.pagination.current, 1);
                modalOperate.modalHide(modalId);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //全选
    vm.checkAll = function(){
        angular.forEach(vm.rightList,function(item,index){
            if(vm.selectedAll){
                vm.selRightIds[item.ID] = true;
            }else{
                vm.selRightIds[item.ID] = false;
            }
        });
    }
});