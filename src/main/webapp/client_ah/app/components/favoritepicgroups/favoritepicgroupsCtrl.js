clientModule.controller('favoritepicgroupsCtrl',function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, $stateParams){
    var vm = this;

    function resetTempSortsData() {
        vm.getMoreGroupsList = [];
        vm.favoritepicgroups_total = 0;
        vm.itemsPerPage = 10;
        vm.pagination = {
            current: 1
        };
    }

    //页面初始化
    function init(){
    	resetTempSortsData();
    	getFavoriteGroups(vm.pagination.current);
    }
    init();

    //获取更多分类图片
    function getFavoriteGroups(page){
        req.post('favoriteGroupPics/getFavoritePicGroups.do',{
            rows: vm.itemsPerPage,
            page: page,
        }).success(function(resp){
            if(resp.code == '211'){
                vm.getMoreGroupsList = resp.data;
                vm.favoritepicgroups_total = resp.other;
                vm.totalPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
    	getFavoriteGroups(pageNumber);
    };

});