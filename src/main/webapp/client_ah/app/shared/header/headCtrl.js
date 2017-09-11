// 监视器 监视ng-repeat循环完成
clientModule.directive('onFinishRendersFilters', function($timeout) {
	return {
		restrict : 'A',
		link : function(scope, element, attr) {
			if (scope.$last === true) {
				$timeout(function() {
					jQuery(".slideBox").slide({
						mainCell : ".bd ul",
						autoPlay : true
					});

				});
			}
		}
	};
});

clientModule.controller('headerCtrl', function($scope, $cookies, req, md5,
		$state, $rootScope, layerIfShow, getFullText) {
	var vm = this;

	function initSetting() {
		// 从cookie获取客户端用户名
		$rootScope.client_uName = $cookies.get('client_uname');
		$rootScope.client_logined = $cookies.get('client_logined');
	}
	// 登录按钮
	vm.login_user = function() {
		$("#gray").css("display", 'block');
		$("#login_div").css("display", 'block');
	}
	// 初始化样式
	function tc_center() {
		var _top = ($(window).height() - $("#login_div").height()) / 2;
		var _left = ($(window).width() - $("#login_div").width()) / 2;
		$("#login_div").css({
			top : _top,
			left : _left
		});
		$("#login_div").css({
			top : _top,
			left : _left
		});
		var _top1 = ($(window).height() - $("#register_div").height()) / 2;
		var _left1 = ($(window).width() - $("#register_div").width()) / 2;
		$("#register_div").css({
			top : _top1,
			left : _left1
		});
	}
	// 关闭按钮
	vm.go_close = function() {
		$("#gray").hide();
		$(".login_box").hide();
		$(".register_box").hide();
	}
	// 注册按钮
	vm.register = function() {
		$("#gray").css("display", 'block');
		$("#register_div").css("display", 'block');
	}
	// 忘记密码
	vm.forgetPassword = function() {
		$("#gray").hide();
		$(".login_box").hide();
		$state.go('root.forgetPassword');
	}
	// 登录
	vm.loginIn = function() {
		// 验证用户信息
		if (!valid_info())
			return;
		req_getSalt(function() {
			req_login();
		});
	}
	// 验证用户信息
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
	// 发送验证码
	var clock = '';
	var nums = 60;
	var start = true;
	vm.sendmsg = function(form) {
		if ($("#login_user").val() == '') {
			layer.msg("请输入用户名");
			$('#login_user').focus();
			return;
		}

		if (nums == 60 && start) {
			start = false;
			$("#code").removeAttr('ng-click');
			$("#code").attr('disabled', "true");
			$("#code").html(nums + '秒后可重新获取');
			clock = setInterval(doLoop, 1000); // 一秒执行一次
			console.log("发送短信");
			req.post('phonemsg/getPhoneVilidate.do', {
				userName : $("#login_user").val()
			}).success(function(resp) {
				if (resp.code != '211') {
					layer.alert(resp.msg);
				}
			});

		}
	}
	vm.sendmsg2 = function() {
		if(vm.mobilePhone==''||!(/^1[3,4,5,7,8]\d{9}$/.test(vm.mobilePhone))){
            layer.alert('请输入正确格式的移动电话');
            return;
        }
		
		if (nums == 60 && start) {
			start = false;
			$("#code2").removeAttr('ng-click');
			$("#code2").attr('disabled', "true");
			$("#code2").html(nums + '秒后可重新获取');
			clock = setInterval(doLoop2, 1000); // 一秒执行一次
			console.log("发送短信");
			req.post('phonemsg/sendMsgCode.do', {
				phoneNum : vm.mobilePhone
			}).success(function(resp) {
				if (resp.code != '211') {
					layer.alert(resp.msg);
				}
			});
			
		}
	}
	function doLoop() {
		nums--;
		if (nums > 0) {
			$("#code").html(nums + '秒后可重新获取');
		} else {
			clearInterval(clock); // 清除js定时器
			$("#code").html('获取验证码');
			$("#code").attr('ng-click', 'header.sendmsg()');
			$("#code").removeAttr('disabled');
			nums = 60; // 重置时间
			start = true;
		}
	}
	function doLoop2() {
		nums--;
		if (nums > 0) {
			$("#code2").html(nums + '秒后可重新获取');
		} else {
			clearInterval(clock); // 清除js定时器
			$("#code2").html('获取验证码');
			$("#code2").attr('ng-click', 'header.sendmsg2()');
			$("#code2").removeAttr('disabled');
			nums = 60; // 重置时间
			start = true;
		}
	}
	// 获取salt
	function req_getSalt(callback) {
		req.post('login/getSalt.do', {
			userName : $("#login_user").val()
		}).success(function(resp) {
			if (resp.code == '211') {
				vm.loginSalt = resp.data;
				if (callback)
					callback();
			} else {
				console.log(resp.msg);
			}
		});
	}
	// 登录请求
	function req_login() {
		var saltPwdMix = md5.createHash($("#login_pass").val()) + vm.loginSalt;
		vm.finalPwd = md5.createHash(saltPwdMix);
		req.post('login/doLogin.do', {
			userName : $("#login_user").val(),
			password : vm.finalPwd,
			vilidate : $("#login_code").val(),
			type : 1
		}).success(
				function(resp) {
					if (resp.code == '211') {
						// 用户信息
						var userInfo = resp.data.user;
						// 用户权限
						var userRight = resp.data.right;
						// 存储角色信息
						var roleInfo = resp.data.role;
						saveUserInfo(userInfo, roleInfo);
						if (vm.cookie_latestHash) {
							location.hash = vm.cookie_latestHash;
							// 重新刷新页面
							location.reload();
						} else {
							$state.go('root.index', {}, {
								reload : true
							});
						}
						$rootScope.user_online = true;
					} else {
						// 模拟点击执行
						$('#vCodeId').trigger("click");
						layer.msg(resp.msg);
						if (resp.msg == '验证码出错') {
							// layer.msg(resp.msg);
							$('#login_code').focus();
						} else if (resp.msg == '该用户不存在或正在申请中'
								|| resp.msg == '无该用户'
								|| resp.msg == '该用户暂时无法登陆'
								|| resp.msg == 'salt失效，请重新登录') {
							$('#login_user').focus();
						} else {
							$('#login_pass').focus();
						}
					}
				});
	}
	// 存储用户信息
	function saveUserInfo(userInfo, roleInfo) {
		// 设置过期日期
		var expireDate = new Date();
		expireDate.setDate(expireDate.getDate() + 30);
		roleInfoStr = angular.toJson(roleInfo, true);
		// 设置cookies
		$cookies.put("client_uid", userInfo.id, {
			path : '/'
		});
		$cookies.put("client_uname", userInfo.userName, {
			path : '/'
		});
		$cookies.put("client_upwd", userInfo.password, {
			path : '/'
		});
		$cookies.put("admin_uname", userInfo.userName, {
			path : '/'
		});
		$cookies.put("admin_upwd", userInfo.password, {
			path : '/'
		});
		$cookies.put("admin_id", userInfo.id, {
			path : '/'
		});
		$cookies.put("admin_authorName", userInfo.authorName, {
			expires : expireDate,
			path : '/'
		});
		$cookies.put("admin_roleInfo", roleInfoStr, {
			expires : expireDate,
			path : '/'
		});
		$cookies.put("admin_tureName", userInfo.tureName, {
			expires : expireDate,
			path : '/'
		});
		$cookies.put("admin_lastLoginTime", userInfo.lastLoginTime, {
			expires : expireDate,
			path : '/'
		});
		if (userInfo.standby3) {
			$cookies.put("admin_head_portrait", userInfo.standby3, {
				expires : expireDate,
				path : '/'
			});
		}
		// $window.localStorage['admin_userRight'] =
		// JSON.stringify(vm.userRight);
		// add by liu.jinfeng@20170910 定义变量区分是否登录
		$cookies.put("client_logined", true);
	}
	/* setup(); */

	// 判断用户名是否已存在
	// 匹配用户名正则
	var uNameRegExp = /^[\.a-zA-Z\u4e00-\u9fa50-9_-]{3,16}$/;
	vm.req_checkHoverUserExist = function() {
		// layer.alert(vm.loginName);
		if (!vm.loginName) {
			vm.state = 3;
			return;
		} else if (!(uNameRegExp.test(vm.loginName))) {
			vm.state = 1;
			return;
		} else {
			req.post('login/checkUser.do', {
				userName : vm.loginName
			}).success(function(resp) {
				if (resp.code == '211') {
					// valid = false;
					vm.state = 2;
				} else {
					vm.state = 0;
				}
			});
		}

	}
	//确认注册
    vm.confirmRegister = function(form){
//    	layer.alert("success");
        validRegisterInfo(form,function(){
            req_Register();
        });
//        console.log(form);
//        console.log(form.$valid);
//        if(form.$valid){
//            req_Register();
//        }
    }
    //校验注册信息
    function validRegisterInfo(form,callback){
        var idNumExp = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        var pwdExp = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$/;
        var standby1 = /[1-9][0-9]{4,10}/;
        var standby2 = /^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$/;
        var postCode = /^[1-9][0-9]{5}$/;
        var charLen = /^.{8,16}$/;
        var pwdHintQue = /^.{0,20}$/;
        //验证用户信息
        if(!valid_Info()) return;

        if(!vm.applyCategory){
        	layer.alert('请选择申请类别');
            return;
        }
        if(!vm.enterPwd){
            layer.alert('请输入8-16个密码');
            return;
        }
        if(!(pwdExp.test(vm.enterPwd))){
            layer.alert('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
            return;
        }
        if(!vm.confirmPwd){
            layer.alert('请输入8-16个字符确认密码');
            return;
        }
        if(vm.enterPwd != vm.confirmPwd){
            layer.alert('两次输入密码必须一致');
            return;
        }
        if(form.pwdHintQuestion.$error.required ||!(pwdHintQue.test(vm.pwdQuestion))){
            layer.alert('请输入不多于20个字符密码提示问题');
            return;
        }
        if(form.pwdHintAnswer.$error.required || !(charLen.test(vm.pwdAnswer))){
            layer.alert('请输入8-16个字符密码提示答案');
            return;
        }
       if($("#province").val()==''||$("#province").val()=='省份'){
            layer.alert('请选择所在省');
            return;
        } 
        if($("#city").val()==''||$("#city").val()=='地级市'){
            layer.alert('请选择所在市');
            return;
        }
        if(form.realName.$error.required){
            layer.alert('请填写真实姓名');
            return;
        }
        if(!vm.IdNumber||!(idNumExp.test(vm.IdNumber))){
            layer.alert('请输入正确格式的身份证号');
            return;
        }
        if(form.unitName.$error.required){
            layer.alert('请填写单位名称');
            return;
        }
        if(form.postcode.$error.required || !(postCode.test(vm.postcode))){
            layer.alert('请填写正确格式的邮政编码');
            return;
        }
        if(form.contactPhone.$error.pattern){
            layer.alert('请输入正确格式的联系电话');
            return;
        }
        if(form.mobilePhone.$error.required || form.mobilePhone.$error.pattern){
            layer.alert('请输入正确格式的移动电话');
            return;
        }
        if(form.register_code.$error.required || form.register_code.$error.pattern){
        	layer.alert('请输入正确格式的验证码');
        	return;
        }
        if(form.mail.$error.required || form.mail.$error.pattern){
            layer.alert('请输入正确格式的邮箱');
            return;
        }
        if(vm.standby1 && !(standby1.test(vm.standby1))){
            layer.alert('请输入正确格式的QQ号码');
            return;
        }
        if(vm.standby2 && !(standby2.test(vm.standby2))){
            layer.alert('请输入正确格式的微信号');
            return;
        }

        if(callback) callback();
    }
  //校验信息
    function valid_Info(){
        var valid = true;
        if(!vm.loginName){
            valid = false;
            layer.alert("请输入用户名");
        }else if(!(uNameRegExp.test(vm.loginName))){
            valid = false;
            layer.alert("请输入正确格式的用户名");
        }
        return valid;
    }
  //确认注册请求
    function req_Register(){
        var reqData = {
            userName: vm.loginName,
            roleId: vm.applyCategory,  //用户类别
            subscriberType: 0, //订户类型，默认是个人
            subscriptionType:'', //订阅类型
            tureName: vm.realName,
            photographyDirection: 00000000,
            password: md5.createHash(vm.enterPwd), //密码为密文
            question: vm.pwdQuestion,
            answer:  vm.pwdAnswer,
            province: $("#province").val(),
            city: $("#city").val(),
            authorName: vm.loginName,
            unitName: vm.unitName,
            zipcode: vm.postcode,
            telBind: vm.mobilePhone,
            telContact: vm.contactPhone||'',
            emailBind: vm.mail,
            standby1: vm.standby1||'',
            standby2: vm.standby2||'',
            feeType: 0,
            idCard: vm.IdNumber,
            code: vm.register_code,
            is_publish: $('input[name="publishInfo"]:checked ').val(),
            langType:0
        };
        console.log(reqData);
        req.post('login/registerOne.do',reqData).success(function(resp){
            if(resp.code == '211'){
            	$('#register_form')[0].reset();//清空表单
            	//关闭窗口
            	$("#gray").hide();
        		$(".register_box").hide();
                var sendData ={
                    userName: vm.loginName,
                    password: md5.createHash(vm.enterPwd), //密码为密文
                    userEmail:vm.mail
                }
                if(vm.applyCategory == '3'){
                    sendData['roleId'] = vm.applyCategory;
                }
                //发送邮件
                req.post("/mail/sendEmailByName.do",sendData).success(function (response) {
                    if(response.code == '211'){
                        console.log("邮件发送："+response.msg);
                    }
                });
                //发送短信
                req.post("/phonemsg/sendMessageByUserName.do",sendData).success(function (response) {
                    if(response.code == '211'){
                        console.log("短信发送："+response.msg);
                    }
                });
                $state.go('root.registerSuccess',{userName:vm.loginName,emailBind:vm.mail,telBind:vm.mobilePhone});
            }else{
                layer.alert(resp.msg);
            }
        });
    }
	// 初始化
	function init() {
		initSetting();
		tc_center();
		getselCpCategories(function(category) {
			angular.forEach(category, function(item, index) {
				if (item.categoryName == '新闻类别') {
					vm.categories = item.categories;
					// console.log(vm.categories);
				}
			});
		});

	}

	init();

	// 用户退出请求
	function req_userExit() {
		req.post('login/doLogout.do', {}).success(function(resp) {
			if (resp.code == '211') {
				// $state.go('root.login');
				$rootScope.user_online = false;
				$cookies.remove('client_uname');
				$cookies.remove('client_logined');
				$cookies.put('client_uname', '');
				$cookies.put('client_logined', '');
				initSetting();
				removeAllCookies();
			} else {
				console.log(resp.msg);
			}
		});
	}
	// 重新登录前清除所有的cookie
	function removeAllCookies() {
		var cookies = $cookies.getAll();
		angular.forEach(cookies, function(v, k) {
			$cookies.remove(k);
		});
	}
	// 用户退出
	vm.logOut = function() {
		req_userExit();
	};

	// 获取稿件分类信息
	function getselCpCategories(callback) {
		req.post('classification/selCpCategories.do', {
			langType : 0
		}).success(function(resp) {
			if (resp.code == '211') {
				vm.selCpCategories = resp.data;
				if (callback)
					callback(resp.data);
				console.log('success');
			} else {
				console.log(resp.msg);
			}
		});
	}
	getOnlineUserList();
	// 获取在线用户列表
	function getOnlineUserList() {
		req.post('login/getOnLineUsers.do', {
			rows : '',
			page : ''
		}).success(function(resp) {
			if (resp.code == '211') {
				vm.OnLineUsersList = resp.data;
				vm.onlineList_total = resp.other;
			} else if (resp.msg != '未登录') {
				console.log(resp.msg);
			}
		});
	}

	/**
	 * 全文检索
	 */
	vm.searchAll = function() {
		/*
		 * getFullText.req_getFullText({ page: 1, rows: 10, strWhere:
		 * vm.searchAllName });
		 */
		vm.randomNum = new Date().getTime() + Math.random() + Math.random();
		var fullTextUrl = $state.href('root.fullText', {
			searchAllName : vm.searchAllName,
			randomNum : vm.randomNum
		});
		window.open(fullTextUrl, '_blank');
	};

	/**
	 * 获取广告位第一位图片
	 */
	function getAdFirstShowImageData() {
		req.post('adver/adverToHomePage.do', {}).success(function(resp) {
			if (resp.code == '211') {
				var adArray = resp.data;
				var slides = $scope.slides = [];
				/*
				 * slides.push({image : adArray[0].file}); slides.push({image :
				 * adArray[1].file}); slides.push({image : adArray[2].file});
				 */
				vm.adFirstImageObj = {
					file : ""
				};
				for (var j = 0; j < adArray.length; j++) {
					if (adArray[j]['position'] == 1) {
						vm.adFirstImageObj = adArray[j];
						slides.push({
							image : adArray[j].file
						});
						// break;
					}
				}
			} else {
				console.log(resp.msg);
			}
		});
	}

	/**
	 * 广告页或专题页的跳转
	 * 
	 * @param itemObj
	 *            广告位对象
	 */
	vm.onToHeadShowAdTopicClick = function(itemObj) {
		var imgFile = itemObj['file'];
		if (imgFile) {
			var topicId = itemObj['topicId'];
			var adUrl = itemObj['url'];
			if (topicId) {
				$state.go('root.special', {
					'id' : topicId
				});
			} else {
				window.open(adUrl);
			}
		}
	};

	// 请求
	getAdFirstShowImageData();
});
