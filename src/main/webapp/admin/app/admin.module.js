var lang = "0";
var adminModule = angular.module('admin.module',['ui.router','ngCookies','angular-md5','admin.services','admin.filter','angularjs-dropdown-multiselect','admin.directive','admin.value','ui.bootstrap.datetimepicker','ngFileUpload','LocalStorageModule','ui.sortable','angularUtils.directives.dirPagination','pascalprecht.translate']);
adminModule.config(function($stateProvider,$urlRouterProvider,$locationProvider){
    $urlRouterProvider.otherwise('/manager/managerIndex');
    //$urlRouterProvider.otherwise('/login');
    $stateProvider
        //登录
        .state('login', {
            url:'/login',
            views:{
                'login':{
                    templateUrl: 'admin/app/components/login/login/login.html',
                    controller: 'loginCtrl as login'
                }
            }
            /*templateUrl: 'admin/app/components/login/login/login.html',
            controller: 'loginCtrl as login'*/
        })
        //注册
        /*.state('register', {
            url:'/register',
            templateUrl: 'admin/app/components/login/register/register.html'
        })*/
        //弹框遮罩层
        .state('mask', {
            url:'/mask',
            views:{
                'login': {
                    templateUrl: 'admin/app/shared/mask/mask.html',
                    controller: 'maskCtrl as mask'
                }
            }
        })
        //角色总容器
        .state('role', {
            abstract: true,
            url:'',
            views:{
                'header':{
                    templateUrl: 'admin/app/shared/header/header.html',
                    controller: 'headCtrl as header'
                }
            }

        })
        //错误页面提示
		.state('error', {
			url: '/error',
			views: {
				'manager@': {
					templateUrl: 'admin/app/components/error/error.html'
				}
			}
		})


        /**************************************** 管理员 ****************************************/
        //管理员主容器
        .state('role.manager', {
            abstract: true,
            url:'/manager',
            views:{
                'manager@':{
                    templateUrl: 'admin/app/components/role/manager/manager.html',
                    controller: 'managerCtrl as manager'
                }
            }
        })
        
        //管理员主页
        .state('role.manager.managerIndex', {
            url:'/managerIndex',
            templateUrl: 'admin/app/components/role/manager/manager_index.html',
            controller: 'managerIndexCtrl as managerIndex'
        })
        //管理员-用户管理
        .state('role.manager.userManage', {
            url:'/userManage/:rightId?',
            templateUrl: 'admin/app/components/role/manager/systemManage/userManage/user_manage.html',
            controller: 'userManageCtrl as userManage'
        })
        //管理员-下载管理
        .state('role.manager.downLoadManage', {
            url:'/downLoadManage/:selectType?',
            templateUrl: 'admin/app/components/role/manager/systemManage/downLoadManage/downLoad_manage.html',
            controller: 'downLoadManageCtrl as downLoadManage'
        })
         //管理员-群組管理
        .state('role.manager.groupManage', {
            url:'/groupManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/groupManage/group_manage.html',
            controller: 'groupManageCtrl as groupManage'
        })
        .state('role.manager.groupManageDetail', {
            url:'/groupManageDetail/:groupId',
            templateUrl: 'admin/app/components/role/manager/systemManage/groupManage/group_manage_detail.html',
            controller: 'groupManageDetailCtrl as groupManageDetail'
        })
        //管理员-在线用户
        .state('role.manager.onlineUser', {
            url:'/onlineUser',
            templateUrl: 'admin/app/components/role/manager/systemManage/onelineUser/online_user.html',
            controller: 'onlineUserCtrl as onlineUser'
        })
        //管理员-分类管理
        .state('role.manager.sortManage', {
            url:'/sortManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/sortManage/sort_manage.html',
            controller: 'sortManageCtrl as sortManage'
        })
        //管理员-角色管理
        .state('role.manager.roleManage', {
            url:'/roleManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/roleManage/role_manage.html',
            controller: 'roleManageCtrl as roleManage'
        })
        //管理员-权限管理
        .state('role.manager.authorityManage', {
            url:'/authorityManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/authorityManage/authority_manage.html',
            controller: 'authorityManageCtrl as authorityManage'
        })
        //管理员-检索词管理
        .state('role.manager.termManage', {
            url:'/termManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/termManage/term_manage.html',
            controller: 'termManageCtrl as termManage'
        })
        //管理员-敏感词管理
        .state('role.manager.sensitiveWordManage', {
            url:'/sensitiveWordManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/sensitiveWordManage/sensitive_word_manage.html',
            controller: 'sensitiveWordManageCtrl as sensitiveWordManage'
        })
        //管理员-广告位管理
        .state('role.manager.advertisingManage', {
            url:'/advertisingManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/advertisingManage/advertising_manage.html',
            controller: 'advertisingManageCtrl as advertisingManage'
        })
        //管理员-专题位管理
        .state('role.manager.projectPosManage', {
            url:'/projectPosManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectPosManage/project_position_manage.html',
            controller: 'projectPosManageCtrl as projectPosManage'
        })
        //管理员-需求管理
        .state('role.manager.demandManage', {
            url:'/demandManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/demandManage/demand_manage.html',
            controller: 'demandManageCtrl as demandManage'
        })
        //管理员-图片需求
        .state('role.manager.picDemandManage', {
            url:'/picDemandManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/picDemandManage/pic_demand_manage.html',
            controller: 'picDemandManageCtrl as demandManage'
        })
        //管理员-系统配置
        .state('role.manager.sysManage', {
            url:'/sysManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/sysManage/system_manage.html',
            controller: 'sysManageCtrl as sysManage'
        })
        //管理员-校审配置
        .state('role.manager.proofreadManage', {
            url:'/proofreadManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/proofreadManage/proofread_manage.html',
            controller: 'proofreadManageCtrl as proofreadManage'
        })
        //管理员-下载级别管理
        .state('role.manager.downLevelManage', {
            url:'/downLevelManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/downLevelManage/down_level_manage.html',
            controller: 'downLevelManageCtrl as downLevelManage'
        })
        //管理员-日志管理
        .state('role.manager.logManage', {
            url:'/logManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/logManage/log_manage.html',
            controller: 'logManageCtrl as logManage'
        })
        //管理员-消息管理
        .state('role.manager.msgManage', {
            url:'/msgManage/:msgType?',
            templateUrl: 'admin/app/components/role/manager/systemManage/msgManage/msg_manage.html',
            controller: 'msgManageCtrl as msgManage'
        })
        //管理员-网站信息管理
        .state('role.manager.webInfoManage', {
            url:'/webInfoManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/webInfoManage/web_info_manage.html',
            controller: 'webInfoManageCtrl as webInfoManage'
        })
         //管理员-友情链接管理
        .state('role.manager.friendsLinkManage', {
            url:'/friendsLinkManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/friendsLinkManage/friendsLink_manage.html',
            controller: 'friendsLinkManageCtrl as friendsLinkManage'
        })
        //管理员-订单管理
        .state('role.manager.orderManage', {
            url:'/orderManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/orderManage/order_manage.html',
            controller: 'orderManageCtrl as orderManage'
        })
        //管理员-价格管理
        .state('role.manager.priceManage', {
            url:'/priceManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/priceManage/price_manage.html',
            controller: 'priceManageCtrl as priceManage'
        })
        //管理员-专题管理
        .state('role.manager.projectManage', {
            url:'/projectManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage.html',
            controller: 'projectManageCtrl as projectManage'
        })
         //管理员-栏目管理
        .state('role.manager.columnManage', {
            url:'/columnManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/columnManage/column_manage.html',
            controller: 'columnManageCtrl as columnManage'
        })
        //管理员-专题管理详情-栏目管理
        .state('role.manager.projectManageDetail', {
            url:'/projectManageDetail/:id/:name',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_detail.html',
            controller: 'projectManageDetailCtrl as projectManageDetail'
        })
        //管理员-专题管理新详情-栏目管理
        .state('role.manager.projectManageNewDetail', {
            url:'/projectManageNewDetail/:id?/:name?/',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_new_detail.html',
            controller: 'projectManageNewDetailCtrl as projectManageDetail'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-子栏目
        // id 栏目id specialId 专题id
        .state('role.manager.projectManageEdit0', {
            url:'/projectManageEdit0/:id/:specialId/',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_0.html',
            controller: 'projectManageEditCtrl0 as projectManageEdit0'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-一图一文
        .state('role.manager.projectManageEdit1', {
            url:'/projectManageEdit1/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_1.html',
            controller: 'projectManageEditCtrl1 as projectManageEdit1'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-轮播图一
        .state('role.manager.projectManageEdit2', {
            url:'/projectManageEdit2/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_2.html',
            controller: 'projectManageEditCtrl2 as projectManageEdit2'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-轮播图二
        .state('role.manager.projectManageEdit3', {
            url:'/projectManageEdit3/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_3.html',
            controller: 'projectManageEditCtrl3 as projectManageEdit3'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-四展
        .state('role.manager.projectManageEdit4', {
            url:'/projectManageEdit4/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_4.html',
            controller: 'projectManageEditCtrl4 as projectManageEdit4'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-五展
        .state('role.manager.projectManageEdit5', {
            url:'/projectManageEdit5/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_5.html',
            controller: 'projectManageEditCtrl5 as projectManageEdit5'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-六展
        .state('role.manager.projectManageEdit6', {
            url:'/projectManageEdit6/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_6.html',
            controller: 'projectManageEditCtrl6 as projectManageEdit6'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-八展
        .state('role.manager.projectManageEdit8', {
            url:'/projectManageEdit8/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_8.html',
            controller: 'projectManageEditCtrl8 as projectManageEdit8'
        })
        //管理员-专题管理详情-栏目管理-栏目编辑-十展
        .state('role.manager.projectManageEdit10', {
            url:'/projectManageEdit10/:id/:specialId',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_edit_10.html',
            controller: 'projectManageEditCtrl10 as projectManageEdit10'
        })
        /**管理员-专题管理详情-栏目预览
         * id 栏目id
         * topicId 专题id
         * templateId 模板id
         * */
        .state('role.manager.projectManageView', {
            url:'/projectManageView/:id/:topicId/:templateId?',
            templateUrl: 'admin/app/components/role/manager/systemManage/projectManage/project_manage_view.html',
            controller: 'projectManageViewCtrl as projectManageView'
        })
        //管理员-模板管理
        .state('role.manager.templateManage', {
            url:'/templateManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/templateManage/template_manage.html',
            controller: 'templateManageCtrl as templateManage'
        })
        //管理员-子库管理
        .state('role.manager.subpoolManage', {
            url:'/subpoolManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/subpoolManage/subpool_manage.html',
            controller: 'subpoolManageCtrl as subpoolManage'
        })

        //管理员-投稿统计
        .state('role.manager.contributeStatistics', {
            url:'/contributeStatistics',
            templateUrl: 'admin/app/components/role/manager/dataStatistics/contributeStatistics/contributeStatistics.html',
            controller: 'contributeStatisticsCtrl as contributeStatistics'
        })
        //管理员-下载统计，今日下载---内部下载
        .state('role.manager.downloaddataStatistics', {
            url:'/downloaddataStatistics/:rightId',
            templateUrl: 'admin/app/components/role/manager/dataStatistics/downloaddataStatistics/downloaddataStatistics.html',
            controller: 'downloaddataStatisticsCtrl as downloaddataStatistics'
        })
        //管理员-下载统计
        .state('role.manager.downloadStatistics', {
            url:'/downloadStatistics',
            templateUrl: 'admin/app/components/role/manager/dataStatistics/downloadStatistics/downloadStatistics.html',
            controller: 'downloadStatisticsCtrl as downloadStatistics'
        })
        //管理员-签稿统计
        .state('role.manager.signDraftStatistics', {
            url:'/signDraftStatistics',
            templateUrl: 'admin/app/components/role/manager/dataStatistics/signDraftStatistics/signDraftStatistics.html',
            controller: 'signDraftStatisticsCtrl as signDraftStatistics'
        })
        //管理员-稿酬统计
        .state('role.manager.remunerationStatistics', {
            url:'/remunerationStatistics',
            templateUrl: 'admin/app/components/role/manager/dataStatistics/remunerationStatistics/remunerationStatistics.html',
            controller: 'remunerationStatisticsCtrl as remunerationStatistics'
        })
        //管理员-用户信息统计
        .state('role.manager.userinfoStatistics', {
            url:'/userinfoStatistics',
            templateUrl: 'admin/app/components/role/manager/dataStatistics/userinfoStatistics/userinfoStatistics.html',
            controller: 'userinfoStatisticsCtrl as userinfoStatistics'
        })

        //管理员-新建稿件
        .state('role.manager.newManuscript', {
            url:'/newManuscript',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/newManuscript/new_manuscript.html',
            controller: 'newManuscriptCtrl as newManuscript'

        })
        //管理员-草稿箱
        .state('role.manager.draftbox', {
            url:'/draftbox',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/draftBox/draft_box.html',
            controller: 'mDraftboxCtrl as draftbox'
        })
        //管理员-草稿箱-详情
        .state('role.manager.draftDetail', {
            url:'/draftDetail/:id',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/draftBox/draft_detail.html',
            controller: 'mDraftDetailCtrl as draftDetail'
        })
        //管理员-草稿箱-编辑
        .state('role.manager.draftEdit', {
            url:'/draftEdit/:id',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/draftBox/draft_edit.html',
            controller: 'mDraftEditCtrl as draftEdit'
        })
        //管理员-我的稿件
        .state('role.manager.myManuscript', {
            url:'/myManuscript/:gType?',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/myManuscript/my_manuscript.html',
            controller: 'myManuscriptCtrl as myManuscript'
        })
        //管理员-我的稿件详情
        .state('role.manager.myManuscriptDetail', {
            url:'/myManuscriptDetail/:id?/:gType?',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/myManuscript/myManuscript_detail.html',
            controller: 'myManuscriptDetailCtrl as detail'
        })
        //管理员-我的稿件编辑
        .state('role.manager.myManuscriptEdit', {
            url:'/myManuscriptEdit/:id/:gType?',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/myManuscript/my_manuscript_edit.html',
            controller: 'myManuscriptEditCtrl as edit'
        })
        //管理员-稿费统计
        .state('role.manager.payStatistics', {
            url:'/payStatistics',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/payStatistics/pay_statistics.html',
            controller: 'payStatisticsCtrl as payStatistics'
        })
        //管理员-使用指南
        .state('role.manager.useGuide', {
            url:'/useGuide',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/useGuide/use_guide.html',
            controller: 'useGuideCtrl as useGuide'
        })
        //管理员-水印管理
        .state('role.manager.waterMarkManage', {
            url:'/waterMarkManage',
            templateUrl: 'admin/app/components/role/manager/systemManage/waterMarkManage/water_mark_manage.html',
            controller: 'waterMarkManageCtrl as waterMarkManage'
        })
        //管理员-待发稿件
        .state('role.manager.sendManuscript', {
            url:'/sendManuscript',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/sendManuscript/sendManuscript.html',
            controller: 'mSendManuscriptCtrl as sendManuscript'
        })
        //管理员-待发稿件-详情（新闻、专题图片）
        .state('role.manager.sendManuscriptDetail', {
            url:'/sendManuscriptDetail/:id/:dtType?',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/sendManuscript/sendManuscript_detail.html',
            controller: 'mManuscriptDetailCtrl as detail'
        })
        //管理员-待发稿件-编辑
        .state('role.manager.sendManuscriptEdit', {
            url:'/sendManuscriptEdit/:id?/:dtType?/:type?',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/sendManuscript/sendManuscript_edit.html',
            controller: 'mSendManuscriptEditCtrl as edit'
        })
        //管理员-历史版本
        .state('role.manager.historyVersion', {
            url:'/historyVersion/:groupId/:dtType?/:gType?',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/sendManuscript/history_version.html',
            controller: 'mHistoryVersionCtrl as history'
        })
        //管理员-我的稿件历史版本
        .state('role.manager.myHistoryVersion', {
            url:'/historyVersion/:groupId/:gType?',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/myManuscript/history_version.html',
            controller: 'myHistoryVersionCtrl as history'
        })
        //管理员-历史版本-详情（新闻、专题图片）
        .state('role.manager.historyVersionDetail', {
            url:'/historyVersionDetail/:groupId/:id/:dtType?',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/sendManuscript/history_version_detail.html',
            controller: 'mHistoryVersionDetailCtrl as historyDetail'
        })
        //管理员-我的稿件历史版本详情
        .state('role.manager.myHistoryVersionDetail', {
            url:'/historyVersion/:groupId/:id/:gType?',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/myManuscript/history_version_detail.html',
            controller: 'myHistoryVersionDetail as historyDetail'
        })
        //管理员-操作记录
        .state('role.manager.operateRecord', {
            url:'/operateRecord/:groupId/:dtType?/:gType?',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/sendManuscript/operate_record.html',
            controller: 'mOperateRecordCtrl as record'
        })
        //管理员-我的稿件操作记录
        .state('role.manager.myOperateRecord', {
            url:'/myOperateRecord/:groupId/:gType?',
            templateUrl: 'admin/app/components/role/manager/contributeFunction/myManuscript/operate_record.html',
            controller: 'myOperateRecordCtrl as record'
        })

        //管理员-内部留资
        .state('role.manager.internalRetention', {
            url:'/internalRetention',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/internalRetention/internalRetention.html',
            controller: 'mInternalRetentionCtrl as internalRetention'
        })
        //管理员-内部留资-详情
        .state('role.manager.internalRetentionDetail', {
            url:'/internalRetentionDetail/:id/:dtType?',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/internalRetention/internalRetention_detail.html',
            controller: 'mInternalRetentionDetailCtrl as detail'
        })

        //管理员-老照片
        .state('role.manager.oldPhoto', {
            url:'/oldPhoto',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/oldPhoto/oldPhoto.html',
            controller: 'oldPhotoCtrl as oldPhoto'
        })

        //管理员-待发布图片
        .state('role.manager.toPublishPic', {
            url:'/toPublishPic/:id',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/oldPhoto/toPublishPic.html',
            controller: 'toPublishPicCtrl as toPublishPic'
        })

       //管理员-资料库
        /**
         * 栏目编辑选择稿件
         * topicId 专题id
         * lanmuId 栏目id //返回需要
         * columnId 子栏目/栏目id
         * columnTemplate 模板id
         * num 稿件位置
         * oldId 原稿件id ，在更多稿件替换的时候需要
         * */
        .state('role.manager.database', {
            url:'/database/:columnId?/:columnTemplate?/:num?/:oldId?/:topicId?/:lanmuId?/',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/database/database.html',
            controller: 'mDatabaseCtrl as database'
        })

       //管理员-资料库--详情
        /**
         * 栏目编辑选择稿件
         * topicId 专题id
         * lanmuId 栏目id //返回需要
         * columnId 子栏目/栏目id
         * columnTemplate 模板id
         * num 稿件位置
         * oldId 原稿件id ，在更多稿件替换的时候需要
         * */
        .state('role.manager.databaseDetail', {
            url:'/databaseDetail/:id/:columnId?/:columnTemplate?/:num?/:oldId?/:topicId?/:lanmuId?/:isHadOut?',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/database/database_detail.html',
            controller: 'mDatabaseDetailCtrl as detail'
        })


        //管理员-资料库-编辑
        .state('role.manager.databaseEdit', {
            url:'/databaseEdit/:id',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/database/database_edit.html',
            controller: 'mDatabaseEditCtrl as edit'
        })


        //管理员-基本信息
        .state('role.manager.basicInfo', {
            url:'/basicInfo',
            templateUrl: 'admin/app/components/role/manager/editorTopBtn/basicInfo/basicInfo.html',
            controller: 'mEdtBasicInfoCtrl as basicInfo'
        })

});
adminModule.run(function($rootScope, req, $window, $state, $cookies){
    //滚动条回到顶部
    function scrollReturnTop(){
         document.body.scrollTop = document.documentElement.scrollTop = 0;
    }
    $rootScope.layerIfShow = false;
    $rootScope.$on('$stateChangeSuccess',function(event,toState,toParams,fromState,fromParams){

        scrollReturnTop();
        console.log('Enter state: ' + toState.name);
        //当前路由状态名
        $rootScope.currentStateName = toState.name;
        //判断是否清除校审级别的本地存储（个别页面保留不清楚，如待发稿件，待发稿件详情，待发稿件编辑页等）
        function judgeIfClearDutyStorage(){
            var noNeddDutyLeveArr = ['role.manager.sendManuscript','role.manager.sendManuscriptDetail','role.manager.sendManuscriptEdit','role.manager.historyVersion','role.manager.historyVersionDetail','role.manager.operateRecord'];
            if(noNeddDutyLeveArr.indexOf($rootScope.currentStateName) == -1){
                $window.localStorage['userDutyLevelEn'] = '';
		$window.localStorage['userDutyLevelZh'] = '';
	    }
	}
	//资料库中是否显示选择稿件(用columnFlag判断的)
        function ifRemoveColumnFlag(){
            if(($rootScope.currentStateName != 'role.manager.database') && ($rootScope.currentStateName != 'role.manager.databaseDetail')){
                $cookies.remove('columnFlag');
            }
        }
        
        ifRemoveColumnFlag();
        judgeIfClearDutyStorage();
        //日期选择器设置为中文
        moment.locale('zh-cn');
    })
});
adminModule.factory('HttpInterceptor', ['$q', '$cookies', '$rootScope','$location', function ($q, $cookies, $rootScope, $location, $window) {
    //重新登录前清除所有的cookie
    function removeAllCookies(){
        var cookies = $cookies.getAll();
        angular.forEach(cookies, function (v, k) {
            $cookies.remove(k);
        });
    }
    //未登录弹框提示并跳转到登录页面
    function handleLogOut() {
        $rootScope.user_online = false;
        removeAllCookies();
        layer.alert('请登录', function (index) {
            $cookies.put('latest_hash', location.hash);
            layer.close(index);
            location.href = '/cnsphoto/admin.html#/login';
            //location.reload();
        });
    }

    //处理ajax未登录状态
    function handleJqAjax(){
        //全局的ajax访问，处理ajax清求时异常
        $.ajaxSetup({
            contentType:"application/x-www-form-urlencoded;charset=utf-8",
            complete:function(XMLHttpRequest,textStatus){
                if(XMLHttpRequest.status == '200'){
                    //通过XMLHttpRequest取得响应结果
                    var res = XMLHttpRequest.responseText;
                    var jsonData = JSON.parse(res);
                    if(jsonData.code == '520'){
                        handleLogOut();
                    }
                }
            }
        });
    };
    handleJqAjax();

    return {
        request:       function (config) {
            return config;
        },
        requestError:  function (err) {
            return $q.reject(err);
        },
        response:      function (res) {
            //遇到掉线但接口可以返回响应的情况：判断msg内容
            if (res.status == 200) {
                if (res.data.msg == '未登录') {
                    handleLogOut();
                }
            }
            return res;
        },
        responseError: function (err) {
            console.warn(err);
            if (-1 === err.status) {
                // 远程服务器无响应
            } else if (500 === err.status) {
                // 处理各类自定义错误
            } else if (501 === err.status) {
                // ...
            } else if (404 === err.status) {
                //handleLogOut();
            }
            return $q.reject(err);
        }
    };
}]);
//添加拦截器
adminModule.config(['$httpProvider', '$locationProvider', function ($httpProvider, $locationProvider) {
    $httpProvider.interceptors.push('HttpInterceptor');
    $httpProvider.defaults.withCredentials = true;
}]);

adminModule.config(['$translateProvider',function($translateProvider){
    lang = window.localStorage.lang || '0';
    window.localStorage.lang = lang;
    $translateProvider.preferredLanguage(lang);
    $translateProvider.useStaticFilesLoader({
        prefix: 'admin/assets/i18n/',
        suffix: '.json'
    })
}]);