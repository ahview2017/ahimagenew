clientModule.controller('indexCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, $timeout, jugeGroupPos,$filter,modalOperate) {
    var vm = this;
    
    
    /*** 顶部搜索框控制js 首先将搜索框隐藏 ***/
    $("#searchBox").hide(); 

    //当滚动条的位置处于距顶部0像素以下时，跳转链接出现，否则消失 
    var startX, startY; 

    document.addEventListener('touchstart',function (ev) {  
            startX = ev.touches[0].pageX;  
            startY = ev.touches[0].pageY;  
        }, false);  
  
        document.addEventListener('touchend',function (ev) {  
        var endX, endY;  
        endX = ev.changedTouches[0].pageX;  
        endY = ev.changedTouches[0].pageY;  
        var direction = GetSlideDirection(startX, startY, endX, endY);
        if ($(window).scrollTop()==0){ 
            if(direction==2){
                $("#searchBox").fadeIn(500);
            }else if(direction==1){
                $("#searchBox").fadeOut(500);
            }
        }else{ 
            $("#searchBox").fadeOut(500); 
        }      
    }, false);  


    //滑动处理  
    function GetSlideDirection(startX, startY, endX, endY) {  
        var dy = startY - endY;  
        //var dx = endX - startX;  
        var result = 0;  
        if(dy>0) {//向上滑动  
            result=1;  
        }else if(dy<0){//向下滑动  
            result=2;  
        }  
        else{  
            result=0;  
        }  
        return result;  
    }
    
    //手机版检索
    vm.doSearch = function(){
    	//校验检索词是否为空
    	if(!vm.searchStr){
    		layer.alert("请输入检索词");
    		return;
    	}
    	//window.location.href = "/photo/index_m.html#/searchGroup/?searchStr="+vm.searchStr;
    	$cookies.put("searchStr", vm.searchStr);
    	$state.go('root.searchGroup',{searchStr:vm.searchStr});
    	
    	
    }
 
    //获取对应栏目的稿件
    vm.getMoreGroups = function (signId,page) {
        req_getMoreGroups(signId,page);
    };
    
    var mySwiper2 = new Swiper('#swiper-container2',{//一级导航栏
        watchSlidesProgress : true,
        watchSlidesVisibility : true,
        slidesPerView : 4,
        onTap: function(){
            //mySwiper1.slideTo( mySwiper2.clickedIndex),
            mySwiper3.slideTo( mySwiper2.clickedIndex)
        }
    })
    //初始化页面相关配置
    function initSetting() {
    	vm.currpage = 1;
	    vm.currchnl = 3064;
    	vm.currpageForSearch = 1;
    	vm.isSearch = false;
    	 //从cookie里取得存储的用户上次离开的页面地址
    	//vm.currchnl = $cookies.get('client_chnl')==null?3064:$cookies.get('client_chnl');
    	//vm.activeIndex =  $cookies.get('client_activeIndex')==null?0:$cookies.get('client_activeIndex');
//    	updateNavPosition(),
//        switchChnlTab();
    	// console.log(" vm.currchnl:"+vm.currchnl );
    	
	   // vm.chnlArr = [3064,3077,3078,3079,3080,3081,3082,3083,3084];
	    vm.chnlArr = [3064,3077,3078,3079,3080,3081,3101,3126,3103,3125,3083,3084,3085];//edit by xiayunan@20180119
	    //点赞数
        vm.thumbsUpCount = 0;
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
        
        //updateNavPosition();
        //switchChnlTab();

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
    	vm.getMoreGroups(3101,1);
    	vm.getMoreGroups(3126,1);
    	vm.getMoreGroups(3103,1);
    	vm.getMoreGroups(3125,1);
    	vm.getMoreGroups(3083,1);
    	vm.getMoreGroups(3084,1);
    	vm.getMoreGroups(3085,1);
    	
    }

    vm.thumbsUp = function(groupId,index){
    	saveGroupPicThumbsUp(groupId,index);
    };



    
    //稿件点赞
    function saveGroupPicThumbsUp(groupId,oIndex){
    	console.log("groupId:"+groupId);
    	 req.post('groupPicCtro/saveGroupPicThumbsUp.do', {
             groupId: groupId,
          }).success(function (resp) {
              if (resp.code == '211') {
            	  if(resp.data.status==1){
            		  //alert("您已经赞过了！");
            	  }else if(resp.data.status==0){
            		  //alert("太棒了，赞一个！");
            		//新闻图片
       				if ( vm.currchnl == 3064) {
       					angular.forEach(vm.newsPhotoMobile,function(item,index){
       	            		 if(oIndex==index){
       	            			 item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//专题图片
       				if ( vm.currchnl == 3077) {
       					angular.forEach(vm.topicPhoto,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });;
                       }
       				//皖风徽韵
       				if ( vm.currchnl == 3078) {
       					angular.forEach(vm.anhuiStyle,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//历史资料
       				if ( vm.currchnl == 3079) {
       					angular.forEach(vm.anhuiHistory,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//各市图库
       				if ( vm.currchnl == 3080) {
       					angular.forEach(vm.anhuiCitys,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//视频
       				if ( vm.currchnl == 3081) {
       					angular.forEach(vm.anhuiVideo,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//艺苑菁华artEssence(安徽画坛)
       				if ( vm.currchnl == 3101) {
       					angular.forEach(vm.artEssence,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//网上展厅
       				if ( vm.currchnl == 3126) {
       					angular.forEach(vm.webDisplay,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//漫画天地
       				if ( vm.currchnl == 3103) {
       					angular.forEach(vm.comicWorld,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//摄影名家
       				if ( vm.currchnl == 3125) {
       					angular.forEach(vm.famousPhotographers,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				
       				//互动空间
       				if ( vm.currchnl == 3083) {
       					angular.forEach(vm.interactiveSpace,function(item,index){
       	            		 if(oIndex==index){
       	            			item.isThumbsUp = true;
       	            		 }
       	         		 });
                       }
       				//掌上图库
      				if ( vm.currchnl == 3084) {
      					angular.forEach(vm.handPics,function(item,index){
      	            		 if(oIndex==index){
      	            			item.isThumbsUp = true;
      	            		 }
      	         		 });
                      }
      				//特别策划
      				if ( vm.currchnl == 3085) {
      					angular.forEach(vm.specialPlanning,function(item,index){
      	            		 if(oIndex==index){
      	            			item.isThumbsUp = true;
      	            		 }
      	         		 });
                      }
      				
            	  }
            	  getThumbsUpCount(groupId,oIndex);
              }else if(resp.msg != '未登录'){
                  layer.alert(resp.msg);
              }
          });
    }
    
    //获取稿件点赞数 add by xiayunan@20171030
    function getThumbsUpCount(groupId,oIndex){
    	 req.post('groupPicCtro/getThumbsUpCount.do', {
            groupId: groupId,
         }).success(function (resp) {
             if (resp.code == '211') {
            	//新闻图片
 				if ( vm.currchnl == 3064) {
 					angular.forEach(vm.newsPhotoMobile,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//专题图片
 				if ( vm.currchnl == 3077) {
 					angular.forEach(vm.topicPhoto,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });;
                 }
 				//皖风徽韵
 				if ( vm.currchnl == 3078) {
 					angular.forEach(vm.anhuiStyle,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//历史资料
 				if ( vm.currchnl == 3079) {
 					angular.forEach(vm.anhuiHistory,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//各市图库
 				if ( vm.currchnl == 3080) {
 					angular.forEach(vm.anhuiCitys,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//视频
 				if ( vm.currchnl == 3081) {
 					angular.forEach(vm.anhuiVideo,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//艺苑菁华artEssence(安徽画坛)
 				if ( vm.currchnl == 3101) {
 					angular.forEach(vm.artEssence,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				
 				//网上展厅
 				if ( vm.currchnl == 3126) {
 					angular.forEach(vm.webDisplay ,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//漫画天地
 				if ( vm.currchnl == 3103) {
 					angular.forEach(vm.comicWorld,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//摄影名家
 				if ( vm.currchnl == 3125) {
 					angular.forEach(vm.famousPhotographers,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				
 				//互动空间
 				if ( vm.currchnl == 3083) {
 					angular.forEach(vm.interactiveSpace,function(item,index){
 	            		 if(oIndex==index){
 	            			 item.thumbsUpCount = resp.data;
 	            		 }
 	         		 });
                 }
 				//掌上图库
				if ( vm.currchnl == 3084) {
					angular.forEach(vm.handPics,function(item,index){
	            		 if(oIndex==index){
	            			 item.thumbsUpCount = resp.data;
	            		 }
	         		 });
                }
				//特别策划
				if ( vm.currchnl == 3085) {
					angular.forEach(vm.specialPlanning,function(item,index){
	            		 if(oIndex==index){
	            			 item.thumbsUpCount = resp.data;
	            		 }
	         		 });
                }
            	 
             }else if(resp.msg != '未登录'){
                 layer.alert(resp.msg);
             }
         });
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
        
      //  var activeNav = $('#swiper-container2 .swiper-slide').eq(vm.activeIndex).addClass('active-nav');
        //console.log("mySwiper3.activeIndex:"+mySwiper3.activeIndex);
        
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
    	vm.currpageForSearch = 1;
    	var index = $('#swiper-container2 .active-nav').index();
    	//var index = vm.activeIndex;//edit by xiayunan@20180202
    	//console.log("index:"+index);
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
	        	vm.currchnl = vm.chnlArr[7];  //网上展厅
	            break;
	        case 8:
	        	vm.currchnl = vm.chnlArr[8];  //漫画天地
	            break;
	        case 9:
	        	vm.currchnl = vm.chnlArr[9];  //摄影名家
	            break;
	        case 10:
	        	vm.currchnl = vm.chnlArr[10];  //互动空间
	            break;
	        case 11:
	        	vm.currchnl = vm.chnlArr[11];  //掌上图库
	            break;
	        case 12:
	        	vm.currchnl = vm.chnlArr[12];  //特别策划
	            break;
    	}
    	//$cookies.put("client_chnl", vm.currchnl, {path: '/'})
    	$cookies.put("client_chnl", vm.currchnl);
    	
    }

    /*
    var mySwiper1 = new Swiper('#swiper-container1',{//二级导航栏
        
    })*/
   
    
    var mySwiper3 = new Swiper('#swiper-container3',{
        onSlideChangeStart: function(){
            //updateNavPosition1(),
//        	vm.activeIndex = mySwiper3.activeIndex;
//        	$cookies.put("client_activeIndex", vm.activeIndex);//add by xiayunan@20180202
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
	  	var href = window.location.href;
	  	if(href.indexOf("newDetail")==-1){//细缆页不执行滚动出发事件
	  		vm.pageHeight = Math.max(document.body.scrollHeight,document.body.offsetHeight);
		    vm.viewportHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
		    vm.scrollHeight = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
	    	//真实内容的高度
	    	//var pageHeight = Math.max(document.body.scrollHeight,document.body.offsetHeight);
	    	//视窗的高度
	    	//var viewportHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
	    	//隐藏的高度
	    	//var scrollHeight = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
	    	if(vm.pageHeight - vm.viewportHeight - vm.scrollHeight < 650){//如果满足触发条件，执行
	    		vm.currpage++;
	    		req_appendMoreGroups(vm.currchnl,vm.currpage);
	    	}
	  		
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
				if (signId == 3101) {
                    vm.artEssence = jugeGroupPos.jugeGroupPos(3101,resp.data);
                    vm.currchnl = 3101;
                }
				
				//网上展厅
				if (signId == 3126) {
                    vm.webDisplay = jugeGroupPos.jugeGroupPos(3126,resp.data);
                    vm.currchnl = 3126;
                }
				
				//漫画天地
				if (signId == 3103) {
                    vm.comicWorld = jugeGroupPos.jugeGroupPos(3103,resp.data);
                    vm.currchnl = 3103;
                }
				
				//摄影名家
				if (signId == 3125) {
                    vm.famousPhotographers = jugeGroupPos.jugeGroupPos(3125,resp.data);
                    vm.currchnl = 3125;
                }
				
				
				//互动空间
				if (signId == 3083) {
                    vm.interactiveSpace = jugeGroupPos.jugeGroupPos(3097,resp.data);
                    vm.currchnl = 3083;
                }
				//掌上图库
				if (signId == 3084) {
                    vm.handPics = jugeGroupPos.jugeGroupPos(3084,resp.data);
                    vm.currchnl = 3084;
                }
				//特别策划
				if (signId == 3085) {
                    vm.specialPlanning = jugeGroupPos.jugeGroupPos(3085,resp.data);
                    vm.currchnl = 3085;
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
    
    //获取栏目信息
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
                if (pColumnId == 3101) {
                    vm.artEssence1 = resp.data;
                }
                
                //网上展厅
                if (pColumnId == 3126) {
                    vm.webDisplay1 = resp.data;
                }
                //漫画天地
                if (pColumnId == 3103) {
                    vm.comicWorld1 = resp.data;
                }
                //摄影名家
                if (pColumnId == 3125) {
                    vm.famousPhotographers1 = resp.data;
                }
                
                //互动空间
                if (pColumnId == 3083) {
                    vm.interactiveSpace1 = resp.data;
                }
                // 掌上图库
                if (pColumnId == 3084) {
                    vm.handPics1 = resp.data;
                }
                
                // 特别策划
                if (pColumnId == 3085) {
                    vm.specialPlanning1 = resp.data;
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
    
    
   
	
	//获取更多分类图片
    function req_getMoreGroups(signId,page){
        req.post('getPicture/getMoreGroups.do',{
            sginId: signId, //栏目id
            //cateId: cateId,
            rows: 3, //每页条数
            page: page,  //当前页码
            parameter: vm.searchStr,
            pType: 0,
            timeType: 2
        }).success(function(resp){
            if(resp.code == '211'){
                //新闻图片
				if (signId == 3064) {
					vm.newsPhotoMobile = resp.data;
					//vm.newsPhotoMobile = jugeGroupPos.jugeGroupPos(3064,resp.data);
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
				if (signId == 3101) {
                    vm.artEssence = resp.data;
                    //vm.currchnl = 3082;
                }
				
				//网上展厅
				if (signId == 3126) {
                    vm.webDisplay = resp.data;
                    //vm.currchnl = 3082;
                }
				
				//漫画天地
				if (signId == 3103) {
                    vm.comicWorld = resp.data;
                    //vm.currchnl = 3082;
                }
				
				//艺苑菁华artEssence
				if (signId == 3125) {
                    vm.famousPhotographers = resp.data;
                    //vm.currchnl = 3082;
                }
				//互动空间
				if (signId == 3083) {
                    vm.interactiveSpace = resp.data;
                    //vm.currchnl = 3083;
                }
				//掌上图库
				if (signId == 3084) {
                    vm.handPics = resp.data;
                    //vm.currchnl = 3084;
                }
				//特别策划
				if (signId == 3085) {
                    vm.specialPlanning = resp.data;
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
           // cateId: cateId,
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
				if (signId == 3101) {
					appendData(resp.data,vm.artEssence);
                }
				//网上展厅
				if (signId == 3126) {
					appendData(resp.data,vm.webDisplay);
                }
				//漫画天地
				if (signId == 3103) {
					appendData(resp.data,vm.comicWorld);
                }
				//摄影名家
				if (signId == 3125) {
					appendData(resp.data,vm.famousPhotographers);
                }
				//互动空间
				if (signId == 3083) {
					appendData(resp.data,vm.interactiveSpace);
                }
				//掌上图库
				if (signId == 3084) {
					appendData(resp.data,vm.handPics);
                }
				//特别策划
				if (signId == 3085) {
					appendData(resp.data,vm.specialPlanning);
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
				 TITLE: item.TITLE|| '',
				 columnName: item.columnName|| '',
				 thumbsUpCount: item.thumbsUpCount|| '',
				 isThumbsUp: item.isThumbsUp|| '',
				 VIDEO_ID:item.VIDEO_ID|| ''
			 });
		 });
    }
    
    
    
    
});