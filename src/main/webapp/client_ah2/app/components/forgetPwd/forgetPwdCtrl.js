clientModule.controller('forgetPwdCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;
    //初始化页面相关设置
    function initSetting(){
        //找回密码对象
        vm.find = {
            userName:'',
            phone:'',
            vilidate:'',
            email:'',
            question:'',
            answer:''
        }
    }
    //页面初始化
    function init(){
        initSetting();
    }
    init();
    //发送验证码
    vm.sendValidCode = function(){
        req_sendValidCode();
    }
    //发送验证码请求
    function req_sendValidCode(){
        if(!vm.find.phone){
            layer.alert('请输入手机号');
            return;
        }
        req.post('phonemsg/sendVilidate.do',{
            phoneNum: vm.find.phone
        }).success(function(resp){
            if(resp.code == 211){
                layer.msg('发送成功...');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
   /* 找回密码
    findStatus找回方式*/
    vm.findPwd = function(findStatus){
        findPassword(findStatus);
    }
    //找回密码
    function findPassword(findStatus){
        var mailRegExp = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        if(findStatus == 2){
            if(!vm.find.phone){
                layer.alert('请输入手机号码');
                return;
            }
        }
        if(findStatus == 3){
            if(vm.find.email && !mailRegExp.test(vm.find.email)){
                layer.alert('请输入正确格式的邮箱');
                return;
            }
        }
        req.post('login/findPassword.do',{
            userName: vm.find.userName,
            status: findStatus,
            phone: vm.find.phone,
            vilidate: vm.find.vilidate,
            email:  vm.find.email,
            question: vm.find.question,
            answer:  vm.find.answer,
        }).success(function(resp){
            if(resp.code == 211){
                layer.msg(resp.msg);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }



});