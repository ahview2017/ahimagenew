/**
 * Created by Sun on 2016/12/13.
 */
adminModule.controller('topicManageCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    //ģ̬�����ֲ���ʾ
    function maskShow(){
        $rootScope.layerIfShow = true;
    }
    //ģ̬�����ֲ�����
    function maskHide(){
        $rootScope.layerIfShow = false;
    }
    //ר��ģ̬����ʾ
    vm.projectModalShow = function(modalId){
        $('#' + modalId).show();
        maskShow();
    }
    //ר��ģ̬������
    vm.projectModalHide = function(modalId){
        $('#' + modalId).hide();
        maskHide();
    }



});