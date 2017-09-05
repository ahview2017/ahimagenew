/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('cameristManageCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;
    //模态框遮罩层显示
    function maskShow(){
        $rootScope.layerIfShow = true;
    }
    //模态框遮罩层隐藏
    function maskHide(){
        $rootScope.layerIfShow = false;
    }
    //用户管理添加模态框显示
    vm.userModalShow = function(modalId){
        $('#' + modalId).show();
        maskShow();
    }
    //用户管理添加模态框隐藏
    vm.userModalHide = function(modalId){
        $('#' + modalId).hide();
        maskHide();
    }



});