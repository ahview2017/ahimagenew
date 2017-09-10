/**
 * Created by Sun on 2017/2/10.
 */
clientModule.controller('myInfoCtrl', function ($scope, $cookies, req, md5, $state, $rootScope,$stateParams) {
    var vm = this;

    // 验证手机号码正则
    var regMobile = /^1[3,4,5,7,8]\d{9}$/;

    //验证邮政编码
    var regZipcode= /^[1-9][0-9]{5}$/;

    // 邮箱验证正则
    var regEmail = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+(\.[a-zA-Z]{2,3})+$/;

    //15位和18位身份证号码的正则表达式
    var regIdCard = /^(^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$)|(^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[Xx])$)$/;


    //验证密码正则
    var pwdExp = /^(?![A-Z]+$)(?![a-z]+$)(?!\d+$)(?![\W_]+$)\S{8,16}$/;
    //初始化相关设置
    function initSetting(){
        vm.tab = 1; //默认显示基本信息
        //默认选中基本信息
        vm.activeTabClass1 = 'color-blue';
        // 路由获取tab信息
        if( $stateParams.activeTab){
            vm.tab =    $stateParams.activeTab;
        }
        //登录日志相关初始化
        vm.loginLogInfo = [];
        vm.loginInfo_total = 0;
        vm.itemsPerPage = 10;
        vm.pagination = {
            logCurrent: 1
        };

        //全部下载相关初始化
        vm.picDownArray = [];
        vm.allDown_total = 0;
        vm.itemsPerPage = 10;
        vm.pagination = {
            allDownCurrent: 1
        };
    }
    //页面初始化
    function init(){
        initSetting();
        getMineInformData();
        getExcepMineInfoData();
    }
    init();

    // 展示除了我的信息外的其他信息
   function getExcepMineInfoData(){
       switch(parseInt(vm.tab)){
           case 3:  //登录日志
               getLoginLog(vm.pagination.logCurrent);
               break;
           case 6: //一周下载
               //因为一周下载和全部下载用的同一个接口，所以开始之前初始化下载数组，避免这两个相互影响
               vm.picDownArray = [];
               getPicDownData("", "", "", "", "", 1, 1,1);
               break;
           case 7: //全部下载
               //因为一周下载和全部下载用的同一个接口，所以开始之前初始化下载数组，避免这两个相互影响
               vm.picDownArray = [];
               getPicDownData("", "", "", "", "", 1, 1,3);
               break;

       }
   }
    //初始化激活tab类
    function initActiveTabClass(){
        vm.activeTabClass1 = '';
        vm.activeTabClass2 = '';
        vm.activeTabClass3 = '';
        vm.activeTabClass4 = '';
        vm.activeTabClass5 = '';
        vm.activeTabClass6 = '';
        vm.activeTabClass7 = '';
        vm.activeTabClass8 = '';
        vm.activeTabClass9 = '';
    }
    //菜单切换
    vm.myInfoMenuClick = function (myInfoMenu) {
        initActiveTabClass();
        switch (parseInt(myInfoMenu)) {
            case 1 :
                vm.tab = 1;  //基本信息
                vm.activeTabClass1 = 'color-blue';
                break;
            case 2:
                vm.tab = 2;  //下载信息
                vm.activeTabClass2 = 'color-blue';
                break;
            case 3:
                vm.tab = 3;  //登录日志
                getLoginLog(vm.pagination.logCurrent);
                vm.activeTabClass3 = 'color-blue';
                break;
            case 4:
                vm.tab = 4;  //修改资料
                vm.activeTabClass4 = 'color-blue';
                break;
            case 5:
                vm.tab = 5;  //修改密码
                vm.activeTabClass5 = 'color-blue';
                break;
            case 6:
                vm.tab = 6;  //一周下载
                vm.activeTabClass6 = 'color-blue';
                //初始化页码显示
                vm.pagination = {
                    allDownCurrent: 1
                };
                //因为一周下载和全部下载用的同一个接口，所以开始之前初始化下载数组，避免这两个相互影响
                vm.picDownArray = [];
                getPicDownData("", "", "", "", "", 1, 1,1);
                break;
            case 7:
                vm.tab = 7;  //全部下载
                vm.activeTabClass7 = 'color-blue';
                //初始化页码显示
                vm.pagination = {
                    allDownCurrent: 1
                };
                //因为一周下载和全部下载用的同一个接口，所以开始之前初始化下载数组，避免这两个相互影响
                vm.picDownArray = [];
                getPicDownData("", "", "", "", "", 1, 1,3);
                break;
            case 8:
                var myOrderurl = $state.href('root.myOrder');
                window.open(myOrderurl);
                vm.activeTabClass8 = 'color-blue';
                break;
            case 9:
                var needChannelurl = $state.href('root.needChannel');
                window.open(needChannelurl);
                vm.activeTabClass9 = 'color-blue';
                break;
        }
    };

    /**
     * 获取我的信息数据
     */
    function getMineInformData() {
        req.post("myInfo/selCpUserBasicInfo.do", {}).success(function (resp) {
            if (resp.code == '211') {
                vm.mineInformObj = resp.data;
                vm.editInformObj = vm.mineInformObj;
                vm.editInformObj.isPublish = vm.editInformObj.isPublish + "";
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    /**
     * 获取登录日志
     */
    function getLoginLog(pageNumber){
        req.post("logCtro/getUserLoginLog.do", {
            siteId: 1,
            flag:1,
            rows: vm.itemsPerPage,
            page: pageNumber,
        }).success(function (resp) {
            if (resp.code == '211') {
               vm.loginLogInfo =   resp.data;
                vm.loginInfo_total = resp.other;
                vm.loginLogtotalPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //页数变化
    vm.pageChanged = function (pageNumber) {
        //登录日志
        if(vm.tab == 3){
            getLoginLog(pageNumber);
        }
        //全部下载
        if(vm.tab == 6 || vm.tab == 7){
      /*      getPicDownData("", "", "", "", "", pageNumber, 1,3);*/
            getPicDownData(vm.picAllAuthor, vm.picAllFileName, vm.picAllTitle,
                $('#picAllStartId').val(), $('#picAllEndId').val(), pageNumber, 1,3);
        }
    };

    /**
     * 获取图片下载数据
     */
    function getPicDownData(uploader, fileName, title, startTime, endTime, page, type,queryType) {
        req.post("pictureDownRecord/selPictureDowns.do", {
            uploader: uploader,
            fileName: fileName,
            title: title,
            starttime: startTime,
            endtime: endTime,
            flag: queryType,
            page: page,
            rows: 10
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.picDownArray = resp.data;
                vm.allDown_total = resp.other;
                vm.allDownTotalPages = resp.page;
            }else if(resp.msg != '未登录'){
               vm.picDownArray = [];
               layer.alert(resp.msg);
            }
        });
    }

    /**
     * 修改我的信息
     */
    vm.onEditMineInformClick = function () {

        if(!vm.editInformObj.tureName) {
            layer.alert("请输入真实姓名！");
            return;
        }
        if(!vm.editInformObj.idCard) {
            layer.alert("请输入身份证号！");
            return;
        }
        if(!(regIdCard.test(vm.editInformObj.idCard))) {
            layer.alert("身份证号格式不正确！");
            return;
        }
        if(!vm.editInformObj.authorName) {
            layer.alert("请输入作者名！");
            return;
        }
        if(vm.editInformObj.zipcode){
            if(!(regZipcode.test(vm.editInformObj.zipcode))) {
                layer.alert("邮政编码格式不正确！");
                return;
            }
        }

        if(!vm.editInformObj.emailBind) {
            layer.alert("请输入电子邮箱！");
            return;
        }
        if(!(regEmail.test(vm.editInformObj.emailBind))) {
            layer.alert("电子邮箱格式不正确！");
            return;
        }
        if(!vm.editInformObj.telBind) {
            layer.alert("请输入联系电话！");
            return;
        }
        if(!(regMobile.test(vm.editInformObj.telBind))) {
            layer.alert("联系电话格式不正确！");
            return;
        }
        if(typeof(vm.editInformObj.isPublish)=="undefined") {
            layer.alert("请选择是否公开个人信息！");
            return;
        }

        req.post("myInfo/upCpUserBasicInfo.do", {
            tureName: vm.editInformObj.tureName,
            idCard: vm.editInformObj.idCard,
            province: vm.editInformObj.province,
            city: vm.editInformObj.province,
            unitName: vm.editInformObj.unitName,
            authorName: vm.editInformObj.authorName,
            address: vm.editInformObj.address,
            zipcode: vm.editInformObj.zipcode,
            emailBind: vm.editInformObj.emailBind,
            telBind: vm.editInformObj.telBind,
            isPublish: vm.editInformObj.isPublish
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('修改我的信息成功');
            } else {
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 修改用户密码
     */
    vm.onEditPwdClick = function () {
        if (!vm.editMiPwdOld) {
            layer.alert('密码不能为空');
            return;
        }
        if (!vm.editMiPwdNew) {
            layer.alert('新密码不能为空');
            return;
        }
        if(vm.editMiPwdNew && !(pwdExp.test(vm.editMiPwdNew))) {
            layer.alert('请输入8-16个字符的新密码，且密码要含有小写字母、大写字母、数字、特殊符号的两种及以上');
            return;
        }
        if (vm.editMiPwdNew != vm.editMiPwdCon) {
            layer.alert('密码不相等');
            return;
        }
        if (vm.editMiPwdNew == vm.editMiPwdOld) {
            layer.alert('新密码和原密码不能相等');
            return;
        }
        req.post("myInfo/upPassword.do", {
            password: md5.createHash(vm.editMiPwdOld),
            newPassword: md5.createHash(vm.editMiPwdNew)
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.alert('修改密码成功');
            } else {
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 搜索下载图片
     */
    vm.onPicDownSearchClick = function (type) {
        vm.pagination = {
            allDownCurrent: 1
        };
        if (type === 0) {
            getPicDownData(vm.picAuthor, vm.picFileName, vm.picTitle,
                $('#picStartId').val(), $('#picEndId').val(), 1, 1,3);
        } else {
            getPicDownData(vm.picAllAuthor, vm.picAllFileName, vm.picAllTitle,
                $('#picAllStartId').val(), $('#picAllEndId').val(), 1, 1,3);
        }
    };


});