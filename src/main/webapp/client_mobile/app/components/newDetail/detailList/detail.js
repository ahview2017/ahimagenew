

/**
 * Created by Sun on 2016/12/30.
 */
clientModule.controller('detail', function ($scope,$sce,$cookies, req, md5, $state, $rootScope, $stateParams, getFullText, $timeout,jugeGroupPos) {
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
    }



// $(document).ready(function() {
// 	$("#auto-loop").lightGallery({
// 		loop:true,
// 		auto:true,
// 		pause:2000,
// 		lang: {
//             allPhotos: '动车穿越大湖名城'
//         },
//         vimeoColor: 'cccccc',
// 	});
// });
//首先将#back-to-top隐藏 
// $("#back-to-top").hide(); 
// //当滚动条的位置处于距顶部100像素以下时，跳转链接出现，否则消失 
// $(function () { 
// $(window).scroll(function(){ 
// if ($(window).scrollTop()>100){ 
// $("#back-to-top").fadeIn(200); 
// } 
// else
// { 
// $("#back-to-top").fadeOut(200); 
// } 
// }); 
// //当点击跳转链接后，回到页面顶部位置 
// $("#back-to-top").click(function(){ 
// $('body,html').animate({scrollTop:0},100); 
// return false; 
// }); 
// }); 


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
				if(videoId!=0){
					vm.masUrl = vm.masBaseUrl+"&method=exPlay&type=vod&id="+videoId;
				}
				vm.masUrl = $sce.trustAsResourceUrl(vm.masUrl);
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
		getMasBaseUrl();
        getClientGroupPics(function(){
            req_getFullText(1);
        });
    }

    init();



	//获取Mas视频基础URL add by xiayunan 20170907
	function getMasBaseUrl(){
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

    // 请求
    getWebPublishData();
});

