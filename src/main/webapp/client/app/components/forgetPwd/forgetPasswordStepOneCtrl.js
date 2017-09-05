clientModule.controller('forgetPasswordStepOneCtrl',function($scope,$cookies,req,forgetPassword,md5,$state,$rootScope){
    var vm = this;

    //初始化页面相关设置
    function initSetting(){
        vm.status = forgetPassword.staGetter();
        vm.userName = forgetPassword.usernameGetter();
        vm.telBind = forgetPassword.telGetter();
        vm.emailBind = forgetPassword.emailGetter();
        vm.vilidateStatus = true;

        $('#viliPage').hide();
        if(!vm.status){
             $state.go('root.forgetPassword');
        }
    }
    initSetting();
    var valCount = 120;


    //获取验证码
    vm.getVilidata = function () {
        var reqUrl = "";
        var viliCount = document.getElementById("viliCount");

        if(!vm.selPattern){
            layer.msg('请选择验证方式！');
            return;
        }else if(vm.selPattern == 1){
            reqUrl = "phonemsg/sendPhoneVilidate.do";
        }else if(vm.selPattern == 2){
            reqUrl = "mail/sendEmailVilidate.do";
        }

        reqVilidata(reqUrl);

        $('#forgetOneBtn').attr("disabled",true);
        // vm.vilidateStatusBtn = true;
        viliCount.innerHTML = valCount;
        $('#viliPage').show();

        //秒数刷新
        var registerTimer = setInterval(function(){
            if(--valCount>0){
                viliCount.innerHTML = valCount;
                console.log("================"+valCount);
            }
            else
            {
                $('#forgetOneBtn').attr("disabled",false);
                clearInterval(registerTimer);
                valCount = 120;
                $('#viliPage').hide();
            }
        },1000);

    }

    function reqVilidata(reqUrl){


        //改变输入框状态
        vm.vilidateStatus = false;

        req.post(reqUrl,{
            userName: vm.userName
        }).success(function(resp){
            if(resp.code == 211){

            }else{
                layer.msg(resp.msg);
            }
        });
    }

    vm.submitVilidata = function(){
        var reqUrl = "";
        if(!vm.userName){
            layer.msg("该用户信息异常！");
            return;
        }
        if(!vm.vilidate){
            layer.msg("验证码不能为空！");
            return;
        }
        if(vm.selPattern == 1){
            reqUrl = "phonemsg/confirmPhoneVilidate.do";
        }else if(vm.selPattern == 2){
            reqUrl = "mail/confirmEmailVilidate.do";
        }
        req.post(reqUrl,{
            userName: vm.userName,
            vilidate: vm.vilidate
        }).success(function(resp){
            if(resp.code == 211){
                //参数传递
                forgetPassword.markSetter(resp.data);
                forgetPassword.usernameSetter(vm.userName);
                $state.go('root.forgetPasswordStepTwo');
            }else if(resp.code == 511 ){
                layer.msg(resp.msg);
            }else{
                layer.alert(resp.msg);
            }
        });
    }









});