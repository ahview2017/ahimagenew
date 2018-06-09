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
				mainCell:".bd ul",effect:"leftLoop",autoPlay:true,delayTime:500,titCell:".btn li",events:"click",nextCell: ".index_prev",
			prevCell: ".index_next"
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
        
        vm.showFlag1 = false;
        vm.showFlag2 = false;
        vm.showFlag3 = false;
        vm.showFlag4 = false;
        vm.showFlag5 = false;
        vm.showFlag6 = false;
        vm.showFlag7 = false;
        vm.showFlag8 = false;
        vm.showFlag9 = false;
        vm.showFlag10 = false;
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
			size: size,
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
                }
				//历史资料
				if (signId == 3079) {
                    vm.anhuiHistory = jugeGroupPos.jugeGroupPos(3079,resp.data);
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
                console.log("<<<adArray.length:"+adArray.length);
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
               
                //解决广告位为空时的空白div问题 add by xiayunan@20180302
            	if(vm.adShowImageArray[1]['file']!=null&&vm.adShowImageArray[1]['display']==1){
            		vm.showFlag1 = true;
                }
            	if(vm.adShowImageArray[2]['file']!=null&&vm.adShowImageArray[2]['display']==1){
                	vm.showFlag2 = true;
                }
            	if(vm.adShowImageArray[3]['file']!=null&&vm.adShowImageArray[3]['display']==1){
                	vm.showFlag3 = true;
                }
            	if(vm.adShowImageArray[4]['file']!=null&&vm.adShowImageArray[4]['display']==1){
                	vm.showFlag4 = true;
                }
            	if(vm.adShowImageArray[5]['file']!=null&&vm.adShowImageArray[5]['display']==1){
                	vm.showFlag5 = true;
                }
            	if(vm.adShowImageArray[6]['file']!=null&&vm.adShowImageArray[6]['display']==1){
                	vm.showFlag6 = true;
                }
            	if(vm.adShowImageArray[7]['file']!=null&&vm.adShowImageArray[7]['display']==1){
                	vm.showFlag7 = true;
                }
            	if(vm.adShowImageArray[8]['file']!=null&&vm.adShowImageArray[8]['display']==1){
                	vm.showFlag8 = true;
                }
            	if(vm.adShowImageArray[9]['file']!=null&&vm.adShowImageArray[9]['display']==1){
                	vm.showFlag9 = true;
                }
            	if(vm.adShowImageArray[10]['file']!=null&&vm.adShowImageArray[10]['display']==1){
                	vm.showFlag10 = true;
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