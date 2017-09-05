/**
 * 订单管理
 */
adminModule.controller('orderManageCtrl', function ($scope, $cookies, req, md5, $state, $rootScope,
                                                    modalOperate,allModalMove) {
    var vm = this;

    //模态框遮罩层显示
    function maskShow() {
        $rootScope.layerIfShow = true;
    }

    //模态框遮罩层隐藏
    function maskHide() {
        $rootScope.layerIfShow = false;
    }
   //移动模态框
    vm.moveModal = function(dragDiv,tagDiv) {
        allModalMove.modalMove(dragDiv,tagDiv);
    }
    //订单模态框显示
    vm.orderModalShow = function (modalId) {
        $('#' + modalId).show();
        maskShow();
    };
    //订单模态框隐藏
    vm.orderModalHide = function (modalId) {
        $('#' + modalId).hide();
        maskHide();
    };
    //订单高级检索模态框隐藏
    vm.orderSearchModalHide = function (modalId) {
        modalOperate.modalHide(modalId);
    };
    //订单高级检索模态框显示
    vm.advanceSearchModalShow = function (modalId) {
        modalOperate.modalShow(modalId);
    }

    //初始化相关配置
    function initSetting(){
        //存放订单数组
        vm.orderTableArray = [];
        //订单总条数
        vm.orderList_total = 0;
        //默认当前页1
        vm.pagination = {
            current: 1
        };
        //默认每页10条
        vm.selPageRows = '10';
        //检索类型
        vm.orderSearchType = 1;
        //金额类型
        vm.orderNumFlag = true;
        //数量类型
        vm.orderMoneyFlag = true;
    }
    //初始化
    function init(){
        initSetting();
        getOrderTableData(3, "", 1, 1);
    }
    init();


    /**
     * 获取订单表格数据
     */
    function getOrderTableData(searchType, searchTitle, page, type) {
        var searchUrl = "";
        var paramsObj = {
            page: page,
            rows: vm.selPageRows,
            langType: window.localStorage.lang
        };
        if (searchType == 1) {
            searchUrl = "order/search.do";
            paramsObj['orderno'] = searchTitle;
        }else if(searchType == 2) {
            searchUrl = "order/advancedSearch.do";
            paramsObj['orderno'] = vm.search.orderno;
            paramsObj['userName'] = vm.search.userName;
            paramsObj['orderStatus'] = vm.search.orderStatus;
            paramsObj['createStartTime'] = vm.search.createStartTime;
            paramsObj['createEndTime'] = vm.search.createEndTime;
            paramsObj['payStartTime'] = vm.search.payStartTime;
            paramsObj['payEndTime'] = vm.search.payEndTime;
            paramsObj['staMoney'] = vm.search.staMoney;
            paramsObj['endMoney'] = vm.search.endMoney;
            paramsObj['staNum'] = vm.search.staNum;
            paramsObj['endNum'] = vm.search.endNum;
        } else {
            searchUrl = "order/show.do";
        }
        req.post(searchUrl, paramsObj).success(function (resp) {
            if (resp.code && resp.code == 211) {
                vm.orderTableArray = resp.data;
                vm.totalPages = resp.page;
                vm.orderList_total = resp.other;
                vm.pagination.current = page;
                if(searchType == 1){
                    vm.orderSearchType = 1;
                }else if(searchType == 2){
                    vm.orderSearchType = 2;
                    vm.orderSearchModalHide('orderManage-manuscript-search-modal');
                }else{
                    vm.orderSearchType = 3;
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getOrderTableData(vm.orderSearchType, vm.searchOrderModel, pageNumber, 1);
    };
    //状态变化
    vm.getOrderStateChange = function () {
        if(vm.search.orderMoney==2){
            vm.orderMoneyFlag = false;
        }else {
            vm.orderMoneyFlag = true;
        }
        if(vm.search.orderNum==2){
            vm.orderNumFlag = false;
        }else {
            vm.orderNumFlag = true;
        }

    }
    /**
     * 点击搜索
     */
    vm.onSearchOrderClick = function () {
        getOrderTableData(1, vm.searchOrderModel, 1, 1);
    };
    /**
     *  高级检索
     */
    vm.orderAdvanceSearch = function () {
        if(vm.search.orderMoney==0){
            vm.search.staMoney = 0;
            vm.search.endMoney = 49;
        }else if(vm.search.orderMoney==1){
            vm.search.staMoney = 50;
            vm.search.endMoney = null;
        }
        if(vm.search.orderNum==0){
            vm.search.staNum = 0;
            vm.search.endNum = 4;
        }else if(vm.search.orderNum==1){
            vm.search.staNum = 5;
            vm.search.endNum = null;
        }
        getOrderTableData(2, vm.searchOrderModel, 1, 1);
    }
    /**
     * 回车搜索
     */
    vm.onEnterSearchClick = function (e) {
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.onSearchOrderClick();
        }
    };

    //查看图片
    vm.lookPic = function(orderId){
        var jumpUrl ='';
        if(window.localStorage.lang==0){
            jumpUrl = '/#/lookPics/' + orderId;
        }else{
            jumpUrl = '/cnsphoto_en/index.html#/lookPics/' + orderId;
        }
        window.open(jumpUrl,'_blank');
    }
    //下载订单图片
    vm.downOrderPic = function(orderId){
        req_downOrderPic(orderId);
    }
    //下载订单图片请求
    function  req_downOrderPic(orderId){
        var params  = '?orderId=' + orderId;
        window.open('/cnsphoto/downloadPicture/adminDownByOrder.do' + params,'_blank');
    }
});