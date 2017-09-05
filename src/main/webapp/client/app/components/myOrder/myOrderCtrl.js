/**
 * Created by Sun on 2017/1/20.
 */
clientModule.controller('myOrderCtrl',function($scope,$cookies,req,md5,$state,$rootScope,modalOperate){
    var vm = this;

    //初始化设置
    function initSetting(){
        vm.myOrderList = [];
        vm.myOrder_total = 0;
        vm.itemsPerPage = 10;
        vm.pagination = {
            current: 1
        };
        // 初始化订单搜索对象
        vm.search = {
            orderNum: '',
            picCount:'',
            orderStatus:'',
            createStartTime:'',
            createEndTime:''
        }

        //从cookie获取客户端用户名
        vm.client_uName = $cookies.get('client_uname');
    }

    //页面初始化
    function init() {
        initSetting();
        getMyOrders();
    }


    init();

    //页数变化
    vm.pageChanged = function (pageNumber) {
        getMyOrders(pageNumber);
    };
    // 查看所有已下载图片
    vm.lookAllDownPic = function(){
      var lookAllDownPicUrl  = $state.href('root.myInfo',{activeTab:7});
      window.open(lookAllDownPicUrl,'_blank');
    }


    //获取我的订单
    function getMyOrders(pageNumber){
        req.post('order/getMyOrders.do',{
            page: pageNumber,
            langType:0
        }).success(function(resp){
            if(resp.code == '211'){
                    vm.myOrderList = resp.data;
                    vm.myOrder_total = resp.other;
                    vm.myOrderPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    // 回车搜索
    vm.enterSearch = function(e){
        var keyCode = window.event ? e.keyCode : e.which;
        if (keyCode == 13) {
            vm.searchMyOrder();
        }
    }
    // 订单状态搜索
    vm.changeOrderStatus = function(){
        vm.searchMyOrder();
    }
    // 订单搜索
    vm.searchMyOrder = function(){
        req_advSearchOrder();
    }
    // 订单检索请求
    function req_advSearchOrder(){
        req.post('order/advancedSearch.do',{
            userName: vm.client_uName,
            orderno: vm.search.orderNum,
            staNum: vm.search.picCount,
            endNum: vm.search.picCount,
            orderStatus: vm.search.orderStatus,
            createStartTime: vm.search.createStartTime,
            createEndTime: vm.search.createEndTime,
        }).success(function(resp){
            if(resp.code == '211'){
                console.log('success');
                vm.myOrderList = resp.data;
                vm.myOrder_total = resp.other;
                vm.myOrderPages = resp.page;
            }else if(resp.msg != '未登录'){
                layer.alert(resp.msg);
            }
        });
    }
    //删除订单
    vm.delOrder = function(orderNum,orderId){
        //询问框
        layer.confirm('是否确认删除改订单？', {
            btn: ['是','否'] //按钮
        }, function(){
            req_delOrder(orderNum,orderId);
        });
    }
    //删除订单请求
    function req_delOrder(orderNum,orderId){
        req.post('order/delete.do',{
            id: orderId,
            orderNo:orderNum
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
       /* req.post('downloadPicture/downByOrder.do',{
                orderId: vm.orderId,
                type: downWay
            }).success(function(resp){
                //以5开头的code(比如511,514)为错误信息，此时不进行下载
                var codeStr = '';
                if(resp && resp.code){
                    codeStr = resp.code.toString();
                }
                if(resp && codeStr.indexOf(5) == 0){
                    layer.msg(resp.msg);
                }else{
                var params =
                    '?orderId=' + vm.orderId +
                    '&type=' + downWay;
                window.open('/cnsphoto/downloadPicture/downByOrder.do'+ params,'_blank');
            }
        });*/
        var params =
            '?orderId=' + vm.orderId +
            '&type=' + downWay;
        window.open('/cnsphoto/downloadPicture/downByOrder.do'+ params,'_blank');
    }

});