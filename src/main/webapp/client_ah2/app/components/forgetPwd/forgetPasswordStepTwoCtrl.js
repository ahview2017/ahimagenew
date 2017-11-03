clientModule.controller('forgetPasswordStepTwoCtrl',function($scope,$cookies,req,forgetPassword,md5,$state,$rootScope){
    var vm = this;
    //初始化页面相关设置
    function initSetting(){
        vm.confirmStatus = false;
        vm.mark = forgetPassword.markGetter();
        vm.userName = forgetPassword.usernameGetter();
        if(!vm.mark){
            $state.go('root.forgetPassword');
        }
    }
    initSetting();

    vm.submitData = function(){
        var pwdExp = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$/;

        //避免用户重复提交
        if(vm.confirmStatus){
            layer.msg("正在提交...");
            return;
        }
        if(!vm.password){
            layer.msg("新密码不能为空！");
            return;
        }
        if(!(pwdExp.test(vm.password))){
            layer.msg('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
            return;
        }
        if(!vm.confirmPwd){
            layer.msg("确认密码不能为空！");
            return;
        }
        if(vm.password != vm.confirmPwd){
            layer.msg("两次密码不一致！");
            return;
        }

        vm.confirmStatus = true;

        req.post('login/submitNewPassword.do',{
            userName: vm.userName,
            newPassword:md5.createHash(vm.password),
            mark: vm.mark
        }).success(function(resp){
            if(resp.code == 211){
                //参数传递
                forgetPassword.successSetter(resp.data);
                $state.go('root.forgetPasswordSuccess');
            }else if(resp.code == 511 ){
                layer.msg(resp.msg);
                vm.confirmStatus = false;
            }else{
                layer.alert(resp.msg);
            }
        });
    }









});