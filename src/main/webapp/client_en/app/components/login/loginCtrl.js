photo_enModule.controller('loginCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $window) {
    var vm = this;    
    var client_width=$(document).height()-335+'px';
    $('.client_en-login-container').css('min-height',client_width);
    //页面初始化
    function init() {
        initSetting();
    }

    function initSetting() {
        //初始化登录信息
        vm.user = {
            name: '',
            pwd: ''
        }

        //从cookie里取得存储的用户上次离开的页面地址
        vm.cookie_latestHash = $cookies.get('latest_hash');
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
        } else if (!vm.inputImgVCodeModel) {
            valid = false;
            layer.alert("请输入验证码");
        }
        return valid;
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
            vilidate: vm.inputImgVCodeModel,
            type: 1
        }).success(function (resp) {
            if (resp.code == '211') {
                //用户信息
                var userInfo = resp.data.user;
                //用户权限
                var userRight = resp.data.right;
                //存储角色信息
                var roleInfo = resp.data.role;
                saveUserInfo(userInfo, roleInfo);
                if(vm.cookie_latestHash){
                    location.hash = vm.cookie_latestHash;
                    //重新刷新页面
                    location.reload();
                }else{
                    $state.go('root.mainPage', {}, {reload: true});
                }
                $rootScope.user_online = true;
            } else {
                // 模拟点击执行
                $('#vCodeId').trigger("click");
                layer.msg(resp.msg);
            }
        });
    }

    //存储用户信息
    function saveUserInfo(userInfo, roleInfo) {
        //设置过期日期
        var expireDate = new Date();
        expireDate.setDate(expireDate.getDate() + 30);
        roleInfoStr = angular.toJson(roleInfo, true);
        //设置cookies
        $cookies.put("client_uid", userInfo.id, {expires: expireDate, path: '/'});
        $cookies.put("client_uname", userInfo.userName, {expires: expireDate, path: '/'});
        $cookies.put("client_upwd", userInfo.password, {expires: expireDate, path: '/'});
        $cookies.put("admin_uname", userInfo.userName, {expires: expireDate, path: '/'});
        $cookies.put("admin_upwd", userInfo.password, {expires: expireDate, path: '/'});
        $cookies.put("admin_id", userInfo.id, {expires: expireDate, path: '/'});
        $cookies.put("admin_authorName", userInfo.authorName, {expires: expireDate, path: '/'});
        $cookies.put("admin_roleInfo", roleInfoStr, {expires: expireDate, path: '/'});
        $cookies.put("admin_tureName", userInfo.tureName, {expires: expireDate, path: '/'});
        $cookies.put("admin_lastLoginTime", userInfo.lastLoginTime, {expires: expireDate, path: '/'});
        if (userInfo.standby3) {
            $cookies.put("admin_head_portrait", userInfo.standby3, {expires: expireDate, path: '/'});
        }
        $window.localStorage['admin_userRight'] = JSON.stringify(vm.userRight);
    }


    // 模拟点击执行
    $('#vCodeId').trigger("click");
});