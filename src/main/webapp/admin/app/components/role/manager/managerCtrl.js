/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('managerCtrl',function($scope, $cookies, req, md5, $state ,$rootScope){
        var vm = this;        
        //初始化
        function init(){
            //设置背景颜色
            $("body").css('background-color', '#F2F2F2');
            //从cookie里取得角色id
            vm.adminRoleId = $cookies.get('admin_roleId');
            //存储导航图片url
            vm.rightIconUrl = '';

            //存储导航选中的图片url
            vm.activeIconUrl = '';

            //默认切换左边导航的名字为空
            vm.toggleTreeName = '';

            allRestFlase();

            //初始化横向导航用到的相关图片地址
            vm.manageImgUrl = [
                {
                    waitMs: 'admin/assets/img/role/zxs_ld_but_picb_03.png',
                    activeWaitMs:'admin/assets/img/role/zxs_ld_but_picbn_03.png',
                    state:'role.manager.sendManuscript'
                },
                {
                    dataBank: 'admin/assets/img/role/zxs_ld_but_picb_05.png',
                    activeDataBank:'admin/assets/img/role/zxs_ld_but_picbn_05.png',
                    state:'role.manager.database'
                },
                {
                    InternalLeaveInfo: 'admin/assets/img/role/zxs_ld_but_picb_07.png',
                    activeInternalLeaveInfo:'admin/assets/img/role/zxs_ld_but_picbn_07.png',
                    state:'role.manager.internalRetention'
                },
                {
                    photographerManage: 'admin/assets/img/role/zxs_ld_but_picb_09.png',
                    activePhotographerManage:'admin/assets/img/role/zxs_ld_but_picbn_09.png',
                    state:'role.manager.userManage'
                },
                {
                    projectManage: 'admin/assets/img/role/zxs_ld_but_picb_11.png',
                    activeProjectManage:'admin/assets/img/role/zxs_ld_but_picbn_11.png',
                    state:'role.manager.projectManage'
                },
                {
                    basicInfo: 'admin/assets/img/role/zxs_ld_but_pica_15.png',
                    activeBasicInfo:'admin/assets/img/role/zxs_ld_but_pican_15.png',
                    state:'role.manager.basicInfo'
                },
                {
                    msgManage: 'admin/assets/img/role/zxs_ld_but_pica_15.png',
                    activeMsgManage:'admin/assets/img/role/zxs_ld_but_pican_15.png',
                    state:'role.manager.msgManage'
                },
                {
                    newMs: 'admin/assets/img/role/zxs_ld_but_pica_03.png',
                    ativeNewMs:'admin/assets/img/role/zxs_ld_but_pican_03.png',
                    state:'role.manager.newManuscript'
                },
                {
                    draft: 'admin/assets/img/role/zxs_ld_but_pica_05.png',
                    activeDraft:'admin/assets/img/role/zxs_ld_but_pican_05.png',
                    state:'role.manager.draftbox'
                },
                {
                    myMs: 'admin/assets/img/role/zxs_ld_but_pica_07.png',
                    activeMyMs:'admin/assets/img/role/zxs_ld_but_pican_07.png',
                    state:'role.manager.myManuscript'
                },
                {
                    imgDemand: 'admin/assets/img/role/zxs_ld_but_pica_07.png',
                    activeImgDemand:'admin/assets/img/role/zxs_ld_but_pican_07.png',
                    state:'role.manager.picDemandManage'
                }
            ];
        };
        init();

        //初始化时都为false
        function allRestFlase(){
            //切换系统管理falg
            vm.toggleSysMenuFlag = false;
            //切换管理功能falg
            vm.toggleManageMenuFlag = false;
            //切换销售管理falg
            vm.toggleSaleMenuFlag = false;
            //切换稿件功能falg
            vm.toggleMsMenuFlag = false;
            //切换数据统计falg
            vm.toggleDataMenuFlag = false;
        }

        //除了系统管理都重置为false
        function exceptSysReset(){
            //切换管理功能falg
            vm.toggleManageMenuFlag = false;
            //切换销售管理falg
            vm.toggleSaleMenuFlag = false;
            //切换稿件功能falg
            vm.toggleMsMenuFlag = false;
            //切换数据统计falg
            vm.toggleDataMenuFlag = false;
        }

        //除了管理功能都重置为false
        function exceptManageReset(){
            //切换系统管理falg
            vm.toggleSysMenuFlag = false;
            //切换销售管理falg
            vm.toggleSaleMenuFlag = false;
            //切换稿件功能falg
            vm.toggleMsMenuFlag = false;
            //切换数据统计falg
            vm.toggleDataMenuFlag = false;
        }

        //除了销售管理都重置为false
        function exceptSaleReset(){
            //切换系统管理falg
            vm.toggleSysMenuFlag = false;
            //切换管理功能falg
            vm.toggleManageMenuFlag = false;
            //切换稿件功能falg
            vm.toggleMsMenuFlag = false;
            //切换数据统计falg
            vm.toggleDataMenuFlag = false;
        }

        //除了投稿功能都重置为false
        function exceptMsReset(){
            //切换系统管理falg
            vm.toggleSysMenuFlag = false;
            //切换管理功能falg
            vm.toggleManageMenuFlag = false;
            //切换销售管理falg
            vm.toggleSaleMenuFlag = false;
            //切换数据统计falg
            vm.toggleDataMenuFlag = false;
        }

        //除了数据统计都重置为false
        function exceptDataReset(){
            //切换系统管理falg
            vm.toggleSysMenuFlag = false;
            //切换管理功能falg
            vm.toggleManageMenuFlag = false;
            //切换销售管理falg
            vm.toggleSaleMenuFlag = false;
            //切换稿件功能falg
            vm.toggleMsMenuFlag = false;
        }
        //收缩树形导航
        vm.shrinkTreeNav = function(toggleTreeName){
            vm.toggleTreeName = toggleTreeName;
            if(vm.toggleTreeName == '系统管理'){
                vm.toggleSysMenuFlag = !vm.toggleSysMenuFlag;
                exceptSysReset();
                for(var i=0;i<$('.m-item-option').length;i++){
                     	$('.m-item-option').eq(i).click(function(){
                    	  $(this).addClass('manager_active');
                    	  $(this).siblings().removeClass('manager_active');
                        }); 
                     }     
            }else if(vm.toggleTreeName == '管理功能'){
                vm.toggleManageMenuFlag = !vm.toggleManageMenuFlag;
                exceptManageReset();
            }else if(vm.toggleTreeName == '销售管理'){
                vm.toggleSaleMenuFlag = !vm.toggleSaleMenuFlag;
                exceptSaleReset();
            }else if(vm.toggleTreeName == '投稿功能'){
                vm.toggleMsMenuFlag = !vm.toggleMsMenuFlag;
                exceptMsReset();
                for(var i=0;i<$('.m-item-option').length;i++){
                     	$('.m-item-option').eq(i).click(function(){
                    	  $(this).addClass('manager_active');
                    	  $(this).siblings().removeClass('manager_active');
                        }); 
                     }     
            }else if(vm.toggleTreeName == '数据统计'){
                vm.toggleDataMenuFlag = !vm.toggleDataMenuFlag;
                exceptDataReset();
            }
        }

        //切换树形导航(点击根据权限名进行跳转)
        /*各个权限的id分别为：
         角色管理: 9, 权限管理:6, 在线用户:30 ,日志管理:34,系统配置:36,下载级别管理:41, 敏感词管理:46, 校审配置:50,
         广告位管理:56, 订单管理:61, 需求管理:63, 消息管理:65, 水印管理:76, 图片价格管理:85, 网站信息:89, 系统配置:97,
         分类管理:103,新建稿件:110,草稿箱:113,我的稿件:121,待发稿件:123, 一审:131, 二审:136, 三审:141, 内部留资:146,
         在线编辑:149,补签:153,购物车:195,用户管理:206,
         专题管理:210,栏目管理:218,收藏夹:230,我的信息:239,资料库管理:245,检索词管理:256，基本信息：266，图片需求: 270,
         摄影师：276，订户管理：277,投稿统计：313
         */
        vm.toggleTreeMenu = function(selRightId){
            switch(selRightId){
                case 402:
                    $state.go('role.manager.downloaddataStatistics',{rightId:402});
                    break;
                case 403:
                    $state.go('role.manager.downloaddataStatistics',{rightId:403});
                    break;
                case 404:
                    $state.go('role.manager.downloaddataStatistics',{rightId:404});
                    break;
                case 405:
                    $state.go('role.manager.downloaddataStatistics',{rightId:405});
                    break;
                case 406:
                    $state.go('role.manager.downloaddataStatistics',{rightId:406});
                    break;
                case 407:
                    $state.go('role.manager.downloaddataStatistics',{rightId:407});
                    break;
                case 408:
                    $state.go('role.manager.downloaddataStatistics',{rightId:408});
                    break;
            	case 377:
                    $state.go('role.manager.friendsLinkManage',{rightId:377});
                    break;
            	case 345:
                    $state.go('role.manager.groupManage',{rightId:345});
                    break;
                case 334:
                    $state.go('role.manager.columnManage',{rightId:334});
                    break;
                case 206:
                    $state.go('role.manager.userManage',{rightId:206});
                    break;
                case 276:
                    $state.go('role.manager.userManage',{rightId:276});
                    /*$state.go('role.manager.userManage',{rightId:276},{reload:true});*/
                    break;
                case 277:
                    $state.go('role.manager.userManage',{rightId:277});
                    break;
                case 103:
                    $state.go('role.manager.sortManage');
                    break;
                case 9:
                    $state.go('role.manager.roleManage');
                    break;
                case 6:
                    $state.go('role.manager.authorityManage');
                    break;
                case 50:
                    $state.go('role.manager.proofreadManage');
                    break;
                case 46:
                    $state.go('role.manager.sensitiveWordManage');
                    break;
                case 56:
                    $state.go('role.manager.advertisingManage');
                    break;
                case 63:
                    $state.go('role.manager.demandManage');
                    break;
                case 36:
                    $state.go('role.manager.sysManage');
                    break;
                case 76:
                    $state.go('role.manager.waterMarkManage');
                    break;
                case 41:
                    $state.go('role.manager.downLevelManage');
                    break;
                case 34:
                    $state.go('role.manager.logManage');
                    break;
                case 65:
                    $state.go('role.manager.msgManage');
                    break;
                case 89:
                    $state.go('role.manager.webInfoManage');
                    break;
                case 61:
                    $state.go('role.manager.orderManage');
                    break;
                case 85:
                    $state.go('role.manager.priceManage');
                    break;
                case 210:
                    $state.go('role.manager.projectManage');
                    break;
                case '子库管理':
                    $state.go('role.manager.subpoolManage');
                    break;
                case 256:
                    $state.go('role.manager.termManage');
                    break;
                case 110:
                    $state.go('role.manager.newManuscript');
                    break;
                case 113:
                    $state.go('role.manager.draftbox');
                    break;
                case 121:
                    $state.go('role.manager.myManuscript');
                    break;
                case '稿费统计':
                    $state.go('role.manager.payStatistics');
                    break;
                case '使用指南':
                    $state.go('role.manager.useGuide');
                    break;
                case 123:
                    $state.go('role.manager.sendManuscript');
                    break;
                case 146:
                    $state.go('role.manager.internalRetention');
                    break;
                case 245:
                    $state.go('role.manager.database');
                    break;
                case 266:
                    $state.go('role.manager.basicInfo');
                    break;
                case 270:
                    $state.go('role.manager.picDemandManage');
                    break;
                case 313:
                    $state.go('role.manager.contributeStatistics');
                    break;
                case 399:
                    $state.go('role.manager.sendManuscript');
                    break;
                case 30:
                    $state.go('role.manager.onlineUser');
                    break;
            }
        }




        //切换菜单导航背景
        vm.toggleMenuBg = function(state,rightIconUrl){
            console.log(state);
            if(state == 'role.manager.sendManuscript'){
                rightIconUrl = 'admin/assets/img/role/zxs_ld_but_picbn_03.png';
            }
        }


});