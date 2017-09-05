clientModule.controller('photoListCtrl',function($scope,$cookies,req,md5,$state,$stateParams){
    var vm = this;

    //获取从路由传过来的稿件类型：0新闻图片，1专题图片
    vm.type = $stateParams.type;

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
        getPropertiesGroups(vm.pagination.current);
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
        getPropertiesGroups(pageNumber);
    };

});