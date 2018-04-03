clientModule.controller('ahnewswinnerslistCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos) {
    var vm = this;
	
    $(function () {
        $timeout(function () {
            $('#news_slideBox').slide({
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
	
	   $(window).resize(function(){
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
                //一等奖
                if (signId == 3166) {
                    vm.firstprize = jugeGroupPos.jugeGroupPos(3166,resp.data);
                }
				//二等奖
				if (signId == 3167) {
                    vm.secondprize = jugeGroupPos.jugeGroupPos(3167,resp.data);
                }
				
				//漫画将
				if (signId == 3170) {
                    vm.cartoonprize = jugeGroupPos.jugeGroupPos(3170,resp.data);
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


});