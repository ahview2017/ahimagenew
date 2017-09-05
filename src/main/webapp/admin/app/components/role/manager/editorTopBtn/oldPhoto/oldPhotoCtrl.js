/**
 * Created by fangfang on 2016/11/25.
 */
adminModule.controller('oldPhotoCtrl', function ($scope, $cookies, req, md5, $state, $rootScope, Upload) {
    var vm = this;


    //页数变化
    vm.pageChanged = function (pageNumber) {
        getPhotoExcelList(pageNumber);
    };

    //初始化页面相关配置
    function initSetting(){
        //老照片数组
        vm.oldPhotoList = [];
        //老照片总条数
        vm.oldPicList_total = 0;
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
        req.post('oldPhotoUpload/getPhotoExcelList.do',{
            page: pageNum,
            rows: vm.selPageRows
        }).success(function(resp){
            if(resp.code && resp.code == 211){
                vm.oldPhotoList = resp.data;
                vm.totalPages = resp.page;
                vm.oldPicList_total = resp.other;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //上传老照片（旧版，不用了）
    /*vm.uploadOldPhoto = function(file){
        if(!file) return;
        Upload.upload({
            url: '/cnsphoto/oldPhotoUpload/photoExcelUpload.do',
            data: {
                zipFile: file
            }
        }).then(function (resp) {
            console.log('sucess');
            if(resp.data.code == 'success'){
                layer.alert(resp.data.msg);
            }else if(resp.msg != '未登录'){
                layer.alert(resp.data.msg);
            }
        }, function (resp) {
            console.log('Error status: ' + resp.status);
        });
    }*/


    //确认上传老照片
    vm.confirmUpOldPic = function(){
        req.post('oldPhotoUpload/localZipUpload.do',{
            fileName: vm.oldZipName
        }).success(function(resp){
            if(resp.code && resp.code == 211){
               layer.alert('操作成功');
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //查询入库结果
    vm.queryStorageResult = function(excelId){
        req.post('oldPhotoUpload/getOldPicResult.do',{
            excelId: excelId
        }).success(function(resp){
            if(resp.code && resp.code == 211){
                if(resp.data && resp.data.length != 0){
                    vm.storageOldPics  = resp.data;
                    $('#query-storage-modal').modal('show');
                }else{
                    layer.msg('入库成功');
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }




});