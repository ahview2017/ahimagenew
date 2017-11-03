clientModule.controller('forgetPasswordSuccessCtrl',function($scope,$cookies,req,forgetPassword,md5,$state,$rootScope){
    var vm = this;

    //初始化页面相关设置
    function initSetting(){
        vm.successStatus = forgetPassword.successGetter();
        if(!vm.successStatus){
            $state.go('root.forgetPassword');
        }
    }
    initSetting();

    vm.jumpToIndex = function () {
        $state.go('root.index');
    }



});