clientModule.controller('specPhotoCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos) {
    var vm = this;
	
    $(function () {
        $timeout(function () {
            $('#news_slideBox').slide({
                //duration: 0.4,//滚动持续时间，单位：秒
                //easing: 'linear',//swing,linear//滚动特效
                //delay: 6,//滚动延迟时间，单位：秒
                //hideClickBar: false,//不自动隐藏点选按键
                //clickBarRadius: 3,
                //width: 305,
                //height: 549
				mainCell:".news_bd ul",effect:"leftLoop",autoPlay:true,delayTime:500,titCell:".news_btn li",events:"click"
            });
        }, 1000);
    });

    $(function () {
        $timeout(function () { 
            $('#ericnews_box_box').slide({
                mainCell:".ericnews_box ul",
                effect:"leftMarquee",
                autoPlay:true,
                vis:6,
                interTime:50
            });
        }, 1000);
    });
	
	/**导航切换效果*/
	/*$(function () {
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
	//头部一级导航菜单效果
    $(document).ready(function(){
        $('li.tab_li').mousemove(function(){
            $(this).find('.rel_pad').show();
            $('.marquee').hide();
        });
        $('li.tab_li').mouseleave(function(){
            $(this).find('.rel_pad').hide();
            $('.marquee').show();
        });
    });*/
	
    /**登录、注册模块js 20170910 
            function register(){
                $("#gray").css("display",'block');
                $("#register_div").css("display",'block');
            }
            function login(){
                $("#gray").css("display",'block');
                $("#login_div").css("display",'block');
            }

            function go_close(){
                $("#gray").hide();
                $(".login_box").hide();
                $(".register_box").hide();
            }

            $(window).resize(function(){
                tc_center();
            });

            

        $('#login_in').bind('click',function(event){
                post_login();
        });

        function post_login(){
             var cur_user = document.getElementById('login_user').value;
             var cur_pass = document.getElementById('login_pass').value;
             if (cur_user == '' || cur_pass == ''){
                  alert('用户名或密码不可为空');
                  return false;
            }
           //窗口水平居中
            
        setup();
	*/    $(window).resize(function(){
                tc_center();
            });
            function tc_center(){
                var _top=($(window).height()-$("#login_div").height())/2;
                var _left=($(window).width()-$("#login_div").width())/2;
                $("#login_div").css({top:_top,left:_left});
                $("#login_div").css({top:_top,left:_left});
                var _top1=($(window).height()-$("#register_div").height())/2;
                var _left1=($(window).width()-$("#register_div").width())/2;
                //alert($(window).width()+":"+$("#login_div").width()+":"+_left);
                $("#register_div").css({top:_top1,left:_left1});
            }


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
	
	//获取首页栏目信息
	vm.getselChildColumn = function (pColumnId) {
        req_getselChildColumn(pColumnId);
    };
	
	// 初始化各市图库栏目信息
	function req_getselChildColumn(pColumnId) {
		req.post('enColumn/selChildColumn.do', {
			pColumnId : pColumnId
		}).success(function(resp) {
			if (resp.code == '211') {
				//各市图库
				if (pColumnId == 3080) {
                    vm.citys = resp.data;
                }
				//皖风徽韵
				if (pColumnId == 3078) {
                    vm.style = resp.data;
                }
				//艺苑菁华
				if (pColumnId == 3082) {
                    vm.essence = resp.data;
                }
				
			} else {
			}
		});
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
                //热点聚焦
                if (signId == 3086) {
                    vm.spotlight = jugeGroupPos.jugeGroupPos(3086,resp.data);
                }
				//江淮影像
				if (signId == 3087) {
                    vm.jianghuaiImage = jugeGroupPos.jugeGroupPos(3087,resp.data);
                }
				//多彩生活
				if (signId == 3088) {
                    vm.colorfulLife = jugeGroupPos.jugeGroupPos(3088,resp.data);
                }
				
				
				//视频
				if (signId == 3081) {
                    vm.anhuiVideo = jugeGroupPos.jugeGroupPos(3081,resp.data);
                }
				//数字报刊
				if (signId == 3097) {
                    vm.ericNewsPaper = jugeGroupPos.jugeGroupPos(3097,resp.data);
                }
				//互动空间
				if (signId == 3083) {
                    vm.interactiveSpace = jugeGroupPos.jugeGroupPos(3097,resp.data);
                }
				//专题图片
				if (signId == 3077) {
                    vm.ahhuiTopics = jugeGroupPos.jugeGroupPos(3077,resp.data);
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