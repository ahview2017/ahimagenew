/**
 * Created by Sun on 2016/12/30.
 */
clientModule.controller('picsDetailCtrl', function ($scope,$sce,$cookies, req, md5, $state, $rootScope, $stateParams, getFullText, $timeout,jugeGroupPos) {
    var vm = this;
    
    //从路由取得稿件id
    vm.groupId = $stateParams.groupId;

	//从路由取得图片id
    vm.pictureId = $stateParams.pictureId;


    //初始化页面相关配置
    function initSetting() {
        //选中图片id
        vm.selPicIds = {};
        //默认页
        $scope.page = 1;
        
        getMasBaseUrl();
    }




    //获取客户端稿件详情
    function getClientGroupPics(callback) {
        req.post('getPicture/getClientGroupPics.do', {
            groupId: vm.groupId,
			picType: 1,
			size: 2
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.clientPictureDetail = resp.data;
                vm.groupKeyWords = resp.data.keywords;
                
				var videoId = resp.data.videoId;
				//console.log("videoId:"+videoId);
				vm.videoId = videoId;
				vm.masUrl = '';
				
				if(videoId!=0&&vm.masBaseUrl!=''){
					vm.masUrl = vm.masBaseUrl+"&method=exPlay&type=vod&id="+videoId;
					vm.masUrl = $sce.trustAsResourceUrl(vm.masUrl);
				}
				//console.log("vm.masUrl:"+vm.masUrl);

                if(callback) callback();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    // 全文检索-检索相似稿件
    function req_getFullText(page){    
            if(page){
                $scope.page = page;
            }
            getFullText.req_getFullText({
                page: $scope.page,
                rows: 4,
                gKEYWORDS: vm.groupKeyWords
            },function (resp) {
                if(resp.code == 211){
                    vm.similarMs = resp.data;
                    $scope.similarTotalPage = resp.page;
                }else if(resp.msg != '未登录'){
                    layer.alert(resp.msg);
                }
            });
     }
    //页面初始化
    function init() {
        initSetting();
        getClientGroupPics(function(){
            req_getFullText(1);
        });
        
		

    }

    init();



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


    // 细览图片轮播控制
    $(function () {
        $timeout(function () {
            $('.detial_content_pic').slide({
               mainCell:".pic_show ul",effect:"leftLoop",autoPlay:false,nextCell:".detail_prev",prevCell:".detail_next",events:"click"
            });
        }, 1000);
    });


    // $(function(){
    //     vm.picCount=$("#picCount").html();
    //     $(".span_color_red").html=vm.picCount;
    // })


    //判断是否选择了数据
    function judgeIfSelData() {
        vm.selKeyArr = [];
        for (var key in vm.selPicIds) {
            if (vm.selPicIds[key]) {
                vm.selKeyArr.push(key);
               // console.log(vm.selKeyArr.length);
            }
        }
       // console.log(vm.selKeyArr.length);
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

    //下载图片
   /* vm.downPic = function() {
        judgeIfSelData();
        if (vm.selKeyArr.length < 1) {
            layer.alert('请至少选择一个图片进行操作');
            return;
        } else {
            getPicIds();
            req_genereateOrder();
        }

    };
    //订户下载生成订单请求
    function req_genereateOrder(callback) {
        req.post('downloadPicture/downForOrder.do', {
            picIds: vm.picIds
        }).success(function (resp) {
            if (resp.code == '211') {
                vm.orderList = resp.other;
                vm.orderId = resp.other.id;
                vm.pics= resp.data;
                vm.orderDetailId='';
                angular.forEach(vm.pics,function (iterator) {
                    vm.orderDetailId += iterator.id+",";
                });
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
            '&langType=' + 0 +
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
    } */

    /**
     * 获取网站公告
     */
    /*function getWebPublishData() {
        req.post('notice/showToHomePage.do', {}).success(function (resp) {
            if (resp.code == '211') {
                vm.webPublishArray = resp.data;console.log(resp.data);
                if (vm.webPublishArray.length > 2) {
                    vm.webPublishArray = vm.webPublishArray.slice(0, 2);
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //加入购物车
    vm.addCart = function () {
        judgeIfSelData();
        if (vm.selKeyArr.length < 1) {
            layer.alert('请至少选择一个图片进行操作');
            return;
        } else {
            getPicIds();
            req_onCartPicClick();
        }

    };

    //加入购物车请求
    function req_onCartPicClick() {
        req.post("car/add.do", {
            pictureId: vm.picIds,
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
    }; */

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
              //  console.log(vm.bestUse);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
});
