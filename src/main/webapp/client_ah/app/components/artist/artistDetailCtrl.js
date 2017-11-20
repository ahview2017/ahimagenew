clientModule.controller('photographerDetailCtrl',function($scope,$cookies,req,md5,$state,$stateParams,jugeGroupPos){
    var vm = this;

    //获取从路由传过来的稿件类型：0新闻图片，1专题图片
    vm.type = $stateParams.type;
    
    vm.userId = $stateParams.userId;
    console.log("userId:"+vm.userId);
    vm.sginId = $stateParams.sginId;
    console.log("sginId:"+vm.sginId);
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
    }
    //页面初始化
    function init(){
        initSetting();
        //getPropertiesGroups(vm.pagination.current);
        req_getClientGroups(vm.sginId,8,1,2);
        getPhotographerByUserId(vm.userId);
    }
    init();

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

});