adminModule.controller('headCtrl',function($translate, $scope, $cookies, req, md5, $state, $rootScope, layerIfShow, $window,$document){
    var vm = this;
    //设置过期日期
    var expireDate = new Date();
    expireDate.setDate(expireDate.getDate() + 30);
    //页面初始化设置
    function initSetting(){
        vm.layerIfShow = layerIfShow;
        vm.selRoleName = 1;
        //从cookie获取用户角色信息
        var admin_roleInfo_='admin_roleInfo_'+window.localStorage.lang;
        vm.userRoleInfoStr = $cookies.get(admin_roleInfo_);
        //从cookie获取用户头像
        $rootScope.userPortrait = $cookies.get('admin_head_portrait');
        //把从cookie获取用户角色信息转为对象
        $rootScope.userRole = angular.fromJson(vm.userRoleInfoStr);
        
        var admin_roleInfo_0 = angular.fromJson($cookies.get('admin_roleInfo_0'));
        var admin_roleInfo_1 = angular.fromJson($cookies.get('admin_roleInfo_1'));
        if(admin_roleInfo_1.length == 0){
        	$("#enChange").hide();
        	window.localStorage.lang=0;
        }else if(admin_roleInfo_0 == 0){
        	$("#enChange").hide();
        	window.localStorage.lang=1;
        }else{
        }
        //默认的第一个角色id
        if($rootScope.userRole){
            vm.firstRoleId = $rootScope.userRole[0].id;
        }
        //从cookie里取得角色id
        if($rootScope.userRole && !$cookies.get('admin_roleName')){
            vm.adminRoleName = $rootScope.userRole[0].roleName;
        }else{
            vm.adminRoleName = $cookies.get('admin_roleName');
        }
        $cookies.put("admin_roleId",vm.firstRoleId, {expires: expireDate, path: '/'});
    }
    //初始化
    function initHead(){
        initSetting();
        getUserName();
        req_changeUserRole(function(){
            getAccessList();
            req_getWaitMsNum();
        });
        req_getSendMsg();
    }
    initHead();
    //获取站内信消息未读数量
    function req_getSendMsg(){
        req.post('station/show.do',{}).success(function(resp){
            if(resp.data && resp.data.length != 0){
                $rootScope.unreadMsgNum = resp.data[0].unRead;
            }else{
                $rootScope.unreadMsgNum = '';
            }
        });
    }
    //获取新建稿件数量
    function req_getWaitMsNum(){
        req.post('groupPicCtro/showNoReadPicGroupNum.do',{}).success(function(resp){
            if(resp.code == '211'){
                $rootScope.picGroupNum = resp.data.PicGroupNum;
            }else{
                console.log(resp.msg);
            }
        });
    }
    //切换第一个角色
    vm.firstShowRole = function(){
        vm.roleifShow = !vm.roleifShow;
        vm.adminRoleId = $cookies.get('admin_roleId');
    }

    //从cookie获取用户名
    function getUserName(){
        vm.uName = $cookies.get('admin_uname');
    }
    //跳转到客户端首页
    vm.jumpClientIndex = function(){
        location.href='/';
    }

    //选择角色
    vm.chooseRole = function(index,role){
        $rootScope.roleClickFlag = true;
        //每次重新选择角色时清除cookie里的角色ID
        $cookies.remove("admin_roleId");
        //每次重新选择角色时清除cookie里的角色名
        $cookies.remove("admin_roleName");
        //每次重新选择角色时清除本地存储里的用户权限
        $window.localStorage['userRoleRight'] = '';
        req_changeUserRole();
        vm.roleifShow = !vm.roleifShow;
        vm.variedRoleArr = $rootScope.userRole;
        $state.go('role.manager.managerIndex');
        //如果是订户就跳转到客户端
        if(role.id == 5){
            location.href='/';
        }
        vm.userRoleId = role.id;
        vm.roleName = role.roleName;
        //存储角色ID
        $cookies.put("admin_roleId",vm.userRoleId, {expires: expireDate, path: '/'});
        console.log($cookies.put("admin_roleId",vm.userRoleId, {expires: expireDate, path: '/'}));
        $cookies.put("admin_roleName", vm.roleName, {expires: expireDate, path: '/'});
        vm.variedRoleArr.unshift(vm.variedRoleArr[index]);
        vm.variedRoleArr.splice(index + 1,1);
        //存储角色信息（排除订户）
        if(vm.userRoleId != 5){
            $cookies.put("admin_roleInfo",angular.toJson(vm.variedRoleArr), {expires: expireDate, path: '/'});
        }
        vm.adminRoleId = $cookies.get('admin_roleId');
        vm.adminRoleName = $cookies.get('admin_roleName');
        req_changeUserRole(function(){
            getAccessList();
            req_getWaitMsNum();
        });
    }
    //绑定页面单击事件，关闭角色切换的div
    $document.bind("click", function(event) {
        if(event.target.className.indexOf('role-toggle-click') != -1){
            $rootScope.roleClickFlag = true;
        }else{
            $rootScope.roleClickFlag=false;
        }
        $scope.$apply();
    });
    //切换角色接口
    function req_changeUserRole(callback){
        req.post('userCtro/changeUserRole.do',{
            roleId: vm.userRoleId || vm.firstRoleId
        }).success(function(resp){
            if(resp.code == '211'){
               vm.userRoleRight = resp.data;
                //存储角色拥有的权限
               $window.localStorage['userRoleRight']=JSON.stringify(vm.userRoleRight);
               console.log(vm.userRoleRight);
                if(callback) callback();
            }else{
                console.log(resp.msg);
            }
        });
    }
    //获取权限列表
    function getAccessList(){
        //从cookie里取得roleId
        $rootScope.admin_roleId = $cookies.get('admin_roleId');
        console.log($rootScope.admin_roleId);
        //从localStorage里取得用户权限信息
        var accessList = JSON.parse($window.localStorage['userRoleRight'] || '{}');

        //存储权限对象
        $rootScope.accessObj = {
            sysM:[],
            contributeFun:[],
            manageFn:[],
            saleM:[],
            editorM:[],
            photographerM:[],
            dataStatistics:[]
        };
        /*各个权限的id分别为：
                 角色管理: 9, 权限管理:6, 在线用户:30 ,日志管理:34,系统配置:36,下载级别管理:41, 敏感词管理:46, 校审配置:50,
                 广告位管理:56, 订单管理:61, 需求管理:63, 消息管理:65, 水印管理:76, 图片价格管理:85, 网站信息:89
                 分类管理:103,新建稿件:110,草稿箱:113,我的稿件:121,待发稿件:123, 一审:131, 二审:136, 三审:141, 内部留资:146,
                 在线编辑:149,补签:153,购物车:195,用户管理:206,
                 专题管理:210,栏目管理:218,收藏夹:230,我的信息:239,资料库管理:245,检索词管理:256，基本信息：266,图片需求: 270，
                 摄影师：276，订户管理：277,投稿统计：313
        */


        //系统管理数组
        var sysManageArr = [206,30, 103,334,345,377, 9, 6, 50, 46 , 56, 63 , 36 ,76,41,34,65,89,61,85,210,'子库管理',256,399];
        //投稿功能数组
        var contributeFunArr = [110,113,121,'稿费统计','使用指南'];
        //管理功能数组
        var manageFunArr = [206,30, 103, 9, 6, 50, 46 , 56, 63 , 36 ,76,41,34,65,89,61,85,210,'子库管理',256];
        //销售管理数组
        var saleMArr = [206,30, 103, 9, 6, 50, 46 , 56, 63 , 36 ,76,41,34,65,89,61,85,210,'子库管理',256,
            277,'账单管理',266];
        //值班编辑拥有的管理权限
        var editorM = [123,245,146,276,210,'老照片',266,65];
        //摄影师拥有的管理权限
        var photographerM = [110,113,121,'稿费统计',266,'使用指南',65,270];
        //数据统计数组
        var dataStatisticsArr = [313,397,398,399,402,403,404,405,406,407,408,'图片下载统计','用户下载统计','签稿统计','稿酬统计','用户信息统计'];
        console.log(accessList);
        for(var i = 0; i < accessList.length; i++){
            //系统管理
            if((sysManageArr.indexOf(accessList[i].ID) != -1)){
                if(accessList[i].ID == 206){
                    accessList[i].sortId = 0;
                }
                if(accessList[i].ID == 30){
                    accessList[i].sortId = 1;
                }
                if(accessList[i].ID == 103){
                    accessList[i].sortId = 2;
                }
                if(accessList[i].ID == 9){
                    accessList[i].sortId = 3;
                }
                if(accessList[i].ID == 6){
                    accessList[i].sortId = 4;
                }
                if(accessList[i].ID == 50){
                    accessList[i].sortId = 5;
                }
                if(accessList[i].ID == 46){
                    accessList[i].sortId = 6;
                }
                if(accessList[i].ID == 56){
                    accessList[i].sortId = 7;
                }
                if(accessList[i].ID == 63){
                    accessList[i].sortId = 8;
                }
                if(accessList[i].ID == 36){
                    accessList[i].sortId = 9;
                }
                if(accessList[i].ID == 76){
                    accessList[i].sortId = 10;
                }
                if(accessList[i].ID == 41){
                    accessList[i].sortId = 11;
                }
                if(accessList[i].ID == 34){
                    accessList[i].sortId = 12;
                }
                if(accessList[i].ID == 65){
                    accessList[i].sortId = 13;
                }
                if(accessList[i].ID == 89){
                    accessList[i].sortId = 14;
                }
                if(accessList[i].ID == 61){
                    accessList[i].sortId = 15;
                }
                if(accessList[i].ID == 85){
                    accessList[i].sortId = 16;
                }
                if(accessList[i].ID == 210){
                    accessList[i].sortId = 17;
                }
                if(accessList[i].ID == '子库管理'){
                    accessList[i].sortId = 18;
                }
                if(accessList[i].ID == 334){
                    accessList[i].sortId = 19;
                }
                if(accessList[i].ID == 256){
                    accessList[i].sortId = 20;
                }
                if(accessList[i].ID == 345){
                    accessList[i].sortId = 21;
                }
                if(accessList[i].ID == 377){
                    accessList[i].sortId = 22;
                }
                if(accessList[i].ID == 399){
                    accessList[i].sortId = 23;
                }
                $rootScope.accessObj.sysM.push(accessList[i]);
            }
            //投稿功能
            if((contributeFunArr.indexOf(accessList[i].ID) != -1)){
                $rootScope.accessObj.contributeFun.push(accessList[i]);
            }
            //管理功能
            if((manageFunArr.indexOf(accessList[i].ID) != -1)){
                $rootScope.accessObj.manageFn.push(accessList[i]);
            }
            //销售管理
            if((saleMArr.indexOf(accessList[i].ID) != -1)){
                $rootScope.accessObj.saleM.push(accessList[i]);
            }
            //值班编辑拥有的管理权限
            if((editorM.indexOf(accessList[i].ID) != -1)){
                if(accessList[i].ID == 123){
                    accessList[i].sortId = 0;
                }
                if(accessList[i].ID == 245){
                    accessList[i].sortId = 1;
                }
                if(accessList[i].ID == 146){
                    accessList[i].sortId = 2;
                }
                if(accessList[i].ID == 276){
                    accessList[i].sortId = 3;
                }
                if(accessList[i].ID == 210){
                    accessList[i].sortId = 4;
                }
                if(accessList[i].ID == 266){
                    accessList[i].sortId = 6;
                }
                $rootScope.accessObj.editorM.push(accessList[i]);
            }
            //摄影师拥有的管理权限
            if((photographerM.indexOf(accessList[i].ID) != -1)){
                if(accessList[i].ID == 110){
                    accessList[i].sortId = 0;
                }
                if(accessList[i].ID == 113){
                    accessList[i].sortId = 1;
                }
                if(accessList[i].ID == 121){
                    accessList[i].sortId = 2;
                }
                if(accessList[i].ID == 266){
                    accessList[i].sortId = 4;
                }
                if(accessList[i].ID == 65){
                    accessList[i].sortId = 5;
                }
                if(accessList[i].ID == 270){
                    accessList[i].sortId = 6;
                }
                $rootScope.accessObj.photographerM.push(accessList[i]);
            }
            //数据统计
            if((dataStatisticsArr.indexOf(accessList[i].ID) != -1)){            	
                $rootScope.accessObj.dataStatistics.push(accessList[i]);
            }
        }
        console.log($rootScope.accessObj);
    }
    //重新登录前清除所有的cookie
    function removeAllCookies(){
        var cookies = $cookies.getAll();
        angular.forEach(cookies, function (v, k) {
            $cookies.remove(k);
        });
    }
    //用户退出请求
    function req_userExit(){
        req.post('login/doLogout.do',{}).success(function(resp){
            if(resp.code == '211'){
                $state.go('login');
                $rootScope.user_online = false;
                removeAllCookies();
            }else{
                console.log(resp.msg);
            }
        });
    }
    //用户退出
    vm.logOut = function(){
        req_userExit();
    };

    vm.switchLang = function (currentLang) {
        $translate.use(currentLang);
        window.localStorage.lang = currentLang;
        window.location.reload();
    };

    $scope.currentLang = $translate.use();
	$rootScope.currentLang = $scope.currentLang;
});