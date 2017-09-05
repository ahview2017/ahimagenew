/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('toPublishPicCtrl', function ($scope, $cookies, req, $stateParams) {
    var vm = this;

    //获取从路由传过来的excelId
    vm.excelId = $stateParams.id;

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getPhotoExcelList(pageNumber);
    };

    //初始化页面相关配置
    function initSetting(){
        //待发布图片数组
        vm.listData = [];
        //待发布图片总条数
        vm.topubPicList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
        //总条数
        vm.totalPages = 1;
    }
    // 初始化
    function init() {
        initSetting();
        getPhotoExcelList(1);
    }

    init();

    //获取老照片列表
    function getPhotoExcelList(pageNum){
        req.post('oldPhotoUpload/getExcelPhotoExcelList.do',{
            excelId: vm.excelId,
            page: pageNum,
            rows: vm.selPageRows
        }).success(function(resp){
            if(resp.code && resp.code == 211){
                vm.toPublishPicList = resp.data;
                vm.totalPages = resp.page;
                vm.topubPicList_total = resp.other;
            }else if(resp.msg != '未登录'){
                console.log(resp.msg);
            }
        });
    }

});