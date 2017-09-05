/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('projectPosManageCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    //模态框遮罩层显示
    function maskShow(){
        $rootScope.layerIfShow = true;
    }
    //模态框遮罩层隐藏
    function maskHide(){
        $rootScope.layerIfShow = false;
    }
    //广告位模态框显示
    vm.projectPosModalShow = function(modalId){
        $('#' + modalId).show();
        maskShow();
    }
    //广告位模态框隐藏
    vm.projectPosModalHide = function(modalId){
        $('#' + modalId).hide();
        maskHide();
    }


});