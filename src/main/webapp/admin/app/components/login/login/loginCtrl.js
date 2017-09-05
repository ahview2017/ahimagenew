adminModule.controller('loginCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $location, localStorageService, $window) {
    var vm = this;
    //页面初始化
    function init() {
        initSetting();
    }

    function initSetting() {
        //设置body背景为白色
        $("body").css('background-color', '#FFF');
        //初始化登录信息
        vm.user = {
            name: '',
            pwd: ''
        };
        //初始化头像信息
        $cookies.remove('admin_head_portrait');

        //从cookie里取得存储的用户上次离开的页面地址
        vm.cookie_latestHash = $cookies.get('latest_hash');

        vm.validCodeImg ='/cnsphoto/yanzheng.do?tm='+Math.random();
    }

    init();

    //验证用户信息
    function valid_info() {
        var valid = true;
        if (vm.user.name == '') {
            valid = false;
            layer.alert("请输入用户名");
        } else if (vm.user.pwd == '') {
            valid = false;
            layer.alert("请输入密码");
        } else if (!vm.vCodeModel) {
            valid = false;
            layer.alert("请输入验证码");
        }
        return valid;
    }
    //找回密码
    vm.goFindPwd = function(){
        $window.location.href='/#/forgetPassword';
    }
    // 客户端登录
    vm.loginIn = function () {
        //验证用户信息
        if (!valid_info()) return;
        req_getSalt(function () {
            req_login();
        });
    };

    //回车登录
    vm.enterLogin = function (e) {
        var e = e || window.event;
        var code = e.keyCode ? e.keyCode : e.which;
        if (code == 13) {
            vm.loginIn();
        }
    };

    //获取salt
    function req_getSalt(callback) {
        req.post('login/getSalt.do', {
            userName: vm.user.name
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.loginSalt = resp.data;
                if (callback) callback();
            } else {
                console.log(resp.msg);
            }
        });
    }

    //登录请求
    function req_login() {
        var saltPwdMix = md5.createHash(vm.user.pwd) + vm.loginSalt;
        vm.finalPwd = md5.createHash(saltPwdMix);
        req.post('login/doLogin.do', {
            userName: vm.user.name,
            password: vm.finalPwd,
            vilidate: vm.vCodeModel
        }).success(function (resp) {
            if (resp.code == '211') {
                //用户信息
                var userInfo = resp.data.user;
                //存储角色信息
                var roleInfo = resp.data.role;
                //用户权限
                vm.userRight = resp.data.right;
                //如果用户只有订户角色则不让登录后台系统
                if(roleInfo.length == 1 && roleInfo[0].id == 5){
                    layer.alert('访客、订户、一线通用户不能登录编辑系统！');
                    return;
                }else{
                    saveUserInfo(userInfo, roleInfo);
                    $rootScope.user_online = true;
                    if(vm.cookie_latestHash && vm.cookie_latestHash != '#/login'){
                        location.hash = vm.cookie_latestHash;
                    }else{
                        $state.go('role.manager.managerIndex');
                    }
                }
            } else {
                layer.msg(resp.msg);
                // 模拟点击执行
                vm.changeValidCodeImg();
            }
        });
    }

    //改变验证码图片
    vm.changeValidCodeImg = function(){
        vm.validCodeImg ='/cnsphoto/yanzheng.do?tm='+Math.random();
        // vm.validCodeImg ='http://192.168.180.124/cnsphoto/yanzheng.do?tm='+Math.random();

    }

    //存储用户信息
    function saveUserInfo(userInfo, roleInfo) {
        //设置过期日期
        var expireDate = new Date();
        expireDate.setDate(expireDate.getDate() + 30);
        roleInfoSt = angular.toJson(roleInfo, true);
        //设置cookies
        $cookies.put("client_uid", userInfo.id, {path: '/'});
        $cookies.put("client_uname", userInfo.userName, {path: '/'});
        $cookies.put("client_upwd", userInfo.password, {path: '/'});
        $cookies.put("admin_uname", userInfo.userName, {path: '/'});
        $cookies.put("admin_upwd", userInfo.password, {path: '/'});
        $cookies.put("admin_id", userInfo.id, {path: '/'});
        $cookies.put("admin_authorName", userInfo.authorName, {expires: expireDate, path: '/'});
        var roleInfoStr0 = [];
        var roleInfoStr1 = [];
        var roleInfoStr = eval(roleInfoSt);
        for(var i in roleInfoStr){
        	if(roleInfoStr[i].langType == 0 || roleInfoStr[i].langType == null){
        		roleInfoStr0.push(roleInfoStr[i]);
        	}else if(roleInfoStr[i].langType == 1){
        		roleInfoStr1.push(roleInfoStr[i]);
        	}
        }
        
        var roleInfoStr_0 = angular.toJson(roleInfoStr0,true)
        var roleInfoStr_1 = angular.toJson(roleInfoStr1,true)
        $cookies.put("admin_roleInfo_0", roleInfoStr_0, {expires: expireDate, path: '/'});
        $cookies.put("admin_roleInfo_1", roleInfoStr_1, {expires: expireDate, path: '/'});
        $cookies.put("admin_tureName", userInfo.tureName, {expires: expireDate, path: '/'});
        $cookies.put("admin_lastLoginTime", userInfo.lastLoginTime, {expires: expireDate, path: '/'});
        if (userInfo.standby3) {
            $cookies.put("admin_head_portrait", userInfo.standby3, {expires: expireDate, path: '/'});
        }
        $window.localStorage['admin_userRight'] = JSON.stringify(vm.userRight);
    }
/*
    // 模拟点击执行
    $('#vCodeId').trigger("click");*/
});