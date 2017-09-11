// 监视器 监视ng-repeat循环完成
clientModule.directive('onFinishRendersFilters', function ($timeout) {
        return {
            restrict: 'A',
            link: function(scope, element, attr) {
                if (scope.$last === true) {
                    $timeout(function() {
                        jQuery(".slideBox").slide({mainCell:".bd ul",autoPlay:true});
					
                    });
                }
            }
        };
    });

clientModule.controller('headerCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, getFullText) {
    var vm = this;
   
    function initSetting() {
        //从cookie获取客户端用户名
        $rootScope.client_uName = $cookies.get('client_uname');
        $rootScope.client_logined = $cookies.get('client_logined');
        
    }
    //登录按钮
    vm.login_user = function(){
    	 $("#gray").css("display",'block');
         $("#login_div").css("display",'block');
    }
    //初始化样式
    function tc_center(){
        var _top=($(window).height()-$("#login_div").height())/2;
        var _left=($(window).width()-$("#login_div").width())/2;
        $("#login_div").css({top:_top,left:_left});
        $("#login_div").css({top:_top,left:_left});
        var _top1=($(window).height()-$("#register_div").height())/2;
        var _left1=($(window).width()-$("#register_div").width())/2;
        $("#register_div").css({top:_top1,left:_left1});
    }
    //关闭按钮
    vm.go_close = function(){
    	 $("#gray").hide();
         $(".login_box").hide();
         $(".register_box").hide();
    }
    //注册按钮
    vm.register = function(){
    	 $("#gray").css("display",'block');
         $("#register_div").css("display",'block');
    }
    //忘记密码
    vm.forgetPassword = function(){
    	$("#gray").hide();
        $(".login_box").hide();
    	$state.go('root.forgetPassword');
    }
    //登录
    vm.loginIn = function(){
    	//验证用户信息
        if (!valid_info()) return;
        req_getSalt(function () {
            req_login();
        });
    }
    //验证用户信息
    function valid_info() {
        var valid = true;
        if ($("#login_user").val() == '') {
            valid = false;
            layer.msg("请输入用户名");
            $('#login_user').focus();
        } else if ($("#login_pass").val() == '') {
            valid = false;
            layer.msg("请输入密码");
            $('#login_pass').focus();
        } else if ($("#login_code").val() == '') {
            valid = false;
            layer.msg("请输入验证码");
            $('#login_code').focus();
        }
        return valid;
    }
  //发送验证码
    var clock = '';
    var nums = 60;
    var start = true;
    vm.sendmsg = function(form){
    	if ($("#login_user").val() == '') {
            layer.msg("请输入用户名");
            $('#login_user').focus();
            return;
        }
    	
    	if(nums==60&&start){
    		start = false;
	    	$("#code").removeAttr('ng-click');
	    	$("#code").attr('disabled',"true");
	    	$("#code").html(nums+'秒后可重新获取');
	    	clock = setInterval(doLoop, 1000); //一秒执行一次
	    	console.log("发送短信");
	    	req.post('phonemsg/getPhoneVilidate.do',{
	    		userName: $("#login_user").val()
            }).success(function(resp){
                if(resp.code != '211'){
                    layer.alert(resp.msg);
                }
            });
	    	
    	}
    }
    function doLoop()
    {
    	nums--;
	    if(nums > 0){
	    	 $("#code").html(nums+'秒后可重新获取');
	    }else{
		     clearInterval(clock); //清除js定时器
		     $("#code").html('获取验证码');
		     $("#code").attr('ng-click','header.sendmsg()');
		     $("#code").removeAttr('disabled');
		     nums = 60; //重置时间
		     start = true;
	    }
    }
  //获取salt
    function req_getSalt(callback) {
        req.post('login/getSalt.do', {
            userName: $("#login_user").val()
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
        var saltPwdMix = md5.createHash($("#login_pass").val()) + vm.loginSalt;
        vm.finalPwd = md5.createHash(saltPwdMix);
        req.post('login/doLogin.do', {
            userName: $("#login_user").val(),
            password: vm.finalPwd,
            vilidate: $("#login_code").val(),
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
                    $state.go('root.index', {}, {reload: true});
                }
                $rootScope.user_online = true;
            } else {
                // 模拟点击执行
                $('#vCodeId').trigger("click");
                layer.msg(resp.msg);
                if(resp.msg=='验证码出错'){
                	//layer.msg(resp.msg);
                	 $('#login_code').focus();
                }else if(resp.msg=='该用户不存在或正在申请中'||resp.msg=='无该用户'||resp.msg=='该用户暂时无法登陆'||resp.msg=='salt失效，请重新登录'){
                	 $('#login_user').focus();
                }else{
                	 $('#login_pass').focus();
                }
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
        $cookies.put("client_uid", userInfo.id, {path: '/'});
        $cookies.put("client_uname", userInfo.userName, { path: '/'});
        $cookies.put("client_upwd", userInfo.password, {path: '/'});
        $cookies.put("admin_uname", userInfo.userName, { path: '/'});
        $cookies.put("admin_upwd", userInfo.password, {path: '/'});
        $cookies.put("admin_id", userInfo.id, {path: '/'});
        $cookies.put("admin_authorName", userInfo.authorName, {expires: expireDate, path: '/'});
        $cookies.put("admin_roleInfo", roleInfoStr, {expires: expireDate, path: '/'});
        $cookies.put("admin_tureName", userInfo.tureName, {expires: expireDate, path: '/'});
        $cookies.put("admin_lastLoginTime", userInfo.lastLoginTime, {expires: expireDate, path: '/'});
        if (userInfo.standby3) {
            $cookies.put("admin_head_portrait", userInfo.standby3, {expires: expireDate, path: '/'});
        }
        //$window.localStorage['admin_userRight'] = JSON.stringify(vm.userRight);
        // add by liu.jinfeng@20170910 定义变量区分是否登录
        $cookies.put("client_logined", true);
    }
        /*setup();*/

    

    //初始化
    function init() {
        initSetting();
        tc_center();
        getselCpCategories(function (category) {
            angular.forEach(category, function (item, index) {
                if (item.categoryName == '新闻类别') {
                    vm.categories = item.categories;
                    console.log(vm.categories);
                }
            });
        });
    }

    init();

    //用户退出请求
    function req_userExit() {
        req.post('login/doLogout.do', {}).success(function (resp) {
            if (resp.code == '211') {
//                $state.go('root.login');
                $rootScope.user_online = false;
                $cookies.remove('client_uname');
                $cookies.remove('client_logined');
                $cookies.put('client_uname','');
                $cookies.put('client_logined','');
                initSetting();
                removeAllCookies();
            } else {
                console.log(resp.msg);
            }
        });
    }
    //重新登录前清除所有的cookie
    function removeAllCookies(){
        var cookies = $cookies.getAll();
        angular.forEach(cookies, function (v, k) {
            $cookies.remove(k);
        });
    }
    //用户退出
    vm.logOut = function () {
        req_userExit();
    };

    //获取稿件分类信息
    function getselCpCategories(callback) {
        req.post('classification/selCpCategories.do', {langType:0}).success(function (resp) {
            if (resp.code == '211') {
                vm.selCpCategories = resp.data;
                if (callback) callback(resp.data);
                console.log('success');
            } else {
                console.log(resp.msg);
            }
        });
    }
    getOnlineUserList();
    //获取在线用户列表
    function getOnlineUserList(){
        req.post('login/getOnLineUsers.do',{
            rows: '',
            page: ''
        }).success(function(resp){
            if(resp.code == '211'){
                vm.OnLineUsersList = resp.data;
                vm.onlineList_total = resp.other;
            }else if(resp.msg != '未登录'){
               console.log(resp.msg);
            }
        });
    }

    /**
     * 全文检索
     * */
    vm.searchAll = function () {
        /*getFullText.req_getFullText({
            page: 1,
            rows: 10,
            strWhere: vm.searchAllName
        });*/
        vm.randomNum = new Date().getTime() + Math.random() + Math.random();
        var fullTextUrl = $state.href('root.fullText', {searchAllName: vm.searchAllName,randomNum: vm.randomNum});
        window.open(fullTextUrl,'_blank');
    };

    /**
     * 获取广告位第一位图片
     */
    function getAdFirstShowImageData() {
        req.post('adver/adverToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                var adArray =  resp.data;
                var slides = $scope.slides = [];
                 /*   slides.push({image : adArray[0].file});
                    slides.push({image : adArray[1].file});
                    slides.push({image : adArray[2].file});*/
                vm.adFirstImageObj = {file: ""};
                for (var j = 0; j < adArray.length; j++) {
                    if (adArray[j]['position'] == 1) {
                        vm.adFirstImageObj = adArray[j];
                        slides.push({image : adArray[j].file});
                     //  break;
                    }
                }
            } else {
                console.log(resp.msg);
            }
        });
    }

    /**
     * 广告页或专题页的跳转
     * @param itemObj 广告位对象
     */
    vm.onToHeadShowAdTopicClick = function (itemObj) {
        var imgFile = itemObj['file'];
        if (imgFile) {
            var topicId = itemObj['topicId'];
            var adUrl = itemObj['url'];
            if (topicId) {
                $state.go('root.special', {'id': topicId});
            } else {
                window.open(adUrl);
            }
        }
    };

    // 请求
    getAdFirstShowImageData();
});
