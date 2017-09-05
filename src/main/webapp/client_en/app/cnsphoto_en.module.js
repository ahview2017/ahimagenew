var lang = "1";
window.localStorage.lang = lang;
var cnsphoto_enModule = angular.module('cnsphoto_en.module', ['ui.router', 'cnsphoto_en.services', 'cnsphoto_en.value', 'cnsphoto_en.directive', 'cnsphoto_en.filter', 'ngSanitize', 'angular-md5', 'ngCookies']);
cnsphoto_enModule.config(function($stateProvider, $urlRouterProvider, $locationProvider) {
	$urlRouterProvider.otherwise('/');
	$stateProvider

		//总容器
		.state('root', {
			abstract: true,
			url: '',
			views: {
				'header': {
					templateUrl: 'client_en/app/shared/header/header.html',
					controller: 'headerCtrl as header'
				},
				'footer': {
					templateUrl: 'client_en/app/shared/footer/footer.html',
					controller: 'footerCtrl as footer'
				}
			}
		})

		.state('root.mainPage', {
			// url: '/mainPage',
			url: '/',
			views: {
				'main@': {
					templateUrl: 'client_en/app/shared/pbody/mainPage.html',
					controller: 'mainPageCtrl as mainPage'
				}
			}
		})
		//错误页面提示
		.state('error', {
			url: '/error',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/error/error.html'
				}
			}
		})
		//login
		.state('root.login', {
			url: '/login',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/login/login.html',
					controller: 'loginCtrl as login'
				}
			}
		})
		//register
		.state('root.register', {
			url: '/register',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/register/register.html',
					controller: 'registerCtrl as register'
				}
			}
		})
		.state('root.registerStepOne', {
			url: '/registerStepOne',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/register/register_step_one.html',
					controller: 'registerStepOneCtrl as registerStepOne'
				}
			}
		})
		.state('root.registerStepTwo', {
			url: '/registerStepTwo/:userName/:applyCategory',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/register/register_step_two.html',
					controller: 'registerStepTwoCtrl as registerStepTwo'
				}
			}
		})
        .state('root.registerSuccess', {
            url:'/registerSuccess/:userName/:emailBind/:telBind',
            views:{
                'main@':{
                    templateUrl: 'client_en/app/components/register/register_success.html',
                    controller: 'registerSuccessCtrl as registerSuccess'
                }
            }
        })
		//forgetPwd
        .state('root.forgetPassword', {
            url:'/forgetPassword',
            views:{
                'main@':{
                    templateUrl: 'client_en/app/components/forgetPwd/forget_password.html',
                    controller: 'forgetPasswordCtrl as forgetPassword'
                }
            }
        })
        //忘记密码2
        .state('root.forgetPasswordStepOne', {
            url:'/forgetPasswordStepOne',
            views:{
                'main@':{
                    templateUrl: 'client_en/app/components/forgetPwd/forget_password_step_one.html',
                    controller: 'forgetPasswordStepOneCtrl as forgetPasswordStepOne'
                }
            }
        })
        //忘记密码3
        .state('root.forgetPasswordStepTwo', {
            url:'/forgetPasswordStepTwo',
            views:{
                'main@':{
                    templateUrl: 'client_en/app/components/forgetPwd/forget_password_step_two.html',
                    controller: 'forgetPasswordStepTwoCtrl as forgetPasswordStepTwo'
                }
            }
        })
        //忘记密码4
        .state('root.forgetPasswordSuccess', {
            url:'/forgetPasswordSuccess',
            views:{
                'main@':{
                    templateUrl: 'client_en/app/components/forgetPwd/forget_password_success.html',
                    controller: 'forgetPasswordSuccessCtrl as forgetPasswordSuccess'
                }
            }
        })
		.state('root.forgetPwd', {
			url: '/forgetPwd',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/forgetPwd/forgetPwd.html',
					controller: 'forgetPwdCtrl as forgetPwd'
				}
			}
		})
		//shoppingCart
		.state('root.shoppingCart', {
			url: '/shoppingCart/list',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/shoppingCart/shoppingCart.html',
					controller: 'shoppingCartCtrl as shoppingCart'
				}
			}
		})
		//我的订单-列表
		.state('root.myOrder', {
			url: '/myOrder',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/myOrder/my_order.html',
					controller: 'myOrderCtrl as myOrder'
				}
			}
		})
		//我的订单-查看图片
		.state('root.lookPics', {
			url: '/lookPics/:orderId',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/myOrder/look_pics.html',
					controller: 'lookPicsCtrl as lookPics'
				}
			}
		})
		//热点回放
        .state('root.topicPlayBack', {
            url:'/topicPlayBack',
            views:{
                'main@':{
                    templateUrl: 'client_en/app/components/topicPlayback/topic_play_back.html',
                    controller: 'topicPlayBackCtrl as topicPlayBack'
                }
            }
        })
		//全文检索列表
		.state('root.fullText', {
			url: '/fullTextSearch/:searchAllName?/:randomNum?/:searchObj?',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/fullTextSearch/fulltext.html',
					controller: 'fullTextCtrl as fullText'
				}
			}
		})
		//高级检索
		.state('root.advSearch', {
			url: '/advSearch',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/advSearch/adv_search.html',
					controller: 'advSearchCtrl as advSearch'
				}
			}
		})
		//网站公告更多
		.state('root.webInfoAllView', {
			url: '/webInfoAllView',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/webInfoView/webInfoAllView.html',
					controller: 'webInfoAllViewCtrl as webInfoAllView'
				}
			}
		})
		//网站信息(关于我们、版权声明、我要投稿)
        .state('root.webInfoView', {
            url:'/webInfoView/:id/:name:/:type',
            views:{
                'main@':{
                    templateUrl: 'client_en/app/components/webInfoView/webInfoView.html',
                    controller: 'webInfoViewCtrl as webInfoview'
                }
            }
        })
		//news
		.state('root.news', {
			url: '/column/:id',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/news/news.html',
					controller: 'newsCtrl as news'
				}
			}
		})
		.state('root.news_photos', {
			url: '/news_photos/:id',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/news/news_photos.html',
					controller: 'news_photosCtrl as news_photos'
				}
			}
		})
		//feature
		.state('root.feature', {
			url: '/feature',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/feature/feature.html',
					controller: 'featureCtrl as feature'
				}
			}
		})
		.state('root.feature_photos', {
			url: '/feature_photos',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/feature/feature_photos.html',
					controller: 'feature_photosCtrl as feature_photos'
				}
			}
		})
		//illustration
		.state('root.illustration', {
			url: '/illustration',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/illustration/illustration.html',
					controller: 'illustrationCtrl as illustration'
				}
			}
		})
		.state('root.illustration_photos', {
			url: '/illustration_photos',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/illustration/illustration_photos.html',
					controller: 'illustration_photosCtrl as illustration_photos'
				}
			}
		})
		//portrait
		.state('root.portrait', {
			url: '/portrait',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/portrait/portrait.html',
					controller: 'portraitCtrl as portrait'
				}
			}
		})
		.state('root.portrait_photos', {
			url: '/portrait_photos',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/portrait/portrait_photos.html',
					controller: 'portrait_photosCtrl as portrait_photos'
				}
			}
		})
		//history
		.state('root.history', {
			url: '/history',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/history/history.html',
					controller: 'historyCtrl as history'
				}
			}
		})
		.state('root.history_photos', {
			url: '/history_photos',
			views: {
				'main@': {
					templateUrl: 'client_en/app/components/history/history_photos.html',
					controller: 'history_photosCtrl as history_photos'
				}
			}
		})
});
cnsphoto_enModule.run(function($rootScope, $state) {
	/*$rootScope.layerIfShow = false;*/
	$rootScope.$on('$stateChangeSuccess', function(event, toState, toParams, fromState, fromParams) {
		console.log('Enter state: ' + toState.name);
		/*console.log(toState.permission);*/
		//当前路由状态名
		$rootScope.currentStateName = toState.name;

		// 判断权限跳转
		/* var permission = toState.permission;*/
		// if(undefined == permission || 'login' != toState.name || angular.isString(permission)
		//   || permissions.hasPermission(permission)) {
		// $state.go('errorPage');
		//}

		//日期选择器设置为中文
		//moment.local('zh-cn');
	})
});

cnsphoto_enModule.config(['$httpProvider', config]);

function config($httpProvider) {
	$httpProvider.defaults.withCredentials = true;
}