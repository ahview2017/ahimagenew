/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('onlineUserCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $stateParams, modalOperate) {
    var vm = this;
    function initSetting(){
        //存放在线用户数组
        vm.OnLineUsersList = [];
        //在线用户总条数
        vm.onlineList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
    }
    function init(){
        initSetting();
        getOnlineUserList(vm.pagination.current);
    }
    init();

    //获取在线用户列表
    function getOnlineUserList(pageNum){
        req.post('login/getOnLineUsers.do',{
            rows: vm.selPageRows,
            page: pageNum
        }).success(function(resp){
            if(resp.code == '211'){
                vm.OnLineUsersList = resp.data;
                vm.totalPages = resp.page;
                vm.onlineList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getOnlineUserList(pageNumber);
    };

    //下线用户
    vm.logoutUser = function(userId){
        layer.confirm('您确定要下线用户？', {
            btn: ['确定','取消'] //按钮
        }, function(){
            req_logoutUser(userId);
            getOnlineUserList(vm.pagination.current);
        }, function(){
           //取消
        });
    }

    //下线用户请求
    function req_logoutUser(userId){
        req.post('login/logoutUser.do',{
            userId: userId
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});