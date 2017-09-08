clientModule.controller('indexCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos) {
    var vm = this;
	
    $(function () {
        $timeout(function () {
            $('#slideBox').slide({
                //duration: 0.4,//滚动持续时间，单位：秒
                //easing: 'linear',//swing,linear//滚动特效
                //delay: 6,//滚动延迟时间，单位：秒
                //hideClickBar: false,//不自动隐藏点选按键
                //clickBarRadius: 3,
                //width: 305,
                //height: 549
				mainCell:".bd ul",effect:"fade",autoPlay:true,delayTime:1000,titCell:".btn li"
            });
        }, 1000);
    });
	
	/**导航切换效果*/
	$(function () {
        var dz=$('li.tab_li');
        var b;
        for (var p=0; p< dz.length; p++) {
            dz[p].max=p;
            dz[p].onmouseover=function  () {
                dz.removeClass("hover"); 
                $(this).addClass("hover");
                b=this.max;
            }
        }
    });
    $(document).ready(function(){
        $('li.tab_li').mousemove(function(){
            $(this).find('.rel_pad').show();
        });
        $('li.tab_li').mouseleave(function(){
            $(this).find('.rel_pad').hide();
        });
    });
	
	/**
	$(function () {
	jQuery(".slideBox").slide({titCell:".btn li",mainCell:".bd ul",autoPlay:true,trigger:"click",effect:"fold",delayTime:0});
	jQuery(".slideBox").slide({mainCell:".bd ul",effect:"fold",autoPlay:true,delayTime:1000});
	});
	*/

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
    vm.getClientGroups = function (signId,limit,size,picType) {
        req_getClientGroups(signId,limit,size,picType);
    };
    //获取首页签发的稿件请求
    function req_getClientGroups(signId,limit,picType,size) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: limit,
			picType: picType,
			size: size
        }).success(function (resp) {
            if (resp.code == '211') {
                //3头条
                if (signId == 3067) {
                    vm.toddayNews = jugeGroupPos.jugeGroupPos(3067,resp.data);
                }
				//新闻图片
				if (signId == 3064) {
                    vm.newsPhoto = jugeGroupPos.jugeGroupPos(3064,resp.data);
                }
				//皖风徽韵
				if (signId == 3078) {
                    vm.anhuiStyle = jugeGroupPos.jugeGroupPos(3078,resp.data);
                }
				//艺苑菁华artEssence
				if (signId == 3082) {
                    vm.artEssence = jugeGroupPos.jugeGroupPos(3082,resp.data);
                }
				//各市图库
				if (signId == 3080) {
                    vm.anhuiCitys = jugeGroupPos.jugeGroupPos(3080,resp.data);
					console.log(vm.anhuiCitys);
                }
				//历史资料
				if (signId == 3079) {
                    vm.anhuiHistory = jugeGroupPos.jugeGroupPos(3079,resp.data);
					console.log(vm.anhuiHistory);
                }
				//视频
				if (signId == 3081) {
                    vm.anhuiVideo = jugeGroupPos.jugeGroupPos(3081,resp.data);
					console.log(vm.anhuiVideo);
                }
				
				
				
                //4每日推荐==大美安徽
                if (signId == 3089) {
                    vm.beautifulAnhui = jugeGroupPos.jugeGroupPos(3089,resp.data);
                    
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