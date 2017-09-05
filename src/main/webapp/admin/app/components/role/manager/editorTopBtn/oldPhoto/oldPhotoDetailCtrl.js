/**
 * Created by Sun on 2016/12/15.
 */
adminModule.controller('oldPhotoDetailCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    //模态框遮罩层显示
    function maskShow(){
        $rootScope.layerIfShow = true;
    }
    //模态框遮罩层隐藏
    function maskHide(){
        $rootScope.layerIfShow = false;
    }
    //专题模态框显示
    vm.projectModalShow = function(modalId){
        $('#' + modalId).show();
        maskShow();
    }
    //专题模态框隐藏
    vm.projectModalHide = function(modalId){
        $('#' + modalId).hide();
        maskHide();
    }



});