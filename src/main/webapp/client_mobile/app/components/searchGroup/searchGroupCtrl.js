clientModule.controller('searchGroupCtrl', function ($scope, $cookies, req, md5, $state,  $stateParams,$rootScope, $timeout,jugeGroupPos,$filter,modalOperate) {
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
    	vm.isSearch = true;
    	//校验检索词是否为空
    	if(!vm.searchStr){
    		layer.alert("请输入检索词");
    	}
    	req_getMoreGroups(1);
    	
    	vm.currpage = 1;
    }
 
    //获取对应栏目的稿件
    vm.getMoreGroups = function (page) {
        req_getMoreGroups(page);
    };
    
    
    //初始化页面相关配置
    function initSetting() {
    	vm.currpage = 1;
	    //vm.currchnl = 3064;
    	vm.currpageForSearch = 1;
    	vm.isSearch = false;
	    //点赞数
        vm.thumbsUpCount = 0;
	    vm.pageHeight = Math.max(document.body.scrollHeight,document.body.offsetHeight);
	    vm.viewportHeight = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight || 0;
	    vm.scrollHeight = window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop || 0;
//        getselCpCategories(function(category){
//            angular.forEach(category,function(item,index){
//                if(item.categoryName == '新闻类别'){
//                    vm.categories = item.categories;
//                }
//            });
//        });
        
        //updateNavPosition();
        //switchChnlTab();
	    
	    
	    //判断url是否有searchKey
	   // var searchStr = getUrlParam("searchStr");
	    
	    var searchStr = $cookies.get('searchStr')
	    
	    
	   // var searchStr = '大雪';
	    //alert("searchStr:"+searchStr);
	    if (searchStr != null){
	    	vm.isSearch = true;
	    	vm.searchStr = searchStr;
	    	vm.getMoreGroups(1);
	    }
	    	

    }
    
    
    //页面初始化
    function init() {
        initSetting();
        //req_getClientPicture();
        //getClientGroupPics();
    }
    
    init();
    
    
    //获取url中的参数
    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg); //匹配目标参数
        if (r != null) return r[2];
        return null; //返回参数值
    }
    
//    function initChnlData(){
//    	vm.getMoreGroups(3064,1);
//    }

    vm.thumbsUp = function(groupId,index){
    	saveGroupPicThumbsUp(groupId,index);
    };



    
    //稿件点赞
    function saveGroupPicThumbsUp(groupId,oIndex){
    	 req.post('groupPicCtro/saveGroupPicThumbsUp.do', {
             groupId: groupId,
          }).success(function (resp) {
              if (resp.code == '211') {
            	  if(resp.data.status==1){
            		  //alert("您已经赞过了！");
            	  }else if(resp.data.status==0){
       					angular.forEach(vm.searchGroups,function(item,index){
       	            		 if(oIndex==index){
       	            			 item.isThumbsUp = true;
       	            		 }
       	         		});
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
            	 angular.forEach(vm.searchGroups,function(item,index){
            		 if(oIndex==index){
            			 item.thumbsUpCount = resp.data;
            		 }
         		 });
            	
            	 
             }else if(resp.msg != '未登录'){
                 layer.alert(resp.msg);
             }
         });
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
	    		if(vm.isSearch){
	    			vm.currpageForSearch++;
		    		req_appendMoreGroups(vm.currpageForSearch);
	    		}else{
	    			vm.currpage++;
		    		req_appendMoreGroups(vm.currpage);
	    		}
	    		
	    	}
	  	}
    }

	//我要投稿  add by xiayunan@20171017
	vm.sendArticle = function(){
		var jumpUrl ='/photo/index_m.html#/newArticle/';
		window.open(jumpUrl);
	};
	
   
	
	//获取更多分类图片
    function req_getMoreGroups(page){
        req.post('getPicture/getMobileSearchGroups.do',{
            rows: 3, //每页条数
            page: page,  //当前页码
            parameter: vm.searchStr,
            pType: 3,
            timeType: 2
        }).success(function(resp){
            if(resp.code == '211'){
            	vm.searchGroups = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    
    //获取更多分类图片
    function req_appendMoreGroups(page){
        req.post('getPicture/getMobileSearchGroups.do',{
           // cateId: cateId,
            rows: 3, //每页条数
            page: page,  //当前页码
            parameter: vm.searchStr,
            pType: 3,
            timeType: 2
        }).success(function(resp){
            if(resp.code == '211'){
            	appendData(resp.data,vm.searchGroups);
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