/**
 * Created by Sun on 2017/1/20.
 */
clientModule.controller('needChannelCtrl',function($scope,$cookies,req,md5,$state,$rootScope){
    var vm = this;

    function initSetting(){
        vm.demandList = [];
        vm.needList_total = 0;
        vm.itemsPerPage = 10;
        vm.pagination = {
            current: 1
        };
    }

    //需求通道列表
    function renderList(pageNumber) {
        req.post('needs/showNeedToClient.do',{
            page: pageNumber,
            langType:0
        }).success(function(resp){
            if(resp.code && resp.code == 211){
                vm.demandList = resp.data;
                vm.needList_total = resp.other;
                vm.demandPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页面初始化
    function init() {
        initSetting();
        renderList(1);
    }

    init();

    //页数变化
    vm.pageChanged = function (pageNumber) {
        renderList(pageNumber);
    };




});