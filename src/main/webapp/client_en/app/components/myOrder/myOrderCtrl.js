/**
 * Created by Sun on 2017/1/20.
 */
photo_enModule.controller('myOrderCtrl',function($scope,$cookies,req,md5,$state,$rootScope,modalOperate,getFullTexts){
    var vm = this;
    var client_width=$(document).height()-360+'px';
    $('.need_channel').css('min-height',client_width);
    //初始化设置
    function initSetting(){
        vm.myOrderList = [];
        vm.myOrder_total = 0;
        vm.itemsPerPage = 10;
        vm.pagination = {
            current: 1
        };
    }

    //页面初始化
    function init() {
        initSetting();
        //getMyOrders(1);
    }

    init();

/*    //页数变化
    vm.pageChanged = function (pageNumber) {
        getMyOrders(pageNumber);
    };


    //获取我的订单
    function getMyOrders(pageNumber){
        req.post('order/getMyOrders.do',{
            page: pageNumber,
            langType:window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){console.log(resp.data);
                    vm.myOrderList = resp.data;
                    vm.myOrder_total = resp.other;
                    vm.myOrderPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }*/
    vm.req_fullText = function (page) {
        var fullParams = {
            page: page, 
            langType: window.localStorage.lang
        }
        getFullTexts.req_getFullText(fullParams,function (resp) {
            if(resp.code == 211){
                vm.myOrderList = resp.data;console.log(resp.data);
                vm.myOrderPages = resp.page || 1;
                vm.myOrder_total = resp.other || 0;
                if(resp.page && resp.page >=  1){
                    $('#jq-page').pagination({
                        totalData:resp.other,
                        pageCount:resp.page,
                        current: page,
                        jump:true,
                        coping:true,
                        homePage:'首页',
                        endPage:'末页',
                        prevContent:'上一页',
                        nextContent:'下一页',
                        showData:10,
                        jumpIptCls: 'toPageNum',
                        jumpBtnCls: 'toPageBtn',
                        callback: function (num) {
                            vm.req_fullText(num);
                            vm.pageNum = num;
                        }
                    });
                }
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    vm.req_fullText(1);
    //删除订单
    vm.delOrder = function(orderNum){
        //询问框
        layer.confirm('是否确认删除改订单？', {
            btn: ['是','否'] //按钮
        }, function(){
            req_delOrder(orderNum);
        });
    }
    //删除订单请求
    function req_delOrder(orderNum){
        req.post('order/delete.do',{
            id: orderNum,
            langType:window.localStorage.lang
        }).success(function(resp){
            if(resp.code == '211'){
                layer.alert('操作成功');
                getMyOrders();
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //去结算
    vm.settleCounts = function(orderId){
        vm.orderId = orderId;
       $('#settle-counts-modal').modal('show');
    }
    //确定下载图片
    vm.sureDownPic = function(orderId,downWay){
        req_sureDownPic(orderId,downWay);
    }
    //确定下载图片请求
    function req_sureDownPic(orderId,downWay){
        var params =
            '?orderId=' + vm.orderId +
            '&type=' + downWay;
        window.open('/photo/downloadPicture/downByOrder.do'+ params,'_blank');
    }

});