clientModule.controller('indexCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos,$filter,modalOperate) {
    var vm = this;
    //初始化页面相关配置
    function initSetting() {
    	vm.currpage = 0;
	    vm.currchnl = 3064;
	    vm.chnlArr = [3064,3077,3078,3079,3080,3081,3082,3083,3084];
	    vm.pageHeight = Math.max(document.body.scrollHeight,document.body.offsetHeight);
	    vm.viewportHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
	    vm.scrollHeight = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
        getselCpCategories(function(category){
            angular.forEach(category,function(item,index){
                if(item.categoryName == '新闻类别'){
                    vm.categories = item.categories;
                }
            });
        });
    }
    
    //页面初始化
    function init() {
        initSetting();
        //req_getClientPicture();
        //getClientGroupPics();
    }
    
    init();
    
    
    function initChnlData(){
    	vm.getMoreGroups(3064,1);
    	vm.getMoreGroups(3077,1);
    	vm.getMoreGroups(3078,1);
    	vm.getMoreGroups(3079,1);
    	vm.getMoreGroups(3080,1);
    	vm.getMoreGroups(3081,1);
    	vm.getMoreGroups(3082,1);
    	vm.getMoreGroups(3083,1);
    	vm.getMoreGroups(3084,1);
    }

   
    /*
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
    */
    
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
    
    
    function switchChnlTab(){
    	initChnlData();//初始化各栏目数据
    	vm.currpage = 1;//切换导航栏目当前页置为1
    	var index = $('#swiper-container2 .active-nav').index();
    	switch (parseInt(index)) {
			case 0:
		        vm.currchnl = vm.chnlArr[0];  //新闻图片
		        break;
	        case 1 :
	        	vm.currchnl = vm.chnlArr[1];  //专题图片
	            break;
	        case 2:
	        	vm.currchnl = vm.chnlArr[2]; //皖风徽韵
	            break;
	        case 3:
	        	vm.currchnl = vm.chnlArr[3];  //历史资料
	            break;
	        case 4:
	        	vm.currchnl = vm.chnlArr[4];  //各市图库
	            break;
	        case 5:
	        	vm.currchnl = vm.chnlArr[5];  //视频VR
	            break;
	        case 6:
	        	vm.currchnl = vm.chnlArr[6];  //艺苑菁华
	            break;
	        case 7:
	        	vm.currchnl = vm.chnlArr[7];  //互动空间
	            break;
	        case 8:
	        	vm.currchnl = vm.chnlArr[8];  //掌上图库
	            break;
    	}
    	
    }

    /*
    var mySwiper1 = new Swiper('#swiper-container1',{//二级导航栏
        
    })*/
    var mySwiper2 = new Swiper('#swiper-container2',{//一级导航栏
        watchSlidesProgress : true,
        watchSlidesVisibility : true,
        slidesPerView : 4,
        onTap: function(){
            //mySwiper1.slideTo( mySwiper2.clickedIndex),
            mySwiper3.slideTo( mySwiper2.clickedIndex)
        }
    })
    
    var mySwiper3 = new Swiper('#swiper-container3',{
        onSlideChangeStart: function(){
            //updateNavPosition1(),
            updateNavPosition(),
            switchChnlTab()
        }
    })


    //首先将#back-to-top隐藏 
    $("#back-to-top").hide(); 
    //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失 
    $(function () { 
	    $(window).scroll(function(){ 
		    if ($(window).scrollTop()>100){ 
		    	$("#back-to-top").fadeIn(200); 
		    }else{ 
		    	$("#back-to-top").fadeOut(200); 
		    } 
		 }); 
		 //当点击跳转链接后，回到页面顶部位置 
		 $("#back-to-top").click(function(){ 
		    $('body,html').animate({scrollTop:0},100); 
		    return false; 
		 }); 
		 
		 
		//下拉分页 add by xiayunan@20171023
		$(window).bind("scroll",scrollFn);//绑定滚动事件
    }); 

	
  //手机版滚动监听事件 add by xiayunan@20171023
  function scrollFn(){
	  	vm.pageHeight = Math.max(document.body.scrollHeight,document.body.offsetHeight);
	    vm.viewportHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
	    vm.scrollHeight = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
	    
    	//真实内容的高度
    	//var pageHeight = Math.max(document.body.scrollHeight,document.body.offsetHeight);
    	//视窗的高度
    	//var viewportHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
    	//隐藏的高度
    	//var scrollHeight = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
    	if(vm.pageHeight - vm.viewportHeight - vm.scrollHeight < 20){//如果满足触发条件，执行
    		vm.currpage++;
    		req_appendMoreGroups(vm.currchnl,vm.currpage);
    	}
    }
  

    


	//我要投稿  add by xiayunan@20171017
	vm.sendArticle = function(){
		var jumpUrl ='/photo/index_m.html#/newArticle/';
		window.open(jumpUrl);
	};
	

    
	
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
				//新闻图片
				if (signId == 3064) {
                    vm.newsPhoto = jugeGroupPos.jugeGroupPos(3064,resp.data);
                    vm.currchnl = 3064;
                }
				//专题图片
				if (signId == 3077) {
                    vm.topicPhoto = jugeGroupPos.jugeGroupPos(3077,resp.data);
                    vm.currchnl = 3077;
                }
				//皖风徽韵
				if (signId == 3078) {
                    vm.anhuiStyle = jugeGroupPos.jugeGroupPos(3078,resp.data);
                    vm.currchnl = 3078;
                }
				//历史资料
				if (signId == 3079) {
                    vm.anhuiHistory = jugeGroupPos.jugeGroupPos(3079,resp.data);
                    vm.currchnl = 3079;
                }
				//各市图库
				if (signId == 3080) {
                    vm.anhuiCitys = jugeGroupPos.jugeGroupPos(3080,resp.data);
                    vm.currchnl = 3080;
                }
				//视频
				if (signId == 3081) {
                    vm.anhuiVideo = jugeGroupPos.jugeGroupPos(3081,resp.data);
                    vm.currchnl = 3081;
                }
				//艺苑菁华artEssence
				if (signId == 3082) {
                    vm.artEssence = jugeGroupPos.jugeGroupPos(3082,resp.data);
                    vm.currchnl = 3082;
                }
				//互动空间
				if (signId == 3083) {
                    vm.interactiveSpace = jugeGroupPos.jugeGroupPos(3097,resp.data);
                    vm.currchnl = 3083;
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


    // 获取稿件分类信息
    function getselCpCategories(callback) {
        req.post('classification/selCpCategories.do', {
            langType : 0
        }).success(function(resp) {
            if (resp.code == '211') {
                vm.selCpCategories = resp.data;
                if (callback)
                    callback(resp.data);
            } else {
                console.log(resp.msg);
            }
        });
    }
    
    //获取首页栏目信息
    vm.getselChildColumn = function (pColumnId) {
        req_getselChildColumn(pColumnId);
    };
    
    // 获取栏目信息
    function req_getselChildColumn(pColumnId) {
        req.post('enColumn/selChildColumn.do', {
            pColumnId : pColumnId
        }).success(function(resp) {
                //新闻图片
                if (pColumnId == 3064) {
                    vm.newsPhoto1 = resp.data;
                }
                
                //专题图片
                if (pColumnId == 3077) {
                    vm.topicPhoto1 = resp.data;
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
                
                /*
                var mysubNavs1 = new Swiper('#subnavs1',{slidesPerView : 4});
                var mysubNavs2 = new Swiper('#subnavs2',{slidesPerView : 3});
                var mysubNavs3 = new Swiper('#subnavs3',{slidesPerView : 4});
                var mysubNavs4 = new Swiper('#subnavs4',{slidesPerView : 4});
                var mysubNavs5 = new Swiper('#subnavs5',{slidesPerView : 6});
                var mysubNavs6 = new Swiper('#subnavs6',{slidesPerView : 3});
                var mysubNavs7 = new Swiper('#subnavs7',{slidesPerView : 3});
                var mysubNavs8 = new Swiper('#subnavs8',{slidesPerView : 4});
                var mysubNavs9 = new Swiper('#subnavs9',{slidesPerView : 4});
                */
                
               
                
        });
    }
    
    
    //获取对应栏目的稿件
    vm.getMoreGroups = function (signId,page) {
        req_getMoreGroups(signId,page);
    };
	
	//获取更多分类图片
    function req_getMoreGroups(signId,page){
        req.post('getPicture/getMoreGroups.do',{
            sginId: signId, //栏目id
            //cateId: cateId,
            rows: 3, //每页条数
            page: page,  //当前页码
            //parameter: vm.tempSortsData.keyWord,
            pType: 0,
            timeType: 2
        }).success(function(resp){
            if(resp.code == '211'){
                //新闻图片
				if (signId == 3064) {
					vm.newsPhotoMobile = resp.data;
					//vm.newsPhotoMobile = jugeGroupPos.jugeGroupPos(3064,resp.data);
					console.log(vm.newsPhotoMobile);
					//vm.currchnl = 3064;
                }
				
				//专题图片
				if (signId == 3077) {
                    vm.topicPhoto = resp.data;
                    //vm.currchnl = 3077;
                }
				//皖风徽韵
				if (signId == 3078) {
                    vm.anhuiStyle =resp.data;
                    //vm.currchnl = 3078;
                }
				//历史资料
				if (signId == 3079) {
                    vm.anhuiHistory = resp.data;
                    //vm.currchnl = 3079;
                }
				//各市图库
				if (signId == 3080) {
                    vm.anhuiCitys = resp.data;
                    //vm.currchnl = 3080;
                }
				//视频
				if (signId == 3081) {
                    vm.anhuiVideo = resp.data;
                    //vm.currchnl = 3081;
                }
				//艺苑菁华artEssence
				if (signId == 3082) {
                    vm.artEssence = resp.data;
                    //vm.currchnl = 3082;
                }
				//互动空间
				if (signId == 3083) {
                    vm.interactiveSpace = resp.data;
                    //vm.currchnl = 3083;
                }
				//掌上图库
				if (signId == 3084) {
                    vm.artEssence = resp.data;
                    //vm.currchnl = 3084;
                }
				
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    //下拉分页加载数据
    /*
    vm.appendMoreGroups = function (signId,page) {
        req_appendMoreGroups(signId,page);
    };
    */
    //获取更多分类图片
    function req_appendMoreGroups(signId,page){
        req.post('getPicture/getMoreGroups.do',{
            sginId: signId, //栏目id
            //cateId: cateId,
            rows: 3, //每页条数
            page: page,  //当前页码
            //parameter: vm.tempSortsData.keyWord,
            pType: 0,
            timeType: 2
        }).success(function(resp){
            if(resp.code == '211'){
                //新闻图片
				if (signId == 3064) {
					appendData(resp.data,vm.newsPhotoMobile);
                }
				
				//专题图片
				if (signId == 3077) {
					appendData(resp.data,vm.topicPhoto);
                }
				//皖风徽韵
				if (signId == 3078) {
					appendData(resp.data,vm.anhuiStyle);
                }
				//历史资料
				if (signId == 3079) {
					appendData(resp.data,vm.anhuiHistory);
                }
				//各市图库
				if (signId == 3080) {
					appendData(resp.data,vm.anhuiCitys);
                }
				//视频
				if (signId == 3081) {
					appendData(resp.data,vm.anhuiVideo);
                }
				//艺苑菁华artEssence
				if (signId == 3082) {
					appendData(resp.data,vm.artEssence);
                }
				//互动空间
				if (signId == 3083) {
					appendData(resp.data,vm.interactiveSpace);
                }
				//掌上图库
				if (signId == 3084) {
					appendData(resp.data,vm.artEssence);
                }
				
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    //下拉分页添加数据  add by xiayunan@20171027
    function appendData(data,arr){
    	angular.forEach(data,function(item,index){
    		arr.push({
				 AUTHOR: item.AUTHOR + '',
				 PICTURE_COUNT: item.PICTURE_COUNT || '',
				 AUTHOR_ID: item.AUTHOR_ID || '',
				 filePath:  item.filePath + '',
				 ID: item.ID || '',
				 SGIN_TIME: item.SGIN_TIME || '',
				 FILENAME: item.FILENAME|| '',
				 picId: item.picId|| '',
				 POSITION: item.POSITION|| '',
				 TITLE: item.TITLE|| ''
			 });
		 });
    }
    
    
    
    
});