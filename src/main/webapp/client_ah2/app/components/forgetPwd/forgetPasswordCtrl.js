clientModule.controller('forgetPasswordCtrl',function($scope,$cookies,req,forgetPassword,md5,$state,$rootScope){
    var vm = this;
    //初始化页面相关设置
    function initSetting(){

    }
    initSetting();

    vm.submitData = function(){
        if(!vm.userName){
            layer.alert("用户信息不能为空！");
            return;
        }
        if(!vm.vilidate){
            layer.alert("验证码不能为空！");
            return;
        }
        req.post('login/findUserByInfo.do',{
            userInfo: vm.userName,
            vilidate: vm.vilidate
        }).success(function(resp){
            if(resp.code == 211){
                //参数传递
                forgetPassword.staSetter(resp.data.status);
                forgetPassword.usernameSetter(resp.data.userName);
                forgetPassword.telSetter(resp.data.telBind);
                forgetPassword.emailSetter(resp.data.emailBind);
                $state.go('root.forgetPasswordStepOne');
            }else if(resp.code == 514 ){
                layer.msg(resp.msg);
            }else if(resp.code == 503||resp.code == 512){
                layer.alert(resp.msg);
            }else if(resp.code == 400) {
                layer.msg(resp.desc);
            }else{
                layer.alert(resp.msg);
            }
        });
    }









});