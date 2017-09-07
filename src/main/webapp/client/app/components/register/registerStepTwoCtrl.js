clientModule.controller('registerStepTwoCtrl',function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, ellipsis, $stateParams, cityList, regex){
    var vm = this;
    //取得从路由传来的用户名
    vm.loginName = $stateParams.userName;
    //取得从路由传来的用户类别
    vm.applyCategory = $stateParams.applyCategory;
    //匹配用户名正则
    var uNameRegExp = /^[\.a-zA-Z\u4e00-\u9fa50-9_-]{3,16}$/;



    //通过路由传过来的用户类别判断是摄影师还是订户，摄影师为3，订户为5
    if(vm.applyCategory == '3'){
        vm.applyCategoryName = '摄影师';
    }else if(vm.applyCategory == '5'){
        vm.applyCategoryName = '订户';
    }
    //页面初始化
    function init(){
        initRegisterSetting();
        initRegisterInfo();
    }
    init();
    //初始化注册信息
    function initRegisterInfo(){
        vm.registerInfo = {
            ifPublic:'公开',
            draftWay:'0',
            userCategory:'0'
        }
        vm.registerRegex = {
            Pwd:'^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$',
            charLen: '^.{8,16}$',
            pwdHintQue:'^.{0,20}$'
        }
    }
    //初始化注册相关设置
    function initRegisterSetting(){
        //获取城市列表
        vm.cityList = cityList.citylist;
        //常用正则
        vm.regex = regex;
    }
    //确认注册
    vm.confirmRegister = function(form){
        validRegisterInfo(form,function(){
            req_Register();
        });
        console.log(form);
        console.log(form.$valid);
        if(form.$valid){
            req_Register();
        }
    }
//发送验证码
    var clock = '';
    var nums = 10;
    var start = true;
    vm.sendmsg = function(form){
    	if(form.mobilePhone.$error.required || form.mobilePhone.$error.pattern){
            layer.alert('请输入正确格式的移动电话');
            return;
        }
    	
    	if(nums==10&&start){
    		start = false;
	    	$("#code").removeAttr('ng-click');
	    	$("#code").attr('disabled',"true");
	    	$("#code").html(nums+'秒后可重新获取');
	    	clock = setInterval(doLoop, 1000); //一秒执行一次
	    	console.log("发送短信");
	    	req.post('phonemsg/sendMsgCode.do',{
	    		phoneNum: vm.registerInfo.mobilePhone
            }).success(function(resp){
                if(resp.code == '211'){
                  
                }else{
                    layer.alert("获取验证码失败");
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
		     $("#code").attr('ng-click','registerStepTwo.sendmsg(register_form');
		     $("#code").removeAttr('disabled');
		     nums = 10; //重置时间
		     start = true;
	    }
    }

    //校验注册信息
    function validRegisterInfo(form,callback){
        var idNumExp = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;
        var pwdExp = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$/;
        var standby1 = /[1-9][0-9]{4,10}/;
        var standby2 = /^[a-zA-Z]{1}[-_a-zA-Z0-9]{5,19}$/;

        //alert(vm.registerInfo.phoneCode);
        //验证用户信息
        if(!valid_Info()) return;

        //摄影师才有
        if(vm.applyCategory == '3' && form.locationArea.$error.required){
            layer.alert('请选择所在地域');
            return;
        }
        //if(form.enterPwd.$error.required || form.enterPwd.$error.pattern){
        //    layer.alert('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上）');
        //    return;
        //}
        //if(form.confirmPwd.$error.required || form.confirmPwd.$error.pattern){
        //    layer.alert('请输入8-16个字符确认密码');
        //    return;
        //}
      
        if(!vm.registerInfo.enterPwd){
            layer.alert('请输入8-16个密码');
            return;
        }
        if(!(pwdExp.test(vm.registerInfo.enterPwd))){
            layer.alert('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
            return;
        }
        if(!vm.registerInfo.confirmPwd){
            layer.alert('请输入8-16个字符确认密码');
            return;
        }
        if(vm.registerInfo.enterPwd != vm.registerInfo.confirmPwd){
            layer.alert('两次输入密码必须一致');
            return;
        }
        if(form.pwdHintQuestion.$error.required || form.pwdHintQuestion.$error.pattern){
            layer.alert('请输入不多于20个字符密码提示问题');
            return;
        }
        if(form.pwdHintAnswer.$error.required || form.pwdHintAnswer.$error.pattern){
            layer.alert('请输入8-16个字符密码提示答案');
            return;
        }
        if(form.locationProv.$error.required){
            layer.alert('请选择所在省');
            return;
        }
        if(form.locationCity.$error.required){
            layer.alert('请选择所在市');
            return;
        }
        if(form.realName.$error.required){
            layer.alert('请填写真实姓名');
            return;
        }
        if(vm.registerInfo.userCategory == '0' &&  !vm.registerInfo.IdNumber){
            layer.alert('请输入正确格式的身份证号');
            return;
        }
        if(vm.registerInfo.userCategory == '0' && vm.registerInfo.IdNumber && !(idNumExp.test(vm.registerInfo.IdNumber))){
            layer.alert('请输入正确格式的身份证号');
            return;
        }
        //订户才有
        if( vm.applyCategory == '5' && form.contactPerson.$error.required){
            layer.alert('请填写联系人');
            return;
        }
        //摄影师才有
        if(vm.applyCategory == '3' && form.authorName.$error.required){
            layer.alert('请填写作者名');
            return;
        }
        if(form.unitName.$error.required){
            layer.alert('请填写单位名称');
            return;
        }
        if(form.postcode.$error.required || form.postcode.$error.pattern){
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
        //ch add by liu.jinfeng@20170906
        // alert(vm.registerInfo.phoneCode+"=="+(/^\d{4}$/.test(vm.registerInfo.phoneCode)));
        if(form.phoneCode.$error.required || !(/^\d{6}$/.test(vm.registerInfo.phoneCode))){
        	layer.alert('请输入6位数字验证码');
            return;
        }
        if(form.mail.$error.required || form.mail.$error.pattern){
            layer.alert('请输入正确格式的邮箱');
            return;
        }
        if(vm.registerInfo.standby1 && !(standby1.test(vm.registerInfo.standby1))){
            layer.alert('请输入正确格式的QQ号码');
            return;
        }
        if(vm.registerInfo.standby2 && !(standby2.test(vm.registerInfo.standby2))){
            layer.alert('请输入正确格式的微信号');
            return;
        }

        //摄影师才有
        if(vm.applyCategory == '3'  && vm.registerInfo.draftWay == '0'){
            if(form.mailContactAddress.$error.required){
                layer.alert('请填写通讯地址');
                return;
            }
            if(form.mailDraftName.$error.required){
                layer.alert('请填写收稿费人姓名');
                return;
            }
            if(form.mailIdNum.$error.required || form.mailIdNum.$error.pattern){
                layer.alert('请输入正确格式的身份证号');
                return;
            }
            if(form.mailPostCode.$error.required || form.mailPostCode.$error.pattern){
                layer.alert('请输入正确格式的邮政编码');
                return;
            }
        }
        if(vm.applyCategory == '3'  && vm.registerInfo.draftWay == '1'){
            if(form.bankAccount.$error.required){
                layer.alert('请输入邮政储蓄存折帐号或邮政储蓄卡号');
                return;
            }
            if(form.bankUsername.$error.required){
                layer.alert('请输入上述卡的户名');
                return;
            }
            if(form.bankIdCard.$error.required || form.bankIdCard.$error.pattern){
                layer.alert('请输入正确格式的上述卡户名的身份证号');
                return;
            }
            if(form.bankName.$error.required){
                layer.alert('请输入开户行');
                return;
            }
        }
        if(callback) callback();
    }

    //改变省的时候
    vm.changeProv = function (prov) {
        for(var i = 0; i < vm.cityList.length; i++){
            if(prov == vm.cityList[i].p){
                vm.registerInfo.cities = vm.cityList[i].c;
            }
        }
    };
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
    //判断用户名是否已存在
    vm.req_checkHoverUserExist = function(){
        if(!vm.loginName){
            vm.state = 3;
            return;
        }else if(!(uNameRegExp.test(vm.loginName))) {
            vm.state = 1;
            return;
        }else {
            req.post('login/checkUser.do',{
                userName: vm.loginName
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

    //确认注册请求
    function req_Register(){


        var checkDerection = "";

        $(".checkboxModel").each(function () {
            // console.log(this.checked);
            if(this.checked){
                checkDerection+='1';
            }else{
                checkDerection+='0';
            }

        });

        //alert(vm.registerInfo.userCategory);
        var reqData = {
            userName: vm.loginName,
            roleId: vm.applyCategory,  //用户类别
            subscriberType: vm.registerInfo.userCategory, //订户类型
            subscriptionType: vm.registerInfo.subscriptionType, //订阅类型
            tureName: vm.registerInfo.realName,
            photographyDirection: checkDerection,
            password: md5.createHash(vm.registerInfo.enterPwd), //密码为密文
            question: vm.registerInfo.pwdQuestion,
            answer:  vm.registerInfo.pwdAnswer,
            province: vm.registerInfo.selProv,
            city: vm.registerInfo.selCity,
            authorName: vm.registerInfo.contactPerson || vm.registerInfo.authorName,
            unitName: vm.registerInfo.unitName,
            zipcode: vm.registerInfo.postcode,
            telBind: vm.registerInfo.mobilePhone,
            telContact: vm.registerInfo.contactPhone,
            emailBind: vm.registerInfo.mail,
            standby1: vm.registerInfo.standby1,
            standby2: vm.registerInfo.standby2,
            feeType: vm.registerInfo.draftWay,
            mailAddress: vm.registerInfo.mailContactAddress,
            mailUsername: vm.registerInfo.mailDraftName,
            mailIdCard: vm.registerInfo.mailIdNum,
            mailZipCode: vm.registerInfo.mailZipCode,
            bankAccount: vm.registerInfo.bankAccount,
            bankUsername: vm.registerInfo.bankUsername,
            bankIdCard: vm.registerInfo.bankIdCard,
            bankName: vm.registerInfo.bankName,
            idCard: vm.registerInfo.IdNumber,
            code: vm.registerInfo.phoneCode,
            langType:0
        };
        req.post('login/registerOne.do',reqData).success(function(resp){
            if(resp.code == '211'){
                var sendData ={
                    userName: vm.loginName,
                    password: md5.createHash(vm.registerInfo.enterPwd), //密码为密文
                    userEmail:vm.registerInfo.mail
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
                $state.go('root.registerSuccess',{userName:vm.loginName,emailBind:vm.registerInfo.mail,telBind:vm.registerInfo.mobilePhone});
            }else{
                layer.alert(resp.msg);
            }
        });
    }





});