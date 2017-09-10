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
    }

    //初始化
    function init() {
        initSetting();
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
                $state.go('root.login');
                $rootScope.user_online = false;
                $cookies.remove('client_uname');
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