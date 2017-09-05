/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('mHistoryVersionCtrl',function($scope, $cookies, req, md5, $state, $rootScope, $stateParams){
    var vm = this;
    //获取从路由传过来的稿件id
    vm.groupId = $stateParams.groupId;
    //获取从路由传过来的校审级别
    vm.dtType = $stateParams.dtType;
    //获取从路由传过来的稿件类别
    vm.gType = $stateParams.gType;
    //初始化页面相关配置
    function initSetting(){

    }
    //页面初始化
    function init(){
        initSetting();
        getHistoryVierson();
        getManuscriptDetails();
    }
    init();

    //获取历史版本
    function getHistoryVierson(){
        req.post('groupPicCtro/getGroupHistoryVersion.do',{
            groupId: vm.groupId,
        }).success(function(resp){
            if(resp.code == '211'){
                vm.historyVersion = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //获取稿件详情
    function getManuscriptDetails() {
        req.post('groupPicCtro/getGroupPics.do', {
            groupId: vm.groupId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.manuscriptDetail = resp.data;
                vm.groupStatus = resp.data.groupStatus;
                console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }



});