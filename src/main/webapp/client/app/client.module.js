var clientModule = angular.module('client.module',['ui.router','ngCookies','angular-md5','client.services','client.filter','client.directive','client.value','ui.bootstrap.datetimepicker','angularUtils.directives.dirPagination']);
clientModule.config(function($stateProvider,$urlRouterProvider,$locationProvider){
    $urlRouterProvider.otherwise('/');
    $stateProvider
        //弹框遮罩层
        .state('mask', {
            url:'/mask',
            templateUrl: 'client/app/shared/mask/mask.html',
            controller: 'maskCtrl as mask'

        })
        //总容器(包含header和footer部分)
        .state('root', {
            abstract: true,
            url:'',
            views:{
                'header':{
                    templateUrl: 'client/app/shared/header/header.html',
                    controller: 'headerCtrl as header'
                },
                'footer':{
                    templateUrl: 'client/app/shared/footer/footer.html',
                    controller: 'footerCtrl as footer'
                }
            }
        })
        //总容器(footer部分)
        .state('roots', {
            abstract: true,
            url:'',
            views:{
                'footer':{
                    templateUrl: 'client/app/shared/footer/footer.html',
                    controller: 'footerCtrl as footer'
                }
            }
        })        
        //index主页
        .state('root.index', {
            url:'/',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/index/index.html',
                    controller: 'indexCtrl as index'
                }
            }
        })
        //图片列表页
        .state('root.photolist', {
            url:'/photolist/:type?',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/photoList/photolist.html',
                    controller: 'photoListCtrl as photoList'
                }
            }
        })
        //更多分类列表页
        .state('root.moreSortList', {
            url:'/moreSortList/:cateId?/:sginId?',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/moreSortList/more_sort_list.html',
                    controller: 'moreSortListCtrl as moreSortList'
                }
            }
        })
        //详情列表页
        .state('root.detailList', {
            url:'/detailList',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/detail/detailList/pics_detail.html',
                    //controller: 'detailListCtrl as detailList'
                }
            }
        })
        //单张图片详情页
        .state('root.photoDetail', {
            url:'/photoDetail',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/detail/photoDetail/photo_detail.html'
                    //controller: 'photoListCtrl as photoList'
                }
            }
        })
        //新详情页列表
        .state('root.newDetailList', {
            url:'/newDetail/list/:groupId',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/newDetail/detailList/pics_detail.html',
                    controller: 'picsDetailCtrl as picsDetail'
                }
            }
        })
        //新单张图片详情页
        .state('root.newSingleDetail', {
            url:'/newDetail/single/:groupId?/:pictureId?/:keywords?',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/newDetail/singleDetail/single_detail.html',
                    controller: 'singleDetailCtrl as singleDetail'
                }
            }
        })
        //专题图片页
        .state('root.specPic', {
            url:'/specPic',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/specPic/specPic.html',
                    controller: 'specPicCtrl as specPic'
                }
            }
        })
        //老照片页
        .state('root.oldPic', {
            url:'/oldPic',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/oldPic/oldPic.html',
                    controller: 'oldPicCtrl as oldPic'
                }
            }
        })
        //院士图库
        .state('root.acadePic', {
            url:'/acadePic',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/acadePic/acadePic.html',
                    controller: 'acadePicCtrl as acadePic'
                }
            }
        })
        //人物图库
        .state('root.personPic', {
            url:'/personPic',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/personPic/personPic.html',
                    controller: 'personPicCtrl as personPic'
                }
            }
        })
        //具体行业人物图片列表
        .state('root.personList', {
            url:'/personList',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/personPic/personList/personList.html',
                    controller: 'personListCtrl as personList'
                }
            }
        })
        //图表漫画
        .state('root.chartList', {
            url:'/chartList',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/chartList/chart_list.html',
                    controller: 'chartListCtrl as chartList'
                }
            }
        })
        //我的信息
        .state('root.myInfo', {
            url:'/myInfo/:activeTab?',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/myInfo/myInfo.html',
                    controller: 'myInfoCtrl as myInfo'
                }
            }
        })
        //购物车
        .state('root.shoppingCart', {
            url:'/shoppingCart/list',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/shoppingCart/shoppingCart.html',
                    controller: 'shoppingCartCtrl as shoppingCart'
                }
            }
        })
        //我的订单-列表
        .state('root.myOrder', {
            url:'/myOrder',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/myOrder/my_order.html',
                    controller: 'myOrderCtrl as myOrder'
                }
            }
        })
        //我的订单-查看图片
        .state('root.lookPics', {
            url:'/lookPics/:orderId',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/myOrder/look_pics.html',
                    controller: 'lookPicsCtrl as lookPics'
                }
            }
        })
        //需求通道-列表
        .state('root.needChannel', {
            url:'/needChannel/list',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/needChannel/need_channel.html',
                    controller: 'needChannelCtrl as needChannel'
                }
            }
        })
        //发布图片需求
        .state('root.releaseNeed', {
            url:'/needChannel/releaseNeed',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/needChannel/release_need.html',
                    controller: 'releaseNeedCtrl as releaseNeed'
                }
            }
        })
        //提供图片
        .state('root.providePic', {
            url:'/needChannel/providePic/:id',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/needChannel/provide_pic.html',
                    controller: 'providePicCtrl as providePic'
                }
            }
        })
        //收藏夹
        .state('root.favorite', {
            url:'/favorite/list',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/favorite/favorite.html',
                    controller: 'favoriteCtrl as favorite'
                }
            }
        })
        //收藏夹详情
        .state('root.favoriteDetail', {
            url:'/favorite/detail/:id/:name',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/favorite/detail.html',
                    controller: 'favoriteDetailCtrl as detail'
                }
            }
        })
        //娱乐风尚
        .state('root.fashionPic', {
            url:'/fashionPic',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/fashionPic/fashionPic.html',
                    controller: 'fashionPicCtrl as fashionPic'
                }
            }
        })
        //财经频道
        .state('root.financePic', {
            url:'/financePic',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/financePic/financePic.html',
                    controller: 'financePicCtrl as financePic'
                }
            }
        })
        //网站信息(关于我们、版权声明、我要投稿)
        .state('root.webInfoView', {
            url:'/webInfoView/:id/:name:/:type',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/webInfoView/webInfoView.html',
                    controller: 'webInfoViewCtrl as webInfoview'
                }
            }
        })
        //网站公告(所有数据)
        .state('root.webInfoAllView', {
            url:'/webInfoAllView',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/webInfoView/webInfoAllView.html',
                    controller: 'webInfoAllViewCtrl as webInfoAllView'
                }
            }
        })
        //网站留言
        .state('root.siteMsg', {
            url:'/siteMsg',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/siteMsg/siteMsg.html',
                    controller: 'siteMsgCtrl as siteMsg'
                }
            }
        })
        //热点回放
        .state('root.topicPlayBack', {
            url:'/topicPlayBack',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/topicPlayback/topic_play_back.html',
                    controller: 'topicPlayBackCtrl as topicPlayBack'
                }
            }
        })
        //每月好稿
        .state('root.monthDraft', {
            url:'/monthDraft',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/monthDraft/month_draft.html',
                    controller: 'monthDraftCtrl as monthDraft'
                }
            }
        })
        //名家讲座
        .state('famousLecture', {
            url:'/famousLecture',
            views:{
                'specCon@':{
                    templateUrl: 'client/app/components/famousLecture/famous_lecture.html',
                    controller: 'famousLectureCtrl as famousLecture'
                }
            }
        })
        //登录
        .state('root.login', {
            url:'/login',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/login/login.html',
                    controller: 'loginCtrl as login'
                }
            }
        })
        //注册 - 用户协议
        .state('root.register', {
            url:'/register',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/register/register.html',
                    controller: 'registerCtrl as register'
                }
            }
        })
        //注册 - 注册步骤一
        .state('root.registerStepOne', {
            url:'/registerStepOne',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/register/register_step_one.html',
                    controller: 'registerStepOneCtrl as registerStepOne'
                }
            }
        })
        //注册 - 注册步骤二
        .state('root.registerStepTwo', {
            url:'/registerStepTwo/:userName/:applyCategory',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/register/register_step_two.html',
                    controller: 'registerStepTwoCtrl as registerStepTwo'
                }
            }
        })
        //注册完成页面
        .state('root.registerSuccess', {
            url:'/registerSuccess/:userName/:emailBind/:telBind',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/register/register_success.html',
                    controller: 'registerSuccessCtrl as registerSuccess'
                }
            }
        })
        //忘记密码1
        .state('root.forgetPassword', {
            url:'/forgetPassword',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/forgetPwd/forget_password.html',
                    controller: 'forgetPasswordCtrl as forgetPassword'
                }
            }
        })
        //忘记密码2
        .state('root.forgetPasswordStepOne', {
            url:'/forgetPasswordStepOne',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/forgetPwd/forget_password_step_one.html',
                    controller: 'forgetPasswordStepOneCtrl as forgetPasswordStepOne'
                }
            }
        })
        //忘记密码3
        .state('root.forgetPasswordStepTwo', {
            url:'/forgetPasswordStepTwo',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/forgetPwd/forget_password_step_two.html',
                    controller: 'forgetPasswordStepTwoCtrl as forgetPasswordStepTwo'
                }
            }
        })
        //忘记密码4
        .state('root.forgetPasswordSuccess', {
            url:'/forgetPasswordSuccess',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/forgetPwd/forget_password_success.html',
                    controller: 'forgetPasswordSuccessCtrl as forgetPasswordSuccess'
                }
            }
        })



        //忘记密码
        .state('forgetPwd', {
            url:'/forgetPwd',
            views:{
                'specCon@':{
                    templateUrl: 'client/app/components/forgetPwd/forget_pwd.html',
                    controller: 'forgetPwdCtrl as forgetPwd'
                }
            }
        })

        //当代名医
        .state('currentDoctor', {
            url:'/currentDoctor',
            views:{
                'specCon@':{
                    templateUrl: 'client/app/components/currentDoctor/forget_pwd.html',
                    controller: 'currentDoctorCtrl as currentDoctor'
                }
            }
        })
        //当代名医具体分类
        //searchAllName 搜索名
        .state('currentDoctorClassify', {
            url:'/currentDoctorClassify/:searchAllName',
            views:{
                'specCon@':{
                    templateUrl: 'client/app/components/currentDoctor/current_doctor_classify.html',
                    controller: 'currentDoctorClassifyCtrl as currentDoctorClassify'
                }
            }
        })
        //全文检索列表
        .state('root.fullText', {
            url:'/fullTextSearch/:searchAllName?/:randomNum?/:searchObj?',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/fullRetrieval/fulltext.html',
                    controller: 'fullTextCtrl as fullText'
                }
            }
        })
        //高级检索
        .state('root.advSearch', {
            url:'/advSearch',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/advSearch/adv_search.html',
                    controller: 'advSearchCtrl as advSearch'
                }
            }
        })
        //专题展示
        /*.state('root.special', {
            url:'/special/:id',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/special/special.html',
                    controller: 'specialCtrl as special'
                }
            }
        })*/
        //专题展示去掉头
        .state('roots.special', {
            url:'/special/:id',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/special/special.html',
                    controller: 'specialCtrl as special'
                }
            }
        })
        //专题更多稿件展示
        //id 栏目id
        .state('root.moreContribute', {
            url:'/moreContribute/:id',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/special/moreContribute.html',
                    controller: 'moreContributeCtrl as moreContribute'
                }
            }
        })
        //公告列表
        //id 专题id
        .state('root.ggList', {
            url:'/ggList/:id',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/notice/ggList.html',
                    controller: 'ggListCtrl as ggList'
                }
            }
        })
        //公告展示
        //公告id
        .state('root.ggDisplay', {
            url:'/ggDisplay/:id',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/notice/ggDisplay.html',
                    controller: 'ggDisplayCtrl as ggDisplay'
                }
            }
        })
        //帮助
        .state('root.help', {
            url:'/help',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/help/help.html',
                    //controller: 'helpCtrl as help'
                }
            }
        })
        //编辑提示
        .state('root.editTips', {
            url:'/editTips',
            views:{
                'main@':{
                    templateUrl: 'client/app/components/editTips/edit_tips.html',
                    //controller: 'editTipsCtrl as editTips'
                }
            }
        })








});
clientModule.run(function($rootScope, req, $cookies){
    $rootScope.layerIfShow = false;
    $rootScope.$on('$stateChangeSuccess',function(event, toState, toParams, fromState, fromParams){
        console.log('Enter state: ' + toState.name);
        //日期选择器设置为中文
        moment.locale('zh-cn');
        //重新获取用户名看用户是否处于登录状态
        if(!$cookies.get('client_uname')){
            //从cookie获取客户端用户名
            $rootScope.client_uName = $cookies.get('client_uname');
        }
    })
});
clientModule.factory('HttpInterceptor', ['$q', '$cookies', '$rootScope','$location', function ($q, $cookies, $rootScope, $location, $window) {
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
        console.log($rootScope.user_online);
        removeAllCookies();
        //重新获取用户名看用户是否处于登录状态
        if(!$cookies.get('client_uname')){
            //从cookie获取客户端用户名
            $rootScope.client_uName = $cookies.get('client_uname');
        }
        layer.alert('请登录', function (index) {
            $cookies.put('latest_hash', location.hash);
            layer.close(index);
            location.href = '/#/login';
            location.reload();
        });
    }
    return {
        request:       function (config) {
            return config;
        },
        requestError:  function (err) {
            return $q.reject(err);
        },
        response:      function (res) {
            //遇到掉线但接口可以返回响应的情况：判断msg内容
            if (res.status == 200 && res.config.url != '/cnsphoto/login/getOnLineUsers.do') {
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
clientModule.config(['$httpProvider', '$locationProvider', function ($httpProvider, $locationProvider) {
    $httpProvider.interceptors.push('HttpInterceptor');
}]);