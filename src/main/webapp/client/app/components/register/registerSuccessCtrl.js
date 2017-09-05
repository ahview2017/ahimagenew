clientModule.controller('registerSuccessCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,ellipsis,$stateParams){

    var vm = this;
    //取得从路由传来的用户名
    vm.loginName = $stateParams.userName;
    //取得从路由传来的邮箱
    vm.emailBind = $stateParams.emailBind;
    //取得从路由传来的手机号
    vm.telBind = $stateParams.telBind;


    //秒数刷新
    var countPage = document.getElementById("countPage");
    var val = 6;

    function init(){
      countPage.innerHTML = val;
    }
    init();
    //秒数刷新
    var timer = setInterval(function(){
        if(--val>0){
            countPage.innerHTML = val;
        }
        else
        {
            clearInterval(timer);
            $state.go('root.index');
        }
    },1000);

    vm.jumpToIndex = function () {
        $state.go('root.index');
    }


});