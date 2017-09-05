clientModule.controller('indexCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos) {
    var vm = this;
    $(function () {
        $timeout(function () {
            $('#index-banner').slideBox({
                duration: 0.4,//滚动持续时间，单位：秒
                easing: 'linear',//swing,linear//滚动特效
                delay: 6,//滚动延迟时间，单位：秒
                hideClickBar: false,//不自动隐藏点选按键
                clickBarRadius: 3,
                //width: 305,
                //height: 549
            });
        }, 500);
    });

    //过滤每日最佳采用
    $scope.dailyGroupFilter = function (item) {
        return item.POSITION == '2' || item.POSITION == '3';
    };

    //初始化页面相关配置
    function initSetting() {
    }

    //页面初始化
    function init() {
        initSetting();
        //req_getClientPicture();
        //getClientGroupPics();
    }

    init();

    //获取首页签发的稿件
    vm.getClientGroups = function (signId) {
        req_getClientGroups(signId);
    };
    //获取首页签发的稿件请求
    function req_getClientGroups(signId) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: 10
        }).success(function (resp) {
            if (resp.code == '211') {
                //3今日头条
                if (signId == 23) {
                    vm.toddayNews = jugeGroupPos.jugeGroupPos(23,resp.data);
                }
                //4每日推荐
                if (signId == 24) {
                    vm.dailyRecGroups = jugeGroupPos.jugeGroupPos(24,resp.data);
                    console.log(vm.dailyRecGroups);
                }
                //5一周最佳采用
                if (signId == 25) {
                    vm.bestUse = jugeGroupPos.jugeGroupPos(25,resp.data);
                }
                //6娱乐风尚
                if (signId == 112) {
                    vm.amusementFashion = jugeGroupPos.jugeGroupPos(112,resp.data);
                }
                //7财富经济
                if (signId == 113) {
                    vm.economicWealth =  jugeGroupPos.jugeGroupPos(113,resp.data);
                }
                //8台湾视角
                if (signId == 114) {
                    vm.taiwanAngle = jugeGroupPos.jugeGroupPos(114,resp.data);
                }
                //9国际风采
                if (signId == 115) {
                    vm.Intlpresence = jugeGroupPos.jugeGroupPos(115,resp.data);
                }
                //10限价图片
                if (signId == 116) {
                    vm.limitPictures = jugeGroupPos.jugeGroupPos(116,resp.data);
                }
                //11漫画图表
                if (signId == 118) {
                    vm.chartComic = jugeGroupPos.jugeGroupPos(118,resp.data);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 获取网站公告
     */
    function getWebPublishData() {
        req.post('notice/showToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.webPublishArray = resp.data;
                if (vm.webPublishArray.length > 2) {
                    vm.webPublishArray = vm.webPublishArray.slice(0, 2);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 获取广告位图片
     */
    function getAdShowImageData() {
        req.post('adver/adverToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                var adArray = resp.data;
                vm.adShowImageArray = [];
                var isEqualFlag = false;
                for (var i = 1; i < 14; i++) {
                    for (var j = 0; j < adArray.length; j++) {
                        if (adArray[j]['position'] == i) {
                            isEqualFlag = true;
                            insertIndexArrayFun(vm.adShowImageArray, i - 1, adArray[j]);
                            break;
                        } else {
                            isEqualFlag = false;
                        }
                    }
                    if (!isEqualFlag) {
                        insertIndexArrayFun(vm.adShowImageArray, i - 1, {file: ""});
                    }
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 向数组指定位置插入数据
     * @param array 原数组
     * @param index 指定位置
     * @param item  插入元素
     */
    function insertIndexArrayFun(array, index, item) {
        return array.splice(index, 0, item);
    }

    /**
     * 广告页或专题页的跳转
     * @param itemObj 广告位对象
     */
    vm.onToShowAdTopicClick = function (itemObj) {
        var imgFile = itemObj['file'];
        if (imgFile) {
            var topicId = itemObj['topicId'];
            var adUrl = itemObj['url'];
            var position = itemObj['position'];
            if(position==6){
                var topicPlayUrl = $state.href('root.topicPlayBack');
                window.open(topicPlayUrl,'_blank');
            }else{
                if (topicId) {
                    var topicJumpUrl = $state.href('root.special', {'id': topicId});
                    window.open(topicJumpUrl,'_blank');
                } else {
                    window.open(adUrl,'_blank');
                }
            }
        }
    };

    // 请求
    getWebPublishData();

    // 请求广告位图片
    getAdShowImageData();
});