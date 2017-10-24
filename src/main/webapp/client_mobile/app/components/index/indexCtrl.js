clientModule.controller('indexCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos,cityList,$filter,modalOperate) {
    var vm = this;
	
    var mysubNavs1 = new Swiper('#subnavs1',{slidesPerView : 4});
    var mysubNavs2 = new Swiper('#subnavs2',{slidesPerView : 3});
    var mysubNavs3 = new Swiper('#subnavs3',{slidesPerView : 4});
    var mysubNavs4 = new Swiper('#subnavs4',{slidesPerView : 4});
    var mysubNavs5 = new Swiper('#subnavs5',{slidesPerView : 6});
    var mysubNavs6 = new Swiper('#subnavs6',{slidesPerView : 3});
    var mysubNavs7 = new Swiper('#subnavs7',{slidesPerView : 3});
    var mysubNavs8 = new Swiper('#subnavs8',{slidesPerView : 4});
    var mysubNavs9 = new Swiper('#subnavs9',{slidesPerView : 4});


    var mySwiper1 = new Swiper('#swiper-container1',{
        
    })
    var mySwiper2 = new Swiper('#swiper-container2',{
        watchSlidesProgress : true,
        watchSlidesVisibility : true,
        slidesPerView : 4,
        onTap: function(){
            mySwiper1.slideTo( mySwiper2.clickedIndex),
            mySwiper3.slideTo( mySwiper2.clickedIndex)
        }
    })
    var mySwiper3 = new Swiper('#swiper-container3',{
        onSlideChangeStart: function(){
            updateNavPosition1(),
            updateNavPosition()
        }
    })
    function updateNavPosition1(){
        var activeNav = $('#swiper-container1 .subBtn').eq(mySwiper3.activeIndex).addClass('active-nav');
        if (!activeNav.hasClass('swiper-slide-visible')) {
            if (activeNav.index()>mySwiper1.activeIndex) {
                var thumbsPerNav = Math.floor(mySwiper1.width/activeNav.width())-1
                mySwiper1.slideTo(activeNav.index()-thumbsPerNav)
            }else {
                mySwiper1.slideTo(activeNav.index())
            }   
        }
    }
    function updateNavPosition(){
        $('#swiper-container2 .active-nav').removeClass('active-nav')
        var activeNav = $('#swiper-container2 .swiper-slide').eq(mySwiper3.activeIndex).addClass('active-nav');


        if (!activeNav.hasClass('swiper-slide-visible')) {
            if (activeNav.index()>mySwiper2.activeIndex) {
                var thumbsPerNav = Math.floor(mySwiper2.width/activeNav.width())-1
                mySwiper2.slideTo(activeNav.index()-thumbsPerNav)
            }else {
                mySwiper2.slideTo(activeNav.index())
            }   
        }
    }


	

    //首先将#back-to-top隐藏 
    $("#back-to-top").hide(); 
    //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失 
    $(function () { 
    $(window).scroll(function(){ 
    if ($(window).scrollTop()>100){ 
    $("#back-to-top").fadeIn(200); 
    } 
    else
    { 
    $("#back-to-top").fadeOut(200); 
    } 
    }); 
    //当点击跳转链接后，回到页面顶部位置 
    $("#back-to-top").click(function(){ 
    $('body,html').animate({scrollTop:0},100); 
    return false; 
    }); 
    }); 

    /*$(function () {
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
    });*/
	
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
	
    //登录、注册模块js 20170910 
            // function register(){
            //     $("#gray").css("display",'block');
            //     $("#register_div").css("display",'block');
            // }
            // function login(){
            //     $("#gray").css("display",'block');
            //     $("#login_div").css("display",'block');
            // }

            // function go_close(){
            //     $("#gray").hide();
            //     $(".login_box").hide();
            //     $(".register_box").hide();
            // }

            // $(window).resize(function(){
            //     tc_center();
            // });

            

        // $('#login_in').bind('click',function(event){
        //         post_login();
        // });

        // function post_login(){
        //      var cur_user = document.getElementById('login_user').value;
        //      var cur_pass = document.getElementById('login_pass').value;
        //      if (cur_user == '' || cur_pass == ''){
        //           alert('用户名或密码不可为空');
        //           return false;
        //     }
           //窗口水平居中
            
        // setup();
        $(window).resize(function(){
                tc_center();
            });
            function tc_center(){
                var _top=($(window).height()-$("#login_div").height())/2;
                var _left=($(window).width()-$("#login_div").width())/2;
                $("#login_div").css({top:_top,left:_left});
                $("#login_div").css({top:_top,left:_left});
                var _top1=($(window).height()-$("#newArticle_div").height())/2;
                var _left1=($(window).width()-$("#newArticle_div").width())/2;
                //alert($(window).width()+":"+$("#login_div").width()+":"+_left);
                $("#newArticle_div").css({top:_top1,left:_left1});
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
    	//省市县联动数据
		vm.msCityList = cityList.citylist;
		//存储picData数组
        vm.manuscriptPicData = [];
      //拍摄时间
        vm.nowfTime = $filter('date')(new Date(),'yyyy-MM-dd');
      //存储稿件信息
        vm.newManuscriptManuscript = {
            title: '',
            people: '',
            keywords:'',
            place:'',
            memo:'',
            remark:'',
            abroadPlace:'',
            selProv:'安徽',
            selCity:'合肥'
        }
      //初始化图片相关信息
        vm.manuscriptPic = {
            picpeople: {},
            pickeywords:{},
            picAuthorName:{},
            picmemo:{}
        }
        
        getselCpCategories(function(category){
            angular.forEach(category,function(item,index){
                if(item.categoryName == '新闻类别'){
                    vm.categories = item.categories;
                    //console.log("2=="+vm.categories);
                    loadEditSortZTree();
                }
            });
        });
    }


	//我要投稿  add by xiayunan@20171017
	vm.sendArticle = function(){
		/*
		$("#gray").css("display", 'block');
		$("#newArticle_div").css("display", 'block');
		*/
		var jumpUrl ='/photo/index_m.html#/newArticle/';
		window.open(jumpUrl);
	};
	

	// 关闭按钮
	vm.go_close = function() {
		$("#gray").hide();
		$(".login_box").hide();
		$(".register_box").hide();
		$(".newArticle_box").hide();
	}
	
	// 发送验证码
	var clock = '';
	var nums = 60;
	var start = true;
	vm.sendmsg = function(form) {
		var phoneCode = $("#phone_code").val();
		if (phoneCode == '') {
			layer.msg("请输入手机号");
			$('#phone_code').focus();
			return;
		}
		
		if(phoneCode==''||!(/^1[3,4,5,7,8]\d{9}$/.test(phoneCode))){
            layer.alert('请输入正确格式的移动电话');
            return;
        }

		if (nums == 60 && start) {
			start = false;
			$("#code").removeAttr('ng-click');
			$("#code").attr('disabled', "true");
			$("#code").html(nums + '秒后可重新获取');
			clock = setInterval(doLoop, 1000); // 一秒执行一次
			console.log("发送短信");
			req.post('phonemsg/getVilidateCodeForMobile.do', {
				phoneNum : $("#phone_code").val()
			}).success(function(resp) {
				if (resp.code != '211') {
					layer.alert(resp.msg);
				}
			});

		}
	}
	
	function doLoop() {
		nums--;
		if (nums > 0) {
			$("#code").html(nums + '秒后可重新获取');
		} else {
			clearInterval(clock); // 清除js定时器
			$("#code").html('获取验证码');
			$("#code").attr('ng-click', 'header.sendmsg()');
			$("#code").removeAttr('disabled');
			nums = 60; // 重置时间
			start = true;
		}
	}
	
	//改变省的时候
	vm.changeProv = function(prov) {
		for(var i = 0; i < vm.msCityList.length; i++) {
			if(prov == vm.msCityList[i].p) {
				vm.cities = vm.msCityList[i].c;
			}
		}
	};
	
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
				console.log(resp.msg);
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
				//数字报刊
				if (signId == 3097) {
                    vm.ericNewsPaper = jugeGroupPos.jugeGroupPos(3097,resp.data);
					console.log(vm.ericNewsPaper);
                }
				//互动空间
				if (signId == 3083) {
                    vm.interactiveSpace = jugeGroupPos.jugeGroupPos(3097,resp.data);
					console.log(vm.interactiveSpace);
                }
				//专题图片
				if (signId == 3077) {
                    vm.ahhuiTopics = jugeGroupPos.jugeGroupPos(3077,resp.data);
					console.log(vm.ahhuiTopics);
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

    // 获取稿件分类信息
    /*
    function getselCpCategories(callback) {
        req.post('classification/selCpCategoriesNoLogin.do', {
            langType : 0
        }).success(function(resp) {
            if (resp.code == '211') {
                vm.selCpCategories = resp.data;
                if (callback)
                    callback(resp.data);
                console.log('success');
            } else {
                console.log(resp.msg);
            }
        });
    }
    */
    
    //获取首页栏目信息
    vm.getselChildColumn = function (pColumnId) {
        req_getselChildColumn(pColumnId);
    };
    
    // 获取栏目信息
    function req_getselChildColumn(pColumnId) {
        req.post('enColumn/selChildColumn.do', {
            pColumnId : pColumnId
        }).success(function(resp) {
            // if (resp.code == '211') {
                //新闻图片
                if (pColumnId == 3064) {
                    vm.newsPhoto1 = resp.data;
                    // console.log('success');
                }
                //专题图片
                if (pColumnId == 3077) {
                    vm.topicPhoto1 = resp.data;
                    // console.log('success');
                }
                //皖风徽韵
                if (pColumnId == 3078) {
                    vm.anhuiStyle1 = resp.data;
                }
                //历史资料
                if (pColumnId == 3079) {
                    vm.anhuiHistory1 = resp.data;
                }
                //各市图库
                if (pColumnId == 3080) {
                    vm.anhuiCitys1 = resp.data;
                }
                //视频VR
                if (pColumnId == 3081) {
                    vm.anhuiVideo1 = resp.data;
                }
                //艺苑菁华
                if (pColumnId == 3082) {
                    vm.artEssence1 = resp.data;
                }
                //互动空间
                if (pColumnId == 3083) {
                    vm.interactiveSpace1 = resp.data;
                }
                // 掌上图库
                if (pColumnId == 3084) {
                    vm.handPics1 = resp.data;
                }
                
            // } 
                else {
                console.log(resp.msg);
            }
        });
    }
    
    getOnlineUserList();
    // 获取在线用户列表
    function getOnlineUserList() {
        req.post('login/getOnLineUsers.do', {
            rows : '',
            page : ''
        }).success(function(resp) {
            if (resp.code == '211') {
                vm.OnLineUsersList = resp.data;
                vm.onlineList_total = resp.other;
            } else if (resp.msg != '未登录') {
                console.log(resp.msg);
            }
        });
    }
    //我要投稿
    vm.newManuscript = function(){
        window.location.href = "/photo/admin.html#/manager/newManuscript";
    }

    /**
     * 全文检索
     */
    vm.searchAll = function() {
        /*
         * getFullText.req_getFullText({ page: 1, rows: 10, strWhere:
         * vm.searchAllName });
         */
        vm.randomNum = new Date().getTime() + Math.random() + Math.random();
        var fullTextUrl = $state.href('root.fullText', {
            searchAllName : vm.searchAllName,
            randomNum : vm.randomNum
        });
        window.open(fullTextUrl, '_blank');
    };

    /**
     * 获取广告位第一位图片
     */
    function getAdFirstShowImageData() {
        req.post('adver/adverToHomePage.do', {}).success(function(resp) {
            if (resp.code == '211') {
                var adArray = resp.data;
                var slides = $scope.slides = [];
                /*
                 * slides.push({image : adArray[0].file}); slides.push({image :
                 * adArray[1].file}); slides.push({image : adArray[2].file});
                 */
                vm.adFirstImageObj = {
                    file : ""
                };
                for (var j = 0; j < adArray.length; j++) {
                    if (adArray[j]['position'] == 1) {
                        vm.adFirstImageObj = adArray[j];
                        slides.push({
                            image : adArray[j].file
                        });
                        // break;
                    }
                }
            } else {
                console.log(resp.msg);
            }
        });
    }

    /**
     * 广告页或专题页的跳转
     * 
     * @param itemObj
     *            广告位对象
     */
    vm.onToHeadShowAdTopicClick = function(itemObj) {
        var imgFile = itemObj['file'];
        if (imgFile) {
            var topicId = itemObj['topicId'];
            var adUrl = itemObj['url'];
            if (topicId) {
                $state.go('root.special', {
                    'id' : topicId
                });
            } else {
                window.open(adUrl);
            }
        }
    };
    
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

    // 请求
    getWebPublishData();

    // 请求广告位图片
    getAdShowImageData();
    
  //上传完图片向upMenuscriptPicArr数组推入图片的相关信息，让图片列表展示
    function uploadedPicCallback(j){
        //完成后对应的速度条消失
        if(j == 0 || displayed[j-1]){
            $('.ms-upinfo-box .ms-up-content-box').eq(j).fadeOut();
        }
        angular.forEach(vm.uploadEditPicList,function(item,index){
        	//add by liu.jinfeng@20170914
        	if(!item.bIsExif){
        		vm.bIsExif = false;
        		return;
        	}
            $scope.$apply(function(){

                vm.upMenuscriptPicArr.push({
                    id: item.id + '',
                    filmTime: $filter('date')(item.filmTime,'yyyy-MM-dd'),
                    img: item.smallPath,
                    isCover: '0',
                    sortId: (index + 1) + '',
                    people: item.people,
                    keywords: item.keywords,
                    authorName: item.authorName,
                    memo: item.memo
                })
                //默认上传的第一个为主图
                if(index == '0'){
                    vm.upMenuscriptPicArr[0].isCover = '1';
                }
            });
        });
    }
    
  //上次的图片请求是否完成的标志
    var displayed = [];
    //上传稿件请求
    function req_upMs(j){
        var formdata = new FormData();
        //console.log(vm.upMsFiles[j]);
        formdata.append('picFiles', vm.upMsFiles[j]);
        formdata.append("langType",lang);
        //console.log(formdata);
        $.ajax({
            type: "POST",
            data: formdata,
            url: "/photo/groupPicCtro/upPic.do" + '?time=' + new Date().getTime(),
            cache: false,
            contentType: false,     //必须false才会自动加上正确的Content-Type
            processData: false,     //必须false才会避开jQuery对formdata的默认处理
            async: true,
            xhrFields: {
                withCredentials: true
            },
            xhr:function(){
                //获取ajaxSetting中的xhr对象，为它的upload属性绑定progress事件的处理函数
                myXhr = $.ajaxSettings.xhr();
                if(myXhr.upload){
                    //检查upload属性是否存在
                    myXhr.upload.addEventListener('progress',function(e){
                        if(e.lengthComputable) {
                            var percent = e.loaded/e.total*100;
                            $('.ms-upinfo-box .ms-up-content-box .ms-up-progress-desc .ms-up-progress-bar').eq(j).html(percent.toFixed(2) + "%");
                            $('.ms-upinfo-box .ms-up-content-box .ms-up-progress-bg-box .ms-up-progress-bg').eq(j).width(percent.toFixed(2) + "%");
                        }
                    },false);
                }
                return myXhr; //xhr对象返回给jQuery使用
            }
        }).success(function (resp) {
           if(resp.code && resp.code == '211'){
                vm.uploadEditPicList = resp.data;
                //console.log(vm.uploadEditPicList);
                vm.bIsExif = true;
                uploadedPicCallback(j);
                displayed[j] = true;
                if(!vm.bIsExif){
                	layer.alert("上传不包含Exif信息的图片失败");
                	if((j+1) < vm.upMsTotalLen){
                		 req_upMs(j+1);
                	}
                	
                }else if(j === vm.upMsTotalLen -1){
                   // console.log('显示完毕');
                   //操作结束清空input中的内容，解决input file表单无法重复上传同一个图片的问题
                     $('#picFile').val('');
                }else if(j < vm.upMsTotalLen){
                    req_upMs(j+1);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
               //上传不成功时去掉进度条，多张图片时继续下面的图片上传
                $('.ms-upinfo-box .ms-up-content-box').eq(j).fadeOut();
                displayed[j] = true;
                if(j < vm.upMsTotalLen){
                   req_upMs(j+1);
                }
           }
          // console.log($('#picFile').val());
        }).error(function (resp) {
            //console.log(resp);
        });
    }
    
  //上传图片格式的验证
    function validUpMsPic(filename){
        var upPicSuffix= filename.substring(name.lastIndexOf(".")+1).toLowerCase();
        if(!/\.(jpg|jpeg|JPG|JPEG)$/.test(upPicSuffix))
        {
            layer.alert("图片类型必须是.jpg,jpeg中的一种")
            return false;
        }else{
            return true;
        }
    }

    //上传图片
    $scope.uploadManuscript = function(element){
        //每次上传前清除之前的速度条div里的速度条
        $('.ms-upinfo-box .ms-up-content-box').remove();
        //每次上传前初始化displayed标志
        displayed = [];
        //保存上传的文件对象
        vm.upMsFiles = element.files;
        vm.upMsTotalLen = element.files.length;
        //遍历上传的文件
        for(var i = 0,len = vm.upMsTotalLen;i < len;i++){
            //验证上传图片是否是jpg/jpeg格式
            if(validUpMsPic(vm.upMsFiles[i].name)){
                if(vm.upMsFiles && vm.upMsFiles[i].size){
                    vm.msFileSize = $filter('Filesize')(vm.upMsFiles[i].size);
                }
                var msSpeedHtml = '<div class="ms-up-content-box">'
                    + '<p class="ms-up-progress-desc">'
                    + '<span>' + vm.upMsFiles[i].name + "（" + vm.msFileSize + "）- "+ '</span>'
                    + '<span class="ms-up-progress-bar">0%</span>'
                    +'</p>'
                    + '<div class="ms-up-progress-bg-box">'
                    + '<div class="ms-up-progress-bg">'
                    + '</div>'
                    + '</div>'
                    + '</div>';
                $('.ms-upinfo-box').append(msSpeedHtml);
                (function(j){
                    if(j == 0 || displayed[j-1]){
                        req_upMs(j);
                    }
                })(i);
            }else{
                //如果上传图片格式不正确则从上传数组中删除（Array.prototype.slice.call把类数组转为数组）
                vm.upMsFiles = Array.prototype.slice.call(element.files,0);
                vm.upMsFiles.splice(i,1);
                vm.upMsTotalLen = vm.upMsFiles.length;
                //console.log(vm.upMsFiles);
                //console.log(vm.upMsTotalLen);
            }
        }
        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
    
    //删除每项图片
    vm.removePicItem = function(index){
        vm.upMenuscriptPicArr.splice(index, 1);
    }

   
    
    //获取图片的相关参数
    function getPicDataParams(){
        //console.log(typeof angular.toJson(vm.upMenuscriptPicArr,true));
        angular.forEach(vm.upMenuscriptPicArr,function(item,index){
            if(vm.upMenuscriptPicArr[index].people && vm.upMenuscriptPicArr[index].people.length > 200){
                layer.alert('人物要少于200字');
                return;
            }
             if(!vm.upMenuscriptPicArr[index].keywords){
             layer.alert('请填写图片关键词！');
             return;
             }
            if(vm.upMenuscriptPicArr[index].keywords && vm.upMenuscriptPicArr[index].keywords.length > 200){
                layer.alert('关键词要少于200字');
                return;
            }
            if(vm.upMenuscriptPicArr[index].authorName && vm.upMenuscriptPicArr[index].authorName.length > 200){
                layer.alert('作者要少于200字');
                return;
            }
            if(vm.upMenuscriptPicArr[index].memo && vm.upMenuscriptPicArr[index].memo.length > 4000){
                layer.alert('图片说明要少于4000字');
                return;
            }
            vm.manuscriptPicData.push({
                id: item.id + '',
                people: vm.upMenuscriptPicArr[index].people || '',
                keywords: vm.upMenuscriptPicArr[index].keywords || '',
                authorId:  vm.authorId + '',
                authorName: vm.upMenuscriptPicArr[index].authorName || '',
                memo: vm.upMenuscriptPicArr[index].memo || '',
                isCover: vm.upMenuscriptPicArr[index].isCover,
                sortId: (index + 1) + '',
                filmTime: vm.upMenuscriptPicArr[index].filmTime + ' 00:00:00'
            })
        });
        //console.log(vm.manuscriptPicData);
    }
    
    
    
    // 编辑稿件分类模态框显示
    vm.editMsSortsModalShow = function(){
         $('#edit-sort-modal').modal('show');
        loadEditSortZTree();
    }
    // 加载编辑分类树
    function loadEditSortZTree(){
//    	layer.alert(vm.selCpCategories);
    	//console.log("3=="+vm.selCpCategories);
    	console.log("<<<vm.selCpCategories:"+vm.selCpCategories);
        $.fn.zTree.init($("#edit_sort_tree"), setting, vm.selCpCategories);
        console.log("<<<1111111111:"+vm.selCpCategories);
        selectedEverSorts();
    }
 // 确定编辑稿件分类
    vm.confirmEditMsSorts = function(){
       //console.log(getChildNodesSortId());
        if(getChildNodesSortId() == ''){
            layer.alert('请选择稿件类别');
            return;
        }
        $('#edit-sort-modal').modal('hide');
    }
    // 获取子节点的分类id，多个用英文逗号隔开
    function getChildNodesSortId(){
        var cateIdParamArr = [];
        var categoryNameArr  = [];
        var cateIdParamStr = '';
        var treeObj = $.fn.zTree.getZTreeObj("edit_sort_tree");
        var nodes = treeObj.getCheckedNodes(true); //获取选中的值
        for(var i = 0, nodesLen = nodes.length; i < nodesLen; i++){
            // 排除父节点
            //if(!nodes[i].isParent){
                cateIdParamArr.push(nodes[i].id);
                categoryNameArr.push(nodes[i].categoryName);
                cateIdParamStr = cateIdParamArr.join();
                vm.categoryNameStr = categoryNameArr.join();
//                console.log(cateIdParamStr);
//                console.log(vm.categoryNameStr);
            //}
        }
        return cateIdParamStr;
    }
  //获取稿件分类信息
    function getselCpCategories(callback){
        req.post('classification/selCpCategoriesNoLogin.do',{langType:0}).success(function(resp){
            if(resp.code == '211'){
            	category = resp.data;
            	angular.forEach(category, function (item, index) {
                if (item.categoryName == '新闻类别') {
                    vm.selCpCategories  = item.categories;
//                    console.log(vm.selCpCategories);
	                }
	            });
                if (callback) callback(resp.data);
                //console.log('success');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    
    
    
    // 选中已经编辑完的分类
    function selectedEverSorts(){
        var treeObj = $.fn.zTree.getZTreeObj("edit_sort_tree");
        var nodes = treeObj.transformToArray(treeObj.getNodes());
        if(vm.manuscriptCates && vm.manuscriptCates.length){
            for (var i=0, l=nodes.length; i < l; i++) {
                for(var j = 0,Len = vm.manuscriptCates.length; j < Len; j++){
                    if(vm.manuscriptCates[j].id == nodes[i].id){
                        treeObj.checkNode(nodes[i], true, true);
                    }
                }
            }
        }
    }
    var zTree;
    var setting = {
        view: {
            dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
       /*     showLine: true,//是否显示节点之间的连线
            fontCss:{'color':'black','font-weight':'bold'},//字体样式函数*/
            selectedMulti: false //设置是否允许同时选中多个节点
        },
        check:{
            chkboxType: { "Y": "", "N": "" },
            chkStyle: "checkbox",//复选框类型
            enable: true //每个节点上是否显示 CheckBox
        },
        data: {
            key: {
                checked: "checked",
                children: "categories",
                name: "categoryName"
            },
            simpleData: {//简单数据模式
                enable:true
            }
        },
        callback: {
            /*beforeClick: function(treeId, treeNode) {
                zTree = $.fn.zTree.getZTreeObj("edit_sort_tree");
                if (treeNode.isParent) {
                    zTree.expandNode(treeNode);//如果是父节点，则展开该节点
                }else{
                    zTree.checkNode(treeNode, !treeNode.checked, true, true);//单击勾选，再次单击取消勾选
                }
            }*/
        }
    };
    
    
    
    
    
    
    
    
    
    
    
    
});