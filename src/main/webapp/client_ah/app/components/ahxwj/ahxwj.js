clientModule.controller('ahxwj',function($scope,$cookies,req,md5,$state,$stateParams){
    var vm = this;

    //获取从路由传过来的稿件类型：0新闻图片，1专题图片
   // vm.type = $stateParams.type;

    //初始化页面相关配置
    function initSetting(){
        vm.photoList = [];
        vm.photoList_total = 0;
        //默认每页10条
        vm.selPageRows = '10';
        vm.pagination = {
            current: 1
        };
    }
    //页面初始化
    function init(){
        initSetting();
        getPhotographerList(vm.pagination.current);
        
    }
    init();

    //获取摄影师列表
    function getPhotographerList(pageNumber){
        req.post('userCtro/getArtistList.do',{
            rows: vm.selPageRows,
            page: pageNumber,
        }).success(function(resp){
            if(resp.code == '211'){
                vm.photoList = resp.data;
                vm.photoList_total = resp.other;
                vm.totalPages = resp.page;
                changeShowStatus();//改变显示状态，0：左边，1：右边
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    
    //改变显示状态:0:左边 1：右边
    function changeShowStatus(){
    	angular.forEach(vm.photoList,function(item,index){
      		 if(index%2==0){
      			item.showStatus = 0;
      		 }else{
      			item.showStatus = 1;
      		 }
   		 });
    }


    //页数变化
    vm.pageChanged = function (pageNumber) {
    	getPhotographerList(pageNumber);
    };

});