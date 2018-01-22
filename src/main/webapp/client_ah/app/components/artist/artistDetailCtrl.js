clientModule.controller('artistDetailCtrl',function($scope,$sce,$cookies, req, md5, $state, $rootScope, $stateParams, getFullText, $timeout,jugeGroupPos){
    var vm = this;

    //从路由取得稿件id
    vm.groupId = $stateParams.groupId;

    //从路由取得图片id
    vm.pictureId = $stateParams.pictureId;


    //获取从路由传过来的稿件类型：0新闻图片，1专题图片
    vm.type = $stateParams.type;
    
    vm.userId = $stateParams.userId;
    vm.sginId = $stateParams.sginId;
    //初始化页面相关配置
    function initSetting(){
        vm.photoList = [];
        vm.photoList_total = 0;
        //默认每页10条
        vm.selPageRows = '20';
        vm.pagination = {
            current: 1
        };
        
        vm.allData = [];
        vm.firstData = [];
        vm.secondData = [];
        vm.thirdData = [];
        //选中图片id
        vm.selPicIds = {};
        
        //点赞数
        vm.thumbsUpCount = 0;
        
        //是否点过赞
        vm.isThumbsUp = false;
        
        //是否显示购物车
        vm.showStatus = 0;
    }
    //页面初始化
    function init(){
        initSetting();
        //getPropertiesGroups(vm.pagination.current);
        getThumbsUpCount();
        req_getClientGroups(vm.sginId,8,1,2);
        getPhotographerByUserId(vm.userId);
        getClientGroupPics(function(){
            req_getFullText(1);
        });
    }
    init();

    vm.thumbsUp = function(){
        saveGroupPicThumbsUp();
    };



    
    //稿件点赞
    function saveGroupPicThumbsUp(){
         req.post('groupPicCtro/saveGroupPicThumbsUp.do', {
             groupId: vm.groupId,
          }).success(function (resp) {
              if (resp.code == '211') {
                  if(resp.data.status==1){
                      //alert("您已经赞过了！");
                  }else if(resp.data.status==0){
                      //alert("太棒了，赞一个！"); 
                      vm.isThumbsUp = true;
                      
                  }
                  getThumbsUpCount();
              }else if(resp.msg != '未登录'){
                  layer.alert(resp.msg);
              }
          });
    }
    
    // 细览图片轮播控制
    // $(function () {
    //     $timeout(function () {
    //         $('.detial_content_pic').slide({
    //            mainCell:".pic_show ul",effect:"leftLoop",autoPlay:false,nextCell:".detail_prev",prevCell:".detail_next",events:"click"
    //         });
    //     }, 1000);
    // });
    $(function () {
        $timeout(function () {
            $(".detial_content_pic").slide({
                mainCell:".picDetails_bd ul",
                effect:"fold",
                autoPlay:false 
            });
            $(".detial_content_pic").slide({
                titCell:".tit ul",
                mainCell:".hd ul",
                prevCell:".next1",
                nextCell:".prev1",
                autoPage:true,
                effect:"left",
                autoPlay:false,
                scroll:6,
                vis:6
            });
        }, 1000);
    });
    
    //弹出微信
    
        //alert("太棒了，赞一个！");
        var qrcode = new QRCode(document.getElementById("qrcode"), {
            width : 100,
            height : 100
        });

        function makeCode2() {      
            var locurl = document.location.href;
            locurl = locurl.replace("index","index_m");
            qrcode.makeCode(locurl);
        }

        vm.popwin = function(){
        // alert("太棒了，赞一个！");
            makeCode2();
            $("#codeoutr").css("display","block");
        };
        vm.popclose = function(){

            $("#codeoutr").css("display","none");
        }
    

    //获取稿件点赞数 add by xiayunan@20171030
    function getThumbsUpCount(){
         req.post('groupPicCtro/getThumbsUpCount.do', {
            groupId: vm.groupId,
         }).success(function (resp) {
             if (resp.code == '211') {
                 vm.thumbsUpCount = resp.data;
             }else if(resp.msg != '未登录'){
                 layer.alert(resp.msg);
             }
         });
    }



    //获取新闻图片
    function getPropertiesGroups(pageNumber){
        req.post('getPicture/getPropertiesGroups.do',{
            type: vm.type,
            rows: vm.selPageRows,
            page: pageNumber,
            parameter:'',
            pType:'',
            timeType:'',
            langType:0
        }).success(function(resp){
            if(resp.code == '211'){
                vm.photoList = resp.data;
                vm.photoList_total = resp.other;
                vm.totalPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }


    //页数变化
    vm.pageChanged = function (pageNumber) {
        //getPropertiesGroups(pageNumber);
    };

    //获取客户端稿件详情
    function getClientGroupPics(callback) {
        req.post('getPicture/getClientGroupPics.do', {
            groupId: vm.groupId,
            picType: 1,
            size: 2,
            signId:vm.sginId
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.clientPictureDetail = resp.data;
                vm.groupKeyWords = resp.data.keywords;
                var videoId = resp.data.videoId;
                vm.videoId = videoId;
                if(videoId!=0&&vm.masBaseUrl){
                    vm.masUrl = vm.masBaseUrl+"&method=exPlay&type=vod&id="+videoId;
                    vm.masUrl = $sce.trustAsResourceUrl(vm.masUrl);
                }
                
                vm.showStatus = resp.data.showStatus;
                vm.isThumbsUp = resp.data.isThumbsUp;
                if(callback) callback();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    //获取首页签发的稿件请求
    function req_getClientGroups(signId,limit,picType,size) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: limit,
			picType: picType,
			size: size
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.allData = jugeGroupPos.jugeGroupPos(signId,resp.data);
                angular.forEach(vm.allData,function(item,index){
                	if(index<3){
                		vm.firstData.push(item);
                	}else if(index>=3&&index<5){
                		vm.secondData.push(item);
                	}else if(index>=5&&index<8){
                		vm.thirdData.push(item);
                	}
          		 });  
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    
    //获取摄影师列表
    function getPhotographerByUserId(userId){
        req.post('userCtro/getPhotographerByUserId.do',{
        	userId: userId,
        }).success(function(resp){
            if(resp.code == '211'){
                vm.trueName = resp.data.TURE_NAME;
                vm.photoPath = resp.data.STANDBY3;
                vm.userDetail = resp.data.USER_DETAIL;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    vm.showMoreSortList = function(){
    	var url = "/photo/index.html#/moreSortList/?sginId="+vm.sginId;
    	window.open(url);
    }

     //下载时切换图文
    vm.toggleImgTxt = function (downType) {
        vm.downType = downType;
    };

    //下载图片
    vm.downPic = function (picid) {
        req_genereateOrder(picid);
    };
    //订户下载生成订单请求
    function req_genereateOrder(picid) {
        req.post('downloadPicture/downForOrder.do', {
            picIds: picid
        }).success(function (resp) {
            if (resp.code == '211') {

                vm.orderList = resp.other;
                vm.orderId = resp.other.id;
                vm.pics= resp.data;
                vm.orderDetailId='';
                angular.forEach(vm.pics,function (iterator) {
                    vm.orderDetailId += iterator.id+",";
                });
                // alert(vm.orderDetailId);
                $('#down-pic-order-modal').modal('show');

            }else if(resp.msg != '未登录'){
               layer.alert(resp.msg);
            }
        });
    }

    //确定下载图片
    vm.sureDownPic = function (orderId, downWay) {
        req_sureDownPic(orderId, downWay);
    };
    //确定下载图片请求
    function req_sureDownPic(orderId, downWay) {
        var params =
            '?orderId=' + orderId +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByOrder.do' + params, '_blank');
    }

    //根据订单详情id下载图片
    vm.sureDownPicByOrderDetail = function (details, downWay) {
        req_sureDownPicByOrderDetailId(details, downWay);
    };
    //根据订单详情id下载图片
    function req_sureDownPicByOrderDetailId(details, downWay) {
        var params =
            '?picIds=' + details +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByPicId.do' + params, '_blank');
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

        //获取Mas视频基础URL add by xiayunan 20170907
    function getMasBaseUrl(){
        /*
        $.ajax({ 
              type : "get", 
              url : "groupPicCtro/getMasBaseUrl.do", 
              async : false, 
              success : function(resp){ 
                  if(resp.code == '211') {
                    vm.masBaseUrl = resp.data.masBaseUrl;
                  }else if(resp.msg != '未登录') {
                    layer.alert(resp.msg);
                  }
              } 
        });  
        */
        vm.masBaseUrl = 'http://vi.ahnews.com.cn/mas/openapi/pages.do?appKey=TRSPMS123';
        req.get('groupPicCtro/getMasBaseUrl.do').success(function(resp) {
            if(resp.code == '211') {
                vm.masBaseUrl = resp.data.masBaseUrl;
            }else if(resp.msg != '未登录') {
                layer.alert(resp.msg);
            }
        });
        
    }

    // 切换相似稿件图片
    $scope.changePic = function(addsubFlag){
        if(addsubFlag && ($scope.page <  $scope.similarTotalPage)){
            $scope.page++;
            req_getFullText($scope.page);
        }else if(!addsubFlag && ($scope.page > 1)){
            $scope.page--;
              req_getFullText($scope.page);
        }
    }


    //全选
    vm.checkAll = function () {
        angular.forEach(vm.clientPictureDetail.pics, function (item, index) {
            if (vm.selectedAll) {
                vm.selPicIds[item.id] = true;
            } else {
                vm.selPicIds[item.id] = false;
            }
        });
    };

    //判断是否选择了数据
    function judgeIfSelData() {
        vm.selKeyArr = [];
        for (var key in vm.selPicIds) {
            if (vm.selPicIds[key]) {
                vm.selKeyArr.push(key);
            }
        }
    }

    //获取选中图片ID
    function getPicIds() {
        vm.finalPicIds = '';
        for (var key in vm.selPicIds) {
            if (vm.selPicIds[key]) {
                vm.finalPicIds += key + ',';
            }
        }
        vm.picIds = vm.finalPicIds.slice(0, vm.finalPicIds.length - 1);
    }

    /**
     * 获取收藏夹
     */
    function getCollectBookData() {
        req.post('favorite/showAllFavoriteFolderName.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.collectBookArray = resp.data;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    /**
     * 点击是否显示收藏夹
     */
    vm.IsShowMineCollectFlag = false;
    vm.onIsShowMineCollectClick = function () {
        vm.IsShowMineCollectFlag = !vm.IsShowMineCollectFlag;
        if(vm.IsShowMineCollectFlag){
            // 获取收藏夹名
            getCollectBookData();
        }
    };

    /**
     * 添加收藏夹
     */
    vm.onAddCollectConClick = function () {
        if (!vm.addCollectNameModel) {
            layer.alert('请输入收藏夹名');
            return;
        }
        req.post("favorite/addFavoriteFolder.do", {
            folderName: vm.addCollectNameModel
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.addCollectNameModel = "";
                vm.onModalHide('favoriteAddModal');
                getCollectBookData();
                layer.msg("添加成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 将图片加入收藏夹
     */
    vm.onCollectPicClick = function (fileId) {
        req.post("favoriteFolderPic/addFavoriteFolderPic.do", {
            picid: vm.pictureId,
            folderId: fileId
        }).success(function (resp) {
            if (resp.code == '211') {
                layer.msg("收藏成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    /**
     * 将图片加入购物车
     */
    vm.onCartPicClick = function (picid) {
        req.post("car/add.do", {
            pictureId: picid,
        }).success(function (resp) {
            if (resp.code == '211') {
                contacts=resp.data.contacts
                notSales=resp.data.notSales
                if(contacts.length!=0 && notSales.length!=0 ){
                    layer.msg("包含不可出售图，张数用户不能添加定价图片")
                }else if(contacts.length!=0  ){
                    layer.msg("张数用户不能添加定价图片")
                }else if( notSales.length!=0 ){
                    layer.msg("包含不可出售图")
                }else{
                    layer.msg("加入购物车成功");
                }
                // layer.msg("加入购物车成功");
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    };

    // 请求
    getWebPublishData();

    //获取首页签发的稿件请求
    req_getClientGroups(25);
    function req_getClientGroups(signId) {
        req.post('getPicture/getClientGroups.do', {
            sginId: signId,
            limit: 10
        }).success(function (resp) {
            if (resp.code == '211') {
                //5一周最佳采用
                if (signId == 25) {
                    vm.bestUse = jugeGroupPos.jugeGroupPos(25,resp.data);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

});