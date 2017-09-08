clientModule.controller('moreSortListCtrl',function($scope, $cookies, req, md5, $state, $rootScope, layerIfShow, $stateParams){
    var vm = this;
    //从路由获得分类id
    vm.cateId = $stateParams.cateId;
    //从路由获得签发分类sginId
    vm.sginId = $stateParams.sginId;
    //初始化页面相关配置
    function initSetting(){

    }

    function resetTempSortsData() {
        vm.tempSortsData = {
            time: 2,
            keyWord: '',
            pType: 0
        }
        vm.getMoreGroupsList = [];
        vm.moreSortList_total = 0;
        vm.itemsPerPage = 30;
        vm.pagination = {
            current: 1
        };
    }

    //时间范围
    vm.timeFrame = [
        {
            id: 0,
            name: '7天内'
        }, {
            id: 1,
            name: '一个月'
        }, {
            id: 2,
            name: '不限'
        }
    ];

    //按XX检索
    vm.retrievalType = [
        {
            id: 0,
            name: '作者'
        }, {
            id: 1,
            name: '文件名'
        }, {
            id: 2,
            name: '标题'
        }
    ];

    //页面初始化
    function init(){
        initSetting();
        resetTempSortsData();
        getMoreGroups(vm.pagination.current);
    }
    init();

    //获取更多分类图片
    function getMoreGroups(page){
        req.post('getPicture/getMoreGroups.do',{
            sginId: vm.sginId,
            cateId: vm.cateId,
            rows: vm.itemsPerPage,
            page: page,
            parameter: vm.tempSortsData.keyWord,
            pType: vm.tempSortsData.pType,
            timeType: vm.tempSortsData.time
        }).success(function(resp){
            if(resp.code == '211'){
                vm.getMoreGroupsList = resp.data;
                vm.moreSortList_total = resp.other;
                vm.totalPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getMoreGroups(pageNumber);
    };

    //点击检索
    vm.retrieval = function(){
        getMoreGroups(1);
    }
});