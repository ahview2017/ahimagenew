/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('managerIndexCtrl',function($scope, $cookies, req, md5, $state, $rootScope, modalOperate, allModalMove){
    var vm = this;
    //初始化设置
    function initSetting(){
        //从cookie里取得角色id
        vm.adminRoleId = $cookies.get('admin_roleId');
        //从cookie里取得最近登录时间
        vm.lastLoginTime = $cookies.get('admin_lastLoginTime');
    };
    //初始化
    function init(){
        initSetting();
        getSiteNotice();
        getStationLetter();
        getSiteMsg();
    }
    init();
    //获取网站公告
    function getSiteNotice(){
        req.post('notice/showToHomePage.do',{
            limit: 5
        }).success(function(resp){
            if(resp.code == '211'){
                vm.showToHomePage = resp.data;
            }else{
                console.log(resp.msg);
            }
        });
    }
    //获取站内信
    function getStationLetter(){
        req.post('station/showToAdminHome.do',{
            limit: 5
        }).success(function(resp){
            if(resp.code == '211'){
                vm.stationLetter = resp.data;
            }else{
                console.log(resp.msg);
            }
        });
    }
    //获取网站留言
    function getSiteMsg(){
        req.post('leavingmsg/show.do',{
            limit: 5
        }).success(function(resp){
            if(resp.code == '211'){
                vm.siteMsg = resp.data;
            }else{
                console.log(resp.msg);
            }
        });
    }
    //获取消息展示详情
    vm.msgDetailShow = function(msgTitle,msgContent){
        vm.msgContent = msgContent;
        vm.msgTitle = msgTitle;
        modalOperate.modalShow('indexMsgDetailContentModal');
    }
    //关闭模态框
    vm.manageModalHide = function(modalId){
        modalOperate.modalHide(modalId);
    }
    //点击移动模态框
    vm.moveModal = function(dragDiv,tagDiv){
        allModalMove.modalMove(dragDiv,tagDiv);
    }

});