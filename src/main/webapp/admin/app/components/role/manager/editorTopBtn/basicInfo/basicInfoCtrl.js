/**
 * Created by Sun on 2016/12/15.
 */
adminModule.controller('mEdtBasicInfoCtrl', function ($scope, $cookies, req, md5, $state, $rootScope) {
    var vm = this;
    //设置过期日期
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() + 30);
    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }

    //用户管理添加模态框显示
    vm.userModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //用户管理添加模态框隐藏
    vm.userModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };

    //初始化相关配置
    function initSetting(){
        //默认激活的导航项为全部稿件
        vm.acitiveSlideTit = 1;
        //设置过期日期
        var expireDate = new Date();
        expireDate.setDate(expireDate.getDate() + 30);
        //是否展示个人信息的详细信息的标识
        vm.showBasicInfo = false;
    }
    //初始化
    function init() {
        initSetting();
        getBaseInformData();
    }

    init();

    //选择激活的导航项
    vm.chooseManuscriptType = function (acitiveSlideTit) {
        vm.acitiveSlideTit = acitiveSlideTit;
        //如果是支付设置获取银行卡信息
        if(vm.acitiveSlideTit == 3){
            paySettingsInfoShow();
        }
    };
    //展开详细信息
    vm.showDetailBasicInfo = function () {
        vm.showBasicInfo = !vm.showBasicInfo;
        if(vm.showBasicInfo){
            $('#spread-detail-info').html('收起详细信息');
        }else{
            $('#spread-detail-info').html('展开详细信息');
        }

    };

    /**
     * 获取基本信息
     */
    function getBaseInformData() {
        req.post('basicInfo/selCpUserBasicInfo.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.baseInformObj = resp.data;
                $("#saveBiShowImageId").attr("src", vm.baseInformObj['standby3']);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //展示支付设置信息
    function paySettingsInfoShow() {
        req.post('basicInfo/selBankInfo.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.paySettingsInfo = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 处理头像修改
     */
    $("#saveBiShowImageId").attr("src", "admin/assets/img/icon_default_head.png");
    $scope.onUpdateHeadImageClick = function (element) {
        $("#saveBasicFormId").ajaxSubmit(function (resp) {
            if (resp.code == '211') {
                //刷新视图显示头像
                $scope.$apply(function(){
                    $rootScope.userPortrait  = resp.data;
                    //存用户头像
                    $cookies.put("admin_head_portrait",$rootScope.userPortrait, {expires: expireDate, path: '/'});
                    $rootScope.userPortrait = $cookies.get('admin_head_portrait');
                });
                layer.msg('头像修改成功');
            } else {
                layer.alert("头像修改失败");
            }
        });
        return false;
    };

    /**
     * 处理基本信息保存
     */
    vm.onBaseInfoSaveClick = function () {
        // 邮箱验证正则
        var regEmail = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;
       // 验证手机号码正则
        var regMobile = /^1[3,4,5,7,8]\d{9}$/;
        //邮政编码正则
        var regZipCode = /^[1-9]\d{5}$/;
        //联系电话正则（固定电话或者手机号码）
        var linkPhoneReg = /^(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/;
        //if (!vm.baseInformObj.tureName) {
        //    layer.alert("请输入真实姓名");
        //    return;
        //}
        if (!vm.baseInformObj.emailBind) {
           layer.alert("请输入绑定邮件");
           return;
        }
        if (vm.baseInformObj.emailBind && !regEmail.test(vm.baseInformObj.emailBind)) {
            layer.alert("请输入正确格式的绑定邮件");
            return;
        }
       if (!vm.baseInformObj.telBind) {
            layer.alert("请输入绑定手机");
            return;
        }
        if (vm.baseInformObj.telBind && !regMobile.test(vm.baseInformObj.telBind)) {
            layer.alert("请输入正确格式的绑定手机");
            return;
        }
        //if (!vm.baseInformObj.city) {
        //    layer.alert("请输入所属省市");
        //    return;
        //}
        if (vm.showBasicInfo && !vm.baseInformObj.zipcode) {
            layer.alert("请输入邮政编码");
           return;
        }
        if (vm.showBasicInfo && vm.baseInformObj.zipcode && !regZipCode.test(vm.baseInformObj.zipcode)) {
            layer.alert("请输入正确格式的邮政编码");
            return;
        }
        //if (!vm.baseInformObj.unitName) {
        //    layer.alert("请输入单位名称");
        //    return;
        //}
        //if (!vm.baseInformObj.address) {
        //    layer.alert("请输入详细地址");
        //    return;
        //}
        if (vm.showBasicInfo && !vm.baseInformObj.telContact) {
           layer.alert("请输入联系电话");
           return;
        }
        if (vm.showBasicInfo && vm.baseInformObj.telContact && !linkPhoneReg.test(vm.baseInformObj.telContact)) {
            layer.alert("请输入正确格式的联系电话");
            return;
        }
        if (vm.showBasicInfo && !vm.baseInformObj.emailContact) {
           layer.alert("请输入联系邮箱");
           return;
        }
        if (vm.showBasicInfo && vm.baseInformObj.emailContact && !regEmail.test(vm.baseInformObj.emailContact)) {
            layer.alert("请输入正确格式的联系邮箱");
            return;
        }
        req.post('basicInfo/upCpUserBasicInfo.do', {
            tureName: vm.baseInformObj.tureName,
            emailBind: vm.baseInformObj.emailBind,
            telBind: vm.baseInformObj.telBind,
            city: vm.baseInformObj.city,
            zipcode: vm.baseInformObj.zipcode,
            unitName: vm.baseInformObj.unitName,
            address: vm.baseInformObj.address,
            telContact: vm.baseInformObj.telContact,
            emailContact: vm.baseInformObj.emailContact
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert("保存成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 修改密码
     */
    vm.onBaseInfoUpdatePwdClick = function () {
        var pwdExp = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$/;
        if (!vm.currentPwd) {
            layer.alert("请输入当前密码");
            return;
        }
        if (!vm.newPwd) {
            layer.alert("请输入新密码");
            return;
        }
        if(!(pwdExp.test(vm.newPwd))){
            layer.alert('请输入8-16个字符密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
            return;
        }
        if (!vm.confirmPwd) {
            layer.alert("请输入确认密码");
            return;
        }
        if (vm.newPwd != vm.confirmPwd) {
            layer.alert("两次输入的密码不一致");
            return;
        }
        req.post('basicInfo/upPassword.do', {
            password: md5.createHash(vm.currentPwd),
            newPassword: md5.createHash(vm.newPwd)
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('修改密码成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };


    /**
     * 设置支付方式
     */
    vm.onBaseInfoSetPayClick = function () {
        //15位和18位身份证号码的正则表达式
        var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;
        //var cardNoReg = /^\d{16}|\d{19}$/;
        if (!vm.biAccountNum) {
            layer.alert("请输入账户或卡号");
            return;
        }
        if (!vm.biAccountName) {
            layer.alert("请输入户名");
            return;
        }
        if (!vm.biAccountIdCard) {
            layer.alert("请输入卡主的身份证");
            return;
        }
        if (vm.biAccountIdCard && (!regIdCard.test(vm.biAccountIdCard))) {
            layer.alert("请输入正确格式的卡户主身份证");
            return;
        }
        // if (!vm.biAccountBank) {
        //     layer.alert("请输入开户行");
        //     return;
        // }
        req.post('basicInfo/upCpUserPayType.do', {
            bankAccount: vm.biAccountNum,
            bankUsername: vm.biAccountName,
            bankIdCard: vm.biAccountIdCard,
            // bankName: vm.biAccountBank
            bankName: '中国邮政储蓄银行'
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert("设置支付方式成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };


});