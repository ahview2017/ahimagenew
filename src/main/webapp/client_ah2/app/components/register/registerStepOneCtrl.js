clientModule.controller('registerStepOneCtrl',function($scope,$cookies,req,md5,$state,$rootScope,layerIfShow,ellipsis){
    var vm = this;
    //匹配用户名正则
    var uNameRegExp = /^[\.a-zA-Z\u4e00-\u9fa50-9_-]{3,16}$/;
    //页面初始化
    function init(){
        //初始化登录信息
        vm.user = {
            userName : '',
            userCategory:'5'
        }
    }
    init();

    //校验信息
    function valid_Info(){
        var valid = true;
        if(!vm.user.userName){
            valid = false;
            layer.alert("请输入用户名");
        }else if(!(uNameRegExp.test(vm.user.userName))){
            valid = false;
            layer.alert("请输入正确格式的用户名");
        }
        return valid;
    }

    //判断用户名是否已存在
    function req_checkUserExist(){
        req.post('login/checkUser.do',{
            userName: vm.user.userName
        }).success(function(resp){
            if(resp.code == '211'){
                //valid = false;
                $state.go('root.registerStepTwo',{userName:vm.user.userName,applyCategory:vm.user.userCategory});
            }else{
                layer.alert(resp.msg);
            }
        });
    }
    vm.req_checkHoverUserExist = function(){
        if(!vm.user.userName){
            vm.state = 3;
            return;
        }else if(!(uNameRegExp.test(vm.user.userName))) {
            vm.state = 1;
            return;
        }else {
                req.post('login/checkUser.do',{
                    userName: vm.user.userName
                }).success(function(resp){
                    if(resp.code == '211'){
                        //valid = false;
                        vm.state = 2;
                    }else{
                        vm.state = 0;
                    }
                });
            }

    }
    //vm.regex = '^[\u4e00-\u9fa5A-Za-z0-9_-]{3,16}$'
    vm.regex = '\\d+';
    //下一步
    vm.signInNext = function(form){
        console.log(form.$valid);
        console.log(vm.user.userName);
        console.log(vm.user.userCategory);
        //验证用户信息
        if(!valid_Info()) return;
        req_checkUserExist();
    }



});