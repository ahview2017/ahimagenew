clientModule.controller('registerCtrl',function($scope, $cookies, req, md5, $state ,$rootScope ,layerIfShow ,ellipsis){
    var vm = this;
    //页面初始化
    function init(){
        vm.agreeFlag = '我接受';
    }
    init();

    //下一步
    vm.nextStep = function(){
        if(vm.agreeFlag == '我接受'){
            $state.go('root.registerStepOne');
        }else if(vm.agreeFlag == '我不接受'){
            layer.alert('申请注册必须同意协议条款');
        }
    }
    //返回
    vm.return = function(){
        $state.go('root.index');
    }



});