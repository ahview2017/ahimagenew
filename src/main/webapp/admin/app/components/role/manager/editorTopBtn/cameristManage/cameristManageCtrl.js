/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('cameristManageCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;
    //ģ̬�����ֲ���ʾ
    function maskShow(){
        $rootScope.layerIfShow = true;
    }
    //ģ̬�����ֲ�����
    function maskHide(){
        $rootScope.layerIfShow = false;
    }
    //�û��������ģ̬����ʾ
    vm.userModalShow = function(modalId){
        $('#' + modalId).show();
        maskShow();
    }
    //�û��������ģ̬������
    vm.userModalHide = function(modalId){
        $('#' + modalId).hide();
        maskHide();
    }



});