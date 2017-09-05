/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('payStatisticsCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow){
    var vm = this;
    //模态框遮罩层显示
    function maskShow(){
        $rootScope.layerIfShow = true;
    }
    //模态框遮罩层隐藏
    function maskHide(){
        $rootScope.layerIfShow = false;
    }
    //角色管理添加模态框显示
    vm.roleModalShow = function(modalId){
        $('#' + modalId).show();
        maskShow();
    }
    //角色管理添加模态框隐藏
    vm.roleModalHide = function(modalId){
        $('#' + modalId).hide();
        maskHide();
    }


});